/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;


/**
 * ComplexStore holds multiple data for the Generic hashmap
 *
 */
public class ComplexStore {
    private String eplId;
    private List<Category> category;
    private String ndfIen;
    private DosageFormVo dosageForm;

    /**
     * default constructor
     * @param id EPL_ID
     * @param type The ProductType ListDataField
     * @param ien The IEN
     * @param dosageFormVo DosageFormVo
     */
    public ComplexStore(String id, List<Category> type, String ien, DosageFormVo dosageFormVo) {
        eplId = id;
        category = type;
        ndfIen = ien;
        dosageForm = dosageFormVo;
    }
    
    /**
     * getEplId
     * @return eplId
     */
    public String getEplId() {
        return eplId;
    }

    /**
     * getCategory
     * @return category
     */
    public List<Category> getCategory() {
        return category;
    }

    /**
     * getNdfIen
     * @return ndfIen
     */
    public String getNdfIen() {
        return ndfIen;
    }

    /**
     * getDosageForm
     * @return dosageForm
     */
    public DosageFormVo getDosageForm() {
        return dosageForm;
    }

}
