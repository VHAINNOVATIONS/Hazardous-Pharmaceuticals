/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.AtcChoice;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesAutoCreate;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;


/**
 * Validate a Product
 */
public class ProductValidator extends AbstractValidator<ProductVo> {

    private static final String BOTH = "BOTH";
    private static final String BOTH_MIXED = "Both";
    private static final String I_INP = "I-Inpatient";
    private static final String O_OUT = "O-Outpatient";
    
    /**
     * Validation for product
     * 
     * @param target Product
     * @param environment is the local/national environment
     * @param user is the user
     * @param pageNum is the page number in the add product wizard
     * @param errors Errors
     * 
     * 
     */
    @Override
    public void validate(ProductVo target, UserVo user, Environment environment, int pageNum, Errors errors) {

        validatePage1(target, user, environment, errors);

    }

    /**
     * validateMigration
     * @param target target
     * @param user user
     * @param environment environment
     * @param errors errors
     */
    public void validateMigration(ProductVo target, UserVo user, Environment environment, Errors errors) {

        Errors tempErrors = new Errors();
        validate(target, user, environment, tempErrors);

        // remove any errors due to VA PRINT NAME, CMOP ID and DISPENSE UNIT
        if (tempErrors.hasErrors()) {
            List<ValidationError> errorList = tempErrors.getErrors();

            for (ValidationError e : errorList) {
                if (e.getFieldKey() != null && (!(e.getFieldKey().equals(FieldKey.VA_PRINT_NAME)
                    || e.getFieldKey().equals(FieldKey.CMOP_ID) || e.getFieldKey().equals(FieldKey.DISPENSE_UNIT)))) {
                    errors.addError(e);
                }
            }
        }
    }

    /**
     * validate
     * @param target Product
     * @param environment is the local/national environment
     * @param user is the user
     * @param errors Errors
     */
    @Override
    public void validate(ProductVo target, UserVo user, Environment environment, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.PRODUCT);

            return;
        }

        // item status can not be empty
        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);

        // get the vadf for the product
