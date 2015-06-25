/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;


/**
 * Data representing a request. This RequestVo wraps around the ProductVo, NdcVo, and Collection of ModDifferenceVo
 * 
 */
public class RequestVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String id; // This is the EPL_ID for a request instance.

    private String itemId; // The EPL_ID for the item (NOI, Product, or NDC).
    private EntityType entityType; // The item's type.

    private RequestType requestType; // One of ADD or MOD request type.
    private RequestState requestState; // One of PENDING_FIRST_REVIEW, PENDING_SECOND_REVIEW, or COMPLETED.

    private UserVo lastReviewer;

    private UserVo newItemRequestor; // Applicable for adding new item, UserVo has also site information.
    private RequestItemStatus newItemRequestStatus; // One of PENDING, APPROVED, or REJECTED.

    private boolean underReview; // Indicates if the request instance is flagged as under review.
    private boolean markedForPepsSecondReview; // Is this request marked for a PEPS second review?

    private String siteName; // Indicates which site the request was initiated at

    private String rejectionReasonText;

    private String note;
    private String newNote; //this field is not saved to the db, only holds the current comment

    private RequestRejectionReason requestRejectionReason;

    private Collection<ModDifferenceVo> requestDetails = new ArrayList<ModDifferenceVo>(0);

    private String psrName;

    private Map<ManagedItemVo, RequestVo> dependentRequests = new HashMap<ManagedItemVo, RequestVo>();

    /**
     * Empty Constructor
     * 
     */
    public RequestVo() {

        newNote = "";

    }

    /**
     * Constructor
     * 
     * @param requestDetails for mod/add
     */
    public RequestVo(Collection<ModDifferenceVo> requestDetails) {

        this.requestDetails = requestDetails;
        newNote = "";
    }

    /**
     * constructor
     * 
     * @param itemId for the Product/NDC/OI item
     * @param reqItemType Product/NDC/OI
     * @param reqType Add/Mod
     * @param reqState Pending 1st Approval,Pending 2nd Approval, Completed
     * @param lastReviewer the last person to review
     * @param newItemRequestor only present for Add request
     * @param newItemReqState only present for Add Request
     * @param isUnderReview boolean value
     * @param markedForPepsSecondReview boolean value
     * @param requestDetails the collection of Details objects
     */
    public RequestVo(String itemId, EntityType reqItemType, RequestType reqType, RequestState reqState, UserVo lastReviewer,
                     UserVo newItemRequestor, RequestItemStatus newItemReqState, boolean isUnderReview,
                     boolean markedForPepsSecondReview, Collection<ModDifferenceVo> requestDetails) {

        this.itemId = itemId;
        this.entityType = reqItemType;
        this.requestType = reqType;
        this.newItemRequestStatus = newItemReqState;
        this.lastReviewer = lastReviewer;
        this.newItemRequestor = newItemRequestor;
        this.newNote = "";

        if (newItemRequestor == null) {
            this.siteName = lastReviewer.getLocation();
        } else {
            this.siteName = newItemRequestor.getLocation();
        }

        this.requestState = reqState;
        this.underReview = isUnderReview;
        this.markedForPepsSecondReview = markedForPepsSecondReview;
        setRequestDetails(requestDetails);
    }

    /**
     * This request is under review, which means that the reviewer has not completed accepting or rejecting all changes
     * within the request, and will return to it later to finish it.
     * 
     * @return boolean True if this request is under review, false otherwise.
     */
    public boolean isUnderReview() {

        return underReview;
    }

    /**
     * This request is under review, which means that the reviewer has not completed accepting or rejecting all changes
     * within the request, and will return to it later to finish it.
     * 
     * @param underReview flag for indicating a request is under review
     */
    public void setUnderReview(boolean underReview) {

        this.underReview = underReview;
    }

    /**
     * getters and setters
     */

    /**
     * getId
     * @return id
     */
    public String getId() {

        return id;
    }

    /**
     * setId
     * @param id for the request
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * getItemId
     * 
     * @return itemId
     */
    public String getItemId() {

        return itemId;
    }

    /**
     * setItemId
     * 
     * @param itemId id of item the request for
     */
    public void setItemId(String itemId) {

        this.itemId = itemId;
    }

    /**
     * True if the {@link #requestType} is a {@link RequestType#ADDITION}.
     * 
     * @return boolean
     */
    public boolean isAddition() {

        return requestType.isAddition();
    }

    /**
     * True if the {@link #requestType} is a {@link RequestType#MODIFICATION}.
     * 
     * @return boolean
     */
    public boolean isModification() {

        return requestType.isModification();
    }

    /**
     * True if the {@link #requestType} is a {@link RequestType#PROBLEM_REPORT}.
     * 
     * @return boolean
     */
    public boolean isProblemReport() {

        return requestType.isProblemReport();
    }

    /**
     * getRequestType
     * @return reqType
     */
    public RequestType getRequestType() {

        return requestType;
    }

    /**
     * setRequestType
     * @param reqType type of request(add or modify)
     */
    public void setRequestType(RequestType reqType) {

        this.requestType = reqType;
    }

    /**
     * getNewItemRequestStatus
     * @return newItemRequestStatus
     */
    public RequestItemStatus getNewItemRequestStatus() {

        return newItemRequestStatus;
    }

    /**
     * True if {@link RequestItemStatus#APPROVED}, else false.
     * 
     * @return boolean
     */
    public boolean isApproved() {

        return getNewItemRequestStatus().isApproved();
    }

    /**
     * True if {@link RequestItemStatus#PENDING}, else false.
     * 
     * @return boolean
     */
    public boolean isPending() {

        return getNewItemRequestStatus().isPending();
    }

    /**
     * True if {@link RequestItemStatus#REJECTED}, else false.
     * 
     * @return boolean
     */
    public boolean isRejected() {

        return getNewItemRequestStatus().isRejected();
    }

    /**
     * setNewItemRequestStatus
     * @param newItemRequestStatus status of the item the request is for
     */
    public void setNewItemRequestStatus(RequestItemStatus newItemRequestStatus) {

        this.newItemRequestStatus = newItemRequestStatus;
    }

    /**
     * True if {@link RequestState#COMPLETED} and {@link RequestType#ADDITION} and {@link RequestItemStatus#REJECTED}, else
     * false.
     * 
     * @return boolean
     */
    public boolean isFinalRejectionOfAddRequest() {

        return getRequestState().isCompleted() && getRequestType().isAddition() && getNewItemRequestStatus().isRejected();
    }

    /**
     * True if {@link RequestState#COMPLETED}, else false.
     * 
     * @return boolean
     */
    public boolean isCompleted() {

        return getRequestState().isCompleted();
    }

    /**
     * True if {@link RequestState#PENDING_FIRST_REVIEW}, else false.
     * 
     * @return boolean
     */
    public boolean isPendingFirstReview() {

        return getRequestState().isPendingFirstReview();
    }

    /**
     * True if {@link RequestState#PENDING_SECOND_REVIEW}, else false.
     * 
     * @return boolean
     */
    public boolean isPendingSecondReview() {

        return getRequestState().isPendingSecondReview();
    }

    /**
     * getNewItemRequestor
     * @return newItemRequestor
     */
    public UserVo getNewItemRequestor() {

        return newItemRequestor;
    }

    /**
     * setNewItemRequestor
     * @param newItemRequestor user making the request
     */
    public void setNewItemRequestor(UserVo newItemRequestor) {

        this.newItemRequestor = newItemRequestor;
    }

    /**
     * getLastReviewer
     * @return lastReviewer
     */
    public UserVo getLastReviewer() {

        return lastReviewer;
    }

    /**
     * setLastReviewer
     * @param lastReviewer user who last reviewed the request
     */
    public void setLastReviewer(UserVo lastReviewer) {

        this.lastReviewer = lastReviewer;
    }

    /**
     * getEntityType
     * @return entityType
     */
    public EntityType getEntityType() {

        return entityType;
    }

    /**
     * setEntityType
     * @param reqItemType the type of the item for which the request is being made
     */
    public void setEntityType(EntityType reqItemType) {

        this.entityType = reqItemType;
    }

    /**
     * getRequestDetails
     * @return requestDetails
     */
    public Collection<ModDifferenceVo> getRequestDetails() {

        if (requestDetails == null) {
            this.requestDetails = new ArrayList<ModDifferenceVo>();
        }

        return requestDetails;
    }

    /**
     * setRequestDetails
     * @param requestDetails modDifferences for this request
     */
    public void setRequestDetails(Collection<ModDifferenceVo> requestDetails) {

        this.requestDetails = new ArrayList<ModDifferenceVo>();

        if (requestDetails != null && !requestDetails.isEmpty()) {
            this.requestDetails.addAll(requestDetails);
        }
    }

    /**
     * getSiteName
     * @return siteName
     */
    public String getSiteName() {

        return siteName;
    }

    /**
     * setSiteName
     * @param siteName the site where the request was submitted from
     */
    public void setSiteName(String siteName) {

        this.siteName = trimToEmpty(siteName);
    }

    /**
     * getRejectReason
     * @return rejectionReasonText
     */
    public String getRejectionReasonText() {

        return rejectionReasonText;
    }

    /**
     * setRejectReason
     * @param rejectionReasonText reason the request was rejected
     */
    public void setRejectionReasonText(String rejectionReasonText) {

        this.rejectionReasonText = trimToEmpty(rejectionReasonText);
    }

    /**
     * getNote
     * @return note
     */
    public String getNote() {

        return note;
    }

    /**
     * setNote
     * @param note additional comments made for this request
     */
    public void setNote(String note) {

        this.note = trimToEmpty(note);
    }

    /**
     * Checks if this request has any field-level request details still in the 'pending' state.
     * 
     * @return boolean true if at least one field-level request detail is in the 'pending' state.
     */
    public boolean hasPendingDetails() {

        // Empty lists have no pending details.
        if (requestDetails == null || requestDetails.size() <= 0) {
            return false;
        }

        for (ModDifferenceVo detail : requestDetails) {
            if (detail.getModRequestItemStatus().isPending()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if one of the {@link #getRequestDetails()} is marked as having a conflict by
     * {@link ModDifferenceVo#hasConflict()}.
     * 
     * @return boolean.
     */
    public boolean hasConflicts() {

        for (ModDifferenceVo modDifference : getRequestDetails()) {
            if (modDifference.hasConflict()) {
                return true;
            }
        }

        return false;
    }

    /**
     * The {@link RequestVo} cannot have any pending details and all rejected details must have a reason.
     * <p>
     * If the {@link RequestVo} passes these rules, copy the reject reason from the comments.
     * 
     * @throws ValidationException if {@link RequestVo} did not follow rules above.
     */
    public void checkFields() throws ValidationException {

        // If any field-level request details are still "pending", then report an error to the user
        // as it is not ready to process via the request management state machine.
        if (hasPendingDetails()) {
            throw new BusinessRuleException(BusinessRuleException.NO_PENDING_REQUESTS);
        }

        // If any field-level request details are "rejected" with no reason provided, then report an error to the
        // user as it is not ready to process via the request management state machine.
        if (hasMissingRejectReasons()) {
            throw new BusinessRuleException(BusinessRuleException.REJECT_REASON_REQUIRED);
        }

        // All is well so copy the reject reason from the comments
        for (ModDifferenceVo mods : requestDetails) {

            if (RequestItemStatus.REJECTED.equals(mods.getModRequestItemStatus())) {
                mods.setRejectReasonText(mods.getComments());
            } else {

                // if it wasn't rejected, then clear out any reject reason text
                // that might have been entered on a previous review
                mods.setRejectReasonText(null);
            }
        }
    }

    /**
     * checks if the item or all of its details have been rejected on the first review.
     * 
     * @param twoReviewItem True if this item requires a second review.
     * @return boolean is rejected on the first approval
     */
    public boolean isRejectedOnFirstApproval(boolean twoReviewItem) {

        boolean allDetailsRejected = true;

        // is this the first review?
        if (RequestState.PENDING_FIRST_REVIEW.equals(requestState) && twoReviewItem) {

            // if the item has been rejected then
            if (RequestItemStatus.REJECTED.equals(newItemRequestStatus) && RequestType.ADDITION.equals(requestType)) {

                // were're completed
                return true;
            }

            // check for mod differences
            if (requestDetails != null && requestDetails.size() > 0) {

                // check if all mod differences have been rejected
                for (ModDifferenceVo detail : requestDetails) {
                    if (!detail.getModRequestItemStatus().isRejected()) {
                        allDetailsRejected = false;
                    }
                }

                return allDetailsRejected;
            } else {

                return false;
            }

        } else {

            // no, can't quite complete the state yet
            return false;
        }
    }

    /**
     * Checks if this request has any request or field-level request details in the 'rejected' state that do not have a
     * reason specified for them.
     * 
     * @return boolean true if at least one field-level request detail is in the 'rejected' state with a missing reason.
     */
    public boolean hasMissingRejectReasons() {

        if (RequestType.ADDITION.equals(requestType) && RequestItemStatus.REJECTED.equals(newItemRequestStatus)) {
            if (requestRejectionReason == null) {
                return true;
            }
        }

        // Empty lists have no details to worry about here.
        if (requestDetails == null || requestDetails.size() <= 0) {
            return false;
        }

        for (ModDifferenceVo detail : requestDetails) {
            if (detail.getModRequestItemStatus().isRejected()
                && ((detail.getComments() == null) || (detail.getComments().trim().length() <= 0))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adjust newly-discovered data-field differences (assumed made by the current PLM) to account for the fact that 
     * data-fields that were previously 'approved' by other PLMs are shown to the current PLM as they would appear if 
     * the request completed.  If the current PLM has now made a change on top of those previous changes to the same 
     * data-field, this method ensures that the old-value for the new mod-differences is the original value of the item's
     * data-field, not the 'approved' value if the request completes.  If this operation was not performed, and a follow-on
     * PLM then rejected this field-mod, then the previous 'approved' value would be restored (incorrectly), rather than 
     * the original value for the item (the correct situation if a field-mod is rejected).
     * 
     * @param newDifferences Collection of {@link ModDifferenceVo} between the old and new ManagedItemVo
     */
    public void adjustNewModDifferences(Collection<ModDifferenceVo> newDifferences) {

        if (newDifferences == null || newDifferences.size() <= 0) {
            return;
        }

        // Per CR 1959 "Show Updated Values During Modification Review", we applied previously 'approved' field mod 
        //   requests to the item that the current PLM is viewing now.  If the current PLM has now made another mod those 
        //   same fields as previously mod-requested, then we need to ensure that the old value is set as per the original 
        //   field-mod (reflective of the original field state, not of the state it 'might be' if the 'approved' field value 
        //   is later applied via a completed request).  

        // Build up a map of data field FieldKey's to existing field-level mod requests.

        Map<FieldKey, ModDifferenceVo> mapFieldsToExistingModReqs = new HashMap<FieldKey, ModDifferenceVo>();

        for (ModDifferenceVo modDiffExisting : getRequestDetails()) {

            // Note: There could be more than one existing field-level mod request for a given field, but that's OK, as
            //   the old-value (which is all we care about for this method) should be the same for all of them.
            mapFieldsToExistingModReqs.put(modDiffExisting.getDifference().getFieldKey(), modDiffExisting);
        }

        // Scan for a match of a new data-field-mod to an previous change request for the same field.
        if (mapFieldsToExistingModReqs.size() > 0) {
            for (ModDifferenceVo modDiffNew : newDifferences) {

                ModDifferenceVo modDiffExisting = mapFieldsToExistingModReqs.get(modDiffNew.getDifference().getFieldKey());

                // If we have a match, set the old-value of the new difference based on the previous request setting.
                if (modDiffExisting != null) {
                    modDiffNew.getDifference().setOldValue(modDiffExisting.getDifference().getOldValue());
                }
            }
        }
    }

    /**
     * Scan the differences for conflicts with the {@link RequestVo}. If there are conflicts, mark each by calling
     * {@link ModDifferenceVo#setConflict(boolean)} with a true boolean value.
     * <p>
     * A conflict is defined as a {@link ModDifferenceVo} in each Collection for the same {@link FieldKey} that are both
     * approved or have no RequestItemStatus (and therefore assumed as approved).
     * <p>
     * Checks for conflicts within the {@link RequestVo#getRequestDetails()} and between the
     * {@link RequestVo#getRequestDetails()} and the given Collection of {@link ModDifferenceVo}.
     * <p>
     * If the method returns true, the result is the {@link RequestVo} and the given Collection of {@link ModDifferenceVo}
     * will have {@link ModDifferenceVo#hasConflict()} true values.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * @return boolean true if there were conflicts
     */
    public boolean checkForConflicts(Collection<ModDifferenceVo> differences) {

        boolean hasConflicts = false;

        // A RequestVo could have conflicts within itself, so create a new Collection with both lists of ModDifferenceVo
        Collection<ModDifferenceVo> allDifferences = new ArrayList<ModDifferenceVo>();
        allDifferences.addAll(getRequestDetails());
        allDifferences.addAll(differences);

        // Reset all conflict flags in case this method was called before on this RequestVo and/or with these differences
        for (ModDifferenceVo modDifference : allDifferences) {
            modDifference.setConflict(false);
        }

        Map<FieldKey, ModDifferenceVo> check = new HashMap<FieldKey, ModDifferenceVo>();

        // Start by putting all the request details from the RequestVo in the check Map
        for (ModDifferenceVo modDifference : getRequestDetails()) {
            check.put(modDifference.getDifference().getFieldKey(), modDifference);
        }

        // Find duplicates when the check Map returns a ModDifferenceVo during the put() call from allDifferences
        for (ModDifferenceVo modDifference : allDifferences) {
            ModDifferenceVo duplicate = check.put(modDifference.getDifference().getFieldKey(), modDifference);

            // Ignore a ModDifferenceVo if the two ModDifferenceVo are the same instance (by identity check)
            if (duplicate != null && duplicate != modDifference) {

                // If both RequestItemStatus are null or approved, then there is a conflict
                RequestItemStatus duplicateStatus =
                        duplicate.getModRequestItemStatus() == null ? RequestItemStatus.APPROVED : duplicate
                                .getModRequestItemStatus();
                RequestItemStatus modDifferenceStatus =
                        modDifference.getModRequestItemStatus() == null ? RequestItemStatus.APPROVED : modDifference
                                .getModRequestItemStatus();

                if (duplicateStatus.isApproved() && modDifferenceStatus.isApproved()) {
                    duplicate.setConflict(true);
                    modDifference.setConflict(true);
                    hasConflicts = true;
                }
            }
        }

        return hasConflicts;
    }

    /**
     * getRequestRejectReason
     * @return requestRejectionReason property
     */
    public RequestRejectionReason getRequestRejectionReason() {

        return requestRejectionReason;
    }

    /**
     * setRequestRejectReason
     * @param requestRejectionReason requestRejectionReason property
     */
    public void setRequestRejectionReason(RequestRejectionReason requestRejectionReason) {

        this.requestRejectionReason = requestRejectionReason;
    }

    /**
     * isMarkedForPepsSecondReview
     * @return markedForPepsSecondReview property
     */
    public boolean isMarkedForPepsSecondReview() {

        return markedForPepsSecondReview;
    }

    /**
     * setMarkedForPepsSecondReview
     * @param markedForPepsSecondReview markedForPepsSecondReview property
     */
    public void setMarkedForPepsSecondReview(boolean markedForPepsSecondReview) {

        this.markedForPepsSecondReview = markedForPepsSecondReview;
    }

    /**
     * getRequestState
     * @return requestState property
     */
    public RequestState getRequestState() {

        return requestState;
    }

    /**
     * setRequestState
     * @param requestState requestState property
     */
    public void setRequestState(RequestState requestState) {

        this.requestState = requestState;
    }

    /**
     * getPsrName
     * @return psrName property
     */
    public String getPsrName() {

        return psrName;
    }

    /**
     * setPsrName
     * @param psrName psrName property
     */
    public void setPsrName(String psrName) {

        this.psrName = trimToEmpty(psrName);
    }

    /**
     * getNewNote
     * 
     * @return newNote property
     */
    public String getNewNote() {

        return newNote;
    }

    /**
     * setNewNote
     * 
     * @param newNote property
     */
    public void setNewNote(String newNote) {

        this.newNote = newNote;
    }

    /**
     * getDependentRequests
     * @return the dependentRequests
     */
    public Map<ManagedItemVo, RequestVo> getDependentRequests() {

        return dependentRequests;
    }

    /**
     * setDependentRequests
     * @param dependentRequests the dependentRequests to set
     */
    public void setDependentRequests(Map<ManagedItemVo, RequestVo> dependentRequests) {

        this.dependentRequests = dependentRequests;
    }

}
