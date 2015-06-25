/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;


/**
 * validates SpecialHandlingVo for a Product
 */
public class SpecialHandlingForProductValidator extends AbstractValidator<ListDataField<SpecialHandlingVo>> {

    /**
     * validate
     * 
     * @param target the special handlings
     * @param errors the errors collection
     * 
     */
    public void validate(ListDataField<SpecialHandlingVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {

            return;
        }

        // up to five values can be selected
        if (target.getValue().size() > NUM_5) {
            errors.addError(FieldKey.SPECIAL_HANDLINGS, ErrorKey.COMMON_NEED_UPTO_FIVE_VALUES,
                new Object[] {FieldKey.SPECIAL_HANDLINGS});

            return;
        }

    }// end validate
}
