/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility.requestmachine.add;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;


/**
 * A class representing the data needed to make a state transition from one state to another in the request management flows.
 */
public abstract class AbstractAddState extends RequestStateMachine {

    /**
     * Default Constructor, we need the item, who reviewed it (if any) and the request Details
     * 
     * @param managedItem the item being added
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param oldRequest the {@link RequestVo} prior to modifications made (i.e., the {@link RequestVo} in the database)
     * @param environmentUtility {@link EnvironmentUtility} to retrieve current environment
     */
    protected AbstractAddState(ManagedItemVo managedItem, ManagedItemVo parentItem,
        RequestVo oldRequest, EnvironmentUtility environmentUtility) {
        super(managedItem, parentItem, oldRequest, environmentUtility);
    }

    /**
     * Method used to process a given state to determine what state it should be in next
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule Ignore the User Rule
     * @return the next state in the {@link RequestStateMachine}
     * @throws ValidationException if error in request data
     */
    @Override
    protected RequestStateMachine processStateChange(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences,
                                                     UserVo user, boolean ignoreUserRule) throws ValidationException {
        request.checkFields();

        if (EntityType.isSecondApprovalType(request.getEntityType())) {
            if (RequestItemStatus.APPROVED.equals(request.getNewItemRequestStatus())) {
                return approve(request, acceptedDifferences, user, ignoreUserRule);
            } else if (EntityType.isSecondApprovalType(request.getEntityType())) {
                return reject(request, acceptedDifferences, user);
            }
        } 
        
        // if this is a single approval field.
        if (RequestItemStatus.REJECTED.equals(request.getNewItemRequestStatus())) {
            return reject(request, acceptedDifferences, user);
        } else {
            return approve(request, acceptedDifferences, user, ignoreUserRule);
        }
        

    }

    /**
     * Method used to process a given state to determine what state it should be in next
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @param ignoreUserRule ignoreUserRule
     * @return the next state in the {@link RequestStateMachine}
     * @throws ValidationException if error in request data
     */
    public abstract RequestStateMachine approve(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences,
                                                UserVo user, boolean ignoreUserRule) throws ValidationException;

    /**
     * Method used to process a given state to determine what state it should be in next
     * 
     * @param request {@link RequestVo} being processed
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being processed
     * @param user {@link UserVo} which made the request
     * @return the next state in the {@link RequestStateMachine}
     * @throws ValidationException if error in request data
     */
    public abstract RequestStateMachine reject(RequestVo request, Collection<ModDifferenceVo> acceptedDifferences,
                                               UserVo user) throws ValidationException;
}
