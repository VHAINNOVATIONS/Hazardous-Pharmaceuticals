/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Event type for audit history type for a request
 */
public enum EventCategory {
   
    /**
     * REQUEST_TO_ADD
     */
    REQUEST_TO_ADD,
    
    /**
     * APPROVED_REQUEST.
     */
    APPROVED_REQUEST,
    
    /**
     * REJECTED_REQUEST.
     */
    REJECTED_REQUEST,
    
    /**
     * MODIFICATION_REQUEST.
     */
    MODIFICATION_REQUEST,
    
    /**
     * COMPLETED_MODIFICATION_REQUEST.
     */
    COMPLETED_MODIFICATION_REQUEST,
    
    /**
     * LOCAL_MODIFICATION.
     */
    LOCAL_MODIFICATION,
    
    /**
     * NATIONAL_MODIFICATION
     */
    NATIONAL_MODIFICATION,
    
    /**
     * SYSTEM_EVENT.
     */
    SYSTEM_EVENT;
}
