/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.utility;


import java.util.TimerTask;

import gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService;


/**
 * TimerTask used to run the local DIF update
 */
public class DifUpdateTimerTask extends TimerTask {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DifUpdateTimerTask.class);

    private SiteUpdateScheduleService siteUpdateScheduleService;
    
    /**
     * Creates a DifUpdateTimerTask passing in reference to the SiteUpdateScheduleService
     * 
     * @param siteUpdateScheduleService service used to execute update
     */
    public DifUpdateTimerTask(SiteUpdateScheduleService siteUpdateScheduleService) {
        this.siteUpdateScheduleService = siteUpdateScheduleService;
    }
    
    /**
     * Executed when TimerTask is scheduled to run
     * 
     * 
     * @see java.util.TimerTask#run()
     */    
    public void run() {
        
        if (siteUpdateScheduleService == null) {
            LOG.debug("Service is not initialized. Update will not be executed.");
        } else {
            siteUpdateScheduleService.performUpdate(false);
        }
        
    }    
}
