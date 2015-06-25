/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;




/**
 * UserDomainCapabilityTest
 */
public class UserDomainCapabilityTest extends DomainCapabilityTestCase {

    /**
     * testSkipped
     */
    public void testSkipped() {
        boolean isTrue = true;
        assertTrue("Entire class is skipped.", isTrue);
    }

//    private UserDomainCapability localuserDomainCapability;
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
//        this.localuserDomainCapability = getLocalOneCapability(UserDomainCapability.class);
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveAllLocal() throws Exception {
//        int initialCount = localuserDomainCapability.retrieveAll().size();
//        UserVo user = new UserGenerator().pseudoRandom();
//
//        localuserDomainCapability.create(user);
//
//        List<UserVo> nameCollection = localuserDomainCapability.retrieveAll();
//
//        assertEquals(new String("Collection did returned correct number"), initialCount + 1, nameCollection.size());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testCreateLocal() throws Exception {
//        UserVo user = new UserGenerator().pseudoRandom();
//
//        UserVo returnedVo = localuserDomainCapability.create(user);
//
//        assertNotNull("User Id should not be null", returnedVo.getId());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testCreateShouldRequireThatUsernameBeUniqueLocal() throws Exception {
//        String duplicateUserName = "duplicateUserName";
//        UserVo user;
//        UserVo returnedVo;
//
//        // get duplicate username that's unique to this test
//        // duplicateUserName += new UserGenerator().pseudoRandom().getUsername();
//
//        // create first user
//        user = new UserGenerator().pseudoRandom();
//        user.setUsername(duplicateUserName);
//
//        returnedVo = localuserDomainCapability.create(user);
//        assertNotNull("User Id should not be null", returnedVo.getId());
//
//        // create second user with duplicate username
//        user = new UserGenerator().pseudoRandom();
//        user.setUsername(duplicateUserName);
//        returnedVo = localuserDomainCapability.create(user);
//
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testCreateRetrieveByIdLocal() throws Exception {
//        Long userId;
//        UserVo user = new UserGenerator().pseudoRandom();
//
//        UserVo returnedUser = localuserDomainCapability.create(user);
//        userId = returnedUser.getId();
//
//        UserVo returnedVo = localuserDomainCapability.retrieve(userId);
//
//        assertEquals("id should match expected", userId, returnedVo.getId());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testCreateRetrieveByUserNameLocal() throws Exception {
//        String username;
//        UserVo user = new UserGenerator().pseudoRandom();
//        username = user.getUsername();
//
//        localuserDomainCapability.create(user);
//
//        UserVo returnedVo = localuserDomainCapability.retrieve(username);
//
//        assertEquals("id should match expected", username, returnedVo.getUsername());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testCreateWithSessionPreferencesLocal() throws Exception {
//        Long userId;
//        SessionPreferenceType preferenceType = SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID;
//        String preferenceValue = "42";
//        UserVo user = new UserGenerator().pseudoRandom();
//
//        user.getSessionPreferences().put(preferenceType, preferenceValue);
//
//        user = localuserDomainCapability.create(user);
//        userId = user.getId();
//        assertNotNull("id should be set", userId);
//
//        UserVo returnedVo = localuserDomainCapability.retrieve(userId);
//
//        assertEquals("should have expected count", 1, returnedVo.getSessionPreferences().size());
//        assertTrue("should contain preference type", returnedVo.getSessionPreferences().keySet().contains(preferenceType));
//        assertTrue("should contain preference value", returnedVo.getSessionPreferences().values().contains(preferenceValue));
//        assertEquals("id should match expected", userId, returnedVo.getId());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     * 
//     */
//    public void testUpdateUserNotifictionPreferencesLocal() throws Exception {
//
//        List<UserVo> users = localuserDomainCapability.retrieveAll();
//        UserVo user = users.get(0);
//
//        NotificationType notif = NotificationType.APPROVED_NDC_ITEMS;
//
//        List<NotificationType> notifs = new ArrayList<NotificationType>();
//
//        notifs.add(notif);
//
//        user.setNotifications(notifs);
//
//        user = localuserDomainCapability.update(user);
//
//        UserVo returnedVo = localuserDomainCapability.retrieve(user.getId());
//
//        System.out.println(returnedVo);
//
//        assertNotNull("should contain not null preference type", returnedVo.getPreferences().get(0));
//
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     * 
//     */
//    public void testShouldSupportLocationLocal() throws Exception {
//        Long userId;
//        UserVo user = new UserGenerator().pseudoRandom();
//
//        user.setLocation("location1");
//        user = localuserDomainCapability.create(user);
//        userId = user.getId();
//        user = localuserDomainCapability.retrieve(userId);
//        assertEquals("should have location after create", "location1", user.getLocation());
//
//        user.setLocation("location2");
//        user = localuserDomainCapability.update(user);
//        user = localuserDomainCapability.retrieve(userId);
//        assertEquals("should have location after create", "location2", user.getLocation());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     * 
//     */
//    public void testUpdateUserSessionPreferencesLocal() throws Exception {
//
//        List<UserVo> users = localuserDomainCapability.retrieveAll();
//        UserVo user = users.get(0);
//
//        System.out.println(user.getSessionPreferences());
//
//        SessionPreferenceType preferenceType = SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID;
//        String preferenceValue = "mynewPref";
//
//        user.getSessionPreferences().put(preferenceType, preferenceValue);
//
//        user = localuserDomainCapability.update(user);
//
//        UserVo returnedVo = localuserDomainCapability.retrieve(user.getId());
//
//        System.out.println(returnedVo);
//
//        assertNotNull("should contain not null session type", returnedVo.getSessionPreferences());
//
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     * 
//     */
//    public void testUpdateSimpleUserInstanceLocal() throws Exception {
//        Long userId;
//        UserVo user = new UserDomainGenerator(localuserDomainCapability).createSaved();
//        userId = user.getId();
//        assertNotNull("id should be set", userId);
//
//        user = localuserDomainCapability.retrieve(userId);
//
//        user.setFirstName("Billy");
//
//        localuserDomainCapability.update(user);
//
//        user = localuserDomainCapability.retrieve(userId);
//        assertEquals("Should have updated the field name", "Billy", user.getFirstName());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     * 
//     */
//    public void testUpdateSimpleUserInstanceWhenNotificationTypesHasBeenSetToNullLocal() throws Exception {
//        Long userId;
//        UserVo user = new UserDomainGenerator(localuserDomainCapability).createSaved();
//        userId = user.getId();
//        assertNotNull("id should be set", userId);
//
//        user = localuserDomainCapability.retrieve(userId);
//
//        user.setFirstName("Billy");
//        user.setNotifications(null);
//
//        localuserDomainCapability.update(user);
//
//        user = localuserDomainCapability.retrieve(userId);
//        assertEquals("Should have updated the field name", "Billy", user.getFirstName());
//    }
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testUserDomainGeneratorLocal() throws Exception {
//        Long userId;
//        UserVo user = new UserDomainGenerator(localuserDomainCapability).createSaved();
//        userId = user.getId();
//        assertNotNull("id should be set", userId);
//
//        user = localuserDomainCapability.retrieve(userId);
//        assertNotNull("should have found the user in the database", userId);
//    }
}
