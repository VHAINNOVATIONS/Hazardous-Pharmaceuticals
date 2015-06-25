/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.test;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField;

import junit.framework.TestCase;


/**
 * Verify the FieldKeys are properly configured.
 */
public class FieldKeyTest extends TestCase {

    private static final int NUM_6 = 6;
    private static final Logger LOG = Logger.getLogger(FieldKeyTest.class);
    private static final String KEY = "FieldKey: ";
    private static final String LINE = "----------";
    private static final String APM = "action.profile.message";
    private static final String CLASS = "class ";
    private static final String KEYS_DO_NOT = "The following FieldKeys do not: ";
    private static final String FK_GEN_TYPE = "FieldKey Generic Type: ";

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final Set<Field> FIELD_KEY_FIELDS = getFieldKeyFields();


    /**
     * Find the {@link Field} on the {@link FieldKey} class for this {@link FieldKey} instance.
     * 
     * @param key FieldKey
     * @return Field
     */
    private static Field getFieldKeyField(FieldKey key) {

        Field fieldKey = null;

        for (Field field : FIELD_KEY_FIELDS) {
            try {
                Object value = field.get(FieldKey.PRODUCT);

                if (key.equals(value)) {
                    fieldKey = field;
                    break;
                }
            } catch (Exception e) {
                LOG.debug("Unable to access field " + field.getName());
            }
        }

        return fieldKey;
    }

    /**
     * Get all {@link Field} on the {@link FieldKey} class.
     * 
     * @return Set<Field>
     */
    private static Set<Field> getFieldKeyFields() {

        Set<Field> fieldKeys = new HashSet<Field>();
        Set<Field> fields = ReflectionUtility.getDeclaredFields(FieldKey.class);

        for (Field field : fields) {
            try {
                Object value = field.get(FieldKey.PRODUCT);

                if (value instanceof FieldKey) {
                    fieldKeys.add(field);
                }
            } catch (Exception e) {
                LOG.debug("Unable to access field  " + field.getName());
            }
        }

        return fieldKeys;
    }

    /**
     * Discover the {@link Type} of field represented by this {@link FieldKey}.
     * 
     * @param key FieldKey
     * @return Type
     */
    private static Type getFieldKeyType(FieldKey key) {

        Type type = null;

        Type[] types = ((ParameterizedType) getFieldKeyField(key).getGenericType()).getActualTypeArguments();

        if (types.length == 1) {
            type = types[0];
        } else {
            LOG.debug("Unrecognized FieldKey types size");
        }

        return type;
    }

    /**
     * setUp.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        LOG.debug("---------------------" + getName() + "--------------------");
    }

    /**
     * Verify localized abbreviations exist for all {@link FieldKey} (at least for the US Locale).
     */
    public void testAbbreviationLocalization() {

        Collection<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer abbreviations = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (!fieldKey.isMultitextDataFieldValue()) {
                String abbreviation = fieldKey.getLocalizedAbbreviation(Locale.US);

                if ((fieldKey.getKey() + ".abbreviation").equals(abbreviation)) {
                    abbreviations.append(fieldKey.getKey());
                    abbreviations.append(", ");
                }
            }
        }

