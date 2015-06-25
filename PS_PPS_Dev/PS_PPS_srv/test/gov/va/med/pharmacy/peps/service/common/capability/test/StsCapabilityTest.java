/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.test;



import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.service.common.capability.StsCapability;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;



/**
 * Test the base class of DefaultRulesCapability. Also test that a sub class still has access to the super's Spring injected
 * classes.
 */
public class StsCapabilityTest extends DomainCapabilityTestCase {
    private static final Logger LOG = Logger.getLogger(StsCapabilityTest.class);
    private StsCapability stsCapability;

    
    private StandardMedRouteDomainCapability standardMedRouteDomainCapability;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
   
    /**
     * Get instance of {@link RulesCapability}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.info("---------- " + getName() + " ----------");

        this.stsCapability = SpringTestConfigUtility.getNationalSpringBean(StsCapability.class);
        this.standardMedRouteDomainCapability = getNationalCapability(
            StandardMedRouteDomainCapability.class);
        this.itemAuditHistoryDomainCapability = getNationalCapability(
            ItemAuditHistoryDomainCapability.class);
        
        stsCapability.setItemAuditHistoryDomainCapability(itemAuditHistoryDomainCapability);
        stsCapability.setStandardMedRouteDomainCapability(standardMedRouteDomainCapability);
 
    }

    
    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     */
    public void testSynchronizeSts() {
        
        try {
            stsCapability.synchronizedFDBUpdate();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
}
