/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.vo.DrugDataVendorVersionVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.VersionCapability;

import firstdatabank.database.FDBException;
import firstdatabank.dif.FWStatus;
import firstdatabank.dif.Navigation;


/**
 * Retrieve drug data vendor version information
 */
public class VersionCapabilityImpl implements VersionCapability {
    private Navigation navigation;

    /**
     * Retrieve drug data vendor version information.
     * 
     * @return DrugDataVendorVersionVo with version information
     */
    public DrugDataVendorVersionVo retrieveDrugDataVendorVersion() {
        DrugDataVendorVersionVo version = new DrugDataVendorVersionVo();
        FWStatus status = null;

        try {
            status = navigation.getStatus();
        } catch (FDBException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        }

        version.setBuildVersion(status.getDatabaseBuildVersion());
        version.setDataVersion(status.getDatabaseDataVersion());
        version.setIssueDate(status.getDatabaseIssueDate());

        return version;
    }

    /**
     * setNavigation.
     * @param navigation property
     */
    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }
}
