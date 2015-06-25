/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.callback;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Exposes managed item callback methods to the interface project.
 */
public interface MigrationResponseCapabilityCallback {

    /**
     * MigrationResponseCapabilityCallback
     * Search for {@link ManagedItemVo} using the given {@link SearchCriteriaVo} as search criteria.
     * 
     * @param searchCriteria criteria for the search
     * @return List<ManagedItemVo> Managed items matching the given search criteria
     * @throws ValidationException if the given {@link SearchCriteriaVo} is invalid
     */
    List<ManagedItemVo> search(SearchCriteriaVo searchCriteria) throws ValidationException;

    /**
     * MigrationResponseCapabilityCallback
     * Create a new instance of the ManagedItemVo. Return the same ManagedItemVo with its new ID.
     * 
     * @param item ManagedItemVo to create
     * @param user UserVo performing create
     * @return created ManagedItemVo
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    ProcessedItemVo create(ManagedItemVo item, UserVo user) throws ValidationException;
     
     /**
      * MigrationResponseCapabilityCallback
      * Save an updated ManagedItemVo.
      * 
      * @param managedItem to be updated
      * @param user UserVo
      * @return ManagedItemVo
      * @throws ValidationException ValidationException
      */
    ManagedItemVo update(ManagedItemVo managedItem, UserVo user) throws ValidationException;

    /**
     * MigrationResponseCapabilityCallback
     * Save an updated ManagedItemVo.
     * 
     * @param ndcVoFromVista This is the VO that will be updated
     * @param ndcItemAuditHistoryVo This is the item audit history records
     * @param bCreate True if this is a new record
     * @param productVoFromVista This is the product vo that will be updated
     * @param productItemAuditHistoryVo This is the product item audit history record
     * @throws ValidationException ValidationException
     */
    void processFromLocalVista(ManagedItemVo ndcVoFromVista, ItemAuditHistoryVo ndcItemAuditHistoryVo, 
        boolean bCreate, ManagedItemVo productVoFromVista, 
        ItemAuditHistoryVo productItemAuditHistoryVo) throws ValidationException;

    /**
     * MigrationResponseCapabilityCallback
     * Create a new managed Item.
     * 
     * @param type This is the type of VO being created.
     * @return ManagedItemVo
     */
    ManagedItemVo retrieveBlankTemplate(EntityType type);


      
}
