/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.DomainValidationException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.Column;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.common.vo.validator.SearchTemplateValidator;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * The controller for simple and data elements search
 */
@Controller("advancedSearchController")
public class AdvancedSearchController extends AbstractSearchController {

    private static final Logger LOG = Logger.getLogger(AdvancedSearchController.class);
    private static final String MANAGE_TEMPLATES = "manageTemplates";
    private static final String AVAILABLE_FIELDS_MAP = "availableFieldsMap";

    @Autowired
    private UserService userService;

    /**
     * Default constructor
     *
     */
    public AdvancedSearchController() {
        super();
    }

    /**
     * Description
     *
     * 
     * @return SearchTemplateVo
     */
    @ModelAttribute(ControllerConstants.SEARCH_TEMPLATE_KEY)
    public SearchTemplateVo createSearchTemplate() {
        return new SearchTemplateVo();
    }

    /**
     * creates the template type map
     *
     * @param locale Locale
     * @return SortedMap
     */
    @ModelAttribute("templateTypeMap")
    public SortedMap<TemplateType, String> createTemplateTypeMap(Locale locale) {
        SortedMap<TemplateType, String> templateTypeMap = new TreeMap<TemplateType, String>();
        Collection<TemplateType> templateTypes;

        if (getUser().hasRole(Role.PSS_PPSN_SUPERVISOR)) {
            templateTypes = TemplateType.getNationalValues();
        } else {
            templateTypes = TemplateType.getPersonalValues();
        }

        for (TemplateType element : templateTypes) {
            templateTypeMap.put(element,
                    getMessageSource().getMessage("TemplateType." + element.name(), null, locale));
        }

        return templateTypeMap;
    }

