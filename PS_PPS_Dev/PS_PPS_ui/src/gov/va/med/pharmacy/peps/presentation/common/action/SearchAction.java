/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.action;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.Column;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.SearchTemplateValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.ConversationScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.FlashScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.FlowScope;
import gov.va.med.pharmacy.peps.presentation.common.utility.BooleanOptionGroup;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * Provides Search template actions, such as save, retrieve, and modify. Helps manage the advanced search view and
 * functionality.
 */
public class SearchAction extends PepsAction {
    public static final String SEARCH_RESULTS_TABLE = "searchResultsTable";

    private static final long serialVersionUID = 1L;

    private SearchTemplateService searchTemplateService;

    private UserService userService;

    @ConversationScope
    private SearchTemplateVo searchTemplate = new SearchTemplateVo();

    @ConversationScope
    private String currentSearchTemplateId;

    @FlowScope
    private List<SearchFieldVo> templateFields = new ArrayList<SearchFieldVo>();

    @FlowScope
    private List<SearchTemplateVo> templates;

    @FlashScope
    private String templateName;

    @FlashScope
    private TemplateType templateType;

    @ConversationScope
    private boolean advancedSearch;

    @FlowScope
    private boolean warned;

    @FlowScope
    private EntityType searchMode = null;

    @ConversationScope
    private List<PartialSaveVo> partialItems;

    // localize these
    @FlowScope
    private BooleanOptionGroup systemLevel = new BooleanOptionGroup("Save At", "System Level", "User Level");

    @FlowScope
    private BooleanOptionGroup advancedAndSearch = new BooleanOptionGroup("Advanced Search", FieldKey.ADVANCED_AND_SEARCH
        .getLocalizedName(getLocale()), "OR");

    @ConversationScope
    private List items;

    @FlowScope
    private EntityType itemType;

    private ManagedItemService managedItemService;

    @ConversationScope
    private boolean isStartSearchFlow; // True indicates advanced search flow is started again.

    /**
     * Clean the slate so that we are working with a new template.
     */
    public void cleanSlate() {
        getSearchTemplate().makeNew();
        this.items = null;
    }

    /**
     * Allow sub-classes to perform processing during a render cycle.
     */
    protected void handleOnRenderSeen() {

        // Add logic to clear out an advanced search 'value' field if its field selection has changed.
        // This logic was added to deal with defect #2219 'Unexpected advanced search field behavior', whereby
        // a previous field selection's value was being stamped on to a new field's value...it should instead be blank.
        if (!isStartSearchFlow && searchTemplate != null && searchTemplate.getSearchCriteria().isAdvanced()) {
            searchTemplate.getSearchCriteria().fixValuesAfterFieldChange();
        }

        isStartSearchFlow = false;
    }

    /**
     * Adds a new search term to the current search.
     * 
     * @return SUCCESS
     */
    public String addSearchTerm() {
        getSearchTemplate().getSearchCriteria().getSearchTerms().add(SearchTermVo.newEmptyInstance(SearchDomain.ADVANCED));
        cleanSlate();

        return SUCCESS;
    }

    /**
     * 
     * @return items property
     */
    public List getItems() {
        return items;
    }

    /**
     * 
     * @return itemType property
     */
    public EntityType getItemType() {
        return itemType;
    }

    /**
     * 
     * @return advancedAndSearch property
     */
    public BooleanOptionGroup getAdvancedAndSearch() {
        return advancedAndSearch;
    }

    /**
     * 
     * @param advancedAndSearch advancedAndSearch property
     */
    public void setAdvancedAndSearch(BooleanOptionGroup advancedAndSearch) {
        this.advancedAndSearch = advancedAndSearch;
    }

    /**
     * If a searchMode is set, only that type will be allowed for a search. This is used for selecting a parent via search.
     * 
     * @return searchMode property
     */
    public EntityType getSearchMode() {
        return searchMode;
    }

    /**
     * Returns the search criteria for the search.
     * 
     * @return searchTemplate property
     */
    public SearchTemplateVo getSearchTemplate() {
        return searchTemplate;
    }

