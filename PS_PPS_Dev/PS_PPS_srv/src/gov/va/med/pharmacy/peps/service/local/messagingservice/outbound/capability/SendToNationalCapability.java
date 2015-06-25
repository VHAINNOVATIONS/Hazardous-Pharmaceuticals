/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.messagingservice.outbound.capability;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.common.messagingservice.utility.MessagingServiceJmsConfiguration;


/**
 * Send messages to National PEPS instance
 */
public interface SendToNationalCapability extends MessagingServiceJmsConfiguration {
    
    /**
     * Send valueObject to the National PEPS instance
     * 
     * @param valueObject ValueObject to send
     * @param queueName Queue National will send response on 
     */
    void send(ValueObject valueObject, String queueName);
    
    /**
     * send
     * 
     * @param valueObject the object sent to national
     */
    void send(ValueObject valueObject);
    
}
