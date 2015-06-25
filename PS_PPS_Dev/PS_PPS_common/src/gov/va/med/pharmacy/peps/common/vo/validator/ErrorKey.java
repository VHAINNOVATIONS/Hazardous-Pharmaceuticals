/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import gov.va.med.pharmacy.peps.common.utility.ResourceBundleUtility;


/**
 * A key into the resource bundle of possible validation errors.
 */
public class ErrorKey implements Serializable {

    // Please keep keys in alphabetical order to ease future maintenance

    /** COMMON_CRITERIA_STRING */
    public static final ErrorKey COMMON_CRITERIA_STRING = new ErrorKey("errors.common.criteriaString");
    
    /** COMMON_CRITERIA_STRING */
    public static final ErrorKey GCN_SEQNUM_NOMATCH = new ErrorKey("errors.gcnseqnum.nomatch");

    /** COMMON_CRITERIA_STRING */
    public static final ErrorKey GCN_FDBSEQNUM_NOMATCH = new ErrorKey("errors.fdbgcnseqnum.nomatch");
    
    /** FDB_ERROR_MESSAGE */
    public static final ErrorKey FDB_ERROR_MESSAGE = new ErrorKey("fdb.error.message");

    
    /** COMMON_EMPTY */
    public static final ErrorKey COMMON_EMPTY = new ErrorKey("errors.common.empty");

    /** COMMON_EQUAL_LENGTH */
    public static final ErrorKey COMMON_EQUAL_LENGTH = new ErrorKey("errors.common.equalLength");

    /** COMMON_INSERT_STRING */
    public static final ErrorKey COMMON_INSERT_STRING = new ErrorKey("errors.common.insertString");

    /** COMMON_INVALID_SELECTION */
    public static final ErrorKey COMMON_INVALID_SELECTION = new ErrorKey("errors.common.invalidSelection");

    /** COMMON_MAX_LENGTH */
    public static final ErrorKey COMMON_MAX_LENGTH = new ErrorKey("errors.common.maxLength");

    /** COMMON_MAX_MIN_LENGTH */
    public static final ErrorKey COMMON_MAX_MIN_LENGTH = new ErrorKey("errors.common.maxMinLength");

    /** COMMON_GROUPLIST_MAX_MIN_LENGTH */
    public static final ErrorKey COMMON_GROUPLIST_MAX_MIN_LENGTH = new ErrorKey("errors.common.groupList.maxMinLength");

    /** COMMON_NEED_UPTO_FIVE_VALUES */
    public static final ErrorKey COMMON_NEED_UPTO_FIVE_VALUES = new ErrorKey("errors.common.need.upto.five.values");

    /** COMMON_MAX_MIN_VALUE_EXCLUSIVE */
    public static final ErrorKey COMMON_MAX_MIN_VALUE_EXCLUSIVE = new ErrorKey("errors.common.maxMinValueExclusive");

    /** COMMON_MAX_MIN_VALUE_INCLUSIVE */
    public static final ErrorKey COMMON_MAX_MIN_VALUE_INCLUSIVE = new ErrorKey("errors.common.maxMinValueInclusive");

    /** COMMON_GROUPLIST_MAX_MIN_VALUE_INCLUSIVE */
    public static final ErrorKey COMMON_GROUPLIST_MAX_MIN_VALUE_INCLUSIVE =
            new ErrorKey("errors.common.groupList.maxMinValueInclusive");

    /** COMMON_NOT_NUMERIC */
    public static final ErrorKey COMMON_NOT_NUMERIC = new ErrorKey("errors.common.notNumeric");

    /** COMMON_NOT_INTEGER */
    public static final ErrorKey COMMON_NOT_INTEGER = new ErrorKey("errors.common.notInteger");

    /** COMMON_GROUP_NOT_NUMERIC */
    public static final ErrorKey COMMON_GROUP_NOT_NUMERIC = new ErrorKey("errors.common.group.notNumeric");

