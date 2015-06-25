/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.CmopIdValidator;

import junit.framework.TestCase;


/**
 * CmopIdValidatorTest's brief summary
 * 
 * Unit Test of CmopIdValidator class
 * CmopId is a required text field of 1 to 20 characters
 *
 */
public class CmopIdValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(CmopIdValidatorTest.class);

    private CmopIdValidator validator = new CmopIdValidator();
    private Errors errors;

    /**
     * Validates the validation cases for CmopId
     */
    public void testNameValidation() {

        String test = null;

        errors = new Errors();
        test = null;
        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null CmopId.", errors.hasErrors());

        errors = new Errors();
        test = "";
        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty CmopId.", errors.hasErrors());

        errors = new Errors();
        test = "This name is extremely verbose. It is way too long to pass the character limit imposed on the field.";
        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue(" Expected error did not occur for too long CmopId.", errors.hasErrors());

        errors = new Errors();
        test = "123456";
        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long CmopId.", errors.hasErrors());

        errors = new Errors();
        test = "1234";
        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too short CmopId.", errors.hasErrors());

        errors = new Errors();
        test = "12345";
        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid CmopId of 5 characters.", errors.hasErrors());

    }

    /**
     * getErrors
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
