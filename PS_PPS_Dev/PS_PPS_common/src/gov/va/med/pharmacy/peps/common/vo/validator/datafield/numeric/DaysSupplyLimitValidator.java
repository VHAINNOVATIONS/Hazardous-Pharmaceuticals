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
 * Days supply limit, if selected, must be between 30 and 90, inclusive.
 */
public class DaysSupplyLimitValidator extends AbstractValidator<DataField<Long>> {

    /**
     * Days supply limit, if selected, must be between 30 and 90, inclusive.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(DataField<Long> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);

            return;
        }

        if (target.isSelected()) {
            rejectIfOutsideRangeInclusive(target.getValue(), PPSConstants.I30, 
                                          PPSConstants.NINETY, errors, FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);
        }
    }
}