    /**
     * 
     * @return searchService property
     */
    public SearchTemplateService getSearchTemplateService() {
        return searchTemplateService;
    }

    /**
     * 
     * @return systemLevel property
     */
    public BooleanOptionGroup getSystemLevel() {
        return systemLevel;
    }

    /**
     * 
     * @return templateFields property
     */
    public List<SearchFieldVo> getTemplateFields() {
        return templateFields;
    }

    /**
     * 
     * @return templateName property
     */
    public String getTemplateName() {
        if (templateName == null || !templateName.equals(getSearchTemplate().getTemplateName())) {
            templateName = getSearchTemplate().getTemplateName();
        }

        return templateName;
    }

    /**
     * 
     * @return templates property
     */
    public List<SearchTemplateVo> getTemplates() {
        return templates;
    }

    /**
     * 
     * @return advancedSearch property
     */
    public boolean isAdvancedSearch() {
        return advancedSearch;
    }

    /**
     * 
     * @return warned property
     */
    public boolean isWarned() {
        return warned;
    }

    /**
     * Loads the appropriate search criteria for a ManagedItem search. Also reset the search results for when this flow is
     * used a second time within an overall parent flow. For example, when editing an item, the user first searches for an
     * item, edits it, and select a parent via the same search flow.
     * 
     * @return SUCCESS
     */
    public String loadItemSearch() {
        if (isAdvancedSearch()) {
            loadAdvancedSearch();
        }
        else {
            loadSimpleSearch();
        }

        return SUCCESS;
    }

    /**
     * Loads the appropriate search criteria when performing a parent search.
     * 
     * @return SUCCESS
     */
    public String loadConditionalSearch() {

        if (searchMode != null) {
            if (searchMode.isProduct()) {
                loadProductSearch();
            }
            else if (searchMode.isOrderableItem()) {
                loadOISearch();
            }
            else {
                loadOISearch();
            }
        }

        return SUCCESS;
    }

    /**
     * Loads the appropriate search criteria for a ManagedItem search. Also reset the search results for when this flow is
     * used a second time within an overall parent flow. For example, when editing an item, the user first searches for an
     * item, edits it, and select a parent via the same search flow.
     * 
     * @return SUCCESS
     */
    public String loadAdvancedSearch() {

        isStartSearchFlow = true;

        if (templateName == null) {
            this.searchTemplate = new SearchTemplateVo();

            // Try Catch comment here rather than in the service call because we don't want the retrieve method to return a
            // blank template in most cases, just when we're trying to set the default. Potentially add a new method for
            // retreieveDefault that may circumvent this
            try {
                String defaultTemplateId = retrieveDefaultTemplateId(getUser());

                if (defaultTemplateId != null) {
                    this.searchTemplate = searchTemplateService.retrieve(defaultTemplateId);
                }

                else {
                    this.searchTemplate = new SearchTemplateVo();
                }
            }
            catch (ItemNotFoundException ex) {
                this.searchTemplate = new SearchTemplateVo();
            }

            this.templateName = searchTemplate.getTemplateName();
        }

        return SUCCESS;
    }

    /**
     * Loads the appropriate search criteria for a ManagedData search. Also reset the search results for when this flow is
     * used a second time within an overall parent flow. For example, when editing an item, the user first searches for an
     * item, edits it, and select a parent via the same search flow.
     * 
     * @return SUCCESS
     * 
     * @see gov.va.med.pharmacy.peps.presentation.common.action.PepsAction#render()
     */
    public String loadDomainSearch() {
        searchTemplate.setSearchCriteria(new SearchCriteriaVo(SearchDomain.DOMAIN, getEnvironment()));
        searchTemplate.retrievePrintTemplate(true, null);

        if (itemType != null && itemType.isDomainType()) {
            if (getEnvironment().isNational() && itemType.isLocalOnlyData()) {
                this.items = Collections.EMPTY_LIST;
            }
            else {
                this.items = managedItemService.retrieve(itemType);
            }
        }

        searchTemplate.setSearchCriteria(new SearchCriteriaVo(SearchDomain.DOMAIN, getEnvironment()));

        return SUCCESS;
    }

