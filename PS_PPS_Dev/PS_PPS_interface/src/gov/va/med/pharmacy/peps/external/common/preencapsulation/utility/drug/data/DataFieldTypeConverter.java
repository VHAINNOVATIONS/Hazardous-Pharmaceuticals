/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.data;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.BooleanDataFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.BooleanFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexDataFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexItemNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexItemType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DoubleDataFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DoubleFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugDataFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.IntegerDataFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.IntegerFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ItemType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ListDataFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ListFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.StringDataFieldType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.StringFieldNameType;


/**
 * Creates the elements used in the drug data response. This class is abstract because only NdcDataFieldTypeConverter and
 * ProductDataFieldTypeConverter should be used.
 */
public class DataFieldTypeConverter extends AbstractConverter {

    /** FACTORY
     * factory for creation of all JAXB generated classes
     */
    protected static ObjectFactory fACTORY = new ObjectFactory();

    /** DATE_FORMAT
     * controls formatting for the dates used
     */
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

    //
    // complex methods
    //

    /**
     * private constructor
     */
    protected DataFieldTypeConverter() {
    }

    /**
     * Creates a complex field with data from items. The items map should map from ComplexItemNameType to a String value.
     * Uses the fieldName as the name for the constructed ComplexDataFieldType
     * 
     * @param fieldName name to apply to the constructed complex type
     * @param items mapping from omplexItemNameType to a String value
     * @return returns the constructed ComplexDataFieldType
     */
    protected static synchronized ComplexDataFieldType createComplexField(ComplexFieldNameType fieldName,
                                                                          Map<String, String> items) {
        ComplexDataFieldType complexType = fACTORY.createComplexDataFieldType();
        complexType.setFieldName(fieldName);

        Set<String> itemNames = items.keySet();

        for (String itemName : itemNames) {
            ComplexItemType item = fACTORY.createComplexItemType();
            item.setName(ComplexItemNameType.fromValue(itemName));
            item.setValue((String) items.get(itemName));
            complexType.getComplexItem().add(item);
        }

        return complexType;
    }

    //
    // list methods
    //

    /**
     * Adds a list field to the drugDataFieldsType using VA data pulled from the dataFields using the key.
     * 
     * @param drugDataFieldsType DrugDataFieldsType the list will be added to
     * @param dataFields map containing the data
     * @param key FieldKey to pull the correct DataField from dataFields
     * @param fieldName name to apply to the constructed list
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addListField(DrugDataFieldsType drugDataFieldsType,
                                                                  DataFields<DataField> dataFields, FieldKey key,
                                                                  ListFieldNameType fieldName) {

        // retrieve data field from map using the key
        ListDataField mapVal = (ListDataField) dataFields.getDataField(key);

        if (mapVal != null && mapVal.getValue() != null && mapVal.getValue().size() > 0) {
            if (mapVal.getValue().get(0) instanceof String) {
                addListField(drugDataFieldsType, (List<String>) mapVal.getValue(), fieldName);
            } else if (mapVal.getValue().get(0) instanceof ProductVo) {
                List<String> values = new ArrayList<String>();

                for (ProductVo product : (List<ProductVo>) mapVal.getValue()) {
                    values.add(product.getValue());
                }

                addListField(drugDataFieldsType, values, fieldName);
            }
        }

        return drugDataFieldsType;
    }

    /**
     * Adds a ListDataFieldType to the DrugDataFieldsType, using value as the single value in the list and naming the
     * ListDataFieldType using the ListFieldNameType
     * 
     * @param drugDataFieldsType DrugDataFieldsType the list will be added to
     * @param value used as the single value for the list
     * @param fieldName name to apply to the constructed list
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addListField(DrugDataFieldsType drugDataFieldsType, String value,
                                                                  ListFieldNameType fieldName) {
        if (value != null) {
            drugDataFieldsType.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createListField(Arrays.asList(value), fieldName));
        }

        return drugDataFieldsType;
    }

    /**
     * Adds a ListDataFieldType to the DrugDataFieldsType, using values as the values in the list and naming the
     * ListDataFieldType using the ListFieldNameType
     * 
     * @param drugDataFieldsType DrugDataFieldsType the list will be added to
     * @param values used as the values for the list
     * @param fieldName fieldName name to apply to the constructed list
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addListField(DrugDataFieldsType drugDataFieldsType,
                                                                  List<String> values, ListFieldNameType fieldName) {
        if (values != null) {
            drugDataFieldsType.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createListField(values, fieldName));
        }

        return drugDataFieldsType;
    }

    /**
     * Creates a ListDataFieldType using values as the values in the list and naming the ListDataFieldType using the
     * ListFieldNameType
     * 
     * @param values used as the values for the list
     * @param fieldName fieldName name to apply to the constructed list
     * @return returns the constructed ListDataFieldType
     */
    protected static synchronized ListDataFieldType createListField(List<String> values, ListFieldNameType fieldName) {
        ListDataFieldType listType = fACTORY.createListDataFieldType();
        listType.setFieldName(fieldName);

        for (String val : values) {
            ItemType item = fACTORY.createItemType();
            item.setValue(val);
            listType.getItem().add(item);
        }

        return listType;
    }

