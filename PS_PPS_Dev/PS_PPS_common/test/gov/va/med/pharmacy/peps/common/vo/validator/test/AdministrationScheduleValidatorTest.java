/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.validator.AdministrationScheduleValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;

import junit.framework.TestCase;


/**
 * Test case for the AdministrationScheduleValidator
 */
public class AdministrationScheduleValidatorTest extends TestCase {

    private static final String FREQ_OUT_RANGE = "Frequency out of range doesn't return error";
    private static final String SCHED_BAD_LEN = "ScheduleName of incorrect length doesn't return error";
    private static final String OTHER_LANG = "OtherLanguageExpansion of incorrect length doesn't return error";
    private static final String SCHED_OUTPAT = "ScheduleOutpatientExpansion of incorrect length doesn't return error";
    private static final String PACK_PREFIX = "PackagePrefix of incorrect length doesn't return error";
    private static final String STD_ADMIN_LENTIME = "StandardAdministrationTimes of incorrect length doesn't return error";
    private static final String STD_ADMIN_FRMTIME = "StandardAdministrationTimes in incorrect format doesn't return error";
    private static final String STD_ADMIN_MILTIME = "StandardAdministrationTimes not in miltary time doesn't return error";
    private static final String STD_ADMIN_ASCTIME = "StandardAdministrationTimes not in ascending order doesn't return error";

    private static final String TEST = "TEST";

    private AdministrationScheduleValidator validator = new AdministrationScheduleValidator();
    private Errors errors;
    private AdministrationScheduleVo test;

    /**
     * Test null admin schedule has errors
     */
    public void testNullAdministrationSchedule() {

        errors = new Errors();
        test = null;

        validator.validate(test, errors);

        assertTrue("Null AdministrationSchedule does not return error", errors.hasErrors());

    }

    /**
     * test empty admin schedule has errors
     */
    public void testEmptyAdministrationSchedule() {

        errors = new Errors();
        test = new AdministrationScheduleVo();

        validator.validate(test, errors);

        assertTrue("Empty AdministrationSchedule does not return error", errors.hasErrors());
    }

    /**
     * make sure the the field length and range checks are passing
     */
    public void testFieldLengthRangeRestrictions() {

        //Test Frequency Range
        errors = new Errors();
        test = setupTestVo();

        test.setFrequency(Long.parseLong("0"));

        validator.validate(test, errors);

        assertTrue(FREQ_OUT_RANGE, errors.hasErrors());

        errors = new Errors();
        test.setFrequency(Long.parseLong("129601"));

        validator.validate(test, errors);

        assertTrue(FREQ_OUT_RANGE, errors.hasErrors());

        //Test ScheduleName Length
        errors = new Errors();
        test = new AdministrationScheduleVo();
        test.setPackagePrefix(TEST);

        test.setScheduleName(null);

        validator.validate(test, errors);

        assertTrue(SCHED_BAD_LEN, errors.hasErrors());

        errors = new Errors();
        test.setScheduleName("A");

        validator.validate(test, errors);

        assertTrue(SCHED_BAD_LEN, errors.hasErrors());

        errors = new Errors();
        test.setScheduleName("This string should be over the twenty characters needed to fail the test.");

        validator.validate(test, errors);

        assertTrue(SCHED_BAD_LEN, errors.hasErrors());

        //Test OtherLanguageExpansion Length
        errors = new Errors();
        test = setupTestVo();

        test.setOtherLanguageExpansion("A");

        validator.validate(test, errors);

        assertTrue(OTHER_LANG, errors.hasErrors());

        errors = new Errors();
        test.setScheduleName("This string should be over the fifty characters needed to fail the test. I hope.");

        validator.validate(test, errors);

        assertTrue(OTHER_LANG, errors.hasErrors());

        //Test ScheduleOutpatientExpansion Length
        errors = new Errors();
        test = setupTestVo();

        test.setScheduleOutpatientExpansion("A");

        validator.validate(test, errors);

        assertTrue(SCHED_OUTPAT, errors.hasErrors());

        errors = new Errors();
        test.setScheduleOutpatientExpansion("This string should be over the twenty characters needed to"
            + " fail the test. I hope.");

        validator.validate(test, errors);

        assertTrue(SCHED_OUTPAT, errors.hasErrors());

        //Test PackagePrefix Length
        errors = new Errors();
        test = new AdministrationScheduleVo();
        test.setScheduleName(TEST);

        test.setPackagePrefix(null);

        validator.validate(test, errors);

        assertTrue(PACK_PREFIX, errors.hasErrors());

        errors = new Errors();
        test.setPackagePrefix("");

        validator.validate(test, errors);

        assertTrue(PACK_PREFIX, errors.hasErrors());

        errors = new Errors();
        test.setPackagePrefix("This string should be over the four characters needed to fail the test.");

        validator.validate(test, errors);

        assertTrue(PACK_PREFIX, errors.hasErrors());

        //Test StandardAdministrationTimes Length
        errors = new Errors();
        test = setupTestVo();

        test.setStandardAdministrationTimes("1");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_LENTIME, errors.hasErrors());

        errors = new Errors();
        test.setStandardAdministrationTimes("0000-0100-0200-0300-0400-0500-0600-0700-0800-0900-1000-1100-1200-1300-1400-"
            + "1500-1600-1700-1800-1900-2000-2100-2200-2300-2400");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_LENTIME, errors.hasErrors());

        //Test StandardShift Length       
        errors = new Errors();
        test = setupTestVo();
        test.setStandardShifts("M-T-W-T-F-S-S-M");

        validator.validate(test, errors);

        assertTrue("StandardShift of incorrect length doesn't return error", errors.hasErrors());
    }

    /**
     * Tests to make sure the format of StandardAdministrationTimes is correct
     */
    public void testStandardAdministrationTimesFormat() {

        errors = new Errors();
        test = setupTestVo();

        test.setStandardAdministrationTimes("26");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_MILTIME, errors.hasErrors());

        errors = new Errors();
        test.setStandardAdministrationTimes("23-24-");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_FRMTIME, errors.hasErrors());

        errors = new Errors();
        test.setStandardAdministrationTimes("01-02-15-06");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_ASCTIME, errors.hasErrors());

        errors = new Errors();
        test.setStandardAdministrationTimes("2369");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_MILTIME, errors.hasErrors());

        errors = new Errors();
        test.setStandardAdministrationTimes("0100-1500-");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_FRMTIME, errors.hasErrors());

        errors = new Errors();
        test.setStandardAdministrationTimes("0100-0200-1500-0600");

        validator.validate(test, errors);

        assertTrue(STD_ADMIN_ASCTIME, errors.hasErrors());
    }

    /**
     * Tests to make sure the format of StandardShifts is correct
     */
    public void testStandardShiftsFormat() {

        errors = new Errors();
        test = setupTestVo();

        test.setStandardShifts("M-");

        validator.validate(test, errors);

        assertTrue("StandardShifts in incorrect format doesn't return error", errors.hasErrors());
    }

    /**
     * Sets up an AdministrationScheduleVo for testing, fills in required fields
     * so tests won't pass because they're empty and causing errors
     * 
     * @return tester vo
     */
    private AdministrationScheduleVo setupTestVo() {

        AdministrationScheduleVo tester = new AdministrationScheduleVo();
        tester.setScheduleName(TEST);
        tester.setPackagePrefix(TEST);

        return tester;
    }
}
