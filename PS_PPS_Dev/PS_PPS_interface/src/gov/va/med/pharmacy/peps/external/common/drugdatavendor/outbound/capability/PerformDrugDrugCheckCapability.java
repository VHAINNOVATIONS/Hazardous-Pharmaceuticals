/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import java.util.Map;

import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugDrugCheckResultVo;

import firstdatabank.dif.ScreenDrugs;


/**
 * Perform drug-drug interaction check
 */
public interface PerformDrugDrugCheckCapability {

    /**
     * Perform drug-drug interaction check
     * 
     * @param screenDrugs FDB ScreenDrugs used as input to drug-drug check call
     * @param drugMap Map of the combined String of IEN and order number to DrugCheckVo. The combined String of IEN and order
     *            number is stored in the FDB ScreenDrug's custom ID attribute.
     * @param prospectiveOnly boolean true if check should only be done on prospective drugs
     * @param customTables boolean true if customized tables to be used in the check
     * @return DrugCheckResultsVo contains response from FDB with Collection of DrugDrugCheckResultVo
     */
    DrugCheckResultsVo<DrugDrugCheckResultVo> performDrugDrugCheck(ScreenDrugs screenDrugs, Map<String, DrugCheckVo> drugMap,
        boolean prospectiveOnly, boolean customTables);
}
