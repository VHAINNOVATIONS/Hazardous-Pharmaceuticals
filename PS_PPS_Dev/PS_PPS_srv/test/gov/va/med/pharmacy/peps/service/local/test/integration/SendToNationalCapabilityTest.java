/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.NamingException;

import org.springframework.jndi.JndiTemplate;

import gov.va.med.pharmacy.peps.common.vo.RequestMessageVo;
import gov.va.med.pharmacy.peps.service.common.messagingservice.utility.MessagingServiceJmsConfiguration;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;
import gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.SendToNationalCapability;
import gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.impl.SendToNationalCapabilityImpl;


/**
 * Verify sending reply to national works as expected
 */
public class SendToNationalCapabilityTest extends IntegrationTestCase {

    private SendToNationalCapability sendToNationalCapability;

    private QueueReceiver receiver;

    private RequestMessageVo vo;

    private Queue queue;

    private QueueConnection queueConnection;

    private QueueSession queueSession;

    private JndiTemplate context;

    /**
     * Setup the Test
     * 
     * @throws JMSException JMSException
     * @throws NamingException NamingException
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws JMSException, NamingException {

        this.context = getNationalJndiTemplate();

        SendToNationalCapabilityImpl impl = new SendToNationalCapabilityImpl();
        impl.setJndiTemplate(context);
        this.sendToNationalCapability = impl;

        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context
            .lookup(MessagingServiceJmsConfiguration.CONNECTION_FACTORY);
        this.queueConnection = queueConnectionFactory.createQueueConnection();
        queueConnection.start();

        this.queue = (Queue) context.lookup(MessagingServiceJmsConfiguration.DEFAULT_QUEUE);

        this.queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        this.receiver = queueSession.createReceiver(queue);

        this.vo = new RequestMessageVo();

    }

    /**
     * Close the subscriber.
     * 
     * @throws JMSException JMSException
     * @throws NamingException NamingException
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    public void tearDown() throws JMSException, NamingException {
        receiver.close();
        queueSession.close();
        queueConnection.close();
    }

    /**
     * Test sending to the default queue at National
     */
    public void testSendSpecificQueue() {

        try {

            sendToNationalCapability.send(vo, SendToNationalCapability.DEFAULT_QUEUE);

        } catch (Exception e) {
            fail();
        }

    }

    /**
     * Test sending to the default queue by default.
     */
    public void testSendDefaultQueue() {

        try {

            sendToNationalCapability.send(vo);

        } catch (Exception e) {
            fail();
        }

    }

}