    /** COMMON_NOT_STARTS_WITH_PUNCTUATION */
    public static final ErrorKey COMMON_NOT_STARTS_WITH_PUNCTUATION = new ErrorKey("errors.common.notStartsWithPunctuation");

    /** COMMON_NOT_OPTION */
    public static final ErrorKey COMMON_NOT_OPTION = new ErrorKey("errors.common.notOption");

    /** COMMON_USER_PRIVILEGES */
    public static final ErrorKey COMMON_USER_PRIVILEGES = new ErrorKey("errors.common.userPrivileges");

    /** COMMON_INCORRECT_DECIMAL_DIGITS */
    public static final ErrorKey COMMON_INCORRECT_DECIMAL_DIGITS = new ErrorKey("errors.common.decimalDigits");

    /** COMMON_GROUPLIST_INCORRECT_DECIMAL_DIGITS */
    public static final ErrorKey COMMON_GROUPLIST_INCORRECT_DECIMAL_DIGITS =
            new ErrorKey("errors.common.groupList.decimalDigits");

    /** COMMON_ONLY_DECIMAL_DIGITS */
    public static final ErrorKey COMMON_ONLY_DECIMAL_DIGITS = new ErrorKey("errors.common.only.decimalDigits");

    /** COMMON_NEED_AT_LEAST_ONE_ALPHA_STRING */
    public static final ErrorKey COMMON_NEED_AT_LEAST_ONE_ALPHA_STRING = new ErrorKey("errors.common.need.one.or.more.alpha");

    /** COMMON_NEED_FIRST_CHARACTER_IS_ALPHA_NUMERIC */
    public static final ErrorKey COMMON_NEED_FIRST_CHARACTER_IS_ALPHA_NUMERIC =
            new ErrorKey("errors.common.need.first.character.is.alpha.numeric");

    /** COMMON_CANNOT_HAVE_TRAILING_SPACES */
    public static final ErrorKey COMMON_CANNOT_HAVE_TRAILING_SPACES = new ErrorKey("errors.common.cannot.have.trailing.spaces");

    /** COMMON_NEED_ONLY_ONE_UPPERCASE_CHARACTER */
    public static final ErrorKey COMMON_NEED_ONLY_ONE_UPPERCASE_CHARACTER =
            new ErrorKey("errors.common.need.only.one.uppercase.character");

    /** COMMON_IMPROPER_DATE_FORMAT */
    public static final ErrorKey COMMON_STARTS_WITH_NUMERIC = new ErrorKey("errors.common.starts.with.numeric");

    /** COMMON_IMPROPER_DATE_FORMAT */
    public static final ErrorKey COMMON_IMPROPER_DATE_FORMAT = new ErrorKey("errors.common.improper.date.format");

    /** COMMON_FIELD_DEPENDS_ON_PARENT */
    public static final ErrorKey COMMON_FIELD_DEPENDS_ON_PARENT = new ErrorKey("errors.common.field.depends.on.parent");

    /** ATC_MNEMONIC_DUPLICATE_FOUND */
    public static final ErrorKey COMMON_STARTS_WITH_PUNCTUATION = new ErrorKey("errors.common.starts.with.punctuation");

    /** COMMON_STARTS_WITH_PUNCTUATION */
    public static final ErrorKey COMMON_CANNOT_BE_IN_THE_FUTURE = new ErrorKey("errors.common.cannot.be.in.the.future");

    /** CMOP_ID_EXISTS */
    public static final ErrorKey CMOP_ID_EXISTS = new ErrorKey("errors.cmopid.exists");

    /** DRUG_CLASS_MULTIPLE_CLASSIFICATIONS */
    public static final ErrorKey DRUG_CLASS_MULTIPLE_CLASSIFICATIONS = new ErrorKey("errors.drugClass.multipleClassifications");

    /** DAY_ND_OR_DOSE_NI_LIMIT_INCORRECT_VAL */
    public static final ErrorKey DAY_ND_OR_DOSE_NI_LIMIT_INCORRECT_VAL = new ErrorKey("errors.dayNdOrDoseNlLimit.value");

