/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Data representing site configuration
 */
public class SiteUpdateScheduleVo extends ValueObject {

    /** PPS */
    public static final String PPS = "PPS";

    /** VISTA */
    public static final String VISTA = "VISTA";

    /** COTS */
    public static final String COTS = "COTS";
    
    private static final long serialVersionUID = 1L;

    private String softwareUpdateType;
    private String softwareUpdateVersion;
    private Long id;
    private String siteNumber;
    private String inProgress;
    private Date startDtm;
    private Date endDtm;
    private Date scheduleStartDtm;
    private String scheduleInterval;
    private String md5Sum;

    /**
     * getId for SiteUpdateScheduleVo.
     * 
     * @return id property
     */
    public Long getId() {
        return id;
    }

    /**
     * setId for SiteUpdateScheduleVo.
     * 
     * @param id id property
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getSiteNumber for SiteUpdateScheduleVo.
     * 
     * @return siteNumber property
     */
    public String getSiteNumber() {
        return siteNumber;
    }

    /**
     * setSiteNumber for SiteUpdateScheduleVo.
     * 
     * @param siteNumber siteNumber property
     */
    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    /**
     * Description
     * 
     * @return softwareUpdateType property
     */
    public String getSoftwareUpdateType() {
        return softwareUpdateType;
    }

    /**
     * Description
     * 
     * @param softwareUpdateType softwareUpdateType property
     */
    public void setSoftwareUpdateType(String softwareUpdateType) {
        this.softwareUpdateType = softwareUpdateType;
    }

    /**
     * Description
     * 
     * @return softwareUpdateVersion property
     */
    public String getSoftwareUpdateVersion() {
        return softwareUpdateVersion;
    }

    /**
     * Description
     * 
     * @param softwareUpdateVersion softwareUpdateVersion property
     */
    public void setSoftwareUpdateVersion(String softwareUpdateVersion) {
        this.softwareUpdateVersion = softwareUpdateVersion;
    }

    /**
     * Description
     * 
     * @return scheduleStartDtm property
     */
    public Date getScheduleStartDtm() {
        return scheduleStartDtm;
    }

    /**
     * Description
     * 
     * @param scheduleStartDtm scheduleStartDtm property
     */
    public void setScheduleStartDtm(Date scheduleStartDtm) {
        this.scheduleStartDtm = scheduleStartDtm;
    }

    /**
     * Description
     * 
     * @return scheduleInterval property
     */
    public String getScheduleInterval() {
        return scheduleInterval;
    }

    /**
     * Description
     * 
     * @param scheduleInterval scheduleInterval property
     */
    public void setScheduleInterval(String scheduleInterval) {
        this.scheduleInterval = scheduleInterval;
    }

    /**
     * Description
     * 
     * @return inProgress property
     */
    public String getInProgress() {
        return inProgress;
    }

    /**
     * Description
     * 
     * @param inProgress inProgress property
     */
    public void setInProgress(String inProgress) {
        this.inProgress = inProgress;
    }

    /**
     * Description
     * 
     * @return startDtm property
     */
    public Date getStartDtm() {
        return startDtm;
    }

    /**
     * Description
     * 
     * @param startDtm startDtm property
     */
    public void setStartDtm(Date startDtm) {
        this.startDtm = startDtm;
    }

    /**
     * Description
     * 
     * @return endDtm property
     */
    public Date getEndDtm() {
        return endDtm;
    }

    /**
     * Description
     * 
     * @param endDtm endDtm property
     */
    public void setEndDtm(Date endDtm) {
        this.endDtm = endDtm;
    }

    /**
     * Description
     * 
     * @return md5Sum property
     */
    public String getMd5Sum() {
        return md5Sum;
    }

    /**
     * Description
     * 
     * @param md5Sum md5Sum property
     */
    public void setMd5Sum(String md5Sum) {
        this.md5Sum = md5Sum;
    }
}
