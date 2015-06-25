/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.UserService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Local test case for logging in and out
 */
public class UserServiceTest extends IntegrationTestCase {
    private static final String USER = "plm1l1";
    private static final String PASS = "abc123!!!";
    
    private UserService userService;

    /**
     * Setups the test case
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        userService = getNationalService(UserService.class);
    }

    /**
     * Tests Login
     * 
     * @throws Exception Exception
     */
    public void testLogin() throws Exception {

        UserVo user = new UserVo();
        user.setUsername(USER);
        user.setPassword(PASS);

        UserVo userVo = userService.login(user);

        assertNotNull(new String("username was null"), userVo.getUsername());

    }

    /**
     * Tests logging out
     * 
     * @throws Exception Exception
     */
    public void testLogout() throws Exception {

        String username = USER;
        UserVo userVo = new UserVo();
        userVo.setUsername(username);

        try {
            userService.logout(userVo);
        } catch (Exception e) {
            fail();
        }

    }

    /**
     * Test retrieving notification types
     * 
     * @throws Exception Exception
     */
    public void testRetrieveAllNotificationTypes() throws Exception {
        List<NotificationType> types = userService.retrieveAllNotificationTypes();

        assertTrue("No notification types returned", types.size() > 0);
    }

    /**
     * Test update of user preferences
     * 
     * @throws Exception Exception
     */
    public void testUpdatePreferences() throws Exception {
        
        UserVo user = new UserVo();
        user.setUsername(USER);
        user.setPassword(PASS);

        user = userService.login(user);

        List<NotificationType> notifications = new ArrayList<NotificationType>();
        notifications.add(userService.retrieveAllNotificationTypes().get(1));
        user.setNotifications(notifications);

        UserVo updatedUser = userService.updatePreferences(user);

        assertTrue("Preferences not returned.", updatedUser.getNotifications().size() == 1);
    }

}
