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

import gov.va.med.pharmacy.peps.service.common.scheduler.tasks.StsTaskImpl;


/**
 * StsJob
 */
public class StsJob extends QuartzJobBean implements StatefulJob  {

    
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(StsJob.class);
    
    private StsTaskImpl stsTask;
    
    /**
     * Contructor for StsJob
     *
     */
    public StsJob() {
    }
    

    
   
    /**
     * Main execution method 
     * @param context - JobExecutionContext
     * @throws JobExecutionException JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        
        LOG.debug("\n");
        LOG.debug("-------------------------- Executing STS  Job ----------------------------------");
        LOG.debug(" Time:  " +  new Date() + "                                                     ");
        LOG.debug(" Next fire time: " + context.getNextFireTime() + "                              ");
        
        stsTask.runStsProcess();
    }




    /**
     * getStsTask
     * @return the stsTask
     */
    public StsTaskImpl getStsTask() {
        return stsTask;
    }




    /**
     * setStsTask
     * @param stsTask stsTask
     */
    public void setStsTask(StsTaskImpl stsTask) {
        this.stsTask = stsTask;
    }


}
