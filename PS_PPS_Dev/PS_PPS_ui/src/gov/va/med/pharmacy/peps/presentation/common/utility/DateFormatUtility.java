/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Convert the standard localized SimpleDateFormat into the custom JavaScript datejs format.
 * 
 * @see http://java.sun.com/j2se/1.5.0/docs/api/java/text/SimpleDateFormat.html
 * @see http://code.google.com/p/datejs/wiki/FormatSpecifiers
 */
public class DateFormatUtility {
    private static final Map<Character, String[]> FORMAT_MAP = new HashMap<Character, String[]>();
    private static final int ABBREVIATED_FORM_INDEX = 0;
    private static final int FULL_FORM_INDEX = 1;
    private static final int MONTH_NUMBER_FORM_INDEX = 0;
    private static final int MONTH_ABBREVIATED_FORM_INDEX = 1;
    private static final int MONTH_FULL_FORM_INDEX = 2;

    private static final List<Character> NUMBER_TYPES = new ArrayList<Character>();
    private static final Character MONTH_TYPE = 'M';
    private static final List<Character> TEXT_TYPES = new ArrayList<Character>();
    private static final Character YEAR_TYPE = 'y';
    private static final List<Character> UNSUPPORTED_TYPES = new ArrayList<Character>();

    private static final int TEXT_FULL_FORM_COUNT = 4;
    private static final int NUMBER_FULL_FORM_COUNT = 2;
    private static final int YEAR_FULL_FORM_COUNT = 4;
    private static final int MONTH_ABBREVIATED_FORM_COUNT = 3;
    private static final int MONTH_FULL_FORM_COUNT = 4;

    private static final String TWENTY_FOUR_HOUR_ONE = "H";
    private static final String TWENTY_FOUR_HOUR_TWO = "k";

    static {
        FORMAT_MAP.put('G', new String[] {"", ""}); //   Era designator  Text    AD
        FORMAT_MAP.put('y', new String[] {"yy", "yyyy"}); //   Year    Year    1996; 96
        FORMAT_MAP.put('M', new String[] {"M", "MMM", "MMMM"}); //   Month in year   Month   July; Jul; 07
        FORMAT_MAP.put('w', new String[] {"", ""}); //   Week in year    Number  27
        FORMAT_MAP.put('W', new String[] {"", ""}); //   Week in month   Number  2
        FORMAT_MAP.put('D', new String[] {"", ""}); //   Day in year     Number  189
        FORMAT_MAP.put('d', new String[] {"d", "dd"}); //   Day in month    Number  10
        FORMAT_MAP.put('F', new String[] {"", ""}); //   Day of week in month    Number  2
        FORMAT_MAP.put('E', new String[] {"ddd", "dddd"}); //   Day in week     Text    Tuesday; Tue
        FORMAT_MAP.put('a', new String[] {"tt", "tt"}); //   Am/pm marker    Text    PM
        FORMAT_MAP.put('H', new String[] {"H", "HH"}); //   Hour in day (0-23)  Number  0
        FORMAT_MAP.put('k', new String[] {"", ""}); //   Hour in day (1-24)  Number  24
        FORMAT_MAP.put('K', new String[] {"", ""}); //   Hour in am/pm (0-11)    Number  0
        FORMAT_MAP.put('h', new String[] {"h", "hh"}); //   Hour in am/pm (1-12)    Number  12
        FORMAT_MAP.put('m', new String[] {"m", "mm"}); //   Minute in hour  Number  30
        FORMAT_MAP.put('s', new String[] {"s", "ss"}); //   Second in minute    Number  55
        FORMAT_MAP.put('S', new String[] {"", ""}); //   Millisecond     Number  978
        FORMAT_MAP.put('z', new String[] {"", ""}); //   Time zone   General time zone   Pacific Standard Time; PST; GMT-08:00
        FORMAT_MAP.put('Z', new String[] {"", ""}); //   Time zone   RFC 822 time zone   -0800

        NUMBER_TYPES.addAll(Arrays.asList(new Character[] {'d', 'H', 'h', 'm', 's'}));
        TEXT_TYPES.addAll(Arrays.asList(new Character[] {'E', 'a'}));
        UNSUPPORTED_TYPES.addAll(Arrays.asList(new Character[] {'D', 'F', 'G', 'K', 'k', 'w', 'W', 'S', 'z', 'Z'}));

        // datejs format characters
        // s      The seconds of the minute between 0-59.                                      "0" to "59"
        // ss     The seconds of the minute with leading zero if required.                     "00" to "59"
        // m      The minute of the hour between 0-59.                                         "0"  or "59"
        // mm     The minute of the hour with leading zero if required.                        "00" or "59"
        // h      The hour of the day between 1-12.                                            "1"  to "12"
        // hh     The hour of the day with leading zero if required.                           "01" to "12"
        // H      The hour of the day between 0-23.                                            "0"  to "23"
        // HH     The hour of the day with leading zero if required.                           "00" to "23"
        // d      The day of the month between 1 and 31.                                       "1"  to "31"
        // dd     The day of the month with leading zero if required.                          "01" to "31"
        // ddd    Abbreviated day name. $C.abbreviatedDayNames.                                "Mon" to "Sun" 
        // dddd   The full day name. $C.dayNames.                                              "Monday" to "Sunday"
        // M      The month of the year between 1-12.                                          "1" to "12"
        // MM     The month of the year with leading zero if required.                         "01" to "12"
        // MMM    Abbreviated month name. $C.abbreviatedMonthNames.                            "Jan" to "Dec"
        // MMMM   The full month name. $C.monthNames.                                          "January" to "December"
        // yy     The year as a two-digit number.                                              "99" or "08"
        // yyyy   The full four digit year.                                                    "1999" or "2008"
        // t      Displays the first character of the A.M./P.M. designator.                    "A" or "P"
        // tt     Displays the A.M./P.M. designator.                                           "AM" or "PM"
        // S      The ordinal suffix ("st, "nd", "rd" or "th") of the current day.             "st, "nd", "rd" or "th"
    }

