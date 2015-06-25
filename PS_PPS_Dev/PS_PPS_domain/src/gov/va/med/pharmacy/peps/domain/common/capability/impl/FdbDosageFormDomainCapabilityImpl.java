/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.FdbDosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDosageFormDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDosageFormDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDosageFormConverter;


/**
 * Perform CRUD operations on FDB Dosage Form
 */
public class FdbDosageFormDomainCapabilityImpl implements FdbDosageFormDomainCapability {

    private EplFdbDosageFormDao fdbDosageFormDao;
    private FdbDosageFormConverter fdbDosageFormConverter;

    /**
     *  Default constructor
     */
    public FdbDosageFormDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB Dosage Form items
     */
    @Override
    public List<FdbDosageFormVo> retrieve(Date filterDate) {

        List<EplFdbDosageFormDo> eplDos = null;

        Criteria criteria = fdbDosageFormDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbDosageFormDo.DRUG_DOSAGE_FORM_DESC));

        if (filterDate != null) {
            criteria.add(Restrictions.ge(EplFdbDosageFormDo.CREATED_DATE_TIME, filterDate));
        }

        eplDos = criteria.list();

        List<FdbDosageFormVo> fdbDosageFormFiles = fdbDosageFormConverter.convert(eplDos);

        return fdbDosageFormFiles;
    }

    /**
     * create
     * @param fdbDosageFormVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    @Override
    public FdbDosageFormVo create(FdbDosageFormVo fdbDosageFormVo, UserVo user) {
        EplFdbDosageFormDo eplFdbDosageFormDo = fdbDosageFormConverter.convert(fdbDosageFormVo);
        EplFdbDosageFormDo returnFdbDosageFormDo = fdbDosageFormDao.insert(eplFdbDosageFormDo, user);

        return fdbDosageFormConverter.convert(returnFdbDosageFormDo);
    }

    /**
     * Update the given FdbDosageFormVo.
     * 
     * @param fdbDosageFormVo fdbDosageFormVo
     * @param user performing the action
     * @return FdbDosageFormVo upddated Vo
     */
    @Override
    public FdbDosageFormVo update(FdbDosageFormVo fdbDosageFormVo, UserVo user) {
        EplFdbDosageFormDo eplFdbDosageFormDo = fdbDosageFormConverter.convert(fdbDosageFormVo);
        fdbDosageFormDao.update(eplFdbDosageFormDo, user);

        return fdbDosageFormVo;
    }

    /**
     * Deletes all of the FdbDosageFormVo's.
     */
    @Override
    public void deleteAll() {
        fdbDosageFormDao.executeQuery("DELETE FROM EplLocalConsoleInfoDo");
    }

    /**
     * set the Local console info
     * 
     * @param pFdbDosageFormDao pFdbDosageFormDao
     */
    @Override
    public void setEplFdbDosageFormDao(EplFdbDosageFormDao pFdbDosageFormDao) {
        this.fdbDosageFormDao = pFdbDosageFormDao;
    }

    /**
     * set the Local console info
     * 
     * @return fdbDosageFormDao 
     */
    public EplFdbDosageFormDao getEplFdbDosageFormDao() {
        return fdbDosageFormDao;
    }

    /**
     * set FdbDosageForm Converter
     * @param fdbDosageFormConverter fdbDosageFormConverter property
     */
    @Override
    public void setFdbDosageFormConverter(FdbDosageFormConverter fdbDosageFormConverter) {
        this.fdbDosageFormConverter = fdbDosageFormConverter;
    }
}
