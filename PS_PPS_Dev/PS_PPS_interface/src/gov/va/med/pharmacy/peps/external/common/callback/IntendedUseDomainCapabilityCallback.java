/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.callback;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;


/**
 * Exposes selected intended use domain callback methods to the interface project.
 */
public interface IntendedUseDomainCapabilityCallback {

    /**
     * Retrieve all the intended use VOs
     * 
     * @return IntendedUse VOs
     */
    List<IntendedUseVo> retrieveDomain();
}
