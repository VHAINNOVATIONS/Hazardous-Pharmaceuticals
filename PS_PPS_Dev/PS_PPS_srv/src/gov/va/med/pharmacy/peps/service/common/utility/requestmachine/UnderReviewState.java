/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Mark the request as "under review".
 */
public class UnderReviewState extends RequestStateMachine {

    /**
     * Marks the request as 'under review'
     * 
     * @param managedItem managed Item for this request
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment
     */
    protected UnderReviewState(ManagedItemVo managedItem, ManagedItemVo parentItem,
        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * 
     * Method used to process a given state to determine what state it should be in next
     * 
     * @param request This is the RequestVo ValueObject that holds the detals about the request.
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule Ignore the User Rule
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     */
    @Override
    protected RequestStateMachine processStateChange(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences,
                                                     UserVo user, boolean ignoreUserRule) throws ValidationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns what Request status the add request with this state should be in
     * 
     * @return RequestState status
     */
    @Override
    public RequestState getRequestStatus() {
        return getOldRequest().getRequestState();
    }

    /**
     * Returns the request item status of the item as it is in the current state
     * 
     * @return RequestItemStatus
     */
    @Override
    public RequestItemStatus getRequestItemStatus() {
        return getOldRequest().getNewItemRequestStatus();
    }
}
