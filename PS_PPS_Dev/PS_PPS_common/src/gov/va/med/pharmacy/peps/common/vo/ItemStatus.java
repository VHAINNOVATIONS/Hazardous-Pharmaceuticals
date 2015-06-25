/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * State of a Product
 */
public enum ItemStatus {

    /** ACTIVE */
    ACTIVE,

    /** INACTIVE */
    INACTIVE,

    /** ARCHIVED */
    ARCHIVED;

    
    /**
     * getValues
     * 
     * @return ItemStatus [] of ACTIVE, INACTIVE only
     */
    public static ItemStatus[] getValues() {
        return (new ItemStatus[] {ItemStatus.ACTIVE, ItemStatus.INACTIVE});
    }

    /**
     * isActive
     * 
     * @return boolean true if this ItemStatus is ACTIVE
     */
    public boolean isActive() {
        return ACTIVE.equals(this);
    }

    /**
     * isInactive
     * 
     * @return boolean true if this ItemStatus is INACTIVE
     */
    public boolean isInactive() {
        return INACTIVE.equals(this);
    }

    /**
     * isArchived
     * 
     * @return boolean true if this ItemStatus is ARCHIVED
     */
    public boolean isArchived() {
        return ARCHIVED.equals(this);
    }

}
