/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.impl.test;


import javax.jms.ObjectMessage;

import gov.va.med.pharmacy.peps.common.transaction.DefaultTransactionSynchronizationListener;
import gov.va.med.pharmacy.peps.common.transaction.TransactionSynchronizationUtility;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.common.messagingservice.test.stub.ObjectMessageStub;
import gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.SendToLocalCapability;


/**
 * Stub implementation used when running "out of container" to send a message to local.
 */
public class SendToLocalCapabilityStub implements SendToLocalCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SendToLocalCapabilityStub.class);

    /**
     * Stub implementation only logs attempt to send to local
     * 
     * @param valueObject ValueObject to send
     * @param queueName String queue to send on
     * 
     */
    public void send(ValueObject valueObject, String queueName) {
        send(valueObject);
    }

    /**
     * Stub implementation only logs attempt to send to local
     * 
     * @param valueObject ValueObject to send
     * 
     */
    public void send(final ValueObject valueObject) {
        if (TransactionSynchronizationUtility.isSynchronizationActive()) {
            LOG.debug("Transaction synchronization is active. Sending VO once national transaction commits.");
            TransactionSynchronizationUtility.addListener(new SendToLocal(valueObject));
        } else {
            LOG.debug("Transaction synchronization not active. Sending VO to local immediately.");
            new SendToLocal(valueObject).send();
        }
    }


    /**
     * Class that synchronizes with the transaction to send a message to locals.
     */
    private class SendToLocal extends DefaultTransactionSynchronizationListener {
        private ValueObject valueObject;

        /**
         * Set the ValueObject to send.
         * 
         * @param valueObject ValueObject to send
         */
        public SendToLocal(final ValueObject valueObject) {
            this.valueObject = valueObject;
        }

        /**
         * Only send the ValueObject to locals if the transaction is committed.
         * 
         * @see org.springframework.transaction.support.TransactionSynchronizationAdapter#afterCompletion(int)
         */
        @Override
        public void afterCommit() {
            send();
        }

        /**
         * Log a warning message saying that the message wasn't sent.
         * 
         * @see gov.va.med.pharmacy.peps.common.transaction.DefaultTransactionSynchronizationListener#afterRollback()
         */
        @Override
        public void afterRollback() {
            LOG.warn("National transaction was not committed, so ValueObject will not be sent to locals!");
        }

        /**
         * Thread calls into local-1 and local-2 to send ValueObject.
         */
        public void send() {
            Runnable sendToLocalOne = new Runnable() {

                /**
                 * Call into Local-1
                 * 
                 * @see java.lang.Runnable#run()
                 */
                public void run() {

                    try {
                        ObjectMessage message = new ObjectMessageStub();
                        message.setObject(valueObject.clone());

               //         ReceiveFromNationalService receiveFromNationalService = SpringTestConfigUtility
//                            .getLocalOneSpringBean(ReceiveFromNationalService.class);

      //                  receiveFromNationalService.onMessage(message);
                    } catch (Throwable t) {
                        LOG.error(
                            "Send to Local-1 threw an exception. Local-1's transaction will be rolled back, " 
                            + " but National's will not.", t);
                    }
                }
            };

            Thread threadLocalOne = new Thread(sendToLocalOne);

            // in case we aren't using transaction synchronization, attempt to "slow down" this thread
            threadLocalOne.setPriority(Thread.MIN_PRIORITY + 1);

            threadLocalOne.start();

            Runnable sendToLocalTwo = new Runnable() {

                /**
                 * Call into Local-2
                 * 
                 * @see java.lang.Runnable#run()
                 */
                public void run() {

                    try {
                        ObjectMessage message = new ObjectMessageStub();
                        message.setObject(valueObject.clone());

                  //      ReceiveFromNationalService receiveFromNationalService = SpringTestConfigUtility
//                            .getLocalTwoSpringBean(ReceiveFromNationalService.class);

//                        receiveFromNationalService.onMessage(message);
                    } catch (Throwable t) {
                        LOG.error("Send to Local-2 threw an exception. Local-2's transaction will be rolled "
                            + " back, but National's will not.", t);
                    }
                }
            };

            Thread threadLocalTwo = new Thread(sendToLocalTwo);

            // in case we aren't using transaction synchronization, attempt to "slow down" this thread
            threadLocalTwo.setPriority(Thread.MIN_PRIORITY);

            threadLocalTwo.start();
        }
    }
}
