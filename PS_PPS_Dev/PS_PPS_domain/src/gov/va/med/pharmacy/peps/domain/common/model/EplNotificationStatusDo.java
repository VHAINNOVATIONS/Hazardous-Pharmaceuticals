/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


import java.util.Date;


/**
 * EplNotificationStatusDo generated by hbm2java
 * 
 * @hibernate.class
 */
public class EplNotificationStatusDo extends gov.va.med.pharmacy.peps.domain.common.model.DataObject implements
    java.io.Serializable {
   

    // Fields
    public static final String STATUS = "status";
    public static final String EPL_USER = "eplUser";

    private static final long serialVersionUID = 1L;
    private EplNotificationStatusDoKey key;
    private String status;
    private EplNotificationDo eplNotification;
    private EplUserDo eplUser;

    // Constructors

    /** default constructor */

    public EplNotificationStatusDo() {
    }

    /** minimal constructor */

    public EplNotificationStatusDo(EplNotificationStatusDoKey key, String status, String createdBy, Date createdDtm) {
        this.key = key;
        this.status = status;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
    }

    /** full constructor */

    public EplNotificationStatusDo(EplNotificationStatusDoKey key, String status, String createdBy, Date createdDtm,
                                   String lastModifiedBy, Date lastModifiedDtm, EplNotificationDo eplNotification,
                                   EplUserDo eplUser) {
        this.key = key;
        this.status = status;
        this.setCreatedBy(createdBy);
        this.setCreatedDtm(createdDtm);
        this.setLastModifiedBy(lastModifiedBy);
        this.setLastModifiedDtm(lastModifiedDtm);
        this.eplNotification = eplNotification;
        this.eplUser = eplUser;
    }

    // Property accessors
    public EplNotificationStatusDoKey getKey() {
        return this.key;
    }

    public void setKey(EplNotificationStatusDoKey key) {
        this.key = key;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EplNotificationDo getEplNotification() {
        return this.eplNotification;
    }

    public void setEplNotification(EplNotificationDo eplNotification) {
        this.eplNotification = eplNotification;
    }

    public EplUserDo getEplUser() {
        return this.eplUser;
    }

    public void setEplUser(EplUserDo eplUser) {
        this.eplUser = eplUser;
    }

    /**
     * toString
     * 
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("status").append("='").append(getStatus()).append("' ");
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

        if (!(other instanceof EplNotificationStatusDo)) {
            return false;
        }

        EplNotificationStatusDo castOther = (EplNotificationStatusDo) other;

        return ((this.getKey() == castOther.getKey()) || (this.getKey() != null && castOther.getKey() != null && this
            .getKey().equals(castOther.getKey())))
            && ((this.getStatus() == castOther.getStatus()) || (this.getStatus() != null && castOther.getStatus() != null && this
                .getStatus().equals(castOther.getStatus())))
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
        result = 37 * result + (getStatus() == null ? 0 : this.getStatus().hashCode());
        result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
        result = 37 * result + (getCreatedDtm() == null ? 0 : this.getCreatedDtm().hashCode());
        result = 37 * result + (getLastModifiedBy() == null ? 0 : this.getLastModifiedBy().hashCode());
        result = 37 * result + (getLastModifiedDtm() == null ? 0 : this.getLastModifiedDtm().hashCode());

        return result;
    }

}