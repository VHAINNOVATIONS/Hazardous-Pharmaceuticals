/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility;


/**
 * Convert any null string to an empty string
 */
public class StringUtility {

    /**
     * Cannot instantiate
     */
    private StringUtility() {
        super();
    }

    /**
     * Convert any null string to an empty string
     * 
     * @param string String to set to empty, if needed
     * @return the original string or an empty string if the original is null
     */
    public static String nullToEmptyString(String string) {
        return string == null ? "" : string;
    }
}
