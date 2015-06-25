/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.utility;


import java.util.Date;
import java.util.Timer;

import gov.va.med.pharmacy.peps.service.local.session.SiteUpdateScheduleService;


/**
 * Created a scheduled task for the local DIF update process
 */
public class DifUpdateScheduler {

    private Timer mainTimer;
    private DifUpdateTimerTask runDifUpdate;
    private SiteUpdateScheduleService siteUpdateScheduleService;

    /**
     * Default constructor
     */
    public DifUpdateScheduler() {
        mainTimer = new Timer();     
    }  
    
    /**
     * Called to cancel the currently scheduled task
     */
    public void cancelTimerTask() {
        if (runDifUpdate != null) {            
            runDifUpdate.cancel();
            runDifUpdate = null;
        }  
        
        if (mainTimer != null) {
            mainTimer.cancel();
            mainTimer = null;
        }
    }
    
    /**
     * Called to schedule a new task to be executed at the given time. No more than
     * one update DIF task is scheduled to run.
     * 
     * @param scheduleTime time to execute the update DIF task
     */
    public void scheduleNewTimerTask(Date scheduleTime) {
        cancelTimerTask();
        runDifUpdate = new DifUpdateTimerTask(siteUpdateScheduleService);
        mainTimer = new Timer();
        mainTimer.schedule(runDifUpdate, scheduleTime);
    }
   
    /**
     * setSiteUpdateScheduleService
     * @param siteUpdateScheduleService siteUpdateScheduleService property
     */
    public void setSiteUpdateScheduleService(SiteUpdateScheduleService siteUpdateScheduleService) {
        this.siteUpdateScheduleService = siteUpdateScheduleService;
    }
    
}
