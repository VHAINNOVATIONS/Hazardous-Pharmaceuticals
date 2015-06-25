/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleTypeVo;


/**
 * Perform CRUD operations with Administration Schedule Types
 */
public interface AdministrationScheduleTypeDomainCapability {

    /**
     * Retrieve all AdministrationScheduleTypeVo.
     * 
     * @return List<AdministrationScheduleTypeVo>
     */
    List<AdministrationScheduleTypeVo> retrieveDomain();

}
