/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NationalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;


/**
 * validates possible dosages
 */
public class PossibleDosagesValidator extends AbstractValidator<Collection<NationalPossibleDosagesVo>> {

    /**
     * validate
     * 
     * @param target the possible dosages
     * @param errors the errors collection
     * 
     */
    public void validate(Collection<NationalPossibleDosagesVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {
            rejectIfNullOrEmpty(target, errors, FieldKey.NATIONAL_POSSIBLE_DOSAGES);

            return;
        }

        for (NationalPossibleDosagesVo possibleDosage : target) {
            if (possibleDosage == null) {
                errors.addError(FieldKey.NATIONAL_POSSIBLE_DOSAGES, ErrorKey.COMMON_EMPTY,
                    new Object[] {FieldKey.NATIONAL_POSSIBLE_DOSAGES});

                return;
            }

            // Dispense Units per Dose, Dose, and Package are required data fields
            double dispenseUnitsPerDose = 0;

            if (possibleDosage.getPossibleDosagesDispenseUnitsPerDose() != null) {
                dispenseUnitsPerDose = possibleDosage.getPossibleDosagesDispenseUnitsPerDose().doubleValue();
            }

            Collection<PossibleDosagesPackageVo> packages = possibleDosage.getPossibleDosagePackage();

            // dispense units per dose, packages, and dose are required fields
            if ((possibleDosage.getPossibleDosagesDispenseUnitsPerDose() == null) || (packages == null)
                || packages.isEmpty()) {
                errors.addError(FieldKey.NATIONAL_POSSIBLE_DOSAGES, ErrorKey.NATIONAL_POSSIBLE_DOSAGES_EMPTY,
                    new Object[] {FieldKey.NATIONAL_POSSIBLE_DOSAGES});

                return;
            }// end if

            // only numbers with 4 decimal digits are used for dispense units per dose
            if (dispenseUnitsPerDose > 0) {
                rejectIfMoreDecimals(possibleDosage.getPossibleDosagesDispenseUnitsPerDose(), NUM_4, errors,
                    FieldKey.POSSIBLE_DOSAGES_DISPENSE_UNITS_PER_DOSE, FieldKey.NATIONAL_POSSIBLE_DOSAGES);
            }

            // dispense units per dose must be between 0 and 999999999
            rejectIfOutsideRangeInclusive(dispenseUnitsPerDose, NUM_0, NUM_999999999, errors,
                FieldKey.POSSIBLE_DOSAGES_DISPENSE_UNITS_PER_DOSE, FieldKey.NATIONAL_POSSIBLE_DOSAGES);

            // BCMA Units per Dose is an optional field
            // numeric data and range is between 1-9999
            if (possibleDosage.getBcmaUnitsPerDose() != null) {
                double bcma = possibleDosage.getBcmaUnitsPerDose().doubleValue();

                rejectIfOutsideRangeInclusive(bcma, NUM_1, NUM_9999, errors, FieldKey.NATIONAL_POSSIBLE_DOSAGES,
                    FieldKey.BCMA_UNITS_PER_DOSE);

            }// end if

        }// end for

    }// end validate
}
