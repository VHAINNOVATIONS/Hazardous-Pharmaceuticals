/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.callback;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;


/**
 * Exposes selected product domain callback methods to the interface project.
 */
public interface ManufacturerDomainCapabilityCallback {

    /**
     * Retrieve all the manufacturer VOs
     * 
     * @return Manufacturer VOs
     */
    List<ManufacturerVo> retrieve();
}
