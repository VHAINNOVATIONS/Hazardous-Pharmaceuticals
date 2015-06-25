/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types available to an NotificationStatusType
 */
public enum NotificationStatusType {

    /** APPROVED */
    APPROVED,

    /** REJECTED */
    REJECTED,

    /** INACTIVATED */
    INACTIVATED,

    /** MODIFIED */
    MODIFIED,

    /** VIEWED */
    VIEWED,

    /** READONLY */
    READONLY,

    /** NEWINSTANCE */
    NEWINSTANCE,

    /** HIDDEN */
    HIDDEN,

    /** SYSTEM */
    SYSTEM,

    /** VIEWEDHIDDEN */
    VIEWEDHIDDEN;

    /**
     * isApproved
     * 
     * @return boolean true if this NotificationStatusTypei
     */
    public boolean isApproved() {

        return APPROVED.equals(this);
    }

    /**
     * isReadOnly
     * 
     * @return boolean true if this NotificationStatusTypei
     */
    public boolean isReadOnly() {

        return READONLY.equals(this);
    }

    /**
     * isNewInstance
     * 
     * @return boolean true if this NotificationStatusTypei
     */
    public boolean isNewInstance() {

        return NEWINSTANCE.equals(this);
    }

    /**
     * isRejected
     * 
     * @return boolean true if this NotificationStatusType
     */
    public boolean isRejected() {

        return REJECTED.equals(this);
    }

    /**
     * isInactivated
     * 
     * @return boolean true if this NotificationStatusType
     */
    public boolean isInactivated() {

        return INACTIVATED.equals(this);
    }

    /**
     * isModified
     * 
     * @return boolean true if this NotificationStatusType
     */
    public boolean isModified() {

        return MODIFIED.equals(this);
    }

    /**
     * isViewed
     * 
     * @return boolean true if this NotificationStatusType
     */
    public boolean isViewed() {

        return VIEWED.equals(this);
    }

    /**
     * isHidden
     * 
     * @return boolean true if this NotificationStatusType
     */
    public boolean isHidden() {

        return HIDDEN.equals(this);
    }

    /**
     * isViewedHidden
     * 
     * @return boolean true if this NotificationStatusTypei
     */
    public boolean isViewedHidden() {

        return VIEWEDHIDDEN.equals(this);
    }
}
