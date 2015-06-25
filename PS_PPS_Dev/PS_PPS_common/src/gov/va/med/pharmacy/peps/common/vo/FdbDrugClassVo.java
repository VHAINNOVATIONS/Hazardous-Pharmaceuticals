/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an Ingredient
 */
public class FdbDrugClassVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long eplId;
    private Long fdbId;
    private String fdbDrugClass;

    /**
     * the constructor
     */
    public FdbDrugClassVo() {

        super();
    }

    /**
     * This is the FdbDrugClassVo method to get the EplId.
     * @return eplId
     */
    public Long getEplId() {
    
        return eplId;
    }

    /**
     * setEplId for FdbDrugClassVo
     * @param eplId eplId
     */
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }

    /**
     * getFdbId  for FdbDrugClassVo
     * @return fdbId
     */
    public Long getFdbId() {
    
        return fdbId;
    }

    /**
     * setFdbId  for FdbDrugClassVo
     * @param fdbId fdbId
     */
    public void setFdbId(Long fdbId) {
    
        this.fdbId = fdbId;
    }

    /**
     * getFdbDrugClass  for FdbDrugClassVo
     * @return fdbDrugClass
     */
    public String getFdbDrugClass() {
    
        return fdbDrugClass;
    }

    /**
     * setFdbDrugClass  for FdbDrugClassVo
     * @param fdbDrugClass fdbDrugClass
     */
    public void setFdbDrugClass(String fdbDrugClass) {
    
        this.fdbDrugClass = fdbDrugClass;
    }

    /**
     * returns the domain group of the managed Data for FdbDrugClassVo
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for FdbDrugClassVo
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain for FdbDrugClassVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for FdbDrugClassVo
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return true;
    }
}
