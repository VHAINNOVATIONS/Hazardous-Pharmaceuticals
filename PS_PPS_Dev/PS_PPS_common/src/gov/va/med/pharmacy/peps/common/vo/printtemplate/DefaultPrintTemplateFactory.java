/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * PrintTemplate factory class for default PrintTemplates
 * <p>
 * NOTE: If new columns are added to a print template that uses external paging, the respective FieldKeys must be also
 * configured in the gov.va.med.pharmacy.peps.domain.common.utility.SchemaUtility class. If this configuration is not done,
 * the sorting will not work!
 */
public class DefaultPrintTemplateFactory {

    /**
     * List of possible print template fields
     */
    private static final List<FieldKey> POSSIBLE_FIELDS = new ArrayList<FieldKey>();
    private static final String ITEM_ID = "itemId";
    private static final String ITEM_TYPE = "itemType";
    private static final String EDIT = "edit";
    private static final String ORDERABLE_ITEM = "ORDERABLE_ITEM";
    private static final String TAB = "tab";
    private static final String CHILDREN_TAB = "children_tab";
    private static final String SELECT_PARENT = "selectParent";
    private static final String OPEN_TEMPLATE = "createFromTable";
    private static final String RETRIEVE = "retrieve";
    private static final String PRODUCT = "PRODUCT";
    private static final String REQUEST_ID = "requestId";
    private static final String HISTORY_TAB = "history_tab";
    
    // CEW RENAMED FROM matchResultProductSearch to matchResults on 3/23/2012
    private static final String MATCH_RESULT_PRODUCT_SEARCH = "matchResultProductSearch";
    private static final String EVT_EDIT_PART_ITEM = "editPartialItem";
    private static final String FDB_DETAILS = "fdbDetails";
    private static final String FDB_UPDATE_DETAILS = "fdbUpdateEdit";
    private static final String NDC_ITEM = "ndc";
    private static final String CAP_NDC = "NDC";
    private static final String ENTITYTYPE = "entityType";
    private static final String ID = "id";

//    private static final String PRODUCT_ID = "productFk";
    private static final String MOVE_CHILDREN = "moveChildren";

