/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.Map;

import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.scheduler.FdbSchedulerControlBean;
import gov.va.med.pharmacy.peps.service.common.scheduler.SchedulerState;



/**
 * FdbSchedulerProcessCapability
 */
public interface FdbSchedulerProcessCapability {
    
      
    /**
     * execute Job commands, PAUSE, RESUME & SCHEDULE
     * @param pSchedulerControl the command
     * @param pRunMode the run mode
     * @return scheduler state
     */
    SchedulerState executeJobCommand(FdbSchedulerControlBean pSchedulerControl, Boolean pRunMode);
    
    /**
     * getScheduler
     * @return scheduler
     */
    Scheduler getScheduler();
    
    /**
     * sets the scheduler
     * @param scheduler the scheduler
     */
    void setScheduler(Scheduler scheduler); 
    
    /**
     * Scheduler State
     * @return return the scheduler state
     */
    SchedulerState getSchedulerState();
    
    /**
     * sets the scheduler state
     * @param pSchedulerState the sheduler state
     */
    void setSchedulerState(SchedulerState pSchedulerState);
    
    /**
     * gets the jobFactory
     * @return the jobFactory
     */
    SpringBeanJobFactory getJobFactory();
    
    /**
     * sets the JobFactory
     * @param pJobFactory sets the job factory
     */
    void setJobFactory(SpringBeanJobFactory pJobFactory);

    /**
     * gets FdbAddTrigger
     * @return the fdbAddTrigger
     */
    CronTriggerBean getFdbAddTrigger();

    /**
     * sets FdbAddTrigger
     * @param fdbAddTrigger the fdbAddTrigger to set
     */
    void setFdbAddTrigger(CronTriggerBean fdbAddTrigger);
    
    /**
     * get FdbAddJob
     * @return JobDetailBean
     */
    JobDetailBean getFdbAddJob();

    /**
     * setFdbAddJob
     * @param fdbAddJob the fdbAddJob to set
     */
    void setFdbAddJob(JobDetailBean fdbAddJob);

    /**
     * update Host Name
     * @param hostName updates host name
     * @param pUser user
     */
    void updateHostName(String hostName, UserVo pUser);

    
    /**
     * update MessagingState
     * @param messagingState messagingState
     * @param pUser pUser
     */
    void updateMessagingState(Boolean messagingState, UserVo pUser);

    
    /**
     * retrieves NationalSettingsMap
     * @return Map
     */
    Map<String, Object> retrieveNationalSettingsMap();
    
    /**
     * updates NationalSettings
     *
     * @param vo NationalSettingVo
     * @param pUser pUser
     */
    void updateNationalSettings(NationalSettingVo vo, UserVo pUser);  
    
    /**
     * get Message Status
     *
     * @return messages status
     */
    boolean getMessageStatus();

}
