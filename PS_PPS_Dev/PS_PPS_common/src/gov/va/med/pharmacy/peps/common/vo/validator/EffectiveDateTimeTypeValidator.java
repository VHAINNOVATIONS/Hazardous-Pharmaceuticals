/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;


/**
 * EffectiveDateTimeTypeValidator's brief summary
 * 
 * Validates Effective Datetime values
 * 
 */
public class EffectiveDateTimeTypeValidator extends AbstractValidator<Collection<VuidStatusHistoryVo>> {

    /**
     * 
     * The Effective Datetime must not be null or empty.
     * 
     * @param value Collection of VuidStatusHistoryVo
     * @param errors Spring Errors object to add validation errors to
     *
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(
     *      java.lang.Object, gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(Collection<VuidStatusHistoryVo> value, Errors errors) {
   
        if (value == null || value.isEmpty()) {
            rejectIfNullOrEmpty(value, errors, FieldKey.EFFECTIVE_DATES);

            return;
        }

        for (VuidStatusHistoryVo target : value) {
            if (target.getEffectiveDateTime() == null) {
                rejectIfNull(target.getEffectiveDateTime(), errors, FieldKey.EFFECTIVE_DTM);

                return;
            }
        }

    }
}