    /**
     * Loads the appropriate search criteria for a ManagedItem simple search. Also reset the search results for when this
     * flow is used a second time within an overall parent flow. For example, when editing an item, the user first searches
     * for an item, edits it, and select a parent via the same search flow.
     * 
     * @return SUCCESS
     * 
     * @see gov.va.med.pharmacy.peps.presentation.common.action.PepsAction#render()
     */
    public String loadSimpleSearch() {
        searchTemplate.setSearchCriteria(new SearchCriteriaVo(SearchDomain.SIMPLE, getEnvironment()));
        searchTemplate.retrievePrintTemplate(true, null);

        return SUCCESS;
    }

    /**
     * Loads the appropriate search criteria for a product search.
     * 
     * @return SUCCESS
     * 
     * @see gov.va.med.pharmacy.peps.presentation.common.action.PepsAction#render()
     */
    public String loadProductSearch() {
        searchTemplate.setSearchCriteria(new SearchCriteriaVo(SearchDomain.PRODUCT, getEnvironment()));
        searchTemplate.retrievePrintTemplate(true, null);

        return SUCCESS;
    }

    /**
     * Loads the appropriate search criteria for an OI search.
     * 
     * @return SUCCESS
     * 
     * @see gov.va.med.pharmacy.peps.presentation.common.action.PepsAction#render()
     */
    public String loadOISearch() {
        searchTemplate.setSearchCriteria(new SearchCriteriaVo(SearchDomain.ORDERABLE_ITEM, getEnvironment()));
        searchTemplate.retrievePrintTemplate(true, null);

        return SUCCESS;
    }

    /**
     * Loads the appropriate search criteria for the ATC setup process.
     * 
     * @return SUCCESS
     * 
     * @see gov.va.med.pharmacy.peps.presentation.common.action.PepsAction#render()
     */
    public String loadSetupAtcsSearch() {
        searchTemplate.setSearchCriteria(new SearchCriteriaVo(SearchDomain.SETUP_ATC, getEnvironment()));
        searchTemplate.retrievePrintTemplate(true, null);

        return SUCCESS;
    }

    /**
     * Manage the print template
     * 
     * @return SUCCESS
     */
    public String managePrintTemplate() {
        List<FieldKey> tempFields = new ArrayList<FieldKey>();

        tempFields.add(FieldKey.SELECT);

        // List returned from Manage Print Template screen
        for (SearchFieldVo field : getTemplateFields()) {
            tempFields.add(field.getFieldKey());
        }

        tempFields = FieldKeyUtility.removeDuplicateWithOrder(tempFields);

        // Create a new print template and set the first field as the link field
        PrintTemplateVo printTemplateVo = new PrintTemplateVo();
        printTemplateVo.setTemplateLocation(retrieveTemplateLocation());
        PrintTemplateVo.populatePrintFieldCollection(printTemplateVo, tempFields);

        // set print template to search template
        getSearchTemplate().setPrintTemplate(printTemplateVo);

        return SUCCESS;
    }

    /**
     * Returns the template location appropriate for current search
     * 
     * @return location
     */
    private TemplateLocation retrieveTemplateLocation() {
        if (EntityType.PRODUCT.equals(getItemType())) {
            return TemplateLocation.PRODUCT_SEARCH;
        }
        else if (EntityType.NDC.equals(getItemType())) {
            return TemplateLocation.NDC_SEARCH;
        }
        else {
            return TemplateLocation.ORDERABLE_ITEM_SEARCH;
        }
    }

    /**
     * Loads appropriate print template for the search
     * 
     * @return SUCCESS
     */
    public String printTemplateLoad() {
        templateFields = new ArrayList<SearchFieldVo>();

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

        return SUCCESS;
    }

    /**
     * Removes selected search templates
     * 
     * @return SUCCESS
     * @throws ValueObjectValidationException
     * @throws MissingResourceException
     */
    public String removeSearchTemplates() throws ValueObjectValidationException, MissingResourceException {
        List<SearchTemplateVo> removable = new ArrayList<SearchTemplateVo>();

        for (SearchTemplateVo template : templates) {
            if (template.isSelected()) {
                getSearchTemplateService().delete(getUser(), template);
                removable.add(template);

                if (getSearchTemplate() == null) {
                    setSearchTemplate(new SearchTemplateVo());
                    setTemplateName(getSearchTemplate().getTemplateName());

                }
            }
        }

        templates.removeAll(removable);

        return SUCCESS;
    }

