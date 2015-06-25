/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;


/**
 * Perform CRUD operations with cs federal schedule
 */
public interface CsFedScheduleDomainCapability {

    /**
     * Retrieve all possible cs fed schedules
     * 
     * @return List of cs fed schedules
     */
    List<CsFedScheduleVo> retrieveDomain();

    /**
     * Retrieve specified CsFedScheduleVo
     * 
     * @param id of unit to retrieve
     * @return CsFedScheduleVo
     * @throws ItemNotFoundException exception
     * 
     */
    CsFedScheduleVo retrieve(String id) throws ItemNotFoundException;

}
