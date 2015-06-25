/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import gov.va.med.pharmacy.peps.common.vo.DrugDataVendorVersionVo;


/**
 * Retrieve drug data vendor version information
 */
public interface VersionCapability {

    /**
     * Retrieve drug data vendor version information.
     * 
     * @return DrugDataVendorVersionVo with version information
     */
    DrugDataVendorVersionVo retrieveDrugDataVendorVersion();
}
