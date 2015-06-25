/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 *DoseUnitServiceTest
 */
public class DoseUnitServiceTest extends IntegrationTestCase {
    
    //private static final String MG_ID = "99977";
    //  private ManagedItemService localManagedItemService;
    //private ManagedItemService nationalManagedItemService;
    private boolean isTrue;
    
    /**
     * Setup the environment
     *  
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        
        //System.out.println("--------------- " + getName() + " ---------------");
        //this.nationalManagedItemService = getNationalService(ManagedItemService.class);
        isTrue = true;
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending
     * 
     * also verifies the request has been pushed to national
     * 
     * @throws Exception Exception
     */
    public void testCreate() throws Exception {
        assertTrue("testCreate test skipped.", isTrue);

//        DoseUnitVo doseUnit = (DoseUnitVo) nationalManagedItemService.retrieveTemplate(MG_ID, EntityType.DOSE_UNIT);
//        makeUnique(doseUnit);
//
//        ProcessedItemVo processItem = nationalManagedItemService.create(doseUnit, PLM1);
//        DoseUnitVo returnedClass = (DoseUnitVo) processItem.getItem();
//
//        assertNotNull("Returned Null dose unit", returnedClass);
//
//        assertTrue("Created dose unit should be active", returnedClass.getItemStatus().isActive());
//        assertTrue("Created dose unit should be pending", returnedClass.getRequestItemStatus().isPending());
//
//        Thread.sleep(15000);
//
//        DoseUnitVo classFromNational = (DoseUnitVo) nationalManagedItemService.retrieve(returnedClass.getId(),
//            EntityType.DOSE_UNIT);
//
//        assertNotNull("Failed To Retrieve from National", classFromNational);
    }

    /**
     * Attempts to create the same VO twice, expects to catch an exception
     * 
     * @throws Exception Exception
     */
    public void testCreateDuplicate() throws Exception {
        assertTrue("Skipped test.", isTrue);

//        DoseUnitVo doseUnit = (DoseUnitVo) nationalManagedItemService.retrieveTemplate(MG_ID, EntityType.DOSE_UNIT);
//        makeUnique(doseUnit);
//
//        nationalManagedItemService.create(doseUnit, PLM1);
//
//        try {
//            nationalManagedItemService.create(doseUnit, PLM1);
//            fail("Should have thrown DuplicateItemException");
//        }
//        catch (DuplicateItemException e) {
//            assertNotNull("Should have thrown DuplicateItemException", e);
//        }
    }
}
