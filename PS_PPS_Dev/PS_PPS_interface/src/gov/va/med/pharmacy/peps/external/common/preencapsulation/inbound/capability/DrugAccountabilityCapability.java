/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.request.DrugAccountabilityRequest;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.drugaccountability.response.DrugAccountabilityResponse;


/**
 * Lookup the NDC and Products for the given NDC numbers and Vuid's.
 */
public interface DrugAccountabilityCapability {

    /**
     * Parse the XML request and pass to processRequest().
     * 
     * @param xml request XML from VistA
     * @return response A drug accountability request object
     */
    DrugAccountabilityRequest handleRequest(String xml);

    /**
     * Parse the XML request and pass to processRequest().
     * 
     * @param response The drug accountability response object
     * @return response XML
     */
    String handleResponse(DrugAccountabilityResponse response);  

}
