/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderUnitDo;
import gov.va.med.pharmacy.peps.external.common.callback.OrderUnitDomainCapabilityCallback;


/**
 * Perform CRUD operations on Order Units
 */
public interface OrderUnitDomainCapability extends ManagedDataDomainCapability<OrderUnitVo, EplOrderUnitDo>,
    OrderUnitDomainCapabilityCallback {

}
