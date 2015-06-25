/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.capability.impl;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestMessageVo;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.RulesCapability;
import gov.va.med.pharmacy.peps.service.local.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.SendToNationalCapability;


/**
 * Managed Item Capability contains code common to all item types that is specific to Local.
 */
public class ManagedItemCapabilityImpl extends
    gov.va.med.pharmacy.peps.service.common.capability.impl.ManagedItemCapabilityImpl implements ManagedItemCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagedItemCapabilityImpl.class);
    private SendToNationalCapability sendToNationalCapability;

    /**
     * Create a RequestVo and save it to the database for the given new ManagedItemVo.
     * 
     * @param managedItem ManagedItemVo to create request for
     * @param user UserVo creating request
     * @return add RequestVo created
     */
    @Override
    protected RequestVo createAddRequest(ManagedItemVo managedItem, UserVo user) {
        return new RequestVo(managedItem.getId(), managedItem.getEntityType(), RequestType.ADDITION,
            RequestState.PENDING_FIRST_REVIEW, null, user, RequestItemStatus.PENDING, false, false, null);
    }

    /**
     * If this request is not for a ManagedItem where only local only data is included, then send the request to national.
     * 
     * @param request {@link RequestVo} to send
     * @param managedItem {@link ManagedItemVo} to send
     * @param user {@link UserVo} sending the item
     */
    @Override
    protected void sendAddToPeps(RequestVo request, ManagedItemVo managedItem, UserVo user) {
        if (!managedItem.isLocalOnlyData()) {
            sendToNational(request, managedItem, user);
        }
    }

    /**
     * Wrap the ManagedItemVo and RequestVo in a RequestMessageVo and send to national.
     * 
     * @param request {@link RequestVo} to send
     * @param managedItem {@link ManagedItemVo} to send
     * @param user {@link UserVo} sending the item
     */
    private void sendToNational(RequestVo request, ManagedItemVo managedItem, UserVo user) {
        RequestMessageVo reqMsg = new RequestMessageVo(managedItem, request, user);
        sendToNationalCapability.send(reqMsg);
    }

    /**
     * Set the status on a ManagedItemVo to be added.
     * 
     * @param managedItem ManagedItemVo to alter
     */
    @Override
    protected void setAddStatus(ManagedItemVo managedItem) {
        if (managedItem.isLocalOnlyData()) {
            managedItem.setRequestItemStatus(RequestItemStatus.APPROVED);
        } else {
            managedItem.setRequestItemStatus(RequestItemStatus.PENDING);
        }
    }

    /**
     * Create a RequestVo for the given modified ManagedItemVo.
     * 
     * If no request is necessary, return null.
     * 
     * @param modDifferences Differences in the given item
     * @param managedItem ManagedItemVo to create request for
     * @param user UserVo creating request
     * @return add RequestVo created (null if no request necessary)
     */
    @Override
    protected RequestVo createModificationRequest(Collection<ModDifferenceVo> modDifferences, ManagedItemVo managedItem,
                                                  UserVo user) {
        RequestVo request = null;

        // If it is a domain type AND is Dosage form, or if it isn't a domain type AND isn't a local-only entity,
        // Then create a mod request
        if ((managedItem.getEntityType().isDomainType() && managedItem instanceof DosageFormVo)
            || (!managedItem.getEntityType().isDomainType() && !managedItem.isLocalOnlyData())) {

            // Determine which mods need to have requests created
            Collection<ModDifferenceVo> finalModDiffs = new ArrayList<ModDifferenceVo>();

            for (ModDifferenceVo modDiff : modDifferences) {
                Difference diffs = modDiff.getDifference();

                if (modDiff.isAcceptChange()) {
                    checkModDifs(diffs, managedItem, modDifferences, user, 
                                 finalModDiffs, modDiff);
                }
            }

            if (!finalModDiffs.isEmpty()) {
                request = new RequestVo(finalModDiffs);

                request.setNewItemRequestor(user);
                request.setSiteName(getEnvironmentUtility().getSiteNumber());
                request.setItemId(managedItem.getId());
                request.setEntityType(managedItem.getEntityType());
                request.setRequestType(RequestType.MODIFICATION);
                request.setRequestState(RequestState.PENDING_FIRST_REVIEW);
                request.setNewItemRequestStatus(RequestItemStatus.PENDING);
                request.setUnderReview(false);
                request.setMarkedForPepsSecondReview(false);
            }
        }

        return request;
    }

    /**
     * checkModDifs
     * @param diffs diffs
     * @param managedItem managedItem
     * @param modDifferences modDifferences
     * @param user user
     * @param finalModDiffs finalModDiffs
     * @param modDiff modDiff
     */
    private void checkModDifs(Difference diffs, ManagedItemVo managedItem,
        Collection<ModDifferenceVo> modDifferences, UserVo user, 
        Collection<ModDifferenceVo> finalModDiffs, 
        ModDifferenceVo modDiff) {
        if (diffs.getNewValue() instanceof DataField) {
            DataField df = (DataField) diffs.getNewValue();

            if (!df.isEditable()) {

                if (managedItem.getNewAuditHistory() == null) {
                    ItemAuditHistoryVo newAuditHistory = new ItemAuditHistoryVo(managedItem, modDiff,
                        getEnvironmentUtility().getSiteNumber(), EventCategory.MODIFICATION_REQUEST, user
                            .getUsername());
                    managedItem.setNewAuditHistory(newAuditHistory);
                }

                // The modification is valid and therefore we need to generate an IAH record
                ItemAuditHistoryDetailsVo itemAudit = new ItemAuditHistoryDetailsVo(managedItem, modDiff,
                    getEnvironmentUtility().getSiteNumber(), EventCategory.MODIFICATION_REQUEST, user
                        .getUsername());
                managedItem.getNewAuditHistory().addDetail(itemAudit);

                // for all accepted non-editable changes, set the ModDifferenceVo status to "PENDING"
                modDiff.setModRequestItemStatus(RequestItemStatus.PENDING);
                modDiff.setSiteName(getEnvironmentUtility().getSiteNumber());
                modDiff.setRequestor(user);
                finalModDiffs.add(modDiff);
            }
        }
    }
    
    /**
     * If the given RequestVo is not null, send the Requestvo item to national.
     * 
     * @param request {@link RequestVo} to send
     * @param managedItem {@link ManagedItemVo} to send
     * @param user {@link UserVo} sending the item
     * @param modifications ItemAuditHistoryVo
     */
    protected void sendModificationToPeps(RequestVo request, ManagedItemVo managedItem, UserVo user,
                                          ItemAuditHistoryVo modifications) {
        if (request != null) {
            sendToNational(request, null, user);
        }
    }

    /**
     * This method processes new or existing items passed from the local vista site during the drug accountability update.
     * The method allows for an NDC to be added or updated or a product to be updated. The bcreate parameter indicates
     * whether this NDC should be added or updated.
     * 
     * @param ndcVoFromVista ManagedItemVo the NDC item to add or update.
     * @param ndcItemAuditHistoryVo the list of audit changes for the NDC
     * @param bCreate True if this is a new NDC item and false if this is an NDC update
     * @param productVoFromVista the product item to update
     * @param productItemAuditHistoryVo the list of audit changes for the product.
     * @throws Exception
     */
    
