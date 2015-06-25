/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManagedItemDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMultiTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMultiTextDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfEditablePropertyDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfEditablePropertyDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;


/**
 * Convert to/from {@link DataFields} and {@link EplVadfOwnerDo}.
 */
public class DataFieldsConverter extends Converter<DataFields<DataField>, EplVadfOwnerDo> {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DataFieldsConverter.class);

    private DataFieldsDomainCapability dataFieldsDomainCapability;

    /**
     * Add a selected item to the given ListDataField
     * 
     * @param dataField ListDataField to add the selection to
     * @param selection PEPS_SET selection
     * @param vaDf PEPS_VA_DFS VA data field definition
     */
    private void addSelectionToListDataField(ListDataField dataField, EplVadfLovDo selection, EplVaDfDo vaDf) {
        if (!isEmptyOrNotSelected(selection.getKey().getListValue())) {
            dataField.addStringSelection(selection.getKey().getListValue());
        }

        if (toBoolean(selection.getDefaultValue())) {
            if (!isEmptyOrNotSelected(selection.getKey().getListValue())) {
                dataField.addStringDefaultSelection(selection.getKey().getListValue());
            }
        }
    }

    /**
     * EplVaDfDo into a list of DataField with only its default value set
     * 
     * @param vaDf PepsVaDfHibernateDO
     * @param dataFields DataFields
     * 
     * @return DataField
     */
    public DataField convert(EplVaDfDo vaDf, DataFields dataFields) {
        return handleGroupDataFields(toDataField(vaDf), dataFields);
    }

    /**
     * The database is structured such that an item could have multiple {@link EplVadfOwnerDo}; however, in practice each
     * item only has one. Convert the first one, if it exists.
     * 
     * @param data Collection of {@link EplVadfOwnerDo}
     * @param entityType {@link EntityType} to which these data fields belong
     * @return {@link DataFields}
     */
    public DataFields convertFirst(Collection<EplVadfOwnerDo> data, EntityType entityType) {
        DataFields<DataField> fields = null;

        if (data == null || data.isEmpty()) {
            fields = new DataFields<DataField>();
        } else {
            try {
                fields = convert(data.iterator().next());
            } catch (Exception e) {
                LOG.trace("Unable to convert VA Data Fields! Returning empty DataFields instance! If retrieving an item "
                          + "via ManagedItemDomainCapability.retreive(), the VA data fields should be repopulated anyway.", e);
                fields = new DataFields<DataField>();
            }
        }

        fields = verifyFields(fields, entityType);

        return fields;
    }

    /**
     * In case the database has incorrect values for DataFields, use the FieldKeys to remove the extras and add default
     * values for the ones that are missing.
     * 
     * @param dataFields {@link DataFields} currently set DataFields
     * @param entityType {@link EntityType} to retrieve valid VA data fields from
     *            {@link FieldKey#getVaDataFields(EntityType)}
     * @return DataFields with extras removed and missing fields with default values
     */
    private DataFields verifyFields(DataFields dataFields, EntityType entityType) {
        List<FieldKey> validFieldKeys = new ArrayList<FieldKey>(FieldKey.getVaDataFields(entityType));

        Map<FieldKey, DataField> fields = dataFields.getDataFields();
        Set<FieldKey> fieldKeysWithValues = new HashSet<FieldKey>(fields.keySet());

        // The ones that remain don't belong to this EntityType, so remove them.
        fieldKeysWithValues.removeAll(validFieldKeys);

        for (FieldKey fieldKey : fieldKeysWithValues) {
            LOG.trace(" Data field '" + fieldKey.getKey() + "' set on '" + entityType.name() + "' with ownerID '"
                + dataFields.getVaDfOwnerId() + "' but the field belongs on a different EntityType!");
            dataFields.removeDataField(fieldKey);
        }

        // The ones that remain have not been set, so get default values for them.
        validFieldKeys.removeAll(fields.keySet());

        for (FieldKey fieldKey : validFieldKeys) {
            DataField field = dataFieldsDomainCapability.retrieveDomain(fieldKey);

            if (field == null) {
                LOG.trace("Data field with key '" + fieldKey.getKey() 
                    + "' is not in the EPL_VA_DFS table! Creating a new instance.");
                field = DataField.newInstance(fieldKey);
            } else {
                field.selectDefaultValue();
                LOG.trace("Data field '" + fieldKey.getKey() + "' not set on '" + entityType.name() + "' with owner ID '"
                    + dataFields.getVaDfOwnerId() + "' but should have been!");
            }

            dataFields.setDataField(field);
        }

        return dataFields;
    }

    /**
     * Convert the given {@link FieldKey} whose value is set within the given {@link EplVadfOwnerDo}.
     * <p>
     * If no value is set, return a {@link DataField} with the default values set.
     * <p>
     * The database is structured such that an item could have multiple {@link EplVadfOwnerDo}; however, in practice each
     * item only has one. Convert the first one, if it exists.
     * 
     * @param data Collection of {@link EplVadfOwnerDo}
     * @param fieldKeys Array of {@link FieldKey} within the values for the given {@link EplVadfOwnerDo} to convert
     * @return {@link DataFields}
     */
    public DataFields convertFirst(Collection<EplVadfOwnerDo> data, FieldKey... fieldKeys) {
        return convertFirst(data, Arrays.asList(fieldKeys));
    }

    /**
     * Convert the given {@link FieldKey} whose value is set within the given {@link EplVadfOwnerDo}.
     * <p>
     * If no value is set, return a {@link DataField} with the default values set.
     * <p>
     * The database is structured such that an item could have multiple {@link EplVadfOwnerDo}; however, in practice each
     * item only has one. Convert the first one, if it exists.
     * 
     * @param data Collection of {@link EplVadfOwnerDo}
     * @param fieldKeys Collection of {@link FieldKey} within the values for the given {@link EplVadfOwnerDo} to convert
     * @return {@link DataFields}
     */
    public DataFields convertFirst(Collection<EplVadfOwnerDo> data, Collection<FieldKey> fieldKeys) {
        DataFields<DataField> dataFields = new DataFields<DataField>();

        if (data != null && !data.isEmpty() && fieldKeys != null && !fieldKeys.isEmpty()) {
            EplVadfOwnerDo owner = data.iterator().next();

            Collection<FieldKey> expandedKeys = new ArrayList<FieldKey>();

            for (FieldKey fieldKey : fieldKeys) {
                if (fieldKey.isGroupDataField()) {
                    expandedKeys.addAll(fieldKey.getGroupedFields());
                } else {
                    expandedKeys.add(fieldKey);
                }
            }

            for (FieldKey key : expandedKeys) {
                for (EplVadfEditablePropertyDo property : owner.getEplVadfEditableProperties()) {
                    if (key.getKey().equals(property.getEplVaDf().getVadfName())) {
                        setEditableProperties(dataFields, property);
                    }
                }

                if (dataFields.getDataField(key) == null) {
                    dataFields.setDataField(dataFieldsDomainCapability.retrieveDomain(key));
                } else {
                    if (key.isListDataField()) {
                        for (EplVadfAssocValueDo property : owner.getEplVadfAssocValues()) {
                            if (key.getKey().equals(property.getEplVadfLov().getEplVaDf().getVadfName())) {
                                setListDataField(dataFields, property);
                            }
                        }
                    } else if (key.isSimpleDataField()) {
                        for (EplVadfNonlistValueDo property : owner.getEplVadfNonlistValues()) {
                            if (key.getKey().equals(property.getEplVaDf().getVadfName())) {
                                setNonListDataField(dataFields, property);
                            }
                        }
                    } else if (key.isMultitextDataField() || key.isPrimaryKeyDataField()) {
                        for (EplMultiTextDo property : owner.getEplMultiTexts()) {
                            if (key.getKey().equals(property.getEplVaDf().getVadfName())) {
                                setMultiTextDataField(dataFields, property);
                            }
                        }
                    }
                }
            }

            setGroupDataFields(dataFields);
        }

        return dataFields;
    }

    /**
     * If the DataField, field, is an element of a CompositeDataField (currently only DispenseQuantityLimit), set the values
     * appropriately and return either the DataField, field, or the CompositeDataField as needed.
     * 
     * @param field DataField to handle
     * @param dataFields DataFields to get the current CompositeDataField instance
     * @return DataField either the original field if it is not a CompositeDataField, or the CompositeDataField
     */
    private DataField handleGroupDataFields(DataField field, DataFields<DataField> dataFields) {
        if (field.isGroupedDataField()) {
            FieldKey<GroupDataField> groupKey = field.getGroupKey();
            GroupDataField group = dataFields.getDataField(groupKey);

            if (group == null) {
                group = DataField.newInstance(groupKey);
            }

            group.selectGroupedFieldValue(field);

            return group;
        } else {
            return field;
        }
    }

    /**
     * Convert the VA data field definition into a DataField with the default value(s) set Note that this method does not set
     * the actual value of the returned DataField.
     * 
     * @param vaDf VA data field definition
     * @return DataField populated from VA data field definition with default values set
     */
    private DataField toDataField(EplVaDfDo vaDf) {
        System.out.println("About to convert vaDf.getVadfName() "+vaDf.getVadfName());
        DataField field = DataField.newInstance((FieldKey) FieldKey.getKey(vaDf.getVadfName()));

        field.setDataFieldId(vaDf.getId());
        field.setEditable(true);

        if (field.isListDataField()) {
            ListDataField listField = (ListDataField) field;

            if (vaDf.getEplVadfLovs() != null) {
                for (EplVadfLovDo eplVadfLovDo : vaDf.getEplVadfLovs()) {

                    // default the value id to null and editable to false since we don't have that info here
                    addSelectionToListDataField(listField, eplVadfLovDo, vaDf);
                }
            }
        } else {
            field.setStringDefaultValue(vaDf.getDefaultValue());
        }

        return field;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(ValueObject)} and then populate the parent
     * data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data {@link ValueObject} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @return fully populated {@link DataObject}
     */
    public EplVadfOwnerDo convert(DataFields<DataField> data, DataObject parent) {
        EplVadfOwnerDo value = convert(data);

        if (parent instanceof EplOrderableItemDo) {
            EplOrderableItemDo orderableItem = (EplOrderableItemDo) parent;
            value.setEplOrderableItem(orderableItem);
            value.setVadfOwnerId(orderableItem.getEplId());
            value.setVadfOwnerType(EntityType.ORDERABLE_ITEM.name());
        } else if (parent instanceof EplProductDo) {
            EplProductDo product = (EplProductDo) parent;
            value.setEplProduct(product);
            value.setVadfOwnerId(product.getEplId());
            value.setVadfOwnerType(EntityType.PRODUCT.name());
        } else if (parent instanceof EplNdcDo) {
            EplNdcDo ndc = (EplNdcDo) parent;
            value.setEplNdc(ndc);
            value.setVadfOwnerId(ndc.getEplId());
            value.setVadfOwnerType(EntityType.NDC.name());
        } else if (parent instanceof EplDosageFormDo) {
            value.setVadfOwnerId(((EplDosageFormDo) parent).getEplId());
            value.setVadfOwnerType(EntityType.DOSAGE_FORM.name());
        }

        return value;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     * gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplVadfOwnerDo toDataObject(DataFields<DataField> data) {
        EplVadfOwnerDo ownerDo = new EplVadfOwnerDo();

        Set properties = new HashSet();
        Set nonListValues = new HashSet();
        Set ndcProdAssocValues = new HashSet();
        Set multiTextValues = new HashSet();

        if (data.getVaDfOwnerId() != null) {
            ownerDo.setId(Long.valueOf(data.getVaDfOwnerId()));

            // iterate through dataFields
            List<DataField> values = new ArrayList<DataField>();
            values.addAll(data.getDataFields().values());

            // pull the individual grouped DataFields out of the GroupDataFields
            for (FieldKey<GroupDataField> groupKey : FieldKey.getGroupDataFields()) {
                if (data.containsFieldKey(groupKey)) {
                    GroupDataField group = data.getDataField(groupKey);
                    values.addAll(group.getValue());
                    values.remove(group);
                }
            }

            for (DataField field : values) {

                // add editable property
                EplVadfEditablePropertyDo edit = createEditableProperty(field, data.getVaDfOwnerId());

                if (edit.getKey().getVadfIdFk() != null) {
                    properties.add(edit);
                }

                if (field.isListDataField()) {
                    List selections = (List) field.getValue();

                    for (Object selection : selections) {

                        EplVadfAssocValueDoKey nkey = new EplVadfAssocValueDoKey();

                        nkey.setVadfOwnerIdFk(new Long(data.getVaDfOwnerId()));
                        nkey.setVadfIdFk(field.getDataFieldId());
                        nkey.setListValue(selection.toString());

                        EplVadfAssocValueDo ndcAssoc = new EplVadfAssocValueDo();
                        ndcAssoc.setKey(nkey);

                        ndcProdAssocValues.add(ndcAssoc);
                    }
                } else if (field.isPrimaryKeyDataField()) {

                    // these are where the primary key/epl_id is being stored in the text field
                    // also populate the pk_owner_type =P which means what is stored in text column is the primary key of
                    // the managed ItemVo

                    // only save if it has a value
                    if ((field.getValue() != null) && (((List<ManagedItemVo>) field.getValue()).size() > 0)) {
                        List<ManagedItemVo> selections = (List<ManagedItemVo>) field.getValue();

                        for (ManagedItemVo selection : selections) {
                            EplMultiTextDoKey nkey = new EplMultiTextDoKey();

                            nkey.setVadfOwnerIdFk(new Long(data.getVaDfOwnerId()));
                            nkey.setVadfIdFk(field.getDataFieldId());
                            nkey.setText(selection.getId());

                            EplMultiTextDo multi = new EplMultiTextDo();
                            multi.setKey(nkey);
                            multi.setPkOwnerType("P");
                            multi.setPkTableName(selection.getEntityType().name());

                            multiTextValues.add(multi);
                        }
                    }
                } else if (field.isMultitextDataField()) {
                    List selections = (List) field.getValue();

                    for (Object selection : selections) {

                        EplMultiTextDoKey nkey = new EplMultiTextDoKey();

                        nkey.setVadfOwnerIdFk(new Long(data.getVaDfOwnerId()));
                        nkey.setVadfIdFk(field.getDataFieldId());
                        nkey.setText(selection.toString());

                        EplMultiTextDo multi = new EplMultiTextDo();
                        multi.setKey(nkey);

                        multiTextValues.add(multi);
                    }
                } else {

                    // only save if it has a value
                    if (field.getValue() != null) {
                        EplVadfNonlistValueDoKey mkey = new EplVadfNonlistValueDoKey();
                        mkey.setVadfIdFk(field.getDataFieldId());
                        mkey.setVadfOwnerIdFk(new Long(data.getVaDfOwnerId()));

                        EplVadfNonlistValueDo nonListDo = new EplVadfNonlistValueDo();
                        nonListDo.setKey(mkey);

                        if (field.isDateSimpleDataField()) {
                            nonListDo.setVaDfValue(String.valueOf(((Date) field.getValue()).getTime()));
                        } else {
                            nonListDo.setVaDfValue(field.getValue().toString());
                        }

                        nonListValues.add(nonListDo);
                    }//
                }// end of else

            } // end of for

            ownerDo.setEplVadfEditableProperties(properties);
            ownerDo.setEplVadfNonlistValues(nonListValues);
            ownerDo.setEplVadfAssocValues(ndcProdAssocValues);
            ownerDo.setEplMultiTexts(multiTextValues);
        }

        return ownerDo;
    }

    /**
     * 
     * @param field DataFieldO
     * @param ownerId String
     * @return EplVadfEditabelPropertyDo
     */
    private EplVadfEditablePropertyDo createEditableProperty(DataField field, String ownerId) {

        EplVadfEditablePropertyDoKey key = new EplVadfEditablePropertyDoKey();
        key.setVadfIdFk(field.getDataFieldId());
        key.setVadfOwnerIdFk(Long.valueOf(ownerId));

        EplVadfEditablePropertyDo propertyDO = new EplVadfEditablePropertyDo();
        propertyDO.setKey(key);
        propertyDO.setEditableYn(toYesOrNo(field.isEditable()));

        return propertyDO;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a DataFields value object.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated DataFields ValueObject
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     * gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DataFields toValueObject(EplVadfOwnerDo data) {
        DataFields<DataField> dataFields = new DataFields();

        if (data.getId() == null) {
            return dataFields;
        }

        dataFields.setVaDfOwnerId(data.getId().toString());

        // iterate over my editableVaProperties() to create dataField and add to dataFields
        // only set the datafield id and editable
        for (EplVadfEditablePropertyDo property : data.getEplVadfEditableProperties()) {
            setEditableProperties(dataFields, property);
        }

        // iterate over list values
        // all the list values should already exist in the DataFields set
        // find the correct one and append the values
        for (EplVadfAssocValueDo property : data.getEplVadfAssocValues()) {
            setListDataField(dataFields, property);
        }

        // iterate over non list values
        for (EplVadfNonlistValueDo property : data.getEplVadfNonlistValues()) {
            setNonListDataField(dataFields, property);
        }

        // iterate over mulit text values
        // both primarykey fields and multi texst fields are stored in here
        // if pk_owner_type is set to P then it is a primary key field
        // if pk_owner_type is null then it is multiple data filed
        for (EplMultiTextDo property : data.getEplMultiTexts()) {
            setMultiTextDataField(dataFields, property);
        }

        // values for all fields grouped in a GroupDataField must be present before the GroupDataField will be created
        setGroupDataFields(dataFields);

        return dataFields;
    }

    /**
     * Set the DataField ID and editable values.
     * 
     * @param dataFields {@link DataFields} current data fields
     * @param property {@link EplVadfEditablePropertyDo}
     */
    private void setEditableProperties(DataFields<DataField> dataFields, EplVadfEditablePropertyDo property) {
        FieldKey fieldKey = FieldKey.getKey(property.getEplVaDf().getVadfName());

        DataField field = dataFields.getDataField(fieldKey);

        if (field == null) {
            field = DataField.newInstance(fieldKey);
        }

        field.setDataFieldId(property.getKey().getVadfIdFk());
        field.setEditable(toBoolean(property.getEditableYn()));

        dataFields.setDataField(field);
    }

    /**
     * Set the ListDataField values
     * 
     * @param dataFields {@link DataFields} current data fields
     * @param property {@link EplVadfAssocValueDo}
     */
    private void setListDataField(DataFields<DataField> dataFields, EplVadfAssocValueDo property) {
        FieldKey fieldKey = FieldKey.getKey(property.getEplVadfLov().getEplVaDf().getVadfName());

        ListDataField listField = (ListDataField) dataFields.getDataField(fieldKey);

        if (listField == null) {
            listField = (ListDataField) DataField.newInstance(fieldKey);
        }

        listField.setDataFieldId(property.getKey().getVadfIdFk());

        if (!isEmptyOrNotSelected(property.getKey().getListValue())) {
            listField.addStringSelection(property.getKey().getListValue());
        }

        if (toBoolean(property.getEplVadfLov().getDefaultValue())) {
            if (!isEmptyOrNotSelected(property.getKey().getListValue())) {
                listField.addStringDefaultSelection(property.getKey().getListValue());
            }
        }

        dataFields.setDataField(listField);
    }

    /**
     * Set the non-List data field values
     * 
     * @param dataFields {@link DataFields} current data fields
     * @param property {@link EplVadfNonlistValueDo}
     */
    private void setNonListDataField(DataFields<DataField> dataFields, EplVadfNonlistValueDo property) {
        FieldKey fieldKey = FieldKey.getKey(property.getEplVaDf().getVadfName());

        DataField field = dataFields.getDataField(fieldKey);

        if (field == null) {
            field = DataField.newInstance(fieldKey);
        }

        field.setDataFieldId(property.getKey().getVadfIdFk());
        field.selectStringValue(property.getVaDfValue());
        field.setStringDefaultValue(property.getEplVaDf().getDefaultValue());

        dataFields.setDataField(field);
    }

    /**
     * Set the multi-text data field values
     * 
     * @param dataFields {@link DataFields} current data fields
     * @param property {@link EplMultiTextDo}
     */
    private void setMultiTextDataField(DataFields<DataField> dataFields, EplMultiTextDo property) {
        FieldKey fieldKey = FieldKey.getKey(property.getEplVaDf().getVadfName());

        ListDataField field = (ListDataField) dataFields.getDataField(fieldKey);

        if (field == null) {
            field = (ListDataField) DataField.newInstance(fieldKey);
        }

        field.setDataFieldId(property.getKey().getVadfIdFk());
        field.addStringDefaultSelection(property.getEplVaDf().getDefaultValue());

        if (property.getPkOwnerType() != null && "P".equals(property.getPkOwnerType()) && property.getPkTableName() != null) {
            EntityType entityType = EntityType.valueOf(property.getPkTableName());
            ManagedItemDomainCapability capability = getManagedItemDomainCapability(entityType);

            try {
                ManagedItemVo item = capability.retrieveMinimal(property.getKey().getText());
                field.addSelection(item);
            } catch (ItemNotFoundException e) {
                LOG.warn("Item not found to add to Primary Key List DataField. Setting ID instead.", e);
                field.addStringSelection(property.getKey().getText());
            }
        } else {
            field.addStringSelection(property.getKey().getText());
        }

        dataFields.setDataField(field);
    }

    /**
     * If any grouped data fields are in DataFields, group them together under one DataField instance.
     * 
     * @param dataFields {@link DataFields} current data fields
     */
    private void setGroupDataFields(DataFields<DataField> dataFields) {
        for (FieldKey<GroupDataField> groupKey : FieldKey.getGroupDataFields()) {
            if (dataFields.containsFieldKeys(groupKey.getGroupedFields())) {
                GroupDataField group = dataFields.getDataField(groupKey);

                if (group == null) {
                    group = DataField.newInstance(groupKey);
                }

                for (FieldKey<DataField> groupedKey : groupKey.getGroupedFields()) {
                    DataField dataField = dataFields.removeDataField(groupedKey);
                    group.selectGroupedFieldValue(dataField);
                }

                dataFields.setDataField(group);
            }
        }
    }

    /**
     * setDataFieldsDomainCapability
     * 
     * @param dataFieldsDomainCapability dataFieldsDomainCapability property
     */
    public void setDataFieldsDomainCapability(DataFieldsDomainCapability dataFieldsDomainCapability) {
        this.dataFieldsDomainCapability = dataFieldsDomainCapability;
    }
}
