/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability;


import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugData;


/**
 * Lookup the NDC and Products for the given NDC numbers and Vuid's.
 */
public interface DrugDataCapability {

    /**
     * Parse the XML request and pass to processRequest().
     * 
     * @param xml request XML from VistA
     * @return response XML
     */
    String handleRequest(String xml);

    /**
     * Lookup the NDC and Products for the given NDC numbers and Vuid's.
     * 
     * @param drugs DrugData containing NDC numbers and Vuid's
     * @return DrugData containing drugs (NDCs/Products) not found and drugs found (NDCs/Products)
     */
    DrugData processRequest(gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.request.DrugData drugs);

}
