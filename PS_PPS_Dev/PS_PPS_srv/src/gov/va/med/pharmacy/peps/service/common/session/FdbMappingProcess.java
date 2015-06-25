/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


/**
 * FdbMappingService
 *
 */
public interface FdbMappingProcess extends Runnable {

    /** 
     * run method for process
     */
    void run();

    /**
     * getFdbMappingService
     * @return FdbMappingService
     */
    FdbMappingService getFdbMappingService();

    /**
     * setFdbMappingService
     * @param fdbMappingService FdbMappingService
     */
    void setFdbMappingService(FdbMappingService fdbMappingService);

    /**
     * getDomainService
     * @return DomainService
     */
    DomainService getDomainService();

    /**
     * setDomainService
     * @param domainService DomainService
     */
    void setDomainService(DomainService domainService);

}
