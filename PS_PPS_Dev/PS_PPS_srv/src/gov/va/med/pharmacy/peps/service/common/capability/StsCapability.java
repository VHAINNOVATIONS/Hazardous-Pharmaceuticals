/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import gov.va.med.pharmacy.peps.domain.common.capability.ItemAuditHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.StandardMedRouteDomainCapability;


/**
 * StsCapability is used to manage the STS updates
 *
 */
public interface StsCapability {

    /**
     * synchronizedFDBUpdate.
     */
    void synchronizedFDBUpdate();

    /**
     * setItemAuditHistoryDomainCapability.
     * @param itemAuditHistoryDomainCapability the itemAuditHistoryDomainCapability to set
     */
    void setItemAuditHistoryDomainCapability(ItemAuditHistoryDomainCapability itemAuditHistoryDomainCapability);

    /**
     * setStandardMedRouteDomainCapability.
     * @param standardMedRouteDomainCapability the standardMedRouteDomainCapability to set
     */
    void setStandardMedRouteDomainCapability(StandardMedRouteDomainCapability
        standardMedRouteDomainCapability);

}
