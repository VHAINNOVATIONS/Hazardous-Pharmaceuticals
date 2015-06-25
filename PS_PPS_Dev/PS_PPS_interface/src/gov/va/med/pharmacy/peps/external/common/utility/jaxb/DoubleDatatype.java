/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.utility.jaxb;


import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;


/**
 * JAXB double datatype.
 */
public class DoubleDatatype {

    /**
     * Private constructor.
     */
    private DoubleDatatype() {
    }

    /**
     * Print double datatype without scientific notation.
     * 
     * @param value double value
     * @return non scientific notation
     */
    public static String print(double value) {
        return NumberFormatUtility.format(value);
    }

    /**
     * Convert string value to a double.
     * 
     * @param value string value
     * @return double
     */
    public static double parse(String value) {
        return Double.valueOf(value);
    }
}
