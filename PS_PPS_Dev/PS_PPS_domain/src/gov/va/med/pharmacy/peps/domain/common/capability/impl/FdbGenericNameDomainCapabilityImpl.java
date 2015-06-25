/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.FdbGenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbGenericNameDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbGenericNameDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbGenericNameDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbGenericNameConverter;


/**
 * Perform CRUD operations on FDB Update
 */
public class FdbGenericNameDomainCapabilityImpl implements FdbGenericNameDomainCapability {

    private EplFdbGenericNameDao fdbGenericNameDao;
    private FdbGenericNameConverter fdbGenericNameConverter;

    /**
     *  Default constructor
     */
    public FdbGenericNameDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB update items
     */
    @Override
    public List<FdbGenericNameVo> retrieve(Date filterDate) {

        List<EplFdbGenericNameDo> eplDos = null;

        Criteria criteria = fdbGenericNameDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbGenericNameDo.FDB_GENERIC_DRUG_NAME));

        if (filterDate != null) {
            criteria.add(Restrictions.ge(EplFdbGenericNameDo.CREATED_DATE_TIME, filterDate));
        }

        eplDos = criteria.list();

        List<FdbGenericNameVo> fdbGenericNameFiles = fdbGenericNameConverter.convert(eplDos);

        return fdbGenericNameFiles;
    }

    /**
     * create
     * @param fdbGenericNameVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    @Override
    public FdbGenericNameVo create(FdbGenericNameVo fdbGenericNameVo, UserVo user) {
        EplFdbGenericNameDo eplFdbGenericNameDo = fdbGenericNameConverter.convert(fdbGenericNameVo);
        EplFdbGenericNameDo returnFdbGenericNameDo = fdbGenericNameDao.insert(eplFdbGenericNameDo, user);

        return fdbGenericNameConverter.convert(returnFdbGenericNameDo);
    }

    /**
     * Update the given FdbGenericNameVo.
     * 
     * @param fdbGenericNameVo fdbGenericNameVo
     * @param user performing the action
     * @return FdbGenericNameVo upddated Vo
     */
    @Override
    public FdbGenericNameVo update(FdbGenericNameVo fdbGenericNameVo, UserVo user) {
        EplFdbGenericNameDo eplFdbGenericNameDo = fdbGenericNameConverter.convert(fdbGenericNameVo);
        fdbGenericNameDao.update(eplFdbGenericNameDo, user);

        return fdbGenericNameVo;
    }

    /**
     * Deletes all of the FdbGenericNameVo's.
     */
    @Override
    public void deleteAll() {
        fdbGenericNameDao.executeQuery("DELETE FROM EplLocalConsoleInfoDo");
    }

    /**
     * set the Local console info
     * 
     * @param pFdbGenericNameDao pFdbGenericNameDao
     */
    @Override
    public void setEplFdbGenericNameDao(EplFdbGenericNameDao pFdbGenericNameDao) {
        this.fdbGenericNameDao = pFdbGenericNameDao;
    }

    /**
     * set the Local console info
     * 
     * @return fdbGenericNameDao 
     */
    public EplFdbGenericNameDao getEplFdbGenericNameDao() {
        return fdbGenericNameDao;
    }

    /**
     * set FdbGenericName Converter
     * @param fdbGenericNameConverter fdbGenericNameConverter property
     */
    @Override
    public void setFdbGenericNameConverter(FdbGenericNameConverter fdbGenericNameConverter) {
        this.fdbGenericNameConverter = fdbGenericNameConverter;
    }
}
