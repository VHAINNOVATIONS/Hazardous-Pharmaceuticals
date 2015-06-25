/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.ProductDispenseUnitPerOrderUnitValidator;

import junit.framework.TestCase;


/**
 * PackageSizeValidatorTest
 *
 */
public class DipsenseUnitPerOrderUnitValidatorTest extends TestCase {

    private static final double MAXRANGE = 99999.9999;
    private static final double MINRANGE = 1.0;
    private static final double TOO_BIG  = 100000.0;
    private static final double TOO_SMALL  = 0.9;
    private static final double THREE_DIGITS  = 14.242;
    private static final double FOUR_DIGITS   = 14.2342;
    private static final double FIVE_DIGITS   = 14.23432;
    private static final double ONEPOINTOHONE  = 1.0001;
    
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(DipsenseUnitPerOrderUnitValidatorTest.class);
    private ProductDispenseUnitPerOrderUnitValidator validator = new ProductDispenseUnitPerOrderUnitValidator();
    private Errors errors;

    /**
     * Validates the validation cases for Package Size
     */
    public void testNameValidation() {

        DataField<Double> test = null;
        
        errors = new Errors();
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null DipsenseUnitPerOrderUnit.", errors.hasErrors());
        
        test = DataField.newInstance(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);
        
        errors = new Errors();
        test.selectValue(MINRANGE);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid DipsenseUnitPerOrderUnit of zero.", errors.hasErrors());

        errors = new Errors();
        test.selectValue(TOO_BIG);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for DipsenseUnitPerOrderUnit > 99999.9999.", errors.hasErrors());

        errors = new Errors();
        test.selectValue(TOO_SMALL);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for DipsenseUnitPerOrderUnit of 0.9.", errors.hasErrors());

        errors = new Errors();
        test.selectValue(MAXRANGE);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid DipsenseUnitPerOrderUnit of 99999.9999.", errors.hasErrors());

        errors = new Errors();
        test.selectValue(ONEPOINTOHONE);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid DipsenseUnitPerOrderUnit of 1.0001.", errors.hasErrors());

        errors = new Errors();
        test.selectValue(THREE_DIGITS);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid DipsenseUnitPerOrderUnit of three.", errors.hasErrors());
        
        errors = new Errors();
        test.selectValue(FOUR_DIGITS);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid DipsenseUnitPerOrderUnit of four digits.", errors.hasErrors());
        
        errors = new Errors();
        test.selectValue(FIVE_DIGITS);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for DipsenseUnitPerOrderUnit with 5 decimal places.", errors.hasErrors());

    }

    /**
     * getErrors for the DispenseUntiPerOrderUnitValidatorTest.
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
