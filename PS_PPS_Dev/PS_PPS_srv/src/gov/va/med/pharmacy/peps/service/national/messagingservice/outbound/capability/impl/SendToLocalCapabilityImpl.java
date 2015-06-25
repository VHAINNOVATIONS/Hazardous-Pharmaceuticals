/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.impl;


import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.springframework.jndi.JndiTemplate;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.SendToLocalCapability;


/**
 * Send message to a JMS Topic
 */
public class SendToLocalCapabilityImpl implements SendToLocalCapability {
    private JndiTemplate jndiTemplate;

    /**
     * Empty constructor
     */
    public SendToLocalCapabilityImpl() {
        super();
    }

    /**
     * Send given ValueObject to the topic name via JMS
     * 
     * @param valueObject ValueObject to send
     * @param topicName Topic to send on
     * 
     * @see gov.va.med.pharmacy.peps.external.national.messagingservice.outbound.capability.SendToTopicCapability#send(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject,
     *      java.lang.String)
     */
    public void send(ValueObject valueObject, String topicName) {
        TopicConnection connection = null;

        try {
            TopicConnectionFactory factory = (TopicConnectionFactory) jndiTemplate.lookup(CONNECTION_FACTORY);
            Topic topic = (Topic) PortableRemoteObject.narrow(jndiTemplate.lookup(topicName), Topic.class);

            connection = factory.createTopicConnection();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicPublisher publisher = session.createPublisher(topic);
            ObjectMessage message = session.createObjectMessage(valueObject);

            publisher.publish(message);

            publisher.close();
            session.close();
            connection.close();
        } catch (NamingException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.MESSAGING_SERVICE);
        } catch (JMSException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.MESSAGING_SERVICE);
        }
    }

    /**
     * Send the ValueObject on the default topic.
     * 
     * @param valueObject ValueObject to send
     * 
     * @see gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.SendToLocalCapability#send(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public void send(ValueObject valueObject) {
        this.send(valueObject, SendToLocalCapability.DEFAULT_TOPIC);
    }

    /**
     * Set the JNDI template used to lookup topic
     * 
     * @param jndiTemplate jndiTemplate property
     */
    public void setJndiTemplate(JndiTemplate jndiTemplate) {
        this.jndiTemplate = jndiTemplate;
    }
}
