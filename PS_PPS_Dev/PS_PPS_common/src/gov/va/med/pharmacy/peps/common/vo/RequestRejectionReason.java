/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Different selections for Request Rejection Reason
 */
public enum RequestRejectionReason {
    
    /** INAPPROPRIATE_REQUEST */
    INAPPROPRIATE_REQUEST,

    /** ITEM_EXISTS */
    ITEM_EXISTS,

    /** INCOMPLETE_INFORMATION */
    INCOMPLETE_INFORMATION;

    /**
     * isInappropriateRequest
     * 
     * @return boolean true if this request rejection reason is inappropriate request
     */
    public boolean isInappropriateRequest() {
        return INAPPROPRIATE_REQUEST.equals(this);
    }

    /**
     * isItemExists
     * 
     * @return boolean true if request rejection reason is item already exists under a different name
     */
    public boolean isItemExists() {
        return ITEM_EXISTS.equals(this);
    }

    /**
     * isIncompleteInformation
     * 
     * @return boolean true if the rejection reason is incomplete information
     */
    public boolean isIncompleteInformation() {
        return INCOMPLETE_INFORMATION.equals(this);
    }
}
