/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.UpcUpnValidator;

import junit.framework.TestCase;


/**
 * UpcUpnValidatorTest
 *
 */
public class UpcUpnValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(UpcUpnValidatorTest.class);
    private UpcUpnValidator validator = new UpcUpnValidator();
    private Errors errors;

    /**
     * Validates the validation cases for UPC/UPN
     */
    public void testNameValidation() {

        String test = null;

        errors = new Errors();
        test = null;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid null UPC/UPN.", errors.hasErrors());

        errors = new Errors();
        test = "";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid empty UPC/UPN.", errors.hasErrors());

        errors = new Errors();
        test = "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long UPC/UPN!", errors.hasErrors());

        errors = new Errors();
        test = "123456789012345678901";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long UPC/UPN.", errors.hasErrors());

        errors = new Errors();
        test = "12345678901234567890";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid UPC/UPN of 20 characters.", errors.hasErrors());

        errors = new Errors();
        test = "A";
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid UPC/UPN of 1 character.", errors.hasErrors());

    }

    /**
     * getErrors for the UpcUpnValidatorTest.
     * 
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
