/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validate the active ingredient
 */
public class InpatientPharmacyLocationValidator extends AbstractValidator<DataField<String>> {

    /**
     * validates the product active ingredient Name can not be null or empty Strength can not be null or empty
     * 
     * @param target the active ingredients
     * @param errors the errors collection
     * 
     */
    public void validate(DataField<String> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {

            return;
        }

        if (target.getValue() != null) {

            rejectIfLengthOutsideRangeInclusive(target.getValue().trim(), 1, PPSConstants.I12, errors,
                FieldKey.INPATIENT_PHARMACY_LOCATION);

            // can have upto two commas
            String[] times = target.getValue().trim().split(",");

            if (times.length > PPSConstants.I3) {

                errors.addError(FieldKey.INPATIENT_PHARMACY_LOCATION, ErrorKey.INPATIENT_PHARMACY_LOCATION_VALUES,
                    new Object[] {FieldKey.INPATIENT_PHARMACY_LOCATION});
            }

            return;

        }
    }
}
