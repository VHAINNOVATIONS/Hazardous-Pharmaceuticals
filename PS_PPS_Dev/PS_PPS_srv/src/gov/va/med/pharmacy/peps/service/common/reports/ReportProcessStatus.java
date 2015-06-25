/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.reports;


/** ReportProcessStatus */

public enum ReportProcessStatus {

    /**
     * STARTED
     */
    STARTED("started"),

    /**
     * RUNNING
     */
    RUNNING("running"),

    /**
     * STOPPED
     */
    STOPPED("stopped"),

    /**
     * COMPLETED
     */
    COMPLETED("completed");

    private String name;

    /**
     * Report Process Status
     * @param pName name parameter
     */
    ReportProcessStatus(String pName) {
        this.name = pName;
    }

    /**
     * getName
     * @return name
     */
    public String getName() {
        return this.name;
    }

}
