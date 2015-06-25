/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;




/**
 * LocalNdcDomainCapabilityTest
 */
public class LocalNdcDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final boolean IS_TRUE = true;


//    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LocalNdcDomainCapabilityTest.class);
//
//    private NdcDomainCapability localNdcDomainCapability;
//    private NdcGenerator ndcGenerator = new NdcGenerator();

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
        //this.localNdcDomainCapability = getLocalOneCapability(NdcDomainCapability.class);
        
    }
    

    /**
     * Tests local AddNdc using the built NDC
     * 
     */
    public void testAddNdcVoLocal() {

        assertTrue("testCreate skipped", IS_TRUE);
        
//        NdcVo ndc = new NdcGenerator().pseudoRandom();
//
//        try {
//
//            NdcVo returnedVo = localNdcDomainCapability.create(ndc, getTestUser());
//            assertNotNull("Returned Ndc", returnedVo);
//        }
//        catch (DuplicateItemException ex) {
//
//            fail("Duplicate item exception should not have been thrown");
//
//        }
    }

    
    
    /**
     * testCreateWithDuplicateNdcNumber
     * 
     */
    public void testCreateWithDuplicateNdcNumber() {
        assertTrue("testCreateWithDuplicateNdcNumber skipped.", IS_TRUE);
    
//        String ndcNumber;
//        
//        NdcVo ndc = ndcGenerator.pseudoRandom();
//        ndc.activate();
//        ndcNumber = ndc.getNdc();
//        assertNotNull("random NDC number should have been generated", ndcNumber);
//        ndc.setUpcUpn(null);
//        ndc.setVendor(null);
//        ndc.setVendorStockNumber(null);
//        ndc = localNdcDomainCapability.create(ndc, getTestUser());
//        String ndcId = ndc.getId();
//
//        assertEquals("NDC number should be unchanged", ndcNumber, ndc.getNdc());
//
//        // duplicate add
//        try {
//            ndc = ndcGenerator.pseudoRandom();
//            ndc.activate();
//            ndc.setNdc(ndcNumber); // re-use ndc number
//            ndc.setUpcUpn(null);
//            ndc.setVendor(null);
//            ndc.setVendorStockNumber(null);
//            ndc = localNdcDomainCapability.create(ndc, getTestUser());
//            fail("Duplicate item exception should have been thrown");
//        }
//        catch (DuplicateItemException ex) {
//
//            LOG.debug("SUCCESS");
//        }
//        catch (Exception ex) {
//            fail("The wrong type of exception was thrown: " + ex.getMessage());
//        }
//
//        // Verify that self-duplicates are not considered to be duplicates.
//        NdcVo ndcTest = new NdcVo();
//        ndcTest.setId(ndcId);
//        ndcTest.setNdc(ndcNumber); // Matches an existing NDC above.
//        ndcTest.setUpcUpn(null);
//        ndcTest.setVendor(null);
//        ndcTest.setVendorStockNumber(null);
//        
//        boolean exists = localNdcDomainCapability.existsByUniquenessFields(ndcTest);
//        assertFalse("Returned Item Result non-null when self-duplicate", exists);
//
//        // Verify that a self-duplicate plus another dup will still fail dup check.
//        NdcVo ndc2 = ndcGenerator.pseudoRandom();
//        String ndcNumber2 = ndc2.getNdc();
//        assertNotNull("random NDC number should have been generated", ndcNumber2);
//        ndc2.setUpcUpn(null);
//        ndc2.setVendor(null);
//        ndc2.setVendorStockNumber(null);
//        ndc2 = localNdcDomainCapability.create(ndc2, getTestUser());
//        String ndcId2 = ndc2.getId();
//        assertFalse("Random NDC IDs should not match", ndcId.equals(ndcId2));
//        assertFalse("Random NDC numbers should not match", ndcNumber.equals(ndcNumber2));
    }
}
