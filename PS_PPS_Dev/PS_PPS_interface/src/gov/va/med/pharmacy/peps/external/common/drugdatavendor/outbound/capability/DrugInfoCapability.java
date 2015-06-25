/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugInfoResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugInfoVo;


/**
 * Lookup the dose routes and types for the given drugs.
 */
public interface DrugInfoCapability {

    /**
     * Lookup the dose routes and types for the given drugs.
     * 
     * @param drugs Collection of DrugInfoVo
     * @return DrugInfoResultsVo containing drugs not found and drugs with does routes and types
     */
    DrugInfoResultsVo processDrugInfoRequest(Collection<DrugInfoVo> drugs);
}
