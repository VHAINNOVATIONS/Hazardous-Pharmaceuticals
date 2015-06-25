/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * YES/NO selection that allows for an "empty" (no selection) value by being null.
 */
public enum YesNo {

    /** YES */
    YES,

    /** NO */
    NO;

    /**
     * Test if this instance of YesNo is equal to YES.
     * 
     * @return true if this instance is equal to YES
     */
    public boolean isYes() {

        return YES.equals(this);
    }

    /**
     * Test if this instance of YesNo is equal to NO.
     * 
     * @return true if this instance is equal to NO
     */
    public boolean isNo() {

        return NO.equals(this);
    }
}
