/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;



/**
 * Cs Fed Schecule validator
 */
public class CsFedScheduleValidator extends AbstractValidator<CsFedScheduleVo> {
    
    /**
     * validates CsFedSchedule Vo
     * 
     * @param target the CsFedScheduleVo
     * @param errors the errors collection
     */
    public void validate(CsFedScheduleVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.CS_FED_SCHEDULE);

            return;
        }

        rejectIfNullOrEmpty(target.getId(), errors, FieldKey.CS_FED_SCHEDULE);
        rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.CS_FED_SCHEDULE);
    }
}
