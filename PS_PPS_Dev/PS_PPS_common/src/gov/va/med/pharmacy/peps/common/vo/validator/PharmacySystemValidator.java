/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;


/**
 * validator for Pharmacy System
 */
public class PharmacySystemValidator extends AbstractValidator<PharmacySystemVo> {

    /**
     * validates PharmacySystem Vo
     * 
     * @param target the PharmacySystemVo
     * @param errors the errors collection
     */
    public void validate(PharmacySystemVo target, Errors errors) {
        if (target == null) {
            rejectIfNull(target, errors, FieldKey.PHARMACY_SYSTEM);

            return;
        }

        // the SiteName (stored in value) can not be empty or null
        rejectIfNullOrEmpty(target.getSiteName(), errors, FieldKey.PHARMACY_SYSTEM);
        rejectIfNullOrEmpty(target.getPsPmisPrinter(), errors, FieldKey.PHARMACY_SYSTEM_PMIS_PRINTER);

        // the length is between 2 to 40 characters, inclusive
        rejectIfLengthOutsideRangeInclusive(target.getValue(), NUM_2, NUM_40, errors, FieldKey.PHARMACY_SYSTEM);

        // test each of the 26 remaining fields (all 1-15, except one is 1-30 and one 255)
        validateField(target.getPsAnd(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_AND);
        validateField(target.getPsDays(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_DAYS);
        validateField(target.getPsEight(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_EIGHT);
        validateField(target.getPsExcept(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_EXCEPT);
        validateField(target.getPsFive(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_FIVE);
        validateField(target.getPsFor(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_FOR);
        validateField(target.getPsFour(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_FOUR);
        validateField(target.getPsHours(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_HOURS);
        validateField(target.getPsMinutes(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_MINUTES);
        validateField(target.getPsMonths(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_MONTHS);
        validateField(target.getPsNine(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_NINE);
        validateField(target.getPsOne(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_ONE);
        validateField(target.getPsOneFourth(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_ONE_FOURTH);
        validateField(target.getPsOneHalf(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_ONE_HALF);
        validateField(target.getPsOneThird(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_ONE_THIRD);

        // two special length strings here
        validateField(target.getPsPmisPrinter(), NUM_1, NUM_255, errors, FieldKey.PHARMACY_SYSTEM_PMIS_PRINTER);
        validateField(target.getPsPmisSectionDelete(), NUM_1, NUM_30, errors, FieldKey.PHARMACY_SYSTEM_PMIS_SECTION_DELETE);

        validateField(target.getPsSeconds(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_SECONDS);
        validateField(target.getPsSeven(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_SEVEN);
        validateField(target.getPsSix(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_SIX);
        validateField(target.getPsTen(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_TEN);
        validateField(target.getPsThen(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_THEN);
        validateField(target.getPsThree(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_THREE);
        validateField(target.getPsThreeFourths(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_THREE_FOURTHS);
        validateField(target.getPsTwo(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_TWO);
        validateField(target.getPsTwoThirds(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_TWO_THIRDS);
        validateField(target.getPsWeeks(), NUM_1, NUM_15, errors, FieldKey.PHARMACY_SYSTEM_WEEKS);

    }

    /**
     * Test each string for min and max length, null or empty is ok for all Pharmacy System field except SiteName (mandatory
     * field, test seperately)
     * 
     * @param string the field being tested
     * @param errors the errors vo
     * @param min the minimum length of the field, inclusive
     * @param max the maximum length of the field, inclusive
     * @param fieldKey fieldKey of field being tested
     */
    public void validateField(String string, int min, int max, Errors errors, FieldKey<String> fieldKey) {

        // null or empty is accepted since these are all expected to be optional strings in Pharmacy System
        if (!isNullOrEmpty(string)) {

            // ok, it's not null or empty so check min and max inclusive lengths
            rejectIfLengthOutsideRangeInclusive(string, min, max, errors, fieldKey);
        }
    }
}
