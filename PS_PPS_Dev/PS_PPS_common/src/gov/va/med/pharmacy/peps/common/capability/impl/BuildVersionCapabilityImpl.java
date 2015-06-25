/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.capability.impl;


import java.io.IOException;
import java.util.Properties;

import gov.va.med.pharmacy.peps.common.capability.BuildVersionCapability;
import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;


/**
 * Return the baseline used for the current build and the date the build was run.
 * Chris Walker modified this file on May 31, 2011
 */
public class BuildVersionCapabilityImpl implements BuildVersionCapability {
    private static final String BASELINE_PROPERTY = "baseline";
    private static final String VERSION_PROPERTY = "version";
    private static final String DATE_PROPERTY = "date";

    private String baseline;
    private String version;
    private String date;

    /**
     * Load the version properties.
     */
    public BuildVersionCapabilityImpl() {
        try {
            Properties properties = PropertyUtility.loadProperties(getClass());

            this.baseline = properties.getProperty(BASELINE_PROPERTY);
            this.version = properties.getProperty(VERSION_PROPERTY);
            this.date = properties.getProperty(DATE_PROPERTY);
        } catch (IOException e) {
            throw new CommonException(e, CommonException.VERSION_PROPERTIES_UNAVAILABLE);
        }
    }

    /**
     * Return the name of the baseline used for the current build
     * 
     * @return String baseline name
     * 
     * @see gov.va.med.pharmacy.peps.common.capability.BuildVersionCapability#getBaselineName()
     */
    public String getBaseline() {
        return baseline;
    }

    /**
     * Return the version of the current build.
     * 
     * @return String version
     * 
     * @see gov.va.med.pharmacy.peps.common.capability.BuildVersionCapability#getVersion()
     */
    public String getVersion() {
        return version;
    }

    /**
     * Return the date and time of the current build.
     * 
     * @return String build date
     * 
     * gov.va.med.pharmacy.peps.common.capability.BuildVersionCapability#getBuildDate()
     */
    public String getDate() {
        return date;
    }
}
