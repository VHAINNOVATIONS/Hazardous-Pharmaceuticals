/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler;


import java.util.Date;


/**
 * 
 * JobStatusInfos
 *  
 */
public class JobStatusInfo {

    private String jobName;
    private String groupName;
    private JobStatus status;
    private FdbJobNames fdbJobNames;
    private Date nextFireTime;
    private ProcessStatus processStatus;
    private Date lastSuccessRunDate;
    private Integer hour;
    private Integer mins;
    
    /**
     * getJobName for JobStatusInfo.
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }
    
    /**
     * setJobName for JobStatusInfo.
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    /**
     * getGroupName for JobStatusInfo.
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }
    
    /**
     * setGroupName for JobStatusInfo.
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    /**
     * getStatus
     * @return the status
     */
    public JobStatus getStatus() {
        return status;
    }
    
    /**
     * setStatus
     * @param status the status to set
     */
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    /**
     * getFdbJobNames
     * @return the fdbJobNames
     */
    public FdbJobNames getFdbJobNames() {
        return fdbJobNames;
    }

    /**
     * setFdbJobNames
     * @param fdbJobNames the fdbJobNames to set
     */
    public void setFdbJobNames(FdbJobNames fdbJobNames) {
        this.fdbJobNames = fdbJobNames;
    }

    /**
     * getNextFireTime
     * @return the nextFireTime
     */
    public Date getNextFireTime() {
        return nextFireTime;
    }

    /**
     * setNextFireTime
     * @param nextFireTime the nextFireTime to set
     */
    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    /**
     * get ProcessStatus
     * @return the processStatus
     */
    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    /**
     * set ProcessStatus
     * @param processStatus the processStatus to set
     */
    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    /**
     * get Last Success Run Date
     * @return the lastSuccessRunDate
     */
    public Date getLastSuccessRunDate() {
        return lastSuccessRunDate;
    }

    /**
     * set Last Success Run date
     * @param lastSuccessRunDate the lastSuccessRunDate to set
     */
    public void setLastSuccessRunDate(Date lastSuccessRunDate) {
        this.lastSuccessRunDate = lastSuccessRunDate;
    }

    /**
     * gets Hour for JobStatusInfo.
     * @return the hour
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * sets Hour for JobStatusInfo.
     * @param hour the hour to set
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     * gets Minutes
     * @return the mins
     */
    public Integer getMins() {
        return mins;
    }

    /**
     * sets Minutes
     * @param mins the mins to set
     */
    public void setMins(Integer mins) {
        this.mins = mins;
    }


    
}
