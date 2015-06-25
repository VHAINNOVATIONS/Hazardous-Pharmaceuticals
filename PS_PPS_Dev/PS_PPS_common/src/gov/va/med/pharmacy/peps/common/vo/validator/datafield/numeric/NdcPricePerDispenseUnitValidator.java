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
 * Validate NDC price per dispense unit VA data field
 */
public class NdcPricePerDispenseUnitValidator extends AbstractValidator<DataField<Double>> {

    /**
     * NdcPricePerDispenseUnitValidator. The data field and its value cannot be null
     * 
     * @param target DataField
     * @param errors object to add errors to
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(DataField<Double> target, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);

            return;
        }

        if (isNull(target.getValue())) {
            rejectIfNull(target.getValue(), errors, FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);

            return;
        }
        
        rejectIfOutsideRangeInclusive(target.getValue(), 0, PPSConstants.I999999, errors, 
            FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);
        
        DecimalFormat formatter = new DecimalFormat("#0.0000");       
        Double other = new Double(formatter.format(target.getValue()));
        boolean matchFound1 = other.equals(target.getValue());
        
        if (!matchFound1) {
            errors
                .addError(ErrorKey.COMMON_INCORRECT_DECIMAL_DIGITS, new Object[] {FieldKey.NDC_PRICE_PER_DISPENSE_UNIT, 
                    PPSConstants.I4});
        }  

    }
}
