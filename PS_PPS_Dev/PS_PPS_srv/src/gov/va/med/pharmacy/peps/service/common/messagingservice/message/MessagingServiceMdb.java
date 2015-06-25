/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.messagingservice.message;


import javax.jms.Message;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;


/**
 * Receive a JMS message
 */
public interface MessagingServiceMdb {

    /**
     * Receive a JMS message
     * 
     * @param message JMS message
     * @throws ValidationException 
     */
    void onMessage(Message message) throws ValidationException;
}
