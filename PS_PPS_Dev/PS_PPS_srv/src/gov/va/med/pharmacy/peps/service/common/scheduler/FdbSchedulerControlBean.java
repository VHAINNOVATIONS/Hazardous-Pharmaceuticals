/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler;


/**
 * 
 * SchedulerControl
 * defines scheduler and job commands
 */
public class FdbSchedulerControlBean {

    private SchedulerCommands schedulerCommand;
    private JobCommands jobCommands;
    private FdbJobNames fdbJobNames;
    private String jobName; 
    private String cronExpression;
    private Integer hour;
    private Integer mins;
 

    
    /**
     * gets JobCommands
     * @return the jobCommands
     */
    public JobCommands getJobCommands() {
        return jobCommands;
    }

    /**
     * sets JobCommands
     * @param jobCommands the jobCommands to set
     */
    public void setJobCommands(JobCommands jobCommands) {
        this.jobCommands = jobCommands;
    }

    /**
     * gets SchedulerCommand
     * @return the schedulerCommand
     */
    public SchedulerCommands getSchedulerCommand() {
        return schedulerCommand;
    }
    
    /**
     * set SchedulerCommand
     * @param schedulerCommand the schedulerCommand to set
     */
    public void setSchedulerCommand(SchedulerCommands schedulerCommand) {
        this.schedulerCommand = schedulerCommand;
    }

    /**
     * gets FdbJobNames
     * @return the fdbJobNames
     */
    public FdbJobNames getFdbJobNames() {
        return fdbJobNames;
    }

    /**
     * sets FdbJobNames
     * @param fdbJobNames the fdbJobNames to set
     */
    public void setFdbJobNames(FdbJobNames fdbJobNames) {
        this.fdbJobNames = fdbJobNames;
    }

    /**
     * getJobName
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * setJobName
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * getCronExpression
     * @return the cronExpression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * setCronExpression
     * @param cronExpression the cronExpression to set
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * gets Hour
     * @return the hour
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * sets Hour
     * @param hour the hour to set
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     * gets Mins 
     * @return the mins
     */
    public Integer getMins() {
        return mins;
    }

    /**
     * sets Mins
     * @param mins the mins to set
     */
    public void setMins(Integer mins) {
        this.mins = mins;
    }
    
    
    
    
    
    
}
