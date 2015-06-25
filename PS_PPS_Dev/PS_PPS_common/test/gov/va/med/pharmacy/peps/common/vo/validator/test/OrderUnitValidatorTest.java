/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.OrderUnitValidator;

import junit.framework.TestCase;


/**
 * OrderUnitValidatorTest
 *
 */
public class OrderUnitValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(OrderUnitValidatorTest.class);
    private static final String TES = "TES";
    private OrderUnitValidator validator = new OrderUnitValidator();
    private Errors errors;
    
    
    /**
     * Validates an empty order unit doesn't pass
     */
    public void testEmptyOrderUnit() {
        errors = new Errors();
        OrderUnitVo test = new OrderUnitVo();

        validator.validate(test, errors);
        LOG.debug("In testEmptyOrderUnit()");
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty OrderUnit.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Order Unit Name
     */
    public void testNameValidation() {

        OrderUnitVo test = new OrderUnitVo();

        errors = new Errors();
        test.setValue(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Name.", errors.hasErrors());

        errors = new Errors();
        test.setValue("");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty Name.", errors.hasErrors());

        errors = new Errors();
        test.setValue("This name is extremely verbose. It is way too long to pass the character limit imposed on the field.");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name!!", errors.hasErrors());

        errors = new Errors();
        test.setValue("1234");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name.", errors.hasErrors());

        errors = new Errors();
        test.setValue("123");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 3 characters.", errors.hasErrors());

        errors = new Errors();
        test.setValue("AB");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 2 characters.", errors.hasErrors());

        errors = new Errors();
        test.setValue("A");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too short Name.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Order Unit Expansion
     */
    public void testExpansionValidation() {

        OrderUnitVo test = new OrderUnitVo();
        test.setValue(TES);

        errors = new Errors();
        test.setExpansion("This name is extremely verbose. It is way too long to pass the character limit imposed on the"
            + " field.");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Expansion!", errors.hasErrors());

        errors = new Errors();
        test.setExpansion("1234567890123456789012345678901");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Expansion.", errors.hasErrors());

        errors = new Errors();
        test.setExpansion("123456789012345678901234567890");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Expansion of 30 characters.", errors.hasErrors());

        errors = new Errors();
        test.setExpansion("ABC");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Expansion of 3 characters.", errors.hasErrors());

        errors = new Errors();
        test.setExpansion("AB");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too short Expansion.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Order Unit Item Status
     */
    public void testItemStatusValidation() {

        OrderUnitVo test = new OrderUnitVo();
        test.setValue(TES);

        errors = new Errors();
        test.setItemStatus(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Item Status.", errors.hasErrors());

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

//    /**
//     * Generates a default order unit with the bare minimum filled in
//     * 
//     * @return orderUnit
//     */
//    /*
//    private OrderUnitVo generateValidOrderUnit() {
//     OrderUnitVo ret = new OrderUnitVo();
//     
//     ret.setValue("TEST");
//     
//     return ret;
//    }*/
}
