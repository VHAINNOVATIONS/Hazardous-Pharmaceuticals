/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.capability;


/**
 * Return the baseline used for the current build and the date the build was run. This is used to populate
 * the Presentation layer build numbers.
 */
public interface BuildVersionCapability {

    /**
     * Return the name of the baseline used for the current build.
     * 
     * @return String baseline name
     */
    String getBaseline();

    /**
     * Return the version of the current build.
     * 
     * @return String version
     */
    String getVersion();

    /**
     * Return the date and time of the current build.
     * 
     * @return String build date
     */
    String getDate();
}
