/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;
import org.quartz.Trigger;


/**
 * 
 * SchedulerStateImpls
 * 
 */
public class SchedulerStateImpl implements SchedulerState {
    
    private Map<String, JobStatusInfo> jobStatusInfoMap = new HashMap<String, JobStatusInfo>();
    private Map<String, Trigger[]> jobTriggerMap = new HashMap<String, Trigger[]>();
    private List<String> jobNames;
    private String groupName;
    private Date nextFireTime; 
    private Trigger[] triggers;
    private SchedulerStatus schedulerStatus;
    private StateResponseMessage stateResponseMessage;
    private Scheduler scheduler;
    private JobStatusInfo jobStatusInfo;
    private JobStartTimeData jobStartTimeData = new JobStartTimeData();
    private Map<FdbJobNames, EplNationalInfo> eplNationalInfoMap = new HashMap<FdbJobNames, EplNationalInfo>();
    private String fdaHostName;
    
    private String errorMessage;
    private int messagesOnQueueCount;
    private boolean messagingRunning;
    private boolean messagingQueueInProcess;
    private Date serverCurrentTime;
    

    
    
    /**
     * gets EplNationalInfoMap
     * @return the eplNationalInfoMap
     */
    public Map<FdbJobNames, EplNationalInfo> getEplNationalInfoMap() {
        return eplNationalInfoMap;
    }

    /**
     * setEplNationalInfoMap
     * @param eplNationalInfoMap the eplNationalInfoMap to set
     */
    public void setEplNationalInfoMap(
            Map<FdbJobNames, EplNationalInfo> eplNationalInfoMap) {
        this.eplNationalInfoMap = eplNationalInfoMap;
    }

    /**
     * add EplNationalInfoMap
     * @param pEplNationalInfo pEplNationalInfo
     */
    public void addEplNationalInfoMap(EplNationalInfo pEplNationalInfo) {
        eplNationalInfoMap.put(pEplNationalInfo.getFdbJobNames(), pEplNationalInfo);
    }
    
    /**
     * adds JobStatusInfoToMap
     * @param pJobStatusInfo pJobStatusInfo
     */
    public void addJobStatusInfoToMap(JobStatusInfo pJobStatusInfo) {
        jobStatusInfoMap.put(pJobStatusInfo.getJobName(), pJobStatusInfo);    // add to the map
    }
    
    
    /**
     * getJobStatusInfo
     * @return the jobStatusInfo
     */
    public JobStatusInfo getJobStatusInfo() {
        return jobStatusInfo;
    }

    /**
     * sets JobStatusInfo
     * @param jobStatusInfo the jobStatusInfo to set
     */
    public void setJobStatusInfo(JobStatusInfo jobStatusInfo) {
        this.jobStatusInfo = jobStatusInfo;
    }

    /**
     * adds JobTriggersMap
     * @param pJobName pJobName
     * @param pTriggers pTriggers
     */
    public void addJobTriggersMap(String pJobName, Trigger[] pTriggers) {
        jobTriggerMap.put(pJobName, pTriggers);
    }

    
    /**
     * gets StateResponseMessage
     * @return StateResponseMessage 
    */
    public StateResponseMessage getStateResponseMessage() {
        return stateResponseMessage;
    }

    /**
     * sets StateResponseMessage
     * @param stateResponseMessage stateResponseMessage
     */
    public void setStateResponseMessage(StateResponseMessage stateResponseMessage) {
        this.stateResponseMessage = stateResponseMessage;
    }

    /**
     * gets SchedulerStatus
     * @return SchedulerStatus
     */
    public SchedulerStatus getSchedulerStatus() {
        return schedulerStatus;
    }

    /**
     * set SchedulerStatus
     * @param pSchedulerStatus pSchedulerStatus
     */
    public void setSchedulerStatus(SchedulerStatus pSchedulerStatus) {
        this.schedulerStatus = pSchedulerStatus;
    }

    /**
     * gets JobTriggerMap
     * @return Map
     */
    public Map<String, Trigger[]> getJobTriggerMap() {
        return jobTriggerMap;
    }

    /**
     * sets JobTriggerMap
     * @param pJobTriggerMap pJobTriggerMap
     */
    public void setJobTriggerMap(Map<String, Trigger[]> pJobTriggerMap) {
        this.jobTriggerMap = pJobTriggerMap;
    }

