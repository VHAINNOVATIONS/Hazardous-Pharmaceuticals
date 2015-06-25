/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.transaction.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationListener;


/**
 * {@link TransactionSynchronizationListener} that sets a boolean when it is called.
 */
public class TestTransactionSynchronizationListener implements TransactionSynchronizationListener {
    private static final Logger LOG = Logger.getLogger(TestTransactionSynchronizationListener.class);
    
    private boolean beforeCommitCalled = false;
    private int beforeCommitCount = 0;

    private boolean afterCommitCalled = false;
    private int afterCommitCount = 0;

    private boolean afterRollbackCalled = false;
    private int afterRollbackCount = 0;

    /**
     * Set called to true and increment the count.
     * 
     * @see gov.va.med.pharmacy.peps.common.transaction.DefaultTransactionSynchronizationListener#afterCommit()
     */
    public void afterCommit() {
        LOG.debug("afterCommit() ");
        this.afterCommitCalled = true;
        this.afterCommitCount++;
    }

    /**
     * Set called to true and increment the count.
     * 
     * @see gov.va.med.pharmacy.peps.common.transaction.DefaultTransactionSynchronizationListener#afterRollback()
     */
    public void afterRollback() {
        LOG.debug("afterRollback()");
        this.afterRollbackCalled = true;
        this.afterRollbackCount++;
    }

    /**
     * Set called to true and increment the count.
     * 
     * @see gov.va.med.pharmacy.peps.common.transaction.DefaultTransactionSynchronizationListener#beforeCommit()
     */
    public void beforeCommit() {
        LOG.debug("beforeCommit()");
        this.beforeCommitCalled = true;
        this.beforeCommitCount++;
    }

    /**
     * isBeforeCommitCalled
     * @return called property
     */
    public boolean isBeforeCommitCalled() {
        return beforeCommitCalled;
    }

    /**
     * getBeforeCommitCount
     * @return count property
     */
    public int getBeforeCommitCount() {
        return beforeCommitCount;
    }

    /**
     * isAfterCommitCalled
     * @return afterCommitCalled property
     */
    public boolean isAfterCommitCalled() {
        return afterCommitCalled;
    }

    /**
     * getAfterCommitCount
     * @return afterCommitCount property
     */
    public int getAfterCommitCount() {
        return afterCommitCount;
    }

    /**
     * isAfterRollbackCalled
     * @return afterRollbackCalled property
     */
    public boolean isAfterRollbackCalled() {
        return afterRollbackCalled;
    }

    /**
     * getAfterRollbackCount
     * @return afterRollbackCount property
     */
    public int getAfterRollbackCount() {
        return afterRollbackCount;
    }
}