        assertTrue("All FieldKeys must have localizable abbreviations. " + abbreviations.toString() + " do not!",
            abbreviations.length() == 0);
    }

    /**
     * Verify localized descriptions exist for all {@link FieldKey} (at least for the US Locale).
     */
    public void testDescriptionLocalization() {

        Collection<FieldKey> fieldKeys = FieldKey.values();

        StringBuffer descriptions = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (!fieldKey.isMultitextDataFieldValue()) {
                String description = fieldKey.getLocalizedDescription(Locale.US);

                if ((fieldKey.getKey() + ".description").equals(description)) {
                    descriptions.append(fieldKey.getKey());
                    descriptions.append(", ");
                }
            }
        }

        assertTrue("All FieldKeys must have localizable descriptions. " + descriptions.toString() + " do not!!", descriptions
            .length() == 0);
    }

    /**
     * Verify the {@link FieldKey#getFieldClass()} is the same as the generic type set for the {@link FieldKey} attribute.
     * 
     * In other words new FieldKey<String>("my.key", ..., String.class, ...) where "String" is equal. Note that if the
     * generic type is for a Collection or DataField, the generic types of those must be equal.
     * 
     * For example, new FieldKey<Collection<String>>("my.key", ..., String.class, ...)
     */
    public void testGenericTypes() {

        Set<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer buffer = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (!fieldKey.isMultitextDataFieldValue()) {
                Class fieldClass = fieldKey.getFieldClass();
                Type fieldType = getFieldKeyField(fieldKey).getGenericType();
                Class fieldKeyAttribute;

                if (fieldType instanceof ParameterizedType) {
                    Type fieldKeyType = ((ParameterizedType) fieldType).getActualTypeArguments()[0];

                    if (fieldKeyType instanceof ParameterizedType) {
                        fieldKeyAttribute = (Class) ((ParameterizedType) fieldKeyType).getRawType();

                        if (DataField.class.isAssignableFrom(fieldKeyAttribute)
                            || Collection.class.isAssignableFrom(fieldKeyAttribute)) {
                            fieldKeyAttribute = (Class) ((ParameterizedType) fieldKeyType).getActualTypeArguments()[0];
                        }
                    } else {
                        fieldKeyAttribute = (Class) fieldKeyType;
                    }
                } else {
                    fieldKeyAttribute = (Class) fieldType;
                }

                if (!fieldClass.equals(fieldKeyAttribute)) {
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(KEY + fieldKey.getKey());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append("Field Class: " + fieldClass.getName());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append("Field Generic Type: " + fieldKeyAttribute.getName());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(LINE);
                }
            }
        }

        assertTrue("FieldKey generic types should be equal to the fieldClass constructor parameter. "
            + "The following FieldKeys are not: " + buffer.toString(), buffer.length() == 0);
    }

    /**
     * Test
     */
    public void testGetKey() {

        FieldKey<DataField<String>> fieldKey = FieldKey.ACTION_PROFILE_MESSAGE;

        assertEquals("Wrong key!", APM, fieldKey.getKey());
    }

    /**
     * Test
     */
    public void testGetKeyUsingTheFieldKeysKeyValue() {

        String keyValue = APM;
        FieldKey<DataField<String>> fieldKey = FieldKey.getKey(keyValue);

        assertEquals("Wrong key", APM, fieldKey.getKey());
    }

