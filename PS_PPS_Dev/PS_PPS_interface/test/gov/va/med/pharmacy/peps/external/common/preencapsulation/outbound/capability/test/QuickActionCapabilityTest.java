/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.QuickActionCapability;

import junit.framework.TestCase;


/**
 * Quick Action Capabilit Test
 */
public class QuickActionCapabilityTest extends TestCase {

    private QuickActionCapability capability;

    /**
     * Setup the test
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        ApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] {"classpath*:xml/spring/test/*Context.xml", "classpath*:xml/local/spring/test/*Context.xml",
                          "classpath*:xml/local/spring/test/CommonContext-Local-1.xml",
                          "classpath*:xml/spring/test/Callback.xml"});

        this.capability = (QuickActionCapability) context.getBean("quickActionCapability");
    }

    /**
     * TEst Sending the drug file
     */
    public void testSendDrugFileToInter() {
        try {
            capability.sendDrugFileToExternalInterface(null);
        } catch (Exception e) {
            fail("Quick Action message was not queued due to Exception thrown");
        }
    }

}
