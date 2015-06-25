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
 * validates reorder level VA DF
 */
public class ReorderLevelValidator extends AbstractValidator<DataField<Long>> {

    /**
     * validate
     * 
     * @param target the reorder level VA DF
     * @param errors the errors collection
     * 
     */
    public void validate(DataField<Long> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.REORDER_LEVEL);

            return;
        }

        rejectIfOutsideRangeInclusive(target.getValue(), 0, PPSConstants.I9999, errors, FieldKey.REORDER_LEVEL);
    }
}
