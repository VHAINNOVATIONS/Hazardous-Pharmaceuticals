/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Date format for user Preferences
 */
public enum DateFormat {

    /**
     * MDDYY("M/dd/yy").
     */
    MDYY("M/d/yy"),

    /**
     * MDDYYYY("M/d/yyyy").
     */
    MDYYYY("M/d/yyyy"),

    /**
     * MDDYYYY("M/dd/yyyy").
     */
    MDDYYYY("M/dd/yyyy"),

    /**
     * MDDYYYY("M/dd/yy").
     */
    MDDYY("M/dd/yy"),
        
    
    /**
     * MMDDYY("MM/dd/yy").
     */
    MMDDYY("MM/dd/yy"),

    /**
     * MMDDYYYY("MM/dd/yyyy").
     */
    MMDDYYYY("MM/dd/yyyy"),
    
    /**
     *  DAY_MONTH_YEAR("dd-MMM-yyyy").
     */
    DAY_MONTH_YEAR("dd-MMM-yyyy"),
    
    /**
     * WEEKDAY_MONTH_DAY_YEAR("EEE, MMM d, yyyy").
     */
    WEEKDAY_MONTH_DAY_YEAR("EEE, MMM d, yyyy");

    private String dateFormat;

    /**
     * The DateFormat Method
     * 
     * @param format sets the dateFormat
     */
    private DateFormat(String format) {
        dateFormat = format;
    }

    /**
     * The isDayMonthYear method
     * 
     * @return boolean
     */
    public boolean isDayMonthYear() {
        return DAY_MONTH_YEAR.equals(this);
    }

    /**
     * isMddyyyy.
     * @return boolean
     */
    public boolean isMddyyyy() {
        return MDDYYYY.equals(this);
    }

    /**
     * isMmddyyyy.
     * @return boolean
     */
    public boolean isMmddyyyy() {
        return MMDDYYYY.equals(this);
    }

    /**
     * isWeekdayMonthDayYear.
     * @return boolean
     */
    public boolean isWeekdayMonthDayYear() {
        return WEEKDAY_MONTH_DAY_YEAR.equals(this);
    }

    /**
     * Returns the date format.
     * 
     * @return String
     */
    public String getFormat() {
        return dateFormat;
    }
}
