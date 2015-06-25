/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.presentation.common.utility.BooleanOptionGroup;
import gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * AbstractSearchController's brief summary
 * 
 * Details of AbstractSearchController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public abstract class AbstractSearchController extends PepsController {

    private static final String SUB_CATEGORY = "SubCategory.";
    private static final String CATEGORY = "Category.";
    private static final String NEVER_TRUE = "This if should never evaluate to true.";
    private static final int MIN_PREF_STR_LEN = 3;

    private static final Logger LOG = Logger.getLogger(AbstractSearchController.class);

    @Autowired
    private DomainUtility domainUtility;

    @Autowired
    private ManagedItemService managedItemService;

    @Autowired
    private SearchTemplateService searchTemplateService;

    @Autowired
    private UserService userService;

    /**
     * Description
     *
     * @param entityType EntityType
     * 
     * @return SearchCriteriaVo
     */
    @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY)
    public SearchCriteriaVo createSearchCriteria(
        @RequestParam(value = ControllerConstants.REQ_PARAM_KEY_ENTITYTYPE,
            required = false,
            defaultValue = ControllerConstants.REQ_PARAM_DEF_VALUE_ENTITYTYPE) EntityType entityType) {

        SearchCriteriaVo searchCriteria = null;

        if (ControllerConstants.PAGE_SEARCH_ADV.equals(request.getServletPath())
            || ControllerConstants.PAGE_RETRIEVE_TEMPLATE.equals(request.getServletPath())) {
            searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, entityType, environmentUtility.getEnvironment());
        } else if (ControllerConstants.PAGE_SEARCH_DATA_ELEMENTS.equals(request.getServletPath())) {
            searchCriteria = new SearchCriteriaVo(SearchDomain.DOMAIN, entityType, environmentUtility.getEnvironment());
        } else {
            searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, entityType, environmentUtility.getEnvironment());

        }

        List<SearchFieldVo> searchFieldList;

        if (ControllerConstants.PAGE_SEARCH_ADV.equals(request.getServletPath())
            || ControllerConstants.PAGE_SEARCH_UPDATE_ADV.equals(request.getServletPath())
            || ControllerConstants.PAGE_RETRIEVE_TEMPLATE.equals(request.getServletPath())) {
            searchFieldList = searchCriteria.getAdvancedSearchFields();
        } else if (ControllerConstants.PAGE_SEARCH_DATA_ELEMENTS.equals(request.getServletPath())) {
            searchFieldList = searchCriteria.getDomainSearchFields();
        } else if (request.getServletPath().contains(ControllerConstants.ASSOCIATE_PRODUCT_GO)
            || request.getServletPath().toLowerCase().contains(ControllerConstants.PARENT)) {
            if (entityType.isProduct()) {
                searchFieldList = SearchCriteriaVo.getProductSearchFields();
            } else {
                searchFieldList = SearchCriteriaVo.getOISearchFields();
            }
        } else {
            searchFieldList = searchCriteria.getSimpleSearchFields();
        }

        setupSearchFieldsMap(searchCriteria, searchFieldList);

        return searchCriteria;
    }

    /**
     * setupSearchFieldsMap
     *
     * @param searchCriteria SearchCriteriaVo
     * @param searchFieldList List<SearchFieldVo>
     */
    protected void setupSearchFieldsMap(SearchCriteriaVo searchCriteria, List<SearchFieldVo> searchFieldList) {
        LinkedHashMap<String, String> searchFieldsMap = new LinkedHashMap<String, String>();

        for (SearchFieldVo element : searchFieldList) {
            searchFieldsMap.put(element.getKey(), element.getLocalizedName(request.getLocale()));
        }

        searchCriteria.setSearchFieldsMap(searchFieldsMap);
    }

    /**
     * Description
     *
     * @return SortedMap
     */
    @ModelAttribute("itemStatusTypeMap")
    public SortedMap<ItemStatus, String> createItemStatusTypeMap() {

        SortedMap<ItemStatus, String> itemStatusTypeMap = new TreeMap<ItemStatus, String>();

        for (ItemStatus element : ItemStatus.getValues()) {
            itemStatusTypeMap.put(element,
                getMessageSource().getMessage("ItemStatus." + element.name(), null, request.getLocale()));
        }

        return itemStatusTypeMap;
    }

    /**
     * Description
     *
     * @return SortedMap
     */
    @ModelAttribute("requestStatusTypeMap")
    public SortedMap<RequestItemStatus, String> createRequestStatusTypeMap() {

        SortedMap<RequestItemStatus, String> requestStatusTypeMap = new TreeMap<RequestItemStatus, String>();

        for (RequestItemStatus element : RequestItemStatus.displayedValues()) {
            requestStatusTypeMap.put(element,
                getMessageSource().getMessage("RequestItemStatus." + element.name(), null, request.getLocale()));
        }

        return requestStatusTypeMap;
    }

    /**
     * Description
     * 
     * @return SortedMap
     */
    @ModelAttribute("searchTypeMap")
    public SortedMap<SearchType, String> createSearchTypeMap() {

        //Get Search Types
        SortedMap<SearchType, String> searchTypeMap = new TreeMap<SearchType, String>();

        for (SearchType element : SearchType.values()) {
            searchTypeMap.put(element,
                getMessageSource().getMessage("SearchType." + element.name(), null, request.getLocale()));
        }

        return searchTypeMap;
    }

    /**
     * Description
     *
     *@return LinkedHashMap
     */
    @ModelAttribute("categoryMap")
    public Map<String, String> createCategoryMap() {

        LinkedHashMap<String, String> categoryMap = new LinkedHashMap<String, String>();
        Locale locale = request.getLocale();

        categoryMap.put(String.valueOf(Category.MEDICATION),
            messageSource.getMessage(CATEGORY + Category.MEDICATION, null, locale));
        categoryMap.put(String.valueOf(Category.INVESTIGATIONAL),
            messageSource.getMessage(CATEGORY + Category.INVESTIGATIONAL, null, locale));
        categoryMap.put(String.valueOf(Category.COMPOUND),
            messageSource.getMessage(CATEGORY + Category.COMPOUND, null, locale));
        categoryMap.put(String.valueOf(Category.SUPPLY), messageSource.getMessage(CATEGORY + Category.SUPPLY, null, locale));

        return categoryMap;
    }

    /**
     * Description
     *
     * @return Map
     */
    @ModelAttribute("subCategoryMap")
    public Map<String, String> createSubCategoryMap() {

        LinkedHashMap<String, String> subCategoryMap = new LinkedHashMap<String, String>();
        Locale locale = request.getLocale();

        subCategoryMap.put(String.valueOf(SubCategory.HERBAL),
                           messageSource.getMessage(SUB_CATEGORY + SubCategory.HERBAL, null, locale));
        subCategoryMap.put(String.valueOf(SubCategory.CHEMOTHERAPY),
                           messageSource.getMessage(SUB_CATEGORY + SubCategory.CHEMOTHERAPY, null, locale));
        subCategoryMap.put(String.valueOf(SubCategory.OTC),
                           messageSource.getMessage(SUB_CATEGORY + SubCategory.OTC, null, locale));
        subCategoryMap.put(String.valueOf(SubCategory.VETERINARY),
                           messageSource.getMessage(SUB_CATEGORY + SubCategory.VETERINARY, null, locale));

        return subCategoryMap;
    }

    /**
     * Description
     *
     * @param searchCriteria SearchCriteriaVo
     * @return LinkedHashMap
     */
    @ModelAttribute("entityTypeMap")
    public Map<String, String> createEntityTypeMap(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria) {

        LinkedHashMap<String, String> entityTypeMap = new LinkedHashMap<String, String>();

        searchCriteria.getEntityType();

        for (EntityType element : EntityType.entities()) {
            entityTypeMap.put(element.name(),
                              getMessageSource().getMessage("EntityType." + element.name(), null, request.getLocale()));
        }

        return entityTypeMap;
    }

    /**
     * Description
     *
     * @param searchCriteria SearchCriteriaVo
     * @return Map<Boolean, String>
     */
    @ModelAttribute("advancedAndSearchMap")
    public Map<Boolean, String> createAdvancedAndSearchMap(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria) {

        BooleanOptionGroup advancedAndSearch = new BooleanOptionGroup("Advanced Search",
            FieldKey.ADVANCED_AND_SEARCH
                .getLocalizedName(getLocale()), "OR");

        return advancedAndSearch.getOptions();
    }

    /**
     * Returns the print template appropriate for the search
     * 
     * @param searchCriteria - the criteria used to search for items
     * @param isSelectParentSearch boolean 
     * @return printTemplate How to display the data
     */
    public PrintTemplateVo retrievePrintTemplate(SearchCriteriaVo searchCriteria, boolean isSelectParentSearch) {
        PrintTemplateVo printTemplate = null;
        FieldKey fieldKey = searchCriteria.getSearchTerms().get(0).getFieldKey();

        if (searchCriteria.getEntityType() == null) {
            return null;
        }

        //if (!searchCriteria.isAdvanced()) {
        if (searchCriteria.getSearchDomain().isProductSearch()) {
            printTemplate = DefaultPrintTemplateFactory.defaultProductSearch(false);

        } else if (searchCriteria.getSearchDomain().isSetupAtcSearch()) {
            printTemplate = DefaultPrintTemplateFactory.defaultSetupAtcSearch();

        } else if (searchCriteria.getSearchDomain().isOrderableItemSearch()) {
            printTemplate = DefaultPrintTemplateFactory.defaultOrderableItemSearch(false);

        } else if (searchCriteria.getEntityType().isProduct()) {

            if (isSelectParentSearch) {
                printTemplate = DefaultPrintTemplateFactory.selectParentProductSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.ACTIVE_INGREDIENT)) {
                printTemplate = DefaultPrintTemplateFactory.defaultActiveIngredientProductSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.VA_PRODUCT_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultVaProductNameSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.VA_PRINT_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultVaPrintNameSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.GENERIC_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultGenericNameSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.CMOP_ID)) {
                printTemplate = DefaultPrintTemplateFactory.defaultVaProductIdSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.PRIMARY_DRUG_CLASS2)) {
                printTemplate = DefaultPrintTemplateFactory.defaultVaDrugClassSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.SYNONYM_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultSynonymProductSearch();
            } else {
                printTemplate = personalProductSearch(getUser());

            }
        } else if (searchCriteria.getEntityType().isNdc()) {
            if (fieldKey != null && fieldKey.equals(FieldKey.TRADE_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultNdcTradeNameSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.UPC_UPN)) {
                printTemplate = DefaultPrintTemplateFactory.defaultUpcUpnSearch();

            } else if (fieldKey != null && fieldKey.equals(FieldKey.NDC)) {
                printTemplate = DefaultPrintTemplateFactory.defaultNdcSearch();

            } else {
                printTemplate = personalNdcSearch(getUser());

            }
        } else if (searchCriteria.getEntityType().isOrderableItem()) {
            if (isSelectParentSearch) {
                printTemplate = DefaultPrintTemplateFactory.selectParentOrderableItemSearch();
                
            } else if (fieldKey != null && fieldKey.equals(FieldKey.ORDERABLE_ITEM_SYNONYM)) {
                printTemplate = DefaultPrintTemplateFactory.defaultOrderableItemSynonymSearch();
                
            } else if (fieldKey != null && fieldKey.equals(FieldKey.OI_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultPpsOiNameSearch();

            } else {
                printTemplate = personalOiSearch(getUser());

            }
        } else if (searchCriteria.getEntityType().equals(EntityType.DRUG_CLASS)) {
            if (searchCriteria.getSearchTerms().get(0).getFieldKey().equals(FieldKey.CLASSIFICATION)) {
                printTemplate = DefaultPrintTemplateFactory.defaultDrugClassSearch();

            } else {
                printTemplate = DefaultPrintTemplateFactory.defaultDrugCodeSearch();

            }
        } else if (searchCriteria.getEntityType().equals(EntityType.DRUG_TEXT)) {
            printTemplate = DefaultPrintTemplateFactory.defaultDrugTextSearch();

        } else if (searchCriteria.getEntityType().equals(EntityType.DOSE_UNIT)) {
            printTemplate = DefaultPrintTemplateFactory.defaultDoseUnitSearch();

        } else {
            printTemplate = DefaultPrintTemplateFactory.defaultManagedDataSearch();

        }

        if (searchCriteria.isAdvanced() && searchCriteria.getPrintTemplate() != null) {

            printTemplate = searchCriteria.getPrintTemplate();
        }

        return printTemplate;
    }

    /**
     * 
     * Sets the paging and sorting attributes for the search criteria
     *
     * @param searchCriteria SearchCriteriaVo
     */
    public void prepareSearchCriteria(SearchCriteriaVo searchCriteria) {

        if (isExternalPagingRequest()) {
            pageTable(searchCriteria);
        } else {
            initializePagingAttributes(searchCriteria);
        }
    }

    /**
     * Set default values for the paging attributes if they are not yet set.
     * 
     * @param searchCriteria SearchCriteriaVo
     */
    public void initializePagingAttributes(SearchCriteriaVo searchCriteria) {

        if (searchCriteria.getSortedFieldKey() == null) {
            FieldKey searchTerm = searchCriteria.getSearchTerms().get(0).getFieldKey();

            if (!(FieldKey.SEARCH_ALL_FIELDS.equals(searchTerm) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS.equals(searchTerm))
                && searchTerm.isSortable()) {
                searchCriteria.setSortedFieldKey(searchTerm);
            } else {

                //Default key to sort the table by
                searchCriteria.setSortedFieldKey(FieldKey.ID);
            }
        }

        if (searchCriteria.getSortOrder() == null) {
            searchCriteria.setSortOrder(SortOrder.ASCENDING);
        }

        // Currently advanced searches are not paged
        searchCriteria.setPageSize(getUser().getTableSizePreference());
        searchCriteria.setStartRow(0);

    }

    /**
     * A "render" request is a paging (or sorting) request if the DisplayTag
     * library's request parameters are present.
     * 
     * @return boolean true if this request is a paging or sorting request
     */
    protected final boolean isExternalPagingRequest() {
        String tableId = getPagedTable();

        return tableId != null;
    }

    /**
     * Get the HTML ID of the table being paged. If no table paged, return null.
     * 
     * @return String HTML ID of table being paged. If no table paged, return
     *         null.
     */
    protected final String getPagedTable() {
        String table = request.getParameter(ControllerConstants.TABLE_ID_PARAMETER);

        return table;
    }

    /**
     * Sets the paging and sorting attributes for the search criteria
     * 
     * @param criteria SearchCriteriaVo
     */
    protected void pageTable(SearchCriteriaVo criteria) {

        criteria.setSortedFieldKey(getSortedFieldKey());
        criteria.setSortOrder(getSortOrder());
        criteria.setStartRow(getStartRow());
        criteria.setPageSize(getUser().getTableSizePreference());

    }

    /**
     * Get the {@link FieldKey} for the column being sorted.
     * <p>
     * For this method to work, {@link #isExternalPagingRequest()} must be true
     * and {@link #getPagedTable()} must not return null!
     * 
     * @return {@link FieldKey} of column being sorted -- may be null if no sort
     *         (or default sort) selected
     */
    protected final FieldKey getSortedFieldKey() {
        String tableId = getPagedTable();
        String sortParameter = new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT);
        String sort = request.getParameter(sortParameter);

        FieldKey sortedFieldKey = null;

        if (sort == null) {

            //Default sorting
            sortedFieldKey = FieldKey.ID;
        } else {
            FieldKey sortKey = FieldKey.getKey(sort);

            if (FieldKey.DISPLAYABLE_INGREDIENT_NAME.equals(sortKey)) {
                sortedFieldKey = FieldKey.ACTIVE_INGREDIENT;
            } else if (FieldKey.DISPLAYABLE_INGREDIENT_STRENGTH.equals(sortKey)) {
                sortedFieldKey = FieldKey.STRENGTH;
            } else if (FieldKey.DISPLAYABLE_INGREDIENT_UNIT.equals(sortKey)) {
                sortedFieldKey = FieldKey.DRUG_UNIT;
            } else if (FieldKey.DISPLAYABLE_SYNONYM_NAME.equals(sortKey)) {
                sortedFieldKey = FieldKey.SYNONYM_NAME;
            } else {
                sortedFieldKey = sortKey;
            }
        }

        return sortedFieldKey;
    }

    /**
     * Get the {@link SortOrder} for the column being sorted.
     * <p>
     * For this method to work, {@link #isExternalPagingRequest()} must be true
     * and {@link #getPagedTable()} must not return null!
     * 
     * @return {@link SortOrder} for the column being sorted -- may be null if
     *         no sort (or default sort) selected
     */
    protected final SortOrder getSortOrder() {
        SortOrder sortOrder = null;
        String tableId = getPagedTable();
        String orderParameter = new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER);
        String order = request.getParameter(orderParameter);

        int value = 0;

        if (order != null) {
            value = Integer.parseInt(order);
        }

        switch (value) {
            case 1:
                sortOrder = SortOrder.DESCENDING;
                break;

            case 2:
                sortOrder = SortOrder.ASCENDING;
                break;

            default:
                sortOrder = SortOrder.ASCENDING;
                break;
        }

        return sortOrder;
    }

    /**
     * Get the index within the full list for the row to start the next (or
     * previous) page on.
     * <p>
     * For this method to work, {@link #isExternalPagingRequest()} must be true
     * and {@link #getPagedTable()} must not return null!
     * 
     * @return integer indicating row index to start the next or previous page
     *         on
     */
    protected final int getStartRow() {
        String tableId = getPagedTable();
        String pageParameter = new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        String page = request.getParameter(pageParameter);

//        int pageNumber = Integer.parseInt(page);
//        int pageSize = getUser().getTableSizePreference();
//
//        return (pageNumber - 1) * pageSize;

        int pageNumber = 1;

        if (page != null) {
            pageNumber = Integer.parseInt(page);
        }

        int pageSize = getUser().getTableSizePreference();

        return (pageNumber - 1) * pageSize;
    }

    /**
     * get domain utility
     * 
     * @return the domainUtility
     */
    public DomainUtility getDomainUtility() {
        return domainUtility;
    }

    /**
     * set domain utility 
     * @param domainUtility the domainUtility to set
     */
    public void setDomainUtility(DomainUtility domainUtility) {
        this.domainUtility = domainUtility;
    }

    /**
     * get managed item service 
     * 
     * @return the managedItemService
     */
    public ManagedItemService getManagedItemService() {
        return managedItemService;
    }

    /**
     * set managed item service
     * 
     * @param managedItemService the managedItemService to set
     */
    public void setManagedItemService(ManagedItemService managedItemService) {
        this.managedItemService = managedItemService;
    }

    /**
     * get search template service 
     * 
     * @return the searchTemplateService
     */
    public SearchTemplateService getSearchTemplateService() {
        return searchTemplateService;
    }

    /**
     * set search template service
     * @param searchTemplateService the searchTemplateService to set
     */
    public void setSearchTemplateService(SearchTemplateService searchTemplateService) {
        this.searchTemplateService = searchTemplateService;
    }

    /**
     * get the userService
     * 
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * sets userService field
     * 
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * saveLastSearch save the values from the criteria to search preferences.
     *
     * @param criteria search criteria to save parts of
     */
    protected void saveLastSimpleSearch(SearchCriteriaVo criteria) {

        if (!this.canProceedWithPrefOperations(criteria)) {

            return;
        }

        clearSimpleSearchPrefs(criteria);
        saveSearchCritToPrefs(criteria);

        try {
            userService.updatePreferences(getUser());
        } catch (ValueObjectValidationException e) {
            LOG.error("Unable to save user search preferences.", e);
        }
    }

    /**
     * canProceedWithPrefOperations
     *
     * @param criteria SearchCriteriaVo
     * @return boolean
     */
    protected boolean canProceedWithPrefOperations(SearchCriteriaVo criteria) {

        if (criteria == null) {
            LOG.warn("Unable to save last search criteria to settings. SearchCriteria object is null.");

            return false;
        }

        if (getUser() == null) {
            LOG.warn("Unable to save last search criteria to settings. User object is null.");

            return false;
        }

        if (criteria.getSearchDomain() == null) {
            LOG.warn("Unable to save last search criteria to settings. Search domain value is null.");

            return false;
        }

        return true;
    }

    /**
     * saveLastDomainSearch save the values from the criteria to search preferences.
     *
     * @param criteria search criteria to save parts of
     */
    protected void saveLastDomainSearch(SearchCriteriaVo criteria) {

        if (!this.canProceedWithPrefOperations(criteria)) {
            return;
        }

        clearDomainSearchPrefs(criteria);
        saveDomainCritToPrefs(criteria);

        try {
            userService.updatePreferences(getUser());
        } catch (ValueObjectValidationException e) {
            LOG.error("Unable to save user search domain preferences.", e);
        }
    }

    /**
     * saveDomainCritToPrefs
     *
     * @param criteria SearchCriteriaVo
     */
    protected void saveDomainCritToPrefs(SearchCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();
        String value = null;

        if (criteria.getSearchDomain() != null) {
            prefs.put(SessionPreferenceType.LAST_SEARCH_DOMAIN, criteria.getSearchDomain().toString());
        }

        if (!SearchDomain.DOMAIN.equals(criteria.getSearchDomain())) {
            return;
        }

        if (criteria.getItemStatus() != null && !criteria.getItemStatus().isEmpty()) {
            value = StringUtils.join(criteria.getItemStatus(), DELIM);
            prefs.put(SessionPreferenceType.LAST_DOMAIN_ITEM_STATUS, value);
        }

        if (criteria.getRequestStatus() != null && !criteria.getRequestStatus().isEmpty()) {
            value = StringUtils.join(criteria.getRequestStatus(), DELIM);
            prefs.put(SessionPreferenceType.LAST_DOMAIN_ITEM_REQUEST, value);
        }

        if (!isFirstSearchTermNull(criteria.getSearchTerms()) && criteria.getSearchTerms().get(0).getSearchField() != null) {
            value = criteria.getSearchTerms().get(0).getSearchField().getKey();
            prefs.put(SessionPreferenceType.LAST_DOMAIN_SEARCH_FIELD, value);
        }
    }

    /**
     * saveSearchCritToPrefs  save the non-null values in the search criteria to given search preferences (Simple or Domain)
     *
     * @param criteria search criteria
     */
    protected void saveSearchCritToPrefs(SearchCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();
        String value = null;

        if (criteria.getSearchDomain() != null) {
            prefs.put(SessionPreferenceType.LAST_SEARCH_DOMAIN, criteria.getSearchDomain().toString());
        }

        if (criteria.getEntityType() != null) {
            prefs.put(SessionPreferenceType.LAST_SIMPLE_ENTITY_TYPE, criteria.getEntityType().toString());
        }

        if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
            value = StringUtils.join(criteria.getCategories(), DELIM);
            prefs.put(SessionPreferenceType.LAST_SIMPLE_CATS, value);
        }

        if (!isFirstSearchTermNull(criteria.getSearchTerms()) && criteria.getSearchTerms().get(0).getSearchType() != null) {
            prefs.put(SessionPreferenceType.LAST_SIMPLE_SEARCH_TYPE, criteria.getSearchTerms().get(0).getSearchType()
                .toString());
        }

        if (!isFirstSearchTermNull(criteria.getSearchTerms()) && criteria.getSearchTerms().get(0).getSearchField() != null) {
            prefs.put(SessionPreferenceType.LAST_SIMPLE_SEARCH_FIELD, criteria.getSearchTerms().get(0).getSearchField()
                .getKey());
            LOG.debug("search field: " + criteria.getSearchTerms().get(0).getSearchField().getKey());
        }

        if (criteria.getSubCategories() != null && !criteria.getSubCategories().isEmpty()) {
            value = StringUtils.join(criteria.getSubCategories(), DELIM);
            prefs.put(SessionPreferenceType.LAST_SIMPLE_SUBCATS, value);
        }

        if (criteria.getItemStatus() != null && !criteria.getItemStatus().isEmpty()) {
            value = StringUtils.join(criteria.getItemStatus(), DELIM);

            /*if (value.contains(",")) {
                value = "[" + value + "]";
            }*/
            prefs.put(SessionPreferenceType.LAST_SIMPLE_ITEM_STATUS, value);
        }

        if (criteria.getRequestStatus() != null && !criteria.getRequestStatus().isEmpty()) {
            value = StringUtils.join(criteria.getRequestStatus(), DELIM);
            prefs.put(SessionPreferenceType.LAST_SIMPLE_ITEM_REQUEST, value);
        }

        // ID 3496 - strength and dosage form are not to be saved.
