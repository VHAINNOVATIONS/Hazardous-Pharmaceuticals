/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DosageFormNounVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Validates dosage form nouns
 */
public class DosageFormNounValidator extends AbstractValidator<DosageFormNounVo> {

    /**
     * The dispense unit per dose must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(DosageFormNounVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DOSAGE_FORM_NOUN);

            return;
        }
        
        rejectIfNullOrEmpty(target.getNoun(), errors, FieldKey.NOUN);
        
        if (!isNullOrEmpty(target.getNoun())) {
            rejectIfLongerThanMax(target.getNoun(), PPSConstants.I30, errors, FieldKey.NOUN);
        }
        
        if (!isNullOrEmpty(target.getOtherLanguageNoun())) {
            rejectIfLongerThanMax(target.getOtherLanguageNoun(), PPSConstants.I50, errors, FieldKey.OTHER_LANGUAGE_NOUN);
        }
    }
}
