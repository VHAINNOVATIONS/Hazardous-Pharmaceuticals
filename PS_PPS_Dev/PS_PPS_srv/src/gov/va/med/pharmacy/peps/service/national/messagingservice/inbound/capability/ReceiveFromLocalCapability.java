/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Receive from Local capability.
 */
public interface ReceiveFromLocalCapability {

    /**
     * Handle the response sent from a local
     * 
     * @param valueObject ValueObject returned from local
     * @throws ValidationException 
     */
    void onMessage(ValueObject valueObject) throws ValidationException;
}
