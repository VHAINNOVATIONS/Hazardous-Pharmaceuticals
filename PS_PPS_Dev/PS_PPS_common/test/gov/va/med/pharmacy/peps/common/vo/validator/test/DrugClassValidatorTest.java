/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugClassificationValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;

import junit.framework.TestCase;


/**
 * DrugClassValidatorTest
 */
public class DrugClassValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(DrugClassValidatorTest.class);
    private DrugClassificationValidator validator = new DrugClassificationValidator();
    private Errors errors = new Errors();

    /**
     * Validates a null drug class doesn't pass
     */
    public void testNullDrugClass() {

        errors = new Errors();
        DrugClassVo test = null;

        validator.validate(test, errors);

        assertTrue("Null DrugClass does not return error.", errors.hasErrors());

    }

    /**
     * Validates an empty drug class doesn't pass
     */
    public void testEmptyDrugClass() {

        errors = new Errors();
        DrugClassVo test = new DrugClassVo();

        validator.validate(test, errors);

        assertTrue("Empty DrugClass does not return error.", errors.hasErrors());

    }

    /**
     * Tests the validation cases for Drug Class Code
     */
    public void testCodeValidation() {

        DrugClassVo test = generateValidDrugClass();

        errors = new Errors();
        test.setCode(null);
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Null Code does not return error.", errors.hasErrors());

        errors = new Errors();
        test.setCode("");
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        validator.validate(test, errors);
        assertTrue("Empty code does not return error.", errors.hasErrors());

        errors = new Errors();
        test.setCode("XX11");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Too Short Code not returning error.", errors.hasErrors());

        errors = new Errors();
        test.setCode("11AAA");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Invalid Format Code does not generate Error.", errors.hasErrors());

        errors = new Errors();
        test.setCode("AA1234");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertTrue("Too long Code does not generate Error.", errors.hasErrors());

        errors = new Errors();
        test.setCode("AA123");
        validator.validate(test, errors);
        LOG.debug(getErrors().getLocalizedErrorsAsString());
        assertFalse("Valid Code rejected", errors.hasErrors());

    }

    /**
     * Validates the validation cases for Drug Class Classification
     */
    public void testClassificationValidation() {

        DrugClassVo test = generateValidDrugClass();

        errors = new Errors();
        test.setClassification(null);
        validator.validate(test, errors);

        assertTrue("Null Classification does not return error.", errors.hasErrors());

        errors = new Errors();
        test.setClassification("");
        validator.validate(test, errors);

        assertTrue("Empty Classification does not return error.", errors.hasErrors());

        errors = new Errors();
        test.setClassification("this classification is extremely verbose. It is way too long to pass the 64 characters");
        validator.validate(test, errors);

        String tooLongMsg = "Too Long Classification does not return error.";
        assertTrue(tooLongMsg, errors.hasErrors());

        errors = new Errors();
        test.setClassification("12345678901234567890123456789012345678901234567890123456789012345");
        validator.validate(test, errors);

        assertTrue(tooLongMsg, errors.hasErrors());

        errors = new Errors();
        test.setClassification("1234567890123456789012345678901234567890123456789012345678901234");
        validator.validate(test, errors);

        assertFalse("Valid Classification of 64 chars returns error.", errors.hasErrors());

        errors = new Errors();
        test.setClassification("1");
        validator.validate(test, errors);

        assertFalse("Valid Classification of 1 char returns error.", errors.hasErrors());

    }

    /**
     * Validates drug classification type validation.
     */
    public void testClassificationTypeValidation() {

        DrugClassVo test = generateValidDrugClass();

        errors = new Errors();
        test.setClassificationType(null);
        validator.validate(test, errors);

        assertTrue("Null Classification Type returned no errors", errors.hasErrors());
    }

    /**
     * Generates a default drug class with the bare minimum filled in
     * 
     * @return drugClass
     */
    private DrugClassVo generateValidDrugClass() {

        DrugClassVo ret = new DrugClassVo();

        ret.setCode("AA111");
        ret.setClassification("FAKE DRUG");
        ret.setVuid("12345678901234567890");

        DrugClassificationTypeVo type = new DrugClassificationTypeVo();

        type.setId("1");
        type.setValue("fake");

        ret.setClassificationType(type);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        try {
            Date date = df.parse("01/02/2010");
            VuidStatusHistoryVo effectiveDate =
                    new VuidStatusHistoryVo();
            effectiveDate.setEffectiveDateTime(date);
            effectiveDate.setEffectiveDtmStatus(ItemStatus.INACTIVE);
            List<VuidStatusHistoryVo> list =
                new ArrayList<VuidStatusHistoryVo>();
            list.add(effectiveDate);

            Date date2 = df.parse("01/03/2010");
            VuidStatusHistoryVo effectiveDate2 =
                    new VuidStatusHistoryVo();
            effectiveDate2.setEffectiveDateTime(date2);
            effectiveDate2.setEffectiveDtmStatus(ItemStatus.ACTIVE);
            list.add(effectiveDate2);

            ret.setEffectiveDates(list);
        } catch (ParseException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

        return ret;
    }

    /**
     * getErrors
     * @return errors property
     */
    public Errors getErrors() {

        return errors;
    }

}
