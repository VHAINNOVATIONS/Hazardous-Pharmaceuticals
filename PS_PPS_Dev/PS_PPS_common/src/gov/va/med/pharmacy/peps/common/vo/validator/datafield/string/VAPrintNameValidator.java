/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates VA Print Name
 */
public class VAPrintNameValidator extends AbstractValidator<String> {

    /**
     * 
     * this is an optional field
     * 
     * @param target
     *            VA Print Name
     * @param errors
     *            the errors collection
     * 
     */
    public void validate(String target, Errors errors) {

        if (StringUtils.isEmpty(target)) {
            return;
        }
            
        // the length is between 1 to 40 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target, 1, PPSConstants.I40, errors,
            FieldKey.VA_PRINT_NAME);

    }
}
