/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;

import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.FssInterfaceCapability;




/**
 * StsCapability is used to manage the STS updates
 *
 */
public interface FssCapability {

    /**
     * synchronizedFSSUpdate.
     */
    void synchronizedFSSUpdate();
    
    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability the itemAuditHistoryDomainCapability to set
     */
    void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability);

    /**
     * setFssInterfaceCapability.
     * @param fssInterfaceCapability the FssInterfaceCapability to set
     */
    void setFssInterfaceCapability(FssInterfaceCapability
        fssInterfaceCapability);
    
    /**
     * setNdcDomainCapability.
     * @param ndcDomainCapability the ndcDomainCapability to set
     */
    void setNdcDomainCapability(NdcDomainCapability  ndcDomainCapability); 
   
    /**
     * setManagedItemCapability.
     * @param managedItemCapability the managedItemCapability to set
     */
    void setManagedItemCapability(ManagedItemCapability  managedItemCapability);
    
    /**
     * setNationalSettingDomainCapability.
     * @param nationalSettingDomainCapability the nationalSettingDomainCapability to set
     */
    void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability);
    
}
