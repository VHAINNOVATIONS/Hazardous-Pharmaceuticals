/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Status of a request for an item
 */
public enum RequestState {

    /** PENDING_FIRST_REVIEW */
    PENDING_FIRST_REVIEW,

    /** PENDING_SECOND_REVIEW */
    PENDING_SECOND_REVIEW,

    /** COMPLETED */
    COMPLETED;

    /**
     * isPendingFirstReview
     * 
     * @return boolean true if this RequestItemStatus is PENDING
     */
    public boolean isPendingFirstReview() {
        return PENDING_FIRST_REVIEW.equals(this);
    }

    /**
     * isPendingSecondReview
     * 
     * @return boolean true if this RequestRequestStatus is PENDING_SECOND_REVIEW
     */
    public boolean isPendingSecondReview() {
        return PENDING_SECOND_REVIEW.equals(this);
    }

    /**
     * isCompleted
     * 
     * @return boolean true if this RequestRequestStatus is COMPLETED
     */
    public boolean isCompleted() {
        return COMPLETED.equals(this);
    }
}
