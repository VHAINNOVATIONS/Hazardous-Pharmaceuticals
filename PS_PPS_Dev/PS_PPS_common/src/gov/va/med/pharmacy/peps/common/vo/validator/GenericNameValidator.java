/**
 * Source file created in 2012 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;


/**
 * GenericName must not be null. If the GenericName is not null, its labeler ID must not be null or empty and its name must
 * not be null or empty.
 */
public class GenericNameValidator extends AbstractValidator<GenericNameVo> {

    /**
     * GenericName must not be null. If the GenericName is not null, its labeler ID must not be null or empty and its name
     * must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(GenericNameVo target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.GENERIC_NAME);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        
        if (isNullOrEmpty(target.getValue())) {
            errors.addError(FieldKey.GENERIC_NAME, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.GENERIC_NAME});
        }

        this.rejectIfLengthOutsideRangeInclusive(target.getValue(), PPSConstants.I3, 
            PPSConstants.I64, errors, FieldKey.VALUE);

        // VUID and Effective Date Time are mandatory when the request item status is approved
        if ((target.getRequestItemStatus().equals(RequestItemStatus.APPROVED))) {

            // VUID
            FieldKey.VUID.newValidatorInstance().validate(target.getVuid(),
                                                          errors);

            // Effective Date Time
            FieldKey.EFFECTIVE_DATES.newValidatorInstance().validate(target.getEffectiveDates(), errors);
        }
    }
}
