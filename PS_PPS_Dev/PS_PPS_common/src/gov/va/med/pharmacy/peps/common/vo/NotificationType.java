/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types available to an NotificationType
 * 
 * There are a large number of entities in this Enum, many of the notification types in effect cannot exist (i.e. rejected
 * Local Medication Route), but these must exist to support the requirements as they currently stand
 *  
 */
public enum NotificationType {

    /** APPROVED_ORDERABLE_ITEMS */
    APPROVED_ORDERABLE_ITEMS(true),

    /** REJECTED_ORDERABLE_ITEMS */
    REJECTED_ORDERABLE_ITEMS(true),

    /** MODIFIED_ORDERABLE_ITEMS */
    MODIFIED_ORDERABLE_ITEMS(true),

    /** INACTIVATED_ORDERABLE_ITEMS */
    INACTIVATED_ORDERABLE_ITEMS(true),

    /** ORDERABLE_ITEM_MARKED_FOR_LOCAL_USE */
    ORDERABLE_ITEM_MARKED_FOR_LOCAL_USE(true),


    /** APPROVED_PRODUCT_ITEMS */
    APPROVED_PRODUCT_ITEMS(true),

    /** REJECTED_PRODUCT_ITEMS */
    REJECTED_PRODUCT_ITEMS(true),

    /** MODIFIED_PRODUCT_ITEMS */
    MODIFIED_PRODUCT_ITEMS(true),

    /** INACTIVATED_PRODUCT_ITEMS */
    INACTIVATED_PRODUCT_ITEMS(true),

    /** PRODUCT_ITEM_MARKED_FOR_LOCAL_USE */
    PRODUCT_ITEM_MARKED_FOR_LOCAL_USE(true),


    /** APPROVED_NDC_ITEMS */
    APPROVED_NDC_ITEMS(true),

    /** REJECTED_NDC_ITEMS */
    REJECTED_NDC_ITEMS(true),

    /** MODIFIED_NDC_ITEMS */
    MODIFIED_NDC_ITEMS(true),

    /** INACTIVATED_NDC_ITEMS */
    INACTIVATED_NDC_ITEMS(true),


    /** APPROVED_ADMINISTRATION_SCHEDULE */
    APPROVED_ADMINISTRATION_SCHEDULE(false),

    /** REJECTED_ADMINISTRATION_SCHEDULE */
    REJECTED_ADMINISTRATION_SCHEDULE(false),

    /** MODIFIED_ADMINISTRATION_SCHEDULE */
    MODIFIED_ADMINISTRATION_SCHEDULE(false),

    /** INACTIVATED_ADMINISTRATION_SCHEDULE */
    INACTIVATED_ADMINISTRATION_SCHEDULE(false),


    /** APPROVED_DISPENSE_UNIT */
    APPROVED_DISPENSE_UNIT(false),

    /** REJECTED_DISPENSE_UNIT */
    REJECTED_DISPENSE_UNIT(false),

    /** MODIFIED_DISPENSE_UNIT */
    MODIFIED_DISPENSE_UNIT(false),

    /** INACTIVATED_DISPENSE_UNIT */
    INACTIVATED_DISPENSE_UNIT(false),


    /** APPROVED_DOSAGE_FORM */
    APPROVED_DOSAGE_FORM(false),

    /** REJECTED_DOSAGE_FORM */
    REJECTED_DOSAGE_FORM(false),

    /** MODIFIED_DOSAGE_FORM */
    MODIFIED_DOSAGE_FORM(false),

    /** INACTIVATED_DOSAGE_FORM */
    INACTIVATED_DOSAGE_FORM(false),


    /** APPROVED_DOSE_UNIT */
    APPROVED_DOSE_UNIT(false),

    /** REJECTED_DOSE_UNIT */
    REJECTED_DOSE_UNIT(false),

    /** MODIFIED_DOSE_UNIT */
    MODIFIED_DOSE_UNIT(false),

