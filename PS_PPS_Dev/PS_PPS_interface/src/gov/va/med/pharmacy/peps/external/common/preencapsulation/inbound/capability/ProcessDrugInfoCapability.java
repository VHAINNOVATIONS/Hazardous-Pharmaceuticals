/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability;


/**
 * Retrieve the dose routes and dose types for a given list of GCN sequence numbers.
 */
public interface ProcessDrugInfoCapability {

    /**
     * Handle the XML request
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    String handleRequest(String request);
}
