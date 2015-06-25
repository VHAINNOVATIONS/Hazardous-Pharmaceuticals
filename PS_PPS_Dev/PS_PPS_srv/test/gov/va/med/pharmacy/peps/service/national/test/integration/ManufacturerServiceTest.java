/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


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
    private ManagedItemService nationalManagedItemService;

    /**
     * ManufacturerServiceTest
     * 
     * @param name Name of the manufacturer to test
     */
    public ManufacturerServiceTest(String name) {
        super(name);
    }

    /**
     * Setup the environment
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        localManagedItemService = getNationalService(ManagedItemService.class);
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
        ProcessedItemVo processItem = nationalManagedItemService.create(buildManufacturer(), buildUser());
        ManufacturerVo returnedClass = (ManufacturerVo) processItem.getItem();

        assertNotNull("Returned Null manufacturer", returnedClass);

        assertTrue("Created manufacturer not active", returnedClass.getItemStatus().isActive());
       
        Thread.sleep(new Long("14000"));

        ManufacturerVo classFromNational = (ManufacturerVo) localManagedItemService.retrieve(returnedClass.getId(),
            EntityType.MANUFACTURER);

        assertNotNull("Failed To Retrieve from Local", classFromNational);
    }

    /**
     * Attempts to create the same VO twice, expects to catch an exception
     * 
     * @throws Exception Exception
     */
    public void testCreateDuplicate() throws Exception {
        ManufacturerVo moddedClass = buildManufacturer();
        moddedClass.setValue("National2");

        nationalManagedItemService.create(moddedClass, buildUser());
        boolean throwsException = false;

        try {
            nationalManagedItemService.create(moddedClass, buildUser());
        } catch (DuplicateItemException e) {
            throwsException = true;
        }

        assertTrue("Failed to prevent insertion of duplicate item", throwsException);
    }

    /**
     * buildManufacturer Creates an arbitrary manufacturer for testing things
     * 
     * There is no significance to any of the values
     * 
     * @return ManufacturerVo
     */
    private ManufacturerVo buildManufacturer() {

        ManufacturerVo manufacturer = new ManufacturerVo();

        manufacturer.setValue("NationalManufacturer");
        manufacturer.setAddress("123 Fake Street");
        manufacturer.setPhone("(210)-867-5309");

        return manufacturer;
    }

    /**
     * Builds a user by using the UserGenerator method.
     * 
     * @return The build user.
     */
    private UserVo buildUser() {
        return new UserGenerator().getLocalManagerOne();
    }
}
