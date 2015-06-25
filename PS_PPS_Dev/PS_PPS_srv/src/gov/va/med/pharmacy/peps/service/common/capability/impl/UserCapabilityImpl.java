/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.exception.AuthenticationException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.DateFormat;
import gov.va.med.pharmacy.peps.common.vo.DrugClassSortPreference;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.NotificationType;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.TimeFormat;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.UserDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.UserCapability;


/**
 * Perform security actions on a user
 */
public class UserCapabilityImpl implements UserCapability {

    private EnvironmentUtility environmentUtility;
    private UserDomainCapability userDomainCapability;
    private SeqNumDomainCapability seqNumDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;

    /**
     * Retrieve the fields this user is allowed to edit. Field authorization takes into account the environment the
     * application is running in (Local vs. National), the user's roles, the state of the given object, and the current
     * values of the data fields.
     * 
     * @param user UserVo for which to retrieve authorized fields
     * @param obj Object the user is working with. Could be any ValueObject.
     * @return FieldAuthorizationVo containing fields that are disabled and editable
     */
    public FieldAuthorizationVo authorizeFields(UserVo user, Object obj) {

        List<Role> roles = user.getRoles();
        Environment environment = environmentUtility.getEnvironment();

        boolean newInstance = false;
        Set<FieldKey> disabledFields = new HashSet<FieldKey>();
        Set<FieldKey> nonEditableFields = new HashSet<FieldKey>();
        Set<FieldKey> requiredFields = new HashSet<FieldKey>();
        Set<FieldKey> secondReviewFields = new HashSet<FieldKey>();
        Set<FieldKey> disabledAddMultipleDataFields = new HashSet<FieldKey>();
        Set<FieldKey> disabledModifyMultipleDataFields = new HashSet<FieldKey>();
        Set<FieldKey> disabledRemoveMultipleDataFields = new HashSet<FieldKey>();

        // Handle non-ValueObject instances as all-enabled.
        if (obj instanceof ValueObject) {
            ValueObject vo = (ValueObject) obj;

            // If the user can't edit anything, lock down all fields. //NATIONAL_READ_ONLY //SECOND_REVIEWER
            // REMOVE Local Only roles for now roles.contains(Role.LOCAL_READ_ONLY)
            //|| (!user.isLocalManager() && environment.isLocal())
            if (user.isNationalManager() || user.isSecondReviewer() && environment.isNational()) {

                disabledFields.addAll(vo.listDisabledFields(environment, roles));
                nonEditableFields.addAll(vo.listNonEditableFields(environment, roles));
                requiredFields.addAll(vo.listRequiredFields(environment, roles));
                secondReviewFields.addAll(vo.listSecondReviewFields(environment, roles));
                disabledAddMultipleDataFields.addAll(vo.listDisabledAddMultipleDataFields(environment, roles));
                disabledModifyMultipleDataFields.addAll(vo.listDisabledModifyMultipleDataFields(environment, roles));
                disabledRemoveMultipleDataFields.addAll(vo.listDisabledRemoveMultipleDataFields(environment, roles));
            } else {
                disabledFields.addAll(vo.listAllFields());

                if (roles.contains(Role.PSS_PPSN_VIEWER)) {
                    disabledFields.remove(FieldKey.SPANISH_TEXT);
                }

            }

            newInstance = vo.isNewInstance();
        }

        return new FieldAuthorizationVo(disabledFields, nonEditableFields, requiredFields, secondReviewFields,
                                        disabledAddMultipleDataFields, disabledModifyMultipleDataFields,
                                        disabledRemoveMultipleDataFields, newInstance);
    }

    /**
     * Update 
     * @param userIn String username for the PEPS system
     * @return UserVo containing user information
     * 
     * @throws AuthenticationException if the given username/password pair do not authenticate
     */
    public UserVo update(UserVo userIn) throws AuthenticationException {
        return userDomainCapability.update(userIn);
    }

