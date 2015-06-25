/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * FdbNameType filter
 */
public enum FdbNameType {
    
    /**
     * BRAND
     */
    BRAND_ONLY, 
    
    /**
     * GENERIC
     */
    GENERIC_ONLY, 
    
    /**
     * BOTH
     */
    BOTH;
    
    /**
     * isBrand.
     * @return boolean true if this FdbNameType filter is BRAND
     */
    public boolean isBrandOnly() {
        return BRAND_ONLY.equals(this);
    }

    /**
     * isGeneric.
     * @return boolean true if this FdbNameType filter is GENERIC
     */
    public boolean isGenericOnly() {
        return GENERIC_ONLY.equals(this);
    }

    /**
     * isBoth.
     * @return boolean true if this FdbNameType filter is both BRAND and GENERIC
     */
    public boolean isBoth() {
        return BOTH.equals(this);
    }
}
