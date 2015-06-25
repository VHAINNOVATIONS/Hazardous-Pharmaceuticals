/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.stub;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.external.common.callback.ManufacturerDomainCapabilityCallback;


/**
 * Manufacturer callback stub.
 */
public class ManufacturerDomainCapabilityStub implements ManufacturerDomainCapabilityCallback {

    /**
     * retrieve
     * 
     * @return null
     * 
     * @see gov.va.med.pharmacy.peps.external.common.callback.ManufacturerDomainCapabilityCallback#retrieve()
     */
    public List<ManufacturerVo> retrieve() {

        return null;
    }

}
