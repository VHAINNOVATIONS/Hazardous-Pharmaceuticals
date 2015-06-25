/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Hold data concerning the user, its roles, etc.
 */
public class UserVo extends ValueObject {

    /** DEFAULT_TABLE_SIZE */
    public static final int DEFAULT_TABLE_SIZE = 10;
    
    /**
     * DEFAULT_HOME_PAGE
     */
    public static final String DEFAULT_HOME_PAGE = "H";
    
    /**
     * SEARCH_HOME_PAGE
     */
    public static final String SEARCH_HOME_PAGE = "S";

    /** DEFAULT_FDB_RX_OTC */
    public static final String DEFAULT_FDB_RX_OTC = PPSConstants.BOTH;
    
    /** DEFAULT_FDB_NAME_TYPE */
    public static final String DEFAULT_FDB_NAME_TYPE = PPSConstants.BOTH;
    
    /** DEFAULT_FDB_PACKAGED_DRUG */
    public static final String DEFAULT_FDB_PACKAGED_DRUG = PPSConstants.BOTH;
    
    /** DEFAULT_FDB_PHONETIC_SEARCH */
    public static final String DEFAULT_FDB_PHONETIC_SEARCH = "LITERAL";
    
    /** DEFAULT_FDB_SEARCH_METHODS */
    public static final String DEFAULT_FDB_SEARCH_METHODS = "BEGINS_WITH_LITERAL";
    
    /** DEFAULT_FDB_STATUS_CODE_ACTIVE */
    public static final String DEFAULT_FDB_STATUS_CODE_ACTIVE = "true";
    
    private static final long serialVersionUID = 1L;

    private Long id = -1L;
    private Long duz;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isOnlySecondReviewer;
    private boolean rolePsrFlag;
    private boolean rolePnmFlag;
    private boolean rolePnsFlag;
    private boolean roleMigFlag;
    private boolean rolePsrApvdFlag;
    private boolean rolePnmApvdFlag;
    private boolean rolePnsApvdFlag;
    private boolean roleMigApvdFlag;
    private String password;
    private String location;
    private String stationNumber;
    private String hostName;
    private List<Role> roles = new ArrayList<Role>();
    private List<NotificationType> notifications = new ArrayList<NotificationType>();
    private Map<SessionPreferenceType, String> sessionPreferences = new HashMap<SessionPreferenceType, String>();
    private List<PreferenceGroupVo> preferenceGroup = new ArrayList<PreferenceGroupVo>();

    /**
     * Default the "add.view" preference to "advancedAdd".
     */
    public UserVo() {

        super();
    }

