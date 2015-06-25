/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.impl;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.common.vo.Environment;


/**
 * Determine if the PEPS application is deployed at a Local or National environment.
 * 
 * Note that this code will only work if Domain, Interface, Presentation, or Service code and associated resources (i.e., the
 * Spring configuration files) are on the class path. This situation occurs when deployed and when running unit tests in
 * Domain, Interface, Presentation, or Service, but not when running unit tests in Common.
 */
@Component
@Scope(value = "application")
public class EnvironmentUtilityImpl implements EnvironmentUtility {
    private static final String SITE_NUMBER_PROPERTY = "site.number";
    private static final String LOCAL_SPRING_FOLDER = "xml/local/spring";
    private static final boolean WINDOWS = System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0;

    private String siteNumber;
    private boolean local;

    /**
     * Set the site number and if the current environment is local.
     */
    public EnvironmentUtilityImpl() {
        super();

        siteNumber = PropertyUtility.getOverriddenProperty(SITE_NUMBER_PROPERTY);
        local = Thread.currentThread().getContextClassLoader().getResource(LOCAL_SPRING_FOLDER) != null;
    }

    /**
     * Return true if the PEPS application is deployed at a Local environment.
     * 
     * The environment is determined by checking what Spring configuration files are available. Since the Spring
     * configuration files are required for PEPS to work, if a local Spring configuration folder is present
     * (xml/local/spring), then the method returns true. Otherwise, the method returns false.
     * 
     * @return boolean true if the PEPS application is deployed at a Local environment
     */
    public boolean isLocal() {
        return local;
    }

    /**
     * Return true if the PEPS application is deployed at a National environment.
     * 
     * The environment is determined by checking if the PEPS application is not deployed at a Local environment.
     * 
     * @return boolean true if the PEPS application is deployed at a National environment
     */
    public boolean isNational() {
        return !isLocal();
    }

    /**
     * Get the environment in which the PEPS application is deployed.
     * 
     * @return {@link Environment#LOCAL} or {@link Environment#NATIONAL}
     */
    public Environment getEnvironment() {
        if (isLocal()) {
            return Environment.LOCAL;
        } else {
            return Environment.NATIONAL;
        }
    }

    /**
     * The site number is also known as the VistA station number.
     * 
     * @return String site number
     */
    public String getSiteNumber() {
        return "999";
       // return siteNumber;
    }

    /**
     * True if the system is running in a Linux OS.
     * <p>
     * Linux is presumed if the OS is not MS Windows.
     * 
     * @return boolean
     * 
     * @see #isWindows()
     */
    public boolean isLinux() {
        return !isWindows();
    }

    /**
     * True if the system is running in a MS Windows OS.
     * 
     * @return boolean
     */
    public boolean isWindows() {
        return WINDOWS;
    }

    /**
     * The site number is also known as the VistA station number.
     * 
     * @param siteNumber siteNumber property
     */
    protected void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    /**
     * True if this is a local instance.
     * 
     * @param local local property
     */
    protected void setLocal(boolean local) {
        this.local = local;
    }
}