//    /**
//     * Verify all {@link FieldKey} that represent fields on ValueObjects (i.e., those with EntityType field levels) have
//     * attributes on the ValueObjects of the same type of class.
//     * 
//     * @throws Exception if error
//     */
//    public void testImplementationClasses() throws Exception {
//
//        Set<FieldKey> fieldKeys = FieldKey.values();
//        StringBuffer buffer = new StringBuffer();
//
//        for (FieldKey fieldKey : fieldKeys) {
//            if (!fieldKey.isMultitextDataFieldValue() && !fieldKey.isVaDataField()) {
//                List<EntityType> entityTypes = fieldKey.getEntityTypes();
//
//                for (EntityType entityType : entityTypes) {
//                    Class valueObject = entityType.toValueObjectClass();
//                    Field field = ReflectionUtility.getDeclaredField(fieldKey.toAttributeName(), valueObject);
//                    Type fieldKeyType = getFieldKeyType(fieldKey);
//
//                    if (field == null) {
//                        LOG.debug("Unable to test  " + fieldKey.getKey() + " on ValueObject  "
//                            + valueObject.getName() + "  because the field couldn't be found at all. "
//                            + "Verify that testFieldLevelAttributes() is passing! Looking for getter and setter methods...");
//
//                        if (hasGetterButNotSetter(valueObject, fieldKey)) {
//                            Method getter = findGetter(valueObject, fieldKey);
//                            Type returnType = getter.getGenericReturnType();
//
//                            if (!returnType.equals(fieldKeyType)) {
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(KEY + fieldKey.getKey());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("FieldKey Generic Type1: " + fieldKeyType.toString());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("ValueObject1: " + valueObject.getName());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("ValueObject Method1: " + getter.getName());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("ValueObject Method Return Type1: " + returnType.toString());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(LINE);
//                            }
//                        } else if (hasGetterAndSetter(valueObject, fieldKey)) {
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append(KEY + fieldKey.getKey());
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append("FieldKey Generic Type2: " + fieldKeyType.toString());
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append("ValueObject2: " + valueObject.getName());
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append("Has getter and setter without matching field2!");
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append(LINE);
//                        }
//                    } else {
//                        Type implementationType = field.getGenericType();
//
//                        if (field.getType().isPrimitive()) {
//                            implementationType = (Type) ClassUtils.primitiveToWrapper(field.getType());
//                        }
//
//                        if (!implementationType.equals(fieldKeyType)) {
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append(KEY + fieldKey.getKey());
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append("FieldKey Generic Type3: " + fieldKeyType.toString());
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append("ValueObject3: " + valueObject.getName());
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append("ValueObject Attribute Type3: " + implementationType.toString());
//                            buffer.append(LINE_SEPARATOR);
//                            buffer.append(LINE);
//                        }
//                    }
//                }
//            }
//        }
//
//        assertTrue("All FieldKeys must have generic types equal to the attributes on the respective ValueObjects. "
//            + " The following FieldKeys do not: " + buffer.toString(), buffer.toString().length() == 0);
//    }

    /**
     * Returns true if there is a getter method but not a setter method for the given {@link FieldKey} on the given
     * {@link Class}. This method does not test for parameter types, only that methods named get* or set* are on the class.
     * 
     * @param clazz {@link Class} to find the method on
     * @param fieldKey {@link FieldKey} to find getter/setter
     * @return boolean
     */
    private boolean hasGetterButNotSetter(Class clazz, FieldKey fieldKey) {

        return hasGetter(clazz, fieldKey) && !hasSetter(clazz, fieldKey);
    }

    /**
     * Returns true if there are getter method and setter methods for the given {@link FieldKey} on the given {@link Class}.
     * This method does not test for parameter types, only that methods named get* or set* are on the class.
     * 
     * @param clazz {@link Class} to find the method on
     * @param fieldKey {@link FieldKey} to find getter/setter
     * @return boolean
     */
    private boolean hasGetterAndSetter(Class clazz, FieldKey fieldKey) {

        return hasGetter(clazz, fieldKey) && hasSetter(clazz, fieldKey);
    }

    /**
     * Return true if there is a method named get*, is* or has* on the given {@link Class} based upon the given
     * {@link FieldKey}.
     * 
     * @param clazz {@link Class} to find the method on
     * @param fieldKey {@link FieldKey} to find getter
     * @return boolean
     */
    private boolean hasGetter(Class clazz, FieldKey fieldKey) {

        boolean hasGetter = false;

        String getMethod = createGetMethod(fieldKey);
        String isMethod = createIsMethod(fieldKey);
        String hasMethod = createHasMethod(fieldKey);

        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if ((method.getName().equals(getMethod) || method.getName().equals(isMethod) || method.getName().equals(
                hasMethod))
                && !hasGetter) {
                hasGetter = true;
                break;
            }
        }

        return hasGetter;
    }

    /**
     * Return true if there is a method named set* on the given {@link Class} based upon the given {@link FieldKey}.
     * 
     * @param clazz {@link Class} to find the method on
     * @param fieldKey {@link FieldKey} to find getter
     * @return boolean
     */
    private boolean hasSetter(Class clazz, FieldKey fieldKey) {

        boolean hasSetter = false;

        String setMethod = createSetMethod(fieldKey);

        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(setMethod) && !hasSetter) {
                hasSetter = true;
                break;
            }
        }

        return hasSetter;
    }

    /**
     * Generate a get method name off of the given {@link FieldKey}.
     * 
     * @param fieldKey {@link FieldKey} for which to generate get method
     * @return String get method name
     */
    private String createGetMethod(FieldKey fieldKey) {

        return "get" + fieldKey.toAttributeName().substring(0, 1).toUpperCase() + fieldKey.toAttributeName().substring(1);
    }

    /**
     * Generate a is method name off of the given {@link FieldKey}.
     * 
     * @param fieldKey {@link FieldKey} for which to generate is method
     * @return String is method name
     */
    private String createIsMethod(FieldKey fieldKey) {

        return "is" + fieldKey.toAttributeName().substring(0, 1).toUpperCase() + fieldKey.toAttributeName().substring(1);
    }

    /**
     * Generate a has method name off of the given {@link FieldKey}.
     * 
     * @param fieldKey {@link FieldKey} for which to generate has method
     * @return String has method name
     */
    private String createHasMethod(FieldKey fieldKey) {

        return "has" + fieldKey.toAttributeName().substring(0, 1).toUpperCase() + fieldKey.toAttributeName().substring(1);
    }

    /**
     * Generate a set method name off of the given {@link FieldKey}.
     * 
     * @param fieldKey {@link FieldKey} for which to generate set method
     * @return String set method name
     */
    private String createSetMethod(FieldKey fieldKey) {

        return "set" + fieldKey.toAttributeName().substring(0, 1).toUpperCase() + fieldKey.toAttributeName().substring(1);
    }

    /**
     * Tests localization of field keys
     */
    public void testLocalizeFieldKey() {

        FieldKey fieldKey = FieldKey.VALUE;
        assertEquals("wrong localized name!", "Name", fieldKey.getLocalizedName(Locale.US));
    }

    /**
     * Tests localization of field keys by entity type
     */
    public void testLocalizeFieldKeyEntityType() {

        FieldKey fieldKey = FieldKey.VALUE;
        assertEquals("wrong localized name", "Drug Unit Name", fieldKey.getLocalizedName(Locale.US, EntityType.DRUG_UNIT));
    }

    /**
     * Verify localized names exist for all {@link FieldKey} (at least for the US Locale).
     */
    public void testNameLocalization() {

        Collection<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer names = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (!fieldKey.isMultitextDataFieldValue()) {
                String name = fieldKey.getLocalizedName(Locale.US);

                if ((fieldKey.getKey() + ".name").equals(name)) {
                    names.append(fieldKey.getKey());
                    names.append(", ");
                }
            }
        }

        assertTrue("All FieldKeys must have localizable names. " + names.toString() + " do not.", names.length() == 0);
    }

