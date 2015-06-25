/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.list;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates formulary field
 */
public class FormularyValidator extends AbstractValidator<ListDataField<String>> {

    /**
     * Formulary cannot be null. Selections of formulary cannot be null or empty.
     * 
     * @param target instance of Formulary
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(ListDataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.FORMULARY);

            return;
        }
        
        boolean valid = true;

        // check to see if the target is selected before checking the Formulary.
        if (!target.isSelected()) {
            valid = false;
        }

        for (String selection : target.getValue()) {
            if (isNullOrEmpty(selection) || target.containsDefault(selection)) {
                valid = false;
            }
        }

        if (!valid) {
            errors.addError(FieldKey.FORMULARY, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.FORMULARY});
        }
    }
}
