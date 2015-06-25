/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * FdbStatusCode filter
 */
public enum FdbStatusCode {
    
    /**
     * ACTIVE
     */
    ACTIVE, 
    
    /**
     * REPLACED
     */
    REPLACED, 
    
    /**
     * RETIRED
     */
    RETIRED, 
    
    /**
     * INACTIVE
     */
    INACTIVE, 
    
    /**
     * UNASSOCIATED
     */
    UNASSOCIATED;
    
    /**
     * isActive.
     * @return boolean true if this FdbStatusCode filter is ACTIVE
     */
    public boolean isActive() {
        return ACTIVE.equals(this);
    }

    /**
     * isReplaced.
     * @return boolean true if this FdbStatusCode filter is REPLACED
     */
    public boolean isReplaced() {
        return REPLACED.equals(this);
    }
    
    /**
     * isRetired.
     * @return boolean true if this FdbStatusCode filter is RETIRED
     */
    public boolean isRetired() {
        return RETIRED.equals(this);
    }

    /**
     * isInactive.
     * @return boolean true if this FdbStatusCode filter is INACTIVE
     */
    public boolean isInactive() {
        return INACTIVE.equals(this);
    }

    /**
     * isUnassociated.
     * @return boolean true if this FdbStatusCode filter is UNASSOCIATED
     */
    public boolean isUnassociated() {
        return UNASSOCIATED.equals(this);
    }
}
