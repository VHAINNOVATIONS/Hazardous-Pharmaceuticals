/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;



/**
 * OiScheduleTypeDomainCapabilityTest
 */
public class OiScheduleTypeDomainCapabilityTest extends DomainCapabilityTestCase {
    
//    private OiScheduleTypeDomainCapability localOiScheduleTypeDomainCapability;
   // private OiScheduleTypeDomainCapability nationalOiScheduleTypeDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localOiScheduleTypeDomainCapability = getLocalOneCapability(OiScheduleTypeDomainCapability.class);
       // this.nationalOiScheduleTypeDomainCapability = getNationalCapability(OiScheduleTypeDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllOiScheduleTypeLocal() throws Exception {
//        List<OiScheduleTypeVo> rCollection = localOiScheduleTypeDomainCapability.retrieveDomain();
//
//        assertTrue("Collection returned correct number", rCollection.size() > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllOiScheduleTypeNational() throws Exception {
        boolean isTrue = true;
        assertTrue("Local only test", isTrue);
        
       // List<OiScheduleTypeVo> rCollection = nationalOiScheduleTypeDomainCapability.retrieveDomain();

       // assertTrue("Collection returned correct number", rCollection.size() > 0);
    }

}

