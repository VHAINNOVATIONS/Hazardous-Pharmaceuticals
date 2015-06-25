/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;

import junit.framework.TestCase;


/**
 * Verify JCAHO numeric formatting rules.
 * 
 * @see http://www.jointcommission.org/NR/rdonlyres/2329F8F5-6EC5-4E21-B932-54B2B7D53F00/0/dnu_list.pdf
 */
public class NumberFormatUtilityTest extends TestCase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NumberFormatUtilityTest.class);
    private static final double D_0_1 = 0.1;
    private static final double D_01_1 = 01.1;
    private static final double D_1_00040 = 1.00040;
    

    /**
     * setUp
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.debug("-------------------- " + getName() + " -------------------");
    }

    /**
     * Remove trailing zero
     */
    public void testDateComparison() {

        try {
            String dateFormat = "MM/dd/yyyy";
            String str1 = "04/19/0419";
            String str2 = "09/12/2012";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date date1 = sdf.parse(str1);
            Date date2 = sdf.parse(str2);
            LOG.info("dates are " + sdf.format(date1) + " and " + sdf.format(date2));
    
            if (date1.after(date2)) {
                LOG.info(sdf.format(date1) + " is after " + sdf.format(date2)); 
            } else {
                LOG.info(sdf.format(date1) + " is NOT after " + sdf.format(date2));
               
            }
        } catch (ParseException e) {
            fail("parse error " + e.toString());
            
        }

        
    }
    
    
    /**
     * Remove trailing zero
     */
    public void testTrailingZero() {
        double number = 1.0;

        String value = NumberFormatUtility.format(number);
        LOG.debug(value);

        assertEquals("Should not have decimal point", -1, value.indexOf("."));
        assertFalse("Should not end in zero.", value.endsWith("0"));
    }

    /**
     * Remove trailing zero, but keep decimal
     */
    public void testTrailingNumber() {

        String value = NumberFormatUtility.format(D_1_00040);
        LOG.debug(value);

        assertEquals("Should have decimal point.", 1, value.indexOf("."));
        assertFalse("Should not end in zero", value.endsWith("0"));
    }

    /**
     * Keep leading zero
     */
    public void testLeadingZero() {

        String value = NumberFormatUtility.format(D_0_1);
        LOG.debug(value);

        assertEquals("Should have decimal point!", 1, value.indexOf("."));
        assertTrue("Should have leading zero", value.startsWith("0"));
    }

    /**
     * Remove leading zero, but keep number.
     */
    public void testLeadingNumber() {

        String value = NumberFormatUtility.format(D_01_1);
        LOG.debug(value);

        assertEquals("Should have decimal point", 1, value.indexOf("."));
        assertFalse("Should not have leading zero", value.startsWith("0"));
    }
}
