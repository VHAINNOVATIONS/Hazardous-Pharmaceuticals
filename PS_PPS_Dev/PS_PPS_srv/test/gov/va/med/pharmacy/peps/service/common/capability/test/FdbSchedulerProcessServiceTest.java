/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.capability.test;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbJobNames;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbSchedulerControlBean;
import gov.va.med.pharmacy.peps.service.common.scheduler.JobCommands;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerState;
import gov.va.med.pharmacy.peps.service.common.session.FdbSchedulerProcessService;


/**
 * 
 * FdbSchedulerProcessServiceTest
 */
public class FdbSchedulerProcessServiceTest extends DomainCapabilityTestCase  {
    
    
   
    private static final Logger LOG = Logger.getLogger(FdbSchedulerProcessServiceTest.class);
    private FdbSchedulerProcessService fdbSchedulerProcessService;
    
    /**
     * setUp
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() {
        fdbSchedulerProcessService = getNationalCapability(FdbSchedulerProcessService.class);
        assertNotNull("fdbSchedulerProcessService is null", fdbSchedulerProcessService);
    }
    
    
    /**
     * test start Jobs
     * @throws InterruptedException 
     */
    @Test
    public void testStartPauseAllResumeAllJobs() throws InterruptedException {
        
       
        FdbSchedulerControlBean schedulerControl = new  FdbSchedulerControlBean(); 
        
        // by default when starting a job, Scheduler needs to be running.
        
        LOG.debug("----------------- STARTING ALL JOBS -------------------------------------");
        schedulerControl.setFdbJobNames(FdbJobNames.FDB_ADD_JOB);   // job type
        schedulerControl.setJobName(FdbJobNames.FDB_ADD_JOB.getJobName());   // job NAME
        schedulerControl.setFdbJobNames(FdbJobNames.FDB_ADD_JOB); 
        schedulerControl.setJobCommands(JobCommands.RESUME); // this assumes that scheduler will schedule all jobs at startup.
        
        SchedulerState state = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        
        assertNotNull(" state is null", state);
        LOG.debug("---------------- SLEEP FOR 10 SECS -------------------------------------");
        Thread.sleep(PPSConstants.I10000);
        
        LOG.debug("--------------- PAUSING ALL JOBS--------------------------------------");
        schedulerControl.setJobCommands(JobCommands.PAUSE_ALL); // pause all jobs.
        
        SchedulerState state2 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state is  null", state2);
        
        LOG.debug("--------------- SLEEP FOR 5 SECS -------------------------------------");
        Thread.sleep(PPSConstants.I5000);
        
        LOG.debug("--------------- RESUMING ALL JOBS-------------------------------------");
        schedulerControl.setJobCommands(JobCommands.RESUME_ALL); // resume all jobs.
        SchedulerState state3 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state is null ", state3);
        
        Thread.sleep(PPSConstants.I10000);
        LOG.debug("---------------  DONE -------------------------------------");

            
    
        
    }
    