    /** DOSE_UNIT_SYNONYMS */
    public static final ErrorKey DOSE_UNIT_SYNONYMS = new ErrorKey("errors.dose.unit.synonyms");

    /** DRUG_CLASS_NO_FIRST_CHAR_DIGIT */
    public static final ErrorKey DRUG_CLASS_NO_FIRST_CHAR_DIGIT = new ErrorKey("errors.drugClass.noFirstCharDigit");

    /** DRUG_CLASS_INCORRECT_CODE_FORMAT */
    public static final ErrorKey DRUG_CLASS_INCORRECT_CODE_FORMAT = new ErrorKey("errors.drugClass.incorrectCode");

    /** DRUG_CLASS_INCORRECT_PRIMARY */
    public static final ErrorKey DRUG_CLASS_INCORRECT_PRIMARY = new ErrorKey("errors.drugClass.incorrectPrimary");

    /** DRUG_CLASS_DUPLICATE */
    public static final ErrorKey DRUG_CLASS_DUPLICATE = new ErrorKey("errors.drugClass.duplicate");

    /** DUPLICATE_SEARCH_TERMS */
    public static final ErrorKey DUPLICATE_SEARCH_TERMS = new ErrorKey("errors.duplicate.search.terms");

    /** DUPLICATE_DEFAULT_SEARCH */
    public static final ErrorKey DUPLICATE_DEFAULT_SEARCH = new ErrorKey("errors.duplicate.default.search");

    /** FOLLOWUP_TIME_EMPTY */
    public static final ErrorKey FOLLOWUP_TIME_EMPTY = new ErrorKey("errors.followUpTime.empty");

    /** FOLLOWUP_TIME_WITHOUT_HIGHRISK */
    public static final ErrorKey FOLLOWUP_TIME_WITHOUT_HIGHRISK = new ErrorKey("errors.followUpTime.withoutHighRisk");

    /** FOLLOWUP_TIME_HIGHRISK_NO */
    public static final ErrorKey FOLLOWUP_TIME_HIGHRISK_NO = new ErrorKey("errors.followUpTime.highRiskNo");

    /** FORMULARY_ALTERNATIVE_NOT_SELECTED */
    public static final ErrorKey FORMULARY_ALTERNATIVE_NOT_SELECTED = new ErrorKey("errors.formulary.alternative.notselected");

    /** HIGH_RISK_EMPTY */
    public static final ErrorKey HIGH_RISK_EMPTY = new ErrorKey("errors.highRisk.empty");

    /** INPATIENT_PHARMACY_LOCATION_VALUES */
    public static final ErrorKey INPATIENT_PHARMACY_LOCATION_VALUES = new ErrorKey("errors.inpatient.pharmacy.locaion.values");

    /** AR_WS_CONVERSION_NUMBER_EMPTY */
    public static final ErrorKey AR_WS_CONVERSION_NUMBER_EMPTY = new ErrorKey("errors.ar.ws.amis.conversion.number.empty");

    /** INACTIVATION_REASON_REQUIRED */
    public static final ErrorKey INACTIVATION_REASON_REQUIRED = new ErrorKey("errors.inactivation.reason.required");

    /** INGREDIENT_STRENGTH_EMPTY */
    public static final ErrorKey INGREDIENT_STRENGTH_EMPTY = new ErrorKey("errors.active.ingredient.strength.empty");

    /** ACTIVE_INGREDIENT_NAME_EMPTY */
    public static final ErrorKey ACTIVE_INGREDIENT_NAME_EMPTY = new ErrorKey("errors.active.ingredient.name.empty");

    /** ACTIVE_INGREDIENT_UNIT_EMPTY */
    public static final ErrorKey ACTIVE_INGREDIENT_UNIT_EMPTY = new ErrorKey("errors.active.ingredient.unit.empty");

