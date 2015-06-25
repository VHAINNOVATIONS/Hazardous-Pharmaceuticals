/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedicationInstructionDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageTypeDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPharmacySystemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugClassAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSpecialHandlingDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplStandardMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSynonymDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDispenseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDrugClassDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaGenNameDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfValuesViewDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplWarnLabelDo;


/**
 * Methods to support knowing if a join is required to retrieve/sort a particular {@link FieldKey} and other database schema
 * (table and column name) helper methods.
 * <p>
 * Acts as a central repository for knowledge about the database schema for use in generating Hibernate Criteria.
 */
public class SchemaUtility {

    private static final String DOT = ".";
    private static final Map<Key, String> FIELD_KEY_TO_COLUMN = configureFieldKeyToColumn();
    private static final Map<Key, String[]> FIELD_KEY_TO_TABLE = configureFieldKeyToTables();
    private static final Map<Correlation, String[]> CORRELATIONS = configureCorrelations();

    /**
     * Default, empty constructor.
     * <p>
     * Cannot instantiate.
     */
    private SchemaUtility() {
        super();
    }

    /**
     * Return true if a join is required to retrieve or sort by the given {@link FieldKey} on the given {@link DataObject}.
     * <p>
     * This method assumes that the given {@link DataObject} class has an attribute for the given {@link FieldKey}!
     * <p>
     * Returns true if the given {@link FieldKey} represents a separate table in the database.
     * 
     * @param dataObject {@link DataObject} of the current table on which to join
     * @param fieldKey {@link FieldKey} that needs to be retrieved or sorted
     * 
     * @return boolean true if a join is required to retrieve or sort by the given {@link FieldKey}
     */
    public static boolean isJoinRequired(Class<? extends DataObject> dataObject, FieldKey fieldKey) {
        String[] join = getJoinPropertyNames(dataObject, fieldKey);

        return join != null;
    }

    /**
     * Get the {@link DataObject} class that must be joined to in order to retrieve or sort by the given {@link FieldKey}.
     * <p>
     * Assumes that {@link #isJoinRequired(Class, FieldKey)} returns true!
     * 
     * @param dataObject {@link DataObject} of the current table on which to join
     * @param fieldKey {@link FieldKey} that needs to be retrieved or sorted
     * 
     * @return String array property names to be joined, in the order in which they need to be joined
     */
    public static String[] getJoinPropertyNames(Class<? extends DataObject> dataObject, FieldKey fieldKey) {
        String[] propertyNames;

        if (fieldKey.isVaDataField()) {
            List<String> join = joinToParent(dataObject, fieldKey);
            join.add("eplVadfOwners");
            join.add(EplVadfOwnerDo.EPL_VADF_VALUES_VIEW);
            propertyNames = join.toArray(new String[join.size()]);
        } else {
            propertyNames = FIELD_KEY_TO_TABLE.get(new Key(dataObject, fieldKey));

            if (propertyNames == null) {
                propertyNames = FIELD_KEY_TO_TABLE.get(new Key(DataObject.class, fieldKey));
            }
        }

        return propertyNames;
    }

    /**
     * Join to the parent item if required.
     * 
     * @param dataObject {@link DataObject} of the current table on which to join
     * @param fieldKey {@link FieldKey}
     * 
     * @return List of String property names to be joined, in the order in which they need to be joined
     */
    private static List<String> joinToParent(Class<? extends DataObject> dataObject, FieldKey fieldKey) {
        List<String> join = new ArrayList<String>();

        EntityType entityType = null;

        if (EplNdcDo.class.isAssignableFrom(dataObject)) {
            entityType = EntityType.NDC;
        } else if (EplProductDo.class.isAssignableFrom(dataObject)) {
            entityType = EntityType.PRODUCT;
        } else if (EplOrderableItemDo.class.isAssignableFrom(dataObject)) {
            entityType = EntityType.ORDERABLE_ITEM;
        }

        if (entityType != null && !fieldKey.getEntityTypes().contains(entityType)) {
            switch (entityType) {
                case NDC:
                    join.add(EplNdcDo.EPL_PRODUCT);
                    break;

                case PRODUCT:
                    join.add(EplProductDo.EPL_ORDERABLE_ITEM);
                    break;

                default:
                    break;
            }

            if (EntityType.NDC.equals(entityType) && fieldKey.isOrderableItemLevelField() && !fieldKey.isNdcLevelField()
                && !fieldKey.isProductLevelField()) {

                join.add(EplProductDo.EPL_ORDERABLE_ITEM);
            }
        }

        return join;
    }

    /**
     * Map the given FieldKey to the appropriate column name in the database.
     * <p>
     * The returned property name must be used on a Hibernate Criteria object for the correct database table! For example, if
     * {@link FieldKey#MANUFACTURER} were passed in, the resulting "name" property must be applied a Hibernate Criteria on
     * the "EPL_MANUFACTURERS" table.
     * 
     * @param dataObject {@link DataObject} of the current table on which to join
     * @param fieldKey {@link FieldKey} to map
     * 
     * @return String property name on the appropriate table for the given {@link FieldKey}
     */
    public static String getPropertyName(Class<? extends DataObject> dataObject, FieldKey fieldKey) {
        String propertyName = null;

        if (fieldKey.isVaDataField()) {
            propertyName = EplVadfValuesViewDo.VADF_VALUE;
        } else {
            propertyName = FIELD_KEY_TO_COLUMN.get(new Key(dataObject, fieldKey));

            if (propertyName == null) {
                propertyName = FIELD_KEY_TO_COLUMN.get(new Key(DataObject.class, fieldKey));
            }
        }

        return propertyName;
    }