    static {
        POSSIBLE_FIELDS.add(FieldKey.TRADE_NAME);
        POSSIBLE_FIELDS.add(FieldKey.GENERIC_NAME);
        POSSIBLE_FIELDS.add(FieldKey.PRIMARY_DRUG_CLASS);
        POSSIBLE_FIELDS.add(FieldKey.PRIMARY_DRUG_CLASS2);
        POSSIBLE_FIELDS.add(FieldKey.CMOP_ID);
        POSSIBLE_FIELDS.add(FieldKey.CODE);
        POSSIBLE_FIELDS.add(FieldKey.DOSAGE_FORM);
        POSSIBLE_FIELDS.add(FieldKey.PRODUCT_STRENGTH);
        POSSIBLE_FIELDS.add(FieldKey.PRODUCT_UNIT);
        POSSIBLE_FIELDS.add(FieldKey.OI_ROUTE);
        POSSIBLE_FIELDS.add(FieldKey.NDC);
        POSSIBLE_FIELDS.add(FieldKey.MANUFACTURER);
        POSSIBLE_FIELDS.add(FieldKey.OI_NAME);
        POSSIBLE_FIELDS.add(FieldKey.DISPENSE_QUANTITY_LIMIT);
        POSSIBLE_FIELDS.add(FieldKey.DISPENSE_UNIT);
        POSSIBLE_FIELDS.add(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);
        POSSIBLE_FIELDS.add(FieldKey.DEA_SCHEDULE);
        POSSIBLE_FIELDS.add(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        POSSIBLE_FIELDS.add(FieldKey.FORMULARY);
        POSSIBLE_FIELDS.add(FieldKey.DEFAULT_MAIL_SERVICE);
        POSSIBLE_FIELDS.add(FieldKey.RECALL_LEVEL);
        POSSIBLE_FIELDS.add(FieldKey.CATEGORIES);
        POSSIBLE_FIELDS.add(FieldKey.APPROVED_FOR_SPLITTING);
        POSSIBLE_FIELDS.add(FieldKey.APPLICATION_PACKAGE_USE);
        POSSIBLE_FIELDS.add(FieldKey.SCORED);
        POSSIBLE_FIELDS.add(FieldKey.LABS_ORDER_ENTRY);
        POSSIBLE_FIELDS.add(FieldKey.VITALS_ORDER_ENTRY);
        POSSIBLE_FIELDS.add(FieldKey.WITNESS_TO_ADMINISTRATION);
        POSSIBLE_FIELDS.add(FieldKey.DO_NOT_MAIL);
        POSSIBLE_FIELDS.add(FieldKey.PATIENT_SPECIFIC_LABEL);
        POSSIBLE_FIELDS.add(FieldKey.REFRIGERATION);
        POSSIBLE_FIELDS.add(FieldKey.HIGH_RISK);
        POSSIBLE_FIELDS.add(FieldKey.PROTECT_FROM_LIGHT);
        POSSIBLE_FIELDS.add(FieldKey.TRANSMIT_TO_CMOP);
        POSSIBLE_FIELDS.add(FieldKey.CMOP_DISPENSE);
        POSSIBLE_FIELDS.add(FieldKey.DO_NOT_HANDLE_IF_PREGNANT);
        POSSIBLE_FIELDS.add(FieldKey.FOLLOW_UP_TIME);
        POSSIBLE_FIELDS.add(FieldKey.HAZARDOUS_TO_DISPOSE);
        POSSIBLE_FIELDS.add(FieldKey.HAZARDOUS_TO_HANDLE);
        POSSIBLE_FIELDS.add(FieldKey.HAZARDOUS_TO_PATIENT);
        POSSIBLE_FIELDS.add(FieldKey.LONG_TERM_OUT_OF_STOCK);
        POSSIBLE_FIELDS.add(FieldKey.LOW_SAFETY_MARGIN);
        POSSIBLE_FIELDS.add(FieldKey.NON_RENEWABLE);
        POSSIBLE_FIELDS.add(FieldKey.ITEM_STATUS);
        POSSIBLE_FIELDS.add(FieldKey.REQUEST_ITEM_STATUS);
        POSSIBLE_FIELDS.add(FieldKey.UPC_UPN);

//        POSSIBLE_FIELDS.add(FieldKey.LOCAL_USE);

    }

    /**
     * Cannot instantiate
     */
    private DefaultPrintTemplateFactory() {

        super();
    }

    /**
     * Sets, sorts, and returns possible fields for print template
     * 
     * @return List of FieldKeys
     */
    public static List<FieldKey> getPossibleFields() {
        return POSSIBLE_FIELDS;
    }

    /**
     * Retrieves the default print template using the associated template location.
     * 
     * @param templateLocation template location used to specify the default print template
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo getDefaultPrintTemplate(TemplateLocation templateLocation) {

        PrintTemplateVo printTemplate;

        if (templateLocation.isEditableModificationSummary()) {
            printTemplate = defaultEditableModificationSummary();
        } else if (templateLocation.isNoneditableModificationSummary()) {
            printTemplate = defaultNoneditableModificationSummary();
        } else if (templateLocation.isNdcList()) {
            printTemplate = defaultNdcList();
        } else if (templateLocation.isNdcSearch()) {
            printTemplate = defaultNdcSearch();
        } else if (templateLocation.isOrderableItemSearch()) {
            printTemplate = defaultOrderableItemSearch();
        } else if (templateLocation.isProductList()) {
            printTemplate = defaultProductList();
        } else if (templateLocation.isRequestSearch()) {
            printTemplate = defaultRequestSearch();
        } else if (templateLocation.isSiteConfiguration()) {
            printTemplate = defaultSiteConfiguration();
        } else if (templateLocation.isNationalStatusConfiguration()) {
            printTemplate = defaultStatusConfiguration();
        } else if (templateLocation.isPartialSaveConfiguration()) {
            printTemplate = defaultPartialSave();
        } else if (templateLocation.isManageSearchTemplates()) {
            printTemplate = defaultManageSearchTemplates();
        } else if (templateLocation.isManagedDataSearch()) {
            printTemplate = defaultManagedDataSearch();
        } else { // if (templateLocation.isProductSearch()) {
            printTemplate = defaultProductSearch();
        }

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the Modification Summary Editable table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultEditableModificationSummary() {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.EDITABLE_MODIFICATION_SUMMARY);
        printTemplate.addTextField(FieldKey.FIELD_NAME);
        printTemplate.addTextField(FieldKey.CURRENT_VALUE);
        printTemplate.addTextField(FieldKey.MODIFIED_VALUE);
        printTemplate.addInputField(FieldKey.MODIFICATION_REASON);
        printTemplate.addInputField(FieldKey.ACCEPT_CHANGE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the Modification Summary non-editable table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultNoneditableModificationSummary() {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.NONEDITABLE_MODIFICATION_SUMMARY);
        printTemplate.addTextField(FieldKey.FIELD_NAME);
        printTemplate.addTextField(FieldKey.CURRENT_VALUE);
        printTemplate.addTextField(FieldKey.MODIFIED_VALUE);
        printTemplate.addInputField(FieldKey.MODIFICATION_REASON);
        printTemplate.addInputField(FieldKey.REQUEST_TO_MODIFY_VALUE);
        printTemplate.addInputField(FieldKey.REQUEST_TO_MAKE_EDITABLE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a list of Products on an Orderable Item.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultProductList() {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_LIST);

        Map<String, Object> itemLinkParameters = new HashMap<String, Object>();
        itemLinkParameters.put(ITEM_ID, FieldKey.ID);
        itemLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

//        printTemplate.addRedirectFlowLinkField(FieldKey.VA_PRODUCT_NAME, "manageItem", itemLinkParameters);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, itemLinkParameters);

        printTemplate.addTextField(FieldKey.VA_PRINT_NAME);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.APPLICATION_PACKAGE_USE);

        // FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT removed as it is a local only field
        // printTemplate.addTextField(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT);
        printTemplate.addTextField(FieldKey.NDC_COUNT, false);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a list of NDCs on a Product.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultNdcList() {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_LIST);

        Map<String, Object> itemLinkParameters = new HashMap<String, Object>();
        itemLinkParameters.put(ITEM_ID, FieldKey.ID);
        itemLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.NDC, EDIT, itemLinkParameters);

        printTemplate.addTextField(FieldKey.TRADE_NAME);
        printTemplate.addTextField(FieldKey.VENDOR_STOCK_NUMBER);
        printTemplate.addTextField(FieldKey.PACKAGE_SIZE);
        printTemplate.addTextField(FieldKey.PACKAGE_TYPE);
        printTemplate.addTextField(FieldKey.NDC_PRICE_PER_ORDER_UNIT);
        printTemplate.addTextField(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        printTemplate.addTextField(FieldKey.MANUFACTURER);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.UPC_UPN);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultProductSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.VA_PRINT_NAME);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DISPENSE_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM); // added.
        printTemplate.addTextField(FieldKey.CMOP_ID);

        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);

        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);

        //defaultsProductSearch fields
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        // defaultProductSearch fields for item_id, type and children.
        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

//      defaultProductSearchFields
        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        return printTemplate;
    }

    /**
     * Create a new instance of the fields available for display on a Product search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo availableProductSearchFields() {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);


        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);

        printTemplate.addTextField(FieldKey.VA_PRINT_NAME);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM); // added.
        printTemplate.addTextField(FieldKey.CMOP_ID);

        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);
        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);

        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.DISPENSE_UNIT);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);
        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        return printTemplate;
    }
    
    /**
     * Create a new instance of the default fields displayed for an OI search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo selectParentProductSearch() {
        return selectParentProductSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @return PrintTemplateVo
     * @param templateLink display a link allowing the user to create a new item using the exiting row item as a template
     */
    public static PrintTemplateVo selectParentProductSearch(boolean templateLink) {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);

        if (templateLink) {
            Map<String, Object> templatelinkParameters = new HashMap<String, Object>();
            templatelinkParameters.put(ITEM_ID, FieldKey.ID);
            templatelinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
            printTemplate.addLinkField(FieldKey.CREATE_NEW, OPEN_TEMPLATE, templatelinkParameters, false);
        }

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, SELECT_PARENT, linkParameters);

