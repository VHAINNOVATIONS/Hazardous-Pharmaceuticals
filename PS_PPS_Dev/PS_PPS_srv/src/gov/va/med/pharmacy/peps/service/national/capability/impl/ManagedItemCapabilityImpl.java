/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ServiceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidItemType;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.domain.common.capability.GenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.IngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.service.national.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.national.capability.RequestCapability;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;


/**
 * Managed Item Capability contains code common to all item types that is specific to National.
 */
public class ManagedItemCapabilityImpl extends
        gov.va.med.pharmacy.peps.service.common.capability.impl.ManagedItemCapabilityImpl implements ManagedItemCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagedItemCapabilityImpl.class);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    // private SendToLocalCapability sendToLocalCapability;
    private RequestCapability requestCapability;
    private IngredientDomainCapability ingredientDomainCapability;
    private GenericNameDomainCapability genericNameDomainCapability;


    /**
     * Commit the request, modifications to the ManagedItemVo, and the ManagedItemVo itself.
     * 
     * @param oldItem original ManagedItemVo, without any modifications from the {@link RequestVo} or from the given
     *            Collection of {@link ModDifferenceVo}
     * @param request RequestVo to commit
     * @param differences Collection of {@link ModDifferenceVo} between the old and new ManagedItemVo
     * @param user UserVo performing commit
     * @param ignoreUserRule if true the system will approve the request even if the user making the request to apprve
     *          was the same user who did the last action on the request.
     * @return {@link ProcessedRequestVo} Updated item, request information, and warnings
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    @Override
    public synchronized ProcessedRequestVo commitRequest(final ManagedItemVo oldItem, final RequestVo request,
                                                         final Collection<ModDifferenceVo> differences, UserVo user,
                                                         boolean ignoreUserRule) 
        throws ValidationException {

        //Try to retrieve the request - this will throw an error if the request has already been completed.
        requestCapability.retrieve(request.getId());

        checkCommitRequestPreconditions(oldItem, request, differences, user);

        ManagedItemVo itemCopy = oldItem.copy();
        RequestVo requestCopy = request.copy();

        if (requestCopy.getRequestType().isAddition()) {
            StringBuffer note = new StringBuffer();

            if (StringUtils.isNotEmpty(requestCopy.getNewNote())) {
                note.append(user.getUsername());
                note.append(": ");
                note.append(requestCopy.getNewNote());
                note.append(LINE_SEPARATOR);
                note.append(LINE_SEPARATOR);
            }

            note.append(StringUtils.trimToEmpty(requestCopy.getNote()));
            requestCopy.setNote(note.toString());
        }

        Collection<ModDifferenceVo> acceptedDifferences = filterAcceptedDifferences(differences);

        ManagedItemVo parentItem = null;

        if (itemCopy.isNdcItem()) {
            parentItem = retrieve(itemCopy.getParentId(), itemCopy.getParent().getEntityType());
        }
        
        if (parentItem != null) {
            if (RequestItemStatus.REJECTED.equals(parentItem.getRequestItemStatus())) {
                if (StringUtils.isBlank(itemCopy.getRejectionReasonText())) {
                    itemCopy.setRejectionReasonText(parentItem.getRejectionReasonText());
                    requestCopy.setRejectionReasonText(parentItem.getRejectionReasonText());
                }
            
                if (itemCopy.getRequestRejectionReason() == null) {
                    itemCopy.setRequestRejectionReason(parentItem.getRequestRejectionReason());
                    requestCopy.setRequestRejectionReason(parentItem.getRequestRejectionReason());
                }
            }
        }

        requestCopy = requestCapability.commitRequest(itemCopy, requestCopy, acceptedDifferences, user, 
            parentItem, ignoreUserRule);

        itemCopy = applyChanges(itemCopy, requestCopy, user);

        Collection<ModDifferenceVo> approvedRequests = filterApprovedDifferences(requestCopy.getRequestDetails());
        checkForDuplicate(itemCopy, requestCopy, approvedRequests);

       

        if (requestCopy.isCompleted()) {
            
            // If the request was completed.
            request.setDependentRequests(getDependenciesToApproveOrReject(itemCopy, true));

            for (ManagedItemVo reqItem : request.getDependentRequests().keySet()) {
                RequestVo depReq = request.getDependentRequests().get(reqItem);
                
                // if the dependencies have already been approved; just not yet updated then skip this part.
                if (depReq != null && !depReq.isApproved()) {
                    depReq.adjustNewModDifferences(depReq.getRequestDetails());
                    depReq.setNewItemRequestStatus(RequestItemStatus.APPROVED);
                    depReq.setUnderReview(false);
                    request.setRejectionReasonText(null);
                    request.setRequestRejectionReason(null);
                    reqItem.setRequestRejectionReason(null);
                    reqItem.setRejectionReasonText(null);
                    enforceBusinessRules(reqItem, user, false);
                    ProcessedRequestVo processedVo = commitRequest(reqItem, depReq, 
                        new ArrayList<ModDifferenceVo>(), user, true);
                    updateReferences(processedVo, itemCopy);
                    
                   
                }
            }
            
            // end
            
            itemCopy = processCompletedRequest(itemCopy, requestCopy, approvedRequests, user);

            if (itemCopy.isOrderableItemItem()) {
                cascadeChangesToChildren(itemCopy, user, oldItem, approvedRequests);
            }

            //Put the dependent requests approvals somewhere in here - sgw 1/9/2012
            
        } else {

            // Set the request item status to pending so the VUID and effective date validators aren't hit
            // Since the itemCopy gets blown away, this doesn't have any long term effects
            itemCopy.setRequestItemStatus(RequestItemStatus.PENDING);
            itemCopy.validate();
            itemCopy = oldItem;
        }

        Errors warnings = getRulesCapability(oldItem.getEntityType()).checkForWarnings(itemCopy, null, false);

        return new ProcessedRequestVo(itemCopy, requestCopy, warnings);
    }

    

    /**
     * generateVUID creates a VUID for a newly approved item.
     * 
     * @param item The managedItem
     * @param user The user doing the approval.
     */
    void generateVUID(ManagedItemVo item, UserVo user) {
        
        if (item.hasVuid()) {
            
            String vuid = "";
            boolean updateNS = true;
            List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();
            NationalSettingVo nationalSettingVo = null; 
            
            for (NationalSettingVo vo : settingsList) {
                if (NationalSetting.CURRENT_VUID.toString().equals(vo.getKeyName())) {
                    Long value = vo.getIntegerValue();
                    vuid = value.toString();
                    value++;
                    vo.setIntegerValue(value);
                    nationalSettingVo = vo;

                    break;
                }
            }
            
            List<VuidStatusHistoryVo> effective = new ArrayList<VuidStatusHistoryVo>();
            VuidStatusHistoryVo statusHistory = new VuidStatusHistoryVo();
            statusHistory.setEffectiveDateTime(new Date(System.currentTimeMillis()));
            statusHistory.setEffectiveDtmStatus(ItemStatus.ACTIVE);
            statusHistory.setVuid(Long.valueOf(vuid));            
            
            if (EntityType.PRODUCT.equals(item.getEntityType())) {
                ProductVo vo = (ProductVo) item;
                vo.setVuid(vuid);
                vo.setMasterEntryForVuid(true);
                statusHistory.setItemType(VuidItemType.PRODUCTS);
                effective.add(statusHistory);
                vo.setEffectiveDates(effective);
            } else if (EntityType.INGREDIENT.equals(item.getEntityType())) {
                
                GenericNameVo generic;
                
                try {
                    
                    generic = (GenericNameVo) genericNameDomainCapability.retrieveByName(item.getValue());
                } catch (Exception e) {
                    generic = null;
                }
                
                IngredientVo vo = (IngredientVo) item;

                if (generic != null && StringUtils.isNotBlank(generic.getVuid())) {
                    vo.setVuid(generic.getVuid());
                    updateNS = false;
                } else {
                    vo.setVuid(vuid);
                }
                
                vo.setMasterEntryForVuid(true);
                statusHistory.setItemType(VuidItemType.INGREDIENTS);
                effective.add(statusHistory);
                vo.setEffectiveDates(effective);
            } else if (EntityType.DRUG_CLASS.equals(item.getEntityType())) {
                DrugClassVo vo = (DrugClassVo) item;
                vo.setVuid(vuid);
                vo.setMasterEntryForVuid(true);
                statusHistory.setItemType(VuidItemType.DRUG_CLASSES);
                effective.add(statusHistory);
                vo.setEffectiveDates(effective);
            } else if (EntityType.GENERIC_NAME.equals(item.getEntityType())) {
                
                IngredientVo ing;
                
                try {
                    ing = (IngredientVo) ingredientDomainCapability.retrieveByName(item.getValue());
                } catch (Exception e) {
                    ing = null;
                }
                
                GenericNameVo vo = (GenericNameVo) item;
                
                if (ing != null && StringUtils.isNotBlank(ing.getVuid())) {
                    vo.setVuid(ing.getVuid());
                    updateNS = false;
                } else {
                    vo.setVuid(vuid);
                }
              
                vo.setMasterEntryForVuid(true);
                statusHistory.setItemType(VuidItemType.GENERIC);
                effective.add(statusHistory);
                vo.setEffectiveDates(effective);
            }
            
            if (updateNS && nationalSettingVo != null) {
                nationalSettingDomainCapability.update(nationalSettingVo, user);
            }
        } 
    }
    
    /**
     * Process a completed request.
     * <p>
     * Sends given {@link ManagedItemVo} to STS
     * 
     * @param item ManagedItemVo associated with the request
     * @param request RequestVo to process
     * @param differences all accepted and approved {@link ModDifferenceVo}
     * @param user {@link UserVo} making request
     * @return updated {@link ManagedItemVo}
     * 
     * @throws ValidationException if error validating data
     */
    private ManagedItemVo processCompletedRequest(ManagedItemVo item, RequestVo request,
                                                  Collection<ModDifferenceVo> differences, UserVo user) 
        throws ValidationException {

        ManagedItemVo updatedItem = item;

        if (request.isAddition()) {
            updatedItem = processCompletedAddition(item, request, differences, user);
            
        } else if (request.isModification()) {
            updatedItem = processCompletedModification(item, request, differences, user);
        } else {
            updatedItem = processCompletedGenericRequest(item, request, differences, user);
        }

        updatedItem = getRulesCapability(item.getEntityType()).processCompletedRequest(request, item, differences, user);

        ItemAuditHistoryVo history = updatedItem.getNewAuditHistory();

        // process the dependency List
        request.setDependentRequests(this.getDependenciesToApproveOrReject(item, false));

        HashMap<String, ManagedItemVo> approvedDrugUnits = new HashMap<String, ManagedItemVo>();
        
        for (ManagedItemVo reqItem : request.getDependentRequests().keySet()) {
            
            // so the issue here is that I can have the same item in multiple dependencies;  
            // So if I have already approved a drug unit then I need to force that drug unit on product.
            // Process the Not NDC requests at this point.
            if (!(EntityType.NDC.equals(reqItem.getEntityType()))) {
                RequestVo depReq = request.getDependentRequests().get(reqItem);
                depReq.adjustNewModDifferences(depReq.getRequestDetails());
                depReq.setNewItemRequestStatus(request.getNewItemRequestStatus());
                depReq.setRejectionReasonText(request.getRejectionReasonText());
                depReq.setRequestRejectionReason(request.getRequestRejectionReason());
                depReq.setUnderReview(false);
                
                if (EntityType.DRUG_UNIT.equals(reqItem.getEntityType())) {
                    if (approvedDrugUnits.containsKey(reqItem.getValue())) {
                        if (updatedItem instanceof ProductVo) {
                            ((ProductVo) updatedItem).setProductUnit((DrugUnitVo) approvedDrugUnits.get(reqItem.getValue()));
                            
                            if (((ProductVo) updatedItem).getActiveIngredients() != null 
                                && ((ProductVo) updatedItem).getActiveIngredients().size() > 1) {
                                for (ActiveIngredientVo aiVo : ((ProductVo) updatedItem).getActiveIngredients()) {
                                    if (approvedDrugUnits.containsKey(aiVo.getDrugUnit().getValue())) {
                                        aiVo.setDrugUnit((DrugUnitVo) approvedDrugUnits.get(reqItem.getValue()));
                                    }
                                }
                                
                            }
                        }
                    } else {
                        commitRequest(reqItem, depReq, new ArrayList<ModDifferenceVo>(), user, true);
                        approvedDrugUnits.put(reqItem.getValue(), reqItem);
                    }
                } else { 
                    ProcessedRequestVo processedVo = commitRequest(reqItem, depReq, new ArrayList<ModDifferenceVo>(), user, true);
                    updateReferences(processedVo, updatedItem);
                }
                
            }
                                                                                                    
        }
             
        // To get IEN
        if (RequestType.ADDITION.equals(request.getRequestType()) 
            && RequestItemStatus.APPROVED.equals(request.getNewItemRequestStatus())) {

            if (this.getVistaFileSynchCapability().sendNewItemToVista(updatedItem, 
                user, okToSendToVista(), catchingUp())) {
                addToQueue(updatedItem, updatedItem.getEntityType(), ItemAction.ADD, user);
            }
        }
        
        updatedItem = update(updatedItem, user);
        
        // DEBUG section
        if (EntityType.PRODUCT.equals(updatedItem.getEntityType())) {
            ProductVo product = (ProductVo) updatedItem;
            LOG.debug("Updated " + product.getVaProductName() + " is " + product.getNdfProductIen());
        }
        
        saveItemAuditHistoryRecords(history, user);

        
     // process the dependency List for NDCs only
        request.setDependentRequests(this.getDependenciesToApproveOrReject(item, false));
        
        for (ManagedItemVo reqItem : request.getDependentRequests().keySet()) {
            
            // Process the NDC requests at this point.
            if (EntityType.NDC.equals(reqItem.getEntityType())) {
                RequestVo depReq = request.getDependentRequests().get(reqItem);
                depReq.adjustNewModDifferences(depReq.getRequestDetails());
                depReq.setNewItemRequestStatus(request.getNewItemRequestStatus());
                depReq.setUnderReview(false);
                reqItem.setParent(updatedItem);
                commitRequest(reqItem, depReq, new ArrayList<ModDifferenceVo>(), user, true);
            }
                                                                                                    
        }
        
        return updatedItem;
    }

    /**
     * Handles processing requests that are not additions or modifications
     * 
     * @param item ManagedItemVo associated with the request
     * @param request RequestVo to process
     * @param differences all accepted and approved {@link ModDifferenceVo}
     * @param user {@link UserVo} making request
     * @return updated {@link ManagedItemVo}
     * @throws ValidationException ValidationException
     */
    private ManagedItemVo processCompletedGenericRequest(ManagedItemVo item, RequestVo request,
                                                         Collection<ModDifferenceVo> differences, UserVo user) 
        throws ValidationException {

        if (item.getNewAuditHistory() == null) {
            
            // create a new Audit History VO
            ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
            itemAudit.setAuditItemType(item.getEntityType());
            itemAudit.setAuditItemId(item.getId());
            itemAudit.setSiteName(this.getEnvironmentUtility().getSiteNumber()); 
            itemAudit.setEventCategory(EventCategory.COMPLETED_MODIFICATION_REQUEST);
            itemAudit.setNewValue("");
            itemAudit.setReason(""); 
            itemAudit.setOldValue("");
            itemAudit.setUsername(user.getUsername());
            item.setNewAuditHistory(itemAudit);
        } else {
            item.getNewAuditHistory().setEventCategory(EventCategory.NATIONAL_MODIFICATION);

            // We didn't actually go through a request, so every change is just a national modification
            for (ItemAuditHistoryDetailsVo detail : item.getNewAuditHistory().getDetails()) {
                detail.setEventCategory(EventCategory.NATIONAL_MODIFICATION);
            }
        }

        // This function is not part of PPS-N 1.0
        // stsUpdate(differences, item, user);

        return item;
    }

    /**
     * This method processes new and existing items passed from the local Vista This method processes new or existing items
     * passed from the local vista site during the drug accountability update. The method allows for an NDC to be added or
     * updated or a product to be updated. The bcreate parameter indicates whether this NDC should be added or updated.
     * 
     * @param ndcVoFromVista NDC ManagedItemVo the item to add or update
     * @param ndcItemAuditHistoryVo the list of audit changes
     * @param bCreate True if this is a new item and false if this is an update
     * @param productVoFromVista Product Vo from Vista
     * @param productItemAuditHistoryVo The list of audit changes from the product
     */
    @Override
    public void processFromLocalVista(ManagedItemVo ndcVoFromVista, ItemAuditHistoryVo ndcItemAuditHistoryVo, boolean bCreate,
                                      ManagedItemVo productVoFromVista, ItemAuditHistoryVo productItemAuditHistoryVo) {
        LOG.debug("National doesn't have a local VistA, so we won't process anything here.");
    }

    /**
     * Process a completed addition request.
     * <p>
     * Sends given {@link ManagedItemVo} to STS and creates an {@link ItemAuditHistoryVo} record.
     * 
     * @param item ManagedItemVo associated with the request
     * @param request RequestVo to process
     * @param differences all accepted and approved {@link ModDifferenceVo}
     * @param user {@link UserVo} making request
     * @return updated {@link ManagedItemVo}
     * 
     * @throws ValidationException if error validating data
     */
    private ManagedItemVo processCompletedAddition(ManagedItemVo item, RequestVo request,
                                                   Collection<ModDifferenceVo> differences, UserVo user) 
        throws ValidationException {

        EventCategory event = EventCategory.APPROVED_REQUEST;

        if (request.isRejected()) {
            event = EventCategory.REJECTED_REQUEST;
            request.setNote(request.getRejectionReasonText());
        } else { 
            generateVUID(item, user);
        }

        // if the item is approved or rejected, put the note in the reason field
        if (item.getNewAuditHistory() == null) {
            ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
            itemAudit.setAuditItemType(item.getEntityType());
            itemAudit.setAuditItemId(item.getId());
            itemAudit.setSiteName(this.getEnvironmentUtility().getSiteNumber()); 
            itemAudit.setEventCategory(event);
            itemAudit.setReason(""); 
            itemAudit.setOldValue("");
            itemAudit.setNewValue("");
            itemAudit.setUsername(user.getUsername());
            item.setNewAuditHistory(itemAudit);
        } else {
            item.getNewAuditHistory().setReason(request.getNote());
        }

        item.getNewAuditHistory().setEventCategory(event);

        // This function is not necessary PPS-N 1.0.
        //stsComplete(differences, item, user);

        return item;
    }

    /**
     * Process a completed modification request.
     * <p>
     * Sends given {@link ManagedItemVo} to STS and creates an {@link ItemAuditHistoryVo} record.
     * 
     * @param item ManagedItemVo associated with the request
     * @param request RequestVo to process
     * @param differences all accepted and approved {@link ModDifferenceVo}
     * @param user {@link UserVo} making request
     * @return updated {@link ManagedItemVo}
     * 
     * @throws ValidationException if error validating data
     */
    private ManagedItemVo processCompletedModification(ManagedItemVo item, RequestVo request,
                                                       Collection<ModDifferenceVo> differences, UserVo user) 
        throws ValidationException {

        if (item.getNewAuditHistory() == null) {
            ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
            itemAudit.setAuditItemType(item.getEntityType());
            itemAudit.setAuditItemId(item.getId());
            itemAudit.setSiteName(this.getEnvironmentUtility().getSiteNumber()); 
            itemAudit.setEventCategory(EventCategory.COMPLETED_MODIFICATION_REQUEST);
            itemAudit.setReason(""); 
            itemAudit.setOldValue("");
            itemAudit.setNewValue("");
            itemAudit.setUsername(user.getUsername());
            item.setNewAuditHistory(itemAudit);
        } else {
            item.getNewAuditHistory().setEventCategory(EventCategory.COMPLETED_MODIFICATION_REQUEST);
        }

        // This method is not part of PPS-N 1.0
        //stsUpdate(differences, item, user);

        return item;
    }

    /**
     * Apply the changes, in memory, specified in the given {@link RequestVo#getRequestDetails()}.
     * <p>
     * This method does not commit the changes to the database!
     * 
     * @param oldItem original {@link ManagedItemVo} (from the database, without changes) on which to apply changes
     * @param request {@link RequestVo} making the changes, including all of the recently accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} making the request
     * @return Collection of {@link ItemAuditHistoryVo}
     */
    @Override
    public ManagedItemVo applyChanges(ManagedItemVo oldItem, RequestVo request, UserVo user) {

        // If we've completed this request and its rejected, we don't want to apply any pending changes
        if (request != null && !request.isFinalRejectionOfAddRequest()) {
            oldItem.updateFromRequest(request, user, getEnvironmentUtility());
        }

        return oldItem;
    }

    /**
     * Check for changes in uniqueness fields, requiring a re-dup check.
     * <p>
     * Duplicate check not performed if this is a final rejection of an add request.
     * 
     * @param item {@link ManagedItemVo} to test
     * @param request {@link RequestVo} to test
     * @param approvedDifferences Collection of approved {@link ModDifferenceVo}
     * 
     * @throws DuplicateItemException if duplicate item exists
     */
    private void checkForDuplicate(ManagedItemVo item, RequestVo request, Collection<ModDifferenceVo> approvedDifferences) 
        throws DuplicateItemException {
        if (!request.isFinalRejectionOfAddRequest() && item.hasUpdatedUniquenessCriteria(approvedDifferences)) {
            ManagedItemDomainCapability domainCapability =
                    getManagedItemCapabilityFactory().getDomainCapability(item.getEntityType());

            if (domainCapability.existsByUniquenessFields(item)) {
                throw new DuplicateItemException(DuplicateItemException.DUPLICATE_ITEM, new Object[] { item.toShortString() });
            }
        }
    }

    /**
     * Verify that the item, user, differences, and request are not null.
     * 
     * @param managedItem {@link ManagedItemVo}
     * @param request {@link RequestVo}
     * @param differences Collection of {@link ModDifferenceVo}
     * @param user {@link UserVo}
     */
    private void checkCommitRequestPreconditions(ManagedItemVo managedItem, RequestVo request,
                                                 Collection<ModDifferenceVo> differences, UserVo user) {

        if (user == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, FieldKey.USER.getLocalizedName(Locale.getDefault()));
        }

        if (managedItem == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, "Managed Item");
        }

        if (request == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, "Request");
        }

        if (differences == null) {
            throw new ServiceException(ServiceException.ILLEGAL_ARGUMENT, "Differences");
        }
    }

    /**
     * Send the given modified item to all Locals.
     * <p>
     * When a mod request is generated at national, no message should be sent to local. When a mod request is generated at
     * local and sent to national, no message should be broadcast back to the locals. Once a mod request is completed
     * (approved or rejected), a message should be sent to all locals.
     * 
     * @param request {@link RequestVo} to send, may be null if this is a completed request
     * @param managedItem {@link ManagedItemVo} to send
     * @param user {@link UserVo} sending the item
     * @param modifications The list of Item audit history details
     */
    @Override
    protected void sendModificationToPeps(RequestVo request, ManagedItemVo managedItem, UserVo user,
                                          ItemAuditHistoryVo modifications) {

        // The update() method calls with a null request -- assume that it should always be sent
        if (request == null || RequestState.COMPLETED.equals(request.getRequestState())) {

            // PPS-L Functionality - Setting a member variable (strParentIdtoNotSend) is NOT thread-safe. 
            // Spring provides one instance of the class per VM. 
            // This must be handled in some fashion that does not include using a member-level variable.
            if (managedItem.getId().equals(getStrParentIdtoNotSend())) {
                LOG.debug("The system did NOT send '" + managedItem.getEntityType() + " : " + managedItem.toShortString()
                          + "' to all Locals because the item was the parent of a parent/child update!");
            } else {
                if (managedItem.getEntityType().isEntityType()
                    || (managedItem.getEntityType().isDomainType() && !managedItem.isLocalOnlyData())) {
                    managedItem.setNewAuditHistory(modifications);

                    // The local functionality is not used in PPS-N 1.0.  Need to add this back in PPS-L 1.0.
                    //     LOG.debug("National Send " + managedItem.getEntityType() + ": " + managedItem.toShortString());
                    //     sendToLocalCapability.send(managedItem);
                } else {
                    LOG.debug("The system Did NOT send '" + managedItem.getEntityType() + ": " + managedItem.toShortString()
                              + "' to all Locals because it was a National-only domain type!");
                }
            } 
        } else {
            LOG.debug("Did NOT send '" + managedItem.getEntityType() + ": " + managedItem.toShortString()
                      + "' to all Locals because the request was not completed!");
        }
    }

    /**
     * Reject the problem report.
     * 
     * @param item ManagedItemVo
     * @param request RequestVo to reject
     * @param user UserVo
     * @return rejected RequestVo
     */
    @Override
    public RequestVo rejectProblemReport(ManagedItemVo item, RequestVo request, UserVo user) {
        request.setNewItemRequestStatus(RequestItemStatus.REJECTED);
        request.setRequestState(RequestState.COMPLETED);
        request.setRequestState(RequestState.COMPLETED);
        request.setUnderReview(false);
        request.setRejectionReasonText("Rejected by national manager.");
        request.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);

        getRequestDomainCapability().updateRequest(request, user);

        return request;
    }

    /**
     * Send STS messages when an Add request is completed.
     * 
     * @param differences Collection of accepted and approved {@link ModDifferenceVo}
     * @param item ManagedItemVo
     * @param user {@link UserVo} making request
     */

    /* Commenting out this method for PPS-N 1.0, this may become necessary again in PPS-N 2.0 so am leaving the code in for now.
    private void stsComplete(Collection<ModDifferenceVo> differences, ManagedItemVo item, UserVo user) {
        try {
            if (item.hasVuid()) {
                if (item.getRequestItemStatus().isRejected()) {
                    getStsCapability().inactivate(item);
                }
                else {
                    updateVuid(differences, item, user);

                    if (item.getRequestItemStatus().isApproved()) {
                        String vuid = (String) ReflectionUtility.getValue(item, FieldKey.VUID);
                        getStsCapability().setNational(vuid);
                    }
                }
            }
        }
        catch (Exception e) {
            LOG.error("Could not make call to STS", e);
        }
    }
    */

    /**
     * If the VUID needs updating (uniqueness fields on the item changed), then inactivate the existing item in STS and
     * request a new VUID.
     * 
     * @param differences Collection of accepted and approved {@link ModDifferenceVo}
     * @param item {@link ManagedItemVo} with VUID
     * @param user {@link UserVo} making request
     */

    /* Commenting out this method for PPS-N 1.0, this may become necessary again in PPS-N 2.0 so am leaving the code in for now.
    private void updateVuid(Collection<ModDifferenceVo> differences, ManagedItemVo item, UserVo user) {
        if (item.isVuidUpdateNeeded(differences)) {
            String oldVuid = ReflectionUtility.getValue(item, FieldKey.VUID);
            getStsCapability().inactivate(item);
            String newVuid = getStsCapability().assignVuid(item);
            ReflectionUtility.setValue(item, FieldKey.VUID, newVuid);

            item.getNewAuditHistory().addDetail(
                new ItemAuditHistoryDetailsVo(EventCategory.NATIONAL_MODIFICATION,
                    "Item uniqueness fields changed, so inactivated old VUID and requested new VUID from STS.", "VUID",
                    oldVuid, newVuid));

        }
    }
    */

    /**
     * Updates VUID in STS as needed based on changes to uniqueness fields, when a modification request is completed.
     * 
     * @param differences Collection of accepted and approved {@link ModDifferenceVo}
     * @param item managed item
     * @param user {@link UserVo} making request
     */

    /* Commenting out this method for PPS-N 1.0, this may become necessary again in PPS-N 2.0 so am leaving the code in for now.
       private void stsUpdate(Collection<ModDifferenceVo> differences, ManagedItemVo item, UserVo user) {
        try {
            if (item.hasVuid()) {
                updateVuid(differences, item, user);

                for (ModDifferenceVo diffVo : differences) {
                    FieldKey key = diffVo.getDifference().getFieldKey();
                    Object oldValue = diffVo.getDifference().getOldValue();
                    Object newValue = diffVo.getDifference().getNewValue();

                    // if item status changes
                    if (FieldKey.ITEM_STATUS.equals(key)) {

                        // if item is inactivated
                        if (ItemStatus.ACTIVE.equals(oldValue) && ItemStatus.INACTIVE.equals(newValue)) {
                            getStsCapability().inactivate(item);
                        }

                        // if item is reactivated
                        else if (ItemStatus.INACTIVE.equals(oldValue) && ItemStatus.ACTIVE.equals(newValue)) {
                            getStsCapability().activate(item);
                        }
                    }

                    // if drug class parent is changed
                    else if (FieldKey.PARENT_DRUG_CLASS.equals(key)) {
                        String vuid = (String) ReflectionUtility.getValue(item, FieldKey.VUID);

                        // if drug class parent is added
                        if (oldValue == null && newValue != null) {
                            getStsCapability().updateDrugClassParent(vuid, ((DrugClassVo) newValue).getVuid());
                        }

                        // if drug class parent is removed
                        else if (oldValue != null && newValue == null) {
                            getStsCapability().removeDrugClassParent(vuid);
                        }
                    }

                    // a field is updated/approved that sts cares about
                    else if (FieldKey.DRUG_CLASSES.equals(key)) {
                        getStsCapability().update(item);
                    }
                    else if (FieldKey.GCN_SEQUENCE_NUMBER.equals(key)) {
                        getStsCapability().update(item);
                    }
                }
            }
        }
        catch (Exception e) {
            LOG.error("Could not make call to STS", e);
        }
    }
    */

    /**
     * Creates a request for second national review if the modDifferences for the ManagedItemVo requires it.
     * 
     * If no request is necessary, return null.
     * 
     * If a RequestVo is made, it is first saved to the database before returning.
     * 
     * @param modDifferences Differences in the given item
     * @param managedItem ManagedItemVo to create request for (Note: It doesn't matter if this item was updated with the mod
     *            diff collection or not)
     * @param user UserVo creating request
     * @return add RequestVo, if needed because 2-review fields were changed. (null if no request necessary)
     */
    @Override
    protected RequestVo createModificationRequest(Collection<ModDifferenceVo> modDifferences, ManagedItemVo managedItem,
                                                  UserVo user) {
        RequestVo modRequest = null;
        Collection<FieldKey> twoReviewFields =
                managedItem.listSecondReviewFields(getEnvironmentUtility().getEnvironment(), user.getRoles());
        Collection<ModDifferenceVo> modDiffsForRequest = new ArrayList<ModDifferenceVo>();
        boolean isAtLeastOneFieldNeedingTwoReviews = false;

        // Check to see if one or more of the field changes required two reviews.
        for (ModDifferenceVo modDiff : modDifferences) {
            Difference diff = modDiff.getDifference();

            if (modDiff.isAcceptChange()) {

                // Add to the list of accepted mod diffs to possibly added to a mod request if needed.
                modDiffsForRequest.add(modDiff);

                // If this field requires two reviews, save it for a mod request later on down below.
                if (twoReviewFields.contains(diff.getFieldKey())) {
                    isAtLeastOneFieldNeedingTwoReviews = true;
                }
            }
        }

        // If we have at least one two-review field change...
        if (isAtLeastOneFieldNeedingTwoReviews) {

            // ...Make all field changes two-review field-level mod requests.
            for (ModDifferenceVo modDiff : modDiffsForRequest) {

                if (managedItem.getNewAuditHistory() == null) {
                    ItemAuditHistoryVo newAuditHistory =
                            new ItemAuditHistoryVo(managedItem, modDiff, getEnvironmentUtility().getSiteNumber(),
                                                   EventCategory.MODIFICATION_REQUEST, user.getUsername());
                    managedItem.setNewAuditHistory(newAuditHistory);
                }

                // The modification is valid and therefore we need to generate an IAH record
                ItemAuditHistoryDetailsVo itemAudit =
                        new ItemAuditHistoryDetailsVo(managedItem, modDiff, getEnvironmentUtility().getSiteNumber(),
                                                      EventCategory.MODIFICATION_REQUEST, user.getUsername());
                managedItem.getNewAuditHistory().addDetail(itemAudit);

                // for all accepted changes needing 2nd review, set the ModDifferenceVo status to "APPROVED"
                modDiff.setModRequestItemStatus(RequestItemStatus.APPROVED);
                modDiff.setSiteName(getEnvironmentUtility().getSiteNumber());
                modDiff.setRequestor(user);
                modDiff.setReviewer(user); // Current PNM mod counts as first reviewer.

                // If the field is a VADF one, then be sure to set the request-to-make-editable status.
                Difference diffs = modDiff.getDifference();

                if (diffs.getNewValue() instanceof DataField) {
                    modDiff.setRequestToMakeEditable(((DataField) diffs.getNewValue()).isEditable());
                }
            }

            // Create a mod request for these field-level mod request details.
            modRequest =
                    new RequestVo(managedItem.getId(), managedItem.getEntityType(), RequestType.MODIFICATION,
                                  RequestState.PENDING_SECOND_REVIEW, user, user, RequestItemStatus.PENDING, false, false,
                                  modDiffsForRequest);
            modRequest = getRequestDomainCapability().create(modRequest, user);
        }

        return modRequest;
    }

    /**
     * Inserts a pending item that was created at local.
     * 
     * @param managedItem the new item
     * @param user the requesting userVo
     * @return ManagedItemVo
     */
    @Override
    public ManagedItemVo insertFromLocal(ManagedItemVo managedItem, UserVo user) {

        ManagedItemVo newItem = null;

        /* This is PPS-L functionality
        try {
            enforceBusinessRules(managedItem, user, true);
            newItem = insertItem(managedItem, user);
        } catch (DuplicateItemException e) {
            LOG.error(e);
            newItem.inactivate();
        } catch (ValidationException e) {
            LOG.error(e);
            insertSystemNotification(managedItem, user, "Receive from local failed, see system logs.", e);
        } catch (Throwable t) {
            LOG.error(t);
            insertSystemNotification(managedItem, user, "Receive from local failed, see system logs.", t);
        }

        // send the product to all the locals
        //    sendToLocalCapability.send(newItem);
        */

        return newItem;
    }

    /**
     * If the ManagedItemVo is a two review item, create a RequestVo and save it to the database for the given new
     * ManagedItemVo. Otherwise return null.
     * 
     * @param managedItem ManagedItemVo to create request for
     * @param user UserVo creating request
     * @return add RequestVo created (null if not created)
     */
    @Override
    protected RequestVo createAddRequest(ManagedItemVo managedItem, UserVo user) {
        RequestVo request = null;

        if (managedItem.isTwoReviewItem()) {
            request =
                    new RequestVo(managedItem.getId(), managedItem.getEntityType(), RequestType.ADDITION,
                                  RequestState.PENDING_SECOND_REVIEW, user, user, RequestItemStatus.APPROVED, false, false,
                                  null);

            getRequestDomainCapability().create(request, user);
        }

        return request;
    }

    /**
     * Create and save the IAH records for an ADD of a ManagedItemVo by the given user.
     * 
     * @param managedItem ManagedItemVo for which to create IAH
     * @param user UserVo adding the item
     */
    @Override
    protected void createAddItemAuditHistory(ManagedItemVo managedItem, UserVo user) {
        ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
        itemAudit.setAuditItemType(managedItem.getEntityType());
        itemAudit.setAuditItemId(managedItem.getId());
        itemAudit.setSiteName(getEnvironmentUtility().getSiteNumber()); 
        itemAudit.setEventCategory(EventCategory.REQUEST_TO_ADD);
        itemAudit.setReason(""); 
        itemAudit.setOldValue("");
        itemAudit.setNewValue("");
        itemAudit.setUsername(user.getUsername());

        if (!managedItem.isTwoReviewItem()) {
            itemAudit.setEventCategory(EventCategory.APPROVED_REQUEST);
        }

        managedItem.setNewAuditHistory(itemAudit);
        saveItemAuditHistoryRecords(itemAudit, user);
    }

    /**
     * Send the ManagedItemVo to local
     * 
     * @param request {@link RequestVo} to send
     * @param managedItem {@link ManagedItemVo} to send
     * @param user {@link UserVo} sending the item
     */
    @Override
    protected void sendAddToPeps(RequestVo request, ManagedItemVo managedItem, UserVo user) {

        //    sendToLocalCapability.send(managedItem);
    }

    /**
     * Set the status on a ManagedItemVo to be added.
     * 
     * @param managedItem ManagedItemVo to alter
     */
    @Override
    protected void setAddStatus(ManagedItemVo managedItem) {
        if (managedItem.isTwoReviewItem()) {
            managedItem.setRequestItemStatus(RequestItemStatus.PENDING);
            managedItem.setItemStatus(ItemStatus.ACTIVE);
        } else {
            managedItem.setRequestItemStatus(RequestItemStatus.APPROVED);
            managedItem.setItemStatus(ItemStatus.ACTIVE);
        }
    }

    /**
     * setRequestCapability.
     * @param requestCapability property
     */
    public void setRequestCapability(RequestCapability requestCapability) {
        this.requestCapability = requestCapability;
    }

    /**
     * setIngredientDomainCapability.
     * @param ingredientDomainCapabilityIn property
     */
    public void setIngredientDomainCapability(IngredientDomainCapability ingredientDomainCapabilityIn) {
        this.ingredientDomainCapability = ingredientDomainCapabilityIn;
    }
    
    /**
     * setGenericNameDomainCapability.
     * @param genericNameDomainCapability property
     */
    public void setGenericNameDomainCapability(GenericNameDomainCapability genericNameDomainCapability) {
        this.genericNameDomainCapability = genericNameDomainCapability;
    }
    
}
