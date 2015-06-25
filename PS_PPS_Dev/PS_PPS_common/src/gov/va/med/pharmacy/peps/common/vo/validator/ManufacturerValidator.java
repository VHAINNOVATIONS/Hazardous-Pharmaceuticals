/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;


/**
 * Validate {@link ManufacturerVo} on creation or modification of the Manufacturer.
 * <p>
 * Selection of a Manufacturer on an NDC is validated within the {@link NdcValidator}.
 */
public class ManufacturerValidator extends AbstractValidator<ManufacturerVo> {

    /**
     * Manufacturer must not be null. If the Manufacturer is not null, its labeler ID must not be null or empty and its name
     * must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(ManufacturerVo target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.MANUFACTURER);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        
        if (isNullOrEmpty(target.getValue())) {
            errors.addError(FieldKey.MANUFACTURER, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.VALUE});
        } else {
            rejectIfLengthOutsideRangeInclusive(target.getValue(), 2, PPSConstants.I25, errors, FieldKey.VALUE);
        }
    }
}
