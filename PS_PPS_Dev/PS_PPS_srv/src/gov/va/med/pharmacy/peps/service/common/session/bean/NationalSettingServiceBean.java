/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.List;

import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.NationalSettingService;


/**
 * Perform settings updates
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject"
 *                local-extends="javax.ejb.EJBLocalObject"
 */
public class NationalSettingServiceBean extends
    AbstractPepsStatelessSessionBean<NationalSettingService> implements NationalSettingService {

    /** serialVersionUID */
    private static final long serialVersionUID = 3064294286517114842L;

    /**
     * create
     * 
     * @param nationalSettingVo NationalSettingVo
     * @param user UserVo
     * @return NationalSettingVo
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public NationalSettingVo create(NationalSettingVo nationalSettingVo, UserVo user) {

        return getService().create(nationalSettingVo, user);
    }

    /**
     * retrieve
     * 
     * @param id Long
     * @return NationalSettingVo
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public NationalSettingVo retrieve(Long id) {
        return getService().retrieve(id);
    }

    /**
     * retrieve
     * 
     * @param keyName String
     * @return NationalSettingVo
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public NationalSettingVo retrieve(String keyName) {
        return getService().retrieve(keyName);
    }

    /**
     * retrieveAll
     * 
     * @return NationalSettingVo
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public List<NationalSettingVo> retrieveAll() {
        return getService().retrieveAll();
    }

    /**
     * update
     * 
     * @param nationalSettingVo NationalSettingVo
     * @param user UserVo
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public void update(NationalSettingVo nationalSettingVo, UserVo user) {
        getService().update(nationalSettingVo, user);
    }

    /**
     * delete
     * 
     * @param nationalSettingVo NationalSettingVo
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public void delete(NationalSettingVo nationalSettingVo) {
        getService().delete(nationalSettingVo);
    }

    /**
     * delete
     * 
     * @param id Long
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public void delete(Long id) {
        getService().delete(id);
    }

    /**
     * delete
     * 
     * @param keyName String
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public void delete(String keyName) {
        getService().delete(keyName);
    }
}
