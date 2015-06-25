/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;


/**
 *  This class is used to validate the DrugClassVo
 */
public class DrugClassificationValidator extends AbstractValidator<DrugClassVo> {

    /**
     * Default DrugClass validate method
     * 
     * @param value DrugClassVo to validate 
     * @param errors Errors to place errors in
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator
     * #validate(java.lang.Object, gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(DrugClassVo value, Errors errors) {

        if (isNull(value)) {
            rejectIfNull(value, errors, FieldKey.DRUG_CLASS);

            return;
        }

        rejectIfNull(value.getItemStatus(), errors, FieldKey.ITEM_STATUS);
  
        if (isNullOrEmpty(value.getClassification())) {
            rejectIfNullOrEmpty(value.getClassification(), errors, FieldKey.CLASSIFICATION);
        } else {
            rejectIfLongerThanMax(value.getClassification(), PPSConstants.I64, errors, FieldKey.CLASSIFICATION);
        }

        if (isNullOrEmpty(value.getCode())) {
            rejectIfNullOrEmpty(value.getCode(), errors, FieldKey.CODE);
        } else {

            String code = value.getCode();

            if (code.length() == PPSConstants.I5) {
                if (!(Character.isLetter(code.charAt(0)) && Character.isLetter(code.charAt(1))
                        && Character.isDigit(code.charAt(2)) && Character.isDigit(code.charAt(PPSConstants.I3))
                        && Character.isDigit(code.charAt(PPSConstants.I4)))) {
                    errors.addError(FieldKey.CODE, ErrorKey.DRUG_CLASS_INCORRECT_CODE_FORMAT);
                }
            } else {
                errors.addError(FieldKey.CODE, ErrorKey.DRUG_CLASS_INCORRECT_CODE_FORMAT);
            }

        }
        
        if (isNull(value.getClassificationType())) {
            rejectIfNull(value.getClassificationType(), errors, FieldKey.CLASSIFICATION_TYPE);

        }

        if (!isNullOrEmpty(value.getDescription())) {
            rejectIfLengthOutsideRangeInclusive(value.getDescription(), 1, PPSConstants.I2000, errors, FieldKey.DESCRIPTION);
        }
        
        // VUID and Effective Date Time are mandatory when the request item status is approved
        if ((value.getRequestItemStatus().equals(RequestItemStatus.APPROVED))) {

            // VUID
            FieldKey.VUID.newValidatorInstance().validate(value.getVuid(),
                                                          errors);

            // Effective Date Time
            FieldKey.EFFECTIVE_DATES.newValidatorInstance().validate(value.getEffectiveDates(), errors);
        }
    }

}
