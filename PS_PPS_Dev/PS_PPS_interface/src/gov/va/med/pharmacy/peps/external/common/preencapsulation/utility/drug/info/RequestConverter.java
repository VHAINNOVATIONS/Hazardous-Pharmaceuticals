/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.info;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.info.request.Drug;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.info.request.DrugInfoRequest;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugInfoVo;


/**
 * Convert DrugInfoRequest JAXB objects into value objects
 */
public class RequestConverter {

    /**
     * Cannot instantiate
     */
    private RequestConverter() {
        super();
    }

    /**
     * Convert DrugInfoRequest JAXB objects into value objects
     * 
     * @param request DrugInfoRequest to convert
     * @return Collection of DrugInfoVo
     */
    public static Collection<DrugInfoVo> toDrugInfoVo(DrugInfoRequest request) {
        Collection<DrugInfoVo> drugs = new ArrayList<DrugInfoVo>(request.getDrug().size());

        for (Drug drug : request.getDrug()) {
            DrugInfoVo drugInfo = new DrugInfoVo();
            drugInfo.setGcnSeqNo(drug.getGcnSeqNo().toString());

            drugs.add(drugInfo);
        }

        return drugs;
    }

}
