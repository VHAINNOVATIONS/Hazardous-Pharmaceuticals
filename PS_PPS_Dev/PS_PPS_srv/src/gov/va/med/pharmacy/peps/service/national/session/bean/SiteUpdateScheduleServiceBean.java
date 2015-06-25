/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.national.session.SiteUpdateScheduleService;


/**
 * Site Update Schedule Capability contains code common for processing DIF updates at National
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class SiteUpdateScheduleServiceBean extends AbstractPepsStatelessSessionBean<SiteUpdateScheduleService> implements
    SiteUpdateScheduleService {

    private static final long serialVersionUID = 1L;

    /**
     * Insert the given SiteUpdateScheduleVo.
     * 
     * @param siteUpdateScheduleVo new scheduled update configuration to create
     * @param user {@link UserVo} performing the action
     * @return new site update schedule
     * 
     * @see gov.va.med.pharmacy.peps.service.national.session.SiteUpdateScheduleService#create(
     *      gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo)
     * 
     * @ejb.interface-method
     * 
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
     * @see gov.va.med.pharmacy.peps.service.national.session.SiteUpdateScheduleService#retrieveFDBVersion()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public SiteConfigVo retrieveFDBVersion() {
        return getService().retrieveFDBVersion();
    }

    /**
     * Returns the current scheduled update configuration for the given site
     * 
     * @param siteNumber identifier of the site
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.national.session.
     *      SiteUpdateScheduleService#retrieveSiteUpdateSchedule(java.lang.String)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public SiteUpdateScheduleVo retrieveSiteUpdateSchedule(String siteNumber) {
        return getService().retrieveSiteUpdateSchedule(siteNumber);
    }

    /**
     * Updates an existing scheduled update configuration
     * 
     * @param siteUpdateScheduleVo updated version of the scheduled update configuration
     * @param user {@link UserVo} performing the action
     * @return SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.national.session.SiteUpdateScheduleService#update(
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
     * Begins the process of the DIF update
     * 
     * @return boolean
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public boolean performUpdate() {
        return getService().performUpdate();
    }

    /**
     * checks if DIF update can be performed
     * 
     * @return boolean
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public boolean canPerformUpdate() {
        return getService().canPerformUpdate();
    }

    /**
     * Returns if the national server is currently running
     * 
     * @return boolean
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public boolean isNationalRunning() {
        return getService().isNationalRunning();
    }

    /**
     * Message that a local has begun running a DIF update process
     * 
     * @param siteUpdateScheduleVo of type SiteUpdateScheduleVo
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public void localRunning(SiteUpdateScheduleVo siteUpdateScheduleVo) {
        getService().localRunning(siteUpdateScheduleVo);
    }

    /**
     * Message that a local has finished running a DIF update process
     * @param siteUpdateScheduleVo of type SiteUpdateScheduleVo
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public void localFinished(SiteUpdateScheduleVo siteUpdateScheduleVo) {
        getService().localFinished(siteUpdateScheduleVo);
    }

    /**
     * Save the given byte array as the update file for the updater to use later.
     * 
     * @param bytes byte array containing update file data
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public void saveUpdateFile(byte[] bytes) {
        getService().saveUpdateFile(bytes);
    }
}
