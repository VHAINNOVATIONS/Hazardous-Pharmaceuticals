/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Formats for time
 */
public enum TimeFormat {

    /** STANDARD */
    STANDARD("h:mm a"),

    /** MILITARY */
    MILITARY("HH:mm");

    private String format;

    /**
     * Set the Time Format
     * 
     * @param format The Time Format
     */
    private TimeFormat(String format) {

        this.format = format;
    }

    /**
     * Description
     * @return boolean True if Standard format
     */
    public boolean isStandard() {

        return STANDARD.equals(this);
    }

    /**
     * Description
     * @return True if Military format
     */
    public boolean isMilitary() {

        return MILITARY.equals(this);
    }

    /**
     * Description
     * @return The format
     */
    public String getFormat() {

        return format;
    }
}
