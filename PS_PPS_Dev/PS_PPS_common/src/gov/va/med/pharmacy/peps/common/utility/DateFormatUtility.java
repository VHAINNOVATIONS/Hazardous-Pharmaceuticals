/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;

import gov.va.med.pharmacy.peps.common.vo.DateFormat;
import gov.va.med.pharmacy.peps.common.vo.TimeFormat;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Format {@link Date} into String based upon a given format.
 * 
 * @see http://java.sun.com/j2se/1.5.0/docs/api/java/text/SimpleDateFormat.html
 */
public class DateFormatUtility {
    
    private static final int YEAR_2000 = 2000;
    private static final String[] FORMATS = getValidFormats();
    private static final String SPACE = " ";

    
    /**
     * Cannot instantiate
     */
    private DateFormatUtility() {
        super();
    }
    
    /**
     * Return a String array of the valid date/time formats
     * 
     * @return String array of valid date/time formats
     */
    private static String[] getValidFormats() {
        List<String> formats = new ArrayList<String>();

        for (DateFormat format : DateFormat.values()) {
            formats.add(format.getFormat());
            formats.add(format.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
            formats.add(format.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
        }

//        formats.add(DateFormat.DAY_MONTH_YEAR.getFormat());
//        formats.add(DateFormat.DAY_MONTH_YEAR.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.DAY_MONTH_YEAR.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
//
//        formats.add(DateFormat.MDYY.getFormat());
//        formats.add(DateFormat.MDYY.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.MDYY.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
//
//        formats.add(DateFormat.MDYYYY.getFormat());
//        formats.add(DateFormat.MDYYYY.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.MDYYYY.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
//
//        formats.add(DateFormat.MDDYY.getFormat());
//        formats.add(DateFormat.MDDYY.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.MDDYY.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
//
//        formats.add(DateFormat.MDDYYYY.getFormat());
//        formats.add(DateFormat.MDDYYYY.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.MDDYYYY.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
//
//        formats.add(DateFormat.MMDDYY.getFormat());
//        formats.add(DateFormat.MMDDYY.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.MMDDYY.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
//
//        formats.add(DateFormat.MMDDYYYY.getFormat());
//        formats.add(DateFormat.MMDDYYYY.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.MMDDYYYY.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());
//
//        formats.add(DateFormat.WEEKDAY_MONTH_DAY_YEAR.getFormat());
//        formats.add(DateFormat.WEEKDAY_MONTH_DAY_YEAR.getFormat() + SPACE + TimeFormat.MILITARY.getFormat());
//        formats.add(DateFormat.WEEKDAY_MONTH_DAY_YEAR.getFormat() + SPACE + TimeFormat.STANDARD.getFormat());

        return formats.toArray(new String[formats.size()]);
    }
    
    /**
     * Convert a String to a Date.
     * 
     * @param s String to convert to Date
     * @return Date as computed from the specified String.
     * @throws ParseException if the specified String can not be converted into a Date.
     */
    public static Date convertToDate(String s) throws ParseException {
        Date date = DateUtils.parseDate(s, FORMATS);

        return add2000YearsToDate(date);
    }

    /**
     * Convert a String to a Date.
     * The parser parses strictly - it does not allow for dates such as "February 942, 1996". 
     * 
     * @param s String to convert to Date
     * @return Date as computed from the specified String.
     * @throws ParseException if the specified String can not be converted into a Date.
     */
    public static Date convertToDateStrictly(String s) throws ParseException {
        Date date = DateUtils.parseDateStrictly(s, FORMATS);

        return add2000YearsToDate(date);
    }

    /**
     * Format the given Date into a String using the given date/time format for the given Locale.
     * 
     * @param date Date to format
     * @param format The format to use
     * @param locale current Locale
     * @return formatted date
     */
    public static String format(Date date, String format, Locale locale) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(format, locale).format(date);
        }
    }

    /**
     * Format the given Date into a String using the user's date/time format preference for the given Locale.
     * 
     * @param date Date to format
     * @param user {@link UserVo} with a date/time format preference
     * @param locale current {@link Locale}
     * @return formatted date
     */
    public static final String format(Date date, UserVo user, Locale locale) {
        return format(date, user.getDateTimeFormatPreference(), locale);
    }

    /**
     * 
     * Adds 2000 years to the date if it is under the year 2000
     *
     * @param date to check and modify
     * @return date in the year 2000
     */
    private static Date add2000YearsToDate(Date date) {

        Date returnDate = date;
        int year = DateUtils.toCalendar(date).get(Calendar.YEAR);

        if (year < YEAR_2000) {
            returnDate = DateUtils.addYears(date, YEAR_2000);
        }

        return returnDate;

    }
}
