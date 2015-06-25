/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;




/**
 * NotificationDomainCapabilityTest
 */
public class NotificationDomainCapabilityTest extends DomainCapabilityTestCase {
    private boolean isTrue = true;
   
//    private NotificationDomainCapability localNotificationDomainCapability;
//    private UserDomainCapability localUserDomainCapability;
    
    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        //        this.localNotificationDomainCapability = getLocalOneCapability(NotificationDomainCapability.class);
//        this.localUserDomainCapability = getLocalOneCapability(UserDomainCapability.class);
    }
    
    
    /**
     * testSkipped because this test case is for local only functionality
     * @throws Exception
     */
    public void testSkipped() {
        assertTrue("Entire class is skipped.", isTrue);
    }
    
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testCreate() throws Exception {
//        String notificationId;
//        NotificationVo notification = new NotificationVo();
//
//        // status does not need to be set
//
//        NotificationType notificationType = NotificationType.APPROVED_NDC_ITEMS;
//
//        notification.setNotificationType(notificationType);
//
//        notificationId = localNotificationDomainCapability.create(notification, getTestUser()).getId();
//        NotificationVo insertedVo = localNotificationDomainCapability.retrieve(notificationId);
//
//        assertNotNull("id should not be null", insertedVo.getId());
//        assertEquals("PreferenceType", notification.getNotificationType(), insertedVo.getNotificationType());
//        assertEquals("should have expected notification status", NotificationStatusType.NEWINSTANCE,
//            ((NotificationStatusVo) insertedVo.getNotificationStatus().toArray()[0]).getStatus());
//    }
//
//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testRetrieveStatus() throws Exception {
//        String notificationId;
//        NotificationVo notification = new NotificationVo();
//
//        // status does not need to be set
//
//        NotificationType notificationType = NotificationType.APPROVED_NDC_ITEMS;
//
//        notification.setNotificationType(notificationType);
//
//        notificationId = localNotificationDomainCapability.create(notification, getTestUser()).getId();
//        String status = localNotificationDomainCapability.retrieveNotificationStatus(getTestUser().getId(), Long
//            .valueOf(notificationId));
//        assertEquals("status should not be null", "NEWINSTANCE", status);
//
//    }
//
//    /**
//     * Test apparently notification status is not required...
//     * 
//     * @throws Exception
//     */
//    public void testPreferenceTypeOnCreate() throws Exception {
//        String notificationId;
//        NotificationVo notification = new NotificationVo();
//
//        NotificationType notificationType = NotificationType.APPROVED_NDC_ITEMS;
//
//        notification.setNotificationType(notificationType);
//
//        notificationId = localNotificationDomainCapability.create(notification, getTestUser()).getId();
//        NotificationVo insertedVo = localNotificationDomainCapability.retrieve(notificationId);
//
//        assertNotNull("id should not be null", insertedVo.getId());
//        assertEquals("notificationType", notification.getNotificationType(), insertedVo.getNotificationType());
//    }
//
//    /**
//     * Test apparently notification status is not required...
//     * 
//     * @throws Exception
//     */
//    public void testNotificationCount() throws Exception {
//        String notificationId;
//        NotificationVo notification = new NotificationVo();
//
//        NotificationType notificationType = NotificationType.APPROVED_NDC_ITEMS;
//
//        notification.setNotificationType(notificationType);
//
//        notificationId = localNotificationDomainCapability.create(notification, getTestUser()).getId();
//        localNotificationDomainCapability.retrieve(notificationId);
//
//        int count = localNotificationDomainCapability.retrieveNotificationCount();
//        assertNotNull("count is not Null", count > 0);
//    }
//
//    /**
//     * Test apparently notification status is not required...
//     * 
//     * @throws Exception
//     */
//    public void testUpdateNotificationStatus() throws Exception {
//        String notificationId;
//        NotificationVo notification = new NotificationVo();
//        notification.setNotificationType(NotificationType.APPROVED_ORDERABLE_ITEMS);
//        UserVo user = new UserDomainGenerator(localUserDomainCapability).createSaved();
//
//        // status does not need to be set
//
//        notificationId = localNotificationDomainCapability.create(notification, getTestUser()).getId();
//        NotificationVo insertedVo = localNotificationDomainCapability.retrieve(notificationId);
//
//        for (NotificationStatusVo status : insertedVo.getNotificationStatus()) {
//            localNotificationDomainCapability.updateNotificationStatus(new Long(notificationId), user,
//                NotificationStatusType.VIEWED, "TEST");
//        }
//
//        NotificationVo retrieved = localNotificationDomainCapability.retrieve(notificationId);
//
//        for (NotificationStatusVo status : retrieved.getNotificationStatus()) {
//            if (user.getId().equals(status.getUser().getId())) {
//                assertEquals("should have expected notification status", NotificationStatusType.VIEWED, status.getStatus());
//            }
//        }
//    }
//
//    /**
//     * Test apparently preference type is not required...
//     * 
//     * @throws Exception
//     */
//    public void testNotificationStatusOnCreate() throws Exception {
//        String notificationId;
//        NotificationVo notification = new NotificationVo();
//        notification.setNotificationType(NotificationType.INACTIVATED_NDC_ITEMS);
//
//        // status does not need to be set
//
//        notificationId = localNotificationDomainCapability.create(notification, getTestUser()).getId();
//        NotificationVo insertedVo = localNotificationDomainCapability.retrieve(notificationId);
//
//        assertNotNull("id should not be null", insertedVo.getId());
//
//        for (NotificationStatusVo status : insertedVo.getNotificationStatus()) {
//            assertEquals("should have expected notification status", NotificationStatusType.NEWINSTANCE, status.getStatus());
//        }
//    }
//
//    /**
//     * This test loops through all available preference types and verifies that the filter works for each individual one
//     * 
//     * @throws Exception
//     */
//    public void testSearchWithPreferencesNotificationLocal() throws Exception {
//        int initialCount;
//
//        UserVo user = new UserDomainGenerator(localUserDomainCapability).createSaved();
//        NotificationType oldNotificationType = NotificationType.UNKNOWN_NON_SYSTEM_EVENT;
//
//        for (NotificationType notificationType : NotificationType.values()) {
//
//            user.setNotifications(new ArrayList<NotificationType>());
//            user.getNotifications().add(notificationType);
//
//            // prepare criteria for test
//            Collection<NotificationSummaryVo> notificationSummaryCollection;
//            SearchNotificationsCriteriaVo criteria = new SearchNotificationsCriteriaVo();
//            criteria.setUnread(true);
//            criteria.setUser(user);
//
//            Calendar startDate = Calendar.getInstance();
//            startDate.add(Calendar.DAY_OF_YEAR, -1);
//            Calendar endDate = Calendar.getInstance();
//            endDate.add(Calendar.DAY_OF_YEAR, 1);
//            criteria.setSearchNotificationStartDate(startDate.getTime());
//            criteria.setSearchNotificationEndDate(endDate.getTime());
//
//            // Add at least one notification and verify that it included in the resultss
//            NotificationVo notification;
//
//            // add a new notification
//            notification = new NotificationVo();
//            notification.setNotificationType(notificationType);
//            localNotificationDomainCapability.create(notification, getTestUser());
//
//            // get initial count
//            notificationSummaryCollection = localNotificationDomainCapability.search(criteria);
//            initialCount = notificationSummaryCollection.size();
//
//            assertTrue("Initial notification not returned in search results", initialCount > 0);
//
//            // add a new notification
//            notification = new NotificationVo();
//            notification.setNotificationType(oldNotificationType);
//
//            localNotificationDomainCapability.create(notification, getTestUser()); // should be in final count
//
//            // check new count
//            notificationSummaryCollection = localNotificationDomainCapability.search(criteria);
//
//            assertEquals("The count should not have changed between adding a new notification for type " + notificationType,
//                initialCount, notificationSummaryCollection.size());
//
//            oldNotificationType = notificationType;
//
//        }
//    }
//
//
//
//    /**
//     * Test
//     * 
//     * @throws Exception
//     */
//    public void testSearchHiddenNotificationsLocal() throws Exception {
//        UserVo user = new UserDomainGenerator(localUserDomainCapability).createSaved();
//        int initialCount;
//
//        for (NotificationType type : NotificationType.values()) {
//            user.getNotifications().add(type);
//        }
//
//        // prepare criteria for test
//        Collection<NotificationSummaryVo> notificationSummaryCollection;
//        SearchNotificationsCriteriaVo criteria = new SearchNotificationsCriteriaVo();
//        criteria.setHidden(true);
//        Calendar startDate = Calendar.getInstance();
//        startDate.add(Calendar.DAY_OF_YEAR, -1);
//        Calendar endDate = Calendar.getInstance();
//        endDate.add(Calendar.DAY_OF_YEAR, 1);
//        criteria.setSearchNotificationStartDate(startDate.getTime());
//        criteria.setSearchNotificationEndDate(endDate.getTime());
//        criteria.setUser(user);
//
//        // get initial count
//        notificationSummaryCollection = localNotificationDomainCapability.search(criteria);
//        initialCount = notificationSummaryCollection.size();
//
//        NotificationVo notification;
//        NotificationStatusVo status;
//        Collection<NotificationStatusVo> notificationStatus;
//        ItemAuditHistoryVo audit;
//        List<ItemAuditHistoryVo> audits;
//        NotificationType notificationType;
//
//        // add a new HIDDEN notification
//
//        notification = new NotificationVo();
//        status = new NotificationStatusVo();
//        status.setStatus(NotificationStatusType.HIDDEN);
//        status.setUser(user);
//        notificationStatus = new HashSet<NotificationStatusVo>();
//        notificationStatus.add(status);
//        notification.setNotificationStatus(notificationStatus);
//        audits = new ArrayList<ItemAuditHistoryVo>();
//        audit = new ItemAuditHistoryGenerator().psuedoRandom("9991", EntityType.NDC);
//        audit.setEventCategory(EventCategory.APPROVED_REQUEST);
//        audits.add(audit);
//        notification.setItemAudits(audits);
//        notificationType = NotificationType.APPROVED_NDC_ITEMS;
//        notification.setNotificationType(notificationType);
//        localNotificationDomainCapability.create(notification, getTestUser()); // should be in final count
//        initialCount++;
//
//        // add a new VIEWED notification
//
//        notification = new NotificationVo();
//        status = new NotificationStatusVo();
//        status.setStatus(NotificationStatusType.VIEWED);
//        status.setUser(user);
//        notificationStatus = new HashSet<NotificationStatusVo>();
//        notificationStatus.add(status);
//        notification.setNotificationStatus(notificationStatus);
//        audits = new ArrayList<ItemAuditHistoryVo>();
//        audit = new ItemAuditHistoryGenerator().psuedoRandom("9991", EntityType.NDC);
//        audit.setEventCategory(EventCategory.REJECTED_REQUEST);
//        audits.add(audit);
//        notification.setItemAudits(audits);
//        notificationType = NotificationType.APPROVED_NDC_ITEMS;
//        notification.setNotificationType(notificationType);
//        localNotificationDomainCapability.create(notification, getTestUser()); // should not be in final count
//
//        // check new count
//        notificationSummaryCollection = localNotificationDomainCapability.search(criteria);
//        assertEquals("count should be one more than initial", initialCount, notificationSummaryCollection.size());
//    }
}
