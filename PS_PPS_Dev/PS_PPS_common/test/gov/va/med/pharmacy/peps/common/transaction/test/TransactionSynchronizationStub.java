/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.transaction.test;


import javax.transaction.Status;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronization;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;


/**
 * Wrap calls into the {@link TransactionSynchronization} with Spring's {@link TransactionSynchronizationAdapter}.
 */
public class TransactionSynchronizationStub extends TransactionSynchronizationAdapter {
    private static final TransactionSynchronization SYNCHRONIZATION = new TransactionSynchronization();

    /**
     * Default constructor.
     */
    public TransactionSynchronizationStub() {
        super();
    }

    /**
     * This method is called by the transaction manager after the transaction is committed or rolled back.
     * <p>
     * If the transaction has committed, call {@link TransactionSynchronizationUtility#triggerAfterCommit()}.
     * <p>
     * If the transaction has rolled back, call {@link TransactionSynchronizationUtility#triggerAfterRollback()}.
     * <p>
     * Always call {@link TransactionSynchronizationUtility#clear()} after transaction synchronization is complete.
     * 
     * @param status The status of the transaction completion.
     * 
     * @see TransactionSynchronization#afterCompletion(int)
     * @see org.springframework.transaction.support.TransactionSynchronizationAdapter#afterCompletion(int)
     */
    @Override
    public void afterCompletion(int status) {
        int jtaStatus;

        switch (status) {
            case STATUS_COMMITTED:
                jtaStatus = Status.STATUS_COMMITTED;
                break;

            case STATUS_ROLLED_BACK:
                jtaStatus = Status.STATUS_ROLLEDBACK;
                break;

            default:
                jtaStatus = Status.STATUS_UNKNOWN;
                break;
        }

        SYNCHRONIZATION.afterCompletion(jtaStatus);
    }

    /**
     * The beforeCompletion method is called by the transaction manager prior to the start of the two-phase transaction
     * commit process. This call is executed with the transaction context of the transaction that is being committed.
     * <p>
     * Call {@link TransactionSynchronizationUtility#triggerBeforeCommit()}.
     * 
     * @param readOnly True if the Spring AOP transaction was read-only
     * 
     * @see TransactionSynchronization#beforeCompletion()
     * @see org.springframework.transaction.support.TransactionSynchronizationAdapter#beforeCompletion()
     */
    @Override
    public void beforeCommit(boolean readOnly) {
        SYNCHRONIZATION.beforeCompletion();
    }
}
