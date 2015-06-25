/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Data representing a Migration Control Class
 */
public class MigrationControlVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private Long eplId;
    private String userName;
    private Date startDateTime;
    private Date stopDateTime;
    private Long threadId;
    private String migrationStatus;
    private String userNdcFile;
    private String userOiFile;
    private String userProductFile;

    /**
     * the constructor
     */
    public MigrationControlVo() {
    }

    /**
     * getEplId
     * @return eplId
     */
    public Long getEplId() {
        return eplId;
    }

    /**
     * setEplId
     * @param eplId eplId
     */
    public void setEplId(Long eplId) {
        this.eplId = eplId;
    }

    /**
     * getUserName
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * setUserName
     * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * getStartDateTime
     * @return startDateTime
     */
    public Date getStartDateTime() {
        return startDateTime;
    }

    /**
     * setStartDateTime
     * @param startDateTime startDateTime
     */
    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * getStopDateTime
     * @return stopDateTime
     */
    public Date getStopDateTime() {
        return stopDateTime;
    }

    /**
     * setStopDateTime
     * @param stopDateTime stopDateTime
     */
    public void setStopDateTime(Date stopDateTime) {
        this.stopDateTime = stopDateTime;
    }

    /**
     * getThreadId
     * @return threadId
     */
    public Long getThreadId() {
        return threadId;
    }

    /**
     * setThreadId
     * @param threadId threadId
     */
    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    /** 
     * getMigrationStatus
     * @return migrationStatus
     */
    public String getMigrationStatus() {
        return migrationStatus;
    }

    /**
     * setMigrationStatus
     * @param migrationStatus migrationStatus
     */
    public void setMigrationStatus(String migrationStatus) {
        this.migrationStatus = migrationStatus;
    }

    /**
     * getUserNdcFile
     * @return userNdcFile
     */
    public String getUserNdcFile() {
        return userNdcFile;
    }

    /**
     * setUserNdcFile
     * @param userNdcFile userNdcFile
     */
    public void setUserNdcFile(String userNdcFile) {
        this.userNdcFile = userNdcFile;
    }

    /**
     * getUserOiFile
     * @return userOiFile
     */
    public String getUserOiFile() {
        return userOiFile;
    }

    /**
     * setUserOiFile
     * @param userOiFile userOiFile
     */
    public void setUserOiFile(String userOiFile) {
        this.userOiFile = userOiFile;
    }

    /**
     * getUserProductFile
     * @return userProductFile
     */
    public String getUserProductFile() {
        return userProductFile;
    }

    /** 
     * setUserProductFile
     * @param userProductFile userProductFile
     */
    public void setUserProductFile(String userProductFile) {
        this.userProductFile = userProductFile;
    }


    /**
     * Returns true if the domain is standardized
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if the domain is an NDF domain for MigrationControlVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

    /**
     * Returns true if this is a local only domain for MigrationControlVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }
}
