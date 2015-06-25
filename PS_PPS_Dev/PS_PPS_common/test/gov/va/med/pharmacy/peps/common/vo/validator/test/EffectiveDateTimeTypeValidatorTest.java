/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator.test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.validator.EffectiveDateTimeTypeValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;

import junit.framework.TestCase;


/**
 * EffectiveDateTimeTypeValidatorTest
 * 
 */
public class EffectiveDateTimeTypeValidatorTest extends TestCase {

    private static final Logger LOG = Logger.getLogger(EffectiveDateTimeTypeValidatorTest.class);
    private EffectiveDateTimeTypeValidator validator = new EffectiveDateTimeTypeValidator();
    private Errors errors;
    private Collection<VuidStatusHistoryVo> test;

    /**
     * Validates a null Effective DateTime Type doesn't pass
     */
    public void testNullEffectiveDateTimeType() {
        errors = new Errors();
        test = null;

        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for null EffectiveDateTimeType.",
                   errors.hasErrors());

    }

    /**
     * Validates an empty Effective DateTime Type doesn't pass
     */
    public void testEmptyEffectiveDateTimeType() {
        errors = new Errors();
        test = new ArrayList<VuidStatusHistoryVo>();

        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for empty EffectiveDateTimeType.",
                   errors.hasErrors());

    }

    /**
     * Validates the validation cases for Effective DateTime Type EffectiveDateTime
     */
    public void testEffectiveDateTimeValidation() {

        test = new ArrayList<VuidStatusHistoryVo>();
        VuidStatusHistoryVo history = new VuidStatusHistoryVo();

        DateFormat df =
            new SimpleDateFormat(gov.va.med.pharmacy.peps.common.vo.DateFormat.MMDDYYYY.getFormat(), Locale.getDefault());

        errors = new Errors();

        history.setEffectiveDateTime(null);
        test.add(history);

        validator.validate(test, errors);
        LOG.info(getErrors().getLocalizedErrorsAsString());
        assertTrue("Expected error did not occur for Effective DateTime Name.",
                   errors.hasErrors());

        test.clear();
        errors = new Errors();

        try {
            Date date = df.parse("01/02/2010");
            history.setEffectiveDateTime(date);
        } catch (ParseException e) {
            LOG.error(e);
        }
        
        test.add(history);
        validator.validate(test, errors);
        assertFalse("Error occurred for valid Effective DateTime in the past.",
            errors.hasErrors());
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        history.setEffectiveDateTime(cal.getTime());

        test.add(history);
        validator.validate(test, errors);
        assertFalse("Error occurred for valid Effective DateTime created today.",
            errors.hasErrors());

    }

    /**
     * getErrors for the EffectiveDateTimetypeValidatorTest
     * @return errors property
     */
    public Errors getErrors() {
        return errors;
    }

}
