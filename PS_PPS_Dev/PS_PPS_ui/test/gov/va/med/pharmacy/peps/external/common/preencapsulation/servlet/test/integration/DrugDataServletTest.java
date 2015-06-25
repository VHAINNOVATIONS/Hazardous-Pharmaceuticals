/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.integration;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.VistAWebServiceTestCase;


/**
 * Test requests to retrieve NDC and Products using NDC Numbers and Product Vuids
 */
public class DrugDataServletTest extends VistAWebServiceTestCase {

    /**
     * Verify a request with a single invalid VUID
     * 
     * @throws Exception exception
     */
    public void testSingleVUIDInvalid() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/data/drugDataInvalidVuidRequest.xml",
            ".*<drugsNotFound>.*");
    }

    /**
     * Verify a request with a single drug (NDC Number) works properly
     * 
     * @throws Exception exception
     */
    public void testSingleNdc() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/data/drugDataSingleNdcRequest.xml",
            ".*<drugRequestIdentifier>\\s*<ndc>182014101</ndc>\\s*</drugRequestIdentifier>.*");
    }

    /**
     * Verify a request with a single drug (product VUID) works properly
     * 
     * @throws Exception exception
     */
    public void testSingleVuid() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/data/drugDataSingleVuidRequest.xml",
            ".*<drugRequestIdentifier>\\s*<vuid>4007158</vuid>\\s*</drugRequestIdentifier>.*");
    }

    /**
     * Verify a request with multiple drugs works properly. 2 NDC Numbers and 2 VUIDS are used, one each will not be found
     * and one each will be found
     * 
     * @throws Exception exception
     */
    public void testMultipleDrug() throws Exception {
        String tag = ".*<drugsNotFound>\\s*<vuid>2902325</vuid>\\s*</drugsNotFound>.*"
            + "<drugRequestIdentifier>\\s*<ndc>182014101</ndc>\\s*</drugRequestIdentifier>.*"
            + "<drugRequestIdentifier>\\s*<vuid>4003336</vuid>\\s*</drugRequestIdentifier>.*";

        assertActualResponseEqual("../Interface/etc/xml/document/drug/data/drugDataMultipleRequest.xml", tag);
    }

    /**
     * Return URI.
     * 
     * @return URI
     */
    public String getRequestURL() {
        return "encapsulation/drug/data";
    }
}
