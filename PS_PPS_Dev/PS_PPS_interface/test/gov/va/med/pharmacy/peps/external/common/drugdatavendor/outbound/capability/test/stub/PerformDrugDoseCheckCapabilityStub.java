/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test.stub;


import java.util.Map;

import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugDoseCheckCapability;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckResultsVo;

import firstdatabank.dif.ScreenDrugs;


/**
 * Stub to perform a drug dosage check - return an empty results object
 */
public class PerformDrugDoseCheckCapabilityStub implements PerformDrugDoseCheckCapability {

    /**
     * Empty constructor
     */
    public PerformDrugDoseCheckCapabilityStub() {
        super();
    }

    /**
     * Stub to perform a drug dosage check - return an empty results object
     * 
     * @param screenDrugs FDB ScreenDrugs used as input to dose check call
     * @param idMap Map of GCN Sequence Number to DrugCheckVo
     * @param prospectiveOnly boolean true if check should only be done on prospective drugs
     * @param ageInDays long age of patient in days
     * @param weightInKg double weight of patient in kilograms
     * @param bodySurfaceAreaInSqM double body surface area of patient in square meters
     * @param customTables boolean true if customized tables to be used in the check
     * @return DrugCheckResultsVo containing results from FDB with Collection of DrugDoseCheckResultVo
     */
    public DrugCheckResultsVo performDrugDoseCheck(ScreenDrugs screenDrugs, Map idMap, boolean prospectiveOnly,
                                                   long ageInDays, double weightInKg, double bodySurfaceAreaInSqM, 
                                                   boolean customTables) {
        return new DrugCheckResultsVo();
    }
}
