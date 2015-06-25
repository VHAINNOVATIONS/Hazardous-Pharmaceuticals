/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ShapeVo;


/**
 * Perform CRUD operations on shape
 */
public interface ShapeDomainCapability {

    /**
     * Retrieve a List of possible values to select from
     * 
     * @return List of possible values
     */
    List<ShapeVo> retrieveDomain();
}