    /** DRUG_CLASS_NO_WILDCARDS */
    public static final ErrorKey DRUG_CLASS_NO_WILDCARDS = new ErrorKey("errors.drugClass.noWildcards");

    /** GENERIC_NAME_NO_FIRST_CHAR_DIGIT */
    public static final ErrorKey GENERIC_NAME_NO_FIRST_CHAR_DIGIT = new ErrorKey("errors.genericName.noFirstCharDigit");

    /** GENERIC_NAME_NO_WIRLDCARDS */
    public static final ErrorKey GENERIC_NAME_NO_WIRLDCARDS = new ErrorKey("errors.genericName.noWildcards");

    /** INVALID_SEARCH */
    public static final ErrorKey INVALID_SEARCH = new ErrorKey("errors.search.invalidSearch");

    /** EMPTY_SEARCH */
    public static final ErrorKey EMPTY_SEARCH = new ErrorKey("errors.search.emptySearch");

    /** RANGE_SEARCH_BOUNDS */
    public static final ErrorKey RANGE_SEARCH_BOUNDS = new ErrorKey("errors.range.search.bounds");

    /** RANGE_SEARCH_ORDER */
    public static final ErrorKey RANGE_SEARCH_ORDER = new ErrorKey("errors.range.search.order");

    /** EMPTY_COMMENT */
    public static final ErrorKey EMPTY_COMMENT = new ErrorKey("errors.comment.empty");

    /** UNSAVED_DEFAULT_SEARCH */
    public static final ErrorKey UNSAVED_DEFAULT_SEARCH = new ErrorKey("errors.search.unsavedDefaultSearch");

    /** INVALID_NDC_FORMAT */
    public static final ErrorKey INVALID_NDC_FORMAT = new ErrorKey("errors.search.invalidNdcFormat");

    /** ALL_AND_LOCAL_REQUESTS_SEARCH */
    public static final ErrorKey ALL_AND_LOCAL_REQUESTS_SEARCH = new ErrorKey("errors.search.all.and.local.requests");

    /** ALL_AND_OTHER_FILTER_SEARCH */
    public static final ErrorKey ALL_AND_OTHER_FILTER_SEARCH = new ErrorKey("errors.search.all.and.other.filter");

    /** ITEM_TYPE_INVALID_DOMAIN */
    public static final ErrorKey ITEM_TYPE_INVALID_DOMAIN = new ErrorKey("errors.itemType.invalidDomain");

    /** MARK_FOR_LOCAL_USE_APPLICATION_PACKAGE_USE */
    public static final ErrorKey MARK_FOR_LOCAL_USE_APPLICATION_PACKAGE_USE = new ErrorKey("errors.markForLocalUse.appPkgUse");

    /** MARK_FOR_LOCAL_USE_INACTIVE_ITEM */
    public static final ErrorKey MARK_FOR_LOCAL_USE_INACTIVE_ITEM = new ErrorKey("errors.markForLocalUse.inactiveItem");

    /** MARK_FOR_LOCAL_USE_ORDERABLE_ITEM */
    public static final ErrorKey MARK_FOR_LOCAL_USE_ORDERABLE_ITEM = new ErrorKey("errors.markForLocalUse.orderableItem");

    /** PRODUCT_TYPE_SUPPLY_ONLY */
    public static final ErrorKey PRODUCT_TYPE_SUPPLY_ONLY = new ErrorKey("errors.productType.supplyOnly");

    /** QUANTITY_DISP_MSG_NOT_NULL */
    public static final ErrorKey QUANTITY_DISP_MSG_NOT_NULL = new ErrorKey("errors.quantity.dispense.msg.not.null");

    /** START_END_DATE_EQUAL */
    public static final ErrorKey START_END_DATE_EQUAL = new ErrorKey("errors.startEndDate.equal");

    /** START_END_DATE_PRECEEDS */
    public static final ErrorKey START_END_DATE_PRECEEDS = new ErrorKey("errors.startEndDate.precedes");

    /** STATUS_COTS_REJECTED */
    public static final ErrorKey STATUS_COTS_REJECTED = new ErrorKey("errors.status.cotsRejected");