//        DataFields<DataField> vadfs = target.getVaDataFields();

        // inactive products can not be marked for local use
        if (((ItemStatus.INACTIVE).equals(target.getItemStatus())) && (target.isLocalUse()) && (environment.isLocal())) {
            errors.addError(FieldKey.LOCAL_USE, ErrorKey.MARK_FOR_LOCAL_USE_INACTIVE_ITEM,
                new Object[] { FieldKey.LOCAL_USE });
        }

        validatePage1(target, user, environment, errors);

        validateAutoCreatePossibleDosageFields(target, user, environment, errors);

    }// end validator method

    /**
     * The default validate method is not used in the product validator. It is overriden with a blank method.
     * 
     * Use validate(ProductVo target, UserVo user, Environment environment, Errors errors)
     * 
     * @param target productVo
     * @param errors Errors to add to.
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(ProductVo target, Errors errors) {

    }

    /**
     * validates the page 1 of the Add Product Wizard for both Local and National Add
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     */
    public void validatePage1(ProductVo target, UserVo user, Environment environment, Errors errors) {

        // get the vadf for the product
        DataFields<DataField> vadfs = target.getVaDataFields();

        // product parent = orderable item
        if (target.getOrderableItem() == null || target.getOrderableItem().getId() == null) {

            // rejectIfNull(null, errors, FieldKey.ORDERABLE_ITEM);
            errors.addError(ErrorKey.ORDERABLE_ITEM_PARENT_EMPTY, new Object[] { FieldKey.ORDERABLE_ITEM });
        }

        // active ingredients
        FieldKey.ACTIVE_INGREDIENT.newValidatorInstance().validate(target.getActiveIngredients(), errors);

        // VA Product name
        FieldKey.VA_PRODUCT_NAME.newValidatorInstance().validate(target.getVaProductName(), errors);

        
        DataField<Date> proposedInativationDate =
            vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);
        
        if (target.getInactivationDate() == null 
            && (proposedInativationDate == null || proposedInativationDate.getValue() == null)) {

            // VA Dispense Unit
            FieldKey.DISPENSE_UNIT.newValidatorInstance().validate(target.getDispenseUnit(), errors);

            rejectIfNull(target.getVaPrintName(), errors, FieldKey.VA_PRINT_NAME);
            FieldKey.VA_PRINT_NAME.newValidatorInstance().validate(target.getVaPrintName(), errors);        

        }

        // Formulary Name
        FieldKey.NATIONAL_FORMULARY_NAME.newValidatorInstance().validate(target.getNationalFormularyName(), errors);

        // Formulary Alternative
        FieldKey.FORMULARY_ALTERNATIVE.newValidatorInstance().validate(vadfs.getDataField(FieldKey.FORMULARY_ALTERNATIVE),
            errors);

        // Generic Name
        FieldKey.GENERIC_NAME.newValidatorInstance().validate(target.getGenericName(), errors);

        // Product Strength
        FieldKey.PRODUCT_STRENGTH.newValidatorInstance().validate(target.getProductStrength(), errors);

        // CMOP ID
        if (!isNullOrEmptyString(target.getCmopId())) {
            FieldKey.CMOP_ID.newValidatorInstance().validate(target.getCmopId(), errors);
        }
    
        // CS Federal Schedule
        FieldKey.CS_FED_SCHEDULE.newValidatorInstance().validate(target.getCsFedSchedule(), errors);

        ListDataField<SpecialHandlingVo> specialHandlings = vadfs.getDataField(FieldKey.SPECIAL_HANDLINGS);
        FieldKey.SPECIAL_HANDLINGS.newValidatorInstance().validate(specialHandlings, errors);

        // Single/Multi Source Product
        FieldKey.SINGLE_MULTISOURCE_PRODUCT.newValidatorInstance().validate(target.getSingleMultiSourceProduct(), errors);

        // TallMan

        if (!isNullOrEmptyString(target.getTallManLettering())) {
            FieldKey.TALL_MAN_LETTERING.newValidatorInstance().validate(target.getTallManLettering(), errors);
        }

        // DEA Schedule
        ListDataField<String> deaSchedule = vadfs.getDataField(FieldKey.DEA_SCHEDULE);
        FieldKey.DEA_SCHEDULE.newValidatorInstance().validate(deaSchedule, errors);

        // NCPDP_DISPENSE_UNIT
        ListDataField<String> ncpdpDispenseUnit = vadfs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT);
        FieldKey.NCPDP_DISPENSE_UNIT.newValidatorInstance().validate(ncpdpDispenseUnit, errors);

        // NCPDP Quantity Multiplier
        DataField<String> ncpdpQuantityMultiplier = vadfs.getDataField(FieldKey.NDCDP_QUANTITY_MULTIPLIER);
        FieldKey.NDCDP_QUANTITY_MULTIPLIER.newValidatorInstance().validate(ncpdpQuantityMultiplier, errors);

        // Drug Class
        FieldKey.DRUG_CLASSES.newValidatorInstance().validate(target.getDrugClasses(), errors);

        // synonym
        // if synonym is added the required fields should be filled
        // Previously a local-only validation.  Now also validating for National 
        // due to inclusion in PPS Migration requirement.
        if ((target.getSynonyms() != null) && (!target.getSynonyms().isEmpty())) {
            FieldKey.SYNONYMS.newValidatorInstance().validate(target.getSynonyms(), errors);
        }

        FieldKey.MONITOR_ROUTINE.newValidatorInstance().validate(
            target.getVaDataFields().getDataField(FieldKey.MONITOR_ROUTINE), errors);

        // Number of doses
        // numeric and value between 0-999
        DataField<Long> numberOfDoses = target.getVaDataFields().getDataField(FieldKey.NUMBER_OF_DOSES);

        if ((numberOfDoses != null) && (numberOfDoses.getValue() != null) && (numberOfDoses.getValue().longValue() != 0)) {
            FieldKey.NUMBER_OF_DOSES.newValidatorInstance().validate(numberOfDoses, errors);
        }

        validateDispenseDataFields(target, user, environment, errors);
        validateDispenseDataFields2(target, user, environment, errors);
        validateAdditionalDispenseDataFields(target, user, environment, errors);
        validateAdditionalFields(target, user, environment, errors);
        validateMultiDataFields(target, user, environment, errors);
        validateMoreAdditionalFields(target, user, environment, errors);

        if (environment.isNational()) {

            // GCN Sequence Number
            if (!isNullOrEmptyString(target.getGcnSequenceNumber())) {
                FieldKey.GCN_SEQUENCE_NUMBER.newValidatorInstance().validate(target.getGcnSequenceNumber(), errors);
            }
        }

        // validate local only fields for Local environment only
        if (environment.isLocal()) {

            // Application Package Use and Local Use
            // get the vadf application package use
            ListDataField<String> appPkgUse = vadfs.getDataField(FieldKey.APPLICATION_PACKAGE_USE);

            // Application Pkg use and Local Use
            // only Active items can be marked for local use
            // if local use is selected, then application package use must have value
            if (target.isLocalUse() && (appPkgUse != null) && (!appPkgUse.isSelected())) {

                // errors.addError(ErrorKey.MARK_FOR_LOCAL_USE_APPLICATION_PACKAGE_USE,
                errors.addError(FieldKey.APPLICATION_PACKAGE_USE, ErrorKey.MARK_FOR_LOCAL_USE_APPLICATION_PACKAGE_USE,
                    new Object[] { FieldKey.APPLICATION_PACKAGE_USE });
            }

            // local print name mandatory for local
            // Validate Local Special Handling
            if (isNullOrEmpty(target.getLocalPrintName())) {
                target.setLocalPrintName(target.getVaPrintName());
            }

            FieldKey.LOCAL_PRINT_NAME.newValidatorInstance().validate(target.getLocalPrintName(), errors);
            DataField<String> localSpecialHandling = vadfs.getDataField(FieldKey.LOCAL_SPECIAL_HANDLING);
            FieldKey.LOCAL_SPECIAL_HANDLING.newValidatorInstance().validate(localSpecialHandling, errors);

            validateFormularyFields(target, user, environment, errors);
            validateAtcDataFields(target, user, environment, errors);


        }// end if local


    }

    /**
     * validates formulary fields
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateFormularyFields(ProductVo target, UserVo user, Environment environment, Errors errors) {

        if ((environment.isNational())
            && ((target.isNewInstance()) || (target.getRequestItemStatus().equals(RequestItemStatus.APPROVED)))) {
            FieldKey.NATIONAL_FORMULARY_NAME.newValidatorInstance().validate(target.getNationalFormularyName(), errors);

            // FieldKey.FORMULARY.newValidatorInstance().validate(formulary, errors);

            // National Formulary Name

//        } else {
//
//            // for local environment if national formulary name is not empty validate the length
//            if (!isNullOrEmptyString(target.getNationalFormularyName())) {
//                FieldKey.NATIONAL_FORMULARY_NAME.newValidatorInstance().validate(target.getNationalFormularyName(), errors);
//            }
        }

    }

    /**
     * validates dispensing data fields
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateDispenseDataFields(ProductVo target, UserVo user, Environment environment, Errors errors) {

        // get the vadf for the product
        DataFields<DataField> vadfs = target.getVaDataFields();

        // product price per order unit
        // Previously a local-only validation.  Now also validating for National 
        // due to inclusion in PPS Migration requirement.
        DataField<Double> productPricePerOrderUnit = vadfs.getDataField(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT);

        if ((productPricePerOrderUnit != null) && (productPricePerOrderUnit.getValue() != null)
            && (productPricePerOrderUnit.getValue().doubleValue() != 0)) {
            FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT.newValidatorInstance().validate(productPricePerOrderUnit, errors);
        }

        // product price per dispense unit
        // Previously a local-only validation.  Now also validating for National 
        // due to inclusion in PPS Migration requirement.
        DataField<Double> productPricePerDispenseUnit = vadfs.getDataField(FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT);

        if ((productPricePerDispenseUnit != null) && (productPricePerDispenseUnit.getValue() != null)
            && (productPricePerDispenseUnit.getValue().doubleValue() != 0)) {
            FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT.newValidatorInstance().validate(productPricePerDispenseUnit, errors);
        }

        DataField<Double> productDispenseUnitsPerOrderUnit =
            vadfs.getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

        // product dispense unit per order unit
        if (productDispenseUnitsPerOrderUnit != null) {
            FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT.newValidatorInstance().validate(productDispenseUnitsPerOrderUnit,
                errors);
        }

        // Dispense days supply limit
        DataField<Long> dispenseDaysSupplyLimit = vadfs.getDataField(FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);

        if ((dispenseDaysSupplyLimit != null) && (dispenseDaysSupplyLimit.getValue() != null)
            && (dispenseDaysSupplyLimit.getValue().longValue() != 0)) {
            FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT.newValidatorInstance().validate(dispenseDaysSupplyLimit, errors);
        }

        // Dispense Limit For Order
        DataField<String> dispenseLimitForOrder = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_ORDER);

        if ((dispenseLimitForOrder != null) && (!isNullOrEmptyString(dispenseLimitForOrder.getValue()))) {
            FieldKey.DISPENSE_LIMIT_FOR_ORDER.newValidatorInstance().validate(dispenseLimitForOrder, errors);
        }

        // Dispense Limit Schedule
        DataField<String> dispenseLimitSchedule = vadfs.getDataField(FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE);

        if ((dispenseLimitSchedule != null) && (!isNullOrEmptyString(dispenseLimitSchedule.getValue()))) {
            FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE.newValidatorInstance().validate(dispenseLimitSchedule, errors);
        }

        // max dispense limit
        DataField<String> maxDispenseLimit = vadfs.getDataField(FieldKey.MAX_DISPENSE_LIMIT);

        if ((maxDispenseLimit != null) && (!isNullOrEmptyString(maxDispenseLimit.getValue()))) {
            FieldKey.MAX_DISPENSE_LIMIT.newValidatorInstance().validate(maxDispenseLimit, errors);
        }


    }

    
    /**
     * validates dispensing data fields
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateDispenseDataFields2(ProductVo target, UserVo user, Environment environment, Errors errors) {
        
        // get the vadf for the product
        DataFields<DataField> vadfs = target.getVaDataFields();
        
        // Dispense Override
        DataField<String> dispenseOverride = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE);

        if ((dispenseOverride != null) && (!isNullOrEmptyString(dispenseOverride.getValue()))) {
            FieldKey.DISPENSE_OVERRIDE.newValidatorInstance().validate(dispenseOverride, errors);
        }

        // Dispense Override Reason
        DataField<String> dispenseOverrideReason = vadfs.getDataField(FieldKey.DISPENSE_OVERRIDE_REASON);

        if ((dispenseOverrideReason != null) && (!isNullOrEmptyString(dispenseOverrideReason.getValue()))) {
            FieldKey.DISPENSE_OVERRIDE_REASON.newValidatorInstance().validate(dispenseOverrideReason, errors);
        }

        // Dispense Override Reason enterer
        DataField<String> dispenseOverrideReasonEnterer = vadfs.getDataField(FieldKey.OVERRIDE_REASON_ENTERER);

        if ((dispenseOverrideReasonEnterer != null) && (!isNullOrEmptyString(dispenseOverrideReasonEnterer.getValue()))) {
            FieldKey.OVERRIDE_REASON_ENTERER.newValidatorInstance().validate(dispenseOverrideReasonEnterer, errors);
        }

        // total dispense quantity
        DataField<String> totalDispenseQty = vadfs.getDataField(FieldKey.TOTAL_DISPENSE_QUANTITY);

        if ((totalDispenseQty != null) && (!isNullOrEmptyString(totalDispenseQty.getValue()))) {
            FieldKey.TOTAL_DISPENSE_QUANTITY.newValidatorInstance().validate(totalDispenseQty, errors);
        }

        // Dispense Quantity Override
        DataField<String> dispenseQuantityOverride = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE);

        if ((dispenseQuantityOverride != null) && (!isNullOrEmptyString(dispenseQuantityOverride.getValue()))) {
            FieldKey.DISPENSE_QUANTITY_OVERRIDE.newValidatorInstance().validate(dispenseQuantityOverride, errors);
        }

        // Dispense Quantity Override Reason
        DataField<String> dispenseQuantityOverrideReason = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON);

        if ((dispenseQuantityOverrideReason != null) && (!isNullOrEmptyString(dispenseQuantityOverrideReason.getValue()))) {
            FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON.newValidatorInstance()
                .validate(dispenseQuantityOverrideReason, errors);
        }

        // Dispense Quantity Limit
        GroupDataField dispenseQuantityLimit = vadfs.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT);

        if ((dispenseQuantityLimit != null)
            && (dispenseQuantityLimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE) != null)
            && (dispenseQuantityLimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE).getValue() != null)
            && (dispenseQuantityLimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME) != null)
            && (dispenseQuantityLimit.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME).getValue() != null)) {
            FieldKey.DISPENSE_QUANTITY_LIMIT.newValidatorInstance().validate(dispenseQuantityLimit, errors);
        }
    }

    
    /**
     * validates additional dispensing data fields
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateAdditionalDispenseDataFields(ProductVo target, UserVo user, Environment environment, Errors errors) {

        // get the vadf for the product
        DataFields<DataField> vadfs = target.getVaDataFields();

        // Federal Stock Number
        DataField<String> fsn = vadfs.getDataField(FieldKey.FSN);

        if ((fsn != null) && (!isNullOrEmptyString(fsn.getValue()))) {
            FieldKey.FSN.newValidatorInstance().validate(fsn, errors);
        }

        // Quantity Dispense Message
        DataField<String> qtyDispMsg = vadfs.getDataField(FieldKey.QUANTITY_DISPENSE_MESSAGE);

        if ((qtyDispMsg != null) && (!isNullOrEmptyString(qtyDispMsg.getValue()))) {
            FieldKey.QUANTITY_DISPENSE_MESSAGE.newValidatorInstance().validate(qtyDispMsg, errors);
        }

        // Quantity Dispense Message
        DataField<String> inpPharma = vadfs.getDataField(FieldKey.INPATIENT_PHARMACY_LOCATION);

        if ((inpPharma != null) && (!isNullOrEmptyString(inpPharma.getValue()))) {
            FieldKey.INPATIENT_PHARMACY_LOCATION.newValidatorInstance().validate(inpPharma, errors);
        }

        // Maximum Dose Per Day

        if ((target.getMaxDosePerDay() != null) && (target.getMaxDosePerDay().longValue() != 0)) {
            FieldKey.MAX_DOSE_PER_DAY.newValidatorInstance().validate(target.getMaxDosePerDay(), errors);
        }

//        if (environment.isLocal()) {

        // Action Profile Message
        // 1-120 characters long
        DataField<String> actionProfileMessage = vadfs.getDataField(FieldKey.ACTION_PROFILE_MESSAGE);

        if ((actionProfileMessage != null) && (!isNullOrEmptyString(actionProfileMessage.getValue()))) {
            FieldKey.ACTION_PROFILE_MESSAGE.newValidatorInstance().validate(actionProfileMessage, errors);
        }
    }

//    }

    /**
     * validates additional fields page 7 of local and page 8 of national
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateAdditionalFields(ProductVo target, UserVo user, Environment environment, Errors errors) {

        // get the vadf for the product
        DataFields<DataField> vadfs = target.getVaDataFields();

        // AR/WS Amis conversion Number
        // numeric and value between 0-9999
        DataField<Long> arWsAmisconversionNum = vadfs.getDataField(FieldKey.AR_WS_CONVERSION_NUMBER);

        // if Ar/Ws amis category is not null, then Ar/Ws Amis conversion number must not be empty
        ListDataField<String> arWsAmisCategory = vadfs.getDataField(FieldKey.AR_WS_AMIS_CATEGORY);

        if (((arWsAmisCategory != null) && (arWsAmisCategory.isSelected()))
            && ((arWsAmisconversionNum == null) || (arWsAmisconversionNum.getValue() == null))) {

//            || (arWsAmisconversionNum.getValue().longValue() == 0)) {
            errors.addError(FieldKey.AR_WS_CONVERSION_NUMBER, ErrorKey.AR_WS_CONVERSION_NUMBER_EMPTY,
                new Object[] { FieldKey.AR_WS_CONVERSION_NUMBER });
        }

        // AR/WS Amis conversion Number
        // numeric and value between 1-9999
        if ((arWsAmisconversionNum != null) && (arWsAmisconversionNum.getValue() != null)
            && (arWsAmisconversionNum.getValue().longValue() != 0)) {
            FieldKey.AR_WS_CONVERSION_NUMBER.newValidatorInstance().validate(arWsAmisconversionNum, errors);
        }

        // Inpatient Medication Expired Orders Max time
        DataField<String> ipMedExpOrdMaxTime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME);

        if ((ipMedExpOrdMaxTime != null) && !isNullOrEmptyString(ipMedExpOrdMaxTime.getValue())) {
            FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME.newValidatorInstance().validate(ipMedExpOrdMaxTime, errors);
        }

        // Inpatient Medication Expired Orders Min time
        DataField<String> ipMedExpOrdMinTime = vadfs.getDataField(FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME);

        if ((ipMedExpOrdMinTime != null) && !isNullOrEmptyString(ipMedExpOrdMinTime.getValue())) {
            FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME.newValidatorInstance().validate(ipMedExpOrdMinTime, errors);
        }

        // unit dose schedule
        DataField<String> unitDoseSchedule = vadfs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE);

        FieldKey.UNIT_DOSE_SCHEDULE.newValidatorInstance().validate(unitDoseSchedule, errors);

        // High Risk and Follow-up time
        // If High Risk is set to "Yes", Follow-up time can be "Yes" or "No".
        // If High Risk is set to "No", Follow-up time is "No"
        DataField<Boolean> highRisk = vadfs.getDataField(FieldKey.HIGH_RISK);
        DataField<Boolean> followUp = vadfs.getDataField(FieldKey.FOLLOW_UP_TIME);

//        // High Risk must be set
        if (highRisk == null) {
            errors.addError(ErrorKey.HIGH_RISK_EMPTY, new Object[] { FieldKey.HIGH_RISK });
        } else if (followUp == null) {
            errors.addError(ErrorKey.FOLLOWUP_TIME_EMPTY, new Object[] { FieldKey.FOLLOW_UP_TIME });
        } else {
            if (!highRisk.getValue().booleanValue() && followUp.getValue().booleanValue()) {
                errors.addError(ErrorKey.FOLLOWUP_TIME_HIGHRISK_NO);
            }
        }

        // High-risk follow-up time period
        DataField<String> highRiskFollowUpTimePeriod = vadfs.getDataField(FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD);

        if ((highRiskFollowUpTimePeriod != null) && (!isNullOrEmptyString(highRiskFollowUpTimePeriod.getValue()))) {
            FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD.newValidatorInstance().validate(highRiskFollowUpTimePeriod, errors);
        }

        if (environment.isLocal()) {

            // GCN Sequence Number
            if ((target.getGcnSequenceNumber() != null) && (target.getGcnSequenceNumber().trim().length() > 0)) {
                FieldKey.GCN_SEQUENCE_NUMBER.newValidatorInstance().validate(target.getGcnSequenceNumber(), errors);
            }

            // reorder level
            DataField<Long> reorderLevel = vadfs.getDataField(FieldKey.REORDER_LEVEL);

            if ((reorderLevel != null) && (reorderLevel.getValue() != null) && (reorderLevel.getValue().longValue() != 0)) {
                FieldKey.REORDER_LEVEL.newValidatorInstance().validate(reorderLevel, errors);
            }
        }
    }

    /**
     * validates ATC related information.
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateAtcDataFields(ProductVo target, UserVo user, Environment environment, Errors errors) {

        if (!environment.isLocal()) {
            return;
        }

        // ATC Mnemonic is an optional field unless ATC Choice is specified.
        // the value of ATC Mnemonics is between 1 to 15

        String atcMnemonic = target.getAtcMnemonic();

        if ((atcMnemonic != null) && (atcMnemonic.trim().length() > 0)) {

            // the length is between 1 to 15 characters, inclusive
            rejectIfLengthOutsideRangeInclusive(atcMnemonic, NUM_1, NUM_15, errors, FieldKey.ATC_MNEMONIC);

            // Must have at least one alpha character specified.
            rejectIfNotAtLeastOneAlpha(atcMnemonic, errors, FieldKey.ATC_MNEMONIC);
        }

        if (target.getAtcChoice() == null) {
            return;
        }

        if (atcMnemonic == null || atcMnemonic.trim().length() <= 0) {
            errors.addError(ErrorKey.ATC_MNEMONIC_FIELD_EMPTY);
        }

        if (AtcChoice.ONE_ATC_MODE.equals(target.getAtcChoice()) && target.getOneAtcCanister() != null) {

            // the value of atc canister should be between 0 and 800
            rejectIfOutsideRangeInclusive(target.getOneAtcCanister(), NUM_0, NUM_800, errors, FieldKey.ATC_CANISTERS,
                FieldKey.ATC_CANISTER);

        }

        if (AtcChoice.MULTIPLE_ATCS_MODE.equals(target.getAtcChoice())) {

            // Validate the ATC canister multiple.
            if ((target.getAtcCanisters() != null) && (!target.getAtcCanisters().isEmpty())) {
                FieldKey.ATC_CANISTERS.newValidatorInstance().validate(target.getAtcCanisters(), errors);
            }
        }

    }

    /**
     * validates MDF
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateMultiDataFields(ProductVo target, UserVo user, Environment environment, Errors errors) {

        // ndc by out patient site
        if ((target.getNdcByOutpatientSiteNdc() != null) && (!target.getNdcByOutpatientSiteNdc().isEmpty())
            && environment.isLocal()) {
            FieldKey.NDC_BY_OUTPATIENT_SITE_NDC.newValidatorInstance().validate(target.getNdcByOutpatientSiteNdc(), errors);
        }

        // possible dosages
        if ((target.getNationalPossibleDosages() != null) && (!target.getNationalPossibleDosages().isEmpty())) {
            FieldKey.NATIONAL_POSSIBLE_DOSAGES.newValidatorInstance().validate(target.getNationalPossibleDosages(), errors);
        }

        // local possible dosages

        if ((target.getLocalPossibleDosages() != null) && (!target.getLocalPossibleDosages().isEmpty())) {
            FieldKey.LOCAL_POSSIBLE_DOSAGES.newValidatorInstance().validate(target.getLocalPossibleDosages(), errors);
        }
    }

    /**
     * validates additional fields page 10 of local and page 9 of national
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateMoreAdditionalFields(ProductVo target, UserVo user, Environment environment, Errors errors) {

        // get the vadf for the product
        DataFields<DataField> vadfs = target.getVaDataFields();

        if (environment.isLocal()) {

            // current inventory
            DataField<Long> currentInventory = vadfs.getDataField(FieldKey.CURRENT_INVENTORY);

            if ((currentInventory != null) && (currentInventory.getValue() != null)
                && (currentInventory.getValue().longValue() != 0)) {
                FieldKey.CURRENT_INVENTORY.newValidatorInstance().validate(currentInventory, errors);
            }

            // normal amount to order
            DataField<Long> normalAmtToOrder = vadfs.getDataField(FieldKey.NORMAL_AMOUNT_TO_ORDER);

            if ((normalAmtToOrder != null) && (normalAmtToOrder.getValue() != null)
                && (normalAmtToOrder.getValue().longValue() != 0)) {
                FieldKey.NORMAL_AMOUNT_TO_ORDER.newValidatorInstance().validate(normalAmtToOrder, errors);
            }
        }

        // Message
        // this is an optional field the length is between 1-68 characters
        DataField<String> message = vadfs.getDataField(FieldKey.MESSAGE);

        if ((message != null) && (!isNullOrEmptyString(message.getValue()))) {
            FieldKey.MESSAGE.newValidatorInstance().validate(message, errors);
        }

        // monitor max days
        DataField<Long> monitorMaxDays = vadfs.getDataField(FieldKey.MONITOR_MAX_DAYS);

        if ((monitorMaxDays != null) && (monitorMaxDays.getValue() != null) && (monitorMaxDays.getValue().longValue() != 0)) {
            FieldKey.MONITOR_MAX_DAYS.newValidatorInstance().validate(monitorMaxDays, errors);
        }

        // Day(ND)or Dose(NL)Limit
        // if Day(ND)or Dose(NL)Limit is present the value will be
        // should be(1..99)D or (1..99)L
        DataField<String> dayDoseLimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);

        if (dayDoseLimit != null) {
            FieldKey.DAY_ND_OR_DOSE_NL_LIMIT.newValidatorInstance().validate(dayDoseLimit, errors);

        }// end Day(ND)or Dose(NL)Limit

        // Default Mail Service---optional field
        // length should be between 1 to 120 characters
        DataField<String> defaultMailService = vadfs.getDataField(FieldKey.DEFAULT_MAIL_SERVICE);

        if ((defaultMailService != null) && (!isNullOrEmptyString(defaultMailService.getValue()))) {
            FieldKey.DEFAULT_MAIL_SERVICE.newValidatorInstance().validate(defaultMailService, errors);
        }

        // VUID and Effective Date Time are mandatory when the request item status is approved
        if ((target.getRequestItemStatus().equals(RequestItemStatus.APPROVED))) {

            // VUID
            FieldKey.VUID.newValidatorInstance().validate(target.getVuid(), errors);

            // Effective Date Time
            FieldKey.EFFECTIVE_DATES.newValidatorInstance().validate(target.getEffectiveDates(), errors);
        }

        // Request Item Status cannot be null
        rejectIfNull(target.getRequestItemStatus(), errors, FieldKey.REQUEST_ITEM_STATUS);

        // Rejection Reason Text cannot be null or empty if Request Item Status is REJECTED
        if ((isNullOrEmpty(target.getRejectionReasonText()))
            && (target.getRequestItemStatus().equals(RequestItemStatus.REJECTED))) {
            rejectIfNull(target.getRejectionReasonText(), errors, FieldKey.REJECTION_REASON_TEXT);

        }

        // Migrated Dosage Form Name
        if (!isNullOrEmpty(target.getMigratedDosageFormName())) {
            FieldKey.MIGRATED_DOSAGE_FORM_NAME.newValidatorInstance().validate(target.getMigratedDosageFormName(), errors);
        }

        // FDA Med Guide
        if (!isNullOrEmpty(target.getFdaMedGuide())) {
            rejectIfLengthOutsideRangeInclusive(target.getFdaMedGuide(), NUM_1, NUM_100, errors, FieldKey.FDA_MED_GUIDE);
        }

    }

    /**
     * validates Auto-Create Possible Dosages related information.
     * 
     * @param target the product
     * @param user the user
     * @param environment local/national
     * @param errors the errors collection
     */
    private void validateAutoCreatePossibleDosageFields(ProductVo target, UserVo user,
        Environment environment, Errors errors) {

        Errors warnings = new Errors();

        if (target.getCreatePossibleDosage()) {

            // When Auto-Create Possible Dosage is true, Possible Dosages to Create and Product Package must be empty
            if (target.getPossibleDosagesAutoCreate() != null || target.getProductPackage() != null) {
                errors.addError(ErrorKey.POSS_DOSAGE_PROD_PACKAGE_MUST_BE_EMPTY);
            }

        } else { // Auto-Create Possible Dosage is false

            // When Auto-Create Possible Dosage is false, Possible Dosages to Create must have a value
            if (target.getPossibleDosagesAutoCreate() == null) {
                errors.addError(ErrorKey.POSSIBLE_DOSAGES_IS_EMPTY);
            }

            // No additional validation checks if the Category is SUPPLY
            if (target.getCategories() == null) {
                errors.addError(FieldKey.CATEGORIES, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.CATEGORIES });
            } else if (target.getCategories().contains(Category.SUPPLY)) {

                return;

            } else {

                // When Possible Dosages to Create has a value other than No Possible Dosages, Product Package must have a value
                if ((PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate())
                    || PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate()))
                    && target.getProductPackage() == null) {
                    errors.addError(ErrorKey.PRODUCT_PACKAGE_MUST_HAVE_VALUE);

                }

                // When Possible Dosages to Create is set to No Possible Dosages, Product Package must be empty
                if (PossibleDosagesAutoCreate.NO_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate())
                    && target.getProductPackage() != null) {
                    errors.addError(ErrorKey.PRODUCT_PACKAGE_MUST_BE_EMPTY);

                }

                // When Possible Dosages to Create has a value other than No Possible Dosages, or Product Package has a value, 
                // OI Dosages and Dispense Units Per Dose must exist.
                boolean itExists = true;
                itExists = oiDosagesDispenseUnitsExist(target, user, environment, errors);

                if (!itExists) {
                    if ((PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate())
                        || PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate()))
                        || target.getProductPackage() != null) {
                        errors.addError(ErrorKey.NO_DOSAGES_DEFINED);
                    }

                }

                // When Possible Dosages to Create has a value other than No Possible Dosages, the Product Unit must 
                // match an OI Dosage
                if ((PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate())
                    || PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate()))
                    && target.getProductUnit() != null) {
                    DosageFormUnitVo dosageMatch = null;
                    dosageMatch = productUnitOIDosageFormUnitMatch(target, user, environment, errors);

                    if (dosageMatch == null) {
                        warnings.addError(ErrorKey.PRODUCT_UNIT_OI_DOSAGEFORMUNIT_MISMATCH);
                    }

                }

                if ((PossibleDosagesAutoCreate.ONE_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate())
                    || PossibleDosagesAutoCreate.TWO_X_POSSIBLE_DOSAGES.equals(target.getPossibleDosagesAutoCreate()))
                    && target.getProductUnit() != null) {
                    if (possibleDosageProductPackageDosageFormRestrictions(target, user, environment, errors)) {
                        errors.addError(ErrorKey.PRODUCT_LESS_RESTRICTIVE_THAN_OI_DOSAGE);
                    }
                }

            } // end else 

        } // end else Auto-Create Possible Dosage is false

    } // end validateAutoCreatePossibleDosageFields

    /**
     * this method checks to see if the Product Package and Possible Dosages to Create are equal or more restrictive 
     * than the product unit under the OI dosage form.
     *
     * @param target ProductVo
     * @param user UserVo
     * @param environment Environment
     * @param warnings Errors
     * @return boolean
     */
    private boolean possibleDosageProductPackageDosageFormRestrictions(ProductVo target, UserVo user,
        Environment environment, Errors warnings) {

        Collection<DosageFormUnitVo> dosageFormUnits = target.getOrderableItem().getDosageForm().getDfUnits();

        Collection<DispenseUnitPerDoseVo> dispenseUnits =
            target.getOrderableItem().getDosageForm().getDfDispenseUnitsPerDose();

        String productUnit = String.valueOf(target.getProductUnit().getValue());

        boolean moreRestrictive = false;

        if (productUnit != null) {
            for (DosageFormUnitVo dosageFormUnit : dosageFormUnits) {
                if (dosageFormUnit.getDrugUnit().getValue().equals(productUnit)) {
                    int possDosagescount = dosageFormUnit.getPackages().size();
                    String possDosagesResult = "";

                    if (possDosagescount == 2) {
                        possDosagesResult = BOTH;
                    } else {

                        String possDosagesIO = String.valueOf(dosageFormUnit.getPackages());

                        if (I_INP.equals(possDosagesIO)) {
                            possDosagesResult = possDosagesIO;
                        }

                        if (O_OUT.equals(possDosagesIO)) {
                            possDosagesResult = possDosagesIO;
                        }
                    }

                    String prodPack = String.valueOf(target.getProductPackage());
                    String targetProdPack = "";
                    int targetProdPackCt = 0;

                    if ("INPATIENT".equals(prodPack)) {
                        targetProdPack = I_INP;
                        targetProdPackCt = 1;
                    }

                    if ("OUTPATIENT".equals(prodPack)) {
                        targetProdPack = O_OUT;
                        targetProdPackCt = 1;
                    }

                    if (BOTH.equals(prodPack)) {
                        targetProdPack = BOTH_MIXED;
                        targetProdPackCt = 2;
                    }

                    //compare the Product Package with the OI Dosage - Possible Dosages
                    if (BOTH.equals(possDosagesResult) || possDosagesResult.equals(targetProdPack)) {
                        moreRestrictive = false;
                    } else {
                        moreRestrictive = true;

                        return moreRestrictive;
                    }

                    //compare the dispense units
                  
                    String dispenseValue = "";
                    int dispenseCount = 0;
                    int targetCount = 0;
                   
                    String doseValue = String.valueOf(target.getPossibleDosagesAutoCreate());

                    if ("NO_POSSIBLE_DOSAGES".equals(doseValue)) {
                   
                        targetCount = 0;

                        return moreRestrictive;
                    } else if ("ONE_X_POSSIBLE_DOSAGES".equals(doseValue)) {
                   
                        targetCount = 1;
                    } else if ("TWO_X_POSSIBLE_DOSAGES".equals(doseValue)) {
                   
                        targetCount = 2;
                    }

                    int dispenseRows = 0;

                    for (DispenseUnitPerDoseVo dispenseUnit : dispenseUnits) {
                        dispenseRows++;

                        if (targetCount < dispenseRows) {

                            return moreRestrictive;
                        }

                        dispenseValue = String.valueOf(dispenseUnit.getPackages());
                        dispenseCount = dispenseUnit.getPackages().size();
                        int count = 0;

                        for (PossibleDosagesPackageVo pk : dispenseUnit.getPackages()) {
                            dispenseValue = String.valueOf(pk.getValue());
                            count++;
                        }

                        if (count == 2) {
                            dispenseValue = BOTH_MIXED;
                        }

                        if (targetProdPackCt <= dispenseCount) {

                            //check inpatient versus outpatient for each row
                            if (BOTH_MIXED.equals(dispenseValue)) {
                                moreRestrictive = false;
                            } else if (dispenseValue.equals(targetProdPack)) {
                                moreRestrictive = false;
                            } else {
                                moreRestrictive = true;

                                return moreRestrictive;
                            }
                        } else {
                            moreRestrictive = true;

                            return moreRestrictive;
                        }

                    } // end for

                    return moreRestrictive;
                } else {
                    moreRestrictive = true;
                }

            } // end for
        }

        return moreRestrictive;

    }

    /**
     * this method checks to see if the Product Unit field of the product matches with any of the dosage form
     * units of the associated OI's dosage form
     * 
     *
     * @param target ProductVo
     * @param user UserVo
     * @param environment Environment
     * @param warnings Errors
     * @return DosageFormUnitVo
     */
    private DosageFormUnitVo productUnitOIDosageFormUnitMatch(ProductVo target, UserVo user, Environment environment,
        Errors warnings) {

        Collection<DosageFormUnitVo> dosageFormUnits = target.getOrderableItem().getDosageForm().getDfUnits();

        String productUnit = String.valueOf(target.getProductUnit().getValue());

        DosageFormUnitVo dosageUnitMatch = null;

        if (dosageFormUnits.isEmpty() || productUnit.isEmpty()) {
            return null;
        }

        if (productUnit != null) {
            for (DosageFormUnitVo dosageFormUnit : dosageFormUnits) {
                if (dosageFormUnit.getDrugUnit().getValue().equals(productUnit)) {
                    dosageUnitMatch = dosageFormUnit;
                    break;
                }
            }
        }

        return dosageUnitMatch;

    }

    /**
     * this method verifies that the dosage form and dispense units per dose of the associated OI's dosage form
     * have values.
     * 
     * @param target ProductVo
     * @param user UserVo
     * @param environment Environment
     * @param errors Errors
     * @return boolean
     */
    private boolean oiDosagesDispenseUnitsExist(ProductVo target, UserVo user, Environment environment, Errors errors) {

        boolean exist = true;

        Collection<DosageFormUnitVo> dosageFormUnits = target.getOrderableItem().getDosageForm().getDfUnits();

        Collection<DispenseUnitPerDoseVo> dispenseUnits =
            target.getOrderableItem().getDosageForm().getDfDispenseUnitsPerDose();

        if (dosageFormUnits.isEmpty() || dispenseUnits.isEmpty()) {
            exist = false;
        }

        return exist;

    }

    /**
     * determines if the string is null or empty
     * 
     * @param str the String argument
     * @return boolean
     */
    private boolean isNullOrEmptyString(String str) {

        if ((str == null) || (str.trim().length() == 0)) {
            return true;
        }

        return false;
    }
}// end class
