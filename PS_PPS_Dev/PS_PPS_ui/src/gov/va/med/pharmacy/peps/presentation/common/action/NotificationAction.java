/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.action;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.ConversationScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.FlashScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.FlowScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RequestScope;
import gov.va.med.pharmacy.peps.service.common.session.NotificationService;


/**
 * Perform actions on Notifications
 */
public class NotificationAction extends PepsAction {
    private static final long serialVersionUID = 1L;
    private static final long SEVEN_DAYS = 604800000; // Number of milliseconds in seven days
    private static final long MAX_DAYS = 15552000000L; // Number of milliseconds in 180 days

    private NotificationService notificationService;

    @FlashScope
    private PrintTemplateVo notificationPrintTemplate = DefaultPrintTemplateFactory.defaultNotifications();

    @ConversationScope
    private SearchNotificationsCriteriaVo criteria = new SearchNotificationsCriteriaVo();

    @ConversationScope
    private List<NotificationSummaryVo> notifications;

    @ConversationScope
    private int notificationCount;

    @FlowScope
    private long notificationId;

    @ConversationScope
    private String notificationStatus;

    @ConversationScope
    private NotificationSummaryVo notificationSummary;

    @FlowScope
    private String itemId;

    @FlowScope
    private EntityType itemType;

    @ConversationScope
    private boolean notificationIsAll = true;

    @ConversationScope
    private boolean notificationIsApproved;

    @ConversationScope
    private boolean notificationIsHidden;

    @ConversationScope
    private boolean notificationIsInactivated;

    @ConversationScope
    private boolean notificationIsModified;

    @ConversationScope
    private boolean notificationIsRejected;

    @ConversationScope
    private boolean notificationIsViewed;

    @ConversationScope
    private boolean notificationIsUnread = true;

    @ConversationScope
    private boolean notificationOnlyLocal;

    @ConversationScope
    private boolean notificationDateUse;

    @ConversationScope
    private Date searchNotificationEndDate = new Date();

    @ConversationScope
    private Date searchNotificationEndDateOrig = new Date(searchNotificationEndDate.getTime() - 0L);

    @ConversationScope
    private Date searchNotificationStartDate = new Date(searchNotificationEndDate.getTime() - SEVEN_DAYS);

    @FlashScope
    @RequestScope
    private String viewedBy = "Text";

    /**
     * Search for notifications
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException
     */
    public String search() throws ItemNotFoundException {
        configureCriteria();
        this.notifications = notificationService.search(criteria);

        return SUCCESS;
    }

    /**
     * Search for notifications, limiting the result to the first five.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException
     */
    public String homeSearch() throws ItemNotFoundException {
        search();
        this.notificationCount = notificationService.retrieveNotificationCount();

        if (notifications.size() > 5) {
            this.notifications = notifications.subList(0, 5);
        }

        return SUCCESS;
    }

    /**
     * Retrieve requests for a specified product
     * 
     * @return SUCCESS
     */
    public String retrieve() {

        return SUCCESS;
    }

    /**
     * Will hide/unhide the selected items in the column
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException
     */
    public String hide() throws ItemNotFoundException {

        if (!notifications.isEmpty()) {
            List<NotificationSummaryVo> selectedItems = getTableSelections(notifications);

            for (NotificationSummaryVo summary : selectedItems) {

                if (summary == null) {
                    continue;
                }

                if (summary.getStatus().getStatus().isViewedHidden()) {
                    notificationService.updateNotificationStatus(summary.getNotificationId(), getUser(),
                        NotificationStatusType.VIEWED, summary.getViewedBy());
                }

                else if (summary.getStatus().getStatus().isHidden()) {
                    notificationService.updateNotificationStatus(summary.getNotificationId(), getUser(),
                        NotificationStatusType.NEWINSTANCE, summary.getViewedBy());
                }

                else if (summary.getStatus().getStatus().isViewed()) {
                    notificationService.updateNotificationStatus(summary.getNotificationId(), getUser(),
                        NotificationStatusType.VIEWEDHIDDEN, summary.getViewedBy());
                }
                else if (summary.getStatus().getStatus().isNewInstance()) {

                    notificationService.updateNotificationStatus(summary.getNotificationId(), getUser(),
                        NotificationStatusType.HIDDEN, summary.getViewedBy());
                }
            }

            Collection<NotificationSummaryVo> summaries = notificationService.search(criteria);
            this.notifications = new ArrayList<NotificationSummaryVo>();

            for (NotificationSummaryVo summary : summaries) {
                this.notifications.add(summary);
            }
        }

        return SUCCESS;
    }

