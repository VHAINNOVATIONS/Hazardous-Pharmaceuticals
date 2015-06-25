/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Options for local use on a search template
 */
public enum LocalUseSearchType {

    /** ALL_ITEMS */
        ALL_ITEMS,

    /** LOCAL_USE */
        LOCAL_USE,

    /** NOT_LOCAL_USE */
        NOT_LOCAL_USE;

    /**
     * isLocalUse
     * 
     * @return boolean true if this ItemStatus is ACTIVE
     */
    public boolean isLocalUse() {
        return LOCAL_USE.equals(this);
    }

    /**
     * isNotLocalUse
     * 
     * @return boolean true if this ItemStatus is INVACTIVE
     */
    public boolean isNotLocalUse() {
        return NOT_LOCAL_USE.equals(this);
    }

    /**
     * isAllItems
     * 
     * @return boolean true if this ItemStatus is ACTIVE
     */
    public boolean isAllItems() {
        return ALL_ITEMS.equals(this);
    }
}
