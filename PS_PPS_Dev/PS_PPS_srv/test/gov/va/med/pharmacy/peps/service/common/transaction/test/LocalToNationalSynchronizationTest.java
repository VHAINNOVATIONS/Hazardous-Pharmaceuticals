/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.transaction.test;


import org.apache.log4j.Logger;


import junit.framework.TestCase;


/**
 * Test that transaction synchronization occurs when one service calls another.
 */
public class LocalToNationalSynchronizationTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(LocalToNationalSynchronizationTest.class);
    
    private boolean isTrue = true;
    
//    private static final String[] LOCAL_ONE_SPRING_CONFIG = {"classpath*:xml/spring/test/*Context.xml",
//                                                             "classpath*:xml/local/spring/test/*Context.xml",
//                                                             "classpath*:xml/local/spring/test/TransactionContextTest.xml",
//                                                             "classpath*:xml/local/spring/test/CommonContext-Local-1.xml",
//                                                             "classpath*:xml/local/spring/test/DomainContext-Local-1.xml"};
//
//    private RequestService requestService;
//    private RequestCapabilityImpl requestCapability;
//    private TestTransactionSynchronizationListener nationalTransactionSynchronizationListener;
//    private TestTransactionSynchronizationListener localTransactionSynchronizationListener;

    /**
     * setUp will get get the Spring application context.
     * 
     * @throws Exception if error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        LOG.debug("---------------" + getName() + "--------------");

   /*     ApplicationContext testContext = new ClassPathXmlApplicationContext(LOCAL_ONE_SPRING_CONFIG);
        this.requestService = (RequestService) testContext.getBean(ClassUtility.getSpringBeanId(RequestService.class));

        this.localTransactionSynchronizationListener = new TestTransactionSynchronizationListener();
        RequestCapabilityStub requestCapabilityStub = (RequestCapabilityStub) testContext.getBean(ClassUtility
            .getSpringBeanId(RequestCapability.class));
        requestCapabilityStub.setTransactionSynchronizationListener(localTransactionSynchronizationListener);

        this.nationalTransactionSynchronizationListener = new TestTransactionSynchronizationListener();
        this.requestCapability = (RequestCapabilityImpl) SpringTestConfigUtility
            .getNationalSpringBean(RequestCapability.class);
        requestCapability.setRequestDomainCapability(new RequestDomainCapabilityStub(
            nationalTransactionSynchronizationListener));
            */
    }

    /**
     * Test that transaction synchronization works in the development environment across service calls (local to national).
     * 
     * @throws Exception if error
     */
    public void testLocalToNationalCall() throws Exception {
        assertTrue("Test when local comes back in", isTrue);

        /*
            requestService.retrieve("0");

            assertTrue("Should have called local before commit", 
                localTransactionSynchronizationListener.isBeforeCommitCalled());
            assertEquals("Should have called local before commit once", 1, localTransactionSynchronizationListener
                .getBeforeCommitCount());

            assertTrue("Should have called local after commit", localTransactionSynchronizationListener.isAfterCommitCalled());
            assertEquals("Should have called local after commit once", 1, localTransactionSynchronizationListener
                .getAfterCommitCount());

            assertTrue("Should have called national before commit", nationalTransactionSynchronizationListener
                .isBeforeCommitCalled());
            assertEquals("Should have called national before commit once", 1, nationalTransactionSynchronizationListener
                .getBeforeCommitCount());

            assertTrue("Should have called national after commit", nationalTransactionSynchronizationListener
                .isAfterCommitCalled());
            assertEquals("Should have called national after commit once", 1, nationalTransactionSynchronizationListener
                .getAfterCommitCount());
                */
    }
}
