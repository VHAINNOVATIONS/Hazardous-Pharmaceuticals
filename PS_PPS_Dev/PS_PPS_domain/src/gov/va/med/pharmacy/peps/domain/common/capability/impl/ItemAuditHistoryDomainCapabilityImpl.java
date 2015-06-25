/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplItemAuditHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplItemAuditHistoryDetailDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplItemAuditHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.ItemAuditHistoryConverter;


/**
 * Perform CRUD operations on item audit history
 */
public class ItemAuditHistoryDomainCapabilityImpl implements ItemAuditHistoryDomainCapability {

    private EplItemAuditHistoryDao eplItemAuditHistoryDao;
    private SeqNumDomainCapability seqNumDomainCapability;
    private EplItemAuditHistoryDetailDao eplItemAuditHistoryDetailDao;

    private ItemAuditHistoryConverter itemAuditHistoryConverter;

    /**
     * Returns true if the the item audit history record already exists in the database for the given id, false otherwise
     * 
     * @param id String
     * @return boolean
     */
    public boolean exists(String id) {
        return eplItemAuditHistoryDao.exists(Long.valueOf(id));
    }

    /**
     * Insert the given ItemAuditHistoryVo.
     * 
     * @param itemAuditHistory ItemAuditHistoryVo
     * @param notif {@link EplNotificationDo}
     * @param user {@link UserVo} performing the action
     * @return ItemAuditHistoryVo with new ID
     */
    public ItemAuditHistoryVo create(ItemAuditHistoryVo itemAuditHistory, EplNotificationDo notif, UserVo user) {

        // check to see if there is an id , if not assign one
        if (itemAuditHistory.getId() == null) {
            itemAuditHistory.setId(seqNumDomainCapability.generateItemAuditHistoryId(user));
            EplItemAuditHistoryDo data = itemAuditHistoryConverter.convert(itemAuditHistory, notif);
            eplItemAuditHistoryDao.insert(data, user);
            eplItemAuditHistoryDetailDao.insert(data.getEplItemAuditHistoryDetails(), user);

            return itemAuditHistory;
        } else {
            if (!(exists(itemAuditHistory.getId()))) {
                EplItemAuditHistoryDo data = itemAuditHistoryConverter.convert(itemAuditHistory, notif);
                eplItemAuditHistoryDao.insert(data, user);
                eplItemAuditHistoryDetailDao.insert(data.getEplItemAuditHistoryDetails(), user);

            }

            return itemAuditHistory;
        }
    }

    /**
     * Retrieves item audit history for the given item Type and Item Id
     * 
     * @param itemId String
     * @param itemType String
     * @return ItemAuditHistoryVo collection
     */
    public Collection<ItemAuditHistoryVo> retreiveByAuditItemId(String itemId, String itemType) {

        Criteria criteria = eplItemAuditHistoryDao.getCriteria();
        criteria.add(Restrictions.eq(EplItemAuditHistoryDo.AUDIT_ITEM_ID, new Long(itemId)));
        criteria.add(Restrictions.eq(EplItemAuditHistoryDo.AUDIT_ITEM_TYPE, itemType));
        criteria.addOrder(Order.desc(EplItemAuditHistoryDo.CREATED_DTM));
        List<EplItemAuditHistoryDo> auditDos = criteria.list();
        List<ItemAuditHistoryVo> auditVos = new ArrayList<ItemAuditHistoryVo>();

        for (EplItemAuditHistoryDo data : auditDos) {
            auditVos.add(itemAuditHistoryConverter.convert(data));
        }

        List<ItemAuditHistoryVo> fullAuditVos = new ArrayList<ItemAuditHistoryVo>();

        for (ItemAuditHistoryVo audit : auditVos) {
            fullAuditVos.add(audit);

            for (ItemAuditHistoryDetailsVo detail : audit.getDetails()) {
                ItemAuditHistoryVo expandedDetail = detail.convertToItemAuditHistory();
                expandedDetail.setUsername(audit.getUsername());
                expandedDetail.setDtModified(audit.getDtModified());

                fullAuditVos.add(expandedDetail);
            }
        }

        return fullAuditVos;
    }

    /**
     * Re-associate the Item Audit History records currently associated with the oldItem with the newItem.
     * 
     * @param oldItem {@link ManagedItemVo} with IAH records to re-associate
     * @param newItem {@link ManagedItemVo} to association IAH records with
     * @param user {@link UserVo} performing the action
     */
    public void reassociate(ManagedItemVo oldItem, ManagedItemVo newItem, UserVo user) {
        Criteria criteria = eplItemAuditHistoryDao.getCriteria();
        criteria.add(Restrictions.eq(EplItemAuditHistoryDo.AUDIT_ITEM_ID, Long.valueOf(oldItem.getId())));

        List<EplItemAuditHistoryDo> auditHistory = criteria.list();

        for (EplItemAuditHistoryDo eplItemAuditHistoryDo : auditHistory) {
            eplItemAuditHistoryDo.setAuditItemId(Long.valueOf(newItem.getId()));
            eplItemAuditHistoryDao.update(eplItemAuditHistoryDo, user);
        }
    }

    /**
     * set the eplItemAuditHistoryDao property
     * 
     * @param eplItemAuditHistoryDao eplItemAuditHistoryDao property
     */
    public void setEplItemAuditHistoryDao(EplItemAuditHistoryDao eplItemAuditHistoryDao) {
        this.eplItemAuditHistoryDao = eplItemAuditHistoryDao;
    }

    /**
     * setSeqNumDomainCapability
     * @param seqNumDomainCapability seqNumDomainCapability property
     */
    public void setSeqNumDomainCapability(SeqNumDomainCapability seqNumDomainCapability) {
        this.seqNumDomainCapability = seqNumDomainCapability;
    }

    /**
     * setEplItemAuditHistoryDetailDao
     * @param eplItemAuditHistoryDetailDao eplItemAuditHistoryDetailDao property
     */
    public void setEplItemAuditHistoryDetailDao(EplItemAuditHistoryDetailDao eplItemAuditHistoryDetailDao) {
        this.eplItemAuditHistoryDetailDao = eplItemAuditHistoryDetailDao;
    }

    /**
     * setItemAuditHistoryConverter
     * @param itemAuditHistoryConverter itemAuditHistoryConverter property
     */
    public void setItemAuditHistoryConverter(ItemAuditHistoryConverter itemAuditHistoryConverter) {
        this.itemAuditHistoryConverter = itemAuditHistoryConverter;
    }
}
