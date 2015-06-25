/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.test;


import gov.va.med.pharmacy.peps.common.vo.DrugDataVendorVersionVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.VersionCapability;
import gov.va.med.pharmacy.peps.external.common.test.InterfaceTestCase;


/**
 * Test the VersionCapability
 */
public class VersionCapabilityTest extends InterfaceTestCase {
    private VersionCapability versionCapability;

    /**
     * Get instance of the VersionCapability via Spring.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        this.versionCapability = getSpringBean(VersionCapability.class);
    }

    /**
     * Verify the return result is not null and all attributes are not null.
     */
    public void testRetrieveDrugDataVendorVersion() {
        DrugDataVendorVersionVo version = versionCapability.retrieveDrugDataVendorVersion();

        assertNotNull("Version VO cannot be null", version);
        assertNotNull("Build Version cannot be null", version.getBuildVersion());
        assertNotNull("Data Version cannot be null", version.getDataVersion());
        assertNotNull("Issue Date cannot be null", version.getIssueDate());
    }
}
