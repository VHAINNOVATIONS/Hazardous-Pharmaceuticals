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
 * Dispense unit cannot be null. Selections of dispense unit cannot be null or empty.
 */
public class DispenseUnitValidator extends AbstractValidator<ListDataField<String>> {

    /**
     * Dispense unit cannot be null. Selections of dispense unit cannot be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(ListDataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.DISPENSE_UNIT);

            return;
        }

        boolean valid = true;

        if (!target.isSelected()) {
            valid = false;
        }

        for (String selection : target.getValue()) {
            if (isNullOrEmpty(selection) || target.containsDefault(selection)) {
                valid = false;
            }
        }

        if (!valid) {
            errors.addError(FieldKey.DISPENSE_UNIT, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.DISPENSE_UNIT});
        }
    }
}
