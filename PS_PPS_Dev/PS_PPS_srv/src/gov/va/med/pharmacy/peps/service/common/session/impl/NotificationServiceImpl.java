/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.NotificationCapability;
import gov.va.med.pharmacy.peps.service.common.session.NotificationService;


/**
 * Search and retrieve notifications.
 */
public class NotificationServiceImpl implements NotificationService {

    /** notificationCapability */
    private NotificationCapability notificationCapability;

    /**
     * Search for the pending requests
     * 
     * @param criteria for searchCriteria
     * @return Collection<RequestSummaryVo>
     * @throws ItemNotFoundException exception
     */
    public List<NotificationSummaryVo> search(SearchNotificationsCriteriaVo criteria) throws ItemNotFoundException {
        return notificationCapability.search(criteria);
    }

    /**
     * Update Notification status
     * 
     * @param notificationId Long
     * @param user {@link UserVo} performing the action
     * @param status NotificationStatusType
     * @param viewedBy String
     */
    public void updateNotificationStatus(Long notificationId, UserVo user, NotificationStatusType status, String viewedBy) {
        notificationCapability.updateNotificationStatus(notificationId, user, status, viewedBy);
    }

    /**
     * simple retrieve by id
     * 
     * @param id notification id
     * @return NotificationVo
     */
    public NotificationVo retrieve(String id) {

        return notificationCapability.retrieve(id);
    }

    /**
     * Retrieve system notification count
     * 
     * @return notification count
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.NotificationService#retrieveNotificationCount()
     */
    public int retrieveNotificationCount() {
        return notificationCapability.retrieveNotificationCount();
    }

    /**
     * Retrieve Notification status
     * 
     * @param userId Long
     * @param notificationId Long
     * @return NotificationStatusVo
     */
    public String retrieveNotificationStatus(Long userId, Long notificationId) {

        return notificationCapability.retrieveNotificationStatus(userId, notificationId);

    }

    /**
     * getNotificationCapability
     * @return notificationCapability property
     */
    public NotificationCapability getNotificationCapability() {
        return notificationCapability;
    }

    /**
     * setNotificationCapability
     * @param notificationCapability notificationCapability property
     */
    public void setNotificationCapability(NotificationCapability notificationCapability) {
        this.notificationCapability = notificationCapability;
    }

}
