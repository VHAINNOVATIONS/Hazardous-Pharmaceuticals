/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;


/**
 * Provide convenience methods to create for tag and jsp files
 */
@Component
public class JspTagUtility implements MessageSourceAware {

    /** PARENT_FIELD_KEY  */
    public static final String PARENT_FIELD_KEY = "parentFieldKey";
    
    /** INDEX  */
    public static final String FIELD_KEY = "fieldKey";
    
    /** INDEX  */
    public static final String INDEX = "index";
    
    private static final Logger LOG = Logger.getLogger(JspTagUtility.class);

    private static MessageSource messageSource;
    
    

    /**
     * Cannot instantiate
     */
    private JspTagUtility() {
        super();
    }

    /**
     * 
     * Gets the field value out of the passed object. This field must be a
     * property on the object to be retrieved
     * 
     * @param obj
     *            The object who's field value is needed
     * @param key
     *            The camel case string value of the attribute who's field value
     *            is needed
     * @return Object value of the field
     * @throws IllegalAccessException an exception
     * @throws InvocationTargetException an exception 
     */
    public static Object getFieldValue(Object obj, String key) throws IllegalAccessException,
        InvocationTargetException {

        Field field = null;
        Method method = null;
        Object fieldValue = null;

        if (obj != null) {

            try {
                method = obj.getClass().getMethod("get" + StringUtils.capitalize(key));
                method.setAccessible(true);
                fieldValue = method.invoke(obj);
                LOG.debug("Checking with get Reflection");
            } catch (NoSuchMethodException e) {
                try {
                    method = obj.getClass().getMethod("is" + StringUtils.capitalize(key));
                    method.setAccessible(true);
                    fieldValue = method.invoke(obj);
                    LOG.debug("Checking with is Reflection");
                } catch (NoSuchMethodException e2) {
                    try {
                        field = getField(obj.getClass(), key);

                        if (field != null) {
                            fieldValue = field.get(obj);
                        }
                    } catch (SecurityException e1) {
                        LOG.warn(e1.getMessage());
                    }
                }
            }

        }

        return fieldValue;
    }

    /**
     * 
     * Gets the field value of the FieldKey from the passed in object
     * 
     * @param obj Object with the value you want
     * @param key FieldKey value
     * @return Object value of the field
     * @throws IllegalAccessException an exception 
     * @throws InvocationTargetException an exception
     */
    public static Object getFieldValue(Object obj, FieldKey key) throws IllegalAccessException,
        InvocationTargetException {

        Object fieldValue = null;

        if (obj != null) {

            // If this is a ManagedItemVo, check the datafields for the value first
            if (obj instanceof ManagedItemVo && (key != null && key.isVaDataField())) {
                fieldValue = ((ManagedItemVo) obj).getVaDataFields().getDataField(key);
            }

            // If there is no datafield value, start looking at the other attributes and methods
            if (fieldValue == null) {
                fieldValue = getFieldValue(obj, key.fromDotsToCamelCase());
            }
        }

        return fieldValue;

    }

    /**
     * 
     * Helper method for getFieldValue. It travels up an objects class hierarchy
     * searching for a declared field contained in fieldName
     * 
     * @param clazz
     *            The class of the object
     * @param fieldName
     *            The name of the field you're looking for
     * @return The field if it's found
     */
    private static Field getField(Class<?> clazz, String fieldName) {
        Class<?> tmpClass = clazz;

        do {
            LOG.debug("Checking each Field reflexivly");
            
            for (Field field : tmpClass.getDeclaredFields()) {
                String candidateName = field.getName();

                if (!candidateName.equals(fieldName)) {
                    continue;
                }

                field.setAccessible(true);

                return field;
            }

            tmpClass = tmpClass.getSuperclass();

        } while (tmpClass != null);

// commenting out on 6/26/2012 as it is spamming the logfile and not sure how to fix it.
// FdbAddVo is the vo doing the spamming.        
//        if (tmpClass == null) {
//            LOG.info("Field '" + fieldName + "' not found on class " + clazz);
//        }

        return null;
    }

    /**
     * Return true if the given {@link String} is null or its length is less than or equal to zero.
     * 
     * @param string String to test
     * @return true if string is null or it's trimmed length is less than or equal to zero
     */
    @SuppressWarnings("unused")
    private static boolean isEmptyString(String string) {
        return string == null || string.trim().length() <= 0;
    }

    /**
     * Gets the text value of the passed key from the resource bundle
     * 
     * @param locale the locale
     * @param key String key to localize
     * @return String localized value
     */
    public static String getText(Locale locale, String key) {

        String result = null;

        // Try to grab the value from the resource bundle, if not, just return the key value
        try {
            result = messageSource.getMessage(key, null, locale);
        } catch (NoSuchMessageException e) {
            result = key;
        }

        if (result == null) {
            result = key;
        }

        return result;
    }

    /**
     * Gets the text value of the passed key from the resource bundle
     * 
     * @param request HttpServletRequest
     * @param key String key to localize
     * @return String localized value
     */
    public static String getText(HttpServletRequest request, String key) {

        return getText(request.getLocale(), key);
    }

    /**
     * 
     * Gets the text value of the passed key from the resource bundle
     *
     * @param request HttpServletRequest
     * @param fieldKey The fieldKey of the value to tbe localized
     * @param value the value to the localized
     * @return String localized value
     */
    public static String getText(HttpServletRequest request, FieldKey fieldKey, String value) {

        String key = fieldKey.getKey();

        final int unpluralizeYpos = 3;

        if (key.endsWith("ies")) {
            key = key.substring(0, key.length() - unpluralizeYpos) + "y";
        } else if (key.endsWith("s")) {
            key = key.substring(0, key.length() - 1);
        }

        key = StringUtils.capitalize(key + "." + value);

        return getText(request.getLocale(), key);
    }

