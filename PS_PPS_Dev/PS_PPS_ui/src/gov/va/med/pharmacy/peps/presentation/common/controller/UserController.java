/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.med.authentication.kernel.LoginUserInfoVO;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.DateFormat;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.TimeFormat;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;
import gov.va.med.pharmacy.peps.presentation.common.spring.interceptor.PepsSpringInterceptor;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * The controller used to set the user preferences
 * 
 */
@Controller("userController")
public class UserController extends AbstractSearchController {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UserController.class);

//    private static String LOGOUT_VIEW = "user.logout";
    private static String LOGIN_VIEW = "user.login";
    
    private static String USER_PREFERENCES_VIEW = "display.settings";
    
    private static final String PAGE_DISPLAY_SETTINGS = "displaySettings.go";
    private static final String PAGE_LOGIN = "/login.go";

    @Autowired
    private UserService userService;

    /**
     * Empty constructor
     *
     */
    public UserController() {

    }

    /**
     * creates the date format map
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("dateFormatMap")
    public SortedMap<DateFormat, String> createDateFormatMap(Locale locale) {
        SortedMap<DateFormat, String> dateFormatMap = new TreeMap<DateFormat, String>();

        for (DateFormat format : DateFormat.values()) {
            dateFormatMap.put(format, getMessageSource().getMessage(format.toString(), null, locale));
        }

        return dateFormatMap;
    }

    /**
     * creates the time format map
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("timeFormatMap")
    public SortedMap<TimeFormat, String> createTimeFormatMap(Locale locale) {
        SortedMap<TimeFormat, String> timeFormatMap = new TreeMap<TimeFormat, String>();
        timeFormatMap.put(TimeFormat.STANDARD, getMessageSource().getMessage("time.format.std", null, locale));
        timeFormatMap.put(TimeFormat.MILITARY, getMessageSource().getMessage("time.format.mil", null, locale));

        return timeFormatMap;
    }

    /**
     * creates the homePage map
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("homePageFormatMap")
    public SortedMap<String, String> homePageFormatMap(Locale locale) {
        SortedMap<String, String> homePageFormatMap = new TreeMap<String, String>();
        homePageFormatMap.put("H", getMessageSource().getMessage("home.page.home", null, locale));
        homePageFormatMap.put("S", getMessageSource().getMessage("home.page.search", null, locale));

        return homePageFormatMap;
    }

    /**
     * creates the fdbRxOTC map
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("fdbRxOtcMap")
    public SortedMap<String, String> fbRxOtcMap(Locale locale) {
        SortedMap<String, String> fdbRxOtcMap = new TreeMap<String, String>();
        fdbRxOtcMap.put("RX", getMessageSource().getMessage("FdbRxOtc.RX", null, locale));
        fdbRxOtcMap.put("OTC", getMessageSource().getMessage("FdbRxOtc.OTC", null, locale));
        fdbRxOtcMap.put("BOTH", getMessageSource().getMessage("FdbRxOtc.BOTH", null, locale));

        return fdbRxOtcMap;
    }

    /**
     * creates the fdbPhoneticSearchMap
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("fdbPhoneticSearchMap")
    public SortedMap<String, String> fdbPhoneticSearchMap(Locale locale) {
        SortedMap<String, String> fdbPhoneticSearchMap = new TreeMap<String, String>();
        fdbPhoneticSearchMap.put("LITERAL", getMessageSource().getMessage("FdbPhoneticSearch.LITERAL", null, locale));
        fdbPhoneticSearchMap.put("LITERAL_AND_PHONETIC",
            getMessageSource().getMessage("FdbPhoneticSearch.LITERAL_AND_PHONETIC", null, locale));
        fdbPhoneticSearchMap.put("PHONETIC", getMessageSource().getMessage("FdbPhoneticSearch.PHONETIC", null, locale));

        return fdbPhoneticSearchMap;
    }

    /**
     * creates the fdbSearchMethodsMap
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("fdbSearchMethodsMap")
    public SortedMap<String, String> fdbSearchMethodsMap(Locale locale) {
        SortedMap<String, String> fdbSearchMethodsMap = new TreeMap<String, String>();
        fdbSearchMethodsMap.put("BEGINS_WITH_LITERAL",
            getMessageSource().getMessage("FdbSearchMethods.BEGINS_WITH_LITERAL", null, locale));
        fdbSearchMethodsMap.put("CONTAINS_LITERAL",
            getMessageSource().getMessage("FdbSearchMethods.CONTAINS_LITERAL", null, locale));
        fdbSearchMethodsMap.put("BEGINS_WITH_EACH_STRING",
            getMessageSource().getMessage("FdbSearchMethods.BEGINS_WITH_EACH_STRING", null, locale));
        fdbSearchMethodsMap.put("CONTAINS_EACH_STRING",
            getMessageSource().getMessage("FdbSearchMethods.CONTAINS_EACH_STRING", null, locale));

        return fdbSearchMethodsMap;
    }

    /**
     * creates the fdbNameTypeMap
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("fdbNameTypeMap")
    public SortedMap<String, String> fdbNameTypeMap(Locale locale) {
        SortedMap<String, String> fdbNameTypeMap = new TreeMap<String, String>();
        fdbNameTypeMap.put("BRAND", getMessageSource().getMessage("FdbNameType.BRAND_ONLY", null, locale));
        fdbNameTypeMap.put("GENERIC", getMessageSource().getMessage("FdbNameType.GENERIC_ONLY", null, locale));
        fdbNameTypeMap.put("BOTH", getMessageSource().getMessage("FdbNameType.BOTH", null, locale));

        return fdbNameTypeMap;
    }

    /**
     * creates the fdbPackagedDrugMap
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("fdbPackagedDrugMap")
    public SortedMap<String, String> fdbPackagedDrugMap(Locale locale) {
        SortedMap<String, String> fdbPackagedDrugMap = new TreeMap<String, String>();
        fdbPackagedDrugMap.put("PACKAGED DRUG",
            getMessageSource().getMessage("FdbPackagedDrug.PACKAGE_DRUG_ONLY", null, locale));
        fdbPackagedDrugMap.put("EQUIV. PACKAGED DRUG",
            getMessageSource().getMessage("FdbPackagedDrug.EQUIVALENT_PACKAGE_DRUG_ONLY", null, locale));
        fdbPackagedDrugMap.put("BOTH", getMessageSource().getMessage("FdbPackagedDrug.BOTH", null, locale));

        return fdbPackagedDrugMap;
    }

    /**
     * Create Preferences
     *
     * @return UserController.Preferences
     */
    @ModelAttribute("preferences")
    public UserController.Preferences createPreferences() {
        Preferences preferences = new Preferences();

        if (getUser() != null) {
            preferences.setDateFormat(DateFormat.valueOf(getUser().getSessionPreferences()
                .get(SessionPreferenceType.LONG_DATE_FORMAT)));
            preferences.setHomePageFormat(getUser().getSessionPreferences()
                                           .get(SessionPreferenceType.HOME_PAGE_DEFAULT));
            preferences.setTimeFormat(TimeFormat.valueOf(getUser().getSessionPreferences()
                .get(SessionPreferenceType.TIME_FORMAT)));
            preferences.setTableRowCount(getUser().getSessionPreferences().get(SessionPreferenceType.TABLE_ROW_COUNT));
            preferences.setFdbDevices(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_DEVICES)));
            preferences.setFdbSingleIngredient(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_SINGLE_INGREDIENT)));
            preferences.setFdbObsoleteDrugs(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_OBSOLETE_DRUGS)));
            preferences.setFdbPrivateLabelers(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_PRIVATE_LABELERS)));
            preferences.setFdbRepackagers(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_REPACKAGERS)));
            preferences.setFdbStatusCodeActive(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_STATUS_CODE_ACTIVE)));
            preferences.setFdbStatusCodeReplaced(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_STATUS_CODE_REPLACED)));
            preferences.setFdbStatusCodeRetired(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_STATUS_CODE_RETIRED)));
            preferences.setFdbStatusCodeInactive(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_STATUS_CODE_INACTIVE)));
            preferences.setFdbStatusCodeUnassociated(Boolean.valueOf(getUser().getSessionPreferences().get(
                SessionPreferenceType.FDB_STATUS_CODE_UNASSOCIATED)));
            preferences.setFdbRxOtc(getUser().getSessionPreferences().get(SessionPreferenceType.FDB_RX_OTC));
            preferences.setFdbNameType(getUser().getSessionPreferences().get(SessionPreferenceType.FDB_NAME_TYPE));
            preferences.setFdbPackagedDrug(getUser().getSessionPreferences().get(SessionPreferenceType.FDB_PACKAGED_DRUG));
            preferences.setFdbPhoneticSearch(getUser().getSessionPreferences()
                .get(SessionPreferenceType.FDB_PHONETIC_SEARCH));
            preferences.setFdbSearchMethods(getUser().getSessionPreferences().get(SessionPreferenceType.FDB_SEARCH_METHODS));

        }

        return preferences;
    }

    /**
     * Displays login page
     * 
     * @param model so the page can have access to the model (possible future use)
     * @param request request object, possible future use
     * @param session session object, possible future use
     * 
     * @return tiles definition string
     */
    @RequestMapping(value = PAGE_LOGIN, method = { RequestMethod.GET })
    public String login(Model model, HttpServletRequest request, HttpSession session) {
        LOG.debug(PAGE_LOGIN + ": " + RequestMethod.GET);
        
        pageTrail.clearTrail();
        pageTrail.addPage("login", "Login");

        return LOGIN_VIEW;
    }

    /**
    * Redirects the Kajee Login page to the proper user preference
    * 
    * @param model so the page can have access to the model (possible future use)
    * @param request request object, possible future use
    * @param session session object, possible future use
    * 
    * @return tiles definition string
    */
    @RequestMapping(value = "/kaajeeLogin.go", method = { RequestMethod.GET })
    public String redirectKaajeeLogin(Model model, HttpServletRequest request, HttpSession session) {
        LOG.debug("/kaajeeLogin.go" + ": " + RequestMethod.GET);
        
        return redirectLogin(model, request, session);

    }

    /**
     * Displays login page
     * 
     * @param model so the page can have access to the model (possible future use)
     * @param request request object, possible future use
     * @param session session object, possible future use
     * 
     * @return tiles definition string
     */
    @RequestMapping(value = PAGE_LOGIN, method = { RequestMethod.POST })
    public String redirectLogin(Model model, HttpServletRequest request, HttpSession session) {
        LOG.debug(PAGE_LOGIN + ": " + RequestMethod.POST);
        
        return redirectToDefaultUrl();
    }
    
    /**
     * return the proper homepage redirect
     *
     * @return String
     */
    private String redirectToDefaultUrl() {
        StringBuffer redirectSb = new StringBuffer();
        redirectSb.append(REDIRECT + "/");

        if (getUser() == null) {
            redirectSb.append("home.go");
        } else {
            redirectSb.append(getUser().getHomePageDefaultUrl());
        }

        return redirectSb.toString();
    }

    /**
     * Displays logout page
     * 
     * @param request request object, possible future use
     * @param response response object, possible future use
     * @param session session object, possible future use
     * @return tiles definition string
     */
    @RequestMapping(value = "/logout.go")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        // DO NOT REMOVE THIS BLOCK:  BEGIN
        // DO NOT REMOVE THIS BLOCK:  BEGIN
        // If the server keeps the original referring URL (from prior to authentication), it may try to place
        // the user on that page.  However, if the user's session timed out, their last page would be logout.go
        // thus after logging in, you would immediately be logged out.  This hack provides a means to avoid this
        // problem.
        String test = new StringBuffer("").append(request.getContextPath()).append("/login/login.jsp").toString();
        String header1 = request.getHeader("referer");
        String header2 = request.getHeader("Referer");
        
        if ((header1 != null && header1.endsWith(test)) || (header2 != null && header2.endsWith(test))) {
            
            return redirectToDefaultUrl();
        }
        
        // DO NOT REMOVE THIS BLOCK:  END
        // DO NOT REMOVE THIS BLOCK:  END

        // At this point this will perform log out methods
        LOG.trace("user controller: user logout");

        pageTrail.clearTrail();