//        if (criteria.getStrength() != null && !criteria.getStrength().isEmpty()) {
//            prefs.put(SessionPreferenceType.LAST_SIMPLE_STRENGTH, criteria.getStrength());
//        }

        // ID 3496 - strength and dosage form are not to be saved.
//        if (criteria.getDosageForm() != null && !criteria.getDosageForm().isEmpty()) {
//            prefs.put(SessionPreferenceType.LAST_SIMPLE_DOSAGE_FORM, criteria.getDosageForm());
//        }

    }

    /**
     * loadPrefsToDomainCrit
     *
     * @param criteria SearchCriteriaVo
     */
    protected void loadPrefsToDomainCrit(SearchCriteriaVo criteria) {

        if (!this.canProceedWithPrefOperations(criteria)) {
            return;
        }

        String[] pstr = null;
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        pstr = StringUtils.split(prefs.get(SessionPreferenceType.LAST_DOMAIN_ITEM_STATUS), DELIM);

        if (criteria.getItemStatus() == null) {
            criteria.setItemStatus(new ArrayList<ItemStatus>());
        }

        if (pstr != null) {
            criteria.setItemStatus(new ArrayList<ItemStatus>());

            for (String s : pstr) {
                ItemStatus is = ItemStatus.valueOf(s);
                criteria.getItemStatus().add(is);
            }
        }

        pstr = StringUtils.split(prefs.get(SessionPreferenceType.LAST_DOMAIN_ITEM_REQUEST), DELIM);

        if (criteria.getRequestStatus() == null) {
            criteria.setRequestStatus(new ArrayList<RequestItemStatus>());
        }

        if (pstr != null) {
            criteria.setRequestStatus(new ArrayList<RequestItemStatus>());

            for (String s : pstr) {
                RequestItemStatus is = RequestItemStatus.valueOf(s);
                criteria.getRequestStatus().add(is);
            }
        }

        String value = prefs.get(SessionPreferenceType.LAST_DOMAIN_SEARCH_FIELD);

        if (value != null) {
            SearchFieldVo st = criteria.getSearchTerms().get(0).getSearchField();

            if (st != null) {
                st.setKey(value);
            }
        }
    }

    /**
     * isSearchTermNull
     *
     * @param lstv List<SearchTermVo>
     * @return boolean
     */
    protected boolean isFirstSearchTermNull(List<SearchTermVo> lstv) {
        if (lstv == null) {
            return true;
        }

        if (lstv.isEmpty()) {
            return true;
        }

        if (lstv.get(0) == null) {
            return true;
        }

        return false;
    }

    /**
     * loadPrefsToSearchCrit
     *
     * @param criteria destination Search Criteria for the prefs. 
     */
    protected void loadPrefsToSearchCrit(SearchCriteriaVo criteria) {

        if (!this.canProceedWithPrefOperations(criteria)) {
            return;
        }

        String[] pstr = null;
        String value = null;
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        value = prefs.get(SessionPreferenceType.LAST_SIMPLE_ENTITY_TYPE);

        criteria.setEntityType(null);

        if (value != null) {
            EntityType et = EntityType.valueOf(value);

//            LOG.debug("entity pref: " + value);

            if (et != null && !isFirstSearchTermNull(criteria.getSearchTerms())) {

//                criteria = this.createSearchCriteria(et);
                criteria.setEntityType(et);
                criteria.getSearchTerms().get(0).getSearchField().setEntityType(et);
                setupSearchFieldsMap(criteria, criteria.getSimpleSearchFields());
            }
        }

        pstr = StringUtils.split(prefs.get(SessionPreferenceType.LAST_SIMPLE_CATS), DELIM);

//        if (criteria.getCategories() == null) {
//        }
        criteria.setCategories(new ArrayList<Category>());

        if (pstr != null) {
            for (String s : pstr) {
                Category is = Category.valueOf(s);
                criteria.getCategories().add(is);
            }
        }

        if (!isFirstSearchTermNull(criteria.getSearchTerms())) {
            value = prefs.get(SessionPreferenceType.LAST_SIMPLE_SEARCH_TYPE);

            if (value != null) {
                SearchType st = SearchType.valueOf(value);

                if (st != null) {
                    criteria.getSearchTerms().get(0).setSearchType(st);
                }
            }

            value = prefs.get(SessionPreferenceType.LAST_SIMPLE_SEARCH_FIELD);

            if (value != null) {
                SearchFieldVo st = new SearchFieldVo(value); //criteria.getSearchTerms().get(0).getSearchField();
                criteria.getSearchTerms().get(0).setSearchField(st);
            }
        }

        pstr = StringUtils.split(prefs.get(SessionPreferenceType.LAST_SIMPLE_SUBCATS), DELIM);

//        if (criteria.getSubCategories() == null) {
//        }
        criteria.setSubCategories(new ArrayList<SubCategory>());

        if (pstr != null) {
            for (String s : pstr) {
                SubCategory is = SubCategory.valueOf(s);
                criteria.getSubCategories().add(is);
            }
        }

        pstr = StringUtils.split(prefs.get(SessionPreferenceType.LAST_SIMPLE_ITEM_STATUS), DELIM);

//        if (criteria.getItemStatus() == null) {
//        }

        // explicitly clear this out, makes sure "default" is removed.
        criteria.setItemStatus(new ArrayList<ItemStatus>());

        if (pstr != null) {
            for (String s : pstr) {
                ItemStatus is = ItemStatus.valueOf(s);
                criteria.getItemStatus().add(is);
            }
        }

        pstr = StringUtils.split(prefs.get(SessionPreferenceType.LAST_SIMPLE_ITEM_REQUEST), DELIM);

//        if (criteria.getRequestStatus() == null) {
//        }

        // explicitly clear this out, makes sure "default" is removed.
        criteria.setRequestStatus(new ArrayList<RequestItemStatus>());

        if (pstr != null) {
            for (String s : pstr) {
                RequestItemStatus is = RequestItemStatus.valueOf(s);
                criteria.getRequestStatus().add(is);
            }
        }

        // ID 3496 - strength and dosage form are not to be saved.
//        value = prefs.get(SessionPreferenceType.LAST_SIMPLE_STRENGTH);
//
//        if (value != null) {
//            criteria.setStrength(value);
//        }

        // ID 3496 - strength and dosage form are not to be saved.
//        value = prefs.get(SessionPreferenceType.LAST_SIMPLE_DOSAGE_FORM);
//
//        if (value != null) {
//            criteria.setDosageForm(value);
//        }

    }

    /**
     * clearDomainSearchPrefs
     *
     * @param criteria SearchCriteriaVo
     */
    protected void clearDomainSearchPrefs(SearchCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        // really this should never execute
        if (criteria.getSearchDomain() == null) {
            LOG.error(NEVER_TRUE);
            prefs.remove(SessionPreferenceType.LAST_SEARCH_DOMAIN);
        }

        if (!SearchDomain.DOMAIN.equals(criteria.getSearchDomain())) {
            return;
        }

        // clear domain search settings, if need be.
        if (criteria.getItemStatus() == null || criteria.getItemStatus().isEmpty()) {
            prefs.remove(SessionPreferenceType.LAST_DOMAIN_ITEM_STATUS);
        }

        if (criteria.getRequestStatus() == null || criteria.getRequestStatus().isEmpty()) {
            prefs.remove(SessionPreferenceType.LAST_DOMAIN_ITEM_REQUEST);
        }

        if (isFirstSearchTermNull(criteria.getSearchTerms())) {
            prefs.remove(SessionPreferenceType.LAST_DOMAIN_SEARCH_FIELD);
        }
    }

    /**
     * clearSimpleSearchPrefs  clear out all preference values for the search where the values are null or empty
     *
     * @param criteria search criteria
     */
    protected void clearSimpleSearchPrefs(SearchCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        // really this should never execute
        if (criteria.getSearchDomain() == null) {
            LOG.error(NEVER_TRUE);
            prefs.remove(SessionPreferenceType.LAST_SEARCH_DOMAIN);
        }

        if (!SearchDomain.SIMPLE.equals(criteria.getSearchDomain())) {
            return;
        }

        // clear simple search settings, if need be
        if (isFirstSearchTermNull(criteria.getSearchTerms()) && criteria.getSearchTerms().get(0).getSearchType() == null) {
            prefs.remove(SessionPreferenceType.LAST_SIMPLE_SEARCH_TYPE);

        }

        if (isFirstSearchTermNull(criteria.getSearchTerms()) && criteria.getSearchTerms().get(0).getSearchField() == null) {
            prefs.remove(SessionPreferenceType.LAST_SIMPLE_SEARCH_FIELD);
        }

        if (criteria.getEntityType() == null) {
            prefs.remove(SessionPreferenceType.LAST_SIMPLE_ENTITY_TYPE);
        }

        if (criteria.getCategories() == null || criteria.getCategories().isEmpty()) {
            prefs.remove(SessionPreferenceType.LAST_SIMPLE_CATS);
        }

        if (criteria.getSubCategories() == null || criteria.getSubCategories().isEmpty()) {
            prefs.remove(SessionPreferenceType.LAST_SIMPLE_SUBCATS);
        }

        if (criteria.getItemStatus() == null || criteria.getItemStatus().isEmpty()) {
            prefs.remove(SessionPreferenceType.LAST_SIMPLE_ITEM_STATUS);
        }

        if (criteria.getRequestStatus() == null || criteria.getRequestStatus().isEmpty()) {
            prefs.remove(SessionPreferenceType.LAST_SIMPLE_ITEM_REQUEST);
        }

        // ID 3496 - strength and dosage form are not to be saved.
        //if (criteria.getStrength() == null || criteria.getStrength().isEmpty()) {
        prefs.remove(SessionPreferenceType.LAST_SIMPLE_STRENGTH);

        // ID 3496 - strength and dosage form are not to be saved.
        //if (criteria.getDosageForm() == null || criteria.getDosageForm().isEmpty()) {
        prefs.remove(SessionPreferenceType.LAST_SIMPLE_DOSAGE_FORM);
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @param user UserVo
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo personalProductSearch(UserVo user) {

        PrintTemplateVo printTemplate = new PrintTemplateVo();

        String userPreferenceString = user.getSessionPreferences().get(SessionPreferenceType.DEFAULT_PROD_PRINT_TEMPLATE_ID);

        if (userPreferenceString.isEmpty() || userPreferenceString.length() < MIN_PREF_STR_LEN) {
            printTemplate = DefaultPrintTemplateFactory.defaultProductSearch(true);
        } else if ("PERSONAL PRODUCT SEARCH".equalsIgnoreCase(userPreferenceString)) {
            printTemplate = DefaultPrintTemplateFactory.defaultProductSearch(true);
        } else {
            List<FieldKey> buildKeyList = buildKeyList(userPreferenceString);
            
            if (buildKeyList.size() > 0) {
                String templateType = "product";
                PrintTemplateVo.populatePersonalPrintFieldCollection(printTemplate, buildKeyList, templateType);
            }
        }

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a NDC search.
     * 
     * @param user UserVo
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo personalNdcSearch(UserVo user) {

        PrintTemplateVo printTemplate = new PrintTemplateVo();

        String userPreferenceString = user.getSessionPreferences().get(SessionPreferenceType.DEFAULT_NDC_PRINT_TEMPLATE_ID);

        if (userPreferenceString.isEmpty() || userPreferenceString.length() < MIN_PREF_STR_LEN) {
            printTemplate = DefaultPrintTemplateFactory.defaultNdcSearch(true);
        } else if ("PERSONAL NDC SEARCH".equalsIgnoreCase(userPreferenceString)) {
            printTemplate = DefaultPrintTemplateFactory.defaultNdcSearch(true);
        } else {
            List<FieldKey> buildKeyList = buildKeyList(userPreferenceString);

            if (buildKeyList.size() > 0) {
                String templateType = "ndc";
                PrintTemplateVo.populatePersonalPrintFieldCollection(printTemplate, buildKeyList, templateType);
            }
        }

        // return the correct defaultprintTemplate to the calling method.
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a OI search.
     * 
     * @param user UserVo
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo personalOiSearch(UserVo user) {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        String userPreferenceString = user.getSessionPreferences().get(SessionPreferenceType.DEFAULT_OI_PRINT_TEMPLATE_ID);

        if (userPreferenceString.isEmpty() || userPreferenceString.length() < MIN_PREF_STR_LEN) {
            printTemplate = DefaultPrintTemplateFactory.defaultOrderableItemSearch(true);
        } else if ("PERSONAL ORDERABLE ITEM SEARCH".equalsIgnoreCase(userPreferenceString)) {
            printTemplate = DefaultPrintTemplateFactory.defaultOrderableItemSearch(true);
        } else {
            List<FieldKey> buildKeyList = buildKeyList(userPreferenceString);

            if (buildKeyList.size() > 0) {
                String templateType = "oi";
                PrintTemplateVo.populatePersonalPrintFieldCollection(printTemplate, buildKeyList, templateType);
            }
        }

        return printTemplate;
    }

    /**
     * buildKeyList
     *
     * @param userPreferenceString String
     * @return List
     */
    private static List<FieldKey> buildKeyList(String userPreferenceString) {
        List<FieldKey> rv = new ArrayList<FieldKey>();
        int prefStringLength = userPreferenceString.length();

        if (prefStringLength > 2) {
            String revUserPreferenceString = userPreferenceString.substring(1, prefStringLength - 1);
            String[] keys = revUserPreferenceString.split(",");
            rv.add(FieldKey.SELECT);

            for (String key : keys) {
                rv.add(FieldKey.getKey(key.trim()));
            }
        }

        return rv;
    }
}
