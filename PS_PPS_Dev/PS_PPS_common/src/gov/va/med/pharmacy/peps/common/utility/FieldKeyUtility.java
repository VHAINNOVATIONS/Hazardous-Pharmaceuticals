/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;


/**
 * Utility methods for the {@link FieldKey} class.
 */
public class FieldKeyUtility {
    private static final int MAX_FIELDS_PER_TAB = 51; // should be divisible by 3

    /**
     * Cannot instantiate.
     */
    private FieldKeyUtility() {
        super();
    }
    
    /**
     * Removes duplicate FieldKeys from a list of FieldKeys, while retaining order
     * 
     * @param list - list of {@link FieldKey}
     * @return original List<FieldKey> with duplicates removed
     */
    public static List<FieldKey> removeDuplicateWithOrder(List<FieldKey> list) {
        Set<FieldKey> set = new LinkedHashSet<FieldKey>(list);

        return new ArrayList<FieldKey>(set);
    }

    /**
     * Detects duplicate {@link FieldKey} in a list
     * 
     * @param list - list of {@link FieldKey}
     * @return boolean indicating presence of duplicates
     */
    public static boolean detectDuplicates(List<FieldKey> list) {
        Set<FieldKey> set = new HashSet<FieldKey>(list);

        return list.size() != set.size();
    }

    /**
     * Return all {@link FieldKey} instances for the given {@link EntityType} sorted by localized name. The FieldKey names
     * are localized using the given {@link Locale}.
     * 
     * @param entityType {@link EntityType} for which to retrieve FieldKeys
     * @param locale {@link Locale} to localize
     * @return {@link List} of {@link FieldKey} instances sorted by localized name
     */
    public static List<FieldKey> getSortedFieldKeys(EntityType entityType, Locale locale) {
        List<FieldKey> fields = new ArrayList<FieldKey>(FieldKey.getDataFields(entityType));
        sortFieldKeys(fields, locale, entityType);

        return fields;
    }

    /**
     * Return a filtered list of {@link FieldKey} instances for the given {@link EntityType} sorted by localized name. The
     * FieldKey names are localized using the given {@link Locale}.
     * <p>
     * The given ignored FieldKeys will not be in the returned list. In addition, fields that are really containers for other
     * fields or were specifically requested to be ignored will not be returned.
     * 
     * @param entityType {@link EntityType} for which to retrieve FieldKeys
     * @param locale {@link Locale} to localize
     * @param ignore array of FieldKey to remove from the generated list
     * @return {@link List} of {@link FieldKey} instances sorted by localized name
     */
    public static List<FieldKey> getSortedFieldKeys(EntityType entityType, Locale locale, FieldKey... ignore) {
        List<FieldKey> ignoredFields = Arrays.asList(ignore);
        List<FieldKey> allFields = new ArrayList<FieldKey>(FieldKey.getDataFields(entityType));
        List<FieldKey> displayedFields = new ArrayList<FieldKey>();

        // Remove fields that are really containers for other fields or that were requested to be ignored
        allFields.remove(FieldKey.ID);
        allFields.remove(FieldKey.VA_DATA_FIELDS);
        allFields.remove(FieldKey.NDC_COUNT);
        allFields.remove(FieldKey.NDC_LIST);
        allFields.remove(FieldKey.NDC_LONG_NAME);
        allFields.remove(FieldKey.PRODUCT_COUNT);
        allFields.remove(FieldKey.PRODUCT_LIST);
        allFields.remove(FieldKey.PRODUCT_LONG_NAME);
        allFields.remove(FieldKey.PRODUCT);
        allFields.remove(FieldKey.ORDERABLE_ITEM);
        allFields.remove(FieldKey.PRIMARY_DRUG_CLASS);
        allFields.remove(FieldKey.SECONDARY_DRUG_CLASS);
        allFields.remove(FieldKey.REQUEST_STATE);
        allFields.remove(FieldKey.ORDERABLE_ITEM_PARENT);
        allFields.remove(FieldKey.PREVIOUS_ORDERABLE_ITEM_PARENT);

        // Drug class name is not displayed, as it is not actually a field
        if (EntityType.DRUG_CLASS.equals(entityType)) {
            allFields.remove(FieldKey.VALUE);
        }

        if (EntityType.PRODUCT.equals(entityType)) {
            allFields.remove(FieldKey.PRICE_PER_ORDER_UNIT_SEARCHABLE);
            allFields.remove(FieldKey.PRICE_PER_DISPENSE_UNIT_SEARCHABLE);
        }

        if (entityType.isNdc()) {
            allFields.remove(FieldKey.REQUEST_REJECTION_REASON);
            allFields.remove(FieldKey.REJECTION_REASON_TEXT);
        }

        if (!entityType.isDomainType()) {
            allFields.remove(FieldKey.APPLICATION_PACKAGE_USE_SEARCHABLE);
        }

        // Filter out the FieldKeys that we don't want displayed individually as they are grouped by other fields or are
        // explicitly ignored. These include fields in the ignorFields Collection, multitext data field "values",
        // and grouped data fields whose group is not also being displayed.
        for (FieldKey fieldKey : allFields) {
            if (!fieldKey.isMultitextDataFieldValue() && !ignoredFields.contains(fieldKey)
                && !(fieldKey.isGroupedDataField() && allFields.contains(fieldKey.getGroupKey()))) {
                displayedFields.add(fieldKey);
            }
        }

        sortFieldKeys(displayedFields, locale, entityType);

        return displayedFields;
    }

