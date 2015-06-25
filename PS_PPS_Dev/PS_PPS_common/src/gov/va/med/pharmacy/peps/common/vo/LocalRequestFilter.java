/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * LocalRequestFilter
 */
public enum LocalRequestFilter {

    /** ALL_ITEMS */
    ALL_ITEMS,

    /** ONLY_LOCAL */
    ONLY_LOCAL;

    /**
     * isOnlyLocal
     * 
     * @return boolean true if Filter is Only Local
     */
    public boolean isOnlyLocal() {
        return ONLY_LOCAL.equals(this);
    }

    /**
     * isAllItems
     * 
     * @return boolean true if this Filter is All Items
     */
    public boolean isAllItems() {
        return ALL_ITEMS.equals(this);
    }
}