    /** INACTIVATED_DOSE_UNIT */
    INACTIVATED_DOSE_UNIT(false),


    /** APPROVED_DRUG_CLASS */
    APPROVED_DRUG_CLASS(false),

    /** REJECTED_DRUG_CLASS */
    REJECTED_DRUG_CLASS(false),

    /** MODIFIED_DRUG_CLASS */
    MODIFIED_DRUG_CLASS(false),

    /** INACTIVATED_DRUG_CLASS */
    INACTIVATED_DRUG_CLASS(false),


    /** APPROVED_DRUG_TEXT */
    APPROVED_DRUG_TEXT(false),

    /** REJECTED_DRUG_TEXT */
    REJECTED_DRUG_TEXT(false),

    /** MODIFIED_DRUG_TEXT */
    MODIFIED_DRUG_TEXT(false),

    /** INACTIVATED_DRUG_TEXT */
    INACTIVATED_DRUG_TEXT(false),


    /** APPROVED_DRUG_UNIT */
    APPROVED_DRUG_UNIT(false),

    /** REJECTED_DRUG_UNIT */
    REJECTED_DRUG_UNIT(false),

    /** MODIFIED_DRUG_UNIT */
    MODIFIED_DRUG_UNIT(false),

    /** INACTIVATED_DRUG_UNIT */
    INACTIVATED_DRUG_UNIT(false),


    /** APPROVED_GENERIC_NAME */
    APPROVED_GENERIC_NAME(false),

    /** REJECTED_GENERIC_NAME */
    REJECTED_GENERIC_NAME(false),

    /** MODIFIED_GENERIC_NAME */
    MODIFIED_GENERIC_NAME(false),

    /** INACTIVATED_GENERIC_NAME */
    INACTIVATED_GENERIC_NAME(false),


    /** APPROVED_INGREDIENT */
    APPROVED_INGREDIENT(false),

    /** REJECTED_INGREDIENT */
    REJECTED_INGREDIENT(false),

    /** MODIFIED_INGREDIENT */
    MODIFIED_INGREDIENT(false),

    /** INACTIVATED_INGREDIENT */
    INACTIVATED_INGREDIENT(false),


    /** APPROVED_MANUFACTURER */
    APPROVED_MANUFACTURER(false),

    /** REJECTED_MANUFACTURER */
    REJECTED_MANUFACTURER(false),

    /** MODIFIED_MANUFACTURER */
    MODIFIED_MANUFACTURER(false),

    /** INACTIVATED_MANUFACTURER */
    INACTIVATED_MANUFACTURER(false),


    /** APPROVED_MEDICATION_INSTRUCTION */
    APPROVED_MEDICATION_INSTRUCTION(false),

    /** REJECTED_MEDICATION_INSTRUCTION */
    REJECTED_MEDICATION_INSTRUCTION(false),

    /** MODIFIED_MEDICATION_INSTRUCTION */
    MODIFIED_MEDICATION_INSTRUCTION(false),

    /** INACTIVATED_MEDICATION_INSTRUCTION */
    INACTIVATED_MEDICATION_INSTRUCTION(false),


    /** APPROVED_ORDER_UNIT */
    APPROVED_ORDER_UNIT(false),

    /** REJECTED_ORDER_UNIT */
    REJECTED_ORDER_UNIT(false),

    /** MODIFIED_ORDER_UNIT */
    MODIFIED_ORDER_UNIT(false),

    /** INACTIVATED_ORDER_UNIT */
    INACTIVATED_ORDER_UNIT(false),


    /** APPROVED_SPECIAL_HANDLING */
    APPROVED_SPECIAL_HANDLING(false),

    /** REJECTED_SPECIAL_HANDLING */
    REJECTED_SPECIAL_HANDLING(false),

    /** MODIFIED_SPECIAL_HANDLING */
    MODIFIED_SPECIAL_HANDLING(false),

