/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import java.util.Comparator;

import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;




/**
 * This is the Product OI Comparator class used to sort the products by OI Name.
 *
 */
public class ProductOIComparatorDesc implements Comparator<EplProductDo> {

    @Override
    public int compare(EplProductDo o1, EplProductDo o2) {

        String a01 = "";
        String a02 = "";

        // Check if NDC's trade name is already in synonyms
        a01 = o1.getEplOrderableItem().getOiName();
        a02 = o2.getEplOrderableItem().getOiName();

        return a02.compareTo(a01);
    }
}
