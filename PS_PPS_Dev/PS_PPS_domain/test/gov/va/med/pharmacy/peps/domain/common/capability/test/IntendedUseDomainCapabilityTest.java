/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;


/**
 * IntendedUseDomainCapabilityTest.
 */
public class IntendedUseDomainCapabilityTest extends DomainCapabilityTestCase {
    
//    private IntendedUseDomainCapability localIntendedUseDomainCapability;
    private IntendedUseDomainCapability nationalIntendedUseDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localIntendedUseDomainCapability = getLocalOneCapability(IntendedUseDomainCapability.class);
        this.nationalIntendedUseDomainCapability = getNationalCapability(IntendedUseDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * @throws Exception
//     */
//    public void testFindAllIntendedUseLocal() throws Exception {
//
//        List<IntendedUseVo> nameCollection = localIntendedUseDomainCapability.retrieveDomain();
//
//        assertEquals(new String("Collection did returned correct number"), 4, nameCollection.size());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * @throws PharmacyException PharmacyException
     */
    public void testFindAllIntendedUseNational() throws PharmacyException {

        List<IntendedUseVo> nameCollection = nationalIntendedUseDomainCapability.retrieveDomain();

        assertEquals(new String("Collection did returned correct number"), PPSConstants.I4, nameCollection.size());
    }
    
    
    /**
     * This test is a straight copy of the method used in the NDCRulesCapabilityImpl getIntendedUse().
     * 
     * If this method fails the ndc rule to update a product's Synonyms based on trade name will fail as well
     */
    public void testFindTradeNameIntendedUse() {
        
        IntendedUseVo intendedUseVo = null;
        List<IntendedUseVo> intendedUseList = nationalIntendedUseDomainCapability.retrieveDomain();

        for (IntendedUseVo intendedUse : intendedUseList) {
            if (intendedUse.isTradeName()) {
                intendedUseVo = intendedUse;
                break;
            }
        }

        // Throw exception if item not found
        if (intendedUseVo == null) {
            fail("National Intended Use did not find the Trade Name Intended Use");
        }

    }
    
}
