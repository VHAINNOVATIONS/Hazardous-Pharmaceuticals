/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * DrugClassServiceTest
 */
public class DrugClassServiceTest extends IntegrationTestCase {

 //   ManagedItemService localManagedItemService;
    private ManagedItemService nationalManagedItemService;

    /**
     * DrugClassServiceTest
     * 
     * @param name Name
     */
    public DrugClassServiceTest(String name) {
        super(name);
    }

    /**
     * Setup the environment
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        
    //    localManagedItemService = getLocalOneService(ManagedItemService.class);
        nationalManagedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending
     * 
     * also verifies the request has been pushed to national
     * 
     * @throws Exception Exception
     */
    public void testCreate() throws Exception {
        ProcessedItemVo processItem = nationalManagedItemService.create(buildDrugClass(), buildUser());
        DrugClassVo returnedClass = (DrugClassVo) processItem.getItem();

        assertNotNull("Returned Null Drug Class", returnedClass);

        assertTrue("Created Drug class not active", returnedClass.getItemStatus().isActive());
        assertTrue("Created Drug Class not pending second approval", returnedClass.getRequestItemStatus().isPending());
        
        Thread.sleep(new Long("15000"));

        
        DrugClassVo classFromLocal = (DrugClassVo) nationalManagedItemService.retrieve(returnedClass.getId(),
            EntityType.DRUG_CLASS);
        
        assertNotNull("Failed To Retrieve from Local", classFromLocal);
    }

    /**
     * Attempts to create the same VO twice, expects to catch an exception
     * 
     * @throws Exception Exception
     */
    public void testCreateDuplicate() throws Exception {
        DrugClassVo moddedClass = buildDrugClass();
        moddedClass.setCode("TN124");
       
        nationalManagedItemService.create(moddedClass, buildUser());
        boolean throwsException = false;

        // create the service for the testCreateDuplicate test
        try {
            nationalManagedItemService.create(moddedClass, buildUser());
        } catch (DuplicateItemException e) {
            throwsException = true;
        }
        
        // assert if an exception was thrown.
        assertTrue("Failed to prevent insertion of duplicate item", throwsException);
         
    }
    
    /**
     * The buildDrugClass method creates an arbitrary drug class for testing things
     * 
     * There is no significance to any of the values
     * 
     * @return drugClassVo
     */
    private DrugClassVo buildDrugClass() {
        DrugClassVo drugClass = new DrugClassVo();
        drugClass.setCode("TN123");
        drugClass.setClassification("TEST NATIONAL CLASSIFICATION");

        DrugClassificationTypeVo classType = new DrugClassificationTypeVo();
        classType.setId("2");
        classType.setValue("1-Minor");

        drugClass.setClassificationType(classType);

        return drugClass;
    }

    /**
     * Makes a user for the DrugClassServiceTest
     * 
     * @return UserVo
     */
    private UserVo buildUser() {
        return new UserGenerator().getLocalManagerOne();
    }
}
