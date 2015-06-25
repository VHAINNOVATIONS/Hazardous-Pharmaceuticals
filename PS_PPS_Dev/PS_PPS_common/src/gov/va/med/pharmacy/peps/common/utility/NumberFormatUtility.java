/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.text.DecimalFormat;


/**
 * Format {@link Double} and {@link Float} with decimals into JCAHO format.
 * <p>
 * This prevents {@link Double#toString(double)} or {@link String#valueOf(double)} from formatting the {@link Double} in
 * scientific notation.
 */
public class NumberFormatUtility {
    private static final String JCAHO_FORMAT = "0.#############################################################";

    /**
     * Cannot instantiate
     */
    private NumberFormatUtility() {
        super();
    }
    
    /**
     * Format per JCAHO rules.
     * 
     * @see http://www.jointcommission.org/NR/rdonlyres/2329F8F5-6EC5-4E21-B932-54B2B7D53F00/0/dnu_list.pdf
     * 
     * @param number Number
     * @return formatted String
     */
    public static String format(Number number) {
        String formatted = null;

        if (number instanceof Double) {
            DecimalFormat decimalFormat = new DecimalFormat(JCAHO_FORMAT);
            formatted = decimalFormat.format((Double) number);
        } else if (number instanceof Float) {
            DecimalFormat decimalFormat = new DecimalFormat(JCAHO_FORMAT);
            formatted = decimalFormat.format((Float) number);
        } else if (number != null) {
            formatted = String.valueOf(number);
        }

        return formatted;
    }


}
