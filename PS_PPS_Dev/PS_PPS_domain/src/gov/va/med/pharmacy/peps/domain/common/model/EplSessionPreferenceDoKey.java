/**
 * Source file created in 2008 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.domain.common.model;


// Generated Jul 10, 2008 10:44:54 AM by Hibernate Tools 3.2.0.beta8



/**
 * EplSessionPreferenceDoKey generated by hbm2java
 * @hibernate.class
 */
public class EplSessionPreferenceDoKey implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 1L;
    
    private Long userIdFk;
    private String prefName;

    // Constructors

    /** default constructor */
    
    public EplSessionPreferenceDoKey() {
    }

    /** full constructor */
    
    public EplSessionPreferenceDoKey(Long userIdFk, String prefName) {
        this.userIdFk = userIdFk;
        this.prefName = prefName;
    }

    // Property accessors
    public Long getUserIdFk() {
        return this.userIdFk;
    }

    public void setUserIdFk(Long userIdFk) {
        this.userIdFk = userIdFk;
    }

    public String getPrefName() {
        return this.prefName;
    }

    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        
        if ((other == null)) {
            return false;
        }
        
        if (!(other instanceof EplSessionPreferenceDoKey)) {
            return false;
        }
        
        EplSessionPreferenceDoKey castOther = (EplSessionPreferenceDoKey) other;

        return ((this.getUserIdFk() == castOther.getUserIdFk()) || (this.getUserIdFk() != null
            && castOther.getUserIdFk() != null && this.getUserIdFk().equals(castOther.getUserIdFk())))
            && ((this.getPrefName() == castOther.getPrefName()) || (this.getPrefName() != null
                && castOther.getPrefName() != null && this.getPrefName().equals(castOther.getPrefName())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getUserIdFk() == null ? 0 : this.getUserIdFk().hashCode());
        result = 37 * result + (getPrefName() == null ? 0 : this.getPrefName().hashCode());
        
        return result;
    }

}
