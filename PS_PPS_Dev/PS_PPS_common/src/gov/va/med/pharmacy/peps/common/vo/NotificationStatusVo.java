/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a NotificationStatusVo.
 * 
 */
public class NotificationStatusVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private NotificationStatusType status;
    private UserVo user;

    /**
     * getUser
     * 
     * @return user property
     */
    public UserVo getUser() {
        return user;
    }

    /**
     * setUser
     * 
     * @param user user property
     */
    public void setUser(UserVo user) {
        this.user = user;
    }

    /**
     * getStatus
     * 
     * @return status property
     */
    public NotificationStatusType getStatus() {
        return status;
    }

    /**
     * setStatus
     * 
     * @param status status property
     */
    public void setStatus(NotificationStatusType status) {
        this.status = status;
    }
}
