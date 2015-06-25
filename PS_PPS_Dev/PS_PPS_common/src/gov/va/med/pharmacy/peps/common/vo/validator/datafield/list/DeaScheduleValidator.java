/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.list;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * DeaScheduleValidator.
 */
public class DeaScheduleValidator extends AbstractValidator<ListDataField<String>> {

    /**
     * Simply validates the DEA Schedule is not null or is selected to a null value
     * 
     * @param target Dea Schedule
     * @param errors Errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(ListDataField<String> target, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.DEA_SCHEDULE);

            return;
        }

        boolean valid = true;

        // if the target is selected for the DeaScheduleValidator
        if (!target.isSelected()) {
            valid = false;
        }

        for (String selection : target.getValue()) {
            if (isNullOrEmpty(selection)) {
                valid = false;
            }
        }

        if (!valid) {
            errors.addError(FieldKey.DEA_SCHEDULE, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.DEA_SCHEDULE});
        }
    }

}