    /**
     * creates the available print templates map
     *
     * @param locale Locale
     * @param manageTemplates contains print template
     * @return SortedMap
     */
    @ModelAttribute(AVAILABLE_FIELDS_MAP)
    public SortedMap<String, String> createAvailableFields(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Locale locale) {
        SortedMap<String, String> availableFieldsMap = new TreeMap<String, String>();

        List<SearchFieldVo> templateFields = new ArrayList<SearchFieldVo>();

        for (SearchFieldVo sfVo : getSearchTemplate().getSearchCriteria().getAdvancedSearchFields()) {
            Boolean isDisplayed = false;

            if (!(FieldKey.SELECT.equals(sfVo.getFieldKey()) || FieldKey.SEARCH_NO_FIELDS.equals(sfVo.getFieldKey())
                || FieldKey.SEARCH_ALL_FIELDS.equals(sfVo.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                .equals(sfVo.getFieldKey()))) {

                if (templateFields == null) {
                    availableFieldsMap.put(sfVo.getFieldKey().getKey(), sfVo.getLocalizedName(locale));
                } else {
                    for (SearchFieldVo displayed : templateFields) {
                        if (sfVo.getFieldKey().equals(displayed.getFieldKey())) {
                            isDisplayed = true;
                        }
                    }

                    if (!isDisplayed) {
                        availableFieldsMap.put(sfVo.getFieldKey().getKey(), sfVo.getLocalizedName(locale));
                    }
                }

            }
        }

        return availableFieldsMap;
    }

    /**
     * creates the selected print template fields map
     * 
     * @param manageTemplates TemplateManager
     * @param locale Locale
     * @return Map
     */
    @ModelAttribute("templateFieldsMap")
    public LinkedHashMap<String, String> createTemplateFields(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Locale locale) {

        List<SearchFieldVo> templateFields = new ArrayList<SearchFieldVo>();

        LinkedHashMap<String, String> templateFieldsMap = new LinkedHashMap<String, String>();

        if (getSearchTemplate().getPrintTemplate() != null) {
            if (manageTemplates.getDisplayedList() == null) {
                for (Column column : getSearchTemplate().getPrintTemplate().getFields()) {

                    if (!(FieldKey.SELECT.equals(column.getFieldKey())
                        || FieldKey.SEARCH_NO_FIELDS.equals(column.getFieldKey())
                        || FieldKey.SEARCH_ALL_FIELDS.equals(column.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                        .equals(column.getFieldKey()))) {

                        // add the search Field VO to the template fields with the appropriate columns
                        templateFields.add(new SearchFieldVo(column.getFieldKey(), getSearchTemplate().getSearchCriteria()
                            .getEntityType()));
                        templateFieldsMap.put(column.getFieldKey().getKey(), column.getFieldKey().getLocalizedName(locale));
                    }
                }
            } else {
                for (String displayed : manageTemplates.getDisplayedList()) {
                    Boolean isMatched = false;

                    for (SearchFieldVo element : getSearchTemplate().getSearchCriteria().getAdvancedSearchFields()) {

                        if (element.getFieldKey().getKey().equals(displayed)) {
                            templateFields.add(new SearchFieldVo(element.getFieldKey(), getSearchTemplate()
                                .getSearchCriteria()
                                .getEntityType()));
                            templateFieldsMap.put(element.getFieldKey().getKey(), element.getFieldKey()
                                .getLocalizedName(locale));
                            isMatched = true;
                        }
                    }

                    if (!isMatched) {

                        for (Column column : getSearchTemplate().getPrintTemplate().getFields()) {

                            if (!(FieldKey.SELECT.equals(column.getFieldKey())
                                || FieldKey.SEARCH_NO_FIELDS.equals(column.getFieldKey())
                                || FieldKey.SEARCH_ALL_FIELDS.equals(column.getFieldKey()) 
                                || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                                .equals(column.getFieldKey()))) {

                                if (column.getFieldKey().getKey().equals(displayed)) {

                                    templateFieldsMap.put(column.getFieldKey().getKey(), column.getFieldKey()
                                        .getLocalizedName(locale));
                                }
                            }
                        }
                    }
                }
            }
        }

        return templateFieldsMap;
    }

    /**
     * Create Manage TEmplate
     *
     * @return AdvancedSearchController.TemplateManager
     */
    @ModelAttribute(MANAGE_TEMPLATES)
    public AdvancedSearchController.TemplateManager createManageTemplates() {
        TemplateManager manageTemplates = new TemplateManager();

//        if (getSearchTemplate().getPrintTemplate() != null) {
//            manageTemplates.setSelectedList(getSearchTemplate().getPrintTemplate().getFields());
//        }

        return manageTemplates;

    }

    /**
     * 
     * Gets the page for viewing and fetches the search results
     *
     * @param searchCriteria SearchCriteriaVO
     * @param isFirstRun String
     * @param addTerm boolean
     * @param setDefault boolean
     * @param hasEntityChanged boolean
     * @param currentSearchTemplateId String
     * @param model the model
     * 
     * @return String the view name
     * @throws ValidationException ValidationException
     * 
     */
    @RequestMapping(value = ControllerConstants.PAGE_SEARCH_ADV, method = RequestMethod.GET)
    public String advSearchItems(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = "isFirstRun", required = false) String isFirstRun,
        @RequestParam(value = "addTerm", required = false) boolean addTerm,
        @RequestParam(value = "setDefault", required = false) boolean setDefault,
        @RequestParam(value = "hasEntityChanged", required = false) boolean hasEntityChanged,
        @RequestParam(value = "currentSearchTemplateId", required = false) String currentSearchTemplateId,
        Model model) throws ValidationException {

        pageTrail.addPage("advSearch", "Advanced Search", true);

        SearchTemplateVo searchTemplate = getSearchTemplate();

        //Check and get default template id
        //Get default template name

        if (setDefault) {
            currentSearchTemplateId = searchTemplate.getId();
            setCurrentTemplateAsDefault(currentSearchTemplateId);
        }

        // Check to see if there are previous terms in flowscope, if not store them
        List<SearchTermVo> previousTerms;

        if (isFirstRun == null) {
            previousTerms = searchTemplate.getSearchCriteria().getSearchTerms();
            flowScope.put("previousSearchTerms", previousTerms);
        } else {
            previousTerms = (List<SearchTermVo>) flowScope.get("previousSearchTerms");
            flowScope.put("previousSearchTerms", searchCriteria.getSearchTerms());
        }

        if (searchTemplate.getSearchCriteria().getEntityType() != null
            && searchTemplate.getSearchCriteria().getEntityType() != searchCriteria.getEntityType() && isFirstRun != null) {
            searchTemplate.setPrintTemplate(null);

            List<SearchTermVo> removable = new ArrayList<SearchTermVo>();

            for (SearchTermVo term : searchCriteria.getSearchTerms()) {
                term.setValue(null);

                if (removable.size() < 1) {
                    removable.add(term);
                }

            }

            searchCriteria.getSearchTerms().retainAll(removable);
            searchCriteria.getSearchTerms().get(0).setSearchField(searchCriteria.getSearchFields().get(0));
        }

        if (isFirstRun == null) {

            String defaultTemplateId = retrieveDefaultTemplateId(getUser());

            //retrieve the currently selected template if available
            //else set to default if available or create a blank template
            if (currentSearchTemplateId != null) {

                if (getSearchTemplate().getId() == null || !getSearchTemplate().getId().equals(currentSearchTemplateId)) {
                    setSearchTemplate(getSearchTemplateService().retrieve(currentSearchTemplateId));
                }

                searchTemplate = getSearchTemplate();

                searchCriteria = createSearchCriteria(searchTemplate.getEntityType());
                searchTemplate.getSearchCriteria().setSearchFieldsMap(searchCriteria.getSearchFieldsMap());
                searchCriteria = searchTemplate.getSearchCriteria();

                setSelectedTemplate();

                model.addAttribute("printTemplate", searchTemplate.getPrintTemplate());
                model.addAttribute("searchCriteria", searchTemplate.getSearchCriteria());

            } else {

                if (defaultTemplateId != null) {
                    currentSearchTemplateId = defaultTemplateId;
                    setSearchTemplate(getSearchTemplateService().retrieve(currentSearchTemplateId));
                    searchTemplate = getSearchTemplate();
                    previousTerms = getSearchTemplate().getSearchCriteria().getSearchTerms();
                } else {
                    searchTemplate.setSearchCriteria(searchCriteria);
                }


                searchCriteria = createSearchCriteria(searchTemplate.getEntityType());
                searchTemplate.getSearchCriteria().setSearchFieldsMap(searchCriteria.getSearchFieldsMap());
                searchCriteria = searchTemplate.getSearchCriteria();
                searchTemplate.setSelected(true);
                searchCriteria.setSearchTerms(previousTerms);
                model.addAttribute("searchCriteria", searchCriteria);
            }

        }

        searchTemplate.setSearchCriteria(searchCriteria);
        searchTemplate.getSearchCriteria().isAdvanced();

        if (addTerm) {
            searchTemplate.getSearchCriteria().getSearchTerms().add(SearchTermVo.newEmptyInstance(SearchDomain.ADVANCED));
        }

        if (currentSearchTemplateId == null) {
            List<SearchTermVo> currTerms = searchTemplate.getSearchCriteria().getSearchTerms();

            if (previousTerms.size() <= currTerms.size()) {
                for (int i = 0; i < previousTerms.size(); i++) {
                    if (!previousTerms.get(i).getFieldKey().equals(currTerms.get(i).getFieldKey())) {
                        searchTemplate.getSearchCriteria().getSearchTerms().get(i).setValue(null);
                    }
                }

            }
        }

        //searchTemplate
        model.addAttribute("setDefault", false);
        model.addAttribute("addTerm", false);
        model.addAttribute("templateName", searchTemplate.getTemplateName());
        model.addAttribute("templateNotes", searchTemplate.getNotes());
        model.addAttribute("templateType", searchTemplate.getTemplateType());

        model.addAttribute("searchTemplate", searchTemplate);

        return "advanced.search.spring";
    }

    /**
     * Updates the advanced search page, displays the search results
     *
     * @param searchCriteria 
     * @param model 
     * @return String
     * @throws ValidationException exception
     */
    @RequestMapping(value = ControllerConstants.PAGE_SEARCH_UPDATE_ADV, method = { RequestMethod.GET })
    public String advSearchUpdate(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        Model model) throws ValidationException {


        pageTrail.addPage("advSearch", "Advanced Search");

        SearchTemplateVo searchTemplate = getSearchTemplate();

        prepareSearchCriteria(searchTemplate.getSearchCriteria());

        SearchCriteriaVo sC = new SearchCriteriaVo(SearchDomain.ADVANCED, searchCriteria.getEntityType(), getEnvironment());
        sC = searchCriteria;

        searchTemplate.getSearchCriteria().setSearchTerms(sC.getSearchTerms());

        if (sC.getSearchTerms().get(0).getFieldKey().equals(FieldKey.SEARCH_NO_FIELDS)) {
            searchTemplate.getSearchCriteria().setItemStatus(sC.getItemStatus());
            searchTemplate.getSearchCriteria().setRequestStatus(sC.getRequestStatus());
            searchTemplate.setEntityType(sC.getEntityType());
        }

        List<ManagedItemVo> items = new ArrayList<ManagedItemVo>();

        try {
            items = getManagedItemService().search(searchTemplate.getSearchCriteria());
        } catch (DomainValidationException e) {
            List<String> errorsList = new ArrayList<String>();
            errorsList.add(e.getLocalizedMessage());
            flashScope.put(ERRORS, errorsList);
        }

        //The search blanks out the search terms on the template, so set them again for saving.
        searchTemplate.getSearchCriteria().setSearchTerms(sC.getSearchTerms());
        searchTemplate.getSearchCriteria().setItemStatus(sC.getItemStatus());
        searchTemplate.getSearchCriteria().setRequestStatus(sC.getRequestStatus());
        searchTemplate.getSearchCriteria().setEntityType(sC.getEntityType());

        PrintTemplateVo printTemplate;

        if (searchTemplate.getPrintTemplate() == null) {

            printTemplate = retrievePrintTemplate(searchTemplate.getSearchCriteria(), false);
            searchTemplate.setPrintTemplate(printTemplate);
            searchTemplate.getSearchCriteria().setPrintTemplate(printTemplate);

        } else {

            searchTemplate.getSearchCriteria().setPrintTemplate(searchTemplate.getPrintTemplate());
        }

        searchTemplate.setSearchCriteria(searchTemplate.getSearchCriteria());
        searchTemplate.getSearchCriteria().isAdvanced();
        searchTemplate.setPrintTemplate(retrievePrintTemplate(searchTemplate.getSearchCriteria(), false));

        model.addAttribute("printTemplate", searchTemplate.getPrintTemplate());
        model.addAttribute("items", items);
        model.addAttribute("setDefault", false);
        model.addAttribute("addTerm", false);
        model.addAttribute("templateName", searchTemplate.getTemplateName());
        model.addAttribute("templateNotes", searchTemplate.getNotes());
        model.addAttribute("templateType", searchTemplate.getTemplateType());
        model.addAttribute("searchTemplate", searchTemplate);

        return "advanced.search.results";
    }

    /**
     * Adds an empty term to the advanced search criteria 
     *
     * @param searchCriteria SearchCriteriaVo
     * @param tmpId Search Template ID 
     * @return String the redirect
     * @throws ItemNotFoundException 
     */
    @RequestMapping(value = ControllerConstants.PAGE_RETRIEVE_TEMPLATE, method = RequestMethod.GET)
    public String advSearchRetrieve(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = "currentSearchTemplateId", required = true) String tmpId) throws ItemNotFoundException {

        return REDIRECT + ControllerConstants.PAGE_SEARCH_ADV + "?currentSearchTemplateId=" + tmpId + "";
    }

