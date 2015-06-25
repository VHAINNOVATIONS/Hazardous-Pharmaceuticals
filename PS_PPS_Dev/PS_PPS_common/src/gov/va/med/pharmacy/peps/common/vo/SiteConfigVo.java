/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Data representing site configuration
 */
public class SiteConfigVo extends ValueObject {

    /** FIELD_SEPERATOR */
    public static final String FIELD_SEPERATOR = " | ";

    /** siteConfigSimpleDateFormat */
    public static final SimpleDateFormat SITE_CONFIG_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", 
        Locale.US);

    private static final long serialVersionUID = 1L;

    private String siteNumber;
    private String siteName;
    private String siteServerName;
    private String siteVistaVersion; // stubbed until M5
    private String sitePepsDbVersion;
    private String siteCotsDbVersion; // composed of FDB issueDate + dataVersion + buildVersion
    private String siteVersionInfoDate; // date/time of object creation
    private String siteVistaMessageSynchronization;

    /**
     * id is the site number
     * 
     * @return id property
     */
    public String getSiteNumber() {
        return siteNumber;
    }

    /**
     * id is the site number
     * 
     * @param id id property
     */
    public void setSiteNumber(String id) {
        this.siteNumber = id;
    }

    /**
     * value is the site name
     * 
     * @return value property
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * value is the site name
     * 
     * @param value value property
     */
    public void setSiteName(String value) {
        this.siteName = value;
    }

    /**
     * Description
     * 
     * @return siteVistaVersion property
     */
    public String getSiteVistaVersion() {
        return siteVistaVersion;
    }

    /**
     * Description
     * 
     * @param siteVistaVersion siteVistaVersion property
     */
    public void setSiteVistaVersion(String siteVistaVersion) {
        this.siteVistaVersion = siteVistaVersion;
    }

    /**
     * Description
     * 
     * @return sitePepsDbVersion property
     */
    public String getSitePepsDbVersion() {
        return sitePepsDbVersion;
    }

    /**
     * Description
     * 
     * @param sitePepsDbVersion sitePepsDbVersion property
     */
    public void setSitePepsDbVersion(String sitePepsDbVersion) {
        this.sitePepsDbVersion = sitePepsDbVersion;
    }

    /**
     * Description
     * 
     * @return siteCotsDbVersion property
     */
    public String getSiteCotsDbVersion() {
        return siteCotsDbVersion;
    }

    /**
     * Description
     * 
     * @param siteCotsDbVersion siteCotsDbVersion property
     */
    public void setSiteCotsDbVersion(String siteCotsDbVersion) {
        this.siteCotsDbVersion = siteCotsDbVersion;
    }

    /**
     * date/time of when this info was current (time of object creation)
     * 
     * @return siteVersionInfoDate property
     */
    public String getSiteVersionInfoDate() {
        return siteVersionInfoDate;
    }

    /**
     * date/time of when this info was current (time of object creation)
     * 
     * @param siteVersionInfoDate siteVersionInfoDate property
     */
    public void setSiteVersionInfoDate(String siteVersionInfoDate) {
        this.siteVersionInfoDate = siteVersionInfoDate;
    }

    /**
     * Description
     * 
     * @return siteServerName property
     */
    public String getSiteServerName() {
        return siteServerName;
    }

    /**
     * Description
     * 
     * @param siteServerName siteServerName property
     */
    public void setSiteServerName(String siteServerName) {
        this.siteServerName = siteServerName;
    }

    /**
     * Description
     * 
     * 
     * @param vistaMessageSent sent
     */
    public void setVistaMessageSynchronization(String vistaMessageSent) {
        this.siteVistaMessageSynchronization = vistaMessageSent;
    }

    /**
     * Description
     * 
     * 
     * @return sent
     */
    public String getSiteVistaMessageSynchronization() {
        return siteVistaMessageSynchronization;
    }

}
