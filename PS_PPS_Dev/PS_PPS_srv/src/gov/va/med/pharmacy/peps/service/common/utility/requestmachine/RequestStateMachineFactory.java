/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine;


import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add.PendingFirstApprovalAddState;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add.PendingSecondApprovedAddState;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add.PendingSecondRejectedAddState;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify.PendingFirstApprovalModState;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify.PendingSecondApprovalModState;


/**
 * Returns the request state for the given item and request.
 */
public class RequestStateMachineFactory {
    private EnvironmentUtility environmentUtility;

    /**
     * Default, empty constructor.
     * <p>
     * Be sure to set the {@link EnvironmentUtility} via the {@link #setEnvironmentUtility(EnvironmentUtility)} method!
     */
    public RequestStateMachineFactory() {
        super();
    }

    /**
     * Set the {@link EnvironmentUtility}.
     * 
     * @param environmentUtility {@link EnvironmentUtility}
     */
    public RequestStateMachineFactory(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * Returns the request state for the given item and request
     * 
     * @param item the item for the request
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database).
     *            Used by request states to compare the old and new request for differences.
     * @return the request state machine for this request
     * @throws RequestCompletedException 
     */
    public RequestStateMachine getRequestStateMachine(ManagedItemVo item, ManagedItemVo parentItem, RequestVo oldRequest)
        throws RequestCompletedException {

        if (oldRequest.getRequestType().equals(RequestType.ADDITION)) {
            if (oldRequest.getRequestState().equals(RequestState.PENDING_FIRST_REVIEW)) {
                return new PendingFirstApprovalAddState(item, parentItem, oldRequest, getEnvironmentUtility());
            } else if (oldRequest.getRequestState().equals(RequestState.PENDING_SECOND_REVIEW)) {
                if (oldRequest.getNewItemRequestStatus().equals(RequestItemStatus.APPROVED)) {
                    return new PendingSecondApprovedAddState(item, parentItem, oldRequest, getEnvironmentUtility());
                } else {
                    return new PendingSecondRejectedAddState(item, parentItem, oldRequest, getEnvironmentUtility());
                }

            } else {
                throw new RequestCompletedException(RequestCompletedException.REQUEST_COMPLETE);
            }

        } else {
            if (oldRequest.getRequestState().equals(RequestState.PENDING_FIRST_REVIEW)) {
                return new PendingFirstApprovalModState(item, parentItem, oldRequest, getEnvironmentUtility());
            } else if (oldRequest.getRequestState().equals(RequestState.PENDING_SECOND_REVIEW)) {
                return new PendingSecondApprovalModState(item, parentItem, oldRequest, getEnvironmentUtility());
            } else {
                throw new RequestCompletedException(RequestCompletedException.REQUEST_COMPLETE);
            }

        }
    }

    /**
     * Setter injected by Spring.
     * 
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * 
     * @return environmentUtility property
     */
    private EnvironmentUtility getEnvironmentUtility() {
        return environmentUtility;
    }
}
