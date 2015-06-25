/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageUseageDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPackageUsageDao;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PackageUsageConverter;


/**
 * Perform CRUD operations on package Usages
 */
public class PackageUseageDomainCapabilityImpl implements PackageUseageDomainCapability {
    private EplPackageUsageDao eplPackageUsageDao;
    private PackageUsageConverter packageUsageConverter;

    /**
     * Retrieve a List of possible values to select from
     * 
     * @return List of possible values
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.PackageUseageDomainCapability#retrieveDomain()
     */
    public List<PackageUsageVo> retrieveDomain() {
        return packageUsageConverter.convert(eplPackageUsageDao.retrieve());
    }

    /**
     * set the Package Usage DAO
     * 
     * @param eplPackageUsageDao EplPackageUseageDao property
     */
    public void setEplPackageUsageDao(EplPackageUsageDao eplPackageUsageDao) {
        this.eplPackageUsageDao = eplPackageUsageDao;
    }

    /**
     * setPackageUsageConverter
     * @param packageUsageConverter packageUsageConverter property
     */
    public void setPackageUsageConverter(PackageUsageConverter packageUsageConverter) {
        this.packageUsageConverter = packageUsageConverter;
    }
}
