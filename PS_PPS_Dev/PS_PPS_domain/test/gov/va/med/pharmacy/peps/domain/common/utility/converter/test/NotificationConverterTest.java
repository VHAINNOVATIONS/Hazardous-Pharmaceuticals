/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NotificationConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NotificationStatusConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.UserConverter;

import junit.framework.TestCase;


/**
 * Test fixture
 */
public class NotificationConverterTest extends TestCase {

    private static final NotificationStatusType NOTIFICATION_STATUS = NotificationStatusType.INACTIVATED;
    private static final String USER_FIRST_NAME = "userFirstName";
    private static final String USER_LAST_NAME = "userLastName";
    private static final Long USER_ID = 9991L;
    private static final String LOCATION = "location";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final EventCategory EVENT_CATEGORY = EventCategory.APPROVED_REQUEST;
    private static final EntityType ITEM_TYPE = EntityType.ORDERABLE_ITEM;
    private static final String AUDIT_HISTORY_ID = "9991";

    private EplNotificationDo dataDo;
    private NotificationVo objectVo;

    private NotificationConverter notificationConverter;

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        this.notificationConverter = new NotificationConverter();
        NotificationStatusConverter notificationStatusConverter = new NotificationStatusConverter();
        notificationStatusConverter.setUserConverter(new UserConverter());
        notificationConverter.setNotificationStatusConverter(notificationStatusConverter);

        this.objectVo = createVo();
        this.dataDo = null;
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private NotificationVo createVo() {
        objectVo = new NotificationVo();
        objectVo.setId(AUDIT_HISTORY_ID);

        NotificationType preferenceType = NotificationType.APPROVED_NDC_ITEMS;

        objectVo.setNotificationType(preferenceType);

        Collection<NotificationStatusVo> notificationStatus = new ArrayList<NotificationStatusVo>();
        NotificationStatusVo status = new NotificationStatusVo();
        status.setStatus(NOTIFICATION_STATUS);

        UserVo user = new UserVo();
        user.setFirstName(USER_FIRST_NAME);
        user.setId(USER_ID);
        user.setLastName(USER_LAST_NAME);
        user.setLocation(LOCATION);
        user.setPassword(PASSWORD);
        user.setUsername(USERNAME);
        status.setUser(user);

        notificationStatus.add(status);
        objectVo.setNotificationStatus(notificationStatus);

        Collection<ItemAuditHistoryVo> itemAudits = new ArrayList<ItemAuditHistoryVo>();
        ItemAuditHistoryVo audit = new ItemAuditHistoryVo();
        audit.setEventCategory(EVENT_CATEGORY);
        audit.setId(AUDIT_HISTORY_ID);
        audit.setAuditItemType(ITEM_TYPE);
        objectVo.setItemAudits(itemAudits);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoHasAttributes() {
        dataDo = notificationConverter.convert(objectVo);

        assertEquals("should be equal", dataDo.getNotificationType(), objectVo.getNotificationType().name());
    }
}
