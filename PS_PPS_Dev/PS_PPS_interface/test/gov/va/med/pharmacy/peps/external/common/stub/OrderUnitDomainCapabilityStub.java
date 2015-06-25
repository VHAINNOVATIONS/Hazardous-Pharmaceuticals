/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.stub;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.external.common.callback.OrderUnitDomainCapabilityCallback;


/**
 * OrderUnit domain callback stub.
 */
public class OrderUnitDomainCapabilityStub implements OrderUnitDomainCapabilityCallback {

    /** FOUND */
    public static boolean FOUND = true;

    /**
     * Retrieve the OrderUnitVO with the given order unit.
     * 
     * @param  orderUnit  This is the text of the order unit
     * @return OrderUnitVo
     * @throws ItemNotFoundException exception
     */
    public OrderUnitVo retrieve(String orderUnit) throws ItemNotFoundException {
      
        return null;
    }

   
    /**
     * Retrieve all possible OrderUnit
     * 
     * @return List of Active OrderUnits
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.OrderUnitDomainCapability#retrieveDomain()
     */
    public List<OrderUnitVo> retrieve() {
        return null;
    }

}
