/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability;


import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * National implementation of Managed Item Capabilities
 */
public interface ManagedItemCapability extends gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability {

    /**
     * Inserts a pending item that was created at local.
     * 
     * @param item ManagedItemVo
     * @param user This is the user
     * @return ManagedItemVo
     */
    ManagedItemVo insertFromLocal(ManagedItemVo item, UserVo user);

    /**
     * This method Needs to be here to support the Interface call back method.
     * 
     * This method processes new or existing items passed from the local vista site during the drug
     * accountability update.   The method allows for an NDC to be added or updated or a product to be updated.
     * The bcreate parameter indicates whether this NDC should be added or updated.
     * 
     * @param ndcVoFromVista ManagedItemVo the item to add or update
     * @param ndcItemAuditHistoryVo the list of audit changes
     * @param bCreate True if this is a new item and false if this is an update
     * @param productVoFromVista this is the object from Vista for the product
     * @param productItemAuditHistoryVo this is the item audit history for the product.
     */
    void processFromLocalVista(ManagedItemVo ndcVoFromVista, ItemAuditHistoryVo ndcItemAuditHistoryVo, boolean bCreate,
                               ManagedItemVo productVoFromVista, ItemAuditHistoryVo productItemAuditHistoryVo);

}
