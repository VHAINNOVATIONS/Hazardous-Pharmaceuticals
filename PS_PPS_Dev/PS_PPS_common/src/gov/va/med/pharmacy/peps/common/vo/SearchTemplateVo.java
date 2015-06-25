/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;


/**
 * Provide search criteria for search, operates as a search template when saved.
 */
public class SearchTemplateVo extends ValueObject {

    /** DEFAULT_PROD_PRINT_TEMPLATE_ID */
    public static final String DEFAULT_PROD_PRINT_TEMPLATE_ID = "PERSONAL PRODUCT SEARCH";
    
    /** DEFAULT_OI_PRINT_TEMPLATE_ID */
    public static final String DEFAULT_OI_PRINT_TEMPLATE_ID = "PERSONAL ORDERABLE ITEM SEARCH";
    
    /** DEFAULT_NDC_PRINT_TEMPLATE_ID */
    public static final String DEFAULT_NDC_PRINT_TEMPLATE_ID = "PERSONAL NDC SEARCH";

    private static final long serialVersionUID = 1L;

    /**
     * Empty search term placeholder
     */
    private SearchCriteriaVo searchCriteria;

    /**
     * Used as the primary key for the search template
     */
    private String id;

    /**
     * Template property that indicates if the search template has been selected.
     */
    private boolean selected;

    /**
     * Template property for template name
     */
    private String templateName;

    /**
     * Template property for name to be displayed in listing of search templates. When the search is default, this changes to
     * templateName (default)
     */
    private String displayableName;

    /**
     * Template property for notes relating to the saved template.
     */
    private String notes;

    /**
     * Template property that describes if the search template is the default template.
     */
    private boolean isDefault;

    /**
     * Search property for print template associated with the current search.
     */
    private PrintTemplateVo printTemplate;

    /**
     * Type of template: LOCAL, NATIONAL, or PERSONAL
     */
    private TemplateType templateType;

    /**
     * Owner of this search template.
     */
    private UserVo user;

    /**
     * Constructor for search template
     */
    public SearchTemplateVo() {

        this.searchCriteria = new SearchCriteriaVo(SearchDomain.ADVANCED, null);
        this.templateType = TemplateType.PERSONAL;
        this.printTemplate = retrievePrintTemplate(true, null);
    }

    /**
     * Returns the print template appropriate for the search
     * 
     * @param overwrite - overwrites the current print template
     * @param fieldKey - The fieldKey that is being used for the search
     * @return printTemplate
     */
    public PrintTemplateVo retrievePrintTemplate(boolean overwrite, FieldKey fieldKey) {

        if (getEntityType() == null) {
            return null;
        }
        
        if (overwrite || printTemplate == null || templateName == null || !searchCriteria.isAdvanced()) {
            
            setTemplate(fieldKey);
           
        }
        
        return printTemplate;
    }

