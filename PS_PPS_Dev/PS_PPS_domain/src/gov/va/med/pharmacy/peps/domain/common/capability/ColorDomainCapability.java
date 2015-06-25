/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ColorVo;


/**
 * Perform CRUD operations on colors
 */
public interface ColorDomainCapability {

    /**
     * Retrieve a List of possible colors to select from
     * 
     * @return List of possible values
     */
    List<ColorVo> retrieveDomain();
}
