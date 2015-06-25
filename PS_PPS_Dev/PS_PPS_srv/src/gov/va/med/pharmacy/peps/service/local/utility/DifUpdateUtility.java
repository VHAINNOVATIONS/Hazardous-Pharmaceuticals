/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.utility;


import gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService;


/**
 * Utility class used at local for initializing the DIF update task scheduling
 */
public class DifUpdateUtility {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DifUpdateUtility.class);
    
    private SiteUpdateScheduleService siteUpdateScheduleService;
      
    /**
     * init
     */
    public void init() {
        if (siteUpdateScheduleService == null) {
            LOG.debug("Service is not initialized. Initializaiton of update task scheduling will not be done.");
        } else {
            siteUpdateScheduleService.init(); 
        }
        
    }
    
    /**
     * setSiteUpdateScheduleService
     * @param siteUpdateScheduleService siteUpdateScheduleService property
     */
    public void setSiteUpdateScheduleService(SiteUpdateScheduleService siteUpdateScheduleService) {
        this.siteUpdateScheduleService = siteUpdateScheduleService;
    }

}
