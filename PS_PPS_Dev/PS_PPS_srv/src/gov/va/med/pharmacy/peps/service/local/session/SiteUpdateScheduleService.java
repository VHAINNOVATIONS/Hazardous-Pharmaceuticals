/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.session;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform COTS update actions
 */
public interface SiteUpdateScheduleService {
    
    /**
     * SiteUpdateScheduleService
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo inserted Vo with new ID
     */
    SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user);

    /**
     * SiteUpdateScheduleService
     * Update the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo updated Vo
     */
    SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user);
            
    /**
     * SiteUpdateScheduleService
     * Returns the current scheduled time and interval at which time updates are to occur
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     */
    SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber);

    /**
     * SiteUpdateScheduleService
     * Returns the current version of the FDB DB as part of the entire sites configuration information
     * 
     * @return SiteConfigVo
     */
    SiteConfigVo retrieveFDBVersion();
    
    /**
     * SiteUpdateScheduleService
     * Initialization method called from Spring at startup of the application
     */
    void init();
    
    /**
     * SiteUpdateScheduleService
     * Begins the process of the DIF update
     * 
     * @param manual indicates if this is a manual udate or not
     * @return boolean
     */
    boolean performUpdate(boolean manual);


    /**
     * SiteUpdateScheduleService
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber siteNumber
     * @param softwareUpdateType software update type
     * @return SiteUpdateScheduleVo
     */
    SiteUpdateScheduleVo retrieveNextScheduleStart(String siteNumber, String softwareUpdateType);

        
}
