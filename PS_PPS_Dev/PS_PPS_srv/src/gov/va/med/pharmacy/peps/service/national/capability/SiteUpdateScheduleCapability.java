/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability;


import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 *Site Update Schedule Capability contains code common for processing DIF updates at National.
 */
public interface SiteUpdateScheduleCapability extends
    gov.va.med.pharmacy.peps.service.common.capability.SiteUpdateScheduleCapability {

    /**
     * This method processes DIF schedule update progress passed from local to national.
     * 
     * @param vo SiteUpdateScheduleVo
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo
     */
    SiteUpdateScheduleVo processFromLocal(SiteUpdateScheduleVo vo, UserVo user);

    /**
     * Begins the process of the DIF update
     * 
     * @return boolean
     */
    boolean performUpdate();

    /**
     * Checks if DIF update can be performed
     * 
     * @return boolean
     */
    boolean canPerformUpdate();

    /**
     * Indicates if the national server is running or not
     * 
     * @return boolean
     */
    boolean isNationalRunning();

    /**
     * Returns true if national or one or more locals is running
     * 
     * @return boolean
     */
    boolean isAnyRunning();

    /**
     * Message that a local has begun running a DIF update process
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     */
    void localRunning(SiteUpdateScheduleVo siteUpdateScheduleVo);

    /**
     * Message that a local has finished running a DIF update process
     * @param siteUpdateScheduleVo SiteUpdateScheduleVo
     */
    void localFinished(SiteUpdateScheduleVo siteUpdateScheduleVo);

    /**
     * Save the given byte array as the update file for the updater to use later.
     * 
     * @param bytes byte array containing update file data
     */
    void saveUpdateFile(byte[] bytes);
}