    //
    // boolean methods
    //

    /**
     * Adds a boolean field to the drugDataFieldsType using VA data pulled from the dataFields using the key.
     * 
     * @param drugDataFieldsType DrugDataFieldsType the boolean field will be added to
     * @param dataFields map containing the data
     * @param key FieldKey to pull the correct DataField from dataFields
     * @param fieldName name to apply to the constructed boolean field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addBooleanField(DrugDataFieldsType drugDataFieldsType,
                                                                     DataFields<DataField> dataFields, FieldKey key,
                                                                     BooleanFieldNameType fieldName) {

        // retrieve data field from map using the key
        DataField<Boolean> mapVal = dataFields.getDataField(key);

        if (mapVal != null && mapVal.getValue() != null) {
            addBooleanField(drugDataFieldsType, mapVal.getValue(), fieldName);
        }

        return drugDataFieldsType;
    }

    /**
     * Adds a BooleanDataFieldType to the DrugDataFieldsType, using value as the boolean value in the BooleanDataFieldType
     * and naming the BooleanDataFieldType using the BooleanFieldNameType
     * 
     * @param drugDataFieldsType DrugDataFieldsType the boolean field will be added to
     * @param value used as the boolean value for the field
     * @param fieldName fieldName name to apply to the constructed boolean field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addBooleanField(DrugDataFieldsType drugDataFieldsType, Boolean value,
                                                                     BooleanFieldNameType fieldName) {
        if (value != null) {
            drugDataFieldsType.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createBooleanField(value, fieldName));
        }

        return drugDataFieldsType;
    }

    /**
     * Creates a BooleanDataFieldType using boolean value as the values in the field and naming the BooleanDataFieldType
     * using the BooleanFieldNameType
     * 
     * @param value used as the boolean value for the field
     * @param fieldName fieldName name to apply to the constructed field
     * @return returns the constructed BooleanDataFieldType
     */
    protected static synchronized BooleanDataFieldType createBooleanField(boolean value, BooleanFieldNameType fieldName) {
        BooleanDataFieldType boolType = fACTORY.createBooleanDataFieldType();
        boolType.setFieldName(fieldName);
        boolType.setValue(value);

        return boolType;
    }

    //
    // integer methods
    //

    /**
     * Adds an integer field to the drugDataFieldsType using VA data pulled from the dataFields using the key.
     * 
     * @param drugDataFieldsType DrugDataFieldsType the integer field will be added to
     * @param dataFields map containing the data
     * @param key FieldKey to pull the correct DataField from dataFields
     * @param fieldName name to apply to the constructed integer field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addIntegerField(DrugDataFieldsType drugDataFieldsType,
                                                                     DataFields<DataField> dataFields, FieldKey key,
                                                                     IntegerFieldNameType fieldName) {

        // retrieve data field from map using the key
        DataField mapVal = dataFields.getDataField(key);

        if (mapVal != null && mapVal.getValue() != null) {
            if (mapVal.getValue() instanceof BigInteger) {
                addIntegerField(drugDataFieldsType, (BigInteger) mapVal.getValue(), fieldName);
            } else if (mapVal.getValue() instanceof Long) {
                addIntegerField(drugDataFieldsType, BigInteger.valueOf((Long) mapVal.getValue()), fieldName);
            }
        }

        return drugDataFieldsType;
    }

    /**
     * Adds a IntegerDataFieldType to the DrugDataFieldsType, using value as the integer value in the IntegerDataFieldType
     * and naming the IntegerDataFieldType using the IntegerFieldNameType
     * 
     * @param drugDataFieldsType DrugDataFieldsType the integer field will be added to
     * @param value used as the integer value for the field
     * @param fieldName fieldName name to apply to the constructed integer field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addIntegerField(DrugDataFieldsType drugDataFieldsType,
                                                                     BigInteger value, IntegerFieldNameType fieldName) {
        if (value != null) {
            drugDataFieldsType.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createIntegerField(value, fieldName));
        }

        return drugDataFieldsType;
    }

    /**
     * Creates a IntegerDataFieldType using integer value as the values in the field and naming the IntegerDataFieldType
     * using the IntegerFieldNameType
     * 
     * @param value used as the integer value for the field
     * @param fieldName fieldName name to apply to the constructed field
     * @return returns the constructed IntegerDataFieldType
     */
    protected static synchronized IntegerDataFieldType createIntegerField(BigInteger value, IntegerFieldNameType fieldName) {
        IntegerDataFieldType intType = fACTORY.createIntegerDataFieldType();
        intType.setFieldName(fieldName);
        intType.setValue(value);

        return intType;
    }

