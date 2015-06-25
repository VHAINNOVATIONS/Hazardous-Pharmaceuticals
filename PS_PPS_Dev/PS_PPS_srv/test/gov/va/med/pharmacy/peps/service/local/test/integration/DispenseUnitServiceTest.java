/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * DispenseUnitService Tests
 */
public class DispenseUnitServiceTest extends IntegrationTestCase {
  
    //private static final String TAB_ID = "9991";

    //private ManagedItemService localManagedItemService;
    //private ManagedItemService nationalManagedItemService;
    private boolean isTrue;

    /**
     * Setup the environment
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        //System.out.println("--------------- " + getName() + " ---------------");
        //this.localManagedItemService = getNationalService(ManagedItemService.class);
        //this.nationalManagedItemService = getNationalService(ManagedItemService.class);
        isTrue = true;
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending for the dispenseUnitServiceTest.
     * 
     * also verifies the request has been pushed to national
     * 
     * @throws Exception Exception
     */
    public void testCreate() throws Exception {
        assertTrue("Skipped test until locals come out.", isTrue);
        
//        DispenseUnitVo dispenseUnit = (DispenseUnitVo) localManagedItemService.retrieveTemplate(TAB_ID,
//            EntityType.DISPENSE_UNIT);
//        makeUnique(dispenseUnit);
//
//        DispenseUnitVo returnedClass = (DispenseUnitVo) localManagedItemService.create(dispenseUnit, PLM1).getItem();
//
//        assertNotNull("Returned Null dispense unit", returnedClass);
//
//        assertTrue("Created dispense unit should be active", returnedClass.getItemStatus().isActive());
//        assertTrue("Created dispense unit should be pending", returnedClass.getRequestItemStatus().isPending());
//
//        Thread.sleep(20000);
//
//        DispenseUnitVo classFromNational = (DispenseUnitVo) nationalManagedItemService.retrieve(returnedClass.getId(),
//            EntityType.DISPENSE_UNIT);
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

//        DispenseUnitVo dispenseUnit = (DispenseUnitVo) localManagedItemService.retrieveTemplate(TAB_ID,
//            EntityType.DISPENSE_UNIT);
//        makeUnique(dispenseUnit);
//
//        localManagedItemService.create(dispenseUnit, PLM1);
//
//        try {
//            localManagedItemService.create(dispenseUnit, PLM1);
//            fail("Should have thrown DuplicateItemException");
//        }
//        catch (DuplicateItemException e) {
//            assertNotNull("Should have thrown DuplicateItemException", e);
//        }
    }
}
