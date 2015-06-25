/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusType;
import gov.va.med.pharmacy.peps.common.vo.NotificationStatusVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.NotificationVo;
import gov.va.med.pharmacy.peps.common.vo.SearchNotificationsCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NotificationDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplItemAuditHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNotificationDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNotificationStatusDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplUserDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplItemAuditHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationStatusDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationStatusDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplUserDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NotificationConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NotificationStatusConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NotificationSummaryConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on notification
 */
public class NotificationDomainCapabilityImpl implements NotificationDomainCapability {

    private EplNotificationDao eplNotificationDao;
    private ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability;
    private EplNotificationStatusDao eplNotificationStatusDao;
    private EplItemAuditHistoryDao eplItemAuditHistoryDao;
    private EplUserDao eplUserDao;
    private SeqNumDomainCapability seqNumDomainCapability;
    private NotificationConverter notificationConverter;
    private NotificationStatusConverter notificationStatusConverter;
    private NotificationSummaryConverter notificationSummaryConverter;

    /**
     * Insert the given NotificationVo.
     * 
     * @param notification NotificationVo
     * @param user {@link UserVo} performing the action
     * @return NotificationVo with new ID
     */
    public NotificationVo create(NotificationVo notification, UserVo user) {

        if (notification.getId() == null) {
            notification.setId(seqNumDomainCapability.generateNotificationId(user));
        }

        EplNotificationDo notificationDo = notificationConverter.convert(notification);
        eplNotificationDao.insert(notificationDo, user);

        // if there are notification statuses insert them
        // otherwise the following code will create a notification status for every user in the system
        // with a status of NEW INSTANCE
        if (notification.getNotificationStatus() != null && notification.getNotificationStatus().size() > 0) {
            for (NotificationStatusVo statusVo : notification.getNotificationStatus()) {
                EplNotificationStatusDo statusDo = notificationStatusConverter.convert(statusVo, notificationDo);
                eplNotificationStatusDao.insert(statusDo, user);
            }
        } else {
            List<EplUserDo> users = eplUserDao.retrieve();

            for (EplUserDo userDo : users) {
                EplNotificationStatusDo statusDo = new EplNotificationStatusDo();

                statusDo.setStatus(NotificationStatusType.NEWINSTANCE.name());

                EplNotificationStatusDoKey key = new EplNotificationStatusDoKey();
                key.setNotificationIdFk(notificationDo.getId());
                key.setUserIdFk(userDo.getId());
                statusDo.setKey(key);

                eplNotificationStatusDao.insert(statusDo, user);
            }
        }

        // if notification has item audit history

        List<ItemAuditHistoryVo> audits = new ArrayList<ItemAuditHistoryVo>();

        if (notification.getItemAudits() != null && notification.getItemAudits().size() > 0) {
            for (ItemAuditHistoryVo itemVo : notification.getItemAudits()) {
                ItemAuditHistoryVo returnedAudit = itemAuditHistoryDomainCapability.create(itemVo, notificationDo, user);
                audits.add(returnedAudit);
            }
        }

        notification.setItemAudits(audits);

        return notification;
    }

    /**
     * Retrieve Notification status
     * 
     * @param userId Long
     * @param notificationId Long
     * @return NotificationStatusVo
     */
    public String retrieveNotificationStatus(Long userId, Long notificationId) {
        StringBuffer query = new StringBuffer();

        query.append(HqlBuilder.create("SELECT stat FROM").append(EplNotificationStatusDo.class).append(
            "stat where stat.key.userIdFk"));

        query.append(" =  " + userId);
        query.append(" AND  stat.key.notificationIdFk = ");
        query.append(notificationId);

        List<EplNotificationStatusDo> returnedDos = eplNotificationStatusDao.executeHqlQuery(query.toString());

        if (returnedDos != null && returnedDos.size() > 0) {
            return returnedDos.get(0).getStatus();
        } else {
            return null;
        }

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
        EplNotificationStatusDo data = notificationStatusConverter.convert(notificationId, user.getId(), status);
      
        eplNotificationStatusDao.update(data, user);

        // update notification's modified by to viewed By
        String hql = "update EplNotificationDo set lastModifiedBy = '" + viewedBy + "' where id = " + notificationId;

        eplNotificationDao.executeQuery(hql);

    }

    /**
     * Retrieves a count of all notifications in the database
     * 
     * @return number of notifications
     */
    public int retrieveNotificationCount() {
        Criteria all = eplNotificationDao.getCriteria();
        int count = eplNotificationDao.getCount(all);

        return count;
    }

