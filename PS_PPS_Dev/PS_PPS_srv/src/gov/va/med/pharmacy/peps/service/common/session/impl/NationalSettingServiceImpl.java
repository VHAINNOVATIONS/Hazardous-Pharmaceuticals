/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.service.common.session.NationalSettingService;


/**
 * NationalSettingServiceImpl's brief summary
 * 
 * Details of NationalSettingServiceImpl's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class NationalSettingServiceImpl implements NationalSettingService {
    
    @Autowired
    private NationalSettingDomainCapability nationalSettingDomainCapability;

    @Override
    public NationalSettingVo create(NationalSettingVo nationalSettingVo, UserVo user) {
        NationalSettingVo rv = nationalSettingDomainCapability.create(nationalSettingVo, user);
        
        return rv;
    }

    @Override
    public NationalSettingVo retrieve(Long id) {
        return nationalSettingDomainCapability.retrieve(id);
    }

    @Override
    public NationalSettingVo retrieve(String keyName) {
        return nationalSettingDomainCapability.retrieve(keyName);
    }

    @Override
    public List<NationalSettingVo> retrieveAll() {
        return nationalSettingDomainCapability.retrieve();
    }

    @Override
    public void update(NationalSettingVo nationalSettingVo, UserVo user) {
        nationalSettingDomainCapability.update(nationalSettingVo, user);
    }

    @Override
    public void delete(NationalSettingVo nationalSettingVo) {
        Long id = nationalSettingVo.getId();
        delete(id);
    }

    @Override
    public void delete(Long id) {
        nationalSettingDomainCapability.delete(id);
    }

    @Override
    public void delete(String keyName) {
        nationalSettingDomainCapability.delete(keyName);        
    }

    /**
     * getNationalSettingDomainCapability
     *
     * @return NationalSettingDomainCapability
     */
    public NationalSettingDomainCapability getNationalSettingDomainCapability() {
        
        return nationalSettingDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability
     *
     * @param nationalSettingDomainCapability NationalSettingDomainCapability
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;        
    }

}
