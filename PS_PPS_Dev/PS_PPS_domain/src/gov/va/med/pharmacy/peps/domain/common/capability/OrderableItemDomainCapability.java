/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;


/**
 * Perform CRUD operations on OrderableItemVo
 */
public interface OrderableItemDomainCapability extends ManagedItemDomainCapability<OrderableItemVo, EplOrderableItemDo> {

    /**
     * Search for Orderable Items with the given search criteria. This method returns only those Orderable Items that match
     * all the criteria
     * 
     * @param searchCriteria criteria for matching Orderable Items
     * @return List of OrderableItemVo
     */
    List<OrderableItemVo> searchAllAndAdvanced(SearchCriteriaVo searchCriteria);

    /**
     * Search for Orderable Items with the given search criteria.
     * 
     * @param searchCriteria criteria for matching Orderable Items
     * @return List of OrderableItemVo
     */
    List<OrderableItemVo> searchAllOrAdvanced(SearchCriteriaVo searchCriteria);
}
