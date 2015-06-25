/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Test calls into the ManagedItemService using DoseUnitVo
 */
public class DoseUnitServiceTest extends IntegrationTestCase {
    private static final Logger LOG = Logger.getLogger(DoseUnitServiceTest.class);
    private static final String MG_ID = "99977";

 //   private ManagedItemService localManagedItemService;
    private ManagedItemService nationalManagedItemService;

    /**
     * Setup the environment
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.debug("--------------- " + getName() + " ---------------");

        //    this.localManagedItemService = getLocalOneService(ManagedItemService.class);
        this.nationalManagedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending
     * 
     * also verifies the request has been pushed to national
     * 
     * @throws Exception Exception
     */
    public void testCreate() throws Exception {
        DoseUnitVo doseUnit = (DoseUnitVo) nationalManagedItemService.retrieveTemplate(MG_ID, EntityType.DOSE_UNIT);
        makeUnique(doseUnit);

        ProcessedItemVo processItem = nationalManagedItemService.create(doseUnit, PNM1);
        DoseUnitVo returnedClass = (DoseUnitVo) processItem.getItem();

        assertNotNull("Returned Null dose unit", returnedClass);

        assertTrue("Created dose unit should be active", returnedClass.getItemStatus().isActive());
        assertTrue("Created dose unit should be pending", returnedClass.getRequestItemStatus().isPending());

        Thread.sleep(new Long("15000"));

        DoseUnitVo classFromLocal = (DoseUnitVo) nationalManagedItemService.retrieve(returnedClass.getId(),
            EntityType.DOSE_UNIT);

        assertNotNull("Failed To Retrieve from Local", classFromLocal);
    }

    /**
     * Attempts to create the same VO twice, expects to catch an exception
     * 
     * @throws Exception Exception
     */
    public void testCreateDuplicate() throws Exception {
        DoseUnitVo doseUnit = (DoseUnitVo) nationalManagedItemService.retrieveTemplate(MG_ID, EntityType.DOSE_UNIT);
        makeUnique(doseUnit);

        nationalManagedItemService.create(doseUnit, PNM1);

        try {
            nationalManagedItemService.create(doseUnit, PNM1);
            fail("Should have thrown DuplicateItemException!");
        } catch (DuplicateItemException e) {
            assertNotNull("Should have thrown DuplicateItemException", e);
        }
    }
}
