/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.ReportVo;
import gov.va.med.pharmacy.peps.service.common.capability.ReportsCapability;


/**
 * perform reports operations
 */
public class ReportsCapabilityImpl implements ReportsCapability {

    @Override
    public ReportVo retrieve() throws ItemNotFoundException {
        return null;
    }

//    /**
//     * The domain capability that manages reports
//     */
    //private ReportsDomainCapability reportsDomainCapability;

//   /**
//     * 
//     * @return reportsDomainCapability property
//     */
//    public reportsDomainCapability getReportsDomainCapability() {
//        return reportsDomainCapability;
//    }

//    /**
//     * 
//     * @param reportsDomainCapability property
//     */
//    public void setReportsDomainCapability(ReportsDomainCapability reportsDomainCapability) {
//        this.reportsDomainCapability = reportsDomainCapability;
//    }
}
