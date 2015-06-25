/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Drug data vendor version information
 */
public class DrugDataVendorVersionVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String buildVersion = "";
    private String dataVersion = "";
    private String issueDate = "";

    /**
     * getBuildVersion.
     * @return buildVersion property
     */
    public String getBuildVersion() {
        return buildVersion;
    }

    /**
     * setBuildVersion.
     * @param buildVersion buildVersion property
     */
    public void setBuildVersion(String buildVersion) {
        this.buildVersion = trimToEmpty(buildVersion);
    }

    /**
     * getDataVersion.
     * @return dataVersion property
     */
    public String getDataVersion() {
        return dataVersion;
    }

    /**
     * setDataVersion.
     * @param dataVersion dataVersion property
     */
    public void setDataVersion(String dataVersion) {
        this.dataVersion = trimToEmpty(dataVersion);
    }

    /**
     * getIssueDate.
     * @return issueDate property
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * setIssueDate.
     * @param issueDate issueDate property
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = trimToEmpty(issueDate);
    }
}
