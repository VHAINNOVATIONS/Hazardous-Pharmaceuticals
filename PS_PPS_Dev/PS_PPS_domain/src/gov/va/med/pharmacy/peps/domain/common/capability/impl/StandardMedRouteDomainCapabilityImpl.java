/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplStandardMedRouteDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVuidStatusHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplStandardMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVuidStatusHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.utility.SchemaUtility;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.StandardMedRouteConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.VuidStatusHistoryConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on standard medication route
 */
public class StandardMedRouteDomainCapabilityImpl extends
    ManagedDataDomainCapabilityImpl<StandardMedRouteVo, EplStandardMedRouteDo> implements
    StandardMedRouteDomainCapability {

    private EplStandardMedRouteDao eplStandardMedRouteDao;
    private StandardMedRouteConverter standardMedRouteConverter;
    private EplVuidStatusHistoryDao eplVuidStatusHistoryDao;
    private VuidStatusHistoryConverter vuidStatusHistoryConverter;

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(StandardMedRouteVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplStandardMedRouteDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * retrieve
     * @param eplId eplId
     * @return StandardMedicationRouteVo
     * @throws ItemNotFoundException 
     */
    public StandardMedRouteVo retrieve(String eplId) throws ItemNotFoundException {
        StandardMedRouteVo standardMedicationRouteVo = super.retrieve(eplId);
        
        StringBuffer queryBuffer = new StringBuffer(HqlBuilder.create("SELECT item FROM").append(
            EplVuidStatusHistoryDo.class).append("item WHERE item.").toString());

        queryBuffer.append(EplVuidStatusHistoryDo.VUID).append(" = '").
            append(standardMedicationRouteVo.getVuid()).append("' AND ");
        queryBuffer.append(EplVuidStatusHistoryDo.ITEM_TYPE).
            append(" = ").append(PPSConstants.VUID_STANDARDMEDROUTE);
        List<EplVuidStatusHistoryDo> vuidStatusHistroyList = eplVuidStatusHistoryDao.executeHqlQuery(queryBuffer.toString());
        
        List<VuidStatusHistoryVo> vuidStatusHistroyVoList = vuidStatusHistoryConverter.convert(vuidStatusHistroyList);
        standardMedicationRouteVo.setEffectiveDates(vuidStatusHistroyVoList);
       
        return standardMedicationRouteVo;
        
    }
    
    
    /**
     * Get all {@link ItemStatus#ACTIVE} {@link ManagedItemVo} of the given {@link EntityType}.
     * 
     * @return Active List<ManagedItemVo>
     */
    public List<StandardMedRouteVo> retrieve() {
        Criteria criteria = getDataAccessObject().getCriteria();
        String sortPropertyName = SchemaUtility.getPropertyName(getDataObjectClass(), FieldKey.VALUE);

        if (sortPropertyName != null && sortPropertyName.trim().length() > 0) {
            Order order = Order.asc(sortPropertyName);
            order.ignoreCase();
            criteria.addOrder(order);
        }

        List<EplStandardMedRouteDo> results = criteria.list();

        return getConverter().convert(results);
    }
    
    /**
     * update
     * @param medRouteVo medRouteVo
     * @param user user
     * @return StandardMedicationRouteVo
     * @throws DuplicateItemException  DuplicateItemException
     */
    public StandardMedRouteVo update(StandardMedRouteVo medRouteVo, UserVo user) throws DuplicateItemException {
        StandardMedRouteVo standardMedicationRouteVo = super.update(medRouteVo, user);
        
        return standardMedicationRouteVo;
        
    }
    
    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplStandardMedRouteDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public StandardMedRouteConverter getConverter() {
        return standardMedRouteConverter;
    }

    /**
     * setEplStandardMedRouteDao.
     * @param eplStandardMedRouteDao eplStandardMedRouteDao property
     */
    public void setEplStandardMedRouteDao(EplStandardMedRouteDao eplStandardMedRouteDao) {
        this.eplStandardMedRouteDao = eplStandardMedRouteDao;
    }
    
    /**
     * setEplVuidStatusHistoryDao for StandardMedRouteDomainCapabilityImpl.
     * @param eplVuidStatusHistoryDao eplVuidStatusHistoryDao property
     */
    public void setEplVuidStatusHistoryDao(
            EplVuidStatusHistoryDao eplVuidStatusHistoryDao) {
        this.eplVuidStatusHistoryDao = eplVuidStatusHistoryDao;
    }

    /**
     * setVuidStatusHistoryConverter for StandardMedRouteDomainCapabilityImpl.
     * @param vuidStatusHistoryConverter vuidStatusHistoryConverter property
     */
    public void setVuidStatusHistoryConverter(
            VuidStatusHistoryConverter vuidStatusHistoryConverter) {
        this.vuidStatusHistoryConverter = vuidStatusHistoryConverter;
    }

    /**
     * setStandardMedRouteConverter for StandardMedRouteDomainCapabilityImpl.
     * @param standardMedRouteConverter standardMedRouteConverter property
     */
    public void setStandardMedRouteConverter(StandardMedRouteConverter standardMedRouteConverter) {
        this.standardMedRouteConverter = standardMedRouteConverter;
    }
}
