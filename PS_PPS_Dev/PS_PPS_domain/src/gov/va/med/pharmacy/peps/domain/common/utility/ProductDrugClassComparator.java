/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import java.util.Comparator;

import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugClassAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;




/**
 * This is the Product Synonym Comparator class
 *
 */
public class ProductDrugClassComparator implements Comparator<EplProductDo> {

    @Override
    public int compare(EplProductDo o1, EplProductDo o2) {

        String a001 = "";
        String a002 = "";


        for (EplProdDrugClassAssocDo drugClass : o1.getEplProdDrugClassAssocs()) {
            if ("Y".equals(drugClass.getPrimaryYn())) {
                a001 = drugClass.getEplVaDrugClass().getCode();
                break;
            }
        }
        
        for (EplProdDrugClassAssocDo drugClass : o2.getEplProdDrugClassAssocs()) {
            if ("Y".equals(drugClass.getPrimaryYn())) {
                a002 = drugClass.getEplVaDrugClass().getCode();
                break;
            }
        }

        return a001.compareTo(a002);
    }
}
