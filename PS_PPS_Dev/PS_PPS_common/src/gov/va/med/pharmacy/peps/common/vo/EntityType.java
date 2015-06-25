/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;
import org.springframework.util.StringUtils;


/**
 * Item type for a request or Managed Item.
 */
public enum EntityType {

    /**
     * ADMINISTRATION_SCHEDULE.
     */
    ADMINISTRATION_SCHEDULE(Type.DOMAIN_TYPE, true, false, NotificationType.APPROVED_ADMINISTRATION_SCHEDULE,
        NotificationType.REJECTED_ADMINISTRATION_SCHEDULE, NotificationType.MODIFIED_ADMINISTRATION_SCHEDULE,
        NotificationType.INACTIVATED_ADMINISTRATION_SCHEDULE, null),

    /**
      * DISPENSE_UNIT.
      */
    DISPENSE_UNIT(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_DISPENSE_UNIT,
            NotificationType.REJECTED_DISPENSE_UNIT, NotificationType.MODIFIED_DISPENSE_UNIT,
            NotificationType.INACTIVATED_DISPENSE_UNIT, null),

    /**
      * DOSAGE_FORM.
      */
    DOSAGE_FORM(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_DOSAGE_FORM,
        NotificationType.REJECTED_DOSAGE_FORM, NotificationType.MODIFIED_DOSAGE_FORM,
        NotificationType.INACTIVATED_DOSAGE_FORM, null),

    /**
     * DOSE_UNIT.
     */
    DOSE_UNIT(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_DOSE_UNIT,
        NotificationType.REJECTED_DOSE_UNIT, NotificationType.MODIFIED_DOSE_UNIT,
        NotificationType.INACTIVATED_DOSE_UNIT, null),

    /**
     * DRUG_CLASS.
     */
    DRUG_CLASS(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_DRUG_CLASS,
        NotificationType.REJECTED_DRUG_CLASS, NotificationType.MODIFIED_DRUG_CLASS,
        NotificationType.INACTIVATED_DRUG_CLASS, null),

    /**
     * DRUG_TEXT.
     */
    DRUG_TEXT(Type.DOMAIN_TYPE, false, false, NotificationType.APPROVED_DRUG_TEXT,
        NotificationType.REJECTED_DRUG_TEXT, NotificationType.MODIFIED_DRUG_TEXT,
        NotificationType.INACTIVATED_DRUG_TEXT, null),

