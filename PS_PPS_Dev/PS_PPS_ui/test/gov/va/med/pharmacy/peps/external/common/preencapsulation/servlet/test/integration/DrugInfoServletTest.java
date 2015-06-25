/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.integration;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.DrugInfoTestCase;


/**
 * Test requests to retrieve dose routes and types for GCN sequence numbers
 */
public class DrugInfoServletTest extends DrugInfoTestCase {

    /**
     * Verify a request with a single drug works properly
     * 
     * @throws Exception exception
     */
    public void testSingleDrug() throws Exception {
        assertActualResponseEqual("xml/test/messages/drugInfoSingle.xml", "xml/test/messages/drugInfoSingleResponse.xml");
    }

    /**
     * Verify a request with multiple drugs works properly
     * 
     * @throws Exception exception
     */
    public void testMultipleDrug() throws Exception {
        assertActualResponseEqual("xml/test/messages/drugInfoMultiple.xml",
            "xml/test/messages/drugInfoMultipleResponse.xml");
    }

    /**
     * Verify unknown GCN sequence numbers are handled properly
     * 
     * @throws Exception exception
     */
    public void testDrugNotFound() throws Exception {
        assertActualResponseEqual("xml/test/messages/drugInfoUnknownGcn.xml",
            "xml/test/messages/drugInfoUnknownGcnResponse.xml");
    }
}
