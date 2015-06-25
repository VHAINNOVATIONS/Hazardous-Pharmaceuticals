/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;
import gov.va.med.pharmacy.peps.external.common.callback.ManufacturerDomainCapabilityCallback;


/**
 * Perform CRUD operations on manufacturers
 */
public interface ManufacturerDomainCapability extends 
                  ManagedDataDomainCapability<ManufacturerVo, EplManufacturerDo>, ManufacturerDomainCapabilityCallback {

    /**
     * retrieveByName
     * @param name name
     * @return  Collection<ManufacturerVo>
     */
    Collection<ManufacturerVo> retrieveByName(String name);
}
