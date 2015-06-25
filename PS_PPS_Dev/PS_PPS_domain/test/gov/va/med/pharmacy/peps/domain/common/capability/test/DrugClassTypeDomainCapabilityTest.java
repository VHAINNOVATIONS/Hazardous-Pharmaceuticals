/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassTypeDomainCapability;


/**
 * DrugClassTypeDomainCapabilityTest.
 */
public class DrugClassTypeDomainCapabilityTest extends DomainCapabilityTestCase {
    
//    private DrugClassTypeDomainCapability localDrugClassTypeDomainCapability;
    private DrugClassTypeDomainCapability nationalDrugClassTypeDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localDrugClassTypeDomainCapability = getLocalOneCapability(DrugClassTypeDomainCapability.class);
        this.nationalDrugClassTypeDomainCapability = getNationalCapability(DrugClassTypeDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllDrugClassTypeLocal() throws Exception {
//        List<DrugClassificationTypeVo> rCollection = localDrugClassTypeDomainCapability.retrieveDomain();
//
//        assertTrue("Collection returned correct number", rCollection.size() > 0);
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testFindAllDrugClassTypeNational() throws PharmacyException {

        List<DrugClassificationTypeVo> rCollection = nationalDrugClassTypeDomainCapability.retrieveDomain();

        assertTrue("Collection returned correct number", rCollection.size() > 0);
    }

}
