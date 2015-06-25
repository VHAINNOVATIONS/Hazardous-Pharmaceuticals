/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Type for a request to modify an existing item or add a new item
 */
public enum RequestType {

    /** ADDITION */
    ADDITION,

    /** MODIFICATION */
    MODIFICATION,

    /** PROBLEM_REPORT */
    PROBLEM_REPORT;

    /**
     * isAddition
     * @return boolean true if this RequestType is ADDITION
     */
    public boolean isAddition() {

        return ADDITION.equals(this);
    }

    /**
     * isModification
     * @return boolean true if this RequestType is MODIFICATION
     */
    public boolean isModification() {

        return MODIFICATION.equals(this);
    }

    /**
     * isProblemReport
     * @return boolean true if this RequestType is PROBLEM_REPORT
     */
    public boolean isProblemReport() {

        return PROBLEM_REPORT.equals(this);
    }

    /**
     * getAddition
     * @return boolean true if this RequestType is ADDITION
     */
    public boolean getAddition() {

        return isAddition();
    }

    /**
     * getModification
     * @return boolean true if this RequestType is MODIFICATION
     */
    public boolean getModification() {

        return isModification();
    }

    /**
     * getProblemReport
     * @return boolean true if this RequestType is PROBLEM_REPORT
     */
    public boolean getProblemReport() {

        return isProblemReport();
    }

}