    /**
     * Get the attribute/field used in a correlated query for the inner table (used in the subquery).
     * 
     * @param outer DataObject class/table used as the outer table in a subquery
     * @param inner DataObject class/table used as the inner table in a subquery
     * @return String inner attribute name
     */
    public static String getInnerCorrelatedAttribute(Class<? extends DataObject> outer, Class<? extends DataObject> inner) {
        String[] attributes = CORRELATIONS.get(new Correlation(outer, inner));

        return attributes[1];
    }

    /**
     * Get the attribute/field used in a correlated query for the outer table (used in the subquery).
     * 
     * @param outer DataObject class/table used as the outer table in a subquery
     * @param inner DataObject class/table used as the inner table in a subquery
     * @return String outer attribute name
     */
    public static String getOuterCorrelatedAttribute(Class<? extends DataObject> outer, Class<? extends DataObject> inner) {
        String[] attributes = CORRELATIONS.get(new Correlation(outer, inner));

        return attributes[0];
    }

    /**
     * Convert the given String format of a field to its proper class type for Hibernate.
     * <p>
     * Currently only converts ID types from String to Long.
     * 
     * @param fieldKey {@link FieldKey}
     * @param value String value
     * @return Object converted value
     */
    public static Object toValue(FieldKey fieldKey, String value) {
        Object result = value;

        if (FieldKey.ID.equals(fieldKey) || FieldKey.ORDERABLE_ITEM_PARENT.equals(fieldKey)
            || FieldKey.PRODUCT.equals(fieldKey)) {
            result = new Long(value);
        }

        return result;
    }

