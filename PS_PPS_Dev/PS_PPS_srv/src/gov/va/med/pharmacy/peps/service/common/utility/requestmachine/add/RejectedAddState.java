/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add;


import java.util.Collection;

import org.apache.commons.lang.StringUtils;

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
 * Rejected request state.
 */
public class RejectedAddState extends AbstractAddState {

    /**
     * RejectedAdd state, indicates this is finished
     * 
     * @param managedItem rejected item
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment
     */
    protected RejectedAddState(ManagedItemVo managedItem, ManagedItemVo parentItem,
        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);

        if (StringUtils.isEmpty(oldRequest.getRejectionReasonText())) {
            oldRequest.setRejectionReasonText(oldRequest.getNote());
        } else {
            oldRequest.setRejectionReasonText(oldRequest.getRejectionReasonText() + " " + oldRequest.getNote());
        }

        rejectTheItem(oldRequest, managedItem);
    }

    /**
     * 
     * This request is finished, this should never be called, so it throws an exception
     * 
     * @param user userVo
     * @return throws an exception
     * @throws ValidationException if the validation fails
     */
    public AbstractAddState underReview(UserVo user) throws ValidationException {
        throw new UnsupportedOperationException();
    }

    /**
     * RejectAddStat RequestStatus
     * Returns what Request status the add request with this state should be in
     * 
     * @return RequestState status
     */
    @Override
    public RequestState getRequestStatus() {
        return RequestState.COMPLETED;
    }

    /**
     * Returns the request item status of the item as it is in the current state for the RequestAddState
     * object.
     * 
     * @return RequestItemStatus
     */
    @Override
    public RequestItemStatus getRequestItemStatus() {
        return RequestItemStatus.REJECTED;
    }

    /**
     * Updates the given {@link ManagedItemVo} as inactive and rejected.
     * 
     * @param updatedRequest RequestVo
     * @param currentItem ManagedItemVo
     */
    private void rejectTheItem(RequestVo updatedRequest, ManagedItemVo currentItem) {
        currentItem.inactivate();
        currentItem.rejectItem(updatedRequest.getRequestRejectionReason(), updatedRequest.getRejectionReasonText());
    }

    /**
     * RejectAddState approve method.
     * Method used to approve a given state to determine what state it should be in next
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed for RequestAddState
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule ignore the user rule.
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     */
    @Override
    public RequestStateMachine approve(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user,
        boolean ignoreUserRule)
        throws ValidationException {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * Method used to reject a given state to determine what state it should be in next
     * 
     * @param request details on the request
     * @param modifications modifications the user made
     * @param user which user approved this
     * @return the next ModRequestState
     * @throws ValidationException ValidationException
     */
    @Override
    public RequestStateMachine reject(RequestVo request, Collection<ModDifferenceVo> modifications, UserVo user)
        throws ValidationException {
        throw new UnsupportedOperationException();
    }
}
