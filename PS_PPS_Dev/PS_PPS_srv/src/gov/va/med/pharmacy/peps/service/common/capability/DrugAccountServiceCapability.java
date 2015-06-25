/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.RequestAdd;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.RequestUpdate;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.response.ResponseType;


/**
 * Performs Drug accountability Actions for the local level
 */
public interface DrugAccountServiceCapability {
    
    /**
     * Process the drugAccountability request from Vista
     *  
     * @param xml Collection of add and update messages
     * @return xml response containing success and failure messages 
     */
    String processRequest(String xml);
    
    
    /**
     *  Processes the individual add
     * 
     * @param request A single add request object
     * @return response The response type
     */
    ResponseType processNewRequest(RequestAdd request);

    /**
     * Processes the individual update
     * 
     * @param request A single update request object
     * @return response The response type
     */
    ResponseType processUpdateRequest(RequestUpdate request);
}
