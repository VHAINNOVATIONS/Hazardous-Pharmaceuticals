/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;


/**
 * Request is in a completed modification state.
 */
public class CompletedModState extends AbstractModState {

    /**
     * Call the superclass constructor.
     * 
     * @param managedItem the item being added
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility}
     */
    public CompletedModState(ManagedItemVo managedItem, ManagedItemVo parentItem,
        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * getRequestStatus
     * @return {@link RequestState#COMPLETED}
     * 
     * @see gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine#getRequestStatus()
     */
    @Override
    public RequestState getRequestStatus() {
        return RequestState.COMPLETED;
    }

    /**
     * Complete the request.  this is not currently implemented.
     * 
     * @param request {@link RequestVo} being processed
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
}
