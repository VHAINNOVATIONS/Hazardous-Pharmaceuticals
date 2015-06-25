/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Jan 11, 2008 2:18:52 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;


/**
 * EplProdDrugClassAssocDo generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplProdDrugClassAssocDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements
    java.io.Serializable {

    // Fields
    public static final String EPL_VA_DRUG_CLASS = "eplVaDrugClass";
    public static final String EPL_PRODUCT = "eplProduct";
    public static final String PRIMARY_YN = "primaryYn";
    //public static final String KEY = "key";
    
    private static final long serialVersionUID = 1L;

    public EplProdDrugClassAssocDoKey key;
    private String primaryYn;
    private EplVaDrugClassDo eplVaDrugClass;
    private EplProductDo eplProduct;

    // Constructors

    /**
     * default constructor
     */
    public EplProdDrugClassAssocDo() {
    }

    /**
     * minimal constructor
     */
    public EplProdDrugClassAssocDo(EplProdDrugClassAssocDoKey key, String primaryYn, String createdBy, Date createdDtm) {
        this.key = key;
        this.primaryYn = primaryYn;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
    }

    /**
     * full constructor
     */
    public EplProdDrugClassAssocDo(EplProdDrugClassAssocDoKey key, String primaryYn, String createdBy, Date createdDtm,
                                   String lastModifiedBy, Date lastModifiedDtm, EplVaDrugClassDo eplVaDrugClass,
                                   EplProductDo eplProduct) {
        this.key = key;
        this.primaryYn = primaryYn;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
        this.setLastModifiedBy(lastModifiedBy);
        this.setLastModifiedDtm(lastModifiedDtm);
        this.eplVaDrugClass = eplVaDrugClass;
        this.eplProduct = eplProduct;
    }

    // Property accessors
    public EplProdDrugClassAssocDoKey getKey() {
        return this.key;
    }

    public void setKey(EplProdDrugClassAssocDoKey key) {
        this.key = key;
    }

    public String getPrimaryYn() {
        return this.primaryYn;
    }

    public void setPrimaryYn(String primaryYn) {
        this.primaryYn = primaryYn;
    }

    public EplVaDrugClassDo getEplVaDrugClass() {
        return this.eplVaDrugClass;
    }

    public void setEplVaDrugClass(EplVaDrugClassDo eplVaDrugClass) {
        this.eplVaDrugClass = eplVaDrugClass;
    }

    public EplProductDo getEplProduct() {
        return this.eplProduct;
    }

    public void setEplProduct(EplProductDo eplProduct) {
        this.eplProduct = eplProduct;
    }

    /**
     * toString
     * 
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("primaryYn").append("='").append(getPrimaryYn()).append("' ");
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

        if (!(other instanceof EplProdDrugClassAssocDo)) {
            return false;
        }

        EplProdDrugClassAssocDo castOther = (EplProdDrugClassAssocDo) other;

        return ((this.getKey() == castOther.getKey()) || (this.getKey() != null && castOther.getKey() != null && this
            .getKey().equals(castOther.getKey())))
            && ((this.getPrimaryYn() == castOther.getPrimaryYn()) || (this.getPrimaryYn() != null
                && castOther.getPrimaryYn() != null && this.getPrimaryYn().equals(castOther.getPrimaryYn())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getKey() == null ? 0 : this.getKey().hashCode());
        result = 37 * result + (getPrimaryYn() == null ? 0 : this.getPrimaryYn().hashCode());

        return result;
    }

}