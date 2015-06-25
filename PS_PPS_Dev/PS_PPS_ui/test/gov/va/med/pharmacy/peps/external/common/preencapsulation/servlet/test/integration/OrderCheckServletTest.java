/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.integration;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.OrderCheckTestCase;


/**
 * Test the Order Check operation using the deployed servlet as an integration test.
 */
public class OrderCheckServletTest extends OrderCheckTestCase {

    /**
     * Test web service order check
     * 
     * @throws Exception exception
     */
    public void testSendXMLCall() throws Exception {
        assertActualResponseEqual("xml/test/messages/orderCheckBig.xml", "xml/test/messages/orderCheckBigResponse.xml");
    }

    /**
     * Test order check 1
     * 
     * @throws Exception exception
     */
    public void testOrderCheck1() throws Exception {
        assertActualResponseEqual("xml/test/messages/orderCheck1.xml", "xml/test/messages/orderCheckResponse1.xml");
    }

    /**
     * Test order check 2
     * 
     * @throws Exception exception
     */
    public void testOrderCheck2() throws Exception {
        assertActualResponseEqual("xml/test/messages/orderCheck2.xml", "xml/test/messages/orderCheckResponse2.xml");
    }

    /**
     * Test order check 4
     * 
     * @throws Exception exception
     */
    public void testOrderCheck4() throws Exception {
        assertActualResponseEqual("xml/test/messages/orderCheck4.xml", "xml/test/messages/orderCheckResponse4.xml");
    }

    /**
     * Test an invalid request for an order check
     * 
     * @throws Exception exception
     */
    public void testOrderCheckInvalidMessage1() throws Exception {
        assertActualExceptionEqual("xml/test/messages/drugCheckInputMissingAttribute.xml",
            "xml/test/messages/drugCheckInputMissingAttributeResponse.xml");
    }

    /**
     * Test an invalid request for an order check
     * 
     * @throws Exception exception
     */
    public void testOrderCheckInvalidMessage2() throws Exception {
        assertActualExceptionEqual("xml/test/messages/drugCheckInputMissingElement.xml",
            "xml/test/messages/drugCheckInputMissingElementResponse.xml");
    }

    /**
     * Test an invalid request for an order check
     * 
     * @throws Exception exception
     */
    public void testOrderCheckInvalidMessage3() throws Exception {
        assertActualExceptionEqual("xml/test/messages/drugCheckInputWrongEnumeration.xml",
            "xml/test/messages/drugCheckInputWrongEnumerationResponse.xml");
    }

    /**
     * Test that a general dose check works.
     * 
     * @throws Exception exception
     */
    public void testGeneralDoseCheck() throws Exception {
        assertActualResponseEqual("xml/test/messages/generalDoseCheck.xml", "xml/test/messages/generalDoseCheckResponse.xml");
    }

    /**
     * Test that duplicate GCNs are handled properly, and that the right drug is associated with the right response. We used
     * to use GCNs to map back from the FDB results to the drugs, but the GCNs may not be unique in a check, therefore we
     * needed to use a combination of IEN and order number.
     * 
     * @throws Exception exception
     */
    public void testDuplicateGcn() throws Exception {
        assertActualResponseEqual("xml/test/messages/duplicateGcn.xml", "xml/test/messages/duplicateGcnResponse.xml");
    }

    /**
     * Test optional fields frequency, duration, and duration rate in a specific dose check.
     * 
     * @throws Exception if error
     */
    public void testOptionalFieldsInSpecificDoseCheck() throws Exception {
        assertActualResponseEqual("xml/test/messages/optionalSpecificDoseCheck.xml",
            "xml/test/messages/optionalSpecificDoseCheckResponse.xml");
    }

    /**
     * Test ping request
     * 
     * @throws Exception if error
     */
    public void testPing() throws Exception {
        assertActualResponseEqual("xml/test/messages/ping.xml", "xml/test/messages/pingResponse.xml");
    }

    /**
     * Test that a non-ping request without a body produces an error
     * 
     * @throws Exception if error
     */
    public void testNoBody() throws Exception {
        assertActualExceptionEqual("xml/test/messages/noBody.xml", "xml/test/messages/noBodyResponse.xml");
    }

    /**
     * Test that a request without any order check types throws an exception
     * 
     * @throws Exception if error
     */
    public void testNoChecks() throws Exception {
        assertActualExceptionEqual("xml/test/messages/noChecks.xml", "xml/test/messages/noChecksResponse.xml");
    }

    /**
     * Test that a prospectiveOnly="true" that has no prospective drugs throws an exception
     * 
     * @throws Exception if error
     */
    public void testNoProspectives() throws Exception {
        assertActualExceptionEqual("xml/test/messages/noProspectives.xml", "xml/test/messages/noProspectivesResponse.xml");
    }

    /**
     * Test that a non-ping request without a body produces an error
     * 
     * @throws Exception if error
     */
    public void testNoDrugs() throws Exception {
        assertActualExceptionEqual("xml/test/messages/noDrugs.xml", "xml/test/messages/noDrugsResponse.xml");
    }

    /**
     * Test ping request
     * 
     * @throws Exception if error
     */
    public void testProfileOnly() throws Exception {
        assertActualResponseEqual("xml/test/messages/profileOnly.xml", "xml/test/messages/profileOnlyResponse.xml");
    }

    /**
     * Test that the duplicate therapy bug is resolved.
     * 
     * The FDB DIF API prior to version 3.2.2 had a bug where if custom tables and duplicate allowance were requested to be
     * used, the duplicate allowance would be ignored.
     * 
     * @throws Exception exception
     */
    public void testDuplicateTherapyBug() throws Exception {
        assertActualResponseEqual("xml/test/messages/duplicateTherapyBug.xml",
            "xml/test/messages/duplicateTherapyBugResponse.xml");
    }

}
