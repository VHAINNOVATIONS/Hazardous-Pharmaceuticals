/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;


/**
 * Pending first approval request state.
 */
public class PendingFirstApprovalModState extends AbstractModState {

    /**
     * Call the superclass constructor.
     * 
     * @param managedItem the item being added
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility}
     */
    public PendingFirstApprovalModState(ManagedItemVo managedItem, ManagedItemVo parentItem,
                                        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * getRequestStatus
     * @return Pending first
     * 
     * @see gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine#getRequestStatus()
     */
    @Override
    public RequestState getRequestStatus() {
        return RequestState.PENDING_FIRST_REVIEW;
    }

    /**
     * Call {@link RequestVo#checkFields()} and transition into the second revew or completed state.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule Ignore the User Rule
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if error in request data
     * 
     * @see gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine#processStateChange(RequestVo,
     *      java.util.Collection, gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    @Override
    protected RequestStateMachine processStateChange(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences,
                                                     UserVo user, boolean ignoreUserRule) throws ValidationException {

        checkForConflicts(request, acceptedDifferences);
        request.checkFields();

        if (requiresSecondReview(request, acceptedDifferences, user)) {
            return new PendingSecondApprovalModState(getManagedItem(), getParentItem(),
                updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());
        }

        return new CompletedModState(getManagedItem(), getParentItem(),
            updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());
    }

    /**
     * Sub classes can override to determine if the {@link RequestItemStatus} should cause a second review.
     * <p>
     * Called when a single {@link ModDifferenceVo} has a changed {@link RequestItemStatus} between what is in the database
     * and what the user passed in. The given {@link RequestItemStatus} is the new value provided by the user.
     * <p>
     * Default implementation returns true.
     * 
     * @param fieldKey {@link FieldKey} with the change in {@link RequestItemStatus}
     * @param requestItemStatus {@link RequestItemStatus} provided by user (changed from what is in the databse)
     * @param user {@link UserVo} making the request
     * @return boolean
     * 
     * @throws BusinessRuleException if error in business logic
     */
    @Override
    protected boolean changeRequiresSecondReview(FieldKey fieldKey, RequestItemStatus requestItemStatus, UserVo user)
        throws BusinessRuleException {
        if (!RequestItemStatus.APPROVED.equals(requestItemStatus) && !RequestItemStatus.REJECTED.equals(requestItemStatus)) {
            throw new BusinessRuleException(BusinessRuleException.MOD_MUST_BE_APPROVED_REJECTED);
        }

        return RequestItemStatus.APPROVED.equals(requestItemStatus)
            && getManagedItem().listSecondReviewFields(getEnvironmentUtility().getEnvironment(), user.getRoles()).contains(
                fieldKey);
    }
}
