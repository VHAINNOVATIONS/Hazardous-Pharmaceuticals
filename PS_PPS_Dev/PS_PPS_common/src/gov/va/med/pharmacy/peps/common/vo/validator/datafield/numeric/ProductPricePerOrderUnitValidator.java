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
 * validates product price per order unit
 */
public class ProductPricePerOrderUnitValidator extends AbstractValidator<DataField<Double>> {

    /**
     * validate
     * 
     * @param target the Product Price per Order Unit VA DF
     * @param errors the errors collection
     * 
     */
    public void validate(DataField<Double> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT);

            return;
        }

        rejectIfMoreDecimals(target.getValue(), 2, errors, FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT);
        rejectIfOutsideRangeInclusive(target.getValue(), 0, PPSConstants.I9999, errors, 
            FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT);
    }
}
