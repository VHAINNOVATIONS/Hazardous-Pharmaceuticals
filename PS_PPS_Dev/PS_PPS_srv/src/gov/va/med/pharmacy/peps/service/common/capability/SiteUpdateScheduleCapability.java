/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 *SiteUpdateScheduleCapability
 */
public interface SiteUpdateScheduleCapability {
    
    /**
     * SiteUpdateScheduleCapability
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo inserted Vo with new ID
     */
    SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user);

    /**
     * SiteUpdateScheduleCapability
     * Update the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo updated Vo
     */
    SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user);
            
    /**
     * SiteUpdateScheduleCapability
     * Returns the current scheduled time and interval at which time updates are to occur
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     */
    SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber);

    /**
     * SiteUpdateScheduleCapability
     * Returns the current version of the FDB DB as part of the entire sites configuration information
     * 
     * @return SiteConfigVo
     */
    SiteConfigVo retrieveFDBVersion();
    
    /**
     * SiteUpdateScheduleCapability
     * retrieveNextScheduleStart
     * 
     * @param siteNumber siteNumber
     * @param softwareUpdateType software update type
     * @return SiteUpdateScheduleVo
     */
    SiteUpdateScheduleVo retrieveNextScheduleStart(String siteNumber, String softwareUpdateType);


}
