/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.PackageTypeValidator;

import junit.framework.TestCase;


/**
 * PackageTypeValidatorTest
 *
 */
public class PackageTypeValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(PackageTypeValidatorTest.class);
    private PackageTypeValidator validator = new PackageTypeValidator();
    private Errors errors;

    /**
     * Validates a null Package Type doesn't pass
     */
    public void testNullPackageType() {
        errors = new Errors();
        PackageTypeVo test = null;

        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null PackageType.", errors.hasErrors());

    }

    /**
     * Validates an empty Package Type doesn't pass
     */
    public void testEmptyPackageType() {
        errors = new Errors();
        PackageTypeVo test = new PackageTypeVo();

        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty PackageType.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Package Type Name
     */
    public void testNameValidation() {

        PackageTypeVo test = new PackageTypeVo();

        // Validate a packageType with a null name.
        errors = new Errors();
        test.setValue(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Name.", errors.hasErrors());

        // Validate a packageType with an empty name.
        errors = new Errors();
        test.setValue("");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty Name.", errors.hasErrors());

        // Validate a packageType with a too long name.
        errors = new Errors();
        test.setValue("This name is extremely verbose. It is way too long to pass the character limit imposed on the field.");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name!", errors.hasErrors());

        errors = new Errors();
        test.setValue("12345678901234567890123456789012345678901");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name.", errors.hasErrors());

        errors = new Errors();
        test.setValue("1234567890123456789012345678901234567890");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 40 characters.", errors.hasErrors());

        errors = new Errors();
        test.setValue("A");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 1 character.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Package Type Item Status
     */
    public void testItemStatusValidation() {

        PackageTypeVo test = new PackageTypeVo();
        test.setValue("TEST");

        // PackageTypeValidator Test
        errors = new Errors();
        test.setItemStatus(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Item Status.", errors.hasErrors());

        
        // PackageTypeValidator Test
        errors = new Errors();
        test.setItemStatus(ItemStatus.ARCHIVED);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Item Status of ARCHIVED.", errors.hasErrors());

    }

    /**
     * PackageTypeValidator TestgetErrors
     * 
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }
}