    /** STATUS_INVALID_DOMAIN */
    public static final ErrorKey STATUS_INVALID_DOMAIN = new ErrorKey("errors.status.invalidDomain");

    /** SYNONYM_REQ_FIELD_EMPTY */
    public static final ErrorKey SYNONYM_REQ_FIELD_EMPTY = new ErrorKey("errors.synonym.req.field.empty");

    /** ATC_MNEMONIC_FIELD_EMPTY */
    public static final ErrorKey ATC_MNEMONIC_FIELD_EMPTY = new ErrorKey("errors.atc.mnemonic.empty");

    /** ATC_CANISTER_REQ_FIELD_EMPTY */
    public static final ErrorKey ATC_CANISTER_REQ_FIELD_EMPTY = new ErrorKey("errors.atc.req.field.empty");

    /** ATC_WARD_GROUP_DUPLICATE */
    public static final ErrorKey ATC_WARD_GROUP_DUPLICATE = new ErrorKey("errors.atc.ward.group.duplicate");

    /** ATC_MODE_SELECTED_WITHOUT_MARKING */
    public static final ErrorKey ATC_MODE_SELECTED_WITHOUT_MARKING = new ErrorKey("warnings.atc.mode.selected.without.marking");

    /** ATC_MNEMONIC_DUPLICATE_FOUND */
    public static final ErrorKey ATC_MNEMONIC_DUPLICATE_FOUND = new ErrorKey("errors.atc.mnemonic.duplicate.found");

    /** NATIONAL_POSSIBLE_DOSAGES_EMPTY */
    public static final ErrorKey NATIONAL_POSSIBLE_DOSAGES_EMPTY = new ErrorKey("errors.possible.dosages.req.field.empty");

    /** NATIONAL_POSSIBLE_DOSAGES_ADD */
    public static final ErrorKey NATIONAL_POSSIBLE_DOSAGES_ADD = new ErrorKey("errors.possible.dosages.add");

    /** NATIONAL_POSSIBLE_DOSAGES_MOD */
    public static final ErrorKey NATIONAL_POSSIBLE_DOSAGES_MOD = new ErrorKey("errors.possible.dosages.mod");

    /** LOCAL_POSSIBLE_DOSAGES_EMPTY */
    public static final ErrorKey LOCAL_POSSIBLE_DOSAGES_EMPTY = new ErrorKey("errors.local.possible.dosages.req.field.empty");

    /** PRODUCT_INGREDIENTUNIT_OI_DOSAGEFORMUNIT_MISMATCH */
    public static final ErrorKey PRODUCT_INGREDIENTUNIT_OI_DOSAGEFORMUNIT_MISMATCH =
            new ErrorKey("errors.productingredientunit.oidosageformunit.mismatch");

    /** TRADE_NAME_NO_FIRST_CHAR_DIGIT */
    public static final ErrorKey TRADE_NAME_NO_FIRST_CHAR_DIGIT = new ErrorKey("errors.tradeName.noFirstCharDigit");

    /** TRADE_NAME_NO_WILDCARDS */
    public static final ErrorKey TRADE_NAME_NO_WILDCARDS = new ErrorKey("errors.tradeName.noWildcards");

    /** NDC_LENGTH */
    public static final ErrorKey NDC_LENGTH = new ErrorKey("errors.ndc.length");

    /** NDC_FORMAT */
    public static final ErrorKey NDC_FORMAT = new ErrorKey("errors.ndc.ndcFormat");

    /** PRODUCT_PARENT_EMPTY */
    public static final ErrorKey PRODUCT_PARENT_EMPTY = new ErrorKey("errors.ndc.productparentempty");

    /** PRODUCT_PARENT_EMPTY_CHANGE_PARENT */
    public static final ErrorKey PRODUCT_PARENT_EMPTY_CHANGE_PARENT = new ErrorKey(
        "errors.ndc.productparentempty.change.parent");