    /**
     * Retrieves a specific search template. Parameters of templateName and templateSystemLevel are passed in through the
     * eventFlowLink tag from manage search templates.
     * 
     * @return SUCCESS
     * @throws MissingResourceException
     * @throws CloneNotSupportedException
     */
    public String retrieveSearchTemplate() throws MissingResourceException, CloneNotSupportedException {
        for (SearchTemplateVo template : templates) {

            if (template.getId().equals(getCurrentSearchTemplateId())) {
                setSearchTemplate((SearchTemplateVo) template.clone());
                setTemplateName(getSearchTemplate().getTemplateName());
            }
        }

        return SUCCESS;
    }

    /**
     * Obtains the saved search templates
     * 
     * @return SUCCESS
     * @throws ValidationException
     */
    public String retrieveSearchTemplates() throws ValidationException {
        this.templates = getSearchTemplateService().retrieve(getUser());

        String defaultTemplateId = retrieveDefaultTemplateId(getUser());

        if (defaultTemplateId == null) {
            return SUCCESS;
        }
        else {
            for (SearchTemplateVo template : templates) {
                if (template.getId().equals(defaultTemplateId)) {
                    template.setDefault(true);
                }
            }
        }

        return SUCCESS;
    }

    /**
     * Saves the search criteria as a search template
     * 
     * @throws Exception when unable to save search
     * @return SUCCESS on success or WARN if there is already a template by that name
     */
    public String saveSearch() throws Exception {

        getSearchTemplate().setId(null);

        if (getSearchTemplateService().create(getUser(), getSearchTemplate(), warned)) {

            if (warned) {
                warned = false;
            }

            return SUCCESS;
        }
        else {
            return WARN;
        }
    }

    /**
     * Searches for a managed item
     * 
     * @return SUCCESS
     * @throws ValidationException
     */
    public String search() throws ValidationException {
        this.isStartSearchFlow = true;

        // We came in from the menu and we haven't selected a search term
        if (searchTemplate.getSearchCriteria().isEmpty() && itemType == null) {
            searchTemplate.resetEntityTypeChanged();

            return SUCCESS;
        }

        this.itemType = searchTemplate.getSearchCriteria().getEntityType();

        initializePagingAttributes();

        if (itemType.isDomainType()) {
            searchTemplate.retrievePrintTemplate(true, null);
        }

        if (getEnvironment().isNational() && itemType.isLocalOnlyData()) {
            this.items = Collections.EMPTY_LIST;
        }
        else if (searchTemplate.isEntityTypeChanged()) {
            searchTemplate.getSearchCriteria().resetSearchTerms();
            this.items = null;
        }
        else {
            this.items = managedItemService.search(searchTemplate.getSearchCriteria());
        }

        // retrieves a new print template if the entity type changed.
        if (searchTemplate.isEntityTypeChanged()) {
            searchTemplate.retrievePrintTemplate(true, null);
            searchTemplate.makeNew();
            searchTemplate.resetEntityTypeChanged();
        }
        else {
            if (searchTemplate.getSearchCriteria().isSimple()) {
                searchTemplate.retrievePrintTemplate(false, searchTemplate.getSearchCriteria().getSearchTerms().get(0)
                    .getFieldKey());
                searchTemplate.makeNew();
                searchTemplate.resetEntityTypeChanged();
            }
            else {
                searchTemplate.retrievePrintTemplate(false, null);
            }
        }

        return SUCCESS;
    }

