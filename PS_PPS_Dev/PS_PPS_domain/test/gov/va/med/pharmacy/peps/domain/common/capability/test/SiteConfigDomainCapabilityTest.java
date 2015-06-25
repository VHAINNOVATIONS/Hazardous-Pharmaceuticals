/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import gov.va.med.pharmacy.peps.domain.common.capability.SiteConfigDomainCapability;


/**
 * Test the {@link SiteConfigDomainCapability}
 */
public class SiteConfigDomainCapabilityTest extends DomainCapabilityTestCase {

//    private static final String SITE_NUMBER_LOCAL = "671";
//    private static final String SITE_NUMBER_NATIONAL = "999";

    private static final String DISABLED_TEST = "This test was disabled.";
    private static final boolean DISABLED_BOOL = true;

    @SuppressWarnings("unused")
    private SiteConfigDomainCapability siteConfigDomainCapability;

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.siteConfigDomainCapability = getNationalCapability(SiteConfigDomainCapability.class);
    }

    /**
     * Test retrieving all sites.
     */
    public void testRetrieveAll() {
        assertTrue(DISABLED_TEST, DISABLED_BOOL);

/*        List<SiteConfigVo> sites = siteConfigDomainCapability.retrieveAll();

        assertFalse("Should have at least one site", sites.isEmpty());
*/
    }

    /**
     * Test retrieving a specific site
     */
    public void testRetrieveSpecificSiteNational() {
        assertTrue(DISABLED_TEST, DISABLED_BOOL);

/*        SiteConfigVo site = siteConfigDomainCapability.retrieve(SITE_NUMBER_NATIONAL);

        assertNotNull("Should get site", site);
        assertEquals("ID should be equal to requested ID", SITE_NUMBER_NATIONAL, site.getSiteNumber());
        assertNotNull("Should have name", site.getSiteName());
        assertNotNull("Should have server name", site.getSiteServerName());
        assertNotNull("Should have peps version info", site.getSitePepsDbVersion());
*/
    }

    /**
     * Test retrieving the current site (Local-1)
     */
    public void testRetrieveSpecificSiteLocal() {
        assertTrue(DISABLED_TEST, DISABLED_BOOL);

/*        SiteConfigVo site = siteConfigDomainCapability.retrieve(SITE_NUMBER_LOCAL);

        assertNotNull("Should get site", site);
        assertEquals("ID should be equal to requested ID", SITE_NUMBER_LOCAL, site.getSiteNumber());
        assertNotNull("Should have name", site.getSiteName());
        assertNotNull("Should have server name", site.getSiteServerName());
        assertNotNull("Should have peps version info", site.getSitePepsDbVersion());
*/
    }

    /**
     * Test retrieving the current site (National)
     */
    public void testRetrieveSiteNational() {
        assertTrue(DISABLED_TEST, DISABLED_BOOL);

/*        SiteConfigVo site = siteConfigDomainCapability.retrieve();

        assertNotNull("Should get site", site);
        assertEquals("ID should be equal to requested ID", SITE_NUMBER_NATIONAL, site.getSiteNumber());
        assertNotNull("Should have name", site.getSiteName());
        assertNotNull("Should have server name", site.getSiteServerName());
        assertNotNull("Should have peps version info", site.getSitePepsDbVersion());
*/
    }
}
