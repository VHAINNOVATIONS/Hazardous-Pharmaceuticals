/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Language choices.
 */
public enum LanguageChoice {

    /** ENGLISH */
        ENGLISH,

    /** SPANISH */
        SPANISH;

    /**
     * isEnglish
     * 
     * @return boolean if this is English
    */
    public boolean isEnglish() {
        return ENGLISH.equals(this);
    }

    /**
     * isSpanish
     * 
     * @return boolean if this is Spanish
    */
    public boolean isSpanish() {
        return SPANISH.equals(this);
    }
}
