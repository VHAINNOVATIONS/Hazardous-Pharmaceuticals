/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.FdbDrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugUnitDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDrugUnitDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDrugUnitConverter;


/**
 * Perform CRUD operations on FDB Drug Unit
 */
public class FdbDrugUnitDomainCapabilityImpl implements FdbDrugUnitDomainCapability {

    private EplFdbDrugUnitDao fdbDrugUnitDao;
    private FdbDrugUnitConverter fdbDrugUnitConverter;

    /**
     *  Default constructor
     */
    public FdbDrugUnitDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB Drug Unit items
     */
    @Override
    public List<FdbDrugUnitVo> retrieve(Date filterDate) {
        List<EplFdbDrugUnitDo> eplDos = null;

        Criteria criteria = fdbDrugUnitDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbDrugUnitDo.FDB_DRUG_STRENGTH_UNITS));

        if (filterDate != null) {
            criteria.add(Restrictions.ge(EplFdbDrugUnitDo.CREATED_DATE_TIME, filterDate));
        }

        eplDos = criteria.list();

        List<FdbDrugUnitVo> fdbDrugUnitFiles = fdbDrugUnitConverter.convert(eplDos);

        return fdbDrugUnitFiles;
    }

    /**
     * create
     * @param fdbDrugUnitVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    @Override
    public FdbDrugUnitVo create(FdbDrugUnitVo fdbDrugUnitVo, UserVo user) {
        EplFdbDrugUnitDo eplFdbDrugUnitDo = fdbDrugUnitConverter.convert(fdbDrugUnitVo);
        EplFdbDrugUnitDo returnFdbDrugUnitDo = fdbDrugUnitDao.insert(eplFdbDrugUnitDo, user);

        return fdbDrugUnitConverter.convert(returnFdbDrugUnitDo);
    }

    /**
     * Update the given FdbDrugUnitVo.
     * 
     * @param fdbDrugUnitVo fdbDrugUnitVo
     * @param user performing the action
     * @return FdbDrugUnitVo upddated Vo
     */
    @Override
    public FdbDrugUnitVo update(FdbDrugUnitVo fdbDrugUnitVo, UserVo user) {
        EplFdbDrugUnitDo eplFdbDrugUnitDo = fdbDrugUnitConverter.convert(fdbDrugUnitVo);
        fdbDrugUnitDao.update(eplFdbDrugUnitDo, user);

        return fdbDrugUnitVo;
    }

    /**
     * Deletes all of the FdbDrugUnitVo's.
     */
    @Override
    public void deleteAll() {
        fdbDrugUnitDao.executeQuery("DELETE FROM EplLocalConsoleInfoDo");
    }

    /**
     * set the Local console info
     * 
     * @param pFdbDrugUnitDao pFdbDrugUnitDao
     */
    @Override
    public void setEplFdbDrugUnitDao(EplFdbDrugUnitDao pFdbDrugUnitDao) {
        this.fdbDrugUnitDao = pFdbDrugUnitDao;
    }

    /**
     * set the Local console info
     * 
     * @return fdbDrugUnitDao 
     */
    public EplFdbDrugUnitDao getEplFdbDrugUnitDao() {
        return fdbDrugUnitDao;
    }

    /**
     * set FdbDrugUnit Converter
     * @param fdbDrugUnitConverter fdbDrugUnitConverter property
     */
    @Override
    public void setFdbDrugUnitConverter(FdbDrugUnitConverter fdbDrugUnitConverter) {
        this.fdbDrugUnitConverter = fdbDrugUnitConverter;
    }
}
