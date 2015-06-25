/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.scheduler;


/**
 * FdbJobNames
 *
 */
public enum FdbJobNames {

    /**
     * run all task job
     */
    INACTIVATION_CHECK_JOB(0, "inactivationCheckJob", "Inactivation Job"),
    
    /**
     * Fdb Add Job
     */
    FDB_ADD_JOB(1, "fdbAddJob", "Fdb Add"),
    
    /**
     * FDB update job
     */
    FDB_UPDATE_JOB(1, "fdbUpdateJob", "Fdb Update"),
    
    /**
     * STS job
     */
    STS_JOB(1, "stsJob", "STS"),
    
    /**
     * FSS job
     */
    FSS_JOB(1, "fssJob", "FSS");
    
    
    private String jobName;
    private String jobDisplayName;
    private int code;
    
    /**
     * JobDetailsEnum
     * @param pCode code
     * @param pJobName job name
     * @param pJobDisplayName pJobDisplayName
     */
    FdbJobNames(int pCode, String pJobName, String pJobDisplayName) {
        this.jobName = pJobName;
        this.code = pCode;
        this.jobDisplayName = pJobDisplayName;
    }
    
    /**
     * returns job name
     * @return job name
     */
    public String getJobName() {
        return this.jobName;
    }
    
    /**
     * returns the code
     * @return the code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * getJobDisplayName
     * @return the jobDisplayName
     */
    public String getJobDisplayName() {
        return jobDisplayName;
    }
}
