/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.servlet.test.integration;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.VistAWebServiceTestCase;


/**
 * Test PMI.
 */
public class PatientMedicationInformationServletTest extends VistAWebServiceTestCase {

    /**
     * Test English version request.
     * 
     * @throws Exception exception
     */
    public void testPmiRequest() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/pmi/pmiRequest.xml", ".*Warfarin.*");
    }

    /**
     * Test Fail version request.
     * 
     * @throws Exception exception
     */
    public void testPmiFailRequest() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/pmi/pmiRequestFail.xml", ".*<noData/>.*");
    }

    /**
     * Test Spanish version request.
     * 
     * @throws Exception exception
     */
    public void testSpanishPmiRequest() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/pmi/pmiRequest-Spanish.xml", ".*Warfarina.*");
    }

    /**
     * Test English version request with a VUID.
     * 
     * @throws Exception exception
     */
    public void testVuidPmiRequest() throws Exception {
        assertActualResponseEqual("../Interface/etc/xml/document/drug/pmi/pmiRequest-VUID.xml", ".*(Warfarin|noData).*");
    }

    /**
     * Return URI.
     * 
     * @return URI
     */
    public String getRequestURL() {
        return "drugdatavendor/pmi";
    }
}
