/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Order in which a {@link PaginatedList} is sorted.
 */
public enum SortOrder {

    /** ASCENDING */
    ASCENDING,

    /** DESCENDING */
    DESCENDING;

    /**
     * Return true if this instance of {@link SortOrder} is {@link #ASCENDING}.
     * 
     * @return boolean true if in ascending order
     */
    public boolean isAscending() {

        return ASCENDING.equals(this);
    }

    /**
     * Return true if this instance of {@link SortOrder} is {@link #DESCENDING}.
     * 
     * @return boolean true if in descending order
     */
    public boolean isDescending() {

        return DESCENDING.equals(this);
    }
}
