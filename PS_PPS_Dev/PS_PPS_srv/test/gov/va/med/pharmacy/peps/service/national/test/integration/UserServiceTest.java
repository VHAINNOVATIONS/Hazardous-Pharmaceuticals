/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.test.integration;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.UserService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Test class for user logging in and out
 */
public class UserServiceTest extends IntegrationTestCase {
    private static final Logger LOG = Logger.getLogger(UserServiceTest.class);
    private UserService userService;

    /**
     * set up the test
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        LOG.debug(getName());
        userService = getNationalService(UserService.class);
    }

    /**
     * test for logging in
     * 
     * @throws Exception Exception
     */
    public void testLogin() throws Exception {

        UserVo userVo = new UserVo();
        userVo.setUsername("pnm1n1");
        userVo.setPassword("abc123!!!");

        userVo = userService.login(userVo);

        assertNotNull(new String("username was null"), userVo.getUsername());

    }

    /**
     * test for logging out
     * 
     * @throws Exception Exception
     */
    public void testLogout() throws Exception {
        UserVo userVo = new UserGenerator().getNationalManagerOne();

        try {
            userService.logout(userVo);
        } catch (Exception e) {
            fail();
        }

    }

}
