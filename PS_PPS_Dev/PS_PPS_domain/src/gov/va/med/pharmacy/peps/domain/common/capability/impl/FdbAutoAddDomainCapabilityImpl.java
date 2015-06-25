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


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbAutoAddDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAutoAddDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbAutoAddConverter;





/**
 * Perform CRUD operations on FDB AutoAdd
 */
public class FdbAutoAddDomainCapabilityImpl extends
    ManagedDataDomainCapabilityImpl<FdbAutoAddVo, EplFdbAutoAddDo> implements
    FdbAutoAddDomainCapability {
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(FdbAutoAddDomainCapabilityImpl.class);
    
    private EplFdbAutoAddDao fdbAutoAddDao;
    private FdbAutoAddConverter fdbAutoAddConverter;
    
    
    /**
     *  Default constructor
     */
    public FdbAutoAddDomainCapabilityImpl() {
        super();
    }

    /**
     * delets all rows from the table > 90 days old for EplFdbAutoAddDo.
     */
    public void deleteOver90() {

        Date today = new Date(); 
        Calendar cal = new GregorianCalendar(); 
        cal.setTime(today); 
        cal.add(Calendar.DAY_OF_MONTH, PPSConstants.IMINUS90); 
        Date today90 = cal.getTime(); 
        
        //Local format is used when in Derby, National Format when in Oracle. used on EplFdbAutoAddDo.
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss", Locale.US);
        SimpleDateFormat nationalFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);

        StringBuffer query = new StringBuffer();
        query.append("DELETE FROM EplFdbAutoAddDo where CREATED_DTM <= '");

        
        if (System.getProperty(PPSConstants.OS_NAME).equalsIgnoreCase("win")) {
            query.append(localFormat.format(today90) + "'");
        } else {
            query.append(nationalFormat.format(today90) + "'");
        }
        
        fdbAutoAddDao.executeQuery(query.toString());
    }
    
    /**
     * retrieve
     * @return a list of FDB auto add items
     */
    public List<FdbAutoAddVo> retrieve() {
        List<EplFdbAutoAddDo> eplDos = fdbAutoAddDao.retrieve();
        List<FdbAutoAddVo> fdbAutoAddFiles = fdbAutoAddConverter.convert(eplDos);
    
        return fdbAutoAddFiles;
    }
    
    /**
     * create
     * @param fdbAutoAddVo the FDB add vo
     * @param user user
     * @return the newly created FDB
     */
    public FdbAutoAddVo create(FdbAutoAddVo fdbAutoAddVo, UserVo user) {
       
    
        EplFdbAutoAddDo eplFdbAutoAddDo = fdbAutoAddConverter.convert(fdbAutoAddVo);
        EplFdbAutoAddDo returnFdbAutoAddDo = fdbAutoAddDao.insert(eplFdbAutoAddDo, user);
    
        return fdbAutoAddConverter.convert(returnFdbAutoAddDo);
    }
    
  
    /**
     * Update the given FdbAutoAddVo.
     * 
     * @param fdbAutoAddVo autoAddVo
     * @param user {@link UserVo} performing the action
     * @return FdbAutoAddVo auto add Vo
     */
    public FdbAutoAddVo update(FdbAutoAddVo fdbAutoAddVo, UserVo user) {
        EplFdbAutoAddDo eplFdbAutoAddDo = fdbAutoAddConverter.convert(fdbAutoAddVo);
        fdbAutoAddDao.update(eplFdbAutoAddDo, user);
    
        return fdbAutoAddVo;
    }
    
    /**
     * Deletes all of the FdbAutoAddVo's.
     */
    public void deleteAll() {
        fdbAutoAddDao.executeQuery("DELETE FROM EplFdbAutoAddDo");
    }
    
    
    /**
     * Deletes FdbAutoAddVo's by NDC number
     *
     * @param pNdcNo NDC number
     */
    @Override
    public void deleteByNdcNumber(String pNdcNo) {
        fdbAutoAddDao.executeQuery("DELETE FROM EplFdbAutoAddDo where ndc = '" + pNdcNo + "'");
        
    }
    
    /**
     * set the setEplFdbAutoAddDao
     * 
     * @param fdbAutoAddDaoIn fdbAutoAddDaoIn
     */
    public void setEplFdbAutoAddDao(EplFdbAutoAddDao fdbAutoAddDaoIn) {
        this.fdbAutoAddDao = fdbAutoAddDaoIn;
    }
    
    /**
     * setFdbAutoAddConverter
     * @param fdbAutoAddConverter fdbAutoAddConverter property
     */
    public void setFdbAutoAddConverter(FdbAutoAddConverter fdbAutoAddConverter) {
        this.fdbAutoAddConverter = fdbAutoAddConverter;
    }

    @Override
    public void checkForActiveDependencies(FdbAutoAddVo managedItem, UserVo user) throws ValidationException {
        
    }

    @Override
    public Converter<FdbAutoAddVo, EplFdbAutoAddDo> getConverter() {
        return null;
    }

    @Override
    public DataAccessObject getDataAccessObject() {
        return null;
    }

    @Override
    protected Criteria createUniquenessCriteria(FdbAutoAddVo item) {
        return null;
    }
    

}
