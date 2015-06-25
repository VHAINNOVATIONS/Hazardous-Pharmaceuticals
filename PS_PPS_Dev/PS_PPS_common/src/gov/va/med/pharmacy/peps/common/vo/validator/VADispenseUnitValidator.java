/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * validator for va dispense unit
 */
public class VADispenseUnitValidator extends AbstractValidator<DispenseUnitVo> {

    /**
     * validates VA Dispense Unit Vo
     * 
     * @param target the VaDispenseUnitVo
     * @param errors the errors collection
     */
    public void validate(DispenseUnitVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DISPENSE_UNIT);

            return;
        }

        rejectIfNullOrEmpty(target.getId(), errors, FieldKey.DISPENSE_UNIT);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.DISPENSE_UNIT);
    }
}