//    /**
//     * Test all {@link FieldKey} that declare a field level via an {@link EntityType} have the correct attribute on them.
//     * 
//     * @throws Exception if error looking up getter method
//     */
//    public void testFieldLevelAttributes() throws Exception {
//
//        Collection<FieldKey> fieldKeys = FieldKey.values();
//        StringBuffer buffer = new StringBuffer();
//
//        for (FieldKey fieldKey : fieldKeys) {
//            Collection<EntityType> entityTypes = fieldKey.getEntityTypes();
//
//            if (!fieldKey.isMultitextDataFieldValue()) {
//                if (entityTypes != null && !entityTypes.isEmpty()) {
//                    for (EntityType entityType : entityTypes) {
//                        Class valueObject = entityType.toValueObjectClass();
//                        Field field = ReflectionUtility.getDeclaredField(fieldKey.toAttributeName(), valueObject);
//
//                        if (field == null) {
//                            if (hasGetterButNotSetter(valueObject, fieldKey)) {
//                                LOG.debug("Getter found for FieldKey  '" + fieldKey.getKey()
//                                    + "'  but no setter was found. Assuming this is a convenience method.");
//                            } else {
//                                if (hasGetterAndSetter(valueObject, fieldKey)) {
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append(KEY + fieldKey.getKey());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("Attribute5: " + fieldKey.toAttributeName());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("Class5: " + valueObject.getName());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("Has getter and setter without matching field5!");
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append(LINE);
//                                } else {
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append(KEY + fieldKey.getKey());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("Attribute6: " + fieldKey.toAttributeName());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("Class6: " + valueObject.getName());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append(LINE);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        assertTrue("All FieldKeys that declare a field level must represent a member variable on the class. "
//            + "The following FieldKey/ValueObject pairs do not: " + buffer.toString(), buffer.toString().length() == 0);
//    }

//    /**
//     * All classes which extend Selectable should use either {@link FieldKey.FieldSubType#SINGLE_SELECT} or
//     * {@link FieldKey.FieldSubType#MULTI_SELECT}.
//     */
//    public void testSelectableFieldSubTypes() {
//
//        Collection<FieldKey> fieldKeys = FieldKey.values();
//        StringBuffer buffer = new StringBuffer();
//
//        for (FieldKey fieldKey : fieldKeys) {
//            if (Selectable.class.isAssignableFrom(fieldKey.getFieldClass()) && !fieldKey.isPrimaryKeyDataField()) {
//                if (!fieldKey.isSelectableField()) {
//                    buffer.append(LINE_SEPARATOR);
//                    buffer.append(KEY + fieldKey.getKey());
//                    buffer.append(LINE_SEPARATOR);
//                    buffer.append("FieldSubType7: " + fieldKey.getFieldSubType());
//                    buffer.append(LINE_SEPARATOR);
//                    buffer.append(LINE);
//                }
//            }
//        }
//
//        assertTrue("All FieldKeys whose field class' are instances of Selectable must define their FieldSubTypes as "
//            + "SINGLE_SELECT or MULTI_SELECT. The following FieldKeys do not: " + buffer.toString(), buffer.toString()
//                .length() == 0);
//    }

//    /**
//     * All FieldKeys that are instances of {@link DataField} must use the {@link FieldKey.FieldType#VA_DATA_FIELD}.
//     * 
//     * @throws Exception if error calling {@link Class#forName(String, boolean, ClassLoader)}
//     */
//    public void testVaDataFieldFieldTypes() throws Exception {
//
//        Collection<FieldKey> fieldKeys = FieldKey.values();
//        StringBuffer buffer = new StringBuffer();
//
//        for (FieldKey fieldKey : fieldKeys) {
//            if (!fieldKey.isMultitextDataFieldValue()) {
//                String type = getFieldKeyType(fieldKey).toString();
//
//                if (type.contains("<")) {
//                    type = type.substring(0, type.indexOf('<'));
//                }
//
//                if (type.startsWith(CLASS)) {
//                    type = type.substring(NUM_6);
//                }
//
//                Class typeClass = Class.forName(type, true, Thread.currentThread().getContextClassLoader());
//
//                if (DataField.class.isAssignableFrom(typeClass)) {
//                    if (!fieldKey.isVaDataField()) {
//                        buffer.append(LINE_SEPARATOR);
//                        buffer.append(KEY + fieldKey.getKey());
//                        buffer.append(LINE_SEPARATOR);
//                        buffer.append("FieldSubType: " + fieldKey.getFieldSubType());
//                        buffer.append(LINE_SEPARATOR);
//                        buffer.append(LINE);
//                    }
//                }
//            }
//        }
//
//        assertTrue("All FieldKeys whose generic types are instances of DataField must define their FieldType as "
//            + "VA_DATA_FIELD. The following FieldKeys do not: " + buffer.toString(), buffer.toString().length() == 0);
//    }

    /**
     * All FieldKeys that represent VA Data Fields must use one of the the VA Data Field {@link FieldKey.FieldSubType}.
     */
    public void testVaDataFieldFieldSubTypes() {

        Collection<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer buffer = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (fieldKey.isVaDataField()) {
                if (!(fieldKey.isSimpleDataField() || fieldKey.isListDataField() || fieldKey.isPrimaryKeyDataField()
                    || fieldKey.isMultitextDataField() || fieldKey.isGroupDataField())) {
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(KEY + fieldKey.getKey());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append("FieldSubType: " + fieldKey.getFieldSubType());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(LINE);
                }
            }
        }

        assertTrue("All FieldKeys whose FieldType is VA_DATA_FIELD must use the correct FieldSubType. "
            + KEYS_DO_NOT + buffer.toString(), buffer.toString().length() == 0);
    }

