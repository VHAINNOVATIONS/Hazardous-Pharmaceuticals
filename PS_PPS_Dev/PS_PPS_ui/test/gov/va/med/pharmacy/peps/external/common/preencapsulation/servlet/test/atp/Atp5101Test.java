/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.atp;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.DrugInfoTestCase;


/**
 * Test the message bean using the MockVistACall object.
 */
public class Atp5101Test extends DrugInfoTestCase {

    /**
     * ATP test case 5101
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCall() throws Exception {
        assertActualResponseEqual("xml/test/messages/atp/5101.xml", "xml/test/messages/atp/5101Response.xml");
    }
}
