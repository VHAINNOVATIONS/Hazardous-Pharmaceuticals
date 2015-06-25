/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import gov.va.med.pharmacy.peps.common.vo.Environment;


/**
 * Determine if the PEPS application is deployed at a Local or National environment.
 * 
 * Note that this code will only work if Domain, Interface, Presentation, or Service code and associated resources (i.e., the
 * Spring configuration files) are on the class path. This situation occurs when deployed and when running unit tests in
 * Domain, Interface, Presentation, or Service, but not when running unit tests in Common.
 */
public interface EnvironmentUtility {

    /**
     * Return true if the PEPS application is deployed at a Local environment.
     * 
     * The environment is determined by checking what Spring configuration files are available. Since the Spring
     * configuration files are required for PEPS to work, if a local Spring configuration folder is present
     * (xml/local/spring), then the method returns true. Otherwise, the method returns false.
     * 
     * @return boolean true if the PEPS application is deployed at a Local environment
     */
    boolean isLocal();

    /**
     * Return true if the PEPS application is deployed at a National environment.
     * 
     * The environment is determined by checking if the PEPS application is not deployed at a Local environment.
     * 
     * @return boolean true if the PEPS application is deployed at a National environment
     */
    boolean isNational();

    /**
     * Get the environment in which the PEPS application is deployed.
     * 
     * @return {@link Environment#LOCAL} or {@link Environment#NATIONAL}
     */
    Environment getEnvironment();

    /**
     * Get the site number. The site number is also known as the VistA station number.
     * 
     * @return String site number
     */
    String getSiteNumber();

    /**
     * True if the system is running in a Linux OS.
     * <p>
     * Linux is presumed if the OS is not MS Windows.
     * 
     * @return boolean
     * 
     * @see #isWindows()
     */
    boolean isLinux();

    /**
     * True if the system is running in a MS Windows OS.
     * 
     * @return boolean
     */
    boolean isWindows();
}