    /**
     * Adds an empty term to the advanced search criteria 
     *
     * @param searchCriteria SearchCriteriaVo
     * 
     * @return String the redirect
     */
    @RequestMapping(value = "/addterm.go", method = RequestMethod.GET)
    public String advSearchAddTerm(@ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria) {

        flowScope.put("previousSearchTerms", searchCriteria.getSearchTerms());
        getSearchTemplate().getSearchCriteria().getSearchTerms().add(SearchTermVo.newEmptyInstance(SearchDomain.ADVANCED));

        return REDIRECT + ControllerConstants.PAGE_SEARCH_ADV;
    }

    /**
     * 
     * Saves the search template
     *
     * @param searchCriteria SearchCriteriaVO
     * @param manageTemplates TemplateManager
     * @param templateType String 
     * @param notes String
     * @param templateName String
     * @param model the model
     * 
     * @return String the view name
     * @throws MissingResourceException MissingResourceException
     * @throws ValueObjectValidationException ValueObjectValidationException
     * @throws ItemNotFoundException ItemNotFoundException
     * 
     */
    @RequestMapping(value = "/savetemplate.go", method = RequestMethod.GET)
    public String advSearchSaveTemplate(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        @RequestParam(value = "type", required = false) TemplateType templateType,
        @RequestParam(value = "notes", required = false) String notes,
        @RequestParam(value = "template", required = false) String templateName,
        Model model) throws MissingResourceException, ValueObjectValidationException, ItemNotFoundException {

        pageTrail.addPage("saveTemplate", "Save Template");
        String url = new String("/advancedcriteria.go");

        setSearchTemplates(getSearchTemplateService().retrieve(getUser()));
        manageTemplates.setTemplatesList(getSearchTemplates());

        if (compareNames(templateName, manageTemplates)) {
            manageTemplates.setTemplateName(templateName);
            manageTemplates.setTemplateNotes(notes);
            manageTemplates.setTemplateType(templateType);

            url = "advanced.search.warn";

        } else {
            SearchTemplateVo newTemplate = new SearchTemplateVo();

            if (getUser().hasRole(Role.PSS_PPSN_SUPERVISOR)) {
                LOG.info("Template updated");

//                newTemplate.setTemplateType(TemplateType.NATIONAL);
                newTemplate.setTemplateType(templateType);
            } else {
                newTemplate.setTemplateType(TemplateType.PERSONAL);
            }

            newTemplate.setEntityType(getSearchTemplate().getEntityType());
            newTemplate.setTemplateName(templateName);
            newTemplate.setUser(getUser());
            newTemplate.setDefault(false);
            newTemplate.setNotes(notes);
            newTemplate.setId(null);
            newTemplate.setSearchCriteria(getSearchTemplate().getSearchCriteria());
            newTemplate.setPrintTemplate(getSearchTemplate().getPrintTemplate());


            getSearchTemplateService().create(getUser(), newTemplate, true);
            String currentSearchTemplateId = newTemplate.getId();
            setSearchTemplates(getSearchTemplateService().retrieve(getUser()));
            flowScope.put("previousSearchTerms", getSearchTemplateService().retrieve(currentSearchTemplateId)
                .getSearchCriteria().getSearchTerms());

            model.addAttribute(MANAGE_TEMPLATES, manageTemplates);

            url =
                REDIRECT + ControllerConstants.PAGE_SEARCH_ADV + "?currentSearchTemplateId=" + currentSearchTemplateId + "";
        }

        return url;
    }

