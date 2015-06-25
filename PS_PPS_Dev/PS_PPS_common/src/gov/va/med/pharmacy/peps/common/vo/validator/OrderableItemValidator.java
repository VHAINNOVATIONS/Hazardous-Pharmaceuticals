/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import org.springframework.util.CollectionUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField;


/**
 * Validate an Orderable Item
 */
public class OrderableItemValidator extends AbstractValidator<OrderableItemVo> {

    /**
     * validate
     * 
     * @param target OrderableItem
     * @param environment is the local/national environment
     * @param user is the user
     * @param pageNum is the page number in the add product wizard
     * @param errors Errors
     */
    @Override
    public void validate(OrderableItemVo target, UserVo user, Environment environment, int pageNum, Errors errors) {

        switch (pageNum) {
            case NUM_1:
                validatePage1(target, user, environment, errors);
                break;
            case NUM_2:
                validatePage2(target, user, environment, errors);
                break;
            case NUM_3:
                validatePage3(target, user, environment, errors);
                break;
            default:
                validate(target, user, environment, errors);
                break;
        }

    }

    /**
     * validate
     * 
     * @param target OrderableItem
     * @param environment is the local/national environment
     * @param user is the user
     * @param errors Errors
     */
    @Override
    public void validate(OrderableItemVo target, UserVo user, Environment environment, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.ORDERABLE_ITEM);