    //
    // double methods
    //

    /**
     * Adds an double field to the drugDataFieldsType using VA data pulled from the dataFields using the key.
     * 
     * @param drugDataFieldsType DrugDataFieldsType the double field will be added to
     * @param dataFields map containing the data
     * @param key FieldKey to pull the correct DataField from dataFields
     * @param fieldName name to apply to the constructed double field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addDoubleField(DrugDataFieldsType drugDataFieldsType,
                                                                    DataFields<DataField> dataFields,
                                                                    FieldKey<DataField<Double>> key,
                                                                    DoubleFieldNameType fieldName) {

        // retrieve data field from map using the key
        DataField<Double> mapVal = dataFields.getDataField(key);

        if (mapVal != null && mapVal.getValue() != null) {
            addDoubleField(drugDataFieldsType, mapVal.getValue(), fieldName);
        }

        return drugDataFieldsType;
    }

    /**
     * Adds a DoubleDataFieldType to the DrugDataFieldsType, using value as the double value in the DoubleDataFieldType and
     * naming the DoubleDataFieldType using the DoubleFieldNameType
     * 
     * @param drugDataFieldsType DrugDataFieldsType the double field will be added to
     * @param value used as the double value for the field
     * @param fieldName fieldName name to apply to the constructed double field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addDoubleField(DrugDataFieldsType drugDataFieldsType, Double value,
                                                                    DoubleFieldNameType fieldName) {
        if (value != null) {
            drugDataFieldsType.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createDoubleField(value, fieldName));
        }

        return drugDataFieldsType;
    }

    /**
     * Creates a DoubleDataFieldType using double value as the values in the field and naming the DoubleDataFieldType using
     * the DoubleFieldNameType
     * 
     * @param value used as the double value for the field
     * @param fieldName fieldName name to apply to the constructed field
     * @return returns the constructed DoubleDataFieldType
     */
    protected static synchronized DoubleDataFieldType createDoubleField(Double value, DoubleFieldNameType fieldName) {
        DoubleDataFieldType doubleType = fACTORY.createDoubleDataFieldType();
        doubleType.setFieldName(fieldName);
        doubleType.setValue(value);

        return doubleType;
    }

    //
    // String methods
    //

    /**
     * Adds an String field to the drugDataFieldsType using VA data pulled from the dataFields using the key.
     * 
     * @param drugDataFieldsType DrugDataFieldsType the String field will be added to
     * @param dataFields map containing the data
     * @param key FieldKey to pull the correct DataField from dataFields
     * @param fieldName name to apply to the constructed String field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addStringField(DrugDataFieldsType drugDataFieldsType,
                                                                    DataFields<DataField> dataFields, FieldKey key,
                                                                    StringFieldNameType fieldName) {

        // retrieve data field from map using the key
        DataField mapVal = dataFields.getDataField(key);

        if (mapVal != null && mapVal.getValue() != null) {
            if (mapVal.getValue() instanceof String) {
                addStringField(drugDataFieldsType, (String) mapVal.getValue(), fieldName);
            } else if (mapVal.getValue() instanceof Date) {
                addStringField(drugDataFieldsType, DATE_FORMAT.format((Date) mapVal.getValue()), fieldName);
            }
        }

        return drugDataFieldsType;
    }

    /**
     * Adds a StringDataFieldType to the DrugDataFieldsType, using value as the String value in the StringDataFieldType and
     * naming the StringDataFieldType using the StringFieldNameType
     * 
     * @param drugDataFieldsType DrugDataFieldsType the String field will be added to
     * @param value used as the String value for the field
     * @param fieldName fieldName name to apply to the constructed String field
     * @return returns the modified drugDataFieldsType
     */
    protected static synchronized DrugDataFieldsType addStringField(DrugDataFieldsType drugDataFieldsType, String value,
                                                                    StringFieldNameType fieldName) {
        if (value != null) {
            drugDataFieldsType.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createStringField(fieldName, value));
        }

        return drugDataFieldsType;
    }

    /**
     * Creates a StringDataFieldType using String value as the values in the field and naming the StringDataFieldType using
     * the StringFieldNameType
     * 
     * @param value used as the String value for the field
     * @param fieldName fieldName name to apply to the constructed field
     * @return returns the constructed StringDataFieldType
     */
    protected static synchronized StringDataFieldType createStringField(StringFieldNameType fieldName, String value) {
        StringDataFieldType stringType = fACTORY.createStringDataFieldType();
        stringType.setFieldName(fieldName);
        stringType.setValue(value);

        return stringType;
    }

}
