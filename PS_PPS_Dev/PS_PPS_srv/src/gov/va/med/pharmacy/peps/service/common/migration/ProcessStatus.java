/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * 
 * ProcessStatus
 *
 */
public enum ProcessStatus {

    /** STARTED */
    STARTED(0, "started"), 
    
    /** RUNNING */
    RUNNING(1, "running"), 
    
    /** PAUSED */
    PAUSED(2, "paused"), 
    
    /** RESUMED */
    RESUMED(3, "resumed"), 
    
    /** STOPPED */
    STOPPED(4, "stopped"),
    
    /** COMPLETED */
    COMPLETED(5, "completed"), 
    
    /** DB_RESET_STARTED */
    DB_RESET_STARTED(6, "dbResetStarted"), 
    
    /** DB_RESET_RUNNING */
    DB_RESET_RUNNING(7, "dbResetRunning"),
    
    /**
     * DB_RESET_STOPPED
     */
    DB_RESET_STOPPED(8, "dbResetStopped");

    private String description;
    private int code;

    /**
     * ProcessStatus
     * @param pCode pCode
     * @param pDesc pDesc
     */
    ProcessStatus(int pCode, String pDesc) {
        this.description = pDesc;
        this.code = pCode;
    }

    /**
     * description
     * @return description
     */
    public String description() {
        return this.description;
    }

    /**
     * getCode
     * @return getCode
     */
    public int getCode() {
        return this.code;
    }

}
