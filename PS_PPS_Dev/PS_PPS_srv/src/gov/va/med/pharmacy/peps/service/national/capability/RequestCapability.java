/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestMessageVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Process add/mod requests.
 */
public interface RequestCapability extends gov.va.med.pharmacy.peps.service.common.capability.RequestCapability {

    /**
     * Process request contained in message
     * 
     * @param requestMessage message containing request
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    void processRequestMessage(RequestMessageVo<ManagedItemVo> requestMessage, UserVo user) throws ValidationException;

    /**
     * Commit a {@link RequestVo} to the database.
     * <p>
     * The {@link RequestVo} returned has all of the given {@link ModDifferenceVo} applied to its
     * {@link RequestVo#getRequestDetails()}.
     * 
     * @param managedItem {@link ManagedItemVo} for request
     * @param request {@link RequestVo} for {@link ManagedItemVo}
     * @param acceptedDifferences Collection of accepted {@link ModDifferenceVo} being committed
     * @param user {@link UserVo} committing request
     * @param parentItem {@link ManagedItemVo} parent of managedItem
     * @param ignoreUserRule ignore the User Rule
     * @return updated {@link RequestVo}
     * @throws ValidationException if error validating data
     */
    RequestVo commitRequest(ManagedItemVo managedItem, RequestVo request, Collection<ModDifferenceVo> acceptedDifferences,
                            UserVo user, ManagedItemVo parentItem, boolean ignoreUserRule) throws ValidationException;
}
