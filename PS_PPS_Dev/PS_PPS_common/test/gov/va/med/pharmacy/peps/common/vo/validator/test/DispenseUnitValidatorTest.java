/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.validator.DispenseUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;

import junit.framework.TestCase;


/**
 * DispenseUnitValidatorTest
 *
 */
public class DispenseUnitValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(DispenseUnitValidatorTest.class);
    private DispenseUnitValidator validator = new DispenseUnitValidator();
    private Errors errors;

    /**
     * Validates a null dispense unit doesn't pass
     */
    public void testNullDispenseUnit() {

        errors = new Errors();
        DispenseUnitVo test = null;

        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null DispenseUnit.", errors.hasErrors());

    }

    /**
     * Validates an empty dispense unit doesn't pass
     */
    public void testEmptyDispenseUnit() {

        errors = new Errors();
        DispenseUnitVo test = new DispenseUnitVo();

        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty DispenseUnit.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Dispense Unit Name
     */
    public void testNameValidation() {

        DispenseUnitVo test = new DispenseUnitVo();

        // DispenseUnitNameValidation testNameValidation
        errors = new Errors();
        test.setValue(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Name.", errors.hasErrors());

        // DispenseUnitNameValidation testNameValidation
        errors = new Errors();
        test.setValue("");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty Name.", errors.hasErrors());

     // DispenseUnitNameValidation
        errors = new Errors();
        test.setValue("This name is extremely verbose. It is way too long to pass the character limit imposed on the field.");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name!", errors.hasErrors());

     // DispenseUnitNameValidation
        errors = new Errors();
        test.setValue("12345678901");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name.", errors.hasErrors());

     // DispenseUnitNameValidation
        errors = new Errors();
        test.setValue("1234567890");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 10 characters.", errors.hasErrors());

        errors = new Errors();
        test.setValue("A");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 1 character.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Dispense Unit Item Status
     */
    public void testItemStatusValidation() {

        DispenseUnitVo test = new DispenseUnitVo();
        test.setValue("TEST");

        // DispenseUnitValidatorTest.testItemStatusValidation
        errors = new Errors();
        test.setItemStatus(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Item Status.", errors.hasErrors());

     // DispenseUnitValidatorTest.testItemStatusValidation
        errors = new Errors();
        test.setItemStatus(ItemStatus.ARCHIVED);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Item Status of ARCHIVED.", errors.hasErrors());
    }

    /**
     * getErrors
     * 
     * @return errors property
     */
    public Errors getErrors() {

        return errors;
    }

}
