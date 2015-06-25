/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.DrugMonographVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbMonographPemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbMonographPemDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbMonographPemDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbMonographPemConverter;;


/**
 * Perform CRUD operations on FDB Update
 */
public class FdbMonographPemDomainCapabilityImpl implements FdbMonographPemDomainCapability {

    private EplFdbMonographPemDao fdbMonographPemDao;
    private FdbMonographPemConverter fdbMonographPemConverter;

    /**
     *  Default constructor
     */
    public FdbMonographPemDomainCapabilityImpl() {
        super();
    }

    /**
     * create
     * @param DrugMonographVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    @Override
    public DrugMonographVo create(DrugMonographVo drugMonographVo, UserVo user) {
        EplFdbMonographPemDo eplFdbGenericNameDo = fdbMonographPemConverter.convert(drugMonographVo);
        EplFdbMonographPemDo returnFdbGenericNameDo = fdbMonographPemDao.insert(eplFdbGenericNameDo, user);

        return fdbMonographPemConverter.convert(returnFdbGenericNameDo);
    }

    /**
     * Update the given FdbGenericNameVo.
     * 
     * @param fdbGenericNameVo fdbGenericNameVo
     * @param user performing the action
     * @return FdbGenericNameVo updated Vo
     */
    @Override
    public DrugMonographVo update(DrugMonographVo drugMonographVo, UserVo user) {
        EplFdbMonographPemDo eplFdbGenericNameDo = fdbMonographPemConverter.convert(drugMonographVo);
        fdbMonographPemDao.update(eplFdbGenericNameDo, user);

        return drugMonographVo;
    }

    /**
     * Deletes all of the FdbGenericNameVo's.
     */
    @Override
    public void deleteAll() {
        fdbMonographPemDao.executeQuery("DELETE FROM EplFdbMonographPemDo");
    }

    /**
     * Deletes all of the EplFdbMonographPemDo.
     */
    public void delete(Long monographId) {
        fdbMonographPemDao.executeQuery("DELETE FROM EplFdbMonographPemDo where monographId = " + monographId);

    }
    
    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB update items
     */
    @Override
    public List<DrugMonographVo> retrieveAll() {

        List<EplFdbMonographPemDo> eplDos = null;

        Criteria criteria = fdbMonographPemDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbMonographPemDo.MONOGRAPH_ID));

        eplDos = criteria.list();

        List<DrugMonographVo> vos = fdbMonographPemConverter.convert(eplDos);

        return vos;
    }

    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB update items
     */
    @Override
    public List<DrugMonographVo> retrieve(Long monograph) {

        List<EplFdbMonographPemDo> eplDos = null;

        Criteria criteria = fdbMonographPemDao.getCriteria();
        criteria.add(Restrictions.eq(EplFdbMonographPemDo.MONOGRAPH_ID, monograph));
        eplDos = criteria.list();

        List<DrugMonographVo> vos = fdbMonographPemConverter.convert(eplDos);

        return vos;
    }
    
    
    /**
     * set the Local console info
     * 
     * @param fdbMonographPemDao pFdbGenericNameDao
     */
    @Override
    public void setEplFdbMonographPemDao(EplFdbMonographPemDao fdbMonographPemDao) {
        this.fdbMonographPemDao = fdbMonographPemDao;
    }

    
    /**
     * set fdbMonographPemConverter Converter
     * @param fdbMonographPemConverter fdbMonographPemConverter property
     */
    @Override
    public void setFdbMonographPemConverter(FdbMonographPemConverter fdbMonographPemConverter) {
        this.fdbMonographPemConverter = fdbMonographPemConverter;
    }
 
}
