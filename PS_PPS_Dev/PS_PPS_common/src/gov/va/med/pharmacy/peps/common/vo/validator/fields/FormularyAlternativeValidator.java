/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.fields;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates the VA Product Name
 */
public class FormularyAlternativeValidator extends AbstractValidator<DataField<String>> {

    /**
     * validate
     * 
     * @param value the Formulary Alternative VA Data Field
     * @param errors the errors collection
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator# validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(DataField<String> value, Errors errors) {

        if (value != null && !StringUtils.isEmpty(value.getValue())) {
        
            // the length is between 3 to 64 characters, inclusive
            rejectIfLengthOutsideRangeInclusive(value.getValue(), PPSConstants.I3, PPSConstants.I64, errors,
                FieldKey.FORMULARY_ALTERNATIVE);
        }
    }


}
