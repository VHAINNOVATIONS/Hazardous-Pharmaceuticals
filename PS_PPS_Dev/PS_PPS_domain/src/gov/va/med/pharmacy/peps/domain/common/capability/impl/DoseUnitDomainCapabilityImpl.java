/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DoseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDoseUnitDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDoseUnitSynonymDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DoseUnitConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * PerUnit CRUD operations on dosage Units
 */
public class DoseUnitDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<DoseUnitVo, EplDoseUnitDo> implements
    DoseUnitDomainCapability {

    private static String DOSE_UNIT_EPL_ID = "eplDoseUnit.eplId";
    private EplDoseUnitDao eplDoseUnitDao;
    private EplProductDao eplProductDao;
    private EplDoseUnitSynonymDao eplDoseUnitSynonymDao;
    private DoseUnitConverter doseUnitConverter;

    /**
     * Insert the given UnitVo.
     * 
     * @param data UnitVo
     * @param user {@link UserVo} performing the action
     * @return UnitVo with new ID
     */
    @Override
    public DoseUnitVo createWithoutDuplicateCheck(DoseUnitVo data, UserVo user) {

        if (data.getId() == null) {
            data.setId(getSeqNumDomainCapability().generateId(data.getEntityType(), user));
        }

        EplDoseUnitDo objectDo = doseUnitConverter.convert(data);

        eplDoseUnitDao.insert(objectDo, user);

        if (objectDo.getEplDoseUnitSynonyms() != null && objectDo.getEplDoseUnitSynonyms().size() > 0) {
            eplDoseUnitSynonymDao.insert(objectDo.getEplDoseUnitSynonyms(), user);
        }

        return data;
    }

    /**
     * Update specified unit in database
     * 
     * @param data to update
     * @param user {@link UserVo} performing the action
     * @return DoseUnitVo
     */
    @Override
    public DoseUnitVo update(DoseUnitVo data, UserVo user) {
        EplDoseUnitDo fedDo = doseUnitConverter.convert(data);

        eplDoseUnitSynonymDao.delete(DOSE_UNIT_EPL_ID, fedDo.getEplId());
        eplDoseUnitSynonymDao.insert(fedDo.getEplDoseUnitSynonyms(), user);

        eplDoseUnitDao.update(fedDo, user);

        return data;
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(DoseUnitVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplDoseUnitDo.NAME, item.getDoseUnitName()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplDoseUnitDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public DoseUnitConverter getConverter() {
        return doseUnitConverter;
    }

    /**
     * setEplDoseUnitDao
     * @param eplDoseUnitDao eplDoseUnitDao property
     */
    public void setEplDoseUnitDao(EplDoseUnitDao eplDoseUnitDao) {
        this.eplDoseUnitDao = eplDoseUnitDao;
    }

    /**
     * setEplDoseUnitSynonymDao
     * @param eplDoseUnitSynonymDao eplDoseUnitSynonymDao property
     */
    public void setEplDoseUnitSynonymDao(EplDoseUnitSynonymDao eplDoseUnitSynonymDao) {
        this.eplDoseUnitSynonymDao = eplDoseUnitSynonymDao;
    }

    /**
     * setDoseUnitConverter
     * @param doseUnitConverter doseUnitConverter property
     */
    public void setDoseUnitConverter(DoseUnitConverter doseUnitConverter) {
        this.doseUnitConverter = doseUnitConverter;
    }

    /**
     * Delete associated domain from data base if IEN is not assigned and other objects are not dependent on this object.
     * 
     * @param doseUnitVo to delete
     * @param user requesting the delete
     * 
     * @throws ValidationException to validate the IEN and dependencies
     * 
     */
    @Override
    public void deletePending(DoseUnitVo doseUnitVo, UserVo user) throws ValidationException {
        
        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE DOSE_UNIT_ID_FK = " + doseUnitVo.getId();
        query.append(whereClause);
        
        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_DRUGUNIT_ASSOC,
                                      products.size(), products.get(0).getVaProductName());


    }

}
