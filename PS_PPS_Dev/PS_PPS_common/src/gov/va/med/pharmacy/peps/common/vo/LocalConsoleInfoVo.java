/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;




/**
 * Data representing site configuration
 */
public class LocalConsoleInfoVo extends ValueObject {

    /** PPS */
    public static final String PPS = "PPS";

    /** COTS */
    public static final String COTS = "COTS";

    /** VISTA */
    public static final String VISTA = "VISTA";

    private static final long serialVersionUID = 1L;

    private String siteNumber;
    private String siteName;
    private String serverName;
    private String softwareUpdateType;
    private String softwareUpdateVersion;
    private String versionInstallDtm;

    /**
     * getServerName
     * 
     * @return serverName property
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * setServerName
     * 
     * @param serverName serverName property
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * getSiteNumber
     * 
     * @return siteNumber property
     */
    public String getSiteNumber() {
        return siteNumber;
    }

    /**
     * setSiteNumber
     * 
     * @param siteNumber siteNumber property
     */
    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    /**
     * getSiteName for LocaleConsoleInfoVo.
     * 
     * @return siteName property
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * setSiteName for LocaleConsoleInfoVo.
     * 
     * @param siteName siteName property
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * getSoftwareUpdateType for LocaleConsoleInfoVo.
     * 
     * @return softwareUpdateType property
     */
    public String getSoftwareUpdateType() {
        return softwareUpdateType;
    }

    /**
     * setSoftwareUpdateType
     * 
     * @param softwareUpdateType softwareUpdateType property
     */
    public void setSoftwareUpdateType(String softwareUpdateType) {
        this.softwareUpdateType = softwareUpdateType;
    }

    /**
     * getSoftwareUpdateVersion
     * 
     * @return softwareUpdateVersion property
     */
    public String getSoftwareUpdateVersion() {
        return softwareUpdateVersion;
    }

    /**
     * setSoftwareUpdateVersion
     * 
     * @param softwareUpdateVersion softwareUpdateVersion property
     */
    public void setSoftwareUpdateVersion(String softwareUpdateVersion) {
        this.softwareUpdateVersion = softwareUpdateVersion;
    }

    /**
     * getVersionInstallDtm
     * 
     * @return versionInstallDtm property
     */
    public String getVersionInstallDtm() {
        return versionInstallDtm;
    }

    /**
     * setVersionInstallDtm
     * 
     * @param versionInstallDtm versionInstallDtm property
     */
    public void setVersionInstallDtm(String versionInstallDtm) {
        this.versionInstallDtm = versionInstallDtm;
    }
}
