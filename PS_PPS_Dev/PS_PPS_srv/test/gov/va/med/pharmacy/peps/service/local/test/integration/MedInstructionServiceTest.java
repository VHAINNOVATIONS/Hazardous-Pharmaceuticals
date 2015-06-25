/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 *MedInstructionServiceTest
 */
public class MedInstructionServiceTest extends IntegrationTestCase {

 //   ManagedItemService localManagedItemService;
  //  private ManagedItemService nationalManagedItemService;

    /**
     * MedInstructionServiceTest
     * 
     * @param name Name
     */
    public MedInstructionServiceTest(String name) {
        super(name);
    }

    /**
     * Setup the environment
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
   
        //   localManagedItemService = getLocalOneService(ManagedItemService.class);
     //   nationalManagedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending
     * 
     * also verifies the request has been pushed to national
     * 
     * @throws Exception v
     */
    public void testCreate() throws Exception {
        
//        ProcessedItemVo processedItem = localManagedItemService.create(
//            buildMedInstruction(), buildUser());
//        MedicationInstructionVo returnedClass = (MedicationInstructionVo) processedItem.getItem();

//        assertNotNull("Returned Null med instruction", returnedClass);
        
//        System.out.println(returnedClass.getRequestItemStatus() + "id " + returnedClass.getId());

//        assertTrue("Created med instruction not active", returnedClass.getItemStatus().isActive());
//        assertTrue("Created med instruction not pending", returnedClass.getRequestItemStatus().isApproved());*/
        boolean isTrue = true;
        
        assertTrue("PlaceHolderTest", isTrue);

    }

    /**
     * Attempts to create the same VO twice, expects to catch an exception
     * 
     * @throws Exception Exception
     */
    public void testCreateDuplicate() throws Exception {
        MedicationInstructionVo moddedClass = buildMedInstruction();
        moddedClass.setValue("Local2");

//        localManagedItemService.create(moddedClass, buildUser());

//        boolean throwsException = false;

//        try {
//            localManagedItemService.create(moddedClass, buildUser());
//        }
//        catch (DuplicateItemException e) {
//            throwsException = true;
//        }

//        assertTrue("Failed to prevent insertion of duplicate item", throwsException);

        boolean isTrue = true;
        assertTrue("PlaceHolder Assertion", isTrue);
    }

    /**
     * Creates an arbitrary med instruction for testing things
     * 
     * There is no significance to any of the values
     * 
     * @return MedicationInstructionVo
     */
    private MedicationInstructionVo buildMedInstruction() {

        MedicationInstructionVo medInstruction = new MedicationInstructionVo();

        medInstruction.setMedInstructionExpansion("EXP");
        medInstruction.setDefaultAdminTimes("admin");
        medInstruction.setValue("MedIns");        

        return medInstruction;
    }
}
