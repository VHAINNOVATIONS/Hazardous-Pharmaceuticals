/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.transaction.test;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationListener;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.impl.RequestDomainCapabilityImpl;


/**
 * Override the methods to register synchronizations.
 */
public class RequestDomainCapabilityStub extends RequestDomainCapabilityImpl {
    private TransactionSynchronizationListener transactionSynchronizationListener;

    /**
     * Test case passes in the {@link TransactionSynchronizationListener} to use.
     * 
     * @param transactionSynchronizationListener {@link TransactionSynchronizationListener}
     */
    public RequestDomainCapabilityStub(TransactionSynchronizationListener transactionSynchronizationListener) {
        this.transactionSynchronizationListener = transactionSynchronizationListener;
    }

    /**
     * Register a transaction synchronization. Forces transaction rollback by throwing exception.
     * 
     * @param itemId String ID
     * @param entityType EntityType
     * @return won't return, throws exception
     * @throws ItemNotFoundException to force rollback
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.impl.RequestDomainCapabilityImpl#retrieve(java.lang.String,
     *      gov.va.med.pharmacy.peps.common.vo.EntityType)
     */
    @Override
    public Collection<RequestVo> retrieve(String itemId, EntityType entityType) throws ItemNotFoundException {
        TransactionSynchronizationUtility.addListener(transactionSynchronizationListener);

        throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, itemId);
    }

    /**
     * Register a transaction synchronization
     * 
     * @param criteria {@link SearchCriteriaVo}
     * @param user the user performing the search
     * @return empty Collection
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.impl.RequestDomainCapabilityImpl
     * #nationalSearch(gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo, UserVo)
     */
    @Override
    public List<RequestSummaryVo> nationalSearch(SearchRequestCriteriaVo criteria, UserVo user) {
        TransactionSynchronizationUtility.addListener(transactionSynchronizationListener);

        return Collections.emptyList();
    }

    /**
     * Register a transaction synchronization
     * 
     * @param requestId String
     * @return empty RequestVo
     * @throws ItemNotFoundException won't be thrown
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.impl.RequestDomainCapabilityImpl#retrieve(java.lang.String)
     */
    @Override
    public RequestVo retrieve(String requestId) throws ItemNotFoundException {
        TransactionSynchronizationUtility.addListener(transactionSynchronizationListener);
        RequestVo vo = new RequestVo();
        vo.setRequestState(RequestState.PENDING_FIRST_REVIEW);
        
        return vo;
    }
}
