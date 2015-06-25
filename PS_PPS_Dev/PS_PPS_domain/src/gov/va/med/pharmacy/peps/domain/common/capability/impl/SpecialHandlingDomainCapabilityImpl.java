/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SpecialHandlingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSpecialHandlingDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplSpecialHandlingDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.SpecialHandlingConverter;


/**
 * Perform CRUD operations on special handling
 */
public class SpecialHandlingDomainCapabilityImpl extends
    ManagedDataDomainCapabilityImpl<SpecialHandlingVo, EplSpecialHandlingDo> implements SpecialHandlingDomainCapability {

    private EplSpecialHandlingDao eplSpecialHandlingDao;
    private SpecialHandlingConverter specialHandlingConverter;

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link SpecialHandlingVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(SpecialHandlingVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplSpecialHandlingDo.CODE, item.getCode()));

        return criteria;
    }

    /**
     * retrieve special handling DAO
     * 
     * @param eplSpecialHandlingDao EplSpecialHandlingDao property
     */
    public void setEplSpecialHandlingDao(EplSpecialHandlingDao eplSpecialHandlingDao) {
        this.eplSpecialHandlingDao = eplSpecialHandlingDao;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplSpecialHandlingDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public SpecialHandlingConverter getConverter() {
        return specialHandlingConverter;
    }

    /**
     * Description
     * 
     * @param specialHandlingConverter specialHandlingConverter property
     */
    public void setSpecialHandlingConverter(SpecialHandlingConverter specialHandlingConverter) {
        this.specialHandlingConverter = specialHandlingConverter;
    }
}
