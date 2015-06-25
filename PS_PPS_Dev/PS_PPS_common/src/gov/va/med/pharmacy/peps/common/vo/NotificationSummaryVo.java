/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Data representing a summary used for displaying notifications
 * 
 */
public class NotificationSummaryVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private EntityType itemType;
    private String itemName;
    private String id;
    private String ndc;
    private String originator;
    private NotificationType notificationType;
    private Date notificationDate;
    private String viewedBy;
    private Long notificationId;
    private NotificationStatusVo status;
    private String notificationStatus;
    private boolean notificationHidden;

    @Deprecated
    private boolean localUse;

    /**
     * getItemType
     * 
     * @return type property
     */
    public EntityType getItemType() {
        return itemType;
    }

    /**
     * setItemType
     * 
     * @param type type property
     */
    public void setItemType(EntityType type) {
        this.itemType = type;
    }

    /**
     * getEntityType
     * 
     * @return type property
     */
    public EntityType getEntityType() {
        return itemType;
    }

    /**
     * getItemName
     * 
     * @return name property
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * setItemName
     * 
     * @param name name property
     */
    public void setItemName(String name) {
        this.itemName = name;
    }

    /**
     * getNdc
     * 
     * @return ndc property
     */
    public String getNdc() {
        return ndc;
    }

    /**
     * setNdc
     * 
     * @param ndc ndc property
     */
    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    /**
     * getOriginator
     * 
     * @return originator property
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * setOriginator
     * 
     * @param originator originator property
     */
    public void setOriginator(String originator) {
        this.originator = originator;
    }

    /**
     * getNotificationDate
     * 
     * @return notificationDate property
     */
    public Date getNotificationDate() {
        return notificationDate;
    }

    /**
     * setNotificationDate
     * 
     * @param notificationDate notificationDate property
     */
    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    /**
     * Returns the viewedBy property, but strips out the first name curently
     * 
     * @return viewedBy property
     */
    public String getViewedBy() {
        
        if (getViewedByCount() == 0) {         
            return " ";
        }
        
        return viewedBy.substring(viewedBy.indexOf(' '));
    }

    /**
     * setViewedBy
     * 
     * @param viewedBy viewedBy property
     */
    public void setViewedBy(String viewedBy) {
        this.viewedBy = viewedBy;
    }

    /**
     * Counts the number of entries in the viewdBy string
     * 
     * @return a count of the number of entries in viewedBy
     */
    public int getViewedByCount() {
        return viewedBy.split(" ").length - 1;
    }

    /**
     * getNotificationType
     * 
     * @return eventCategory property
     */
    public NotificationType getNotificationType() {
        return notificationType;
    }

    /**
     * setNotificationType
     * 
     * @param notificationType eventCategory property
     */
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * getNotificationId
     * 
     * @return notificationId
     */
    public Long getNotificationId() {
        return notificationId;
    }

    /**
     * setNotificationId
     * 
     * @param notificationId param
     */
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Returns the users status on whether the notification is hidden or not
     * 
     * @return notificationHidden
     */
    public boolean isNotificationHidden() {
        return notificationHidden;
    }

    /**
     * setNotificationHidden
     * 
     * @param notificationHidden set the hidden status
     */
    public void setNotificationHidden(boolean notificationHidden) {
        this.notificationHidden = notificationHidden;
    }

    /**
     * getStatus
     * 
     * @return status property
     */
    public NotificationStatusVo getStatus() {
        return status;
    }

    /**
     * setStatus
     * 
     * @param status status property
     */
    public void setStatus(NotificationStatusVo status) {
        this.status = status;
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
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getNotificationStatus
     * 
     * @return String
     */
    public String getNotificationStatus() {
        return notificationStatus;
    }

    /**
     * setNotificationStatus
     * 
     * @param notificationStatus status
     */
    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    /**
     * setLocalUse
     * 
     * @param localUse boolean
     * 
     * @deprecated Change the Domain SQL query to filter local use products and orderable items instead of having the Java
     *             code do it
     */
    @Deprecated
    public void setLocalUse(boolean localUse) {
        this.localUse = localUse;
    }

    /**
     * isLocalUse
     * 
     * @return boolean
     * 
     * @deprecated Change the Domain SQL query to filter local use products and orderable items instead of having the Java
     *             code do it
     */
    @Deprecated
    public boolean isLocalUse() {
        return localUse;
    }
}
