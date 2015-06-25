/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationVo;


/**
 * Validator for Hospital Location
 */
public class HospitalLocationValidator extends AbstractValidator<Collection<HospitalLocationVo>> {

   // private static final String DASH = "-";

    /**
     * validates HospitalLocationVo
     * 
     * @param target the HospitalLocationVo
     * @param errors the errors collection
     */
    @Override
    public void validate(Collection<HospitalLocationVo> target, Errors errors) {
        if (target == null || target.isEmpty()) {
            return;
        }

        for (HospitalLocationVo locationVo : target) {

            errors.addErrors(validateRequiredFields(locationVo, errors));

            if (locationVo.getShifts() != null && locationVo.getShifts().length() > 0) {
                rejectIfLengthOutsideRangeInclusive(locationVo.getShifts(), 1, 
                    PPSConstants.I11, errors, FieldKey.SHIFTS);

                if (locationVo.getShifts().length() < PPSConstants.I12) {
                    rejectIfFalse(locationVo.getShifts().matches("([\\w]{0,9}[\\-]){0,5}[\\w]{1,11}"),
                        ErrorKey.HOSPITAL_SHIFT_FORMAT, errors);
                }
            }

            if (locationVo.getAdministrationTimes() != null && locationVo.getAdministrationTimes().length() > 0) {
                rejectIfLengthOutsideRangeInclusive(locationVo.getAdministrationTimes(), 2, 
                    PPSConstants.I119, errors,
                    FieldKey.ADMINISTRATION_TIMES);
            }

        }
    }

//    /**
//     * Validates the format and order of the administration times
//     * 
//     * @param administrationTimes string of times
//     * @return valid
//     */
//    private boolean adminTimesValidator(String administrationTimes) {
//        boolean valid = false;
//
//        if (administrationTimes != null) {
//
//            // Regex checks for proper formatting and makes sure the user can only enter proper military time
//            valid = (administrationTimes.matches("(([0-1][0-9]|2[0-4])[\\-]){0,39}([0-1][0-9]|2[0-4])") || administrationTimes
//                .matches("((([0-1][0-9]|2[0-3])[0-5][0-9]|2400)[\\-]){0,23}(([0-1][0-9]|2[0-3])[0-5][0-9]|2400)"));
//
//            // Make sure the time is in ascending order
//            if (administrationTimes.contains(DASH) && valid) {
//
//                String[] times = administrationTimes.split(DASH);
//                int time = Integer.parseInt(times[times.length - 1]);
//                int previousTime;
//
//                for (int i = times.length - 2; i >= 0; i--) {
//                    previousTime = Integer.parseInt(times[i]);
//
//                    if (time < previousTime) {
//                        valid = false;
//                        break;
//                    }
//
//                    time = previousTime;
//                }
//            }
//        }
//
//        return valid;
//    }

    /**
     * validates the required fields of the HospitalLocationVo
     * 
     * @param target the HospitalLocationVo
     * @param errors the errors collection
     * @return errors
     */
    private Errors validateRequiredFields(HospitalLocationVo target, Errors errors) {

        if (target.getHospitalLocationSelection() == null || isNullOrEmpty(target.getHospitalLocationSelection().getValue())) {
            errors.addError(ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.HOSPITAL_LOCATION_SELECTION});
        }

        return errors;
    }
}
