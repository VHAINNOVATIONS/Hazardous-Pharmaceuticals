/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validate the local print name
 */
public class LocalPrintNameValidator extends AbstractValidator<String> {

    /**
     * validate
     * 
     * @param target  the local print name
     * @param errors the errors collections
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(String target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.LOCAL_PRINT_NAME);

            return;
        }// end if

        if (isNullOrEmpty(target)) {

            // check if the string is not empty string
            rejectIfNullOrEmpty(target, errors, FieldKey.LOCAL_PRINT_NAME);
            rejectIfLengthOutsideRangeInclusive(target, 1, PPSConstants.I40, errors, FieldKey.LOCAL_PRINT_NAME);

            return;
        }
    }
}
