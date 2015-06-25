/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Oct 15, 2008 11:54:39 AM by Hibernate Tools 3.2.0.beta8



/**
 * EplPrintFieldDoKey generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplPrintFieldDoKey implements java.io.Serializable {

    // Fields
    
    private static final long serialVersionUID = 1L;

    private Long eplIdPrintTemplateFk;
    private String printFieldName;

    // Constructors

    /** default constructor */

    public EplPrintFieldDoKey() {
    }

    /** full constructor */

    public EplPrintFieldDoKey(Long eplIdPrintTemplateFk, String printFieldName) {
        this.eplIdPrintTemplateFk = eplIdPrintTemplateFk;
        this.printFieldName = printFieldName;
    }

    // Property accessors
    public Long getEplIdPrintTemplateFk() {
        return this.eplIdPrintTemplateFk;
    }

    public void setEplIdPrintTemplateFk(Long eplIdPrintTemplateFk) {
        this.eplIdPrintTemplateFk = eplIdPrintTemplateFk;
    }

    public String getPrintFieldName() {
        return this.printFieldName;
    }

    public void setPrintFieldName(String printFieldName) {
        this.printFieldName = printFieldName;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }

        if ((other == null)) {
            return false;
        }

        if (!(other instanceof EplPrintFieldDoKey)) {
            return false;
        }

        EplPrintFieldDoKey castOther = (EplPrintFieldDoKey) other;

        return ((this.getEplIdPrintTemplateFk() == castOther.getEplIdPrintTemplateFk()) || (this.getEplIdPrintTemplateFk() != null
            && castOther.getEplIdPrintTemplateFk() != null && this.getEplIdPrintTemplateFk().equals(
                castOther.getEplIdPrintTemplateFk())))
            && ((this.getPrintFieldName() == castOther.getPrintFieldName()) || (this.getPrintFieldName() != null
                && castOther.getPrintFieldName() != null && this.getPrintFieldName().equals(castOther.getPrintFieldName())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getEplIdPrintTemplateFk() == null ? 0 : this.getEplIdPrintTemplateFk().hashCode());
        result = 37 * result + (getPrintFieldName() == null ? 0 : this.getPrintFieldName().hashCode());
        
        return result;
    }

}