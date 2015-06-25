/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;


/**
 * Perform CRUD operations on OTC/Rx
 */
public interface OtcRxDomainCapability {
    
    /**
     * Retrieve a List of possible OTC/RX to select from
     * 
     * @return List of possible values
     */
    List<OtcRxVo> retrieveDomain();

}
