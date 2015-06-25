/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.domain.common.capability.PharmacySystemDomainCapability;


/**
 * PharmacySystemDomainCapabilityTest's brief summary
 * 
 * Details of PharmacySystemDomainCapabilityTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class PharmacySystemDomainCapabilityTest extends DomainCapabilityTestCase {

//    private PharmacySystemDomainCapability localPharmacySystemDomainCapability;
    private PharmacySystemDomainCapability nationalPharmacySystemDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localPharmacySystemDomainCapability = getLocalOneCapability(PharmacySystemDomainCapability.class);
        this.nationalPharmacySystemDomainCapability = getNationalCapability(PharmacySystemDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testFindAllPharmacySystemLocal() throws Exception {
//
//        List<PharmacySystemVo> nameCollection = localPharmacySystemDomainCapability.retrieve();
//        assertEquals("Collection returned correct number", 1, nameCollection.size());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception exception
     */
    public void testFindAllPharmacySystemNational() throws Exception {

        List<PharmacySystemVo> rCollection = nationalPharmacySystemDomainCapability.retrieve();
        assertEquals("Collection returned correct number", 1, rCollection.size());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdatePharmacySystemLocal() throws Exception {
//
//        PharmacySystemVo names = localPharmacySystemDomainCapability.retrieve("671");
//
//        names.setRejectionReasonText("updatedRejectREasonTExt");
//
//        localPharmacySystemDomainCapability.update(names, getTestUser());
//
//        PharmacySystemVo retrievedUpdated = localPharmacySystemDomainCapability.retrieve("671");
//
//        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), "updatedRejectREasonTExt");
//
//    }

}