//    /**
//     * All FieldKeys that represent attributes on a VO must not use one of the VA Data Field sub types.
//     */
//    public void testDataFieldSubTypes() {
//
//        Collection<FieldKey> fieldKeys = FieldKey.values();
//        StringBuffer buffer = new StringBuffer();
//
//        for (FieldKey fieldKey : fieldKeys) {
//            if (fieldKey.isDataField()) {
//                if (fieldKey.isSimpleDataField() || fieldKey.isListDataField() || fieldKey.isPrimaryKeyDataField()
//                    || fieldKey.isMultitextDataField() || fieldKey.isGroupDataField()) {
//                    buffer.append(LINE_SEPARATOR);
//                    buffer.append(KEY + fieldKey.getKey());
//                    buffer.append(LINE_SEPARATOR);
//                    buffer.append("FieldSubType: " + fieldKey.getFieldSubType());
//                    buffer.append(LINE_SEPARATOR);
//                    buffer.append(LINE);
//                }
//            }
//        }
//
//        assertTrue("All FieldKeys whose FieldType is DATA_FIELD must use the correct FieldSubType. "
//            + KEYS_DO_NOT + buffer.toString(), buffer.toString().length() == 0);
//    }

    /**
     * FieldKeys marked as {@link FieldKey.FieldSubType#MULTI_SELECT} should have Collection generic types.
     * 
     * @throws Exception if error calling {@link Class#forName(String, boolean, ClassLoader)}
     */
    public void testMultiSelectSelectables() throws Exception {

        Collection<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer buffer = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (fieldKey.isMultiSelectField()) {
                Type genericType = getFieldKeyType(fieldKey);
                String type = genericType.toString();

                // check the "<" value for testMultiSelectSelectables
                if (type.contains("<")) {
                    type = type.substring(0, type.indexOf('<'));
                }

                if (type.startsWith(CLASS)) {
                    type = type.substring(NUM_6);
                }

                Class typeClass = Class.forName(type, true, Thread.currentThread().getContextClassLoader());

                // check the isAssignableFrom for testMultiSelectSelectables
                if (!Collection.class.isAssignableFrom(typeClass)) {
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(KEY + fieldKey.getKey());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(FK_GEN_TYPE + genericType.toString());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(LINE);
                }
            }
        }

        assertTrue("All FieldKeys whose FieldSubType is MULTI_SELECT must have a Collection generic type. "
            + KEYS_DO_NOT + buffer.toString(), buffer.toString().length() == 0);
    }

    /**
     * FieldKeys marked as {@link FieldKey.FieldSubType#SINGLE_SELECT} should not have Collection generic types.
     * 
     * @throws Exception if error calling {@link Class#forName(String, boolean, ClassLoader)}
     */
    public void testSingleSelectSelectables() throws Exception {

        Collection<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer buffer = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (fieldKey.isSingleSelectField()) {
                Type genericType = getFieldKeyType(fieldKey);
                String type = genericType.toString();

                if (type.contains("<")) {
                    type = type.substring(0, type.indexOf('<'));
                }

                if (type.startsWith(CLASS)) {
                    type = type.substring(NUM_6);
                }

                // class.forName checks for the correct type of class
                Class typeClass = Class.forName(type, true, Thread.currentThread().getContextClassLoader());

                if (Collection.class.isAssignableFrom(typeClass)) {
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(KEY + fieldKey.getKey());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(FK_GEN_TYPE + genericType.toString());
                    buffer.append(LINE_SEPARATOR);
                    buffer.append(LINE);
                }
            }
        }

        assertTrue("All FieldKeys whose FieldSubType is SINGLE_SELECT must not have a Collection generic type. "
            + "The following FieldKeys do: " + buffer.toString(), buffer.toString().length() == 0);
    }

    /**
     * Group data fields should have attributes on their VOs for the grouped fields.
     * 
     * @throws Exception if error looking up getter methods
     */
    public void testGroupDataFields() throws Exception {

        Collection<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer buffer = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (fieldKey.isGroupDataField() || fieldKey.isGroupListDataField()) {

                // Instances of GroupDataField and GroupListDataField don't have the problem of not having attributes
                // since the attributes are stored in a List<DataField>.
                if (!(GroupDataField.class.isAssignableFrom(fieldKey.getFieldClass()) || GroupListDataField.class
                    .isAssignableFrom(fieldKey.getFieldClass()))) {

                    List<FieldKey> groupedFields = fieldKey.getGroupedFields();

                    for (FieldKey groupedField : groupedFields) {
                        Field field = ReflectionUtility.getDeclaredField(groupedField.toAttributeName(), fieldKey
                            .getFieldClass());

//                        Type fieldKeyType = 
                        getFieldKeyType(groupedField);

                        String attr = "Attribute: ";
                        String clazz = "Class: ";
                        String groupedKey = "Grouped FieldKey: ";

                        if (field == null) {
                            if (hasGetterButNotSetter(fieldKey.getFieldClass(), groupedField)) {
                                LOG.debug("Getter found for FieldKey '" + groupedField.getKey()
                                    + "' but no setter was found. Assuming this is a convenience method.");
                            } else {
                                if (hasGetterAndSetter(fieldKey.getFieldClass(), groupedField)) {
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(KEY + fieldKey.getKey());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(groupedKey + groupedField.getKey());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(attr + groupedField.toAttributeName());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(clazz + fieldKey.getFieldClass().getName());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append("Has getter and setter without matching field!");
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(LINE);
                                } else {
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(KEY + fieldKey.getKey());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(groupedKey + groupedField.getKey());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(attr + groupedField.toAttributeName());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(clazz + fieldKey.getFieldClass().getName());
                                    buffer.append(LINE_SEPARATOR);
                                    buffer.append(LINE);
                                }
                            }
                        }
                    }
                }
            }
        }

        assertTrue("All Group and Group List FieldKeys must have member variables on the class for each grouped field. "
            + "The following FieldKey/Grouped FieldKey pairs do not: " + buffer.toString(), buffer.toString().length() == 0);
    }

