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
 * Pending second approval request state.
 */
public class PendingSecondApprovalModState extends AbstractModState {

    /**
     * constructor
     * 
     * @param managedItem item for this state
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility} 
     */
    public PendingSecondApprovalModState(ManagedItemVo managedItem, ManagedItemVo parentItem, RequestVo oldRequest,
        EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * getRequestStatus
     * @return pending second review
     * 
     * @see gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine#getRequestStatus()
     */
    @Override
    public RequestState getRequestStatus() {
        return RequestState.PENDING_SECOND_REVIEW;
    }

    /**
     * Call {@link RequestVo#checkFields()} and transition into the second approval or completed state.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of ModDifferences that were accepted.
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule boolean
     * @return the next state in the {@link RequestState}
     * @throws ValidationException if there is an error in request data
     */
    @Override
    protected RequestStateMachine processStateChange(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences,
        UserVo user, boolean ignoreUserRule) throws ValidationException {

        checkForConflicts(request, acceptedDifferences);
        request.checkFields();
       
        
        if (stillRequiresSecondApproval(request, acceptedDifferences, user, ignoreUserRule)) {
            return new PendingSecondApprovalModState(getManagedItem(), getParentItem(),
                updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());
        }

        return new CompletedModState(getManagedItem(), getParentItem(),
            updateRequest(request, acceptedDifferences, user), getEnvironmentUtility());
    }

    /**
     * Sub classes can override to determine if the RequestItemStatus} should cause a second review for
     * PendingSecondApprovalModSatate.
     * 
     * <p>
     * Called when a single {@link ModDifferenceVo} has a changed {@link RequestItemStatus} between what is in the database
     * and what the user passed in. The given {@link RequestItemStatus} is the new value provided by the user.
     * <p>
     * Default implementation returns true for PendingSecondApprovalModSatate.
     * 
     * @param fieldKey {@link FieldKey} with the change in {@link RequestItemStatus}
     * @param requestItemStatus {@link RequestItemStatus} provided by user (changed from what is in the databse)
     * @param user {@link UserVo} making the request
     * @return true if changeRequeires a second review.
     * 
     * @throws BusinessRuleException if error in business logic
     */
    @Override
    protected boolean changeRequiresSecondReview(FieldKey fieldKey, RequestItemStatus requestItemStatus, UserVo user)
        throws BusinessRuleException {
        return fieldKey.isRequiresSecondApproval();
    }

    /**
     * True if the last reviewer is equal to the given {@link UserVo} and the old request isn't "under review" or if
     * {@link #requiresSecondReview(Collection, Collection)} returns true.
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule Ignore the ruls about the user.
     * @return boolean
     * @throws BusinessRuleException if error in request data
     */
    private boolean stillRequiresSecondApproval(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, 
        UserVo user, boolean ignoreUserRule) throws BusinessRuleException {
        
        boolean isSameReviewer = getOldRequest().getLastReviewer().getUsername().equals(user.getUsername());
        
        if (ignoreUserRule) {
            isSameReviewer = false;
        }
        
        return (isSameReviewer && !getOldRequest().isUnderReview()) || requiresSecondReview(request, acceptedDifferences, user)
            || isParentProductPending();
    }
}