        // selectParentProductSearch
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.CMOP_ID);
        printTemplate.addTextField(FieldKey.OI_NAME);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

        // selectParentProductSearch
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);
        printTemplate.addTextField(FieldKey.NDC_COUNT);

        return printTemplate;
    }

    /**
     * fdbProductSearch 
     * @return PrintTemplateVo
    */
    public static PrintTemplateVo matchResultProductSearch() {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, MATCH_RESULT_PRODUCT_SEARCH, linkParameters);

        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH); //f/k/a DISPLAYABLE_INGREDIENT_STRENGTH
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT); //f/k/a DISPLAYABLE_INGREDIENT_UNIT
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.CMOP_ID);
        printTemplate.addTextField(FieldKey.OI_NAME);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);
        printTemplate.addTextField(FieldKey.LOCAL_USE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);
        printTemplate.addTextField(FieldKey.NDC_COUNT);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultActiveIngredientProductSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);
        printTemplate.addTextField(FieldKey.DISPLAYABLE_INGREDIENT_NAME);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);
        printTemplate.addLinkField(FieldKey.OI_NAME, RETRIEVE, linkParametersOI);

        // defaultActiveIngredientProductSearch
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        // defaultActiveIngredientProductSearch
        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        // return the template for the defaultActiveIngredientProductSearch
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search for the defaultSynonymProductSearch
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultSynonymProductSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);
        printTemplate.addTextField(FieldKey.DISPLAYABLE_SYNONYM_NAME);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);
        
        // defaultSynonymProductSearch
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);

        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);

        // defaultSynonymProductSearch
        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

        // defaultSynonymProductSearch
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        // defaultSynonymProductSearch
        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        // return the template for the defaultSynonymProductSearch
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product CMOP ID/VA Product ID search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaProductIdSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);
        printTemplate.addTextField(FieldKey.CMOP_ID);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);

        // add Linked Parameters
        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);

        //  defaultVaProductIdSearch
        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        //  defaultVaProductIdSearch
        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);
        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        // return the template for the defaultVaProductIdSearch
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaPrintNameSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);
        printTemplate.addTextField(FieldKey.VA_PRINT_NAME);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

//        defaultVaPrintNameSearch
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);

        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);

//      defaultVaPrintNameSearch
        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);

//      defaultVaPrintNameSearch
        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

//      defaultVaPrintNameSearch
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);

//      defaultVaPrintNameSearch
        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

//      defaultVaPrintNameSearch
        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        // return the template for the defaultVaPrintNameSearch
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaProductNameSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        
        // Set the dosage form value for defaultVaProductNameSearch
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.CMOP_ID);

        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);

        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

//       This is the defaultVaProductNameSearch
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);

//      This is the defaultVaProductNameSearch
        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
       
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

//       This is the defaultVaProductNameSearch
        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        // return the template for the defaultVaProductNameSearch
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product Drug Class search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaDrugClassSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);

        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);

        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);

        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);

        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

//      This is the defaultVaDrugClassSearch
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