    /**
     * Sort the given {@link FieldKey} by localized name. The FieldKey names are localized using the given {@link Locale}.
     * 
     * @param fields List<FieldKey> fields to sort
     * @param entityType {@link EntityType} for which to localize
     * @param locale {@link Locale} to localize
     */
    public static void sortFieldKeys(List<FieldKey> fields, Locale locale, EntityType entityType) {
        Collections.sort(fields, new FieldKeyComparator(locale, entityType));
    }

    /**
     * Return a List of List<FieldKey> as a representation of the {@link FieldKey} instances to be placed within alphabetic
     * tabs (e.g., tabs for A-D, E-H, etc.).
     * 
     * First retrieve all {@link FieldKey} for the given entity type. Then sort these {@link FieldKey} by their localized
     * names. Finally, place these FieldKey into a List of Lists where each contained List<FieldKey> is one tab.
     * 
     * The letter range (e.g., A-D) for each tab can be retrieved by getting the first letter of the first and last
     * {@link FieldKey} in the contained List<FieldKey>.
     * 
     * @param entityType {@link EntityType} to retrieve {@link FieldKey}
     * @param locale {@link Locale} to localize {@link FieldKey} names
     * @param ignore array of FieldKey to remove from the generated list
     * @return List<List<FieldKey>> to represent alphabetic tabs.
     * 
     * @see FieldKey#getLocalizedName(Locale)
     * @see FieldKeyComparator
     */
    public static List<List<FieldKey>> getAlphabeticTabs(EntityType entityType, Locale locale, FieldKey... ignore) {
        List<FieldKey> displayedFields = getSortedFieldKeys(entityType, locale, ignore);

        // Split the fields into a Map containing keys of the first letter and
        // values Lists of the fields which start with that letter
        Map<String, List<FieldKey>> alphaMap = new LinkedHashMap<String, List<FieldKey>>();

        for (FieldKey fieldKey : displayedFields) {
            String firstCharacter = fieldKey.getLocalizedName(locale, entityType).substring(0, 1).toUpperCase();

            if (!alphaMap.containsKey(firstCharacter)) {
                alphaMap.put(firstCharacter, new ArrayList<FieldKey>());
            }

            alphaMap.get(firstCharacter).add(fieldKey);
        }

        List<List<FieldKey>> tabs = new ArrayList<List<FieldKey>>();
        List<FieldKey> tab = new ArrayList<FieldKey>();
        tabs.add(tab);

        for (String firstCharacter : alphaMap.keySet()) {
            List<FieldKey> currentCharacter = alphaMap.get(firstCharacter);

            if (tab.isEmpty() || tab.size() + currentCharacter.size() <= MAX_FIELDS_PER_TAB) {
                tab.addAll(currentCharacter);
            } else {
                tab = new ArrayList<FieldKey>();
                tab.addAll(currentCharacter);
                tabs.add(tab);
            }
        }

        return tabs;
    }

    /**
     * Get the FieldKey for the children list (e.g., Products on an Orderable Item or NDCs on a Product). If the given
     * {@link EntityType} does not have a child type, return null.
     * 
     * @param parentType {@link EntityType} of the parent
     * @return {@link FieldKey} for the child
     */
    public static FieldKey getChildFieldKey(EntityType parentType) {
        FieldKey child = null;

        if (parentType.hasChild()) {
            EntityType childType = parentType.getChild();

            switch (childType) {
                case PRODUCT:
                    child = FieldKey.PRODUCT_LIST;
                    break;
                case NDC:
                    child = FieldKey.NDC_LIST;
                    break;
                default:
                    break;
            }
        }

        return child;
    }

