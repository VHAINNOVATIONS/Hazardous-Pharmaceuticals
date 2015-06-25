/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * ManufacturerServiceTest
 */
public class ManufacturerServiceTest extends IntegrationTestCase {

    private ManagedItemService localManagedItemService;
 
    //  private ManagedItemService nationalManagedItemService;

    /**
     * ManufacturerServiceTest
     * 
     * @param name Name
     */
    public ManufacturerServiceTest(String name) {
        super(name);
    }

    /**
     * Setup the environment
     * 
     * @throws Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()  {
        localManagedItemService = getNationalService(ManagedItemService.class);
      
        //nationalManagedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending
     * 
     * also verifies the request has been pushed to national
     * 
     * @throws Exception Exception
     */
    public void testCreate() throws Exception {
        ProcessedItemVo processedItem = localManagedItemService.create(buildManufacturer(), buildUser());
        ManufacturerVo returnedClass = (ManufacturerVo) processedItem.getItem();

        assertNotNull("Returned Null manufacturer", returnedClass);

        assertTrue("Created manufacturer not active", returnedClass.getItemStatus().isActive());
        assertTrue("Created manufacturer not pending", returnedClass.getRequestItemStatus().isPending());

        Thread.sleep(new Long("15000"));

        ManufacturerVo classFromNational = (ManufacturerVo) localManagedItemService.retrieve(returnedClass.getId(),
            EntityType.MANUFACTURER);

        assertNotNull("Failed To Retrieve ", classFromNational);
    }
    
    /**
     * This method tests the retrieve of a Manufacturer
     * 
     * @throws Exception Exception
     */
    public void testRetrieve() throws Exception {

        ManufacturerVo first = (ManufacturerVo) localManagedItemService.retrieve("9991", EntityType.MANUFACTURER);

        first.setPhone("5555");
        localManagedItemService.update(first, buildUser());

        assertNotNull("Failed to retrieve", first);
    }

    /**
     * Attempts to create the same VO twice, expects to catch an exception
     * 
     * @throws Exception Exception
     */
    public void testCreateDuplicate() throws Exception {
        ManufacturerVo moddedClass = buildManufacturer();
        moddedClass.setValue("LocalManu2");

        localManagedItemService.create(moddedClass, buildUser());

        boolean throwsException = false;

        try {
            localManagedItemService.create(moddedClass, buildUser());
        } catch (DuplicateItemException e) {
            throwsException = true;
        }

        assertTrue("Failed to prevent insertion of duplicate item", throwsException);

    }

    /**
     * Creates an arbitrary manufacturer for testing things
     * 
     * There is no significance to any of the values
     * 
     * @return ManufacturerVo
     */
    private ManufacturerVo buildManufacturer() {

        ManufacturerVo manufacturer = new ManufacturerVo();

        manufacturer.setValue("LocalManu");
        manufacturer.setAddress("123 Fake Street");
        manufacturer.setPhone("(210)-867-5309");

        return manufacturer;
    }

    /**
     * Makes a user
     * 
     * @return UserVo
     */
    private UserVo buildUser() {
        return new UserGenerator().getLocalManagerOne();
    }
}
