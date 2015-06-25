/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.NdcFieldValidator;

import junit.framework.TestCase;


/**
 * NdcFieldValidatorTest
 *
 */
public class NdcFieldValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(NdcFieldValidatorTest.class);
    private NdcFieldValidator validator = new NdcFieldValidator();
    private Errors errors;

    /**
     * Validates the validation cases for NDC Field
     */
    public void testNameValidation() {

        String test = null;

        errors = new Errors();
        test = null;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null NDC.", errors.hasErrors());

        errors = new Errors();
        test = "";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty NDC.", errors.hasErrors());

        errors = new Errors();
        test = "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long NDC!", errors.hasErrors());

        errors = new Errors();
        test = "123456789012";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long NDC.", errors.hasErrors());

        errors = new Errors();
        test = "123456-1234-12";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for NDC in 6-4-2 format.", errors.hasErrors());

        errors = new Errors();
        test = "12345-12345-12";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for NDC in 5-5-2 format.", errors.hasErrors());

        errors = new Errors();
        test = "12345-1234-123";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for NDC in 5-4-3 format.", errors.hasErrors());

        errors = new Errors();
        test = "12345-1234-12";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid NDC in 5-4-2 format.", errors.hasErrors());

        errors = new Errors();
        test = "1-1-1";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid NDC in 1-1-1 format.", errors.hasErrors());

        errors = new Errors();
        test = "1--";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid NDC in 1-0-0 format.", errors.hasErrors());

        errors = new Errors();
        test = "-1-";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid NDC in 0-1-0 format.", errors.hasErrors());

        errors = new Errors();
        test = "--1";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid NDC in 0-0-1 format.", errors.hasErrors());

        errors = new Errors();
        test = "12345678901";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid NDC of 11 characters.", errors.hasErrors());

        errors = new Errors();
        test = "1234567890";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too short NDC.", errors.hasErrors());

        errors = new Errors();
        test = "12345-1234";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for NDC in 5-4 format.", errors.hasErrors());

        errors = new Errors();
        test = "12345-1234-10-1";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for NDC in 5-4-2-1 format.", errors.hasErrors());

        errors = new Errors();
        test = "12345-1234-1A";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for NDC with non-numeric character.", errors.hasErrors());

        errors = new Errors();
        test = "12345/1234/12";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for NDC with invalid segment delimiter.", errors.hasErrors());

    }

    /**
     * getErrors for the NdcFieldValidatorTest.
     * 
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
