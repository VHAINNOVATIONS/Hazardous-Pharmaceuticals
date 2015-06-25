/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.AdministrationTimesValidator;


/**
 * Validator for Ward
 */
public class MedInstructionWardValidator extends AbstractValidator<Collection<WardMultipleVo>> {

    /**
     * validates WardVo
     * 
     * @param target the WardVo
     * @param errors the errors collection
     */
    @Override
    public void validate(Collection<WardMultipleVo> target, Errors errors) {

        if (target == null || target.isEmpty()) {
            return;
        }

        for (WardMultipleVo ward : target) {
            rejectIfNull(ward.getWardSelection(), errors, FieldKey.WARD_SELECTION);

            if (ward.getWardSelection() != null) {
                rejectIfNullOrEmpty(ward.getWardSelection().getValue(), errors, FieldKey.WARD_MULTIPLE);
            }

            // reject if null or empty
            rejectIfNullOrEmpty(ward.getWardAdminTimes(), errors, FieldKey.WARD_ADMIN_TIMES);


            if (ward.getWardAdminTimes() != null && ward.getWardAdminTimes().trim().length() > 0) {
                rejectIfLengthOutsideRangeInclusive(ward.getWardAdminTimes(), NUM_2, NUM_40, errors,
                    FieldKey.WARD_ADMIN_TIMES);
                AdministrationTimesValidator administrationTimesValidator = new AdministrationTimesValidator();
                administrationTimesValidator.validate(ward.getWardAdminTimes(), errors, FieldKey.WARD_ADMIN_TIMES);
            }
        }
    }
}
