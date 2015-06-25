/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.list;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Validate product types
 */
public class OrderableItemSynonymValidator extends AbstractValidator<MultitextDataField<String>> {

    /**
    * OrderableItemSynonym cannot be null. Selections of formulary cannot be null or empty.
    * 
    * @param target instance of Formulary
    * @param errors Spring Errors object to add validation errors to
    */
    public void validate(MultitextDataField<String> target, Errors errors) {
        if (target != null && target.getValue() != null && target.getValue().size() > 0) {
            for (String selection : target.getValue()) {
                rejectIfLengthOutsideRangeInclusive(selection, NUM_1, NUM_30, errors, FieldKey.ORDERABLE_ITEM_SYNONYM);

            }
        }
    }
}
