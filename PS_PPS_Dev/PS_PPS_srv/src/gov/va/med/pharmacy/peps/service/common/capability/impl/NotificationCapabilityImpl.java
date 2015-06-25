/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NotificationDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.common.capability.NotificationCapability;


/**
 * Perform security actions on a user
 */
public class NotificationCapabilityImpl implements NotificationCapability {

    private NotificationDomainCapability notificationDomainCapability;
    private ManagedItemCapability managedItemCapability;

    /**
     * search notifications
     * 
     * @param criteria Criteria to search by
     * @return List<NotificationVo>
     */
    public List<NotificationSummaryVo> search(SearchNotificationsCriteriaVo criteria) {
        return notificationDomainCapability.search(criteria);
    }

    /**
     * Update the user's notification status
     * 
     * @param notificationId Long
     * @param user {@link UserVo} performing the action
     * @param status NotificationStatusType
     * @param viewedBy String
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.NotificationCapability#updateNotificationStatus(
     *      java.lang.Long, java.lang.Long, gov.va.med.pharmacy.peps.common.vo.NotificationStatusType)
     */
    public void updateNotificationStatus(Long notificationId, UserVo user, NotificationStatusType status, String viewedBy) {
        notificationDomainCapability.updateNotificationStatus(notificationId, user, status, viewedBy);
    }

    /**
     * simple retrieve by id
     * 
     * @param id notification id
     * @return NotificationVo
     */
    public NotificationVo retrieve(String id) {

        return notificationDomainCapability.retrieve(id);
    }

    /**
     * Retrieve notification count
     * 
     * @return notification count
     */
    public int retrieveNotificationCount() {
        return notificationDomainCapability.retrieveNotificationCount();
    }

    /**
     * Retrieve Notification status
     * 
     * @param userId Long
     * @param notificationId Long
     * @return NotificationStatusVo
     */
    public String retrieveNotificationStatus(Long userId, Long notificationId) {

        return notificationDomainCapability.retrieveNotificationStatus(userId, notificationId);

    }

    /**
     * getNotificationDomainCapability for NotificationCapabilityImpl.
     * @return notificationDomainCapability property
     */
    public NotificationDomainCapability getNotificationDomainCapability() {
        return notificationDomainCapability;
    }

    /**
     * setNotificationDomainCapability for NotificationCapabilityImpl.
     * @param notificationDomainCapability notificationDomainCapability property
     */
    public void setNotificationDomainCapability(NotificationDomainCapability notificationDomainCapability) {
        this.notificationDomainCapability = notificationDomainCapability;
    }

    /**
     * getManagedItemCapability for the NotificationCapabilityImpl.
     * @return managedItemCapability property
     */
    public ManagedItemCapability getManagedItemCapability() {
        return managedItemCapability;
    }

    /**
     * setManagedItemCapability for the NotificationCapabilityImpl.
     * @param managedItemCapability managedItemCapability property
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }
}
