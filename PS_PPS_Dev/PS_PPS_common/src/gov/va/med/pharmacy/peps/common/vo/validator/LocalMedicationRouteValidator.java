/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;


/**
 * Validator for local medication route
 */
public class LocalMedicationRouteValidator extends AbstractValidator<LocalMedicationRouteVo> {

    /**
     * validate
     * @param value med route to validate
     * @param errors errors list
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(LocalMedicationRouteVo value, Errors errors) {
        if (isNull(value)) {
            rejectIfNull(value, errors, FieldKey.LOCAL_MEDICATION_ROUTE);

            return;
        }

        rejectIfNull(value.getItemStatus(), errors, FieldKey.ITEM_STATUS);

        rejectIfNullOrEmpty(value.getValue(), errors, FieldKey.VALUE);

        if (value.getValue() != null) {
            rejectIfLengthOutsideRangeInclusive(value.getValue(), PPSConstants.I3, PPSConstants.I45, 
                errors, FieldKey.VALUE);
        }

        rejectIfNullOrEmpty(value.getAbbreviation(), errors, FieldKey.ABBREVIATION);

        if (value.getAbbreviation() != null) {
            String x = value.getAbbreviation().trim();
            rejectIfLengthOutsideRangeInclusive(x, 1, PPSConstants.I15, errors, FieldKey.ABBREVIATION);
        }

        if (!isNullOrEmpty(value.getOtherLanguageExpansion())) {
            rejectIfLengthOutsideRangeInclusive(value.getOtherLanguageExpansion(), 2, PPSConstants.I50, errors,
                FieldKey.OTHER_LANGUAGE_EXPANSION);
        }

        if (!isNullOrEmpty(value.getOutpatientExpansion())) {
            rejectIfLengthOutsideRangeInclusive(value.getOutpatientExpansion(), 2, 
                PPSConstants.I50, errors, FieldKey.OUTPATIENT_EXPANSION);
        }
    }

}
