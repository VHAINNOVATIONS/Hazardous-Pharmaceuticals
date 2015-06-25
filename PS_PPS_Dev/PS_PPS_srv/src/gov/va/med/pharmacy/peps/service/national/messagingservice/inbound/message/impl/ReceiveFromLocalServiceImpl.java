/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.impl;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability.ReceiveFromLocalCapability;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.ReceiveFromLocalService;


/**
 * Receive response from local
 */
public class ReceiveFromLocalServiceImpl implements ReceiveFromLocalService {
    
    private ReceiveFromLocalCapability receiveFromLocalCapability;

    /**
     * Empty constructor
     */
    public ReceiveFromLocalServiceImpl() {
        super();
    }

    /**
     * Handling incoming JMS Message
     * 
     * @param message JMS Message
     * 
     * @throws ValidationException if the message is invalid.
     */
    public void onMessage(Message message) throws ValidationException {
        try {
            receiveFromLocalCapability.onMessage((ValueObject) ((ObjectMessage) message).getObject());
        } catch (JMSException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.MESSAGING_SERVICE);
        }
    }

    /**
     * setReceiveFromLocalCapability
     * @param capability capability property
     */
    public void setReceiveFromLocalCapability(ReceiveFromLocalCapability capability) {
        this.receiveFromLocalCapability = capability;
    }

}
