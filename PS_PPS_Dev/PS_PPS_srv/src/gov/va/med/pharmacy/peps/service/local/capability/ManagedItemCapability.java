/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.capability;


import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;


/**
 * Local implementation of ManagedItemCapability
 */
public interface ManagedItemCapability extends gov.va.med.pharmacy.peps.service.common.capability.ManagedItemCapability {

    /**
     * This method processes new or existing items passed from national to local.
     * 
     * @param vo ManagedItemVo
     * @return ManagedItemVo
     */
    ManagedItemVo processFromNational(ManagedItemVo vo);
    
    /**
     * This method processes new and existing items passed from the local Vista
     * @param ndcVoFromVista ManagedItemVo the item to add or update
     * @param ndcItemAuditHistoryVo the list of audit changes
     * @param bCreate True if this is a new item and false if this is an update
     * @param productVoFromVista  This is the product vo coming from Vista
     * @param productItemAuditHistoryVo  This is the item audit history objects for the product
     * @throws Exception
     */
    
//    void processFromLocalVista(ManagedItemVo ndcVoFromVista, ItemAuditHistoryVo ndcItemAuditHistoryVo, boolean bCreate,
//                          ManagedItemVo productVoFromVista, ItemAuditHistoryVo productItemAuditHistoryVo) throws Exception;

}
