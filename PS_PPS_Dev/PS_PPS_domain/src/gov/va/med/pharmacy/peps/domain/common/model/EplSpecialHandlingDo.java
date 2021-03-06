/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated May 4, 2009 4:24:25 PM by Hibernate Tools 3.2.0.beta8

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * EplSpecialHandlingDo generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplSpecialHandlingDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements
    java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    // Fields
    public static final String ITEM_STATUS = "itemStatus";
    public static final String CODE = "specialHandlingCode";
    public static final String DESCRIPTION = "specialHandlingDescription";
    public static final String EPL_ID = "eplId";

    private Long eplId;
    private String specialHandlingCode;
    private String specialHandlingDescription;
    private String itemStatus;
    private Long revisionNumber;
    private Date inactivationDate;
    private String createdBy;
    private Date createdDtm;
    private String lastModifiedBy;
    private Date lastModifiedDtm;
    private Set<EplProdSpecHandlingAssocDo> eplProdSpecHandlingAssocs = new HashSet<EplProdSpecHandlingAssocDo>(0);

    // Constructors

    /** default constructor */
    
    public EplSpecialHandlingDo() {
    }

    /** minimal constructor */
    
    public EplSpecialHandlingDo(Long eplId, String specialHandlingCode, String specialHandlingDescription,
                                String specialHandlingStatus, Long revisionNumber, String createdBy, Date createdDtm) {
        this.eplId = eplId;
        this.specialHandlingCode = specialHandlingCode;
        this.specialHandlingDescription = specialHandlingDescription;
        this.itemStatus = specialHandlingStatus;
        this.revisionNumber = revisionNumber;
        this.createdBy = createdBy;
        this.createdDtm = createdDtm;
    }

    /** full constructor */
    
    public EplSpecialHandlingDo(Long eplId, String specialHandlingCode, String specialHandlingDescription,
                                String specialHandlingStatus, Long revisionNumber, Date inactivationDate, String createdBy,
                                Date createdDtm, String lastModifiedBy, Date lastModifiedDtm,
                                Set<EplProdSpecHandlingAssocDo> eplProdSpecHandlingAssocs) {
        this.eplId = eplId;
        this.specialHandlingCode = specialHandlingCode;
        this.specialHandlingDescription = specialHandlingDescription;
        this.itemStatus = specialHandlingStatus;
        this.revisionNumber = revisionNumber;
        this.inactivationDate = inactivationDate;
        this.createdBy = createdBy;
        this.createdDtm = createdDtm;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDtm = lastModifiedDtm;
        this.eplProdSpecHandlingAssocs = eplProdSpecHandlingAssocs;
    }

    // Property accessors
    public Long getEplId() {
        return this.eplId;
    }

    public void setEplId(Long eplId) {
        this.eplId = eplId;
    }

    public String getSpecialHandlingCode() {
        return this.specialHandlingCode;
    }

    public void setSpecialHandlingCode(String specialHandlingCode) {
        this.specialHandlingCode = specialHandlingCode;
    }

    public String getSpecialHandlingDescription() {
        return this.specialHandlingDescription;
    }

    public void setSpecialHandlingDescription(String specialHandlingDescription) {
        this.specialHandlingDescription = specialHandlingDescription;
    }

    public String getItemStatus() {
        return this.itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getRevisionNumber() {
        return this.revisionNumber;
    }

    public void setRevisionNumber(Long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Date getInactivationDate() {
        return this.inactivationDate;
    }

    public void setInactivationDate(Date inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDtm() {
        return this.createdDtm;
    }

    public void setCreatedDtm(Date createdDtm) {
        this.createdDtm = createdDtm;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDtm() {
        return this.lastModifiedDtm;
    }

    public void setLastModifiedDtm(Date lastModifiedDtm) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    public Set<EplProdSpecHandlingAssocDo> getEplProdSpecHandlingAssocs() {
        return this.eplProdSpecHandlingAssocs;
    }

    public void setEplProdSpecHandlingAssocs(Set<EplProdSpecHandlingAssocDo> eplProdSpecHandlingAssocs) {
        this.eplProdSpecHandlingAssocs = eplProdSpecHandlingAssocs;
    }

    /**
     * toString
     * 
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("specialHandlingCode").append("='").append(getSpecialHandlingCode()).append("' ");
        buffer.append("specialHandlingDescription").append("='").append(getSpecialHandlingDescription()).append("' ");
        buffer.append("specialHandlingStatus").append("='").append(getItemStatus()).append("' ");
        buffer.append("revisionNumber").append("='").append(getRevisionNumber()).append("' ");
        buffer.append("inactivationDate").append("='").append(getInactivationDate()).append("' ");
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
        
        if (!(other instanceof EplSpecialHandlingDo)) {
            return false;
        }
        
        EplSpecialHandlingDo castOther = (EplSpecialHandlingDo) other;

        return ((this.getEplId() == castOther.getEplId()) || (this.getEplId() != null && castOther.getEplId() != null && this
            .getEplId().equals(castOther.getEplId())))
            && ((this.getSpecialHandlingCode() == castOther.getSpecialHandlingCode()) || (this.getSpecialHandlingCode() != null
                && castOther.getSpecialHandlingCode() != null && this.getSpecialHandlingCode().equals(
                    castOther.getSpecialHandlingCode())))
            && ((this.getSpecialHandlingDescription() == castOther.getSpecialHandlingDescription()) || (this
                .getSpecialHandlingDescription() != null
                && castOther.getSpecialHandlingDescription() != null && this.getSpecialHandlingDescription().equals(
                    castOther.getSpecialHandlingDescription())))
            && ((this.getItemStatus() == castOther.getItemStatus()) || (this
                .getItemStatus() != null
                && castOther.getItemStatus() != null && this.getItemStatus().equals(
                    castOther.getItemStatus())))
            && ((this.getRevisionNumber() == castOther.getRevisionNumber()) || (this.getRevisionNumber() != null
                && castOther.getRevisionNumber() != null && this.getRevisionNumber().equals(castOther.getRevisionNumber())))
            && ((this.getInactivationDate() == castOther.getInactivationDate()) || (this.getInactivationDate() != null
                && castOther.getInactivationDate() != null && this.getInactivationDate().equals(
                    castOther.getInactivationDate())))
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

        result = 37 * result + (getEplId() == null ? 0 : this.getEplId().hashCode());
        result = 37 * result + (getSpecialHandlingCode() == null ? 0 : this.getSpecialHandlingCode().hashCode());
        result = 37 * result
            + (getSpecialHandlingDescription() == null ? 0 : this.getSpecialHandlingDescription().hashCode());
        result = 37 * result + (getItemStatus() == null ? 0 : this.getItemStatus().hashCode());
        result = 37 * result + (getRevisionNumber() == null ? 0 : this.getRevisionNumber().hashCode());
        result = 37 * result + (getInactivationDate() == null ? 0 : this.getInactivationDate().hashCode());
        result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
        result = 37 * result + (getCreatedDtm() == null ? 0 : this.getCreatedDtm().hashCode());
        result = 37 * result + (getLastModifiedBy() == null ? 0 : this.getLastModifiedBy().hashCode());
        result = 37 * result + (getLastModifiedDtm() == null ? 0 : this.getLastModifiedDtm().hashCode());

        return result;
    }

}
