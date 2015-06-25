/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbAutoUpdateDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAutoUpdateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbAutoUpdateConverter;


/**
 * Perform CRUD operations on FDB AutoUpdate
 */
public class FdbAutoUpdateDomainCapabilityImpl extends 
    ManagedDataDomainCapabilityImpl<FdbAutoUpdateVo, EplFdbAutoUpdateDo>
    implements FdbAutoUpdateDomainCapability {
    
    private EplFdbAutoUpdateDao fdbAutoUpdateDao;
    private FdbAutoUpdateConverter fdbAutoUpdateConverter;
    
    /**
     * Default constructor
     */
    public FdbAutoUpdateDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve.
     * @return a list of FDB add items
     */
    public List<FdbAutoUpdateVo> retrieve() {
        List<EplFdbAutoUpdateDo> eplDos = fdbAutoUpdateDao.retrieve();
        List<FdbAutoUpdateVo> fdbAutoUpdateFiles = fdbAutoUpdateConverter
                .convert(eplDos);

        return fdbAutoUpdateFiles;
    }

    
    /**
     * delets all rows from the table > 90 days old.
     */
    public void deleteOver90() {

        Date today = new Date(); 
        Calendar cal = new GregorianCalendar(); 
        cal.setTime(today); 
        cal.add(Calendar.DAY_OF_MONTH, PPSConstants.IMINUS90); 
        Date today90 = cal.getTime(); 
        
        //Local format is used when in Derby, National Format when in Oracle
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss", Locale.US);
        SimpleDateFormat nationalFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);

        StringBuffer query = new StringBuffer();
        query.append("DELETE FROM EplFdbAutoUpdateDo where CREATED_DTM <= '");

        
        if (System.getProperty(PPSConstants.OS_NAME).equalsIgnoreCase("win")) {
            query.append(localFormat.format(today90) + "'");
        } else {
            query.append(nationalFormat.format(today90) + "'");
        }
        
        fdbAutoUpdateDao.executeQuery(query.toString());
    }
    
   

    /**
     * Update the given FdbAutoUpdateVo.
     * 
     * @param fdbAutoUpdateVo fdbAutoUpdateVo
     * @param user {@link UserVo} performing the action
     * @return FdbAutoUpdateVo updated Vo
     */
    public FdbAutoUpdateVo update(FdbAutoUpdateVo fdbAutoUpdateVo, UserVo user) {
        EplFdbAutoUpdateDo eplFdbAutoUpdateDo = fdbAutoUpdateConverter
                .convert(fdbAutoUpdateVo);
        fdbAutoUpdateDao.update(eplFdbAutoUpdateDo, user);

        return fdbAutoUpdateVo;
    }

    /**
     * Deletes all of the FdbAutoUpdateVo's.
     */
    public void deleteAll() {
        fdbAutoUpdateDao.executeQuery("DELETE FROM EplFdbAutoUpdateDo");
    }
    
    /**
     * 
     * deleteItemById
     *
     * @param id id to remove
     */
    @Override
    public void deleteItemById(String id) {
        fdbAutoUpdateDao.executeQuery("DELETE FROM EplFdbAutoUpdateDo where epl_id = " + id);
    }
    

    /**
     * setEplFdbAutoUpdateDao.
     *  
     * @param inFdbAutoUpdateDao fdbAutoUpdateDao
     */
    public void setEplFdbAutoUpdateDao(EplFdbAutoUpdateDao inFdbAutoUpdateDao) {
        this.fdbAutoUpdateDao = inFdbAutoUpdateDao;
    }

    /**
     * setFdbAutoUpdateConverter.
     * @param fdbAutoUpdateConverter fdbAutoUpdateConverter property
     */
    public void setFdbAutoUpdateConverter(
            FdbAutoUpdateConverter fdbAutoUpdateConverter) {
        this.fdbAutoUpdateConverter = fdbAutoUpdateConverter;
    }

//    /**
//     * setSeqNumDomainCapability.
//     * @param seqNumDomainCapability seqNumDomainCapability property
//     */
//    public void setSeqNumDomainCapability(
//            SeqNumDomainCapability seqNumDomainCapability) {
//        this.seqNumDomainCapability = seqNumDomainCapability;
//    }

    @Override
    public void checkForActiveDependencies(FdbAutoUpdateVo managedItem, UserVo user) throws ValidationException {
        
    }

    @Override
    public Converter<FdbAutoUpdateVo, EplFdbAutoUpdateDo> getConverter() {
        return this.fdbAutoUpdateConverter;
    }

    @Override
    public DataAccessObject getDataAccessObject() {
        return this.fdbAutoUpdateDao;
    }
    
    @Override
    public boolean existsByUniquenessFields(FdbAutoUpdateVo managedItem) {
        return false;
    }

    @Override
    protected Criteria createUniquenessCriteria(FdbAutoUpdateVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplFdbAutoUpdateDo.EPL_ID, item.getId()));

        return criteria;

    }
}
