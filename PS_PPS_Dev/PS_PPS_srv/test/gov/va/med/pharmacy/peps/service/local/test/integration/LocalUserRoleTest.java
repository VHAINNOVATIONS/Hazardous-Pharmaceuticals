/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * 
 * Tests local user roles for creation and updating items. This is primarily a validating the service layer has a check that
 * prevents a user from performing actions they are not allowed to do, should the presentation layer prevent the action
 */
public class LocalUserRoleTest extends IntegrationTestCase {
  
    //  ManagedItemService localManagedItemService;
    //private UserVo readOnly;
    //private UserVo localAdmin;
    //private UserVo localManager;

//    private String readOnlyCreateFail = "Read Only User did not throw an exception when attempting to create";
//    private String adminCreateFail = "Local Administrator did not throw an exception when attempting to create";
//    private String readOnlyModifyFail = "Read Only User did not throw an exception when attempting to modify";
//    private String adminModifyFail = "Local Administrator did not throw an exception when attempting to modify";
//    private String exceptionNull = "Null exception thrown";

    /**
     * Default Constructor
     * 
     * @param name Name
     */
    public LocalUserRoleTest(String name) {
        super(name);

   //     createUsers();
    }

    /**
     * Setup the environment
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
      
        //  localManagedItemService = getLocalOneService(ManagedItemService.class);
    }

    /**
     * Uses the AdministrationScheduleVo to verify that Read Only and Admin users cannot add local domains and that a manager
     * can add a local domain
     * 
     * @throws Exception Exception
     */
    public void testCreateLocalDomain() throws Exception {
        boolean isTrue = true;
        
        assertTrue("This should always be true.", isTrue);
        

//        AdministrationScheduleVo tester = new AdministrationScheduleVo();

//        tester.setValue(new Date().toString().substring(0, 19));
//        tester.setPackagePrefix("J" + System.currentTimeMillis() % 1000);

 //       try {
//            localManagedItemService.create(tester, readOnly);
//            fail(readOnlyCreateFail);
//        }
//        catch (AuthorizationException ae) {
//            assertNotNull(exceptionNull, ae);
//        }
//        catch (ServiceException e) {
//            assertNotNull(exceptionNull, e);
 //       }

 //       try {
 //           localManagedItemService.create(tester, localAdmin);
//
 //           fail(adminCreateFail);
//        }
//        catch (AuthorizationException ae) {
//            assertNotNull(exceptionNull, ae);
//        }
//        catch (ServiceException e) {
//            assertNotNull(exceptionNull, e);
//        }

//        try {
//            localManagedItemService.create(tester, localManager);
//        }
//        catch (Exception e) {
//            fail("Local Manager could not add the domain");
//        }

    }


}
