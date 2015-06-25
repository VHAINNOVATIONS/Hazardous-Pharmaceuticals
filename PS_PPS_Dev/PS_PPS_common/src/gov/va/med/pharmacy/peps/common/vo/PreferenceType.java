/**
 * Source file created in 2007 by Southwest Research Institute
 */
 
 
package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A Selectable enum that is primarily a wrapper for entity type
 */
public enum PreferenceType implements Selectable {

    
    /** ADMINISTRATION_SCHEDULE */
    ADMINISTRATION_SCHEDULE("0", EntityType.ADMINISTRATION_SCHEDULE),

    /** DOSAGE_FORM */
    DOSAGE_FORM("1", EntityType.DOSAGE_FORM),

    /** DOSE_UNIT */
    DOSE_UNIT("2", EntityType.DOSE_UNIT),

    /** DRUG_TEXT */
    DRUG_TEXT("3", EntityType.DRUG_TEXT),

    /** DRUG_UNIT */
    DRUG_UNIT("4", EntityType.DRUG_UNIT),

    /** GENERIC_NAME */
    GENERIC_NAME("5", EntityType.GENERIC_NAME),

    /** INGREDIENT */
    INGREDIENT("6", EntityType.INGREDIENT),

    /** LOCAL_MEDICATION_ROUTE */
    LOCAL_MEDICATION_ROUTE("7", EntityType.LOCAL_MEDICATION_ROUTE),

    /** MANUFACTURER */
    MANUFACTURER("8", EntityType.MANUFACTURER),

    /** MEDICATION_INSTRUCTION */
    MEDICATION_INSTRUCTION("9", EntityType.MEDICATION_INSTRUCTION),

    /** ORDER_UNIT */
    ORDER_UNIT("10", EntityType.ORDER_UNIT),

    /** PACKAGE_TYPE */
    PACKAGE_TYPE("11", EntityType.PACKAGE_TYPE),

    /** PHARMACY_SYSTEM */
    PHARMACY_SYSTEM("12", EntityType.PHARMACY_SYSTEM),

    /** RX_CONSULT */
    RX_CONSULT("13", EntityType.RX_CONSULT),

    /** SPECIAL_HANDLING */
    SPECIAL_HANDLING("14", EntityType.SPECIAL_HANDLING),

    /** STANDARD_MED_ROUTE */
    STANDARD_MED_ROUTE("15", EntityType.STANDARD_MED_ROUTE),

    /** DISPENSE_UNIT */
    DISPENSE_UNIT("16", EntityType.DISPENSE_UNIT), //VA Dispense Unit on the screen

    /** DRUG_CLASS */
    DRUG_CLASS("17", EntityType.DRUG_CLASS), //VA Drug Class on the screen

    /** VUID_STATUS_HISTORY */
    VUID_STATUS_HISTORY("18", EntityType.VUID_STATUS_HISTORY),

    /** FDB_AUTO_UPDATE */
    FDB_AUTO_UPDATE("19", EntityType.FDB_AUTO_UPDATE),

    /** FDB_ADD */
    FDB_ADD("20", EntityType.FDB_UPDATE),

    /** FDB_UPDATE */
    FDB_UPDATE("21", EntityType.FDB_ADD),

    /** CATEGORY */
    CATEGORY("22", EntityType.CATEGORY),
    
    /** NDF_SYNCH_QUEUE */
    NDF_SYNCH_QUEUE("23", EntityType.NDF_SYNCH_QUEUE);
    
    private EntityType entityType;
    private String id;

    /**
     * c'tor
     * 
     * @param id id
     * @param entityType entityType
     */
    private PreferenceType(String id, EntityType entityType) {

        this.id = id;
        this.entityType = entityType;
    }

    /**
     * Factory method to convert to preference type. Reuses a similar method in the entityType
     * 
     * @param key key
     * @return PreferenceType
     */
    public static PreferenceType createFromString(String key) {
        return toPreferenceType(EntityType.createFromString(key));
    }
    
    /**
     * Convert the given String key for a {@link FieldKey#getKey()} into its respective {@link PreferenceType}. This
     * conversion process includes converting the string to upper case, replacing all periods with underscores, and finally
     * calling {@link PreferenceType#valueOf(String)}.
     * 
     * @param entityType EntityType key
     * @return {@link PreferenceType}
     */
    public static PreferenceType toPreferenceType(EntityType entityType) {
        String preferenceType = entityType.toFieldKey().toUpperCase().replace('.', '_');

        return PreferenceType.valueOf(preferenceType);
    }

    /**
     * getAll
     * 
     * @return list of preference types
     */
    public static List<PreferenceType> getAll() {
        ArrayList<PreferenceType> ret = new ArrayList<PreferenceType>();
        
        for (PreferenceType type : values()) {
            ret.add(type);
        }
        
        return ret;
    }

    /**
     * getId
     * 
     * @return id
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return id;
    }

    /**
     * getValue
     * 
     * @return value
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return FieldKey.getLocalizedName(entityType.toFieldKey(), Locale.getDefault());
    }

    /**
     * getEntityType
     * 
     * @return entityType property
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * setEntityType
     * 
     * @param entityType entityType property
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * setId
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }
}
