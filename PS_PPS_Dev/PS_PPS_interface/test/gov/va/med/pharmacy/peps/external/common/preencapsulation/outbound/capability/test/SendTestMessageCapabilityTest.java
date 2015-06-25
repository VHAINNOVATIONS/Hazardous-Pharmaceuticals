/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.SendTestMessageCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl.SendTestMessageCapabilityImpl;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.test.stub.VistaLinkConnectionUtilityStub;

import junit.framework.TestCase;


/**
 * Class for testing the send message capability
 */
public class SendTestMessageCapabilityTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(SendTestMessageCapabilityTest.class);
    private SendTestMessageCapability capability;
    
    
    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        SendTestMessageCapabilityImpl capabilityImpl = new SendTestMessageCapabilityImpl();
        VistaLinkConnectionUtility connection = new VistaLinkConnectionUtilityStub();
        capabilityImpl.setVistaLinkConnectionUtility(connection);
        this.capability = capabilityImpl;
    }
    
    /**
     * Verify VistALink returns same string that we send it
     */
    public void testSendTestMessage() {
        String testMessage = "Test Message";
        String response = capability.sendTestMessage(testMessage);

        LOG.debug("Response= " + response);

        assertEquals("The response should be the same as the testMessage sent", "Returned Input Value: " + testMessage,
            response);
    }
   
}
