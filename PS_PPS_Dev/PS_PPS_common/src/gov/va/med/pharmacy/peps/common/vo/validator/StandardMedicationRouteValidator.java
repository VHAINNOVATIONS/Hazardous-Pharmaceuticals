/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;


/**
 * The route must not be null. If the route is not null, the route generic code must not be null or empty and the route name
 * must not be null or empty.
 */
public class StandardMedicationRouteValidator extends AbstractValidator<StandardMedRouteVo> {

    /**
     * The route must not be null. If the route is not null, the route generic code must not be null or empty and the route
     * name must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(StandardMedRouteVo target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.STANDARD_MED_ROUTE);

            return;
        }

        rejectIfNull(target.getItemStatus(), errors, FieldKey.ITEM_STATUS);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.STANDARD_MED_ROUTE);
        rejectIfNullOrEmpty(target.getFirstDatabankMedRoute(), errors, FieldKey.FDB_MED_ROUTE);
        
        if (target.getValue() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getValue(), NUM_3, NUM_50, errors, FieldKey.VALUE);
        }
        
        if (target.getFirstDatabankMedRoute() != null) {
            rejectIfLengthOutsideRangeInclusive(target.getFirstDatabankMedRoute(), NUM_3, NUM_30, errors,
                FieldKey.FDB_MED_ROUTE);
        }
    }

}
