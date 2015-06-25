/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;


/**
 * Data representing a merge request. This MergeVo wraps around a Collection of MergeDifferenceVo
 * 
 */
public class MergeVo extends ValueObject {

    /** FIELDS_TO_IGNOR_FOR_CHANGE_DETECTION */
    public static final String[] FIELDS_TO_IGNOR_FOR_CHANGE_DETECTION =
        new String[] { "requestDetails", "note", "rejectReason" };

    private static final long serialVersionUID = 1L;
    private long revisionNumber;

    private String itemId; // The EPL_ID for the item (NOI, Product, or NDC).
    private EntityType entityType; // The item's type.

    // private T requestedItem;
    private Collection<MergeDifferenceVo> mergeRequestDetails = new ArrayList<MergeDifferenceVo>(0);

    /**
     * Empty Constructor
     * 
     */
    public MergeVo() {

    }

    /**
     * Constructor
     * 
     * @param mergeRequestDetails for mod/add
     */
    public MergeVo(Collection<MergeDifferenceVo> mergeRequestDetails) {

        this.mergeRequestDetails = mergeRequestDetails;
    }

    /**
     * Constructor
     * 
     * @param mergeRequestDetails collection with merge differences
     * @param revisionNumber product revision number
     */
    public MergeVo(Collection<MergeDifferenceVo> mergeRequestDetails, long revisionNumber) {

        this(mergeRequestDetails);

        this.revisionNumber = revisionNumber;
    }

    /**
     * constructor
     * 
     * @param revisionNumber The revision Number
     * @param itemId for the Product/NDC/OI item
     * @param reqItemType Product/NDC/OI
     * @param mergeRequestDetails the collection of Details objects
     */
    public MergeVo(long revisionNumber, String itemId, EntityType reqItemType,
        Collection<MergeDifferenceVo> mergeRequestDetails) {

        this.revisionNumber = revisionNumber;
        this.itemId = itemId;
        this.entityType = reqItemType;
        this.mergeRequestDetails = mergeRequestDetails;

    }

    /**
     * getRevisionNumber
     * 
     * @return versionId
     */
    public long getRevisionNumber() {

        return revisionNumber;
    }

    /**
     * setRevisionNumber
     * 
     * @param revisionNumber for the request
     */
    public void setRevisionNumber(long revisionNumber) {

        this.revisionNumber = revisionNumber;
    }

    /**
     * getItemId for MergeVo.
     * 
     * @return itemId
     */
    public String getItemId() {

        return itemId;
    }

    /**
     * setItemId for MergeVo.
     * 
     * @param itemId id of item the request for
     */
    public void setItemId(String itemId) {

        this.itemId = itemId;
    }

    /**
     * getEntityType
     * 
     * @return entityType
     */
    public EntityType getEntityType() {

        return entityType;
    }

    /**
     * setEntityType
     * 
     * @param reqItemType the type of the item for which the request is being made
     */
    public void setEntityType(EntityType reqItemType) {

        this.entityType = reqItemType;
    }

    /**
     * getMergeRequestDetails
     * 
     * @return requestDetails
     */
    public Collection<MergeDifferenceVo> getMergeRequestDetails() {

        return mergeRequestDetails;
    }

    /**
     * setMergeRequestDetails
     * 
     * @param mergeRequestDetails modDifferences for this request
     */
    public void setMergeRequestDetails(Collection<MergeDifferenceVo> mergeRequestDetails) {

        this.mergeRequestDetails = new ArrayList<MergeDifferenceVo>();

        if (mergeRequestDetails != null && !mergeRequestDetails.isEmpty()) {
            this.mergeRequestDetails.addAll(mergeRequestDetails);
        }
    }

    /**
     * 
     * Returns the collection of ModDifferenceVos hidden inside of the MergeDifferenceVos
     *
     * @return mod difference vos ModDifferenceVo
     */
    public Collection<ModDifferenceVo> getModDifferences() {

        Collection<ModDifferenceVo> differences = new ArrayList<ModDifferenceVo>();

        if (!CollectionUtils.isEmpty(getMergeRequestDetails())) {
            for (MergeDifferenceVo mergeDiff : getMergeRequestDetails()) {
                differences.add(mergeDiff.getModDifferenceVo());
            }
        }

        return differences;
    }

}
