/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import java.util.Collection;
import java.util.Date;

import gov.va.med.pharmacy.peps.common.exception.AuthorizationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 *  National user role check, primarily for testing the service layer backup checks to prevent users from performing actions 
 *  they are not allowed to. The presentation layer should prevent this code from being accessed in the usual runtime of the 
 *  application  
 */
public class NationalUserRoleTest extends IntegrationTestCase {

    private ManagedItemService nationalManagedItemService;
    private UserVo readOnly;
    private UserVo nationalAdmin;
    private UserVo nationalManager;
    private UserVo secondReviewer;

    private String nullException = "Null exception thrown";
    private String readOnlyCreateFail = "Read Only User did not throw an exception when attempting to create";
    private String readOnlyModifyFail = "Read Only User did not throw an exception when attempting to modify";
    private String adminCreateFail = "National Administrator did not throw an exception when attempting to create";
    private String adminModifyFail = "National Administrator did not throw an exception when attempting to modify";
    private String psrCreateFail = "National PSR did not throw an exception when attempting to create a national only domain";
    private String psrModifyFail = "National PSR did not throw an exception when attempting to modify";

    /**
     * Description
     * 
     * @param name Name
     */
    public NationalUserRoleTest(String name) {
        super(name);

        createUsers();
    }

    /**
     * Setup the environment
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {

        // Service/build/meta/properties/
        // gov/va/med/pharmacy/peps/service/common/test/integration/IntegrationTestCase.properties
        // setOutOfContainer(true); ie "out.of.container=true"
        nationalManagedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Validates the creation of national domains is prevented for Read Only, Admin, and PSR users
     * 
     * @throws Exception e
     */
    public void testCreateNationalDomain() throws Exception {

        DrugClassVo tester = new DrugClassVo();

        tester.setClassification(new Date().toString());
        tester.setCode("TS" + System.currentTimeMillis() % PPSConstants.I1000);
        DrugClassificationTypeVo type = new DrugClassificationTypeVo();
        type.setId("1");
        type.setValue("Fake1");
        tester.setClassificationType(type);

        try {
            nationalManagedItemService.create(tester, readOnly);
            fail(readOnlyCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        try {
            nationalManagedItemService.create(tester, nationalAdmin);

            fail(adminCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        try {
            nationalManagedItemService.create(tester, secondReviewer);

            fail(psrCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

    }

    /**
     * Validates the modification of national domains is prevented for Read Only, Admin and PSR users
     * 
     * @throws Exception e
     */
    public void testUpdateNationalDomain() throws Exception {

        DrugClassVo tester = new DrugClassVo();

        tester.setClassification(new Date().toString());
        tester.setCode("TS" + System.currentTimeMillis() % PPSConstants.I1000);
        DrugClassificationTypeVo type = new DrugClassificationTypeVo();
        type.setId("1");
        type.setValue("Fake2");
        tester.setClassificationType(type);

        DrugClassVo tester2 = new DrugClassVo();

        tester2.setClassification(tester.getClassification());
        tester2.setCode(tester.getCode());
        tester2.setClassificationType(type);
        tester2.setDescription("Dummy");

        Collection<ModDifferenceVo> mods = tester.compareDifferences(tester2);

        try {
            nationalManagedItemService.commitModifications(mods, tester2, readOnly);
            fail(readOnlyModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        try {
            nationalManagedItemService.commitModifications(mods, tester2, nationalAdmin);
            fail(adminModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        try {
            nationalManagedItemService.commitModifications(mods, tester2, secondReviewer);
            fail(psrModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

    }

    /**
     * Validates the creation of general domains is prevented for Read Only, Admin and PSR users
     * 
     * @throws Exception e
     */
    public void testCreateGeneralDomain() throws Exception {

        ManufacturerVo tester = new ManufacturerVo();

        tester.setValue(new Date().toString());

        // create the nationalManagedItemService as ReadOnly for testCreateGeneralDomain.
        try {
            nationalManagedItemService.create(tester, readOnly);
            fail(readOnlyCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testCreateGeneralDomain.
        try {
            nationalManagedItemService.create(tester, nationalAdmin);

            fail(adminCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testCreateGeneralDomain.
        try {
            nationalManagedItemService.create(tester, secondReviewer);
            fail(psrCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

    }

    /**
     * Validates the modification of general domains is prevented for Read Only, Admin and PSR users
     * for the NationalRoleUserTest
     * 
     * @throws Exception e
     */
    public void testUpdateGeneralDomain() throws Exception {

        ManufacturerVo tester = new ManufacturerVo();

        tester.setValue(new Date().toString());

        ManufacturerVo tester2 = new ManufacturerVo();

        tester.setValue(new Date().toString() + "z");

        Collection<ModDifferenceVo> mods = tester.compareDifferences(tester2);

        // create the nationalManagedItemService as readOnly for testUpdateGeneralDomain.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, readOnly);
            fail(readOnlyModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testUpdateGeneralDomain.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, nationalAdmin);
            fail(adminModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testUpdateGeneralDomain.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, secondReviewer);
            fail(psrModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

    }

    /**
     * Validates the creation of NDCs is prevented for Read Only, Admin and PSR users
     * 
     * @throws Exception e
     */
    public void testCreateNDC() throws Exception {

        NdcVo tester = new NdcVo();

        tester.setNdc(new Date().toString());

        // create the nationalManagedItemService as readOnly for testCreateNDC.
        try {
            nationalManagedItemService.create(tester, readOnly);
            fail(readOnlyCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testCreateNDC.
        try {
            nationalManagedItemService.create(tester, nationalAdmin);

            fail(adminCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testCreateNDC.
        try {
            nationalManagedItemService.create(tester, secondReviewer);
            fail(psrCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
    }

    /**
     * Validates the modification of NDCs is prevented for Read Only, Admin and PSR users for the 
     * NationalUserRoleTest.
     * 
     * @throws Exception 
     */
    public void testUpdateNDC() throws Exception {

        NdcVo tester = new NdcVo();

        tester.setNdc(new Date().toString());

        NdcVo tester2 = new NdcVo();

        tester2.setNdc(new Date().toString() + "z");

        Collection<ModDifferenceVo> mods = tester.compareDifferences(tester2);

        // create the nationalManagedItemService as readOnly for testUpdateNDC.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, readOnly);
            fail(readOnlyModifyFail);
        } catch (AuthorizationException e) {

            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testUpdateNDC.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, nationalAdmin);
            fail(adminModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testUpdateNDC.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, secondReviewer);
            fail(psrModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

    }

    /**
     * Validates the creation of Products is prevented for Read Only, Admin and PSR users
     * 
     * @throws Exception e
     */
    public void testCreateProduct() throws Exception {

        ProductVo tester = new ProductVo();

        tester.setVaProductName(new Date().toString());

        // create the nationalManagedItemService as readOnly for testCreateProduct.
        try {
            nationalManagedItemService.create(tester, readOnly);
            fail(readOnlyCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testCreateProduct.
        try {
            nationalManagedItemService.create(tester, nationalAdmin);

            fail(adminCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testCreateProduct.
        try {
            nationalManagedItemService.create(tester, secondReviewer);

            fail(psrCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
    }

    /**
     * testUpdateProduct for the nationalUserRoleTest.
     * 
     * Validates the modification of NDCs is prevented for Read Only, Admin and PSR users
     * 
     * 
     * @throws Exception e 
     */
    public void testUpdateProduct() throws Exception {

        ProductVo tester = new ProductVo();

        tester.setVaProductName(new Date().toString());

        ProductVo tester2 = new ProductVo();

        tester2.setVaProductName(new Date().toString() + "z");

        Collection<ModDifferenceVo> mods = tester.compareDifferences(tester2);

        // create the nationalManagedItemService as readOnly for testUpdateProduct.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, readOnly);
            fail(readOnlyModifyFail);
        } catch (AuthorizationException e) {

            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testUpdateProduct.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, nationalAdmin);
            fail(adminModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testUpdateProduct.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, secondReviewer);
            fail(psrModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

    }

    /**
     * Validates the creation of OIs is prevented for Read Only, Admin and PSR users
     * 
     * @throws Exception e
     */
    public void testCreateOI() throws Exception {

        OrderableItemVo tester = new OrderableItemVo();

        tester.setOiName(new Date().toString());

        // create the nationalManagedItemService as readOnly for testCreateOI.
        try {
            nationalManagedItemService.create(tester, readOnly);
            fail(readOnlyCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testCreateOI.
        try {
            nationalManagedItemService.create(tester, nationalAdmin);

            fail(adminCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testCreateOI.
        try {
            nationalManagedItemService.create(tester, secondReviewer);

            fail(psrCreateFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }


    }

    /**
     * Validates the modification of OIs is prevented for Read Only, Admin and PSR users
     * 
     * @throws Exception e
     */
    public void testUpdateOI() throws Exception {

        OrderableItemVo tester = new OrderableItemVo();

        tester.setOiName(new Date().toString());

        OrderableItemVo tester2 = new OrderableItemVo();

        tester2.setOiName(new Date().toString() + "z");

        Collection<ModDifferenceVo> mods = tester.compareDifferences(tester2);

        // create the nationalManagedItemService as readOnly for testUpdateOI.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, readOnly);
            fail(readOnlyModifyFail);
        } catch (AuthorizationException e) {

            assertNotNull(nullException, e);
        }

        // create the nationalManagedItemService as nationalAdmin for testUpdateOI.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, nationalAdmin);
            fail(adminModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }
        
        // create the nationalManagedItemService as secondReviewer for testUpdateOI.
        try {
            nationalManagedItemService.commitModifications(mods, tester2, secondReviewer);
            fail(psrModifyFail);
        } catch (AuthorizationException e) {
            assertNotNull(nullException, e);
        }

    }
    
    /**
     * Sets up the users as you would expect from the names. Each user has only one role
     */
    private void createUsers() {
        readOnly = new UserVo();
        nationalAdmin = new UserVo();
        nationalManager = new UserVo();
        secondReviewer = new UserVo();

        readOnly.addRole(Role.PSS_PPSN_VIEWER /*NATIONAL_READ_ONLY*/);

        nationalAdmin.addRole(Role.PSS_PPSN_SUPERVISOR /*NATIONAL_ADMINISTRATIVE_MANAGER*/);

        nationalManager.addRole(Role.PSS_PPSN_MANAGER /*NATIONAL_SERVICE_MANAGER*/);

        secondReviewer.addRole(Role.PSS_PPSN_SECOND_APPROVER /*SECOND_REVIEWER*/);
    }
}
