/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.VuidValidator;

import junit.framework.TestCase;


/**
 * VuidValidatorTest's brief summary
 * 
 * Unit Test of VuidValidator class
 * VUID is a required text field of 1 to 20 characters
 *
 */

/**
 * VuidValidatorTest
 * 
 */
public class VuidValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(VuidValidatorTest.class);
    private VuidValidator validator = new VuidValidator();
    private Errors errors;

    /**
     * Validates the validation cases for VUID
     */
    public void testNameValidation() {

        String test = null;

        errors = new Errors();
        test = null;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null VUID.", errors.hasErrors());

        errors = new Errors();
        test = "";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty VUID.", errors.hasErrors());

        errors = new Errors();
        test = "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long VUID!", errors.hasErrors());

        errors = new Errors();
        test = "123456789012345678901";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long VUID.", errors.hasErrors());

        errors = new Errors();
        test = "12345678901234567890";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid VUID of 20 characters.", errors.hasErrors());

        errors = new Errors();
        test = "A";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid VUID of 1 character.", errors.hasErrors());

    }

    /**
     * getErrors for the VuidValidatorTest.
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
