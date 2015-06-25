/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.exception.ServiceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.RequestMessageVo;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NotificationDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachineFactory;
import gov.va.med.pharmacy.peps.service.national.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.national.capability.RequestCapability;


/**
 * Search, retrieve, and commit {@link RequestVo}.
 * <p>
 * National implementation performs work as all {@link RequestVo} are stored at National.
 */
public class RequestCapabilityImpl implements RequestCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RequestCapabilityImpl.class);

    private RequestDomainCapability requestDomainCapability;
    private ManagedItemCapability managedItemCapability;
    private NotificationDomainCapability notificationDomainCapability;
    private RequestStateMachineFactory requestStateMachineFactory;

    /**
     * Commit a {@link RequestVo} to the database.
     * <p>
     * The {@link RequestVo} returned has all of the given {@link ModDifferenceVo} applied to its
     * {@link RequestVo#getRequestDetails()}.
     * 
     * @param managedItem The request in the form of a ManagedItemVo
     * @param request The request in the form of a RequestVo.
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being committed
     * @param user {@link UserVo} committing request
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param ignoreUserRule Do not take into account the rule considering users approving their own requests.
     * @return updated {@link RequestVo}
     * @throws ValidationException if error validating data
     */
    @Override
    public RequestVo commitRequest(ManagedItemVo managedItem, RequestVo request,
        Collection<ModDifferenceVo> acceptedDifferences, UserVo user, ManagedItemVo parentItem, 
        boolean ignoreUserRule) throws ValidationException {
        RequestVo oldRequest = requestDomainCapability.retrieve(request.getId());

        RequestStateMachine requestMachine =
            requestStateMachineFactory.getRequestStateMachine(managedItem, parentItem, oldRequest);
        
        requestMachine = requestMachine.process(request, acceptedDifferences, user, ignoreUserRule);

        RequestVo updatedRequest = requestDomainCapability.createRequestDetails(requestMachine.getOldRequest(), user);
        updatedRequest = requestDomainCapability.updateRequest(updatedRequest, user);

        return updatedRequest;
    }

    /**
     * This method is used to process a request Message from local
     * 
     * @param requestMessage The Message passed in from the local site.
     * @param user {@link UserVo} performing the action
     * @throws ValidationException An exception
     * 
     * @see gov.va.med.pharmacy.peps.service.national.capability.RequestCapability
     * #processRequestMessage(gov.va.med.pharmacy.peps.common.vo.RequestMessageVo)
     */
    @Override
    public void processRequestMessage(RequestMessageVo<ManagedItemVo> requestMessage, UserVo user) throws ValidationException {

        if (requestMessage.getRequest() == null) {
            throw new ServiceException(ServiceException.PROCESS_MESSAGE_FAILURE);
        }

        Collection<ItemAuditHistoryVo> itemAuditHistory;

        switch (requestMessage.getRequest().getRequestType()) {
            case ADDITION:
                itemAuditHistory = processAddition(requestMessage);
                requestDomainCapability.create(requestMessage.getRequest(), user);
                break;
            case MODIFICATION:
                itemAuditHistory = processModification(requestMessage);

                String requestId =
                    requestDomainCapability.getModificationRequestId(requestMessage.getRequest().getItemId(), requestMessage
                        .getRequest().getEntityType());

                if (requestId == null) {
                    requestDomainCapability.create(requestMessage.getRequest(), user);
                } else {
                    requestMessage.getRequest().setId(requestId);
                    requestDomainCapability.createRequestDetails(requestMessage.getRequest(), user);
                }

                break;
            case PROBLEM_REPORT:
                itemAuditHistory = processProblemReport(requestMessage);
                requestDomainCapability.create(requestMessage.getRequest(), user);
                break;
            default:
                throw new ServiceException(ServiceException.PROCESS_MESSAGE_UNKNOWN_REQUEST_TYPE, requestMessage.getRequest()
                    .getRequestType().toString());
        }

        saveItemAuditHistoryRecords(itemAuditHistory, user);

    }

    /**
     * Process a addition message.
     * 
     * @param requestMessage {@link RequestMessageVo} to process
     * @return Collection of {@link ItemAuditHistoryVo} resulting from processing the message
     * 
     * @throws ValidationException if error during processing of message
     */
    private Collection<ItemAuditHistoryVo> processAddition(RequestMessageVo<ManagedItemVo> requestMessage)
        throws ValidationException {

        ItemAuditHistoryVo itemAudit = requestMessage.getNewItem().getNewAuditHistory();
        Collection<ItemAuditHistoryVo> itemAuditHistory = new ArrayList<ItemAuditHistoryVo>();

        itemAuditHistory.add(itemAudit);

        managedItemCapability.insertFromLocal(requestMessage.getNewItem(), requestMessage.getRequest().getNewItemRequestor());

        return itemAuditHistory;
    }

    /**
     * Process a modification message.
     * 
     * @param requestMessage {@link RequestMessageVo} to process
     * @return Collection of {@link ItemAuditHistoryVo} resulting from processing the message
     * 
     * @throws ValidationException if error during processing of message
     */
    private Collection<ItemAuditHistoryVo> processModification(RequestMessageVo<ManagedItemVo> requestMessage)
        throws ValidationException {

        ManagedItemVo returnedItem =
            managedItemCapability
                .retrieve(requestMessage.getRequest().getItemId(), requestMessage.getRequest().getEntityType());

        Collection<ItemAuditHistoryVo> itemAuditHistory = new ArrayList<ItemAuditHistoryVo>();

        ItemAuditHistoryVo itemAudit = null;

        for (ModDifferenceVo modDiff : requestMessage.getRequest().getRequestDetails()) {

            if (itemAudit == null) {
                itemAudit =
                    new ItemAuditHistoryVo(returnedItem, modDiff, requestMessage.getRequest().getSiteName(),
                        EventCategory.MODIFICATION_REQUEST, requestMessage.getCreatedBy());
            }

            ItemAuditHistoryDetailsVo itemAuditDetail =
                new ItemAuditHistoryDetailsVo(returnedItem, modDiff, requestMessage.getRequest().getSiteName(),
                    EventCategory.MODIFICATION_REQUEST, requestMessage.getCreatedBy());

            itemAudit.addDetail(itemAuditDetail);
        }

        itemAuditHistory.add(itemAudit);

        return itemAuditHistory;
    }

    /**
     * Process a problem report message.
     * <p>
     * Currently does nothing and returns an empty Collection. This method is a placeholder for future development.
     * 
     * @param requestMessage {@link RequestMessageVo} to process
     * @return Collection of {@link ItemAuditHistoryVo} resulting from processing the message
     * 
     * @throws ValidationException if error during processing of message
     */
    private Collection<ItemAuditHistoryVo> processProblemReport(RequestMessageVo<ManagedItemVo> requestMessage)
        throws ValidationException {

        return Collections.emptyList();
    }

    /**
     * Performs the actions to create an Item Audit History record for each audit record using the notification to group the
     * records together for future retrieval.
     * 
     * @param itemAudits ItemAuditHistory
     * @param user {@link UserVo} performing the action
     */
    private void saveItemAuditHistoryRecords(Collection<ItemAuditHistoryVo> itemAudits, UserVo user) {
        if (!itemAudits.isEmpty()) {
            NotificationVo notifVo = new NotificationVo();
            notifVo.setItemAudits(itemAudits);
            notifVo.determineNotificationType();
            notificationDomainCapability.create(notifVo, user);
        }
    }

    /**
     * Retrieve request detail by ID.
     * <p>
     * Mark the retrieved {@link RequestVo} as not under review since the user must now request for it to be under review
     * again. This change, however, is not saved to the database.
     * 
     * @param requestId ID of the RequestVo to retrieve
     * @return RequestVo 
     * 
     * @throws ItemNotFoundException if cannot find Request with given ID
     * @throws RequestCompletedException If the request has already been completed
     */
    @Override
    public RequestVo retrieve(String requestId) throws ItemNotFoundException, RequestCompletedException {
        RequestVo request = requestDomainCapability.retrieve(requestId);

        if (request.isCompleted()) {
            throw new RequestCompletedException(RequestCompletedException.REQUEST_COMPLETE);
        }

        request.setUnderReview(false);

        return request;
    }

    /**
     * Retrieve all the RequestVo for the ManagedItemVo with the given ID and {@link EntityType}.
     * 
     * @param itemId ID for the {@link ManagedItemVo}
     * @param itemType {@link EntityType} for which to retrieve {@link RequestVo}
     * @return Collection<RequestVo>
     * @throws ItemNotFoundException if cannot find Request with given ID
     */
    @Override
    public Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException {
        return requestDomainCapability.retrieve(itemId, itemType);
    }
    
    /**
     * Retrieve request by eplId 
     *  
     * @param itemId an enterprise level id
     * @param entityType entityType
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.RequestDomainCapability
     *      #retrieveRequestDetailById(java.lang.String)
     */
    @Override
    public RequestVo retrieveNonCompletedRequest(String itemId, EntityType entityType) throws ItemNotFoundException {
        return requestDomainCapability.retrieveNonCompletedRequest(itemId, entityType);
    }

    /**
     * Search for the pending requests.
     * 
     * @param criteria for searchCriteria
     * @param user performing the search
     * @return Collection<RequestSummaryVo>
     * 
     * @throws ValueObjectValidationException if error in criteria data
     */
    @Override
    public Collection<RequestSummaryVo> search(SearchRequestCriteriaVo criteria, UserVo user)
        throws ValueObjectValidationException {
        criteria.validate();

        Collection<RequestSummaryVo> requests = new ArrayList<RequestSummaryVo>();

        if (criteria.isLocalSearch()) {
            requests.addAll(requestDomainCapability.localSearch(criteria));
        } else {
            requests.addAll(requestDomainCapability.nationalSearch(criteria, user));
        }

        return finishRetrieveDomainRequests(requests);
    }

    /**
     * Method processes a Collection of RequestSummaryVos and adds ManagedDataVo name and status information.
     * <p>
     * This might be bad for performance, as it has to hit the domain layer multiple times, but we currently need it for
     * display purposes.
     * 
     * @param requests requests without ManagedDataVo name and status info
     * @return updated requests with ManagedDataVo name and status info
     */
    private Collection<RequestSummaryVo> finishRetrieveDomainRequests(Collection<RequestSummaryVo> requests) {

        for (RequestSummaryVo request : requests) {
            if (request.getEntityType().isDomainType()) {

                try {
                    ManagedDataVo managedData =
                        (ManagedDataVo) managedItemCapability.retrieve(request.getRequestItemId(), request.getEntityType());

                    request.setItemName(managedData.getValue());
                    request.setItemStatus(managedData.getItemStatus());
                } catch (ItemNotFoundException e) {
                    LOG.error("Request saved for an item that does not exist!");
                }
            }
        }

        return requests;
    }

    /**
     * setManagedItemCapability
     * @param managedItemCapability managedItemCapability property
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * setRequestDomainCapability
     * 
     * @param requestDomainCapability property
     */
    public void setRequestDomainCapability(RequestDomainCapability requestDomainCapability) {
        this.requestDomainCapability = requestDomainCapability;
    }

    /**
     * setNotificationDomainCapability
     * 
     * @param notificationDomainCapability property
     */
    public void setNotificationDomainCapability(NotificationDomainCapability notificationDomainCapability) {
        this.notificationDomainCapability = notificationDomainCapability;
    }

    /**
     * setRequestStateMachineFactory
     * @param requestStateMachineFactory requestStateMachineFactory property
     */
    public void setRequestStateMachineFactory(RequestStateMachineFactory requestStateMachineFactory) {
        this.requestStateMachineFactory = requestStateMachineFactory;
    }
}