    /**
     * Authenticate this user by verifying the username and password exist. Set the available roles for the {@link UserVo}
     * and load user preferences.
     * 
     * @param userIn String username for the PEPS system
     * @return UserVo containing user information
     * 
     * @throws AuthenticationException if the given username/password pair do not authenticate
     */
    public UserVo login(UserVo userIn) throws AuthenticationException {
        userIn.setUsername(userIn.getUsername().toUpperCase(Locale.US));
        UserVo user = userDomainCapability.retrieve(userIn);

        if (user == null) {
            user = new UserVo();
            user.setFirstName("First Name");
            user.setLastName("Last Name");
            user.setUsername(userIn.getUsername());
            user.setCreatedBy("System");
            user.setStationNumber("999");
            user.setLocation(userIn.getLocation());

            user.setRoleMigApvdFlag(userIn.getRoleMigApvdFlag());
            user.setRolePsrApvdFlag(userIn.getRolePsrApvdFlag());
            user.setRolePnmApvdFlag(userIn.getRolePnmApvdFlag());
            user.setRolePnsApvdFlag(userIn.getRolePnsApvdFlag());
            user.setRoleMigFlag(userIn.getRoleMigFlag());
            user.setRolePsrFlag(userIn.getRolePsrFlag());
            user.setRolePnmFlag(userIn.getRolePnmFlag());
            user.setRolePnsFlag(userIn.getRolePnsFlag());

            user.setCreatedDate(new Date());
            String str = seqNumDomainCapability.generateId(EntityType.USER, user);
            Long l = Long.valueOf(str);
            user.setId(l);
            user.setDuz(l);
            setDefaultPreferences(user);
            user = userDomainCapability.create(user);

            if (user == null) {
                throw new AuthenticationException(AuthenticationException.INVALID_CREDENTIALS, userIn.getUsername());
            } else {
                user.setRoles(userIn.getRoles());
            }
        }

        if (user.getSessionPreferences().isEmpty()) {
            setDefaultPreferences(user);
        }

        //   SiteConfigVo site = siteConfigDomainCapability.retrieve();
        //user.setLocation(user.getStationNumber());

        user.setHostName(getHostName());

        return user;
    }

