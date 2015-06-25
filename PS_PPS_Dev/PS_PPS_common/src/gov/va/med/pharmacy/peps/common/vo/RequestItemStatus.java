/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Status of a request for a Product
 */
public enum RequestItemStatus {

    /** APPROVED */
    APPROVED,

    /** PENDING */
    PENDING,

    /** REJECTED */
    REJECTED;
    
    /**
     * Returns an array of RequestItemStatus to be displayed
     * 
     * @return Array of RequestItemStatus
     */
    public static RequestItemStatus[] displayedValues() {
        
        RequestItemStatus[] ret = {APPROVED, PENDING, REJECTED};
        
        return ret;
        
    }

    /**
     * isApproved
     * @return boolean true if this RequestItemStatus is APPROVED
     */
    public boolean isApproved() {
        return APPROVED.equals(this);
    }

    /**
     * isPending
     * @return boolean true if this RequestItemStatus is PENDING
     */
    public boolean isPending() {
        return PENDING.equals(this);
    }

    /**
     * isRejected
     * 
     * @return boolean true if this RequestItemStatus is REJECTED
     */
    public boolean isRejected() {
        return REJECTED.equals(this);
    }
}
