/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types available to an Item
 */
public enum ItemType {

    /** VA_SPECIFIC */
    VA_SPECIFIC,

    /** COTS */
    COTS;

    /**
     * isVaSpecific
     * 
     * @return boolean true if this ItemType is VA_SPECIFIC
     */
    public boolean isVaSpecific() {
        return VA_SPECIFIC.equals(this);
    }

    /**
     * isCots
     * 
     * @return boolean true if this ItemType is COTS
     */
    public boolean isCots() {
        return COTS.equals(this);
    }
}
