/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.PackageSizeValidator;

import junit.framework.TestCase;


/**
 * PackageSizeValidatorTest
 *
 */
public class PackageSizeValidatorTest extends TestCase {

    private static final double MAXRANGE = 999999.9999;
    private static final double MINRANGE = 0.0;
    private static final double TOO_BIG  = 1000000.0;
    private static final double TOO_SMALL  = -0.001;
    private static final double THREE_DIGITS  = 14.242;
    private static final double FOUR_DIGITS   = 14.2342;
    private static final double FIVE_DIGITS   = 14.23432;
    private static final double POINTOHONE  = 0.0001;
    
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(PackageSizeValidatorTest.class);
    private PackageSizeValidator validator = new PackageSizeValidator();
    private Errors errors;

    /**
     * Validates the validation cases for Package Size
     */
    public void testNameValidation() {

        double test = 0;

        errors = new Errors();
        Double nullTest = null;
        validator.validate(nullTest, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Package Size.", errors.hasErrors());

        errors = new Errors();
        test = MINRANGE;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Package Size of zero.", errors.hasErrors());

        errors = new Errors();
        test = TOO_BIG;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for Package Size > 10000.", errors.hasErrors());

        errors = new Errors();
        test = TOO_SMALL;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for Package Size < 0.", errors.hasErrors());

        errors = new Errors();
        test = MAXRANGE;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Package Size of 10000.", errors.hasErrors());

        errors = new Errors();
        test = POINTOHONE;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Package Size of 0.0001.", errors.hasErrors());

        errors = new Errors();
        test = THREE_DIGITS;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Package Size of three.", errors.hasErrors());
        
        errors = new Errors();
        test = FOUR_DIGITS;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Package Size of four digits.", errors.hasErrors());
        
        errors = new Errors();
        test = FIVE_DIGITS;
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for Package Size with 5 decimal places.", errors.hasErrors());

    }

    /**
     * getErrors for the PackageSizeValidatorTest
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
