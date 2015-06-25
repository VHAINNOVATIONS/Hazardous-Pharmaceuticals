/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.Column;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.service.common.session.PrintTemplateService;
import gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * SearchTemplateController's brief summary
 * 
 * Details of SearchTemplateController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller("searchTemplateController")
public class SearchTemplateController extends AbstractSearchController {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SearchTemplateController.class);

    private static final String AVAIL_PRINT_TEMPLATES = "availablePrintTemplates";
    private static final String SEARCH_PREFERENCES_VIEW = "search.settings";

    private SearchTemplateVo searchTemplate = new SearchTemplateVo();

    @Autowired
    private SearchTemplateService searchTemplateService;

    @Autowired
    private PrintTemplateService printTemplateService;

    @Autowired
    private UserService userService;

    /**
     * Default constructor
     *
     */
    public SearchTemplateController() {
        super();
    }

    /**
     * Create search preferences
     *
     * @return UserController.SearchPreferences
     */
    @ModelAttribute(ControllerConstants.SEARCH_PREFERENCES)
    public SearchTemplateController.SearchTemplateManager createSearchPrefs() {

        SearchTemplateManager searchManager = new SearchTemplateManager();

        if (getUser() != null) {
            searchManager.setDefaultProdPrintTemplateId(getUser().getSessionPreferences().get(
                SessionPreferenceType.DEFAULT_PROD_PRINT_TEMPLATE_ID));
            searchManager.setDefaultOiPrintTemplateId(getUser().getSessionPreferences().get(
                SessionPreferenceType.DEFAULT_OI_PRINT_TEMPLATE_ID));
            searchManager.setDefaultNdcPrintTemplateId(getUser().getSessionPreferences().get(
                SessionPreferenceType.DEFAULT_NDC_PRINT_TEMPLATE_ID));
        }

        return searchManager;
    }

    /**
     * Build the select list containing a personalized search template for each item type
     *
     * @return Map
     * 
     * @throws MissingResourceException exception to throw
     */
    @ModelAttribute(AVAIL_PRINT_TEMPLATES)
    public Map<String, String> getAvailableSearchTemplates() throws MissingResourceException {
        Map<String, String> rv = new LinkedHashMap<String, String>();
        PrintTemplateVo ptv = null;

        if (getUser() == null) {
            throw new MissingResourceException(MissingResourceException.MISSING_RESOURCE, "user object missing");
        }
        
        rv.put("", "");
        
        ptv = getPrintTemplateService().retrieve(getUser(), TemplateLocation.PERSONAL_PRODUCT_SEARCH);

        if (ptv == null) {
            ptv = DefaultPrintTemplateFactory.defaultOrderableItemSearch(true);
            ptv.setTemplateLocation(TemplateLocation.PERSONAL_PRODUCT_SEARCH);
            ptv.setTemplateName("Personal Product Search");
            getPrintTemplateService().create(getUser(), ptv);
        }

        rv.put(ptv.getTemplateLocation().toString(), ptv.getTemplateName());
        ptv = getPrintTemplateService().retrieve(getUser(), TemplateLocation.PERSONAL_ORDERABLE_ITEM_SEARCH);

        if (ptv == null) {
            ptv = DefaultPrintTemplateFactory.defaultOrderableItemSearch(true);
            ptv.setTemplateLocation(TemplateLocation.PERSONAL_ORDERABLE_ITEM_SEARCH);
            ptv.setTemplateName("Personal Orderable Item Search");
            getPrintTemplateService().create(getUser(), ptv);
        }

        rv.put(ptv.getTemplateLocation().toString(), ptv.getTemplateName());
        ptv = getPrintTemplateService().retrieve(getUser(), TemplateLocation.PERSONAL_NDC_SEARCH);

        if (ptv == null) {
            ptv = DefaultPrintTemplateFactory.defaultNdcSearch(true);
            ptv.setTemplateLocation(TemplateLocation.PERSONAL_NDC_SEARCH);
            ptv.setTemplateName("Personal NDC Search");
            getPrintTemplateService().create(getUser(), ptv);
        }

        rv.put(ptv.getTemplateLocation().toString(), ptv.getTemplateName());

        return rv;
    }

    /**
     * display the selectable product fields in the left-hand panel 
     *
     * @return Map<String, String> 
     */
    @ModelAttribute("availableProductColumns")
    public Map<String, String> getAvailableProductColumns() {
        String prodPreferenceString =
            getUser().getSessionPreferences().get(SessionPreferenceType.DEFAULT_PROD_PRINT_TEMPLATE_ID);

        return getAvailableColumns(EntityType.PRODUCT, prodPreferenceString);
    }

    /**
     * display the selectable OI fields in the left-hand panel 
     *
     * @return Map<String, String> 
     */
    @ModelAttribute("availableOrderableItemColumns")
    public Map<String, String> getAvailableOrderableItemColumns() {
        String oiPreferenceString =
            getUser().getSessionPreferences().get(SessionPreferenceType.DEFAULT_OI_PRINT_TEMPLATE_ID);

        return getAvailableColumns(EntityType.ORDERABLE_ITEM, oiPreferenceString);
    }

    /**
     * display the selectable NDC fields in the left-hand panel 
     *
     * @return Map<String, String> 
     */
    @ModelAttribute("availableNdcColumns")
    public Map<String, String> getAvailableNdcColumns() {
        String ndcPreferenceString =
            getUser().getSessionPreferences().get(SessionPreferenceType.DEFAULT_NDC_PRINT_TEMPLATE_ID);

        return getAvailableColumns(EntityType.NDC, ndcPreferenceString);
    }

    /**
     * Load the left-hand side of the search templates table
     * Only show those fields which are still available for selection - 
     * those that are not already shown in the right-hand panel
     *
     * @param e EntityType
     * @param a String
     * @return Map<String, String>
     */
    private Map<String, String> getAvailableColumns(EntityType e, String a) {
        Map<String, String> rv = new LinkedHashMap<String, String>();
        PrintTemplateVo ptv = null;

        if (EntityType.ORDERABLE_ITEM.equals(e)) {
            ptv = DefaultPrintTemplateFactory.availableOrderableItemSearchFields();
        } else if (EntityType.NDC.equals(e)) {
            ptv = DefaultPrintTemplateFactory.availableNdcSearchFields();
        } else if (EntityType.PRODUCT.equals(e)) { 
            ptv = DefaultPrintTemplateFactory.availableProductSearchFields();
        }

        //Build an array from the right side for comparison
        String revPreferenceString = a.substring(1, a.length() - 1);
        String[] keys = revPreferenceString.split(",");
        List<String> revKeyList = new ArrayList<String>();

        for (String key : keys) {
            revKeyList.add(key.trim());
        }

        List<Column> columns = ptv.getFields();
        List<String> tempFields2 = new ArrayList<String>();

        for (Column c : columns) {
            tempFields2.add(c.getFieldKey().getKey().trim());
        }

        //remove the fields that are already selected and listed in the right side panel
        tempFields2.removeAll(revKeyList);

        List<FieldKey> buildKeyList2 = new ArrayList<FieldKey>();

        for (String key2 : tempFields2) {
            buildKeyList2.add(FieldKey.getKey(key2.trim()));
        }

        PrintTemplateVo ptv2 = new PrintTemplateVo();
        PrintTemplateVo.populatePrintFieldCollection(ptv2, buildKeyList2);

        for (Column column : ptv2.getFields()) {
            rv.put(column.getFieldKey().getKey(), column.getFieldKey().getLocalizedName(LocaleContextHolder.getLocale()));
        }

        return rv;

    }
    
    /**
     * creates the selected print template fields map
     *
     * @param searchManager SearchTemplateManager
     * @param locale Locale used in the template construction
     * @param model Model
     * @param request HttpServletRequest
     * @throws ValueObjectValidationException exception
     * @return Map
     */
    @ModelAttribute("productFieldsMap")
    public LinkedHashMap<String, String> createTemplateFields(
        @ModelAttribute("searchManager") SearchTemplateController.SearchTemplateManager searchManager,
        Locale locale,
        Model model,
        HttpServletRequest request) throws ValueObjectValidationException {

        LinkedHashMap<String, String> productFieldsMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> productFieldsMap2 = new LinkedHashMap<String, String>();

        if (searchManager.currentPrintTemplateId != null
            && searchManager.getCurrentPrintTemplateId().equalsIgnoreCase("PERSONAL_PRODUCT_SEARCH")) {

            PrintTemplateVo personalProductTemplate = new PrintTemplateVo();
            String userPreferenceString =
                getUser().getSessionPreferences().get(SessionPreferenceType.DEFAULT_PROD_PRINT_TEMPLATE_ID);

            if (userPreferenceString.equalsIgnoreCase("PERSONAL PRODUCT SEARCH")) {
                userPreferenceString = "";
            }

            int prefStringLength = userPreferenceString.length();
            List<FieldKey> buildKeyList = new ArrayList<FieldKey>();

            // when the prefStringLength is greater than 2
            if (prefStringLength > 2) {
                
                // set the user preference accordingly.
                String revUserPreferenceString = userPreferenceString.substring(1, prefStringLength - 1);
                String[] keys = revUserPreferenceString.split(",");

                for (String key : keys) {
                    buildKeyList.add(FieldKey.getKey(key.trim()));
                }
            }

            if (buildKeyList.size() > 0) {
                PrintTemplateVo.populatePrintFieldCollection(personalProductTemplate, buildKeyList);
            }

            if (personalProductTemplate != null) {
                List<Column> columns = personalProductTemplate.getFields();

                for (Column c : columns) {
                    productFieldsMap.put(c.getFieldKey().getKey(),
                        c.getFieldKey().getLocalizedName(LocaleContextHolder.getLocale()));
                }
            }

            PrintTemplateVo ptvp = DefaultPrintTemplateFactory.availableProductSearchFields();

            if (ptvp != null) {
                if (searchManager.getDisplayedList() != null) {
                    for (String displayed : searchManager.getDisplayedList()) {
                        for (Column column : ptvp.getFields()) {
                            if (column.getFieldKey().getKey().equals(displayed)) {
                                productFieldsMap2.put(column.getFieldKey().getKey(), column.getFieldKey()
                                            .getLocalizedName(locale));
                            }
                        }
                    }

                    if (!productFieldsMap2.isEmpty() && productFieldsMap2 != null) {
                        productFieldsMap = productFieldsMap2;
                    } else {
                        LOG.debug("productFieldsMap2 is null or empty: ");
                    }
                }

            }

            getUser().getSessionPreferences().put(SessionPreferenceType.DEFAULT_PROD_PRINT_TEMPLATE_ID,
                productFieldsMap.keySet().toString());
            LOG.debug("productFieldsMap: " + productFieldsMap.keySet().toString());
            userService.updatePreferences(getUser());

        } // end if (searchManager.currentPrintTemplateId != null

        return productFieldsMap;
    }

    /**
     * creates the selected print template fields map
     *
     * @param searchManager SearchTemplateManager
     * @param locale Locale used in the template construction
     * @param model Model
     * @param request HttpServletRequest
     * @throws ValueObjectValidationException exception
     * @return Map
     */
    @ModelAttribute("ndcFieldsMap")
    public LinkedHashMap<String, String> createTemplateFields2(
        @ModelAttribute("searchManager") SearchTemplateController.SearchTemplateManager searchManager,
        Locale locale,
        Model model,
        HttpServletRequest request) throws ValueObjectValidationException {

        LinkedHashMap<String, String> ndcFieldsMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> ndcFieldsMap2 = new LinkedHashMap<String, String>();

        if (searchManager.currentPrintTemplateId != null
            && searchManager.getCurrentPrintTemplateId().equalsIgnoreCase("PERSONAL_NDC_SEARCH")) {

            PrintTemplateVo personalNdcTemplate = new PrintTemplateVo();
            String userPreferenceString =
                getUser().getSessionPreferences().get(SessionPreferenceType.DEFAULT_NDC_PRINT_TEMPLATE_ID);

            if (userPreferenceString.equalsIgnoreCase("PERSONAL NDC SEARCH")) {
                userPreferenceString = "";
            }

            int prefStringLength = userPreferenceString.length();
            List<FieldKey> buildKeyList = new ArrayList<FieldKey>();

            // when the prefStringLength is greater than 2
            if (prefStringLength > 2) {
                
                // set the user preference accordingly.
                String revUserPreferenceString = userPreferenceString.substring(1, prefStringLength - 1);
                String[] keys = revUserPreferenceString.split(",");

                for (String key : keys) {
                    buildKeyList.add(FieldKey.getKey(key.trim()));
                }
            }

            if (buildKeyList.size() > 0) {
                PrintTemplateVo.populatePrintFieldCollection(personalNdcTemplate, buildKeyList);
            }

            if (personalNdcTemplate != null) {
                List<Column> columns = personalNdcTemplate.getFields();

                for (Column c : columns) {
                    ndcFieldsMap.put(c.getFieldKey().getKey(),
                        c.getFieldKey().getLocalizedName(LocaleContextHolder.getLocale()));
                }
            }

            PrintTemplateVo ptvn = DefaultPrintTemplateFactory.availableNdcSearchFields();

            if (ptvn != null) {
                if (searchManager.getDisplayedList() != null) {
                    for (String displayed : searchManager.getDisplayedList()) {
                        for (Column column : ptvn.getFields()) {
                            if (column.getFieldKey().getKey().equals(displayed)) {
                                ndcFieldsMap2.put(column.getFieldKey().getKey(), column.getFieldKey()
                                            .getLocalizedName(locale));
                            }
                        }
                    }

                    if (!ndcFieldsMap2.isEmpty() && ndcFieldsMap2 != null) {
                        ndcFieldsMap = ndcFieldsMap2;
                    } else {
                        LOG.debug("ndcFieldsMap2 is null or empty: ");
                    }
                }

            }

            getUser().getSessionPreferences().put(SessionPreferenceType.DEFAULT_NDC_PRINT_TEMPLATE_ID,
                ndcFieldsMap.keySet().toString());
            LOG.debug("ndcFieldsMap: " + ndcFieldsMap.keySet().toString());
            userService.updatePreferences(getUser());

        } // end if (searchManager.currentPrintTemplateId != null

        return ndcFieldsMap;
    }

    /**
     * creates the selected print template fields map
     *
     * @param searchManager SearchTemplateManager
     * @param locale Locale
     * @param model Model
     * @param request HttpServletRequest
     * @throws ValueObjectValidationException exception
     * @return Map
     */
    @ModelAttribute("orderableItemFieldsMap")
    public LinkedHashMap<String, String> createTemplateFields3(
        @ModelAttribute("searchManager") SearchTemplateController.SearchTemplateManager searchManager,
        Locale locale,
        Model model,
        HttpServletRequest request) throws ValueObjectValidationException {

        LinkedHashMap<String, String> orderableItemFieldsMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> orderableItemFieldsMap2 = new LinkedHashMap<String, String>();

        if (searchManager.currentPrintTemplateId != null
            && searchManager.getCurrentPrintTemplateId().equalsIgnoreCase("PERSONAL_ORDERABLE_ITEM_SEARCH")) {

            PrintTemplateVo personalOiTemplate = new PrintTemplateVo();
            String userPreferenceString =
                getUser().getSessionPreferences().get(SessionPreferenceType.DEFAULT_OI_PRINT_TEMPLATE_ID);

            if (userPreferenceString.equalsIgnoreCase("PERSONAL ORDERABLE ITEM SEARCH")) {
                userPreferenceString = "";
            }

            int prefStringLength = userPreferenceString.length();
            List<FieldKey> buildKeyList = new ArrayList<FieldKey>();

            if (prefStringLength > 2) {
                String revUserPreferenceString = userPreferenceString.substring(1, prefStringLength - 1);
                String[] keys = revUserPreferenceString.split(",");

                for (String key : keys) {
                    buildKeyList.add(FieldKey.getKey(key.trim()));
                }
            }

            if (buildKeyList.size() > 0) {
                PrintTemplateVo.populatePrintFieldCollection(personalOiTemplate, buildKeyList);
            }

            if (personalOiTemplate != null) {
                List<Column> columns = personalOiTemplate.getFields();

                for (Column c : columns) {
                    orderableItemFieldsMap.put(c.getFieldKey().getKey(),
                        c.getFieldKey().getLocalizedName(LocaleContextHolder.getLocale()));
                }
            }

            PrintTemplateVo ptvo = DefaultPrintTemplateFactory.availableOrderableItemSearchFields();

            if (ptvo != null) {
                if (searchManager.getDisplayedList() != null) {
                    for (String displayed : searchManager.getDisplayedList()) {
                        for (Column column : ptvo.getFields()) {
                            if (column.getFieldKey().getKey().equals(displayed)) {
                                orderableItemFieldsMap2.put(column.getFieldKey().getKey(), column.getFieldKey()
                                            .getLocalizedName(locale));
                            }
                        }
                    }

                    if (!orderableItemFieldsMap2.isEmpty() && orderableItemFieldsMap2 != null) {
                        orderableItemFieldsMap = orderableItemFieldsMap2;
                    } else {
                        LOG.debug("orderableItemFieldsMap2 is null or empty: ");
                    }
                }
            }

            getUser().getSessionPreferences().put(SessionPreferenceType.DEFAULT_OI_PRINT_TEMPLATE_ID,
                orderableItemFieldsMap.keySet().toString());
            LOG.debug("orderableItemFieldsMap: " + orderableItemFieldsMap.keySet().toString());
            userService.updatePreferences(getUser());

        } // end if (searchManager.currentPrintTemplateId != null

        return orderableItemFieldsMap;
    }

    /**
     * Create Manage Template
     *
     * @return SearchTemplateController.SearchTemplateManager
     */
    @ModelAttribute("searchPreferences")
    public SearchTemplateController.SearchTemplateManager createSearchPreferences() {
        SearchTemplateManager searchManager = new SearchTemplateManager();

        return searchManager;

    }

    /**
     * Description
     *
     * @param model Model
     * @param request HttpServletRequest
     * @param locale Locale
     * @param searchPrefs UserController.SearchPreferences
     * @param currentPrintTemplateId String
     * @return String
     * 
     * @throws Exception exception
     */
    @RequestMapping(value = ControllerConstants.PAGE_SEARCH_SETTINGS, method = RequestMethod.GET)
    public String searchSettingsGet(
        Model model,
        HttpServletRequest request,
        Locale locale,
        @ModelAttribute(ControllerConstants.SEARCH_PREFERENCES) SearchTemplateController.SearchTemplateManager searchPrefs,
        @RequestParam(value = "currentPrintTemplateId", required = false) String currentPrintTemplateId) throws Exception {

        pageTrail.clearTrail();
        pageTrail.addPage("searchPrefs", "Search Preferences", true);

        if (searchPrefs.getCurrentPrintTemplateId() != null 
            && !searchPrefs.getCurrentPrintTemplateId().isEmpty()) {
            PrintTemplateVo ptv =
                getPrintTemplateService().retrieve(getUser(),
                    TemplateLocation.valueOf(searchPrefs.getCurrentPrintTemplateId()));
            searchPrefs.setCurrentPrintTemplate(ptv);
        } else {
            LOG.debug("searchPrefs.getCurrentPrintTemplateId() in searchSettings.go is null or empty.");
        }
        

        return SEARCH_PREFERENCES_VIEW;
    }

    /**
     * get searchTemplateService
     * @return the searchTemplateService
     */
    public SearchTemplateService getSearchTemplateService() {
        return searchTemplateService;
    }

    /**
     * set searchTemplateService
     * 
     * @param searchTemplateService the searchTemplateService to set
     */
    public void setSearchTemplateService(SearchTemplateService searchTemplateService) {
        this.searchTemplateService = searchTemplateService;
    }

    /**
     * get the printTemplateService
     * 
     * @return the printTemplateService
     */
    public PrintTemplateService getPrintTemplateService() {
        return printTemplateService;
    }

    /**
     * sets printTemplateService field
     * 
     * @param printTemplateService the printTemplateService to set
     */
    public void setPrintTemplateService(PrintTemplateService printTemplateService) {
        this.printTemplateService = printTemplateService;
    }

    /**
     * get userService
     * 
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * set userService
     * 
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * getSearchTemplate
     *
     * @return SearchTemplateVo
     */
    public SearchTemplateVo getSearchTemplate() {
        return searchTemplate;
    }

    /**
     * setSearchTemplate
     *
     * @param searchTemplate SearchTemplateVo
     */
    public void setSearchTemplate(SearchTemplateVo searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    /**
     * Inner class used to edit saved search/print templates from the form
     *  
     * 
     */
    public static class SearchTemplateManager {

        private List<String> selectedKeys;
        private List<String> addedKeys;
        private List<String> displayedList;
        private String templateName;
        private String currentPrintTemplateId;
        private PrintTemplateVo currentPrintTemplate;
        private String defaultNdcPrintTemplateId;
        private String defaultOiPrintTemplateId;
        private String defaultProdPrintTemplateId;

        /**
         * Empty constructor
         *
         */
        public SearchTemplateManager() {

        }

        /**
         * set the SearchTemplateController selectedKeys value
         * @param selectedKeys the selectedKeys to set
         */
        public void setSelectedKeys(List<String> selectedKeys) {
            
            this.selectedKeys = selectedKeys;
        }

        /**
         * get the SearchTemplateController  selectedKeys value
         * @return the selectedKeys
         */
        public List<String> getSelectedKeys() {
            
            // SearchTemplateContoller selectedKeys value
            return selectedKeys;
        }

        /**
         * set the SearchTemplateController AddedKeys value
         * @param addedKeys the addedKeys to set
         */
        public void setAddedKeys(List<String> addedKeys) {
            this.addedKeys = addedKeys;
        }

        /**
         * Gets the SearchtemplateController AddedKeys value for the SearchTemplateController.
         * @return the addedKeys
         */
        public List<String> getAddedKeys() {
            return addedKeys;
        }

        /**
         * setDisplayedList
         * @param displayedList the displayedList to set for the SearchTemplateController.
         */
        public void setDisplayedList(List<String> displayedList) {
            this.displayedList = displayedList;
        }

        /**
         * getDisplayedList for the SearchTemplateController.
         * @return the displayedList
         */
        public List<String> getDisplayedList() {
            return displayedList;
        }

        /**
         * setTemplateName for the SearchTemplateController.
         * @param templateName the templateName to set
         */
        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        /**
         * getTemplateName for the SearchTemplateController.
         * @return the templateName
         */
        public String getTemplateName() {
            return templateName;
        }

        /**
         * get the currentPrintTemplateId for the SearchTemplateController.
         * 
         * @return the currentPrintTemplateId
         */
        public String getCurrentPrintTemplateId() {
            return currentPrintTemplateId;
        }

        /**
         * get the currentPrintTemplate
         * 
         * @return the currentPrintTemplate
         */
        public PrintTemplateVo getCurrentPrintTemplate() {
            return currentPrintTemplate;
        }

        /**
         * sets currentPrintTemplate field
         * 
         * @param currentPrintTemplate the currentPrintTemplate to set
         */
        public void setCurrentPrintTemplate(PrintTemplateVo currentPrintTemplate) {
            this.currentPrintTemplate = currentPrintTemplate;
        }

        /**
         * sets currentPrintTemplateId field
         * 
         * @param currentPrintTemplateId the currentPrintTemplateId to set
         */
        public void setCurrentPrintTemplateId(String currentPrintTemplateId) {
            this.currentPrintTemplateId = currentPrintTemplateId;
        }

        /**
         * get the defaultProdPrintTemplateId
         * 
         * @return the defaultProdPrintTemplateId
         */
        public String getDefaultProdPrintTemplateId() {
            return defaultProdPrintTemplateId;
        }

        /**
         * sets defaultProdPrintTemplateId field
         * 
         * @param defaultProdPrintTemplateId the defaultProdPrintTemplateId to set
         */
        public void setDefaultProdPrintTemplateId(String defaultProdPrintTemplateId) {
            this.defaultProdPrintTemplateId = defaultProdPrintTemplateId;
        }

        /**
         * get the defaultOiPrintTemplateId
         * 
         * @return the defaultOiPrintTemplateId
         */
        public String getDefaultOiPrintTemplateId() {
            return defaultOiPrintTemplateId;
        }

        /**
         * get the defaultNdcPrintTemplateId
         * 
         * @return the defaultNdcPrintTemplateId
         */
        public String getDefaultNdcPrintTemplateId() {
            return defaultNdcPrintTemplateId;
        }

        /**
         * sets defaultOiPrintTemplateId field
         * 
         * @param defaultOiPrintTemplateId the defaultOiPrintTemplateId to set
         */
        public void setDefaultOiPrintTemplateId(String defaultOiPrintTemplateId) {
            this.defaultOiPrintTemplateId = defaultOiPrintTemplateId;
        }

        /**
         * sets defaultNdcPrintTemplateId field
         * 
         * @param defaultNdcPrintTemplateId the defaultNdcPrintTemplateId to set
         */
        public void setDefaultNdcPrintTemplateId(String defaultNdcPrintTemplateId) {
            this.defaultNdcPrintTemplateId = defaultNdcPrintTemplateId;
        }
    }
}
