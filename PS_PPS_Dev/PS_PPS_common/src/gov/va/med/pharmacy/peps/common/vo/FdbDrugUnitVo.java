/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an Ingredient
 */
public class FdbDrugUnitVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long eplId;
    private Long fdbId;
    private String fdbDrugStrengthunits;

    /**
     * the constructor
     */
    public FdbDrugUnitVo() {

        super();
    }

    /**
     * getEplId for FdbDrugUnitVo
     * @return eplId
     */
    public Long getEplId() {
    
        return eplId;
    }

    /**
     * setEplId for FdbDrugUnitVo
     * @param eplId eplId
     */
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }

    /**
     * getFdbId for FdbDrugUnitVo
     * @return fdbId
     */
    public Long getFdbId() {
    
        return fdbId;
    }

    /**
     * setFdbId for FdbDrugUnitVo
     * @param fdbId fdbId
     */
    public void setFdbId(Long fdbId) {
    
        this.fdbId = fdbId;
    }

   
    /**
     * getFdbDrugStrengthunits for FdbDrugUnitVo
     * @return fdbDrugStrengthunits
     */
    public String getFdbDrugStrengthunits() {
    
        return fdbDrugStrengthunits;
    }

    /**
     * setFdbDrugStrengthunits for FdbDrugUnitVo.
     * @param fdbDrugStrengthunits fdbDrugStrengthunits
     */
    public void setFdbDrugStrengthunits(String fdbDrugStrengthunits) {
    
        this.fdbDrugStrengthunits = fdbDrugStrengthunits;
    }

    /**
     * returns the domain group of the managed Data for FdbDrugUnitVo.
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for FdbDrugUnitVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain for FdbDrugUnitVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for FdbDrugUnitVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return true;
    }
}
