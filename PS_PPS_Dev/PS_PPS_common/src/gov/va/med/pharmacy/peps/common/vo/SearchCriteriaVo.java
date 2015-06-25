/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;


/**
 * Provide search criteria for search, operates as a search template when saved.
 */
public class SearchCriteriaVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * this variable determines if the search is an "advanced and search" or an "advanced or search"
     */
    private boolean advancedAndSearch;

    /**
     * Search property only used for simple search that allows a user to limit results to those with a certain dosage form
     * value.
     */
    private String dosageForm;

    /**
     * Search property for the item type to be searched: product, ndc, or orderable item.
     */
    private EntityType entityType;

    private final Environment environment;

    private FieldKey sortedFieldKey;

    /**
     * Used as the primary key for the searchCriteria
     */
    private String id;

    /**
     * Search property for item status filter
     */
    private List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();

    /**
     * Search property that limits results to those which are only for local use.
     */
    private LocalUseSearchType localUse;

    /**
     * Search property for print template associated with the current search.
     */
    private PrintTemplateVo printTemplate;

    /**
     * Search property for request item status filter
     */
    private List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();

    /**
     * Search property that indicates if the search is simple, advanced, or domain.
     */
    private SearchDomain searchDomain;
    private int pageSize;

    /**
     * Search property that holds the possible fields for this type of search.
     */
    private List<SearchFieldVo> searchFields = new ArrayList<SearchFieldVo>();

    /**
     * Indicates that the item type has changed, and the criteria may need to be changed accordingly.
     */
    private boolean entityTypeChanged;

    /**
     * Search property for the terms in the current search
     */
    private List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();

    /**
     * Search property only used for simple search that allows a user to limit results to those with a certain active
     * ingredient strength value.
     */
    private String strength;

    private List<Category> categories = new ArrayList<Category>(0);

    private List<SubCategory> subCategories = new ArrayList<SubCategory>(0);

    private int startRow;

    private SortOrder sortOrder;

    private LinkedHashMap<String, String> searchFieldsMap; //NOPMD

    /**
     * Most detailed constructor, indicates the type of search.
     * 
     * @param searchDomain
     *            search type
     * @param entityType
     *            the type of item searched for
     * @param environment
     *            Environment
     */
    public SearchCriteriaVo(SearchDomain searchDomain, EntityType entityType, Environment environment) {

        this.searchDomain = searchDomain;
        this.environment = environment;
        this.entityType = entityType;

        // add conditional for OI Search because criteria.getEntityType
        // returned PRODUCT for both Product and OI (select parent)
//        if (entityType == null && getSearchDomain().isOrderableItemSearch()) {
//            this.entityType = EntityType.ORDERABLE_ITEM;
//        } else {
//            this.entityType = EntityType.PRODUCT;
//        }

        // unchecked the medication checkbox under Category
        // if (isSimple()) {
        //     getProductTypes().add("MEDICATION");
        // }

        getItemStatus().add(ItemStatus.ACTIVE);
        getRequestStatus().add(RequestItemStatus.APPROVED);
        this.localUse = LocalUseSearchType.ALL_ITEMS;
        initSearchTerms();
        setSearchFields(retrieveSearchFields());
    }

    /**
     * 
     * Description here
     * 
     * @param searchDomain
     *            search type
     * @param environment
     *            Environment
     */
    public SearchCriteriaVo(SearchDomain searchDomain, Environment environment) {

        this(searchDomain, null, environment);
    }

    /**
     * // Fields used for paging search results private FieldKey sortedFieldKey; private SortOrder sortOrder; private int
     * startRow; private int pageSize;
     * 
     * Returns a list of advanced search fields based on the entity type.
     * 
     * @param entityType item type
     * @return list
     */
    public static List<SearchFieldVo> getAdvancedSearchFields(EntityType entityType) {

        List<SearchFieldVo> advSearchFields = new ArrayList<SearchFieldVo>();

        switch (entityType) {
            case PRODUCT:

                //begin PRODUCT
                advSearchFields.add(new SearchFieldVo(FieldKey.APPROVED_FOR_SPLITTING, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.AR_WS_AMIS_CATEGORY, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.AR_WS_CONVERSION_NUMBER, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.POSSIBLE_DOSAGES_AUTO_CREATE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPLAYABLE_INGREDIENT_NAME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.CMOP_ID, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.CS_FED_SCHEDULE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DAW_CODE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DEA_SCHEDULE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DEFAULT_MAIL_SERVICE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_LIMIT_FOR_ORDER, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_OVERRIDE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_OVERRIDE_REASON, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.OVERRIDE_REASON_ENTERER, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_QUANTITY_OVERRIDE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DO_NOT_HANDLE_IF_PREGNANT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DO_NOT_MAIL, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.EXCLUDE_DRG_DRG_INTERACTION_CHECK, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.FDA_MED_GUIDE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.FOLLOW_UP_TIME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.FORMULARY_ALTERNATIVE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.FSN, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.GCN_SEQUENCE_NUMBER, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.HAZARDOUS_TO_DISPOSE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.HAZARDOUS_TO_HANDLE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.HAZARDOUS_TO_PATIENT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PRIMARY_EPA_CODE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.WASTE_SORT_CODE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DOT_SHIPPING_NAME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.HIGH_RISK_FOLLOWUP, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.HIGH_RISK, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPLAYABLE_INGREDIENT_STRENGTH, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPLAYABLE_INGREDIENT_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.INACTIVATION_DATE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME, 
                    EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME, 
                    EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.LAB_MONITOR_MARK, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.LONG_TERM_OUT_OF_STOCK, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.LOW_SAFETY_MARGIN, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.MAX_DISPENSE_LIMIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.MAX_DOSE_PER_DAY, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NCPDP_DISPENSE_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NDCDP_QUANTITY_MULTIPLIER, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PATIENT_SPECIFIC_LABEL, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.MONITOR_MAX_DAYS, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.MONITOR_ROUTINE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NATIONAL_FORMULARY_INDICATOR, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NATIONAL_FORMULARY_NAME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NDC_CODE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.REQUEST_ITEM_STATUS, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NDF_PRODUCT_IEN, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NON_RENEWABLE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NUMBER_OF_DOSES, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.ORDER_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.OVERRIDE_DF_DOSE_CHK_EXCLUSN, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.CREATE_POSSIBLE_DOSAGE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PRODUCT_STRENGTH, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PRODUCT_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.QUANTITY_DISPENSE_MESSAGE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.REJECTION_REASON_TEXT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.SYNONYM_INTENDED_USE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPLAYABLE_SYNONYM_NAME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.SYNONYM_ORDER_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.SYNONYM_VENDOR, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.NATIONAL_DRUG_TEXTS, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.RECALL_LEVEL, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.REFRIGERATION, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.REQUEST_REJECTION_REASON, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.SERVICE_CODE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.SINGLE_MULTISOURCE_PRODUCT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.SPECIAL_HANDLING, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PRODUCT_PACKAGE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PROPOSED_INACTIVATION_DATE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.PROTECT_FROM_LIGHT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.TALL_MAN_LETTERING, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.TOTAL_DISPENSE_QUANTITY, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.CMOP_DISPENSE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.UNIT_DOSE_SCHEDULE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.UNIT_DOSE_SCHEDULE_TYPE, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.DISPENSE_UNIT, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.VA_DRUG_CLASS, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.GENERIC_NAME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.VA_PRINT_NAME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.VA_PRODUCT_NAME, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.VUID, EntityType.PRODUCT));
                advSearchFields.add(new SearchFieldVo(FieldKey.WITNESS_TO_ADMINISTRATION, EntityType.PRODUCT));

                Collections.sort(advSearchFields, new SearchFieldVo.AlphabeticalComparator());

                advSearchFields.add(0, new SearchFieldVo(// All (Designated Fields)
                    FieldKey.SEARCH_ALL_DESIGNATED_FIELDS, EntityType.PRODUCT));

                advSearchFields.add(0, new SearchFieldVo(// None
                    FieldKey.SEARCH_NO_FIELDS, EntityType.PRODUCT));

                break;

            case NDC:
                advSearchFields = caseNdc(advSearchFields);
                break;

            case ORDERABLE_ITEM:
                advSearchFields = caseOI(advSearchFields);
                break;

            default:
                advSearchFields.add(new SearchFieldVo(// None
                    FieldKey.SEARCH_NO_FIELDS, EntityType.PRODUCT));
                break;

        }

        return advSearchFields;
    }

    
    /**
     * caseNdc
     * @param tSearchFields searchFields
     * @return searchFields
     */
    private static List<SearchFieldVo> caseNdc(List<SearchFieldVo> tSearchFields) {
      
        //begin NDC
        List<SearchFieldVo> advSearchFields = tSearchFields;
        advSearchFields.add(new SearchFieldVo(// Color
            FieldKey.COLOR, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Imprint
            FieldKey.IMPRINT, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Imprint 2
            FieldKey.IMPRINT2, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Manufacturer
            FieldKey.MANUFACTURER, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// NDC
            FieldKey.NDC, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// NDC Dispense Units Per Order Unit
            FieldKey.NDC_DISP_UNITS_PER_ORD_UNIT, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// NDC Item Inactivation Date
            FieldKey.INACTIVATION_DATE, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Order Unit
            FieldKey.ORDER_UNIT, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// OTC/Rx Indicator
            FieldKey.OTC_RX_INDICATOR, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Package Size
            FieldKey.PACKAGE_SIZE, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Package Type
            FieldKey.PACKAGE_TYPE, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Previous NDC
            FieldKey.PREVIOUS_NDC, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Previous UPC UPN
            FieldKey.PREVIOUS_UPC_UPN, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Product Number
            FieldKey.PRODUCT_NUMBER, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Proposed Inactivation Date
            FieldKey.PROPOSED_INACTIVATION_DATE, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Protect from Light
            FieldKey.PROTECT_FROM_LIGHT, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Refrigeration
            FieldKey.REFRIGERATION, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Scored
            FieldKey.SCORED, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Shape
            FieldKey.SHAPE, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Single Multi Source
            FieldKey.SINGLE_MULTISOURCE_NDC, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Source
            FieldKey.SOURCE, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(//Ten Digit NDC
            FieldKey.TEN_DIGIT_NDC, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Ten Digit NDC Format Indication
            FieldKey.TEN_DIGIT_FORMAT_INDICATION, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// Trade Name
            FieldKey.TRADE_NAME, EntityType.NDC));
        advSearchFields.add(new SearchFieldVo(// UPC
            FieldKey.UPC_UPN, EntityType.NDC));

        Collections.sort(advSearchFields, new SearchFieldVo.AlphabeticalComparator());

        advSearchFields.add(0, new SearchFieldVo(// All (Designated Fields)
            FieldKey.SEARCH_ALL_DESIGNATED_FIELDS, EntityType.NDC));
        advSearchFields.add(0, new SearchFieldVo(// None
            FieldKey.SEARCH_NO_FIELDS, EntityType.NDC));
        
        return advSearchFields;
    }
    
    /**
     * caseOI
     * @param tSearchFields searchFields
     * @return searchFields
     */
    private static List<SearchFieldVo> caseOI(List<SearchFieldVo> tSearchFields) {
        
        List<SearchFieldVo> advSearchFields = tSearchFields;
        advSearchFields.add(new SearchFieldVo(// Day(ND) or Dose(NL) Limit
            FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Dosage Form
            FieldKey.DOSAGE_FORM, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// High Alert
            FieldKey.HIGH_ALERT, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Lifetime Cumulative Dosage
            FieldKey.LIFETIME_CUMULATIVE_DOSAGE, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// National Formulary Indicator
            FieldKey.NATIONAL_FORMULARY_INDICATOR, EntityType.ORDERABLE_ITEM));

        //advSearchFields.add(new SearchFieldVo(// National OI Drug Text Entry
        //FieldKey.NATIONAL_DRUG_TEXT, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// National OI Drug Text Entry
            FieldKey.NATIONAL_DRUG_TEXT, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Non VA Med
            FieldKey.NON_VA_MED, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// OI IV Flag
            FieldKey.OI_IV_FLAG, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Orderable Item Inactivation Date
            FieldKey.INACTIVATION_DATE, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// OI_ROUTE
            FieldKey.STANDARD_MED_ROUTE, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Orderable Item Synonym
            FieldKey.ORDERABLE_ITEM_SYNONYM, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Other Language Instructions
            FieldKey.OTHER_LANGUAGE_INSTRUCTIONS, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Special Instructions
            FieldKey.SPECIAL_INSTRUCTIONS, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Patient Instructions
            FieldKey.PATIENT_INSTRUCTIONS, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// PPS Orderable Item Name
            FieldKey.OI_NAME, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Proposed Inactivation Date
            FieldKey.PROPOSED_INACTIVATION_DATE, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Reject Reason Text
            FieldKey.REJECTION_REASON_TEXT, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Request Rejection Reason
            FieldKey.REQUEST_REJECTION_REASON, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(new SearchFieldVo(// Vista OI Name
            FieldKey.VISTA_OI_NAME, EntityType.ORDERABLE_ITEM));

        Collections.sort(advSearchFields, new SearchFieldVo.AlphabeticalComparator());

        advSearchFields.add(0, new SearchFieldVo(// All (Designated Fields)
            FieldKey.SEARCH_ALL_DESIGNATED_FIELDS, EntityType.ORDERABLE_ITEM));
        advSearchFields.add(0, new SearchFieldVo(// None
            FieldKey.SEARCH_NO_FIELDS, EntityType.ORDERABLE_ITEM));
        
        return advSearchFields;
    }


    
    
    /**
     * Return possible OI search fields
     * 
     * @return List of OI search fields
     */
    public static List<SearchFieldVo> getOISearchFields() {

        List<SearchFieldVo> oISearchFields = new ArrayList<SearchFieldVo>();

        // Default parent search field is OI Name
        oISearchFields.add(new SearchFieldVo(FieldKey.OI_NAME, EntityType.ORDERABLE_ITEM));

        return oISearchFields;
    }

    /**
     * Return possible product search fields for the all search
     * 
     * @return List of product search fields
     */
    public static List<SearchFieldVo> getProductSearchFields() {

        List<SearchFieldVo> productSearchFields = new ArrayList<SearchFieldVo>();

        productSearchFields.add(new SearchFieldVo(FieldKey.VA_PRODUCT_NAME, EntityType.PRODUCT));

        // comment out unwanted search fields - default parent search field is VA Product Name
        productSearchFields.add(new SearchFieldVo(FieldKey.VA_PRINT_NAME, EntityType.PRODUCT));
        productSearchFields.add(new SearchFieldVo(FieldKey.GENERIC_NAME, EntityType.PRODUCT));
        productSearchFields.add(new SearchFieldVo(FieldKey.CMOP_ID, EntityType.PRODUCT));
        productSearchFields.add(new SearchFieldVo(FieldKey.PRIMARY_DRUG_CLASS, EntityType.PRODUCT));
        productSearchFields.add(new SearchFieldVo(FieldKey.ACTIVE_INGREDIENT, EntityType.PRODUCT));
        productSearchFields.add(new SearchFieldVo(FieldKey.SYNONYM_NAME, EntityType.PRODUCT));
        productSearchFields.add(new SearchFieldVo(FieldKey.SEARCH_ALL_FIELDS, EntityType.PRODUCT));

        return productSearchFields;
    }

    /**
     * // Fields used for paging search results private FieldKey sortedFieldKey; private SortOrder sortOrder; private int
     * startRow; private int pageSize;
     * 
     * Returns a list of simple search fields based on the entity type.
     * 
     * @param entityType item type
     * @return list
     */
    public static List<SearchFieldVo> getSimpleSearchFields(EntityType entityType) {

        List<SearchFieldVo> simpleSearchFields = new ArrayList<SearchFieldVo>();

        switch (entityType) {
            case PRODUCT:
                simpleSearchFields.add(new SearchFieldVo(FieldKey.VA_PRODUCT_NAME, EntityType.PRODUCT));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.VA_PRINT_NAME, EntityType.PRODUCT));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.GENERIC_NAME, EntityType.PRODUCT));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.CMOP_ID, EntityType.PRODUCT));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.PRIMARY_DRUG_CLASS2, EntityType.PRODUCT));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.ACTIVE_INGREDIENT, EntityType.PRODUCT));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.SYNONYM_NAME, EntityType.PRODUCT));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.SEARCH_ALL_FIELDS, EntityType.PRODUCT));
                break;

            case ORDERABLE_ITEM:
                simpleSearchFields.add(new SearchFieldVo(FieldKey.OI_NAME, EntityType.ORDERABLE_ITEM));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.ORDERABLE_ITEM_SYNONYM, EntityType.ORDERABLE_ITEM));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.SEARCH_ALL_FIELDS, EntityType.ORDERABLE_ITEM));
                break;

            case NDC:
                simpleSearchFields.add(new SearchFieldVo(FieldKey.SEARCH_ALL_FIELDS, EntityType.NDC));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.NDC, EntityType.NDC));

                //Added for UPC_UPN simple search
                simpleSearchFields.add(new SearchFieldVo(FieldKey.UPC_UPN, EntityType.NDC));
                simpleSearchFields.add(new SearchFieldVo(FieldKey.TRADE_NAME, EntityType.NDC));
                break;

            default:
                break;

        }

        return simpleSearchFields;
    }

    /**
     * Return possible product search fields for the all search
     * 
     * @return List of product search fields
     */
    public static List<SearchFieldVo> getSetupAtcSearchFields() {

        List<SearchFieldVo> productSearchFields = new ArrayList<SearchFieldVo>();

        productSearchFields.add(new SearchFieldVo(SearchTermVo.SETUP_ATC_FIELD_KEY, EntityType.PRODUCT));

        return productSearchFields;
    }

    /**
     * When the {@link EntityType} of the search changes, we need to clear out some of the search criteria.
     */
    public void resetSearchTerms() {

        SearchTermVo term = null;

        if (isSimple() && !getSearchTerms().isEmpty()) {
            term = getSearchTerms().get(0);
        }

        getSearchTerms().clear();
        initSearchTerms();
        getSearchTerms().get(0).getSearchField().setEntityType(getEntityType());

        if (term != null) {
            getSearchTerms().get(0).setSearchType(term.getSearchType());
            getSearchTerms().get(0).setValue(term.getValue());
        }

        setDosageForm(null);
        setStrength(null);
        setStartRow(0);
        setSortOrder(SortOrder.ASCENDING);
        setSortedFieldKey(null);
    }

    /**
     * Add logic to clear out an advanced search 'value' field if its field selection has changed. This logic was added to
     * deal with defect #2219 'Unexpected advanced search field behavior', whereby a previous field selection's value was
     * being stamped on to a new field's value...it should instead be blank.
     */
    public void fixValuesAfterFieldChange() {

        if (searchTerms != null) {
            for (SearchTermVo term : searchTerms) {
                term.fixValueAfterFieldChange();
            }
        }
    }

    /**
     * Return possible advanced search fields
     * 
     * @return List of advanced search fields
     */
    public List<SearchFieldVo> getAdvancedSearchFields() {

        if (getEntityType() == null) {

            return new ArrayList<SearchFieldVo>();
        }

        return getAdvancedSearchFields(getEntityType());
    }

    /**
     * Return possible simple search fields
     * 
     * @return List of simple search fields
     */
    public List<SearchFieldVo> getSimpleSearchFields() {

        if (getEntityType() == null) {

            return new ArrayList<SearchFieldVo>();
        }

        return getSimpleSearchFields(getEntityType());
    }

    /**
     * Return possible domain search fields
     * 
     * @return List of domain search fields
     */
    public List<SearchFieldVo> getDomainSearchFields() {

        List<SearchFieldVo> domainSearchFields = new ArrayList<SearchFieldVo>();

        if (environment.isNational()) {
            domainSearchFields.add(new SearchFieldVo(FieldKey.CLASSIFICATION, EntityType.DRUG_CLASS));
            domainSearchFields.add(new SearchFieldVo(FieldKey.CODE, EntityType.DRUG_CLASS));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.DISPENSE_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.DOSAGE_FORM_NAME, EntityType.DOSAGE_FORM));
            domainSearchFields.add(new SearchFieldVo(FieldKey.DOSE_UNIT_NAME, EntityType.DOSE_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.DRUG_TEXT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.DRUG_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.GENERIC_NAME));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.INGREDIENT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.ORDER_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.MANUFACTURER));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.PACKAGE_TYPE));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.SPECIAL_HANDLING));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.STANDARD_MED_ROUTE));
        } else {
            domainSearchFields.add(new SearchFieldVo(FieldKey.CLASSIFICATION, EntityType.DRUG_CLASS));
            domainSearchFields.add(new SearchFieldVo(FieldKey.CODE, EntityType.DRUG_CLASS));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.DISPENSE_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.DOSAGE_FORM_NAME, EntityType.DOSAGE_FORM));
            domainSearchFields.add(new SearchFieldVo(FieldKey.DOSE_UNIT_NAME, EntityType.DOSE_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.DRUG_TEXT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.DRUG_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.GENERIC_NAME));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.INGREDIENT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.ORDER_UNIT));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.MEDICATION_INSTRUCTION));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.LOCAL_MEDICATION_ROUTE));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.MANUFACTURER));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.PACKAGE_TYPE));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.ADMINISTRATION_SCHEDULE));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.SPECIAL_HANDLING));
            domainSearchFields.add(new SearchFieldVo(FieldKey.VALUE, EntityType.STANDARD_MED_ROUTE));

        }

        return domainSearchFields;
    }

    /**
     * Description
     * 
     * @return dosageForm property
     */
    public String getDosageForm() {

        return dosageForm;
    }

    /**
     * Description
     * 
     * @return requestItemType property, indicating if the item is a National Orderable Item, Product, or NDC
     */
    public EntityType getEntityType() {

        // If not advanced, determine entity type from search criteria
        // added check for domain search - no longer needed for simple product,ndc,oi search
        if (!searchDomain.isAdvancedSearch() && searchDomain.isDomainSearch()) {
            setEntityType(getSearchTerms().get(0).getSearchField().getEntityType());
        }

        return entityType;
    }

    /**
     * getIds for SearchCriteria Vo
     * @return object id as string (is Long)
     */
    public String getId() {

        return id;
    }

    /**
     * Description
     * 
     * @return itemStatus property
     */
    public List<ItemStatus> getItemStatus() {

        return itemStatus;
    }

    /**
     * Description
     * 
     * @return requestStatus property
     */
    public List<RequestItemStatus> getRequestStatus() {

        return requestStatus;
    }

    /**
     * Description
     * 
     * @return searchDomain property
     */
    public SearchDomain getSearchDomain() {

        return searchDomain;
    }

    /**
     * Description
     * 
     * @return searchFields property
     */
    public List<SearchFieldVo> getSearchFields() {

        return searchFields;
    }

    /**
     * Description
     * 
     * @return searchTerms property
     */
    public List<SearchTermVo> getSearchTerms() {

        return searchTerms;
    }

    /**
     * Add a searchterm for this
     * 
     * @param term The search term
     */
    public void addSearchTerm(SearchTermVo term) {

        if (!searchTerms.contains(term)) {
            searchTerms.add(term);
        }
    }

    /**
     * Return a List containing the same SearchTermVo in the original list, plus if one of the search terms was "All", expand
     * it to all the appropriate fields.
     * 
     * @return List<SearchTermVo>
     */
    public List<SearchTermVo> expandSearchTerms() {

        Set<SearchTermVo> expanded = new LinkedHashSet<SearchTermVo>();
        Set<SearchTermVo> all = new LinkedHashSet<SearchTermVo>();

        for (SearchTermVo searchTerm : getSearchTerms()) {
            if (searchTerm.isAllSearch()) {
                List<SearchFieldVo> fields;

                if (isAdvanced()) {


                    fields = new ArrayList<SearchFieldVo>();

                    if (EntityType.NDC.equals(getEntityType())) {
                        all.add(new SearchTermVo(getEntityType(), FieldKey.NDC, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.TRADE_NAME, searchTerm.getValue(), searchTerm
                            .getSearchType()));

                        // Added for UPC UPN simple search
                        all.add(new SearchTermVo(getEntityType(), FieldKey.UPC_UPN, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                    } else if (EntityType.ORDERABLE_ITEM.equals(getEntityType())) {
                        all.add(new SearchTermVo(getEntityType(), FieldKey.OI_NAME, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.ORDERABLE_ITEM_SYNONYM, searchTerm.getValue(),
                            searchTerm.getSearchType()));
                    } else if (EntityType.PRODUCT.equals(getEntityType())) {
                        all.add(new SearchTermVo(getEntityType(), FieldKey.VA_PRODUCT_NAME, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.VA_PRINT_NAME, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.GENERIC_NAME, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.CMOP_ID, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.DRUG_CLASS, searchTerm.getValue(),
                            searchTerm.getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.INGREDIENT, searchTerm.getValue(), searchTerm
                            .getSearchType()));
                        all.add(new SearchTermVo(getEntityType(), FieldKey.DISPLAYABLE_SYNONYM_NAME, searchTerm.getValue(), 
                            searchTerm.getSearchType()));
                    }
                } else {
                    fields = getSimpleSearchFields(getEntityType());
                }

                for (SearchFieldVo searchField : fields) {
                    if (!(FieldKey.SEARCH_ALL_FIELDS.equals(searchField.getFieldKey()) || FieldKey.SEARCH_ALL_DESIGNATED_FIELDS
                        .equals(searchField.getFieldKey()))) {
                        all.add(new SearchTermVo(searchField, searchTerm.getValue(), searchTerm.getSearchType()));
                    }
                }
            } else {
                expanded.add(searchTerm);
            }
        }

        expanded.addAll(all);

        return new ArrayList<SearchTermVo>(expanded);
    }

    /**
     * Return true if the list of SearchTermVo contains one for searching all fields.
     * 
     * @return boolean
     */
    public boolean containsAllSearchTerm() {

        for (SearchTermVo searchTerm : getSearchTerms()) {
            if (searchTerm.isAllSearch()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Description
     * 
     * @return strength property
     */
    public String getStrength() {

        return strength;
    }

    /**
     * Sets the appropriate starting search term for the type of search.
     */
    public void initSearchTerms() {

        if (getSearchTerms().isEmpty()) {
            SearchDomain sd = getSearchDomain();
            getSearchTerms().add(SearchTermVo.newEmptyInstance(sd));
        }
    }

    /**
     * 
     * setEntityTypeChanged(this.entityType != entityType);
     * 
     * @return advanced property
     */
    public boolean isAdvanced() {

        return searchDomain.isAdvancedSearch();
    }

    /**
     * Drug Class Code searches can include a special character to indicate a range search.
     * <p>
     * A simple search for drug classes on the drug class code whose search term includes the special character is a drug
     * class code range search.
     * 
     * @return boolean
     */
    public boolean isDrugClassCodeRangeSearch() {

        boolean domainDrugClassSearch = isDomainSearch() && EntityType.DRUG_CLASS.equals(getEntityType());
        boolean codeRangeSearch = false;

        for (SearchTermVo searchTerm : getSearchTerms()) {
            codeRangeSearch |= searchTerm.isDrugClassCodeRangeSearchTerm();
        }

        return domainDrugClassSearch && codeRangeSearch;
    }

    /**
     * Description
     * 
     * @return true if the {@link SearchDomain#DOMAIN}
     */
    public boolean isDomainSearch() {

        return searchDomain.isDomainSearch();
    }

    /**
     * Description
     * 
     * @return simple property
     */
    public boolean isSimple() {

        return searchDomain.isSimpleSearch();
    }

    /**
     * Description
     * 
     * 
     * @return advancedAndSearch property
     */
    public boolean isAdvancedAndSearch() {

        return advancedAndSearch;
    }

    /**
     * Description
     * 
     * @return true if is a default template
     */
    public boolean isEmpty() {

        if (searchTerms == null || searchTerms.size() != 1) {
            return false;
        }

        return searchTerms.get(0).isEmpty();
    }

    /**
     * Description
     * 
     * @return onlyLocalUse property
     */
    public LocalUseSearchType getLocalUse() {

        return localUse;
    }

    /**
     * Returns the search fields appropriate to the current type of search.
     * 
     * @return searchFields
     */
    private List<SearchFieldVo> retrieveSearchFields() {

        List<SearchFieldVo> list = new ArrayList<SearchFieldVo>();

        if (searchDomain.isDomainSearch()) {
            list = getDomainSearchFields();
        } else if (searchDomain.isAdvancedSearch()) {
            list = getAdvancedSearchFields();
        } else if (searchDomain.isSimpleSearch()) {
            list = getSimpleSearchFields();
        } else if (searchDomain.isProductSearch()) {
            list = getProductSearchFields();
        } else if (searchDomain.isSetupAtcSearch()) {
            list = getSetupAtcSearchFields();
        } else {
            list = getOISearchFields();
        }

        return list;
    }

    /**
     * Description
     * 
     * @param advancedAndSearch advancedAndSearch property
     */
    public void setAdvancedAndSearch(boolean advancedAndSearch) {

        this.advancedAndSearch = advancedAndSearch;
    }

    /**
     * Description
     * 
     * @param dosageForm dosageForm property
     */
    public void setDosageForm(String dosageForm) {

        this.dosageForm = trimToEmpty(dosageForm);
    }

    /**
     * Description
     * 
     * @param entityType property, indicating if the item is a National Orderable Item, Product, or NDC
     */
    public void setEntityType(EntityType entityType) {

        setEntityTypeChanged(this.entityType != entityType);

        this.entityType = entityType;

        // If this is an advanced search, set the search fields appropriate for the entity type
        if (searchDomain.isAdvancedSearch()) {
            setSearchFields(retrieveSearchFields());
        }

        if (searchDomain.isSimpleSearch()) {
            setSearchFields(retrieveSearchFields());
        }
    }

    /**
     * setId for the SearchCriteriaVo.
     * 
     * @param id object id
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * setItemStatus for the SearchCriteriaVo.
     * 
     * @param itemStatus itemStatus property
     */
    public void setItemStatus(List<ItemStatus> itemStatus) {

        this.itemStatus = new ArrayList<ItemStatus>();

        if (itemStatus != null) {
            this.itemStatus.addAll(itemStatus);
        }
    }

    /**
     * setLocalUse for the SearchCriteriaVo.
     * 
     * 
     * @param localUse localUse property
     */
    public void setLocalUse(LocalUseSearchType localUse) {

        this.localUse = localUse;
    }

    /**
     * setRequestStatus for the SearchCriteriaVo.
     * 
     * @param requestStatus requestStatus property
     */
    public void setRequestStatus(List<RequestItemStatus> requestStatus) {

        this.requestStatus = new ArrayList<RequestItemStatus>();

        if (requestStatus != null) {
            this.requestStatus.addAll(requestStatus);
        }
    }

    /**
     * Description
     * 
     * @param searchDomain SearchDomain property
     */
    public void setSearchDomain(SearchDomain searchDomain) {

        this.searchDomain = searchDomain;
    }

    /**
     * Description
     * 
     * @param searchFields searchFields property
     */
    public void setSearchFields(List<SearchFieldVo> searchFields) {

        this.searchFields = new ArrayList<SearchFieldVo>();

        if (searchFields != null) {
            this.searchFields.addAll(searchFields);
        }
    }

    /**
     * Description
     * 
     * @param searchTerms searchTerms property
     */
    public void setSearchTerms(List<SearchTermVo> searchTerms) {

        this.searchTerms = new ArrayList<SearchTermVo>();

        if (searchTerms != null && searchTerms.size() > 0) {
            this.searchTerms.addAll(searchTerms);
        }
    }

    /**
     * Description
     * 
     * @param strength strength property
     */
    public void setStrength(String strength) {

        this.strength = trimToEmpty(strength);
    }

    /**
     * getPrintTemplate for the SearchCriteriaVo.
     * 
     * @return printTemplate property
     */
    public PrintTemplateVo getPrintTemplate() {

        return printTemplate;
    }

    /**
     * setPrintTemplate for the SearchCriteriaVo.
     * 
     * @param printTemplate printTemplate property
     */
    public void setPrintTemplate(PrintTemplateVo printTemplate) {

        this.printTemplate = printTemplate;
    }

    /**
     * Description
     * 
     * @return sortedFieldKey property
     */
    public FieldKey getSortedFieldKey() {

        return sortedFieldKey;
    }

    /**
     * Description
     * 
     * @param sortedFieldKey sortedFieldKey property
     */
    public void setSortedFieldKey(FieldKey sortedFieldKey) {

        this.sortedFieldKey = sortedFieldKey;
    }

    /**
     * Description
     * 
     * @return sortOrder property
     */
    public SortOrder getSortOrder() {

        return sortOrder;
    }

    /**
     * Description
     * 
     * @param sortOrder sortOrder property
     */
    public void setSortOrder(SortOrder sortOrder) {

        this.sortOrder = sortOrder;
    }

    /**
     * Description
     * 
     * @return startRow property
     */
    public int getStartRow() {

        return startRow;
    }

    /**
     * Description
     * 
     * @param startRow startRow property
     */
    public void setStartRow(int startRow) {

        this.startRow = startRow;
    }

    /**
     * Description
     * 
     * @return pageSize property
     */
    public int getPageSize() {

        return pageSize;
    }

    /**
     * Description
     * 
     * @param pageSize pageSize property
     */
    public void setPageSize(int pageSize) {

        this.pageSize = pageSize;
    }

    /**
     * Description
     * 
     * @return entityTypeChanged property
     */
    public boolean isEntityTypeChanged() {

        return entityTypeChanged;
    }

    /**
     * Description
     * 
     * @param entityTypeChanged entityTypeChanged property
     */
    public void setEntityTypeChanged(boolean entityTypeChanged) {

        this.entityTypeChanged = entityTypeChanged;
    }

    /**
     * Selected FieldKey.CATEGORIES to filter on.
     * 
     * @return categories property
     */
    public List<Category> getCategories() {

        return categories;
    }

    /**
     * Selected FieldKey.CATEGORIES to filter on.
     * 
     * @param categories categories property
     */
    public void setCategories(List<Category> categories) {

        this.categories = categories;
    }

    /**
     * Selected FieldKey.SUB_CATEGORIES to filter on.
     * 
     * @return subCategories property
     */
    public List<SubCategory> getSubCategories() {

        return subCategories;
    }

    /**
     * Selected FieldKey.SUB_CATEGORIES to filter on.
     * 
     * @param subCategories categories property
     */
    public void setSubCategories(List<SubCategory> subCategories) {

        this.subCategories = subCategories;
    }

    /**
     * getSearchFieldsMap
     *
     * @return LinkedHashMap 
     */
    public LinkedHashMap<String, String> getSearchFieldsMap() { //NOPMD

        return searchFieldsMap;
    }

    /**
     * setSearchFieldsMap
     *
     * @param searchFieldsMap 
     */
    public void setSearchFieldsMap(LinkedHashMap<String, String> searchFieldsMap) { //NOPMD

        this.searchFieldsMap = searchFieldsMap;
    }

}
