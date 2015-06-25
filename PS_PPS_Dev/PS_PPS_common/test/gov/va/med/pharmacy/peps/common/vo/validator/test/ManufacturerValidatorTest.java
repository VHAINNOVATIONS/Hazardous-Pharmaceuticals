/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;



import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ManufacturerValidator;

import junit.framework.TestCase;


/**
 * ManufacturerValidatorTest
 *
 */
public class ManufacturerValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(ManufacturerValidatorTest.class);
    private ManufacturerValidator validator = new ManufacturerValidator();
    private Errors errors;

    /**
     * Validates a null Manufacturer doesn't pass
     */
    public void testNullManufacturer() {
        errors = new Errors();
        ManufacturerVo test = null;

        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Manufacturer.", errors.hasErrors());

    }

    /**
     * Validates an empty Manufacturer doesn't pass
     */
    public void testEmptyManufacturer() {
        errors = new Errors();
        ManufacturerVo test = new ManufacturerVo();

        validator.validate(test, errors);
        LOG.debug("In testManufacturer()");
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty Manufacturer.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Manufacturer Name
     */
    public void testNameValidation() {

        ManufacturerVo test = new ManufacturerVo();

        // Validation test for manufacturer with No value
        errors = new Errors();
        test.setValue(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Name.", errors.hasErrors());

        // Validation test for manufacturer with Empty value
        errors = new Errors();
        test.setValue("");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty Name.", errors.hasErrors());

        // Validation test for manufacturer with too long value
        errors = new Errors();
        test.setValue("This name is extremely verbose. It is way too long to pass the character limit imposed on the field.");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name!", errors.hasErrors());

        errors = new Errors();
        test.setValue("12345678901234567890123456");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name.", errors.hasErrors());

        errors = new Errors();
        test.setValue("1234567890123456789012345");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 25 characters.", errors.hasErrors());

        errors = new Errors();
        test.setValue("AB");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("This Error occurred for valid Name of 2 characters.", errors.hasErrors());

        errors = new Errors();
        test.setValue("A");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too short Name.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Manufacturer Item Status
     */
    public void testItemStatusValidation() {

        ManufacturerVo test = new ManufacturerVo();
        test.setValue("TEST");

        // Set the Error object
        errors = new Errors();
        test.setItemStatus(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("The Expected error did not occur for null Item Status.", errors.hasErrors());

        errors = new Errors();
        test.setItemStatus(ItemStatus.ARCHIVED);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Item Status of ARCHIVED.", errors.hasErrors());

    }

    /**
     * This method will getErrors()
     * 
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }
}
