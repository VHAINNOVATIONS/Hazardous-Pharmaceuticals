/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.domain.common.capability.PharmacySystemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPharmacySystemDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplPharmacySystemDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PharmacySystemConverter;


/**
 * Perform CRUD operations on PharmacySystems
 */
public class PharmacySystemDomainCapabilityImpl extends
    ManagedDataDomainCapabilityImpl<PharmacySystemVo, EplPharmacySystemDo> implements PharmacySystemDomainCapability {

    private EplPharmacySystemDao eplPharmacySystemDao;
    private PharmacySystemConverter pharmacySystemConverter;

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link PharmacySystemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(PharmacySystemVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.eq(EplPharmacySystemDo.SITE_NUMBER, new Integer(item.getPsSiteNumber())));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplPharmacySystemDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public PharmacySystemConverter getConverter() {
        return pharmacySystemConverter;
    }

    /**
     * setEplPharmacySystemDao
     * @param eplPharmacySystemDao eplPharmacySystemDao property
     */
    public void setEplPharmacySystemDao(EplPharmacySystemDao eplPharmacySystemDao) {
        this.eplPharmacySystemDao = eplPharmacySystemDao;
    }

    /**
     * setPharmacySystemConverter
     * @param pharmacySystemConverter pharmacySystemConverter property
     */
    public void setPharmacySystemConverter(PharmacySystemConverter pharmacySystemConverter) {
        this.pharmacySystemConverter = pharmacySystemConverter;
    }
}