    /**
     * Set default values for the paging attributes if they are not yet set.
     */
    private void initializePagingAttributes() {
        SearchCriteriaVo searchCriteria = searchTemplate.getSearchCriteria();

        if (searchCriteria.getSortedFieldKey() == null || searchTemplate.isEntityTypeChanged()) {
            FieldKey searchTerm = searchCriteria.getSearchTerms().get(0).getFieldKey();

            if (!(FieldKey.SEARCH_ALL_FIELDS.equals(searchTerm) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS.equals(searchTerm))
                && searchTerm.isSortable()) {
                searchCriteria.setSortedFieldKey(searchTerm);
            }
            else {
                FieldKey sortedFieldKey = ManagedItemVo.newInstance(itemType).getDefaultSearchSortedFieldKey();
                searchCriteria.setSortedFieldKey(sortedFieldKey);
            }
        }

        if (searchCriteria.getSortOrder() == null || searchTemplate.isEntityTypeChanged()) {
            searchCriteria.setSortOrder(SortOrder.ASCENDING);
        }

        // Currently advanced searches are not paged
        if (!searchCriteria.isAdvanced() && (!isExternalPagingRequest() || searchTemplate.isEntityTypeChanged())) {
            searchCriteria.setPageSize(getUser().getTableSizePreference());
            searchCriteria.setStartRow(0);
        }
    }

    /**
     * Page the search results tables.
     * 
     * @param tableId String HTML ID of the table being paged
     * @param sortedFieldKey {@link FieldKey} of the column being sorted, may be null if no sort (or default sort) is
     *            selected
     * @param sortOrder {@link SortOrder} of the column being sorted, may be null if no sort (or default sort) is selected
     * @param startRow integer starting row index within the full list for the next (or previous) page
     * @param pageSize integer number of rows to return for the next (or previous) page
     * 
     * @return SUCCESS
     * 
     * @throws ValidationException if error validating search criteria
     */
    @Override
    protected String pageTable(String tableId, FieldKey sortedFieldKey, SortOrder sortOrder, int startRow, int pageSize)
        throws ValidationException {

        if (SEARCH_RESULTS_TABLE.equals(tableId)) {
            SearchCriteriaVo criteria = searchTemplate.getSearchCriteria();
            criteria.setSortedFieldKey(sortedFieldKey);
            criteria.setSortOrder(sortOrder);
            criteria.setStartRow(startRow);
            criteria.setPageSize(pageSize);

            return search();
        }

        return SUCCESS;
    }

    /**
     * Setter
     * 
     * @param advancedSearch property
     */
    public void setAdvancedSearch(boolean advancedSearch) {
        this.advancedSearch = advancedSearch;
    }

    /**
     * Sets the default search. Now checks to make sure that only one search is selected to be set as defect.
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String setDefaultSearch() throws Exception {

        boolean foundDefault = false;

        // These aren't coming back from manage search templates, so set current to default
        if (templates == null) {
            setDefaultSearch(getUser(), getSearchTemplate());

            return SUCCESS;
        }

        // Otherwise, find the selected search and place it into context
        for (SearchTemplateVo template : templates) {
            if (template.isSelected() && foundDefault) {
                this.addError(new ValidationError(ErrorKey.DUPLICATE_DEFAULT_SEARCH));
            }
            else if (template.isSelected()) {
                setDefaultSearch(getUser(), template);

                setSearchTemplate((SearchTemplateVo) template.clone());
                setTemplateName(getSearchTemplate().getTemplateName());
                foundDefault = true;

            }
            else {
                template.setDefault(false);
            }
        }

        return SUCCESS;
    }

    /**
     * 
     * @param items items property
     */
    public void setItems(List items) {
        this.items = items;
    }

    /**
     * 
     * @param itemType itemType property
     */
    public void setItemType(EntityType itemType) {
        this.itemType = itemType;
    }

    /**
     * 
     * @param managedItemService managedItemService property
     */
    public void setManagedItemService(ManagedItemService managedItemService) {
        this.managedItemService = managedItemService;
    }

    /**
     * If a searchMode is set, only that type will be allowed for a search. This is used for selecting a parent via search.
     * 
     * @param searchMode searchMode property
     */
    public void setSearchMode(EntityType searchMode) {
        this.searchMode = searchMode;
    }

