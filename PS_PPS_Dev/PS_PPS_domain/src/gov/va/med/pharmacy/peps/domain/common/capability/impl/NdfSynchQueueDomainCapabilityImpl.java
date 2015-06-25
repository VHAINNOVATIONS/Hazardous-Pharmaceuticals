/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NdfSynchQueueDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNdfSynchQueueDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdfSynchQueueDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NdfSynchQueueConverter;


/**
 * Perform CRUD operations on FDB AutoUpdate
 */
public class NdfSynchQueueDomainCapabilityImpl extends 
    ManagedDataDomainCapabilityImpl<NdfSynchQueueVo, EplNdfSynchQueueDo>
    implements NdfSynchQueueDomainCapability {
    
    private EplNdfSynchQueueDao ndfSynchQueueDao;
    private NdfSynchQueueConverter ndfSynchQueueConverter;
    
    /**
     * Default constructor
     */
    public NdfSynchQueueDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve.
     * @return a list of FDB add items
     */
    public List<NdfSynchQueueVo> retrieve() {
        List<EplNdfSynchQueueDo> eplDos = ndfSynchQueueDao.retrieve();
        List<NdfSynchQueueVo> synchQueue = ndfSynchQueueConverter
                .convert(eplDos);

        return synchQueue;
    }

    /**
     * 
     * deleteItemById
     *
     * @param id id to remove
     */
    @Override
    public void deleteItemById(String id) {
        ndfSynchQueueDao.executeQuery("DELETE FROM EplNdfSynchQueueDo where id = " + id);
    }
    

    /**
     * setEplNdfSynchQueueDao.
     *  
     * @param inNdfSynchQueueDao inNdfSynchQueueDao
     */
    public void setEplNdfSynchQueueDao(EplNdfSynchQueueDao inNdfSynchQueueDao) {
        this.ndfSynchQueueDao = inNdfSynchQueueDao;
    }

    /**
     * setNdfSynchQueueConverter.
     * @param ndfSynchQueueConverter ndfSynchQueueConverter property
     */
    public void setNdfSynchQueueConverter(
        NdfSynchQueueConverter ndfSynchQueueConverter) {
        this.ndfSynchQueueConverter = ndfSynchQueueConverter;
    }


    @Override
    public void checkForActiveDependencies(NdfSynchQueueVo managedItem, UserVo user) throws ValidationException {
        
    }

    @Override
    public Converter<NdfSynchQueueVo, EplNdfSynchQueueDo> getConverter() {
        return this.ndfSynchQueueConverter;
    }

    @Override
    public DataAccessObject getDataAccessObject() {
        return this.ndfSynchQueueDao;
    }
    
    @Override
    public boolean existsByUniquenessFields(NdfSynchQueueVo managedItem) {
        return false;
    }

    @Override
    protected Criteria createUniquenessCriteria(NdfSynchQueueVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplNdfSynchQueueDo.ID, item.getId()));

        return criteria;

    }
}
