/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;


/**
 * Validate a Partial Save object
 */
public class PartialSaveValidator extends AbstractValidator<PartialSaveVo> {


    /**
     * This method validates that the Partial Save object is correctly filled in.
     * 
     * @param target The object that is being saved.
     * @param errors The list of errors.
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(PartialSaveVo target, Errors errors) {
        rejectIfNullOrEmpty(target.getComment(), errors, FieldKey.PARTIAL_SAVE);
    }
}
