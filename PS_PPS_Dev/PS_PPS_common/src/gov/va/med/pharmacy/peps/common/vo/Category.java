/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Possible categories
 *
 */
public enum Category {

    /**
     * MEDICATION.
     */
    MEDICATION,

    /**
     * INVESTIGATIONAL.
     */
    INVESTIGATIONAL,

    /**
     * COMPOUND.
     */
    COMPOUND,

    /**
     * SUPPLY
     */
    SUPPLY;

    /**
     * get all categories
     * 
     * @return list of categories
     */
    public static List<Category> getAll() {

        ArrayList<Category> ret = new ArrayList<Category>();

        for (Category type : values()) {
            ret.add(type);
        }

        return ret;
    }
}
