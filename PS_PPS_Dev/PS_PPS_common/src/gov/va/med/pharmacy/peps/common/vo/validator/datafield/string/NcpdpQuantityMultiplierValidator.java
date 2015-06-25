/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.datafield.string;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * validates NCPDP Quantity Multiplier
 */
public class NcpdpQuantityMultiplierValidator extends AbstractValidator<DataField<String>> {

    /**
     * 
     * this is an required field for product item ncpdp quantity multiplier should be a number between .001 and 99999.999
     * 
     * @param target NCPDP Quantity Multiplier
     * @param errors the errors collection
     * 
     */
    public void validate(DataField<String> target, Errors errors) {
        if (isNull(target)) {
            rejectIfNull(target, errors, FieldKey.NDCDP_QUANTITY_MULTIPLIER);

            return;
        }// end if

        if (isNull(target.getValue())) {

            // check if the string is not empty string
            rejectIfNullOrEmpty(target.getValue(), errors, FieldKey.NDCDP_QUANTITY_MULTIPLIER);

            return;
        }

        if (!isNumeric(target.getValue())) {
            rejectIfNotNumeric(target.getValue(), errors, FieldKey.NDCDP_QUANTITY_MULTIPLIER);

            return;
        }

        double value = Double.parseDouble(target.getValue());

        rejectIfMoreDecimals(value, PPSConstants.I3, errors, FieldKey.NDCDP_QUANTITY_MULTIPLIER);
        rejectIfOutsideRangeInclusive(value, PPSConstants.ZEROPOINTZEROZEROONE, 
            PPSConstants.NINENINENINENINENINEPOINTNINENINENINE, errors, FieldKey.NDCDP_QUANTITY_MULTIPLIER);
    }
}
