/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * all available product types
 */
public enum ProductType {

    /** MEDICATION */
    MEDICATION,

    /** SUPPLY */
    SUPPLY;

    /**
     * isMedication
     * 
     * @return boolean true if this product type is MEDICATION
     */
    public boolean isMedication() {
        return MEDICATION.equals(this);
    }

    /**
     * isSupply
     * 
     * @return boolean true if this product type is SUPPLY
     */
    public boolean isSupply() {
        return SUPPLY.equals(this);
    }
}