    /**
     * Checks for existing name
     * @param templateName String Template Name
     * @param manageTemplates TemplateManager
     * @return true if match is found
     */
    private boolean compareNames(String templateName, TemplateManager manageTemplates) {

        if (templateName != null && manageTemplates.getTemplatesList() != null) {

            for (SearchTemplateVo template : manageTemplates.getTemplatesList()) {

                if (template.getTemplateName().equals(templateName)) {
                    return true;
                }
            }

            return false;
        }

        return false;
    }

//    private boolean compareNames(String ptemplateName, String psearchTemplateName) {
//        if (ptemplateName != null && psearchTemplateName != null) {
//            if (psearchTemplateName.toString().toUpperCase().equals(ptemplateName.toString().toUpperCase())) {
//                return true;
//            }
//        }
//
//        return false;
//
//    }

    /**
     * 
     * Overwrites and saves changes to the current search template
     *
     * @param searchCriteria SearchCriteriaVO
     * @param model the model
     * @param manageTemplates TemplateManager 
     * @return String the view name
     * @throws ValueObjectValidationException ValueObjectValidationException
     * @throws MissingResourceException MissingResourceException 
     */
    @RequestMapping(value = "/overwritetemplate.go", method = RequestMethod.GET)
    public String advSearchOverwriteTemplate(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Model model) throws MissingResourceException, ValueObjectValidationException {

        pageTrail.addPage("overwriteTemplate", "Overwrite Template");

        getSearchTemplate().setTemplateName(manageTemplates.getTemplateName());
        getSearchTemplate().setNotes(manageTemplates.getTemplateNotes());
        getSearchTemplate().setTemplateType(manageTemplates.getTemplateType());

        getSearchTemplateService().create(getUser(), getSearchTemplate(), true);
        String currentSearchTemplateId = getSearchTemplate().getId();

        return REDIRECT + ControllerConstants.PAGE_SEARCH_ADV + "?currentSearchTemplateId=" + currentSearchTemplateId + "";
    }

    /**
     * 
     * Cancels the overwrite of the current search template and redirects
     *
     * @param searchCriteria SearchCriteriaVO
     * @param model the model
     * 
     * @return String the view name 
     */
    @RequestMapping(value = "/overwritetemplate.cancel.go", method = RequestMethod.GET)
    public String advSearchCancelTemplateOverwrite(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        Model model) {

        return REDIRECT + pageTrail.goToPreviousUrl();
    }

    /**
     * 
     * Manage saved search template(s)
     *
     * @param manageTemplates AdvancedSearchController.TemplateManager
     * @param model the model
     * 
     * @return String the view name
     * 
     */
    @RequestMapping(value = ControllerConstants.PAGE_MANAGE_SEARCH_TEMPLATES, method = RequestMethod.GET)
    public String manageSavedTemplates(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Model model) {

        pageTrail.addPage("manageSearchTemplates", "Manage Search Templates");

        try {
            setSearchTemplates(getSearchTemplateService().retrieve(getUser()));
            String defaultId = retrieveDefaultTemplateId(getUser());

            if (defaultId != null) {

                for (SearchTemplateVo template : getSearchTemplates()) {

                    if (template.getId().equals(defaultId)) {
                        template.setDefault(true);
                    } else {
                        template.setDefault(false);
                    }
                }
            }

            manageTemplates.setTemplatesList(getSearchTemplates());
        } catch (MissingResourceException e) {
            LOG.error("Error Retrieving Search Templates: " + e.getMessage());
        }

        return "advanced.search.templates";
    }

