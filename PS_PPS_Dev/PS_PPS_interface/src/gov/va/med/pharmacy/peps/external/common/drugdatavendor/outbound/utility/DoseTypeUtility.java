/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility;


import java.io.IOException;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;


/**
 * Convert an FDB dose type name text value to the FDB dose type ID
 */
public class DoseTypeUtility {

    /**
     * Cannot instantiate
     */
    private DoseTypeUtility() {
        super();
    }

    /**
     * Convert the dose type name to the ID
     * 
     * @param name name of the dose type
     * @return FDB ID of the dose type
     */
    public static String doseTypeNameToId(String name) {
        try {
            Properties properties = PropertyUtility.loadProperties(DoseTypeUtility.class);

            String value = properties.getProperty(name);

            return StringUtility.nullToEmptyString(value);
        } catch (IOException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        }
    }

}
