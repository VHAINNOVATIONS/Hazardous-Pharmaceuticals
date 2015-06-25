/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;


/**
 * ControllerConstants holds constant definitions for controllers.
 *
 */
public final class ControllerConstants {

    /** MANAGE_REQUEST_GO */
    public static final String MANAGE_REQUEST_GO = "/manageRequest.go";

    /** MANAGE_DOMAIN_REQUEST_GO */
    public static final String MANAGE_DOMAIN_REQUEST_GO = "/manageDomainRequest.go";

    /** constant value */
    public static final String PRODUCT_SEARCH_KEY = "getProductSearch";

    /** constant value */
    public static final String ACTIVATE = "activate";

    /** constant value */
    public static final PrintTemplateVo ACTIVE_PRDUCTS_NO_ACTIVE_NDCS_REPORT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultActiveProductNoActiveNDCS();

    /** constant value */
    public static final PrintTemplateVo ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE =
        DefaultPrintTemplateFactory.defaultActiveProductProposedInactivation();

    /** constant value */
    public static final String ADMINISTRATION_DATA_TAB = "administration_data_tab";

    /** constant value */
    public static final String ALPHA_TAB = "alpha_tab";

    /** constant value */
    public static final String APPLICATION_DATA_TAB = "application_data_tab";

    /** constant value */
    public static final String BUILD_INFO = "buildInfo";

    /** compound category */
    public static final int CATEGORY_COMPOUND_INT = 1;

    /** investigational category */
    public static final int CATEGORY_INVESTIGATIONAL_INT = 3;

    /** medication category */
    public static final int CATEGORY_MEDICATION_INT = 5;

    /** supply category */
    public static final int CATEGORY_SUPPLY_INT = 7;

    /** constant value */
    public static final String CHILDREN_TAB = "children_tab";

    /** constant value */
    public static final String CMOP_MARKUNMARK_TAB = "cmop_MarkUnmark_tab";

    /** constant value */
    public static final String COMMON_TAB = "common_tab";

    /** constant value */
    public static final PrintTemplateVo CONSOLE_STATUS_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultStatusConfiguration();

    /** constant value */
    public static final String CONTROLLED_SUBSTANCE_TAB = "controlled_substance_tab";

    /** constant value */
    public static final String DETAILS_TAB = "details_tab";

    /** constant value */
    public static final String DISPENSE_DATA_TAB = "dispense_data_tab";

    /** constant value */
    public static final String DOMAIN_KEY = "domain";

    /** constant value */
    public static final PrintTemplateVo DOMAIN_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultManagedDataSearch();

    /** constant value */
    public static final String DRUG_ACCOUNTABILITY_TAB = "drug_accountability_tab";

    /** constant value */
    public static final String DRUG_DATA_TAB = "drug_data_tab";

    /** constant value */
    public static final String ENTEREDIT_DOSAGES_TAB = "enteredit_Dosages_tab";

    /** constant value */
    public static final String ENTITY_TYPE = "entityType";

    /** constant value */
    public static final String DOMAIN_ENTITY_TYPE = "domainEntityType";

    /** constant value */
    public static final String EVENT_ID = "eventId";

    /** constant value */
    public static final String FIELD_KEY = "fieldKey";

    /** constant value */
    public static final String HISTORY_TAB = "history_tab";

    /** constant value */
    public static final PrintTemplateVo HOME_PARTIAL_SAVE_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .homePagePartialSaves();

    /** constant value */
    public static final String INPATIENT_UNITDOSE_TAB = "inpatient_unitdose_tab";

    /** constant value */
    public static final PrintTemplateVo ITEM_AUDIT_HISTORY_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultItemAuditHistoryConfiguration();

    /** constant value */
    public static final String ITEM_ID = "itemId";

    /** constant value */
    public static final String REQUEST_ID = "requestId";

    /** constant value */
    public static final String ITEM_KEY = "item";

    /** constant value */
    public static final String MAIN_REQUEST = "mainRequest";

    /** constant value */
    public static final String WARNINGS = "warnings";

    /** constant value */
    public static final String LABS_TAB = "labs_tab";

    /** constant value */
    public static final String LOCAL_STATUS = "localStatus";

    /** constant value */
    public static final String NATIONAL_STATUS = "nationalStatus";

    /** constant value */
    public static final String NATIONAL_VIEW_TAB = "national_view_tab";

    /** constant value */
    public static final PrintTemplateVo NDC_LIST_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultNdcList();

    /** constant value */
    public static final String NDC_LIST_TABLE = "ndcList";

    /** constant value */
    public static final PrintTemplateVo NDC_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultNdcSearch();

    /** constant value */
    public static final String NDCMAIN_TAB = "ndcmain_tab";

    /** constant value */
    public static final String NDCPRICE_TAB = "ndcprice_tab";

