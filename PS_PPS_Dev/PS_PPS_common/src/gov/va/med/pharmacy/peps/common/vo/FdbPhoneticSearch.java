/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * FdbPhoneticSearch filter
 */
public enum FdbPhoneticSearch {
    
    /**
     * LITERAL
     */
    LITERAL, 
    
    /**
     * LITERAL_AND_PHONETIC
     */
    LITERAL_AND_PHONETIC, 
    
    /**
     * PHONETIC
     */
    PHONETIC;
    
    /**
     * isLiteral.
     * @return boolean true if this FdbPhoneticSearch filter is LITERAL
     */
    public boolean isLiteral() {
        return LITERAL.equals(this);
    }

    /**
     * isLiteralAndPhonetic.
     * @return boolean true if this FdbPhoneticSearch filter is LITERAL_AND_PHONETIC
     */
    public boolean isLiteralAndPhonetic() {
        return LITERAL_AND_PHONETIC.equals(this);
    }
    
    /**
     * isRetired.
     * @return boolean true if this FdbPhoneticSearch filter is PHONETIC
     */
    public boolean isPhonetic() {
        return PHONETIC.equals(this);
    }

}
