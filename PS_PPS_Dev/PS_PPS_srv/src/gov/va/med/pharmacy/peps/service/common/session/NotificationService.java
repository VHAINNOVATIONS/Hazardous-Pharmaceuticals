/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Search and retrieve notifications.
 */
public interface NotificationService {

    /**
     * Search for the pending requests
     * 
     * @param criteria for searchCriteria
     * @return List<RequestSummaryVo>
     * @throws ItemNotFoundException ItemNotFoundException
     */
    List<NotificationSummaryVo> search(SearchNotificationsCriteriaVo criteria) throws ItemNotFoundException;

    /**
     * Update Notification status
     * 
     * @param notificationId Long
     * @param user {@link UserVo} performing the action
     * @param status NotificationStatusType
     * @param viewedBy String
     */
    void updateNotificationStatus(Long notificationId, UserVo user, NotificationStatusType status, String viewedBy);

    /**
     * simple retrieve by id
     * 
     * @param id notification id
     * @return NotificationVo
     */
    NotificationVo retrieve(String id);

    /**
     * Retrieve the count of all notifications in the system
     * 
     * @return notification count
     */
    int retrieveNotificationCount();

    /**
     * Retrieve Notification status
     * 
     * @param userId Long
     * @param notificationId Long
     * @return NotificationStatusVo
     */
    String retrieveNotificationStatus(Long userId, Long notificationId);

}