    /**
     * Set default preferences for users with no preferences in the database.
     * 
     * @param user to associate preferences
     */
    private void setDefaultPreferences(UserVo user) {
        Map<SessionPreferenceType, String> preferences = new HashMap<SessionPreferenceType, String>();
        preferences.put(SessionPreferenceType.LONG_DATE_FORMAT, DateFormat.DAY_MONTH_YEAR.toString());
        preferences.put(SessionPreferenceType.TIME_FORMAT, TimeFormat.MILITARY.toString());
        preferences.put(SessionPreferenceType.TABLE_ROW_COUNT, String.valueOf(UserVo.DEFAULT_TABLE_SIZE));
        preferences.put(SessionPreferenceType.HOME_PAGE_DEFAULT, String.valueOf(UserVo.DEFAULT_HOME_PAGE));
        preferences.put(SessionPreferenceType.DRUG_CLASS_SORT, String.valueOf(DrugClassSortPreference.CODE.toString()));
        preferences.put(SessionPreferenceType.FDB_STATUS_CODE_ACTIVE, String.valueOf(UserVo.DEFAULT_FDB_STATUS_CODE_ACTIVE));
        preferences.put(SessionPreferenceType.FDB_RX_OTC, String.valueOf(UserVo.DEFAULT_FDB_RX_OTC));
        preferences.put(SessionPreferenceType.FDB_NAME_TYPE, String.valueOf(UserVo.DEFAULT_FDB_NAME_TYPE));
        preferences.put(SessionPreferenceType.FDB_PACKAGED_DRUG, String.valueOf(UserVo.DEFAULT_FDB_PACKAGED_DRUG));
        preferences.put(SessionPreferenceType.FDB_PHONETIC_SEARCH, String.valueOf(UserVo.DEFAULT_FDB_PHONETIC_SEARCH));
        preferences.put(SessionPreferenceType.FDB_SEARCH_METHODS, String.valueOf(UserVo.DEFAULT_FDB_SEARCH_METHODS));
        preferences.put(SessionPreferenceType.FDB_DEVICES, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_SINGLE_INGREDIENT, String.valueOf(false));
        preferences.put(SessionPreferenceType.FDB_OBSOLETE_DRUGS, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_PRIVATE_LABELERS, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_REPACKAGERS, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_STATUS_CODE_ACTIVE, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_STATUS_CODE_INACTIVE, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_STATUS_CODE_RETIRED, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_STATUS_CODE_REPLACED, String.valueOf(true));
        preferences.put(SessionPreferenceType.FDB_STATUS_CODE_UNASSOCIATED, String.valueOf(true));
        
        
        preferences.put(SessionPreferenceType.DEFAULT_PROD_PRINT_TEMPLATE_ID,
            String.valueOf(SearchTemplateVo.DEFAULT_PROD_PRINT_TEMPLATE_ID));
        preferences.put(SessionPreferenceType.DEFAULT_OI_PRINT_TEMPLATE_ID,
            String.valueOf(SearchTemplateVo.DEFAULT_OI_PRINT_TEMPLATE_ID));
        preferences.put(SessionPreferenceType.DEFAULT_NDC_PRINT_TEMPLATE_ID,
            String.valueOf(SearchTemplateVo.DEFAULT_NDC_PRINT_TEMPLATE_ID));
        

//        //last search prefs - defaults
//        preferences.put(SessionPreferenceType.LAST_SEARCH_DOMAIN, String.valueOf(SearchDomain.SIMPLE));
//
//        preferences.put(SessionPreferenceType.LAST_DOMAIN_ITEM_STATUS, String.valueOf(ItemStatus.ACTIVE));
//        preferences.put(SessionPreferenceType.LAST_DOMAIN_ITEM_REQUEST, String.valueOf(RequestItemStatus.APPROVED));
//
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_ENTITY_TYPE, String.valueOf(EntityType.PRODUCT));
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_CATS, String.valueOf(Category.MEDICATION));
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_SEARCH_TYPE, String.valueOf(SearchType.BEGINS_WITH));
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_ITEM_STATUS, String.valueOf(ItemStatus.ACTIVE));
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_ITEM_REQUEST, String.valueOf(RequestItemStatus.APPROVED));
//
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_SEARCH_FIELD, String.valueOf(""));
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_SUBCATS, String.valueOf(""));
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_STRENGTH, String.valueOf(""));
//        preferences.put(SessionPreferenceType.LAST_SIMPLE_DOSAGE_FORM, String.valueOf(""));

        user.setSessionPreferences(preferences);
    }

    /**
     * Log the user out of the system. The method is currently empty, as there are no known logout requirements.
     * 
     * @param user UserVo to log out
     */
    public void logout(UserVo user) {

    }

    /**
     * Update/persist the user's selected display settings and preferences. Preferences are stored in the
     * {@link UserVo#getPreferences()} {@link Map}.
     * 
     * Since we currently don't persist user preferences, all this method does is return the {@link UserVo} passed in as a
     * parameter.
     * 
     * @param user {@link UserVo} with updated preferences
     * @return updated UserVo
     * 
     * @throws ValueObjectValidationException if error validating user preferences
     */
    public UserVo updatePreferences(UserVo user) throws ValueObjectValidationException {

        user.validate();
        user.expandPreferences();

        return userDomainCapability.updatePreferences(user);
    }

    /**
     * Load user by ID (KAAJEE DUZ)
     * 
     * @param duz duz
     * @param station String
     * @return UserVo
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    public UserVo retrieve(Long duz, String station) throws AuthenticationException {

        UserVo user = userDomainCapability.retrieve(duz, station);

        if (user != null) {
            if (user.getSessionPreferences().isEmpty()) {
                setDefaultPreferences(user);
            }

            // SiteConfigVo site = siteConfigDomainCapability.retrieve();
            // user.setLocation(site.getSiteNumber());
            user.setHostName(getHostName());
        }

        return user;
    }

    /**
     * RetrieveAllUSers
     * 
     * @return UserVo
     * 
     * @throws AuthenticationException if user could not be found in the database
     */
    public Collection<UserVo> retrieveAll() throws AuthenticationException {
        Collection<UserVo> user = userDomainCapability.retrieveAll();

        return user;
    }

    /**
     * getHostName
     * @return hostName
     */
    private String getHostName() {
        List<NationalSettingVo> settingsList = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : settingsList) {

            if (NationalSetting.HOST_NAME.toString().equals(vo.getKeyName().toString())) {

                return vo.getStringValue();
            }
        }

        return null;
    }

    /**
     * Retrieve all notification types
     * 
     * @return List<NotificationTypeVo>
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.UserCapability#retrieveAllNotificationTypes()
     */
    public List<NotificationType> retrieveAllNotificationTypes() {
        return Arrays.asList(NotificationType.values());
    }

    /**
     * Create a new {@link UserVo}.
     * 
     * @param user {@link UserVo} to create
     * @return created {@link UserVo}
     * @throws ValidationException if error validating data in {@link UserVo}
     */
    public UserVo create(UserVo user) throws ValidationException {

        UserVo user2 = user;

        user2.validate();

        if (user2.getSessionPreferences().isEmpty()) {
            setDefaultPreferences(user2);
        }

        String str = seqNumDomainCapability.generateId(EntityType.USER, user2);
        Long l = Long.valueOf(str);
        user2.setId(l);
        setDefaultPreferences(user2);

        user2 = userDomainCapability.create(user2);

        user2.setHostName(getHostName());

        return user2;
    }

    /**
     * Return true if a {@link UserVo} with the given ID exists.
     * 
     * @param id Long
     * @return boolean
     */
    public boolean exists(Long id) {
        return userDomainCapability.exists(id);
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * getUserDomainCapability
     * @return userDomainCapability property
     */
    public UserDomainCapability getUserDomainCapability() {
        return userDomainCapability;
    }

    /**
     * setUserDomainCapability
     * @param userDomainCapability userDomainCapability property
     */
    public void setUserDomainCapability(UserDomainCapability userDomainCapability) {
        this.userDomainCapability = userDomainCapability;
    }

    /**
     * setSeqNumDomainCapability
     * @param seqNumDomainCapability seqNumDomainCapability property
     */
    public void setSeqNumDomainCapability(SeqNumDomainCapability seqNumDomainCapability) {
        this.seqNumDomainCapability = seqNumDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability
     * @param nationalSettingDomainCapability nationalSettingDomainCapability property
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }
}
