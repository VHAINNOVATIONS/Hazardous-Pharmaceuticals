/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.callback;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;


/**
 * Exposes selected order unit domain callback methods to the interface project.
 */
public interface OrderUnitDomainCapabilityCallback {

    /**
     * Retrieve all possible OrderUnit
     * 
     * @return List of Active OrderUnits
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability#retrieveDomain()
     */
    List<OrderUnitVo> retrieve();
}
