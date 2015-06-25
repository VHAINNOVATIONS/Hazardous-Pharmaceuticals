/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Pharmacy System Pmis Language
 */
public enum PharmacySystemPmisLanguage {

    /** ENGLISH */
    ENGLISH,

    /** SPANISH */
    SPANISH;

    /**
     * isEnglish
     * 
     * @return boolean true if this PharmacySystemPharmacySystemPmisLanguage is ENGLISH
     */
    public boolean isEnglish() {

        return ENGLISH.equals(this);
    }

    /**
     * isSpanish
     * 
     * @return boolean true if this PharmacySystemPharmacySystemPmisLanguage is SPANISH
     */
    public boolean isSpanish() {

        return SPANISH.equals(this);
    }
}
