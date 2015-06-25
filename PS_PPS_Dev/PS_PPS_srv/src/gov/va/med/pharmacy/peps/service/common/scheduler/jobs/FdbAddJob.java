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

import gov.va.med.pharmacy.peps.service.common.scheduler.tasks.FdbAddTaskImpl;


/**
 * 
 * QueryFDBJob
 *
 */
public class FdbAddJob extends QuartzJobBean implements StatefulJob {
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FdbAddJob.class);
    
    private FdbAddTaskImpl fdbAddTask;
    
    /**
     * Contructor for QueryFDBJob
     *
     */
    public FdbAddJob() {
    }
   
    /**
     * Main execution method for FdbAddJob
     * @param context - JobExecutionContext
     * @throws JobExecutionException JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        
        LOG.debug("\n");
        LOG.debug("-------------------------- Executing FDBAdd Job ------------------------------------");
        LOG.debug(" Time:  " +  new Date() + "                                                         ");
        LOG.debug(" Next fire time: " + context.getNextFireTime() + "                                  ");
        fdbAddTask.runFdbAddProcess();
    }




    /**
     * gets FdbAddTask
     * @return the fdbAddTask
     */
    public FdbAddTaskImpl getFdbAddTask() {
        return fdbAddTask;
    }




    /**
     * sets FdbAddTask
     * @param fdbAddTask the fdbAddTask to set
     */
    public void setFdbAddTask(FdbAddTaskImpl fdbAddTask) {
        this.fdbAddTask = fdbAddTask;
    }





    

}
