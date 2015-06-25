/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types of available textual searches.
 */
public enum SearchDomain {

    /** SIMPLE */
    SIMPLE,

    /** ADVANCED */
    ADVANCED,

    /** DOMAIN */
    DOMAIN,

    /** PRODUCT */
    PRODUCT,

    /** ORDERABLE_ITEM */
    ORDERABLE_ITEM,

    /** SETUP_ATC */
    SETUP_ATC;

    /**
     * This method checks to see if this is a Simple Search
     * 
     * @return True if this is a Simple search
     */
    public boolean isSimpleSearch() {

        return SIMPLE.equals(this);
    }

    /**
     * This method checks to see if this is an Advanced Search
     * 
     * @return True if this is an Advanced search
     */
    public boolean isAdvancedSearch() {

        return ADVANCED.equals(this);
    }

    /**
     * This method checks to see if this is a Domain Search
     * 
     * @return True if this is a Domain search
     */
    public boolean isDomainSearch() {

        return DOMAIN.equals(this);
    }

    /**
     * This method checks to see if this is a Product Search
     * 
     * @return True if this is a Product search
     */
    public boolean isProductSearch() {

        return PRODUCT.equals(this);
    }

    /**
     * This method checks to see if this is an Orderable Item Search
     * 
     * @return True if this is an Orderable Item search
     */
    public boolean isOrderableItemSearch() {

        return ORDERABLE_ITEM.equals(this);
    }

    /**
     * Tests if this is an ATC Setup search.
     * 
     * @return boolean True if this is an ATC setup search.
     */
    public boolean isSetupAtcSearch() {

        return SETUP_ATC.equals(this);
    }

    /**
     * Provides access to appropriate view for this search type
     * 
     * @return view name
     */
    public String getViewName() {

        return this.toString().toLowerCase();
    }

}
