/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidItemType;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdDrugClassAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVaDrugClassDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVuidStatusHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugClassAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDrugClassDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVuidStatusHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.utility.SchemaUtility;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugClassConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.VuidStatusHistoryConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on DrugClass
 */
public class DrugClassDomainCapabilityImpl extends
        ManagedDataDomainCapabilityImpl<DrugClassVo, EplVaDrugClassDo>
        implements DrugClassDomainCapability {
    private static final String LAST_CODE = "Z999";
    private EplProductDao eplProductDao;
    private EplVaDrugClassDao eplVaDrugClassDao;
    private EplProdDrugClassAssocDao eplProdDrugClassAssocDao;
    private DrugClassConverter drugClassConverter;
    private EplVuidStatusHistoryDao eplVuidStatusHistoryDao;
    private VuidStatusHistoryConverter vuidStatusHistoryConverter;

    
    /**
     * deleteItem
     * @param drugClassVo drugClassVo
     * @throws ValidationException ValidationException
     */
    @Override
    public void deleteItem(DrugClassVo drugClassVo) throws ValidationException  {
        List<EplVaDrugClassDo> drugClassDo = getDataAccessObject().retrieve(EplDrugUnitDo.EPL_ID, 
            Long.valueOf(drugClassVo.getId()));
       
        if (drugClassDo.size() == 1) {
            if (drugClassDo.get(0).getNdfClassIen() == null) {
                try {
                    checkForActiveDependencies(drugClassVo, null);
                } catch (Exception e) {
                    throw new ValidationException(ValidationException.CANNOT_DELETE,
                        drugClassVo.getCode() + ":" + drugClassVo.getClassification(), 
                        " because another item is using it.");
                }
                
                getDataAccessObject().delete(drugClassDo.get(0));
            } else  {
                throw new ValidationException(ValidationException.CANNOT_DELETE,
                    drugClassVo.getCode() + ":" + drugClassVo.getClassification(), 
                    " because it has already synched with NDF.");
            }
        }
    }
    
    /**
     * drugClass cannot be in-activated if it has an active product.
     *  
     * @param drugClassVo drugClassVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(DrugClassVo drugClassVo, UserVo user) throws ValidationException {

        
        StringBuffer query1 = new StringBuffer();
        query1.append(HqlBuilder.create(" SELECT item FROM ").append(EplProdDrugClassAssocDo.class)
                .append(PPSConstants.ITEM));
        String whereClause1 = " WHERE DRUG_CLASS_ID_FK = " + drugClassVo.getId();
        query1.append(whereClause1);
        List<EplProdIngredientAssocDo> assocs1 = eplProdDrugClassAssocDao.executeHqlQuery(query1.toString());
        
        if (assocs1 != null && assocs1.size() > 0) {
            throw new ValidationException(ValidationException.INACTIVATE_INGREDIENT,
                assocs1.size(), assocs1.get(0).getEplProduct().getVaProductName());
        }
        
        StringBuffer query2 = new StringBuffer();
        query2.append(HqlBuilder.create("SELECT  item FROM  ").append(EplVaDrugClassDo.class)
                .append(PPSConstants.ITEM));
        String whereClause2 = " WHERE PARENT_CLASS_ID_FK = " + drugClassVo.getId();
        query2.append(whereClause2);
        List<EplVaDrugClassDo> assocs2 = this.getDataAccessObject().executeHqlQuery(query2.toString());
        
        if (assocs2 != null && assocs2.size() > 0) {
            throw new ValidationException(ValidationException.INACTIVATE_INGREDIENT,
                assocs2.size(), assocs2.get(0).getEplVaDrugClass().getClassificationName());
        }
        
    }       

        
        
    
    
    /**
     * If the given SearchTermVo is for a Drug Class Code range search, add to
     * the {@link Criteria} the range, otherwise call the super class' method.
     * 
     * @param searchTerm
     *            {@link SearchTermVo} for which to add {@link Criteria}
     * @param criteria
     *            {@link Criteria} on which to add the search terms
     * @return resulting {@link Criteria}
     */
    @Override
    protected Criteria createSearchTermCriteria(SearchTermVo searchTerm,
            Criteria criteria) {
        if (searchTerm.isDrugClassCodeRangeSearchTerm()) {
            String propertyName = SchemaUtility.getPropertyName(
                    getDataObjectClass(), searchTerm.getFieldKey());

            Criterion start = Restrictions.ge(propertyName,
                    searchTerm.getRangeStart()).ignoreCase();

            String rangeEnd = searchTerm.getRangeEnd()
                    + LAST_CODE
                            .substring(searchTerm.getRangeEnd().length() - 1);
            Criterion end = Restrictions.le(propertyName, rangeEnd)
                    .ignoreCase();

            return criteria.add(Restrictions.and(start, end));
        } else {
            return super.createSearchTermCriteria(searchTerm, criteria);
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
    protected Criteria createUniquenessCriteria(DrugClassVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplVaDrugClassDo.CLASSIFICATION_NAME,
                item.getClassification()));
        criteria.add(Restrictions.ilike(EplVaDrugClassDo.CODE, item.getCode()));

        return criteria;
    }

    /**
     * Insert the given DrugClassVo.
     * 
     * @param drugClassVo drugClassVo
     * @param user {@link UserVo} performing the action
     * @return DrugClass with new ID
     */
    @Override
    public DrugClassVo createWithoutDuplicateCheck(DrugClassVo drugClassVo,
            UserVo user) {

        if (drugClassVo.getId() == null) {
            drugClassVo.setId(getSeqNumDomainCapability().generateId(
                    drugClassVo.getEntityType(), user));

            if ((drugClassVo.getVaDataFields() != null)
                    && (!(drugClassVo.getVaDataFields().isEmpty()))) {
                drugClassVo.getVaDataFields().setVaDfOwnerId(
                        getSeqNumDomainCapability().generatedOwnerId(user));
            }
        }

        EplVaDrugClassDo ingredientDo = drugClassConverter.convert(drugClassVo);

        eplVaDrugClassDao.insert(ingredientDo, user);

        if (drugClassVo.getEffectiveDates() != null) {
            for (VuidStatusHistoryVo vuidStatusHistoryVo : drugClassVo
                    .getEffectiveDates()) {
                vuidStatusHistoryVo.setItemType(VuidItemType.DRUG_CLASSES);
                vuidStatusHistoryVo.setId(getSeqNumDomainCapability().generateId(
                        vuidStatusHistoryVo.getEntityType(), user));
                EplVuidStatusHistoryDo vuidStatusHistoryDo = vuidStatusHistoryConverter
                        .convert(vuidStatusHistoryVo);
                eplVuidStatusHistoryDao.insert(vuidStatusHistoryDo, user);
            }
        }

        return (drugClassVo);
    }

    /**
     * retrieve
     * @param eplId eplId
     * @return DrugClassVo
     * @throws ItemNotFoundException 
     */
    public DrugClassVo retrieve(String eplId) throws ItemNotFoundException {
        DrugClassVo drugClassVo = super.retrieve(eplId);
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM ").append(
            EplVuidStatusHistoryDo.class).append("item  WHERE item.").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append(" =  '").append(drugClassVo.getVuid()).append("' AND  ");
        queryBuffer.append(EplVuidStatusHistoryDo.ITEM_TYPE).append("  = ").append(PPSConstants.VUID_DRUGCLASS);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString()); 
        
        List<VuidStatusHistoryVo> vuidStatusHistroyVoList = vuidStatusHistoryConverter.convert(vuidStatusHistroyList);
        drugClassVo.setEffectiveDates(vuidStatusHistroyVoList);
       
        return drugClassVo;
        
    }
    
    /**
     * update
     * @param drugClass drugClass
     * @param user user
     * @return DrugClassVo
     * @throws DuplicateItemException  DuplicateItemException
     */
    public DrugClassVo update(DrugClassVo drugClass, UserVo user) throws DuplicateItemException {
        
        EplVaDrugClassDo updateddc = drugClassConverter.convert(drugClass);

        // update attributes on product table
        eplVaDrugClassDao.update(updateddc, user);

        // check to see if we need to add the VuidStatusHistoryRecord
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM").append(
            EplVuidStatusHistoryDo.class).append("item WHERE item.").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append(" = '").append(drugClass.getVuid()).append("' AND ");
        queryBuffer.append(EplVuidStatusHistoryDo.ITEM_TYPE).append(" = ").append(PPSConstants.VUID_DRUGCLASS);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());
        
        
        if (StringUtils.isNotBlank(drugClass.getVuid()) && vuidStatusHistroyList.size() == 0) {
            if (drugClass.getEffectiveDates() != null) {
                drugClass.getEffectiveDates().get(0).setId(getSeqNumDomainCapability().generateId(
                    EntityType.VUID_STATUS_HISTORY, user));
                EplVuidStatusHistoryDo data = vuidStatusHistoryConverter.convert(drugClass.getEffectiveDates().get(0));
                eplVuidStatusHistoryDao.insert(data, user);
            }
        }
        
        return drugClass;
        
    }
    
    
    /**
     * Retrieve the DrugClassVo with the given vuId.
     * 
     * @param vuId
     *            vuId
     * @return DrugClassVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public DrugClassVo retrieveByVuId(String vuId) throws ItemNotFoundException {
        List<EplVaDrugClassDo> vaDrugClassDoList;
        EplVaDrugClassDo eplVaDrugClassDo = null;
        List<EplVuidStatusHistoryDo> eplVuidStatusHistoryList;
        List<VuidStatusHistoryVo> vuidStatusHistroyList;
        
        String vuId1 = "vuId ";
        
        try {
            String vuid = "VUID";
      
            vaDrugClassDoList = eplVaDrugClassDao.retrieve(vuid, vuId);

            if (vaDrugClassDoList.size() == 0) {
                throw new ItemNotFoundException(
                        ItemNotFoundException.ITEM_NOT_FOUND,
                        new Object[] { vuId1 + vuId });
            }

            eplVaDrugClassDo = vaDrugClassDoList.get(0);
            eplVaDrugClassDao.refresh(eplVaDrugClassDo);

            eplVuidStatusHistoryList = eplVuidStatusHistoryDao.retrieve(vuid,
                    vuId);

            vuidStatusHistroyList = vuidStatusHistoryConverter
                    .convert(eplVuidStatusHistoryList);

        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(e,
                    ItemNotFoundException.ITEM_NOT_FOUND,
                    new Object[] { vuId1 + vuId });
        }

        DrugClassVo retrievedVo = drugClassConverter.convert(eplVaDrugClassDo);

        retrievedVo.setEffectiveDates(vuidStatusHistroyList);

        return retrievedVo;
    }

    /**
     * Get the {@link DataAccessObject} that this DrugClassCapabilityImpl uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplVaDrugClassDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public DrugClassConverter getConverter() {
        return drugClassConverter;
    }

    /**
     * setEplVaDrugClassDao
     * @param eplVaDrugClassDao
     *            eplVaDrugClassDao property
     */
    public void setEplVaDrugClassDao(EplVaDrugClassDao eplVaDrugClassDao) {
        this.eplVaDrugClassDao = eplVaDrugClassDao;
    }

    
    /**
     * setEplProdDrugClassAssocDao
     * @param eplProdDrugClassAssocDao
     *            eplProdDrugClassAssocDao property
     */
    public void setEplProdDrugClassAssocDao(EplProdDrugClassAssocDao eplProdDrugClassAssocDao) {
        this.eplProdDrugClassAssocDao = eplProdDrugClassAssocDao;
    }


    /**
     * setDrugClassConverter
     * @param drugClassConverter
     *            drugClassConverter property
     */
    public void setDrugClassConverter(DrugClassConverter drugClassConverter) {
        this.drugClassConverter = drugClassConverter;
    }

    /**
     * setEplVuidStatusHistoryDao
     * @param eplVuidStatusHistoryDao
     *            eplVuidStatusHistoryDao property
     */
    public void setEplVuidStatusHistoryDao(
            EplVuidStatusHistoryDao eplVuidStatusHistoryDao) {
        this.eplVuidStatusHistoryDao = eplVuidStatusHistoryDao;
    }

    /**
     * setVuidStatusHistoryConverter
     * @param vuidStatusHistoryConverter
     *            vuidStatusHistoryConverter property
     */
    public void setVuidStatusHistoryConverter(
            VuidStatusHistoryConverter vuidStatusHistoryConverter) {
        this.vuidStatusHistoryConverter = vuidStatusHistoryConverter;
    }
    
    /**
     * Delete associated domain from data base if IEN is not assigned and other objects are not dependent on this object.
     * 
     * @param drugClassVo to delete
     * @param user requesting the delete
     * 
     * @throws ValidationException to validate the IEN and dependencies
     * 
     */
    @Override
    public void deletePending(DrugClassVo drugClassVo, UserVo user) throws ValidationException {
        
        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE DOSE_UNIT_ID_FK = " + drugClassVo.getId();
        query.append(whereClause);
        
        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_MANUFACTURER,
                                      products.size(), products.get(0).getVaProductName());


    }

}
