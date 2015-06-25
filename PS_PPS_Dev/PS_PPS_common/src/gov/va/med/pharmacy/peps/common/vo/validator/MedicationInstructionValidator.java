/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;


/**
 * validator for medication instruction
 */
public class MedicationInstructionValidator extends AbstractValidator<MedicationInstructionVo> {
    
    /**
     * validates Medication Instruction Vo
     * 
     * @param target medicationInstructionVo
     * @param errors object
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *     gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(MedicationInstructionVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.MEDICATION_INSTRUCTION);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.VALUE);
        rejectIfNullOrEmpty(target.getMedInstructionExpansion(), errors, FieldKey.MED_INSTRUCTION_EXPANSION);
        rejectIfNullOrEmpty(target.getDefaultAdminTimes(), errors, FieldKey.DEFAULT_ADMIN_TIMES);
        
        if (target.getValue() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getValue(), 1, PPSConstants.I9, errors, FieldKey.VALUE);
        }

        if (target.getAdditionalInstruction() != null && target.getAdditionalInstruction().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getAdditionalInstruction(), PPSConstants.I10, PPSConstants.I100, errors,
                FieldKey.ADDITIONAL_INSTRUCTION);
        }

        if (target.getDefaultAdminTimes() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getDefaultAdminTimes(), 1, 
                PPSConstants.I40, errors, FieldKey.DEFAULT_ADMIN_TIMES);
        }

        if (target.getMedInstructionExpansion() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getMedInstructionExpansion(), 2, PPSConstants.I50, errors,
                FieldKey.MED_INSTRUCTION_EXPANSION);
        }

        FieldKey.FREQUENCY.newValidatorInstance().validate(target.getFrequency(), errors);

        if (target.getInstructions() != null && target.getInstructions().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getInstructions(), PPSConstants.I10, PPSConstants.I75, 
                errors, FieldKey.INSTRUCTIONS);
        }

        if (target.getOtherLanguageExpansion() != null && target.getOtherLanguageExpansion().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getOtherLanguageExpansion(), 2, PPSConstants.I100, errors,
                FieldKey.OTHER_LANGUAGE_EXPANSION);
        }

        if (target.getPlural() != null && target.getPlural().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getPlural(), 1, PPSConstants.I25, errors, FieldKey.PLURAL);
        }

        if (target.getMedInstructionSchedule() != null && target.getMedInstructionSchedule().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getMedInstructionSchedule(), 2, PPSConstants.I20, errors,
                FieldKey.MED_INSTRUCTION_SCHEDULE);
        }

        if (target.getMedInstructionSynonym() != null && target.getMedInstructionSynonym().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(target.getMedInstructionSynonym(), 1, PPSConstants.I9, errors,
                FieldKey.MED_INSTRUCTION_SYNONYM);
        }

        FieldKey.MED_INSTRUCTION_WARD_MULTIPLE.newValidatorInstance().validate(target.getMedInstructionWardMultiple(), errors);

    }
}
