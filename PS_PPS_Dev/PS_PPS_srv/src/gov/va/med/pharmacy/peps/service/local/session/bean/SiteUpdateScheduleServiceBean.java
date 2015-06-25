/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService;


/**
 * Perform security actions on a user
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class SiteUpdateScheduleServiceBean extends AbstractPepsStatelessSessionBean<SiteUpdateScheduleService>
    implements SiteUpdateScheduleService {

    private static final long serialVersionUID = 1L;
    
    /**
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo new scheduled update configuration to create
     * @param user {@link UserVo} performing the action
     * @return new site update schedule
     * 
     * @see gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService#create(
     *      gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo)
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    public SiteUpdateScheduleVo create(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return getService().create(siteUpdateScheduleVo, user);
    }

    /**
     * Returns the current COTS version information
     * 
     * @return configuration containing current FDB version information
     * 
     * @see gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService#retrieveFDBVersion()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public SiteConfigVo retrieveFDBVersion() {
        
        // call the service version of the method.
        return getService().retrieveFDBVersion();
    }

    /**
     * Returns the current scheduled update configuration for the given site
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService#
     *      retrieveSiteUpdateSchedule(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber) {
        return getService().retrieveSiteUpdateSchedule(siteNumber);
    }

    /**
     * Updates an existing scheduled update configuration as provided by the user interface.
     * 
     * @param siteUpdateScheduleVo updated version of the scheduled update configuration
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService#update(
     *      gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public SiteUpdateScheduleVo update(SiteUpdateScheduleVo siteUpdateScheduleVo, UserVo user) {
        return getService().update(siteUpdateScheduleVo, user);
    }
    
    /**
     * Initialization method called from Spring at startup of the application
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public void init() {
        getService().init();
    }
    
    /** 
     * Begins the process of the DIF update
     * 
     * @param manual indicates if this is a manual udate or not
     * @return boolean
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public boolean performUpdate(boolean manual) {
        return getService().performUpdate(manual);
    }
    
    /**
     * Retrieve all SiteUpdateScheduleVo for a given site number.
     * 
     * @param siteNumber siteNumber
     * @param softwareUpdateType software update type
     * @return SiteUpdateScheduleVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public SiteUpdateScheduleVo retrieveNextScheduleStart(String siteNumber, String softwareUpdateType) {
        return getService().retrieveNextScheduleStart(siteNumber, softwareUpdateType);
    }

}
