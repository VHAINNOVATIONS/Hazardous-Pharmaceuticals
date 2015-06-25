/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


/**
 * CmopIdValidator's brief summary
 * 
 * Details of CmopIdValidator's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 *  * CmopIdValidator's brief summary
 * 
 * Details of CmopIdValidator's operations, special dependencies
 * or protocols developers shall know about when using the class.
 * 
 */
public class CmopIdValidator extends AbstractValidator<String> {

    /**
     * A CMOP ID must be a length of 5 characters.
     * 
     * @param value VUID
     * @param errors Errors
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(String value, Errors errors) {

        if (isNullOrEmpty(value)) {
            rejectIfNullOrEmpty(value, errors, FieldKey.CMOP_ID);

            return;

        }

        rejectIfLengthOutsideRangeInclusive(value, PPSConstants.I5, PPSConstants.I5, errors,
                                            FieldKey.CMOP_ID);
    }
}
