/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.session.impl;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.national.capability.SiteUpdateScheduleCapability;
import gov.va.med.pharmacy.peps.service.national.session.SiteUpdateScheduleService;


/**
 * Perform FDB update actions
 */
public class SiteUpdateScheduleServiceImpl implements SiteUpdateScheduleService {

    private SiteUpdateScheduleCapability siteUpdateScheduleCapability;

    /**
     * create the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo new scheduled update configuration to create
     * @param user user performing the action
     * @return SiteUpdateScheduleVo
     * 
     */
    public SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return siteUpdateScheduleCapability.create(siteUpdateScheduleVo, user);
    }

    /**
     * Returns a SiteConfigVo that contains the COTS version details for SiteUpdateScheduleServiceImpl
     * 
     * @return SiteConfigVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.SiteUpdateScheduleService#retrieveFDBVersion()
     */
    public SiteConfigVo retrieveFDBVersion() {
        return siteUpdateScheduleCapability.retrieveFDBVersion();
    }

    /**
     * Returns the current scheduled update configuration for the site given for SiteUpdateScheduleServiceImpl
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     * 
     */
    public SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber) {
        return siteUpdateScheduleCapability.retrieveSiteUpdateSchedule(siteNumber);
    }

    /**
     * Updates an existing scheduled update configuration  for SiteUpdateScheduleServiceImpl
     * 
     * @param siteUpdateScheduleVo updated scheduled update configuration
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.SiteUpdateScheduleService#update
     *      (gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo)
     */
    public SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return siteUpdateScheduleCapability.update(siteUpdateScheduleVo, user);
    }

    /**
     * setSiteUpdateScheduleCapability  for SiteUpdateScheduleServiceImpl
     * @param siteUpdateScheduleCapability siteUpdateScheduleCapability property
     */
    public void setSiteUpdateScheduleCapability(SiteUpdateScheduleCapability siteUpdateScheduleCapability) {
        this.siteUpdateScheduleCapability = siteUpdateScheduleCapability;
    }

    /**
     * Begins the process of the DIF update  for SiteUpdateScheduleServiceImpl
     * 
     * @return boolean
     */
    public boolean performUpdate() {
        return siteUpdateScheduleCapability.performUpdate();
    }

    /**
     * Begins the process of the DIF update  for SiteUpdateScheduleServiceImpl
     * 
     * @return boolean
     */
    public boolean canPerformUpdate() {
        return siteUpdateScheduleCapability.canPerformUpdate();
    }

    /**
     * Returns if the national server is currently running  for SiteUpdateScheduleServiceImpl
     * 
     * @return boolean
     */
    public boolean isNationalRunning() {
        return siteUpdateScheduleCapability.isNationalRunning();
    }

    /**
     * Message that a local has begun running a DIF update process  for SiteUpdateScheduleServiceImpl
     * @param siteUpdateScheduleVo of type SiteUpdateScheduleVo
     */
    public void localRunning(SiteUpdateScheduleVo siteUpdateScheduleVo) {
        siteUpdateScheduleCapability.localRunning(siteUpdateScheduleVo);
    }

    /**
     * Message that a local has finished running a DIF update process
     * @param siteUpdateScheduleVo of type SiteUpdateScheduleVo
     */
    public void localFinished(SiteUpdateScheduleVo siteUpdateScheduleVo) {
        siteUpdateScheduleCapability.localFinished(siteUpdateScheduleVo);
    }

    /**
     * Save the given byte array as the update file for the updater to use later.
     * 
     * @param bytes byte array containing update file data
     */
    public void saveUpdateFile(byte[] bytes) {
        siteUpdateScheduleCapability.saveUpdateFile(bytes);
    }
}
