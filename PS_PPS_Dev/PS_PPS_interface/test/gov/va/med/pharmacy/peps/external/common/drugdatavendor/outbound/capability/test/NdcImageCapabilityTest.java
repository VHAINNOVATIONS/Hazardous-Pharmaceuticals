/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test;


import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.NdcImageCapability;
import gov.va.med.pharmacy.peps.external.common.test.InterfaceTestCase;


/**
 * Test the VersionCapability
 */
public class NdcImageCapabilityTest extends InterfaceTestCase {
    private NdcImageCapability ndcImageCapability;

    /**
     * Get instance of the VersionCapability via Spring.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        this.ndcImageCapability = getSpringBean(NdcImageCapability.class);
    }

    /**
     * Verify the return result is not null and all attributes are not null.
     */
    public void testGetImageFileName542() {
        String fileName = ndcImageCapability.getImageFileName("00002-3239-30");


        assertNotNull("1.File Name cannot be null", fileName);
    }
    
    /**
     * Verify the return result is not null and all attributes are not null.
     */
    public void testGetImageFileName642() {
        String fileName = ndcImageCapability.getImageFileName("000002-3239-30");


        assertNotNull("File Name cannot be null", fileName);
    }
}
