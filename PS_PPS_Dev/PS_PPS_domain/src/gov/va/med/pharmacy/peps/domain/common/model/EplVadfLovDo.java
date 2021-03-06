/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Mar 2, 2008 2:06:48 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * EplVadfLovDo generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplVadfLovDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements java.io.Serializable {

    // Fields
    public static final String DEFAULT_VALUE = "defaultValue";
    public static final String EPL_VA_DF = "eplVaDf";
    
    private static final long serialVersionUID = 1L;
    
    private EplVadfLovDoKey key;
    private String defaultValue;
    private EplVaDfDo eplVaDf;
    private Set<EplVadfAssocValueDo> eplVadfAssocValues = new HashSet<EplVadfAssocValueDo>(0);

    // Constructors

    /**
     * default constructor
     */ 
    public EplVadfLovDo() {
    }

    /**
     * minimal constructor
     * 
     * @param key of type EplVadfLovDoKey
     * @param defaultValue of type String
     * @param createdBy of type String
     * @param createdDtm of type Date
     */
    public EplVadfLovDo(EplVadfLovDoKey key, String defaultValue, String createdBy, Date createdDtm) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
    }

    /**
     * full constructor
     * 
     * @param key of type EplVadfLovDoKey
     * @param defaultValue of type String
     * @param createdBy of type String
     * @param createdDtm of type Date
     * @param lastModifiedBy of type String
     * @param lastModifiedDtm of type Date
     * @param eplVaDf of type EplVaDfDo
     * @param eplVadfAssocValues of type Set<EplVadfAssocValueDo>
     */
    public EplVadfLovDo(EplVadfLovDoKey key, String defaultValue, String createdBy, Date createdDtm, String lastModifiedBy,
                        Date lastModifiedDtm, EplVaDfDo eplVaDf, Set<EplVadfAssocValueDo> eplVadfAssocValues) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.eplVaDf = eplVaDf;
        this.eplVadfAssocValues = eplVadfAssocValues;
    }

    // Property accessors
    
    /**
     * @return EplVadfLovDoKey
     */
    public EplVadfLovDoKey getKey() {
        return this.key;
    }

    /**
     * 
     * 
     * @param key property
     */
    public void setKey(EplVadfLovDoKey key) {
        this.key = key;
    }

    /**
     * 
     * 
     * @return String
     */
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * 
     * 
     * @param defaultValue property
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 
     * 
     * @return EplVaDfDo
     */
    public EplVaDfDo getEplVaDf() {
        return this.eplVaDf;
    }

    /**
     * 
     * 
     * @param eplVaDf property
     */
    public void setEplVaDf(EplVaDfDo eplVaDf) {
        this.eplVaDf = eplVaDf;
    }

    /**
     * 
     * 
     * @return Set<EplVadfAssocValueDo>
     */
    public Set<EplVadfAssocValueDo> getEplVadfAssocValues() {
        return this.eplVadfAssocValues;
    }

    /**
     * 
     * 
     * @param eplVadfAssocValues property
     */
    public void setEplVadfAssocValues(Set<EplVadfAssocValueDo> eplVadfAssocValues) {
        this.eplVadfAssocValues = eplVadfAssocValues;
    }

    /**
     * toString
     * 
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("defaultValue").append("='").append(getDefaultValue()).append("' ");
        buffer.append("createdBy").append("='").append(getCreatedBy()).append("' ");
        buffer.append("createdDtm").append("='").append(getCreatedDtm()).append("' ");
        buffer.append("lastModifiedBy").append("='").append(getLastModifiedBy()).append("' ");
        buffer.append("lastModifiedDtm").append("='").append(getLastModifiedDtm()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * 
     * 
     * @param other the object
     * @return boolean
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.model.DataObject#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        
        if ((other == null)) {
            return false;
        }
        
        if (!(other instanceof EplVadfLovDo)) {
            return false;
        }
        
        EplVadfLovDo castOther = (EplVadfLovDo) other;

        return ((this.getKey() == castOther.getKey()) || (this.getKey() != null && castOther.getKey() != null && this
            .getKey().equals(castOther.getKey())))
            && ((this.getDefaultValue() == castOther.getDefaultValue()) || (this.getDefaultValue() != null
                && castOther.getDefaultValue() != null && this.getDefaultValue().equals(castOther.getDefaultValue())))
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

    /**
     * 
     * 
     * @return int
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.model.DataObject#hashCode()
     */
    public int hashCode() {
        int result = 17;

        result = 37 * result + (getKey() == null ? 0 : this.getKey().hashCode());
        result = 37 * result + (getDefaultValue() == null ? 0 : this.getDefaultValue().hashCode());
        result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
        result = 37 * result + (getCreatedDtm() == null ? 0 : this.getCreatedDtm().hashCode());
        result = 37 * result + (getLastModifiedBy() == null ? 0 : this.getLastModifiedBy().hashCode());
        result = 37 * result + (getLastModifiedDtm() == null ? 0 : this.getLastModifiedDtm().hashCode());

        return result;
    }

}
