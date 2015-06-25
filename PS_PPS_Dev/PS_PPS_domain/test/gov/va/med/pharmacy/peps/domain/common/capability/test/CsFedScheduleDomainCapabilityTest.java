/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.domain.common.capability.CsFedScheduleDomainCapability;


/**
 * CsFedScheduleDomainCapabilityTest
 */
public class CsFedScheduleDomainCapabilityTest extends DomainCapabilityTestCase {

    //    private CsFedScheduleDomainCapability localCsFedScheduleDomainCapability;
    private CsFedScheduleDomainCapability nationalCsFedScheduleDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localCsFedScheduleDomainCapability = getLocalOneCapability(CsFedScheduleDomainCapability.class);
        this.nationalCsFedScheduleDomainCapability = getNationalCapability(CsFedScheduleDomainCapability.class);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testFindAllCsFedScheduleNational() throws PharmacyException {

        List<CsFedScheduleVo> rCollection = nationalCsFedScheduleDomainCapability.retrieveDomain();

        assertTrue("Collection returned correct number", rCollection.size() > 0);
    }
    
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllCsFedScheduleLocal() throws Exception {
//
//        List<CsFedScheduleVo> rCollection = localCsFedScheduleDomainCapability.retrieveDomain();
//
//        assertTrue("Collection returned correct number", rCollection.size() > 0);
//    }

}
