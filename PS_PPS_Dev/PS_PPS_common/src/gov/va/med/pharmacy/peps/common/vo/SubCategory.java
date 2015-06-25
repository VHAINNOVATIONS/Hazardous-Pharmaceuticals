/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Possible Sub Categories
 *
 */
public enum SubCategory {

    /**
     * HERBAL
     */
    HERBAL,

    /**
     * CHEMOTHERAPY
     */
    CHEMOTHERAPY,

    /**
     * OTC
     */
    OTC,

    /**
     * VETERINARY
     */
    VETERINARY;

    /**
     * getAll
     * 
     * @return list of sub categories
     */
    public static List<SubCategory> getAll() {

        ArrayList<SubCategory> ret = new ArrayList<SubCategory>();

        for (SubCategory type : values()) {
            ret.add(type);
        }

        return ret;
    }
}
