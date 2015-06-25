/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.diff;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * Check if any attribute values between oldValue and newValue are different.
 */
public class Diff {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Diff.class);

    /**
     * private c'tor
     *
     */
    private Diff() {

    }

    /**
     * Check if any attribute values between oldValue and newValue are different. If any are, return the each field's
     * difference as an element of a Collection<Difference>. Difference is determined by calling equals() on each field.
     * <p>
     * Both the oldValue and newValue must be of the same class type, otherwise a CommonException is thrown. At least one
     * value must not be null, otherwise an CommonException is thrown.
     * <p>
     * A non-null value is inspected via reflection for all methods that begin with "get". This method is called on both the
     * oldValue and newValue, with a subsequent call to equals() to test if the values are equal. If the values are not
     * equal, the FieldKey is determined via the DataField or attribute name derived from the {@link Field} name and
     * {@link ValueObject#mapAttributeToFieldKey(String)}). The FieldKey, oldValue, and newValue are used to create a new
     * instance of Difference which is added to the Collection to return.
     * <p>
     * Since the {@link #equals(Object)} method is called to determine if there is a difference, special care must be taken
     * in terms of the Class type the attributes extend. If the attributes are Strings they must be equal, including case. If
     * the attributes are instances of other {@link ValueObject} (or really any {@link Object}), then the entire Object must
     * be equal.
     * <p>
     * If any attributes are ValueObjects or Collections, these are both assumed to have FieldKeys (to the Class of
     * ValueObject or attribute name of Collection), but also are not recursed into. In other words, both the ValueObject and
     * Collection are checked for equality on a whole, not for each attribute or element inside of the ValueObject or
     * Collection.
     * <p>
     * Fields are ignored using the {@link IgnoreDifference} annotation. Any ignored field is not considered when creating
     * the Collection of {@link Difference}.
     * 
     * @param oldValue ValueObject containing the old value
     * @param newValue ValueObject containing the new value
     * @return Collection<Difference> with each difference per field that was not equal
     * 
     * @see ValueObject#equals(Object)
     * @see ValueObject#mapAttributeToFieldKey(String)
     */
    public static Collection<Difference> checkForDifferences(ValueObject oldValue, ValueObject newValue) {
        if (oldValue == null && newValue == null) {
            throw new CommonException(CommonException.DIFF_NULL_VALUES);
        }

        if (!isSameClass(oldValue, newValue)) {
            throw new CommonException(CommonException.DIFF_SAME_CLASS_TYPE);
        }

        Collection<Difference> differences = new ArrayList<Difference>();

  //      if (!oldValue.equals(newValue)) {
            Set<Field> fields = findFields(oldValue, newValue);

            for (Field field : fields) {
                if (!isIgnored(field)) {
                    try {
                        field.setAccessible(true);
                        String attributeName = field.getName();
                        Object oldAttribute = field.get(oldValue);
                        Object newAttribute = field.get(newValue);

                        checkAttributes(differences, oldAttribute, newAttribute, attributeName);
                    } catch (IllegalAccessException e) {
                        LOG.warn("Unable to check if an attribute was different", e);
                    }
                }
            }
     //   }

        return differences;
    }

    /**
     * checkAttributes.
     * @param differences differences
     * @param oldAttribute oldAttribute
     * @param newAttribute newAttribute
     * @param attributeName attributeName
     * 
     */
    public static void checkAttributes(Collection<Difference> differences, Object oldAttribute, 
                                       Object newAttribute, String attributeName) {
        if (attributesNotEqual(oldAttribute, newAttribute)) {
            if (oldAttribute instanceof DataFields) {
                differences.addAll(diff((DataFields) oldAttribute, (DataFields) newAttribute));
            } else if (oldAttribute instanceof DataField) {
                differences.add(diff((DataField) oldAttribute, (DataField) newAttribute));
            } else {
                FieldKey fieldKey = FieldKey.toFieldKey(attributeName);

                if (fieldKey != null) {
                    differences.add(new Difference(FieldKey.toFieldKey(attributeName), oldAttribute,
                        newAttribute));
                }
            }
        }
    }
    
    /**
     * True if the given field has the {@link IgnoreDifference} annotation.
     * 
     * @param field Field to test
     * @return boolean
     */
    private static boolean isIgnored(Field field) {
        IgnoreDifference annotation = field.getAnnotation(IgnoreDifference.class);

        return annotation != null;
    }

    /**
     * Return true if both ValueObjects are of the same class.
     * 
     * @param oldValue Old value
     * @param newValue New value
     * @return boolean
     */
    private static boolean isSameClass(ValueObject oldValue, ValueObject newValue) {
        boolean equal = false;

        if (oldValue != null && newValue != null) {
            equal = oldValue.getClass().equals(newValue.getClass());
        }

        return equal;
    }

    /**
     * Return false if both attributes are null.
     * <p>
     * Return true if both attributes are not null and if the objects are not equal using {@link Object#equals(Object)} or if
     * one of the attributes is null.
     * 
     * @param oldAttribute Old attribute value
     * @param newAttribute New attribute value
     * @return boolean
     */
    private static boolean attributesNotEqual(Object oldAttribute, Object newAttribute) {
        if (oldAttribute == null && newAttribute == null) {
            return false;
        }

        // Specific code to handle collections, assuming order doesn't matter for all collections 
        // if they have the same values
        if (oldAttribute != null && newAttribute != null) {
            
            if (oldAttribute instanceof Collection && newAttribute instanceof Collection) {
                
                Collection oldCollection = (Collection) oldAttribute;
                Collection newCollection = (Collection) newAttribute;
                
                return !(oldCollection.size() == newCollection.size() && oldCollection.containsAll(newCollection));
            }
            
        }
        
        return (oldAttribute != null && !oldAttribute.equals(newAttribute))
            || (newAttribute != null && !newAttribute.equals(oldAttribute));
    }

    /**
     * Create a new difference between the old and new data field. The two fields are assumed unequal, therefore at least one
     * is not null.
     * 
     * @param oldDataField Old value
     * @param newDataField New value
     * @return Difference
     */
    private static Difference diff(DataField oldDataField, DataField newDataField) {
        if (oldDataField == null) {
            return new Difference(newDataField.getKey(), oldDataField, newDataField);
        } else {
            return new Difference(oldDataField.getKey(), oldDataField, newDataField);
        }
    }

    /**
     * Perform a diff between to DataFields instances. The key set of FieldKeys from both are retrieved and used two retrieve
     * the individual DataFields, to potentially create new Difference instances for each DataField. The two fields are
     * assumed unequal.
     * 
     * @param oldDataFields Old value
     * @param newDataFields New value
     * @return COllection<Difference>
     */
    private static Collection<Difference> diff(DataFields oldDataFields, DataFields newDataFields) {
        Collection<Difference> differences = new ArrayList<Difference>();

        Set<FieldKey> fieldKeys = new HashSet<FieldKey>();
        fieldKeys.addAll(oldDataFields.getDataFields().keySet());
        fieldKeys.addAll(newDataFields.getDataFields().keySet());

        for (FieldKey fieldKey : fieldKeys) {
            DataField oldDataField = oldDataFields.getDataField(fieldKey);
            DataField newDataField = newDataFields.getDataField(fieldKey);

            if (oldDataField == null) {
                differences.add(new Difference(newDataField.getKey(), null, newDataField));
            } else if (newDataField == null) {
                differences.add(new Difference(oldDataField.getKey(), oldDataField, null));
            } else {
                if (!oldDataField.equals(newDataField)) {
                    differences.add(new Difference(oldDataField.getKey(), oldDataField, newDataField));
                }
            }
        }

        return differences;
    }

    /**
     * Get all {@link Field} from either the old or new value class, interfaces and super class and super interfaces (all
     * fields and inherited fields). Both old and new values cannot be null.
     * 
     * @param oldValue Object old value
     * @param newValue Object new value
     * @return {@link Field} array of all fields on the class and super class
     */
    private static Set<Field> findFields(Object oldValue, Object newValue) {
        Set<Field> fields = new HashSet<Field>();

        if (oldValue == null) {
            fields = ReflectionUtility.getDeclaredFields(newValue.getClass());
        } else {
            fields = ReflectionUtility.getDeclaredFields(oldValue.getClass());
        }

        return fields;
    }
}
