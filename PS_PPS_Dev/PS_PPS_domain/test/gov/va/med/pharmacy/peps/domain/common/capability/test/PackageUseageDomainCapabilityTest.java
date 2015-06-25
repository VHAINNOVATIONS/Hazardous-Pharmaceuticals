/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageUseageDomainCapability;



/**
 * PackageUseageDomainCapabilityTest's brief summary
 * 
 * Details of PackageUseageDomainCapabilityTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class PackageUseageDomainCapabilityTest extends DomainCapabilityTestCase {
    
//    private PackageUseageDomainCapability localpackageUseageDomainCapability;
    private PackageUseageDomainCapability nationalpackageUseageDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localpackageUseageDomainCapability = getLocalOneCapability(PackageUseageDomainCapability.class);
        this.nationalpackageUseageDomainCapability = getNationalCapability(PackageUseageDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllPackageUseageLocal() throws Exception {
//
//        List<PackageUsageVo> rCollection = localpackageUseageDomainCapability.retrieveDomain();
//
//        assertTrue("Collection returned correct number", rCollection.size() > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    public void testFindAllPackageUseageNational() throws Exception {

        List<PackageUsageVo> rCollection = nationalpackageUseageDomainCapability.retrieveDomain();

        assertTrue("Collection returned correct number", rCollection.size() > 0);
    }
}