    /**
     * Helper method for setting criteria
     */
    protected void configureCriteria() {
        boolean wasDateChanged = false;
        
        if (searchNotificationEndDate == null || searchNotificationEndDate.equals(searchNotificationEndDateOrig)
            || !notificationDateUse) {
            searchNotificationEndDate = new Date();
            searchNotificationEndDateOrig = new Date(searchNotificationEndDate.getTime() - 0L);
        }

        if (searchNotificationStartDate == null || searchNotificationStartDate.getTime() < (new Date().getTime() - MAX_DAYS)
            || !notificationDateUse) {
            searchNotificationStartDate = new Date((new Date().getTime() - MAX_DAYS));
            
            //If we're not using the notification date, we don't need to display anything, if we are, we do 
            wasDateChanged = notificationDateUse;
        }

        if (searchNotificationEndDate.getTime() < searchNotificationStartDate.getTime()) {
            searchNotificationEndDate = searchNotificationStartDate;
        }

        getCriteria().setHidden(notificationIsHidden);
        getCriteria().setViewed(notificationIsViewed);
        getCriteria().setUnread(notificationIsUnread);
        getCriteria().setOnlyLocal(notificationOnlyLocal);
        getCriteria().setSearchNotificationStartDate(searchNotificationStartDate);
        getCriteria().setSearchNotificationEndDate(searchNotificationEndDate);
        getCriteria().setWasDateChanged(wasDateChanged);
        getCriteria().setUser(getUser());
    }

    /**
     * Attach current user to the 'ViewedBy' property
     * 
     * @return String
     */
    public String updateViewedBy() {

        NotificationVo notification = null;

        if (notificationId > 0) {
            notification = notificationService.retrieve(Long.toString(notificationId));
        }

        String status = notificationService.retrieveNotificationStatus(getUser().getId(), notificationId);

        if (NotificationStatusType.HIDDEN.name().equals(status)) {
            notificationService.updateNotificationStatus(notificationId, getUser(),
                NotificationStatusType.VIEWEDHIDDEN, notification.getModifiedBy() + ", " + getUser().getUsername());
        }
        else if (NotificationStatusType.NEWINSTANCE.name().equals(status)) {
            notificationService.updateNotificationStatus(notificationId, getUser(), NotificationStatusType.VIEWED,
                notification.getModifiedBy() + ", " + getUser().getUsername());
        }

        return SUCCESS;
    }

    /**
     * 
     * @return notificationService property
     */
    public NotificationService getNotificationService() {
        return notificationService;
    }

    /**
     * 
     * @param notificationService notificationService property
     */
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * 
     * @return requestCriteria property
     */
    public SearchNotificationsCriteriaVo getCriteria() {
        return criteria;
    }

    /**
     * 
     * @param criteria criteria property
     */
    public void setCriteria(SearchNotificationsCriteriaVo criteria) {
        this.criteria = criteria;
    }

    /**
     * 
     * @return printTemplate property
     */
    public PrintTemplateVo getNotificationPrintTemplate() {
        return notificationPrintTemplate;
    }

    /**
     * 
     * @return requests property
     */
    public List<NotificationSummaryVo> getNotifications() {
        return notifications;
    }

    /**
     * 
     * @param notifications results property
     */
    public void setNotifications(List<NotificationSummaryVo> notifications) {
        this.notifications = notifications;
    }

    /**
     * 
     * @return notificationIsAll property
     */
    public boolean isNotificationIsAll() {
        return notificationIsAll;
    }

    /**
     * 
     * @param notificationIsAll all property
     */
    public void setNotificationIsAll(boolean notificationIsAll) {
        this.notificationIsAll = notificationIsAll;
    }

    /**
     * 
     * 
     * @return notificationIsApproved
     */
    public boolean isNotificationIsApproved() {
        return notificationIsApproved;
    }

    /**
     * 
     * 
     * @param notificationIsApproved property
     */
    public void setNotificationIsApproved(boolean notificationIsApproved) {
        this.notificationIsApproved = notificationIsApproved;
    }

    /**
     * 
     * 
     * @return notificationIsHidden
     */
    public boolean isNotificationIsHidden() {
        return notificationIsHidden;
    }

    /**
     * 
     * 
     * @param notificationIsHidden property
     */
    public void setNotificationIsHidden(boolean notificationIsHidden) {
        this.notificationIsHidden = notificationIsHidden;
    }

    /**
     * 
     * 
     * @return notificationIsInactivated
     */
    public boolean isNotificationIsInactivated() {
        return notificationIsInactivated;
    }

    /**
     * 
     * 
     * @param notificationIsInactivated property
     */
    public void setNotificationIsInactivated(boolean notificationIsInactivated) {
        this.notificationIsInactivated = notificationIsInactivated;
    }

    /**
     * 
     * 
     * @return notificationIsModified
     */
    public boolean isNotificationIsModified() {
        return notificationIsModified;
    }

    /**
     * 
     * 
     * @param notificationIsModified property
     */
    public void setNotificationIsModified(boolean notificationIsModified) {
        this.notificationIsModified = notificationIsModified;
    }

