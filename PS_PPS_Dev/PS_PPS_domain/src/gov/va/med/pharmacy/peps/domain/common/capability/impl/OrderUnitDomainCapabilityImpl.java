/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOrderUnitDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderUnitDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.OrderUnitConverter;


/**
 * Perform CRUD operations on order unit domain
 */
public class OrderUnitDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<OrderUnitVo, EplOrderUnitDo> implements
    OrderUnitDomainCapability {

    private EplOrderUnitDao eplOrderUnitDao;
    private OrderUnitConverter orderUnitConverter;

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link OrderUnitVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(OrderUnitVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplOrderUnitDo.ABBREV, item.getAbbrev()));

        return criteria;
    }

    /**
     * retrieve order unit DAO
     * 
     * @param eplOrderUnitDao EplOrderUnitDao property
     */
    public void setEplOrderUnitDao(EplOrderUnitDao eplOrderUnitDao) {
        this.eplOrderUnitDao = eplOrderUnitDao;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplOrderUnitDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public OrderUnitConverter getConverter() {
        return orderUnitConverter;
    }

    /**
     * setOrderUnitConverter
     * @param orderUnitConverter orderUnitConverter property
     */
    public void setOrderUnitConverter(OrderUnitConverter orderUnitConverter) {
        this.orderUnitConverter = orderUnitConverter;
    }

}
