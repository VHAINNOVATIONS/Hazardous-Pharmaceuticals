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

import gov.va.med.pharmacy.peps.service.common.scheduler.tasks.FdbUpdateTaskImpl;


/**
 * 
 * FdbUpdateJob
 */
public class FdbUpdateJob extends QuartzJobBean implements StatefulJob {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FdbUpdateJob.class);
    
    private FdbUpdateTaskImpl fdbUpdateTask;
    
    /**
     * Contructor for QueryFDBJob
     *
     */
    public FdbUpdateJob() {
    }
    
   
    /**
     * Main execution method 
     * @param context - JobExecutionContext
     * @throws JobExecutionException JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        
        LOG.debug("\n");
        LOG.debug("-------------------------- Executing FdbUpdate Job ------------------------------------");
        LOG.debug(" Time:  " +  new Date() + "                                                         ");
        LOG.debug(" Next fire time: " + context.getNextFireTime() + "                                  ");
        
        fdbUpdateTask.runFdbUpdateProcess();
    }




    /**
     * getFdbUpdateTask
     * @return the fdbUpdateTask
     */
    public FdbUpdateTaskImpl getFdbUpdateTask() {
        return fdbUpdateTask;
    }




    /**
     * setFdbUpdateTask
     * @param fdbUpdateTask the fdbUpdateTask to set
     */
    public void setFdbUpdateTask(FdbUpdateTaskImpl fdbUpdateTask) {
        this.fdbUpdateTask = fdbUpdateTask;
    }

    
    
}
