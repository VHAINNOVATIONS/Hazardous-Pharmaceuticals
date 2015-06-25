/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.FdbDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugClassDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDrugClassDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDrugClassDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDrugClassConverter;


/**
 * Perform CRUD operations on FDB Drug Class
 */
public class FdbDrugClassDomainCapabilityImpl implements FdbDrugClassDomainCapability {

    private EplFdbDrugClassDao fdbDrugClassDao;
    private FdbDrugClassConverter fdbDrugClassConverter;

    /**
     *  Default constructor
     */
    public FdbDrugClassDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB Drug Class items
     */
    @Override
    public List<FdbDrugClassVo> retrieve(Date filterDate) {

        List<EplFdbDrugClassDo> eplDos = null;

        Criteria criteria = fdbDrugClassDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbDrugClassDo.FDB_DRUG_CLASS));

        if (filterDate != null) {
            criteria.add(Restrictions.ge(EplFdbDrugClassDo.CREATED_DATE_TIME, filterDate));
        }

        eplDos = criteria.list();

        List<FdbDrugClassVo> fdbDrugClassFiles = fdbDrugClassConverter.convert(eplDos);

        return fdbDrugClassFiles;
    }

    /**
     * create
     * @param fdbDrugClassVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    @Override
    public FdbDrugClassVo create(FdbDrugClassVo fdbDrugClassVo, UserVo user) {

        EplFdbDrugClassDo eplFdbDrugClassDo = fdbDrugClassConverter.convert(fdbDrugClassVo);
        EplFdbDrugClassDo returnFdbDrugClassDo = fdbDrugClassDao.insert(eplFdbDrugClassDo, user);

        return fdbDrugClassConverter.convert(returnFdbDrugClassDo);
    }

    /**
     * Update the given FdbDrugClassVo.
     * 
     * @param fdbDrugClassVo fdbDrugClassVo
     * @param user performing the action
     * @return FdbDrugClassVo upddated Vo
     */
    @Override
    public FdbDrugClassVo update(FdbDrugClassVo fdbDrugClassVo, UserVo user) {
        EplFdbDrugClassDo eplFdbDrugClassDo = fdbDrugClassConverter.convert(fdbDrugClassVo);
        fdbDrugClassDao.update(eplFdbDrugClassDo, user);

        return fdbDrugClassVo;
    }

    /**
     * Deletes all of the FdbDrugClassVo's.
     */
    @Override
    public void deleteAll() {
        fdbDrugClassDao.executeQuery("DELETE FROM EplLocalConsoleInfoDo");
    }

    /**
     * set the Local console info
     * 
     * @param pFdbDrugClassDao pFdbDrugClassDao
     */
    @Override
    public void setEplFdbDrugClassDao(EplFdbDrugClassDao pFdbDrugClassDao) {
        this.fdbDrugClassDao = pFdbDrugClassDao;
    }

    /**
     * set the Local console info
     * 
     * @return fdbDrugClassDao 
     */
    public EplFdbDrugClassDao getEplFdbDrugClassDao() {
        return fdbDrugClassDao;
    }

    /**
     * set FdbDrugClass Converter
     * @param fdbDrugClassConverter fdbDrugClassConverter property
     */
    @Override
    public void setFdbDrugClassConverter(FdbDrugClassConverter fdbDrugClassConverter) {
        this.fdbDrugClassConverter = fdbDrugClassConverter;
    }
}