    /**
     * 
     * Removes selected search template(s) in this AdvancedSearchController.
     *
     * @param manageTemplates AdvancedSearchController.TemplateManager
     * @param model the model
     * 
     * @return String the view name
     * @throws  ValidationException ValidationException
     * 
     */
    @RequestMapping(value = "/removetemplate.go", method = RequestMethod.GET)
    public String manageSavedTemplatesRemove(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Model model) throws ValidationException {

        markSelectedTemplate(manageTemplates, getSearchTemplates());

        removeSearchTemplates();
        manageTemplates.setTemplatesList(getSearchTemplates());

        return "advanced.search.templates";
    }

    /**
     * 
     * set default search template(s)
     *
     * @param manageTemplates TemplateManager
     * @param model the model
     * 
     * @return String the view name
     * @throws CloneNotSupportedException CloneNotSupportedException
     * @throws ValidationException ValidationException
     * 
     */
    @RequestMapping(value = "/setasdefault.go", method = RequestMethod.GET)
    public String manageSavedTemplatesDefault(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Model model) throws ValidationException, CloneNotSupportedException {

        markSelectedTemplate(manageTemplates, getSearchTemplates());

        setAsDefaultSearchTemplate();
        manageTemplates.setTemplatesList(getSearchTemplates());

        return "advanced.search.templates";
    }

    /**
     * 
     * Removes selected search template(s)
     *
     * @param manageTemplates AdvancedSearchController.TemplateManager
     * @param model the model
     * 
     * @return String the view name
     * @throws ValidationException ValidationException
     *  
     */
    @RequestMapping(value = "/removeasdefault.go", method = RequestMethod.GET)
    public String manageSavedTemplateUnDefault(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Model model) throws ValidationException {

        markSelectedTemplate(manageTemplates, getSearchTemplates());

        unDefaultSearchTemplate();
        manageTemplates.setTemplatesList(getSearchTemplates());

        return "advanced.search.templates";
    }

    /**
     * markSelectedTemplate
     *
     * @param manageTemplates TemplateManager
     * @param temps List
     */
    private void markSelectedTemplate(TemplateManager manageTemplates, List<SearchTemplateVo> temps) {
        int index = 0;

        for (SearchTemplateVo template : temps) {

            if (manageTemplates.getTemplatesList().get(index).isSelected()) {
                template.setSelected(true);
            }

            index++;
        }
    }