    /**
     * Cannot instantiate
     */
    private DateFormatUtility() {
        super();
    }

    /**
     * Convert the {@link DateFormat#SHORT} for date and time into the JavaScript Calendar format.
     * 
     * @param locale current Locale
     * @return String JavaScript Calendar format
     */
    public static String getDatejsFormat(Locale locale) {
        String standardFormat = getStandardFormat(locale);
        String datejsFormat = "";

        if (standardFormat.length() > 0) {
            datejsFormat = convertFormat(standardFormat);
        }

        return datejsFormat;
    }

    /**
     * Convert the {@link DateFormat#SHORT} for date and time into the JavaScript Calendar format.
     * 
     * @param locale current Locale
     * @param format format
     * @return String JavaScript Calendar format
     */
    public static String getDatejsFormat(String format, Locale locale) {
        String standardFormat = new SimpleDateFormat(format, locale).toLocalizedPattern();
        String datejsFormat = "";

        if (standardFormat.length() > 0) {
            datejsFormat = convertFormat(standardFormat);
        }

        return datejsFormat;
    }
    
    /**
     * Convert the Standard Java date format into the datejs format.
     * 
     * @param standardFormat non-null and greater than zero length String
     * @return String date format for datejs
     */
    private static String convertFormat(String standardFormat) {
        StringBuffer datejsFormat = new StringBuffer();
        Character previousCharacter = standardFormat.charAt(0);
        int count = 0;

        for (int i = 0; i <= standardFormat.length(); i++) {
            Character character;

            if (i < standardFormat.length()) {
                character = standardFormat.charAt(i);
            } else {
                character = '!'; // dummy character that is not part of the SimpleDateFormat characters
            }

            if (!character.equals(previousCharacter)) {
                String conversion = convertCharacter(previousCharacter, count);
                datejsFormat.append(conversion);
                previousCharacter = character;
                count = 0;
            }

            count++;
        }

        return datejsFormat.toString();
    }

    /**
     * Convert the given SimpleDateFormat character into the datejs format.
     * 
     * @param character Character to convert
     * @param count number of times this character has occurred in a row
     * @return String converted character
     */
    private static String convertCharacter(Character character, int count) {
        String conversion = "";

        if (MONTH_TYPE.equals(character)) {
            conversion = convertMonth(count);
        } else if (YEAR_TYPE.equals(character)) {
            conversion = convertYear(count);
        } else if (NUMBER_TYPES.contains(character)) {
            conversion = convertNumber(character, count);
        } else if (TEXT_TYPES.contains(character)) {
            conversion = convertText(character, count);
        } else {
            conversion = convertUnknown(character, count);
        }

        return conversion;
    }

    /**
     * Convert the given unknown SimpleDateFormat type into the datejs format.
     * 
     * If the character is a part of the UNSUPPORTED_TYPES, then return an empty String. Otherwise just return the character
     * repeated count times as a String.
     * 
     * @param character unknown SimpleDateFormat type
     * @param count number of times the character occurred in a row
     * @return datejs format
     */
    private static String convertUnknown(Character character, int count) {
        String conversion = "";

        if (!UNSUPPORTED_TYPES.contains(character)) {
            for (int j = 0; j < count; j++) {
                conversion = character.toString();
            }
        }

        return conversion;
    }

