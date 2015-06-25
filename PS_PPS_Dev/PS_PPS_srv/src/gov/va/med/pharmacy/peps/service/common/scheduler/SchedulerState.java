/**
 * Source file created in 2007 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.scheduler;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;
import org.quartz.Trigger;


/**
 * 
 * SchedulerState Interface
 *
 */
public interface SchedulerState {

    
    

    /**
     * gets FdaHostName
     * @return the fdaHostName
     */
    String getFdaHostName();

    /**
     * sets Fda Host Name
     * @param fdaHostName the fdaHostName to set
     */
    void setFdaHostName(String fdaHostName);

    
    /**
     * gets EplNationalInfoMap
     * @return the eplNationalInfoMap
     */
    Map<FdbJobNames, EplNationalInfo> getEplNationalInfoMap();

    
    /**
     * sets EplNationalInfoMap
     * @param eplNationalInfoMap eplNationalInfoMap
     */
    void setEplNationalInfoMap(Map<FdbJobNames, EplNationalInfo> eplNationalInfoMap);
    
    /**
     * add EplNationalInfoMap
     * @param pEplNationalInfo pEplNationalInfo
     */
    void addEplNationalInfoMap(EplNationalInfo pEplNationalInfo);
    
    /**
     * Returns the Scheduler Status
     * 
     * @return SchedulerStatus
     */
    SchedulerStatus getSchedulerStatus(); 
    
    /**
     * Setter for the SchedulerStatus
     *
     * @param pSchedulerStatus - scheduler status
     */
    void setSchedulerStatus(SchedulerStatus pSchedulerStatus);
    
    /**
     * 
     * Returns the JobTriggerMap
     *
     * @return TriggerMap
     */
    Map<String, Trigger[]> getJobTriggerMap(); 
    
    /**
     * Setter for TriggerMap
     *
     * @param pJobTriggerMap the JobTriggerMap
     */
    void setJobTriggerMap(Map<String, Trigger[]> pJobTriggerMap);
    
    /**
     * Getter for the Next fire time
     *
     * @return Next Fire time Date
     */
    Date getNextFireTime();
    
    /**
     * setter for the next fire time
     *
     * @param pNextFireTime the fire time
     */
    void setNextFireTime(Date pNextFireTime);
    
    /**
     * Getter for the Response message
     *
     * @return StateResponseMessage
     * 
     */
    StateResponseMessage getStateResponseMessage();
    
    /**
     * setter for the Response message
     *
     * @param stateResponseMessage the state response messages
     */
    void setStateResponseMessage(StateResponseMessage stateResponseMessage);
    
    /**
     * The Group Name
     * @return the group name
     */
    String getGroupName();
    
    /**
     * Setter for the Group name
     *
     * @param groupName -  group name
     */
    void setGroupName(String groupName);

    /**
     * Getter for the Triggers
     *
     * @return Trigger
     */
    Trigger[] getTriggers();
    
    /**
     * setter for the triggers
     *
     * @param triggers - array of triggers
     */
    void setTriggers(Trigger[] triggers);
    
    /**
     * getter for the Scheduler
     *
     * @return Scheduler 
     */
    Scheduler getScheduler();
    
    /**
     * setter for the Scheduler
     *
     * @param scheduler - the scheduler
     */
    void setScheduler(Scheduler scheduler);
    
    /**
     * Adds the job triggers and jobname to the map
     *
     * @param pJobName - the job name
     * @param pTriggers the triggers
     */
    void addJobTriggersMap(String pJobName, Trigger[] pTriggers);
    
    /**
     * get JobNames
     *
     * @return list of job names
     */
    List<String> getJobNames();

    
    /**
     * setter for the job Names
     *
     * @param jobNames list of job names
     */
    void setJobNames(List<String> jobNames);

    
    /**
     * Adds the Job Status info to map
     *
     * @param pJobStatusInfo job status info
     */
    void addJobStatusInfoToMap(JobStatusInfo pJobStatusInfo);
    
    /**
     * Getter for the job status info
     *
     * @return returns the jobStatus info Map
     */
    Map<String, JobStatusInfo> getJobStatusInfoMap();
    
    
    /**
     * Setter for the Job Status info map
     *
     * @param jobStatusInfoMap the map
     */
    void setJobStatusInfoMap(Map<String, JobStatusInfo> jobStatusInfoMap);
    
    /**
     * 
     * Returns the JobStartTimeData
     *
     * @return JobStartTimeData
     */
    JobStartTimeData getJobStartTimeData();
    
    /**
     * Setter for the Job Start Data
     *
     * @param jobStartTimeData JobStartTimeData
     */
    void setJobStartTimeData(JobStartTimeData jobStartTimeData);
    
    /**
     * isMessagingRunning
     * @return the messagingRunning
     */
    boolean isMessagingRunning();

    /**
     * setMessagingRunning
     * @param messagingRunning the messagingRunning to set
     */
    void setMessagingRunning(boolean messagingRunning);
    
    /**
     * isMessagingQueueInProgress
     * @return the messagingQueueInProgress
     */
    boolean isMessagingQueueInProgress();

    /**
     * setMessagingRunning
     * @param messagingQueueInProgress the messagingQueueInProgress to set
     */
    void setMessagingQueueInProcess(boolean messagingQueueInProgress);
    
    
    /**
     * getErrorMessage
     * @return the errorMessage
     */
    String getErrorMessage();

    /**
     * setErrorMessage
     * @param errorMessage the errorMessage to set
     */
    void setErrorMessage(String errorMessage);

    /**
     * getMessagesOnQueueCount
     * @return the messagesOnQueueCount
     */
    int getMessagesOnQueueCount();

    /**
     * setMessagesOnQueueCount
     * @param messagesOnQueueCount the messagesOnQueueCount to set
     */
    void setMessagesOnQueueCount(int messagesOnQueueCount);
    
    /**
     * gets Server Current Time
     * @return the serverCurrentTime
     */
    Date getServerCurrentTime();

    /**
     * sets Server Current Time
     * @param serverCurrentTime the serverCurrentTime to set
     */
    void setServerCurrentTime(Date serverCurrentTime);
}


