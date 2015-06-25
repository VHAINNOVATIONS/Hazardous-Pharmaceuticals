/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform security actions on a user
 */
public interface NotificationCapability {

    /**
     * search notifications
     * 
     * @param criteria Criteria to search by
     * @return List<NotificationVo>
     */
    List<NotificationSummaryVo> search(SearchNotificationsCriteriaVo criteria);

    /**
     * Update Notification status for NotificationCapability.
     * 
     * @param notificationId Long
     * @param user {@link UserVo} performing the action
     * @param status NotificationStatusType
     * @param viewedBy String
     */
    void updateNotificationStatus(Long notificationId, UserVo user, NotificationStatusType status, String viewedBy);

    /**
     * simple retrieve by id for NotificationCapability.
     * 
     * @param id notification id
     * @return NotificationVo
     */
    NotificationVo retrieve(String id);

    /**
     * Retrieve notification count for NotificationCapability.
     * 
     * @return notification count
     */
    int retrieveNotificationCount();

    /**
     * Retrieve Notification status for NotificationCapability.
     * 
     * @param userId Long
     * @param notificationId Long
     * @return NotificationStatusVo
     */
    String retrieveNotificationStatus(Long userId, Long notificationId);
}
