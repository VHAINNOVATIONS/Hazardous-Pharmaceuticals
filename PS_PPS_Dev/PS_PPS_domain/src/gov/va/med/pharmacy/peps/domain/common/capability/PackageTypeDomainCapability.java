/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageTypeDo;


/**
 * Perform CRUD operations on package types
 */
public interface PackageTypeDomainCapability extends ManagedDataDomainCapability<PackageTypeVo, EplPackageTypeDo> {

    /**
     * retrieveByName
     * @param packageDescription packageDescription
     * @return Collection<PackageTypeVo>
     */
    Collection<PackageTypeVo> retrieveByName(String packageDescription);

}
