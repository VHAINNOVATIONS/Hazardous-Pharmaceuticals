/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.utility.jaxb;




/**
 * JAXB float datatype.
 */
public class FloatDatatype {

    /**
     * Private constructor.
     */
    private FloatDatatype() {
    }

    /**
     * Print float datatype without scientific notation.
     * 
     * @param value float value
     * @return non scientific notation
     */
    public static String print(float value) {
        return Float.toString(value);
    }

    /**
     * Convert string value to a float.
     * 
     * @param value string value
     * @return float
     */
    public static float parse(String value) {
        return Float.valueOf(value);
    }
}
