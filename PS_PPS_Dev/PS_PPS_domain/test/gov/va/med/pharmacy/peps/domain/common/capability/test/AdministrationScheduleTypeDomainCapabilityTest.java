/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleTypeVo;
import gov.va.med.pharmacy.peps.domain.common.capability.AdministrationScheduleTypeDomainCapability;


/**
 * AdministrationScheduleTypeDomainCapabilityTest.
 */
public class AdministrationScheduleTypeDomainCapabilityTest extends DomainCapabilityTestCase {
    
//    private AdministrationScheduleTypeDomainCapability localAdministrationScheduleTypeDomainCapability;
    private AdministrationScheduleTypeDomainCapability nationalAdministrationScheduleTypeDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
        this.nationalAdministrationScheduleTypeDomainCapability = 
            getNationalCapability(AdministrationScheduleTypeDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllAdministrationScheduleTypeLocal() throws Exception {
//        List<AdministrationScheduleTypeVo> rCollection = localAdministrationScheduleTypeDomainCapability.retrieveDomain();
//
//        assertTrue("Collection returned correct number", rCollection.size() > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testFindAllAdministrationScheduleTypeNational() throws PharmacyException {

        List<AdministrationScheduleTypeVo> rCollection = nationalAdministrationScheduleTypeDomainCapability.retrieveDomain();

        assertTrue("Collection returned correct number", rCollection.size() > 0);
    }

}
