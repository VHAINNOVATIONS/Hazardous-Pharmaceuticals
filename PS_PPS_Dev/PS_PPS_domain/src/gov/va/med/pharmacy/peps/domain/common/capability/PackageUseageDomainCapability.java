/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;


/**
 * Perform CRUD operations on package Usages
 */
public interface PackageUseageDomainCapability {

    /**
     * Retrieve a List of possible values to select from
     * 
     * @return List of possible values
     */
    List<PackageUsageVo> retrieveDomain();
}
