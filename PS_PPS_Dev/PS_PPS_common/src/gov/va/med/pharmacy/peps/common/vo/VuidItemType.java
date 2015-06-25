/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;


/**
 * Item Types used for Vuid history
 */
public enum VuidItemType {

    /** PRODUCTS */
    PRODUCTS,

    /** INGREDIENTS */
    INGREDIENTS,

    /** DRUG_CLASSES */
    DRUG_CLASSES,

    /** GENERIC */
    GENERIC,

    /** STANDARD_MED_ROUTE */
    STANDARD_MED_ROUTE;

    /**
     * Returns a list of items types
     * 
     * @return a collection of roles that cannot edit
     */
    public static Collection<VuidItemType> getItemTypes() {
        ArrayList<VuidItemType> ret = new ArrayList<VuidItemType>();
        
        ret.add(PRODUCTS);
        ret.add(INGREDIENTS);
        ret.add(DRUG_CLASSES);
        ret.add(GENERIC);
        ret.add(STANDARD_MED_ROUTE);
        
        
        return ret;
    }

    /**
     * Description
     * 
     * @return boolean true if this instance of {@link VuidItemType} is Product
     */
    public boolean isProducts() {
        return PRODUCTS.equals(this) ;

    }

    /**
     * Description
     * 
     * @return boolean true if this instance of {@link VuidItemType} is Ingredients
     */
    public boolean isIngredients() {
        return INGREDIENTS.equals(this) ;

    }

    /**
     * Description
     * 
     * @return boolean true if this instance of {@link VuidItemType} is Drug Classes
     */
    public boolean isDrugClasses() {
        return DRUG_CLASSES.equals(this) ;

    }

    /**
     * Description
     * 
     * @return boolean true if this instance of {@link VuidItemType} is Generic
     */
    public boolean isGeneric() {
        return GENERIC.equals(this) ;

    }

    /**
     * Description
     * 
     * @return boolean true if this instance of {@link VuidItemType} is Standard Med Route
     */
    public boolean isStandardMedRoute() {
        return STANDARD_MED_ROUTE.equals(this) ;

    }
}
