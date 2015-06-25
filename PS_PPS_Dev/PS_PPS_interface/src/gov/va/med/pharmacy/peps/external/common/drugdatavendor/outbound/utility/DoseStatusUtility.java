/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility;


import java.io.IOException;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;

import firstdatabank.dif.FDBDOSEStatus;


/**
 * Strip any reference to FDB from the dose status
 */
public class DoseStatusUtility {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DoseStatusUtility.class);

    /**
     * Cannot instantiate
     */
    private DoseStatusUtility() {
        super();
    }

    /**
     * Get the description of the status and strip any reference to FDB
     * 
     * @param status FDBDOSEStatus to convert
     * @return String status without reference to FDB
     * @throws IOException if cannot load properties file
     */
    public static String convert(FDBDOSEStatus status) throws IOException {
        Properties properties = PropertyUtility.loadProperties(DoseStatusUtility.class);

        String result = properties.getProperty(status.toString());

        if (result == null) {
            LOG.error(status.toString() + " FDBDOSEStatus did not map successfully!");

            return status.toString();
        } else {
            return result;
        }
    }
}
