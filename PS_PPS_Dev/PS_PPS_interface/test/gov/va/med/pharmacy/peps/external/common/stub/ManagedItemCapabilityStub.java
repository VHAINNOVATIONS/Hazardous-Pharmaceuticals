/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.stub;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.callback.ManagedItemCapabilityCallback;


/**
 * Managed item callback stub.
 */
public class ManagedItemCapabilityStub implements ManagedItemCapabilityCallback {

    /** FOUND */
    public static boolean FOUND = true;

    /**
     * Search for {@link ManagedItemVo} using the given {@link SearchCriteriaVo} as search criteria.
     * 
     * @param searchCriteria criteria for the search
     * @return List<ManagedItemVo> Managed items matching the given search criteria
     * @throws ValidationException if the given {@link SearchCriteriaVo} is invalid
     */
    public List<ManagedItemVo> search(SearchCriteriaVo searchCriteria) throws ValidationException {
        List<ManagedItemVo> list = new ArrayList<ManagedItemVo>();

        if (FOUND) {
            list.add(new NdcGenerator().getRandom());
        }

        return list;
    }

    /**
     * Create a new instance of the ManagedItemVo. Return the same ManagedItemVo with its new ID.
     * 
     * @param item ManagedItemVo to create
     * @param user UserVo performing create
     * @return created ManagedItemVo
     * @throws ValidationException if error validating data in ManagedItemVo
     */
    public ProcessedItemVo create(ManagedItemVo item, UserVo user) throws ValidationException {
        return null;
    }

    /**
     * Save an updated ManagedItemVo.
     * 
     * @param managedItem to be updated
     * @param user UserVo
     * @return ManagedItemVo
     * @throws ValidationException exception
     */
    public ManagedItemVo update(ManagedItemVo managedItem, UserVo user) throws ValidationException {

        return null;
    }

    /**
     * This method processes new and existing items passed from the local Vista
     * @param ndcVoFromVista NDC ManagedItemVo the item to add or update
     * @param ndcItemAuditHistoryVo the list of audit changes
     * @param bCreate True if this is a new item and false if this is an update
     * @param productVoFromVista Product Vo from Vista
     * @param productItemAuditHistoryVo  The list of audit changes from the product
     * @throws Exception exception
     */
    public void processFromLocalVista(ManagedItemVo ndcVoFromVista, ItemAuditHistoryVo ndcItemAuditHistoryVo, boolean bCreate,
        ManagedItemVo productVoFromVista, ItemAuditHistoryVo productItemAuditHistoryVo) throws Exception {
        
    }
    
    /**
     * This method retrieves a blank template
     * @param entity The type being created.
     * @return ManagedItemVo
     */
    public ManagedItemVo retrieveBlankTemplate(EntityType entity) {
        
        return null;
    }

}
