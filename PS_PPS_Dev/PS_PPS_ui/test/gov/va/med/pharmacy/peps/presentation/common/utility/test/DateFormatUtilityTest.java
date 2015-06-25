/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility.test;


import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DateFormat;
import gov.va.med.pharmacy.peps.common.vo.TimeFormat;

import junit.framework.TestCase;


/**
 * Test that the DateFormatUtility converts SimpleDateFormats into the correct format.
 */
public class DateFormatUtilityTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(DateFormatUtilityTest.class);
    
    /**
     * Print the name of the current test method
     * 
     * @throws Exception if error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        LOG.debug("----------------------------" + getName() + "--------------------------");
    }

    /**
     * Tests the MDDYY format with both time formats uses a randomly selected date to hard-code the results
     */
    public void testMddyyFormats() {

        // Calender information for inactivation date
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2008, Calendar.APRIL, PPSConstants.I5, PPSConstants.I13, PPSConstants.I23);
        
        // I really don't care that its deprecated, just want a set date/time
      //  Date predetermined = new Date(108, 3, 5, 13, 23, 45);

        String format = DateFormat.MDDYYYY.getFormat() + " " + TimeFormat.STANDARD.getFormat();

        String dateFormat = DateFormatUtility.format(calendar1.getTime(), format, Locale.US);

        assertEquals("MDDYY format with standard time incorrect", "4/05/2008 1:23 PM", dateFormat);

        format = DateFormat.MDDYYYY.getFormat() + " " + TimeFormat.MILITARY.getFormat();

        dateFormat = DateFormatUtility.format(calendar1.getTime(), format, Locale.US);

        assertEquals("MDDYY format with Military time incorrect", "4/05/2008 13:23", dateFormat);
    }

    /**
     * Tests the MMDDYY format with both time formats uses a randomly selected date to hard-code the results
     */
    public void testMmddyyFormats() {

        // I really don't care that its deprecated, just want a set date/time
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2008, Calendar.APRIL, PPSConstants.I5, PPSConstants.I13, PPSConstants.I23);

        String format = DateFormat.MMDDYYYY.getFormat() + " " + TimeFormat.STANDARD.getFormat();

        String dateFormat = DateFormatUtility.format(calendar1.getTime(), format, Locale.US);

        assertEquals("1 - MMDDYY format with standard time incorrect", "04/05/2008 1:23 PM", dateFormat);

        format = DateFormat.MMDDYYYY.getFormat() + " " + TimeFormat.MILITARY.getFormat();

        dateFormat = DateFormatUtility.format(calendar1.getTime(), format, Locale.US);

        assertEquals("1 - MMDDYY format with Military time incorrect", "04/05/2008 13:23", dateFormat);
    }

    /**
     * Tests the DAY_MONTH_YEAR format with both time formats uses a randomly selected date to hard-code the results
     */
    public void testDayMonthYearFormats() {
        
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2008, Calendar.APRIL, PPSConstants.I5, PPSConstants.I13, PPSConstants.I23);
        Date predetermined = calendar1.getTime();

        String format = DateFormat.DAY_MONTH_YEAR.getFormat() + " " + TimeFormat.STANDARD.getFormat();

        String dateFormat = DateFormatUtility.format(predetermined, format, Locale.US);

        assertEquals("2 - MMDDYY format with standard time incorrect", "05-Apr-2008 1:23 PM", dateFormat);

        format = DateFormat.DAY_MONTH_YEAR.getFormat() + " " + TimeFormat.MILITARY.getFormat();

        dateFormat = DateFormatUtility.format(predetermined, format, Locale.US);
        assertEquals("2 - MMDDYY format with Military time incorrect", "05-Apr-2008 13:23", dateFormat);
    }

    /**
     * Tests the WEEKDAY_MONTH_DAY_YEAR format with both time formats uses a randomly selected date to hard-code the results
     */
    public void testWeekdayFormats() {

        // I really don't care that its deprecated, just want a set date/time
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(PPSConstants.I2008, Calendar.APRIL, PPSConstants.I5, PPSConstants.I13, PPSConstants.I23);
        Date predetermined = calendar1.getTime();

        String format = DateFormat.WEEKDAY_MONTH_DAY_YEAR.getFormat() + " " + TimeFormat.STANDARD.getFormat();

        String dateFormat = DateFormatUtility.format(predetermined, format, Locale.US);

        assertEquals("MMDDYY format with standard time incorrect", "Sat, Apr 5, 2008 1:23 PM", dateFormat);

        format = DateFormat.WEEKDAY_MONTH_DAY_YEAR.getFormat() + " " + TimeFormat.MILITARY.getFormat();

        dateFormat = DateFormatUtility.format(predetermined, format, Locale.US);
        assertEquals("MMDDYY format with Military time incorrect", "Sat, Apr 5, 2008 13:23", dateFormat);
    }

}
