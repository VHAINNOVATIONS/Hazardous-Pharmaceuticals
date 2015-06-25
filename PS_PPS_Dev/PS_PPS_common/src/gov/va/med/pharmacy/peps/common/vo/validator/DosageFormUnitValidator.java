/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Validates dosage form units
 */
public class DosageFormUnitValidator extends AbstractValidator<DosageFormUnitVo> {

    /**
     * The dosage form unit must not be null or empty.
     * 
     * @param target instance of dosage form unit Vo
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(DosageFormUnitVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.DOSAGE_FORM_UNIT);

            return;
        }
        
        if (target.getDrugUnit() == null) {
            rejectIfNull(target.getDrugUnit(), errors, FieldKey.DRUG_UNIT);
        } else {
            rejectIfNullOrEmpty(target.getDrugUnit().getValue(), errors, FieldKey.DRUG_UNIT);
        }
    }
}
