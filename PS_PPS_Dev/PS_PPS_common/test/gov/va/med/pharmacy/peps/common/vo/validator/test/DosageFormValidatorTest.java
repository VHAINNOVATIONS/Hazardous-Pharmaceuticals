/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.validator.DosageFormValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;

import junit.framework.TestCase;


/**
 * DosageFormValidatorTest's brief summary
 * 
 * Details of DosageFormValidatorTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */

/**
 * DosageFormValidatorTest
 * 
 */
public class DosageFormValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(DosageFormValidatorTest.class);
    private DosageFormValidator validator = new DosageFormValidator();
    private Errors errors;

    /**
     * Validates a null Dosage Form doesn't pass
     */
    public void testNullDosageForm() {

        errors = new Errors();
        DosageFormVo test = null;

        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null DosageForm.", errors.hasErrors());

    }

    /**
     * Validates an empty Dosage Form doesn't pass
     */
    public void testEmptyDosageForm() {

        errors = new Errors();
        DosageFormVo test = new DosageFormVo();

        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty DosageForm.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Dosage Form Name
     */
    public void testNameValidation() {

        DosageFormVo test = new DosageFormVo();

        errors = new Errors();
        test.setDosageFormName(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Name.", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty Name.", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("AB");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too short Name.", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("This name is extremely verbose. It is way too long to pass the character "
            + "limit imposed on the field.");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name!", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for too long Name.", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("ABCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 30 characters.", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("%BCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for Name beginning with punctuation.", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("1BCDEFGHIJKLMNOPQRSTUVWXYZABCD");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for Name beginning with numeric.", errors.hasErrors());

        errors = new Errors();
        test.setDosageFormName("A/1");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Name of 3 characters.", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Dosage Form Item Status
     */
    public void testItemStatusValidation() {

        DosageFormVo test = new DosageFormVo();
        test.setDosageFormName("TEST");

        errors = new Errors();
        test.setItemStatus(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null Item Status for DosageFormValidatorTest.", errors.hasErrors());

        errors = new Errors();
        test.setItemStatus(ItemStatus.ARCHIVED);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Error occurred for valid Item Status of ARCHIVED  for DosageFormValidatorTest.", errors.hasErrors());

    }

    /**
     * getErrors
     * @return errors property
     */
    public Errors getErrors() {

        return errors;
    }

//    /**
//     * Generates a default drug class with the bare minimum filled in
//     * 
//     * @return DosageForm
//     */
//    /*
//     * private DosageFormVo generateValidDosageForm() {
//     * DosageFormVo ret = new DosageFormVo();
//     * ret.setValue("TEST");
//     * return ret;
//     * }
//     */

}