    /**
     * getNextFireTime
     * @return Date
     */
    public Date getNextFireTime() {
        return nextFireTime;
    }

    
    /**
     * sets NextFireTime
     * @param pNextFireTime pNextFireTime
     */
    public void setNextFireTime(Date pNextFireTime) {
        this.nextFireTime = pNextFireTime;
    }

    /**
     * gets GroupName
     * @return group name

     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * sets GroupName
     * @param groupName sets GroupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    /**
     * gets Triggers
     * @return the triggers
     */
    public Trigger[] getTriggers() {
        return triggers;
    }

    /**
     * sets Triggers
     * @param triggers the triggers to set
     */
    public void setTriggers(Trigger[] triggers) {
        this.triggers = triggers;
    }

    /**
     * gets Scheduler
     * @return the scheduler
     */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * sets Scheduler
     * @param scheduler the scheduler to set
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    /**
     * gets JobNames
     * @return the jobNames
     */
    public List<String> getJobNames() {
        return jobNames;
    }


    /**
     * sets JobNames
     * @param jobNames the jobNames to set
     */
    public void setJobNames(List<String> jobNames) {
        this.jobNames = jobNames;
    }


    /**
     * gets JobStatusInfoMap
     * @return the jobStatusInfoMap
     */
    public Map<String, JobStatusInfo> getJobStatusInfoMap() {
        return jobStatusInfoMap;
    }


    /**
     * set JobStatusInfoMap
     * @param jobStatusInfoMap the jobStatusInfoMap to set
     */
    public void setJobStatusInfoMap(Map<String, JobStatusInfo> jobStatusInfoMap) {
        this.jobStatusInfoMap = jobStatusInfoMap;
    }


    /**
     * gets JobStartTimeData
     * @return the jobStartTimeData
     */
    public JobStartTimeData getJobStartTimeData() {
        return jobStartTimeData;
    }


    /**
     * sets JobStartTimeData 
     * @param pJobStartTimeData the jobStartTimeData to set
     */
    public void setJobStartTimeData(JobStartTimeData pJobStartTimeData) {
        jobStartTimeData = pJobStartTimeData;
    }


    /**
     * gets Fda Host Name
     * @return the fdaHostName
     */
    public String getFdaHostName() {
        return fdaHostName;
    }

    /**
     * sets Fda Host Name
     * @param fdaHostName the fdaHostName to set
     */
    public void setFdaHostName(String fdaHostName) {
        this.fdaHostName = fdaHostName;
    }

    /**
     * getErrorMessage
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * setErrorMessage
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * getMessagesOnQueueCount
     * @return the messagesOnQueueCount
     */
    public int getMessagesOnQueueCount() {
        return messagesOnQueueCount;
    }

    /**
     * setMessagesOnQueueCount
     * @param messagesOnQueueCount the messagesOnQueueCount to set
     */
    public void setMessagesOnQueueCount(int messagesOnQueueCount) {
        this.messagesOnQueueCount = messagesOnQueueCount;
    }

    /**
     * isMessagingRunning
     * @return the messagingRunning
     */
    public boolean isMessagingRunning() {
        return messagingRunning;
    }

    /**
     * setMessagingRunning
     * @param messagingRunning the messagingRunning to set
     */
    public void setMessagingRunning(boolean messagingRunning) {
        this.messagingRunning = messagingRunning;
    }

    /**
     * isMessagingRunning
     * @return the messagingRunning
     */
    public boolean isMessagingQueueInProgress() {
        return messagingQueueInProcess;
    }

    /**
     * setMessagingQueueInProcess
     * @param messagingQueueInProcess the messagingQueueInProcess to set
     */
    public void setMessagingQueueInProcess(boolean messagingQueueInProcess) {
        this.messagingQueueInProcess = messagingQueueInProcess;
    }

    /**
     * gets Server Current Time
     * @return the serverCurrentTime
     */
    public Date getServerCurrentTime() {
        return serverCurrentTime;
    }

    /**
     * sets Server Current Time
     * @param serverCurrentTime the serverCurrentTime to set
     */
    public void setServerCurrentTime(Date serverCurrentTime) {
        this.serverCurrentTime = serverCurrentTime;
    }

    
    
}
