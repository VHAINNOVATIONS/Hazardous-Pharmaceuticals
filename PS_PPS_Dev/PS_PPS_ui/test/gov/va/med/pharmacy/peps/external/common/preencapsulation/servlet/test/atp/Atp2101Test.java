/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.atp;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.OrderCheckTestCase;


/**
 * Test the message bean using the MockVistACall object.
 */
public class Atp2101Test extends OrderCheckTestCase {

    /**
     * ATP test case 2101
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCall() throws Exception {
        assertActualResponseEqual("xml/test/messages/atp/2101.xml", "xml/test/messages/atp/2101Response.xml");
    }
}
