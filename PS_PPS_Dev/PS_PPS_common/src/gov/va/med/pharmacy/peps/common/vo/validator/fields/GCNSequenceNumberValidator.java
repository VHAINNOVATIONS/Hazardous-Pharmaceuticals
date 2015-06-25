/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates the GCN Sequence Number
 */
public class GCNSequenceNumberValidator extends AbstractValidator<String> {

    /**
     * validates the GCN Sequence Number
     * 
     * @param target is the GCN Sequence Number
     * @param errors is the errors collection
     */
    @Override
    public void validate(String target, Errors errors) {
        if (isNullOrEmpty(target)) {
            rejectIfNullOrEmpty(target, errors, FieldKey.GCN_SEQUENCE_NUMBER);

            return;
        }
        
        //no decimal digits allowed
        Pattern p1 = Pattern.compile("^\\d+$");
        Matcher matcher1 = p1.matcher(target);
        boolean matchFound1 = matcher1.matches();

        if (!matchFound1) {
            errors.addError(FieldKey.GCN_SEQUENCE_NUMBER, ErrorKey.COMMON_NOT_NUMERIC,
                new Object[] {FieldKey.GCN_SEQUENCE_NUMBER});

            return;
        }
        
        
        rejectIfOutsideRangeInclusive(Long.valueOf(target), 0, PPSConstants.I999999, errors, FieldKey.GCN_SEQUENCE_NUMBER);
    }
}
