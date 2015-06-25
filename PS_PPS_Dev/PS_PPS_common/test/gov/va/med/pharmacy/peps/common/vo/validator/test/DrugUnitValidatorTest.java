/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;

import junit.framework.TestCase;


/**
 * DrugUnitValidatorTest
 *
 */
public class DrugUnitValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(DrugUnitValidatorTest.class);
    private DrugUnitValidator validator = new DrugUnitValidator();
    private Errors errors;

    /**
     * Validates a null drug unit doesn't pass
     */
    public void testNullDrugUnit() {
        errors = new Errors();
        DrugUnitVo test = null;
        
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null DrugUnit.", errors.hasErrors());
        
    }
    
    /**
     * Validates an empty drug unit doesn't pass
     */
    public void testEmptyDrugUnit() {
        errors = new Errors();
        DrugUnitVo test = new DrugUnitVo();
        
        validator.validate(test,  errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty DrugUnit.", errors.hasErrors());
    
    }
    
    /**
     * Validates the validation cases for Drug Unit Name
     */
    public void testNameValidation() {

        DrugUnitVo test = new DrugUnitVo();
        
        // DrugUnitValidator test null name.
        errors = new Errors();
        test.setValue(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Name.", errors.hasErrors());
    
        // DrugUnitValidator test empty name.
        errors = new Errors();
        test.setValue("");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty Name.", errors.hasErrors());
 
        // DrugUnitValidator test way too long name.
        errors = new Errors();
        test.setValue("This name is extremely verbose. It is way too long to pass the character limit imposed on the field.");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name!", errors.hasErrors());
 
        errors = new Errors();
        test.setValue("1234567890123456789012345678901234567890123456789012345678901234567890123456");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name.", errors.hasErrors());
 
        errors = new Errors();
        test.setValue("123456789012345678901234567890123456789012345678901234567890123456789012345");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 75 characters.", errors.hasErrors());
 
        errors = new Errors();
        test.setValue("A");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 1 character.", errors.hasErrors());
        
    }
    
    /**
     * Validates the validation cases for Drug Unit Item Status
     */
    public void testItemStatusValidation() {

        DrugUnitVo test = new DrugUnitVo();
        test.setValue("TEST");
        
        // testItemStatusValidation
        errors = new Errors();
        test.setItemStatus(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Item Status.", errors.hasErrors());
    
        // testItemStatusValidation
        errors = new Errors();
        test.setItemStatus(ItemStatus.ARCHIVED);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Item Status of ARCHIVED.", errors.hasErrors());

    }

    /**
     * getErrors for DrugUnitValidatiorTest
     * 
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }
}
