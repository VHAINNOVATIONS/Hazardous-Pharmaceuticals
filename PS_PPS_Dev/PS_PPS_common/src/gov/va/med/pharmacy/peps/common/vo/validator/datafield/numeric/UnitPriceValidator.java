/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric;


import java.text.DecimalFormat;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Validate unit price VA data field
 */
public class UnitPriceValidator extends AbstractValidator<DataField<Double>> {

    /**
     * The data field and its value cannot be null
     * 
     * @param target DataField to check for the UnitPriceValidator.
     * @param errors object to add errors to
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(DataField<Double> target, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.UNIT_PRICE);

            return;
        }

        if (isNull(target.getValue())) {
            rejectIfNull(target.getValue(), errors, FieldKey.UNIT_PRICE);

            return;
        }
        
        rejectIfOutsideRangeInclusive(target.getValue(), 0, PPSConstants.I999999, errors, FieldKey.UNIT_PRICE);
        
        // maximum 2 decimal digits allowed
        DecimalFormat formatter = new DecimalFormat("#0.00");
        Double other = new Double(formatter.format(target.getValue()));
        boolean matchFound1 = other.equals(target.getValue());

        if (!matchFound1) {
            errors.addError(ErrorKey.COMMON_INCORRECT_DECIMAL_DIGITS, new Object[] {FieldKey.UNIT_PRICE, 2});
        }
        
    }
}
