/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.impl.test;


import javax.jms.ObjectMessage;

import gov.va.med.pharmacy.peps.common.transaction.DefaultTransactionSynchronizationListener;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.common.messagingservice.test.stub.ObjectMessageStub;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;
import gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.SendToNationalCapability;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.ReceiveFromLocalService;


/**
 * Stub implementation used when running "out of container" to send a message to national.
 */
public class SendToNationalCapabilityStub implements SendToNationalCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SendToNationalCapabilityStub.class);

    /**
     * "Send" the VO to National by directly calling the {@link ReceiveFromLocalService} retrieved via Spring.
     * 
     * Using this approach, we don't have a concept of any different queues, so it just calls {@link #send(ValueObject)}.
     * 
     * @param valueObject ValueObject to send
     * @param queueName String queue to send on
     * 
     * @see gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.
     *      SendToNationalCapability#send(gov.va.med.pharmacy.peps.common.vo.ValueObject,
     *      java.lang.String)
     */
    public void send(ValueObject valueObject, String queueName) {
        send(valueObject);
    }

    /**
     * "Send" the VO to National by directly calling the {@link ReceiveFromLocalService} retrieved via Spring.
     * 
     * Use the {@link ObjectMessageStub} stubbed implementation of {@link ObjectMessage} that can be used outside of the
     * container, without actually using JMS.
     * 
     * @param valueObject ValueObject to send
     * 
     * @see gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.
     *      SendToNationalCapability#send(gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public void send(final ValueObject valueObject) {
        if (TransactionSynchronizationUtility.isSynchronizationActive()) {
            LOG.debug("Transaction synchronization is active. Sending VO only if local transaction commits.");
            TransactionSynchronizationUtility.addListener(new SendToNational(valueObject));
        } else {
            LOG.debug("Transaction synchronization not active. Sending VO to national immediately.");
            new SendToNational(valueObject).send();
        }
    }


    /**
     * Class that synchronizes with the transaction to send a message to National.
     */
    private class SendToNational extends DefaultTransactionSynchronizationListener {
        private ValueObject valueObject;

        /**
         * Set the ValueObject to send.
         * 
         * @param valueObject ValueObject to send
         */
        public SendToNational(final ValueObject valueObject) {
            this.valueObject = valueObject;
        }

        /**
         * Only send the ValueObject to national if the transaction is committed.
         * 
         * @see org.springframework.transaction.support.TransactionSynchronizationAdapter#afterCompletion(int)
         */
        @Override
        public void afterCommit() {
            send();
        }

        /**
         * Log a warning message saying that the message was not sent after a rollback.
         * 
         * @see gov.va.med.pharmacy.peps.common.transaction.DefaultTransactionSynchronizationListener#afterRollback()
         */
        @Override
        public void afterRollback() {
            LOG.warn("Local transaction was not committed, so ValueObject will not be sent to national!");
        }

        /**
         * Thread a call into National to send the ValueObject.
         */
        public void send() {
            Runnable sendToNational = new Runnable() {

                /**
                 * Call into National
                 * 
                 * @see java.lang.Runnable#run()
                 */
                public void run() {

                    try {
                        ObjectMessage message = new ObjectMessageStub();
                        message.setObject(valueObject);

                        ReceiveFromLocalService receiveFromLocalService = SpringTestConfigUtility
                            .getNationalSpringBean(ReceiveFromLocalService.class);

                        LOG.debug("Sending asynchronous (threaded) message to National by calling"
                            + " ReceiveFromLocalServiceImpl directly");
                        receiveFromLocalService.onMessage(message);
                    } catch (Throwable t) {
                        LOG.error("Send to National threw an exception. National's transaction will be"
                            + "rolled back, but local's will not.",
                            t);
                    }
                }
            };

            Thread threadNational = new Thread(sendToNational);

            // in case we aren't using transaction synchronization, attempt to "slow down" this thread
            threadNational.setPriority(Thread.MIN_PRIORITY + 2);

            threadNational.start();
        }
    }
}
