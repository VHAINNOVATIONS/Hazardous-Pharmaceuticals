/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * NationalSettingService's brief summary
 * 
 * Details of NationalSettingService's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public interface NationalSettingService {

    /**
     * create
     *
     * @param nationalSettingVo NationalSettingVo
     * @param user UserVo
     * @return NationalSettingVo
     */
    NationalSettingVo create(NationalSettingVo nationalSettingVo, UserVo user);

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
     * retrieveAll
     *
     * @return List<NationalSettingVo>
     */
    List<NationalSettingVo> retrieveAll();

    /**
     * update
     *
     * @param nationalSettingVo NationalSettingVo
     * @param user UserVo
     */
    void update(NationalSettingVo nationalSettingVo, UserVo user);

    /**
     * delete
     *
     * @param nationalSettingVo NationalSettingVo
     */
    void delete(NationalSettingVo nationalSettingVo);

    /**
     * delete
     *
     * @param id Long
     */
    void delete(Long id);

    /**
     * delete
     *
     * @param keyName String
     */
    void delete(String keyName);
    
}
