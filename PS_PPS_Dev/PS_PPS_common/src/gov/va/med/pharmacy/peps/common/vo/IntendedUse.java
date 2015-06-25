/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Intended use domain
 */
public enum IntendedUse {
    
    /**
     * INPATIENT.
     */
    INPATIENT, 
    
    /**
     * OUTPATIENT
     */
    OUTPATIENT, 
    
    /**
     * NONE.
     */
    NONE, 
    
    /**
     * BOTH.
     */
    BOTH;
    
    /**
     * isInpatient.
     * @return boolean true if this Intended Use is INPATIENT
     */
    public boolean isInpatient() {
        return INPATIENT.equals(this);
    }

    /**
     * isOutpatient.
     * @return boolean true if this Intended Use is OUTPATIENT
     */
    public boolean isOutpatient() {
        return OUTPATIENT.equals(this);
    }

    /**
     * isNone.
     * @return boolean true if this Intended Use is neither INPATIENT not OUTPATIENT
     */
    public boolean isNone() {
        return NONE.equals(this);
    }

    /**
     * isBoth.
     * @return boolean true if this Intended Use is both INPATIENT and OUTPATIENT
     */
    public boolean isBoth() {
        return BOTH.equals(this);
    }
}
