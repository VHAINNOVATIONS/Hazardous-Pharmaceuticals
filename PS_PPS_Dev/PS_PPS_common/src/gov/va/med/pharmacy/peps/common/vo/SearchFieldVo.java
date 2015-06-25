/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Comparator;
import java.util.Locale;


/**
 * Key used in map of DataFields that is unique to a certain entity type. 
 * Also used as portion of the key to localize the DataField name.

 */
public class SearchFieldVo extends ValueObject {
    
    private static final long serialVersionUID = 1L;

    private EntityType entityType;
    private FieldKey fieldKey;
    private FieldKey previousFieldKey;

    /**
     * Constructor
     *
     */
    public SearchFieldVo() {
        super();
    }

    /**
     * Constructor
     * 
     * @param key for the search field
     */
    public SearchFieldVo(String key) {

        setKey(key);
    }

    /**
     * Constructor
     * 
     * @param fieldKey property
     * @param entityType property
     */
    public SearchFieldVo(FieldKey fieldKey, EntityType entityType) {
        this.fieldKey = fieldKey;
        this.entityType = entityType;
    }

    /**
     * Remove the EntityType
     * 
     * @param key String
     * @param entityType property
     * 
     * @return String
     */
    private static String removeEntityType(String key, EntityType entityType) {

        return key.replaceFirst(entityType.getPrefix(), "");
    }

    /**
     * getEntityType
     * @return entityType property
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * setEntityType
     * @param entityType entityType property
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * getFieldKey
     * @return fieldKey property
     */
    public FieldKey getFieldKey() {
        return fieldKey;
    }

    /**
     * setFieldKey
     * @param fieldKey fieldKey property
     */
    public void setFieldKey(FieldKey fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * isChangeSeen
     * @return fieldKey property
     */
    public boolean isChangeSeen() {
        if (fieldKey != null) {
            if (previousFieldKey != null) {
                return !fieldKey.getKey().equals(previousFieldKey.getKey());
            }
        }

        // Else, fieldKey is null.
        return (previousFieldKey != null);
    }

    /**
     * Returns the localized abbreviation of the search field
     * 
     * @param locale location
     * @return localized abbreviation
     */
    public String getLocalizedAbbreviation(Locale locale) {
        return fieldKey.getLocalizedAbbreviation(locale, entityType);
    }
    
    /**
     * Returns the localized description of the search field
     * 
     * @param locale location
     * @return localized description
     */
    public String getLocalizedDescription(Locale locale) {
        return fieldKey.getLocalizedDescription(locale, entityType);
    }

    /**
     * returns the localized name of the search field
     * 
     * @param locale location
     * @return localized name
     */
    public String getLocalizedName(Locale locale) {
        return fieldKey.getLocalizedName(locale, entityType);
    }

    /**
     * getKey
     * @return key property
     */
    public String getKey() {
        return FieldKey.getEntityFieldKey(fieldKey, entityType);
    }
    
    /**
     * Set key property
     * 
     * @param key concatenated string with entity type and field key
     */
    public final void setKey(String key) {
        if (key == null) {
            return;
        }
        
        for (String entity : EntityType.getKeyMap().keySet()) {
            if (key.startsWith(entity)) {
                this.entityType = EntityType.getKeyMap().get(entity);
            }
        }
        
        String field = removeEntityType(key, entityType);

        // Keep track of the previous key value.
        previousFieldKey = this.fieldKey;        
        
        this.fieldKey = FieldKey.toFieldKey(field);
    }

    /**
     * Comparator to support alphabetic searches in sorted structures such as TreeSet.
     */
    public static class AlphabeticalComparator implements Comparator<SearchFieldVo> {
        
        /**
         * Compares search fields based on Localized Names 
         * 
         * @param o1 first search field
         * @param o2 second search field
         * @return Localized name's compareTo method
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(SearchFieldVo o1, SearchFieldVo o2) {
            return o1.getLocalizedName(Locale.getDefault()).toLowerCase().compareTo(
                   o2.getLocalizedName(Locale.getDefault()).toLowerCase());
        }
    }
}
