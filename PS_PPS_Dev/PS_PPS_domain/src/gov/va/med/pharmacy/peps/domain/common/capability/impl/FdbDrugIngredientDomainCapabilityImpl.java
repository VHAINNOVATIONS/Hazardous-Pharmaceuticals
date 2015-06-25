/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.FdbDrugIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbDrugIngredientDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbDrugIngredientDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDrugIngredientDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbDrugIngredientConverter;


/**
 * Perform CRUD operations on FDB Drug ingredient
 */
public class FdbDrugIngredientDomainCapabilityImpl implements FdbDrugIngredientDomainCapability {

    private EplFdbDrugIngredientDao fdbDrugIngredientDao;
    private FdbDrugIngredientConverter fdbDrugIngredientConverter;

    /**
     *  Default constructor
     */
    public FdbDrugIngredientDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB Drug Ingredient items
     */
    @Override
    public List<FdbDrugIngredientVo> retrieve(Date filterDate) {
        List<EplFdbDrugIngredientDo> eplDos = null;

        Criteria criteria = fdbDrugIngredientDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbDrugIngredientDo.FDB_DRUG_INGREDIENT));

        if (filterDate != null) {
            criteria.add(Restrictions.ge(EplFdbDrugIngredientDo.CREATED_DATE_TIME, filterDate));
        }

        eplDos = criteria.list();

        List<FdbDrugIngredientVo> fdbDrugIngredientFiles = fdbDrugIngredientConverter.convert(eplDos);

        return fdbDrugIngredientFiles;
    }

    /**
     * create
     * @param fdbDrugIngredientVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    @Override
    public FdbDrugIngredientVo create(FdbDrugIngredientVo fdbDrugIngredientVo, UserVo user) {
        EplFdbDrugIngredientDo eplFdbDrugIngredientDo = fdbDrugIngredientConverter.convert(fdbDrugIngredientVo);
        EplFdbDrugIngredientDo returnFdbDrugIngredientDo = fdbDrugIngredientDao.insert(eplFdbDrugIngredientDo, user);

        return fdbDrugIngredientConverter.convert(returnFdbDrugIngredientDo);
    }

    /**
     * Update the given FdbDrugIngredientVo.
     * 
     * @param fdbDrugIngredientVo fdbDrugIngredientVo
     * @param user performing the action
     * @return FdbDrugIngredientVo upddated Vo
     */
    @Override
    public FdbDrugIngredientVo update(FdbDrugIngredientVo fdbDrugIngredientVo, UserVo user) {
        EplFdbDrugIngredientDo eplFdbDrugIngredientDo = fdbDrugIngredientConverter.convert(fdbDrugIngredientVo);
        fdbDrugIngredientDao.update(eplFdbDrugIngredientDo, user);

        return fdbDrugIngredientVo;
    }

    /**
     * Deletes all of the FdbDrugIngredientVo's.
     */
    @Override
    public void deleteAll() {
        fdbDrugIngredientDao.executeQuery("DELETE FROM EplLocalConsoleInfoDo");
    }

    /**
     * set the Local console info
     * 
     * @param pFdbDrugIngredientDao pFdbDrugIngredientDao
     */
    @Override
    public void setEplFdbDrugIngredientDao(EplFdbDrugIngredientDao pFdbDrugIngredientDao) {
        this.fdbDrugIngredientDao = pFdbDrugIngredientDao;
    }

    /**
     * set the Local console info
     * 
     * @return fdbDrugIngredientDao 
     */
    public EplFdbDrugIngredientDao getEplFdbDrugIngredientDao() {
        return fdbDrugIngredientDao;
    }

    /**
     * set FdbDrugIngredient Converter
     * @param fdbDrugIngredientConverter fdbDrugIngredientConverter property
     */
    @Override
    public void setFdbDrugIngredientConverter(FdbDrugIngredientConverter fdbDrugIngredientConverter) {
        this.fdbDrugIngredientConverter = fdbDrugIngredientConverter;
    }
}