    /** ORDERABLE_ITEM_PARENT_EMPTY */
    public static final ErrorKey ORDERABLE_ITEM_PARENT_EMPTY = new ErrorKey("errors.product.oiparentempty");

    /** ORDERABLE_ITEM_PARENT_EMPTY_CHANGE_PARENT */
    public static final ErrorKey ORDERABLE_ITEM_PARENT_EMPTY_CHANGE_PARENT = new ErrorKey(
        "errors.product.oiparentempty.change.parent");

    /** ORDERABLE_ITEM_PARENT_CANNOT_BE_LOI */
    public static final ErrorKey ORDERABLE_ITEM_PARENT_CANNOT_BE_LOI = new ErrorKey("errors.oi.oiparentcannotbeloi");

    /** ORDERABLE_ITEM_LOCAL_USE_DEFAULT_MED_ROUTE */
    public static final ErrorKey ORDERABLE_ITEM_LOCAL_USE_DEFAULT_MED_ROUTE = new ErrorKey("errors.oi.defaultMedRoute");

    /** SHIFT_FORMAT */
    public static final ErrorKey SHIFT_FORMAT = new ErrorKey("errors.administrationSchedule.shiftFormat");

    /** HOSPITAL_SHIFT_FORMAT */
    public static final ErrorKey HOSPITAL_SHIFT_FORMAT = new ErrorKey("errors.administrationSchedule.hospitalShiftFormat");

    /** ADMIN_SCHEDULES_DUPLICATE */
    public static final ErrorKey ADMIN_SCHEDULES_DUPLICATE = new ErrorKey("errors.administrationSchedule.duplicate");

    /** ADMIN_SCHEDULES_DEFAULT */
    public static final ErrorKey ADMIN_SCHEDULES_DEFAULT = new ErrorKey("errors.administrationSchedule.default");

    /** ADMIN_SCHEDULES_OTHER_NAME */
    public static final ErrorKey ADMIN_SCHEDULES_OTHER_NAME = new ErrorKey("errors.administrationSchedule.other.name");

    /** ADMINISTRATION_TIMES_24_CLOCK */
    public static final ErrorKey ADMINISTRATION_TIMES_24_CLOCK = new ErrorKey("errors.administrationTimes.24clock");

    /** ADMINISTRATION_TIMES_ASCENDING_ORDER */
    public static final ErrorKey ADMINISTRATION_TIMES_ASCENDING_ORDER =
            new ErrorKey("errors.administrationTimes.ascendingOrder");

    /** ADMINISTRATION_TIMES_EQUAL_LENGTH */
    public static final ErrorKey ADMINISTRATION_TIMES_EQUAL_LENGTH = new ErrorKey("errors.administrationTimes.equalLength");

    /** USER_ROLE_ERROR */
    public static final ErrorKey USER_ROLE_ERROR = new ErrorKey("errors.user.role");

    /** FORMULARY_STATUS_RULE_2 */
    public static final ErrorKey FORMULARY_STATUS_RULE_2 = new ErrorKey("errors.formulary.status.rule2");

    /** FORMULARY_STATUS_RULE_3 */
    public static final ErrorKey FORMULARY_STATUS_RULE_3 = new ErrorKey("errors.formulary.status.rule3");

    /** FORMULARY_STATUS_RULE_4 */
    public static final ErrorKey FORMULARY_STATUS_RULE_4 = new ErrorKey("errors.formulary.status.rule4");

    /** FORMULARY_STATUS_RULE_5 */
    public static final ErrorKey FORMULARY_STATUS_RULE_5 = new ErrorKey("errors.formulary.status.rule5");

    /** OI_LOCAL_USE */
    public static final ErrorKey OI_LOCAL_USE = new ErrorKey("errors.oi.local.use");

    /** OI_ALREADY_MAKRED_FOR_LOCAL_USE */
    public static final ErrorKey OI_ALREADY_MAKRED_FOR_LOCAL_USE = new ErrorKey("errors.oi.already.marked.for.local.use");

