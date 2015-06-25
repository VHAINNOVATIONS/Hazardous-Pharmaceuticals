/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.transaction.test;


import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronization;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;


/**
 * Wrap calls into the {@link TransactionSynchronization} with Spring's {@link TransactionSynchronizationAdapter}.
 * <p>
 * Used only during development when Spring AOP transactions are present and WebLogic CMT is not.
 */
public class TransactionSynchronizationAdvice implements MethodBeforeAdvice {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(TransactionSynchronizationAdvice.class);

    /**
     * Default constructor.
     */
    public TransactionSynchronizationAdvice() {

        super();
    }

    /**
     * Register a new {@link TransactionSychronizationStub}.
     * 
     * @param method Method being invoked
     * @param args parameters for the method
     * @param target Object method is invoked on
     * @throws Throwable if error
     * 
     * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
     */
    public void before(Method method, Object[] args, Object target) throws Throwable {

        TransactionSynchronizationUtility.init();

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationStub());
        } else {
            LOG.error("Unable to register transaction synchronization!");
        }
    }
}