//        This is the defaultVaDrugClassSearch
        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a defaultGenericNameSearch search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultGenericNameSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);

        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, linkParameters);

        Map<String, Object> linkParametersOI = new HashMap<String, Object>();
        linkParametersOI.put(ITEM_ID, FieldKey.PARENT_ID);
        linkParametersOI.put(ITEM_TYPE, ORDERABLE_ITEM);

        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParametersOI);

        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

        printTemplate.addLinkField(FieldKey.NDC_COUNT, EDIT, childrenLinkParameters, false);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultProductSearch() {
        return defaultProductSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product Active Ingredient search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultActiveIngredientProductSearch() {
        return defaultActiveIngredientProductSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product Active Ingredient search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultSynonymProductSearch() {
        return defaultSynonymProductSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product CMOP Id search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaProductIdSearch() {
        return defaultVaProductIdSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product Generic Name search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultGenericNameSearch() {
        return defaultGenericNameSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product VA Product Name search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaProductNameSearch() {
        return defaultVaProductNameSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product Drug Class search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaDrugClassSearch() {
        return defaultVaDrugClassSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product VA Print Name search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVaPrintNameSearch() {
        return defaultVaPrintNameSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a Product search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultManagedDataSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MANAGED_DATA_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VALUE, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        // return the printTemplate for the defaultManagedDataSearch
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search for the defaultDoseUnitSearch
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultDoseUnitSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MANAGED_DATA_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.DOSE_UNIT_NAME, EDIT, linkParameters);
        printTemplate.addLinkField(FieldKey.DOSE_UNIT_NAME, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search for the defaultDrugCodeSearch
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultDrugCodeSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MANAGED_DATA_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.CODE, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search for the defaultDrugClassSearch
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultDrugClassSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MANAGED_DATA_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.CLASSIFICATION, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a Product search for the defaultDrugTextSearch
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultDrugTextSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MANAGED_DATA_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.VALUE, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);
        printTemplate.addTextField(FieldKey.DRUG_TEXT_TYPE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for an NDC search for the defaultNDCSearch
     * 
     * @param multiSelect Indicates whether the multi-sect button was pressed
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultNdcSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.NDC, EDIT, linkParameters);

        printTemplate.addTextField(FieldKey.TRADE_NAME);
        commonNdcFields(printTemplate);

        // return the print templagte for the default NDC Search.
        return printTemplate;
    }

    /**
     * Create a new instance of the fields available for displayed on an NDC search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo availableNdcSearchFields() {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.NDC, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.PACKAGE_SIZE);
        printTemplate.addTextField(FieldKey.PACKAGE_TYPE);
        printTemplate.addTextField(FieldKey.MANUFACTURER);
        printTemplate.addTextField(FieldKey.CMOP_ID);
        printTemplate.addTextField(FieldKey.TRADE_NAME);

        commonNdcFields(printTemplate);
        printTemplate.addTextField(FieldKey.UPC_UPN);

        // return the printtemplate for the availableNdcSearchFields
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for an NDC trade namesearch.
     * 
     * @param multiSelect Indicates whether the multi-select button was pressed or not
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultNdcTradeNameSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);
        printTemplate.addTextField(FieldKey.TRADE_NAME);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.NDC, EDIT, linkParameters);

        commonNdcFields(printTemplate);
        
        return printTemplate;
    }
    
    /**
     * Create a new instance of the default fields displayed for a UPC-UPN search 
     * 
     * @param multiSelect Indicates whether the multi-select button was pressed
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultUpcUpnSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);
        printTemplate.addTextField(FieldKey.UPC_UPN);
        
        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.NDC, EDIT, linkParameters);

        printTemplate.addTextField(FieldKey.TRADE_NAME);
        commonNdcFields(printTemplate);

        return printTemplate;
    }

    /**
     * commonNdcFields
     *
     * @param printTemplate PrintTemplateVo
     */
    private static void commonNdcFields(PrintTemplateVo printTemplate) {
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        
        Map<String, Object> genericLinkParameters = new HashMap<String, Object>();
        genericLinkParameters.put(ITEM_ID, FieldKey.PARENT_ID);
        genericLinkParameters.put(ITEM_TYPE, PRODUCT);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, genericLinkParameters);
        
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);        
    }

    /**
     * Create a new instance of the default fields displayed for an NDC search.
     * 
     * @param searchMode Indicates whether the multi-select button was pressed or not
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo fdbAddSearch(boolean searchMode) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(NDC_ITEM, FieldKey.NDC);
        printTemplate.addLinkField(FieldKey.NDC, FDB_DETAILS, linkParameters);
        printTemplate.addTextField(FieldKey.GCN_SEQUENCE_NUMBER);
        printTemplate.addTextField(FieldKey.PACKAGE_SIZE);
        printTemplate.addTextField(FieldKey.PACKAGE_TYPE);
        printTemplate.addTextField(FieldKey.MANUFACTURER);
        printTemplate.addTextField(FieldKey.FDB_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.LABEL_NAME);
        printTemplate.addTextField(FieldKey.ADDITIONAL_DESC);

        if (searchMode) {
            printTemplate.addTextField(FieldKey.OBSOLETE_DATE);
        } else {
            printTemplate.addTextField(FieldKey.FDB_CREATION_DATE);
        }

        return printTemplate;
    }
    
    
    /**
     * Create a new instance of the default fields displayed for an fdbUpdate
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo fdbUpdate() {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ID, FieldKey.NDC_FK);
        linkParameters.put(ENTITYTYPE, NDC_ITEM);
        printTemplate.addLinkField(FieldKey.NDC, FDB_UPDATE_DETAILS, linkParameters);

        Map<String, Object> linkParameters2 = new HashMap<String, Object>();
        linkParameters2.put(ID, FieldKey.PRODUCT_FK);
        linkParameters2.put(ENTITYTYPE, PRODUCT);
        
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, FDB_UPDATE_DETAILS, linkParameters2);
        printTemplate.addTextField(FieldKey.FDB_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.GCN_SEQUENCE_NUMBER);
        printTemplate.addTextField(FieldKey.MESSAGE);
        printTemplate.addTextField(FieldKey.CREATED_DATE);

        return printTemplate;
    }
    
    /**
     * Create a new instance of the default fields displayed for an FDB Added Report.
     * 
     * @param searchMode Indicates whether the multi-select button was pressed or not
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo fdbAddedReport(boolean searchMode) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.NDC_FK);
        linkParameters.put(ITEM_TYPE, CAP_NDC);
        printTemplate.addLinkField(FieldKey.NDC, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.PACKAGE_SIZE);
        printTemplate.addTextField(FieldKey.PACKAGE_TYPE);
        printTemplate.addTextField(FieldKey.ADDITIONAL_DESC);
        printTemplate.addTextField(FieldKey.GCN_SEQUENCE_NUMBER);
        printTemplate.addTextField(FieldKey.VA_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.LABEL_NAME);
        printTemplate.addTextField(FieldKey.FDB_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.CREATED_DATE);

        return printTemplate;
    }
    
    /**
     * Create a new instance of the default fields displayed for an FDB Updated Report.
     * 
     * @param searchMode Indicates whether the multi-select button was pressed or not
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo fdbUpdatedReport(boolean searchMode) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NDC_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.NDC_FK);
        linkParameters.put(ITEM_TYPE, CAP_NDC);
        printTemplate.addLinkField(FieldKey.NDC, EDIT, linkParameters);
        printTemplate.addTextField(FieldKey.VA_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.FDB_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.GCN_SEQUENCE_NUMBER);
        printTemplate.addTextField(FieldKey.MESSAGE);
        printTemplate.addTextField(FieldKey.CREATED_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for an NDC Tradename search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultNdcTradeNameSearch() {
        return defaultNdcTradeNameSearch(true);
    }
    
    /**
     * Create a new instance of the default fields displayed for a UPC-UPN search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultUpcUpnSearch() {
        return defaultUpcUpnSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for an NDC search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultNdcSearch() {
        return defaultNdcSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for the pending requests search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultDomainRequestSearch() {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.REQUEST_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.REQUEST_ITEM_ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        linkParameters.put(REQUEST_ID, FieldKey.REQUEST_ID);
        printTemplate.addLinkField(FieldKey.DOMAIN_NAME, "manageDomainRequest", linkParameters);
        printTemplate.addTextField(FieldKey.REQUEST_TYPE);
        printTemplate.addTextField(FieldKey.ENTITY_TYPE);
        printTemplate.addTextField(FieldKey.REQUEST_STATE);
        printTemplate.addTextField(FieldKey.CREATED_DATE);
        printTemplate.addTextField(FieldKey.REVIEWED_BY);
        printTemplate.addTextField(FieldKey.UNDER_REVIEW);
        printTemplate.addTextField(FieldKey.PSR_NAME);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the pending requests search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultRequestSearch() {

        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.REQUEST_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.REQUEST_ITEM_ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        linkParameters.put(REQUEST_ID, FieldKey.REQUEST_ID);

        //linkParameters.put(TAB, "requests_tab");
        printTemplate.addLinkField(FieldKey.ITEM_NAME, "manageRequest", linkParameters);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.DISPENSE_UNIT);
        printTemplate.addTextField(FieldKey.ENTITY_TYPE);
        printTemplate.addTextField(FieldKey.CATEGORIES);
        printTemplate.addTextField(FieldKey.REQUEST_TYPE);
        printTemplate.addTextField(FieldKey.REQUEST_STATE);
        printTemplate.addTextField(FieldKey.CREATED_DATE);
        printTemplate.addTextField(FieldKey.REVIEWED_BY);
        printTemplate.addTextField(FieldKey.UNDER_REVIEW);
        printTemplate.addTextField(FieldKey.PSR_NAME);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a PPS-N OI Name search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultPpsOiNameSearch() {
        return defaultPpsOiNameSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for a PPS-N OI Name searchh.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultPpsOiNameSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.ORDERABLE_ITEM_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParameters);

        
        // add the text field for the defaultPpsOiNameSearch
        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_TYPE);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_SYNONYM);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        //     Create the Children links for the OI
        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

        printTemplate.addLinkField(FieldKey.PRODUCT_COUNT, EDIT, childrenLinkParameters, false);

        return printTemplate;
    }
    
    /**
     * Create a new instance of the default fields displayed for an Orderable Item search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultOrderableItemSearch() {
        return defaultOrderableItemSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for an Orderable Item search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultOrderableItemSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.ORDERABLE_ITEM_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParameters);

        commonOiSearch(printTemplate);

        return printTemplate;
    }

    /**
     * Create a new instance of the all fields available display fields for an Orderable Item search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo availableOrderableItemSearchFields() {

        // as of 2012-06-25, this is the same as defaultOrderableItemSearch(false)
        return defaultOrderableItemSearch(false);
    }
    
    /**
     * Create a new instance of the default fields displayed for an OI search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo selectParentOrderableItemSearch() {
        return selectParentOrderableItemSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for an Orderable Item search.
     * 
     * 
     * @return PrintTemplateVo
     * @param templateLink display a link allowing the user to create a new item using the exiting row item as a template
     */
    public static PrintTemplateVo selectParentOrderableItemSearch(boolean templateLink) {

        PrintTemplateVo printTemplate = new PrintTemplateVo();

        if (templateLink) {
            Map<String, Object> templatelinkParameters = new HashMap<String, Object>();
            templatelinkParameters.put(ITEM_ID, FieldKey.ID);
            templatelinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
            printTemplate.addLinkField(FieldKey.CREATE_NEW, OPEN_TEMPLATE, templatelinkParameters, false);
        }

        printTemplate.setTemplateLocation(TemplateLocation.ORDERABLE_ITEM_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.OI_NAME, SELECT_PARENT, linkParameters);

        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_TYPE);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_SYNONYM);

//        printTemplate.addTextField(FieldKey.LOCAL_USE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.CATEGORIES);
        printTemplate.addTextField(FieldKey.PRODUCT_COUNT);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for an Orderable Item search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultOrderableItemSynonymSearch() {
        return defaultOrderableItemSynonymSearch(true);
    }

    /**
     * Create a new instance of the default fields displayed for an Orderable Item Synonym search.
     * 
     * @param multiSelect allow multiple selections for the search results
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultOrderableItemSynonymSearch(boolean multiSelect) {

        PrintTemplateVo printTemplate = new PrintTemplateVo(multiSelect);
        printTemplate.setTemplateLocation(TemplateLocation.ORDERABLE_ITEM_SEARCH);
        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_SYNONYM);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        linkParameters.put(TAB, "synonyms_tab");

        printTemplate.addLinkField(FieldKey.OI_NAME, EDIT, linkParameters);

        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_TYPE);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);

        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

        printTemplate.addLinkField(FieldKey.PRODUCT_COUNT, EDIT, childrenLinkParameters, false);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the search templates table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultManageSearchTemplates() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MANAGE_SEARCH_TEMPLATES);

        Map<String, Object> nameParameters = new HashMap<String, Object>();
        nameParameters.put("templateName", FieldKey.SEARCH_TEMPLATE_NAME);

        printTemplate.addLinkField(FieldKey.SEARCH_TEMPLATE_NAME, EDIT, nameParameters);
        printTemplate.addTextField(FieldKey.SEARCH_TEMPLATE_TEMPLATE_TYPE);
        printTemplate.addTextField(FieldKey.SEARCH_TEMPLATE_NOTES);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the default site configuration table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultSiteConfiguration() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.SITE_CONFIGURATION);
        printTemplate.addTextField(FieldKey.SITE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the default national status configuration table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultStatusConfiguration() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.SITE_CONFIGURATION);

        printTemplate.addTextField(FieldKey.SITE_NUMBER);
        printTemplate.addTextField(FieldKey.SITE_NAME);
        printTemplate.addTextField(FieldKey.SITE_SERVER_NAME);
        printTemplate.addTextField(FieldKey.SITE_COTS_DB_VERSION);
        printTemplate.addTextField(FieldKey.SITE_PEPS_DB_VERSION);
        printTemplate.addTextField(FieldKey.SITE_VERSION_INFO_DATE);
        printTemplate.addTextField(FieldKey.SITE_VISTA_MESSAGE_SYNCHRONIZATION);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for partial save.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultPartialSave() {
        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.PARTIAL_SAVE);

        Map<String, Object> itemLinkParameters = new HashMap<String, Object>();
        itemLinkParameters.put(ITEM_ID, FieldKey.ID);
        itemLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addTextField(FieldKey.PARTIAL_CATEGORY);

        printTemplate.addLinkField(FieldKey.COMMENT, EVT_EDIT_PART_ITEM, itemLinkParameters);
        printTemplate.addTextField(FieldKey.CREATED_BY);
        printTemplate.addTextField(FieldKey.MODIFIED_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for notifications.
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultNotifications() {
        PrintTemplateVo printTemplate = new PrintTemplateVo(true);
        printTemplate.setTemplateLocation(TemplateLocation.NOTIFICATIONS);

        Map<String, Object> itemLinkParameters = new HashMap<String, Object>();
        itemLinkParameters.put(ITEM_ID, FieldKey.ID);
        itemLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        itemLinkParameters.put(TAB, HISTORY_TAB);
        itemLinkParameters.put("notificationId", FieldKey.NOTIFICATION_ID);
        itemLinkParameters.put("status", FieldKey.NOTIFICATION_STATUS);

        printTemplate.addTextField(FieldKey.NOTIFICATION_TYPE);
        printTemplate.addTextField(FieldKey.SOURCE);

        printTemplate.addLinkField(FieldKey.ITEM_NAME, "updateViewedBy", itemLinkParameters);
        printTemplate.addTextField(FieldKey.ORIGINATOR);
        printTemplate.addTextField(FieldKey.NOTIFICATION_DATE);
        printTemplate.addPopupLink(FieldKey.VIEWED_BY_COUNT, FieldKey.VIEWED_BY);
        printTemplate.addTextField(FieldKey.NOTIFICATION_HIDE);

        return printTemplate;
    }

    /**
     * Create a new instance of the fields displayed for notification links on the home page.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo homePageNotifications() {
        PrintTemplateVo printTemplate = new PrintTemplateVo(false);

        Map<String, Object> itemLinkParameters = new HashMap<String, Object>();
        itemLinkParameters.put(ITEM_ID, FieldKey.ID);
        itemLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        itemLinkParameters.put(TAB, HISTORY_TAB);

        printTemplate.addLinkField(FieldKey.ITEM_NAME, EDIT, itemLinkParameters);
        printTemplate.addTextField(FieldKey.NOTIFICATION_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the fields displayed for partial save links on the home page.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo homePagePartialSaves() {
        PrintTemplateVo printTemplate = new PrintTemplateVo(false);
        printTemplate.setTemplateLocation(TemplateLocation.PARTIAL_SAVE);

        Map<String, Object> itemLinkParameters = new HashMap<String, Object>();
        itemLinkParameters.put(ITEM_ID, FieldKey.ID);
        itemLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);

        printTemplate.addLinkField(FieldKey.COMMENT, EVT_EDIT_PART_ITEM, itemLinkParameters);
        printTemplate.addTextField(FieldKey.MODIFIED_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the default national status configuration table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultItemAuditHistoryConfiguration() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.SITE_CONFIGURATION);

        printTemplate.addTextField(FieldKey.EVENT_CATEGORY);
        printTemplate.addTextField(FieldKey.DETAIL_CATEGORY);
        printTemplate.addTextField(FieldKey.FIELD_NAME);
        printTemplate.addTextField(FieldKey.REASON);
        printTemplate.addTextField(FieldKey.OLD_VALUE);
        printTemplate.addTextField(FieldKey.NEW_VALUE);
        printTemplate.addTextField(FieldKey.DT_MODIFIED);
        printTemplate.addTextField(FieldKey.USERNAME);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a setup ATCs search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultSetupAtcSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.SETUP_ATC_SEARCH);
        printTemplate.addTextField(FieldKey.LOCAL_PRINT_NAME);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, RETRIEVE, linkParameters);

        printTemplate.addTextField(FieldKey.ITEM_STATUS);

        return printTemplate;
    }

    /**
     * Fields displayed for a RX Consult.
     * 
     * @return {@link PrintTemplateVo}
     */
    public static PrintTemplateVo defaultRxConsult() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.WARNING_LABEL);

        printTemplate.addTextField(FieldKey.VALUE, false);
        printTemplate.addTextField(FieldKey.CONSULT_TEXT, false);

        return printTemplate;
    }

    /**
     * Fields displayed for Migration Summary Table.
     * 
     * @return {@link PrintTemplateVo}
     */
    public static PrintTemplateVo defaultMigrationSummary() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MIGRATION_SUMMARY);
        printTemplate.addTextField(FieldKey.MIGRATION_FILE_NAME);
        printTemplate.addTextField(FieldKey.ROWS_PROCESSED_QTY);
        printTemplate.addTextField(FieldKey.ROWS_NOT_MIGRATED_QTY);
        printTemplate.addTextField(FieldKey.ROWS_MIGRATED_QTY);

        return printTemplate;
    }

    /**
     * Fields displayed for Migration Error Table.
     * 
     * @return {@link PrintTemplateVo}
     */
    public static PrintTemplateVo defaultMigrationError() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MIGRATION_ERROR);
        printTemplate.addTextField(FieldKey.MIGRATION_ROW_ID);
        printTemplate.addTextField(FieldKey.MIGRATION_FIELD_NAME);
        printTemplate.addTextField(FieldKey.DETAILED_ERROR_TEXT);

        return printTemplate;
    }

    /**
     * Fields displayed for Migration "Multiples" Error Table.
     * 
     * @return {@link PrintTemplateVo}
     */
    public static PrintTemplateVo defaultMigrationMultiError() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.MIGRATION_ERROR);
        printTemplate.addTextField(FieldKey.MIGRATION_ROW_ID);
        printTemplate.addTextField(FieldKey.MIGRATION_FIELD_NAME);
        printTemplate.addTextField(FieldKey.MIGRATION_MULT_ROW_ID);
        printTemplate.addTextField(FieldKey.MIGRATION_MULT_FIELD_NAME);
        printTemplate.addTextField(FieldKey.DETAILED_ERROR_TEXT);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the default drug classification report table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVADrugClassificationReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.CODE);
        printTemplate.addTextField(FieldKey.CLASSIFICATION);
        printTemplate.addTextField(FieldKey.DESCRIPTION);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the default  report table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultProductWithExclusionsDrgDrgReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.EXCLUSIONS_REPORT);
        printTemplate.addTextField(FieldKey.VA_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.EXCLUDED);

