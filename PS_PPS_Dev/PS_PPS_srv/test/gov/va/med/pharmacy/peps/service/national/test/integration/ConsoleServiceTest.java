/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;
import gov.va.med.pharmacy.peps.service.national.session.ConsoleService;


/**
 *  Tests the NationalConsole Service    (System Information) 
 */
public class ConsoleServiceTest extends IntegrationTestCase {

    private ConsoleService consoleService;
    private String s999 = "999";
   
    /**
     * ConsoleServiceTest
     * 
     * @param name String
     */
    public ConsoleServiceTest(String name) {
        super(name);
    }

    /**
     * setUp
     * 
     * @throws Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        consoleService = getNationalService(ConsoleService.class);
    }

    /**
     * Test case to test national getConsole
     * A Local Status Console has a national site config vo and a list with 1 local site config (this site) (always only 1)
     * A National Status Console has a national site config vo and a list with 0..n local site config 
     *  from the national db table 
     */
    public void testGetNationalConsole() {
        
        // get and test basic console vo's
        StatusConsoleVo nationalConsole = consoleService.getConsole();
        assertNotNull("Console can not be null.", nationalConsole);
        assertNotNull("Console National config info can not be null", nationalConsole.getNationalSiteConfigInfo());
        assertNotNull("Console Local config list can not be null but can be empty", 
            nationalConsole.getLocalSiteConfigInfoList());
        int listSize = nationalConsole.getLocalSiteConfigInfoList().size();

        // pull out the national vo and check it's parts
        SiteConfigVo nationalSiteConfigInfo = nationalConsole.getNationalSiteConfigInfo();
        String nationalSiteNumber = nationalSiteConfigInfo.getSiteNumber();
        String nationalSiteName = nationalSiteConfigInfo.getSiteName();
        String nationalServerName = nationalSiteConfigInfo.getSiteServerName();
        String nationalCotsVersion = nationalSiteConfigInfo.getSiteCotsDbVersion();
        String nationalPepsVersion = nationalSiteConfigInfo.getSitePepsDbVersion();
        String nationalVistaVerion = nationalSiteConfigInfo.getSiteVistaVersion();
        String nationalStringDate = nationalSiteConfigInfo.getSiteVersionInfoDate();
        
        assertNotNull("nationalSiteConfigInfo can not be null", nationalSiteConfigInfo);
        assertFalse("nationalSiteNumber can not be null or empty>" + nationalSiteNumber + "<", 
            isNullOrEmptyString(nationalSiteNumber));
        assertEquals("nationalSiteNumber must be 999>" + nationalSiteNumber + "<", s999, nationalSiteNumber);  
        assertFalse("nationalSiteName can not be null or empty>" + nationalSiteName + "<", 
            isNullOrEmptyString(nationalSiteName));
        assertFalse("nationalServerName can not be null or empty>" + nationalServerName + "<", 
            isNullOrEmptyString(nationalServerName));
        assertFalse("nationalCotsVersion can not be null or empty>" + nationalCotsVersion + "<", 
            isNullOrEmptyString(nationalCotsVersion));
        assertFalse("nationalPepsVersion can not be null or empty>" + nationalPepsVersion + "<", 
            isNullOrEmptyString(nationalPepsVersion));
        assertFalse("nationalVistaVerion can not be null or empty>" + nationalVistaVerion + "<", 
            isNullOrEmptyString(nationalVistaVerion));
        assertTrue("nationalVistaVerion is expected to have at least one period> " + nationalVistaVerion + "<", 
            nationalVistaVerion.contains("."));
        assertFalse("nationalStringDate can not be null or empty>" + nationalStringDate + "<", 
            isNullOrEmptyString(nationalStringDate));
        
        // pull out a local vo from the list and check it's parts
        if (listSize > 0) {
                
            List<SiteConfigVo> localSiteConfigList = nationalConsole.getLocalSiteConfigInfoList();
            
            // print the list; for testing only
//            for (Iterator<SiteConfigVo> iter = localSiteConfigList.iterator(); iter.hasNext();) {
//                SiteConfigVo printSiteConfigInfo = (SiteConfigVo) iter.next();
//                System.out.println(this.getClass().getName() + "    LOCAL >" + printSiteConfigInfo);
//            }
            
            SiteConfigVo localSiteConfigInfo = localSiteConfigList.get(0);
     
            String localSiteNumber = localSiteConfigInfo.getSiteNumber();
            String localSiteName = localSiteConfigInfo.getSiteName();
            String localServerName = localSiteConfigInfo.getSiteServerName();
            String localCotsVersion = localSiteConfigInfo.getSiteCotsDbVersion();
            String localPepsVersion = localSiteConfigInfo.getSitePepsDbVersion();
            String localVistaVerion = localSiteConfigInfo.getSiteVistaVersion();
            String localStringDate = localSiteConfigInfo.getSiteVersionInfoDate();
            
            assertNotNull("localSiteConfigInfo can not be null", localSiteConfigInfo);
            assertFalse("localSiteNumber can not be null or empty>" + localSiteNumber + "<", 
                isNullOrEmptyString(localSiteNumber));
            assertFalse("localSiteName can not be null or empty>" + localSiteName + "<", 
                isNullOrEmptyString(localSiteName));
            assertFalse("localServerName can not be null or empty>" + localServerName + "<", 
                isNullOrEmptyString(localServerName));
            assertFalse("localCotsVersion can not be null or empty>" + localCotsVersion + "<", 
                isNullOrEmptyString(localCotsVersion));
            assertFalse("localPepsVersion can not be null or empty>" + localPepsVersion + "<", 
                isNullOrEmptyString(localPepsVersion));
            assertFalse("localVistaVerion can not be null or empty>" + localVistaVerion + "<", 
                isNullOrEmptyString(localVistaVerion));
            assertTrue("localVistaVerion is expected to have at least one period>" + localVistaVerion + "<", 
                localVistaVerion.contains("."));
            assertFalse("localStringDate can not be null or empty>" + localStringDate + "<",
                isNullOrEmptyString(localStringDate));
        } 
    }

