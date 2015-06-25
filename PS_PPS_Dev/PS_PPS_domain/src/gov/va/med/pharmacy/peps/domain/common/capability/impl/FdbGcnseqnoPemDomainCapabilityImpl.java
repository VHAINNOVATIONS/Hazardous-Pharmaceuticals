/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.DrugMonographGcnseqnoAssocsVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbGcnseqnoPemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbGcnseqnoPemDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbGcnseqnoPemDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbGcnseqnoPemConverter;;


/**
 * Perform CRUD operations on FDB Update
 */
public class FdbGcnseqnoPemDomainCapabilityImpl implements FdbGcnseqnoPemDomainCapability {

    private EplFdbGcnseqnoPemDao fdbGcnseqnoPemDao;
    private FdbGcnseqnoPemConverter fdbGcnseqnoPemConverter;

    /**
     *  Default constructor
     */
    public FdbGcnseqnoPemDomainCapabilityImpl() {
        super();
    }

    /**
     * create
     * @param fdbGenericNameVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    @Override
    public DrugMonographGcnseqnoAssocsVo create(DrugMonographGcnseqnoAssocsVo drugMonographGcnseqnoAssocsVo, UserVo user) {
        
        // set the EPL_ID
        String eplId = String.valueOf(drugMonographGcnseqnoAssocsVo.getMonographId()) + "0" + 
            String.valueOf(drugMonographGcnseqnoAssocsVo.getGcnSeqNo());
        drugMonographGcnseqnoAssocsVo.setEplId(Long.valueOf(eplId));
        EplFdbGcnseqnoPemDo eplFdbGcnseqnoPemDo = fdbGcnseqnoPemConverter.convert(drugMonographGcnseqnoAssocsVo);
        EplFdbGcnseqnoPemDo returnFdbGenericNameDo = fdbGcnseqnoPemDao.insert(eplFdbGcnseqnoPemDo, user);

        return fdbGcnseqnoPemConverter.convert(returnFdbGenericNameDo);
    }

    /**
     * Update the given FdbGenericNameVo.
     * 
     * @param fdbGenericNameVo fdbGenericNameVo
     * @param user performing the action
     * @return FdbGenericNameVo updated Vo
     */
    @Override
    public DrugMonographGcnseqnoAssocsVo update(DrugMonographGcnseqnoAssocsVo drugMonographGcnseqnoAssocsVo, UserVo user) {
        EplFdbGcnseqnoPemDo eplFdbGcnseqnoPemDo = fdbGcnseqnoPemConverter.convert(drugMonographGcnseqnoAssocsVo);
        fdbGcnseqnoPemDao.update(eplFdbGcnseqnoPemDo, user);

        return drugMonographGcnseqnoAssocsVo;
    }

    /**
     * Deletes all of the FdbGenericNameVo's.
     */
    @Override
    public void deleteAll() {
        fdbGcnseqnoPemDao.executeQuery("DELETE FROM EplFdbGcnseqnoPemDo");
    }
    
    /**
     * Deletes all of the DrugMonographGcnseqnoAssocsVo.
     */
    public void delete(Long monographId, Long gcnseqno) {
        fdbGcnseqnoPemDao.executeQuery("DELETE FROM EplFdbGcnseqnoPemDo where monographId = " + monographId 
            + " AND gcnseqno = " + gcnseqno);
    }
    
    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB update items
     */
    @Override
    public List<DrugMonographGcnseqnoAssocsVo> retrieveAll() {

        List<EplFdbGcnseqnoPemDo> eplDos = null;

        Criteria criteria = fdbGcnseqnoPemDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbGcnseqnoPemDo.MONOGRAPH_ID));

        eplDos = criteria.list();

        List<DrugMonographGcnseqnoAssocsVo> vos = fdbGcnseqnoPemConverter.convert(eplDos);

        return vos;
    }

    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB update items
     */
    @Override
    public List<DrugMonographGcnseqnoAssocsVo> retrieveByMonograph(Long monograph) {

        List<EplFdbGcnseqnoPemDo> eplDos = null;

        Criteria criteria = fdbGcnseqnoPemDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbGcnseqnoPemDo.GCNSEQNO));
        criteria.add(Restrictions.eq(EplFdbGcnseqnoPemDo.MONOGRAPH_ID, monograph));
        eplDos = criteria.list();

        List<DrugMonographGcnseqnoAssocsVo> vos = fdbGcnseqnoPemConverter.convert(eplDos);

        return vos;
    }
    
    /**
     * retrieve
     * @param filterDate filterDate
     * @return a list of FDB update items
     */
    @Override
    public List<DrugMonographGcnseqnoAssocsVo> retrieveByGcnseqno(Long gcnseqno) {

        List<EplFdbGcnseqnoPemDo> eplDos = null;

        Criteria criteria = fdbGcnseqnoPemDao.getCriteria();
        criteria.addOrder(Order.asc(EplFdbGcnseqnoPemDo.MONOGRAPH_ID));
        criteria.add(Restrictions.eq(EplFdbGcnseqnoPemDo.GCNSEQNO, gcnseqno));
        eplDos = criteria.list();

        List<DrugMonographGcnseqnoAssocsVo> vos = fdbGcnseqnoPemConverter.convert(eplDos);

        return vos;
    }
    
    /**
     * set the Local console info
     * 
     * @param pFdbGenericNameDao pFdbGenericNameDao
     */
    @Override
    public void setEplFdbGcnseqnoPemDao(EplFdbGcnseqnoPemDao fdbGcnseqnoPemDao) {
        this.fdbGcnseqnoPemDao = fdbGcnseqnoPemDao;
    }

    
    /**
     * set FdbGenericName Converter
     * @param fdbGenericNameConverter fdbGenericNameConverter property
     */
    @Override
    public void setFdbGcnseqnoPemConverter(FdbGcnseqnoPemConverter fdbGcnseqnoPemConverter) {
        this.fdbGcnseqnoPemConverter = fdbGcnseqnoPemConverter;
    }
 
}
