/**
 * Source file created in 2007 by Southwest Research Institute
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
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidItemType;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.GenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVaGenericNameDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVuidStatusHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaGenNameDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVuidStatusHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.GenericNameConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.VuidStatusHistoryConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on generic name
 */
public class GenericNameDomainCapabilityImpl extends
        ManagedDataDomainCapabilityImpl<GenericNameVo, EplVaGenNameDo>
        implements GenericNameDomainCapability {
    private static final String VUID = "VUID";
    private EplVaGenericNameDao eplVaGenericNameDao;
    private GenericNameConverter genericNameConverter;
    private EplVuidStatusHistoryDao eplVuidStatusHistoryDao;
    private EplProductDao eplProductDao;
    private VuidStatusHistoryConverter vuidStatusHistoryConverter;

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
    protected Criteria createUniquenessCriteria(GenericNameVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplVaGenNameDo.NAME, item.getValue()));

        return criteria;
    }
    
    
    /**
     * deleteItem
     * @param genericNameVo genericNameVo
     * @throws ValidationException ValidationException
     */
    @Override
    public void deleteItem(GenericNameVo genericNameVo) throws ValidationException  {
        List<EplVaGenNameDo> genericNameDo = getDataAccessObject().retrieve(EplDrugUnitDo.EPL_ID, 
            Long.valueOf(genericNameVo.getId()));
       
        if (genericNameDo.size() == 1) {
            if (genericNameDo.get(0).getNdfGenericIen() == null) {
                try {
                    checkForActiveDependencies(genericNameVo, null);
                } catch (Exception e) {
                    throw new ValidationException(ValidationException.CANNOT_DELETE,
                        genericNameVo.getValue(), " because another item is using it.");
                }
                
                getDataAccessObject().delete(genericNameDo.get(0));
            } else  {
                throw new ValidationException(ValidationException.CANNOT_DELETE,
                    genericNameVo.getValue(), " because it has already synched with NDF.");
            }
        }
    }
    
    
    /**
     * GenericNameVo cannot be in-activated if it has an active product.
     *  
     * @param genericNameVo genericNameVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(GenericNameVo genericNameVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM  ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = "  WHERE VA_GEN_NAME_ID_FK = " + genericNameVo.getId() + " AND ITEM_STATUS LIKE 'ACTIVE'";
        query.append(whereClause);
        
        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        // return the validation if checkForActiveDependencies returned products.
        throw new ValidationException(ValidationException.INACTIVATE_GENERICNAME,
                                      products.size(), products.get(0).getVaProductName());
    }

    /**
     * Insert the given GenericNameVo.
     * 
     * @param genericNameVo genericNameVo
     * @param user {@link UserVo} performing the action
     * @return GenericNameVo with new ID
     */
    @Override
    public GenericNameVo createWithoutDuplicateCheck(
            GenericNameVo genericNameVo, UserVo user) {

        if (genericNameVo.getId() == null) {
            genericNameVo.setId(getSeqNumDomainCapability().generateId(
                    genericNameVo.getEntityType(), user));

            if ((genericNameVo.getVaDataFields() != null)
                    && (!(genericNameVo.getVaDataFields().isEmpty()))) {
                genericNameVo.getVaDataFields().setVaDfOwnerId(
                        getSeqNumDomainCapability().generatedOwnerId(user));
            }
        }

        EplVaGenNameDo genericNameDo = genericNameConverter
                .convert(genericNameVo);

        eplVaGenericNameDao.insert(genericNameDo, user);

        if (genericNameVo.getEffectiveDates() != null) {
            for (VuidStatusHistoryVo vuidStatusHistoryVo : genericNameVo
                    .getEffectiveDates()) {
    
                vuidStatusHistoryVo.setId(getSeqNumDomainCapability().generateId(
                        vuidStatusHistoryVo.getEntityType(), user));
                vuidStatusHistoryVo.setItemType(VuidItemType.GENERIC);
                EplVuidStatusHistoryDo vuidStatusHistoryDo = vuidStatusHistoryConverter
                        .convert(vuidStatusHistoryVo);
                eplVuidStatusHistoryDao.insert(vuidStatusHistoryDo, user);
            }
        }

        return (genericNameVo);
    }

    /**
     * retrieve
     * @param eplId eplId
     * @return GenericNameVo
     * @throws ItemNotFoundException 
     */
    public GenericNameVo retrieve(String eplId) throws ItemNotFoundException {
        GenericNameVo genericNameVo = super.retrieve(eplId);
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create(" SELECT item FROM").append(
            EplVuidStatusHistoryDo.class).append(" item WHERE item.").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append("  = '").append(genericNameVo.getVuid()).append("' AND   ");
        queryBuffer.append(EplVuidStatusHistoryDo.ITEM_TYPE).append("   = ").append(PPSConstants.VUID_GENERIC);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());
        
        List<VuidStatusHistoryVo> vuidStatusHistroyVoList = vuidStatusHistoryConverter.convert(vuidStatusHistroyList);
        genericNameVo.setEffectiveDates(vuidStatusHistroyVoList);
       
        return genericNameVo;
        
    }
    
    /**
     * retrieveByName, returns null if the name does not exist.
     * @param name name
     * @return GenericNameVo
     */
    public GenericNameVo retrieveByName(String name) {
        
        String name2 = name;
     
        // replace ' with double tick.
        if (name2.contains("'")) {
            name2 = name2.replace("'", "''");
        }
        
        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM").append(EplVaGenNameDo.class)
                .append("item"));
        String whereClause = " where GENERIC_NAME LIKE '" + name2 + "'";
        query.append(whereClause);

        List<EplVaGenNameDo> returnedDos = eplVaGenericNameDao.executeHqlQuery(query.toString());

        if (returnedDos == null || returnedDos.size() < 1) {
            return null;
        }
        
        Collection<GenericNameVo> vos =  genericNameConverter.convert(returnedDos);
        
        for (GenericNameVo vo : vos) {
            if (StringUtils.equals(vo.getValue(), name2)) {
                return vo;
            }
        }
             
        return null;
    }
    
    /**
     * retrieve
     * @param generic generic
     * @param user user
     * @return GenericNameVo
     * @throws DuplicateItemException  DuplicateItemException
     */
    public GenericNameVo update(GenericNameVo generic, UserVo user) throws DuplicateItemException {
        
        EplVaGenNameDo updatedGenName = genericNameConverter.convert(generic);

        // update attributes on product table
        eplVaGenericNameDao.update(updatedGenName, user);

        // check to see if we need to add the VuidStatusHistoryRecord
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM").append(
            EplVuidStatusHistoryDo.class).append("item WHERE item.").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append(" = '").append(generic.getVuid()).append("' AND ");
        queryBuffer.append(EplVuidStatusHistoryDo.ITEM_TYPE).append(" = ").append(PPSConstants.VUID_GENERIC);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());
        
        
        // if there is a vuid and no history record then create the history record.
        if (StringUtils.isNotBlank(generic.getVuid())) {
            if (vuidStatusHistroyList.size() == 0) {
                if (generic.getEffectiveDates() != null) {
                    generic.getEffectiveDates().get(0).setId(getSeqNumDomainCapability().generateId(
                        EntityType.VUID_STATUS_HISTORY, user));
                    EplVuidStatusHistoryDo data = vuidStatusHistoryConverter.convert(generic.getEffectiveDates().get(0));
                    eplVuidStatusHistoryDao.insert(data, user);
                }
            }
        }
       
        return generic;
        
    }
    
    
    /**
     * Retrieve the GenericNameVo with the given vuId.
     * 
     * @param vuId vuId
     * @return GenericNameVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    public GenericNameVo retrieveByVuId(String vuId)
        throws ItemNotFoundException {
        List<EplVaGenNameDo> eplVaGenNameDoList;
        EplVaGenNameDo eplVaGenNameDo = null;
        List<EplVuidStatusHistoryDo> eplVuidStatusHistoryList;
        List<VuidStatusHistoryVo> vuidStatusHistroyList;

        try {
            eplVaGenNameDoList = eplVaGenericNameDao.retrieve(VUID, vuId);

            if (eplVaGenNameDoList.size() == 0) {
                throw new ItemNotFoundException(
                        ItemNotFoundException.ITEM_NOT_FOUND,
                        new Object[] { VUID + ":" + vuId });
            }

            eplVaGenNameDo = eplVaGenNameDoList.get(0);
            eplVaGenericNameDao.refresh(eplVaGenNameDo);

            eplVuidStatusHistoryList = eplVuidStatusHistoryDao.retrieve(VUID,
                    vuId);

            vuidStatusHistroyList = vuidStatusHistoryConverter
                    .convert(eplVuidStatusHistoryList);

        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(e,
                    ItemNotFoundException.ITEM_NOT_FOUND,
                    new Object[] { "vuId " + vuId });
        }

        GenericNameVo retrievedVo = genericNameConverter
                .convert(eplVaGenNameDo);

        retrievedVo.setEffectiveDates(vuidStatusHistroyList);

        return retrievedVo;
    }

    /**
     * Get the {@link DataAccessObject} that this GenericNameDomaingCapabilityImpl uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplVaGenericNameDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public GenericNameConverter getConverter() {
        return genericNameConverter;
    }

    /**
     * setEplVaGenericNameDao
     * @param eplVaGenericNameDao eplVaGenericNameDao property
     */
    public void setEplVaGenericNameDao(EplVaGenericNameDao eplVaGenericNameDao) {
        this.eplVaGenericNameDao = eplVaGenericNameDao;
    }

    /**
     * setGenericNameConverter
     * @param genericNameConverter genericNameConverter property
     */
    public void setGenericNameConverter(
            GenericNameConverter genericNameConverter) {
        this.genericNameConverter = genericNameConverter;
    }

    /**
     * setVuidStatusHistoryConverter for GenericNameDomainCapabilityImpl
     * @param vuidStatusHistoryConverter vuidStatusHistoryConverter property
     */
    public void setVuidStatusHistoryConverter(
            VuidStatusHistoryConverter vuidStatusHistoryConverter) {
        this.vuidStatusHistoryConverter = vuidStatusHistoryConverter;
    }

    /**
     * setEplVuidStatusHistoryDao  for GenericNameDomainCapabilityImpl
     * @param eplVuidStatusHistoryDao
     *            eplVuidStatusHistoryDao property
     */
    public void setEplVuidStatusHistoryDao(
            EplVuidStatusHistoryDao eplVuidStatusHistoryDao) {
        this.eplVuidStatusHistoryDao = eplVuidStatusHistoryDao;
    }
    
    /**
     * setEplProductDao  for GenericNameDomainCapabilityImpl
     * @param eplProductDao eplProductDao property
     */
    public void setEplProductDao(EplProductDao eplProductDao) {
        this.eplProductDao = eplProductDao;
    }

    /**
     * Delete associated domain from data base if IEN is not assigned and other objects are not dependent on this object.
     * 
     * @param genericNameVo to delete
     * @param user requesting the delete
     * 
     * @throws ValidationException to validate the IEN and dependencies
     * 
     */
    @Override
    public void deletePending(GenericNameVo genericNameVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE VA_GEN_NAME_ID_FK = " + genericNameVo.getId();
        query.append(whereClause);
        
        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_GENERICNAME,
                                      products.size(), products.get(0).getVaProductName());
    }


}
