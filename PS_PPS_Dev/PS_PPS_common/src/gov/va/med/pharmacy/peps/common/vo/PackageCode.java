/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * State of a package
 */
public enum PackageCode {

    /** INPATIENT */
    INPATIENT,

    /** OUTPATIENT */
    OUTPATIENT,

    /** NONE */
    NONE,

    /** BOTH */
    BOTH;

    /**
     * isInpatient
     * 
     * @return boolean true if this PackageCode is INPATIENT
     */
    public boolean isInpatient() {

        return INPATIENT.equals(this);
    }

    /**
     * isOutpatient
     * 
     * @return boolean true if this PackageCode is OUTPATIENT
     */
    public boolean isOutpatient() {

        return OUTPATIENT.equals(this);
    }

    /**
     * isNone
     * 
     * @return boolean true if this PackageCode is NONE
     */
    public boolean isNone() {

        return NONE.equals(this);
    }

    /**
     * isBoth
     * 
     * @return boolean true if this PackageCode is BOTH
     */
    public boolean isBoth() {

        return BOTH.equals(this);
    }
}
