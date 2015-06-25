/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.atp;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.OrderCheckTestCase;


/**
 * Test the message bean using the MockVistACall object.
 */
public class Atp1007Test extends OrderCheckTestCase {

    /**
     * ATP test case 1007
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCall() throws Exception {
        assertActualResponseEqual("xml/test/messages/atp/1007.xml", "xml/test/messages/atp/1007Response.xml");
    }
}