    /**
     * This method sets the printTemplate based upon the fieldKey.
     * @param fieldKey the fieldKey to base the template upon.
     */
    private void setTemplate(FieldKey fieldKey) {
        
        if (searchCriteria.getSearchDomain().isProductSearch()) {

            printTemplate = DefaultPrintTemplateFactory.defaultProductSearch(false);
        } else if (searchCriteria.getSearchDomain().isSetupAtcSearch()) {
            printTemplate = DefaultPrintTemplateFactory.defaultSetupAtcSearch();
        } else if (searchCriteria.getSearchDomain().isOrderableItemSearch()) {
            printTemplate = DefaultPrintTemplateFactory.defaultOrderableItemSearch(false);
        } else if (getEntityType().isProduct()) {
            printTemplate = productPrintTemplate(fieldKey);
        } else if (getEntityType().isNdc()) {
            if (fieldKey != null && fieldKey.equals(FieldKey.TRADE_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultNdcTradeNameSearch();
            } else if (fieldKey != null && fieldKey.equals(FieldKey.UPC_UPN)) {
                printTemplate = DefaultPrintTemplateFactory.defaultUpcUpnSearch();
            } else if (fieldKey != null && fieldKey.equals(FieldKey.NDC)) {
                printTemplate = DefaultPrintTemplateFactory.defaultNdcSearch();
            } else {
                printTemplate = DefaultPrintTemplateFactory.defaultNdcSearch();
            }
        } else if (getEntityType().isOrderableItem()) {
            if (fieldKey != null && fieldKey.equals(FieldKey.ORDERABLE_ITEM_SYNONYM)) {
                printTemplate = DefaultPrintTemplateFactory.defaultOrderableItemSynonymSearch();
            } else if (fieldKey != null && fieldKey.equals(FieldKey.OI_NAME)) {
                printTemplate = DefaultPrintTemplateFactory.defaultPpsOiNameSearch();
            } else {
                printTemplate = DefaultPrintTemplateFactory.defaultOrderableItemSearch();
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
        
    }
    
    
    
    /**
     * productPrintTemplate
     * @param fieldKey fieldKey
     * @return PrintTemplateVo
     */
    private PrintTemplateVo productPrintTemplate(FieldKey fieldKey) {
        PrintTemplateVo prodPrintTemplate = null;
        
        if (fieldKey != null && fieldKey.equals(FieldKey.ACTIVE_INGREDIENT)) {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultActiveIngredientProductSearch();
        } else if (fieldKey != null && fieldKey.equals(FieldKey.VA_PRODUCT_NAME)) {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultVaProductNameSearch();
    
        } else if (fieldKey != null && fieldKey.equals(FieldKey.VA_PRINT_NAME)) {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultVaPrintNameSearch();
    
        } else if (fieldKey != null && fieldKey.equals(FieldKey.GENERIC_NAME)) {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultGenericNameSearch();
    
        } else if (fieldKey != null && fieldKey.equals(FieldKey.CMOP_ID)) {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultVaProductIdSearch();
    
        } else if (fieldKey != null && fieldKey.equals(FieldKey.PRIMARY_DRUG_CLASS2)) {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultVaDrugClassSearch();
    
        } else if (fieldKey != null && fieldKey.equals(FieldKey.SYNONYM_NAME)) {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultSynonymProductSearch();
        } else {
            prodPrintTemplate = DefaultPrintTemplateFactory.defaultProductSearch();
        }
        
        return prodPrintTemplate;
    }
    

    /**
     * Description
     * 
     * @return printTemplate property
     */
    public PrintTemplateVo getPrintTemplate() {

        return printTemplate;
    }

    /**
     * Description
     * 
     * @param printTemplate property
     */
    public void setPrintTemplate(PrintTemplateVo printTemplate) {

        this.printTemplate = printTemplate;
    }

    /**
     * Description
     * 
     * @return templateName property
     */
    public String getTemplateName() {

        return templateName;
    }

    /**
     * Description
     * 
     * @return displayableName property
     */
    public String getDisplayableName() {

        return displayableName;
    }

    /**
     * Description
     * 
     * @param newDisplayableName property
     */
    public void setDisplayableName(String newDisplayableName) {

        doSetDisplayableName(newDisplayableName);
    }

    /**
     * sets displayable name taking into account if is a default template
     * 
     * @param tDisplayableName displayable name
     */
    private void doSetDisplayableName(String tDisplayableName) {

        if (this.isDefault) {
            this.displayableName = tDisplayableName + "(Default)";
        } else {
            this.displayableName = tDisplayableName;
        }
    }

    /**
     * Sets the template name, and sets the displayable name to the same value.
     * 
     * @param templateName property
     */
    public void setTemplateName(String templateName) {

        this.templateName = templateName;
        
        if (this.printTemplate != null) {
            this.printTemplate.setTemplateName(templateName);
        }

        setDefault(false);
        doSetDisplayableName(templateName);
    }

    /**
     * Sets the attributes for a new template.
     */
    public void makeNew() {

        setTemplateName(null);
        setDefault(false);
        setNotes(null);
    }

    /**
     * Description
     * 
     * @return systemLevel property
     */
    public boolean isSystemLevel() {

        return templateType.isSystemLevel();
    }

    /**
     * Description
     * 
     * @param systemLevel property
     */
    public void setSystemLevel(boolean systemLevel) {

        // for now, sets system to local always for backwards compatibility
        if (systemLevel) {
            this.templateType = TemplateType.LOCAL;
        } else {
            this.templateType = TemplateType.PERSONAL;
        }
    }

    /**
     * Description
     * 
     * @return notes property
     */
    public String getNotes() {

        return notes;
    }

    /**
     * Description
     * 
     * @param notes property
     */
    public void setNotes(String notes) {

        this.notes = trimToEmpty(notes);
    }

    /**
     * Description
     * 
     * @return selected property
     */
    public boolean isSelected() {

        return selected;
    }

    /**
     * Description
     * 
     * @param selected property
     */
    public void setSelected(boolean selected) {

        this.selected = selected;
    }

    /**
     * Description
     * 
     * @return true if is a default template
     */
    public boolean isDefault() {

        return isDefault;
    }

    /**
     * Sets the template as default and changes the displayableName property to indicate that the template is default.
     * 
     * @param defaultTemplate indicates if the template is the current default
     */
    public void setDefault(boolean defaultTemplate) {

        this.isDefault = defaultTemplate;

        doSetDisplayableName(templateName);
    }

    /**
     * Description
     * 
     * @param id object id
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * Description
     * 
     * @return object id as string (is Long)
     */
    public String getId() {

        return id;
    }

    /**
     * Description
     * 
     * @return templateType property
     */
    public TemplateType getTemplateType() {

        return templateType;
    }

    /**
     * Description
     * 
     * @param templateType templateType property
     */
    public void setTemplateType(TemplateType templateType) {

        this.templateType = templateType;
    }

    /**
     * Description
     * 
     * @return searchCriteria property
     */
    public SearchCriteriaVo getSearchCriteria() {

        return searchCriteria;
    }

    /**
     * Description
     * 
     * @param searchCriteria searchCriteria property
     */
    public void setSearchCriteria(SearchCriteriaVo searchCriteria) {

        this.searchCriteria = searchCriteria;
    }

    /**
     * Description
     * 
     * @return entityType property
     */
    public EntityType getEntityType() {

        return searchCriteria.getEntityType();
    }

    /**
     * Description
     * 
     * @param entityType entityType property
     */
    public void setEntityType(EntityType entityType) {

        searchCriteria.setEntityType(entityType);
    }

    /**
     * Description
     * 
     * @return entityTypeChanged property
     */
    public boolean isEntityTypeChanged() {

        return searchCriteria.isEntityTypeChanged();
    }

    /**
     * Description
     * 
     * @param entityTypeChanged entityTypeChanged property
     */
    public void setEntityTypeChanged(boolean entityTypeChanged) {

        searchCriteria.setEntityTypeChanged(entityTypeChanged);
    }

    /**
     * Set the SearchCriteriaVo's entityTypeChanged attribute to false.
     */
    public void resetEntityTypeChanged() {

        searchCriteria.setEntityTypeChanged(false);
    }

    /**
     * Description
     * 
     * @return user property
     */
    public UserVo getUser() {

        return user;
    }

    /**
     * Description
     * 
     * @param user user property
     */
    public void setUser(UserVo user) {

        this.user = user;
    }
}
