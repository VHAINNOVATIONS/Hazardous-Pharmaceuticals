/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler;


/**
 * 
 * Enum to hold JobStatuses
 *
 */
public enum JobStatus {

    /**
     * started
     */
    STARTED(0, "started"),

    /**
     * running
     */
    RUNNING(1, "running"),

    /**
     * paused 
     */
    PAUSED(2, "paused"),

    /**
     * resumed
     */
    RESUMED(3, "resumed"),

    /**
     * stopped
     */
    STOPPED(4, "stopped"),

    /**
     * completed
     */
    COMPLETED(5, "completed");

    private String description;
    private int code;

    /**
     * Enum constructor
     * 
     * @param pCode - code
     * @param pDesc - description
     */
    JobStatus(int pCode, String pDesc) {
        this.description = pDesc;
        this.code = pCode;
    }

    /**
     * Desciption of JobStatus 
     * @return the Job status description
     */
    public String description() {
        return this.description;
    }

    /**
     * getter for the Code 
     *
     * @return Code 
     */
    public int getCode() {
        return this.code;
    }
}
