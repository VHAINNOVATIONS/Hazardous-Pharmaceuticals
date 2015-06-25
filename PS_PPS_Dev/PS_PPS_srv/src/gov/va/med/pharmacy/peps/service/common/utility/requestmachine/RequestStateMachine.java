/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;


/**
 * Super class to Request State Machines, supporting the common methods used in sub classes.
 */
public abstract class RequestStateMachine {
    private ManagedItemVo managedItem;
    private ManagedItemVo parentItem;
    private RequestVo oldRequest;
    private EnvironmentUtility environmentUtility;

    /**
     * Default Constructor. Set the {@link ManagedItemVo}, the {@link RequestVo}, and the {@link EnvironmentUtility}
     * 
     * @param managedItem the item being added
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility}
     */
    protected RequestStateMachine(ManagedItemVo managedItem, ManagedItemVo parentItem,
        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        setManagedItem(managedItem);
        setParentItem(parentItem);
        setOldRequest(oldRequest);
        setEnvironmentUtility(environmentUtility);

        getManagedItem().setRequestItemStatus(getRequestItemStatus());
    }

    /**
     * Method used to process a given state to transition the {@link RequestVo} into the next state.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule boolean
     * @return the next state in the {@link RequestStateMachine}
     * @throws ValidationException if error in request data
     */
    protected abstract RequestStateMachine processStateChange(RequestVo request,
                                                              Collection<ModDifferenceVo> acceptedDifferences, UserVo user,
                                                              boolean ignoreUserRule)
        throws ValidationException;

    /**
     * Process the request state change.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule ignore the User Rule
     * @return the next state in the {@link RequestStateMachine}
     * @throws ValidationException if error in request data
     */
    public RequestStateMachine process(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user, 
        boolean ignoreUserRule)
        throws ValidationException {

        if (request.isUnderReview()) {
            return underReview(request, acceptedDifferences, user);
        } else {
            return processStateChange(request, acceptedDifferences, user, ignoreUserRule);
        }
    }

    /**
     * Marks a request under review.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @return the next state in the {@link RequestStateMachine}
     * @throws ValidationException if error in request data
     */
    protected RequestStateMachine underReview(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user)
        throws ValidationException {

        RequestVo updatedRequest = updateRequest(request, acceptedDifferences, user);

        return new UnderReviewState(getManagedItem(), parentItem, updatedRequest, getEnvironmentUtility());
    }

    /**
     * True if the {@link #getRequestStatus()} is equal to {@link RequestState#COMPLETED}, otherwise false.
     * 
     * @return boolean if this state is completed
     */
    public final boolean isCompleted() {
        return RequestState.COMPLETED.equals(getRequestStatus());
    }

    /**
     * Returns what Request status the add request with this state should be in
     * 
     * @return RequestState status
     */
    public abstract RequestState getRequestStatus();

    /**
     * Returns the request item status of the item as it is in the current state
     * 
     * @return RequestItemStatus
     */
    public abstract RequestItemStatus getRequestItemStatus();

    /**
     * Uses the {@link RequestVo#checkForConflicts(Collection)} and given Collection of accepted {@link ModDifferenceVo} to
     * see if there are any conflicts.
     * <p>
     * If any conflicts are present, throw a BusinessRuleException.
     * 
     * @param request {@link RequestVo} to check
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo}
     * 
     * @throws BusinessRuleException if any conflicts
     */
    protected void checkForConflicts(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences)
        throws BusinessRuleException {

        if (request.checkForConflicts(acceptedDifferences)) {
            throw new BusinessRuleException(BusinessRuleException.REQUEST_CONFLICTS);
        }
    }

    /**
     * Sets the {@link RequestItemStatus} to {@link RequestItemStatus#APPROVED}, the site name to the current
     * {@link EnvironmentUtility#getSiteNumber()}, and the requestor to the given {@link UserVo} for all newly accepted
     * differences and adds each accepted difference to the {@link RequestVo#getRequestDetails()} Collection.
     * <p>
     * Sets the reviewer for all {@link RequestVo#getRequestDetails()} to the given {@link UserVo}.
     * <p>
     * Sets the {@link RequestVo#getLastReviewer()} to the given {@link UserVo}.
     * 
     * @param request the {@link RequestVo} to update
     * @param acceptedDifferences the new accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} modifying {@link RequestVo}
     * @return updated {@link RequestVo}
     */
    protected RequestVo updateRequest(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user) {
        for (ModDifferenceVo difference : acceptedDifferences) {
            difference.setModRequestItemStatus(RequestItemStatus.APPROVED);
            difference.setSiteName(getEnvironmentUtility().getSiteNumber());
            difference.setRequestor(user);

            request.getRequestDetails().add(difference);
        }

        for (ModDifferenceVo mod : request.getRequestDetails()) {
            mod.setReviewer(user);
        }

        request.setLastReviewer(user);

        return request;
    }

