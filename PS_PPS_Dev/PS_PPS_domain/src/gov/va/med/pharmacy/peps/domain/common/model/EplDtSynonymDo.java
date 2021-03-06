/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Aug 19, 2008 1:24:57 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;


/**
 * EplDtSynonymDo generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplDtSynonymDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements java.io.Serializable {

    // Fields
    
    private static final long serialVersionUID = 1L;

    private EplDtSynonymDoKey key;
    private EplDrugTextDo eplDrugText;

    // Constructors

    /** default constructor */
    
    public EplDtSynonymDo() {
    }

    /** minimal constructor */
    
    public EplDtSynonymDo(EplDtSynonymDoKey key, String createdBy, Date createdDtm) {
        this.key = key;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
    }

    /** full constructor */
    
    public EplDtSynonymDo(EplDtSynonymDoKey key, String createdBy, Date createdDtm, String lastModifiedBy,
                          Date lastModifiedDtm, EplDrugTextDo eplDrugText) {
        this.key = key;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
        this.setLastModifiedBy(lastModifiedBy);
        this.setLastModifiedDtm(lastModifiedDtm);
        this.eplDrugText = eplDrugText;
    }

    // Property accessors
    public EplDtSynonymDoKey getKey() {
        return this.key;
    }

    public void setKey(EplDtSynonymDoKey key) {
        this.key = key;
    }

    public EplDrugTextDo getEplDrugText() {
        return this.eplDrugText;
    }

    public void setEplDrugText(EplDrugTextDo eplDrugText) {
        this.eplDrugText = eplDrugText;
    }

    /**
     * toString
     * 
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");
        buffer.append("createdDtm").append("='").append(getCreatedDtm()).append("' ");
        buffer.append("lastModifiedBy").append("='").append(getLastModifiedBy()).append("' ");
        buffer.append("lastModifiedDtm").append("='").append(getLastModifiedDtm()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }

        if ((other == null)) {
            return false;
        }

        if (!(other instanceof EplDtSynonymDo)) {
            return false;
        }

        EplDtSynonymDo castOther = (EplDtSynonymDo) other;

        return ((this.getKey() == castOther.getKey()) || (this.getKey() != null && castOther.getKey() != null && this
            .getKey().equals(castOther.getKey())))
            && ((this.getCreatedBy() == castOther.getCreatedBy()) || (this.getCreatedBy() != null
                && castOther.getCreatedBy() != null && this.getCreatedBy().equals(castOther.getCreatedBy())))
            && ((this.getCreatedDtm() == castOther.getCreatedDtm()) || (this.getCreatedDtm() != null
                && castOther.getCreatedDtm() != null && this.getCreatedDtm().equals(castOther.getCreatedDtm())))
            && ((this.getLastModifiedBy() == castOther.getLastModifiedBy()) || (this.getLastModifiedBy() != null
                && castOther.getLastModifiedBy() != null && this.getLastModifiedBy().equals(castOther.getLastModifiedBy())))
            && ((this.getLastModifiedDtm() == castOther.getLastModifiedDtm()) || (this.getLastModifiedDtm() != null
                && castOther.getLastModifiedDtm() != null && this.getLastModifiedDtm()
                .equals(castOther.getLastModifiedDtm())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getKey() == null ? 0 : this.getKey().hashCode());
        result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
        result = 37 * result + (getCreatedDtm() == null ? 0 : this.getCreatedDtm().hashCode());
        result = 37 * result + (getLastModifiedBy() == null ? 0 : this.getLastModifiedBy().hashCode());
        result = 37 * result + (getLastModifiedDtm() == null ? 0 : this.getLastModifiedDtm().hashCode());

        return result;
    }

}
