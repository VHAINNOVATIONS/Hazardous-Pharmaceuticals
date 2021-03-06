/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Jun 13, 2008 1:48:51 PM by Hibernate Tools 3.2.0.beta8



/**
 * EplPackageUseAssocDoKey generated by hbm2java
 * @hibernate.class
 */
public class EplPackageUseAssocDoKey implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 1L;
    
    private Long eplIdPackageUseFk;
    private Long eplIdLocalMedRouteFk;

    // Constructors

    /** default constructor */
    
    public EplPackageUseAssocDoKey() {
    }

    /** full constructor */
    
    public EplPackageUseAssocDoKey(Long eplIdPackageUseFk, Long eplIdLocalMedRouteFk) {
        this.eplIdPackageUseFk = eplIdPackageUseFk;
        this.eplIdLocalMedRouteFk = eplIdLocalMedRouteFk;
    }

    // Property accessors
    public Long getEplIdPackageUseFk() {
        return this.eplIdPackageUseFk;
    }

    public void setEplIdPackageUseFk(Long eplIdPackageUseFk) {
        this.eplIdPackageUseFk = eplIdPackageUseFk;
    }

    public Long getEplIdLocalMedRouteFk() {
        return this.eplIdLocalMedRouteFk;
    }

    public void setEplIdLocalMedRouteFk(Long eplIdLocalMedRouteFk) {
        this.eplIdLocalMedRouteFk = eplIdLocalMedRouteFk;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        
        if ((other == null)) {
            return false;
        }
        
        if (!(other instanceof EplPackageUseAssocDoKey)) {
            return false;
        }
        
        EplPackageUseAssocDoKey castOther = (EplPackageUseAssocDoKey) other;

        return ((this.getEplIdPackageUseFk() == castOther.getEplIdPackageUseFk()) || (this.getEplIdPackageUseFk() != null
            && castOther.getEplIdPackageUseFk() != null && this.getEplIdPackageUseFk().equals(
                castOther.getEplIdPackageUseFk())))
            && ((this.getEplIdLocalMedRouteFk() == castOther.getEplIdLocalMedRouteFk()) || (this.getEplIdLocalMedRouteFk() != null
                && castOther.getEplIdLocalMedRouteFk() != null && this.getEplIdLocalMedRouteFk().equals(
                    castOther.getEplIdLocalMedRouteFk())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getEplIdPackageUseFk() == null ? 0 : this.getEplIdPackageUseFk().hashCode());
        result = 37 * result + (getEplIdLocalMedRouteFk() == null ? 0 : this.getEplIdLocalMedRouteFk().hashCode());
        
        return result;
    }

}
