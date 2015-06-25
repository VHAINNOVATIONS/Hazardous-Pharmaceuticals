/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.OrderCheckVo;


/**
 * Call the drug data vendor capabilities to perform the checks.
 */
public interface PerformDrugChecksCapability {

    /**
     * Convert drugs into FDB ScreenDrugs and call the individual
     * drug check capabilities to perform the checks.
     * 
     * @param requestVo OrderCheckVo requesting checks
     * @return OrderCheckResultsVo with results from drug data vendor
     */
    OrderCheckResultsVo performDrugChecks(OrderCheckVo requestVo);
}
