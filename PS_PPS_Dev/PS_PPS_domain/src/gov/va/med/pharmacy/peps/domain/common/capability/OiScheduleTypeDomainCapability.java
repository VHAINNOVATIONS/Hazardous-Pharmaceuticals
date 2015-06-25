/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.OiScheduleTypeVo;


/**
 * Perform CRUD operations with oi ScheduleTypes
 */
public interface OiScheduleTypeDomainCapability {

    /**
     * Retrieve all OiScheduleTypeVo.
     * 
     * @return List<OiScheduleTypeVo>
     */
    List<OiScheduleTypeVo> retrieveDomain();

    /**
     * Retrieve an {@link OiScheduleTypeVo} by ID.
     * 
     * @param id String ID of {@link OiScheduleTypeVo} to retrieve
     * @return {@link OiScheduleTypeVo}
     */
    OiScheduleTypeVo retrieve(String id);
}
