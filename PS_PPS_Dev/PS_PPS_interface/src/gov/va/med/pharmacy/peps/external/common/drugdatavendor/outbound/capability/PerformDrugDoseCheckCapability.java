/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import java.util.Map;

import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugDoseCheckResultVo;

import firstdatabank.dif.ScreenDrugs;


/**
 * Perform a drug dosage check
 */
public interface PerformDrugDoseCheckCapability {

    /**
     * Perform a drug dosage check
     * 
     * @param screenDrugs FDB ScreenDrugs used as input to dose check call
     * @param drugMap Map of the combined String of IEN and order number to DrugCheckVo. The combined String of IEN and order
     *            number is stored in the FDB ScreenDrug's custom ID attribute.
     * @param prospectiveOnly boolean true if check should only be done on prospective drugs
     * @param ageInDays long age of patient in days
     * @param weightInKg double weight of patient in kilograms
     * @param bodySurfaceAreaInSqM double body surface area of patient in square meters
     * @param customTables boolean true if customized tables used in the check
     * @return DrugCheckResultsVo containing results from FDB with Collection of DrugDoseCheckResultVo
     */
    DrugCheckResultsVo<DrugDoseCheckResultVo> performDrugDoseCheck(ScreenDrugs screenDrugs, Map<String, DrugCheckVo> drugMap,
        boolean prospectiveOnly, long ageInDays, double weightInKg, double bodySurfaceAreaInSqM, boolean customTables);
}
