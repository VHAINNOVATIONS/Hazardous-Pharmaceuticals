/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on SiteUpdateSchedules
 */
public interface SiteUpdateScheduleDomainCapability {

    /**
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber String
     * @return List<SiteUpdateScheduleVo>
     */
    List<SiteUpdateScheduleVo> retrieveSiteUpdateSchedule(String siteNumber);

    /**
     * Retrieve all SiteUpdateScheduleVo.
     * 
     * @return List<SiteUpdateScheduleVo>
     */
    List<SiteUpdateScheduleVo> retrieveSiteUpdateSchedule();

    /**
     * Retrieve all SiteUpdateScheduleVo in progress
     * 
     * @return List<SiteUpdateScheduleVo>
     */
    List<SiteUpdateScheduleVo> retrieveSiteUpdateScheduleInProgress();

    /**
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo inserted Vo with new ID
     */
    SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user);

    /**
     * Update the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo updatedVo
     */
    SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user);

    /**
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber siteNumber
     * @param softwareUpdateType software update tyep
     * @return SiteUpdateScheduleVo
     */
    SiteUpdateScheduleVo retrieveNextScheduleStart(String siteNumber, String softwareUpdateType);

    /**
     * Deletes SiteUpdateScheduleVo.
     */
    void deleteAll();

}
