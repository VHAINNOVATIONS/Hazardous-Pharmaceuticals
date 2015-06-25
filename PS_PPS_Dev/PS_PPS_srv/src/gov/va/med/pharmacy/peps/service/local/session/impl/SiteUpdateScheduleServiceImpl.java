/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.session.impl;


import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.local.capability.SiteUpdateScheduleCapability;
import gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService;


/**
 * Perform FDB update actions
 */
public class SiteUpdateScheduleServiceImpl implements SiteUpdateScheduleService {

    private SiteUpdateScheduleCapability siteUpdateScheduleCapability;
    
    /**
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo new scheduled update configuration to create
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.SiteUpdateScheduleService#create(
     *      gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo)
     */
    public SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return siteUpdateScheduleCapability.create(siteUpdateScheduleVo, user);
    }

    /**
     * Returns a SiteConfigVo that contains the COTS version details
     * 
     * @return SiteConfigVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.SiteUpdateScheduleService#retrieveFDBVersion()
     */
    public SiteConfigVo retrieveFDBVersion() {
        return siteUpdateScheduleCapability.retrieveFDBVersion();
    }

    /**
     * Returns the current scheduled update configuration for the site given
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.SiteUpdateScheduleService#
     *      retrieveSiteUpdateSchedule(java.lang.String)
     */
    public SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber) {
        return siteUpdateScheduleCapability.retrieveSiteUpdateSchedule(siteNumber);
    }

    /**
     * Updates an existing scheduled update configuration
     * 
     * @param siteUpdateScheduleVo updated scheduled update configuration
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.SiteUpdateScheduleService#update(
     *      gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo)
     */
    public SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return siteUpdateScheduleCapability.update(siteUpdateScheduleVo, user);
    }
    
    /**
     * setSiteUpdateScheduleCapability
     * @param siteUpdateScheduleCapability siteUpdateScheduleCapability property
     */
    public void setSiteUpdateScheduleCapability(SiteUpdateScheduleCapability siteUpdateScheduleCapability) {
        this.siteUpdateScheduleCapability = siteUpdateScheduleCapability;
    }
    
    /**
     * Initialization method called from Spring at startup of the application
     */
    public void init() {
        siteUpdateScheduleCapability.init();
    }
    
    /**
     * Begins the process of the DIF update
     * 
     * @param manual indicates if this is a manual udate or not
     * @return boolean
     */
    public boolean performUpdate(boolean manual) {
        return siteUpdateScheduleCapability.performUpdate(manual);
    }
    
    /**
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber siteNumber
     * @param softwareUpdateType software update type
     * @return SiteUpdateScheduleVo
     */
    public SiteUpdateScheduleVo retrieveNextScheduleStart(String siteNumber, String softwareUpdateType) {
        return siteUpdateScheduleCapability.retrieveNextScheduleStart(siteNumber, softwareUpdateType);
    }


}
