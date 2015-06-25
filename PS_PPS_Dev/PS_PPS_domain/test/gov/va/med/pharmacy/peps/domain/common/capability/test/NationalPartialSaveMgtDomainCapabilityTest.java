/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.domain.common.capability.PartialSaveMgtDomainCapability;


/**
 * NationalPartialSaveMgtDomainCapabilityTest
 */
public class NationalPartialSaveMgtDomainCapabilityTest extends DomainCapabilityTestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
                NationalPartialSaveMgtDomainCapabilityTest.class);
    private PartialSaveMgtDomainCapability partialSaveMgtDomainCapability;

    /**
     * Retrieve the partial save capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.debug("--------------- " + getName() + " ----------------");
        
        this.partialSaveMgtDomainCapability = getNationalCapability(PartialSaveMgtDomainCapability.class);
    }

    /**
     * testRetrieveById
     * 
     * 
     * @throws Exception Exception
     */
    public void testRetrieveById() throws Exception {
        List<PartialSaveVo> testVo = partialSaveMgtDomainCapability.retrieveAll();

        LOG.debug(" TEST RETRIEVE OI: " + testVo.toString());
        assertNotNull(" Returned Item Result not returned", testVo);
    }

    /**
     * 
     * for initial testing only hard coded id will throw error when run a second time
     * 
     * @throws Exception Exception
     */
    public void testAddPartialSaveMgt() throws Exception {
        PartialSaveVo vo = buildPartialSaveMgt();

        PartialSaveVo newVO = partialSaveMgtDomainCapability.create(vo, getTestUser());

        assertNotNull("Returned Item Result not returned", newVO);
    }

    /**
     * For testing partial save vo operations
     * 
     * @return a stub partial save VO
     */
    private PartialSaveVo buildPartialSaveMgt() {
        PartialSaveVo vo = new PartialSaveVo();

        ProductVo product = new ProductVo();
        product.setId("9991");
        vo.setProductVo(product);
        vo.setComment(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        vo.setFileName(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        vo.setItemRevisionNumber(new Long("5"));
        vo.setRequestType(RequestType.ADDITION);
        vo.setModifiedBy(getTestUser().getUsername());
        vo.setModifiedDate(new Date());
        vo.setEntityType(EntityType.PRODUCT);
        

        return vo;
    }

    /**
     * 
     * for initial testing only hardcoded id will throw error when run a second time
     */
    public void testDeleteartialSaveMgt() {
        PartialSaveVo partialSave = buildPartialSaveMgt();
        partialSave = partialSaveMgtDomainCapability.create(partialSave, getTestUser());
        
        try {
            partialSaveMgtDomainCapability.delete(partialSave.getId());
        } catch (Exception e) {
            LOG.debug("Error occurred:  " + e.getMessage());
            fail("Should not have thrown exception");
        }
    }
}
