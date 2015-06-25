/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.transaction.test;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationListener;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.RequestSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.local.capability.impl.RequestCapabilityImpl;


/**
 * Register transaction synchronizations then call to the Local-1 Spring configured bean.
 */
public class RequestCapabilityStub extends RequestCapabilityImpl {

    private TransactionSynchronizationListener transactionSynchronizationListener;

    /**
     * Default constructor. Be sure to call
     * {@link #setTransactionSynchronizationListener(TransactionSynchronizationListener)}.
     */
    public RequestCapabilityStub() {
        super();
    }

    /**
     * The test case provides the {@link TransactionSynchronizationListener}.
     * 
     * @param transactionSynchronizationListener {@link TransactionSynchronizationListener}
     */
    public RequestCapabilityStub(TransactionSynchronizationListener transactionSynchronizationListener) {
        this.transactionSynchronizationListener = transactionSynchronizationListener;
    }

    /**
     * Retrieve request detail by ID.
     * 
     * @param id RequestVo ID to retrieve
     * @return RequestVo
     * @throws ItemNotFoundException if cannot find Request with given ID
     * @throws RequestCompletedException exception
     */
    public RequestVo retrieve(String id) throws ItemNotFoundException, RequestCompletedException {
        TransactionSynchronizationUtility.addListener(transactionSynchronizationListener);

        // return SpringTestConfigUtility.getLocalOneSpringBean(RequestCapability.class).retrieve(id);
        return super.retrieve(id);
    }

    /**
     * Retrieve all the RequestVo for the ManagedItemVo with the given ID and {@link EntityType}.
     * 
     * @param itemId ID for the {@link ManagedItemVo}
     * @param itemType {@link EntityType} for which to retrieve {@link RequestVo}
     * @return Collection<RequestVo>
     * @throws ItemNotFoundException if RequestVo with given ID cannot be found
     */
    public Collection<RequestVo> retrieve(String itemId, EntityType itemType) throws ItemNotFoundException {
        TransactionSynchronizationUtility.addListener(transactionSynchronizationListener);

        // return SpringTestConfigUtility.getLocalOneSpringBean(RequestCapability.class).retrieve(itemId, itemType);
        return super.retrieve(itemId, itemType);
    }

    /**
     * Search for the pending requests.
     * 
     * @param criteria for searchCriteria
     * @param user The user
     * @return Collection<RequestSummaryVo>
     * 
     * @throws ValueObjectValidationException if error in criteria data
     */
    public Collection<RequestSummaryVo> search(SearchRequestCriteriaVo criteria, UserVo user)
        throws ValueObjectValidationException {
        TransactionSynchronizationUtility.addListener(transactionSynchronizationListener);

        // return SpringTestConfigUtility.getLocalOneSpringBean(RequestCapability.class).search(criteria);
        return super.search(criteria, user);
    }

    /**
     * setTransactionSynchronizationListener
     * @param transactionSynchronizationListener transactionSynchronizationListener property
     */
    public void setTransactionSynchronizationListener(TransactionSynchronizationListener transactionSynchronizationListener) {
        this.transactionSynchronizationListener = transactionSynchronizationListener;
    }
}
