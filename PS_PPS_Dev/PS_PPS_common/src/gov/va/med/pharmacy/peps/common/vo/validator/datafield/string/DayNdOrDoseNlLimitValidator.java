/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates the Day (Nd) or Dose(Nl) Limit
 */
public class DayNdOrDoseNlLimitValidator extends AbstractValidator<DataField<String>> {

    /**
     * validator method
     * 
     * @param target DayNdOrDoseNlLimit
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object, gov.va.med.pharmacy.
     *      peps.common.vo.validator.Errors)
     */
    public void validate(DataField<String> target, Errors errors) {
        
        //day nd / dose nl can be null or empty or "not selected"
        if (isNull(target)) {
            return;
        }

        if (isNull(target.getValue())) {
            return;
        }
        
        Pattern p1 = Pattern.compile("^\\d{1,2}[DLdl]$");
        Matcher matcher1 = p1.matcher(target.getValue().toString());
        boolean matchFound1 = matcher1.matches();

        if (!matchFound1) {
            errors.addError(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, ErrorKey.DAY_ND_OR_DOSE_NI_LIMIT_INCORRECT_VAL,
                new Object[] {FieldKey.DAY_ND_OR_DOSE_NL_LIMIT});

        }
    }
}
