/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.NotificationService;


/**
 * Search for notifications
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class NotificationServiceBean extends AbstractPepsStatelessSessionBean<NotificationService> implements
    NotificationService {
    private static final long serialVersionUID = 1L;

    /**
     * Search for notifications with given criteria.
     * 
     * @param criteria criteria to search by
     * @return List<NotificationSummaryVo> list of notifications
     * @throws ItemNotFoundException exception
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.NotificationService
     *      #search(gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo)
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<NotificationSummaryVo> search(SearchNotificationsCriteriaVo criteria) throws ItemNotFoundException {
        return getService().search(criteria);
    }

    /**
     * Update Notification status
     * 
     * @param notificationId Long
     * @param user {@link UserVo} performing the action
     * @param status NotificationStatusType
     * @param viewedBy user who viewed the notification
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public void updateNotificationStatus(Long notificationId, UserVo user, NotificationStatusType status, String viewedBy) {
        getService().updateNotificationStatus(notificationId, user, status, viewedBy);
    }

    /**
     * simple retrieve by id
     * 
     * @param id notification id
     * @return NotificationVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public NotificationVo retrieve(String id) {
        return getService().retrieve(id);
    }

    /**
     * Retrieve notification count
     * 
     * @return notification count
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public int retrieveNotificationCount() {
        return getService().retrieveNotificationCount();
    }

    /**
     * Retrieve Notification status
     * 
     * @param userId Long
     * @param notificationId Long
     * @return NotificationStatusVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public String retrieveNotificationStatus(Long userId, Long notificationId) {
        return getService().retrieveNotificationStatus(userId, notificationId);
    }

}
