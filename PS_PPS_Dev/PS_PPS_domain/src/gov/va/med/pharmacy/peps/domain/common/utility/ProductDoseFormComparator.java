/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import java.util.Comparator;

import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;




/**
 * This is the Product Synonym Comparator class
 *
 */
public class ProductDoseFormComparator implements Comparator<EplProductDo> {

    @Override
    public int compare(EplProductDo o1, EplProductDo o2) {

        String a01 = "";
        String a02 = "";

        // Check if NDC's trade name is already in synonyms
        a01 = o1.getEplOrderableItem().getEplDosageForm().getDfName();
        a02 = o2.getEplOrderableItem().getEplDosageForm().getDfName();

        return a01.compareTo(a02);
    }
}
