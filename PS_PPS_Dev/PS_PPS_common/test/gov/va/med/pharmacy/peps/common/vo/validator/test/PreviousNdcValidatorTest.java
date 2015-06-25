/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.PreviousNdcValidator;

import junit.framework.TestCase;


/**
 * PreviousNdcValidatorTest
 *
 */
public class PreviousNdcValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(PreviousNdcValidatorTest.class);
    private static final String PREV_NDC = "Previous NDC: ";
    private PreviousNdcValidator validator; // = new PreviousNdcValidator();

    private Errors errors;
    private NdcVo ndc;
    private UserVo user;
    private Environment env;

    private int testAsserts = 0;

    /**
     * setup method
     * 
     * @throws Exception 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        errors = new Errors();
        testAsserts = 0;
        NdcGenerator gen = new NdcGenerator();
        ndc = gen.getFirst();
        ndc.setItemStatus(ItemStatus.ACTIVE);

        validator = new PreviousNdcValidator();

        user = new UserGenerator().getNationalManagerOne();

        env = Environment.NATIONAL;

        LOG.debug("In setUp method");

    }

    /**
     * Validates the validation cases for Previous NDC Field
     */
    public void testNameValidation() {

        final int totalTests = 16;

        doTestPreviousNdcTrue(
            "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.",
            "Expected error did not occur for too long Previous NDC. ");

        doTestPreviousNdcTrue("123456789012", "Expected error did not occur for too long Previous NDC.");

        doTestPreviousNdcTrue("123456-1234-12", "Expected error did not occur for Previous NDC in 6-4-2 format.");

        doTestPreviousNdcTrue("12345-12345-12", "Expected error did not occur for Previous NDC in 5-5-2 format.");

        doTestPreviousNdcTrue("12345-1234-123", "Expected error did not occur for Previous NDC in 5-4-3 format.");

        doTestPreviousNdcFalse("12345-1234-12", "Error occurred for valid Previous NDC in 5-4-2 format.");

        doTestPreviousNdcFalse("1-1-1", "Error occurred for multiple valid Previous NDCs in 1-1-1 format.");

        doTestPreviousNdcFalse("1--", "Error occurred for valid Previous NDC in 1-0-0 format.");

        doTestPreviousNdcFalse("-1-", "Error occurred for valid Previous NDC in 0-1-0 format.");

        doTestPreviousNdcFalse("--1", "Error occurred for valid Previous NDC in 0-0-1 format.");

        doTestPreviousNdcFalse("12345678901", "Error occurred for valid Previous NDC of 11 characters.");

        doTestPreviousNdcTrue("1234567890", "Expected error did not occur for too short Previous NDC.");

        doTestPreviousNdcTrue("12345-1234", "Expected error did not occur for Previous NDC in 5-4 format.");

        doTestPreviousNdcTrue("12345-1234-10-1", "Expected error did not occur for Previous NDC in 5-4-2-1 format.");

        doTestPreviousNdcTrue("12345-1234-1A", "Expected error did not occur for Previous NDC with non-numeric character.");

        doTestPreviousNdcTrue("12345/1234/12",
            "Expected error did not occur for Previous NDC with invalid segment delimiter.");

        assertTrue("Tested " + testAsserts + " of " + totalTests + " NDC test cases.", testAsserts == totalTests);
    }

    /**
     * Description
     *
     * @param test 
     * @param assertMsg 
     */
    private void doTestPreviousNdcTrue(String test, String assertMsg) {

        final String prefix = PREV_NDC;
        errors = new Errors();
        DataField<String> previousNdc = DataField.newInstance(FieldKey.PREVIOUS_NDC);
        previousNdc.selectStringValue(test);
        ndc.getVaDataFields().setDataField(previousNdc);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_NDC), user, env, errors);
        LOG.debug(prefix + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_NDC).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue(assertMsg, errors.hasErrors());
        testAsserts++;
    }

    /**
     * Description
     *
     * @param test 
     * @param assertMsg 
     */
    private void doTestPreviousNdcFalse(String test, String assertMsg) {

        final String prefix = PREV_NDC;
        errors = new Errors();
        DataField<String> previousNdc = DataField.newInstance(FieldKey.PREVIOUS_NDC);
        previousNdc.selectStringValue(test);
        ndc.getVaDataFields().setDataField(previousNdc);
        validator.validate(ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_NDC), user, env, errors);
        LOG.debug(prefix + ndc.getVaDataFields().getDataField(FieldKey.PREVIOUS_NDC).getValue());
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse(assertMsg, errors.hasErrors());
        testAsserts++;
    }

    /**
     * getErrors
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
