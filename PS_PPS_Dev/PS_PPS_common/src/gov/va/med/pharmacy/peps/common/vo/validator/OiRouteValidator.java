/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.OiRouteVo;


/**
 * The route must not be null. If the route is not null, the route generic code must not be null or empty and the route name
 * must not be null or empty.
 */
public class OiRouteValidator extends AbstractValidator<Collection<OiRouteVo>> {

    /**
     * The route must not be null. If the route is not null, the route generic code must not be null or empty and the route
     * name must not be null or empty.
     * 
     * @param target instance of OrderableItem
     * @param errors Spring Errors object to add validation errors to
     */
    public void validate(Collection<OiRouteVo> target, Errors errors) {
        if (target == null) {

            return;
        }

        // if no primary selected add error
        for (OiRouteVo oiRoute : target) {
            LocalMedicationRouteVo route = oiRoute.getLocalMedicationRoute();

            if ((route == null) || isNullOrEmpty(route.getId()) || isNullOrEmpty(route.getValue())) {
                errors.addError(FieldKey.LOCAL_MEDICATION_ROUTE, ErrorKey.COMMON_EMPTY,
                    new Object[] {FieldKey.LOCAL_MEDICATION_ROUTE});
            }
        }// end for
    }
}
