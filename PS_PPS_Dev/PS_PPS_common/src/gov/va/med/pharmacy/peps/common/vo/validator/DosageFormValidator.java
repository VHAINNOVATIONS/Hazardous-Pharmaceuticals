/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * The dosage form must not be null. If the dosage form is not null, the generic code dosage form must not be null or empty
 * and the dose form name must not be null or empty.
 */
public class DosageFormValidator extends AbstractValidator<DosageFormVo> {

    /**
     * The dosage form must not be null. If the dosage form is not null, the generic code dosage form must not be null or
     * empty and the dose form name must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(DosageFormVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DOSAGE_FORM);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        
        String dosageFormName = target.getDosageFormName();
        rejectIfNullOrEmpty(dosageFormName, errors, FieldKey.DOSAGE_FORM);
        rejectIfLengthOutsideRangeInclusive(dosageFormName, PPSConstants.I3, 
            PPSConstants.I30, errors, FieldKey.DOSAGE_FORM);
        rejectIfStartsWithPunctuation(dosageFormName, errors, FieldKey.DOSAGE_FORM);
        rejectIfStartsWithNumeric(dosageFormName, errors, FieldKey.DOSAGE_FORM);
        

        for (DispenseUnitPerDoseVo unit : target.getDfDispenseUnitsPerDose()) {
            errors.addErrors(unit.checkForErrors());
        }

        for (DosageFormUnitVo unit : target.getDfUnits()) {
            errors.addErrors(unit.checkForErrors());
        }

        // Removed local fields for PPS-N delivery
        //for (DosageFormNounVo noun : target.getDfNouns()) {
        //    errors.addErrors(noun.checkForErrors());
        //}

        //if (target.getDataFields().getDataField(FieldKey.CONJUNCTION) != null
        //    && target.getDataFields().getDataField(FieldKey.CONJUNCTION).getValue() != null) {
        //    rejectIfLengthOutsideRangeInclusive(target.getDataFields().getDataField(FieldKey.CONJUNCTION).getValue(), 1, 20,
        //        errors, FieldKey.CONJUNCTION);
        //}

        //if (target.getDataFields().getDataField(FieldKey.OTHER_LANGUAGE_VERB) != null
        //    && target.getDataFields().getDataField(FieldKey.OTHER_LANGUAGE_VERB).getValue() != null) {
        //    rejectIfLengthOutsideRangeInclusive(
        //        target.getDataFields().getDataField(FieldKey.OTHER_LANGUAGE_VERB).getValue(), 1, 20, errors,
        //        FieldKey.OTHER_LANGUAGE_VERB);
        //}

        //if (target.getDataFields().getDataField(FieldKey.OTHER_LANGUAGE_PREPOSITION) != null
        //    && target.getDataFields().getDataField(FieldKey.OTHER_LANGUAGE_PREPOSITION).getValue() != null) {
        //    rejectIfLengthOutsideRangeInclusive(target.getDataFields().getDataField(FieldKey.OTHER_LANGUAGE_PREPOSITION)
        //        .getValue(), 1, 20, errors, FieldKey.OTHER_LANGUAGE_PREPOSITION);
        //}

        //if (target.getDataFields().getDataField(FieldKey.PREPOSITION) != null
        //    && target.getDataFields().getDataField(FieldKey.PREPOSITION).getValue() != null) {
        //    rejectIfLengthOutsideRangeInclusive(target.getDataFields().getDataField(FieldKey.PREPOSITION).getValue(), 1, 20,
        //        errors, FieldKey.PREPOSITION);
        //}

        //if (target.getDataFields().getDataField(FieldKey.VERB) != null
        //    && target.getDataFields().getDataField(FieldKey.VERB).getValue() != null) {
        //    rejectIfLengthOutsideRangeInclusive(target.getDataFields().getDataField(FieldKey.VERB).getValue(), 1, 20,
        //        errors, FieldKey.VERB);
        //}

    }
}
