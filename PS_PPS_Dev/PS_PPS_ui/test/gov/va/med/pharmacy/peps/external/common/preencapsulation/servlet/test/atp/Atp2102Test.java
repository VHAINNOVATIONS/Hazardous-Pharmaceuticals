/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.atp;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.OrderCheckTestCase;


/**
 * Test the message bean using the MockVistACall object.
 */
public class Atp2102Test extends OrderCheckTestCase {

    /**
     * ATP test case 2102
     * 
     * @throws Exception 
     */
    public void testSendXMLCall() throws Exception {
        assertActualExceptionEqual("xml/test/messages/atp/2102.xml", "xml/test/messages/atp/2102Response.xml");
    }
}
