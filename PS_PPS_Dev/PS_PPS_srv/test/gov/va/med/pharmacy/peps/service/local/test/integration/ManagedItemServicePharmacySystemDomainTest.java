/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * This is to perform integration test on ManagedItem for the Pharmacy System domain.
 */
public class ManagedItemServicePharmacySystemDomainTest extends IntegrationTestCase {

    private ManagedItemService localManagedItemService;

    /**
     * ManagedItemServicePharmacySystemDomainTest
     * 
     * @param name Name
     */
    public ManagedItemServicePharmacySystemDomainTest(String name) {
        super(name);
    }

    /**
     * Setup the environment
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        // Service/build/meta/properties/
        // gov/va/med/pharmacy/peps/service/common/test/integration/IntegrationTestCase.properties
        // setOutOfContainer(true); ie "out.of.container=true"
        localManagedItemService = getNationalService(ManagedItemService.class);
    }
 

    /**
     * test the Pharmacy System validator by giving it a known bad value This test the required name field and one other
     * field length
     * 
     * @throws Exception Exception
     */
    public void testPharmacySystemValidator() throws Exception {

        final String expectedExceptionMessagePortionForName = 
            "The Pharmacy System field can have minimum 2 and maximum 40 characters.";
        final String expectedExceptionMessagePortionForAnd = "The And field can have minimum 1 and maximum 15 characters.";
        String testSiteName = "Snoopy";
        String badSiteName = "X";
        String testPrinter = "Beetle's printer";
        String testPsAnd = "And";
        String badPsAnd = "123456789012345X"; // one char too long
        UserVo testUser = buildUser();

        // grab the initial number of Pharmacy System domain entries
        List<ManagedItemVo> pharmacySystemInitialList = new ArrayList<ManagedItemVo>();
        pharmacySystemInitialList = localManagedItemService.retrieve(EntityType.PHARMACY_SYSTEM);
        int initialListSize = pharmacySystemInitialList.size();

        // create a vo with a null name field - it should throw an exception
        String exceptionCatch1 = null;
        PharmacySystemVo testVo1 = buildPharmacySystemVoForTesting(null, testPsAnd, testPrinter);
        PharmacySystemVo createdVo = null;
        int messageIndex = PPSConstants.IMINUS2;

        try {
            ProcessedItemVo processItem = localManagedItemService.create(testVo1, testUser);
            createdVo = (PharmacySystemVo) processItem.getItem();

        } catch (Exception ex) {
            exceptionCatch1 = ex.getMessage();
        }

        assertNull("The create should have failed! ", createdVo);
        assertNotNull(" The Pharmacy System domain create with a null name value should have failed!", exceptionCatch1);
        messageIndex = exceptionCatch1.indexOf(expectedExceptionMessagePortionForName);
        assertTrue("The exception message should have had this error message portion >"
            + expectedExceptionMessagePortionForName, -1 < messageIndex);

        // create a vo with a short name field - it should throw an exception
        String exceptionCatch2 = null;
        PharmacySystemVo testVo2 = buildPharmacySystemVoForTesting(badSiteName, testPsAnd, testPrinter);
        PharmacySystemVo createdVo2 = null;
        int messageIndex2 = PPSConstants.IMINUS2;

        try {
            ProcessedItemVo processItem1 = localManagedItemService.create(testVo2, testUser);
            createdVo2 = (PharmacySystemVo) processItem1.getItem() ;

        } catch (Exception ex) {
            exceptionCatch2 = ex.getMessage();
        }

        assertNull("  The create should have failed!", createdVo2);
        assertNotNull("The Pharmacy System domain create with a short name value should have failed!", exceptionCatch2);
        messageIndex2 = exceptionCatch2.indexOf(expectedExceptionMessagePortionForName);
        assertTrue("  The exception message should have had this error message portion>"
            + expectedExceptionMessagePortionForName, -1 < messageIndex2);

        // create a vo with a long field - it should throw an exception
        String exceptionCatch3 = null;
        PharmacySystemVo testVo3 = buildPharmacySystemVoForTesting(testSiteName, badPsAnd, testPrinter);
        PharmacySystemVo createdVo3 = null;
        int messageIndex3 = PPSConstants.IMINUS2;

        try {
            ProcessedItemVo processItem3 = localManagedItemService.create(testVo3, testUser);
            createdVo3 = (PharmacySystemVo) processItem3.getItem();

        } catch (Exception ex) {
            exceptionCatch3 = ex.getMessage();
        }

        assertNull("The create should have failed!", createdVo3);
        assertNotNull("The Pharmacy System domain create with a null name value should have failed!", exceptionCatch3);
        messageIndex3 = exceptionCatch3.indexOf(expectedExceptionMessagePortionForAnd);
        assertTrue("The exception message should have had this error message portion>"
            + expectedExceptionMessagePortionForAnd, -1 < messageIndex3);

        // grab the final number of Pharmacy System domain entries - should be initial, ie no new ones
        List<ManagedItemVo> pharmacySystemFinalList = new ArrayList<ManagedItemVo>();
        pharmacySystemFinalList = localManagedItemService.retrieve(EntityType.PHARMACY_SYSTEM);
        int finalListSize = pharmacySystemFinalList.size();

        // compare the number of Pharmacy System domain entries before/after - should have gained none
        assertEquals("It should have added no Pharmacy System domain entries due to errors", initialListSize, finalListSize);
    }

    /**
     * Makes a new Pharmacy System Vo for testing
     * 
     * @param siteName name in the value field
     * @param psAnd value for the And field
     * @param pmisPrinter value for the printer field
     * @return a new Pharmacy System Vo for testing
     */
    private PharmacySystemVo buildPharmacySystemVoForTesting(String siteName, String psAnd, String pmisPrinter) {

        PharmacySystemVo newVo = new PharmacySystemVo();
        newVo.setValue(siteName);
        newVo.setPsPmisPrinter(pmisPrinter);
        newVo.setPsAnd(psAnd);

        return newVo;
    }

    // /**
    // * testing helper method
    // *
    // * @param vo the vo to be printed
    // */
    // private void printIt(Object vo) {
    // Date now = new Date();
    //        
    // if (vo instanceof PharmacySystemVo) {
    // System.out.println("PharmacySystemVo " + now + " Begin HERE>\n" + vo + "\n<End HERE PharmacySystemVo " + now);
    // }
    // else {
    // System.out.println("Object " + now + " Begin HERE>\n" + vo + "\n<End HERE Object " + now);
    //            
    // }
    // }

    /**
     * Makes a user
     * 
     * @return UserVo
     */
    private UserVo buildUser() {
        return new UserGenerator().getLocalManagerOne();
    }

}
