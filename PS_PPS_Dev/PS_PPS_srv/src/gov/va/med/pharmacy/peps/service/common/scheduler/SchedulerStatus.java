/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler;


/**
 * SchedulerStatus enum
 */
public enum SchedulerStatus {

    /**
     * STARTED
     */
    STARTED(0, "started"),
    
    /**
     * RUNNING
     */
    RUNNING(1, "running"),
    
    /**
     * PAUSED
     */
    PAUSED(2, "paused"),
    
    /**
     * RESUMED
     */
    RESUMED(3, "resumed"),
    
    /**
     * STOPPED
     */
    STOPPED(4, "stopped"),
    
    /**
     * STANDBY
     */
    STANDBY(5, "standby"),
    
    /**
     * SHUTDOWN
     */
    SHUTDOWN(6, "shutdown"),
    
    /**
     * COMPLETED
     */
    COMPLETED(7, "completed");
    
    private String description;
    private int code;
    
    /**
     * SchedulerStatus
     * @param pCode code
     * @param pDesc description
     */
    SchedulerStatus(int pCode, String pDesc) {
        this.description = pDesc;
        this.code = pCode;
    }
    
    /**
     * Description
     *
     * @return the description
     */
    public String description() {
        return this.description;
    }
    
    /**
     * Description
     *
     * @return the code
     */
    public int getCode() {
        return this.code;
    }
}