//    /**
//     * Group data fields should have attributes on their VOs for the grouped fields.
//     * 
//     * @throws Exception if error finding methods on ValueObject class
//     */
//    public void testGroupedDataFieldTypes() throws Exception {
//
//        Collection<FieldKey> fieldKeys = FieldKey.values();
//        StringBuffer buffer = new StringBuffer();
//
//        for (FieldKey fieldKey : fieldKeys) {
//            if (fieldKey.isGroupDataField() || fieldKey.isGroupListDataField()) {
//
//                // Instances of GroupDataField and GroupListDataField don't have the problem of not having attributes
//                // since the attributes are stored in a List<DataField>.
//                if (!(GroupDataField.class.isAssignableFrom(fieldKey.getFieldClass()) || GroupListDataField.class
//                    .isAssignableFrom(fieldKey.getFieldClass()))) {
//
//                    List<FieldKey> groupedFields = fieldKey.getGroupedFields();
//
//                    for (FieldKey groupedField : groupedFields) {
//                        Field field = ReflectionUtility.getDeclaredField(groupedField.toAttributeName(), fieldKey
//                            .getFieldClass());
//                        Type fieldKeyType = getFieldKeyType(groupedField);
//
//                        if (field == null) {
//                            LOG.debug("Unable to test " + groupedField.getKey() + " on ValueObject "
//                                + fieldKey.getFieldClass().getName() + " because the field couldn't be found at all. "
//                                + "Verify that testGroupDataFields() is passing!  Looking for getter and setter methods...");
//
//                            if (hasGetterButNotSetter(fieldKey.getFieldClass(), fieldKey)) {
//                                Method getter = findGetter(fieldKey.getFieldClass(), groupedField);
//                                Type returnType = getter.getGenericReturnType();
//
//                                if (!returnType.equals(fieldKeyType)) {
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append(KEY + fieldKey.getKey());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("Grouped FieldKey: " + groupedField.getKey());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append(FK_GEN_TYPE + fieldKeyType.toString());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("ValueObject: " + fieldKey.getFieldClass().getName());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("ValueObject Method: " + getter.getName());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append("ValueObject Method Return Type: " + returnType.toString());
//                                    buffer.append(LINE_SEPARATOR);
//                                    buffer.append(LINE);
//                                }
//                            } else if (hasGetterAndSetter(fieldKey.getFieldClass(), groupedField)) {
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(KEY + groupedField.getKey());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(FK_GEN_TYPE + fieldKeyType.toString());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("ValueObject: " + fieldKey.getFieldClass().getName());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("Has getter and setter without matching field!");
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(LINE);
//                            }
//                        } else {
//                            Type implementationType = field.getGenericType();
//
//                            if (field.getType().isPrimitive()) {
//                                implementationType = (Type) ClassUtils.primitiveToWrapper(field.getType());
//                            }
//
//                            if (!implementationType.equals(fieldKeyType)) {
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(KEY + groupedField.getKey());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(FK_GEN_TYPE + fieldKeyType.toString());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("ValueObject: " + fieldKey.getFieldClass().getName());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append("ValueObject Attribute Type: " + implementationType.toString());
//                                buffer.append(LINE_SEPARATOR);
//                                buffer.append(LINE);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        assertTrue("All grouped FieldKeys must have generic types equal to the attributes on the respective group "
//            + "ValueObjects. The following FieldKeys do not: " + buffer.toString(), buffer.toString().length() == 0);
//    }

    /**
     * Look up the getter method via reflection.
     * 
     * @param valueObject Class to find getter method on
     * @param fieldKey {@link FieldKey} for which to find getter method
     * @return getter Method
     * @throws NoSuchMethodException if error looking up getter method
     */
    @SuppressWarnings("unused")
    private Method findGetter(Class valueObject, FieldKey fieldKey) throws NoSuchMethodException {

        Method getter;

        try {
            getter = valueObject.getMethod(createGetMethod(fieldKey));
        } catch (NoSuchMethodException e) {
            try {
                getter = valueObject.getMethod(createIsMethod(fieldKey));
            } catch (NoSuchMethodException e2) {
                getter = valueObject.getMethod(createHasMethod(fieldKey));
            }
        }

        return getter;
    }

    /**
     * Test that Group List FieldKeys are either implemented by {@link GroupListDataField} or a {@link Collection} of
     * {@link ValueObject}.
     * 
     * @throws Exception if error in calling {@link Class#forName(String, boolean, ClassLoader)}
     */
    public void testGroupListDataFields() throws Exception {

        Collection<FieldKey> fieldKeys = FieldKey.values();
        StringBuffer buffer = new StringBuffer();

        for (FieldKey fieldKey : fieldKeys) {
            if (fieldKey.isGroupListDataField()) {
                if (!GroupListDataField.class.isAssignableFrom(fieldKey.getFieldClass())) {
                    Type genericType = getFieldKeyType(fieldKey);
                    String type = genericType.toString();

                    if (type.contains("<")) {
                        type = type.substring(0, type.indexOf('<'));
                    }

                    if (type.startsWith(CLASS)) {
                        type = type.substring(NUM_6);
                    }

                    Class typeClass = Class.forName(type, true, Thread.currentThread().getContextClassLoader());

                    if (!Collection.class.isAssignableFrom(typeClass)) {
                        buffer.append(LINE_SEPARATOR);
                        buffer.append(KEY + fieldKey.getKey());
                        buffer.append(LINE_SEPARATOR);
                        buffer.append(FK_GEN_TYPE + genericType.toString());
                        buffer.append(LINE_SEPARATOR);
                        buffer.append(LINE);
                    }
                }
            }
        }

        assertTrue("All Group List FieldKeys must have a GroupListDataField or Collection implementation type. "
            + KEYS_DO_NOT + buffer.toString(), buffer.toString().length() == 0);
    }
}
