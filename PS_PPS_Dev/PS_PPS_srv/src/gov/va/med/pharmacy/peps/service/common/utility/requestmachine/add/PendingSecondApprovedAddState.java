/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;


/**
 * Pending Second Approval request state.
 */
public class PendingSecondApprovedAddState extends AbstractAddState {

    /**
     * We need the item, who reviewed it (if any) and the request Details
     * 
     * @param managedItem the item being added
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment
     */
    public PendingSecondApprovedAddState(ManagedItemVo managedItem, ManagedItemVo parentItem,
                                         RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * Method used to approve a given state to determine what state it should be in next.  This is used 
     * during the request approval flows.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request.  The user is logged in the audit trail.
     * @param ignoreUserRule ignore the user rule
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     */
    @Override
    public RequestStateMachine approve(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user,
        boolean ignoreUserRule)
        throws ValidationException {

        checkForConflicts(request, acceptedDifferences);
        boolean userTheSame = getOldRequest().getLastReviewer().getUsername().equals(user.getUsername());
        
        if (ignoreUserRule) {
            userTheSame = false;
        }
        
        if (EntityType.isSecondApprovalType(request.getEntityType())) {
            if (userTheSame || hasModDifferenceChanges(request, acceptedDifferences, user)) {
                return new PendingSecondApprovedAddState(getManagedItem(), getParentItem(),
                    updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());
            }
        } else if (isParentProductPending()) {
            return new PendingSecondApprovedAddState(getManagedItem(), getParentItem(),
                updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());
        }
         
        return new ApprovedAddState(getManagedItem(), getParentItem(),
            updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());

    }

    /**
     * Method used to reject a given state to determine what state it should be in next
     * 
     * @param request request being checked in.
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     */
    @Override
    public RequestStateMachine reject(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user)
        throws ValidationException {
      
        return new RejectedAddState(getManagedItem(), getParentItem(),
            updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());
        
    }

    /**
     * Determines if the modDifferences for this item have changed, returns true if they have, false otherwise
     * 
     * @param request The requestVo used in this medthod
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} making the request
     * @return boolean
     * 
     * @throws BusinessRuleException if business logic error
     */
    private boolean hasModDifferenceChanges(RequestVo request,
                                            Collection<ModDifferenceVo> acceptedDifferences, UserVo user)
        throws BusinessRuleException {

        boolean returnValue = false;
     
        if (!acceptedDifferences.isEmpty()) {
            for (ModDifferenceVo vo : acceptedDifferences) {
                if (vo.getDifference().getFieldKey().isRequiresSecondApproval()) {
                    returnValue = true;
                    break;
                }
            }
        }
        
        return returnValue || requestItemStatusChanged(request, getOldRequest(), user);
    }

    /**
     * Returns what Request status the add request with this state should be in
     * the PendingSecondApprovalState
     * 
     * @return RequestState status
     */
    @Override
    public RequestState getRequestStatus() {
        return RequestState.PENDING_SECOND_REVIEW;
    }

    /**
     * Returns the request item status of the item as it is in the current state in the
     * PendingSecondApprovalState.
     * 
     * @return RequestItemStatus
     */
    @Override
    public RequestItemStatus getRequestItemStatus() {
        return RequestItemStatus.APPROVED;
    }
}
