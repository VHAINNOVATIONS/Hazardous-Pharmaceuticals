/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;
import java.util.HashMap;

import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleAssocVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * validate the Admin Schedules field
 */
public class AdminSchedulesValidator extends AbstractValidator<Collection<AdministrationScheduleAssocVo>> {

    /**
     * validate
     * 
     * @param target the Admin Schedules
     * @param errors the errors collection
     * 
     */
    public void validate(Collection<AdministrationScheduleAssocVo> target, Errors errors) {

        if ((target == null) || (target.isEmpty())) {

            //Field is optional return

            return;
        }

        int defaultCount = 0;
        HashMap<String, AdministrationScheduleAssocVo> nameMap = new HashMap<String, AdministrationScheduleAssocVo>();

        // if no primary selected add error
        for (AdministrationScheduleAssocVo adminSchedule : target) {
            AdministrationScheduleVo administrationSchedule = adminSchedule.getAdministrationSchedule();

            if ((administrationSchedule == null) || isNullOrEmpty(administrationSchedule.getId())
                || isNullOrEmpty(administrationSchedule.getValue())) {
                errors.addError(FieldKey.ADMINISTRATION_SCHEDULE, ErrorKey.COMMON_EMPTY,
                                new Object[] { FieldKey.ADMINISTRATION_SCHEDULE });
            } else {

                if (adminSchedule.isDefaultSchedule()) {
                    defaultCount++;
                }

                if (nameMap.containsKey(administrationSchedule.getValue())) {
                    errors.addError(FieldKey.ADMINISTRATION_SCHEDULE, ErrorKey.ADMIN_SCHEDULES_DUPLICATE,
                                    new Object[] { FieldKey.ADMINISTRATION_SCHEDULE });
                } else {
                    nameMap.put(administrationSchedule.getValue(), adminSchedule);
                }
            }
        }// end for

        if ((defaultCount != 1) && !((target.size() == 1) && (defaultCount == 0))) {
            errors.addError(FieldKey.ADMINISTRATION_SCHEDULE, ErrorKey.ADMIN_SCHEDULES_DEFAULT,
                            new Object[] { FieldKey.ADMINISTRATION_SCHEDULE });
        }

    }

}
