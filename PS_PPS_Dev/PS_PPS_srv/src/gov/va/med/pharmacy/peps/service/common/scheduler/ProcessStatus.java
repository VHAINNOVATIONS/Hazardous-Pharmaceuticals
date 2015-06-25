/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.scheduler;


/**
 * ProcessStatus
 */
public enum ProcessStatus {

    /**
     * started for ProcessStatus
     */
    STARTED(0, "started"),
    
    /**
     * running for ProcessStatus
     */
    RUNNING(1, "running"),
    
    /**
     * completed for ProcessStatus
     */
    COMPLETED(2, "completed"),
    
    /**
     * STOPPED for ProcessStatus
     */
    STOPPED(3, "stopped");
    
    private String description;
    private int code;
    
    /**
     * Enum constructor
     * 
     * @param pCode - code
     * @param pDesc - description
     */
    ProcessStatus(int pCode, String pDesc) {
        this.description = pDesc;
        this.code = pCode;
    }
    
    /**
     * Description of JobStatus for the ProcessStatus
     * @return the Job status description
     */
    public String description() {
        return this.description;
    }
    
    /**
     * getter for the Code for the ProcessStatus
     *
     * @return Code 
     */
    public int getCode() {
        return this.code;
    }
    
    /**
     * isStarted for the ProcessStatus
     * @return boolean true if this ProcessStatus is STARTED
     */
    public boolean isStarted() {
        return STARTED.equals(this);
    }
    
    /**
     * isRunning
     * @return boolean true if this ProcessStatus is RUNNING
     */
    public boolean isRunning() {
        return RUNNING.equals(this);
    }

    /**
     * isCompleted
     * @return boolean true if this ProcessStatus is COMPLETED
     */
    public boolean isCompleted() {
        return COMPLETED.equals(this);
    }
    
    /**
     * isCompleted
     * @return boolean true if this ProcessStatus is COMPLETED
     */
    public boolean isStopped() {
        return STOPPED.equals(this);
    }

}
