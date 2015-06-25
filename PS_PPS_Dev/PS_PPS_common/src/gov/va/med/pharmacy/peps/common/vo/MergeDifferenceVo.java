/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.diff.MergeDifference;


/**
 * Represent a difference between two values of a field within a ValueObject. This class is also used to track field-level
 * modification requests for national manager review. In this mode of operation, instances of this class would be contained
 * within MergeVo instances.
 * 
 * 
 * @see MergeVo
 */
public class MergeDifferenceVo extends ModDifferenceVo {
    private static final long serialVersionUID = 1L;

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
     * @param reqeustItemStatus RequestItemStatus
     * @param reviewerNote String for the review note
     * @param comments String
     */
    public MergeDifferenceVo(Difference difference, UserVo requestor, String modificationReason,
        boolean requestToModifyValue, boolean requestToMakeEditable, boolean acceptChange, UserVo reviewer,
        RequestItemStatus reqeustItemStatus, String reviewerNote, String comments) {

        super(difference, requestor, modificationReason, requestToModifyValue, requestToMakeEditable, acceptChange,
            reviewer, reqeustItemStatus, reviewerNote, comments);
    }

    /**
     * Empty constructor
     */
    public MergeDifferenceVo() {
        super();
    }

    /**
     * getDifference
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo#getDifference()
     * @return difference MergeDifference
     */
    public MergeDifference getDifference() {

        return (MergeDifference) super.getDifference();

    }

// Not needed in this method since it is in the underlying class.
//    /**
//     * 
//     * Description
//     *
//     * @param difference merge difference
//     */
//    public void setDifference(MergeDifference difference) {
//
//        super.setDifference(difference);
//    }

    /**
     * 
     * Returns the ModDifferenceVo
     *
     * @return modDifferenceVo ModDifferenceVo
     */
    public ModDifferenceVo getModDifferenceVo() {

        Difference difference =
                new Difference(this.getDifference().getFieldKey(), this.getDifference().getModifiersValue(), this
                    .getDifference().getNewValue());
        ModDifferenceVo modDifferenceVo =
                new ModDifferenceVo(difference, this.getRequestor(),
                    this.getModificationReason(), this.getRequestToModifyValue(),
                    this.getRequestToMakeEditable(), this.isAcceptChange(),
                    this.getReviewer(), this.getModRequestItemStatus(), this.getReviewerNote(),
                    this.getComments());

        return modDifferenceVo;

    }

}
