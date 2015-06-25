/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.ReportVo;


/**
 * Search and retrieve requests.
 * <p>
 * Local/National implement this differently. There is not a common implementation, only a common interface!
 */
public interface ReportsCapability {

    /**
     * Retrieve request detail by ID.
     * 
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if cannot find Request with given ID
     */
    ReportVo retrieve() throws ItemNotFoundException;
}
