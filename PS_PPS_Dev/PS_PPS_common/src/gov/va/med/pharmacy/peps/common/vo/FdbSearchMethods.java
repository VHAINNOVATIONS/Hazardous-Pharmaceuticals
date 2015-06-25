/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * FdbSearchMethods filter
 */
public enum FdbSearchMethods {
    
    /**
     * ACTIVE
     */
    BEGINS_WITH_LITERAL, 
    
    /**
     * CONTAINS_LITERAL
     */
    CONTAINS_LITERAL, 
    
    /**
     * BEGINS_WITH_EACH_STRING
     */
    BEGINS_WITH_EACH_STRING, 
 
    /**
     * CONTAINS_EACH_STRING
     */
    CONTAINS_EACH_STRING;
    
    /**
     * isBeginsWithLiteral.
     * @return boolean true if this FdbSearchMethods filter is BEGINS_WITH_LITERAL
     */
    public boolean isBeginsWithLiteral() {
        return BEGINS_WITH_LITERAL.equals(this);
    }

    /**
     * isContainsLiteral.
     * @return boolean true if this FdbSearchMethods filter is CONTAINS_LITERAL
     */
    public boolean isContainsLiteral() {
        return CONTAINS_LITERAL.equals(this);
    }
    
    /**
     * isBeginsWithEachString.
     * @return boolean true if this FdbSearchMethods filter is BEGINS_WITH_EACH_STRING
     */
    public boolean isBeginsWithEachString() {
        return BEGINS_WITH_EACH_STRING.equals(this);
    }

    /**
     * isContainsEachString.
     * @return boolean true if this FdbSearchMethods filter is CONTAINS_EACH_STRING
     */
    public boolean isContainsEachString() {
        return CONTAINS_EACH_STRING.equals(this);
    }
    
}
