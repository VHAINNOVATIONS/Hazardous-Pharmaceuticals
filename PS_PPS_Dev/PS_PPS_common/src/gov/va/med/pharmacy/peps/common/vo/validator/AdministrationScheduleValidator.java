/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.AdministrationTimesValidator;


/**
 * Validator for Administration schedule
 */
public class AdministrationScheduleValidator extends AbstractValidator<AdministrationScheduleVo> {
    private static final String OTHER = "other";

    /**
     * validates AdministrationScheduleVo
     * 
     * @param target the AdministrationScheduleVo
     * @param errors the errors collection
     */
    public void validate(AdministrationScheduleVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.ADMINISTRATION_SCHEDULE);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getPackagePrefix(), errors, FieldKey.PACKAGE_PREFIX);

        FieldKey.HOSPITAL_LOCATION_MULTIPLE.newValidatorInstance().validate(target.getHospitalLocationMultiple(), errors);

        FieldKey.WARD_MULTIPLE.newValidatorInstance().validate(target.getWardMultiple(), errors);

        FieldKey.FREQUENCY.newValidatorInstance().validate(target.getFrequency(), errors);

        rejectIfNullOrEmpty(target.getScheduleName(), errors, FieldKey.VALUE);

        if (target.getScheduleName() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getScheduleName(), 2, PPSConstants.I20, errors, FieldKey.VALUE);
            rejectIfTrue(OTHER.equalsIgnoreCase(target.getScheduleName()), ErrorKey.ADMIN_SCHEDULES_OTHER_NAME, errors,
                FieldKey.VALUE);
        }

        if (target.getOtherLanguageExpansion() != null && target.getOtherLanguageExpansion().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getOtherLanguageExpansion(), 2, PPSConstants.I50, errors,
                FieldKey.OTHER_LANGUAGE_EXPANSION);
        }

        if (target.getScheduleOutpatientExpansion() != null && target.getScheduleOutpatientExpansion().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getScheduleOutpatientExpansion(), 2, PPSConstants.I50, errors,
                FieldKey.SCHEDULE_OUTPATIENT_EXPANSION);
        }

        if (target.getPackagePrefix() != null && target.getPackagePrefix().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getPackagePrefix(), 1, PPSConstants.I4, errors, FieldKey.PACKAGE_PREFIX);
        }

        if (target.getStandardAdministrationTimes() != null && target.getStandardAdministrationTimes().trim().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getStandardAdministrationTimes(), 2, PPSConstants.I119, errors,
                FieldKey.STANDARD_ADMINISTRATION_TIMES);
            AdministrationTimesValidator administrationTimesValidator = new AdministrationTimesValidator();
            administrationTimesValidator.validate(target.getStandardAdministrationTimes(), errors,
                FieldKey.STANDARD_ADMINISTRATION_TIMES);
        }

        if (target.getStandardShifts() != null && target.getStandardShifts().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getStandardShifts(), 1, PPSConstants.I11, errors, 
                FieldKey.STANDARD_SHIFTS);

            if (target.getStandardShifts().length() < PPSConstants.I12) {
                rejectIfFalse(target.getStandardShifts().matches("[A-Za-z]([\\-][A-Za-z]){0,5}"), ErrorKey.SHIFT_FORMAT,
                    errors);
            }
        }
    }
}
