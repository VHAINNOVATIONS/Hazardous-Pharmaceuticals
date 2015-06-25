/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.OrderableItemGenerator;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderableItemDomainCapability;


/**
 * NationalOrderableItemDomainCapabilityTest
 */
public class NationalOrderableItemDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
        NationalOrderableItemDomainCapabilityTest.class);
    private static final String I9992 = "9992";
    
    private OrderableItemDomainCapability orderableItemDomainCapability;
    private OrderableItemVo testSampleVo;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.orderableItemDomainCapability = getNationalCapability(OrderableItemDomainCapability.class);

        OrderableItemVo orderableItem = new OrderableItemGenerator().getRandom();
        
        try {
            testSampleVo = orderableItemDomainCapability.create(orderableItem, getTestUser());
        } catch (DuplicateItemException e) {
            LOG.debug("Exception creating vo: " + e.getMessage());
        }

    }

    /**
     * testRetrieveByIdNational
     * 
     * 
     * @throws Exception Exception
     */
    public void testRetrieveByIdNational() throws Exception {

        OrderableItemVo testVo = orderableItemDomainCapability.retrieve(testSampleVo.getId());

        LOG.debug("TEST RETRIEVE OI:  " + testVo.toString());
        assertNotNull("Returned Item Result not returned ", testVo);
        assertEquals("Should be equal ", testSampleVo.getId(), testVo.getId());
        assertNotNull("OI should contain product(s) ", testVo.getProducts());
        assertFalse("there should be data fields ", testVo.getVaDataFields().isEmpty());
    }

    /**
     * testRetrieveByIdNational2
     * 
     * 
     * @throws Exception Exception
     */
    public void testRetrieveByIdNational2() throws Exception {
        String oiId = I9992;

        OrderableItemVo testVo = orderableItemDomainCapability.retrieve(oiId);

        LOG.debug(" TEST RETRIEVE OI: " + testVo.toString());
        assertNotNull("Returned Item Result not returned", testVo);
        assertEquals(" Should be equal", oiId, testVo.getId());
        assertNotNull("OI should contain product(s)", testVo.getProducts());
        assertFalse("there should be data fields", testVo.getVaDataFields().isEmpty());

    }

    /**
     * 
     * for initial testing only hard-coded id will throw error when run a second time
     * 
     * @throws Exception Exception
     */
    public void testAddOrderableItemnational() throws Exception {
        OrderableItemVo vo = new OrderableItemGenerator().getRandom();
        vo.setNational();
        vo.setOrderableItemParent(null);

        assertNull("Should be null because object is transient", vo.getId());
        OrderableItemVo newVO = orderableItemDomainCapability.create(vo, getTestUser());
        assertNotNull("Should not be null because object is no longer transient", newVO.getId());
    }

    /**
     * testUpdateOrderableItemnational
     * 
     * @throws Exception Exception
     */
    public void testUpdateOrderableItemnational() throws Exception {

        OrderableItemVo vo = orderableItemDomainCapability.retrieve(testSampleVo.getId());

        vo.setItemStatus(ItemStatus.INACTIVE);
        vo.setVistaOiName("NEW VISTA NAME");

        OrderableItemVo newVO = orderableItemDomainCapability.update(vo, getTestUser());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, vo.getVistaOiName(), newVO.getVistaOiName());
    }

    /**
     * testUpdateOrderableItemnationalActivationDate
     * 
     * @throws Exception Exception
     */
    public void testUpdateOrderableItemnationalActivationDate() throws Exception {

        OrderableItemVo vo = orderableItemDomainCapability.retrieve(testSampleVo.getId());

        // vo.setOrderableItemInactivationDate(new Date());
        vo.setRejectionReasonText("rejected");

        OrderableItemVo newVO = orderableItemDomainCapability.update(vo, getTestUser());

        // assertEquals("Should be equal", vo.getOrderableItemInactivationDate(), newVO.getOrderableItemInactivationDate());
        assertEquals("Should be equal", vo.getRejectionReasonText(), newVO.getRejectionReasonText());
    }

    /**
     * An update can be made to the uniqueness crtieria, so the update() method should check for duplicates.
     * 
     * @throws Exception if error
     */
    public void testDuplicateOiByUpdate() throws Exception {
        OrderableItemVo one = orderableItemDomainCapability.retrieve("9991");
        OrderableItemVo two = orderableItemDomainCapability.retrieve(I9992);

        two.setOiName(one.getOiName());

        try {
            orderableItemDomainCapability.update(two, getTestUser());
            fail("Should have thrown the DuplicateItemException");
        } catch (DuplicateItemException e) {
            assertNotNull("Should have thrown DuplicateItemException", e);
        }
    }
}
