/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDfUnitDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDrugUnitDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdIngredientAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDfUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugUnitConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on drug units
 */
public class DrugUnitDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<DrugUnitVo, EplDrugUnitDo> implements
    DrugUnitDomainCapability {

    private EplDrugUnitDao eplDrugUnitDao;
    private DrugUnitConverter drugUnitConverter;
    private EplProductDao eplProductDao;
    private EplProdIngredientAssocDao eplProdIngredientAssocDao;
    private EplDfUnitDao eplDfUnitDao;

    
    /**
     * deleteItem
     * @param drugUnitVo drugUnitVo
     * @throws ValidationException ValidationException
     */
    @Override
    public void deleteItem(DrugUnitVo drugUnitVo) throws ValidationException  {
        List<EplDrugUnitDo> drugUnitDo = getDataAccessObject().retrieve(EplDrugUnitDo.EPL_ID, Long.valueOf(drugUnitVo.getId()));
       
        if (drugUnitDo.size() == 1) {
            if (drugUnitDo.get(0).getNdfDrugunitIen() == null) {
                try {
                    checkForActiveDependencies(drugUnitVo, null);
                } catch (ValidationException e) {
                    throw new ValidationException(ValidationException.CANNOT_DELETE,
                        drugUnitVo.getValue(), " because another item is connected to it.");
                }
              
                getDataAccessObject().delete(drugUnitDo.get(0));
                
            } else  {
                throw new ValidationException(ValidationException.CANNOT_DELETE,
                    drugUnitVo.getValue(), " because it has already synched with NDF.");
            }
            
        }
        
        
        
        
    }
    
    /**
     * drugUnitVo cannot be in-activated if it has an active product, active ingredient or dosage form units .
     *  
     * @param drugUnitVo drugUnitVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(DrugUnitVo drugUnitVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("  SELECT item FROM ").append(EplProdIngredientAssocDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = "  WHERE DRUG_UNIT_ID_FK = " + drugUnitVo.getId();
        query.append(whereClause);
        List<EplProdIngredientAssocDo> assocs = eplProdIngredientAssocDao.executeHqlQuery(query.toString());

        if (assocs != null && assocs.size() > 0) {
            throw new ValidationException(ValidationException.INACTIVATE_DRUGUNIT_ASSOC,
                                          assocs.size(), assocs.get(0).getEplProduct().getVaProductName());
        }
        
        query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM  ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        whereClause = " WHERE DRUG_UNIT_ID_FK = " + drugUnitVo.getId();
        query.append(whereClause);
        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products != null && products.size() > 0) {
            throw new ValidationException(ValidationException.INACTIVATE_DRUGUNIT_PROD,
                                          products.size(), products.get(0).getVaProductName());
        }
        
        query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM ").append(EplDfUnitDo.class)
                .append(PPSConstants.ITEM));
        whereClause = " WHERE DRUG_UNIT_EPL_ID_FK = " + drugUnitVo.getId();
        query.append(whereClause);
        List<EplDfUnitDo> eplDfUnitDo = eplDfUnitDao.executeHqlQuery(query.toString());

        if (eplDfUnitDo != null && eplDfUnitDo.size() > 0) {
            throw new ValidationException(ValidationException.INACTIVATE_DRUGUNIT_DF,
                                          eplDfUnitDo.size(), eplDfUnitDo.get(0).getEplDosageForm().getDfName());
        }

    }
    
    
    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(DrugUnitVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplDrugUnitDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplDrugUnitDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public DrugUnitConverter getConverter() {
        return drugUnitConverter;
    }

    /**
     * setEplDrugUnitDao
     * @param eplDrugUnitDao eplDrugUnitDao property
     */
    public void setEplDrugUnitDao(EplDrugUnitDao eplDrugUnitDao) {
        this.eplDrugUnitDao = eplDrugUnitDao;
    }

    /**
     * setDrugUnitConverter
     * @param drugUnitConverter drugUnitConverter property
     */
    public void setDrugUnitConverter(DrugUnitConverter drugUnitConverter) {
        this.drugUnitConverter = drugUnitConverter;
    }
    
    /**
     * setEplDfUnitDao
     * @param eplDfUnitDao eplDfUnitDao property
     */
    public void setEplDfUnitDao(EplDfUnitDao eplDfUnitDao) {
        this.eplDfUnitDao = eplDfUnitDao;
    }
    
    /**
     * setEplProdIngredientAssocDao for DrugUnitDomainCapabilityImpl.
     * @param eplProdIngredientAssocDao eplProdIngredientAssocDao property
     */
    public void setEplProdIngredientAssocDao(
            EplProdIngredientAssocDao eplProdIngredientAssocDao) {
        this.eplProdIngredientAssocDao = eplProdIngredientAssocDao;
    }
    
    /**
     * setEplProductDao for DrugUnitDomainCapabilityImpl.
     * @param eplProductDao eplProductDao property
     */
    public void setEplProductDao(EplProductDao eplProductDao) {
        this.eplProductDao = eplProductDao;
    }
}
