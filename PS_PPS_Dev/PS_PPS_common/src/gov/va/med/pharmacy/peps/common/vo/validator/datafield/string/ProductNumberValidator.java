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
 * Validates Product Number
 */
public class ProductNumberValidator extends AbstractValidator<DataField<String>> {

    /**
     * Data Range: {min: 0; max: 40}
     * <p>
     * Rule 1: This field is optional for an NDC Item.
     * 
     * @param target Product Number VA data field
     * @param errors the errors collection
     */
    public void validate(DataField<String> target, Errors errors) {
        if (!isNull(target) && !isNullOrEmpty(target.getValue())) {
            rejectIfLengthOutsideRangeInclusive(target.getValue(), 0, PPSConstants.I40, errors, FieldKey.PRODUCT_NUMBER);
        }
    }
}
