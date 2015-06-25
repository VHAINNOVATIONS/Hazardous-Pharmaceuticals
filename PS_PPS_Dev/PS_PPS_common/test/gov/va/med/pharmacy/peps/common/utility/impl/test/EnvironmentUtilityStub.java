/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.impl.test;


import gov.va.med.pharmacy.peps.common.utility.impl.EnvironmentUtilityImpl;


/**
 * Provide methods executed while in development to stub out the ability to set the environment as local-1, local-2, and
 * national.
 */
public class EnvironmentUtilityStub extends EnvironmentUtilityImpl {
    private static final String LOCAL_ONE_SITE_NUMBER = "671";
    private static final String LOCAL_TWO_SITE_NUMBER = "681";
    private static final String NATIONAL_SITE_NUMBER = "999";

    /**
     * Override the discovered local/national value. Set as a local-1 environment.
     * <p>
     * Called by Spring as the init-method at local-1.
     */
    public void setLocalOne() {
        setSiteNumber(LOCAL_ONE_SITE_NUMBER);
        setLocal(true);
    }

    /**
     * Override the discovered local/national value. Set as a local-2 environment.
     * <p>
     * Called by Spring as the init-method at local-2.
     */
    public void setLocalTwo() {
        setSiteNumber(LOCAL_TWO_SITE_NUMBER);
        setLocal(true);
    }

    /**
     * Override the discovered local/national value. Set as a national environment.
     * <p>
     * Called by Spring as the init-method at national.
     */
    public void setNational() {
        setSiteNumber(NATIONAL_SITE_NUMBER);
        setLocal(false);
    }
}