    /**
     * Create the system user for the given environment.
     * 
     * @param environmentUtility EnvironmentUtility from which to retrieve environment information.
     * @return system user
     */
    public static UserVo getSystemUser(EnvironmentUtility environmentUtility) {
        UserVo systemUser = new UserVo();
        systemUser.setUsername("SYSTEM USERNAME");
        systemUser.setFirstName(environmentUtility.getSiteNumber());
        systemUser.setLastName("SYSTEM");
        systemUser.setLocation(environmentUtility.getSiteNumber());
        systemUser.setStationNumber(environmentUtility.getSiteNumber());
        systemUser.setId(-1l); // flag user as bogus

        if (environmentUtility.isLocal()) {
            systemUser.addRole(Role.LOCAL_SERVICE_MANAGER);
        } else {
            systemUser.addRole(Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER
        }

        return systemUser;
    }

    /**
     * getIsOnlySecondReviewer
     *
     * @return boolean
     */
    public boolean getIsOnlySecondReviewer() {
        return isOnlySecondReviewer;
    }
    
    
    /**
     * Test if the List of roles contains the given role.
     * 
     * @param role String value of role
     * @return boolean true if the user is in the given role, else false
     * @see List#contains(Object)
     */
    public boolean hasRole(Role role) {
        return getRoles().contains(role);
    }

    /**
     * Check to see if the user has any of the given roles 
     * 
     * @param roleArray Role[] value of roles
     * @return boolean true if the user is in the given role, else false
     * @see List#contains(Object)
     */
    public boolean hasAnyRole(Role[] roleArray) {

        for (Role role : roleArray) {
            if (hasRole(role)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Test if the List of roles contains only the given role.
     * 
     * @param role String value of role
     * @return boolean true if the user is in the given role, else false
     * @see List#contains(Object)
     */
    public boolean hasOnlyRole(Role role) {
        return getRoles().size() == 1 && getRoles().contains(role);
    }

    /**
     * Test if the List of roles contains the local manager role.
     * 
     * @return boolean true if the user has the local manager role
     * @see #hasRole(Role)
     */
    public boolean isLocalManager() {
        boolean local = false;

        for (Role role : getRoles()) {
            if (role.isLocalManager()) {
                local = true;
            }
        }

        return local;
    }

    /**
     * Test if the List of roles contains the national manager role.
     * 
     * @return boolean true if the user has the national manager role
     * @see #hasRole(Role)
     */
    public boolean isNationalManager() {

        return hasRole(Role.PSS_PPSN_SUPERVISOR) || hasRole(Role.PSS_PPSN_MANAGER);

    }

    /**
     * Test if the List of roles contains the second reviewer role but not the national manager role
     * 
     * @return boolean true if the user has the national manager role
     * @see #hasRole(Role)
     */
    public boolean isSecondReviewer() {
        boolean national = isNationalManager();

        return !national && hasRole(Role.PSS_PPSN_SECOND_APPROVER /*SECOND_REVIEWER*/);
    }

    /**
     * Test if the List of roles only contains the second reviewer role.
     * 
     * @return boolean true if the user has only the second reviewer role
     * @see #hasOnlyRole(Role)
     */
    public boolean isOnlySecondReviewer() {
        
        /*SECOND_REVIEWER*/
        isOnlySecondReviewer = hasOnlyRole(Role.PSS_PPSN_SECOND_APPROVER);

        return isOnlySecondReviewer;
    }

    /**
     * Test if the List of roles contains the local or national manager role.
     * 
     * @return boolean true if the user has the local or national manager role
     * @see #hasRole(Role)
     */
    public boolean isEitherManager() {

        return hasRole(Role.PSS_PPSN_SUPERVISOR) || hasRole(Role.PSS_PPSN_MANAGER);

    }

    /**
     * Test if the List of roles contains the national manager role or the PSR role
     * 
     * @return boolean true if the user has the national manager role or the PSR role
     * @see #hasRole(Role)
     */
    public boolean canApprove() {

        return hasRole(Role.PSS_PPSN_SECOND_APPROVER) //SECOND_REVIEWER
               || hasRole(Role.PSS_PPSN_MANAGER)
               || hasRole(Role.PSS_PPSN_MANAGER); //NATIONAL_SERVICE_MANAGER

    }

    /**
     * Test if the List of roles contains service level privileges.
     * 
     * @return boolean true if the user has service level privileges
     * @see #hasRole(Role)
     */
    public boolean isAdministrativeLevel() {
        boolean serviceLevel = false;

        for (Role role : getRoles()) {
            if (role.isAdministrativeLevel()) {
                serviceLevel = true;
            }
        }

        return serviceLevel;
    }

    /**
     * Test if the List of roles contains the read only role.
     * 
     * @return boolean true if the user has the read only role
     * @see #hasRole(Role)
     */
    public boolean isReadOnly() {

        return roles.size() == 1
            && (hasRole(Role.LOCAL_READ_ONLY)
                || hasRole(Role.PSS_PPSN_VIEWER) //NATIONAL_READ_ONLY
                || hasRole(Role.PSS_PPSN_SECOND_APPROVER));
    }

    /**
     * Determine if given Notification Type is set for this user
     * 
     * @param notificationType NotificationType
     * @return true if notification is set
     */
    public boolean isNotificationSet(NotificationType notificationType) {
        boolean set = false;

        if (notifications != null) {
            set = notifications.contains(notificationType);
        }

        return set;
    }

    /**
     * Add the given role to the List of roles.
     * 
     * @param role String role to add
     * @return boolean true if this collection changed as a result of the call
     */
    public boolean addRole(Role role) {
        return getRoles().add(role);
    }

    /**
     * Remove the given role from the List of roles.
     * 
     * @param role String role to remove
     * @return boolean true if this collection changed as a result of the call
     */
    public boolean removeRole(String role) {
        return getRoles().remove(role);
    }

    
    /**
     * getHostName
     *
     * @return hostName property
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * setHostName
     * 
     * @param hostName hostName property
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    /**
     * Description
     *
     * @return firstName property
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Description
     * 
     * @param firstName firstName property
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Description
     * 
     * @return lastName property
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Description
     * 
     * @param lastName lastName property
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Description
     * 
     * @return username property
     */
    public String getUsername() {
        return username;
    }

    /**
     * Description
     * 
     * @param username username property
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Description
     * 
     * @return duz property
     */
    public Long getDuz() {
        return duz;
    }

    /**
     * Description
     * 
     * @param duz duz property
     */
    public void setDuz(Long duz) {
        this.duz = duz;
    }

    /**
     * getRolePsrFlag - second approver
     * 
     * @return rolePsrFlag
     */
    public boolean getRolePsrFlag() {
    
        return rolePsrFlag;
    }

    /**
     * setRolePsrFlag - second approver
     * 
     * @param rolePsrFlag rolePsrFlag
     */
    public void setRolePsrFlag(boolean rolePsrFlag) {
    
        this.rolePsrFlag = rolePsrFlag;
    }

    /**
     * getRolePnmFlag
     * @return rolePnmFlag
     */
    public boolean getRolePnmFlag() {
    
        return rolePnmFlag;
    }

    /**
     * setRolePnmFlag
     * @param rolePnmFlag rolePnmFlag
     */
    public void setRolePnmFlag(boolean rolePnmFlag) {
    
        this.rolePnmFlag = rolePnmFlag;
    }

    /**
     * getRolePnsFlag - supervisor
     * @return rolePnsFlag
     */
    public boolean getRolePnsFlag() {
    
        return rolePnsFlag;
    }

    /**
     * setRolePnsFlag - supervisor
     *
     * @param rolePnsFlag boolean
     */
    public void setRolePnsFlag(boolean rolePnsFlag) {
    
        this.rolePnsFlag = rolePnsFlag;
    }

    /**
     * getRoleMigFlag
     *
     * @return boolean
     */
    public boolean getRoleMigFlag() {
    
        return roleMigFlag;
    }

    /**
     * setRoleMigFlag
     *
     * @param roleMigFlag boolean
     */
    public void setRoleMigFlag(boolean roleMigFlag) {
    
        this.roleMigFlag = roleMigFlag;
    }

    /**
     * getRolePsrApvdFlag
     *
     * @return boolean
     */
    public boolean getRolePsrApvdFlag() {
    
        return rolePsrApvdFlag;
    }

    /**
     * setRolePsrApvdFlag
     *
     * @param rolePsrApvdFlag boolean
     */
    public void setRolePsrApvdFlag(boolean rolePsrApvdFlag) {
    
        this.rolePsrApvdFlag = rolePsrApvdFlag;
    }

    /**
     * getRolePnmApvdFlag
     *
     * @return boolean
     */
    public boolean getRolePnmApvdFlag() {
    
        return rolePnmApvdFlag;
    }

    /**
     * setRolePnmApvdFlag
     *
     * @param rolePnmApvdFlag boolean
     */
    public void setRolePnmApvdFlag(boolean rolePnmApvdFlag) {
    
        this.rolePnmApvdFlag = rolePnmApvdFlag;
    }

    /**
     * getRolePnsApvdFlag - supervisor
     *
     * @return boolean
     */
    public boolean getRolePnsApvdFlag() {
    
        return rolePnsApvdFlag;
    }

    /**
     * setRolePnsApvdFlag - supervisor
     *
     * @param rolePnsApvdFlag boolean
     */
    public void setRolePnsApvdFlag(boolean rolePnsApvdFlag) {
    
        this.rolePnsApvdFlag = rolePnsApvdFlag;
    }

    /**
     * getRoleMigApvdFlag
     *
     * @return boolean
     */
    public boolean getRoleMigApvdFlag() {
    
        return roleMigApvdFlag;
    }

    /**
     * setRoleMigApvdFlag
     *
     * @param roleMigApvdFlag boolean
     */
    public void setRoleMigApvdFlag(boolean roleMigApvdFlag) {
    
        this.roleMigApvdFlag = roleMigApvdFlag;
    }

    /**
     * getPassword
     *
     * @return String
     */
    public String getPassword() {
    
        return password;
    }

    /**
     * setPassword
     *
     * @param password String
     */
    public void setPassword(String password) {
    
        this.password = password;
    }

    /**
     * Description
     * 
     * @return roles property
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Description
     * 
     * @param roles roles property
     */
    public void setRoles(List<Role> roles) {
        this.roles = new ArrayList<Role>();

        if (roles != null && !roles.isEmpty()) {
            this.roles.addAll(roles);
        }
    }

    /**
     * Description
     * 
     * @return location property
     */
    public String getLocation() {
        return location;
    }

    /**
     * Description
     * 
     * @param location location property
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getPreferences for UserVo.
     * 
     * @return preferences property
     */
    public List<NotificationType> getPreferences() {
        if (notifications == null) {
            notifications = new ArrayList<NotificationType>();
        }

        return notifications;
    }

    /**
     * getId for UserVo.
     * 
     * @return id property
     */
    public Long getId() {
        return id;
    }

    /**
     * setId for UserVo.
     * 
     * @param id id property
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getSessionPreferences for UserVo.
     * 
     * @return sessionPreferences property
     */
    public Map<SessionPreferenceType, String> getSessionPreferences() {

        //System.out.println("1 = " + sessionPreferences);
        return sessionPreferences;
    }

    /**
     * Description
     * 
     * @param sessionPreferences sessionPreferences property
     */
    public void setSessionPreferences(Map<SessionPreferenceType, String> sessionPreferences) {

        this.sessionPreferences = sessionPreferences;

    }

    /**
     * Method that returns all notification preferences of this user (including preference groups) without altering the data
     * on the user
     * 
     * @return All notifications
     */
    public List<NotificationType> getAllNotifications() {
        List<NotificationType> ret = new ArrayList<NotificationType>();
        ret.addAll(getNotifications());

        for (PreferenceGroupVo pref : preferenceGroup) {
            PreferenceType prefType = pref.getPreferenceType();

            if (prefType != null) {

                EntityType type = prefType.getEntityType();

                if (pref.isApproved()) {
                    ret.add(type.getApprovedNotification());
                }

                if (pref.isRejected()) {
                    ret.add(type.getRejectedNotification());
                }

                if (pref.isInactivated()) {
                    ret.add(type.getInactivatedNotification());
                }

                if (pref.isModified()) {
                    ret.add(type.getModifiedNotification());
                }
            }

        }

        return ret;
    }

    /**
     * Description
     * 
     * @return notifications property
     */
    public List<NotificationType> getNotifications() {
        return notifications;
    }

    /**
     * Description
     * 
     * @param notifications notifications property
     */
    public void setNotifications(List<NotificationType> notifications) {
        this.notifications = new ArrayList<NotificationType>();

        if (notifications != null && !notifications.isEmpty()) {
            this.notifications.addAll(notifications);
        }
    }

    /**
     * Return a displayable name for this user, in the required format.
     * 
     * @return String The formated text representing this user VO instance.
     */
    public String toDisplayName() {

        // If no user name specified, then this is effectively a null user.
        if (username == null || username.trim().length() <= 0) {
            return "";
        }

        // if no location, then no one has reviewed it yet
        if (location != null && location.length() > 0) {
            return location + ":" + username;
        } else {
            return "???:" + username;
        }
    }

    /**
     * 
     * Getter that calls toDisplayName - used on JSPs
     *
     * @return toDisplayName
     */
    public String getDisplayName() {

        return toDisplayName();
    }

    /**
     * VistA station number logged into.
     * 
     * @return stationNumber property
     */
    public String getStationNumber() {
        return stationNumber;
    }

    /**
     * VistA station number logged into.
     * 
     * @param stationNumber stationNumber property
     */
    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    /**
     * Return the user's table size preference. This is the number of rows the user wants to see in a table before the table
     * begins to "page" (or alternatively the "page size" of the tables).
     * 
     * <p>
     * 
     * Returns the user preference for the table row count, if that for some reason fails to parse as an integer,
     * it will return
     * #DEFAULT_TABLE_SIZE
     * 
     * @return table size preference
     */
    public int getTableSizePreference() {
        int tableSize = DEFAULT_TABLE_SIZE;

        String tableSizePreference = getSessionPreferences().get(SessionPreferenceType.TABLE_ROW_COUNT);

        if (StringUtils.isNumeric(tableSizePreference)) {
            tableSize = Integer.parseInt(tableSizePreference);
        }

        return tableSize;
    }

    /**
     * Return the user's table size preference. This is the number of rows the user wants to see in a table before the table
     * begins to "page" (or alternatively the "page size" of the tables).
     * <p>
     * 
     * Returns the user preference for table row count, if that for some reason fails to parse as an integer, it will return
     * #DEFAULT_TABLE_SIZE
     * 
     * @return table size preference
     */
    public String getHomePageDefaultPreference() {

        String homePagePreference = getSessionPreferences().get(SessionPreferenceType.HOME_PAGE_DEFAULT);

        if (StringUtils.equalsIgnoreCase(SEARCH_HOME_PAGE, homePagePreference)) {
            return homePagePreference;
        } else {
            return DEFAULT_HOME_PAGE;
        }

    }
    
    /**
     * getHomePageDefaultUrl
     *
     * @return String
     */
    public String getHomePageDefaultUrl() {
        String homePagePref = getHomePageDefaultPreference();
        
        if (homePagePref.equals(UserVo.DEFAULT_HOME_PAGE)) {
            return "home.go";
        } else {
            return "searchItems.go";
        }
    }
    
    /**
     * Both the date and time format preferences must be set!
     * <p>
     * They are set by default and must be saved together in all cases. This should not throw a null pointer exception in any
     * case.
     * 
     * @return String data format
     */
    public String getDateTimeFormatPreference() {

        return getDateFormatPreference() + " " + getTimeFormatPreference();
    }

    /**
     * The date format preference must be set!
     * <p>
     * It is set by default and must be saved in all cases. This should not throw a null pointer exception in any case.
     * 
     * @return String date format
     */
    public String getDateFormatPreference() {
        DateFormat dateFormat = DateFormat.valueOf(sessionPreferences.get(SessionPreferenceType.LONG_DATE_FORMAT));

        return dateFormat.getFormat();
    }

    /**
     * The time format preference must be set!
     * <p>
     * It is set by default and must be saved in all cases. This should not throw a null pointer exception in any case.
     * 
     * @return String date format
     */
    public String getTimeFormatPreference() {
        TimeFormat timeFormat = TimeFormat.valueOf(sessionPreferences.get(SessionPreferenceType.TIME_FORMAT));

        return timeFormat.getFormat();
    }

    /**
     * Returns the user's DrugClassSortPreference
     * 
     * @return true if 'CODE' false if 'Classification'
     */
    public boolean getDrugClassSortPreference() {
        String drugClassSortPreference = getSessionPreferences().get(SessionPreferenceType.DRUG_CLASS_SORT);

        boolean ret = true;

        if (drugClassSortPreference != null) {
            ret = DrugClassSortPreference.valueOf(drugClassSortPreference).isCode();
        }

        return ret;
    }

    /**
     * Description
     * 
     * @return preferenceGroup property
     */
    public List<PreferenceGroupVo> getPreferenceGroup() {
        return preferenceGroup;
    }

    /**
     * Description
     * 
     * @param preferenceGroup preferenceGroup property
     */
    public void setPreferenceGroup(List<PreferenceGroupVo> preferenceGroup) {
        this.preferenceGroup = new ArrayList<PreferenceGroupVo>();

        if (preferenceGroup != null && !preferenceGroup.isEmpty()) {
            this.preferenceGroup.addAll(preferenceGroup);
        }
    }

    /**
     * Rolls the domain preferences stored in the preferenceGroup into preferences to be stored by the database
     */
    public void expandPreferences() {

        for (PreferenceGroupVo pref : preferenceGroup) {
            PreferenceType prefType = pref.getPreferenceType();

            if (prefType != null) {

                EntityType type = prefType.getEntityType();

                if (pref.isApproved()) {
                    notifications.add(type.getApprovedNotification());
                }

                if (pref.isRejected()) {
                    notifications.add(type.getRejectedNotification());
                }

                if (pref.isInactivated()) {
                    notifications.add(type.getInactivatedNotification());
                }

                if (pref.isModified()) {
                    notifications.add(type.getModifiedNotification());
                }
            }

        }
    }

    /**
     * Removes any domain preferences from the preference list and generates a domain group for them
     */
    public void generatePreferenceGroups() {

        List<PreferenceGroupVo> builtList = new ArrayList<PreferenceGroupVo>();

        for (EntityType type : EntityType.domains()) {
            PreferenceGroupVo group = new PreferenceGroupVo();
            group.setPreferenceType(PreferenceType.toPreferenceType(type));
            boolean wasSet = false;

            if (isNotificationSet(type.getApprovedNotification())) {
                group.setApproved(true);
                wasSet = true;
                notifications.remove(type.getApprovedNotification());
            }

            if (isNotificationSet(type.getInactivatedNotification())) {
                group.setInactivated(true);
                wasSet = true;
                notifications.remove(type.getInactivatedNotification());
            }

            if (isNotificationSet(type.getModifiedNotification())) {
                group.setModified(true);
                wasSet = true;
                notifications.remove(type.getModifiedNotification());
            }

            if (isNotificationSet(type.getRejectedNotification())) {
                group.setRejected(true);
                wasSet = true;
                notifications.remove(type.getRejectedNotification());
            }

            if (wasSet) {
                builtList.add(group);
            }
        }

        setPreferenceGroup(builtList);
    }

}