    /**
     * Convert the given SimpleDateFormat text type into the datejs format.
     * 
     * @param character SimpleDateFormat text
     * @param count number of times the text type occurred in a row
     * @return datejs format
     */
    private static String convertText(Character character, int count) {
        String conversion;

        if (count >= TEXT_FULL_FORM_COUNT) {
            conversion = FORMAT_MAP.get(character)[FULL_FORM_INDEX];
        } else {
            conversion = FORMAT_MAP.get(character)[ABBREVIATED_FORM_INDEX];
        }

        return conversion;
    }

    /**
     * Convert the given SimpleDateFormat number type into the datejs format.
     * 
     * @param character SimpleDateFormat number
     * @param count number of times the number type occurred in a row
     * @return datejs format
     */
    private static String convertNumber(Character character, int count) {
        String conversion;

        if (count >= NUMBER_FULL_FORM_COUNT) {
            conversion = FORMAT_MAP.get(character)[FULL_FORM_INDEX];
        } else {
            conversion = FORMAT_MAP.get(character)[ABBREVIATED_FORM_INDEX];
        }

        return conversion;
    }

    /**
     * Convert the SimpleDateFormat year into the datejs format.
     * 
     * @param count number of times the YEAR_TYPE occurred in a row
     * @return datejs format
     */
    private static String convertYear(int count) {
        String conversion;

        if (count >= YEAR_FULL_FORM_COUNT) {
            conversion = FORMAT_MAP.get(YEAR_TYPE)[FULL_FORM_INDEX];
        } else {
            conversion = FORMAT_MAP.get(YEAR_TYPE)[ABBREVIATED_FORM_INDEX];
        }

        return conversion;
    }

    /**
     * Convert the SimpleDateFormat month into the datejs format.
     * 
     * @param count number of times the MONTH_TYPE occurred in a row
     * @return datejs format
     */
    private static String convertMonth(int count) {
        String conversion;

        if (count >= MONTH_FULL_FORM_COUNT) {
            conversion = FORMAT_MAP.get(MONTH_TYPE)[MONTH_FULL_FORM_INDEX];
        } else if (count >= MONTH_ABBREVIATED_FORM_COUNT) {
            conversion = FORMAT_MAP.get(MONTH_TYPE)[MONTH_ABBREVIATED_FORM_INDEX];
        } else {
            conversion = FORMAT_MAP.get(MONTH_TYPE)[MONTH_NUMBER_FORM_INDEX];
        }

        return conversion;
    }

    /**
     * Return the {@link DateFormat#SHORT} for date and time for the given Locale.
     * 
     * @param locale current Locale
     * @return String standard {@link DateFormat#SHORT} for date and time
     */
    public static String getStandardFormat(Locale locale) {
        return getSimpleDateFormat(locale).toLocalizedPattern();
    }

    /**
     * Return the {@link SimpleDateFormat} using the {@link DateFormat#SHORT} for date and time for the given Locale.
     * 
     * @param locale current Locale
     * @return SimpleDateFormat
     */
    private static SimpleDateFormat getSimpleDateFormat(Locale locale) {
        return (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT,
            locale);
    }

    /**
     * Formate the given Date into a String as the {@link DateFormat#SHORT} for date and time for the given Locale.
     * 
     * @param date Date to format
     * @param locale current Locale
     * @return formatted date
     */
    public static String format(Date date, Locale locale) {
        if (date == null) {
            return "";
        } else {
            return getSimpleDateFormat(locale).format(date);
        }
    }

    /**
     * Formate the given Date into a String as the {@link DateFormat#SHORT} for date and time for the given Locale.
     * 
     * @param date Date to format
     * @param format format type
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
     * Return "12" or "24 depending on if the localized SimpleDataFormat for date/time includes either of the 24 hour
     * characters.
     * 
     * @param locale current Locale
     * @return String "24" if the SimpleDateFormat includes either of the 24 hour characters, else "12"
     */
    public static String get12or24(Locale locale) {
        String standardFormat = getStandardFormat(locale);

        if (standardFormat.contains(TWENTY_FOUR_HOUR_ONE) || standardFormat.contains(TWENTY_FOUR_HOUR_TWO)) {
            return "24";
        }

        return "12";
    }
    
    /**
     * Return "12" or "24 depending on if the localized SimpleDataFormat for date/time includes either of the 24 hour
     * characters.
     * 
     * @param locale current Locale
     * @param format current format
     * @return String "24" if the SimpleDateFormat includes either of the 24 hour characters, else "12"
     */
    public static String get12or24(String format, Locale locale) {
        String standardFormat = new SimpleDateFormat(format, locale).toLocalizedPattern();

        if (standardFormat.contains(TWENTY_FOUR_HOUR_ONE) || standardFormat.contains(TWENTY_FOUR_HOUR_TWO)) {
            return "24";
        }

        return "12";
    }


}
