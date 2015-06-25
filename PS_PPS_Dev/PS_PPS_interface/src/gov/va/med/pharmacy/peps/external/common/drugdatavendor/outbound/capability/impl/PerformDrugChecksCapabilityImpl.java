/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugChecksCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugDoseCheckCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugDrugCheckCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugTherapyCheckCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.DoseTypeUtility;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility.RouteUtility;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckVo;

import firstdatabank.database.FDBException;
import firstdatabank.dif.FDBDrugType;
import firstdatabank.dif.FDBUnknownIDException;
import firstdatabank.dif.ScreenDrug;
import firstdatabank.dif.ScreenDrugs;


/**
 * Convert drugs into FDB ScreenDrugs and call the individual drug check capabilities to perform the checks.
 * 
 * This class is abstract so that Spring can provide lookup method injection to give new instances of ScreenDrugs and
 * ScreenDrug FDB objects.
 * 
 * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
 */
public abstract class PerformDrugChecksCapabilityImpl implements PerformDrugChecksCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(PerformDrugChecksCapabilityImpl.class);

    // Dummy values used for general dose checks such to fulfill the FDB API requirements
    private static final double SINGLE_DOSE_AMMOUNT = 1;
    private static final String DOSE_UNIT = "G";
    private static final String DAY = "DAY";
    private static final String FREQUENCY = "Q4H";
    private static final long DURATION = 1;
    

    private PerformDrugDoseCheckCapability performDrugDoseCheckCapability;
    private PerformDrugDrugCheckCapability performDrugDrugCheckCapability;
    private PerformDrugTherapyCheckCapability performDrugTherapyCheckCapability;

    /**
     * Convert drugs into FDB ScreenDrugs and call the individual drug check capabilities to perform the checks.
     * 
     * @param requestVo OrderCheckVo requesting checks
     * @return OrderCheckResultsVo with results from drug data vendor
     */
    public OrderCheckResultsVo performDrugChecks(OrderCheckVo requestVo) {
        OrderCheckResultsVo results = new OrderCheckResultsVo();
        ScreenDrugs screenDrugs = newScreenDrugsInstance();
        ScreenDrugs doseCheckScreenDrugs = newScreenDrugsInstance();

        Map<String, DrugCheckVo> drugMap = new HashMap<String, DrugCheckVo>();

        int uniqueId = 0;

        for (DrugCheckVo drug : requestVo.getDrugsToScreen()) {
            try {
                ScreenDrug screenDrug = toScreenDrug(drug);

                // To be sure to make all drugs unique as GCN sequence number, IEN, and order number are not unique,
                // use an internal incremented unique ID.
                drugMap.put(String.valueOf(uniqueId), drug);
                screenDrug.setCustomID(String.valueOf(uniqueId));
                uniqueId++;

                // Dose checks only get prospective drugs with dosing information
                if (drug.isProspective() && drug.isDoseCheck()) {
                    doseCheckScreenDrugs.addItem(screenDrug);
                }

                screenDrugs.addItem(screenDrug);
            } catch (FDBUnknownIDException e) {
                LOG.warn("Drug with GCN Sequence Number " + drug.getGcnSeqNo() + " was not checked");
                results.getDrugsNotChecked().add(drug);
            } catch (FDBException e) {
                throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
            }
        }

        if (requestVo.isDrugDoseCheck()) {
            results.setDrugDoseCheckResults(performDrugDoseCheckCapability.performDrugDoseCheck(doseCheckScreenDrugs,
                drugMap, requestVo.isProspectiveOnly(), requestVo.getAgeInDays(), requestVo.getWeightInKg(), requestVo
                    .getBodySurfaceAreaInSqM(), requestVo.isCustomTables()));
        }

        if (requestVo.isDrugDrugCheck()) {
            results.setDrugDrugCheckResults(performDrugDrugCheckCapability.performDrugDrugCheck(screenDrugs, drugMap,
                requestVo.isProspectiveOnly(), requestVo.isCustomTables()));
        }

        if (requestVo.isDrugTherapyCheck()) {
            results.setDrugTherapyCheckResults(performDrugTherapyCheckCapability.performDrugTherapyCheck(screenDrugs,
                drugMap, requestVo.isProspectiveOnly(), requestVo.isDuplicateAllowance(), requestVo.isCustomTables()));
        }

        return results;
    }

    /**
     * Convert DrugCheckVO drug into a ScreenDrug
     * 
     * @param drug Drug to convert
     * @return ScreenDrug from VO
     * @throws FDBException if error loading ScreenDrug from VO
     */
    private ScreenDrug toScreenDrug(DrugCheckVo drug) throws FDBException {
        ScreenDrug screenDrug = newScreenDrugInstance();
        screenDrug.load(drug.getGcnSeqNo(), FDBDrugType.fdbDTGCNSeqNo);
        screenDrug.setProspective(drug.isProspective());

        if (drug.getDrugName().trim().length() > 0) {
            screenDrug.setDescription(drug.getDrugName());
        }

        if (drug.isDoseCheck()) {
            screenDrug.setDoseRoute(RouteUtility.routeNameToId(drug.getDoseRoute()));
            screenDrug.setDoseType(DoseTypeUtility.doseTypeNameToId(drug.getDoseType()));

            if (drug.isGeneralDoseCheck()) {
                screenDrug.setDoseInfo(SINGLE_DOSE_AMMOUNT, DOSE_UNIT, DAY, FREQUENCY, DURATION, DAY);
            } else {
                screenDrug.setDoseInfo(drug.getSingleDoseAmount(), drug.getDoseUnit(), drug.getDoseRate(), drug
                    .getFrequency(), drug.getDuration(), drug.getDurationRate());
            }
        }

        return screenDrug;
    }

    /**
     * Spring overridden method through lookup method injection. Spring will provide a new instance of ScreenDrug each time
     * this is called, because the FdbScreenDrug Spring bean is defined with a scope of "prototype".
     * 
     * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
     * 
     * @return new instance of ScreenDrugs
     */
    protected abstract ScreenDrugs newScreenDrugsInstance();

    /**
     * Spring overridden method through lookup method injection. Spring will provide a new instance of ScreenDrug each time
     * this is called, because the FdbScreenDrug Spring bean is defined with a scope of "prototype" for the
     * PerformDrugchecksCapabilityImpl.
     * 
     * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
     * 
     * @return new instance of ScreenDrug
     */
    protected abstract ScreenDrug newScreenDrugInstance();

    /**
     * setPerformDrugDoseCheckCapability
     * @param performDrugDoseCheckCapability performDrugDoseCheckCapability property
     */
    public void setPerformDrugDoseCheckCapability(PerformDrugDoseCheckCapability performDrugDoseCheckCapability) {
        this.performDrugDoseCheckCapability = performDrugDoseCheckCapability;
    }

    /**
     * setPerformDrugDrugCheckCapability
     * @param performDrugDrugCheckCapability performDrugDrugCheckCapability property
     */
    public void setPerformDrugDrugCheckCapability(PerformDrugDrugCheckCapability performDrugDrugCheckCapability) {
        this.performDrugDrugCheckCapability = performDrugDrugCheckCapability;
    }

    /**
     * performDrugTherapyCheckCapability
     * @param performDrugTherapyCheckCapability performDrugTherapyCheckCapability property
     */
    public void setPerformDrugTherapyCheckCapability(PerformDrugTherapyCheckCapability performDrugTherapyCheckCapability) {
        this.performDrugTherapyCheckCapability = performDrugTherapyCheckCapability;
    }
}
