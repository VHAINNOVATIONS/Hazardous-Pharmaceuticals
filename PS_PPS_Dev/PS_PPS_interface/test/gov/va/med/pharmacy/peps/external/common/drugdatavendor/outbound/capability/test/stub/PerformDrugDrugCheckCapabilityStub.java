/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test.stub;


import java.util.Map;

import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.PerformDrugDrugCheckCapability;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugCheckResultsVo;

import firstdatabank.dif.ScreenDrugs;


/**
 * Stub to perform drug-drug interaction check - Return an empty results object
 */
public class PerformDrugDrugCheckCapabilityStub implements PerformDrugDrugCheckCapability {

    /**
     * Empty constructor
     */
    public PerformDrugDrugCheckCapabilityStub() {
        super();
    }

    /**
     * Stub to perform drug-drug interaction check - Return an empty results object
     * 
     * @param screenDrugs FDB ScreenDrugs used as input to drug-drug check call
     * @param idMap Map of GCN Sequence Number to DrugCheckVo
     * @param prospectiveOnly boolean true if check should only be done on prospective drugs
     * @param customTables boolean true if customized tables to be used in the check
     * @return DrugCheckResultsVo contains response from FDB with Collection of DrugDrugCheckResultVo
     */
    public DrugCheckResultsVo performDrugDrugCheck(ScreenDrugs screenDrugs, Map idMap, boolean prospectiveOnly,
                                                   boolean customTables) {
        return new DrugCheckResultsVo();
    }
}
