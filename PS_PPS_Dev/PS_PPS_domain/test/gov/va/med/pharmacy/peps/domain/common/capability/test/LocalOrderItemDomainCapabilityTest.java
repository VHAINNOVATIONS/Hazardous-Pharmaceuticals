/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;




/**
 * LocalOrderItemDomainCapabilityTest.
 */
public class LocalOrderItemDomainCapabilityTest extends DomainCapabilityTestCase {

    private boolean isTrue = true;
    
    /**
     * These tests only exist at local.
     */
    public void testSkipped()  {
        assertTrue("Entire class is skipped.", isTrue);
    }

}