    /**
     * FDB_AUTO_UPDATE.
     */
    FDB_AUTO_ADD(Type.OTHER_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * FDB_AUTO_ADD.
     */
    FDB_AUTO_UPDATE(Type.DOMAIN_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * NDF_SYNCH_QUEUE.
     */
    NDF_SYNCH_QUEUE(Type.DOMAIN_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * CATEGORY.
     */
    CATEGORY(Type.DOMAIN_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * FDB_UPDATE.
     */
    FDB_UPDATE(Type.DOMAIN_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * FDB_ADD.
     */
    FDB_ADD(Type.DOMAIN_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * DRUG_UNIT.
     */
    DRUG_UNIT(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_DRUG_UNIT,
        NotificationType.REJECTED_DRUG_UNIT, NotificationType.MODIFIED_DRUG_UNIT,
        NotificationType.INACTIVATED_DRUG_UNIT, null),

    /**
     * GENERIC_NAME
     */
    GENERIC_NAME(Type.DOMAIN_TYPE, false, false, NotificationType.APPROVED_GENERIC_NAME,
        NotificationType.REJECTED_GENERIC_NAME, NotificationType.MODIFIED_GENERIC_NAME,
        NotificationType.INACTIVATED_GENERIC_NAME, null),

    /**
     * INGREDIENT.
     */
    INGREDIENT(Type.DOMAIN_TYPE, false, false, NotificationType.APPROVED_INGREDIENT,
        NotificationType.REJECTED_INGREDIENT, NotificationType.MODIFIED_INGREDIENT,
        NotificationType.INACTIVATED_INGREDIENT, null),

    /**
     * MANUFACTURER.
     */
    MANUFACTURER(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_MANUFACTURER,
        NotificationType.REJECTED_MANUFACTURER, NotificationType.MODIFIED_MANUFACTURER,
        NotificationType.INACTIVATED_MANUFACTURER, null),

    /**
     * MEDICATION_INSTRUCTION.
     */
    MEDICATION_INSTRUCTION(Type.DOMAIN_TYPE, true, false, NotificationType.APPROVED_MEDICATION_INSTRUCTION,
        NotificationType.REJECTED_MEDICATION_INSTRUCTION, NotificationType.MODIFIED_MEDICATION_INSTRUCTION,
        NotificationType.INACTIVATED_MEDICATION_INSTRUCTION, null),

    /**
     * ORDER_UNIT.
     */
    ORDER_UNIT(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_ORDER_UNIT,
        NotificationType.REJECTED_ORDER_UNIT, NotificationType.MODIFIED_ORDER_UNIT,
        NotificationType.INACTIVATED_ORDER_UNIT, null),

    /**
     * SPECIAL_HANDLING.        
     */
    SPECIAL_HANDLING(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_SPECIAL_HANDLING,
        NotificationType.REJECTED_SPECIAL_HANDLING, NotificationType.MODIFIED_SPECIAL_HANDLING,
        NotificationType.INACTIVATED_SPECIAL_HANDLING, null),

    /**
     * PACKAGE_TYPE.
     */
    PACKAGE_TYPE(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_PACKAGE_TYPE,
        NotificationType.REJECTED_PACKAGE_TYPE, NotificationType.MODIFIED_PACKAGE_TYPE,
        NotificationType.INACTIVATED_PACKAGE_TYPE, null),

    /**
     * PHARMACY_SYSTEM.
     */
    PHARMACY_SYSTEM(Type.DOMAIN_TYPE, true, false, NotificationType.APPROVED_PHARMACY_SYSTEM,
        NotificationType.REJECTED_PHARMACY_SYSTEM, NotificationType.MODIFIED_PHARMACY_SYSTEM,
        NotificationType.INACTIVATED_PHARMACY_SYSTEM, null),

    /**
     * STANDARD_MED_ROUTE.
     */
    STANDARD_MED_ROUTE(Type.DOMAIN_TYPE, false, true, NotificationType.APPROVED_STANDARD_MED_ROUTE,
        NotificationType.REJECTED_STANDARD_MED_ROUTE, NotificationType.MODIFIED_STANDARD_MED_ROUTE,
        NotificationType.INACTIVATED_STANDARD_MED_ROUTE, null),

    /**
     * LOCAL_MEDICATION_ROUTE.
     */
    LOCAL_MEDICATION_ROUTE(Type.DOMAIN_TYPE, true, false, NotificationType.APPROVED_LOCAL_MEDICATION_ROUTE,
        NotificationType.REJECTED_LOCAL_MEDICATION_ROUTE, NotificationType.MODIFIED_LOCAL_MEDICATION_ROUTE,
        NotificationType.INACTIVATED_LOCAL_MEDICATION_ROUTE, null),

    /**
     * RX_CONSULT.
     */
    RX_CONSULT(Type.DOMAIN_TYPE, false, false, NotificationType.APPROVED_RX_CONSULT,
        NotificationType.REJECTED_RX_CONSULT, NotificationType.MODIFIED_RX_CONSULT,
        NotificationType.INACTIVATED_RX_CONSULT, null),

    /**
     * VUID_STATUS_HISTORY.
     */
    VUID_STATUS_HISTORY(Type.DOMAIN_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * USER.
     */
    USER(Type.OTHER_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * REDUCED_COPAY.
     */
    REDUCED_COPAY(Type.OTHER_TYPE, false, false, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, NotificationType.UNKNOWN_NON_SYSTEM_EVENT,
        NotificationType.UNKNOWN_NON_SYSTEM_EVENT, null),

    /**
     * PRODUCT.
     */
    PRODUCT(Type.ENTITY_TYPE, false, false, NotificationType.APPROVED_PRODUCT_ITEMS,
        NotificationType.REJECTED_PRODUCT_ITEMS, NotificationType.MODIFIED_PRODUCT_ITEMS,
        NotificationType.INACTIVATED_PRODUCT_ITEMS, NotificationType.PRODUCT_ITEM_MARKED_FOR_LOCAL_USE),

    /**
     *   ORDERABLE_ITEM.  
     */
    ORDERABLE_ITEM(Type.ENTITY_TYPE, false, false, NotificationType.APPROVED_ORDERABLE_ITEMS,
        NotificationType.REJECTED_ORDERABLE_ITEMS, NotificationType.MODIFIED_ORDERABLE_ITEMS,
        NotificationType.INACTIVATED_ORDERABLE_ITEMS, NotificationType.ORDERABLE_ITEM_MARKED_FOR_LOCAL_USE),

    /**
     * NDC    
     */
    NDC(Type.ENTITY_TYPE, false, false, NotificationType.APPROVED_NDC_ITEMS,
        NotificationType.REJECTED_NDC_ITEMS, NotificationType.MODIFIED_NDC_ITEMS,
        NotificationType.INACTIVATED_NDC_ITEMS, NotificationType.MODIFIED_NDC_ITEMS);

    private static final String DOMAIN_VIEW_NAME = "managed.data";

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EntityType.class);
    private static final String VO_SUFFIX = "Vo";

    /**
     * Type of item, domain or entity
     */
    private Type type;

    /**
     * Only for local use
     */
    private boolean localOnlyData;

    /**
     * Only for local use
     */
    private boolean nationalOnlyData;

    /**
     * Notification for approving this entity type
     */
    private NotificationType approvedNotification;

    /**
     * Notification for Rejecting this entity type
     */
    private NotificationType rejectedNotification;

    /**
     * Notification for modifying this entity type
     */
    private NotificationType modifiedNotification;

    /**
     * Notification for inactivating this notification type
     */
    private NotificationType inactivatedNotification;

    /**
     * Notification for marking this entity type for local use
     */
    private NotificationType localUseNotification;

    /**
     * 
     * Constructor
     * 
     * The entity types know if its local or national use only, and its own notification types.
     * 
     * It knows notification types to prevent a fragile if then else tree for notifications elsewhere in the code
     * 
     * @param type - domain or entity
     * @param localOnlyData - for local use only
     * @param nationalOnlyData - for national use only
     * @param approvedNotification - the notification to use for approvals
     * @param rejectedNotification - the notification to use for rejections
     * @param modifiedNotification - the notification to use for modifications
     * @param inactivatedNotification - the notification to use for inactivations
     * @param localUseNotification - the notifications to use for localUse
     */
    private EntityType(Type type, boolean localOnlyData, boolean nationalOnlyData, NotificationType approvedNotification,
        NotificationType rejectedNotification, NotificationType modifiedNotification,
        NotificationType inactivatedNotification, NotificationType localUseNotification) {

        this.type = type;
        this.localOnlyData = localOnlyData;
        this.nationalOnlyData = nationalOnlyData;

        this.approvedNotification = approvedNotification;
        this.rejectedNotification = rejectedNotification;
        this.modifiedNotification = modifiedNotification;
        this.inactivatedNotification = inactivatedNotification;
        this.localUseNotification = localUseNotification;
    }

    /**
     * Returns a map of keys with associated entity types
     * 
     * @return map
     */
    public static Map<String, EntityType> getKeyMap() {

        Map<String, EntityType> keyMap = new HashMap<String, EntityType>();

        for (EntityType type : values()) {
            String key = type.toFieldKey();
            keyMap.put(key, EntityType.createFromString(key));
        }

        return keyMap;
    }

    /**
     * Factory to create entity type from string
     * 
     * @param key dot-separated string representing entity type
     * 
     * @return EntityType
     */
    public static EntityType createFromString(String key) {

        if (key.equals(ORDERABLE_ITEM.toFieldKey())) {
            return ORDERABLE_ITEM;
        } else if (key.equals(PRODUCT.toFieldKey())) {
            return PRODUCT;
        } else if (key.equals(NDC.toFieldKey())) {
            return NDC;
        } else if (key.equals(INGREDIENT.toFieldKey())) {
            return INGREDIENT;
        } else if (key.equals(DOSE_UNIT.toFieldKey())) {
            return DOSE_UNIT;
        } else if (key.equals(DRUG_UNIT.toFieldKey())) {
            return DRUG_UNIT;
        } else if (key.equals(DRUG_CLASS.toFieldKey())) {
            return DRUG_CLASS;
        } else if (key.equals(PACKAGE_TYPE.toFieldKey())) {
            return PACKAGE_TYPE;
        } else if (key.equals(DOSAGE_FORM.toFieldKey())) {
            return DOSAGE_FORM;
        } else if (key.equals(ADMINISTRATION_SCHEDULE.toFieldKey())) {
            return ADMINISTRATION_SCHEDULE;
        } else if (key.equals(GENERIC_NAME.toFieldKey())) {
            return GENERIC_NAME;
        } else if (key.equals(STANDARD_MED_ROUTE.toFieldKey())) {
            return STANDARD_MED_ROUTE;
        } else if (key.equals(LOCAL_MEDICATION_ROUTE.toFieldKey())) {
            return LOCAL_MEDICATION_ROUTE;
        } else if (key.equals(DRUG_TEXT.toFieldKey())) {
            return DRUG_TEXT;
        } else if (key.equals(ORDER_UNIT.toFieldKey())) {
            return ORDER_UNIT;
        } else if (key.equals(DISPENSE_UNIT.toFieldKey())) {
            return DISPENSE_UNIT;
        } else if (key.equals(MEDICATION_INSTRUCTION.toFieldKey())) {
            return MEDICATION_INSTRUCTION;
        } else if (key.equals(MANUFACTURER.toFieldKey())) {
            return MANUFACTURER;
        } else if (key.equals(PHARMACY_SYSTEM.toFieldKey())) {
            return PHARMACY_SYSTEM;
        } else if (key.equals(STANDARD_MED_ROUTE.toFieldKey())) {
            return STANDARD_MED_ROUTE;
        } else if (key.equals(RX_CONSULT.toFieldKey())) {
            return RX_CONSULT;
        } else if (key.equals(SPECIAL_HANDLING.toFieldKey())) {
            return SPECIAL_HANDLING;
        } else {
            return null;
        }
    }

    /**
     * domains.
     * @return list of domains in enum
     */
    public static List<EntityType> domains() {

        List<EntityType> domains = new ArrayList<EntityType>();

        for (EntityType type : values()) {
            if (type.isDomainType()) {
                domains.add(type);
            }
        }

        return domains;

    }

    /**
     * entities.
     * @return list of items in enum
     */
    public static List<EntityType> entities() {

        List<EntityType> items = new ArrayList<EntityType>();

        for (EntityType type : values()) {
            if (type.isEntityType()) {
                items.add(type);
            }
        }

        return items;
    }

    /**
     * Convert the given ManagedItemVo class into an EntityType. Assumes naming convention of EntityTypes matches value
     * object class name, minus "Vo" and splitting with "_".
     * 
     * @param managedItem ManagedItemVo class
     * @return EntityType
     */
    public static EntityType toEntityType(Class managedItem) {

        String unqualifiedClass = StringUtils.unqualify(managedItem.getName());

        // strip off "Vo"
        if (unqualifiedClass.endsWith(VO_SUFFIX)) {
            unqualifiedClass = unqualifiedClass.substring(0, unqualifiedClass.length() - VO_SUFFIX.length());
        }

        unqualifiedClass = StringUtils.uncapitalize(unqualifiedClass); // prepare for splitting by capital letter

        Matcher matcher = Pattern.compile("\\p{Upper}").matcher(unqualifiedClass);

        StringBuffer buffer = new StringBuffer();
        int begin = 0;
        int end = 0;

        while (matcher.find()) {
            end = matcher.start();
            buffer.append(unqualifiedClass.substring(begin, end));
            buffer.append("_");
            begin = end;
        }

        String entityTypeString = buffer.append(unqualifiedClass.substring(begin)).toString().toUpperCase();

        EntityType entityType = null;

        try {
            entityType = EntityType.valueOf(entityTypeString);
        } catch (IllegalArgumentException e) {

            // If this Class has a super class which is still a sub class of ManagedItemVo, try to get the EntityType for it
            if (ManagedItemVo.class.isAssignableFrom(managedItem.getSuperclass())
                && !ManagedItemVo.class.equals(managedItem.getSuperclass())) {
                entityType = toEntityType(managedItem.getSuperclass());
            }

            if (entityType == null) {
                LOG.trace("Unable to find EntityType with name '" + entityTypeString + "' from class '"
                    + managedItem.getName() + "'", e);
            }
        }

        return entityType;
    }

    /**
     * Convert the given {@link FieldKey} into its respective {@link EntityType}. This conversion process includes
     * converting the string {@link FieldKey#getKey()} to upper case, replacing all periods with underscores, and finally
     * calling {@link EntityType#valueOf(String)}.
     * 
     * @param fieldKey String key
     * @return {@link EntityType}
     */
    public static EntityType toEntityType(FieldKey fieldKey) {

        return toEntityType(fieldKey.getKey());
    }

    /**
     * Convert the given String key for a {@link FieldKey#getKey()} into its respective {@link EntityType}. This conversion
     * process includes converting the string to upper case, replacing all periods with underscores, and finally calling
     * {@link EntityType#valueOf(String)}.
     * 
     * @param fieldKey String key
     * @return {@link EntityType}
     */
    public static EntityType toEntityType(String fieldKey) {

        String entityType = fieldKey.toUpperCase(Locale.US).replace('.', '_');

        return EntityType.valueOf(entityType);
    }

    /**
     * Convert the enumeration {@link #toString()} into a {@link FieldKey#getKey()} such that the text is in lower case and
     * delimited with periods instead of underscores. For example, ORDERABLE_ITEM converts to "orderable.item".
     * 
     * @param entityType {@link EntityType} to convert
     * @return String {@link FieldKey#getKey()} representation of the given {@link EntityType}
     */
    public static String toFieldKey(EntityType entityType) {

        return entityType.toString().toLowerCase().replace('_', '.');
    }

    /**
     * Convert the given EntityType into the ValueObject class that it represents.
     * 
     * @param entityType EntityType to convert
     * @return Class
     */
    public static Class toValueObjectClass(EntityType entityType) {

        String[] words = entityType.toString().toLowerCase().split("_");

        StringBuffer className = new StringBuffer();
        className.append(ValueObject.class.getPackage().getName());
        className.append(".");

        for (String word : words) {
            className.append(StringUtils.capitalize(word));
        }

        className.append(VO_SUFFIX);

        Class clazz;

        try {
            clazz = Class.forName(className.toString());
        } catch (ClassNotFoundException e) {
            LOG.error("ValueObject class '" + className.toString() + "' for EntityType '" + entityType.toString()
                + "' could not be found", e);
            clazz = null;
        }

        return clazz;
    }

    /**
     * isSecondApprovalType
     *
     * @param entityType EntityType
     * @return boolean
     */
    public static boolean isSecondApprovalType(EntityType entityType) {

        boolean isSecondApprovalType = false;

        if (EntityType.PRODUCT.equals(entityType)) {
            isSecondApprovalType = true;
        } else if (EntityType.ORDERABLE_ITEM.equals(entityType)) {
            isSecondApprovalType = true;
        } else if (EntityType.DRUG_UNIT.equals(entityType)) {
            isSecondApprovalType = true;
        } else if (EntityType.DISPENSE_UNIT.equals(entityType)) {
            isSecondApprovalType = true;
        } else if (EntityType.GENERIC_NAME.equals(entityType)) {
            isSecondApprovalType = true;
        } else if (EntityType.INGREDIENT.equals(entityType)) {
            isSecondApprovalType = true;
        } else if (EntityType.DOSAGE_FORM.equals(entityType)) {
            isSecondApprovalType = true;
        } else if (EntityType.DRUG_CLASS.equals(entityType)) {
            isSecondApprovalType = true;
        }

        return isSecondApprovalType;
    }

    /**
     * Provides access to appropriate view for this entity type
     * 
     * @return view name
     */
    public String getViewName() {

        String viewName;

        boolean firstCheck = (isPharmacySystem() || isRxConsult() || isStandardMedRoute() || isMedicationInstruction());
        firstCheck = (firstCheck || isLocalMedicationRoute() || isDosageForm() || isDrugClassCode());
        firstCheck = (firstCheck || isDrugText() || isIngredient());

//        isPharmacySystem() || isRxConsult() || isStandardMedRoute() || isMedicationInstruction()
//        || isLocalMedicationRoute() || isDosageForm() || isDrugClassCode() || isDrugText()
        if (firstCheck) {
            viewName = toFieldKey();
        } else if (isDomainType()) {
            viewName = DOMAIN_VIEW_NAME;
        } else {
            viewName = toFieldKey();
        }

        return viewName;
    }

    /**
     * Gets a human readable capitalized name for the entity type
     * 
     * @return name
     */
    public String getName() {

        return WordUtils.capitalize(this.toString().toLowerCase().replace('_', ' '));
    }

    /**
     * If this EntityType has a child, return that EntityType. If this EntityType does not have a child, return null.
     * 
     * @return child EntityType for this EntityType
     */
    public EntityType getChild() {

        EntityType child;

        switch (this) {
            case NDC:
                child = null;
                break;

            case PRODUCT:
                child = NDC;
                break;

            case ORDERABLE_ITEM:
                child = PRODUCT;
                break;

            default:
                child = null;
                break;
        }

        return child;
    }

    /**
     * If this EntityType has a parent, return that EntityType. If this EntityType does not have a parent, return null.
     * 
     * @return parent EntityType for this EntityType
     */
    public EntityType getParent() {

        EntityType parent;

        switch (this) {
            case NDC:
                parent = PRODUCT;
                break;

            case PRODUCT:
                parent = ORDERABLE_ITEM;
                break;

            case ORDERABLE_ITEM:
                parent = ORDERABLE_ITEM;
                break;

            default:
                parent = null;
                break;
        }

        return parent;
    }

    /**
     * hasChild.
     * @return boolean true if {@link #getChild()} is not equal to null
     */
    public boolean hasChild() {

        return getChild() != null;
    }

    /**
     * Only {@link #ORDERABLE_ITEM} has a concept of local and national instances.
     * 
     * @return boolean true if this EntityType has a concept of local and national instances
     * @see #isOrderableItem()
     */
    public boolean hasLocalNationalConcept() {

        return isOrderableItem();
    }

    /**
     * hasParent.
     * @return boolean true if {@link #getParent()} is not equal to null
     */
    public boolean hasParent() {

        return getParent() != null;
    }

    /**
     * Test to see if this instance of {@link EntityType} is equal to the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType} to test equality with
     * @return true on match
     */
    public boolean is(EntityType entityType) {

        if (entityType == null) {
            return false;
        }

        if (this.equals(entityType)) {
            return true;
        }

        return false;
    }

    /**
     * isDomainType.
     * @return true if item is domain
     */
    public boolean isDomainType() {

        return type.isDomainType();
    }

    /**
     * isEntityType.
     * @return true if item is entity
     */
    public boolean isEntityType() {

        return type.isEntityType();
    }

    /**
     * isNdc.
     * @return boolean true if this EntityType is NDC
     */
    public boolean isNdc() {

        return NDC.equals(this);
    }

    /**
     * isOrderableItem.
     * @return boolean true if this EntityType is ORDERABLE_ITEM
     */
    public boolean isOrderableItem() {

        return ORDERABLE_ITEM.equals(this);
    }

    /**
     * isProduct.
     * @return boolean true if this EntityType is PRODUCT
     */
    public boolean isProduct() {

        return PRODUCT.equals(this);
    }

    /**
     * isPharmacySystem.
     * @return boolean true if this EntityType is PHARMACY_SYSTEM
     */
    public boolean isPharmacySystem() {

        return PHARMACY_SYSTEM.equals(this);
    }

    /**
     * isRxConsult.
     * @return boolean true if this EntityType is RX_CONSULT
     */
    public boolean isRxConsult() {

        return RX_CONSULT.equals(this);
    }

    /**
     * isStandardMedicationRoute.
     * @return boolean true if this EntityType is STANDARD_MED_ROUTE
     */
    public boolean isStandardMedRoute() {

        return STANDARD_MED_ROUTE.equals(this);
    }

    /**
     * isMedicationInstruction.
     * @return boolean true if this EntityType is MEDICATION_INSTRUCTION
     */
    public boolean isMedicationInstruction() {

        return MEDICATION_INSTRUCTION.equals(this);
    }

    /**
     * isLocalMedicationRoute.
     * @return boolean true if this EntityType is LOCAL_MEDICATION_ROUTE
     */
    public boolean isLocalMedicationRoute() {

        return LOCAL_MEDICATION_ROUTE.equals(this);
    }

    /**
     * isDosageForm.
     * @return boolean true if this EntityType is DOSAGE_FORM.
     */
    public boolean isDosageForm() {

        return DOSAGE_FORM.equals(this);
    }

    /**
     * isDrugClassCode.
     * @return boolean true if this EntityType is DRUG_CLASS.
     */
    public boolean isDrugClassCode() {

        return DRUG_CLASS.equals(this);
    }

    /**
     * isSpecialHandling.
     * @return boolean true if this EntityType is SPECIAL_HANDLING.
     */
    public boolean isSpecialHandling() {

        return SPECIAL_HANDLING.equals(this);
    }

    /**
     * isDrugText
     * @return boolean true if this EntityType is DRUG_TEXT.
     */
    public boolean isDrugText() {

        return DRUG_TEXT.equals(this);
    }

    /**
     * isUser.
     * @return boolean true if this EntityType is USER.
     */
    public boolean isUser() {

        return USER.equals(this);
    }

    /**
     * isIngredient.
     * @return boolean true if this EntityType is INGREDIENT.
     */
    public boolean isIngredient() {

        return INGREDIENT.equals(this);
    }

    /**
     * isGenericName.
     * @return boolean true if this EntityType is GENERIC_NAME.
     */
    public boolean isGenericName() {

        return GENERIC_NAME.equals(this);
    }

    /**
     * Returns a dot string representation of the field level that ends with a dot
     * 
     * @return dot separated string name
     */
    public String getPrefix() {

        StringBuffer attribute = new StringBuffer();
        String[] words = this.toString().split("\\_");

        for (String word2 : words) {
            String word = word2.toLowerCase(Locale.US);
            attribute.append(word).append('.');
        }

        return attribute.toString();
    }

    /**
     * Convert the enumeration {@link #toString()} into a {@link FieldKey#getKey()} such that the text is in lower case and
     * delimited with periods instead of underscores. For example, ORDERABLE_ITEM converts to "orderable.item".
     * 
     * @return String {@link FieldKey#getKey()} representation of this EntityType
     */
    public String toFieldKey() {

        return toFieldKey(this);
    }

    /**
     * Convert the given EntityType into the ValueObject class that it represents.
     * 
     * @return Class
     */
    public Class toValueObjectClass() {

        return toValueObjectClass(this);
    }

    /**
     * getType.
     * @return type property
     */
    public Type getType() {

        return type;
    }

    /**
     * setType.
     * @param type type property
     */
    public void setType(Type type) {

        this.type = type;
    }

    /**
     * isLocalOnlyData.
     * @return localOnlyData property
     */
    public boolean isLocalOnlyData() {

        return localOnlyData;
    }

    /**
     * setLocalOnlyData.
     * @param localOnlyData localOnlyData property
     */
    public void setLocalOnlyData(boolean localOnlyData) {

        this.localOnlyData = localOnlyData;
    }

    /**
     * isNationalOnlyData.
     * @return nationalOnlyData property
     */
    public boolean isNationalOnlyData() {

        return nationalOnlyData;
    }

    /**
     * setNationalOnlyData.
     * @param nationalOnlyData nationalOnlyData property
     */
    public void setNationalOnlyData(boolean nationalOnlyData) {

        this.nationalOnlyData = nationalOnlyData;
    }

    /**
     * getApprovedNotification.
     * @return approvedNotification property
     */
    public NotificationType getApprovedNotification() {

        return approvedNotification;
    }

    /**
     * setApprovedNotification.
     * @param approvedNotification approvedNotification property
     */
    public void setApprovedNotification(NotificationType approvedNotification) {

        this.approvedNotification = approvedNotification;
    }

    /**
     * getRejectedNotification.
     * @return rejectedNotification property
     */
    public NotificationType getRejectedNotification() {

        return rejectedNotification;
    }

    /**
     * setRejectedNotification.
     * @param rejectedNotification rejectedNotification property
     */
    public void setRejectedNotification(NotificationType rejectedNotification) {

        this.rejectedNotification = rejectedNotification;
    }

    /**
     * getModifiedNotification.
     * @return modifiedNotification property
     */
    public NotificationType getModifiedNotification() {

        return modifiedNotification;
    }

    /**
     * setModifiedNotification.
     * @param modifiedNotification modifiedNotification property
     */
    public void setModifiedNotification(NotificationType modifiedNotification) {

        this.modifiedNotification = modifiedNotification;
    }

    /**
     * getInactivatedNotification.
     * @return inactivatedNotification property
     */
    public NotificationType getInactivatedNotification() {

        return inactivatedNotification;
    }

    /**
     * setInactivatedNotification.
     * @param inactivatedNotification inactivatedNotification property
     */
    public void setInactivatedNotification(NotificationType inactivatedNotification) {

        this.inactivatedNotification = inactivatedNotification;
    }

    /**
     * NotificationType.
     * @return localUseNotification property
     */
    public NotificationType getLocalUseNotification() {

        return localUseNotification;
    }

    /**
     * setLocalUseNotification.
     * @param localUseNotification localUseNotification property
     */
    public void setLocalUseNotification(NotificationType localUseNotification) {

        this.localUseNotification = localUseNotification;
    }

    /**
     * Convert the enumeration {@link #toString()} into a {@link FieldKey#getKey()} such that the text is in lower case and
     * delimited with periods instead of underscores. For example, ORDERABLE_ITEM converts to "orderable.item".
     * 
     * 
     * @return String {@link FieldKey#getKey()} representation of the given {@link EntityType}
     */
    public String getFieldKey() {

        return toFieldKey(this);
    }

    /**
     * 
     * Returns this.toString().toLowerCase();
     *
     * @return lowercase entityType
     */
    public String toLowerCase() {

        return this.toString().toLowerCase();
    }
    
    /**
     * 
     * Returns this.toString().toLowerCase();
     * @param locale The Locale for the conversion.
     * @return lowercase entityType
     */
    public String toLowerCase(Locale locale) {

        return this.toString().toLowerCase(locale);
    }

    /**
     * 
     * Logic to check to see if this is entity is a domain type which can be deleted
     *
     * @return true if the entity can be deleted
     */
    public boolean isDeleteable() {

        boolean deletableType = (DISPENSE_UNIT.equals(this) || DRUG_UNIT.equals(this) || DRUG_CLASS.equals(this)
            || INGREDIENT.equals(this) || GENERIC_NAME.equals(this) || DOSAGE_FORM.equals(this));

        return (this.isDomainType() && deletableType);
    }

}