    /**
     * Searches the resource bundles for the associated key and field value
     * 
     * @param locale
     *            Locale
     * @param key
     *            String key to localize
     * @param fieldValue
     *            String value of the field associated with the key
     * @return String localized value
     */
    public static String getText(Locale locale, String key, Object fieldValue) {
        String result = null;

        LOG.debug("getText1 key from OgnlUtil: " + key);
        LOG.debug("getText1 result from OgnlUtil: " + result);

        // Try getting the value from the resource bundle using the key only
        try {
            result = messageSource.getMessage(key, null, locale);
        } catch (NoSuchMessageException e) {

            //Try getting the value from the resource bundle using the key concatenated with the field value
            try {
                result = messageSource.getMessage(key + "." + fieldValue.toString(), null, locale);
            } catch (NoSuchMessageException ex) {
                result = fieldValue.toString();
            }
        }

        if (result == null) {
            result = key;
        }

        return result;
    }

    /**
     * Searches the resource bundles for the associated key and field value
     * 
     * @param request HttpServletRequest
     * @param key String key to localize
     * @param fieldValue String value of the field associated with the key
     * @return String localized value
     */
    public static String getText(HttpServletRequest request, String key, Object fieldValue) {
        return getText(request.getLocale(), key, fieldValue);
    }

    /**
     * Call the getText method on the current action (within the
     * {@link ActionSupport} super class). If this returns null,
     * return the key since it couldn't be localized.
     * JspTagUtility.
     * <p>
     * The key used in the unqualified Enum class name, followed by the value of
     * the enum, delimited by a dot '.'. For example, "EntityType.PRODUCT".
     * <p>
     * If the generated key is not found, return the given {@link Enum#name()}
     * value.
     * 
     * @param locale
     *            Locale
     * @param enumeration
     *            Enum value to localize
     * @return String localized value
     * 
     * @see #getText(HttpServletRequest, String)
     */
    public static String getText(Locale locale, Enum enumeration) {
        String unqualifiedClass = org.springframework.util.StringUtils.unqualify(enumeration.getClass().getName());
        String value = enumeration.name();
        String key = unqualifiedClass + "." + value;

        return messageSource.getMessage(key, null, locale);
    }

    /**
     * Call the getText method on the current action (within the
     * {@link ActionSupport} super class). If this returns null,
     * return the key since it couldn't be localized.
     * <p>
     * The key used in the unqualified Enum class name, followed by the value of
     * the enum, delimited by a dot '.'. For example, "EntityType.PRODUCT".
     * <p>
     * If the generated key is not found, return the given {@link Enum#name()}
     * value.
     * 
     * @param request
     *            HttpServletRequest
     * @param enumeration
     *            Enum value to localize
     * @return String localized value
     * 
     * @see #getText(HttpServletRequest, String)
     */
    public static String getText(HttpServletRequest request, Enum enumeration) {
        return getText(request.getLocale(), enumeration);
    }

    /**
     * 
     * Builds a map of enums and localized values given the class type of the
     * enum. This is useful if you don't have the enum itself, but only the
     * Class object.
     * 
     * @param locale the locale
     * @param enumeration Class value of enum
     * @return Map<String, Enum> for display in an select box
     */
    public static Map<String, Enum> getEnumValues(Locale locale, Class enumeration) {
        String unqualifiedClass = org.springframework.util.StringUtils.unqualify(enumeration.getName());
        Map<String, Enum> enums = new TreeMap<String, Enum>();
        Enum[] values = null;

        // Get the constant values, if the enum is of type ItemStatus, use the getValues method to 
        // eliminate the ARCHIVED value from display
        if (gov.va.med.pharmacy.peps.common.vo.ItemStatus.class.isAssignableFrom(enumeration)) {
            values = ItemStatus.getValues();
        } else {
            values = ((Enum[]) enumeration.getEnumConstants());
        }

        //Build the tree with localized values for the enum values
        for (Enum value : values) {
            enums.put(messageSource.getMessage(unqualifiedClass + "." + value.name(), null, locale), value);
        }

        return enums;
    }

    /**
     * 
     * Splits out a formElementName into its parent and child {@link FieldKey} and parses out the 
     * position of the child in the parent
     *
     * @param formElementName the name field of the form
     * @return a Map containing the parent and child {@link FieldKey} and the position of the child 
     * in the parent Map keys are: fieldKey, parentFieldKey, index
     */
    public static Map<String, Object> getParentAndChildFieldKeys(String formElementName) {

        Map<String, Object> keyMap = new HashMap<String, Object>();
        String parentAttributeName = null;
        String childAttributeName = null;
        int index = -1;

        if (formElementName.contains(".")) {
            String[] attributes = formElementName.split("\\.");
            parentAttributeName = attributes[0];

            attributes = attributes[1].split("_");
            childAttributeName = attributes[0];

            try {
                index = Integer.parseInt(attributes[1]);
            } catch (NumberFormatException e) {
                LOG.warn("The index of the child attribute could not be correctly parsed.");
            }

        } else {
            childAttributeName = formElementName;
        }

        if (parentAttributeName != null) {
            keyMap.put(PARENT_FIELD_KEY, FieldKey.toFieldKey(parentAttributeName));
        }

        keyMap.put(FIELD_KEY, FieldKey.toFieldKey(childAttributeName));
        keyMap.put(INDEX, index);

        return keyMap;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        JspTagUtility.messageSource = messageSource;

    }

}
