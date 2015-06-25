/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.capability.test;


import gov.va.med.pharmacy.peps.common.capability.BuildVersionCapability;
import gov.va.med.pharmacy.peps.common.capability.impl.BuildVersionCapabilityImpl;


import junit.framework.TestCase;


/**
 * Verify the BuildVersionCapability returns the values, as expected.
 */
public class BuildVersionCapabilityTest extends TestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BuildVersionCapabilityTest.class);

    
    private BuildVersionCapability capability;

    /**
     * setUp
     * @throws Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.debug("\n------------------------- " + getName() + " -------------------------\n");

        this.capability = new BuildVersionCapabilityImpl();
    }

    /**
     * Only tests that the returned string is not null or empty.
     */
    public void testGetBaseline() {
        String baselineName = capability.getBaseline();

        LOG.debug("Baseline name: " + baselineName);
        assertNotNull("Baseline name must not be null.", baselineName);
        assertTrue("Baseline name must not be empty.", baselineName.trim().length() > 0);
    }

    /**
     * Only tests that the returned string is not null or empty.
     */
    public void testGetDate() {
        String buildDate = capability.getDate();

        LOG.debug("Build date: " + buildDate);
        assertNotNull("Build date must not be null.", buildDate);
        assertTrue("Build date must not be empty.", buildDate.trim().length() > 0);
    }

    /**
     * Only tests that the returned string is not null or empty.
     */
    public void testGetVersion() {
        String version = capability.getVersion();

        LOG.debug("Version: " + version);
        assertNotNull("Version must not be null.", version);
        assertTrue("Version must not be empty.", version.trim().length() > 0);
    }
}
