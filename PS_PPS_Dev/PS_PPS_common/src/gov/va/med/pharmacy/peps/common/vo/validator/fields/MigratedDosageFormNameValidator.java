/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Migrated Dosage Form Name must be between 3 and 30 characters
 * 
 */
public class MigratedDosageFormNameValidator extends AbstractValidator<String> {

    /**
     * Migrated Dosage Form Name must be a length of 3 to 30.
     * 
     * @param value migratedDosageFormName
     * @param errors Errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(String value, Errors errors) {

        if (isNullOrEmpty(value)) {
            rejectIfNullOrEmpty(value, errors,
                                FieldKey.MIGRATED_DOSAGE_FORM_NAME);

            return;

        }

        rejectIfLengthOutsideRangeInclusive(value, PPSConstants.I3, PPSConstants.I30, errors,
                                            FieldKey.MIGRATED_DOSAGE_FORM_NAME);
    }
}
