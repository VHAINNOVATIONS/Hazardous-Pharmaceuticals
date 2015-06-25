/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;


/**
 * Initial state for a request from local. There are no approvals,
 */
public class PendingFirstApprovalAddState extends AbstractAddState {

    /**
     * Default Constructor, we need the item, as there is no reviewer and no mod differences
     * 
     * @param managedItem the item being added
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)o
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment
     */
    public PendingFirstApprovalAddState(ManagedItemVo managedItem, ManagedItemVo parentItem,
                                        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * Method used to approve a given state to determine what state it should be in next
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule ignore the user rule
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     */
    @Override
    public RequestStateMachine approve(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user,
        boolean ignoreUserRule)
        throws ValidationException {

        checkForConflicts(request, acceptedDifferences);

        RequestVo updatedRequest = updateRequest(request, acceptedDifferences, user);

        if (getManagedItem().isTwoReviewItem()) {
            return new PendingSecondApprovedAddState(getManagedItem(), getParentItem(),
                updatedRequest, getEnvironmentUtility());
        } else {
            return new ApprovedAddState(getManagedItem(), getParentItem(), updatedRequest, getEnvironmentUtility());
        }
    }

    /**
     * Method used to reject a given state to determine what state it should be in next
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     */
    @Override
    public RequestStateMachine reject(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user)
        throws ValidationException {
        RequestVo updatedRequest = updateRequest(request, acceptedDifferences, user);

        return new RejectedAddState(getManagedItem(), getParentItem(), updatedRequest, getEnvironmentUtility());
    }

    /**
     * Returns what Request status the add request with this state should be in
     * 
     * @return RequestState status
     */
    @Override
    public RequestState getRequestStatus() {
        return RequestState.PENDING_FIRST_REVIEW;
    }

    /**
     * Returns the request item status of the item as it is in the current state
     * 
     * @return RequestItemStatus
     */
    @Override
    public RequestItemStatus getRequestItemStatus() {
        return RequestItemStatus.PENDING;
    }
}