    /**
     * 
     * 
     * @return notificationIsRejected
     */
    public boolean isNotificationIsRejected() {
        return notificationIsRejected;
    }

    /**
     * 
     * 
     * @param notificationIsRejected property
     */
    public void setNotificationIsRejected(boolean notificationIsRejected) {
        this.notificationIsRejected = notificationIsRejected;
    }

    /**
     * 
     * 
     * @return notificationIsViewed
     */
    public boolean isNotificationIsViewed() {
        return notificationIsViewed;
    }

    /**
     * 
     * 
     * @param notificationIsViewed property
     */
    public void setNotificationIsViewed(boolean notificationIsViewed) {
        this.notificationIsViewed = notificationIsViewed;
    }

    /**
     * 
     * @return searchNotificationEndDate property
     */
    public Date getSearchNotificationEndDate() {
        return searchNotificationEndDate;
    }

    /**
     * 
     * @param searchNotificationEndDate searchNotificationEndDate property
     */
    public void setSearchNotificationEndDate(Date searchNotificationEndDate) {
        this.searchNotificationEndDate = searchNotificationEndDate;

    }

    /**
     * 
     * @return searchNotificationStartDate property
     */
    public Date getSearchNotificationStartDate() {
        return searchNotificationStartDate;
    }

    /**
     * 
     * @param searchNotificationStartDate searchNotificationStartDate property
     */
    public void setSearchNotificationStartDate(Date searchNotificationStartDate) {
        this.searchNotificationStartDate = searchNotificationStartDate;
    }

    /**
     * 
     * @param notificationPrintTemplate notificationPrintTemplate property
     */
    public void setNotificationPrintTemplate(PrintTemplateVo notificationPrintTemplate) {
        this.notificationPrintTemplate = notificationPrintTemplate;
    }

    /**
     * 
     * @return notificationCount property
     */
    public int getNotificationCount() {
        return notificationCount;
    }

    /**
     * 
     * @param notificationCount notificationCount property
     */
    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    /**
     * Get the Notification Id
     * 
     * @return notificationId
     */
    public Long getNotificationId() {
        return notificationId;
    }

    /**
     * Set the NotificationID
     * 
     * @param notificationId NotificationId
     */
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Get the NotificationStatus
     * 
     * @return The NotificationStatus
     */
    public String getNotificationStatus() {
        return notificationStatus;
    }

    /**
     * Set the NotificationStatus
     * 
     * @param notificationStatus NotificationStatus
     */
    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    /**
     * Get the ItemId
     * 
     * @return itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Set the itemId
     * 
     * @param itemId itemId
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Get the Item Type
     * 
     * @return itemType
     */
    public EntityType getItemType() {
        return itemType;
    }

    /**
     * set the itemType
     * 
     * @param itemType itemType
     */
    public void setItemType(EntityType itemType) {
        this.itemType = itemType;
    }

    /**
     * Get the NotificationSummary
     * 
     * @return NotificationSummary
     */
    public NotificationSummaryVo getNotificationSummary() {
        return notificationSummary;
    }

    /**
     * Set the NotificationSummary
     * 
     * @param notificationSummary NotificationSummary
     */
    public void setNotificationSummary(NotificationSummaryVo notificationSummary) {
        this.notificationSummary = notificationSummary;
    }

    /**
     * 
     * @return viewedBy property
     */
    public String getViewedBy() {
        return viewedBy;
    }

    /**
     * 
     * @param viewedBy viewedBy property
     */
    public void setViewedBy(String viewedBy) {
        this.viewedBy = viewedBy;
    }

    /**
     * 
     * @return notificationIsUnread property
     */
    public boolean isNotificationIsUnread() {
        return notificationIsUnread;
    }

    /**
     * 
     * @param notificationIsUnread notificationIsUnread property
     */
    public void setNotificationIsUnread(boolean notificationIsUnread) {
        this.notificationIsUnread = notificationIsUnread;
    }

    /**
     * 
     * @return notificationIsOnlylocal property
     */
    public boolean isNotificationOnlyLocal() {
        return notificationOnlyLocal;
    }

    /**
     * 
     * @param notificationIsOnlylocal notificationIsOnlylocal property
     */
    public void setNotificationOnlyLocal(boolean notificationIsOnlylocal) {
        this.notificationOnlyLocal = notificationIsOnlylocal;
    }

    /**
     * 
     * @return notificationIsDateUsed property
     */
    public boolean isNotificationDateUse() {
        return notificationDateUse;
    }

    /**
     * 
     * @param notificationIsDateUsed notificationIsDateUsed property
     */
    public void setNotificationDateUse(boolean notificationIsDateUsed) {
        this.notificationDateUse = notificationIsDateUsed;
    }

}