//    public void processFromLocalVista(ManagedItemVo ndcVoFromVista, ItemAuditHistoryVo ndcItemAuditHistoryVo,
//                                      boolean bCreate, ManagedItemVo productVoFromVista,
//                                      ItemAuditHistoryVo productItemAuditHistoryVo) throws Exception {
//
//        UserVo user = new UserVo();
//        user.setUsername("Drug Accountability");
//        user.setFirstName("Drug");
//        user.setLastName("Accountability");
//        user.setStationNumber(getEnvironmentUtility().getSiteNumber());
//
//        // Add the NDC if available
//        try {
//            if (ndcVoFromVista != null) {
//                if (bCreate) {
//                    enforceBusinessRules(ndcVoFromVista, null, true);
//                    ManagedItemVo ndcVo = insertItem(ndcVoFromVista, null);
//
//                    if (ndcItemAuditHistoryVo != null) {
//                        String id = ndcVo.getId();
//
//                        // need to set this id here. I don't get the ID until I
//                        // save the item in the step above
//                        ndcItemAuditHistoryVo.setAuditItemId(id);
//                    }
//
//                    ndcVo.setNewAuditHistory(ndcItemAuditHistoryVo);
//
//                    RequestVo requestVo = new RequestVo();
//                    requestVo.setCreatedBy("Drug Accountability");
//                    requestVo.setEntityType(EntityType.NDC);
//                    requestVo.setItemId(ndcVo.getId());
//                    requestVo.setNewItemRequestor(user);
//                    requestVo.setNote("This NDC was added via the automated drug accountability process.");
//                    requestVo.setRequestState(RequestState.PENDING_FIRST_REVIEW);
//                    requestVo.setRequestType(RequestType.ADDITION);
//                    requestVo.setNewItemRequestStatus(RequestItemStatus.PENDING);
//                    requestVo.setSiteName(getEnvironmentUtility().getSiteNumber());
//
//                    this.sendToNational(requestVo, ndcVo, user);
//
//                }
//                else {
//
//                    // Protect editable local settings from national update.
//                    update(ndcVoFromVista, user);
//                }
//
//                // Record IAH records representing the changes about to be applied to the local item
//
//                if (ndcItemAuditHistoryVo != null) {
//                    saveItemAuditHistoryRecords(ndcItemAuditHistoryVo, user);
//                }
//            }
//        }
//        catch (DuplicateItemException e) {
//            insertSystemNotification(ndcVoFromVista, null,
//                "Receive from Vista failed with unexpected duplicate item error, see system logs.", e);
//            throw e;
//        }
//        catch (ValidationException e) {
//            insertSystemNotification(ndcVoFromVista, null,
//                "Receive from Vista failed with unexpected validation error, see system logs.", e);
//            throw e;
//        }
//        catch (Exception e) {
//            insertSystemNotification(ndcVoFromVista, null,
//                "Receive from Vista failed with unexpected validation error, see system logs.", e);
//
//            throw e;
//        }
//        catch (Error e) {
//            insertSystemNotification(ndcVoFromVista, null, "Receive from Vista failed, see system logs.", e);
//
//            throw e;
//        }
//
//        // Update the product if provided
//        try {
//            if (productVoFromVista != null) {
//                update(productVoFromVista, user);
//
//                // records the IAH records for the product update
//                if (productItemAuditHistoryVo != null) {
//                    saveItemAuditHistoryRecords(productItemAuditHistoryVo, user);
//                }
//            }
//        }
//        catch (DuplicateItemException e) {
//            insertSystemNotification(productVoFromVista, null,
//                "Receive from Vista failed with unexpected duplicate item error, see system logs.", e);
//            throw e;
//        }
//        catch (ValidationException e) {
//            insertSystemNotification(productVoFromVista, null,
//                "Receive from Vista failed with unexpected validation error, see system logs.", e);
//            throw e;
//        }
//        catch (Exception e) {
//            insertSystemNotification(productVoFromVista, null,
//                "Receive from Vista failed with unexpected validation error, see system logs.", e);
//
//            throw e;
//        }
//        catch (Error e) {
//            insertSystemNotification(productVoFromVista, null,
//                "Receive from Vista failed with unexpected validation error, see system logs.", e);
//            throw e;
//        }
//
//    }

    /**
     * Process new or existing items passed from national to local.
     * <p>
     * If ID exists, update existing item.
     * <p>
     * Else, if exists by uniqueness fields, merge local/national items.
     * <p>
     * Else insert new item.
     * <p>
     * Finally, if the nationalItem is not PENDING, save the IAH records.
     * 
     * @param nationalItem {@link ManagedItemVo} from national
     * @return {@link ManagedItemVo} updated in database
     */
    public ManagedItemVo processFromNational(ManagedItemVo nationalItem) {
        ManagedItemDomainCapability domainCapability = getManagedItemCapabilityFactory().getDomainCapability(
            nationalItem.getEntityType());
        RulesCapability rulesCapability = getManagedItemCapabilityFactory().getRulesCapability(nationalItem.getEntityType());

        UserVo systemUser = UserVo.getSystemUser(getEnvironmentUtility());
        ManagedItemVo updatedItem = null;

        // We do need to add IAH entries for pending items that are just inserted
        boolean newInsert = false;

        try {
            if (domainCapability.existsById(nationalItem.getId())) {
                updatedItem = rulesCapability.updateFromNational(nationalItem, systemUser);
            } else if (domainCapability.existsByUniquenessFields(nationalItem)) {
                nationalItem.setIsBeingReplacedByNational(true);

                updatedItem = rulesCapability.mergeFromNational(nationalItem, systemUser);
            } else {
                updatedItem = rulesCapability.insertFromNational(nationalItem, systemUser);
                newInsert = true;
            }

            if (!RequestItemStatus.PENDING.equals(nationalItem.getRequestItemStatus()) || newInsert) {
                saveItemAuditHistoryRecords(nationalItem.getNewAuditHistory(), systemUser);
            }
        } catch (DuplicateItemException e) {
            LOG.error("Unexpected error receiving from national!", e);
            insertSystemNotification(nationalItem, systemUser,
                "Receive from national failed with unexpected duplicate item error, see system logs.", e);
        } catch (ValidationException e) {
            LOG.error("Unexpected error receiving from national.", e);
            insertSystemNotification(nationalItem, systemUser,
                "Receive from national failed with unexpected validation error, see system logs.", e);
        } catch (Throwable t) {
            LOG.error("Unexpected error receiving from national", t);
            insertSystemNotification(nationalItem, systemUser, "Receive from national failed, see system logs.", t);
        }

        return updatedItem;
    }

    /**
     * Save the problem report.
     * 
     * @param item ManagedItemVo
     * @param comment String contains problem report text.
     * @param user UserVo
     */
    public void submitProblemReport(ManagedItemVo item, String comment, UserVo user) {
        RequestVo requestVo = new RequestVo(item.getId(), item.getEntityType(), RequestType.PROBLEM_REPORT,
            RequestState.PENDING_FIRST_REVIEW, user, user, RequestItemStatus.PENDING, false, false, null);

        requestVo.setNote(comment);
        requestVo.setEntityType(item.getEntityType());

        sendToNational(requestVo, null, user);
    }

    /**
     * setSendToNationalCapability
     * 
     * @param sendToNationalCapability property
     */
    public void setSendToNationalCapability(SendToNationalCapability sendToNationalCapability) {
        this.sendToNationalCapability = sendToNationalCapability;
    }

}
