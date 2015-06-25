/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultType;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.RxConsultDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProdWarnLabelLAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplWarnLabelDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdWarnLabelLAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplWarnLabelDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.RxConsultConverter;


/**
 * Perform CRUD operations on rx consult
 */
public class RxConsultDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<RxConsultVo, EplWarnLabelDo> implements
    RxConsultDomainCapability {

    private EplWarnLabelDao eplWarnLabelDao;
    private EplProdWarnLabelLAssocDao eplProdWarnLabelLAssocDao;

    private RxConsultConverter rxConsultConverter;

    /**
     * Get all active, LOCAL, NATIONAL, and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    public List<RxConsultVo> getLocalRxConsult() {
        Criteria criteria = eplWarnLabelDao.getCriteria();
        criteria.add(Restrictions.eq(EplWarnLabelDo.ITEM_STATUS, ItemStatus.ACTIVE.name()));
        criteria.addOrder(Order.asc(EplWarnLabelDo.RX_CONSULT_NAME));

        List<EplWarnLabelDo> data = criteria.list();

        return rxConsultConverter.convert(data);
    }

    /**
     * Get all active, NATIONAL and COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    public List<RxConsultVo> getNationalRxConsult() {
        Criteria criteria = eplWarnLabelDao.getCriteria();
        criteria.add(Restrictions.eq(EplWarnLabelDo.ITEM_STATUS, ItemStatus.ACTIVE.name()));

        Criterion national = Restrictions.eq(EplWarnLabelDo.WARNING_LABEL_TYPE, RxConsultType.NATIONAL.name());
        Criterion cots = Restrictions.eq(EplWarnLabelDo.WARNING_LABEL_TYPE, RxConsultType.COTS.name());
        criteria.add(Restrictions.or(national, cots));
        criteria.addOrder(Order.asc(EplWarnLabelDo.RX_CONSULT_NAME));

        List<EplWarnLabelDo> data = criteria.list();

        return rxConsultConverter.convert(data);
    }

    /**
     * Get all active, COTS RX Consult.
     * 
     * @return List<RxConsultVo>
     */
    public List<RxConsultVo> getCotsRxConsult() {
        Criteria criteria = eplWarnLabelDao.getCriteria();
        criteria.add(Restrictions.eq(EplWarnLabelDo.ITEM_STATUS, ItemStatus.ACTIVE.name()));
        criteria.add(Restrictions.eq(EplWarnLabelDo.WARNING_LABEL_TYPE, RxConsultType.COTS.name()));

        List<EplWarnLabelDo> data = criteria.list();

        return rxConsultConverter.convert(data);
    }

    /**
     * Deletes an {@link ManagedItemVo} from the database.
     * 
     * @param item {@link ManagedItemVo} to delete
     */
    public void delete(RxConsultVo item) {
        EplWarnLabelDo warnLabel = rxConsultConverter.convert(item);
        eplWarnLabelDao.delete(warnLabel);
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(RxConsultVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplWarnLabelDo.RX_CONSULT_NAME, item.getValue()));

        return criteria;
    }

    /**
     * Reassociates all items using the existing item to the new item.
     * <p>
     * Deletes existing item after reassociating products with the new item.
     * 
     * @param existingItem existing Local {@link RxConsultVo} to associate Products with
     * @param nationalItem new {@link RxConsultVo} to associate Products with
     * @param user {@link UserVo} performing the action
     */
    public void reassociateExistingItems(RxConsultVo existingItem, RxConsultVo nationalItem, UserVo user) {
        Criteria criteria = eplProdWarnLabelLAssocDao.getCriteria();
        criteria.add(Restrictions.eq(EplProdWarnLabelLAssocDo.EPL_ID_WARN_LABEL_FK, new Long(existingItem.getId())));

        List<EplProdWarnLabelLAssocDo> existingAssocs = criteria.list();
        List<EplProdWarnLabelLAssocDo> newAssocs = new ArrayList<EplProdWarnLabelLAssocDo>(existingAssocs.size());

        for (EplProdWarnLabelLAssocDo eplProdWarnLabelLAssocDo : existingAssocs) {
            EplProdWarnLabelLAssocDo newAssoc = eplProdWarnLabelLAssocDo.copy();
            EplWarnLabelDo warnLabel = new EplWarnLabelDo();
            warnLabel.setEplId(new Long(nationalItem.getId()));
            newAssoc.setEplWarnLabel(warnLabel);
            newAssoc.getKey().setEplIdWarnLabelFk(warnLabel.getEplId());
            newAssocs.add(newAssoc);
        }

        eplProdWarnLabelLAssocDao.delete(EplProdWarnLabelLAssocDo.EPL_ID_WARN_LABEL_FK, new Long(existingItem.getId()));
        eplProdWarnLabelLAssocDao.insert(newAssocs, user);

        delete(existingItem);
    }

    /**
     * setEplWarnLabelDao
     * 
     * @param eplWarnLabelDao eplWarnLabelDao property
     */
    public void setEplWarnLabelDao(EplWarnLabelDao eplWarnLabelDao) {
        this.eplWarnLabelDao = eplWarnLabelDao;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplWarnLabelDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public RxConsultConverter getConverter() {
        return rxConsultConverter;
    }

    /**
     * setEplProdWarnLabelLAssocDao
     * 
     * @param eplProdWarnLabelLAssocDao eplProdWarnLabelLAssocDao property
     */
    public void setEplProdWarnLabelLAssocDao(EplProdWarnLabelLAssocDao eplProdWarnLabelLAssocDao) {
        this.eplProdWarnLabelLAssocDao = eplProdWarnLabelLAssocDao;
    }

    /**
     * setRxConsultConverter
     * 
     * @param rxConsultConverter rxConsultConverter property
     */
    public void setRxConsultConverter(RxConsultConverter rxConsultConverter) {
        this.rxConsultConverter = rxConsultConverter;
    }
}
