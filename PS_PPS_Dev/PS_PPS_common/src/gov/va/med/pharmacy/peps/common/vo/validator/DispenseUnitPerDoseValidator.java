/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Validates dispense units per dose
 */
public class DispenseUnitPerDoseValidator extends AbstractValidator<DispenseUnitPerDoseVo> {

    private static final double MAX_VAL = 99999.9999;

    /**
     * The dispense unit per dose must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(DispenseUnitPerDoseVo target, Errors errors) {

        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DISPENSE_UNIT_PER_DOSE);

            return;
        }

        rejectIfNullOrEmpty(target.getStrDispenseUnitPerDose(), errors, FieldKey.STR_DISPENSE_UNIT_PER_DOSE);

        if (!isNullOrEmpty(target.getStrDispenseUnitPerDose())) {
            rejectIfNotNumeric(target.getStrDispenseUnitPerDose(), errors, FieldKey.STR_DISPENSE_UNIT_PER_DOSE,
                FieldKey.DISPENSE_UNIT_PER_DOSE);

            if (isNumeric(target.getStrDispenseUnitPerDose())) {
                double strDispenseUnitPerDose = Double.parseDouble(target.getStrDispenseUnitPerDose());

                rejectIfMoreDecimals(strDispenseUnitPerDose, PPSConstants.I4, errors, FieldKey.STR_DISPENSE_UNIT_PER_DOSE,
                    FieldKey.NATIONAL_POSSIBLE_DOSAGES);
                rejectIfOutsideRangeInclusive(strDispenseUnitPerDose, 1, MAX_VAL, errors, FieldKey.DISPENSE_UNIT_PER_DOSE,
                    FieldKey.STR_DISPENSE_UNIT_PER_DOSE);
            }
        }
    }
}
