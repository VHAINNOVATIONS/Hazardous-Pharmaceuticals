/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;


/**
 * Perform CRUD operations on item audit history
 */
public interface ItemAuditHistoryDomainCapability {

    /**
     * Insert the given ItemAuditHistoryVo.
     * 
     * @param itemAuditHistory ItemAuditHistoryVo
     * @param notif EplNotificationDo
     * @param user {@link UserVo} performing the action
     * @return ItemAuditHistoryVo with new ID
     */
    ItemAuditHistoryVo create(ItemAuditHistoryVo itemAuditHistory, EplNotificationDo notif, UserVo user);

    /**
     * Retrieves item audit history for the given item Type and Item Id
     * 
     * @param itemId String
     * @param itemType String
     * @return ItemAuditHistoryVo collection
     */
    Collection<ItemAuditHistoryVo> retreiveByAuditItemId(String itemId, String itemType);

    /**
     * Returns true if the the item audit history record already exists in the database for the given id, false otherwise
     * 
     * @param id String
     * @return boolean
     */
    boolean exists(String id);

    /**
     * Re-associate the Item Audit History records currently associated with the oldItem with the newItem.
     * 
     * @param oldItem {@link ManagedItemVo} with IAH records to re-associate
     * @param newItem {@link ManagedItemVo} to association IAH records with
     * @param user {@link UserVo} performing the action
     */
    void reassociate(ManagedItemVo oldItem, ManagedItemVo newItem, UserVo user);
}
