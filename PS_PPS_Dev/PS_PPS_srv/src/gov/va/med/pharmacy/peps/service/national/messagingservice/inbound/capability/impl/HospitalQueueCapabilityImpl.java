/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability.impl;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability.HospitalQueueCapability;


/**
 * Handle the messages received on the Hospital Queue
 */
public class HospitalQueueCapabilityImpl implements HospitalQueueCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HospitalQueueCapabilityImpl.class);

    /**
     * Handle the messages received on the Hospital Queue.
     * 
     * Log errors showing that the messages were not able to be delivered.
     * 
     * @param valueObject ValueObject returned from local
     */
    public void onMessage(ValueObject valueObject) {
        LOG.error("Message unable to be processed by PEPS!" + valueObject);
    }
}
