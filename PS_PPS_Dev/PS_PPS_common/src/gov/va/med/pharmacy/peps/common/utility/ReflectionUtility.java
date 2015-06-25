/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.ReflectionUtils;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * Provide common methods to find getters and setters on ValueObjects as well as derive the FieldKeys from the ValueObject's
 * attributes.
 */
public class ReflectionUtility {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ReflectionUtility.class);

    /**
     * Cannot instantiate
     */
    private ReflectionUtility() {
        super();
    }
    
    /**
     * Create a Set of FieldKeys from the declared {@link Field} in the given ValueObject.
     * 
     * This method assumes that the key values for the FieldKeys can be derived from the ValueObject's attribute names.
     * 
     * @param valueObject ValueObject from which to derive FieldKeys
     * @return Set of FieldKeys
     */
    public static Set<FieldKey> deriveFields(ValueObject valueObject) {
        Set<Field> fields = getDeclaredFields(valueObject.getClass());
        Set<FieldKey> fieldKeys = new HashSet<FieldKey>(fields.size());

        for (Field field : fields) {
            FieldKey fieldKey = FieldKey.toFieldKey(field.getName());

            if (fieldKey == null) {
                LOG.trace("FieldKey not found for field '" + field.getName() + "'");            
            } else {
                fieldKeys.add(fieldKey); 
            }
        }

        return fieldKeys;
    }

    /**
     * Find the Class type for the given attribute on the given Class.
     * <p>
     * If the given attribute represent a Collection, get the generic typed Class of the Collection.
     * 
     * @param name String attribute name
     * @param clazz Class on which the attribute exists
     * @return Class type of attribute
     */
    public static Class getDeclaredFieldType(String name, Class clazz) {
        Field field = getDeclaredField(name, clazz);

        Class fieldClass = field.getType();

        if (Collection.class.isAssignableFrom(fieldClass)) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            fieldClass = (Class) parameterizedType.getActualTypeArguments()[0];
        }

        return fieldClass;
    }

    /**
     * Recursively call into super classes, super interfaces, declaring classes, and enclosing classes to find the given
     * declared {@link Field}. Recursion ends when any of these methods returns null (i.e., there are no more classes to
     * traverse).
     * 
     * @param name String {@link Field} name to find
     * @param clazz {@link Class} to begin recursion
     * @return declared {@link Field}
     */
    public static Field getDeclaredField(String name, Class clazz) {
        Field field = null;

        if (clazz != null) {
            try {
                field = clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {

                // Find declared field in super class (and it's super class and interfaces)
                field = getDeclaredField(name, clazz.getSuperclass());

                // Find declared field in the implemented interfaces
                if (field == null) {
                    Class[] interfaces = clazz.getInterfaces();

                    for (int i = 0; i < interfaces.length && field == null; i++) {
                        field = getDeclaredField(name, interfaces[i]);
                    }
                }

                // Find declared field in the declaring class (if this class is an inner class)
                if (field == null) {
                    field = getDeclaredField(name, clazz.getDeclaringClass());
                }

                // Find declared field in the enclosing class (if this class is an anonymous inner class)
                if (field == null) {
                    field = getDeclaredField(name, clazz.getEnclosingClass());
                }
            }
        }

        if (field == null) {
            if (clazz == null) {
                LOG.trace("Field '" + name + "' not found");             
            } else {
                LOG.trace("Field  '" + name + "' not found on class '" + clazz.getName() + "'");
            }
        }

        return field;
    }

    /**
     * Recursively call into super classes, super interfaces, declaring classes, and enclosing classes to find all declared
     * {@link Field}. Recursion ends when any of these methods returns null (i.e., there are no more classes to traverse).
     * 
     * @param clazz {@link Class} to begin recursion
     * @return Set<Field> all declared {@link Field}
     */
    public static Set<Field> getDeclaredFields(Class clazz) {
        Set<Field> fields = new HashSet<Field>();

        if (clazz != null) {

            // Add all declared fields from the super class (and it's super class and interfaces)
            fields.addAll(getDeclaredFields(clazz.getSuperclass()));

            // Add all declared fields from the implemented interfaces
            for (Class ifc : clazz.getInterfaces()) {
                fields.addAll(getDeclaredFields(ifc));
            }

            // Add all declared fields from the declaring class (if this class is an inner class)
            fields.addAll(getDeclaredFields(clazz.getDeclaringClass()));

            // Add all declared fields from the enclosing class (if this class is an anonymous inner class)
            fields.addAll(getDeclaredFields(clazz.getEnclosingClass()));

            // Add all declared fields from this class
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }

        return fields;
    }

    /**
     * Call {@link #getDeclaredFields(Class)} and then filter for all fields annotated with the given annotation class.
     * 
     * @param clazz {@link Class} on which to find fields
     * @param annotation {@link Annotation} 
     * @return annotated fields
     */
    public static Set<Field> getAnnotatedFields(Class clazz, Class annotation) {
        Set<Field> allFields = getDeclaredFields(clazz);
        Set<Field> annotatedFields = new HashSet<Field>();
        
        for (Field field : allFields) {
            if (field.isAnnotationPresent(annotation)) {
                annotatedFields.add(field);
            }
        }
        
        return annotatedFields;
    }
    
    /**
     * Call {@link #getDeclaredFields(Class)} and then filter for all fields annotated with the given annotation class.
     * 
     * @param clazz {@link Class} on which to find fields
     * @param annotation {@link Annotation} 
     * @return annotated field names
     */
    public static Set<String> getAnnotatedFieldNames(Class clazz, Class annotation) {
        Set<Field> allFields = getDeclaredFields(clazz);
        Set<String> annotatedFields = new HashSet<String>();
        
        for (Field field : allFields) {
            if (field.isAnnotationPresent(annotation)) {
                annotatedFields.add(field.getName());
            }
        }
        
        return annotatedFields;        
    }

    /**
     * Get the value of the given {@link FieldKey} from the given {@link Object}.
     * 
     * @param <T> return type of field
     * @param object {@link Object} to retrieve field from
     * @param fieldKey {@link FieldKey} representing field to retrieve
     * @return Object value of the field if found, otherwise null
     */
    public static <T> T getValue(Object object, FieldKey fieldKey) {
        if (fieldKey.isVaDataField()) {
            DataFields dataFields = getValue(object, fieldKey.toAttributeName());

            return (T) dataFields.getDataField(fieldKey);
        } else {
            return (T) getValue(object, fieldKey.toAttributeName());
        }
    }

    /**
     * Get the value of the given {@link FieldKey} from the given {@link Object}.
     * 
     * @param <T> return type of field
     * @param object {@link Object} to retrieve field from
     * @param attributeName {@link String} attribute name of the field to retrieve
     * @return Object value of the field if found, otherwise null
     */
    public static <T> T getValue(Object object, String attributeName) {
        T value = null;

        try {
            Field field = getDeclaredField(attributeName, object.getClass());
            field.setAccessible(true);
            value = (T) field.get(object);
        } catch (Exception e) {
            LOG.error("Unable to get field '" + attributeName + "' from given ValueObject '" + object, e);
        }

        return value;
    }

    /**
     * Set the specified value for the specified FieldKey to the specified value object.
     * 
     * @param valueObject ValueObject from which to derive FieldKeys
     * @param fieldKey {@link FieldKey} representing field to set
     * @param value Value of field to set
     * @return boolean True if the value was set on the value object, false if the value could not be set
     */
    public static boolean setValue(ValueObject valueObject, FieldKey fieldKey, Object value) {
        boolean successful = false;

        if (fieldKey.isVaDataField()) {
            DataFields<DataField> dataFields = getValue(valueObject, FieldKey.VA_DATA_FIELDS.toAttributeName());

            if (value != null) {
                dataFields.setDataField((DataField) value);
                successful = true;
            }
        } else {
            successful = setValue(valueObject, fieldKey.toAttributeName(), value);
        }

        return successful;
    }

    /**
     * Set the specified value for the specified attribute name to the specified value object.
     * 
     * @param valueObject ValueObject from which to derive FieldKeys
     * @param attributeName {@link String} attribute name of the field to set
     * @param value Value of field to set
     * @return boolean True if the value was set on the value object, false if the value could not be set
     */
    public static boolean setValue(ValueObject valueObject, String attributeName, Object value) {
        boolean successful = false;

        if (valueObject == null || attributeName == null) {
            LOG.error("Unable to set value  '" + value + "' because the given ValueObject or attribute '" + attributeName
                + "' is null.");

            return false;
        }

        try {
            String setterName = "set" + StringUtils.capitalize(attributeName);
            Field field = getDeclaredField(attributeName, valueObject.getClass());
            Method setter = ReflectionUtils.findMethod(valueObject.getClass(), setterName, new Class[] {field.getType()});

            if (setter == null) {
                LOG.trace("Setter method '" + setterName + "' with parameter type '" + value.getClass() + "' on class '"
                    + valueObject.getClass() + "' was not found, setting value directly on the field instead.");

                field.setAccessible(true);
                field.set(valueObject, value);
            } else {
                setter.invoke(valueObject, value);
            }

            successful = true;
        } catch (Exception e) {
            LOG.error("Unable to set value '" + value + "' on given field '" + attributeName + "'.", e);
            successful = false;
        }

        return successful;
    }

    /**
     * Convert a String value into the Type, T, for the given FieldKey. The FieldKey must represent a Date or primitive
     * attribute. No other class types are supported by this method.
     * 
     * If the FieldKey's fieldClass attribute is assignable from {@link java.util.Date}, then the {@link String} is assumed
     * to be the {@link Long} representation of the milliseconds since January 1, 1970. The {@link String} is converted into
     * a {@link Long} and passed into the {@link Date} class' constructor.
     * 
     * If the {@link FieldKey} fieldClass attribute is assignable from {@link Character}, then the {@link String} must be of
     * length 1, representing the character itself.
     * 
     * Otherwise, the constructor which takes a single parameter of type String is called and the results are returned as the
     * type of this DataField.
     * 
     * If there are any errors or a constructor could not be found or called, a CommonException (PharmacyRuntimeException) is
     * thrown.
     * 
     * This method should work for any {@link String}, {@link Number}, or {@link Boolean} as all of these Java classes all
     * have a constructor which takes a single String as a parameter.
     * 
     * @param <T> Type to return
     * @param fieldKey {@link FieldKey} the String value represents
     * @param value String value
     * @return T typed value for the given value
     */
    public static <T> T convertStringValue(FieldKey<T> fieldKey, String value) {
        Class fieldClass = fieldKey.getFieldClass();
        String notNullValue = resetNullValue(fieldKey, value);
        T result = null;

        try {
            if (notNullValue.trim().length() > 0) {
                if (Date.class.isAssignableFrom(fieldClass)) {
                    if (NumberUtils.isNumber(notNullValue)) {
                        result = (T) new Date(Long.parseLong(notNullValue));
                    } else {
                        result = (T) DateFormatUtility.convertToDate(notNullValue);
                    }
                } else if (Character.class.isAssignableFrom(fieldClass) && notNullValue.trim().length() == 1) {
                    result = (T) fieldClass.getConstructor(Character.class).newInstance(notNullValue.charAt(0));
                } else if (Enum.class.isAssignableFrom(fieldClass)) {
                    result = (T) Enum.valueOf(fieldClass, notNullValue);
                } else {
                    result = (T) fieldClass.getConstructor(String.class).newInstance(notNullValue);
                }
            }
        } catch (Exception e) {
            throw new CommonException(e, CommonException.ILLEGAL_ARGUMENT, notNullValue);
        }

        return result;
    }

    /**
     * If the value is null, return the appropriate default value for a Boolean, Number, or String field.
     * 
     * @param fieldKey {@link FieldKey} the String value represents
     * @param value String to test
     * @return non-null String
     */
    private static String resetNullValue(FieldKey fieldKey, String value) {
        if (value == null) {
            if (Boolean.class.isAssignableFrom(fieldKey.getFieldClass())) {
                return "false";
            } else if (Number.class.isAssignableFrom(fieldKey.getFieldClass())) {
                return ""; // changed from "0" so that optional numeric fields are set to ""
            } else { // treat all String fields and any field types not found as empty strings
                return "";
            }
        } else {
            return value.trim();
        }
    }

}
