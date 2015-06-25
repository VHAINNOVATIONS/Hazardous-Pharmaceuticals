/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.list;


import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Validate product types
 */
public class ProductTypeValidator extends AbstractValidator<ListDataField<Category>> {

    /**
     * Product Type cannot be null. If Supply is selected, no other selection is valid.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    @Override
    public void validate(ListDataField<Category> target, Errors errors) {

        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.CATEGORIES);

            return;
        }

        boolean valid = true;

        if (!target.isSelected()) {
            valid = false;
        }

        for (Category selection : target.getValue()) {
            if (isNullOrEmpty(selection.toString())) {
                valid = false;
            }
        }

        if (!valid) {
            errors.addError(FieldKey.CATEGORIES, ErrorKey.COMMON_EMPTY, new Object[] { FieldKey.CATEGORIES });
        }

    }
}