    /** constant value */
    public static final String NONVA_MED_TAB = "nonva_med_tab";

    /** constant value */
    public static final PrintTemplateVo OI_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultOrderableItemSearch();

    /** constant value */
    public static final String OPTIONS_TAB = "options_tab";

    /** constant value */
    public static final String OUTPATIENT_TAB = "outpatient_tab";

    /** constant value */
    public static final String PAGE_MANAGE_SEARCH_TEMPLATES = "managesearchtemplates.go";

    /** PAGE_EDIT */
    public static final String PAGE_EDIT = "/edit.go";

    /** REDIRECT */
    public static final String REDIRECT = "redirect:";

    /** REDIRECT_SLASH */
    public static final String REDIRECT_SLASH = "redirect:/";

    /** page constant */
    public static final String PAGE_RETRIEVE_TEMPLATE = "/retrieve.go";

    /** page constant */
    public static final String PAGE_SEARCH_ADV = "/advancedcriteria.go";

    /** page constant */
    public static final String PAGE_SEARCH_UPDATE_ADV = "/search.update.go";

    /** page constant */
    public static final String PAGE_SEARCH_DATA_ELEMENTS = "/searchDataElements.go";

    /** page constant */
    public static final String PAGE_SEARCH_SETTINGS = "/searchSettings.go";
    
    /** PARENT_ITEM */
    public static final String PARENT_ITEM = "parentItem";
    
    /** page constant */
    public static final String PAGE_UPDATE_TEMPLATE = "/search.template.go";
    
    /** constant value */
    public static final PrintTemplateVo PARTIAL_SAVE_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultPartialSave();

    /** constant value */
    public static final String PHARMACY_SYSTEM_OHTER_LANGUAGE = "pharmacy_system_other_language";

    /** constant value */
    public static final String PHARMACY_SYSTEM_PARAMETERS = "pharmcy_system_parameters";

    /** constant value */
    public static final String FORM_ELEMENT_NAME = "formElementName";

    /** constant value */
    public static final String PRINT_TEMPLATE = "printTemplate";

    /** constant value */
    public static final String PREVIOUS_ITEM = "previousItem";

    /** constant value */
    public static final String PROBLEM_REPORT_TAB = "reports_tab";

    /** constant value */
    public static final PrintTemplateVo PRODUCT_LIST_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultProductList();

    /** constant value */
    public static final String PRODUCT_LIST_TABLE = "productList";

    /** constant value */
    public static final PrintTemplateVo PRODUCT_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultProductSearch();

    /** constant value */
    public static final PrintTemplateVo PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultProductWithExclusionsDrgDrgReport();

    /** constant value */
    public static final String REPORT_TABLE = "reportTableId";

    /** constant value */
    public static final String REPORT_TYPE = "reportType";

    /** constant value */
    public static final String REPORTS_TAB = "reports_tab";

    /** request parameter key for entity type */
    public static final String REQ_PARAM_KEY_ENTITYTYPE = "entityType";

    /** request param default value for entity type key */
    public static final String REQ_PARAM_DEF_VALUE_ENTITYTYPE = "PRODUCT";

    /** constant value */
    public static final PrintTemplateVo REQUESTS_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultRequestSearch();

    /** constant value */
    public static final String REQUESTS_TAB = "requests_tab";

    /** constant value */
    public static final String SAFETY_TAB = "safety_tab";

    /** constant value */
    public static final String SEARCH_CRITERIA_KEY = "searchCriteria";

    /** constant value */
    public static final String SEARCH_TEMPLATE_KEY = "searchTemplate";

    /** SEARCH_TEMPLATES_KEY */
    public static final String SEARCH_TEMPLATES_KEY = "searchTemplates";

    /** page constant */
    public static final String SEARCH_PREFERENCES = "searchPreferences";

    /** constant value */
    public static final String SEARCH_RESULTS_TABLE = "searchResultsTable";

    /** constant value */
    public static final String SEND_DRUG_FILE = "send_drug_file";

    /** chemotherapy sub-category */
    public static final int SUBCATEGORY_CHEMOTHERAPY_INT = 0;

    /** herbal sub-category */
    public static final int SUBCATEGORY_HERBAL_INT = 2;

    /** OTC sub-category */
    public static final int SUBCATEGORY_OTC_INT = 8;

    /** veterinary sub-category */
    public static final int SUBCATEGORY_VETERINARY_INT = 8;

    /** constant value */
    public static final String SYNONYMS_TAB = "synonyms_tab";

    /** constant value */
    public static final String TAB_ELEMENT_ID = "tab";

    /** constant value */
    public static final String TAB_KEY = "tab";

    /** POST_TAB_KEY */
    public static final String POST_TAB_KEY = "post_tab";

    /** constant value */
    public static final String TABLE_ID_PARAMETER = "tableId";

    /** constant value */
    public static final String TEMPLATE_TYPE = "templateType";