//        printTemplate.addTextField(FieldKey.EXCLUDE_DRG_DRG_INTERACTION_CHECK);        
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the default report table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultActiveProductNoActiveNDCS() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        
        Map<String, Object> itemLinkParameters = new HashMap<String, Object>();
        itemLinkParameters.put(ITEM_ID, FieldKey.ID);
        itemLinkParameters.put(ITEM_TYPE, PRODUCT);

        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, EDIT, itemLinkParameters);
        
        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for the default report table.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultActiveProductProposedInactivation() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.VA_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.PROPOSED_INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * defaultVuidNewProductReport 
     * @return PrintTemplateVo for the defaultVuidNewProductReport
     */
    public static PrintTemplateVo defaultVuidNewProductReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.VA_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.VUID);

        return printTemplate;
    }

    /**
     * VUID Inactivated/Reactivated Product Report
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVuidInactiveProductReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.VA_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.VUID);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        // return the printTemplate for the defaultVuidInactiveProductReport
        return printTemplate;
    }

    /**
     * VUID New Ingredient Report
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVuidNewIngredientReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.INGREDIENT_NAME);
        printTemplate.addTextField(FieldKey.VUID);

        return printTemplate;
    }

    /**
     * VUID Inactivated Ingredient Report
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVuidInactiveIngredientReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.INGREDIENT_NAME);
        printTemplate.addTextField(FieldKey.VUID);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * VUID Drug Class Report
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVuidDrugClassReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.VA_DRUG_CLASS);
        printTemplate.addTextField(FieldKey.VUID);

//      printTemplate.addTextField(FieldKey.NAME);
        return printTemplate;
    }

    /**
     * VUID Generic Name Reprot
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVuidGenericNameReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.VUID);

        return printTemplate;
    }

    /**
     * defaultVuidModifiedGenericNameReport Reprot
     * @return PrintTemplateVo for the defaultVuidModifiedGenericNameReport.
     */
    public static PrintTemplateVo defaultVuidModifiedGenericNameReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.VUID);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * VUID Modified Product Report
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo defaultVuidModifiedProductReport() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.DRUG_CLASS_REPORT);
        printTemplate.addTextField(FieldKey.IEN);
        printTemplate.addTextField(FieldKey.VA_PRODUCT_NAME);
        printTemplate.addTextField(FieldKey.VUID);
        printTemplate.addTextField(FieldKey.INACTIVATION_DATE);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for a move NDC Product search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo moveNDCSelectProductSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.PRODUCT_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.VA_PRODUCT_NAME, MOVE_CHILDREN, linkParameters);

        printTemplate.addTextField(FieldKey.GENERIC_NAME);
        printTemplate.addTextField(FieldKey.PRODUCT_STRENGTH);
        printTemplate.addTextField(FieldKey.PRODUCT_UNIT);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.CMOP_ID);
        printTemplate.addTextField(FieldKey.OI_NAME);
        printTemplate.addTextField(FieldKey.PRIMARY_DRUG_CLASS2);
        printTemplate.addTextField(FieldKey.CMOP_DISPENSE);