    /**
     * search for notifications
     * 
     * @param searchNotificationsCriteria Criteria to search by
     * @return List<NotificationSummaryVo>
     */
    public List<NotificationSummaryVo> search(SearchNotificationsCriteriaVo searchNotificationsCriteria) {

        if (noFilterSelected(searchNotificationsCriteria)) {
            return new ArrayList<NotificationSummaryVo>(0);
        }

        Criteria notficationCriteria = eplNotificationDao.getCriteria();
        notficationCriteria.setMaxResults(PPSConstants.I100);

        notficationCriteria.add(Restrictions.ge(EplNotificationDo.CREATED_DATE_TIME, searchNotificationsCriteria
            .getSearchNotificationStartDate()));
        notficationCriteria.add(Restrictions.le(EplNotificationDo.CREATED_DATE_TIME, searchNotificationsCriteria
            .getSearchNotificationEndDate()));
        notficationCriteria.addOrder(Order.desc(EplNotificationDo.CREATED_DATE_TIME));

        if (searchNotificationsCriteria.getUser() != null) {
            Disjunction or = Restrictions.disjunction();

            boolean isEmpty = true;

            for (NotificationType type : searchNotificationsCriteria.getUser().getAllNotifications()) {
                if (!searchNotificationsCriteria.isOnlyLocal()
                    || (searchNotificationsCriteria.isOnlyLocal() && type.canBeLocalOnly())) {
                    isEmpty = false;
                    or.add(Restrictions.eq(EplNotificationDo.NOTIFICATION_TYPE, type.name()));
                }
            }

            // If the user has no notification preferences selected, there's nothing to display
            if (isEmpty) {
                return new ArrayList<NotificationSummaryVo>(0);
            }

            notficationCriteria.add(or);
        }

        // add user notification status constraints
        if (searchNotificationsCriteria.getUser() != null) {
            Criteria notificationStatusCriteria = notficationCriteria.createCriteria("eplNotificationStatuses");
            notificationStatusCriteria.add(Restrictions.eq("eplUser.id", searchNotificationsCriteria.getUser().getId()));
            Collection<String> notificationStatusTypes = new ArrayList<String>();

            if (searchNotificationsCriteria.isViewed()) {
                notificationStatusTypes.add(NotificationStatusType.VIEWED.name());
            }

            if (searchNotificationsCriteria.isUnread()) {
                notificationStatusTypes.add(NotificationStatusType.NEWINSTANCE.name());
            }

            if (searchNotificationsCriteria.isHidden()) {
                notificationStatusTypes.add(NotificationStatusType.HIDDEN.name());
                notificationStatusTypes.add(NotificationStatusType.VIEWEDHIDDEN.name());
            }

            notificationStatusCriteria.add(Restrictions.in(EplNotificationStatusDo.STATUS, notificationStatusTypes));
        }

        List<EplNotificationDo> notificationCollection = notficationCriteria.list();

        List<NotificationSummaryVo> results = new ArrayList<NotificationSummaryVo>();

        for (EplNotificationDo notification : notificationCollection) {

            StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM").append(
                EplItemAuditHistoryDo.class).append("item WHERE item.").toString());

            queryBuffer.append(EplItemAuditHistoryDo.NOTIFICATION).append(" = ").append(notification.getId());

            Collection<EplItemAuditHistoryDo> audits = eplItemAuditHistoryDao.executeHqlQuery(queryBuffer.toString());

            NotificationSummaryVo summary = convertNotificationSummary(notification, audits, searchNotificationsCriteria
                .getUser());

