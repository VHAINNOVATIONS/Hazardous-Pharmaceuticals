/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.jobs;


import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import gov.va.med.pharmacy.peps.service.common.scheduler.tasks.InactivationCheckTaskImpl;


/**
 * InactivationCheckJob
 * This Job executes the runInactivationCheckTask
 */
public class InactivationCheckJob extends QuartzJobBean implements StatefulJob {

    private static final Logger LOG = Logger.getLogger(InactivationCheckJob.class);
    
    private InactivationCheckTaskImpl inactivationCheckTask;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        
        LOG.debug("\n");
        LOG.debug("-------------------------- Executing InactivationCheckJob JOB ------------------------------------");
        LOG.debug(" Time:  " +  new Date() + "                                                                ");
        LOG.debug(" Next fire time: " + context.getNextFireTime() + "                                         ");
        
        inactivationCheckTask.runInactivationCheckProcess();
        
    }

    /**
     * gets InactivationCheckTask
     * @return the inactivationCheckTask
     */
    public InactivationCheckTaskImpl getInactivationCheckTask() {
        return inactivationCheckTask;
    }

    /**
     * sets InactivationCheckTask
     * @param inactivationCheckTask the inactivationCheckTask to set
     */
    public void setInactivationCheckTask(
            InactivationCheckTaskImpl inactivationCheckTask) {
        this.inactivationCheckTask = inactivationCheckTask;
    }

  


}
