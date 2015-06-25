/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;


/**
 * validator for DEA Special Handling
 */
public class SpecialHandlingValidator extends AbstractValidator<SpecialHandlingVo> {

    /**
     * 
     * this is a mandatory field and the length will be between 1 to 6 characters
     * 
     * @param target DEA Special Handling
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object, gov.va.med.pharmacy.
     *      peps.common.vo.validator.Errors)
     */
    public void validate(SpecialHandlingVo target, Errors errors) {

        if (target == null) {
            rejectIfNull(target, errors, FieldKey.SPECIAL_HANDLING);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.VALUE);
        rejectIfNullOrEmpty(target.getDescription(), errors, FieldKey.DESCRIPTION);

        if (target.getDescription() != null) {
            this.rejectIfLengthOutsideRangeInclusive(target.getDescription(), NUM_3, NUM_50, errors, FieldKey.DESCRIPTION);
        }
        
        if (target.getValue() != null) {

            // has to be uppercase            
            if (target.getValue().trim().length() == 1 && target.getValue().trim().matches("[A-Z]")) {
                return;
            } else {
                errors.addError(ErrorKey.COMMON_NEED_ONLY_ONE_UPPERCASE_CHARACTER, new Object[] {FieldKey.VALUE});
            }

        }
    }
}
