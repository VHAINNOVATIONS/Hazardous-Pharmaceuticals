/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import java.util.Comparator;

import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSynonymDo;




/**
 * This is the Product Synonym Comparator class
 *
 */
public class ProductSynonymComparator implements Comparator<EplProductDo> {

    @Override
    public int compare(EplProductDo o1, EplProductDo o2) {

        String a01 = "";
        String a02 = "";

        // Check if NDC's trade name is already in synonyms
        for (EplSynonymDo synonym : o1.getEplSynonyms()) {
            a01 = synonym.getSynonymName();
            break;
        }
        
        for (EplSynonymDo synonym : o2.getEplSynonyms()) {
            a02 = synonym.getSynonymName();
            break;
        }

        return a01.compareTo(a02);
    }
}
