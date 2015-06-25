/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.impl;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability.HospitalQueueCapability;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.HospitalQueueService;


/**
 * Receive messages from the Hospital Queue, which are messages that failed delivery on the Local Topic or National Queue.
 */
public class HospitalQueueServiceImpl implements HospitalQueueService {
    private HospitalQueueCapability hospitalQueueCapability;

    /**
     * Receive messages from the Hospital Queue, which are messages that failed delivery on the Local Topic or National
     * Queue.
     * 
     * @param message JMS Message
     * @throws ValidationException ValidationException
     * 
     * @see gov.va.med.pharmacy.peps.service.common.messagingservice.message.MessagingServiceMdb#onMessage(javax.jms.Message)
     */
    public void onMessage(Message message) throws ValidationException {
        try {
            hospitalQueueCapability.onMessage((ValueObject) ((ObjectMessage) message).getObject());
        } catch (JMSException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.MESSAGING_SERVICE);
        }
    }

    /**
     * setHospitalQueueCapability
     * @param hospitalQueueCapability hospitalQueueCapability property
     */
    public void setHospitalQueueCapability(HospitalQueueCapability hospitalQueueCapability) {
        this.hospitalQueueCapability = hospitalQueueCapability;
    }
}
