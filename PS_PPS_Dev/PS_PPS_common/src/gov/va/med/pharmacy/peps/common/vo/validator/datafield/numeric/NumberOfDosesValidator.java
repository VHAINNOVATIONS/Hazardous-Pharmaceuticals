/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validator for Number of Doses
 */
public class NumberOfDosesValidator extends AbstractValidator<DataField<Long>> {

    /**
     * validate
     * 
     * @param target the Number of Doses VA DF
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(DataField<Long> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.NUMBER_OF_DOSES);

            return;
        }

        rejectIfOutsideRangeInclusive(target.getValue(), 1, PPSConstants.I999, errors, FieldKey.NUMBER_OF_DOSES);
    }
}
