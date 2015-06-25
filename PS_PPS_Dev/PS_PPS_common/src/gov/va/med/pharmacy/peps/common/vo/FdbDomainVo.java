/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * FdbDomainVo
 */
public class FdbDomainVo {

    private static final long serialVersionUID = 1L;

    private String fdbDomainId;
    private Long eplDomainId;
    private String name;
    private Date date;
    private FdbDomainType domainType;
    private String eplTerm; 

    /**
     * FdbDomainVo.
     */
    public FdbDomainVo() {

        super();
    }

    /**
     * getId.
     * setId.
     * getFdbDomainId.
     * @return fdbDomainId
     */
    public String getFdbDomainId() {

        return fdbDomainId;
    }

    /**
     * setFdbDomainId.
     * @param fdbDomainId fdbDomainId
     */
    public void setFdbDomainId(String fdbDomainId) {

        this.fdbDomainId = fdbDomainId;
    }

    /**
     * getName.
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * setName.
     * @param name name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * getDate.
     * @return date
     */
    public Date getDate() {

        return date;
    }

    /**
     * setDate
     * @param date date
     */
    public void setDate(Date date) {

        this.date = date;
    }

    /**
     * eplDomainId
     * @return eplDomainId
     */
    public Long getEplDomainId() {

        return eplDomainId;
    }

    /**
     * eplDomainId
     * @param eplDomainId eplDomainId
     */
    public void setEplDomainId(Long eplDomainId) {

        this.eplDomainId = eplDomainId;
    }

    
    /**
     * domainType
     * @return domainType
     */
    public FdbDomainType getDomainType() {
    
        return domainType;
    }

    
    /**
     * domainType
     * @param domainType domainType
     */
    public void setDomainType(FdbDomainType domainType) {
    
        this.domainType = domainType;
    }

    
    /**
     * eplTerm
     * @return eplTerm
     */
    public String getEplTerm() {
    
        return eplTerm;
    }

    
    /**
     * eplTerm
     * @param eplTerm eplTerm
     */
    public void setEplTerm(String eplTerm) {
    
        this.eplTerm = eplTerm;
    }

}