    /** INACTIVATED_SPECIAL_HANDLING */
    INACTIVATED_SPECIAL_HANDLING(false),


    /** APPROVED_PACKAGE_TYPE */
    APPROVED_PACKAGE_TYPE(false),

    /** REJECTED_PACKAGE_TYPE */
    REJECTED_PACKAGE_TYPE(false),

    /** MODIFIED_PACKAGE_TYPE */
    MODIFIED_PACKAGE_TYPE(false),

    /** INACTIVATED_PACKAGE_TYPE */
    INACTIVATED_PACKAGE_TYPE(false),


    /** APPROVED_PHARMACY_SYSTEM */
    APPROVED_PHARMACY_SYSTEM(false),

    /** REJECTED_PHARMACY_SYSTEM */
    REJECTED_PHARMACY_SYSTEM(false),

    /** MODIFIED_PHARMACY_SYSTEM */
    MODIFIED_PHARMACY_SYSTEM(false),

    /** INACTIVATED_PHARMACY_SYSTEM */
    INACTIVATED_PHARMACY_SYSTEM(false),


    /** APPROVED_STANDARD_MEDICATION_ROUTE */
    APPROVED_STANDARD_MED_ROUTE(false),

    /** REJECTED_STANDARD_MEDICATION_ROUTE */
    REJECTED_STANDARD_MED_ROUTE(false),

    /** MODIFIED_STANDARD_MEDICATION_ROUTE */
    MODIFIED_STANDARD_MED_ROUTE(false),

    /** INACTIVATED_STANDARD_MEDICATION_ROUTE */
    INACTIVATED_STANDARD_MED_ROUTE(false),


    /** APPROVED_LOCAL_MEDICATION_ROUTE */
    APPROVED_LOCAL_MEDICATION_ROUTE(false),

    /** REJECTED_LOCAL_MEDICATION_ROUTE */
    REJECTED_LOCAL_MEDICATION_ROUTE(false),

    /** MODIFIED_LOCAL_MEDICATION_ROUTE */
    MODIFIED_LOCAL_MEDICATION_ROUTE(false),

    /** INACTIVATED_LOCAL_MEDICATION_ROUTE */
    INACTIVATED_LOCAL_MEDICATION_ROUTE(false),


    /** APPROVED_RX_CONSULT */
    APPROVED_RX_CONSULT(false),

    /** REJECTED_RX_CONSULT */
    REJECTED_RX_CONSULT(false),

    /** MODIFIED_RX_CONSULT */
    MODIFIED_RX_CONSULT(false),

    /** INACTIVATED_RX_CONSULT */
    INACTIVATED_RX_CONSULT(false),


    /** COTS_UPDATE */
    COTS_UPDATE(false),

    /** SYSTEM_EVENT */
    SYSTEM_EVENT(false),


    /** DOMAIN_EVENT */
    DOMAIN_EVENT(false),

    /** UNKNOWN_NON_SYSTEM_EVENT */
    UNKNOWN_NON_SYSTEM_EVENT(false);

    private boolean canBeLocalOnly;

    /**
     * Constructor takes in whether or not the filter can apply to items that show up in the OnlyLocalUse filter
     * 
     * The items will have to be checked individually, but this at least prevents us from getting no useful items
     * @param canBeLocalOnly if its possible to be amrked for local use or not
     */
    private NotificationType(boolean canBeLocalOnly) {

        this.canBeLocalOnly = canBeLocalOnly;
    }

    /**
     * canBeLocalOnly
     * 
     * @return canBeLocalOnly property
     */
    public boolean canBeLocalOnly() {

        return canBeLocalOnly;
    }

    /**
     * canBeLocalOnly
     * 
     * @param cblo canBeLocalOnly property
     */
    public void canBeLocalOnly(boolean cblo) {

        this.canBeLocalOnly = cblo;
    }

}
