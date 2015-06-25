/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates product dispense unit per order unit
 */
public class ProductDispenseUnitPerOrderUnitValidator extends AbstractValidator<DataField<Double>> {

    private static final int FOUR = 4; 
    private static final double MAXRANGE = 99999.9999;
    
    /**
     * validate the dispense unti per order unit
     * 
     * @param target the Product dispense unit per Order Unit Linked DF
     * @param errors the errors collection
     * 
     */
    public void validate(DataField<Double> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

            return;
        }
        
        if (isNull(target.getValue())) {
            rejectIfNull(target, errors, FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

            return;
        }

        rejectIfMoreDecimals(target.getValue(), FOUR, errors, FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);
        rejectIfOutsideRangeInclusive(target.getValue(), 1, MAXRANGE, errors, FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);
    }
}
