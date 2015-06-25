/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNationalSettingDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplNationalSettingDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NationalSettingConverter;


/**
 * Perform CRUD operations on National Settings
 */
public class NationalSettingDomainCapabilityImpl implements NationalSettingDomainCapability {

    private static final Logger LOG = Logger.getLogger(NationalSettingDomainCapabilityImpl.class);
    
    private EplNationalSettingDao nationalSettingDao;
    private NationalSettingConverter nationalSettingConverter;

    /**
     *  Default constructor
     */
    public NationalSettingDomainCapabilityImpl() {
        super();
    }

    /**
     * retrieve nationalSettingFiles
     * 
     * @return a list of National Settings records.
     */
    public List<NationalSettingVo> retrieve() {
        List<EplNationalSettingDo> eplDos = nationalSettingDao.retrieve();
        List<NationalSettingVo> nationalSettingFiles = nationalSettingConverter.convert(eplDos);

        return nationalSettingFiles;
    }

    /**
     * retrieve
     *
     * @param id Long
     * @return NationalSettingVo
     */
    @Override
    public NationalSettingVo retrieve(Long id) {

        if (id == null) {
            return null;
        }
        
        EplNationalSettingDo eplDo = nationalSettingDao.retrieve(id);

        if (eplDo == null) {
            return null;
        }

        return nationalSettingConverter.convert(eplDo);
    }

    /**
     * retrieve
     *
     * @param keyName String - setting key name used to retrieve the whole setting object
     * @return NationalSettingVo
     */
    @Override
    public NationalSettingVo retrieve(String keyName) {
        
        if (keyName == null || keyName.isEmpty()) {
            return null;
        }
        
        List<Criterion> critList = new ArrayList<Criterion>();
        Criterion crit = null;
        crit = Restrictions.eq(EplNationalSettingDo.KEY_NAME, keyName);
        critList.add(crit);
        
        List<EplNationalSettingDo> list = nationalSettingDao.retrieve(critList);
        
        if (list.isEmpty()) {
            return null;
        }

        return nationalSettingConverter.convert(list.get(0));
    }

    /**
     * create nationalSettings in the database
     * 
     * @param nationalSettingVo Vo
     * @param user user
     * @return the newly created Vo
     */
    public NationalSettingVo create(NationalSettingVo nationalSettingVo, UserVo user) {

        EplNationalSettingDo eplNationalSettingDo = nationalSettingConverter.convert(nationalSettingVo);
        EplNationalSettingDo returnNationalSettingDo = nationalSettingDao.insert(eplNationalSettingDo, user);

        return nationalSettingConverter.convert(returnNationalSettingDo);
    }

    /**
     * Update the given NationalSettingVo.
     * 
     * @param nationalSettingVo autoAddVo
     * @param user {@link UserVo} performing the action
     * @return NationalSettingVo auto add Vo
     */
    public NationalSettingVo update(NationalSettingVo nationalSettingVo, UserVo user) {
        EplNationalSettingDo eplNationalSettingDo = nationalSettingConverter.convert(nationalSettingVo);
        nationalSettingDao.update(eplNationalSettingDo, user);

        return nationalSettingVo;
    }

    /**
     * Deletes all of the NationalSettingVo's.
     */
    public void deleteAll() {
        nationalSettingDao.executeQuery("DELETE FROM EplNationalSettingDo");
    }

    /**
     * delete a specific setting
     *
     * @param id Long
     */
    @Override
    public void delete(Long id) {       
        if (id != null) {
            nationalSettingDao.executeQuery("DELETE FROM EplNationalSettingDo WHERE " + EplNationalSettingDo.ID + " = " + id);
        }
    }

    /**
     * delete a specific setting
     *
     * @param keyName String
     */
    @Override
    public void delete(String keyName) {
        
        if (keyName == null || keyName.trim().isEmpty()) {
            return;
        }
        
        nationalSettingDao.executeQuery("DELETE FROM EplNationalSettingDo WHERE " + EplNationalSettingDo.KEY_NAME + " = " 
            + keyName);
    }

    /**
     * set the Local console info
     * 
     * @param nationalSettingDaoIn NationalSettingDao
     */
    public void setEplNationalSettingDao(EplNationalSettingDao nationalSettingDaoIn) {
        this.nationalSettingDao = nationalSettingDaoIn;
    }

    /**
     * setNationalSettingConverter set NationalSetting Converter
     * 
     * @param nationalSettingConverter NationalSettingConverter property
     */
    public void setNationalSettingConverter(NationalSettingConverter nationalSettingConverter) {
        this.nationalSettingConverter = nationalSettingConverter;
    }
}
