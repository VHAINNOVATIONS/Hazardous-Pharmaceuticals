/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.stub;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.external.common.callback.IntendedUseDomainCapabilityCallback;


/**
 * Exposes selected product domain callback methods to the interface project.
 */
public class IntendedUseDomainCapabilityStub implements IntendedUseDomainCapabilityCallback {

    /**
     * description
     * 
     * @return null
     * 
     * @see gov.va.med.pharmacy.peps.external.common.callback.IntendedUseDomainCapabilityCallback#retrieveDomain()
     */
    public List<IntendedUseVo> retrieveDomain() {

        return null;
    }

}
