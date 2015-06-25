/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;


/**
 * Validates Order Units
 */
public class OrderUnitValidator extends AbstractValidator<OrderUnitVo> {

    /**
     * The data field and its value/id cannot be null or empty
     * 
     * @param target OrderUnitVo
     * @param errors object to add errors to
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(OrderUnitVo target, Errors errors) {

        if (isNull(target)) {
            return;
        }
      
        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.VALUE);

        if (target.getValue() != null) {
            this.rejectIfLengthOutsideRangeInclusive(target.getValue(), NUM_2, NUM_3, errors, FieldKey.VALUE);
        }

        if (!isNullOrEmpty(target.getExpansion())) {
            this.rejectIfLengthOutsideRangeInclusive(target.getExpansion(), NUM_3, NUM_30, errors, FieldKey.EXPANSION);
        }
    }
}