    /**
     * managePrintTemplate
     * @param manageTemplates TemplateManager
     * @param model the model
     * @param locale locale
     * 
     * @return String the view name
     *  
     */
    @RequestMapping(value = "/manageprinttemplate.go", method = RequestMethod.GET)
    public String managePrintTemplate(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Locale locale, Model model) {

        pageTrail.addPage("managePrintTemplates", "Manage Print Templates");

        SortedMap<String, String> availableFieldsMap = new TreeMap<String, String>();
        List<SearchFieldVo> templateFields = new ArrayList<SearchFieldVo>();
        LinkedHashMap<String, String> templateFieldsMap = new LinkedHashMap<String, String>();

        for (Column column : getSearchTemplate().getPrintTemplate().getFields()) {

            if (!(FieldKey.SELECT.equals(column.getFieldKey())
                || FieldKey.SEARCH_NO_FIELDS.equals(column.getFieldKey())
                || FieldKey.SEARCH_ALL_FIELDS.equals(column.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                .equals(column.getFieldKey()))) {

                templateFields.add(new SearchFieldVo(column.getFieldKey(), getSearchTemplate().getSearchCriteria()
                    .getEntityType()));
                templateFieldsMap.put(column.getFieldKey().getKey(), column.getFieldKey().getLocalizedName(locale));
            }
        }

        for (SearchFieldVo element : getSearchTemplate().getSearchCriteria().getAdvancedSearchFields()) {
            Boolean isDisplayed = false;

            if (!(FieldKey.SELECT.equals(element.getFieldKey()) || FieldKey.SEARCH_NO_FIELDS.equals(element.getFieldKey())
                || FieldKey.SEARCH_ALL_FIELDS.equals(element.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                .equals(element.getFieldKey()))) {

                if (templateFields == null) {
                    availableFieldsMap.put(element.getFieldKey().getKey(), element.getLocalizedName(locale));
                } else {
                    for (SearchFieldVo displayed : templateFields) {

                        if (element.getFieldKey().equals(displayed.getFieldKey())) {
                            isDisplayed = true;
                        }
                    }

                    if (!isDisplayed) {
                        availableFieldsMap.put(element.getFieldKey().getKey(), element.getLocalizedName(locale));
                    }
                }
            }
        }

        model.addAttribute(AVAILABLE_FIELDS_MAP, availableFieldsMap);

        return "advanced.search.print.template";
    }

    /**
     * managePrintTemplate
     *
     * @param manageTemplates TemplateManager
     * @param model the model
     * 
     * @return String the view name
     *  
     */
    @RequestMapping(value = "/manageprinttemplate.submit.go", method = RequestMethod.POST)
    public String managePrintTemplateSubmit(
        @ModelAttribute(MANAGE_TEMPLATES) AdvancedSearchController.TemplateManager manageTemplates,
        Model model) {

        List<FieldKey> tempFields = new ArrayList<FieldKey>();

        tempFields.add(FieldKey.SELECT);

        try {
            for (String displayed : manageTemplates.getDisplayedList()) {

                Boolean isMatched = false;

                for (Column column : getSearchTemplate().getPrintTemplate().getFields()) {
                    if (!(FieldKey.SELECT.equals(column.getFieldKey())
                        || FieldKey.SEARCH_NO_FIELDS.equals(column.getFieldKey())
                        || FieldKey.SEARCH_ALL_FIELDS.equals(column.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                        .equals(column.getFieldKey()))) {

                        if (column.getFieldKey().getKey().equals(displayed)) {
                            tempFields.add(column.getFieldKey());
                            isMatched = true;
                        }
                    }
                }

                if (!isMatched) {
                    for (SearchFieldVo element : getSearchTemplate().getSearchCriteria().getAdvancedSearchFields()) {
                        if (element.getFieldKey().getKey().equals(displayed)) {
                            tempFields.add(element.getFieldKey());
                        }
                    }
                }
            }

            tempFields = FieldKeyUtility.removeDuplicateWithOrder(tempFields);

        } catch (Exception e) {
            LOG.error("No Selected FieldKeys found: " + e.getMessage());
        }

        PrintTemplateVo printTemplateVo = new PrintTemplateVo();
        printTemplateVo.setTemplateLocation(retrieveTemplateLocation());
        PrintTemplateVo.populatePrintFieldCollection(printTemplateVo, tempFields);
        getSearchTemplate().setPrintTemplate(printTemplateVo);
        getSearchTemplate().getSearchCriteria().setPrintTemplate(printTemplateVo);

        StringBuilder url = new StringBuilder(REDIRECT);
        url.append(ControllerConstants.PAGE_SEARCH_ADV);

        if (getSearchTemplate().getId() != null) {
            url.append("?currentSearchTemplateId=");
            url.append(getSearchTemplate().getId());
        }

        return url.toString();
    }

    /**
     * 
     * Cancels the manage print template
     * 
     * @return To the previous screen
     */
    @RequestMapping(value = "/manageprinttemplate.cancel.go", method = RequestMethod.POST)
    public String managePrintTemplateCancel() {

        return REDIRECT + pageTrail.goToPreviousUrl();
    }

//    /**
//     * managePrintTemplate
//     *
//     * @param manageTemplates TemplateManager
//     * @param model the model
//     * 
//     * @return String the view name
//     * @throws Exception 
//     */
//    @RequestMapping(value = "/manageprinttemplate.add.go", method = RequestMethod.GET)
//    public String managePrintTemplateAdd(
//        @ModelAttribute("manageTemplates") AdvancedSearchController.TemplateManager manageTemplates,
//        Model model) throws Exception {
//
//        pageTrail.addPage("managePrintTemplatesAdd", "Manage Print Templates Edit", true);
//
//        return "advanced.search.print.template";
//    }

//    /**
//     * managePrintTemplate
//     *
//     * @param manageTemplates TemplateManager
//     * @param model the model
//     * 
//     * @return String the view name
//     * @throws Exception 
//     */
//    @RequestMapping(value = "/manageprinttemplate.remove.go", method = RequestMethod.GET)
//    public String managePrintTemplateRemove(
//        @ModelAttribute("manageTemplates") AdvancedSearchController.TemplateManager manageTemplates,
//        Model model) throws Exception {
//
//        pageTrail.addPage("managePrintTemplatesSubmit", "Manage Print Templates Submit", true);
//
//        List<FieldKey> tempFields = new ArrayList<FieldKey>();
//        tempFields.add(FieldKey.SELECT);
//        
//        List<FieldKey> tempRemovables = new ArrayList<FieldKey>();
//        
//        try {
//            
//            for (Column column : getSearchTemplate().getPrintTemplate().getFields()) {
//            
//            //keys to remove
//                for (String selected : manageTemplates.getSelectedKeys()) {
//                
//                    if (column.getFieldKey().getKey().equals(selected)) {
//                        
//                        tempRemovables.add(column.getFieldKey());
//                    }
//                }
//            
//                tempFields.add(column.getFieldKey());
//            }
//            
//            tempFields.removeAll(tempRemovables);  
//            manageTemplates.getSelectedKeys().removeAll(manageTemplates.getSelectedKeys());
//            
//        } catch (Exception e) {
//            
//            LOG.error("No Selected FieldKeys found: " + e.getMessage());
//        }
//
//        return "advanced.search.print.template";
//    }

    /**
     * Removes selected search templates
     * @throws ValidationException 
     *  
     */
    private void removeSearchTemplates() throws ValidationException {

        List<SearchTemplateVo> removable = new ArrayList<SearchTemplateVo>();

        for (SearchTemplateVo template : getSearchTemplates()) {
            if (template.isSelected()) {
                getSearchTemplateService().delete(getUser(), template);

                if (template.isDefault()) {
                    setDefaultSessionTemplate(getUser(), null);
                }

                removable.add(template);

            }
        }

        getSearchTemplates().removeAll(removable);
    }

    /**
     * Sets the default search. Now checks to make sure that only one search is
     * selected to be set as defect.
     * @throws ValidationException ValidationException
     * @throws CloneNotSupportedException CloneNotSupportedException
     *  
     */
    private void setAsDefaultSearchTemplate() throws ValidationException, CloneNotSupportedException {

        boolean foundDefault = false;

        for (SearchTemplateVo template : getSearchTemplates()) {
            if (template.isSelected() && foundDefault) {

                @SuppressWarnings("unused")
                boolean doNothing = true;

            } else if (template.isSelected()) {

                setDefaultSessionTemplate(getUser(), template);
                setSearchTemplate((SearchTemplateVo) template.clone());

                //setTemplateName(getSearchTemplate().getTemplateName());
                foundDefault = true;
                template.setSelected(false);
                template.setDefault(true);

            } else {
                template.setDefault(false);
            }
        }
    }

    /**
     * Sets the default search. Now checks to make sure that only one search is
     * selected to be set as defect.
     * @param currentSearchTemplateId template id returned from page
     * @throws ValidationException 
     * @throws ValidationException 
     */
    private void setCurrentTemplateAsDefault(String currentSearchTemplateId) throws ValidationException {

        boolean foundDefault = false;

        // These aren't coming back from manage search templates, so set current to default
        if (getSearchTemplates().isEmpty()) {
            setDefaultSessionTemplate(getUser(), getSearchTemplate());
        }

        // Otherwise, find the selected search and place it into context
        for (SearchTemplateVo template : getSearchTemplates()) {
            if (template.isSelected() && foundDefault) {

                @SuppressWarnings("unused")
                boolean doNothing = true;

            } else if (template.isSelected() || template.getId().equals(currentSearchTemplateId)) {

                setDefaultSessionTemplate(getUser(), getSearchTemplate());
                template = getSearchTemplate();

                //setTemplateName(getSearchTemplate().getTemplateName());
                foundDefault = true;

            } else {
                template.setDefault(false);
            }
        }
    }

    /**
     * Removes default search as default
     * @throws ValidationException exception
     * 
     */
    private void unDefaultSearchTemplate() throws ValidationException {

        setDefaultSessionTemplate(getUser(), null);

        // This is only done through the manage search templates screen
        for (SearchTemplateVo template : getSearchTemplates()) {
            if (template.isSelected()) {
                template.setDefault(false);
                template.setSelected(false);

            }
        }

        getUser().getSessionPreferences().remove(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID);

    }

    /**
     * Sets the default search preferences.
     * 
     * @param user
     *            object
     * @param template
     *            to set as default
     * 
     * @throws ValidationException
     *             if search template does not validate
     */
    public void setDefaultSessionTemplate(UserVo user, SearchTemplateVo template) throws ValidationException {
        if (template == null) {
            user.getSessionPreferences().remove(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID);
        } else if (template.getId() == null) {
            SearchTemplateValidator validator =
                (SearchTemplateValidator) FieldKey.SEARCH_TEMPLATE.newValidatorInstance();
            validator.validateDefault(template, getUser(), getEnvironment());
        } else {

            // Set selected search as default in the user object
            user.getSessionPreferences().put(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID, template.getId());
            template.setDefault(true);
        }

        // Saving the preference change to the database
        userService.updatePreferences(user);

    }

    /**
     * retrieves the default template id from user preferences
     * 
     * @param user
     *            object
     * @return id of template
     */
    public String retrieveDefaultTemplateId(UserVo user) {

        String tmpDefaultTemplateId = null;

        tmpDefaultTemplateId = user.getSessionPreferences().get(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID);

        return tmpDefaultTemplateId;
    }

    /**
     * Sets the current template to selected and deselects all other saved searches
     */
    private void setSelectedTemplate() {

        getSearchTemplate().setSelected(true);

        if (getSearchTemplates().isEmpty()) {

            try {
                setSearchTemplates(getSearchTemplateService().retrieve(getUser()));
            } catch (MissingResourceException e) {
                LOG.error("Missing Resource Exception in setSelectedTemplate: " + e);
            }
        }

        for (SearchTemplateVo template : getSearchTemplates()) {

            if (template.getId().equals(getSearchTemplate().getId())) {
                template.setSelected(true);
            } else if (template.isSelected()) {
                template.setSelected(false);
            } else {
                template.setSelected(false);
            }
        }

    }

    /**
     * 
     * Gets the page for viewing and fetches the search results
     *
     * @param searchCriteria SearchCriteriaVO
     * @param currentId String
     * @param model the model
     * 
     * @return String the view name 
     */
    @RequestMapping(value = "/retrievetemplate.go", method = RequestMethod.GET)
    public String retrieveTemplate(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = "currentSearchTemplateId", required = false) String currentId,
        Model model) {

        return REDIRECT + ControllerConstants.PAGE_SEARCH_ADV + "?currentSearchTemplateId=" + currentId;
    }

    /**
    * Manage the print template
    * 
    * 
    */
    public void managePrintTemplate() {
        List<FieldKey> tempFields = new ArrayList<FieldKey>();

        tempFields.add(FieldKey.SELECT);

        // List returned from Manage Print Template screen
//        for (SearchFieldVo field : getTemplateFields()) {
//            tempFields.add(field.getFieldKey());
//        }

        tempFields = FieldKeyUtility.removeDuplicateWithOrder(tempFields);

        // Create a new print template and set the first field as the link field
        PrintTemplateVo printTemplateVo = new PrintTemplateVo();
        printTemplateVo.setTemplateLocation(retrieveTemplateLocation());
        PrintTemplateVo.populatePrintFieldCollection(printTemplateVo, tempFields);

        // set print template to search template
        getSearchTemplate().setPrintTemplate(printTemplateVo);

        //return SUCCESS;
    }

    /**
     * Returns the template location appropriate for current search
     * 
     * @return location
     */
    private TemplateLocation retrieveTemplateLocation() {
        if (EntityType.PRODUCT.equals(getSearchTemplate().getEntityType())) {
            return TemplateLocation.PRODUCT_SEARCH;
        } else if (EntityType.NDC.equals(getSearchTemplate().getEntityType())) {
            return TemplateLocation.NDC_SEARCH;
        } else {
            return TemplateLocation.ORDERABLE_ITEM_SEARCH;
        }
    }

    /**
     * Loads appropriate print template for the search
     * 
     * 
     */
    public void printTemplateLoad() {
        List<SearchFieldVo> templateFields = new ArrayList<SearchFieldVo>();

        // retrieves the fields in the current print template
        for (Column column : getSearchTemplate().getPrintTemplate().getFields()) {

            // removing fields that are search fields but not display fields
            if (!(FieldKey.SELECT.equals(column.getFieldKey()) || FieldKey.SEARCH_NO_FIELDS.equals(column.getFieldKey())
                || FieldKey.SEARCH_ALL_FIELDS.equals(column.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                .equals(column.getFieldKey()))) {

                templateFields.add(new SearchFieldVo(column.getFieldKey(), getSearchTemplate().getSearchCriteria()
                    .getEntityType()));
            }
        }

        //managePrintTemplates.selectedFields.addAll(templateFields);
    }

    /**
     * Description
     *
     * @return Environment
     */
    private Environment getEnvironment() {

        return null;
    }

    /**
     * setSearchTemplate.
     * @param searchTemplate the searchTemplate to set
     */
    public void setSearchTemplate(SearchTemplateVo searchTemplate) {
        flowScope.put(ControllerConstants.SEARCH_TEMPLATE_KEY, searchTemplate);
    }

    /**
     * getSearchTemplate.
     * @return the searchTemplate
     */
    public SearchTemplateVo getSearchTemplate() {
        SearchTemplateVo searchTemplate;

        if (flowScope.get(ControllerConstants.SEARCH_TEMPLATE_KEY) == null) {
            searchTemplate = createSearchTemplate();
            setSearchTemplate(searchTemplate);
        } else {
            searchTemplate = (SearchTemplateVo) flowScope.get(ControllerConstants.SEARCH_TEMPLATE_KEY);
        }

        return searchTemplate;
    }

    /**
     * 
     * Description
     *
     * @return list
     */
    public List<SearchTemplateVo> getSearchTemplates() {
        List<SearchTemplateVo> searchTemplates;

        if (session.getAttribute(ControllerConstants.SEARCH_TEMPLATES_KEY) == null) {
            searchTemplates = new ArrayList<SearchTemplateVo>();
            setSearchTemplates(searchTemplates);
        } else {
            searchTemplates = (List<SearchTemplateVo>) session.getAttribute(ControllerConstants.SEARCH_TEMPLATES_KEY);
        }

        return searchTemplates;
    }

    /**
     * 
     * Description
     *
     * @param searchTemplates template list
     */
    public void setSearchTemplates(List<SearchTemplateVo> searchTemplates) {
        session.setAttribute(ControllerConstants.SEARCH_TEMPLATES_KEY, searchTemplates);
    }

    /**
     * Inner class used to edit saved search/print templates from the form
     *  
     * 
     */
    public static class TemplateManager {

        private List<SearchTemplateVo> templatesList;
        private List<SearchFieldVo> availableList;
        private List<Column> selectedList;

        private List<String> selectedKeys;
        private List<String> addedKeys;

        private List<String> displayedList;

        private String templateName;
        private String templateNotes;
        private TemplateType templateType;

        /**
         * Empty constructor
         *
         */
        public TemplateManager() {

        }

        /**
         * Description 
         * 
         * @param templatesList the templates to set
         */
        public void setTemplatesList(List<SearchTemplateVo> templatesList) {
            this.templatesList = templatesList;
        }

        /**
         * Description 
         * 
         * @return the templates
         */
        public List<SearchTemplateVo> getTemplatesList() {
            return templatesList;
        }

        /**
         * setAvailableList
         * @param availableList the availableList to set
         */
        public void setAvailableList(List<SearchFieldVo> availableList) {
            this.availableList = availableList;
        }

        /**
         * getAvailableList
         * @return the availableList
         */
        public List<SearchFieldVo> getAvailableList() {
            return availableList;
        }

        /**
         * setSelectedList
         * @param selectedList the selectedList to set
         */
        public void setSelectedList(List<Column> selectedList) {
            this.selectedList = selectedList;
        }

        /**
         * getSelectedList
         * @return the selectedList
         */
        public List<Column> getSelectedList() {
            return selectedList;
        }

        /**
         * setSelectedKeys
         * @param selectedKeys the selectedKeys to set
         */
        public void setSelectedKeys(List<String> selectedKeys) {
            this.selectedKeys = selectedKeys;
        }

        /**
         * getSelectedKeys
         * @return the selectedKeys
         */
        public List<String> getSelectedKeys() {
            return selectedKeys;
        }

        /**
         * setAddedKeys
         * @param addedKeys the addedKeys to set
         */
        public void setAddedKeys(List<String> addedKeys) {
            this.addedKeys = addedKeys;
        }

        /**
         * getAddedKeys
         * @return the addedKeys
         */
        public List<String> getAddedKeys() {
            return addedKeys;
        }

        /**
         * setDisplayedList
         * @param displayedList the displayedList to set
         */
        public void setDisplayedList(List<String> displayedList) {
            this.displayedList = displayedList;
        }

        /**
         * getDisplayedList
         * @return the displayedList
         */
        public List<String> getDisplayedList() {
            return displayedList;
        }

        /**
         * setTemplateName
         * @param templateName the templateName to set
         */
        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        /**
         * getTemplateName
         * @return the templateName
         */
        public String getTemplateName() {
            return templateName;
        }

        /**
         * setTemplateNotes
         * @param templateNotes the templateNotes to set
         */
        public void setTemplateNotes(String templateNotes) {
            this.templateNotes = templateNotes;
        }

        /**
         * getTemplateNotes
         * @return the templateNotes
         */
        public String getTemplateNotes() {
            return templateNotes;
        }

        /**
         * setTemplateType
         * @param templateType the templateType to set
         */
        public void setTemplateType(TemplateType templateType) {
            this.templateType = templateType;
        }

        /**
         * getTemplateType
         * @return the templateType
         */
        public TemplateType getTemplateType() {
            return templateType;
        }

    }

}
