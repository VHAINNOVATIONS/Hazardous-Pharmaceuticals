/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidItemType;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.IngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplIngredientDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdIngredientAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVuidStatusHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVuidStatusHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.IngredientConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.VuidStatusHistoryConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;



/**
 * Perform CRUD operations on Ingredients
 */
public class IngredientDomainCapabilityImpl extends
        ManagedDataDomainCapabilityImpl<IngredientVo, EplIngredientDo>
        implements IngredientDomainCapability {

    private EplIngredientDao eplIngredientDao;
    private IngredientConverter ingredientConverter;
    private EplProdIngredientAssocDao eplProdIngredientAssocDao;
    private EplVuidStatusHistoryDao eplVuidStatusHistoryDao;
    private EplProductDao eplProductDao;
    private VuidStatusHistoryConverter vuidStatusHistoryConverter;

    /**
     * deleteItem
     * @param ingredientVo ingredientVo
     * @throws ValidationException ValidationException
     */
    @Override
    public void deleteItem(IngredientVo ingredientVo) throws ValidationException  {
        List<EplIngredientDo> ingredientDo = getDataAccessObject().retrieve(EplDrugUnitDo.EPL_ID, 
            Long.valueOf(ingredientVo.getId()));
       
        if (ingredientDo.size() == 1) {
            if (ingredientDo.get(0).getNdfIngredientIen() == null) {
                try {
                    checkForActiveDependencies(ingredientVo, null);
                } catch (Exception e) {
                    throw new ValidationException(ValidationException.CANNOT_DELETE,
                        ingredientVo.getValue(), " because another item is using it.");
                }
                
                getDataAccessObject().delete(ingredientDo.get(0));
                
            } else  {
                throw new ValidationException(ValidationException.CANNOT_DELETE,
                    ingredientVo.getValue(), " because it has already synched with NDF.");
            }
        }
    }
    
    /**
     * ingredientVo cannot be in-activated if it has an active product.
     *  
     * @param ingredientVo ingredientVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(IngredientVo ingredientVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT   item FROM ").append(EplProdIngredientAssocDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE  INGREDIENT_ID_FK = " + ingredientVo.getId();
        query.append(whereClause);
        List<EplProdIngredientAssocDo> assocs = eplProdIngredientAssocDao.executeHqlQuery(query.toString());
        
        if (assocs != null && assocs.size() > 0) {
            throw new ValidationException(ValidationException.INACTIVATE_INGREDIENT,
                assocs.size(), assocs.get(0).getEplProduct().getVaProductName());
        }
        
        query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM ").append(EplIngredientDo.class)
                .append(PPSConstants.ITEM));
        whereClause = " WHERE EPL_ID_PRIMARY_INGREDIENT_FK = " + ingredientVo.getId() + " AND ITEM_STATUS LIKE 'ACTIVE'";
        query.append(whereClause);
        List<EplIngredientDo> assocs2 = this.getDataAccessObject().executeHqlQuery(query.toString());
        
        if (assocs2 != null && assocs2.size() > 0) {
            throw new ValidationException(ValidationException.INACTIVATE_INGREDIENT_PRIMARY,
                assocs2.size(), assocs2.get(0).getName());
        }

        
        
        

    }

    
    /**
     * Create Hibernate {@link Criteria} that will find an item by its
     * uniqueness fields.
     * 
     * @param item
     *            {@link ManagedItemVo} for which to create uniqueness
     *            {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(IngredientVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplIngredientDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * Insert the given IngredientVo.
     * 
     * @param ingredientVo
     *            IngredientVo
     * @param user
     *            {@link UserVo} performing the action
     * @return IngredientVo with new ID
     */
    @Override
    public IngredientVo createWithoutDuplicateCheck(IngredientVo ingredientVo,
            UserVo user) {

        if (ingredientVo.getId() == null) {
            ingredientVo.setId(getSeqNumDomainCapability().generateId(
                    ingredientVo.getEntityType(), user));

            if ((ingredientVo.getVaDataFields() != null)
                    && (!(ingredientVo.getVaDataFields().isEmpty()))) {
                ingredientVo.getVaDataFields().setVaDfOwnerId(
                        getSeqNumDomainCapability().generatedOwnerId(user));
            }
        }

        EplIngredientDo ingredientDo = ingredientConverter
                .convert(ingredientVo);

        eplIngredientDao.insert(ingredientDo, user);

        if (ingredientVo.getEffectiveDates() != null) {
            for (VuidStatusHistoryVo vuidStatusHistoryVo : ingredientVo
                    .getEffectiveDates()) {
                vuidStatusHistoryVo.setItemType(VuidItemType.INGREDIENTS);
                vuidStatusHistoryVo.setId(getSeqNumDomainCapability().generateId(
                        vuidStatusHistoryVo.getEntityType(), user));
                EplVuidStatusHistoryDo vuidStatusHistoryDo = vuidStatusHistoryConverter
                        .convert(vuidStatusHistoryVo);
                eplVuidStatusHistoryDao.insert(vuidStatusHistoryDo, user);
            }
        }

        return (ingredientVo);
    }

    /**
     * retrieve
     * @param eplId eplId
     * @return IngredientVo
     * @throws ItemNotFoundException 
     */
    public IngredientVo retrieve(String eplId) throws ItemNotFoundException {
        IngredientVo ingredientVo = super.retrieve(eplId);
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item  FROM").append(
            EplVuidStatusHistoryDo.class).append("item  WHERE item.").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append("  =  '").append(ingredientVo.getVuid());
        queryBuffer.append("'   AND ").append(EplVuidStatusHistoryDo.ITEM_TYPE).
            append("  =  ").append(PPSConstants.VUID_INGREDIENT);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());
        
        List<VuidStatusHistoryVo> vuidStatusHistroyVoList = vuidStatusHistoryConverter.convert(vuidStatusHistroyList);
        ingredientVo.setEffectiveDates(vuidStatusHistroyVoList);
       
        return ingredientVo;
        
    }
    
    /**
     * retrieveByName
     * @param name name
     * @return IngredientVo
     */
    public IngredientVo retrieveByName(String name) {
        
        String name2 = name;
     
        // replace ' with double tick.
        if (name2.contains("'")) {
            name2 = name2.replace("'", "''");
        }
        
        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM").append(EplIngredientDo.class)
                .append("item"));
        String whereClause = " where name LIKE '" + name2 + "'";
        query.append(whereClause);

        List<EplIngredientDo> returnedDos = eplIngredientDao.executeHqlQuery(query.toString());

        if (returnedDos == null || returnedDos.size() < 1) {
            return null;
        } 
  
        Collection<IngredientVo> vos =  ingredientConverter.convert(returnedDos);
        
        for (IngredientVo vo : vos) {
            if (StringUtils.equals(vo.getValue(), name2)) {
                return vo;
            }
        }
             
        return null;
        
    }
    
    /**
     * retrieve
     * @param ingredient ingredient
     * @param user user
     * @return IngredientVo
     * @throws DuplicateItemException  DuplicateItemException
     */
    public IngredientVo update(IngredientVo ingredient, UserVo user) throws DuplicateItemException {
        
        EplIngredientDo updatedIng = ingredientConverter.convert(ingredient);

        // update attributes on product table
        eplIngredientDao.update(updatedIng, user);

        // check to see if we need to add the VuidStatusHistoryRecord
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM").append(
            EplVuidStatusHistoryDo.class).append("item WHERE item.").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append(" = '").append(ingredient.getVuid()).append("' AND ");
        queryBuffer.append(EplVuidStatusHistoryDo.ITEM_TYPE).append(" = ").append(PPSConstants.VUID_INGREDIENT);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());
        
        if (StringUtils.isNotBlank(updatedIng.getVuid()) && vuidStatusHistroyList.size() == 0) {
            if (ingredient.getEffectiveDates() != null) {
                ingredient.getEffectiveDates().get(0).setId(getSeqNumDomainCapability().generateId(
                    EntityType.VUID_STATUS_HISTORY, user));
                EplVuidStatusHistoryDo data = vuidStatusHistoryConverter.convert(ingredient.getEffectiveDates().get(0));
                eplVuidStatusHistoryDao.insert(data, user);
            }
            
        }
        
        return ingredient;
        
    }
    
    /**
     * Retrieve the IngredientVo with the given vuId.
     * 
     * @param vuId
     *            vuId
     * @return IngredientVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public IngredientVo retrieveByVuId(String vuId)
        throws ItemNotFoundException {
        List<EplIngredientDo> eplIngredientDoList;
        EplIngredientDo eplIngredienteDo = null;
        List<EplVuidStatusHistoryDo> eplVuidStatusHistoryList;
        List<VuidStatusHistoryVo> vuidStatusHistroyList;

        String svuId = "vuId ";
        String vuid = "VUID";
        
        try {
            eplIngredientDoList = eplIngredientDao.retrieve(vuid, vuId);

            if (eplIngredientDoList.size() == 0) {
                throw new ItemNotFoundException(
                        ItemNotFoundException.ITEM_NOT_FOUND,
                        new Object[] { svuId + vuId });
            }

            eplIngredienteDo = eplIngredientDoList.get(0);
            eplIngredientDao.refresh(eplIngredienteDo);

            eplVuidStatusHistoryList = eplVuidStatusHistoryDao.retrieve(vuid,
                    vuId);

            vuidStatusHistroyList = vuidStatusHistoryConverter
                    .convert(eplVuidStatusHistoryList);

        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(e,
                    ItemNotFoundException.ITEM_NOT_FOUND,
                    new Object[] { svuId + vuId });
        }

        IngredientVo retrievedVo = ingredientConverter
                .convert(eplIngredienteDo);

        retrievedVo.setEffectiveDates(vuidStatusHistroyList);

        return retrievedVo;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplIngredientDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public IngredientConverter getConverter() {
        return ingredientConverter;
    }

    /**
     * setEplIngredientDao
     * @param eplIngredientDao
     *            eplIngredientDao property
     */
    public void setEplIngredientDao(EplIngredientDao eplIngredientDao) {
        this.eplIngredientDao = eplIngredientDao;
    }

    /**
     * setIngredientConverter
     * @param ingredientConverter
     *            ingredientConverter property
     */
    public void setIngredientConverter(IngredientConverter ingredientConverter) {
        this.ingredientConverter = ingredientConverter;
    }

    /**
     * setEplVuidStatusHistoryDao.
     * @param eplVuidStatusHistoryDao eplVuidStatusHistoryDao property
     */
    public void setEplVuidStatusHistoryDao(
            EplVuidStatusHistoryDao eplVuidStatusHistoryDao) {
        this.eplVuidStatusHistoryDao = eplVuidStatusHistoryDao;
    }
    
    /**
     * setEplProdIngredientAssocDao.
     * @param eplProdIngredientAssocDao eplProdIngredientAssocDao property
     */
    public void setEplProdIngredientAssocDao(
            EplProdIngredientAssocDao eplProdIngredientAssocDao) {
        this.eplProdIngredientAssocDao = eplProdIngredientAssocDao;
    }

    /**
     * setVuidStatusHistoryConverter.
     * @param vuidStatusHistoryConverter vuidStatusHistoryConverter property
     */
    public void setVuidStatusHistoryConverter(
            VuidStatusHistoryConverter vuidStatusHistoryConverter) {
        this.vuidStatusHistoryConverter = vuidStatusHistoryConverter;
    }

    /**
     * Delete associated domain from data base if IEN is not assigned and other objects are not dependent on this object.
     * 
     * @param ingredientVo to delete
     * @param user requesting the delete
     * 
     * @throws ValidationException to validate the IEN and dependencies
     * 
     */
    @Override
    public void deletePending(IngredientVo ingredientVo, UserVo user) throws ValidationException {
        
        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM ").append(EplProdIngredientAssocDoKey.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE INGREDIENT_ID_FK = " + ingredientVo.getId();
        query.append(whereClause);
        
        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_INGREDIENT,
                                      products.size(), products.get(0).getVaProductName());
    }
}
