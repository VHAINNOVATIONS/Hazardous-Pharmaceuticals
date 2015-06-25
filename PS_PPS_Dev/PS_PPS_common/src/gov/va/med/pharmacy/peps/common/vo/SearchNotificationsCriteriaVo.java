/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * search criteria for notifications
 */
public class SearchNotificationsCriteriaVo extends ValueObject {

    private static final long serialVersionUID = 1L;
    private boolean isApproved;
    private boolean isRejected;
    private boolean isInactivated;
    private boolean isModified;
    private boolean isViewed;
    private boolean isHidden;
    private boolean isAll;
    private boolean isUnread;
    private boolean isOnlyLocal;
    private boolean wasDateChanged;

    private Date searchNotificationStartDate;
    private Date searchNotificationEndDate;
    private UserVo user;

    /**
     * isApproved
     * @return isApproved property
     */
    public boolean isApproved() {

        return isApproved;
    }

    /**
     * setApproved
     * @param approved isApproved property
     */
    public void setApproved(boolean approved) {

        this.isApproved = approved;
    }

    /**
     * isRejected
     * @return isRejected property
     */
    public boolean isRejected() {

        return isRejected;
    }

    /**
     * setRejected
     * @param rejected isRejected property
     */
    public void setRejected(boolean rejected) {

        this.isRejected = rejected;
    }

    /**
     * isInactivated
     * @return isInactivated property
     */
    public boolean isInactivated() {

        return isInactivated;
    }

    /**
     * setInactivated
     * @param inactivated isInactivated property
     */
    public void setInactivated(boolean inactivated) {

        this.isInactivated = inactivated;
    }

    /**
     * isModified
     * @return isModified property
     */
    public boolean isModified() {

        return isModified;
    }

    /**
     * setModified
     * @param modified isModified property
     */
    public void setModified(boolean modified) {

        this.isModified = modified;
    }

    /**
     * isViewed
     * @return isViewed property
     */
    public boolean isViewed() {

        return isViewed;
    }

    /**
     * setViewed
     * @param viewed isViewed property
     */
    public void setViewed(boolean viewed) {

        this.isViewed = viewed;
    }

    /**
     * isHidden
     * @return isHidden property
     */
    public boolean isHidden() {

        return isHidden;
    }

    /**
     * setHidden
     * @param hidden isHidden property
     */
    public void setHidden(boolean hidden) {

        this.isHidden = hidden;
    }

    /**
     * isAll
     * @return isAll property
     */
    public boolean isAll() {

        return isAll;
    }

    /**
     * setAll
     * @param all isAll property
     */
    public void setAll(boolean all) {

        this.isAll = all;
    }

    /**
     * getSearchNotificationStartDate
     * @return searchNotificationStartDate property
     */
    public Date getSearchNotificationStartDate() {

        return searchNotificationStartDate;
    }

    /**
     * setSearchNotificationStartDate
     * @param searchNotificationStartDate searchNotificationStartDate property
     */
    public void setSearchNotificationStartDate(Date searchNotificationStartDate) {

        this.searchNotificationStartDate = searchNotificationStartDate;
    }

    /**
     * getSearchNotificationEndDate
     * @return searchNotificationEndDate property
     */
    public Date getSearchNotificationEndDate() {

        return searchNotificationEndDate;
    }

    /**
     * setSearchNotificationEndDate
     * @param searchNotificationEndDate searchNotificationEndDate property
     */
    public void setSearchNotificationEndDate(Date searchNotificationEndDate) {

        this.searchNotificationEndDate = searchNotificationEndDate;
    }

    /**
     * getUser
     * 
     * @return user initiating search
     */
    public UserVo getUser() {

        return user;
    }

    /**
     * setUser
     * 
     * @param user initiating search
     */
    public void setUser(UserVo user) {

        this.user = user;
    }

    /**
     * isUnread
     * @return isUnread property
     */
    public boolean isUnread() {

        return isUnread;
    }

    /**
     * setUnread
     * @param unread isUnread property
     */
    public void setUnread(boolean unread) {

        this.isUnread = unread;
    }

    /**
     * isOnlyLocal
     * @return isOnlyLocal property
     */
    public boolean isOnlyLocal() {

        return isOnlyLocal;
    }

    /**
     * setOnlyLocal
     * @param onlyLocal isOnlyLocal property
     */
    public void setOnlyLocal(boolean onlyLocal) {

        this.isOnlyLocal = onlyLocal;
    }

    /**
     * isWasDateChanged
     * @return wasDateChanged property
     */
    public boolean isWasDateChanged() {

        return wasDateChanged;
    }

    /**
     * setWasDateChanged
     * @param wasDateChanged wasDateChanged property
     */
    public void setWasDateChanged(boolean wasDateChanged) {

        this.wasDateChanged = wasDateChanged;
    }
}
