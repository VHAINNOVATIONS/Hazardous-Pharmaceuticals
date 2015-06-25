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
 * validates NCPDP Dispense Unit Field
 */
public class NcpdpDispenseUnitValidator extends AbstractValidator<ListDataField<String>> {

    /**
     * NCPDP Dispense Unit cannot be null. Selections of NCPDP Dispense Unit cannot be null or empty.
     * 
     * @param target instance of NCPDP Dispense Unit
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(ListDataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.NCPDP_DISPENSE_UNIT);

            return;
        }

        boolean valid = true;

        if (!target.isSelected()) {
            valid = false;
        }

        for (String selection : target.getValue()) {
            if (isNullOrEmpty(selection)) {
                valid = false;
            }
        }

        if (!valid) {
            errors.addError(FieldKey.NCPDP_DISPENSE_UNIT, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.NCPDP_DISPENSE_UNIT});
        }
    }
}
