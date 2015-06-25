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
 * ApprovedAddState
 */
public class ApprovedAddState extends AbstractAddState {

    /**
     * RejectedAdd state, indicates this is finished
     * 
     * @param managedItem rejected item
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment
     */
    protected ApprovedAddState(ManagedItemVo managedItem, ManagedItemVo parentItem,
        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);

        oldRequest.setRejectionReasonText(null);
    }

    /**
     * 
     * This request is finished, this should never be called, so it throws an exception
     * 
     * @param user userVo
     * @return throws an exception
     * 
     * @throws ValidationException if the validation fails
     */
    public AbstractAddState underReview(UserVo user) throws ValidationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns what Request status the add request with this state should be in
     * 
     * @return RequestState status
     */
    @Override
    public RequestState getRequestStatus() {
        return RequestState.COMPLETED;
    }

    /**
     * Returns the request item status of the item as it is in the current state
     * 
     * @return RequestItemStatus
     */
    @Override
    public RequestItemStatus getRequestItemStatus() {
        return RequestItemStatus.APPROVED;
    }

    /**
     * Method used to approve a given state to determine what state it should be in next.  
     * This is an approval flow method.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule Ignore the user rule
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
     * Method used to reject a given state to determine what state it should be in next
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user The user who made the request.
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     */
    @Override
    public RequestStateMachine reject(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user)
        throws ValidationException {
        throw new UnsupportedOperationException();
    }
}
