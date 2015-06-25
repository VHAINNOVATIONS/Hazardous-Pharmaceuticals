/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates the Unit Dose Schedule field
 */
public class UnitDoseScheduleValidator extends AbstractValidator<DataField<String>> {

    /**
     * 
     * this is an optional field for product item
     * Unit Dose Schedule should be between 2-70 characters
     * @param target Unit Dose Schedule
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object, gov.va.med.pharmacy.
     * peps.common.vo.validator.Errors)
     */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            return; //value is optional
        }// end if

        if (isNull(target.getValue())) {

            return; //value is optional
        }

        // the length is between 2 to 70 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), 2, PPSConstants.I70, errors, FieldKey.UNIT_DOSE_SCHEDULE);
    }
}
