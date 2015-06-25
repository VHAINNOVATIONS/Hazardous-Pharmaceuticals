/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability;


/**
 * Parse the XML message and call the drug data vendor to perform the order checks.
 */
public interface ProcessOrderChecksCapability {

    /**
     * Handle the XML request
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    String handleRequest(String request);
}