//        printTemplate.addTextField(FieldKey.LOCAL_USE);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.NATIONAL_FORMULARY_INDICATOR);
        printTemplate.addTextField(FieldKey.CATEGORIES);
        printTemplate.addTextField(FieldKey.NDC_COUNT);

        return printTemplate;
    }

    /**
     * Create a new instance of the default fields displayed for an Orderable Item search.
     * 
     * @return PrintTemplateVo
     */
    public static PrintTemplateVo moveProductSelectOrderableItemSearch() {
        PrintTemplateVo printTemplate = new PrintTemplateVo();
        printTemplate.setTemplateLocation(TemplateLocation.ORDERABLE_ITEM_SEARCH);

        Map<String, Object> linkParameters = new HashMap<String, Object>();
        linkParameters.put(ITEM_ID, FieldKey.ID);
        linkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        printTemplate.addLinkField(FieldKey.OI_NAME, MOVE_CHILDREN, linkParameters);
        commonOiSearch(printTemplate);
        
        return printTemplate;
    }
    
    /**
     * commonOiSearch
     *
     * @param printTemplate PrintTemplateVo
     */
    public static void commonOiSearch(PrintTemplateVo printTemplate) {
        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_TYPE);
        printTemplate.addTextField(FieldKey.DOSAGE_FORM);
        printTemplate.addTextField(FieldKey.ORDERABLE_ITEM_SYNONYM);
        printTemplate.addTextField(FieldKey.REQUEST_ITEM_STATUS);
        printTemplate.addTextField(FieldKey.ITEM_STATUS);
        printTemplate.addTextField(FieldKey.CATEGORIES);

        Map<String, Object> childrenLinkParameters = new HashMap<String, Object>();
        childrenLinkParameters.put(ITEM_ID, FieldKey.ID);
        childrenLinkParameters.put(ITEM_TYPE, FieldKey.ENTITY_TYPE);
        childrenLinkParameters.put(TAB, CHILDREN_TAB);

        printTemplate.addLinkField(FieldKey.PRODUCT_COUNT, EDIT, childrenLinkParameters, false);
    }
}