    /** constant value */
    public static final PrintTemplateVo VA_DRUG_CLASSIFICATION_REPORT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVADrugClassificationReport();

    /** constant value */
    public static final PrintTemplateVo VUID_DRUG_CLASS_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidDrugClassReport();

    /** constant value */
    public static final PrintTemplateVo VUID_GENERIC_NAME_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidGenericNameReport();

    /** constant value */
    public static final PrintTemplateVo VUID_MODIFIED_GENERIC_NAME_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidModifiedGenericNameReport();

    /** constant value */
    public static final PrintTemplateVo VUID_INACTIVE_INGREDIENT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidInactiveIngredientReport();

    /** constant value */
    public static final PrintTemplateVo VUID_INACTIVE_PRODUCT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidInactiveProductReport();

    /** constant value */
    public static final PrintTemplateVo VUID_MODIFIED_PRODUCT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidModifiedProductReport();

    /** constant value */
    public static final PrintTemplateVo VUID_NEW_INGREDIENT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidNewIngredientReport();

    /** constant value */
    public static final PrintTemplateVo VUID_NEW_PRODUCT_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultVuidNewProductReport();

    /** constant value */
    public static final String WARD_STOCK_TAB = "ward_stock_tab";

    //public static final String WARNING_LABELS_TAB = "warning_labels_tab";

    /** SLASH_REQUEST_SLASH */
    public static final String SLASH_REQUEST_SLASH = "/request/";

    /** IS_REDIRECTED_FROM_MERGE_SUMMARY */
    public static final String IS_REDIRECTED_FROM_MERGE_SUMMARY = "isRedirectedFromMergeSummary";

    /** SEARCH_PARENT_GO */
    public static final String SEARCH_PARENT_GO = "searchParent.go";

    /** ASSOCIATE_PRODUCT_GO */
    public static final String ASSOCIATE_PRODUCT_GO = "associateProduct.go";

    /** PARENT */
    public static final String PARENT = "parent";

    /** ADDING_NEW_PARENT */
    public static final String ADDING_NEW_PARENT = "addingNewParent";

    /** USER_CONTEXT_KEY */
    public static final String USER_CONTEXT_KEY = "PPSN" + UserContext.class.getSimpleName();

    /** COPYING_NDCS_FROM_PRODUCT */
    public static final String COPYING_NDCS_FROM_PRODUCT = "copyingNdcsFromProduct";
    
    /** PLUS_BUTTON_INDEX */
    public static final String PLUS_BUTTON_INDEX = "plusButtonIndex";


    /** hashmap of tabs */
    public static final Map<EntityType, String> DEFAULT_TAB;

    static {
        Map<EntityType, String> dTab = new HashMap<EntityType, String>();
        dTab.put(EntityType.PRODUCT, NATIONAL_VIEW_TAB);
        dTab.put(EntityType.ORDERABLE_ITEM, DETAILS_TAB);
        dTab.put(EntityType.NDC, NDCMAIN_TAB);
        dTab.put(EntityType.DRUG_UNIT, ALPHA_TAB);
        dTab.put(EntityType.DISPENSE_UNIT, ALPHA_TAB);
        dTab.put(EntityType.DOSAGE_FORM, ALPHA_TAB);
        dTab.put(EntityType.DOSE_UNIT, ALPHA_TAB);
        dTab.put(EntityType.DRUG_CLASS, ALPHA_TAB);
        dTab.put(EntityType.DRUG_TEXT, ALPHA_TAB);
        dTab.put(EntityType.GENERIC_NAME, ALPHA_TAB);
        dTab.put(EntityType.INGREDIENT, ALPHA_TAB);
        dTab.put(EntityType.MANUFACTURER, ALPHA_TAB);
        dTab.put(EntityType.ORDER_UNIT, ALPHA_TAB);
        dTab.put(EntityType.PACKAGE_TYPE, ALPHA_TAB);
        dTab.put(EntityType.RX_CONSULT, ALPHA_TAB);
        dTab.put(EntityType.SPECIAL_HANDLING, ALPHA_TAB);
        dTab.put(EntityType.STANDARD_MED_ROUTE, ALPHA_TAB);
        DEFAULT_TAB = Collections.unmodifiableMap(dTab);
    }

    /**
     * private constructor prohibiting its use as an instantiated object
     *
     */
    private ControllerConstants() {

    }

    /**
     * Gets the event string for entity type (e.g. Domain)
     *
     * @param entityType of the item
     * @return the event string ("/manageDomainRequest.go" or "/manageRequest.go")
     */
    public static String getRequestReturnEvent(EntityType entityType) {
        String event;

        if (entityType.isDomainType()) {
            event = MANAGE_DOMAIN_REQUEST_GO;
        } else {
            event = MANAGE_REQUEST_GO;
        }

        return event;
    }
}