    /**
     * 
     * @param searchTemplate searchTemplate property
     */
    public void setSearchTemplate(SearchTemplateVo searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    /**
     * 
     * @param searchTemplateService searchService property
     */
    public void setSearchTemplateService(SearchTemplateService searchTemplateService) {
        this.searchTemplateService = searchTemplateService;
    }

    /**
     * Setter
     * 
     * @param systemLevel property
     */
    public void setSystemLevel(BooleanOptionGroup systemLevel) {
        this.systemLevel = systemLevel;
    }

    /**
     * Add all the given template fields to a new list of template fields to prevent XWorkList implementations being set on
     * this action.
     * 
     * @param templateFields templateFields property
     */
    public void setTemplateFields(List<SearchFieldVo> templateFields) {
        this.templateFields = new ArrayList<SearchFieldVo>();

        if (templateFields != null) {
            this.templateFields.addAll(templateFields);
        }
    }

    /**
     * Set the template name for the current search
     * 
     * @param templateName property
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;

        if (templateName != null && templateName.trim().length() == 0) {
            this.templateName = null;
        }
    }

    /**
     * Add all the given templates to a new list of templates to prevent XWorkList implementations being set on this action.
     * 
     * @param templates templates property
     */
    public void setTemplates(List<SearchTemplateVo> templates) {
        this.templates = new ArrayList<SearchTemplateVo>();

        if (templates != null) {
            this.templates.addAll(templates);
        }
    }

    /**
     * 
     * @param warned warned property
     */
    public void setWarned(boolean warned) {
        this.warned = warned;
    }

    /**
     * Removes default search as default
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String unDefaultSearch() throws Exception {

        setDefaultSearch(getUser(), null);

        // This is only done through the manage search templates screen
        for (SearchTemplateVo template : templates) {
            if (template.isSelected()) {
                template.setDefault(false);

                return SUCCESS;
            }
        }

        return SUCCESS;
    }

    /**
     * This method is used when a search is to be overwritten and the user needs to be warned.
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String warn() throws Exception {

        warned = true;

        return SUCCESS;
    }

    /**
     * Delete a partially saved ManagedItemVo.
     * 
     * @return END
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    public String deletePartial() throws ItemNotFoundException {
        List<PartialSaveVo> partialSaveVos = getTableSelections(partialItems);
   
        for (PartialSaveVo partialSaveVo : partialSaveVos) {
            if (partialSaveVo != null) {
                managedItemService.deletePartial(partialSaveVo.getId(), partialSaveVo.getEntityType());
            }
        }

        return SUCCESS;
    }

    /**
     * retrieves the default template id from user preferences
     * 
     * @param user object
     * @return id of template
     */
    public String retrieveDefaultTemplateId(UserVo user) {
        return user.getSessionPreferences().get(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID);
    }

    /**
     * Sets the default search preferences.
     * 
     * @param user object
     * @param template to set as default
     * 
     * @throws ValidationException if search template does not validate
     */
    public void setDefaultSearch(UserVo user, SearchTemplateVo template) throws ValidationException {
        if (template == null) {
            user.getSessionPreferences().remove(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID);
        }
        else if (template.getId() == null) {
            SearchTemplateValidator validator = (SearchTemplateValidator) FieldKey.SEARCH_TEMPLATE.newValidatorInstance();
            validator.validateDefault(template, getUser(), getEnvironment());
        }
        else {

            // Set selected search as default in the user object
            user.getSessionPreferences().put(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID, template.getId());
            template.setDefault(true);
        }

        // Saving the preference change to the database
        userService.updatePreferences(user);

    }

    /**
     * 
     * @return templateType property
     */
    public TemplateType getTemplateType() {
        return templateType;
    }

    /**
     * 
     * @param templateType templateType property
     */
    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    /**
     * 
     * @return currentSearchTemplateId property
     */
    public String getCurrentSearchTemplateId() {
        return currentSearchTemplateId;
    }

    /**
     * 
     * @param currentSearchTemplateId currentSearchTemplateId property
     */
    public void setCurrentSearchTemplateId(String currentSearchTemplateId) {
        this.currentSearchTemplateId = currentSearchTemplateId;
    }

    /**
     * 
     * @return userService property
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * 
     * @param userService userService property
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}