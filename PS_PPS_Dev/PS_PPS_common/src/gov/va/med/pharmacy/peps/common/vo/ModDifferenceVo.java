/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;


/**
 * Represent a difference between two values of a field within a ValueObject. This class is also used to track field-level
 * modification requests for national manager review. In this mode of operation, instances of this class would be contained
 * within RequestVo instances.
 * 
 * @see RequestVo
 */
public class ModDifferenceVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String id; // This is the EPL_ID for a mod difference instance.

    // Modification change details related information.
    private Difference difference; // Contains specifics on the change made: the field key and old/new value.
    private String modificationReason; // The reason for the modification.

    // (For non-editable VADFs) Confirmation that the change is desired to be made.
    private boolean requestToModifyValue = true;

    // (For non-editable VADFs) Indicates that the field should be made editable by the nat'l manager.
    private boolean requestToMakeEditable = false;
    private boolean acceptChange = true; // (For editable VADFs) Confirmation that the change is desired.

    // Modification request life cycle related attributes.
    private UserVo requestor; // Denotes who requested this change.
    private UserVo reviewer; // Denotes who the last reviewer was.
    private RequestItemStatus requestItemStatus;
    private String reviewerNote;
    private String comments;

    private String siteName;
    private String requestRejectReason;
    private String rejectReasonText;

    // Used during multi-edits
    private boolean changeable = true; // true if one of the items has does not have the same value as the new value
    private boolean editable = true; // true if the VA DF is editable

    // Used during merge of data
    private boolean conflict = false; // true if there is a conflict between the old and new values

    /**
     * Set the FieldKey, old value, and new value.
     * 
     * @param difference Difference with field key, old value, new value
     * @param requestor the requestor information
     * @param modificationReason String reason for the modification
     * @param requestToModifyValue boolean true if the user wants to modify the data field value
     * @param requestToMakeEditable boolean true if the user wants to make the data field editable
     * @param acceptChange boolean true if the user wants to save the modifications to the item
     * @param reviewer String for reviewer name
     * @param requestItemStatus RequestItemStatus
     * @param reviewerNote String for the review note
     * @param comments String
     */
    public ModDifferenceVo(Difference difference, UserVo requestor, String modificationReason, boolean requestToModifyValue,
        boolean requestToMakeEditable, boolean acceptChange, UserVo reviewer, RequestItemStatus requestItemStatus,
        String reviewerNote, String comments) {

        setDifference(difference);
        setRequestor(requestor);
        setModificationReason(modificationReason);
        setRequestToModifyValue(requestToModifyValue);
        setRequestToMakeEditable(requestToMakeEditable);
        setAcceptChange(acceptChange);
        setReviewer(reviewer);
        setModRequestItemStatus(requestItemStatus);
        setReviewerNote(reviewerNote);
        setComments(comments);
    }

    /**
     * Empty constructor
     */
    public ModDifferenceVo() {

    }

    /**
     * Called when updating a VO from request details, this method properly updates the new VA data field value by setting
     * the editable flag and value according to the request itself.
     */
    public void updateNewDataFieldValueForRequest() {
        DataField newDf = (DataField) getDifference().getNewValue();

        if (getRequestToMakeEditable()) {
            newDf.setEditable(true);
        } else {

            // If you didn't request to change the status, we'll use whatever national has
            newDf.setEditable(newDf.isEditable());
        }

        if (!getRequestToModifyValue()) {
            newDf.selectValue(((DataField) getDifference().getOldValue()).getValue());
        }
    }

    /**
     * getDifference
     * 
     * @return difference property
     */
    public Difference getDifference() {
        return difference;
    }

    /**
     * setDifference
     * 
     * @param difference difference property
     */
    public void setDifference(Difference difference) {
        this.difference = difference;
    }

    /**
     * getRequestor
     * 
     * @return UserVo
     */
    public UserVo getRequestor() {
        return requestor;
    }

    /**
     * setRequestor
     * 
     * @param requestor UserVo
     */
    public void setRequestor(UserVo requestor) {
        this.requestor = requestor;
    }

    /**
     * getModificationReason
     * 
     * @return modificationReason property
     */
    public String getModificationReason() {
        return modificationReason;
    }

    /**
     * setModificationReason
     * 
     * @param modificationReason reason property
     */
    public void setModificationReason(String modificationReason) {
        this.modificationReason = trimToEmpty(modificationReason);
    }

    /**
     * getRequestToModifyValue
     * 
     * @return changeValue property
     */
    public boolean getRequestToModifyValue() {
        return requestToModifyValue;
    }

    /**
     * setRequestToModifyValue
     * 
     * @param requestToModifyValue requestToModifyValue property
     */
    public void setRequestToModifyValue(boolean requestToModifyValue) {
        this.requestToModifyValue = requestToModifyValue;
    }

    /**
     * getRequestToMakeEditable
     * 
     * @return changeEditable property
     */
    public boolean getRequestToMakeEditable() {
        return requestToMakeEditable;
    }

    /**
     * setRequestToMakeEditable
     * 
     * @param requestToMakeEditable requestToMakeEditable property
     */
    public void setRequestToMakeEditable(boolean requestToMakeEditable) {
        this.requestToMakeEditable = requestToMakeEditable;
    }

    /**
     * isAcceptChange
     * 
     * @return acceptChange property
     */
    public boolean isAcceptChange() {
        return acceptChange;
    }

    /**
     * setAcceptChange
     * 
     * @param acceptChange acceptChanges property
     */
    public void setAcceptChange(boolean acceptChange) {
        this.acceptChange = acceptChange;
    }

    /**
     * getReviewer
     * 
     * @return String
     */
    public UserVo getReviewer() {
        return reviewer;
    }

    /**
     * setReviewer
     * 
     * @param reviewer UserVo
     */
    public void setReviewer(UserVo reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * getModRequestItemStatus
     * 
     * @return RequestItemStatus
     */
    public RequestItemStatus getModRequestItemStatus() {
        return requestItemStatus;
    }

    /**
     * setModRequestItemStatus
     * 
     * @param status RequestItemStatus
     */
    public void setModRequestItemStatus(RequestItemStatus status) {
        this.requestItemStatus = status;
    }

    /**
     * getReviewerNote
     * 
     * @return String
     */
    public String getReviewerNote() {
        return reviewerNote;
    }

    /**
     * setReviewerNote
     * 
     * @param reviewerNote String
     */
    public void setReviewerNote(String reviewerNote) {
        this.reviewerNote = trimToEmpty(reviewerNote);
    }

    /**
     * getComments
     * 
     * @return String
     */
    public String getComments() {

        return comments;
    }

    /**
     * setComments
     * 
     * @param comments property
     */
    public void setComments(String comments) {
        this.comments = trimToEmpty(comments);
    }

    /**
     * getSiteName for the ModDifferenceVo.
     * 
     * @return siteName property
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * setSiteName for the ModDifferenceVo.
     * 
     * @param siteName siteName property
     */
    public void setSiteName(String siteName) {
        this.siteName = trimToEmpty(siteName);
    }

    /**
     * getId for ModDifferenceVo
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for ModDifferenceVo
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getRequestRejectReason for ModDifferenceVo
     * 
     * @return requestRejectReason property
     */
    public String getRequestRejectReason() {
        return requestRejectReason;
    }

    /**
     * setRequestRejectReason for ModDifferenceVo
     * 
     * @param requestRejectReason requestRejectReason property
     */
    public void setRequestRejectReason(String requestRejectReason) {
        this.requestRejectReason = trimToEmpty(requestRejectReason);
    }

    /**
     * getRejectReasonText
     * 
     * @return rejectReasonText property
     */
    public String getRejectReasonText() {
        return rejectReasonText;
    }

    /**
     * setRejectReasonText
     * 
     * @param rejectReasonText rejectReasonText property
     */
    public void setRejectReasonText(String rejectReasonText) {
        this.rejectReasonText = trimToEmpty(rejectReasonText);
    }

    /**
     * isChangeable
     * 
     * @return changeable property
     */
    public boolean isChangeable() {
        return changeable;
    }

    /**
     * setChangeable
     * 
     * @param changeable changeable property
     */
    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    /**
     * isEditable
     * 
     * @return editable property
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * setEditable
     * 
     * @param editable editable property
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * hasConflict
     * 
     * @return conflict property
     */
    public boolean hasConflict() {
        return conflict;
    }

    /**
     * setConflict
     * 
     * @param conflict conflict property
     */
    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }
}
