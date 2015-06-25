/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;


/**
 * validates the synonyms
 */
public class SynonymValidator extends AbstractValidator<Collection<SynonymVo>> {

    /**
     * validate
     * 
     * @param target the synonyms
     * @param errors the errors collection
     * 
     */
    @Override
    public void validate(Collection<SynonymVo> target, Errors errors) {
        if ((target == null) || (target.isEmpty())) {
            rejectIfNullOrEmpty(target, errors, FieldKey.SYNONYMS);

            return;
        }

        for (SynonymVo synonym : target) {
            if (synonym == null) {
                errors.addError(FieldKey.SYNONYMS, ErrorKey.COMMON_EMPTY, new Object[] {FieldKey.SYNONYMS});

                return;
            }

            // Synonym Name is the mandatory field for Synonym
            if (isNullOrEmpty(synonym.getSynonymName())) {
                errors.addError(FieldKey.SYNONYMS, ErrorKey.SYNONYM_REQ_FIELD_EMPTY, new Object[] {FieldKey.SYNONYMS});

                return;
            }// end if

            // synonym name contains 1-40 characters
            rejectIfLengthOutsideRangeInclusive(synonym.getSynonymName(), NUM_1, NUM_40, errors, FieldKey.SYNONYMS,
                FieldKey.SYNONYM_NAME);

            // ndc code contains 5-20 characters
            if (!isNullOrEmpty(synonym.getNdcCode())) {
                rejectIfLengthOutsideRangeInclusive(synonym.getNdcCode(), NUM_5, NUM_20, errors, FieldKey.SYNONYMS,
                    FieldKey.NDC_CODE);
            }

            // Dispense Units per Order Unit is an option an optional field
            // data range is 1 to 999999
            // max of 4 decimal digits allowed
            if ((synonym.getSynonymDispenseUnitPerOrderUnit() != null)
                && (synonym.getSynonymDispenseUnitPerOrderUnit().longValue() != 0)) {

                rejectIfMoreDecimals(synonym.getSynonymDispenseUnitPerOrderUnit(), NUM_4, errors,
                    FieldKey.SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT, FieldKey.SYNONYMS);
                rejectIfOutsideRangeInclusive(synonym.getSynonymDispenseUnitPerOrderUnit(), 1, NUM_999999, errors,
                    FieldKey.SYNONYMS, FieldKey.SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT);
            }

            String vendor = synonym.getSynonymVendor();
            String vsn = synonym.getSynonymVsn();

            if ((vendor != null) && (vendor.trim().length() > 0)) {
                rejectIfLengthOutsideRangeInclusive(vendor, 1, PPSConstants.I35, errors, FieldKey.SYNONYMS,
                    FieldKey.VENDOR);
            }

            if ((vsn != null) && (vsn.trim().length() > 0)) {
                rejectIfLengthOutsideRangeInclusive(vsn, 1, PPSConstants.I30, errors, FieldKey.SYNONYMS, FieldKey.SYNONYM_VSN);
            }

            // price per order unit is an optional field
            // if present it must be numeric and Data Range: {min: 0; max: 999999}
            // if present must have maximum of 2 decimal digits

            if (!isNull(synonym.getSynonymPricePerOrderUnit())) {
                rejectIfMoreDecimals(synonym.getSynonymPricePerOrderUnit(), NUM_2, errors,
                    FieldKey.SYNONYM_PRICE_PER_ORDER_UNIT);
                rejectIfOutsideRangeInclusive(synonym.getSynonymPricePerOrderUnit(), NUM_0, NUM_999999, errors,
                    FieldKey.SYNONYMS,
                    FieldKey.SYNONYM_PRICE_PER_ORDER_UNIT);
            }

            // price per dispense unit is an optional field
            // if present it must be numeric and Data Range: {min: 0; max: 999999}

            if (!isNull(synonym.getSynonymPricePerDispenseUnit())) {
                rejectIfMoreDecimals(synonym.getSynonymPricePerDispenseUnit(), NUM_4, errors,
                    FieldKey.SYNONYM_PRICE_PER_DISPENSE_UNIT);
                rejectIfOutsideRangeInclusive(synonym.getSynonymPricePerDispenseUnit(), NUM_0, NUM_999999, errors,
                    FieldKey.SYNONYMS,
                    FieldKey.SYNONYM_PRICE_PER_DISPENSE_UNIT);
            }
        }
    }
}
