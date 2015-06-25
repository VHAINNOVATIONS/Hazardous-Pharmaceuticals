/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.DataFieldsCapability;


/**
 * Perform operations on and with data fields
 */
public class DataFieldsCapabilityImpl implements DataFieldsCapability {
    private DataFieldsDomainCapability dataFieldsDomainCapability;

    /**
     * Retrieve all data fields for the given {@link EntityType} with the default values set
     * 
     * @param itemType EntityType to retrieve default values for
     * @return DataFields
     */
    public DataFields<DataField> retrieveDefaultDomain(EntityType itemType) {
        DataFields allDataFields = retrieveDefaultDomain();
        DataFields dataFields = new DataFields();

        Collection<FieldKey> fieldKeys = FieldKey.getVaDataFields(itemType);

        for (FieldKey fieldKey : fieldKeys) {
            if (fieldKey != null) {
                DataField dataField = allDataFields.getDataField(fieldKey);

                if (dataField != null) {
                    dataFields.setDataField(dataField);
                }
            }
        }

        return dataFields;
    }

    /**
     * Retrieve all data fields with only the default values set
     * 
     * @return DataFields
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.DataFieldsCapability#retrieveDefaultDomain()
     */
    private DataFields retrieveDefaultDomain() {
        DataFields<DataField> fields = retrieveDomain();

        for (DataField dataField : fields.getDataFields().values()) {
            dataField.setEditable(true);

            if (dataField.isGroupDataField()) {
                for (DataField groupedField : ((GroupDataField) dataField).getValue()) {
                    setDataFieldDefaultValue(groupedField);
                }
            } else {
                setDataFieldDefaultValue(dataField);
            }
        }

        return fields;
    }

    /**
     * Set the value of the given DataField to the DataField's default value. If the DataField's default value is no-select
     * (null), the selected property will be set to false and no value will have been set on the field.
     * 
     * @param field DataField to set the value on
     */
    private void setDataFieldDefaultValue(DataField field) {
        if (field instanceof ListDataField) {
            ListDataField listField = (ListDataField) field;
            listField.unselect();
            listField.addSelections(listField.getDefaultValue());
        } else {
            field.unselect();
            field.selectValue(field.getDefaultValue());
        }
    }

    /**
     * Retrieve all possible values for all data fields
     * 
     * @return DataFields
     * 
     * @see gov.va.med.pharmacy.peps.service.common.capability.DataFieldsCapability#retrieveDomain()
     */
    public DataFields retrieveDomain() {
        return dataFieldsDomainCapability.retrieveDomain();
    }

    /**
     * Sets the dataFieldCapability
     * 
     * @param dataFieldsDomainCapability dataFieldsDomainCapability property
     */
    public void setDataFieldsDomainCapability(DataFieldsDomainCapability dataFieldsDomainCapability) {
        this.dataFieldsDomainCapability = dataFieldsDomainCapability;
    }
}
