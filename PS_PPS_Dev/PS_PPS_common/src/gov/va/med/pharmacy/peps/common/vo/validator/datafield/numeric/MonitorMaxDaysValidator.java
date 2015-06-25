/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * valiates monitor max days
 */
public class MonitorMaxDaysValidator extends AbstractValidator<DataField<Long>> {

    /**
     * validate
     * 
     * @param target the Normal amount to order VA DF
     * @param errors the errors collection
     * 
     */
    public void validate(DataField<Long> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.MONITOR_MAX_DAYS);

            return;
        }

        rejectIfOutsideRangeInclusive(target.getValue(), 0, PPSConstants.I999, errors, 
            FieldKey.MONITOR_MAX_DAYS);
    }
}
