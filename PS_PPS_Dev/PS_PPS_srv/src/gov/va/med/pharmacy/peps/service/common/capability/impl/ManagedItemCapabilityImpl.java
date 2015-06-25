/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.AuthorizationException;
import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ServiceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.utility.SerializationUtility;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MergeDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.MergeVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ModificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.diff.GroupDifference;
import gov.va.med.pharmacy.peps.common.vo.diff.MergeDifference;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbNdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbProductDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdfSynchQueueDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NotificationDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PartialSaveMgtDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SpecialHandlingDomainCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.service.common.capability.DataFieldsCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.common.capability.StsCapability;
import gov.va.med.pharmacy.peps.service.common.utility.ManagedItemCapabilityFactory;

import firstdatabank.database.FDBException;


/**
 * Perform operations concerning the ManagedItemVo common to all managed item types.
 * 
 * Concrete sub-classes (local and national) implement abstract functionality.
 */
public abstract class ManagedItemCapabilityImpl implements ManagedItemCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagedItemCapabilityImpl.class);
    private static final String MANAGED_ITEM = "Managed Item";
    private static final String NO_SELECTION = "No Selection Made";
    
    /** nationalSettingDomainCapability */
    protected NationalSettingDomainCapability nationalSettingDomainCapability;

    private String strParentIdtoNotSend = "";

    private ManagedItemCapabilityFactory managedItemCapabilityFactory;
    private PartialSaveMgtDomainCapability partialSaveMgtDomainCapability;
    private DataFieldsCapability dataFieldsCapability;
    private VistaFileSynchCapability vistaFileSynchCapability;
    private EnvironmentUtility environmentUtility;
    private StsCapability stsCapability;
    private DrugReferenceCapability drugReferenceCapability;
    private FdbNdcDomainCapability fdbNdcDomainCapability;
    private FdbProductDomainCapability fdbProductDomainCapability;
    private RequestDomainCapability requestDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private NotificationDomainCapability notificationDomainCapability;
    private NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability;
    private SpecialHandlingDomainCapability specialHandlingDomainCapability;


    /**
     * The given user approves the given request for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to approve
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @param user UserVo user approving the request
     * @return RequestUpdateInformation Updated item and request information
     * 
     * @throws ValidationException if data does not validate
     */
    @Override
    public ProcessedRequestVo approveRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> modDifferences,
        UserVo user) throws ValidationException {

        request.adjustNewModDifferences(modDifferences);

        request.setNewItemRequestStatus(RequestItemStatus.APPROVED);
        request.setUnderReview(false);
        request.setRejectionReasonText(null);
        request.setRequestRejectionReason(null);

        item.setRequestRejectionReason(null);
        item.setRejectionReasonText(null);
        
        enforceBusinessRules(item, user, false);
        request.checkForConflicts(modDifferences);

        Errors warnings = getRulesCapability(item.getEntityType()).checkForWarnings(item, modDifferences, false);

        request.setDependentRequests(getDependenciesToApproveOrReject(item, true));

        for (ManagedItemVo reqItem : request.getDependentRequests().keySet()) {
            RequestVo depReq = request.getDependentRequests().get(reqItem);
            depReq.adjustNewModDifferences(depReq.getRequestDetails());
            depReq.setNewItemRequestStatus(RequestItemStatus.APPROVED);
            depReq.setUnderReview(false);
            reqItem.setRequestRejectionReason(null);
            reqItem.setRejectionReasonText(null);
            request.setRejectionReasonText(null);
            request.setRequestRejectionReason(null);

            enforceBusinessRules(reqItem, user, false);
            depReq.checkForConflicts(modDifferences);
        }

        return new ProcessedRequestVo(item, request, warnings);
    }

    /**
     * Checks the revision number of the item to perform the optimistic locking check.
     * 
     * @param item ManagedItemVo to create
     * @return ManagedItemVo
     * @throws ItemNotFoundException if given item cannot be found in the database by ID
     * @throws OptimisticLockingException if revision ID from ManagedItemVo in database is different
     */
    protected long checkRevisionNumber(ManagedItemVo item) throws ItemNotFoundException, OptimisticLockingException {
        ManagedItemDomainCapability managedItemDomainCapability =
            managedItemCapabilityFactory.getDomainCapability(item.getEntityType());

        long revisionNumber = managedItemDomainCapability.getRevisionNumber(item.getId());

        if (item.getRevisionNumber() != revisionNumber) {

            ItemAuditHistoryVo iah = null;

            Collection<ItemAuditHistoryVo> iahs =
                itemAuditHistoryDomainCapability.retreiveByAuditItemId(item.getId(), item.getEntityType().name());

            if (iahs != null && !iahs.isEmpty()) {
                iah = iahs.iterator().next();
            }

            throw new OptimisticLockingException(OptimisticLockingException.ITEM_MODIFIED_BY_ANOTHER_USER,
                new Object[] { iah.getCreatedBy() });
        }

        return revisionNumber + 1;
    }

    /**
     * Compare the old and updated ManagedItemVo for differences and check if there are any warnings.
     * 
     * @param oldItem original {@link ManagedItemVo}
     * @param updatedItem updated {@link ManagedItemVo}
     * @param user {@link UserVo} updating {@link ManagedItemVo}
     * @return {@link ModificationSummaryVo} with Collection of {@link ModDifferenceVo} and {@link Errors} as warnings
     * @throws ValidationException if error validating updated {@link ManagedItemVo}
     */
    @Override
    public ModificationSummaryVo submitModifications(ManagedItemVo oldItem, ManagedItemVo updatedItem, UserVo user)
        throws ValidationException {

        RulesCapability rulesCapability = getRulesCapability(updatedItem.getEntityType());

        Collection<ModDifferenceVo> modDifferences = oldItem.compareDifferences(updatedItem);
        rulesCapability.enforceRules(updatedItem, user, modDifferences, false);
        Errors warnings = rulesCapability.checkForWarnings(updatedItem, modDifferences, false);

        return new ModificationSummaryVo(modDifferences, warnings);
    }

    /**
     * Applies the mod difference to all of the items in the list. Also retrieves the fully populated version of the times
     * 
     * This method is essentially a copy of one from the presentation layer's DifferenceUtility, with the addition of it
     * pulling the fully populated VOs from the database
     * 
     * @param modDifferences ModDifferenceVo
     * @param items items to apply the change to
     * @param user userVo
     * @return Collection<ItemModDifferenceVo>
     * @throws ItemNotFoundException ItemNotFoundException
     */
    @Override
    public Collection<ItemModDifferenceVo> submitAllModifications(Collection<ModDifferenceVo> modDifferences,
        Collection<ManagedItemVo> items, UserVo user) throws ItemNotFoundException {
        List<ItemModDifferenceVo> itemModDifferences = new ArrayList<ItemModDifferenceVo>(items.size());

        for (ManagedItemVo partialItem : items) {
            ManagedItemVo item = retrieve(partialItem.getId(), partialItem.getEntityType());
            List<ModDifferenceVo> differences = new ArrayList<ModDifferenceVo>();

            for (ModDifferenceVo difference : modDifferences) {

                Object currentValue = ReflectionUtility.getValue(item, difference.getDifference().getFieldKey());

                if (currentValue == null) {

                    // cew: added this on 1/9/2012 to allow for strings being null in the database.
                    if (StringUtils.isNotBlank(difference.getDifference().getNewValueShortString().toString())) {
                        differences.add(createModDifference(item, difference, user));
                    }
                } else {               

                    // If the value is changing for this item then create a mod difference.
                    differences.add(createModDifference(item, difference, user));     
                }
            }

            itemModDifferences.add(new ItemModDifferenceVo(item, differences));
        }
        
        return itemModDifferences;
    }
    
    /**
     * Applies the mod difference to all of the items in the list. Also retrieves the fully populated version of the times
     * 
     * @param modDifferences ModDifferenceVo
     * @param items items to apply the change to
     * @param user userVo
     * @return Collection<ItemModDifferenceVo>
     * @throws ItemNotFoundException ItemNotFoundException
     */
    @Override
    public Collection<ItemModDifferenceVo> submitGroupModifications(Collection<ModDifferenceVo> modDifferences,
        Collection<ManagedItemVo> items, UserVo user) throws ItemNotFoundException {
        List<ItemModDifferenceVo> itemModDifferences = new ArrayList<ItemModDifferenceVo>(items.size());

        for (ManagedItemVo partialItem : items) {
            ManagedItemVo item = retrieve(partialItem.getId(), partialItem.getEntityType());
            
            List<ModDifferenceVo> differences = new ArrayList<ModDifferenceVo>();

            for (ModDifferenceVo difference : modDifferences) {
                Object currentValue = ReflectionUtility.getValue(item, difference.getDifference().getFieldKey());
                
                if (currentValue == null) {
                    
                    // cew: added this on 1/9/2012 to allow for strings being null in the database.
                    if (StringUtils.isNotBlank(difference.getDifference().getNewValueShortString().toString())) {
                        differences.add(createModDifference(item, difference, user));
                    }
                } else {
                    differences.add(createGroupModDifference(item, difference, user));    
                }
            }

            itemModDifferences.add(new ItemModDifferenceVo(item, differences));
        }
        
        return itemModDifferences;
    }

    /**
     * Create a {@link ModDifferenceVo} for the given {@link ManagedItemVo} based upon the given {@link ModDifferenceVo}.
     * <p>
     * The result is a new {@link ModDifferenceVo} with its new value the same as the given {@link ModDifferenceVo} new
     * value, but its old value is equal to that on the given {@link ManagedItemVo}.
     * 
     * Note: This method was copied from the Presentation Layer DifferenceUtility when the code was moved from the
     * presentation layer
     * 
     * @param item {@link ManagedItemVo} for which to create a {@link ModDifferenceVo}
     * @param modDifference {@link ModDifferenceVo} from which to base the new {@link ModDifferenceVo}
     * @param user {@link UserVo} performing operation
     * @return {@link ModDifferenceVo}
     */
    private ModDifferenceVo createModDifference(ManagedItemVo item, ModDifferenceVo modDifference, UserVo user) {
        Object currentValue = ReflectionUtility.getValue(item, modDifference.getDifference().getFieldKey());

        Difference newDiff =
            new Difference(modDifference.getDifference().getFieldKey(), currentValue, modDifference.getDifference()
                .getNewValue());
        
        ModDifferenceVo newModDifference =
            new ModDifferenceVo(newDiff, modDifference.getRequestor(), modDifference.getModificationReason(), true, false,
                true, modDifference.getReviewer(), modDifference.getModRequestItemStatus(), modDifference.getReviewerNote(),
                modDifference.getComments());

        if (currentValue instanceof DataField) {
            DataField dataField = (DataField) currentValue;

            if (dataField.compareDifferences((ValueObject) newDiff.getNewValue()).size() == 0) {
                newModDifference.setChangeable(false);
                newModDifference.setAcceptChange(false);
            }

            if (!dataField.isEditable() && (!user.hasRole(Role.PSS_PPSN_SUPERVISOR) && !user.hasRole(Role.PSS_PPSN_MANAGER))) {

                // NATIONAL_ADMINISTRATIVE_MANAGER
                //NATIONAL_SERVICE_MANAGER

                newModDifference.setEditable(false);
                newModDifference.setAcceptChange(false);
            }
        } else {

            // is this a change to local use on the managed item vo? this is an oddball case
            if (FieldKey.LOCAL_USE.equals(modDifference.getDifference().getFieldKey())) {
                if (newDiff.getNewValue().equals(item.isLocalUse())) {
                    newModDifference.setChangeable(false);
                    newModDifference.setAcceptChange(false);
                }
            }
        }

        return newModDifference;
    }
    
    /**
     * Create a {@link ModDifferenceVo} for the given {@link ManagedItemVo} based upon the given {@link ModDifferenceVo}.
     * <p>
     * The result is a new {@link ModDifferenceVo} with its new value the same as the given {@link ModDifferenceVo} new
     * value, but its old value is equal to that on the given {@link ManagedItemVo}.
     * 
     * Note: This method is for GroupListDataFields
     * 
     * @param item {@link ManagedItemVo} for which to create a {@link ModDifferenceVo}
     * @param modDifference {@link ModDifferenceVo} from which to base the new {@link ModDifferenceVo}
     * @param user {@link UserVo} performing operation
     * @return {@link ModDifferenceVo}
     */
    private ModDifferenceVo createGroupModDifference(ManagedItemVo item, ModDifferenceVo modDifference, UserVo user) {
        Object currentValue = ReflectionUtility.getValue(item, modDifference.getDifference().getFieldKey());   

        boolean fieldChanged = false;
        boolean isNewRow = false;
        boolean removedRow = false;

        Object[] oldValue = ((Collection) modDifference.getDifference().getOldValue()).toArray();
        String oldValueShortString =  modDifference.getDifference().getOldValueShortString();
        String originalShortString = oldValueShortString;  
        Object[] newValue = ((Collection) modDifference.getDifference().getNewValue()).toArray();
        String newValueShortString =  modDifference.getDifference().getNewValueShortString();
        
        if (newValue.length > oldValue.length) {
            isNewRow = true;
            
            if (!oldValueShortString.equalsIgnoreCase(NO_SELECTION)) {
                fieldChanged = true;
            }  
        }
        
        if (newValue.length == oldValue.length) {
            
            //assumption that if the primary value is changed, a row was removed and another added
            String[] oldValuesString = oldValueShortString.split(" ");
            String[] newValuesString = newValueShortString.split(" ");
            
            if (oldValuesString[7].equalsIgnoreCase(newValuesString[7])) {
                fieldChanged = true;
            } else {
                removedRow = true;
            }
        }
        
        if (newValue.length < oldValue.length) {
            removedRow = true;
        }
        
        List groupDiff = ListUtils.subtract(Arrays.asList(newValue), Arrays.asList(oldValue));
        
        GroupDifference newDiff =
            new GroupDifference(modDifference.getDifference().getFieldKey(), currentValue, groupDiff,
                isNewRow, fieldChanged, removedRow, originalShortString, newValueShortString);
        
        ModDifferenceVo newModDifference =
            new ModDifferenceVo(newDiff, modDifference.getRequestor(), modDifference.getModificationReason(), true, false,
                true, modDifference.getReviewer(), modDifference.getModRequestItemStatus(), modDifference.getReviewerNote(),
                modDifference.getComments());

        return newModDifference;
    }


    /**
     * Commit the modifications to the ManagedItemVo and the ManagedItemVo itself. Default implementation throws
     * {@link UnsupportedOperationException}.
     * 
     * @param modDifferences differences between the old and new ManagedItemVo
     * @param managedItem original ManagedItemVo without changes detailed in modDifferences parameter
     * @param user UserVo performing commit
     * @return ProcessedItemVo with updated ManagedItemVo and warnings
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    @Override
    public final ProcessedItemVo commitModifications(Collection<ModDifferenceVo> modDifferences, ManagedItemVo managedItem,
        UserVo user) throws ValidationException {
        ManagedItemVo updatedItem = managedItem;
        checkModificationPreconditions(modDifferences, updatedItem, user);

        // Check to see if the item going into an inactive state was an accepted mod difference
        if (isItemInactivated(modDifferences)) {
            ManagedItemDomainCapability capability =
                managedItemCapabilityFactory.getDomainCapability(managedItem.getEntityType());

            capability.checkForActiveDependencies(managedItem, user);
        }

        Collection<ModDifferenceVo> acceptedModDifferences = filterAcceptedDifferences(modDifferences);

        if (acceptedModDifferences != null && !acceptedModDifferences.isEmpty()) {
            ManagedItemVo itemCopy = updatedItem.copy();

            // This will not apply any changes to the item if any of the changes require second review
            itemCopy.updateFromModDifferences(acceptedModDifferences, user, getEnvironmentUtility());

            boolean willGenerateRequest =
                itemCopy.hasModifiedSecondReviewField(acceptedModDifferences, getEnvironmentUtility().getEnvironment(), user);

            // If a request is going to be generated, a copy of the copy needs to get created to check business rules
            if (willGenerateRequest) {

                // Create an item purely for validation
                ManagedItemVo validationItem = itemCopy.copy();

                // Apply all accepted changes to the validation item, even those that require second review
                // This has to be done so the item can pass validation 
                for (ModDifferenceVo modDiff : acceptedModDifferences) {
                    validationItem.updateFieldToNewValue(modDiff.getDifference());
                }

                // Enforce the business rules with the validation item
                enforceBusinessRules(validationItem, user, false);
            } else {
                enforceBusinessRules(itemCopy, user, true);
            }

            long revisionNumber = checkRevisionNumber(managedItem);
            itemCopy.setRevisionNumber(revisionNumber);

            // There's a bit of inconsistency in how the update method returns items, so pulling the IAH out now so it does
            // not get overridden - there should be no new iah entries at this point anyway
            ItemAuditHistoryVo mods = itemCopy.getNewAuditHistory();
            itemCopy = updateItem(itemCopy, user);

            saveItemAuditHistoryRecords(mods, user);

            itemCopy.setNewAuditHistory(null);

            RequestVo acceptedRequest = checkForExistingRequest(itemCopy);

            //If there isn't a request out there already, make one, otherwise use the existing request
            if (acceptedRequest == null) {
                acceptedRequest = createModificationRequest(acceptedModDifferences, itemCopy, user);
            } else { // This should never be executed since the UI directs the user into a req flow if there is an existing req
                ProcessedRequestVo processedRequest = commitRequest(updatedItem, acceptedRequest, acceptedModDifferences, user,
                    false);
                itemCopy = processedRequest.getItem();
            }

            saveItemAuditHistoryRecords(itemCopy.getNewAuditHistory(), user);

            //PPS-L functionality
            //sendModificationToPeps(request, itemCopy, user, mods);

            // collect differences for VistA
            List<Difference> differences = new ArrayList<Difference>();

            for (ModDifferenceVo modDiff : acceptedModDifferences) {
                differences.add(modDiff.getDifference());
            }

//            if (itemCopy.isProductItem()) {
//                updateSpecialHandlingBasedOnDrugClassChange(itemCopy, user, managedItem, acceptedModDifferences);
//            }

            // Send the modified item to VistA 
            if (acceptedRequest == null || acceptedRequest.isCompleted()) {

                //Cascade the category changes
                if (itemCopy.isOrderableItemItem()) {
                    cascadeChangesToChildren(itemCopy, user, managedItem, acceptedModDifferences);
                }

                if (vistaFileSynchCapability.sendModifiedItemToVista(itemCopy, 
                    differences, user, okToSendToVista(), catchingUp())) {
                    addToQueue(itemCopy, itemCopy.getEntityType(), ItemAction.MODIFY, user);
                }

            }

            updatedItem = itemCopy;
        }

        Errors warnings =
            getRulesCapability(updatedItem.getEntityType()).checkForWarnings(updatedItem, acceptedModDifferences, false);

        return new ProcessedItemVo(updatedItem, warnings);
    }

    /**
     * Commit all the modifications to the given items.
     * 
     * @param itemModDifferences List<ItemModDifferenceVo> differences between the old and new {@link ManagedItemVo}
     * @param user UserVo performing commit
     * @return updated ManagedItemVos
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    @Override
    public final Collection<ProcessedItemVo> commitAllModifications(Collection<ItemModDifferenceVo> itemModDifferences,
        UserVo user) throws ValidationException {
        Collection<ProcessedItemVo> items = new ArrayList<ProcessedItemVo>(itemModDifferences.size());

        for (ItemModDifferenceVo itemModDifference : itemModDifferences) {
            items.add(commitModifications(itemModDifference.getDifferences(), itemModDifference.getItem(), user));
        }

        return items;
    }

    /**
     * Create a RequestVo and save it to the database for the given modified ManagedItemVo.
     * 
     * If no request is necessary, return null.
     * 
     * @param modDifferences Differences in the given item
     * @param managedItem ManagedItemVo to create request for
     * @param user UserVo creating request
     * @return add RequestVo created (null if no request necessary)
     * @throws ValidationException 
     */
    protected abstract RequestVo createModificationRequest(Collection<ModDifferenceVo> modDifferences,
        ManagedItemVo managedItem, UserVo user) throws ValidationException;

    /**
     * Send the given modified item to either local or national, depending on which location we're currently at.
     * 
     * @param request {@link RequestVo} to send
     * @param managedItem {@link ManagedItemVo} to send
     * @param user {@link UserVo} sending the item
     * @param modifications of type ItemAuditHistoryVo
     */
    protected abstract void sendModificationToPeps(RequestVo request, ManagedItemVo managedItem, UserVo user,
        ItemAuditHistoryVo modifications);

    /**
     * Update the given ManagedItemVo in the database.
     * 
     * @param managedItem ManagedItemVo to update
     * @param user UserVo updating item
     * @return updated ManagedItemVo
     * @throws DuplicateItemException DuplicateItemException
     */
    protected ManagedItemVo updateItem(ManagedItemVo managedItem, UserVo user) throws DuplicateItemException {
        ManagedItemDomainCapability capability = managedItemCapabilityFactory.getDomainCapability(managedItem.getEntityType());

        return capability.update(managedItem, user);
    }

    /**
     * Verify that the UserVo and ManagedItemVo are not null and the user is a manager.
     * 
     * Check that the item has not been updated already. If it has not, update the revision number on the item.
     * 
     * @param modDifferences differences between the old and new item
     * @param managedItem ManagedItemVo changed
     * @param user UserVo making changes
     * @throws ValidationException if error validating data for preconditions
     */
    protected void checkModificationPreconditions(Collection<ModDifferenceVo> modDifferences, ManagedItemVo managedItem,
        UserVo user) throws ValidationException {
        if (user == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, FieldKey.USER.getLocalizedName(Locale.getDefault()));
        }

        if (managedItem == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, MANAGED_ITEM);
        }

        // Only local and national managers are allowed to make a modification to an item, the button should be gone
        // Preventing them from doing this in the code as well as a safety precaution
        if (!user.isEitherManager()) {
            throw new AuthorizationException(AuthorizationException.NOT_AUTHORIZED);
        }

        if (managedItem instanceof ManagedDataVo) {
            ManagedDataVo data = (ManagedDataVo) managedItem;

            if (!data.getDomainGroup().canEdit(environmentUtility.getEnvironment())) {
                String item =
                    FieldKey.getKey(EntityType.toFieldKey(data.getEntityType())).getLocalizedName(Locale.getDefault());
                String environment = environmentUtility.getEnvironment().getLocalizedName(Locale.getDefault());

                throw new BusinessRuleException(BusinessRuleException.CANNOT_EDIT, item, environment);
            }
        }

        checkInactivateReason(modDifferences);
    }

    /**
     * PEPS981 Ensure if a domain is being inactivated, it has a reason in the difference.
     *
     * @param modDifferences differences between the old and new item
     * @throws ValueObjectValidationException e
     */
    protected void checkInactivateReason(Collection<ModDifferenceVo> modDifferences) throws ValueObjectValidationException {
        for (ModDifferenceVo modDifference : modDifferences) {

            boolean itemStatusField = modDifference.getDifference().getFieldKey() == FieldKey.ITEM_STATUS;
            boolean commentsEmpty = StringUtils.isEmpty(modDifference.getModificationReason());
            boolean inactivate = modDifference.getDifference().getOldValue() == ItemStatus.ACTIVE;

            if (itemStatusField && commentsEmpty && inactivate) {
                Errors errors = new Errors();
                errors.addError(FieldKey.MODIFICATION_REASON, ErrorKey.INACTIVATION_REASON_REQUIRED);
                throw new ValueObjectValidationException(errors);
            }
        }
    }

    /**
     * Verify that the UserVo and ManagedItemVo are not null and the user is a manager.
     * 
     * Check that the item has not been updated already. If it has not, update the revision number on the item.
     * 
     * @param managedItem ManagedItemVo changed
     * @param user UserVo creating this item
     * @throws ValidationException if error validating preconditions
     */
    protected void checkCreationPreconditions(ManagedItemVo managedItem, UserVo user) throws ValidationException {
        if (user == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, FieldKey.USER.getLocalizedName(Locale.getDefault()));
        }

        if (managedItem == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, MANAGED_ITEM);
        }

        // Only local and national managers are allowed to make an item, the button should be gone
        // Preventing them from doing this in the code as well as a safety precaution
        if (!user.isEitherManager()) {
            throw new AuthorizationException(AuthorizationException.NOT_AUTHORIZED);
        }

        if (managedItem instanceof ManagedDataVo) {
            ManagedDataVo data = (ManagedDataVo) managedItem;

            if (!data.getDomainGroup().canAdd(environmentUtility.getEnvironment())) {
                String item =
                    FieldKey.getKey(EntityType.toFieldKey(data.getEntityType())).getLocalizedName(Locale.getDefault());
                String environment = environmentUtility.getEnvironment().getLocalizedName(Locale.getDefault());

                throw new BusinessRuleException(BusinessRuleException.CANNOT_ADD, item, environment);
            }
        }
    }

    /**
     * Commit the request, modifications to the ManagedItemVo, and the ManagedItemVo itself.
     * 
     * @param oldItem original ManagedItemVo, without any modifications from the {@link RequestVo} or from the given
     *            Collection of {@link ModDifferenceVo}
     * @param request RequestVo to commit to the database to fulfill this request.
     * @param differences Collection of {@link ModDifferenceVo} between the old and new ManagedItemVo
     * @param user UserVo performing commit
    * @param ignoreUserRule if true the system will approve the request even if the user making the request to apprve
     *          was the same user who did the last action on the request.
     * @return {@link ProcessedRequestVo} Updated item and request information
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    @Override
    public ProcessedRequestVo commitRequest(ManagedItemVo oldItem, RequestVo request, Collection<ModDifferenceVo> differences,
        UserVo user, boolean ignoreUserRule) throws ValidationException {

        // Let sub-classes specialize this method!
        throw new UnsupportedOperationException();
    }

    /**
     * Submit the request, modifications to the ManagedItemVo, and the ManagedItemVo itself.
     * <p>
     * Enforce the business rules via {@link #enforceBusinessRules(ManagedItemVo, UserVo)} and check the {@link RequestVo}
     * and differences Collection for conflicts. Does not commit the {@link RequestVo} to the database. Finally, set the last
     * reviewer on the request, the request details, and the given {@link ModDifferenceVo} as the given {@link UserVo}.
     * <p>
     * Does not commit changes to the database!
     * 
     * @param item ManagedItemVo to begin commit steps with, with the user's latest updates but not the accepted reqs.
     * @param request RequestVo to commit
     * @param differences Collection of {@link ModDifferenceVo} between the old and new ManagedItemVo
     * @param user UserVo performing commit
     * @return RequestUpdateInformation Updated item and request information
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    @Override
    public ProcessedRequestVo submitRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> differences,
        UserVo user) throws ValidationException {
        request.adjustNewModDifferences(differences);

        enforceBusinessRules(item, user, false);
        request.checkForConflicts(differences);
        
        request.setDependentRequests(this.getDependenciesToApproveOrReject(item, true));
    
        // request.setLastReviewer(user);

        for (ModDifferenceVo modDifferenceVo : differences) {
            modDifferenceVo.setReviewer(user);
        }

        for (ModDifferenceVo modDifferenceVo : request.getRequestDetails()) {
            modDifferenceVo.setReviewer(user);
        }

        // Create a collection including all diffs - from original request and from possible mods to check for warnings
        Collection<ModDifferenceVo> warningDiffs = new ArrayList<ModDifferenceVo>(differences);
        warningDiffs.addAll(request.getRequestDetails());

        Errors warnings = getRulesCapability(item.getEntityType()).checkForWarnings(item, warningDiffs, false);

        return new ProcessedRequestVo(item, request, warnings);
    }

    /**
     * Apply the changes, in memory, specified in the given {@link RequestVo#getRequestDetails()}.
     * <p>
     * This method does not commit the changes to the database!
     * 
     * @param oldItem original {@link ManagedItemVo} (from the database, without changes) on which to apply changes
     * @param request {@link RequestVo} making the changes, including all of the recently accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} making the request
     * @return The ManagedItemVo passed it.
     */
    @Override
    public ManagedItemVo applyChanges(ManagedItemVo oldItem, RequestVo request, UserVo user) {

        // Default operation is a no-op.
        return oldItem;
    }

    /**
     * Create a new {@link ManagedItemVo}.
     * 
     * @param managedItem ManagedItemVo to insert
     * @param user UserVo
     * @return ManagedItemVo
     * @throws ValidationException if validation does not work
     */
    @Override
    public final synchronized ProcessedItemVo create(ManagedItemVo managedItem, UserVo user) throws ValidationException {
        checkCreationPreconditions(managedItem, user);
        setAddStatus(managedItem);

        // This section used to set the NDCS Fields for the NDC Rules capability to update correctly.
        ManagedItemVo beforeInsert = null;  
        
        if (managedItem instanceof NdcVo) {
            try {
                beforeInsert = drugReferenceCapability.populateFdbNdcFields((NdcVo) managedItem);
            } catch (FDBException e) {
                LOG.debug("No NDC Found in FDB.");
                beforeInsert = managedItem;
            }
        } else {
            beforeInsert = managedItem;
        }

        enforceBusinessRules(beforeInsert, user, true);

        ManagedItemVo insertedItem = insertItem(beforeInsert, user);
        createAddRequest(insertedItem, user);
        createAddItemAuditHistory(insertedItem, user);
       
        if (!managedItem.isTwoReviewItem()) {
            
            if (vistaFileSynchCapability.sendNewItemToVista(managedItem, user,
                okToSendToVista(), catchingUp())) {
                addToQueue(managedItem, managedItem.getEntityType(), ItemAction.ADD, user);
            }

            insertedItem = update(managedItem, user);
        }

        Errors warnings = new Errors();
        warnings = getRulesCapability(insertedItem.getEntityType()).checkForWarnings(insertedItem, null, true);

        return new ProcessedItemVo(insertedItem, warnings);
    }

    /**
     * Execute the ManagedItemVo's Business Rules Capability, this includes VO validation and cross-item business rules.
     * 
     * @param managedItem ManagedItemVo to check business rules on
     * @param user UserVo performing check
     * @param canPerformUpdate boolean indicating if the call is allowed to perform updates on other items
     * @throws ValidationException if error enforcing rules
     */
    protected void enforceBusinessRules(ManagedItemVo managedItem, UserVo user, boolean canPerformUpdate)
        throws ValidationException {
        RulesCapability rules = managedItemCapabilityFactory.getRulesCapability(managedItem.getEntityType());
        rules.enforceRules(managedItem, user, canPerformUpdate);
    }

    /**
     * Set the status on a ManagedItemVo to be added.
     * 
     * @param managedItem ManagedItemVo to alter
     */
    protected abstract void setAddStatus(ManagedItemVo managedItem);

    /**
     * populateFdbFields
     * @param managedItem managedItem
     * @param user user
     * @return ManagedItemVo
     */
    public ManagedItemVo populateFdbFields(ManagedItemVo managedItem, UserVo user) {

        if (EntityType.NDC.equals(managedItem.getEntityType())) {
            NdcVo ndc = (NdcVo) managedItem;

            try {
                ndc = drugReferenceCapability.populateFdbNdcFields(ndc);
            } catch (FDBException e) {
                LOG.debug(e);
            }

            if (ndc.getFdbNdcVo() != null) {
                ndc.getFdbNdcVo().setNdcIdFk(Long.valueOf(managedItem.getId()));

                if (fdbNdcDomainCapability.retrieve(Long.valueOf(ndc.getId())) == null) {
                    fdbNdcDomainCapability.create(ndc.getFdbNdcVo(), user);
                }
            }

            return ndc;
        } else if (EntityType.PRODUCT.equals(managedItem.getEntityType())) {
            ProductVo product = (ProductVo) managedItem;

            try {
                product.setFdbProductVo(drugReferenceCapability.populateFdbProductFields(product.getGcnSequenceNumber()));
            } catch (FDBException e) {
                LOG.debug(e);
            }

            if (product.getFdbProductVo() != null) {
                product.getFdbProductVo().setProductIdFk(Long.valueOf(managedItem.getId()));

                if (fdbProductDomainCapability.retrieve(Long.valueOf(product.getId())) == null) {
                    fdbProductDomainCapability.create(product.getFdbProductVo(), user);
                }
            }

            return product;
        } else {
            return managedItem;
        }
    }

    /**
     * Insert the ManagedItemVo into the database.
     * 
     * @param managedItem ManagedItemVo to insert
     * @param user UserVo inserting item
     * @return inserted item
     * 
     * @throws DuplicateItemException if this item is a duplicate of another according to its rules
     */
    @Override
    public ManagedItemVo insertItem(ManagedItemVo managedItem, UserVo user) throws DuplicateItemException {
        ManagedItemDomainCapability capability = managedItemCapabilityFactory.getDomainCapability(managedItem.getEntityType());

        ManagedItemVo insertedVo = capability.create(managedItem, user);

        return populateFdbFields(insertedVo, user);
    }

    /**
     * Create a RequestVo and save it to the database for the given new ManagedItemVo.
     * 
     * @param managedItem ManagedItemVo to create request for
     * @param user UserVo creating request
     * @return add RequestVo created
     */
    protected abstract RequestVo createAddRequest(ManagedItemVo managedItem, UserVo user);

    /**
     * Create and save the IAH records for an ADD of a ManagedItemVo by the given user.
     * 
     * @param managedItem ManagedItemVo for which to create IAH
     * @param user UserVo adding the item
     */
    protected void createAddItemAuditHistory(ManagedItemVo managedItem, UserVo user) {

        ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
        itemAudit.setAuditItemType(managedItem.getEntityType());
        itemAudit.setAuditItemId(managedItem.getId());
        itemAudit.setSiteName(environmentUtility.getSiteNumber()); 
        itemAudit.setEventCategory(EventCategory.REQUEST_TO_ADD);
        itemAudit.setReason(""); 
        itemAudit.setOldValue("");
        itemAudit.setNewValue("");
        itemAudit.setUsername(user.getUsername());
        
        managedItem.setNewAuditHistory(itemAudit);
        saveItemAuditHistoryRecords(itemAudit, user);
    }

    /**
     * A method to generate IAH entries for an inactivated item. For use in the RulesCapability when we inactivate products
     * 
     * @param managedItem inactivated item
     * @param user user spawning this
     * @param reasonText Reason text
     */
    @Override
    public void createInactivationItemAuditHistory(ManagedItemVo managedItem, UserVo user, String reasonText) {
        ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
        itemAudit.setAuditItemType(managedItem.getEntityType());
        itemAudit.setAuditItemId(managedItem.getId());
        itemAudit.setSiteName(environmentUtility.getSiteNumber()); 
        itemAudit.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
        itemAudit.setReason(reasonText); 
        itemAudit.setOldValue("");
        itemAudit.setNewValue("");
        itemAudit.setUsername(user.getUsername());
        
        ItemAuditHistoryDetailsVo itemAuditDetail =
            new ItemAuditHistoryDetailsVo(EventCategory.APPROVED_REQUEST, "", FieldKey.ITEM_STATUS.getKey(),
                ItemStatus.ACTIVE.toString(), ItemStatus.INACTIVE.toString());
        itemAudit.addDetail(itemAuditDetail);

        saveItemAuditHistoryRecords(itemAudit, user);

    }

    /**
     * Send the given new item to either local or national, depending on which location we're currently at.
     * 
     * @param request {@link RequestVo} to send
     * @param managedItem {@link ManagedItemVo} to send
     * @param user {@link UserVo} sending the item
     */
    protected abstract void sendAddToPeps(RequestVo request, ManagedItemVo managedItem, UserVo user);

    /**
     * Performs the actions to create an Item Audit History record for each audit record using the notification to group the
     * records together for future retrieval.
     * 
     * @param itemAudit IAH record to save
     * @param user {@link UserVo} performing the action
     */
    @Override
    public void saveItemAuditHistoryRecords(ItemAuditHistoryVo itemAudit, UserVo user) {
        if (!(itemAudit == null)) {
            NotificationVo notification = new NotificationVo();
            ArrayList<ItemAuditHistoryVo> itemAudits = new ArrayList<ItemAuditHistoryVo>();
            itemAudits.add(itemAudit);
            notification.setItemAudits(itemAudits);
            notification.determineNotificationType();
            notificationDomainCapability.create(notification, user);
        }
    }

    /**
     * Delete the ManagedItemVo.
     * 
     * @param managedItem managedItemVo
     * @throws ValidationException if cannot find the PartialSaveVo for the given ID
     */
    @Override
    public void deleteItem(ManagedItemVo managedItem) throws ValidationException {

        ManagedItemDomainCapability capability = managedItemCapabilityFactory.getDomainCapability(managedItem.getEntityType());
        capability.deleteItem(managedItem);
        this.requestDomainCapability.delete(managedItem);
    }
    
    /**
     * Delete the partially saved ManagedItemVo.
     * 
     * @param partialId of type String
     * @param itemType Type of partial item to retrieve
     * @return deleted partial ManagedItemVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    @Override
    public ManagedItemVo deletePartial(String partialId, EntityType itemType) throws ItemNotFoundException {

        ManagedItemVo managedItemVo = null;

        try {
            managedItemVo = retrievePartialItem(partialId, itemType);
        } catch (ValidationException e) {
            LOG.debug("Ignoring exception during deletion of partial item.");
        }

        return managedItemVo;
    }

    /**
     * Builds a new Collection of {@link ModDifferenceVo} where each difference is accepted.
     * 
     * @param differences Collection of {@link ModDifferenceVo} instances to filter
     * @return Collection<ModDifferenceVo> filtered {@link ModDifferenceVo}, containing only accepted differences
     * 
     * @see ModDifferenceVo#isAcceptChange()
     */
    protected Collection<ModDifferenceVo> filterAcceptedDifferences(Collection<ModDifferenceVo> differences) {
        Collection<ModDifferenceVo> acceptedDifferences = new ArrayList<ModDifferenceVo>();

        for (ModDifferenceVo difference : differences) {
            if (difference.isAcceptChange()) {
                acceptedDifferences.add(difference);
            }
        }

        return acceptedDifferences;
    }

    /**
     * Builds a new Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal
     * {@link RequestItemStatus#APPROVED}.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * 
     * @return Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal to
     *         {@link RequestItemStatus#APPROVED}
     */
    protected Collection<ModDifferenceVo> filterApprovedDifferences(Collection<ModDifferenceVo> differences) {
        return filterDifferences(differences, RequestItemStatus.APPROVED);
    }

    /**
     * Builds a new Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal
     * {@link RequestItemStatus#PENDING}.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * 
     * @return Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal to
     *         {@link RequestItemStatus#PENDING}
     */
    protected Collection<ModDifferenceVo> filterPendingDifferences(Collection<ModDifferenceVo> differences) {
        return filterDifferences(differences, RequestItemStatus.PENDING);
    }

    /**
     * Builds a new Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal
     * {@link RequestItemStatus#REJECTED}.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * 
     * @return Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal to
     *         {@link RequestItemStatus#REJECTED}
     */
    protected Collection<ModDifferenceVo> filterRejectedDifferences(Collection<ModDifferenceVo> differences) {
        return filterDifferences(differences, RequestItemStatus.REJECTED);
    }

    /**
     * Builds a new Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal to the one given.
     * <p>
     * The given {@link RequestItemStatus} must not be null.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * @param status the {@link RequestItemStatus}, must not be null
     * 
     * @return Collection of {@link ModDifferenceVo} where the {@link RequestItemStatus} is equal to the one given
     */
    protected Collection<ModDifferenceVo> filterDifferences(Collection<ModDifferenceVo> differences, RequestItemStatus status) {
        Collection<ModDifferenceVo> filteredDifferences = new ArrayList<ModDifferenceVo>();

        for (ModDifferenceVo difference : differences) {
            if (status.equals(difference.getModRequestItemStatus())) {
                filteredDifferences.add(difference);
            }
        }

        return filteredDifferences;
    }

    /**
     * The given user marks the given request under review for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to mark under review
     * @param user UserVo setting request review status
     * @return updated RequestVo
     */
    @Override
    public RequestVo markRequestUnderReview(ManagedItemVo item, RequestVo request, UserVo user) {
        request.setUnderReview(true);

        return request;
    }

    /**
     * The given user marks the given request as needing a PEPS second review for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to mark as needing a PEPS second review
     * @param user UserVo updating request
     * @return updated RequestVo
     */
    @Override
    public RequestVo markRequestForPsr(ManagedItemVo item, RequestVo request, UserVo user) {
        request.setMarkedForPepsSecondReview(true);
        request.setLastReviewer(user);

        return request;
    }

    /**
     * Reject the problem report as not a valid problem.  
     * Note: This is an unimplemented method until PPSL is implemented.
     * 
     * @param item ManagedItemVo
     * @param request RequestVo to reject
     * @param user UserVo
     * @return rejected RequestVo
     */
    @Override
    public RequestVo rejectProblemReport(ManagedItemVo item, RequestVo request, UserVo user) {
        throw new UnsupportedOperationException();
    }

    /**
     * The given user rejects the given request for the given item.
     * 
     * @param item ManagedItemVo with the request
     * @param request RequestVo to reject
     * @param newDifferences Collection<ModDifferenceVo> Newly-generated field-modifications.
     * @param user UserVo rejecting request
     * @return rejected RequestVo
     */
    @Override
    public RequestVo rejectRequest(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> newDifferences,
        UserVo user) {
        request.adjustNewModDifferences(newDifferences);

        request.setNewItemRequestStatus(RequestItemStatus.REJECTED);
        request.setUnderReview(false);
        request.setDependentRequests(getDependenciesToApproveOrReject(item, true));

        return request;
    }
    
    /**
     * Rescinds the previous rejection and puts the item back into a PENDING state.
     * 
     * @param managedItem original ManagedItemVo without changes detailed in modDifferences parameter
     * @param user UserVo performing commit
     * @return ProcessedItemVo processedItemVo
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    @Override
    public ProcessedItemVo commitRescindRejection(ManagedItemVo managedItem, UserVo user)
        throws ValidationException {
        checkCreationPreconditions(managedItem, user);
       
        managedItem.setRequestItemStatus(RequestItemStatus.PENDING);
        managedItem.setItemStatus(ItemStatus.ACTIVE);
        managedItem.setRejectionReasonText("");
        managedItem.setRequestRejectionReason(null);
        
        enforceBusinessRules(managedItem, user, true);

        ManagedItemVo updatedItem = updateItem(managedItem, user);
        createAddRequest(updatedItem, user);
        createAddItemAuditHistory(updatedItem, user);
        
       
        Errors warnings = new Errors();
        warnings = getRulesCapability(updatedItem.getEntityType()).checkForWarnings(updatedItem, null, true);

        return new ProcessedItemVo(updatedItem, warnings);
    }

    /**
     * Get all ManagedDataVo of the given {@link EntityType}. Only applies to managed domains. This prevents returning a
     * large list of all orderable items, products, or NDCs.
     * 
     * @param itemType {@link EntityType}
     * @return full list of the given domain
     */
    @Override
    public List<ManagedItemVo> retrieve(EntityType itemType) {
        List<ManagedItemVo> results = Collections.EMPTY_LIST;

        if (itemType.isDomainType()) {
            ManagedItemDomainCapability capability = managedItemCapabilityFactory.getDomainCapability(itemType);
            results = capability.retrieve();
        }

        return results;
    }

    /**
     * Retrieve by ID
     * 
     * @param itemId String ID
     * @param itemType EntityType
     * @return instance for given ID
     * @throws ItemNotFoundException e 
     */
    @Override
    public ManagedItemVo retrieve(String itemId, EntityType itemType) throws ItemNotFoundException {
        ManagedItemDomainCapability domainCapability = managedItemCapabilityFactory.getDomainCapability(itemType);
        ManagedItemVo managedItem = domainCapability.retrieve(itemId);
        
        return managedItem;
    }

    /**
     * Retrieve a minimally populated ManagedItemVo with the given ID.
     * <p>
     * The returned Value Object most likely only has enough data for the {@link ManagedItemVo#toShortString()} and
     * {@link ManagedItemVo#getId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ManagedItemVo} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * 
     * @param itemId String ID of the item to retrieve
     * @param itemType Type of item to retrieve
     * @return minimally populated ManagedItemVo
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    @Override
    public ManagedItemVo retrieveMinimal(String itemId, EntityType itemType) throws ItemNotFoundException {
        ManagedItemDomainCapability domainCapability = managedItemCapabilityFactory.getDomainCapability(itemType);

        return domainCapability.retrieveMinimal(itemId);
    }

    /**
     * Retrieve the ManaedItemVo with the passed in ID. 
     * If the item type has children, all children are returned fully populated.
     * <p>
     * Use the {@link UserVo#getTableSizePreference()} to determine the page size for the children, using the default sort
     * order. Only the first {@link UserVo#getTableSizePreference()} children will be populated! Additional children must be
     * retrieved by calling {@link #retrieveChildren(String, EntityType, int, FieldKey, SortOrder, int)}
     * 
     * @param itemId String Id (i.e. EplId) of the item to retrieve
     * @param itemType Type of item to retrieve
     * @param user {@link UserVo} from which to retrieve the page size
     * @return ManagedItemVo
     * 
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    @Override
    public ManagedItemVo retrieve(String itemId, EntityType itemType, UserVo user) throws ItemNotFoundException {
        
        ManagedItemDomainCapability domainCapability = managedItemCapabilityFactory.getDomainCapability(itemType);
        ManagedItemVo managedItem = domainCapability.retrieve(itemId);

        return managedItem;
    }

    /**
     * Retrieve a list of items for the given parent Item Id.
     * <p>
     * The returned PaginatedList contains a partial list of all the children for the given parent. The first row returned is
     * startRow with pageSize total rows returned. The list will be sorted by sortedFieldKey in sortOrder to limit the
     * potential for different result sets each call for the common version of the ManagedItemCapabilityImpl.
     * 
     * @param parentItemId String ID of parent for which to retrieve children items
     * @param parentItemType EntityType of parent for which to retrieve children items
     * @param sortedFieldKey FieldKey representing field to sort by
     * @param sortOrder SortOrder representing ascending or descending sort
     * @param startRow integer index in full list of child items from which to start retrieving
     * @param pageSize integer number of rows to retrieve
     * 
     * @return PaginatedList<ManagedItemVo> child items
     */
    @Override
    public PaginatedList<ManagedItemVo> retrieveChildren(String parentItemId, EntityType parentItemType,
        FieldKey sortedFieldKey, SortOrder sortOrder, int startRow, int pageSize) {
        ManagedItemDomainCapability capability = managedItemCapabilityFactory.getDomainCapability(parentItemType.getChild());

        return capability.retrieveChildren(parentItemId, sortedFieldKey, sortOrder, startRow, pageSize);
    }

    /**
     * Retrieve an blank template instance of the child to the current ManagedItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return blank template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    @Override
    public ManagedItemVo retrieveBlankChildTemplate(String parentId, EntityType parentType) throws ItemNotFoundException {
        ManagedItemVo parent = retrieve(parentId, parentType);
        ManagedItemVo template = parent.blankChildTemplate(getEnvironmentUtility().getEnvironment());
        template.setVaDataFields(dataFieldsCapability.retrieveDefaultDomain(template.getEntityType()));

        inheritPropertiesFromParent(template);

        return template;
    }

    /**
     * Retrieve an blank template instance of the local child to the current national ManagedItemVo. Only applies for
     * ManagedItemVo types that have a national/local relationship, which currently is only OrderableItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return blank template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    @Override
    public ManagedItemVo retrieveBlankLocalTemplate(String parentId, EntityType parentType) throws ItemNotFoundException {
        ManagedItemVo parent = null;

        if (parentId == null) {
            parent = new OrderableItemVo();
        } else {
            parent = retrieve(parentId, parentType);
        }

        ManagedItemVo template = parent.blankLocalTemplate(getEnvironmentUtility().getEnvironment());
        template.setVaDataFields(dataFieldsCapability.retrieveDefaultDomain(template.getEntityType()));

        return template;
    }

    /**
     * Retrieve an blank template instance of the current ManagedItemVo type.
     * 
     * @param itemType Type of item template to retrieve
     * @return blank template instance of ManagedItemVo
     */
    @Override
    public ManagedItemVo retrieveBlankTemplate(EntityType itemType) {
        ManagedItemVo template = ManagedItemVo.newInstance(itemType).blankTemplate(getEnvironmentUtility().getEnvironment());
        template.setVaDataFields(dataFieldsCapability.retrieveDefaultDomain(itemType));

        return template;
    }

    /**
     * Retrieves the list of children items for the given Parent Item Id
     * 
     * @param <T> type of ManagedItemVo
     * @param parentItemId String
     * @param parentItemType EntityType
     * @return List<ManagedItemVo>
     */
    @Override
    public <T extends ManagedItemVo> List<T> retrieveChildren(String parentItemId, EntityType parentItemType) {
        ManagedItemDomainCapability capability = managedItemCapabilityFactory.getDomainCapability(parentItemType.getChild());

        return capability.retrieveChildren(parentItemId);
    }

    /**
     * Retrieve an local template instance of the current national ManagedItemVo. Only applies for ManagedItemVo types that
     * have a national/local relationship, which currently is only OrderableItemVo.
     * 
     * @param parentId ManagedItemVo ID set as the parent of the blank template
     * @param parentType EntityType of parent
     * @return template instance of ManagedItemVo
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    @Override
    public ManagedItemVo retrieveLocalTemplate(String parentId, EntityType parentType) throws ItemNotFoundException {
        ManagedItemVo parent = retrieve(parentId, parentType);

        return parent.localTemplate(getEnvironmentUtility().getEnvironment());
    }

    /**
     * Retrieves the partially saved item database
     * 
     * @param partialId String
     * @param itemType EntityType
     * @return ManagedItemVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    @Override
    public ManagedItemVo retrievePartialItem(String partialId, EntityType itemType) throws ItemNotFoundException {

        PartialSaveVo partialSave = partialSaveMgtDomainCapability.retrieve(partialId);
        ManagedItemVo managedItemVo = null;

        if (partialSave == null) {
            LOG.error(ItemNotFoundException.ITEM_NOT_FOUND + ": " + partialId);
        } else {

            managedItemVo = SerializationUtility.deserialize(partialSave.getFileName());

            if (managedItemVo == null) {
                LOG.error(ItemNotFoundException.ITEM_NOT_FOUND + ": " + partialSave.getFileName());
            } else {
                managedItemVo.setPartialSave(true);

                try {
                    if (managedItemVo.getChildrenCount() > 0) {
                        ManagedItemDomainCapability capability =
                            managedItemCapabilityFactory.getDomainCapability(itemType.getChild());
                        managedItemVo.setChildren(capability.retrieveChildren(managedItemVo.getId()));
                    }
                } catch (Exception e) {
                    managedItemVo.setChildren(Collections.EMPTY_LIST);
                    managedItemVo.setChildrenCount(0);
                }
            }

            // delete the record from the data base
            partialSaveMgtDomainCapability.delete(partialId);
        }

        return managedItemVo;
    }

    /**
     * Retrieve the partially saved ManagedItemVo via the PartialSaveVo. Do not delete the PartialSaveVo from the database.
     * 
     * @param partialId String
     * @param itemType EntityType
     * @return PartialSaveVo
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    @Override
    public PartialSaveVo retrievePartialSave(String partialId, EntityType itemType) throws ItemNotFoundException {
        return partialSaveMgtDomainCapability.retrieve(partialId);
    }

    /**
     * Retrieve all partially saved {@link ManagedItemVo}.
     * 
     * @return List<PartialSaveVo> partially saved managed items
     */
    @Override
    public List<PartialSaveVo> retrievePartialSaves() {
        List<PartialSaveVo> partialSaves = partialSaveMgtDomainCapability.retrieveAll();
        List<PartialSaveVo> partialSaves1 = new ArrayList<PartialSaveVo>();

        for (PartialSaveVo vo : partialSaves) {

            File file = new File(vo.getFileName());

            // make sure the serialized file still exists.
            if (file != null && file.exists()) {

                partialSaves1.add(vo);
            } else {

                // nope, something happened to the file
                try {

                    // delete the partial save record
                    deletePartial(vo.getId(), vo.getEntityType());
                } catch (Exception e) {
                    LOG.debug("Ignore exception from deletion of partial item");
                }
            }
        }

        return partialSaves1;

    }

    /**
     * Retrieve all partially saved {@link ManagedItemVo} for the given user.
     * 
     * @param user UserVo for which to retrieve partially saved work
     * @return List<PartialSaveVo> partially saved managed items
     */
    @Override
    public List<PartialSaveVo> retrievePartialSaves(UserVo user) {
        return partialSaveMgtDomainCapability.retrieveAll(user);
    }

    /**
     * Retrieve a ManagedItemVo for use as a new managed item based on an existing managed item.
     * 
     * @param itemId String ID of managed item to use as template
     * @param itemType EntityType of item template to retrieve
     * @return ManagedItemVo with given ID, but fields cleared for use as template
     * @throws ItemNotFoundException e
     */
    @Override
    public ManagedItemVo retrieveTemplate(String itemId, EntityType itemType) throws ItemNotFoundException {
        ManagedItemVo managedItem = retrieve(itemId, itemType);

        ManagedItemVo template = managedItem.template(getEnvironmentUtility().getEnvironment());
        template.setId(null);
        template.setItemStatus(ItemStatus.ACTIVE);
        template.setRequestItemStatus(RequestItemStatus.PENDING);
        template.setRejectionReasonText(null);
        template.setRequestRejectionReason(null);
        template.makeAllDataFieldsEditable();

        // set local use field to false at national
        if (environmentUtility.isNational()) {
            template.setLocalUse(false);
        }

        inheritPropertiesFromParent(template);

        return template;
    }

    /**
     * Save the partially entered ManagedItemVo.
     * 
     * @param item ManagedItemVo
     * @param comment String
     * @param user UserVo
     * @return partially saved ManagedItmVo
     */
    @Override
    public ManagedItemVo savePartial(ManagedItemVo item, String comment, UserVo user) {
        String filename = SerializationUtility.serialize(item);

        PartialSaveVo partialSaveVo = new PartialSaveVo();
        partialSaveVo.setComment(comment);
        partialSaveVo.setFileName(filename);
        partialSaveVo.setEntityType(item.getEntityType());

        if (item.isNewInstance()) {
            partialSaveVo.setRequestType(RequestType.ADDITION);
        } else {
            partialSaveVo.setRequestType(RequestType.MODIFICATION);
        }

        partialSaveVo.setModifiedDate(new Date());
        partialSaveVo.setModifiedBy(user.getUsername());

        if (partialSaveVo.getRequestType().isAddition()) {
            partialSaveVo.setItemRevisionNumber(1L);
        } else {
            partialSaveVo.setItemRevisionNumber(item.getRevisionNumber());
        }

        // put the vo in the database
        partialSaveMgtDomainCapability.create(partialSaveVo, user);

        return item;
    }

    /**
     * Save the problem report. Functionality is in package gov.va.med.pharmacy.peps.service.local.capability.impl
     * 
     * @param item ManagedItemVo
     * @param comment String contains problem report text.
     * @param user UserVo
     */
    @Override
    public void submitProblemReport(ManagedItemVo item, String comment, UserVo user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Validates search criteria and then calls the domain capability to perform search.
     * 
     * @param searchCriteria SearchCriteriaVo
     * @return List<ProductVo> Returns paginated partial list of items
     * @throws ValidationException e
     */
    @Override
    public List<ManagedItemVo> search(SearchCriteriaVo searchCriteria) throws ValidationException {
        searchCriteria.validate();
        ManagedItemDomainCapability capability =
            managedItemCapabilityFactory.getDomainCapability(searchCriteria.getEntityType());

        return capability.search(searchCriteria);
    }
    
    /**
     * Validates search criteria and then calls the domain capability to perform search.
     * 
     * @param searchCriteria SearchCriteriaVo
     * @return List<ProductVo> Returns full list of items
     * @throws ValidationException e
     */
    @Override
    public List<ManagedItemVo> searchFullList(SearchCriteriaVo searchCriteria) throws ValidationException {
        searchCriteria.validate();
        ManagedItemDomainCapability capability =
            managedItemCapabilityFactory.getDomainCapability(searchCriteria.getEntityType());

        return capability.searchFullList(searchCriteria);
    }

    /**
     * Retrieve the parent with the given itemId and itemType, then set the current item's (the child) parent.
     * 
     * @param child Child ManagedItemVo
     * @param parentId ManagedItemVo ID for the parent
     * @param parentType ManagedItemVo EntityType for the parent
     * @return child ManagedItemVo with parent set
     * @throws ItemNotFoundException if cannot find parent with given ID and EntityType
     */
    @Override
    public ManagedItemVo selectParent(ManagedItemVo child, String parentId, EntityType parentType)
        throws ItemNotFoundException {

        ManagedItemVo parent = this.retrieve(parentId, parentType);
        child.setPreviousParent(child.getParent());
        child.selectParent(parent);

        // Set the national formulary indicator to the OI's, and the name to the product's Generic Name and Dosage Form
        if (child.isProductItem()) {

            ProductVo product = (ProductVo) child;
            product.setNationalFormularyIndicator(((OrderableItemVo) parent).getNationalFormularyIndicator());

            // Set the formulary name if the Generic Name has already been selected.
            if (product.getGenericName() != null) {
                StringBuffer nationalFormularyName = new StringBuffer();
                nationalFormularyName.append(StringUtils.trimToEmpty(product.getGenericName().toShortString()));

                if (nationalFormularyName.length() > 0) {
                    nationalFormularyName.append(" ");
                }

                nationalFormularyName.append(product.getDosageForm() == null ? "" : StringUtils.trimToEmpty(product
                    .getDosageForm().toShortString()));
                product.setNationalFormularyName(StringUtils.trimToNull(nationalFormularyName.toString()));
            }
        }

        inheritPropertiesFromParent(child);

        return child;
    }

    /**
     * Product and NDC inherit the value for application package use and categories from the parent
     * 
     * @param managedItem Child ManagedItemVo
     */
    protected void inheritPropertiesFromParent(ManagedItemVo managedItem) {
        if (managedItem.isProductItem() || managedItem.isNdcItem()) {
            ManagedItemVo parent = managedItem.getParent();

            if (parent != null) {

                // get the parent's application package use
                ListDataField<String> appPkgUseParent =
                    managedItem.getParent().getVaDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE);

                // get the child's application package use
                ListDataField<String> appPkgUseChild =
                    managedItem.getVaDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE);

                if (appPkgUseChild == null) {
                    appPkgUseChild = DataField.newInstance(FieldKey.APPLICATION_PACKAGE_USE);
                    managedItem.getVaDataFields().setDataField(appPkgUseChild);
                }

                if (appPkgUseParent != null) {
                    appPkgUseChild.selectValue(appPkgUseParent.getValue());
                }

                // added this if because by default the parent doesn't have category set.  If the parent
                // has a null category list then don't populate it.
                if (parent.isOrderableItemItem()) {
                    if (((OrderableItemVo) parent).getCategories() != null
                        && ((OrderableItemVo) parent).getCategories().size() > 0) {
                        ((ProductVo) managedItem).setCategories(((OrderableItemVo) parent).getCategories());
                        
                        toggleProductSupplySpecialHandling((ProductVo) managedItem);
                    }
                } else if (parent.isProductItem()) {
                    if (((ProductVo) parent).getCategories() != null && ((ProductVo) parent).getCategories().size() > 0) {
                        ((NdcVo) managedItem).setCategories(((ProductVo) parent).getCategories());
                    }
                }
            }
        }
    }

    /**
     * Adds or removes the Supply Special Handling instruction from a Product based 
     * on whether or not the product is categorized as a Supply or has a Primary Drug
     * Class starting with X.
     *
     * @param product to modify, which has been modified and not yet updated     
     * @return the special Handling difference.
     */
    protected Difference toggleProductSupplySpecialHandling(ProductVo product) {
        ListDataField<SpecialHandlingVo> itemSpecialHandlings =
            product.getVaDataFields().getDataField(FieldKey.SPECIAL_HANDLINGS);

        // If the specialHandlings VADF is null, retrieve it from the DB so this method does not fail with a 
        // null pointer exception on a foreign key constraint
        if (itemSpecialHandlings == null) {
            itemSpecialHandlings =
                (ListDataField<SpecialHandlingVo>) getDataFieldsCapability().retrieveDomain().getDataField(
                    FieldKey.SPECIAL_HANDLINGS);
        }

        ListDataField<SpecialHandlingVo> oldItemSpecialHandlings = itemSpecialHandlings.copy();
        
        List<SpecialHandlingVo> specialHandlings = getSpecialHandlingDomainCapability().retrieve();

        SpecialHandlingVo supply = null;

        for (SpecialHandlingVo specialHandling : specialHandlings) {
            if ("S".equals(specialHandling.getValue())) {
                supply = specialHandling;
                break;
            }
        }
        
        boolean update = false;
        DrugClassVo primaryDrugClass = product.getPrimaryDrugClass();
        
        if (product.getCategories().contains(Category.SUPPLY)
            || (primaryDrugClass != null && primaryDrugClass.getCode().startsWith("X"))) {
            if (supply != null && !itemSpecialHandlings.contains(supply)) {
                itemSpecialHandlings.addSelection(supply);
                product.getVaDataFields().setDataField(itemSpecialHandlings);
                update = true;
            }
        } else {
            if (supply != null && itemSpecialHandlings.contains(supply)) {
                itemSpecialHandlings.removeSelection(supply);
                product.getVaDataFields().setDataField(itemSpecialHandlings);
                update = true;
            }
        }
     
        if (update) {
            return new Difference(FieldKey.SPECIAL_HANDLING, oldItemSpecialHandlings, itemSpecialHandlings);
        } 
        
        return null;

    }

    /**
     * Description
     * 
     * @param dataFieldsCapability dataFieldsCapability property
     */
    public final void setDataFieldsCapability(DataFieldsCapability dataFieldsCapability) {
        this.dataFieldsCapability = dataFieldsCapability;
    }

    /**
     * Description
     * 
     * @param environmentUtility environmentUtility property
     */
    public final void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * Description
     * 
     * @param managedItemCapabilityFactory managedItemCapabilityFactory property
     */
    public final void setManagedItemCapabilityFactory(ManagedItemCapabilityFactory managedItemCapabilityFactory) {
        this.managedItemCapabilityFactory = managedItemCapabilityFactory;
    }

    /**
     * Description
     * 
     * @param partialSaveMgtDomainCapability partialSaveMgtDomainCapability property
     */
    public final void setPartialSaveMgtDomainCapability(PartialSaveMgtDomainCapability partialSaveMgtDomainCapability) {
        this.partialSaveMgtDomainCapability = partialSaveMgtDomainCapability;
    }

    /**
     * Description
     * 
     * @param stsCapability stsCapability property
     */
    public final void setStsCapability(StsCapability stsCapability) {
        this.stsCapability = stsCapability;
    }

    /**
     * Description
     * 
     * @param capability property
     */
    public final void setVistaFileSynchCapability(VistaFileSynchCapability capability) {
        this.vistaFileSynchCapability = capability;
    }

    /**
     * Update parent/child relationships (ex. product\ndcs)
     * 
     * @param child the child to update
     * @param parent the parent
     * @param user the user doing the swap
     * @throws ValidationException e
     * @return ProcessedItemVo
     */
    @Override
    public ProcessedItemVo updateParentChildRelationships(ManagedItemVo child, ManagedItemVo parent, UserVo user)
        throws ValidationException {

        ProcessedItemVo vo = null; 
            
        // We do not want to sent the parent update to local as the child being updated at the
        // other site will handle the update of the parent as well..
        if (child != null) {

            // M5P2 - Setting a member variable (strParentIdtoNotSend) is NOT thread-safe. Spring provides one instance
            // of the class per VM. This must be handled in some fashion that does not include using a member-level variable.
            strParentIdtoNotSend = parent.getId();
            vo = updateChildrenParent(child, parent, user);

            //updateChildren2(child, parent, user);
            strParentIdtoNotSend = "";
        }
        
        return vo;
    }

    /**
     * Update all children to have the correct parent.
     * 
     * @param child ManagedItemVo
     * @param parent ManagedItemVo
     * @param user the user doing the update
     * @throws ValidationException e
     * @return ProcessedItemVO
     */
    private ProcessedItemVo updateChildrenParent(ManagedItemVo child, ManagedItemVo parent, UserVo user)
        throws ValidationException {

        
        ManagedItemVo fullChild = this.retrieve(child.getId(), child.getEntityType());
        
        // Populate the fdb values for warnings later if needed.
        if (fullChild instanceof NdcVo) {
            
            try {
                this.drugReferenceCapability.populateFdbNdcFields((NdcVo) fullChild);
            } catch (FDBException e) {
                LOG.debug("No Fdb values for NDC " + child.getValue());
            }
        }

        ManagedItemVo oldChild = fullChild.copy();

        oldChild.selectParent(parent);

        Collection<ModDifferenceVo> childDiff = fullChild.compareDifferences(oldChild);

        for (ModDifferenceVo modDiff : childDiff) {
            modDiff.setModificationReason("Move "
                + FieldKey.getKey(child.getEntityType().toFieldKey()).getLocalizedName(Locale.getDefault()));
        }
        
       
        
        return commitModifications(childDiff, fullChild, user);
    }

    /**
     * Used to deterimine if the sendToVistaFlat is on.
     * @return true if messaging is turned off, false otherwise
     */
    protected boolean okToSendToVista() {

        // retrieve the national settings from the database.
        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.MESSAGE_STATUS.toString().equals(vo.getKeyName())) {
                return vo.getBooleanValue();
            }
        }

        return false; // didn't have the message, don't send.

    }

    /**
     * Used to determine if the MESSAGE_QUEUE_IN_PROCESS flag is on.
     * @return true if queue is being emptied, false otherwise
     */
    protected boolean catchingUp() {

        // This will set the settingsList witht he national settings from the database.
        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {
            if (NationalSetting.MESSAGE_QUEUE_IN_PROGRESS.toString().equals(vo.getKeyName())) {
                return vo.getBooleanValue();
            }
        }

        return false; // didn't have the key, assume we are not sending.

    }

    /**
     * Updates without needing a Collection of ModDifferenceVo and without creating a request. Enforces business rules,
     * updates ManagedItemVo, and sends it to local/national and VistA.
     * 
     * @param managedItem ManagedItemVo to update
     * @param user UserVo updating item
     * @return ManagedItemVo updated item
     * @throws ValidationException if error enforcing business rules
     * 
     * @see #enforceBusinessRules(ManagedItemVo, UserVo)
     * @see #updateItem(ManagedItemVo, UserVo)
     * @see #sendModificationToPeps(RequestVo, ManagedItemVo, UserVo, ItemAuditHistoryVo)
     * @see #sendModificationToVista(ManagedItemVo, UserVo)
     */
    @Override
    public final ManagedItemVo update(ManagedItemVo managedItem, UserVo user) throws ValidationException {

        enforceBusinessRules(managedItem, user, true);

        // retrieve the original item for later comparison
        ManagedItemDomainCapability capability = managedItemCapabilityFactory.getDomainCapability(managedItem.getEntityType());
        ManagedItemVo originalItem = capability.retrieve(managedItem.getId());
       
        ManagedItemVo updatedItem = updateItem(managedItem, user);

        if (RequestItemStatus.APPROVED.equals(updatedItem.getRequestItemStatus())) {
            sendModificationToPeps(null, updatedItem, user, managedItem.getNewAuditHistory());

            if (vistaFileSynchCapability.sendModifiedItemToVista(updatedItem, 
                originalItem.diff(updatedItem), user,
                okToSendToVista(), catchingUp())) {
                addToQueue(updatedItem, updatedItem.getEntityType(), ItemAction.MODIFY, user);
            }

            return populateFdbFields(updatedItem, user);
        } else {
            return updatedItem;
        }

    }

    /**
     * Cascades orderable item category and national formulary indicator changes to all children and children of children
     *
     * @param managedItem The orderable Item    
     * @param user the user logged in
     * @param originalItem the original item
     * @param mods a collection of ModDifferences.
     * @throws ValidationException ValidationException
     */
    protected void cascadeChangesToChildren(ManagedItemVo managedItem, UserVo user, ManagedItemVo originalItem,
        Collection<ModDifferenceVo> mods)
        throws ValidationException {

        OrderableItemVo orderableItem = (OrderableItemVo) managedItem;
        ManagedItemDomainCapability productCapability = managedItemCapabilityFactory.getDomainCapability(EntityType.PRODUCT);
        ManagedItemDomainCapability ndcCapability = managedItemCapabilityFactory.getDomainCapability(EntityType.NDC);

        boolean categoryChange = !orderableItem.getCategories().equals(((OrderableItemVo) originalItem).getCategories());
        boolean nationalFormularyChange =
            orderableItem.getNationalFormularyIndicator() != ((OrderableItemVo) originalItem).getNationalFormularyIndicator();

        if (categoryChange || nationalFormularyChange) {
            Map<FieldKey, String> reasons = new HashMap<FieldKey, String>();
            Map<FieldKey, Difference> differences = new HashMap<FieldKey, Difference>();

            for (ModDifferenceVo vo : mods) {
                FieldKey key = vo.getDifference().getFieldKey();

                if (key.equals(FieldKey.CATEGORIES) || key.equals(FieldKey.NATIONAL_FORMULARY_INDICATOR)) {
                    reasons.put(key, vo.getModificationReason());
                    differences.put(key, vo.getDifference());
                }
            }
            
            for (ManagedItemVo product : orderableItem.getChildren()) {
                product = productCapability.retrieve(product.getId());

                if (categoryChange) {
                    ((ProductVo) product).setCategories(orderableItem.getCategories());
                    Difference specialHandlingDiff = toggleProductSupplySpecialHandling((ProductVo) product);
                    
                    if (specialHandlingDiff != null) {
                        differences.put(FieldKey.SPECIAL_HANDLING, specialHandlingDiff);
                        reasons.put(FieldKey.SPECIAL_HANDLING, reasons.get(FieldKey.CATEGORIES));
                    }
                }

                if (nationalFormularyChange) {
                    ((ProductVo) product).setNationalFormularyIndicator(orderableItem.getNationalFormularyIndicator());
                }

                update(product, user);
                saveItemAuditHistoryCascade(reasons, product, differences, user);
                
                if (categoryChange) {
                    for (ManagedItemVo ndc : ((ProductVo) product).getChildren()) {
                        ndc = ndcCapability.retrieve(ndc.getId());
                        ((NdcVo) ndc).setCategories(orderableItem.getCategories());
                        update(ndc, user);
                        saveItemAuditHistoryCascade(reasons, ndc, differences, user);
                    }
                }
            }
        }
    }


    /**
     * Updates the supply entry in the special handling field depending on the drug class changes
     *
     * @param updatedProduct The orderable Item    
     * @param originalProduct the original item
     * @param user the user logged in
     */
    @Override
    public void updateSpecialHandlingBasedOnDrugClassChange(ProductVo updatedProduct, ProductVo originalProduct,
        UserVo user) {

        DrugClassVo primaryDrugClass = updatedProduct.getPrimaryDrugClass();
        DrugClassVo oldPrimaryDrugClass = originalProduct.getPrimaryDrugClass();

        if (primaryDrugClass != null && !primaryDrugClass.equals(oldPrimaryDrugClass)) {
            toggleProductSupplySpecialHandling(updatedProduct);
        }
    
    }

    /**
     * saveItemAuditHistoryCascade
     * @param reasons reasons
     * @param vo reason
     * @param differences differences 
     * @param user user
     */
    protected void saveItemAuditHistoryCascade(Map<FieldKey, String> reasons, ManagedItemVo vo,
        Map<FieldKey, Difference> differences, UserVo user) {
        ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
        StringBuilder auditReason = new StringBuilder();
        Collection<ItemAuditHistoryDetailsVo> collection = new ArrayList<ItemAuditHistoryDetailsVo>();
        
        for (FieldKey key : reasons.keySet()) {
            ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
            detail.setColumnName(key.getKey());
            detail.setNewValue(differences.get(key).getNewValueShortString());
            detail.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
            detail.setDetailReason(reasons.get(key));
            detail.setOldValue(differences.get(key).getOldValueShortString());
            collection.add(detail);

            auditReason.append(reasons.get(key));

            if (!reasons.get(key).trim().isEmpty()) {
                auditReason.append("; ");
            }
        }
        
        audit.setDetails(collection);
        audit.setAuditItemId(vo.getId());
        audit.setCreatedBy(user.getUsername());
        audit.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
        audit.setDetailCategory(EventCategory.NATIONAL_MODIFICATION);
        audit.setAuditItemType(vo.getEntityType());
        audit.setSiteName(user.getLocation());
        audit.setUsername(user.getUsername());
        audit.setReason(auditReason.toString());
        saveItemAuditHistoryRecords(audit, user);
    }

    /**
     * Calculates the differences in field's values between user object and database object
     * 
     * @param item user object
     * @param differences a collection of differences
     * @param user the user making the current changes
     * @return value object with differences and revision number
     * @throws ItemNotFoundException when product object not found in database
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability#computeMergeInformation(
     *          gov.va.med.pharmacy.peps.common.vo.ManagedItemVo, Collection, UserVo)
     */
    @Override
    public MergeVo computeMergeInformation(ManagedItemVo item, Collection<ModDifferenceVo> differences, UserVo user)
        throws ItemNotFoundException {

        // Retrieve product from db
        ManagedItemVo dbItem = this.retrieve(item.getId(), item.getEntityType());

        // Check to see if there is a request - this will be used to build the MergeDifferenceVo
        RequestVo request = checkForExistingRequest(dbItem);

        // find differences between fields
        Collection<Difference> dbDiffs = dbItem.diff(item);

        // Create the empty list for storing the merge differences
        Collection<MergeDifferenceVo> mergeDiffs = new ArrayList<MergeDifferenceVo>();

        // Loop through the differences with the database item, and start building the merge differences
        for (Difference dbDiff : dbDiffs) {

            // Ignore fields with no key
            if (dbDiff.getFieldKey() == null) {
                continue;
            }

            if (request == null) {
                Date modifiedDate = Calendar.getInstance().getTime();

                for (ModDifferenceVo oldItemDiff : differences) {
                    if (oldItemDiff.getDifference().getFieldKey().equals(dbDiff.getFieldKey())) {
                        ItemAuditHistoryDetailsVo iahDetail =
                            findItemHistoryByFieldKey(dbItem.getItemAuditHistory(), dbDiff.getFieldKey(), user);
                        MergeDifferenceVo mergedDiff = new MergeDifferenceVo();
                        MergeDifference md =
                            new MergeDifference(dbDiff.getFieldKey(), oldItemDiff.getDifference().getOldValue(),
                                dbDiff.getNewValue(), dbDiff.getOldValue());
                        mergedDiff.setDifference(md);
                        mergedDiff.setModifiedBy(iahDetail.getModifiedBy());
                        mergedDiff.setModifiedDate(iahDetail.getModifiedDate() == null ? modifiedDate : iahDetail
                            .getModifiedDate());
                        mergeDiffs.add(mergedDiff);
                        break;
                    }
                }
            } else {
                for (ModDifferenceVo reqDiff : request.getRequestDetails()) {
                    if (reqDiff.getDifference().getFieldKey().equals(dbDiff.getFieldKey())) {
                        MergeDifferenceVo mergedDiff = new MergeDifferenceVo();
                        MergeDifference md =
                                new MergeDifference(dbDiff.getFieldKey(), dbDiff.getOldValue(), dbDiff.getNewValue(), reqDiff
                                    .getDifference().getNewValue());
                        mergedDiff.setDifference(md);
                        mergedDiff.setModifiedBy(reqDiff.getModifiedBy());
                        mergedDiff.setModifiedDate(reqDiff.getModifiedDate());
                        mergeDiffs.add(mergedDiff);
                        break;
                    }
                }
            }
        }

        // Create merge object with differences and db data revision number
        MergeVo merge = new MergeVo(mergeDiffs, dbItem.getRevisionNumber());

        return merge;
    }

    /**
     * Finds ItemAuditHistoryDetailsVo by using field key
     * 
     * @param collection with items of audit history
     * @param key column name
     * @param user the user making the current changes
     * @return ItemAuditHistoryDetailVo
     */
    private ItemAuditHistoryDetailsVo findItemHistoryByFieldKey(Collection<ItemAuditHistoryVo> collection, FieldKey key,
        UserVo user) {

        // Create item history detail and initialize
        ItemAuditHistoryDetailsVo itemFound = new ItemAuditHistoryDetailsVo();
        itemFound.setModifiedBy(user.toDisplayName());
        itemFound.setModifiedDate(null);

        if (!collection.isEmpty()) {
            for (ItemAuditHistoryVo iah : collection) {
                if (!iah.getDetails().isEmpty()) {
                    for (ItemAuditHistoryDetailsVo iahDetail : iah.getDetails()) {
                        if ((iahDetail.getFieldName() != null)
                            && ((iahDetail.getFieldName().equalsIgnoreCase(key.getKey())) 
                                || iahDetail.getFieldName().equalsIgnoreCase(
                                    key.getLocalizedName(Locale.getDefault())))) {
                            itemFound = iahDetail;
                        }
                    }
                }
            }
        }

        return itemFound;
    }

    /**
     * Commit merge.
     * 
     * @param mergeItem MergeVo merge data
     * @param item ManagedItemVo to merge
     * @param user UserVo performing merge
     * @return merged ManagedItemVo
     * @throws ValidationException e
     */
    @Override
    public ManagedItemVo commitMergeModifications(MergeVo mergeItem, ManagedItemVo item, UserVo user)
        throws ValidationException {

        // apply the database revision number from mergeItem into the user's managedItem
        item.setRevisionNumber(mergeItem.getRevisionNumber());

        // retrieve collection of differences to be committed
        Collection<ModDifferenceVo> differences = mergeItem.getModDifferences();

        RequestVo request = checkForExistingRequest(item);

        // If there is no request, continue with the regular commitMods, otherwise merge the changes into the request
        if (request == null) {
            this.commitModifications(differences, item, user);
        } else {
            for (ModDifferenceVo mergeDiff : differences) {
                for (ModDifferenceVo reqDiff : request.getRequestDetails()) {
                    if (mergeDiff.getDifference().getFieldKey().equals(reqDiff.getDifference().getFieldKey())) {
                        if (mergeDiff.isAcceptChange()) {
                            reqDiff.setModRequestItemStatus(RequestItemStatus.REJECTED);
                            reqDiff.setComments("Automatically rejected by Item Modification merge.");
                            break;
                        } else if (!mergeDiff.isAcceptChange()
                            && mergeDiff.getDifference().getFieldKey().isRequiresSecondApproval()) {
                            request.setUnderReview(true);
                            break;
                        }
                    }
                }
            }

            this.commitRequest(item, request, differences, user, false);
        }

        return item;
    }

    /**
     * This flow is used for proposing an item. It sends a request to national and does not save it locally.
     * 
     * @param vo ManagedItemVo
     * @param user user
     * @return ManagedItemVo item
     */
    public ManagedItemVo proposeItem(ManagedItemVo vo, UserVo user) {
        throw new UnsupportedOperationException();
    }

    /**
     * Generate the VistA OI Name and OI name from the selected VA Generic name and dosage
     * 
     * @param item VOs with user changes
     * @return VO with generated values
     */
    @Override
    public ManagedItemVo generateOINames(ManagedItemVo item) {
        OrderableItemVo oiItem = (OrderableItemVo) item;

        if (oiItem.getGenericName() == null) {
            return oiItem;
        }

        if (oiItem.getGenericName().getValue().length() > PPSConstants.I40) {
            oiItem.setVistaOiName(oiItem.getGenericName().getValue().substring(0, PPSConstants.I40));
        } else {
            oiItem.setVistaOiName(oiItem.getGenericName().getValue());
        }

        if (oiItem.getDosageForm() == null) {
            if (oiItem.getGenericName().getValue().length() > PPSConstants.I40) {
                oiItem.setOiName(oiItem.getGenericName().getValue().substring(0, PPSConstants.I40));
            } else {
                oiItem.setOiName(oiItem.getGenericName().getValue());
            }
        } else {
            if (oiItem.getGenericName().getValue().length() > PPSConstants.I40) {
                oiItem.setOiName(oiItem.getGenericName().getValue().substring(0, PPSConstants.I40) + " " 
                    + oiItem.getDosageForm().getDosageFormName());
            } else {
                oiItem.setOiName(oiItem.getGenericName().getValue() + " " + oiItem.getDosageForm().getDosageFormName());
            }
        }

        return oiItem;
    }

    /**
     * Inserts a system notification.
     * 
     * @param managedItem ManagedItemVo to create notification for
     * @param user UserVo creating notification
     * @param message String message to send
     * @param throwable Throwable that caused notification
     */
    protected void insertSystemNotification(ManagedItemVo managedItem, UserVo user, String message, Throwable throwable) {
        notificationDomainCapability.createSystemNotification(managedItem, user, environmentUtility.getSiteNumber(), message,
            throwable);
    }

    /**
     * Update the parent/child relationships between two items. The items supplied as parameters already have the children
     * moved to the appropriate parent.
     * 
     * @param one {@link ManagedItemVo}
     * @param two {@link ManagedItemVo}
     * @param user {@link UserVo} performing the operation
     * @throws ValidationException if the data is not valid
     */
    @Override
    public void moveChildren(ManagedItemVo one, ManagedItemVo two, UserVo user) throws ValidationException {

        for (ManagedItemVo child : one.getChildren()) {
            updateParentChildRelationships(child, one, user);
        }

        for (ManagedItemVo child : two.getChildren()) {
            updateParentChildRelationships(child, two, user);
        }
    }

    /**
     * 
     * Checks to see if a any of the mod differences are inactivating the item
     *
     * @param modDifferences list of mod differences
     * @return true if the item has been inactivated
     */
    private boolean isItemInactivated(Collection<ModDifferenceVo> modDifferences) {
        boolean isItemInactivated = false;

        for (ModDifferenceVo modDifference : modDifferences) {

            // If the mod difference is an item status change and it has been accepted, check further
            if (modDifference.getDifference().getFieldKey() == FieldKey.ITEM_STATUS && modDifference.isAcceptChange()) {
                if (((ItemStatus) modDifference.getDifference().getNewValue()).equals(ItemStatus.INACTIVE)) {
                    isItemInactivated = true;
                    break;
                }
            }
        }

        return isItemInactivated;
    }

    /**
     * addToDependentList
     * @param id id
     * @param type type
     * @return RequestVo
     */
    private RequestVo addToDependentList(String id, EntityType type) {

        try {
            return requestDomainCapability.retrieveNonCompletedRequest(
                    id, type);
        } catch (ItemNotFoundException ex) {
            return null;
        }
    }
    
    /**
     * getPendingNonAffliliatedItems
     * @param item item
     * @param user user
     * @return a list of ManagedItemVos
     */
    @Override
    public List<ManagedItemVo> getPendingNonAffliliatedItems(ManagedItemVo item, UserVo user) {
        List<ManagedItemVo> list = new ArrayList<ManagedItemVo>();
        Map<ManagedItemVo, RequestVo> map = getDependenciesToApproveOrReject(item, true);
        
        for (ManagedItemVo dependent : map.keySet()) {
            ManagedItemDomainCapability managedItemDomainCapability =
                managedItemCapabilityFactory.getDomainCapability(dependent.getEntityType());

            try {
                managedItemDomainCapability.checkForActiveDependencies(dependent, user);
                list.add(dependent);
                
            } catch (Exception e) {

                // this is OK since this just means it has a dependency.  do not add item.
                LOG.debug("This item has an active dependency." + dependent.getValue());
            }
        }

        return list;
      
    }
    
    protected void updateReferences(ProcessedRequestVo processedVo, ManagedItemVo itemCopy) {
        
        if (itemCopy instanceof OrderableItemVo) {
            if (processedVo.getItem() instanceof DosageFormVo) {
                ((OrderableItemVo) itemCopy).setDosageForm((DosageFormVo) processedVo.getItem());
            }
        } else if (itemCopy instanceof ProductVo) {
            if (processedVo.getItem() instanceof GenericNameVo) {      
                ((ProductVo) itemCopy).setGenericName((GenericNameVo) processedVo.getItem());
            } else if (processedVo.getItem() instanceof DosageFormVo) {      
                ((ProductVo) itemCopy).getOrderableItem().setDosageForm((DosageFormVo) processedVo.getItem());
            } else if (processedVo.getItem() instanceof DispenseUnitVo) {
                ((ProductVo) itemCopy).setDispenseUnit((DispenseUnitVo) processedVo.getItem());
            } else if (processedVo.getItem() instanceof DrugUnitVo) {
                ((ProductVo) itemCopy).setProductUnit((DrugUnitVo) processedVo.getItem());
            } else if (processedVo.getItem() instanceof DrugClassVo) {
                Collection<DrugClassGroupVo> drugClassVo = ((ProductVo) itemCopy).getDrugClasses();
               
                for (DrugClassGroupVo vo : drugClassVo) {
                    if (vo.getDrugClass().getClassification().equalsIgnoreCase(((DrugClassVo)processedVo.getItem()).getClassification()) 
                        && vo.getDrugClass().getCode().equalsIgnoreCase(((DrugClassVo) processedVo.getItem()).getCode())) {
                        vo.setDrugClass((DrugClassVo) processedVo.getItem());
                        break;
                    }
                }
            } else if (processedVo.getItem() instanceof IngredientVo) {
                Collection<ActiveIngredientVo> activeIngVo = ((ProductVo) itemCopy).getActiveIngredients();
               
                for (ActiveIngredientVo vo : activeIngVo) {
                    if (vo.getIngredient().getValue().equalsIgnoreCase(((IngredientVo) processedVo.getItem()).getValue())) { 
                        vo.setIngredient((IngredientVo) processedVo.getItem());
                        break;
                    }
                }
            }
        } else if (itemCopy instanceof IngredientVo) {
            if (processedVo.getItem() instanceof IngredientVo) {   
                ((IngredientVo) itemCopy).setPrimaryIngredient((IngredientVo) processedVo.getItem());
            }
        } else if (itemCopy instanceof DrugClassVo) {
            if (processedVo.getItem() instanceof DrugClassVo) {   
                ((DrugClassVo) itemCopy).setParentDrugClass((DrugClassVo) processedVo.getItem());
            }
        }
    }
    

    /**
     * 
     * Returns a map of dependent items with requests to be approved
     *
     * @param item with potential dependents
     * @param multiLevel true if we should go deep into the resultset, false if only going one level
     * @return the map of items and their requests
     */
    protected Map<ManagedItemVo, RequestVo> getDependenciesToApproveOrReject(ManagedItemVo item, boolean multiLevel) {
        Map<ManagedItemVo, RequestVo> dependentRequests = new HashMap<ManagedItemVo, RequestVo>();
        RequestVo requestVo;

        if (EntityType.PRODUCT.equals(item.getEntityType())) {
            
            if (((ProductVo) item).getOrderableItem() != null 
                && ((ProductVo) item).getOrderableItem().getDosageForm() != null) {
                requestVo = addToDependentList(((ProductVo) item).getOrderableItem().getDosageForm().getId(), 
                    EntityType.DOSAGE_FORM);

                if (requestVo != null) {
                    dependentRequests.put(((ProductVo) item).getOrderableItem().getDosageForm(), requestVo);
                }
            }
            
            if (((ProductVo) item).getGenericName() != null) {
                requestVo = addToDependentList(((ProductVo) item).getGenericName().getId(), EntityType.GENERIC_NAME);

                if (requestVo != null) {
                    dependentRequests.put(((ProductVo) item).getGenericName(), requestVo);
                }
            }

            if (((ProductVo) item).getProductUnit() != null) {
                requestVo = addToDependentList(((ProductVo) item).getProductUnit().getId(), EntityType.DRUG_UNIT);

                if (requestVo != null) {
                    dependentRequests.put(((ProductVo) item).getProductUnit(), requestVo);
                }
            }

            if (((ProductVo) item).getDispenseUnit() != null) {
                requestVo = addToDependentList(((ProductVo) item).getDispenseUnit().getId(), EntityType.DISPENSE_UNIT);

                if (requestVo != null) {
                    dependentRequests.put(((ProductVo) item).getDispenseUnit(), requestVo);
                }
            }

            Collection<DrugTextVo> dtList = ((ProductVo) item).getNationalDrugTexts();

            for (Object element : dtList) {
                DrugTextVo dtVo = (DrugTextVo) element;
                requestVo = addToDependentList(dtVo.getId(), EntityType.DRUG_TEXT);

                if (requestVo != null) {
                    dependentRequests.put(dtVo, requestVo);
                }
            }

            Collection<ActiveIngredientVo> ingredientList = ((ProductVo) item).getActiveIngredients();

            for (Object element : ingredientList) {
                ActiveIngredientVo aiVo = (ActiveIngredientVo) element;

                if (aiVo.getIngredient() != null) {
                    requestVo = addToDependentList(aiVo.getIngredient().getId(), EntityType.INGREDIENT);

                    if (requestVo != null) {
                        dependentRequests.put(aiVo.getIngredient(), requestVo);

                        if (multiLevel) {
                            dependentRequests.putAll(getDependenciesToApproveOrReject(aiVo.getIngredient(), multiLevel));
                        }
                    }
                }

                if (aiVo.getDrugUnit() != null) {
                    requestVo = addToDependentList(aiVo.getDrugUnit().getId(), EntityType.DRUG_UNIT);

                    if (requestVo != null) {
                        dependentRequests.put(aiVo.getDrugUnit(), requestVo);
                    }
                }
            }

            Collection<DrugClassGroupVo> drugClassList = ((ProductVo) item).getDrugClasses();

            for (Object element : drugClassList) {
                DrugClassGroupVo dcVo = (DrugClassGroupVo) element;

                if (dcVo != null && dcVo.getDrugClass() != null) {
                    requestVo = addToDependentList(dcVo.getDrugClass().getId(), EntityType.DRUG_CLASS);
    
                    if (requestVo != null) {
                        dependentRequests.put(dcVo.getDrugClass(), requestVo);
                        
                        if (multiLevel) {
                            Map<ManagedItemVo, RequestVo> tempMap = getDependenciesToApproveOrReject(dcVo.getDrugClass(),
                                multiLevel);
    
                            // this part is used to ensure that the parent drug class of the child is the same instance of the 
                            // object as the parent is on the dependency list.  This way both dependencies are sent, 
                            // but when the 
                            // parent is sent first, then the IEN is automatically placed in the parent object of the child 
                            // so when the child is sent the parent IEN is known.
                            if (!tempMap.isEmpty()) {
                                dependentRequests.remove(dcVo.getDrugClass());
    
                                for (Map.Entry<ManagedItemVo, RequestVo> entry : tempMap.entrySet()) {
                                    dcVo.getDrugClass().setParent(entry.getKey());
                                }  
                                
                                dependentRequests.put(dcVo.getDrugClass(), requestVo);
                                dependentRequests.putAll(tempMap);
                            }
                        }
                    }
                    
                }
            }

            Collection<NdcVo> ndcList = ((ProductVo) item).getNdcs();

            for (Object element : ndcList) {
                NdcVo ndc = (NdcVo) element;
                requestVo = addToDependentList(ndc.getId(), EntityType.NDC);

                if (requestVo != null) {
                    try {
                        ndc = (NdcVo) retrieve(ndc.getId(), EntityType.NDC);
                    } catch (ItemNotFoundException e) {
                        LOG.debug(e);
                    }

                    dependentRequests.put(ndc, requestVo);
                }

                if (multiLevel) {
                    dependentRequests.putAll(getDependenciesToApproveOrReject(ndc, multiLevel));
                }
            }
        } else if (EntityType.ORDERABLE_ITEM.equals(item.getEntityType())) {
            requestVo = addToDependentList(((OrderableItemVo) item).getDosageForm().getId(), EntityType.DOSAGE_FORM);

            if (requestVo != null) {
                dependentRequests.put(((OrderableItemVo) item).getDosageForm(), requestVo);

                if (multiLevel) {
                    dependentRequests.putAll(getDependenciesToApproveOrReject(((OrderableItemVo) item).getDosageForm(),
                        multiLevel));
                }
            }

            Collection<ProductVo> products = ((OrderableItemVo) item).getProducts();

            for (Object element : products) {
                ProductVo prodVo = (ProductVo) element;
                requestVo = addToDependentList(prodVo.getId(), EntityType.PRODUCT);

                if (requestVo != null) {
                    try {
                        prodVo = (ProductVo) retrieve(prodVo.getId(), EntityType.PRODUCT);
                    } catch (ItemNotFoundException e) {
                        LOG.debug(e);
                    }

                    dependentRequests.put(prodVo, requestVo);

                    if (multiLevel) {
                        dependentRequests.putAll(getDependenciesToApproveOrReject(prodVo, multiLevel));
                    }
                }
            }
        } else if (EntityType.NDC.equals(item.getEntityType())) {

            requestVo = addToDependentList(((NdcVo) item).getManufacturer().getId(), EntityType.MANUFACTURER);

            if (requestVo != null) {
                dependentRequests.put(((NdcVo) item).getManufacturer(), requestVo);
            }

            requestVo = addToDependentList(((NdcVo) item).getPackageType().getId(), EntityType.PACKAGE_TYPE);

            if (requestVo != null) {
                dependentRequests.put(((NdcVo) item).getPackageType(), requestVo);
            }
        } else if (EntityType.INGREDIENT.equals(item.getEntityType())) {
            if (((IngredientVo) item).getPrimaryIngredient() != null) {
                requestVo = addToDependentList(((IngredientVo) item).getPrimaryIngredient().getId(), EntityType.INGREDIENT);

                if (requestVo != null) {
                    dependentRequests.put(((IngredientVo) item).getPrimaryIngredient(), requestVo);
                }
            }
            
        } else if (EntityType.DRUG_CLASS.equals(item.getEntityType())) {
            DrugClassVo child = (DrugClassVo) item;
            DrugClassVo parent = child.getParentDrugClass();

            if (parent != null) {
                requestVo = addToDependentList(parent.getId(), EntityType.DRUG_CLASS);

                if (requestVo != null) {
                    dependentRequests.put(parent, requestVo);
                }
            }
        } else if (EntityType.DOSAGE_FORM.equals(item.getEntityType())) {
            DosageFormVo dosageVo = null;

            try {
                dosageVo = (DosageFormVo) retrieve(item.getId(), EntityType.DOSAGE_FORM);
                Collection<DosageFormUnitVo> dup = dosageVo.getDfUnits();

                for (Object element : dup) {
                    DosageFormUnitVo duVo = (DosageFormUnitVo) element;
                    requestVo = addToDependentList(duVo.getDrugUnit().getId(), EntityType.DRUG_UNIT);

                    if (requestVo != null) {
                        dependentRequests.put(duVo.getDrugUnit(), requestVo);
                    }
                }
            } catch (Exception e) {
                LOG.debug(e);
            }

        }

        return dependentRequests;
    }

    /**
     * Add Current Request to the VistA Queue
     *
     * @param managedItem Item to be added (by reference) 
     * @param itemType The item's type
     * @param action The action - Add or Modify
     * @param user - The current user
     */
    protected void addToQueue(ManagedItemVo managedItem, EntityType itemType, ItemAction action, UserVo user) {
        NdfSynchQueueVo ndfSynchQueueVo = new NdfSynchQueueVo();
        ndfSynchQueueVo.setIdFk(managedItem.getId());
        ndfSynchQueueVo.setItemType(itemType.toString());
        ndfSynchQueueVo.setActionType(action.value());

        //        NdfSynchQueueDomainCapability 
//        ndfSynchQueueDomainCapability = 
//            managedItemCapabilityFactory.getDomainCapability(EntityType.NDF_SYNCH_QUEUE);
        
        ndfSynchQueueDomainCapability.createWithoutDuplicateCheck(ndfSynchQueueVo, user);
        List<NationalSettingVo> settingList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo setting : settingList) {

            if (setting.getKeyName().equalsIgnoreCase(NationalSetting.NUM_MSG_QUEUE.toString())) {
                setting.setIntegerValue(setting.getIntegerValue() + 1);
                nationalSettingDomainCapability.update(setting, user);
                break;
            }
        }
        
        
    }

    /**
     * Checks for existing requests
     *
     * @param managedItem 
     * @return the request if found, otherwise null
     */
    private RequestVo checkForExistingRequest(ManagedItemVo managedItem) {
        RequestVo existingRequest = null;

        try {
            existingRequest =
                requestDomainCapability.retrieveNonCompletedRequest(managedItem.getId(), managedItem.getEntityType());
        } catch (ItemNotFoundException e) {
            LOG.info("No existing request found");
        }

        return existingRequest;
    }

    /**
     * 
     * Checks for warnings on a ManagedItemVo that is being passed into this method.
     *
     * @param item {@link ManagedItemVo}.  This is the item to check for warnings.
     * @param modDifferences Collection of modification differences already applied to the specified item, may be null.
     * @param newAdd this boolean is true when the item is a newly added item. this will be true in create method and false
     *            in other methods
     * @return {@link Errors} representing warnings
     * @throws ValidationException exception
     */
    @Override
    public Errors checkForWarnings(ManagedItemVo item, Collection<ModDifferenceVo> modDifferences, boolean newAdd)
        throws ValidationException {
        return getRulesCapability(item.getEntityType()).checkForWarnings(item, modDifferences, newAdd);
    }

    /**
     * getStrParentIdtoNotSend
     * @return strParentIdtoNotSend
     */
    public String getStrParentIdtoNotSend() {
        return strParentIdtoNotSend;
    }

    /**
     * setStrParentIdtoNotSend
     * @param strParentIdtoNotSend strParentIdtoNotSend
     */
    public void setStrParentIdtoNotSend(String strParentIdtoNotSend) {
        this.strParentIdtoNotSend = strParentIdtoNotSend;
    }

    /**
     * Get the {@link RulesCapability} for the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType}
     * @return {@link RulesCapability}
     */
    protected RulesCapability getRulesCapability(EntityType entityType) {
        return getManagedItemCapabilityFactory().getRulesCapability(entityType);
    }

    /**
     * Get the {@link ManagedItemDomainCapability} for the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType}
     * @return {@link ManagedItemDomainCapability}
     */
    protected ManagedItemDomainCapability getManagedItemDomainCapability(EntityType entityType) {
        return getManagedItemCapabilityFactory().getDomainCapability(entityType);
    }

    /**
     * Description
     * 
     * 
     * @param itemAuditHistoryDomainCapability property
     */
    public final void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }

    /**
     * setDrugReferenceCapability
     * 
     * 
     * @param drugReferenceCapability property
     */
    public final void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
        this.drugReferenceCapability = drugReferenceCapability;
    }

    /**
     * setFdbProductDomainCapability
     * 
     * 
     * @param fdbProductDomainCapability property
     */
    public final void setFdbProductDomainCapability(FdbProductDomainCapability fdbProductDomainCapability) {
        this.fdbProductDomainCapability = fdbProductDomainCapability;
    }

    /**
     * setFdbNdcDomainCapability
     * 
     * 
     * @param fdbNdcDomainCapability property
     */
    public final void setFdbNdcDomainCapability(FdbNdcDomainCapability fdbNdcDomainCapability) {
        this.fdbNdcDomainCapability = fdbNdcDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability
     * 
     * 
     * @param nationalSettingDomainCapability property
     */
    public final void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * Description
     * 
     * @return ItemAuditHistoryDomainCapability property
     */
    public final ItemAuditHistoryDomainCapability getItemAuditHistoryDomainCapability() {
        return itemAuditHistoryDomainCapability;
    }

    /**
     * Description
     * 
     * @return notificationDomainCapability property
     */
    public final NotificationDomainCapability getNotificationDomainCapability() {
        return notificationDomainCapability;
    }

    /**
     * Description
     * 
     * @param notificationDomainCapability property
     */
    public final void setNotificationDomainCapability(NotificationDomainCapability notificationDomainCapability) {
        this.notificationDomainCapability = notificationDomainCapability;
    }

    /**
     * Description
     * 
     * @return requestDomainCapability property
     */
    public final RequestDomainCapability getRequestDomainCapability() {
        return requestDomainCapability;
    }

    /**
     * Description
     * 
     * @param requestDomainCapability requestDomainCapability property
     */
    public void setRequestDomainCapability(RequestDomainCapability requestDomainCapability) {
        this.requestDomainCapability = requestDomainCapability;
    }

    /**
     * Description
     * 
     * @return managedItemCapabilityFactory property
     */
    public final ManagedItemCapabilityFactory getManagedItemCapabilityFactory() {
        return managedItemCapabilityFactory;
    }

    /**
     * Description
     * 
     * @return partialSaveMgtDomainCapability property
     */
    public final PartialSaveMgtDomainCapability getPartialSaveMgtDomainCapability() {
        return partialSaveMgtDomainCapability;
    }

    /**
     * Description
     * 
     * @return dataFieldsCapability property
     */
    public final DataFieldsCapability getDataFieldsCapability() {
        return dataFieldsCapability;
    }

    /**
     * Description
     * 
     * @return vistaLinkCapability property
     */
    public final VistaFileSynchCapability getVistaFileSynchCapability() {
        return vistaFileSynchCapability;
    }

    /**
     * Description
     * 
     * @return environmentUtility property
     */
    public final EnvironmentUtility getEnvironmentUtility() {
        return environmentUtility;
    }

    /**
     * Description
     * 
     * @return stsCapability property
     */
    public final StsCapability getStsCapability() {
        return stsCapability;
    }

    
    /**
     * getNdfSynchQueueDomainCapability
     * @return the ndfSynchQueueDomainCapability
     */
    public NdfSynchQueueDomainCapability getNdfSynchQueueDomainCapability() {
        return ndfSynchQueueDomainCapability;
    }

    
    /**
     * setNdfSynchQueueDomainCapability
     * @param ndfSynchQueueDomainCapability the ndfSynchQueueDomainCapability to set
     */
    public void setNdfSynchQueueDomainCapability(NdfSynchQueueDomainCapability ndfSynchQueueDomainCapability) {
        this.ndfSynchQueueDomainCapability = ndfSynchQueueDomainCapability;
    }

    /**
     * getSpecialHandlingDomainCapability
     * @return the specialHandlingDomainCapability
     */
    public SpecialHandlingDomainCapability getSpecialHandlingDomainCapability() {
        return specialHandlingDomainCapability;
    }

    /**
     * setSpecialHandlingDomainCapability
     * @param specialHandlingDomainCapability the specialHandlingDomainCapability to set
     */
    public void setSpecialHandlingDomainCapability(SpecialHandlingDomainCapability specialHandlingDomainCapability) {
        this.specialHandlingDomainCapability = specialHandlingDomainCapability;
    }
}
