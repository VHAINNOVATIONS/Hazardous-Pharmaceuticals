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
 * Application package use must have a selection.
 */
public class ApplicationPackageUseValidator extends AbstractValidator<ListDataField<String>> {

    /**
     * Application package use must have a selection.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(ListDataField<String> target, Errors errors) {
        if (!target.isSelected()) {
            errors.addError(FieldKey.APPLICATION_PACKAGE_USE, ErrorKey.MARK_FOR_LOCAL_USE_APPLICATION_PACKAGE_USE);
        }
    }
}
