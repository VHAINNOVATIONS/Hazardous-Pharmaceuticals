/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.test;


import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;


/**
 * Class override to allow roll-backs for tests instead of commits.
 */
public class HibernateRollbackTransactionManager extends HibernateTransactionManager {
    private static final long serialVersionUID = 1L;
    
    /**
     * This overrides the doCommit method so that Spring will rollback instead of commit.
     * @param transactionStatus current transaction status
     * 
     * @see org.springframework.orm.hibernate3.HibernateTransactionManager#
     *      doCommit(org.springframework.transaction.support.DefaultTransactionStatus)
     */
    @Override
    protected void doCommit(DefaultTransactionStatus transactionStatus) {
    

        super.doRollback(transactionStatus);
    }
}
