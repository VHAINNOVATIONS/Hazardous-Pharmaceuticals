/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.session.bean;


import javax.ejb.CreateException;
import javax.ejb.SessionContext;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractStatelessSessionBean;
import org.springframework.transaction.jta.JtaTransactionManager;

import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronization;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;
import gov.va.med.pharmacy.peps.common.utility.ClassUtility;


/**
 * Abstract stateless session bean for all PPS services. Defines methods to load Spring and retrieve a service based upon
 * the class name.
 * 
 * @param <T> Type of class for the Service this abstract bean uses
 */
public abstract class AbstractPepsStatelessSessionBean<T> extends AbstractStatelessSessionBean { 
    private static final long serialVersionUID = 1;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(AbstractPepsStatelessSessionBean.class);

    private T service;

    /**
     * Start Spring as a Singleton so it is loaded only once
     * 
     * @param sessionContext SessionContext
     * 
     * @see org.springframework.ejb.support.AbstractSessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(SessionContext sessionContext) {
        super.setSessionContext(sessionContext);
        setBeanFactoryLocator(ContextSingletonBeanFactoryLocator.getInstance());
        setBeanFactoryLocatorKey("businessBeanFactory");
    }

    /**
     * Set the service interface to the Spring configured bean
     * 
     * @throws CreateException if error
     * 
     * @see org.springframework.ejb.support.AbstractStatelessSessionBean#onEjbCreate()
     */
    protected void onEjbCreate() throws CreateException {
        Class generic = ClassUtility.getGenericType(getClass());
        String className = ClassUtility.getSpringBeanId(generic);

        // Proxy the service Impl class to rollback the transaction if a checked exception is thrown.
        // Checked exceptions are not rolled back by default in EJBs.
        if (getBeanFactory().containsBean(className)) {
            T springBean = (T) getBeanFactory().getBean(className);
            ProxyFactory factory = new ProxyFactory(springBean);
            factory.addInterface(generic);
            factory.addAdvice(new TransactionInterceptor());

            this.service = (T) factory.getProxy();
        }
        
    }

    /**
     * Retrieves the service for this class as a Spring bean. The Spring bean must be named after the class name.
     * 
     * The domainServiceBean must have a Spring bean defined as domainService".
     * 
     * @return Object service implementation class from Spring. If no Spring bean was found, return null.
     */
    protected T getService() {
        return service;
    }


    /**
     * Proxy to rollback transactions when a checked exception is thrown and to register the
     * {@link TransactionSynchronization} class.
     * <p>
     * The TransactionRollbackInterceptor class needs to be an inner class of the AbstractPepsStatelessSessionBean so that it
     * has access to the SessionContext.
     * <p>
     * Rolling back a transaction when a checked exception is thrown is not the default behavior for an EJB. Typically a
     * transaction would only be rolled back only for a RuntimeException or RemoteException.
     */
    private class TransactionInterceptor implements MethodInterceptor {
        private static final String TRANSACTION_MANAGER_BEAN_ID = "transactionManager";

        /**
         * Wrap the invocation in a try/catch block that calls setRollbackOnly() if an exception is thrown.
         * 
         * @param methodInvocation Service method to invoke
         * @return result of invoking method
         * @throws Throwable if error, including all exceptions thrown by the Service method
         * 
         * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
         */
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            synchronizeTransactions();

            try {
                return methodInvocation.proceed();
            } catch (Throwable t) {
                if (Status.STATUS_ROLLEDBACK == getTransaction().getStatus()) {
                    LOG.error("Transaction already rolled back, setRollbackOnly() not called! ", t);
                } else {
                    LOG.error("Rolling back transaction due to exception: ", t);
                    getSessionContext().setRollbackOnly();
                }

                throw t;
            }
        }

        /**
         * Register the {@link TransactionSynchronization} with the current transaction.
         * 
         * @throws RollbackException If error registering synchronization
         * @throws SystemException If error registering synchronization
         */
        private void synchronizeTransactions() throws RollbackException, SystemException {
            TransactionSynchronizationUtility.init();

            getTransaction().registerSynchronization(new TransactionSynchronization());
        }

        /**
         * Get the JTA transaction from the Spring transaction manager.
         * 
         * @return {@link Transaction}
         * @throws SystemException if error getting transaction from the transaction manager
         */
        private Transaction getTransaction() throws SystemException {
            JtaTransactionManager transactionManager = (JtaTransactionManager) getBeanFactory().getBean(
                TRANSACTION_MANAGER_BEAN_ID);

            return transactionManager.getTransactionManager().getTransaction();
        }
    }
}
