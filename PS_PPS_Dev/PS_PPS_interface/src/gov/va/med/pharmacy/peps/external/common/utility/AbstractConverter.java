/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.utility;


import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.Selectable;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;


/**
 * Common conversion routines.
 */
public class AbstractConverter {

    /** VISTA_TRUE */
    public static final String VISTA_TRUE = "1";

    /** VISTA_FALSE */
    public static final String VISTA_FALSE = "0";

    /** ABSTRACT_FACTORY */
    protected static final gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory ABSTRACT_FACTORY =
        new gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory();

    /** VALUE_DASH_PATTERN 
     *  example yyy[-]yyy
     */
    protected static final Pattern VALUE_DASH_PATTERN = Pattern.compile("\\s*(\\w*)\\s*-?.*", Pattern.CASE_INSENSITIVE);

    /**
     * private constructor
     */
    protected AbstractConverter() {
    }

    /**
     * Check for new/modified fields in the difference set.
     * 
     * @param fields set of defined fields
     * @param differences difference set
     * @param itemAction add/modify/inactivate
     * @return true if has modified fields
     */
    public static boolean hasNewOrModifiedFields(Set<FieldKey> fields, Map<FieldKey, Difference> differences,
                                                 ItemAction itemAction) {
        if (ItemAction.ADD.equals(itemAction)) {
            return true;
        } else { // check for modified VistA field
            for (FieldKey key : fields) {
                if (differences.containsKey(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Get the correct candidate key value.
     * 
     * @param key field key
     * @param differences set difference
     * @param newValue current/new value for key
     * @return old value if field key is present/valid, new value if field key is not present
     */
    public static Object toCandidateKeyValue(FieldKey key, Map<FieldKey, Difference> differences, Object newValue) {
        Object oldValue = getOldValue(key, differences);

        if (oldValue == null) {
            return newValue;
        } else {
            return oldValue;
        }
    }

    /**
     * Check if the field key value was previously set and is now unset.
     * 
     * @param key field key
     * @param differences set difference
     * @return true if unset, false otherwise
     */
    public static boolean isUnset(FieldKey key, Map<FieldKey, Difference> differences) {
        Difference difference = differences.get(key);

        if (difference == null) {
            return false;
        }

        return (isValid(difference.getOldValue()) && !isValid(difference.getNewValue()));
    }

    /**
     * Get the old value for a field key difference.
     * 
     * @param key field key
     * @param differences set difference
     * @return true is has an old value
     */
    public static boolean hasOldValue(FieldKey key, Map<FieldKey, Difference> differences) {
        return isValid(getOldValue(key, differences));
    }

    /**
     * Get the old value for a field key difference.
     * 
     * @param key field key
     * @param differences set difference
     * @return old value if field key is present, null if field key is not present
     */
    public static Object getOldValue(FieldKey key, Map<FieldKey, Difference> differences) {
        Difference difference = differences.get(key);

        if (difference == null) {
            return null;
        }

        return differences.get(key).getOldValue();
    }

    /**
     * Get the new value for a field key difference.
     * 
     * @param key field key
     * @param differences set difference
     * @return true is has an old value
     */
    public static boolean hasNewValue(FieldKey key, Map<FieldKey, Difference> differences) {
        return isValid(getNewValue(key, differences));
    }

    /**
     * Get the new value for a field key difference.
     * 
     * @param key field key
     * @param differences set difference
     * @return new value if field key is present, null if field key is not present
     */
    public static Object getNewValue(FieldKey key, Map<FieldKey, Difference> differences) {
        Difference difference = differences.get(key);

        if (difference == null) {
            return null;
        }

        return differences.get(key).getNewValue();
    }

    /**
     * Determine whether a Selectable field value is valid (i.e., not null).
     * 
     * @param field Selectable field value
     * @return true if valid, false otherwise
     */
    public static boolean isValid(Selectable field) {
        if (field == null) {
            return false;
        }

        return isValid(field.getValue());
    }

    /**
     * Determine whether a data field value is valid (i.e., not null).
     * 
     * @param field DataField value
     * @return true if valid, false otherwise
     */
    public static boolean isValid(DataField field) {
        if (field == null) {
            return false;
        }

        return isValid(field.getValue());
    }

    /**
     * Determine whether a value is valid (e.g., not null).
     * 
     * @param object value to check
     * @return true if valid, false otherwise
     */
    public static boolean isValid(Object object) {
        if (object instanceof String) {
            return isValid((String) object);
        } else if (object instanceof Date) {
            return isValid((Date) object);
        } else if (object instanceof Number) {
            return isValid((Number) object);
        } else if (object instanceof Boolean) {
            return isValid((Boolean) object);
        } else if (object instanceof Collection) {
            return isValid((Collection) object);
        }

        return (object != null);
    }

    /**
     * Determine whether a string value is valid (i.e., not null).
     * 
     * @param value string value
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String value) {
        return (value != null && !"".equals(value.trim()) && !"NA".equals(value) && !"N/A".equals(value));
    }

    /**
     * Determine whether a date value is valid (i.e., not null).
     * 
     * @param value date value
     * @return true if valid, false otherwise
     */
    public static boolean isValid(Date value) {
        return (value != null);
    }

    /**
     * Determine whether a number value is valid (i.e., not null).
     * 
     * @param value date value
     * @return true if valid, false otherwise
     */
    public static boolean isValid(Number value) {
        return (value != null);
    }

    /**
     * Determine whether a boolean value is valid (i.e., not null).
     * 
     * @param value date value
     * @return true if valid, false otherwise
     */
    public static boolean isValid(Boolean value) {
        return (value != null);
    }

    /**
     * Determine whether a collection value is valid (i.e., not null).
     * 
     * @param value date value
     * @return true if valid, false otherwise
     */
    public static boolean isValid(Collection value) {
        return ((value != null) && !value.isEmpty());
    }

    /**
     * Extract a single group from a regex.
     * 
     * @param pattern pattern
     * @param value value to be matched
     * @return single matching group, or null if no match
     */
    public static String toString(Pattern pattern, String value) {
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            return null;
        }

        return matcher.group(1);
    }

    /**
     * To '1 | 0' boolean representation.
     * 
     * @param value boolean value
     * @return '1' or '0'
     */
    public static String toBooleanOneOrZero(boolean value) {
        return (value ? VISTA_TRUE : VISTA_FALSE);
    }

    /**
     * Manually convert decimal numbers to avoid scientific notation.
     * 
     * @param value decimal value
     * @return string representation
     */
    public static String toString(double value) {
        return NumberFormatUtility.format(value);
    }

    /**
     * Manually convert decimal numbers to avoid scientific notation.
     * 
     * @param value decimal value
     * @return string representation
     */
    public static String toString(float value) {
        return NumberFormatUtility.format(value);
    }

}
