/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Handle the messages received on the Hospital Queue
 */
public interface HospitalQueueCapability {

    /**
     * Handle the messages received on the Hospital Queue
     * 
     * @param valueObject ValueObject returned from local
     */
    void onMessage(ValueObject valueObject);
}