    /** DEA_SPECIAL_HANDLING_COUNT */
    public static final ErrorKey DEA_SPECIAL_HANDLING_COUNT = new ErrorKey("errors.dea.special.handling.count");

    /** DEA_SCHEDULE_VALIDATION */
    public static final ErrorKey DEA_SCHEDULE_VALIDATION = new ErrorKey("errors.dea.schedule");

    /** FREQUENCY_DIVISIBLE */
    public static final ErrorKey FREQUENCY_DIVISIBLE = new ErrorKey("errors.frequency.not.divisible");

    /** INVALID_OTC */
    public static final ErrorKey INVALID_OTC = new ErrorKey("invalid.otc");
    
    /** POSSIBLE_DOSAGES_IS_EMPTY */
    public static final ErrorKey POSSIBLE_DOSAGES_IS_EMPTY = new ErrorKey("errors.possible.dosages.empty");
    
    /** POSS_DOSAGE_PROD_PACKAGE_MUST_BE_EMPTY */
    public static final ErrorKey POSS_DOSAGE_PROD_PACKAGE_MUST_BE_EMPTY =
        new ErrorKey("poss.dosage.prod.package.must.be.empty");
    
    /** PRODUCT_PACKAGE_MUST_HAVE_VALUE */
    public static final ErrorKey PRODUCT_PACKAGE_MUST_HAVE_VALUE = new ErrorKey("prod.package.must.have.value");
    
    /** PRODUCT_PACKAGE_MUST_BE_EMPTY */
    public static final ErrorKey PRODUCT_PACKAGE_MUST_BE_EMPTY = new ErrorKey("prod.package.must.be.empty");
    
    /** NO_DOSAGES_DEFINED */
    public static final ErrorKey NO_DOSAGES_DEFINED = new ErrorKey("no.dosages.defined");
    
    /** PRODUCT_UNIT_OI_DOSAGEFORMUNIT_MISMATCH */
    public static final ErrorKey PRODUCT_UNIT_OI_DOSAGEFORMUNIT_MISMATCH = new ErrorKey("product.unit.dosage.form.mismatch");

    /** PRODUCT_LESS_RESTRICTIVE_THAN_OI_DOSAGE */
    public static final ErrorKey PRODUCT_LESS_RESTRICTIVE_THAN_OI_DOSAGE =
        new ErrorKey("product.less.restrictive.than.oi.dosage");
    
    /** PRODUCT_AND_NDC_PENDING */
    public static final ErrorKey PRODUCT_AND_NDC_PENDING = new ErrorKey("product.and.ndc.pending");

    /** PRODUCT_PENDING_NDC_APPROVED */
    public static final ErrorKey PRODUCT_PENDING_NDC_APPROVED = new ErrorKey("product.pending.ndc.approved");

    /** PRODUCT_INACTIVE_NDC_ACTIVE */
    public static final ErrorKey PRODUCT_INACTIVE_NDC_ACTIVE = new ErrorKey("product.inactive.ndc.active");
    
    /** NO_ITEM_TO_BIND_ENTITY */
    public static final ErrorKey NO_ITEM_TO_BIND_ENTITY = new ErrorKey("no.item.to.bind.entity");

    /** VISTA_XML_MARSHALLING */
    public static final ErrorKey VISTA_XML_MARSHALLING = new ErrorKey("vista.xml.marshalling");

    /** CANNOT_DELETE */
    public static final ErrorKey CANNOT_DELETE = new ErrorKey("cannot.delete");
    
    /** REQUIRED_ITEMS_NOT_SELECTED */
    public static final ErrorKey REQUIRED_ITEMS_NOT_SELECTED = new ErrorKey("required.items.not.selected");

    /** NO_ITEMS_WERE_SELECTED */
    public static final ErrorKey NO_ITEMS_WERE_SELECTED = new ErrorKey("no.items.were.selected");

