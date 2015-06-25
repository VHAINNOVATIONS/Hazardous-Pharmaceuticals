/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplStandardMedRouteDo;


/**
 * Perform CRUD operations on standard medication route
 */
public interface StandardMedRouteDomainCapability extends
    ManagedDataDomainCapability<StandardMedRouteVo, EplStandardMedRouteDo> {

}
