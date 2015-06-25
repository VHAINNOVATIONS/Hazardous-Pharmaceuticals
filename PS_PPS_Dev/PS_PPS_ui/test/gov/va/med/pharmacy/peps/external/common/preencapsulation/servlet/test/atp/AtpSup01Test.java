/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet.test.atp;


import gov.va.med.pharmacy.peps.external.common.preencapsulation.test.OrderCheckTestCase;


/**
 * Test the message bean using the MockVistACall object.
 */
public class AtpSup01Test extends OrderCheckTestCase {

    /**
     * ATP test case Sup01
     * 
     * @throws Exception Exception
     */
    public void testSendXMLCall() throws Exception {
        assertActualResponseEqual("xml/test/messages/atp/sup01.xml", "xml/test/messages/atp/sup01Response.xml");
    }
}
