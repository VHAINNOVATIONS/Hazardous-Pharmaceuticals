/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.Date;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingService;


/**
 * Performs Mapping service functions
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 * 
 */
public class FdbMappingServiceBean extends AbstractPepsStatelessSessionBean<FdbMappingService> implements FdbMappingService {

    private static final long serialVersionUID = 1L;

    /**
     * getFdbDosageForms
     * 
     * @param domainType domainType
     * @param filterDate filterDate
     * @return List<FdbVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public List<FdbDomainVo> getFdbDomains(FdbDomainType domainType, Date filterDate) {
        return this.getService().getFdbDomains(domainType, filterDate);
    }

    /**
     * assignFdbDomainAssociation
     * @param domainType domainType
     * @param domains domains
     * @param user user
     * @throws ValidationException 
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     *  
     */
    @Override
    public void assignFdbDomainAssociation(FdbDomainType domainType, List<FdbDomainVo> domains, UserVo user)
        throws ValidationException {
        this.getService().assignFdbDomainAssociation(domainType, domains, user);
    }

    /**
     * importDomainMapping
     * 
     * @param fileName file name
     * @param user user
     * @throws Exception Exception
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     *  
     */
    @Override
    public void importDomainMapping(String fileName, UserVo user) throws Exception {
        this.getService().importDomainMapping(fileName, user);

    }

    /**
     *  getUser
     *  @return UserVo uservo
     *  
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public UserVo getUser() {
        return this.getService().getUser();
    }

    /**
     * getFileName
     * 
     * @return String
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public String getFileName() {
        return this.getService().getFileName();
    }

    /**
     * getEplTerms
     * 
     * @param domain FdbDomainType
     * 
     * @return Map
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public Map<Long, String> getEplTerms(FdbDomainType domain) {
        return this.getService().getEplTerms(domain);
    }

    /**
     * getEplIds
     * 
     * @param domain FdbDomainType
     * 
     * @return Map
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public Map<String, Long> getEplIds(FdbDomainType domain) {
        return this.getService().getEplIds(domain);
    }

    /**
     * setProcessRunning
     * 
     * @param processRunning Boolean
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setProcessRunning(Boolean processRunning) {
        this.getService().setProcessRunning(processRunning);

    }

    /**
     * getProcessRunning
     * 
     * @return Boolean
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    @Override
    public Boolean getProcessRunning() {
        return this.getService().getProcessRunning();
    }

//    /**
//     * getEplDomainService
//     * @return DomainService
//     * @ejb.interface-method
//     * 
//     * @ejb.transaction type = "Required"
//     */
//    @Override
//    public DomainService getEplDomainService() {
//        return this.getService().getEplDomainService();
//    }
//
//    /**
//     * setEplDomainService
//     * @param eplDomainService DomainService
//     * 
//     * @ejb.interface-method
//     * 
//     * @ejb.transaction type = "Required"
//     */
//    @Override
//    public void setEplDomainService(DomainService eplDomainService) {
//        this.getService().setEplDomainService(eplDomainService);
//
//    }
}
