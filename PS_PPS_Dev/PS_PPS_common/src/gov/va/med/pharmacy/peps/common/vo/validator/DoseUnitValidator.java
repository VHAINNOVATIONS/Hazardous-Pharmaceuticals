/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * validator for dose unit
 */
public class DoseUnitValidator extends AbstractValidator<DoseUnitVo> {

    /**
     * validates Dose Unit Vo
     * 
     * @param target the DoseUnitVo
     * @param errors the errors collection
     */
    @Override
    public void validate(DoseUnitVo target, Errors errors) {

        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DOSE_UNIT);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getDoseUnitName(), errors, FieldKey.DOSE_UNIT);
        rejectIfNullOrEmpty(target.getFdbDoseUnit(), errors, FieldKey.FDB_DOSE_UNIT);

        if (target.getDoseUnitName() != null && target.getDoseUnitName().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getDoseUnitName().trim(), 1,
                PPSConstants.I30, errors, FieldKey.DOSE_UNIT_NAME);
        }

        if (target.getFdbDoseUnit() != null && target.getFdbDoseUnit().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getFdbDoseUnit().trim(), 1,
                PPSConstants.I30, errors, FieldKey.FDB_DOSE_UNIT);

            // first character not alpha numeric add to errors
            if (!Character.isLetterOrDigit(target.getFdbDoseUnit().charAt(0))) {
                errors
                    .addError(ErrorKey.COMMON_NEED_FIRST_CHARACTER_IS_ALPHA_NUMERIC, new Object[] { FieldKey.FDB_DOSE_UNIT });
            }

        }

        if (target.getDoseUnitSynonyms() != null && !target.getDoseUnitSynonyms().isEmpty()) {
            rejectIfFalse(doseUnitSynonymsValidator(target.getDoseUnitSynonyms()), ErrorKey.DOSE_UNIT_SYNONYMS, errors);
        }
    }

    /**
     * Validates length of all synonyms in collection.
     * 
     * @param synonyms collection
     * @return valid; true if all synonyms are 1-30 characters in length, false otherwise
     */
    private boolean doseUnitSynonymsValidator(Collection<DoseUnitSynonymVo> synonyms) {

        boolean valid = true;
        int length;

        for (DoseUnitSynonymVo synonym : synonyms) {
            length = synonym.getDoseUnitSynonymName().length();

            if (length < 1 || length > PPSConstants.I30) {
                valid = false;
                break;
            }
        }

        return valid;
    }

}