    /**
     * The FIELD_KEY_TO_COLUMN maps the "Key" to a database column name.
     * <p>
     * If the FieldKey maps to the same column name for all DataObjects, use the DataObject class in the Key.
     * <p>
     * If the FieldKey maps to different column names for each DataObject, use a DataObject subclass in the Key.
     * 
     * @return Map<Key, String> FieldKey to Column
     */
    private static Map<Key, String> configureFieldKeyToColumn() {
        Map<Key, String> map = new HashMap<Key, String>();
        
        mapDataObjectColumns(map);
        
        map.put(new Key(EplProductDo.class, FieldKey.ID), EplProductDo.EPL_ID);
        map.put(new Key(EplProductDo.class, FieldKey.REQUEST_ITEM_STATUS), EplProductDo.REQUEST_STATUS);
        map.put(new Key(EplProductDo.class, FieldKey.ITEM_STATUS), EplProductDo.ITEM_STATUS);
        map.put(new Key(EplProductDo.class, FieldKey.LOCAL_USE), EplProductDo.LOCAL_USE);
        map.put(new Key(EplProductDo.class, FieldKey.INACTIVATION_DATE), EplProductDo.INACTIVATION_DATE);

        map.put(new Key(EplNdcDo.class, FieldKey.ID), EplNdcDo.EPL_ID);
        map.put(new Key(EplNdcDo.class, FieldKey.REQUEST_ITEM_STATUS), EplNdcDo.REQUEST_STATUS);
        map.put(new Key(EplNdcDo.class, FieldKey.ITEM_STATUS), EplNdcDo.ITEM_STATUS);
        map.put(new Key(EplNdcDo.class, FieldKey.LOCAL_USE), EplNdcDo.LOCAL_DISPENSE);
        map.put(new Key(EplNdcDo.class, FieldKey.INACTIVATION_DATE), EplNdcDo.INACTIVATION_DATE);

        map.put(new Key(EplOrderableItemDo.class, FieldKey.ID), EplOrderableItemDo.EPL_ID);
        map.put(new Key(EplOrderableItemDo.class, FieldKey.REQUEST_ITEM_STATUS), EplOrderableItemDo.REQUEST_STATUS);
        map.put(new Key(EplOrderableItemDo.class, FieldKey.ITEM_STATUS), EplOrderableItemDo.ITEM_STATUS);
        map.put(new Key(EplOrderableItemDo.class, FieldKey.LOCAL_USE), EplOrderableItemDo.LOCAL_USE);
        map.put(new Key(EplOrderableItemDo.class, FieldKey.ORDERABLE_ITEM_TYPE), EplOrderableItemDo.OI_TYPE);
        map.put(new Key(EplOrderableItemDo.class, FieldKey.ORDERABLE_ITEM_PARENT), EplOrderableItemDo.EPL_ID);
        map.put(new Key(EplOrderableItemDo.class, FieldKey.INACTIVATION_DATE), EplOrderableItemDo.INACTIVATION_DATE);

        map.put(new Key(EplAdminScheduleDo.class, FieldKey.ID), EplAdminScheduleDo.EPL_ID);
        map.put(new Key(EplAdminScheduleDo.class, FieldKey.VALUE), EplAdminScheduleDo.NAME);
        map.put(new Key(EplAdminScheduleDo.class, FieldKey.REQUEST_ITEM_STATUS), EplAdminScheduleDo.REQUEST_STATUS);
        map.put(new Key(EplAdminScheduleDo.class, FieldKey.ITEM_STATUS), EplAdminScheduleDo.ITEM_STATUS);
        map.put(new Key(EplAdminScheduleDo.class, FieldKey.INACTIVATION_DATE), EplAdminScheduleDo.INACTIVATION_DATE);

        map.put(new Key(EplVaDispenseUnitDo.class, FieldKey.ID), EplVaDispenseUnitDo.EPL_ID);
        map.put(new Key(EplVaDispenseUnitDo.class, FieldKey.VALUE), EplVaDispenseUnitDo.DISPENSE_UNIT_NAME);
        map.put(new Key(EplVaDispenseUnitDo.class, FieldKey.REQUEST_ITEM_STATUS), EplVaDispenseUnitDo.REQUEST_STATUS);
        map.put(new Key(EplVaDispenseUnitDo.class, FieldKey.ITEM_STATUS), EplVaDispenseUnitDo.ITEM_STATUS);
        map.put(new Key(EplVaDispenseUnitDo.class, FieldKey.INACTIVATION_DATE), EplVaDispenseUnitDo.INACTIVATION_DATE);

        map.put(new Key(EplDosageFormDo.class, FieldKey.ID), EplDosageFormDo.EPL_ID);
        map.put(new Key(EplDosageFormDo.class, FieldKey.DOSAGE_FORM_NAME), EplDosageFormDo.DF_NAME);
        map.put(new Key(EplDosageFormDo.class, FieldKey.VALUE), EplDosageFormDo.DF_NAME);
        map.put(new Key(EplDosageFormDo.class, FieldKey.REQUEST_ITEM_STATUS), EplDosageFormDo.REQUEST_STATUS);
        map.put(new Key(EplDosageFormDo.class, FieldKey.ITEM_STATUS), EplDosageFormDo.ITEM_STATUS);
        map.put(new Key(EplDosageFormDo.class, FieldKey.INACTIVATION_DATE), EplDosageFormDo.INACTIVATION_DATE);

        map.put(new Key(EplDoseUnitDo.class, FieldKey.ID), EplDoseUnitDo.EPL_ID);
        map.put(new Key(EplDoseUnitDo.class, FieldKey.DOSE_UNIT_NAME), EplDoseUnitDo.NAME);
        map.put(new Key(EplDoseUnitDo.class, FieldKey.REQUEST_ITEM_STATUS), EplDoseUnitDo.REQUEST_STATUS);
        map.put(new Key(EplDoseUnitDo.class, FieldKey.ITEM_STATUS), EplDoseUnitDo.ITEM_STATUS);
        map.put(new Key(EplDoseUnitDo.class, FieldKey.INACTIVATION_DATE), EplDoseUnitDo.INACTIVATION_DATE);

        map.put(new Key(EplVaDrugClassDo.class, FieldKey.ID), EplVaDrugClassDo.EPL_ID);
        map.put(new Key(EplVaDrugClassDo.class, FieldKey.VALUE), EplVaDrugClassDo.CODE);
        map.put(new Key(EplVaDrugClassDo.class, FieldKey.CODE), EplVaDrugClassDo.CODE);
        map.put(new Key(EplVaDrugClassDo.class, FieldKey.CLASSIFICATION), EplVaDrugClassDo.CLASSIFICATION_NAME);
        map.put(new Key(EplVaDrugClassDo.class, FieldKey.REQUEST_ITEM_STATUS), EplVaDrugClassDo.REQUEST_STATUS);
        map.put(new Key(EplVaDrugClassDo.class, FieldKey.ITEM_STATUS), EplVaDrugClassDo.ITEM_STATUS);
        map.put(new Key(EplVaDrugClassDo.class, FieldKey.INACTIVATION_DATE), EplVaDrugClassDo.INACTIVATION_DATE);

        map.put(new Key(EplDrugTextDo.class, FieldKey.ID), EplDrugTextDo.EPL_ID);
        map.put(new Key(EplDrugTextDo.class, FieldKey.VALUE), EplDrugTextDo.DRUG_TEXT_NAME);
        map.put(new Key(EplDrugTextDo.class, FieldKey.REQUEST_ITEM_STATUS), EplDrugTextDo.REQUEST_STATUS);
        map.put(new Key(EplDrugTextDo.class, FieldKey.ITEM_STATUS), EplDrugTextDo.ITEM_STATUS);
        map.put(new Key(EplDrugTextDo.class, FieldKey.INACTIVATION_DATE), EplDrugTextDo.INACTIVATION_DATE);

        map.put(new Key(EplDrugUnitDo.class, FieldKey.ID), EplDrugUnitDo.EPL_ID);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.VALUE), EplDrugUnitDo.NAME);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.REQUEST_ITEM_STATUS), EplDrugUnitDo.REQUEST_STATUS);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.ITEM_STATUS), EplDrugUnitDo.ITEM_STATUS);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.INACTIVATION_DATE), EplDrugUnitDo.INACTIVATION_DATE);

        map.put(new Key(EplDrugUnitDo.class, FieldKey.ID), EplDrugUnitDo.EPL_ID);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.VALUE), EplDrugUnitDo.NAME);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.REQUEST_ITEM_STATUS), EplDrugUnitDo.REQUEST_STATUS);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.ITEM_STATUS), EplDrugUnitDo.ITEM_STATUS);
        map.put(new Key(EplDrugUnitDo.class, FieldKey.INACTIVATION_DATE), EplDrugUnitDo.INACTIVATION_DATE);

        map.put(new Key(EplVaGenNameDo.class, FieldKey.ID), EplVaGenNameDo.EPL_ID);
        map.put(new Key(EplVaGenNameDo.class, FieldKey.VALUE), EplVaGenNameDo.NAME);
        map.put(new Key(EplVaGenNameDo.class, FieldKey.REQUEST_ITEM_STATUS), EplVaGenNameDo.REQUEST_STATUS);
        map.put(new Key(EplVaGenNameDo.class, FieldKey.ITEM_STATUS), EplVaGenNameDo.ITEM_STATUS);
        map.put(new Key(EplVaGenNameDo.class, FieldKey.INACTIVATION_DATE), EplVaGenNameDo.INACTIVATION_DATE);

        map.put(new Key(EplIngredientDo.class, FieldKey.ID), EplIngredientDo.EPL_ID);
        map.put(new Key(EplIngredientDo.class, FieldKey.VALUE), EplIngredientDo.NAME);
        map.put(new Key(EplIngredientDo.class, FieldKey.REQUEST_ITEM_STATUS), EplIngredientDo.REQUEST_STATUS);
        map.put(new Key(EplIngredientDo.class, FieldKey.ITEM_STATUS), EplIngredientDo.ITEM_STATUS);
        map.put(new Key(EplIngredientDo.class, FieldKey.INACTIVATION_DATE), EplIngredientDo.INACTIVATION_DATE);

        map.put(new Key(EplLocalMedRouteDo.class, FieldKey.ID), EplLocalMedRouteDo.EPL_ID);
        map.put(new Key(EplLocalMedRouteDo.class, FieldKey.VALUE), EplLocalMedRouteDo.NAME);
        map.put(new Key(EplLocalMedRouteDo.class, FieldKey.REQUEST_ITEM_STATUS), EplLocalMedRouteDo.REQUEST_STATUS);
        map.put(new Key(EplLocalMedRouteDo.class, FieldKey.ITEM_STATUS), EplLocalMedRouteDo.ITEM_STATUS);
        map.put(new Key(EplLocalMedRouteDo.class, FieldKey.INACTIVATION_DATE), EplLocalMedRouteDo.INACTIVATION_DATE);

        map.put(new Key(EplManufacturerDo.class, FieldKey.ID), EplManufacturerDo.EPL_ID);
        map.put(new Key(EplManufacturerDo.class, FieldKey.VALUE), EplManufacturerDo.NAME);
        map.put(new Key(EplManufacturerDo.class, FieldKey.REQUEST_ITEM_STATUS), EplManufacturerDo.REQUEST_STATUS);
        map.put(new Key(EplManufacturerDo.class, FieldKey.ITEM_STATUS), EplManufacturerDo.ITEM_STATUS);
        map.put(new Key(EplManufacturerDo.class, FieldKey.INACTIVATION_DATE), EplManufacturerDo.INACTIVATION_DATE);

        map.put(new Key(EplMedicationInstructionDo.class, FieldKey.ID), EplMedicationInstructionDo.EPL_ID);
        map.put(new Key(EplMedicationInstructionDo.class, FieldKey.VALUE), EplMedicationInstructionDo.NAME);
        map.put(new Key(EplMedicationInstructionDo.class, FieldKey.REQUEST_ITEM_STATUS),
            EplMedicationInstructionDo.REQUEST_STATUS);
        map.put(new Key(EplMedicationInstructionDo.class, FieldKey.ITEM_STATUS), EplMedicationInstructionDo.ITEM_STATUS);
        map.put(new Key(EplMedicationInstructionDo.class, FieldKey.INACTIVATION_DATE),
            EplMedicationInstructionDo.INACTIVATION_DATE);

        map.put(new Key(EplOrderUnitDo.class, FieldKey.ID), EplOrderUnitDo.EPL_ID);
        map.put(new Key(EplOrderUnitDo.class, FieldKey.VALUE), EplOrderUnitDo.ABBREV);
        map.put(new Key(EplOrderUnitDo.class, FieldKey.REQUEST_ITEM_STATUS), EplOrderUnitDo.REQUEST_STATUS);
        map.put(new Key(EplOrderUnitDo.class, FieldKey.ITEM_STATUS), EplOrderUnitDo.ITEM_STATUS);
        map.put(new Key(EplOrderUnitDo.class, FieldKey.INACTIVATION_DATE), EplOrderUnitDo.INACTIVATION_DATE);

        map.put(new Key(EplSpecialHandlingDo.class, FieldKey.ID), EplSpecialHandlingDo.EPL_ID);
        map.put(new Key(EplSpecialHandlingDo.class, FieldKey.VALUE), EplSpecialHandlingDo.CODE);
        map.put(new Key(EplSpecialHandlingDo.class, FieldKey.ITEM_STATUS), EplSpecialHandlingDo.ITEM_STATUS);

        map.put(new Key(EplPackageTypeDo.class, FieldKey.ID), EplPackageTypeDo.EPL_ID);
        map.put(new Key(EplPackageTypeDo.class, FieldKey.VALUE), EplPackageTypeDo.NAME);
        map.put(new Key(EplPackageTypeDo.class, FieldKey.REQUEST_ITEM_STATUS), EplPackageTypeDo.REQUEST_STATUS);
        map.put(new Key(EplPackageTypeDo.class, FieldKey.ITEM_STATUS), EplPackageTypeDo.ITEM_STATUS);
        map.put(new Key(EplPackageTypeDo.class, FieldKey.INACTIVATION_DATE), EplPackageTypeDo.INACTIVATION_DATE);

        map.put(new Key(EplPharmacySystemDo.class, FieldKey.ID), EplPharmacySystemDo.SITE_NUMBER);
        map.put(new Key(EplPharmacySystemDo.class, FieldKey.VALUE), EplPharmacySystemDo.SITE_NUMBER);
        map.put(new Key(EplPharmacySystemDo.class, FieldKey.REQUEST_ITEM_STATUS), EplPharmacySystemDo.REQUEST_STATUS);
        map.put(new Key(EplPharmacySystemDo.class, FieldKey.ITEM_STATUS), EplPharmacySystemDo.ITEM_STATUS);
        map.put(new Key(EplPharmacySystemDo.class, FieldKey.INACTIVATION_DATE), EplPharmacySystemDo.INACTIVATION_DATE);

        map.put(new Key(EplWarnLabelDo.class, FieldKey.ID), EplWarnLabelDo.EPL_ID);
        map.put(new Key(EplWarnLabelDo.class, FieldKey.VALUE), EplWarnLabelDo.RX_CONSULT_NAME);
        map.put(new Key(EplWarnLabelDo.class, FieldKey.REQUEST_ITEM_STATUS), EplWarnLabelDo.REQUEST_STATUS);
        map.put(new Key(EplWarnLabelDo.class, FieldKey.ITEM_STATUS), EplWarnLabelDo.ITEM_STATUS);
        map.put(new Key(EplWarnLabelDo.class, FieldKey.INACTIVATION_DATE), EplWarnLabelDo.INACTIVATION_DATE);

        map.put(new Key(EplStandardMedRouteDo.class, FieldKey.ID), EplStandardMedRouteDo.EPL_ID);
        map.put(new Key(EplStandardMedRouteDo.class, FieldKey.VALUE), EplStandardMedRouteDo.NAME);
        map.put(new Key(EplStandardMedRouteDo.class, FieldKey.REQUEST_ITEM_STATUS), EplStandardMedRouteDo.REQUEST_STATUS);
        map.put(new Key(EplStandardMedRouteDo.class, FieldKey.ITEM_STATUS), EplStandardMedRouteDo.ITEM_STATUS);
        map.put(new Key(EplStandardMedRouteDo.class, FieldKey.INACTIVATION_DATE), EplStandardMedRouteDo.INACTIVATION_DATE);

        return map;
    }

    /**
     * mapDataObjectColumns
     *
     * @param map Map<Key, String>
     */
    private static void mapDataObjectColumns(Map<Key, String> map) {
        map.put(new Key(DataObject.class, FieldKey.NDC), EplNdcDo.NDC_NO_DASHES);
        map.put(new Key(DataObject.class, FieldKey.UPC_UPN), EplNdcDo.UPC_UPN);
        map.put(new Key(DataObject.class, FieldKey.TRADE_NAME), EplNdcDo.TRADE_NAME);
        map.put(new Key(DataObject.class, FieldKey.PACKAGE_SIZE), EplNdcDo.PACKAGE_SIZE);
        map.put(new Key(DataObject.class, FieldKey.PACKAGE_TYPE), EplPackageTypeDo.NAME);
        map.put(new Key(DataObject.class, FieldKey.MANUFACTURER), EplManufacturerDo.NAME);
        map.put(new Key(DataObject.class, FieldKey.VA_PRODUCT_NAME), EplProductDo.VA_PRODUCT_NAME);
        map.put(new Key(DataObject.class, FieldKey.VA_PRINT_NAME), EplProductDo.VA_PRINT_NAME);
        map.put(new Key(DataObject.class, FieldKey.LOCAL_PRINT_NAME), EplProductDo.LOCAL_PRINT_NAME);
        map.put(new Key(DataObject.class, FieldKey.GENERIC_NAME), EplVaGenNameDo.NAME);
        map.put(new Key(DataObject.class, FieldKey.PRIMARY_DRUG_CLASS), EplVaDrugClassDo.CODE);
        map.put(new Key(DataObject.class, FieldKey.PRIMARY_DRUG_CLASS2), EplVaDrugClassDo.CODE);
        map.put(new Key(DataObject.class, FieldKey.CMOP_ID), EplProductDo.CMOP_ID);
        map.put(new Key(DataObject.class, FieldKey.CMOP_DISPENSE), EplProductDo.CMOP_DISPENSE);
        map.put(new Key(DataObject.class, FieldKey.NATIONAL_FORMULARY_INDICATOR), EplProductDo.NATIONAL_FORMULARY_INDICATORY);
        map.put(new Key(DataObject.class, FieldKey.CREATED_DATE), DataObject.CREATED_DATE_TIME);
        map.put(new Key(DataObject.class, FieldKey.CREATED_BY), DataObject.CREATED_BY);
        map.put(new Key(DataObject.class, FieldKey.MODIFIED_DATE), DataObject.LAST_MODIFIED_DATE_TIME);
        map.put(new Key(DataObject.class, FieldKey.MODIFIED_BY), DataObject.LAST_MODIFIED_BY);
        map.put(new Key(DataObject.class, FieldKey.ACTIVE_INGREDIENT), EplIngredientDo.NAME);
        map.put(new Key(DataObject.class, FieldKey.SYNONYM_NAME), EplSynonymDo.SYNONYM_NAME);
        map.put(new Key(DataObject.class, FieldKey.OI_NAME), EplOrderableItemDo.OI_NAME);
        map.put(new Key(DataObject.class, FieldKey.DOSAGE_FORM), EplDosageFormDo.DF_NAME);
        map.put(new Key(DataObject.class, FieldKey.PRODUCT_STRENGTH), EplProductDo.STRENGTH);
        map.put(new Key(DataObject.class, FieldKey.PRODUCT_UNIT), EplDrugUnitDo.NAME);
        map.put(new Key(DataObject.class, FieldKey.DISPENSE_UNIT), EplVaDispenseUnitDo.DISPENSE_UNIT_NAME);
        map.put(new Key(DataObject.class, FieldKey.VENDOR_STOCK_NUMBER), EplNdcDo.VSN);
        map.put(new Key(DataObject.class, FieldKey.REVISION_NUMBER), EplProductDo.REVISION_NUMBER);

        map.put(new Key(DataObject.class, FieldKey.MEDICATION), EplProductDo.MEDICATION);
        map.put(new Key(DataObject.class, FieldKey.MEDICATION), EplNdcDo.MEDICATION);
        map.put(new Key(DataObject.class, FieldKey.MEDICATION), EplOrderableItemDo.MEDICATION);
        map.put(new Key(DataObject.class, FieldKey.INVESTIGATIONAL), EplProductDo.INVESTIGATIONAL);
        map.put(new Key(DataObject.class, FieldKey.INVESTIGATIONAL), EplNdcDo.INVESTIGATIONAL);
        map.put(new Key(DataObject.class, FieldKey.INVESTIGATIONAL), EplOrderableItemDo.INVESTIGATIONAL);
        map.put(new Key(DataObject.class, FieldKey.COMPOUND), EplProductDo.COMPOUND);
        map.put(new Key(DataObject.class, FieldKey.COMPOUND), EplNdcDo.COMPOUND);
        map.put(new Key(DataObject.class, FieldKey.COMPOUND), EplOrderableItemDo.COMPOUND);
        map.put(new Key(DataObject.class, FieldKey.SUPPLY), EplProductDo.SUPPLY);
        map.put(new Key(DataObject.class, FieldKey.SUPPLY), EplNdcDo.SUPPLY);
        map.put(new Key(DataObject.class, FieldKey.SUPPLY), EplOrderableItemDo.SUPPLY);
        map.put(new Key(DataObject.class, FieldKey.HERBAL), EplProductDo.HERBAL);
        map.put(new Key(DataObject.class, FieldKey.HERBAL), EplNdcDo.HERBAL);
        map.put(new Key(DataObject.class, FieldKey.HERBAL), EplOrderableItemDo.HERBAL);
        map.put(new Key(DataObject.class, FieldKey.CHEMOTHERAPY), EplProductDo.CHEMOTHERAPY);
        map.put(new Key(DataObject.class, FieldKey.CHEMOTHERAPY), EplNdcDo.CHEMOTHERAPY);
        map.put(new Key(DataObject.class, FieldKey.CHEMOTHERAPY), EplOrderableItemDo.CHEMOTHERAPY);
        map.put(new Key(DataObject.class, FieldKey.OTC), EplProductDo.OTC);
        map.put(new Key(DataObject.class, FieldKey.OTC), EplNdcDo.OTC);
        map.put(new Key(DataObject.class, FieldKey.OTC), EplOrderableItemDo.OTC);
        map.put(new Key(DataObject.class, FieldKey.VETERINARY), EplProductDo.VETERINARY);
        map.put(new Key(DataObject.class, FieldKey.VETERINARY), EplNdcDo.VETERINARY);
        map.put(new Key(DataObject.class, FieldKey.VETERINARY), EplOrderableItemDo.VETERINARY);
    }
    
    /**
     * The FIELD_KEY_TO_TABLE maps the "Key" to database table names (the Hibernate property name for the association).
     * <p>
     * The array of property names are the tables, in order, that must be joined to view the table from the DataObject.
     * <p>
     * If the FieldKey maps to the same property name for all DataObjects, use the DataObject class in the Key.
     * <p>
     * If the FieldKey maps to different property names for each DataObject, use a DataObject subclass in the Key.
     * 
     * @return Map<Key, String[]> FieldKey to tables to join
     */
    private static Map<Key, String[]> configureFieldKeyToTables() {
        Map<Key, String[]> map = new HashMap<Key, String[]>();

        map.put(new Key(EplNdcDo.class, FieldKey.MANUFACTURER), new String[] { EplNdcDo.MANUFACTURER });
        map.put(new Key(EplNdcDo.class, FieldKey.PACKAGE_TYPE), new String[] { EplNdcDo.PACKAGE_TYPE });
        map.put(new Key(EplNdcDo.class, FieldKey.GENERIC_NAME), new String[] {
            EplNdcDo.EPL_PRODUCT, EplProductDo.VA_GEN_NAME });
        map.put(new Key(EplNdcDo.class, FieldKey.STRENGTH), new String[] {
            EplNdcDo.EPL_PRODUCT, EplProductDo.PROD_INGREDIENT_ASSOCS });
        map.put(new Key(EplNdcDo.class, FieldKey.PRIMARY_DRUG_CLASS), new String[] {
            EplNdcDo.EPL_PRODUCT, EplProductDo.PROD_DRUG_CLASS_ASSOCS, EplProdDrugClassAssocDo.EPL_VA_DRUG_CLASS });
        map.put(new Key(EplNdcDo.class, FieldKey.PRIMARY_DRUG_CLASS2), new String[] {
            EplNdcDo.EPL_PRODUCT, EplProductDo.PROD_DRUG_CLASS_ASSOCS, EplProdDrugClassAssocDo.EPL_VA_DRUG_CLASS });

        map.put(new Key(EplNdcDo.class, FieldKey.PRODUCT_STRENGTH), new String[] { EplNdcDo.EPL_PRODUCT });

        map.put(new Key(EplNdcDo.class, FieldKey.PRODUCT_UNIT), new String[] {
            EplNdcDo.EPL_PRODUCT, EplProductDo.DRUG_UNIT });

        map.put(new Key(EplNdcDo.class, FieldKey.DISPENSE_UNIT), new String[] {
            EplNdcDo.EPL_PRODUCT, EplProductDo.VA_DISPENSE_UNIT });
        
        map.put(new Key(EplNdcDo.class, FieldKey.CMOP_DISPENSE), new String[] {
            EplNdcDo.EPL_PRODUCT });
        map.put(new Key(EplNdcDo.class, FieldKey.NATIONAL_FORMULARY_INDICATOR), new String[] {
            EplNdcDo.EPL_PRODUCT});

        map.put(new Key(EplNdcDo.class, FieldKey.VA_PRODUCT_NAME), new String[] { EplNdcDo.EPL_PRODUCT });

        map.put(new Key(EplProductDo.class, FieldKey.TRADE_NAME), new String[] {
            EplProductDo.SYNONYM_NAMES, EplSynonymDo.INTENDED_USE });
        map.put(new Key(EplProductDo.class, FieldKey.GENERIC_NAME), new String[] { EplProductDo.VA_GEN_NAME });
        map.put(new Key(EplProductDo.class, FieldKey.PRIMARY_DRUG_CLASS), new String[] {
            EplProductDo.PROD_DRUG_CLASS_ASSOCS, EplProdDrugClassAssocDo.EPL_VA_DRUG_CLASS });
        map.put(new Key(EplProductDo.class, FieldKey.PRIMARY_DRUG_CLASS2), new String[] {
            EplProductDo.PROD_DRUG_CLASS_ASSOCS, EplProdDrugClassAssocDo.EPL_VA_DRUG_CLASS });
        map.put(new Key(EplProductDo.class, FieldKey.ACTIVE_INGREDIENT), new String[] {
            EplProductDo.PROD_INGREDIENT_ASSOCS, EplProdIngredientAssocDo.EPL_INGREDIENT });
        map.put(new Key(EplProductDo.class, FieldKey.SYNONYM_NAME), new String[] { EplProductDo.SYNONYM_NAMES });
        map.put(new Key(EplProductDo.class, FieldKey.DOSAGE_FORM), new String[] {
            EplProductDo.EPL_ORDERABLE_ITEM, EplOrderableItemDo.EPL_DOSAGE_FORM });
        map.put(new Key(EplProductDo.class, FieldKey.PRODUCT_UNIT), new String[] {
            EplProductDo.DRUG_UNIT });
        map.put(new Key(EplProductDo.class, FieldKey.DISPENSE_UNIT), new String[] {
            EplProductDo.VA_DISPENSE_UNIT });
        map.put(new Key(EplProductDo.class, FieldKey.OI_NAME), new String[] { EplProductDo.EPL_ORDERABLE_ITEM });

        map.put(new Key(EplOrderableItemDo.class, FieldKey.DOSAGE_FORM), new String[] { EplOrderableItemDo.EPL_DOSAGE_FORM });
        map.put(new Key(EplOrderableItemDo.class, FieldKey.ORDERABLE_ITEM_PARENT),
            new String[] { EplOrderableItemDo.PARENT_OI });

        return map;
    }

    /**
     * Defines a Map of Correlations to a String array of length two, where the first array element is the correlated
     * attribute/field name for the outer table and the second array element is the correlated attribute/field name for the
     * inner table.
     * <p>
     * This Map is used for EXISTS searches, and therefore should only need additional configurations when a new field is
     * searched by that would normally require a join. The search uses subqueries and EXISTS instead of joins to circumvent
     * an issue in Hibernate where the same table cannot be joined to twice (preventing searching on two fields in the same
     * table or searching by one field and sorting by another).
     * 
     * @return Map<Correlation, String[]> defining the correlated DataObjects/tables and their correlated attributes/fields
     * 
     * @see <a href="http://opensource.atlassian.com/projects/hibernate/browse/HHH-879">Hibernate issue HHH-879</a>
     */
    private static Map<Correlation, String[]> configureCorrelations() {
        Map<Correlation, String[]> map = new HashMap<Correlation, String[]>();

        map.put(new Correlation(EplProductDo.class, EplVaGenNameDo.class), new String[] {
            EplProductDo.VA_GEN_NAME + DOT + EplVaGenNameDo.EPL_ID, EplVaGenNameDo.EPL_ID });
        map.put(new Correlation(EplProductDo.class, EplSynonymDo.class), new String[] {
            EplProductDo.EPL_ID, EplSynonymDo.EPL_PRODUCT + DOT + EplProductDo.EPL_ID });

        map.put(new Correlation(EplProductDo.class, EplProdDrugClassAssocDo.class),
                new String[] { EplProductDo.EPL_ID, EplProdDrugClassAssocDo.EPL_PRODUCT + DOT + EplProductDo.EPL_ID });

        map.put(new Correlation(EplProductDo.class, EplProdIngredientAssocDo.class),
                new String[] { EplProductDo.EPL_ID, EplProdIngredientAssocDo.EPL_PRODUCT + DOT + EplProductDo.EPL_ID });

        map.put(new Correlation(EplProductDo.class, EplProdIngredientAssocDo.class),
                new String[] { EplProductDo.EPL_ID, EplProdIngredientAssocDo.EPL_PRODUCT + DOT + EplProductDo.EPL_ID });

        map.put(new Correlation(EplProductDo.class, EplVadfOwnerDo.class), new String[] {
            EplProductDo.EPL_ID, EplVadfOwnerDo.EPL_PRODUCT + DOT + EplProductDo.EPL_ID });

        map.put(new Correlation(EplProductDo.class, EplDrugUnitDo.class), new String[] {
            EplProductDo.DRUG_UNIT + DOT + EplDrugUnitDo.EPL_ID, EplDrugUnitDo.EPL_ID });

        map.put(new Correlation(EplProductDo.class, EplVaDispenseUnitDo.class), new String[] {
            EplProductDo.VA_DISPENSE_UNIT + DOT + EplVaDispenseUnitDo.EPL_ID, EplVaDispenseUnitDo.EPL_ID });

        map.put(new Correlation(EplOrderableItemDo.class, EplDosageFormDo.class), new String[] {
            EplOrderableItemDo.EPL_DOSAGE_FORM + DOT + EplDosageFormDo.EPL_ID, EplDosageFormDo.EPL_ID });

        map.put(new Correlation(EplOrderableItemDo.class, EplOrderableItemDo.class), new String[] {
            EplOrderableItemDo.PARENT_OI + DOT + EplOrderableItemDo.EPL_ID, EplOrderableItemDo.EPL_ID });

        map.put(new Correlation(EplOrderableItemDo.class, EplVadfOwnerDo.class), new String[] {
            EplOrderableItemDo.EPL_ID, EplVadfOwnerDo.EPL_ORDERABLE_ITEM + DOT + EplOrderableItemDo.EPL_ID });

        map.put(new Correlation(EplNdcDo.class, EplVadfOwnerDo.class), new String[] {
            EplNdcDo.EPL_ID, EplVadfOwnerDo.EPL_NDC + DOT + EplNdcDo.EPL_ID });

        return map;
    }

    /**
     * Used to define a correlated query between an outer and inner class/table.
     */
    private static class Correlation {

        private Class<? extends DataObject> outer;
        private Class<? extends DataObject> inner;

        /**
         * Set the outer and inner classes.
         * 
         * @param outer DataObject outer class in correlation
         * @param inner DataObject inner class in correlation
         */
        public Correlation(Class<? extends DataObject> outer, Class<? extends DataObject> inner) {
            this.outer = outer;
            this.inner = inner;
        }

        /**
         * 
         * @return outer property
         */
        @SuppressWarnings("unused")
        public Class<? extends DataObject> getOuter() {
            return outer;
        }

        /**
         * 
         * @param outer outer property
         */
        @SuppressWarnings("unused")
        public void setOuter(Class<? extends DataObject> outer) {
            this.outer = outer;
        }

        /**
         * 
         * @return inner property
         */
        @SuppressWarnings("unused")
        public Class<? extends DataObject> getInner() {
            return inner;
        }

        /**
         * 
         * @param inner inner property
         */
        @SuppressWarnings("unused")
        public void setInner(Class<? extends DataObject> inner) {
            this.inner = inner;
        }

        /**
         * Equals returned by Jakarta Commons EqualsBuilder for Schema utility
         * 
         * @param obj Object to check equals against
         * @return true if equal
         */
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        /**
         * HashCode built by Jakarta Commons HasCodeBuilder for Schema utility
         * 
         * @return int hash
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        /**
         * toString returned by Jakarta Commons ToStringBuilder for Schema utility
         * 
         * @return String properties and values of the Correlation
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

    /**
     * Key to the internal {@link Map} instances used by the {@link SchemaUtility} include both the {@link DataObject} and
     * the {@link FieldKey}.
     */
    private static class Key {

        private Class<? extends DataObject> dataObject;
        private FieldKey fieldKey;

        /**
         * Set the {@link DataObject} and {@link FieldKey}
         * 
         * @param dataObject {@link DataObject}
         * @param fieldKey {@link FieldKey}
         */
        public Key(Class<? extends DataObject> dataObject, FieldKey fieldKey) {
            this.dataObject = dataObject;
            this.fieldKey = fieldKey;
        }

        /**
         * 
         * @return dataObject property
         */
        @SuppressWarnings("unused")
        public Class<? extends DataObject> getDataObject() {
            return dataObject;
        }

        /**
         * 
         * @param dataObject dataObject property
         */
        @SuppressWarnings("unused")
        public void setDataObject(Class<? extends DataObject> dataObject) {
            this.dataObject = dataObject;
        }

        /**
         * 
         * @return fieldKey property
         */
        @SuppressWarnings("unused")
        public FieldKey getFieldKey() {
            return fieldKey;
        }

        /**
         * 
         * @param fieldKey fieldKey property
         */
        @SuppressWarnings("unused")
        public void setFieldKey(FieldKey fieldKey) {
            this.fieldKey = fieldKey;
        }

        /**
         * Equals returned by Jakarta Commons EqualsBuilder
         * 
         * @param obj Object to check equals against
         * @return true if equal
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        /**
         * HashCode built by Jakarta Commons HasCodeBuilder
         * 
         * @return int hash
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        /**
         * toString returned by Jakarta Commons ToStringBuilder
         * 
         * @return String properties and values of the Key
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }
}
