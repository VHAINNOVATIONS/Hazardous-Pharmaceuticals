/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Test calls into ManagedItemService using DispenseUnitVo
 */
public class DispenseUnitServiceTest extends IntegrationTestCase {
    private static final Logger LOG = Logger.getLogger(DispenseUnitServiceTest.class);
    private static final String TAB_ID = "9991";
    private ManagedItemService nationalManagedItemService;

    /**
     * Setup the environment for the DispenseUnitServiceTest
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.debug("--------------- " + getName() + " ---------------");
        
      //  this.localManagedItemService = getLocalOneService(ManagedItemService.class);
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
        DispenseUnitVo dispenseUnit = (DispenseUnitVo) nationalManagedItemService.retrieveTemplate(TAB_ID,
            EntityType.DISPENSE_UNIT);
        makeUnique(dispenseUnit);

        DispenseUnitVo returnedClass = (DispenseUnitVo) nationalManagedItemService.create(dispenseUnit, PNM1).getItem();

        assertNotNull("Returned Null dispense unit", returnedClass);

        assertTrue("Created dispense unit should be active", returnedClass.getItemStatus().isActive());
        assertTrue("Created dispense unit should be pending", returnedClass.getRequestItemStatus().isPending());

        Thread.sleep(PPSConstants.I10000);

        DispenseUnitVo classFromLocal = (DispenseUnitVo) nationalManagedItemService.retrieve(returnedClass.getId(),
            EntityType.DISPENSE_UNIT);

        assertNotNull("Failed To Retrieve from Local", classFromLocal);
    }

    /**
     * Attempts to create the same VO twice, expects to catch an exception
     * 
     * @throws Exception Exception
     */
    public void testCreateDuplicate() throws Exception {
        DispenseUnitVo dispenseUnit = (DispenseUnitVo) nationalManagedItemService.retrieveTemplate(TAB_ID,
            EntityType.DISPENSE_UNIT);
        makeUnique(dispenseUnit);

        nationalManagedItemService.create(dispenseUnit, PNM1);

        try {
            nationalManagedItemService.create(dispenseUnit, PNM1);
            fail("Should have thrown DuplicateItemException!");
        } catch (DuplicateItemException e) {
            assertNotNull("Should have thrown DuplicateItemException", e);
        }
    }
}
