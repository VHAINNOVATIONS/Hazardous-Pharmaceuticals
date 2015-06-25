/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;


/**
 * Data representing a NotificationVo.
 * 
 */
public class NotificationVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String id;
    private NotificationType notificationType;
    private Collection<ItemAuditHistoryVo> itemAudits;
    private Collection<NotificationStatusVo> notificationStatus;

    /**
     * getItemAudits
     * 
     * @return itemAudits property
     */
    public Collection<ItemAuditHistoryVo> getItemAudits() {

        return itemAudits;
    }

    /**
     * setItemAudits
     * 
     * @param itemAudits itemAudits property
     */
    public void setItemAudits(Collection<ItemAuditHistoryVo> itemAudits) {

        this.itemAudits = itemAudits;
    }

    /**
     * getId
     * 
     * @return id property
     */
    public String getId() {

        return id;
    }

    /**
     * setId
     * 
     * @param id String property
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * getNotificationType
     * 
     * @return preferenceType property
     */
    public NotificationType getNotificationType() {

        return notificationType;
    }

    /**
     * setNotificationType
     * 
     * @param preferenceType preferenceType property
     */
    public void setNotificationType(NotificationType preferenceType) {

        this.notificationType = preferenceType;
    }

    /**
     * getNotificationStatus
     * 
     * @return notificationStatus property
     */
    public Collection<NotificationStatusVo> getNotificationStatus() {

        return notificationStatus;
    }

    /**
     * setNotificationStatus
     * 
     * @param notificationStatus notificationStatus property
     */
    public void setNotificationStatus(Collection<NotificationStatusVo> notificationStatus) {

        this.notificationStatus = notificationStatus;
    }

    /**
     * Based on the item audit history of the item, this method determines what the Notification Type of this notification is
     * 
     * 
     */
    public void determineNotificationType() {

        if (getItemAudits() != null && getItemAudits().size() != 0) {
            ItemAuditHistoryVo mainAudit = (ItemAuditHistoryVo) getItemAudits().toArray()[0];

            EventCategory event = mainAudit.getEventCategory();
            EntityType type = mainAudit.getAuditItemType();
            boolean localOnlyMod = false;
            boolean itemStatusChanged = false;

            if (mainAudit.getDetails() != null) {
                for (ItemAuditHistoryDetailsVo detail : mainAudit.getDetails()) {
                    localOnlyMod |= detail.getColumnName().equals(FieldKey.LOCAL_USE.getKey());
                    itemStatusChanged |=
                            detail.getColumnName().equals(FieldKey.ITEM_STATUS.getKey())
                                    && detail.getNewValue().equals(ItemStatus.INACTIVE.toString());

                }
            }

            if (event.equals(EventCategory.APPROVED_REQUEST)) {
                setNotificationType(type.getApprovedNotification());
            } else if (event.equals(EventCategory.REJECTED_REQUEST)) {
                setNotificationType(type.getRejectedNotification());
            } else {
                if (localOnlyMod) {
                    setNotificationType(type.getLocalUseNotification());
                } else if (itemStatusChanged) {
                    setNotificationType(type.getInactivatedNotification());
                } else {
                    setNotificationType(type.getModifiedNotification());
                }
            }

        }

    }
}
