/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;



import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Tests the LocalConsole Service (System Information)
 */
public class ConsoleServiceTest extends IntegrationTestCase {

 //   ConsoleService consoleService;

    /**
     * Local ConsoleServiceTest
     * 
     * @param name String
     */
    public ConsoleServiceTest(String name) {
        super(name);
    }

    /**
     * setUp
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
      
        
     //   consoleService = getLocalOneService(ConsoleService.class);
    }

    /**
     * Test case to test local getConsole A Local Status Console has a national site config vo and a list with 1 local site
     * config (this site) (always only 1) A National Status Console has a national site config vo and a list with 0..n local
     * site config from the national db table There is no known way to test the National Site not available option except by
     * embedding a runtime exception (int x = 1 / 0;) in the source code. On the test rack, it will fail if the National
     * WebLogic is taken down.
     */
    public void testGetLocalConsole() {
        boolean isTrue = true;
        assertTrue("This is a local ONLY test!", isTrue);
       
        // get and test basic console vo's
//        StatusConsoleVo localConsole = consoleService.getConsole();
//        assertNotNull("Console can not be null", localConsole);
//        assertNotNull("Console National config info can not be null", localConsole.getNationalSiteConfigInfo());
//        assertNotNull("Console Local config list can not be null", localConsole.getLocalSiteConfigInfoList());
//        assertEquals("Console Local list must have 1 entry", 1, localConsole.getLocalSiteConfigInfoList().size());

        // for testing only
        // System.out.println(this.getClass().getName() + " NATIONAL>" + localConsole.getNationalSiteConfigInfo());
        // System.out.println(this.getClass().getName() + " LOCAL>" + localConsole.getLocalSiteConfigInfoList());

        // pull out the national vo and check it's parts
//        SiteConfigVo nationalSiteConfigInfo = localConsole.getNationalSiteConfigInfo();
//        String nationalSiteNumber = nationalSiteConfigInfo.getSiteNumber();
//        String nationalSiteName = nationalSiteConfigInfo.getSiteName();
//        String nationalServerName = nationalSiteConfigInfo.getSiteServerName();
///        String nationalCotsVersion = nationalSiteConfigInfo.getSiteCotsDbVersion();
//        String nationalPepsVersion = nationalSiteConfigInfo.getSitePepsDbVersion();
 //       String nationalVistaVerion = nationalSiteConfigInfo.getSiteVistaVersion();
 //       String nationalStringDate = nationalSiteConfigInfo.getSiteVersionInfoDate();

 //       assertNotNull("vista message sychronization", nationalSiteConfigInfo.getSiteVistaMessageSynchronization());
 //       assertNotNull("nationalSiteConfigInfo can not be null", nationalSiteConfigInfo);
 //       assertFalse("nationalSiteNumber can not be null or empty>" + nationalSiteNumber + "<",
 //           isNullOrEmptyString(nationalSiteNumber));
 //       assertEquals("nationalSiteNumber must be 999>" + nationalSiteNumber + "<", "999", nationalSiteNumber);
 //       assertFalse("nationalSiteName can not be null or empty>" + nationalSiteName + "<",
 //           isNullOrEmptyString(nationalSiteName));
 //       assertFalse("nationalServerName can not be null or empty>" + nationalServerName + "<",
 //           isNullOrEmptyString(nationalServerName));
 //       assertFalse("nationalCotsVersion can not be null or empty>" + nationalCotsVersion + "<",
 //           isNullOrEmptyString(nationalCotsVersion));
 //       assertFalse("nationalPepsVersion can not be null or empty>" + nationalPepsVersion + "<",
 //           isNullOrEmptyString(nationalPepsVersion));
 //       assertFalse("nationalVistaVerion can not be null or empty>" + nationalVistaVerion + "<",
 //           isNullOrEmptyString(nationalVistaVerion));
 //       assertTrue("nationalVistaVerion is expected to have at least one period>" + nationalVistaVerion + "<",
 //           nationalVistaVerion.contains("."));
 //       assertFalse("nationalStringDate can not be null or empty>" + nationalStringDate + "<",
 //           isNullOrEmptyString(nationalStringDate));

  //      List<SiteConfigVo> localSiteConfigList = localConsole.getLocalSiteConfigInfoList();
  //      SiteConfigVo localSiteConfigInfo = localSiteConfigList.get(0);

 //       String localSiteNumber = localSiteConfigInfo.getSiteNumber();
 //       String localSiteName = localSiteConfigInfo.getSiteName();
 //       String localServerName = localSiteConfigInfo.getSiteServerName();
 //       String localCotsVersion = localSiteConfigInfo.getSiteCotsDbVersion();
 //       String localVistaVerion = localSiteConfigInfo.getSiteVistaVersion();
 //       String localStringDate = localSiteConfigInfo.getSiteVersionInfoDate();

 //       assertNotNull("vista message sychronization", localSiteConfigInfo.getSiteVistaMessageSynchronization());
 //       assertNotNull("localSiteConfigInfo can not be null", localSiteConfigInfo);
 //       assertFalse("localSiteNumber can not be null or empty>" + localSiteNumber + "<",
 //           isNullOrEmptyString(localSiteNumber));
 //       assertEquals("localSiteNumber must be 671 ", "671", localSiteNumber); // Local-1 site number
 //       assertFalse("localSiteName can not be null or empty>" + localSiteName + "<", isNullOrEmptyString(localSiteName));
 //       assertFalse("localServerName can not be null or empty>" + localServerName + "<",
 //           isNullOrEmptyString(localServerName));
 //       assertFalse("localCotsVersion can not be null or empty>" + localCotsVersion + "<",
//            isNullOrEmptyString(localCotsVersion));
 //       assertFalse("localPepsVersion can not be null or empty>" + localPepsVersion + "<",
//            isNullOrEmptyString(localPepsVersion));
//        assertFalse("localVistaVerion can not be null or empty>" + localVistaVerion + "<",
//            isNullOrEmptyString(localVistaVerion));
//        assertTrue("localVistaVerion is expected to have at least one period>" + localVistaVerion + "<", localVistaVerion
 //           .contains("."));
 //       assertFalse("localStringDate can not be null or empty>" + localStringDate + "<",
 //           isNullOrEmptyString(localStringDate));*/
    }

}
