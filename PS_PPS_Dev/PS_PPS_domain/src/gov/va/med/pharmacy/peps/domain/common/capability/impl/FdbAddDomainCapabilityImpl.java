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
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplFdbAddDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAddDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.FdbAddConverter;




/**
 * Perform CRUD operations on FDB Add
 */
public class FdbAddDomainCapabilityImpl extends 
    ManagedDataDomainCapabilityImpl<FdbAddVo, EplFdbAddDo> implements 
    FdbAddDomainCapability { 
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagedDataDomainCapabilityImpl.class);
    private EplFdbAddDao fdbAddDao;
    private FdbAddConverter fdbAddConverter;
    
    /**
     *  Default constructor
     */
    public FdbAddDomainCapabilityImpl() {
        super();
    }
    
    /**
     * delets all rows from the table > 90 days old for the EplFdbAddDo table.
     */
    public void deleteOver90() {

        Date today = new Date(); 
        Calendar cal = new GregorianCalendar(); 
        cal.setTime(today); 
        cal.add(Calendar.DAY_OF_MONTH, PPSConstants.IMINUS90); 
        Date today90 = cal.getTime(); 
        
        //Local format is used when in Derby, National Format when in Oracle for the EplFdbAddDo table.
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss", Locale.US);
        SimpleDateFormat nationalFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);

        StringBuffer query = new StringBuffer();
        query.append("DELETE FROM EplFdbAddDo where CREATED_DTM <= '");

        
        if (System.getProperty(PPSConstants.OS_NAME).equalsIgnoreCase("win")) {
            query.append(localFormat.format(today90) + "'");
        } else {
            query.append(nationalFormat.format(today90) + "'");
        }
        
        fdbAddDao.executeQuery(query.toString());
    }

    /**
     * retrieve.
     * @return a list of FDB add items
     */
    public List<FdbAddVo> retrieve() {
        List<EplFdbAddDo> eplDos = fdbAddDao.retrieve();
        List<FdbAddVo> fdbAddFiles = fdbAddConverter.convert(eplDos);
    
        return fdbAddFiles;
    }
    
    
    /**
     * Finds by Field name 
     *
     * @param pFieldName field name
     * @param pValue value
     * @return list of fdbAddVo
     */
    public List<FdbAddVo> findByFieldName(String pFieldName, String pValue) {
        List<EplFdbAddDo> eplDos =  fdbAddDao.retrieve(pFieldName, pValue);
        List<FdbAddVo> fdbAddFiles = fdbAddConverter.convert(eplDos);
        
        return fdbAddFiles;
    }
    
    /**
     * create.
     * @param fdbAddVo the FDB add vo
     * @param user required user
     * @return the newly created FDB
     */
    public FdbAddVo create(FdbAddVo fdbAddVo, UserVo user) {
        
        EplFdbAddDo eplFdbAddDo = fdbAddConverter.convert(fdbAddVo);
        EplFdbAddDo returnFdbAddDo = fdbAddDao.insert(eplFdbAddDo, user);
    
        return fdbAddConverter.convert(returnFdbAddDo);
    }
    
  
    /**
     * Updates the FDB file
     * 
     * @param fdbAddVo   migration file
     * @param user      provides user information
     * @return FdbAddVo FDB file returned
     */
    public FdbAddVo update(FdbAddVo fdbAddVo, UserVo user) {
        EplFdbAddDo eplFdbAddDo = fdbAddConverter.convert(fdbAddVo);
        fdbAddDao.update(eplFdbAddDo, user);
    
        return fdbAddVo;
    }
    
    /**
     * Deletes all of the FdbAddVo's.
     */
    public void deleteAll() {
        fdbAddDao.executeQuery("DELETE FROM EplFdbAddDo");
    }

    /**
     * getEplFdbAddDao.
     * @return the fdbAddDao
     */
    public EplFdbAddDao getEplFdbAddDao() {
        return fdbAddDao;
    }

    /**
     * setEplFdbAddDao.
     * @param fdbAddDaoIn the fdbAddDao to set
     */
    public void setEplFdbAddDao(EplFdbAddDao fdbAddDaoIn) {
        this.fdbAddDao = fdbAddDaoIn;
    }

    /**
     * getFdbAddConverter.
     * @return the fdbAddConverter
     */
    public FdbAddConverter getFdbAddConverter() {
        return fdbAddConverter;
    }

    /**
     * setFdbAddConverter.
     * @param fdbAddConverter the fdbAddConverter to set
     */
    public void setFdbAddConverter(FdbAddConverter fdbAddConverter) {
        this.fdbAddConverter = fdbAddConverter;
    }

    /**
     * Deletes FdbAddVos by NDC number
     *
     * @param pNdcNo NDC number
     */
    @Override
    public void deleteByNdcNumber(String pNdcNo) {
        
        LOG.debug("NDCNumbers are '" + pNdcNo + "'");
        String str = "";
        
        if (pNdcNo.length() == 13) {
            String pNdcNoNodashes = pNdcNo.replaceAll("-", "");
            String pNdcWithapos = "'" + pNdcNo + "'";
            String string1 = "'" + pNdcNo.substring(1, 5) + "-" 
                + pNdcNoNodashes.substring(5, 11) + "'"; //4-6  Missing first 0
            String string2 = "'" + pNdcNo.substring(0, 5) + "-" 
                + pNdcNoNodashes.substring(6, 11) + "'"; //5-05  missing first 0 of second segment
            String string3 = "'" + pNdcNo.substring(0, 5) + "-" 
                + pNdcNoNodashes.substring(5, 9) + pNdcNoNodashes.substring(10, 11) + "'";   //5-4-01
            String string4 = "'" + pNdcNo.substring(0, 5) + "-" 
                + pNdcNoNodashes.substring(5, 9) + pNdcNoNodashes.substring(9, 10) + "'";   //5-4-10
            
            str = "DELETE FROM  EplFdbAddDo where ndc IN (" + pNdcWithapos + "," + string1 + "," 
                + string2 + ", " + string3 + "," + string4 + ")";
            LOG.debug(str);
        } else {
            str = "DELETE FROM  EplFdbAddDo where ndc IN ('" + pNdcNo + "')";
        }
        
        LOG.debug(str);
        
        fdbAddDao.executeQuery(str);
        
    }

    @Override
    public void checkForActiveDependencies(FdbAddVo managedItem, UserVo user) throws ValidationException {
    }

    @Override
    public Converter<FdbAddVo, EplFdbAddDo> getConverter() {
        return null;
    }

    @Override
    public DataAccessObject getDataAccessObject() {
        return null;
    }

    @Override
    protected Criteria createUniquenessCriteria(FdbAddVo item) {
        return null;
    }
}