    /** NO_ITEMS_WERE_SELECTED_DELETED */
    public static final ErrorKey NO_ITEMS_WERE_SELECTED_DELETED = new ErrorKey("no.items.were.selected.deleted");

    /** COULD_NOT_FIND_NDC */
    public static final ErrorKey COULD_NOT_FIND_NDC = new ErrorKey("could.not.find.ndc");

    /** SELECTED_NDCS_MUST_HAVE_SAME_GCN */
    public static final ErrorKey SELECTED_NDCS_MUST_HAVE_SAME_GCN = new ErrorKey("selected.ndcs.must.have.same.gcn");
    
    /** DOMAIN_MUST_BE_ACTIVE */
    public static final ErrorKey DOMAIN_MUST_BE_ACTIVE = new ErrorKey("domain.must.be.active");
    
    /** DUPLICATE_INGREDIENT */
    public static final ErrorKey DUPLICATE_INGREDIENT = new ErrorKey("duplicate.ingredient");

    /** DUPLICATE_DRUGCLASS */
    public static final ErrorKey DUPLICATE_DRUGCLASS = new ErrorKey("duplicate.drugclass");

    
    /** PENDING_MULTIEDIT */
    public static final ErrorKey PRIMARY_ING_MUST_BE_ACTIVE = new ErrorKey("primary.ing.must.be.active");
    
    /** PRIMARY_ING_MUST_BE_ACTIVE */
    public static final ErrorKey PENDING_MULTIEDIT = new ErrorKey("pending.multiedit");

    /** CATEGORY_PRIMARY_DRUG_CLASS_SUPPLY_MISMATCH */
    public static final ErrorKey CATEGORY_PRIMARY_DRUG_CLASS_SUPPLY_MISMATCH = new ErrorKey(
        "error.category.primary.drug.class.supply.mismatch");

    private static final long serialVersionUID = 1L;
    private static final String RESOURCE_BUNDLE = "properties/validation/ValidationErrors";
    private String key;

    /**
     * Set the key String.
     * 
     * @param key String for the current ErrorKey key property
     */
    private ErrorKey(String key) {

        this.key = key;
    }

    /**
     * Get the localized error message as a String for the given ErrorKey using the {@link Locale#getDefault()}.
     * 
     * @param key ErrorKey to localize
     * @return String localized error message for the given ErrorKey
     */
    public static String getLocalizedError(ErrorKey key) {
        return ResourceBundleUtility.getResourceBundleValue(RESOURCE_BUNDLE, key.getKey(), Locale.getDefault());
    }

    /**
     * Get the localized error message as a String for the given ErrorKey using the given {@link Locale}.
     * 
     * @param key ErrorKey to localize
     * @param locale {@link Locale} for which to localize the error
     * @return String localized error message for the given ErrorKey
     */
    public static String getLocalizedError(ErrorKey key, Locale locale) {
        return ResourceBundleUtility.getResourceBundleValue(RESOURCE_BUNDLE, key.getKey(), locale);
    }



    /**
     * Get the localized error message as a String for this ErrorKey using the {@link Locale#getDefault()}.
     * 
     * @return String localized error message
     */
    public String getLocalizedError() {
        return ResourceBundleUtility.getResourceBundleValue(RESOURCE_BUNDLE, key, Locale.getDefault());
    }

    /**
     * Get the localized error message as a String for this ErrorKey using the given {@link Locale}.
     * 
     * @param locale {@link Locale} for which to localize this error
     * @return String localized error message
     */
    public String getLocalizedError(Locale locale) {
        return ResourceBundleUtility.getResourceBundleValue(RESOURCE_BUNDLE, key, locale);
    }

    /**
     * 
     * @return String key property
     */
    private String getKey() {
        return key;
    }

    /**
     * equals
     * @param obj to check
     * @return true of obj is equal to the key
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ErrorKey) {
            return ((ErrorKey) obj).getKey().equals(key);
        }

        return false;
    }

    /**
     * hasCode
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(key).toHashCode();
    }

    /**
     * to String
     * @return String
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("key", key).toString();
    }
}