            if (searchNotificationsCriteria.isOnlyLocal()) {
                results.add(summary);
            }

        }

        return results;
    }

    /**
     * adds to the collection of notification summaries using specified notification and collection of auditable items
     * 
     * @param notification notification to add to collection
     * @param audits audit collection
     * @param user UserVo
     * @return {@link NotificationSummaryVo}
     */
    private NotificationSummaryVo convertNotificationSummary(EplNotificationDo notification,
                                                             Collection<EplItemAuditHistoryDo> audits, UserVo user) {
        NotificationSummaryVo summary = null;

        if (audits.size() == 0) {
            summary = notificationSummaryConverter.convert(notification, user);
        }

        for (EplItemAuditHistoryDo audit : audits) {
            summary = notificationSummaryConverter.convert(notification, user, audit);
            break;
        }

        return summary;
    }

    /**
     * Determine if at least one search filter is selected.
     * 
     * @param criteria VO that has filters
     * @return boolean true if no filters are selected, false if at least one is selected
     */
    private boolean noFilterSelected(SearchNotificationsCriteriaVo criteria) {
        return !(criteria.isUnread() || criteria.isViewed() || criteria.isHidden());

    }

    /**
     * simple retrieve by id
     * 
     * @param id notification id
     * @return NotificationVo
     */
    public NotificationVo retrieve(String id) {
        EplNotificationDo notificationDo = this.eplNotificationDao.retrieve(new Long(id));
        NotificationVo notificationVo = notificationConverter.convert(notificationDo);

        return notificationVo;
    }

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
    public NotificationVo createSystemNotification(ManagedItemVo managedItem, UserVo userClient, String siteNum, String msg,
                                                   Throwable t) {

        NotificationVo notifVo = new NotificationVo();
        notifVo.setNotificationType(NotificationType.SYSTEM_EVENT);

        notifVo.setId(seqNumDomainCapability.generateNotificationId(userClient));

        EplNotificationDo notificationDo = notificationConverter.convert(notifVo);
        EplNotificationDo returnedDo = eplNotificationDao.insert(notificationDo, userClient);
        Set<EplNotificationStatusDo> statusDos = new HashSet<EplNotificationStatusDo>();

        // Create a system notification status for every user in the system.
        List<EplUserDo> users = eplUserDao.retrieve();

        for (EplUserDo user : users) {
            EplNotificationStatusDo statusDo = new EplNotificationStatusDo();

            EplNotificationStatusDoKey key = new EplNotificationStatusDoKey();
            key.setNotificationIdFk(notificationDo.getId());
            key.setUserIdFk(user.getId());

            statusDo.setKey(key);
            statusDo.setEplUser(user);
            statusDo.setEplNotification(returnedDo);
            statusDo.setStatus(NotificationStatusType.SYSTEM.name());

            eplNotificationStatusDao.insert(statusDo, userClient);
            statusDos.add(statusDo);
        }

        returnedDo.setEplNotificationStatuses(statusDos);

        Collection<ItemAuditHistoryVo> itemAudits = new ArrayList<ItemAuditHistoryVo>();
        String username = userClient == null ? "UNKNOWN" : userClient.getUsername();
        
        ItemAuditHistoryVo itemAudit = new ItemAuditHistoryVo();
        itemAudit.setAuditItemType(managedItem.getEntityType());
        itemAudit.setAuditItemId(managedItem.getId());
        itemAudit.setSiteName(siteNum); 
        itemAudit.setEventCategory(EventCategory.SYSTEM_EVENT);
        itemAudit.setReason(msg); 
        itemAudit.setOldValue("");
        itemAudit.setNewValue("");
        itemAudit.setUsername(username);
        
        
        itemAudits.add(itemAuditHistoryDomainCapability.create(itemAudit, returnedDo, userClient));

        // Since the Hibernate session is cleared after insert, we need to re-retrieve and convert the notification
        return retrieve(String.valueOf(returnedDo.getId()));
    }

    /**
     * set the EplNotificationDao property
     * 
     * @param eplNotificationDao eplNotificationDao property
     */
    public void setEplNotificationDao(EplNotificationDao eplNotificationDao) {
        this.eplNotificationDao = eplNotificationDao;
    }

    /**
     * setItemAuditHistoryDomainCapability
     * @param itemAuditHistoryDomainCapability itemAuditHistoryDomainCapability property
     */
    public void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability) {
        this.itemAuditHistoryDomainCapability = itemAuditHistoryDomainCapability;
    }

    /**
     * setEplNotificationStatusDao
     * @param eplNotificationStatusDao eplNotificationStatusDao property
     */
    public void setEplNotificationStatusDao(EplNotificationStatusDao eplNotificationStatusDao) {
        this.eplNotificationStatusDao = eplNotificationStatusDao;
    }

    /**
     * setEplItemAuditHistoryDao
     * @param eplItemAuditHistoryDao eplItemAuditHistoryDao property
     */
    public void setEplItemAuditHistoryDao(EplItemAuditHistoryDao eplItemAuditHistoryDao) {
        this.eplItemAuditHistoryDao = eplItemAuditHistoryDao;
    }

    /**
     * getEplUserDao
     * @return eplUserDao property
     */
    public EplUserDao getEplUserDao() {
        return eplUserDao;
    }

    /**
     * setEplUserDao
     * @param eplUserDao eplUserDao property
     */
    public void setEplUserDao(EplUserDao eplUserDao) {
        this.eplUserDao = eplUserDao;
    }

    /**
     * Uses a Criteria object to execute Count(*)
     * 
     * @param criteria hibernate criteria class
     * @return count of matching rows
     */
    public int getCount(Criteria criteria) {
        return eplNotificationDao.getCount(criteria);
    }

    /**
     * setSeqNumDomainCapability
     * @param seqNumDomainCapability seqNumDomainCapability property
     */
    public void setSeqNumDomainCapability(SeqNumDomainCapability seqNumDomainCapability) {
        this.seqNumDomainCapability = seqNumDomainCapability;
    }

    /**
     * setNotificationConverter
     * @param notificationConverter notificationConverter property
     */
    public void setNotificationConverter(NotificationConverter notificationConverter) {
        this.notificationConverter = notificationConverter;
    }

    /**
     * setNotificationStatusConverter
     * @param notificationStatusConverter notificationStatusConverter property
     */
    public void setNotificationStatusConverter(NotificationStatusConverter notificationStatusConverter) {
        this.notificationStatusConverter = notificationStatusConverter;
    }

    /**
     * setNotificationSummaryConverter
     * @param notificationSummaryConverter notificationSummaryConverter property
     */
    public void setNotificationSummaryConverter(NotificationSummaryConverter notificationSummaryConverter) {
        this.notificationSummaryConverter = notificationSummaryConverter;
    }
}
