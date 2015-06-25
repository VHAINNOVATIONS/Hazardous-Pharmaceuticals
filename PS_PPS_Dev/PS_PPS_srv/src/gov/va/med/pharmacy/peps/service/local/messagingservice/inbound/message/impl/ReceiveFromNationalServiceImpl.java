/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.message.impl;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.capability.ReceiveFromNationalCapability;
import gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.message.ReceiveFromNationalService;


/**
 * Receive response from local
 */
public class ReceiveFromNationalServiceImpl implements ReceiveFromNationalService {
    private ReceiveFromNationalCapability receiveFromNationalCapability;

    /**
     * Empty constructor
     */
    public ReceiveFromNationalServiceImpl() {
        super();
    }

    /**
     * Handling incoming JMS Message
     * 
     * @param message JMS Message
     * @throws ValidationException if error validating ValueObject data
     */
    public void onMessage(Message message) throws ValidationException {
        try {
            receiveFromNationalCapability.onMessage((ValueObject) ((ObjectMessage) message).getObject());
        } catch (JMSException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.MESSAGING_SERVICE);
        }
    }

    /**
     * setReceiveFromNationalCapability
     * @param capability capability property
     */
    public void setReceiveFromNationalCapability(ReceiveFromNationalCapability capability) {
        this.receiveFromNationalCapability = capability;
    }

}
