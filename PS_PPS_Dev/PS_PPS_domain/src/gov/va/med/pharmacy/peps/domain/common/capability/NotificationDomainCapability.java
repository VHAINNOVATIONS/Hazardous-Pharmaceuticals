/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on notification
 */
public interface NotificationDomainCapability {

    /**
     * Insert the given NotificationVo.
     * 
     * @param notification NotificationVo
     * @param user {@link UserVo} performing the action
     * @return NotificationVo inserted NotificationVo with new ID
     */
    NotificationVo create(NotificationVo notification, UserVo user);

    /**
     * Search notifications
     * 
     * @param criteria Criteria to search by
     * @return List<NotificationVo>
     */
    List<NotificationSummaryVo> search(SearchNotificationsCriteriaVo criteria);

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
     * Retrieves a count of all notifications in the database
     * 
     * @return number of notifications
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

    /**
     * Insert a system NotificationVo.
     * 
     * @param managedItem The item to log a system event for
     * @param userClient The user using the app that invoked this method
     * @param siteNum The site number for the app this method is invoked on behalf of
     * @param msg A message to log into the notification.
     * @param t Throwable
     * @return NotificationVo the new notification VO
     */
    NotificationVo createSystemNotification(ManagedItemVo managedItem, UserVo userClient, String siteNum, String msg,
                                            Throwable t);

}
