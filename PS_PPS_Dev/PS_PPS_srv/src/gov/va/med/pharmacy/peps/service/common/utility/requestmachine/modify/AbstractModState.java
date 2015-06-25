/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.modify;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;


/**
 * Super class for all mod request states.
 */
public abstract class AbstractModState extends RequestStateMachine {

    /**
     * Call the superclass constructor.
     * 
     * @param managedItem the item being added
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility}
     */
    protected AbstractModState(ManagedItemVo managedItem, ManagedItemVo parentItem,
        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * Mod request should only be for approved items
     * 
     * @return RequestItemStatus.APPROVED
     * 
     * @see gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestState#getModRequestItemStatus()
     */
    @Override
    public final RequestItemStatus getRequestItemStatus() {
        return RequestItemStatus.APPROVED;
    }

    /**
     * True if a second review is required.
     * 
     * @param request {@link RequestVo#getRequestDetails()}
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo}
     * @param user {@link UserVo} making the request
     * @return true if we require a second approval
     * 
     * @throws BusinessRuleException if error in business logic
     */
    protected boolean requiresSecondReview(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences, UserVo user)
        throws BusinessRuleException {
        
        return hasSecondReviewField(acceptedDifferences, user) || requestItemStatusChanged(request, getOldRequest(), user);
    }
}
