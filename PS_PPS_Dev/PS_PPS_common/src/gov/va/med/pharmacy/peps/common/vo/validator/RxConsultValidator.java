/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;


/**
 * Validate data within the RX Consult managed domain
 */
public class RxConsultValidator extends AbstractValidator<RxConsultVo> {

    /**
     * Validate data within the RX Consult managed domain
     * 
     * @param value RxConsultVo to validate
     * @param errors errors list
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(RxConsultVo value, Errors errors) {
        if (isNull(value)) {
            rejectIfNull(value, errors, FieldKey.RX_CONSULT);

            return;
        }
        
        rejectIfNull(value.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(value.getValue(), errors, FieldKey.VALUE);

        if (value.getValue() != null) {
            rejectIfLengthOutsideRangeInclusive(value.getValue(), NUM_1, NUM_25, errors, FieldKey.VALUE);
        }

        if (value.getSpanishTranslation() != null && value.getSpanishTranslation().length() > 0) {
            rejectIfLengthOutsideRangeInclusive(value.getSpanishTranslation(), NUM_1, NUM_250, errors,
                FieldKey.SPANISH_TRANSLATION);
        }

        rejectIfNullOrEmpty(value.getConsultText(), errors, FieldKey.CONSULT_TEXT);

        if (value.getConsultText() != null) {
            rejectIfLengthOutsideRangeInclusive(value.getConsultText(), NUM_3, NUM_280, errors, FieldKey.CONSULT_TEXT);
        }
    }
}