//        try {
//            userService.updatePreferences(getUser());
//        } catch (ValueObjectValidationException e) {
//            LOG.error("Unable to save user search domain preferences.", e);
//        }


        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.addHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies.

        UserContext userContext = PepsSpringInterceptor.getUserContext(session);
        userService.logout(userContext.getUser());
        userContext.setUser(null);
        session.setAttribute("user", null);
        session.removeAttribute(LoginUserInfoVO.SESSION_KEY);
        session.invalidate();        
        
        return redirectToDefaultUrl();
    }

    /**
     * Gets the page for viewing.
     * @see org.springframework.web.servlet.mvc.Controller#handleRequest(HttpServletRequest, 
     *  javax.servlet.http.HttpServletResponse)
     * 
     * @param model so the page can have access to the model
     * @param request request object, possible future use
     * @param locale location specific formatting and properties usage class
     * @param preferences UserController.Preferences
     * 
     * @return String
     * 
     * @throws Exception needed, as it is required by Controller interface
     */
    @RequestMapping(value = PAGE_DISPLAY_SETTINGS, method = RequestMethod.GET)
    public String handleRequest(Model model, HttpServletRequest request, Locale locale,
        @ModelAttribute("preferences") UserController.Preferences preferences) throws Exception { //NOPMD

        pageTrail.clearTrail();
        pageTrail.addPage("userPrefs", "User Preferences", true);

        return USER_PREFERENCES_VIEW;
    }

    /**
     * POST handling method
     * 
     * @param preferences user preferences
     * @param model so the page can have access to the model
     * @param request request object
     * 
     * @return String 
     * 
     * @throws ValueObjectValidationException exception
     */
    @RequestMapping(value = PAGE_DISPLAY_SETTINGS, method = RequestMethod.POST)
    public String savePreferences(
        @ModelAttribute("preferences") UserController.Preferences preferences,
        Model model,
        HttpServletRequest request)
        throws ValueObjectValidationException {

        getUser().getSessionPreferences()
            .put(SessionPreferenceType.LONG_DATE_FORMAT, preferences.getDateFormat().toString());
        getUser().getSessionPreferences().put(SessionPreferenceType.TIME_FORMAT, preferences.getTimeFormat().toString());
        getUser().getSessionPreferences().put(SessionPreferenceType.TABLE_ROW_COUNT, preferences.getTableRowCount());
        getUser().getSessionPreferences().put(SessionPreferenceType.HOME_PAGE_DEFAULT, preferences.getHomePageFormat());
        getUser().getSessionPreferences()
            .put(SessionPreferenceType.FDB_DEVICES, String.valueOf(preferences.getFdbDevices()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_SINGLE_INGREDIENT,
            String.valueOf(preferences.getFdbSingleIngredient()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_OBSOLETE_DRUGS,
            String.valueOf(preferences.getFdbObsoleteDrugs()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_PRIVATE_LABELERS,
            String.valueOf(preferences.getFdbPrivateLabelers()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_REPACKAGERS,
            String.valueOf(preferences.getFdbRepackagers()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_STATUS_CODE_ACTIVE,
            String.valueOf(preferences.getFdbStatusCodeActive()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_STATUS_CODE_REPLACED,
            String.valueOf(preferences.getFdbStatusCodeReplaced()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_STATUS_CODE_RETIRED,
            String.valueOf(preferences.getFdbStatusCodeRetired()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_STATUS_CODE_INACTIVE,
            String.valueOf(preferences.getFdbStatusCodeInactive()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_STATUS_CODE_UNASSOCIATED,
            String.valueOf(preferences.getFdbStatusCodeUnassociated()));
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_RX_OTC, preferences.getFdbRxOtc());
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_NAME_TYPE, preferences.getFdbNameType());
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_PACKAGED_DRUG, preferences.getFdbPackagedDrug());
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_PHONETIC_SEARCH, preferences.getFdbPhoneticSearch());
        getUser().getSessionPreferences().put(SessionPreferenceType.FDB_SEARCH_METHODS, preferences.getFdbSearchMethods());
        userService.updatePreferences(getUser());

        return "redirect:/displaySettings.go";
    }

    /**
     * Inner class used to set/get user preferences by the form
     *  
     * 
     */
    public static class Preferences {

        private DateFormat dateFormat;
        private TimeFormat timeFormat;
        private String tableRowCount;
        private String homePageFormat;
        private boolean fdbDevices;
        private boolean fdbSingleIngredient;
        private boolean fdbObsoleteDrugs;
        private boolean fdbPrivateLabelers;
        private boolean fdbRepackagers;
        private boolean fdbStatusCodeActive;
        private boolean fdbStatusCodeReplaced;
        private boolean fdbStatusCodeRetired;
        private boolean fdbStatusCodeInactive;
        private boolean fdbStatusCodeUnassociated;
        private String fdbNameType;
        private String fdbRxOtc;
        private String fdbPackagedDrug;
        private String fdbPhoneticSearch;
        private String fdbSearchMethods;

        /**
         * Empty constructor
         *
         */
        public Preferences() {

        }

        /**
         * set homePageFormat
         * 
         * @param homePageFormat the homePageDefault to set
         */
        public void setHomePageFormat(String homePageFormat) {
            this.homePageFormat = homePageFormat;
        }

        /**
         * getHomePageFormat
         * 
         * @return the homePageDefault
         */
        public String getHomePageFormat() {
            return homePageFormat;
        }

        /**
         * set date format
         * 
         * @param dateFormat the dateFormat to set
         */
        public void setDateFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        /**
         * get date format
         * 
         * @return the dateFormat
         */
        public DateFormat getDateFormat() {
            return dateFormat;
        }

        /**
         * set time format
         * @param timeFormat the timeFormat to set
         */
        public void setTimeFormat(TimeFormat timeFormat) {
            this.timeFormat = timeFormat;
        }

        /**
         * get time format
         * 
         * @return the timeFormat
         */
        public TimeFormat getTimeFormat() {
            return timeFormat;
        }

        /**
         * set table row count
         * 
         * @param tableRowCount the tableRowCount to set
         */
        public void setTableRowCount(String tableRowCount) {
            this.tableRowCount = tableRowCount;
        }

        /**
         * get table row count
         * 
         * @return the tableRowCount
         */
        public String getTableRowCount() {
            return tableRowCount;
        }

        /**
         * getFdbDevices
         * @return the fdbDevices
         */
        public boolean getFdbDevices() {

            return fdbDevices;
        }

        /**
         * setFdbDevices
         * @param fdbDevices the fdbDevices to set
         */
        public void setFdbDevices(boolean fdbDevices) {

            this.fdbDevices = fdbDevices;
        }

        /**
         * getFdbSingleIngredient
         * @return the fdbSingleIngredient
         */
        public boolean getFdbSingleIngredient() {

            return fdbSingleIngredient;
        }

        /**
         * setFdbSingleIngredient
         * @param fdbSingleIngredient the fdbSingleIngredient to set
         */
        public void setFdbSingleIngredient(boolean fdbSingleIngredient) {

            this.fdbSingleIngredient = fdbSingleIngredient;
        }

        /**
         * getFdbObsoleteDrugs
         * @return the fdbObsoleteDrugs
         */
        public boolean getFdbObsoleteDrugs() {

            return fdbObsoleteDrugs;
        }

        /**
         * setFdbObsoleteDrugs
         * @param fdbObsoleteDrugs the fdbObsoleteDrugs to set
         */
        public void setFdbObsoleteDrugs(boolean fdbObsoleteDrugs) {

            this.fdbObsoleteDrugs = fdbObsoleteDrugs;
        }

        /**
         * getFdbPrivateLabelers
         * @return the fdbPrivateLabelers
         */
        public boolean getFdbPrivateLabelers() {

            return fdbPrivateLabelers;
        }

        /**
         * setFdbPrivateLabelers
         * @param fdbPrivateLabelers the fdbPrivateLabelers to set
         */
        public void setFdbPrivateLabelers(boolean fdbPrivateLabelers) {

            this.fdbPrivateLabelers = fdbPrivateLabelers;
        }

        /**
         * getFdbRepackagers
         * @return the fdbRepackagers
         */
        public boolean getFdbRepackagers() {

            return fdbRepackagers;
        }

        /**
         * setFdbRepackagers
         * @param fdbRepackagers the fdbRepackagers to set
         */
        public void setFdbRepackagers(boolean fdbRepackagers) {

            this.fdbRepackagers = fdbRepackagers;
        }

        /**
         * getFdbStatusCodeActive
         * @return fdbStatusCodeActive
         */
        public boolean getFdbStatusCodeActive() {
            return fdbStatusCodeActive;
        }

        /**
         * setFdbStatusCodeActive
         * @param fdbStatusCodeActive the fdbStatusCodeActive to set
         */
        public void setFdbStatusCodeActive(boolean fdbStatusCodeActive) {
            this.fdbStatusCodeActive = fdbStatusCodeActive;
        }

        /**
         * getFdbStatusCodeReplaced
         * @return fdbStatusCodeReplaced
         */
        public boolean getFdbStatusCodeReplaced() {
            return fdbStatusCodeReplaced;
        }

        /**
         * setFdbStatusCodeReplaced
         * @param fdbStatusCodeReplaced the fdbStatusCodeReplaced to set
         */
        public void setFdbStatusCodeReplaced(boolean fdbStatusCodeReplaced) {
            this.fdbStatusCodeReplaced = fdbStatusCodeReplaced;
        }

        /**
         * getFdbStatusCodeRetired
         * @return fdbStatusCodeRetired
         */
        public boolean getFdbStatusCodeRetired() {
            return fdbStatusCodeRetired;
        }

        /**
         * setFdbStatusCodeRetired
         * @param fdbStatusCodeRetired the fdbStatusCodeRetired to set
         */
        public void setFdbStatusCodeRetired(boolean fdbStatusCodeRetired) {
            this.fdbStatusCodeRetired = fdbStatusCodeRetired;
        }

        /**
         * getFdbStatusCodeInactive
         * @return fdbStatusCodeInactive
         */
        public boolean getFdbStatusCodeInactive() {
            return fdbStatusCodeInactive;
        }

        /**
         * setFdbStatusCodeInactive
         * @param fdbStatusCodeInactive the fdbStatusCodeInactive to set
         */
        public void setFdbStatusCodeInactive(boolean fdbStatusCodeInactive) {
            this.fdbStatusCodeInactive = fdbStatusCodeInactive;
        }

        /**
         * getFdbStatusCodeUnassociated
         * @return fdbStatusCodeUnassociated 
         */
        public boolean getFdbStatusCodeUnassociated() {
            return fdbStatusCodeUnassociated;
        }

        /**
         * setFdbStatusCodeUnassociated
         * @param fdbStatusCodeUnassociated the fdbStatusCodeUnassociated to set
         */
        public void setFdbStatusCodeUnassociated(boolean fdbStatusCodeUnassociated) {
            this.fdbStatusCodeUnassociated = fdbStatusCodeUnassociated;
        }

        /**
         * setFdbNameType
         * @param fdbNameType the fdbNameType to set
         */
        public void setFdbNameType(String fdbNameType) {

            this.fdbNameType = fdbNameType;
        }

        /**
         * getFdbNameType
         * @return the fdbNameType
         */
        public String getFdbNameType() {

            return fdbNameType;
        }

        /**
         * setFdbRxOtc
         * @param fdbRxOtc the fdbRxOtc to set
         */
        public void setFdbRxOtc(String fdbRxOtc) {

            this.fdbRxOtc = fdbRxOtc;
        }

        /**
         * getFdbRxOtc
         * @return the fdbRxOtc
         */
        public String getFdbRxOtc() {

            return fdbRxOtc;
        }

        /**
         * setFdbPackagedDrug
         * @param fdbPackagedDrug the fdbPackagedDrug to set
         */
        public void setFdbPackagedDrug(String fdbPackagedDrug) {

            this.fdbPackagedDrug = fdbPackagedDrug;
        }

        /**
         * getFdbPackagedDrug
         * @return the fdbPackagedDrug
         */
        public String getFdbPackagedDrug() {

            return fdbPackagedDrug;
        }

        /**
         * getFdbPhoneticSearch
         * @return the fdbPhoneticSearch
         */
        public String getFdbPhoneticSearch() {
            return fdbPhoneticSearch;
        }

        /**
         * setFdbPhoneticSearch
         * @param fdbPhoneticSearch the fdbPhoneticSearch to set
         */
        public void setFdbPhoneticSearch(String fdbPhoneticSearch) {
            this.fdbPhoneticSearch = fdbPhoneticSearch;
        }

        /**
         * getFdbSearchMethods
         * @return the fdbSearchMethods
         */
        public String getFdbSearchMethods() {
            return fdbSearchMethods;
        }

        /**
         * setFdbSearchMethods
         * @param fdbSearchMethods the fdbSearchMethods to set
         */
        public void setFdbSearchMethods(String fdbSearchMethods) {
            this.fdbSearchMethods = fdbSearchMethods;
        }

        /**
         * Convert a true/false into Y/N.
         * 
         * @param value
         *            boolean
         * @return String "Y" or "N"
         */
        protected String toYesOrNo(Boolean value) {
            if (value != null && value) {
                return "Y";
            } else {
                return "N";
            }
        }

    };

}
