/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.ResourceBundleUtility;


/**
 * An environment the PEPS application is run in.
 */
public enum Environment {
    
    /**
     * LOCAL Environment
     */
    LOCAL,
    
    /**
     * NATIONAL Environment.
     */
    NATIONAL;

    /**
     * isLocal.
     * @return boolean true if this is a local environment
     */
    public boolean isLocal() {
        return LOCAL.equals(this);
    }

    /**
     * isNational.
     * @return boolean true if this is a national environment
     */
    public boolean isNational() {
        return NATIONAL.equals(this);
    }

    /**
     * Localize the name "LOCAL" and "NATIONAL".
     * 
     * @param locale {@link Locale} to localize
     * @return String localized name
     */
    public String getLocalizedName(Locale locale) {
        return ResourceBundleUtility.getResourceBundleValue(getClass(), this.toString(), locale);
    }
}
