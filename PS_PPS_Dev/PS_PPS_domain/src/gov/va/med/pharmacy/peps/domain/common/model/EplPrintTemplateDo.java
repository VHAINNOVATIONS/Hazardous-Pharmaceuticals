/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Oct 15, 2008 11:54:39 AM by Hibernate Tools 3.2.0.beta8

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * EplPrintTemplateDo generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplPrintTemplateDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements
    java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 1L;
    
    private Long eplId;
    private String templateLocation;
    private String templateName;
    private Set<EplPrintFieldDo> eplPrintFields = new HashSet<EplPrintFieldDo>(0);
    private Set<EplSearchTemplateDo> eplSearchTemplates = new HashSet<EplSearchTemplateDo>(0);

    // Constructors

    /** default constructor */
    
    public EplPrintTemplateDo() {
    }

    /** minimal constructor */
    
    public EplPrintTemplateDo(Long eplId, String templateLocation, String templateName, String createdBy, Date createdDtm) {
        this.eplId = eplId;
        this.templateLocation = templateLocation;
        this.templateName = templateName;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
    }

    /** full constructor */
    
    public EplPrintTemplateDo(Long eplId, String templateLocation, String templateName, String createdBy, Date createdDtm,
                              String lastModifiedBy, Date lastModifiedDtm, Set<EplPrintFieldDo> eplPrintFields,
                              Set<EplSearchTemplateDo> eplSearchTemplates) {
        this.eplId = eplId;
        this.templateLocation = templateLocation;
        this.templateName = templateName;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
        this.setLastModifiedBy(lastModifiedBy);
        this.setLastModifiedDtm(lastModifiedDtm);
        this.eplPrintFields = eplPrintFields;
        this.eplSearchTemplates = eplSearchTemplates;
    }

    // Property accessors
    public Long getEplId() {
        return this.eplId;
    }

    public void setEplId(Long eplId) {
        this.eplId = eplId;
    }

    public String getTemplateLocation() {
        return this.templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Set<EplPrintFieldDo> getEplPrintFields() {
        return this.eplPrintFields;
    }

    public void setEplPrintFields(Set<EplPrintFieldDo> eplPrintFields) {
        this.eplPrintFields = eplPrintFields;
    }

    public Set<EplSearchTemplateDo> getEplSearchTemplates() {
        return this.eplSearchTemplates;
    }

    public void setEplSearchTemplates(Set<EplSearchTemplateDo> eplSearchTemplates) {
        this.eplSearchTemplates = eplSearchTemplates;
    }

    /**
     * toString
     * 
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("templateLocation").append("='").append(getTemplateLocation()).append("' ");
        buffer.append("templateName").append("='").append(getTemplateName()).append("' ");
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
        
        if (!(other instanceof EplPrintTemplateDo)) {
            return false;
        }
        
        EplPrintTemplateDo castOther = (EplPrintTemplateDo) other;

        return ((this.getEplId() == castOther.getEplId()) || (this.getEplId() != null && castOther.getEplId() != null && this
            .getEplId().equals(castOther.getEplId())))
            && ((this.getTemplateLocation() == castOther.getTemplateLocation()) || (this.getTemplateLocation() != null
                && castOther.getTemplateLocation() != null && this.getTemplateLocation().equals(
                    castOther.getTemplateLocation())))
            && ((this.getTemplateName() == castOther.getTemplateName()) || (this.getTemplateName() != null
                && castOther.getTemplateName() != null && this.getTemplateName().equals(castOther.getTemplateName())))
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
        result = 37 * result + (getTemplateLocation() == null ? 0 : this.getTemplateLocation().hashCode());
        result = 37 * result + (getTemplateName() == null ? 0 : this.getTemplateName().hashCode());
        result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
        result = 37 * result + (getCreatedDtm() == null ? 0 : this.getCreatedDtm().hashCode());
        result = 37 * result + (getLastModifiedBy() == null ? 0 : this.getLastModifiedBy().hashCode());
        result = 37 * result + (getLastModifiedDtm() == null ? 0 : this.getLastModifiedDtm().hashCode());

        return result;
    }

}
