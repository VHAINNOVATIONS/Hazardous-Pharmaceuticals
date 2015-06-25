/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.impl;


import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.springframework.jndi.JndiTemplate;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.SendToNationalCapability;


/**
 * Send to National using JMS.
 */
public class SendToNationalCapabilityImpl implements SendToNationalCapability {
    private JndiTemplate jndiTemplate;

    /**
     * Empty constructor
     */
    public SendToNationalCapabilityImpl() {
        super();
    }

    /**
     * Send to National using JMS.
     * 
     * @param valueObject object to send
     * @param queueName JMS queue to send reply on
     * 
     * @see gov.va.med.pharmacy.peps.external.local.messagingservice.depricated.outbound.capability.SendResponse#send(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public void send(ValueObject valueObject, String queueName) {

        QueueConnection connection = null;

        try {
            QueueConnectionFactory factory = (QueueConnectionFactory) jndiTemplate.lookup(CONNECTION_FACTORY);
            Queue queue = (Queue) PortableRemoteObject.narrow(jndiTemplate.lookup(queueName), Queue.class);

            connection = factory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue);
            ObjectMessage message = session.createObjectMessage(valueObject);
            sender.send(message);

            sender.close();
            session.close();
            connection.close();
        } catch (NamingException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.MESSAGING_SERVICE, e
                .getLocalizedMessage());
        } catch (JMSException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.MESSAGING_SERVICE, e
                .getLocalizedMessage());
        }

    }

    /**
     * 
     * Send a ValueObject using to National.
     * 
     * @param valueObject The object to send to National.
     * 
     * @see gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability.SendToNationalCapability#send(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public void send(ValueObject valueObject) {

        send(valueObject, SendToNationalCapability.DEFAULT_QUEUE);

    }

    /**
     * Sets the JNDI template.
     * 
     * @param jndiTemplate JNDI
     */
    public void setJndiTemplate(JndiTemplate jndiTemplate) {
        this.jndiTemplate = jndiTemplate;
    }
}
