/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.transaction.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationListener;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ProductDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.RequestCapability;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;
import gov.va.med.pharmacy.peps.service.national.capability.impl.RequestCapabilityImpl;

import junit.framework.TestCase;


/**
 * Verify that registering a {@link TransactionSynchronizationListener} works in the development environment.
 */
public class TransactionSynchronizationTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(TransactionSynchronizationTest.class);
    
    private RequestService requestService;
    private RequestCapabilityImpl requestCapability;
    private TestTransactionSynchronizationListener testTransactionSynchronizationListener;

    /**
     * Reset the {@link ManagedItemCapability} dependency on the {@link ProductDomainCapability} to a stub that adds a
     * {@link TransactionSynchronizationListener}.
     * <p>
     * Get the {@link ManagedItemService} from the Spring application context.
     * 
     * @throws Exception if error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        LOG.debug("--------------" + getName() + "----------------");

        this.testTransactionSynchronizationListener = new TestTransactionSynchronizationListener();
        this.requestCapability = (RequestCapabilityImpl) SpringTestConfigUtility
            .getNationalSpringBean(RequestCapability.class);
        requestCapability
            .setRequestDomainCapability(new RequestDomainCapabilityStub(testTransactionSynchronizationListener));

        this.requestService = SpringTestConfigUtility.getNationalSpringBean(RequestService.class);
    }

    /**
     * Verify that Spring AOP transactions call the {@link TransactionSynchronizationListener#beforeCommit()} once.
     * 
     * @throws Exception if error
     */
    public void testBeforeCommit() throws Exception {
        requestService.retrieve("0");

        assertTrue("Synchronization should have been called.", testTransactionSynchronizationListener.isBeforeCommitCalled());
        assertEquals("Synchronization should only be called once.", 1, testTransactionSynchronizationListener
            .getBeforeCommitCount());
    }

    /**
     * Verify that Spring AOP transactions call the {@link TransactionSynchronizationListener#afterCommit()} once.
     * 
     * @throws Exception if error
     */
    public void testAfterCommit() throws Exception {
        SearchRequestCriteriaVo searchCriteria = new SearchRequestCriteriaVo(SearchDomain.SIMPLE);
        searchCriteria.setLocalSearch(false);
        searchCriteria.setAllRequests(true);
        requestService.search(searchCriteria, null);

        assertTrue("Synchronization should have been called!", testTransactionSynchronizationListener.isAfterCommitCalled());
        assertEquals("Synchronization should only be called once!", 1, testTransactionSynchronizationListener
            .getAfterCommitCount());
    }

    /**
     * Verify that Spring AOP transactions call the {@link TransactionSynchronizationListener#afterRollback()} once.
     * 
     * @throws Exception if error
     */
    public void testAfterRollback() throws Exception {
        try {
            requestService.retrieve("0", EntityType.PRODUCT);
        } catch (ItemNotFoundException e) {
            assertNotNull("Should throw exception", e);
        }

        assertTrue("Synchronization should have been called", testTransactionSynchronizationListener.isAfterRollbackCalled());
        assertEquals("Synchronization should only be called once", 1, testTransactionSynchronizationListener
            .getAfterRollbackCount());
        assertFalse("Before commit should not have been called", testTransactionSynchronizationListener
            .isBeforeCommitCalled());
    }
}