    /**
     * Get the FieldKey for the children count (e.g., number of Products on an Orderable Item or NDCs on a Product). If the
     * given {@link EntityType} does not have a child type, return null.
     * 
     * @param parentType {@link EntityType} of the parent
     * @return {@link FieldKey} for the child count
     */
    public static FieldKey getChildCountFieldKey(EntityType parentType) {
        FieldKey child = null;

        if (parentType.hasChild()) {
            EntityType childType = parentType.getChild();

            switch (childType) {
                case PRODUCT:
                    child = FieldKey.PRODUCT_COUNT;
                    break;
                case NDC:
                    child = FieldKey.NDC_COUNT;
                    break;
                default:
                    break;
            }
        }

        return child;
    }

    /**
     * Get the FieldKey for the parent (e.g., Orderable Item on a Product or Product on a NDC). If the given
     * {@link EntityType} does not have a parent type, return null.
     * 
     * @param childType {@link EntityType} of the child
     * @return {@link FieldKey} for the child
     */
    public static FieldKey getParentFieldKey(EntityType childType) {
        FieldKey parent = null;

        if (childType.hasParent()) {
            EntityType parentType = childType.getParent();

            switch (parentType) {
                case ORDERABLE_ITEM:
                    parent = FieldKey.ORDERABLE_ITEM;
                    break;
                case PRODUCT:
                    parent = FieldKey.PRODUCT;
                    break;
                case NDC:
                    parent = FieldKey.NDC;
                    break;
                default:
                    break;
            }
        }

        return parent;
    }

    /**
     * Return true if the given {@link FieldKey} has an accepted {@link ModDifferenceVo} within the given Collection of
     * {@link ModDifferenceVo}.
     * 
     * @param fieldKey {@link FieldKey}
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @return boolean
     */
    public static boolean isFieldModified(FieldKey fieldKey, Collection<ModDifferenceVo> modDifferences) {
        boolean modified = false;

        if (modDifferences != null && !modDifferences.isEmpty()) {
            for (ModDifferenceVo modDifference : modDifferences) {
                if (fieldKey.equals(modDifference.getDifference().getFieldKey()) && modDifference.isAcceptChange()) {
                    modified = true;
                    break;
                }
            }
        }

        return modified;
    }

    /**
     * Find the first {@link ModDifferenceVo} for the given {@link FieldKey} that is accepted. If no such
     * {@link ModDifferenceVo} is found, return null.
     * 
     * @param fieldKey {@link FieldKey} for {@link ModDifferenceVo} to find
     * @param modDifferences Collection of {@link ModDifferenceVo} to search
     * @return accepted {@link ModDifferenceVo} if found, else null
     * 
     * @see #isFieldModified(FieldKey, Collection)
     */
    public static ModDifferenceVo getAcceptedModDifference(FieldKey fieldKey, Collection<ModDifferenceVo> modDifferences) {
        ModDifferenceVo result = null;

        if (modDifferences != null && !modDifferences.isEmpty()) {
            for (ModDifferenceVo modDifference : modDifferences) {
                if (fieldKey.equals(modDifference.getDifference().getFieldKey()) && modDifference.isAcceptChange()) {
                    result = modDifference;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 
     * Returns all of the NDF IEN field keys, used to disable all NDF IEN fields 
     *
     * @return all of the NDF IEN field keys
     */
    public static Set<FieldKey> getAllNdfIenFieldKeys() {

        Set<FieldKey> fields = new HashSet<FieldKey>();
        
        fields.add(FieldKey.NDF_PRODUCT_IEN);
        fields.add(FieldKey.NDC_IEN);
        fields.add(FieldKey.INGREDIENT_IEN);
        fields.add(FieldKey.DRUG_UNIT_IEN);
        fields.add(FieldKey.DRUGCLASS_IEN);
        fields.add(FieldKey.PACKAGETYPE_IEN);
        fields.add(FieldKey.DOSAGE_FORM_IEN);
        fields.add(FieldKey.GENERIC_IEN);
        fields.add(FieldKey.DISPENSE_UNIT_IEN);
        fields.add(FieldKey.MANUFACTURER_IEN);

        return fields;
    }

}
