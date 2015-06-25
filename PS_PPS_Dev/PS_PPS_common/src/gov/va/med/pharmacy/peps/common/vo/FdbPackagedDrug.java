/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * FdbPackagedDrug filter
 */
public enum FdbPackagedDrug {
    
    /**
     * PACKAGE DRUG ONLY
     */
    PACKAGE_DRUG_ONLY, 
    
    /**
     * EQUIVALENT PACKAGE DRUG ONLY
     */
    EQUIVALENT_PACKAGE_DRUG_ONLY, 
    
    /**
     * BOTH
     */
    BOTH;
    
    /**
     * isPackageDrugOnly.
     * @return boolean true if this FdbPackagedDrug filter is PACKAGE_DRUG_ONLY
     */
    public boolean isPackageDrugOnly() {
        return PACKAGE_DRUG_ONLY.equals(this);
    }

    /**
     * isGeneric.
     * @return boolean true if this FdbPackagedDrug filter is EQUIVALENT_PACKAGE_DRUG_ONLY
     */
    public boolean isEquivalentPackageDrugOnly() {
        return EQUIVALENT_PACKAGE_DRUG_ONLY.equals(this);
    }

    /**
     * isBoth.
     * @return boolean true if this FdbPackagedDrug filter is both
     */
    public boolean isBoth() {
        return BOTH.equals(this);
    }
}
