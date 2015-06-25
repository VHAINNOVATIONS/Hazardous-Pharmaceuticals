/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.capability;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Interface for receiving from National.
 */
public interface ReceiveFromNationalCapability {

    /**
     * Handle the response sent from a local
     * 
     * @param valueObject ValueObject returned from local
     * @throws ValidationException if error validating ValueObject data
     */
    void onMessage(ValueObject valueObject) throws ValidationException;
}
