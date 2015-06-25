/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LocalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;


/**
 * validates local possible dosages
 */
public class LocalPossibleDosagesValidator extends AbstractValidator<Collection<LocalPossibleDosagesVo>> {

    /**
     * validate
     * 
     * @param target the local possible dosages
     * @param errors the errors collection
     * 
     */
    public void validate(Collection<LocalPossibleDosagesVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {
            rejectIfNullOrEmpty(target, errors, FieldKey.LOCAL_POSSIBLE_DOSAGES);

            return;
        }

        for (LocalPossibleDosagesVo localPossibleDosage : target) {
            if (localPossibleDosage == null) {
                errors.addError(FieldKey.LOCAL_POSSIBLE_DOSAGES, ErrorKey.COMMON_EMPTY,
                    new Object[] {FieldKey.LOCAL_POSSIBLE_DOSAGES});

                return;
            }

            String lPossibleDosage = localPossibleDosage.getLocalPossibleDosage();
            Collection<PossibleDosagesPackageVo> packages = localPossibleDosage.getPossibleDosagePackage();
           

            // local possible dosage, and package are required data fields
            if ((isNullOrEmpty(lPossibleDosage)) || (packages == null) || packages.isEmpty()) {
                errors.addError(FieldKey.LOCAL_POSSIBLE_DOSAGES, ErrorKey.LOCAL_POSSIBLE_DOSAGES_EMPTY,
                    new Object[] {FieldKey.LOCAL_POSSIBLE_DOSAGES});

                return;
            }// end if

            // BCMA Units per Dose is an optional field
            // numeric data and range is between 1-9999
            if (localPossibleDosage.getBcmaUnitsPerDose() != null) {
                double bcma = localPossibleDosage.getBcmaUnitsPerDose().doubleValue();

                rejectIfOutsideRangeInclusive(bcma, 1, PPSConstants.I9999, errors, FieldKey.LOCAL_POSSIBLE_DOSAGES,
                    FieldKey.BCMA_UNITS_PER_DOSE);

            }// end if

            if ((localPossibleDosage.getOtherLanguageDosageName() != null)
                && (localPossibleDosage.getOtherLanguageDosageName().trim().length() > 0)) {
                String name = localPossibleDosage.getOtherLanguageDosageName().trim();
                rejectIfLengthOutsideRangeInclusive(name, 1, PPSConstants.I60, errors, FieldKey.LOCAL_POSSIBLE_DOSAGES,
                    FieldKey.OTHER_LANGUAGE_DOSAGE_NAME);

            }// end if

        }// end for

    }// end validate
}
