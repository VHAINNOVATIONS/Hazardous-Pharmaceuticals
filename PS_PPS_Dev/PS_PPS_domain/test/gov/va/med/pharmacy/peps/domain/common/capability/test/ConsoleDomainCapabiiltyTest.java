/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import gov.va.med.pharmacy.peps.common.exception.PharmacyException;




/**
 * ConsoleDomainCapabiiltyTest
 */
public class ConsoleDomainCapabiiltyTest extends DomainCapabilityTestCase {

    /**
     * testSkipped
     * @throws PharmacyException PharmacyException
     */
    public void testSkipped() throws PharmacyException {
        boolean isTrue = true;
        assertTrue("Entire class is skipped.", isTrue);
    }

//    private ConsoleDomainCapability localConsoleDomainCapability;
//
//    /**
//     * Retrieve the capability being tested from the Spring application context.
//     * 
//     * @throws Exception if error
//     * 
//     * @see junit.framework.TestCase#setUp()
//     */
//    @Override
//    protected void setUp() throws Exception {
//        this.localConsoleDomainCapability = getLocalOneCapability(ConsoleDomainCapability.class);
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllConsolelocal() throws Exception {
//
//        List<LocalConsoleInfoVo> rCollection = localConsoleDomainCapability.retrieveLocalConsoleInfo();
//        LocalConsoleInfoVo dataVo = buildVo("655");
//        localConsoleDomainCapability.create(dataVo, getTestUser());
//
//        assertTrue("Collection returned correct number", rCollection.size() + 1 > rCollection.size());
//    }
//
//    /**
//     * This method buidlsVO
//     * 
//     * @param siteNumber String
//     * @return VaLocalConsoleInfoVo
//     */
//    private LocalConsoleInfoVo buildVo(String siteNumber) {
//        LocalConsoleInfoVo dataVo = new LocalConsoleInfoVo();
//        dataVo.setServerName("SERVER");
//        dataVo.setSiteName("SITE");
//
//        dataVo.setSiteNumber(siteNumber);
//        dataVo.setSoftwareUpdateType("UPDATETYPE");
//        dataVo.setSoftwareUpdateVersion("VERSION");
//
//        dataVo.setVersionInstallDtm("DATE");
//
//        return dataVo;
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testCreateConsole() throws Exception {
//
//        LocalConsoleInfoVo console = buildVo("600");
//        LocalConsoleInfoVo returnedVo = localConsoleDomainCapability.create(console, getTestUser());
//        assertNotNull("Returned with site Number", returnedVo.getSiteNumber());
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateConsolelocal() throws Exception {
//
//        List<LocalConsoleInfoVo> names = localConsoleDomainCapability.retrieveLocalConsoleInfo();
//
//        names.get(0).setVersionInstallDtm("updatedVersion");
//
//        localConsoleDomainCapability.update(names.get(0), getTestUser());
//
//        List<LocalConsoleInfoVo> retrievedUpdated = localConsoleDomainCapability.retrieveLocalConsoleInfo();
//
//        assertEquals("Should be equal", retrievedUpdated.get(0).getVersionInstallDtm(), "updatedVersion");
//
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveInOrder() throws Exception {
//
//        List<LocalConsoleInfoVo> returnedVo = localConsoleDomainCapability.retrieveLocalConsoleInfo();
//        System.out.println(returnedVo);
//        assertNotNull("Returned with site Number", returnedVo.size() > 0);
//
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testDeleteAllConsoleLocal() throws Exception {
//
//        localConsoleDomainCapability.deleteAll();
//
//        List<LocalConsoleInfoVo> names = localConsoleDomainCapability.retrieveLocalConsoleInfo();
//        assertEquals("NO rows in database", names.size(), 0);
//
//    }

}
