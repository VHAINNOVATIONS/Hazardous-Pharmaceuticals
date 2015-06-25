/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability;


import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.VistaDomainName;


/**
 * Retrieves authoritative domain information from VistA.
 */
public interface VistaAuthoritativeDomainCapability {

    /**
     * Retrieve one or more domain value(s) from VistA.
     * 
     * @param domains list of domains to retrieve
     * @param user User making this call (null authenticates using a DUZ)
     * @return map of domain values
     */
    Map<VistaDomainName, List<?>> retrieveVistADomains(VistaDomainName[] domains, UserVo user);

}
