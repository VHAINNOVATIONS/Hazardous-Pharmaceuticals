/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.diff.IgnoreDifference;


/**
 * This class and all sub-classes of it are managed by PEPS, undergoing a create/update life cycle controlled by PEPS system
 * actors, with final add/modify approvals controlled by national PEPS managers.
 */
public abstract class ManagedItemVo extends ValueObject implements Selectable {

    /** MANAGED_ITEM_COMPARATOR */
    public static final ManagedItemComparator MANAGED_ITEM_COMPARATOR = new ManagedItemComparator();

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagedItemVo.class);
    private static final long serialVersionUID = 1L;
    private static final String PREV = "previous";
    private String id;
    private ItemStatus itemStatus;
    private boolean partialSave;

    private RequestItemStatus requestItemStatus;

    private boolean localUse;
    @IgnoreDifference
    private boolean previouslyMarkedForLocalUse;
    private DataFields<DataField> vaDataFields = new DataFields<DataField>();
    @IgnoreDifference
    private long revisionNumber;
    @IgnoreDifference
    private RequestRejectionReason requestRejectionReason;
    @IgnoreDifference
    private String rejectionReasonText;
    @IgnoreDifference
    private boolean isBeingReplacedByNational;
    private String gcnSequenceNumber;
    private LanguageChoice spanishText = LanguageChoice.ENGLISH;
    private String psrName;
    private boolean markedForPepsSecondReview;

    // System Data field
    @IgnoreDifference
    private Date inactivationDate;

    @IgnoreDifference
    private Collection<ItemAuditHistoryVo> itemAuditHistory = new ArrayList<ItemAuditHistoryVo>();

    @IgnoreDifference
    private ItemAuditHistoryVo newAuditHistory;

    /**
     * Default constructor sets ItemStatus to active and RequestItemStatus to Pending
     * 
     */
    public ManagedItemVo() {

        itemStatus = ItemStatus.ACTIVE;
        requestItemStatus = RequestItemStatus.PENDING;
    }

    /**
     * Factory method to create a new {@link ManagedItemVo} from the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType} of {@link ManagedItemVo} to create
     * @return {@link ManagedItemVo}
     */
    public static ManagedItemVo newInstance(EntityType entityType) {
        ManagedItemVo managedItem;

        switch (entityType) {
            case ADMINISTRATION_SCHEDULE:
                managedItem = new AdministrationScheduleVo();
                break;
            case DISPENSE_UNIT:
                managedItem = new DispenseUnitVo();
                break;
            case DOSAGE_FORM:
                managedItem = new DosageFormVo();
                break;
            case DOSE_UNIT:
                managedItem = new DoseUnitVo();
                break;
            case DRUG_CLASS:
                managedItem = new DrugClassVo();
                break;
            case DRUG_TEXT:
                managedItem = new DrugTextVo();
                break;
            case DRUG_UNIT:
                managedItem = new DrugUnitVo();
                break;
            case GENERIC_NAME:
                managedItem = new GenericNameVo();
                break;
            case INGREDIENT:
                managedItem = new IngredientVo();
                break;
            case MANUFACTURER:
                managedItem = new ManufacturerVo();
                break;
            case MEDICATION_INSTRUCTION:
                managedItem = new MedicationInstructionVo();
                break;
            case STANDARD_MED_ROUTE:
                managedItem = new StandardMedRouteVo();
                break;
            case NDC:
                managedItem = new NdcVo();
                break;
            case ORDER_UNIT:
                managedItem = new OrderUnitVo();
                break;
            case SPECIAL_HANDLING:
                managedItem = new SpecialHandlingVo();
                break;
            case ORDERABLE_ITEM:
                managedItem = new OrderableItemVo();
                break;
            case PACKAGE_TYPE:
                managedItem = new PackageTypeVo();
                break;
            case PHARMACY_SYSTEM:
                managedItem = new PharmacySystemVo();
                break;
            case PRODUCT:
                managedItem = new ProductVo();
                break;
            case LOCAL_MEDICATION_ROUTE:
                managedItem = new LocalMedicationRouteVo();
                break;
            case RX_CONSULT:
                managedItem = new RxConsultVo();
                break;
            default:
                managedItem = null;
                break;
        }

        return managedItem;
    }

    /**
     * getId
     * 
     * @return id property
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Get the value to display as a {@link Selectable}.
     * 
     * @return String {@link #toShortString()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    @Override
    public String getValue() {
        return toShortString();
    }

    /**
     * setId
     * 
     * @param id id property
     */
    public final void setId(String id) {
        this.id = id;
    }

    /**
     * getItemStatus
     * 
     * @return itemStatus property
     */
    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    /**
     * setItemStatus
     * 
     * @param itemStatus itemStatus property
     */
    public void setItemStatus(ItemStatus itemStatus) {

        if (itemStatus != null) {
            if (itemStatus.isActive()) {
                setInactivationDate(null);
            } else {
                if (null == getInactivationDate()) { 
                    Calendar gc = GregorianCalendar.getInstance();
                    Date inactDt = gc.getTime();
                    setInactivationDate(inactDt);
                }
            }
        }

        this.itemStatus = itemStatus;
    }

    /**
     * getVaDataFields
     * 
     * @return vaDataFields property
     */
    public DataFields<DataField> getVaDataFields() {

        return vaDataFields;
    }

    /**
     * setVaDataFields
     * 
     * @param vaDataFields vaDataFields property
     */
    public void setVaDataFields(DataFields<DataField> vaDataFields) {

        this.vaDataFields = vaDataFields;
    }

    /**
     * getRequestItemStatus
     * 
     * @return requestStatus property
     */
    public RequestItemStatus getRequestItemStatus() {
        return requestItemStatus;
    }

    /**
     * setRequestItemStatus
     * 
     * @param requestStatus requestStatus property
     */
    public void setRequestItemStatus(RequestItemStatus requestStatus) {
        this.requestItemStatus = requestStatus;
    }

    /**
     * isLocalUse
     * 
     * @return localUse property
     */
    public boolean isLocalUse() {
        return localUse;
    }

    /**
     * setLocalUse
     * 
     * @param localUse localUse property
     */
    public void setLocalUse(boolean localUse) {
        this.localUse = localUse;
    }

    /**
     * Returns if this item requires two reviews before adds/modifies are approved or rejected.
     * <p>
     * Differs from {@link FieldKey#isRequiresSecondApproval()} as this method is used during adds and the FieldKey method is
     * used during mods. In addition, this method only applies to {@link ManagedItemVo}, not individual fields.
     * <p>
     * Default implementation returns true.
     * 
     * @return boolean True if this item requires two reviews, otherwise, only one review is needed.
     */
    public boolean isTwoReviewItem() {
        return true;
    }

    /**
     * Updates this item from the specified request's field-level details.
     * <p>
     * For each field-level request detail seen, if the change is marked as 'approved' it is applied to this item, if is
     * marked as 'rejected' it is removed from this item, and any other status levels are ignored.
     * <p>
     * Add {@link ItemAuditHistoryVo} records as required via {@link #getItemAuditHistory()}.
     * <p>
     * Apply the rejected modifications first, then the approved ones later below. Note: The rejected mods are applied prior
     * to the approved ones, as there may be multiple rejected/approved mod requests for the same data field.
     * 
     * @param request The request whose field-level details need to be applied to this item.
     * @param user UserVo updating item
     * @param environmentUtility EnvironmentUtility to determine local/national and site number
     */
    public final void updateFromRequest(RequestVo request, UserVo user, EnvironmentUtility environmentUtility) {
        
        ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
        itemAudit.setAuditItemType(getEntityType());
        itemAudit.setAuditItemId(getId());
        itemAudit.setSiteName(environmentUtility.getSiteNumber()); 
        itemAudit.setEventCategory(EventCategory.SYSTEM_EVENT);
        itemAudit.setReason(""); 
        itemAudit.setOldValue("");
        itemAudit.setNewValue("");
        itemAudit.setUsername(user.getUsername());
        setNewAuditHistory(itemAudit);

        for (ModDifferenceVo modDifference : request.getRequestDetails()) { // Apply rejected mods first:
            RequestItemStatus riStatus = modDifference.getModRequestItemStatus();

            if (RequestItemStatus.REJECTED.equals(riStatus)) {
                updateRejectedRequestDetail(modDifference, user, environmentUtility);
            } else if (!RequestItemStatus.APPROVED.equals(riStatus)) {
                LOG.debug("Ignoring '" + modDifference.getModRequestItemStatus() + "' request detail.");
            }
        }

        for (ModDifferenceVo modDifference : request.getRequestDetails()) { // Apply approved mods last:
            RequestItemStatus riStatus = modDifference.getModRequestItemStatus();

            if (RequestItemStatus.APPROVED.equals(riStatus)) {
                updateApprovedRequestDetail(modDifference, user, environmentUtility);
            }
        }
    }

    /**
     * Update this {@link ManagedItemVo} with the given approved {@link ModDifferenceVo}.
     * 
     * @param modDifference the approved {@link ModDifferenceVo}
     * @param user UserVo updating item
     * @param environmentUtility EnvironmentUtility to determine local/national and site number
     */
    private void updateApprovedRequestDetail(ModDifferenceVo modDifference, UserVo user,
                                             EnvironmentUtility environmentUtility) {

        if (modDifference.getDifference().getFieldKey().isVaDataField()) {
            modDifference.updateNewDataFieldValueForRequest();
        }

        boolean successful = updateFieldToNewValue(modDifference.getDifference());

        if (successful) {
            ItemAuditHistoryDetailsVo itemAudit =
                    new ItemAuditHistoryDetailsVo(this, modDifference, environmentUtility.getSiteNumber(),
                                                  EventCategory.APPROVED_REQUEST, user.getUsername());

            itemAudit.setDetailReason(modDifference.getModificationReason());
            getNewAuditHistory().addDetail(itemAudit);
        }
    }

    /**
     * Update this {@link ManagedItemVo} with the given rejected {@link ModDifferenceVo}.
     * 
     * @param modDifference the rejected {@link ModDifferenceVo}
     * @param user UserVo updating item
     * @param environmentUtility EnvironmentUtility to determine local/national and site number
     */
    private void updateRejectedRequestDetail(ModDifferenceVo modDifference, UserVo user,
                                             EnvironmentUtility environmentUtility) {

        boolean successful = resetFieldToOldValue(modDifference.getDifference());

        if (successful) {
            ItemAuditHistoryDetailsVo itemAudit =
                    new ItemAuditHistoryDetailsVo(this, modDifference, environmentUtility.getSiteNumber(),
                                                  EventCategory.REJECTED_REQUEST, user.getUsername());

            itemAudit.setDetailReason(modDifference.getModificationReason());
            getNewAuditHistory().addDetail(itemAudit);
        }
    }

    /**
     * Updates this item from the specified collection of field-level modification details. For each field-level modification
     * difference seen, if the change is marked as 'request to modify value', it is applied to this item, otherwise, it is
     * not. If 'onlyUpdateEditableFields' is true, then only VA data fields tagged as editable will be modified by this
     * method.
     * <p>
     * Updates the {@link #getItemAuditHistory()} with the appropriate new {@link ItemAuditHistoryVo} records.
     * 
     * @param modDifferences The collection of field-level differences that need to be applied to this item.
     * @param user {@link UserVo} performing the modifications
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment information
     */
    public void updateFromModDifferences(Collection<ModDifferenceVo> modDifferences, UserVo user,
                                         EnvironmentUtility environmentUtility) {

        boolean modifiedSecondReviewField =
                hasModifiedSecondReviewField(modDifferences, environmentUtility.getEnvironment(), user);

        for (ModDifferenceVo modDifference : modDifferences) {
            if (shouldUpdateModification(modDifference, modifiedSecondReviewField, environmentUtility)) {
                updateModification(modDifference, user, environmentUtility);
            } else {
                resetFieldToOldValue(modDifference.getDifference());
            }
        }
    }

    /**
     * Find out if the Collection of {@link ModDifferenceVo} contains any second review fields.
     * <p>
     * If it does, the item should not updated with any of the modifications, and mod request is created for all the
     * modifications.
     * 
     * @param modDifferences Collection of {@link ModDifferenceVo} to test
     * @param environment current {@link Environment}
     * @param user current {@link UserVo}
     * @return boolean
     */
    public boolean hasModifiedSecondReviewField(Collection<ModDifferenceVo> modDifferences, Environment environment,
                                                 UserVo user) {
        Collection<FieldKey> secondReviewFields = listSecondReviewFields(environment, user.getRoles());

        for (ModDifferenceVo modDifference : modDifferences) {
            if (modDifference.isAcceptChange()
                && secondReviewFields.contains(modDifference.getDifference().getFieldKey())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if the {@link ModDifferenceVo} should be applied to this {@link ManagedItemVo} instance.
     * <p>
     * If the {@link ModDifferenceVo} is accepted and we're updating second review fields (if there are second review fields)
     * return true.
     * <p>
     * Also if the difference is for a VA data field, it is editable.
     * 
     * @param modDifference {@link ModDifferenceVo} to test
     * @param modifiedSecondReviewField True if there is a modified second review field among all the modifications
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment information
     * @return boolean
     */
    private boolean shouldUpdateModification(ModDifferenceVo modDifference, boolean modifiedSecondReviewField,
                                             EnvironmentUtility environmentUtility) {

        // Update second review fields at local. At national a request should be generated for second review fields.
        boolean updateSecondReviewFields = environmentUtility.isLocal();

        boolean updateable = modDifference.isAcceptChange() && (updateSecondReviewFields || !modifiedSecondReviewField);

        if (modDifference.getDifference().getFieldKey().isVaDataField()) {
            DataField dataField = (DataField) modDifference.getDifference().getNewValue();

            // Only update editable VA data fields if at local and if this VO has no local only data
            boolean onlyUpdateEditableFields = environmentUtility.isLocal() && !isLocalOnlyData();
            updateable =
                    updateable && (!onlyUpdateEditableFields || (onlyUpdateEditableFields && dataField.isEditable()));
        }

        return updateable;
    }

    /**
     * Update this instance with the accepted {@link ModDifferenceVo}.
     * <p>
     * If the update is successful, create a new item audit history record.
     * <p>
     * This method assumes {@link #shouldUpdateModification(ModDifferenceVo, boolean, boolean, boolean)} returns true.
     * 
     * @param modDifference {@link ModDifferenceVo} for which to update the values
     * @param user {@link UserVo} performing the action
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment information
     */
    private void updateModification(ModDifferenceVo modDifference, UserVo user, EnvironmentUtility environmentUtility) {
        boolean successful = updateFieldToNewValue(modDifference.getDifference());

        if (successful) {
            EventCategory eventCategory;

            if (environmentUtility.isLocal()) {
                eventCategory = EventCategory.LOCAL_MODIFICATION;
            } else {
                eventCategory = EventCategory.NATIONAL_MODIFICATION;
            }

            if (getNewAuditHistory() == null) {
                ItemAuditHistoryVo nAuditHistory =
                        new ItemAuditHistoryVo(this, modDifference, environmentUtility.getSiteNumber(), eventCategory,
                                               user.getUsername());
                setNewAuditHistory(nAuditHistory);
            }

            ItemAuditHistoryDetailsVo itemAudit =
                    new ItemAuditHistoryDetailsVo(this, modDifference, environmentUtility.getSiteNumber(),
                                                  eventCategory, user.getUsername());
            getNewAuditHistory().addDetail(itemAudit);
        }
    }

    /**
     * Tests if this objects needs a new VUID based on the given differences.
     * <p>
     * Default implementation returns true if both {@link #hasVuid()} and {@link #hasUpdatedUniquenessCriteria(Collection)}
     * return true.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * @return boolean true if VUID update is needed.
     */
    public boolean isVuidUpdateNeeded(Collection<ModDifferenceVo> differences) {
        return hasVuid() && hasUpdatedUniquenessCriteria(differences);
    }

    /**
     * List all uniqueness criteria for this ValueObject.
     * 
     * @return Set<FieldKey> All uniqueness fields for this object.
     */
    public abstract Set<FieldKey> listUniquenessCriteria();

    /**
     * Reset a field to its previous value.
     * <p>
     * Sub classes can override, if necessary, although the {@link ReflectionUtility} should be able to handle all current
     * fields.
     * 
     * @param difference {@link Difference} from which to get the old value
     * @return boolean True if the value was successfully reset to the old value
     */
    protected boolean resetFieldToOldValue(Difference difference) {
        return ReflectionUtility.setValue(this, difference.getFieldKey(), difference.getOldValue());
    }

    /**
     * Update the current value of the field with the new value found in the given {@link Difference}.
     * <p>
     * Sub classes can override, if necessary, although the {@link ReflectionUtility} should be bale to handle all current
     * fields.
     * 
     * @param difference {@link Difference} from which to get the new value
     * @return boolean True if the value was successfully updated to the new value.
     */
    public boolean updateFieldToNewValue(Difference difference) {
        return ReflectionUtility.setValue(this, difference.getFieldKey(), difference.getNewValue());
    }

    /**
     * Merges the current National instance of a {@link ManagedItemVo} with the given Local {@link ManagedItemVo}.
     * <p>
     * First calls {@link #handleMergeLocalFields(ManagedItemVo)} for sub classes to handle their specific local fields.
     * <p>
     * Then takes the editable and local-only VA data field values from the given Local {@link ManagedItemVo} and sets them
     * on this National instance.
     * <p>
     * Finally, merges Local Use appropriately.
     * 
     * @param localItem the Local instance of {@link ManagedItemVo} to merge with National instance of {@link ManagedItemVo}
     */
    public final void mergeLocalFields(ManagedItemVo localItem) {
        Collection<FieldKey> handledFields = new ArrayList<FieldKey>();
        handledFields.addAll(handleMergeLocalFields(localItem));
        handledFields.addAll(mergeEditableAndLocalOnlyVaDatafields(localItem, handledFields));
        handledFields.addAll(mergeLocalUse(localItem, handledFields));
        mergeRemainingLocalOnlyFields(localItem, handledFields);
    }

    /**
     * Sub class method for merging the current National instance of a {@link ManagedItemVo} with the given Local
     * {@link ManagedItemVo}.
     * <p>
     * Returns a Collection of {@link FieldKey} which have been merged by this method. This will prevent the fields
     * represented by these FieldKeys from being overwritten again by future merging.
     * <p>
     * Intended only for fields that require additional logic beyond a simple get/set operation as the
     * {@link #mergeRemainingLocalOnlyFields(ManagedItemVo, Collection)} will handle those types of fields automatically.
     * 
     * @param localItem the Local instance of {@link ManagedItemVo} to merge with National instance of {@link ManagedItemVo}
     * @return Collection of FieldKeys merged within this method
     */
    protected Collection<FieldKey> handleMergeLocalFields(ManagedItemVo localItem) {
        return new ArrayList<FieldKey>(0);
    }

    /**
     * Updates the editable and local-only VA data fields on this National instance of {@link ManagedItemVo} with the values
     * from the given Local instance of {@link ManagedItemVo}.
     * 
     * @param localItem Local instance of {@link ManagedItemVo}
     * @param handledFields FieldKeys already handled by sub classes
     * @return Collection of FieldKeys handled by this merge
     */
    protected Collection<FieldKey> mergeEditableAndLocalOnlyVaDatafields(ManagedItemVo localItem,
                                                                         Collection<FieldKey> handledFields) {
        Collection<FieldKey> handledVaDataFields = new ArrayList<FieldKey>();

        for (FieldKey fieldKey : FieldKey.getVaDataFields(getEntityType())) {
            if (!handledFields.contains(fieldKey)) {
                handledVaDataFields.add(fieldKey);
                DataField localDataField = localItem.getVaDataFields().getDataField(fieldKey);

                if (localDataField != null) {
                    DataField nationalDataField = getVaDataFields().getDataField(fieldKey);

                    if (nationalDataField == null || nationalDataField.isEditable() || fieldKey.isLocalOnlyDataField()) {
                        getVaDataFields().setDataField(localDataField);
                    }
                }
            }
        }

        return handledVaDataFields;
    }

    /**
     * If this National instance of {@link ManagedItemVo} has been inactivated, then set local use to false and the previous
     * local use value. Otherwise, retain whatever the localUse of the Local {@link ManagedItemVo} is
     * 
     * @param localItem Local instance of {@link ManagedItemVo}
     * @param handledFields FieldKeys already handled by sub classes
     * @return Collection of FieldKeys handled by this merge
     */
    protected Collection<FieldKey> mergeLocalUse(ManagedItemVo localItem, Collection<FieldKey> handledFields) {
        Collection<FieldKey> handledLocalUseFields = new ArrayList<FieldKey>();

        if (!handledFields.contains(FieldKey.LOCAL_USE)) {
            if (ItemStatus.INACTIVE.equals(this.getItemStatus())) {
                setLocalUse(false);
                setPreviouslyMarkedForLocalUse(localItem.isPreviouslyMarkedForLocalUse());
            } else {
                setLocalUse(localItem.isLocalUse());
                setPreviouslyMarkedForLocalUse(localItem.isPreviouslyMarkedForLocalUse());
            }

            handledLocalUseFields.add(FieldKey.LOCAL_USE);
            handledLocalUseFields.add(FieldKey.PREVIOUSLY_MARKED_FOR_LOCAL_USE);
        }

        return handledLocalUseFields;
    }

    /**
     * If any local-only fields weren't set by any other merge operation, this method will set all remaining by reflection.
     * <p>
     * This method is a convenience for maintenance in that not all local-only fields must be set in code.
     * <p>
     * NOTE: It does not handle complex objects as it simply gets the local value and sets it on this item instance, if the
     * local value is not null.
     * 
     * @param localItem the Local instance of {@link ManagedItemVo} to merge with National instance of {@link ManagedItemVo}
     * @param handledFields FieldKeys already handled by sub classes and previous merge methods
     */
    private void mergeRemainingLocalOnlyFields(ManagedItemVo localItem, Collection<FieldKey> handledFields) {
        Collection<FieldKey> loalOnlyFields = FieldKey.getLocalOnlyFields(getEntityType());

        for (FieldKey fieldKey : loalOnlyFields) {
            if (!handledFields.contains(fieldKey)) {
                Object localValue = ReflectionUtility.getValue(localItem, fieldKey);

                if (localValue != null) {
                    ReflectionUtility.setValue(this, fieldKey, localValue);
                }
            }
        }
    }

    /**
     * Sets this item's status to inactive and sets its Inactivation date to the current date/time.
     * 
     * @return Date The inactivation date stamped on the item.
     */
    public Date inactivate() {
        setItemStatus(ItemStatus.INACTIVE);
        Calendar gc = GregorianCalendar.getInstance();
        Date inactDt = gc.getTime();
        setInactivationDate(inactDt);

        return inactDt;
    }

    /**
     * Sets this item's status to {@link ItemStatus#ARCHIVED}.
     */
    public void archive() {
        setItemStatus(ItemStatus.ARCHIVED);
    }

    /**
     * Activates the object
     */
    public void activate() {
        setItemStatus(ItemStatus.ACTIVE);
        setInactivationDate(null);
    }

    /**
     * Set {@link RequestItemStatus#REJECTED}, local use false, request rejection reason, and rejection reason text.
     * 
     * @param requestRejectReason String
     * @param rejectReasonText String
     */
    public void rejectItem(RequestRejectionReason requestRejectReason, String rejectReasonText) {
        setRequestItemStatus(RequestItemStatus.REJECTED);
        setLocalUse(false);
        setRequestRejectionReason(requestRejectReason);
        setRejectionReasonText(rejectReasonText);
    }

    /**
     * getRejectionReasonText
     * 
     * @return rejectionReasonText property
     */
    public String getRejectionReasonText() {
        return rejectionReasonText;
    }

    /**
     * setRejectionReasonText
     * 
     * @param rejectionReasonText rejectionReasonText property
     */
    public void setRejectionReasonText(String rejectionReasonText) {
        this.rejectionReasonText = trimToEmpty(rejectionReasonText);
    }

    /**
     * Return true if this ManagedItemVo represents a managed item that has a VUID as an attribute.
     * 
     * @return boolean
     */
    public boolean hasVuid() {
        return FieldKey.VUID.getEntityTypes().contains(getEntityType());
    }

    /**
     * Return true if this ManagedItemVo has a parent ManagedItemVo.
     * 
     * @return boolean
     */
    public boolean hasParent() {
        return getEntityType().hasParent();
    }

    /**
     * Return true if this ManagedItemVo has a List of children ManagedItemVo.
     * 
     * @return boolean
     */
    public boolean hasChildren() {
        return getEntityType().hasChild();
    }

    /**
     * Returns True if the collection of ModDifferences contains a change to a uniqueness field on this ManagedItem
     * 
     * false otherwise
     * 
     * @param differences Accepted Mod Differences to this item
     * @return boolean
     */
    public boolean hasUpdatedUniquenessCriteria(Collection<ModDifferenceVo> differences) {
        Set<FieldKey> uniqueness = listUniquenessCriteria();
        boolean updated = false;

        for (ModDifferenceVo difference : differences) {
            updated |= uniqueness.contains(difference.getDifference().getFieldKey());
        }

        return updated;
    }

    /**
     * Retrieve the parent of this ManagedItemVo using reflection. Optimally sub classes would override this method to
     * directly return the parent.
     * 
     * @return parent ManagedItemVo
     */
    public ManagedItemVo getParent() {
        ManagedItemVo parent = null;

        if (hasParent()) {
            FieldKey parentField = FieldKeyUtility.getParentFieldKey(getEntityType());
            parent = ReflectionUtility.getValue(this, parentField);
        }

        return parent;
    }

    /**
     * Retrieve the parent of this ManagedItemVo using reflection. Optimally sub classes would override this method to
     * directly return the parent.
     * 
     * @return parent ManagedItemVo
     */
    public String getParentId() {
        return getParent().getId();
    }

    /**
     * Set the parent of this ManagedItemVo using reflection. Optimally sub classes would override this method to directly
     * return the parent.
     * 
     * @param parent ManagedItemVo
     */
    public void setParent(ManagedItemVo parent) {
        if (hasParent()) {
            FieldKey parentField = FieldKeyUtility.getParentFieldKey(getEntityType());
            ReflectionUtility.setValue(this, parentField, parent);
        }
    }

    /**
     * Retrieve the previous parent of this ManagedItemVo using reflection. Optimally sub classes would override this method
     * to directly return the previous parent.
     * 
     * @return previous parent ManagedItemVo
     */
    public ManagedItemVo getPreviousParent() {
        ManagedItemVo previousParent = null;

        if (hasParent()) {
            FieldKey parentField = FieldKeyUtility.getParentFieldKey(getEntityType());
            String previousParentAttribute = PREV + StringUtils.capitalize(parentField.toAttributeName());
            previousParent = ReflectionUtility.getValue(this, previousParentAttribute);
        }

        return previousParent;
    }

    /**
     * Return true if this {@link ManagedItemVo} has a previous parent. Tests to see if {@link #getPreviousParent()} returns
     * null.
     * 
     * @return boolean
     */
    public boolean hasPreviousParent() {
        return ((getPreviousParent() != null) && (getPreviousParent().getId() != null));
    }

    /**
     * Set the previous parent of this ManagedItemVo using reflection. Optimally sub classes would override this method to
     * directly return the previous parent.
     * 
     * @param parent previous parent ManagedItemVo
     */
    public void setPreviousParent(ManagedItemVo parent) {
        if (hasParent()) {
            FieldKey parentField = FieldKeyUtility.getParentFieldKey(getEntityType());
            String previousParentAttribute = PREV + StringUtils.capitalize(parentField.toAttributeName());
            ReflectionUtility.setValue(this, previousParentAttribute, parent);
        }
    }

    /**
     * Retrieve the children of this ManagedItemVo using reflection. Optimally sub classes would override this method to
     * directly return the children.
     * 
     * @return List of ManagedItemVo
     */
    public List<ManagedItemVo> getChildren() {
        List<ManagedItemVo> children = null;

        if (hasChildren()) {
            FieldKey childField = FieldKeyUtility.getChildFieldKey(getEntityType());
            children = ReflectionUtility.getValue(this, childField);
        }

        return children;
    }

    /**
     * set the children of this ManagedItemVo using reflection. Optimally sub classes would override this method to directly
     * return the children.
     * 
     * @param children List of ManagedItemVo
     */
    public void setChildren(List<ManagedItemVo> children) {
        if ((children != null) && !children.isEmpty()) {
            FieldKey childField = FieldKeyUtility.getChildFieldKey(getEntityType());
            ReflectionUtility.setValue(this, childField, children);
        }
    }

    /**
     * Get the number of children in the ManagedItemVo using reflection. Optimally sub classes would override this method to
     * directly return the number of children.
     * 
     * @return int number of children
     */
    public int getChildrenCount() {
        Integer children = 0;

        if (hasChildren()) {
            FieldKey childField = FieldKeyUtility.getChildCountFieldKey(getEntityType());
            children = ReflectionUtility.getValue(this, childField);
        }

        return children;
    }

    /**
     * Set the number of children in the ManagedItemVo using reflection. Optimally sub classes would override this method to
     * directly return the number of children.
     * 
     * @param childrenCount int number of children
     */
    public void setChildrenCount(int childrenCount) {
        if (hasChildren()) {
            FieldKey childField = FieldKeyUtility.getChildCountFieldKey(getEntityType());
            ReflectionUtility.setValue(this, childField, childrenCount);
        }
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    public abstract boolean isLocalOnlyData();

    /**
     * Sets default values on local-only fields. Should be called when an item is being inserted from National so that
     * local-only fields have values which require them.
     * <p>
     * Default implementation sets values on local-only VA data fields. Sub classes can override this method to set
     * additional fields.
     */
    public void defaultLocalOnlyFields() {
        Collection<FieldKey> localOnlyVaDataFields = FieldKey.getLocalOnlyVaDataFields(getEntityType());

        for (FieldKey fieldKey : localOnlyVaDataFields) {
            DataField dataField = getVaDataFields().getDataField(fieldKey);

            if (dataField != null) {
                dataField.selectDefaultValue();
            }
        }
    }

    /**
     * Return true if the ID is not null and the ID's trimmed length is less than or equal to zero.
     * 
     * @return boolean
     */
    @Override
    public boolean isNewInstance() {
        return id == null || id.trim().length() <= 0;
    }

    /**
     * Returns true if the managed item is a ProductVo and false otherwise
     * 
     * @return true if the item is a product item
     */
    public boolean isProductItem() {
        if (this instanceof ProductVo) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if the managed item is a NdcVo and false otherwise
     * 
     * @return true if the item is a ndc item, false otherwise
     */
    public boolean isNdcItem() {
        if (this instanceof NdcVo) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if the managed item is a OrderableItemVo and false otherwise
     * 
     * @return true if the item is a orderable item, false otherwise
     */
    public boolean isOrderableItemItem() {
        if (this instanceof OrderableItemVo) {
            return true;
        }

        return false;
    }

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {
        return EntityType.toEntityType(getClass());
    }

    /**
     * getRevisionNumber
     * 
     * @return revisionNumber property
     * 
     */
    public long getRevisionNumber() {
        return revisionNumber;
    }

    /**
     * setRevisionNumber
     * 
     * @param revisionNumber revisionNumber property
     */
    public void setRevisionNumber(long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    /**
     * getItemAuditHistory
     * 
     * @return itemAuditHistory property
     */
    public Collection<ItemAuditHistoryVo> getItemAuditHistory() {
        if (itemAuditHistory == null) {
            setItemAuditHistory(new ArrayList<ItemAuditHistoryVo>());
        }

        return itemAuditHistory;
    }

    /**
     * setItemAuditHistory
     * 
     * @param itemAuditHistory Collection<ItemAuditHistoryVo>
     */
    public void setItemAuditHistory(Collection<ItemAuditHistoryVo> itemAuditHistory) {
        this.itemAuditHistory = new ArrayList<ItemAuditHistoryVo>();

        if (itemAuditHistory != null && !itemAuditHistory.isEmpty()) {
            this.itemAuditHistory.addAll(itemAuditHistory);
        }

    }

    /**
     * This method makes all the fields in the managed item editable
     * 
     */
    public void makeAllDataFieldsEditable() {

        for (FieldKey key : FieldKey.getVaDataFields()) {
            if (key != null) {
                if (vaDataFields.containsFieldKey(key)) {
                    DataField df = vaDataFields.getDataField(key);

                    if (df != null) {
                        if (df.isListDataField()) {
                            df.setEditable(true);
                        }

                        df.setEditable(true);
                        vaDataFields.setDataField(df);
                    }
                }
            }
        }
    }

    /**
     * Set the parent item as this ManagedItemVo's parent. The default implementation does nothing.
     * 
     * @param parent ManagedItemVo to be parent of this ManagedItemVo
     */
    public void selectParent(ManagedItemVo parent) {

    }

    /**
     * Make this ManagedItemVo a template for a separate, new ManagedItemVo. First clone the current item and then call
     * #makeTemplate().
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return ManagedItemVo
     */
    public ManagedItemVo template(Environment environment) {
        ManagedItemVo template;

        try {
            template = (ManagedItemVo) clone();
        } catch (CloneNotSupportedException e) {
            template = blankTemplate(environment);
        }

        return makeTemplate(template, environment);
    }

    /**
     * Make a template from the given ManagedItemVo.
     * 
     * @param managedItemVo managed item to make a template from
     * @param environment {@link Environment} in which the template is being made
     * @return template ManagedItemVo
     */
    protected abstract ManagedItemVo makeTemplate(ManagedItemVo managedItemVo, Environment environment);

    /**
     * Create a new blank template for this ManagedItemVo
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return ManagedItemVo blank template
     */
    public abstract ManagedItemVo blankTemplate(Environment environment);

    /**
     * Retrieve an blank template instance of the local child to the current national ManagedItemVo. Only applies for
     * ManagedItemVo types that have a national/local relationship, which currently is only OrderableItemVo. Default
     * implementation returns a blank template from {@link #blankTemplate()}.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return ManagedItemVo blank local template
     */
    public ManagedItemVo blankLocalTemplate(Environment environment) {
        return blankTemplate(environment);
    }

    /**
     * Retrieve a template instance of the local to the current national ManagedItemVo. Only applies for ManagedItemVo types
     * that have a national/local relationship, which currently is only OrderableItemVo. Default implementation returns a
     * template from {@link #template()}.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return ManagedItemVo template
     */
    public ManagedItemVo localTemplate(Environment environment) {
        return template(environment);
    }

    /**
     * Create a blank template of the child for this ManagedItemVo, with it's parent set as this ManagedItemVo.
     * 
     * @param environment {@link Environment} in which the template is being made
     * @return ManagedItemVo
     */
    public ManagedItemVo blankChildTemplate(Environment environment) {
        ManagedItemVo template = null;

        if (isOrderableItemItem()) {
            template = new ProductVo().blankTemplate(environment);
            template.selectParent(this);
        } else if (isProductItem()) {
            template = new NdcVo().blankTemplate(environment);
            template.selectParent(this);
        } else {
            template = blankTemplate(environment);
        }

        return template;
    }

    /**
     * getUniqueId
     * 
     * @return
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#getUniqueId()
     * @return String UniqueId
     */
    @Override
    public String getUniqueId() {
        return id + "," + this.getEntityType();
    }

    /**
     * getRequestRejectionReason
     * 
     * @return reqeustRejectReason property
     */
    public RequestRejectionReason getRequestRejectionReason() {
        return requestRejectionReason;
    }

    /**
     * setRequestRejectionReason
     * 
     * @param requestRejectReason reqeustRejectReason property
     */
    public void setRequestRejectionReason(RequestRejectionReason requestRejectReason) {
        this.requestRejectionReason = requestRejectReason;
    }

    /**
     * getGcnSequenceNumber
     * 
     * @return gcnSequenceNumber property
     */
    public String getGcnSequenceNumber() {
        return gcnSequenceNumber;
    }

    /**
     * setGcnSequenceNumber
     * 
     * @param gcnSequenceNumber gcnSequenceNumber property
     */
    public void setGcnSequenceNumber(String gcnSequenceNumber) {
        this.gcnSequenceNumber = trimToEmpty(gcnSequenceNumber);
    }

    /**
     * getSpanishText
     * 
     * @return spanishText property
     */
    public LanguageChoice getSpanishText() {
        return spanishText;
    }

    /**
     * setSpanishText
     * 
     * @param spanishText spanishText property
     */
    public void setSpanishText(LanguageChoice spanishText) {
        this.spanishText = spanishText;
    }

    /**
     * Compare two ManagedItemVo based upon their toSthortString() methods.
     * 
     * @param vo ValueObject to compare this ManagedItemVo with
     * @return zero if equal, negative if less, positive if more
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#compareTo(gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    public int compareTo(ValueObject vo) {
        return this.toShortString().compareTo(vo.toShortString());
    }

    /**
     * isPreviouslyMarkedForLocalUse
     * 
     * @return boolean
     */
    public boolean isPreviouslyMarkedForLocalUse() {
        return previouslyMarkedForLocalUse;
    }

    /**
     * setPreviouslyMarkedForLocalUse
     * 
     * @param previouslyMarkedForLocalUse boolean
     */
    public void setPreviouslyMarkedForLocalUse(boolean previouslyMarkedForLocalUse) {
        this.previouslyMarkedForLocalUse = previouslyMarkedForLocalUse;
    }

    /**
     * Sub classes can override this method to return the default sort order for this {@link ManagedItemVo}.
     * <p>
     * Default implementation returns {@link FieldKey#ID}.
     * 
     * @return {@link FieldKey} to sort by
     */
    public FieldKey getDefaultSortedFieldKey() {
        return FieldKey.ID;
    }

    /**
     * Sub classes can override this method to return the default sort order used in search results for this
     * {@link ManagedItemVo}.
     * <p>
     * Default implementation returns {@link #getDefaultSortedFieldKey()}.
     * 
     * @return {@link FieldKey} to sort by
     */
    public FieldKey getDefaultSearchSortedFieldKey() {
        return getDefaultSortedFieldKey();
    }

    /**
     * Get the "child" ManagedItemVo's default FieldKey to sort by.
     * <p>
     * If there is an error getting the "child" type's default sorted FieldKey, return {@link FieldKey#ID}.
     * 
     * @return {@link FieldKey} to sort by
     */
    public FieldKey getChildDefaultSortedFieldKey() {
        try {
            EntityType childType = getEntityType().getChild();
            ManagedItemVo childItem = ManagedItemVo.newInstance(childType);

            return childItem.getDefaultSortedFieldKey();
        } catch (Exception e) {
            LOG.warn("Unable to get child default sort order, returning FieldKey.ID instead", e);

            return FieldKey.ID;
        }
    }

    /**
     * isPartialSave
     * 
     * @return partialSave property
     */
    public boolean isPartialSave() {
        return partialSave;
    }

    /**
     * setPartialSave
     * 
     * @param partialSave partialSave property
     */
    public void setPartialSave(boolean partialSave) {
        this.partialSave = partialSave;
    }

    /**
     * getInactivationDate
     * 
     * @return inactivationDate property
     */
    public Date getInactivationDate() {
        return inactivationDate;
    }

    /**
     * setInactivationDate
     * 
     * @param inactivationDate inactivationDate property
     */
    public void setInactivationDate(Date inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    /**
     * getNewAuditHistory
     * 
     * @return newAuditHistory property
     */
    public ItemAuditHistoryVo getNewAuditHistory() {
        return newAuditHistory;
    }

    /**
     * setNewAuditHistory
     * 
     * @param newAuditHistory newAuditHistory property
     */
    public void setNewAuditHistory(ItemAuditHistoryVo newAuditHistory) {
        this.newAuditHistory = newAuditHistory;
    }

    /**
     * Set if this local managed item is being replaced by a national managed item.
     * 
     * @param isBeingReplacedByNational true if being replaced by national
     */
    public void setIsBeingReplacedByNational(boolean isBeingReplacedByNational) {
        this.isBeingReplacedByNational = isBeingReplacedByNational;
    }

    /**
     * Whether this local managed item is being replaced by national managed item.
     * 
     * @return true if being replaced by national, false otherwise
     */
    public boolean isBeingReplacedByNational() {
        return isBeingReplacedByNational;
    }

    /**
     * Clone this instance of {@link ManagedItemVo} and then remove local-only data from the item that should not be sent up
     * to National in a request.
     * <p>
     * Default implementation does nothing, returning a clone of this {@link ManagedItemVo}.
     * 
     * @param <T> Type of {@link ManagedItemVo}
     * @return copy of Local instance of {@link ManagedItemVo} without local-only data
     */
    public <T extends ManagedItemVo> T removeLocalData() {
        return (T) copy();
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one.
     * <p>
     * Inactive {@link ManagedItemVo} have all fields disabled, except for their Item Status.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListDisabledFields(environment, roles);

        fields.addAll(FieldKeyUtility.getAllNdfIenFieldKeys());
        fields.add(FieldKey.REJECTION_REASON_TEXT);
        fields.add(FieldKey.REQUEST_REJECTION_REASON);

        if (!ItemStatus.INACTIVE.equals(getItemStatus())) {
            fields.add(FieldKey.INACTIVATION_DATE);
            fields.add(FieldKey.REQUEST_ITEM_STATUS);

            if (getRequestItemStatus().isApproved() || getRequestItemStatus().isRejected()) {
                fields.addAll(FieldKey.getDisabledAfterCreationFields(getEntityType()));
            }
        }

        if (environment.isLocal()) {
            for (DataField dataField : getVaDataFields().getDataFields().values()) {
                if (!dataField.isEditable()) {
                    fields.add(dataField.getKey());
                }
            }
        }

        return fields;
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        Set<FieldKey> fields = super.handleListRequiredFields(environment, roles);

        if (FieldKey.ITEM_STATUS.getEntityTypes().contains(getEntityType())) {
            fields.add(FieldKey.ITEM_STATUS);
        }

        return fields;
    }

    /**
     * List all fields for this ValueObject, discovered via reflection. Also adds this {@link ManagedItemVo} VA Data Fields
     * using {@link FieldKey#getVaDataFields(EntityType)}.
     * <p>
     * Note: Sub-classes may need to add to the returned list fields that are contained within structures such as Maps.
     * 
     * @return Set<FieldKey> All fields for this object.
     */
    @Override
    public Set<FieldKey> listAllFields() {
        Set<FieldKey> fields = super.listAllFields();
        fields.addAll(FieldKey.getVaDataFields(getEntityType()));

        return fields;
    }


    /**
     * getVaDataFieldKeys
     *
     * @return Map of VA Data field keys
     */
    public Map<String, FieldKey> getVaDataFieldKeys() {
        Map<String, FieldKey> vaDFs = new HashMap<String, FieldKey>();

        for (FieldKey fieldKey : FieldKey.getVaDataFields(getEntityType())) {
            vaDFs.put(fieldKey.getKey(), fieldKey);
        }

        return vaDFs;
    }

    /**
     * Comparator class intended for use in sorted sets of non-new ManagedItemVo instances, such as via the TreeSet
     * structure.
     */
    public static class ManagedItemComparator implements Comparator<ManagedItemVo> {

        /**
         * Compares two managed items
         * 
         * @param o1 First ManagedItem
         * @param o2 Second Managed Item
         * @return The output of the compareTo method of the Comparator.
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(ManagedItemVo o1, ManagedItemVo o2) {
            return o1.getId().compareTo(o2.getId());
        }

    }

    
    /**
     * Description
     *
     * @return String
     */
    public String getPsrName() {
    
        return psrName;
    }

    
    /**
     * Description
     *
     * @param psrName String
     */
    public void setPsrName(String psrName) {
    
        this.psrName = psrName;
    }
    
    /**
     * Description
     *
     * @return boolean
     */
    public boolean getMarkedForPepsSecondReview() {
    
        return markedForPepsSecondReview;
    }

    
    /**
     * Description
     *
     * @param markedForPepsSecondReview boolean
     */
    public void setMarkedForPepsSecondReview(boolean markedForPepsSecondReview) {
    
        this.markedForPepsSecondReview = markedForPepsSecondReview;
    }

}
