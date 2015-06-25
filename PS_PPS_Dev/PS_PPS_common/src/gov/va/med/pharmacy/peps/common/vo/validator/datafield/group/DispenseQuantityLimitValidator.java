/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.group;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Dispense quantity limit, if not null, dose must be between 0 and 1000 inclusive. The time units must be either "days" or
 * "hours". If the time units is "days", the time must be between 0 and 90, inclusive. If the time units is "hours", the time
 * must be between 0 and 24, inclusive.
 */
public class DispenseQuantityLimitValidator extends AbstractValidator<GroupDataField> {

    /**
     * Dispense quantity limit, if selected, dose must be between 0 and 1000 inclusive. The time units must be either "days"
     * or "hours". If the time units is "days", the time must be between 0 and 90, inclusive. If the time units is "hours",
     * the time must be between 0 and 24, inclusive.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(GroupDataField target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.DISPENSE_QUANTITY_LIMIT);

            return;
        }

        if (target.isSelected()) {
            rejectIfOutsideRangeInclusive(target.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE).getValue(), 
                                          0, PPSConstants.I1000,
                errors, FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE);
            
            rejectIfOutsideRangeInclusive(target.getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME).getValue(), 
                                          0, PPSConstants.NINETY,
                errors, FieldKey.DISPENSE_QUANTITY_LIMIT_TIME);
        }
        
        
    }
}
