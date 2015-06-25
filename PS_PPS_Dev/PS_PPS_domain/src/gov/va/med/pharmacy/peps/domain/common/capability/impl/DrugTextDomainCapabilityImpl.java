/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugTextType;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDrugTextDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDtSynonymDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOiDrugTextNAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdDrugTextLAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdDrugTextNAssocDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiDrugTextNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextLAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DrugTextConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on drug text
 */
public class DrugTextDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<DrugTextVo, EplDrugTextDo> implements
    DrugTextDomainCapability {

    private EplDrugTextDao eplDrugTextDao;
    private EplProdDrugTextLAssocDao eplProdDrugTextLAssocDao;
    private EplOiDrugTextNAssocDao eplOiDrugTextNAssocDao;
    private EplProdDrugTextNAssocDao eplProdDrugTextNAssocDao;
    private EplDtSynonymDao eplDtSynonymDao;
    private DrugTextConverter drugTextConverter;

    /**
     * drugTextVo cannot be in-activated if is has any active orderable Items or products.
     * 
     * @param drugTextVo drugTextVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(DrugTextVo drugTextVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create(" SELECT  item FROM ").append(EplOiDrugTextNAssocDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = "  WHERE DRUG_TEXT_ID_FK = " + drugTextVo.getId();
        query.append(whereClause);
        
        List<EplOiDrugTextNAssocDo> ois = eplOiDrugTextNAssocDao.executeHqlQuery(query.toString());

        if (ois == null || ois.size() == 0) {
            
            StringBuffer query2 = new StringBuffer();
            query2.append(HqlBuilder.create("SELECT  item FROM ").append(EplProdDrugTextNAssocDo.class)
                    .append(PPSConstants.ITEM));
            String whereClause2 = " WHERE DRUG_TEXT_ID_FK = " + drugTextVo.getId();
            query2.append(whereClause2);
            
            List<EplProdDrugTextNAssocDo> products = eplProdDrugTextNAssocDao.executeHqlQuery(query2.toString());
            
            if (products == null || products.size() == 0) {
                return;
            } else {
                throw new ValidationException(ValidationException.INACTIVATE_DRUGTEXT_PROD,
                                              products.size(), products.get(0).getEplProduct().getVaProductName());
            }
        } else {
            throw new ValidationException(ValidationException.INACTIVATE_DRUGTEXT_OI,
                                          ois.size(), ois.get(0).getEplOrderableItem().getOiName());
        }


    }
    
    /**
     * Re-associates existing drug text associations for a product to the 
     * new incoming drug texts denoted by nationalItem
     * 
     * @param existingItem existing Local {@link DrugTextVo} to associate Products with
     * @param nationalItem new {@link DrugTextVo} to associate Products with
     * @param user {@link UserVo} performing the action
     */
    public void reassociateExistingItems(DrugTextVo existingItem, DrugTextVo nationalItem, UserVo user) {

        Criteria criteria = eplProdDrugTextLAssocDao.getCriteria();
        criteria.add(Restrictions.eq(EplProdDrugTextLAssocDo.EPL_ID_DRUG_TEXT_FK, new Long(existingItem.getId())));

        List<EplProdDrugTextLAssocDo> existingAssocs = criteria.list();
        List<EplProdDrugTextLAssocDo> newAssocs = new ArrayList<EplProdDrugTextLAssocDo>(existingAssocs.size());

        for (EplProdDrugTextLAssocDo eplProdDrugTextLAssocDo : existingAssocs) {
            EplProdDrugTextLAssocDo newAssoc = eplProdDrugTextLAssocDo.copy();
            EplDrugTextDo eplDrugText = new EplDrugTextDo();
            eplDrugText.setEplId(new Long(nationalItem.getId()));
            newAssoc.setEplDrugText(eplDrugText);
            newAssoc.getKey().setDrugTextIdFk(eplDrugText.getEplId());
            newAssocs.add(newAssoc);
        }

        eplProdDrugTextLAssocDao.delete(EplProdDrugTextLAssocDo.EPL_ID_DRUG_TEXT_FK, new Long(existingItem.getId()));
        eplProdDrugTextLAssocDao.insert(newAssocs, user);

        delete(existingItem);

    }

    /**
     * Get all active drug texts, LOCAL only
     * 
     * @return List<RxConsultVo>
     */
    public List<DrugTextVo> getLocalDrugText() {
        Criteria criteria = eplDrugTextDao.getCriteria();
        criteria.add(Restrictions.eq(EplDrugTextDo.ITEM_STATUS, ItemStatus.ACTIVE.name()));
        criteria.add(Restrictions.eq(EplDrugTextDo.DRUG_TEXT_TYPE, DrugTextType.LOCAL.name()));
        List<EplDrugTextDo> data = criteria.list();

        return drugTextConverter.convert(data);
    }

    /**
     * Get drug text that matches name
     * 
     * @param drugText DrugTextVo
     * @return DrugTextVo
     */
    public DrugTextVo getUniqueItem(DrugTextVo drugText) {
        Criteria criteria = eplDrugTextDao.getCriteria();
        criteria.add(Restrictions.eq(EplDrugTextDo.DRUG_TEXT_NAME, drugText.getValue()));

        List<EplDrugTextDo> data = criteria.list();

        return drugTextConverter.convert(data.get(0));
    }

    /**
     * Get all active drug texts, NATIONAL only
     * 
     * @return List<DrugTextVo>
     */
    public List<DrugTextVo> getNationalDrugText() {
        Criteria criteria = eplDrugTextDao.getCriteria();
        criteria.add(Restrictions.eq(EplDrugTextDo.ITEM_STATUS, ItemStatus.ACTIVE.name()));

        criteria.add(Restrictions.eq(EplDrugTextDo.DRUG_TEXT_TYPE, DrugTextType.NATIONAL.name()));

        List<EplDrugTextDo> data = criteria.list();

        return drugTextConverter.convert(data);
    }

    /**
     * Insert the given DrugTextVo.
     * 
     * @param drugText DrugTextVo
     * @param user {@link UserVo} performing the action
     * @return DrugTextVo inserted DrugTextVo with new ID
     */
    @Override
    public DrugTextVo createWithoutDuplicateCheck(DrugTextVo drugText, UserVo user) {
        if (drugText.getId() == null) {
            drugText.setId(getSeqNumDomainCapability().generateId(drugText.getEntityType(), user));
        }

        EplDrugTextDo genNameDo = drugTextConverter.convert(drugText);

        eplDrugTextDao.insert(genNameDo, user);

        if (genNameDo.getEplDtSynonyms() != null && genNameDo.getEplDtSynonyms().size() > 0) {
            eplDtSynonymDao.insert(genNameDo.getEplDtSynonyms(), user);
        }

        return drugText;
    }

    /**
     * Update the given DrugTextVo.
     * 
     * @param drugText DrugTextVo
     * @param user {@link UserVo} performing the action
     * @return {@link DrugTextVo}
     */
    @Override
    public DrugTextVo update(DrugTextVo drugText, UserVo user) {
        EplDrugTextDo drugTextDo = drugTextConverter.convert(drugText);

        // delete synonyms and insert
        eplDtSynonymDao.delete("eplDrugText.eplId", drugTextDo.getEplId());
        eplDtSynonymDao.insert(drugTextDo.getEplDtSynonyms(), user);

        eplDrugTextDao.update(drugTextDo, user);

        return drugText;

    }

    /**
     * Deletes an {@link ManagedItemVo} from the database.
     * 
     * @param item {@link ManagedItemVo} to delete
     */
    public void delete(DrugTextVo item) {
        EplDrugTextDo drugTextDo = drugTextConverter.convert(item);
        eplDrugTextDao.delete(drugTextDo);
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(DrugTextVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplDrugTextDo.DRUG_TEXT_NAME, item.getValue()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplDrugTextDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public DrugTextConverter getConverter() {
        return drugTextConverter;
    }

    /**
     * setEplDrugTextDao
     * @param eplDrugTextDao eplDrugTextDao property
     */
    public void setEplDrugTextDao(EplDrugTextDao eplDrugTextDao) {
        this.eplDrugTextDao = eplDrugTextDao;
    }

    /**
     * setEplDtSynonymDao
     * @param eplDtSynonymDao eplDtSynonymDao property
     */
    public void setEplDtSynonymDao(EplDtSynonymDao eplDtSynonymDao) {
        this.eplDtSynonymDao = eplDtSynonymDao;
    }

    /**
     * setEplProdDrugTextLAssocDao
     * @param eplProdDrugTextLAssocDao eplProdDrugTextLAssocDao property
     */
    public void setEplProdDrugTextLAssocDao(EplProdDrugTextLAssocDao eplProdDrugTextLAssocDao) {
        this.eplProdDrugTextLAssocDao = eplProdDrugTextLAssocDao;
    }

    /**
     * setDrugTextConverter
     * @param drugTextConverter drugTextConverter property
     */
    public void setDrugTextConverter(DrugTextConverter drugTextConverter) {
        this.drugTextConverter = drugTextConverter;
    }
    
    /**
     * setEplOiDrugTextNAssocDao
     * @param eplOiDrugTextNAssocDao eplOiDrugTextNAssocDao property
     */
    public void setEplOiDrugTextNAssocDao(EplOiDrugTextNAssocDao eplOiDrugTextNAssocDao) {
        this.eplOiDrugTextNAssocDao = eplOiDrugTextNAssocDao;
    }
    
    /**
     * setEplProdDrugTextNAssocDao
     * @param eplProdDrugTextNAssocDao eplProdDrugTextNAssocDao property
     */
    public void setEplProdDrugTextNAssocDao(EplProdDrugTextNAssocDao eplProdDrugTextNAssocDao) {
        this.eplProdDrugTextNAssocDao = eplProdDrugTextNAssocDao;
    }
}
