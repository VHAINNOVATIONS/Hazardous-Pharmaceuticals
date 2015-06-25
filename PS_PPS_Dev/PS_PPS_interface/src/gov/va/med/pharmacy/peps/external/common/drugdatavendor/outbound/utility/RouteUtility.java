/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility;


import java.io.IOException;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;


/**
 * Convert an FDB route name text value to the FDB route ID
 */
public class RouteUtility {

    /**
     * Cannot instantiate
     */
    private RouteUtility() {
        super();
    }

    /**
     * Convert the route name to the ID
     * 
     * @param name name of the route
     * @return FDB ID of the route
     */
    public static String routeNameToId(String name) {
        try {
            Properties properties = PropertyUtility.loadProperties(RouteUtility.class);

            String value = properties.getProperty(name);

            return StringUtility.nullToEmptyString(value);
        } catch (IOException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.DRUG_DATA_VENDOR);
        }
    }

}
