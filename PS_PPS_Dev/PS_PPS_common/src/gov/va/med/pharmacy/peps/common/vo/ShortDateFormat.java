/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types available to an Item
 * 
 * @deprecated 11-19 tmd : use DateFormat
 */
public enum ShortDateFormat {

    /** MDDYY */
    MDDYY,

    /** MDDYYYY */
    MDDYYYY,

    /** MMDDYYYY */
    MMDDYYYY;

    /**
     * Description
     * @return True if Mddyy
     */
    public boolean isMddyy() {
        return MDDYY.equals(this);
    }

    /**
     * Description
     * @return True if Mddyyyy
     */
    public boolean isMddyyyy() {
        return MDDYYYY.equals(this);
    }

    /**
     * Description
     * @return True if Mmddyyyy
     */
    public boolean isMmddyyyy() {
        return MMDDYYYY.equals(this);
    }

}
