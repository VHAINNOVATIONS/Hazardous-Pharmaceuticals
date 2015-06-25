/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.common.messagingservice.utility.MessagingServiceJmsConfiguration;


/**
 * Send a ValueObject to the given JMS Topic
 */
public interface SendToLocalCapability extends MessagingServiceJmsConfiguration {

    /**
     * Send given ValueObject to the topic name via JMS
     * 
     * @param valueObject ValueObject to send
     * @param topicName Topic to send on
     */
    void send(ValueObject valueObject, String topicName);
    
    /**
     * send
     * 
     * @param valueObject vo sent
     */
    void send(ValueObject valueObject);
    
}
