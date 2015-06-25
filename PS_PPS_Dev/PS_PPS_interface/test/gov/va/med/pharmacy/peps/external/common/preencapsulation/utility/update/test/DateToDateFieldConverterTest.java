/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.test;


import java.util.Calendar;
import java.util.Date;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;

import junit.framework.TestCase;


/**
 * DateToDateFieldConverterTest's brief summary
 * 
 * Details of DateToDateFieldConverterTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class DateToDateFieldConverterTest extends TestCase {

    private Date date;

    /**
     * description
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(PPSConstants.I1980, Calendar.MAY, PPSConstants.I29);

        date = c.getTime();
    }

    /**
     * description
     */
    public void testPrint() {
        String dateString = DateFormatter.toDateString(date);

        assertEquals("date is incorrect", "05-29-1980", dateString);
    }
}
