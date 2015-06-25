/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an Ingredient
 */
public class FdbGenericNameVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long eplId;
    private Long fdbId;
    private String fdbGenericDrugname;

    /**
     * the constructor
     */
    public FdbGenericNameVo() {

        super();
    }

    /**
     * getEplId
     * @return eplId
     */
    public Long getEplId() {
    
        return eplId;
    }

    /**
     * setEplId
     * @param eplId eplId
     */
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }

    /**
     * getFdbId
     * @return fdbId
     */
    public Long getFdbId() {
    
        return fdbId;
    }

    /**
     * setFdbId
     * @param fdbId fdbId
     */
    public void setFdbId(Long fdbId) {
    
        this.fdbId = fdbId;
    }

   
    /**
     * getFdbGenericDrugname
     * @return fdbGenericDrugname
     */
    public String getFdbGenericDrugname() {
    
        return fdbGenericDrugname;
    }

    /**
     * setFdbGenericDrugname
     * @param fdbGenericDrugname fdbGenericDrugname
     */
    public void setFdbGenericDrugname(String fdbGenericDrugname) {
    
        this.fdbGenericDrugname = fdbGenericDrugname;
    }

    /**
     * returns the domain group of the managed Data
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return true;
    }
}
