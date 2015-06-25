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
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbUpdateDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbUpdateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbUpdateConverter;




/**
 * Perform CRUD operations on FDB Update
 */
public class FdbUpdateDomainCapabilityImpl extends 
    ManagedDataDomainCapabilityImpl<FdbUpdateVo, EplFdbUpdateDo> implements 
    FdbUpdateDomainCapability {  
    
    private EplFdbUpdateDao fdbUpdateDao;
    private FdbUpdateConverter fdbUpdateConverter;
    
    /**
     *  Default constructor
     */
    public FdbUpdateDomainCapabilityImpl() {
        super();
    }
    
    /**
     * deletes all rows from the table > 90 days old from the EplFdbUpdateDo table.
     */
    public void deleteOver90() {

        Date today = new Date(); 
        Calendar cal = new GregorianCalendar(); 
        cal.setTime(today); 
        cal.add(Calendar.DAY_OF_MONTH, PPSConstants.IMINUS90); 
        Date today90 = cal.getTime(); 
        
        //Local format is used when in Derby, National Format when in Oracle for EplFdbUpdateDo
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss", Locale.US);
        SimpleDateFormat nationalFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);

        StringBuffer query = new StringBuffer();
        query.append("DELETE FROM EplFdbUpdateDo where CREATED_DTM <= '");

        if (System.getProperty(PPSConstants.OS_NAME).equalsIgnoreCase("win")) {
            query.append(localFormat.format(today90) + "'");
        } else {
            query.append(nationalFormat.format(today90) + "'");
        }
        
        fdbUpdateDao.executeQuery(query.toString());
    }

    /**
     * retrieve
     * @return a list of FDB update items
     */
    public List<FdbUpdateVo> retrieve() {
        

        List<EplFdbUpdateDo> eplDos = fdbUpdateDao.retrieve();
        List<FdbUpdateVo> fdbUpdateFiles = fdbUpdateConverter.convert(eplDos);
    
        return fdbUpdateFiles;
    }
    
   
  
    /**
     * Update the given FdbUpdateVo.
     * 
     * @param fdbUpdateVo fdbUpdateVo
     * @param user performing the action
     * @return FdbUpdateVo upddated Vo
     */
    public FdbUpdateVo update(FdbUpdateVo fdbUpdateVo, UserVo user) {
        EplFdbUpdateDo eplFdbUpdateDo = fdbUpdateConverter.convert(fdbUpdateVo);
        fdbUpdateDao.update(eplFdbUpdateDo, user);
    
        return fdbUpdateVo;
    }
    
    /**
     * Deletes all of the FdbUpdateVo's.
     */
    public void deleteAll() {
        fdbUpdateDao.executeQuery("DELETE FROM EplFdbUpdateDo");
    }
    
    
    /**
     * deletes item by Id
     * @param id item id
     */
    @Override
    public void deleteItemById(String id) {
        fdbUpdateDao.executeQuery("DELETE FROM EplFdbUpdateDo where epl_id = " + id);
    }
    
    /**
     * set the Local console info
     * 
     * @param pFdbUpdateDao pFdbUpdateDao
     */
    public void setEplFdbUpdateDao(EplFdbUpdateDao pFdbUpdateDao) {
        this.fdbUpdateDao = pFdbUpdateDao;
    }
    
    /**
     * set the Local console info
     * 
     * @return fdbUpdateDao 
     */
    public EplFdbUpdateDao getEplFdbUpdateDao() {
        return fdbUpdateDao;
    }
    
    /**
     * set FdbUpdate Converter
     * @param fdbUpdateConverter fdbUpdateConverter property
     */
    public void setFdbUpdateConverter(FdbUpdateConverter fdbUpdateConverter) {
        this.fdbUpdateConverter = fdbUpdateConverter;
    }

  

    @Override
    public DataAccessObject getDataAccessObject() {

        return this.fdbUpdateDao;
    }

    @Override
    protected Criteria createUniquenessCriteria(FdbUpdateVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplFdbUpdateDo.EPL_ID, item.getId()));

        return criteria;

    }
    
    @Override
    public boolean existsByUniquenessFields(FdbUpdateVo managedItem) {
        return false;
    }

    @Override
    public void checkForActiveDependencies(FdbUpdateVo managedItem, UserVo user) throws ValidationException {
        
    }

    @Override
    public Converter<FdbUpdateVo, EplFdbUpdateDo> getConverter() {
        return this.fdbUpdateConverter;
    }

//    /**
//     * Set sequence number
//     * @param seqNumDomainCapability seqNumDomainCapability property
//     */
//    public void setSeqNumDomainCapability(SeqNumDomainCapability seqNumDomainCapability) {
//        this.seqNumDomainCapability = seqNumDomainCapability;
//    }

}
