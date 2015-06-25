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

import gov.va.med.pharmacy.peps.service.common.scheduler.tasks.FssTaskImpl;


/**
 * FssJob
 */
public class FssJob extends QuartzJobBean implements StatefulJob {

    
    
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FssJob.class);
    
    private FssTaskImpl fssTask;
    
    /**
     * Contructor for FssJob
     *
     */
    public FssJob() {
    }
    
   
    /**
     * Main execution method for the FSSJob
     * @param context - JobExecutionContext
     * @throws JobExecutionException JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        
        LOG.debug("\n");
        LOG.debug("-------------------------- Executing FSS  Job ----------------------------------");
        LOG.debug(" Time:  " +  new Date() + "                                         ");
        LOG.debug(" Next fire time: " + context.getNextFireTime() + "                                ");
        
        fssTask.runFssProcess();
    }


    /**
     * getFssTask
     * @return the FssTask
     */
    public FssTaskImpl getFssTask() {
        return fssTask;
    }


    /**
     * setFssTask
     * @param fssTask the FssTask to set
     */
    public void setFssTask(FssTaskImpl fssTask) {
        this.fssTask = fssTask;
    }

}