    /**
     * Test case to test national getNationalSiteVersionInfo
     * This is the national site info the local needs for display in real time
     */
    public void testGetNationalSiteVersionInfo() {
        
        SiteConfigVo testNationalSiteConfigInfo = consoleService.getNationalSiteVersionInfo();
        assertNotNull("National config info can not be null", testNationalSiteConfigInfo);

//        System.out.println(this.getClass().getName() + " NATIONAL>" + testNationalSiteConfigInfo);

        String testNationalSiteNumber = testNationalSiteConfigInfo.getSiteNumber();
        String testNationalSiteName = testNationalSiteConfigInfo.getSiteName();
        String testNationalServerName = testNationalSiteConfigInfo.getSiteServerName();
        String testNationalCotsVersion = testNationalSiteConfigInfo.getSiteCotsDbVersion();
        String testNationalPepsVersion = testNationalSiteConfigInfo.getSitePepsDbVersion();
        String testNationalVistaVerion = testNationalSiteConfigInfo.getSiteVistaVersion();
        String testNationalStringDate = testNationalSiteConfigInfo.getSiteVersionInfoDate();
        
        assertNotNull("testNationalSiteConfigInfo can not be null", testNationalSiteConfigInfo);
        assertFalse("testNationalSiteNumber can not be null or empty>" + testNationalSiteNumber + "<", 
            isNullOrEmptyString(testNationalSiteNumber));
        assertEquals("testNationalSiteNumber must be 999>" + testNationalSiteNumber + "<", s999, testNationalSiteNumber);  
        assertFalse("testNationalSiteName can not be null or empty>" + testNationalSiteName + "<", 
            isNullOrEmptyString(testNationalSiteName));
        assertFalse("testNationalServerName can not be null or empty>" + testNationalServerName + "<", 
            isNullOrEmptyString(testNationalServerName));
        assertFalse("testNationalCotsVersion can not be null or empty>" + testNationalCotsVersion + "<", 
            isNullOrEmptyString(testNationalCotsVersion));
        assertFalse("testNationalPepsVersion can not be null or empty>" + testNationalPepsVersion + "<", 
            isNullOrEmptyString(testNationalPepsVersion));
        assertFalse("testNationalVistaVerion can not be null or empty>" + testNationalVistaVerion + "<", 
            isNullOrEmptyString(testNationalVistaVerion));
        assertTrue("nationalVistaVerion is expected to have at least one period>" + testNationalVistaVerion + "<", 
            testNationalVistaVerion.contains("."));
        assertFalse("testNationalStringDate can not be null or empty>" + testNationalStringDate + "<", 
            isNullOrEmptyString(testNationalStringDate));
    }

    /**
     * Sends a request for the local's version information (System Information) 
     */
    public void testRequestLocalSystemInformation() {
        
        // not sure how to test an async message send, except to try and catch an exception
        try {
            consoleService.requestLocalSystemInformation();
        } catch (Exception e) {
            fail("requestLocalSystemInformation() threw an exception " + e.getStackTrace());
        }
        
    }

    /**
     * Deletes all local version records (System Information) from the national table
     * Then sends a request for the local's version information (System Information)  
     */
    public void testRefreshAllSystemInformation() {
        
        // WARNING!  this is a destructive test - it will delete all records from the national version db table
        // WARNING!  set this to true only if you want the test to run (you will need to refreshDatabase after)
        boolean performTest = false;

        if (performTest) {
            
            // get national console and get size of local list
            StatusConsoleVo initialNationalConsole = consoleService.getConsole();
            assertNotNull("Console can not be null! ", initialNationalConsole);
            assertNotNull("Console Local config list can not be null and should not be empty for this test", 
                initialNationalConsole.getLocalSiteConfigInfoList());
            int initialListSize = initialNationalConsole.getLocalSiteConfigInfoList().size();
            
            if (initialListSize > 0) {
                consoleService.refreshAllSystemInformation();
                StatusConsoleVo afterNationalConsole = consoleService.getConsole();
                assertNotNull("Console can not be null", afterNationalConsole);
                assertNotNull("Console Local config list can not be null and should be empty for this test", 
                    afterNationalConsole.getLocalSiteConfigInfoList());
                int afterListSize = afterNationalConsole.getLocalSiteConfigInfoList().size();
                assertEquals("All local records were not deleted by deleteAll function", 0, afterListSize);
            }
        }
        
    }

    /**
     * Utility test method
     * 
     * @param input a string to be test for null or empty
     * @return boolean - true if input string is null or empty 
     */
    private boolean isNullOrEmptyString(String input) {
        
        return (input == null || input.trim().length() == 0);
    }

}
