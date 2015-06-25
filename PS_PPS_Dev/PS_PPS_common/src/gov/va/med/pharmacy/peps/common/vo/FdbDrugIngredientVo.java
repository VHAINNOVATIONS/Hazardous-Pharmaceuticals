/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an Ingredient
 */
public class FdbDrugIngredientVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long eplId;
    private Long fdbId;
    private String fdbDrugIngredient;

    /**
     * the constructor
     */
    public FdbDrugIngredientVo() {

        super();
    }

    /**
     * getEplId for FdbDrugIngredientVo.
     * @return eplId
     */
    public Long getEplId() {
    
        return eplId;
    }

    /**
     * setEplId for FdbDrugIngredientVo.
     * @param eplId eplId
     */
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }

    /**
     * getFdbId for FdbDrugIngredientVo.
     * @return fdbId
     */
    public Long getFdbId() {
    
        return fdbId;
    }

    /**
     * setFdbId for FdbDrugIngredientVo.
     * @param fdbId fdbId
     */
    public void setFdbId(Long fdbId) {
    
        this.fdbId = fdbId;
    }

    /**
     * getFdbDrugIngredient for FdbDrugIngredientVo.
     * @return fdbDrugIngredient fdbDrugIngredient
     */
    public String getFdbDrugIngredient() {
    
        return fdbDrugIngredient;
    }

    /**
     * setFdbDrugIngredient for FdbDrugIngredientVo.
     * @param fdbDrugIngredient fdbDrugIngredient
     */
    public void setFdbDrugIngredient(String fdbDrugIngredient) {
    
        this.fdbDrugIngredient = fdbDrugIngredient;
    }

    /**
     * returns the domain group of the managed Data for FdbDrugIngredientVo.
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for FdbDrugIngredientVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain for FdbDrugIngredientVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for FdbDrugIngredientVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return true;
    }
}
