/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;


/**
 * ResetNationalDatabaseDomainCapability
 *
 */
public interface ResetNationalDatabaseDomainCapability {

    /**
     * resetDatabase
     * @param iRow The sql file number to load
     * @return true if successful
     */
    boolean resetDatabase(int iRow);

    /**
     * getIds
     * @param type Entity Type
     * @param activeOnly return only active records
     * @return List of Entities
     */
    List<Long> getIds(EntityType type, boolean activeOnly);

    /**
     * getNdcs
     * @param id id
     * @param ndcVo ndcVo
     * @return NdcVo
     */
    NdcVo getNdcs(Long id, NdcVo ndcVo);

    /**
     * getProduct
     * @param id id
     * @param productVo productVo
     * @return ProductVo
     */
    ProductVo getProduct(Long id, ProductVo productVo);

    /**
     * getOrderableItem
     * @param id Id
     * @param oiVo orderableItemVo
     * @return OrderableItemVo
     */
    OrderableItemVo getOrderableItem(Long id, OrderableItemVo oiVo);

    /**
     * getIdsPropInact
     * @param type Entity Type
     * @return List of Entities
     */
    List<Long> getIdsPropInact(EntityType type);
}
