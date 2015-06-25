/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNationalSettingDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.NationalSettingConverter;


/**
 * Perform CRUD operations on NationalSetting
 */
public interface NationalSettingDomainCapability {

    /**
     * Creates the FDB add file
     * 
     * @param nationalSettingVo migration file
     * @param user provides user information
     * @return NationalSettingVo FDB file returned
     */
    NationalSettingVo create(NationalSettingVo nationalSettingVo, UserVo user);

    /**
     * Retrieves a list of National Setting files
     * 
     * @return List<NationalSettingVo> list of National Setting files returned
     */
    List<NationalSettingVo> retrieve();
    
    /**
     * retrieve
     *
     * @param id Long
     * @return NationalSettingVo
     */
    NationalSettingVo retrieve(Long id);
    
    /**
     * retrieve
     *
     * @param keyName String
     * @return NationalSettingVo
     */
    NationalSettingVo retrieve(String keyName);

    /**
     * Updates the FDB file
     * 
     * @param nationalSettingVo
     *            migration file
     * @param user
     *            provides user information
     * @return nationalSettingVo FDB file returned
     */
    NationalSettingVo update(NationalSettingVo nationalSettingVo, UserVo user);

    /**
     * Deletes all of the NationalSettingVo's.
     */
    void deleteAll();
    
    /**
     * delete a specific setting
     *
     * @param id Long
     */
    void delete(Long id);
    
    /**
     * delete a specific setting
     *
     * @param keyName String
     */
    void delete(String keyName);

    /**
     * set the Local console info
     * 
     * @param nationalSettingDao EplNationalSettingDao
     */
    void setEplNationalSettingDao(EplNationalSettingDao nationalSettingDao);

    /**
     * setNationalSettingConverter
     * 
     * @param nationalSettingConverter
     *            NationalSettingConverter property
     */
    void setNationalSettingConverter(NationalSettingConverter nationalSettingConverter);
}
