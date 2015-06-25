/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DispenseUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVaDispenseUnitDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDispenseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DispenseUnitConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on dosage forms
 */
public class DispenseUnitDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<DispenseUnitVo, EplVaDispenseUnitDo>
    implements DispenseUnitDomainCapability {

    private EplVaDispenseUnitDao eplVaDispenseUnitDao;
    private EplProductDao eplProductDao;
    private DispenseUnitConverter dispenseUnitConverter;

    /**
     * deleteItem
     * @param dispenseUnitVo dispenseUnitVo
     * @throws ValidationException ValidationException
     */
    @Override
    public void deleteItem(DispenseUnitVo dispenseUnitVo) throws ValidationException {
        List<EplVaDispenseUnitDo> dispenseUnitDo = getDataAccessObject().retrieve(EplDrugUnitDo.EPL_ID,
                Long.valueOf(dispenseUnitVo.getId()));

        if (dispenseUnitDo.size() == 1) {
            if (dispenseUnitDo.get(0).getNdfDispenseunitIen() == null) {
                try {
                    checkForActiveDependencies(dispenseUnitVo, null);
                } catch (Exception e) {
                    throw new ValidationException(ValidationException.CANNOT_DELETE,
                        dispenseUnitVo.getValue(), " because another item is using it.");
                }

                getDataAccessObject().delete(dispenseUnitDo.get(0));
            } else {
                throw new ValidationException(ValidationException.CANNOT_DELETE,
                    dispenseUnitVo.getValue(), " because it has already synched with NDF.");
            }
        }
    }

    /**
     * dispenseUnitVo cannot be in-activated if it has an active product.
     *  
     * @param dispenseUnitVo dispenseUnitVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(DispenseUnitVo dispenseUnitVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE DISPENSE_UNIT_ID_FK = " + dispenseUnitVo.getId() + " AND ITEM_STATUS LIKE 'ACTIVE'";
        query.append(whereClause);

        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        // throw a validation exception if the dispenseUnit is Inactive.
        throw new ValidationException(ValidationException.INACTIVATE_DISPENSEUNIT,
                                      products.size(), products.get(0).getVaProductName());

    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link DispenseUnitVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(DispenseUnitVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplVaDispenseUnitDo.DISPENSE_UNIT_NAME, item.getValue()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplVaDispenseUnitDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public DispenseUnitConverter getConverter() {
        return dispenseUnitConverter;
    }

    /**
     * setEplVaDispenseUnitDao
     * @param eplVaDispenseUnitDao eplVaDispenseUnitDao property
     */
    public void setEplVaDispenseUnitDao(EplVaDispenseUnitDao eplVaDispenseUnitDao) {
        this.eplVaDispenseUnitDao = eplVaDispenseUnitDao;
    }

    /**
     * setDispenseUnitConverter
     * @param dispenseUnitConverter dispenseUnitConverter property
     */
    public void setDispenseUnitConverter(DispenseUnitConverter dispenseUnitConverter) {
        this.dispenseUnitConverter = dispenseUnitConverter;
    }

    /**
     * setEplProductDao
     * @param eplProductDao eplProductDao property
     */
    public void setEplProductDao(EplProductDao eplProductDao) {
        this.eplProductDao = eplProductDao;
    }

    /**
     * Delete associated domain from data base if IEN is not assigned and other objects are not dependent on this object.
     * 
     * @param dispenseUnitVo to delete
     * @param user requesting the delete
     * 
     * @throws ValidationException to validate the IEN and dependencies
     * 
     */
    @Override
    public void deletePending(DispenseUnitVo dispenseUnitVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE DISPENSE_UNIT_ID_FK = " + dispenseUnitVo.getId();
        query.append(whereClause);

        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_DISPENSEUNIT,
                                      products.size(), products.get(0).getVaProductName());

    }

}