            return;
        }

        // item status can not be null
        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);

        if (target.isLocal() && target.getOrderableItemParent() == null) {
            rejectIfNull(null, errors, FieldKey.ORDERABLE_ITEM);
        }

        if (target.isLocal() && target.getOrderableItemParent() != null) {
            if (target.getOrderableItemParent().isLocal()) {
                errors.addError(FieldKey.PARENT_NAME, ErrorKey.ORDERABLE_ITEM_PARENT_CANNOT_BE_LOI,
                    new Object[] { FieldKey.PARENT_NAME });
            }
        }

        // oi schedule type cannot be null at Local (it is a local-only field, so it isn't required at National)
        if (environment.isLocal()) {
            rejectIfNull(target.getOiScheduleType(), errors, FieldKey.OI_SCHEDULE_TYPE);
        }

        // if user creates LOI with parent in wizard, and then changes it to NOI,
        // must remove the OI parent - also previous OI parent to satisfy default rules
        if (target.isNational() && target.getOrderableItemParent() != null) {
            target.setOrderableItemParent(null);
            target.setPreviousOrderableItemParent(null);
        }

        

        DataField<String> dayDoseLimit = target.getVaDataFields().getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);

        if (dayDoseLimit != null) {
            FieldKey.DAY_ND_OR_DOSE_NL_LIMIT.newValidatorInstance().validate(dayDoseLimit, errors);
        }

        if (target.isLocal()) {
            if (target.getOrderableItemParent() == null || target.getOrderableItemParent().getId() == null) {
                errors.addError(FieldKey.PARENT_NAME, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.PARENT_NAME });
            }
        }

        MultitextDataField<String> oiSynonyms = target.getVaDataFields().getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM);

        if (oiSynonyms != null) {
            FieldKey.ORDERABLE_ITEM_SYNONYM.newValidatorInstance().validate(oiSynonyms, errors);
        }

        validatePage1(target, user, environment, errors);
        validatePage2(target, user, environment, errors);
        validatePage3(target, user, environment, errors);
        validateAdditionalFields(target, user, environment, errors);

    }

    /**
     * Generic validation method
     * 
     * @param target the orderable item
     * @param errors the errors collection
     */
    @Override
    public void validate(OrderableItemVo target, Errors errors) {

        // not used

    }

    /**
     * validates the page 1 of the Add OI Wizard
     * 
     * @param target the orderable item
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     * @return returns the errors
     */
    public Errors validatePage1(OrderableItemVo target, UserVo user, Environment environment, Errors errors) {

        final int min = 3;
        final int max = 40;
        final int oiMax = 95;

        // get the vadf for the product
//        DataFields<DataField> vadfs = target.getVaDataFields();

        if (target.getDosageForm() == null || isNullOrEmpty(target.getDosageForm().getDosageFormName())) {
            errors.addError(FieldKey.DOSAGE_FORM, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.DOSAGE_FORM });
        }

        // Test length of OI Name
        if (target.getOiName() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getOiName(), min, oiMax, errors, FieldKey.OI_NAME);
        }

        if (isNullOrEmpty(target.getVistaOiName())) {
            errors.addError(FieldKey.VISTA_OI_NAME, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.VISTA_OI_NAME });
        } else {

            // Check to make sure the length is correct.
            this.rejectIfLengthOutsideRangeInclusive(target.getVistaOiName(), min, max, errors, FieldKey.VISTA_OI_NAME);

        }

        if (isNullOrEmpty(target.getVistaOiName())) {
            errors.addError(FieldKey.OI_NAME, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.OI_NAME });
        } else {
            if (target.getDosageForm() == null) {
                if (target.getVistaOiName().length() > max) {
                    target.setVistaOiName(target.getVistaOiName().substring(0, max));
                } else {
                    target.setVistaOiName(target.getVistaOiName());
                }
            } else {

                // If the OIName is empty then default it to the VistaName + " " + DosageFormName 
                if (isNullOrEmpty(target.getOiName())) {
                    String oiName = target.getVistaOiName() + " " + target.getDosageForm().getDosageFormName();

                    if (oiName.length() > oiMax) {
                        target.setOiName(oiName.substring(0, oiMax));
                    } else {
                        target.setOiName(oiName);
                    }
                }
            }
        }

        if (CollectionUtils.isEmpty(target.getCategories())) {
            errors
                .addError(FieldKey.CATEGORIES, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.CATEGORIES });
        }

        // OI Type is a mandatory field
        if (target.getOrderableItemType() == null) {
            errors
                .addError(FieldKey.ORDERABLE_ITEM_TYPE, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.ORDERABLE_ITEM_TYPE });
        }

        return errors;

    }

    /**
     * validates the page 2 of the Add OI Wizard
     * 
     * @param target the orderable item
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     */
    public void validatePage2(OrderableItemVo target, UserVo user, Environment environment, Errors errors) {

        // get the vadf for the product
//        DataFields<DataField> vadfs = target.getVaDataFields();

        // Schedule datafield
        FieldKey.ADMIN_SCHEDULES.newValidatorInstance().validate(target.getAdminSchedules(), errors);
    }

    /**
     * validates the page 3 of the Add OI Wizard
     * 
     * @param target the orderable item
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     */
    public void validatePage3(OrderableItemVo target, UserVo user, Environment environment, Errors errors) {

        final int max = 240;

        // get the vadf for the product
        DataFields<DataField> vadfs = target.getVaDataFields();

       
        
        
        // validation for patient instructions
        DataField<String> patientInstructions = vadfs.getDataField(FieldKey.PATIENT_INSTRUCTIONS);

        if ((patientInstructions != null) && (patientInstructions.getValue() != null)
            && (patientInstructions.getValue().trim().length() > 0)) {
            rejectIfLongerThanMax(patientInstructions.getValue(), max, errors, FieldKey.PATIENT_INSTRUCTIONS);
        }

        // validation for other language instructions
        DataField<String> otherLanguageInstructions = vadfs.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS);

        if ((otherLanguageInstructions != null) && (otherLanguageInstructions.getValue() != null)
            && (otherLanguageInstructions.getValue().trim().length() > 0)) {
            rejectIfLongerThanMax(otherLanguageInstructions.getValue(), max, errors, FieldKey.OTHER_LANGUAGE_INSTRUCTIONS);
        }

    }

    /**
     * validates additional OI fields
     * 
     * @param target the orderable item
     * @param user the user
     * @param environment local/national environment
     * @param errors the errors collection
     */
    private void validateAdditionalFields(OrderableItemVo target, UserVo user,
            Environment environment, Errors errors) {

        // Request Item Status cannot be empty
        rejectIfNull(target.getRequestItemStatus(), errors,
                     FieldKey.REQUEST_ITEM_STATUS);

        // Rejection Reason Text cannot be null or empty if Request Item Status is REJECTED
        if ((isNullOrEmpty(target.getRejectionReasonText()))
            && (target.getRequestItemStatus()
                    .equals(RequestItemStatus.REJECTED))) {
            rejectIfNull(target.getRejectionReasonText(), errors,
                         FieldKey.REJECTION_REASON_TEXT);

        }

        // High Alert
        if (!isNull(target.getHighAlert())) {
            rejectIfLengthOutsideRangeInclusive(target.getHighAlert(), PPSConstants.I1, PPSConstants.I120, errors,
                FieldKey.HIGH_ALERT);
        }

        // Special Instructions
        if (target.getSpecialInstructions() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getSpecialInstructions(), PPSConstants.I1, PPSConstants.I120, errors,
                FieldKey.SPECIAL_INSTRUCTIONS);
        }


    }

}
