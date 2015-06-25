/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;



/**
 * Data representing an Ingredient
 */
public class FdbDosageFormVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long eplId;
    private Long fdbId;
    private String drugDosageFormDesc;

    /**
     * the constructor
     */
    public FdbDosageFormVo() {

        super();
    }

    /**
     * getEplId.
     * @return eplId
     */
    public Long getEplId() {
    
        return eplId;
    }

    /**
     * setEplId.
     * @param eplId eplId
     */
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }

    /**
     * getFdbId.
     * @return fdbId
     */
    public Long getFdbId() {
    
        return fdbId;
    }

    /**
     * setFdbId.
     * @param fdbId fdbId
     */
    public void setFdbId(Long fdbId) {
    
        this.fdbId = fdbId;
    }

    /**
     * getDrugDosageFormDesc
     * @return drugDosageFormDesc
     */
    public String getDrugDosageFormDesc() {
    
        return drugDosageFormDesc;
    }

    /**
     * setDrugDosageFormDesc
     * @param drugDosageFormDesc drugDosageFormDesc
     */
    public void setDrugDosageFormDesc(String drugDosageFormDesc) {
    
        this.drugDosageFormDesc = drugDosageFormDesc;
    }

}
