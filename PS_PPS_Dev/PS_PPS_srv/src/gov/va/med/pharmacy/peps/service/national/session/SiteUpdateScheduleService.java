/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.session;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform COTS update actions
 */
public interface SiteUpdateScheduleService {

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
     * @return SiteUpdateScheduleVo updated Vo
     */
    SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user);

    /**
     * Returns the current scheduled time and interval at which time updates are to occur
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     */
    SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber);

    /**
     * Returns the current version of the FDB DB as part of the entire sites configuration information
     * 
     * @return SiteConfigVo
     */
    SiteConfigVo retrieveFDBVersion();

    /**
     * Begins the process of the DIF update
     * 
     * @return boolean
     */
    boolean performUpdate();

    /**
     * Begins the process of the DIF update
     * 
     * @return boolean
     */
    boolean canPerformUpdate();

    /**
     * Returns if the national server is currently running
     * 
     * @return boolean
     */
    boolean isNationalRunning();

    /**
     * Message that a local has begun running a DIF update process
     * @param siteUpdateScheduleVo of type SiteUpdateScheduleVo
     */
    void localRunning(SiteUpdateScheduleVo siteUpdateScheduleVo);

    /**
     * Message that a local has finished running a DIF update process
     * @param siteUpdateScheduleVo of type SiteUpdateScheduleVo
     */
    void localFinished(SiteUpdateScheduleVo siteUpdateScheduleVo);

    /**
     * Save the given byte array as the update file for the updater to use later.
     * 
     * @param bytes byte array containing update file data
     */
    void saveUpdateFile(byte[] bytes);
}
