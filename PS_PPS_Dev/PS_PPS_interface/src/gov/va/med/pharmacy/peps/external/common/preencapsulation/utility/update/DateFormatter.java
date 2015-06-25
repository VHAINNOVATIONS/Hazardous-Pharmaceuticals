/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Convert to VistA data format.
 */
public class DateFormatter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());

    /**
     * Constructor
     *
     */
    private DateFormatter() {
        super();
    }

    /**
     * Convert a date to VistA data format.
     * 
     * @param date date
     * @return formatted date
     */
    public static synchronized String toDateString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