    /**
     * Look for new field changes that require two reviews...these force a follow-on review session.
     * 
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} making the request
     * @return boolean
     */
    protected boolean hasSecondReviewField(Collection<ModDifferenceVo> acceptedDifferences, UserVo user) {
        boolean hasSecondReviewField = false;

        Collection<FieldKey> secondReviewFields = getManagedItem().listSecondReviewFields(
            getEnvironmentUtility().getEnvironment(), user.getRoles());

        for (ModDifferenceVo modDiff : acceptedDifferences) {
            if (!(RequestItemStatus.REJECTED.equals(modDiff.getModRequestItemStatus()))) {
                Difference diff = modDiff.getDifference();
  
                if (secondReviewFields.contains(diff.getFieldKey())) {
                    hasSecondReviewField = true;
                    break;
                }
            }
        }

        return hasSecondReviewField;
    }

    /**
     * Look for changes in the RequestItemStatus of an existing request detail ModDifferenceVo.
     * @param request {@link RequestVo} in its current state
     * @param oldRequest {@link RequestVo} in its old state
     * @param user {@link UserVo} making the request
     * @return true if requires second approval
     * @throws BusinessRuleException if error in business logic
     */
    public static boolean requestItemStatusChanged(RequestVo request, RequestVo oldRequest, UserVo user)
        throws BusinessRuleException {
        boolean requiresSecondReview = false;
        Collection<ModDifferenceVo> requestDetails = request.getRequestDetails();
        Map<String, ModDifferenceVo> originalDifferences = new HashMap<String, ModDifferenceVo>();

        for (ModDifferenceVo modDifference : oldRequest.getRequestDetails()) {
            originalDifferences.put(modDifference.getId(), modDifference);
        }

        for (ModDifferenceVo nextNewDiff : requestDetails) {
            String nextId = nextNewDiff.getId();

            if (nextId == null) {
                throw new BusinessRuleException(BusinessRuleException.NO_NEW_DIFFERENCES);
            }

            ModDifferenceVo nextOrigDiff = originalDifferences.get(nextId);

            if (nextOrigDiff == null) {
                throw new BusinessRuleException(BusinessRuleException.NO_NEW_DIFFERENCES);
            }

            // if the new mod request is not rejected (ie. either approved or pending) and the 
            // mod request state has changed from the previous version.
            if ((!(RequestItemStatus.REJECTED.equals(nextNewDiff.getModRequestItemStatus())) 
                && nextNewDiff.getModRequestItemStatus() != nextOrigDiff.getModRequestItemStatus())) {
                requiresSecondReview = nextNewDiff.getDifference().getFieldKey().isRequiresSecondApproval();

                if (!requiresSecondReview && FieldKey.ITEM_STATUS.equals(nextNewDiff.getDifference().getFieldKey())) {
                    requiresSecondReview = EntityType.isSecondApprovalType(request.getEntityType());
                }

                if (requiresSecondReview) {
                    break;
                }
            }
        }

        return requiresSecondReview;
    }

    /**
     * 
     * Checks to see if the parent of the NDC is still pending
     *
     * @return true if ndc parent is pending
     */
    protected boolean isParentProductPending() {
        
        return (getManagedItem() instanceof NdcVo && (getParentItem() != null && getParentItem().getRequestItemStatus().equals(
            RequestItemStatus.PENDING)));
    }

    /**
     * Sub classes can override to determine if the {@link RequestItemStatus} for the given {@link FieldKey} should cause a
     * second review.
     * <p>
     * Called when a single {@link ModDifferenceVo} has a changed {@link RequestItemStatus} between what is in the database
     * and what the user passed in. The given {@link RequestItemStatus} is the new value provided by the user.
     * <p>
     * Default implementation returns true.
     * 
     * @param fieldKey {@link FieldKey} with the change in {@link RequestItemStatus}
     * @param requestItemStatus {@link RequestItemStatus} provided by user (changed from what is in the database)
     * @param user {@link UserVo} making the request
     * @return boolean
     * 
     * @throws BusinessRuleException if error in business logic
     */
    protected boolean changeRequiresSecondReview(FieldKey fieldKey, RequestItemStatus requestItemStatus, UserVo user)
        throws BusinessRuleException {
        return true;
    }

    /**
     * getManagedItem
     * 
     * @return managedItem property
     */
    public ManagedItemVo getManagedItem() {
        return managedItem;
    }

    /**
     * 
     * @param managedItem managedItem property
     */
    private void setManagedItem(ManagedItemVo managedItem) {
        this.managedItem = managedItem;
    }

    /**
     * getParentItem
     * 
     * @return managedItem property
     */
    public ManagedItemVo getParentItem() {
        return parentItem;
    }

    /**
     * setParentItem
     * 
     * @param parentItem parentItem property
     */
    private void setParentItem(ManagedItemVo parentItem) {
        this.parentItem = parentItem;
    }

    /**
     * The {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database).
     * 
     * @return request property
     */
    public RequestVo getOldRequest() {
        return oldRequest;
    }

    /**
     * The {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database).
     * 
     * @param request request property
     */
    private void setOldRequest(RequestVo request) {
        this.oldRequest = request;
        request.setRequestState(getRequestStatus());
    }

    /**
     * getEnvironmentUtility
     * 
     * @return environmentUtility property
     */
    protected EnvironmentUtility getEnvironmentUtility() {
        return environmentUtility;
    }

    /**
     * 
     * @param environmentUtility environmentUtility property
     */
    private void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }
}