    /**
     * 
     * testStartAllJobsByDefault
     * @throws InterruptedException 
     *
     */
    @Test
    public void testStartAllJobsByDefault() throws InterruptedException {
        
        FdbSchedulerControlBean schedulerControl = new  FdbSchedulerControlBean();
        
        LOG.debug("******************* STARTING ALL JOB ******************************");
        
        schedulerControl.setJobCommands(JobCommands.STATUS); // get status
        
        SchedulerState state = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state is null!", state);
        
        Thread.sleep(PPSConstants.I65000);

        
        
        schedulerControl.setJobCommands(JobCommands.STATUS); // get status
        
        SchedulerState state2 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        
        
        assertNotNull("state is null.", state2);

        
    }
    
    
    /**
     * test Pause Resume FdbAddJob
     *
     * @throws InterruptedException InterruptedException
     */
    @Test
    public void testPauseResumeFdbAddJob() throws InterruptedException {
        
        FdbSchedulerControlBean schedulerControl = new  FdbSchedulerControlBean();
        
        LOG.debug("--------------- STARTING  ALL JOBS -------------------------------------");
        
        schedulerControl.setJobCommands(JobCommands.STATUS); // get status
        
        SchedulerState state = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state is null?", state);
        
        Thread.sleep(new Long("15000"));
        
        LOG.debug("--------------- PAUSING FDB_UPDATE_JOB -------------------------------------");
        schedulerControl.setFdbJobNames(FdbJobNames.FDB_UPDATE_JOB);   
        schedulerControl.setJobCommands(JobCommands.PAUSE);
        
        SchedulerState state2 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state IS null", state2);

        Thread.sleep(PPSConstants.I10000);
        
        LOG.debug("--------------- RESUMING FDB_UPDATE_JOB -------------------------------------");
        schedulerControl.setFdbJobNames(FdbJobNames.FDB_UPDATE_JOB);   
        schedulerControl.setJobCommands(JobCommands.RESUME);
        
        SchedulerState state3 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state is NULL", state3);
        
        Thread.sleep(new Long("15000"));
        

        LOG.debug("--------------- DONE -------------------------------------");
    }
    
    /**
     * Reschedule Job  
     * By default, when starting app, spring automatically starts
     * all jobs but they are only triggered at their preset times.
     *  
     * @throws InterruptedException InterruptedException
     */
    @Test
    public void testScheduleJob() throws InterruptedException {
        
        FdbSchedulerControlBean schedulerControl = new  FdbSchedulerControlBean();
        
        LOG.debug("--------------- STARTING ALL JOBS -------------------------------------");
        schedulerControl.setJobCommands(JobCommands.STATUS);
        
        SchedulerState state = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("  state is null", state);
        
        Thread.sleep(PPSConstants.I5000);
        
        LOG.debug("--------------- STOPPING FDB_UPDATE_JOB -------------------------------------");
        schedulerControl.setJobName(FdbJobNames.FDB_UPDATE_JOB.getJobName()); 
        schedulerControl.setFdbJobNames(FdbJobNames.FDB_UPDATE_JOB); 
        schedulerControl.setJobCommands(JobCommands.PAUSE);                 // 
        
        SchedulerState state2 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("   state is null", state2);
        
        Thread.sleep(PPSConstants.I5000);
        
        LOG.debug("--------------- SCHEDULE FDB_UPDATE_JOB -------------------------------------");
        
        schedulerControl.setJobName(FdbJobNames.FDB_UPDATE_JOB.getJobName());
        schedulerControl.setFdbJobNames(FdbJobNames.FDB_UPDATE_JOB); 
        schedulerControl.setJobCommands(JobCommands.SCHEDULE);
        
        int hours = PPSConstants.I15;
        int mins = PPSConstants.I52;
        String cron = generateCronExpression(hours, mins);  // convert hours mins to cron expression
        schedulerControl.setCronExpression(cron);
        
        SchedulerState state3 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state is null   ", state3);
        
        Thread.sleep(PPSConstants.I5000);
        
        LOG.debug("--------------- START FDB_UPDATE_JOB -------------------------------------");
        schedulerControl.setFdbJobNames(FdbJobNames.FDB_UPDATE_JOB);
        schedulerControl.setJobName(FdbJobNames.FDB_UPDATE_JOB.getJobName()); 
        schedulerControl.setJobCommands(JobCommands.RESUME);
        
        SchedulerState state4 = fdbSchedulerProcessService.executeJobCommand(schedulerControl, true);
        assertNotNull("state is null    ", state4);
        
        Thread.sleep(PPSConstants.I65000);
        
    }
    
    /**
     * generate Cron Expression
     *
     * @param pHours Hours
     * @param pMinutes mins
     * @return cron expr
     */
    private String generateCronExpression(int pHours, int pMinutes) {
        return String.format("%1$s %2$s %3$s %4$s %5$s %6$s", "0", pMinutes, pHours, "*", "*", "?");
    }
    
}

