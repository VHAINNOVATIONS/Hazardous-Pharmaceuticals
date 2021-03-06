/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Feb 17, 2009 10:26:30 AM by Hibernate Tools 3.2.0.beta8

import java.util.Date;


/**
 * EplProductVitalDo generated by hbm2java
 * @hibernate.class
 */
public class EplProductVitalDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements
    java.io.Serializable {

    // Fields
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String vitalDisplayOrderEntry;
    private String vitalDisplayFinishAnOrder;
    private String vitalDisplayAdministration;
    private EplProductDo eplProduct;

    // Constructors

    /** default constructor */

    public EplProductVitalDo() {
    }

    /** minimal constructor */

    public EplProductVitalDo(Long id, String vitalDisplayOrderEntry, String vitalDisplayFinishAnOrder,
                             String vitalDisplayAdministration, String createdBy, Date createdDtm) {
        this.id = id;
        this.vitalDisplayOrderEntry = vitalDisplayOrderEntry;
        this.vitalDisplayFinishAnOrder = vitalDisplayFinishAnOrder;
        this.vitalDisplayAdministration = vitalDisplayAdministration;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
    }

    /** full constructor */

    public EplProductVitalDo(Long id, String vitalDisplayOrderEntry, String vitalDisplayFinishAnOrder,
                             String vitalDisplayAdministration, String createdBy, Date createdDtm, String lastModifiedBy,
                             Date lastModifiedDtm, EplProductDo eplProduct) {
        this.id = id;
        this.vitalDisplayOrderEntry = vitalDisplayOrderEntry;
        this.vitalDisplayFinishAnOrder = vitalDisplayFinishAnOrder;
        this.vitalDisplayAdministration = vitalDisplayAdministration;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
        this.setLastModifiedBy(lastModifiedBy);
        this.setLastModifiedDtm(lastModifiedDtm);
        this.eplProduct = eplProduct;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVitalDisplayOrderEntry() {
        return this.vitalDisplayOrderEntry;
    }

    public void setVitalDisplayOrderEntry(String vitalDisplayOrderEntry) {
        this.vitalDisplayOrderEntry = vitalDisplayOrderEntry;
    }

    public String getVitalDisplayFinishAnOrder() {
        return this.vitalDisplayFinishAnOrder;
    }

    public void setVitalDisplayFinishAnOrder(String vitalDisplayFinishAnOrder) {
        this.vitalDisplayFinishAnOrder = vitalDisplayFinishAnOrder;
    }

    public String getVitalDisplayAdministration() {
        return this.vitalDisplayAdministration;
    }

    public void setVitalDisplayAdministration(String vitalDisplayAdministration) {
        this.vitalDisplayAdministration = vitalDisplayAdministration;
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
        buffer.append("vitalDisplayOrderEntry").append("='").append(getVitalDisplayOrderEntry()).append("' ");
        buffer.append("vitalDisplayFinishAnOrder").append("='").append(getVitalDisplayFinishAnOrder()).append("' ");
        buffer.append("vitalDisplayAdministration").append("='").append(getVitalDisplayAdministration()).append("' ");
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

        if (!(other instanceof EplProductVitalDo)) {
            return false;
        }

        EplProductVitalDo castOther = (EplProductVitalDo) other;

        return ((this.getId() == castOther.getId()) || (this.getId() != null && castOther.getId() != null && this.getId()
            .equals(castOther.getId())))
            && ((this.getVitalDisplayOrderEntry() == castOther.getVitalDisplayOrderEntry()) || (this
                .getVitalDisplayOrderEntry() != null
                && castOther.getVitalDisplayOrderEntry() != null && this.getVitalDisplayOrderEntry().equals(
                    castOther.getVitalDisplayOrderEntry())))
            && ((this.getVitalDisplayFinishAnOrder() == castOther.getVitalDisplayFinishAnOrder()) || (this
                .getVitalDisplayFinishAnOrder() != null
                && castOther.getVitalDisplayFinishAnOrder() != null && this.getVitalDisplayFinishAnOrder().equals(
                    castOther.getVitalDisplayFinishAnOrder())))
            && ((this.getVitalDisplayAdministration() == castOther.getVitalDisplayAdministration()) || (this
                .getVitalDisplayAdministration() != null
                && castOther.getVitalDisplayAdministration() != null && this.getVitalDisplayAdministration().equals(
                    castOther.getVitalDisplayAdministration())))
            && ((this.getCreatedBy() == castOther.getCreatedBy()) || (this.getCreatedBy() != null
                && castOther.getCreatedBy() != null && this.getCreatedBy().equals(castOther.getCreatedBy())))
            && ((this.getCreatedDtm() == castOther.getCreatedDtm()) || (this.getCreatedDtm() != null
                && castOther.getCreatedDtm() != null && this.getCreatedDtm().equals(castOther.getCreatedDtm())))
            && ((this.getLastModifiedBy() == castOther.getLastModifiedBy()) || (this.getLastModifiedBy() != null
                && castOther.getLastModifiedBy() != null && this.getLastModifiedBy().equals(castOther.getLastModifiedBy())))
            && ((this.getLastModifiedDtm() == castOther.getLastModifiedDtm()) || (this.getLastModifiedDtm() != null
                && castOther.getLastModifiedDtm() != null && this.getLastModifiedDtm()
                .equals(castOther.getLastModifiedDtm())))
            && ((this.getEplProduct() == castOther.getEplProduct()) || (this.getEplProduct() != null
                && castOther.getEplProduct() != null && this.getEplProduct().equals(castOther.getEplProduct())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
        result = 37 * result + (getVitalDisplayOrderEntry() == null ? 0 : this.getVitalDisplayOrderEntry().hashCode());
        result = 37 * result + (getVitalDisplayFinishAnOrder() == null ? 0 : this.getVitalDisplayFinishAnOrder().hashCode());
        result = 37 * result
            + (getVitalDisplayAdministration() == null ? 0 : this.getVitalDisplayAdministration().hashCode());
        result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
        result = 37 * result + (getCreatedDtm() == null ? 0 : this.getCreatedDtm().hashCode());
        result = 37 * result + (getLastModifiedBy() == null ? 0 : this.getLastModifiedBy().hashCode());
        result = 37 * result + (getLastModifiedDtm() == null ? 0 : this.getLastModifiedDtm().hashCode());
        result = 37 * result + (getEplProduct() == null ? 0 : this.getEplProduct().hashCode());
        
        return result;
    }

}
