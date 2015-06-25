/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.service.common.session.DomainService;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingProcess;
import gov.va.med.pharmacy.peps.service.common.session.FdbMappingService;


/**
 * Migration CSV Service Bean for FdbMappingProcessBean
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject"
 *                local-extends="javax.ejb.EJBLocalObject"
 */
public class FdbMappingProcessBean extends
        AbstractPepsStatelessSessionBean<FdbMappingProcess> implements
        FdbMappingProcess {

    private static final long serialVersionUID = 1L;

    /**
     * run
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void run() {
        this.getService().run();

    }

    /**
     * getFdbMappingService
     * 
     * @return FdbMappingService
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public FdbMappingService getFdbMappingService() {
        return this.getService().getFdbMappingService();
    }

    /**
     * setFdbMappingService
     * 
     * @param fdbMappingService FdbMappingService
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setFdbMappingService(FdbMappingService fdbMappingService) {
        this.getService().setFdbMappingService(fdbMappingService);

    }

    /**
     * getDomainService
     * 
     * @return DomainService
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public DomainService getDomainService() {
        return this.getService().getDomainService();
    }

    /**
     * setDomainService
     * 
     * @param domainService DomainService
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    @Override
    public void setDomainService(DomainService domainService) {
        this.getService().setDomainService(domainService);

    }

}
