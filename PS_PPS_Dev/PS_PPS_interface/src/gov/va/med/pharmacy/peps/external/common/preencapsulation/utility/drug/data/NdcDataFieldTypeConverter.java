/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.data;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.BooleanFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexItemNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DoubleFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugDataFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugRequestIdentifier;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ListFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.StringFieldNameType;


/**
 * Convert the NdcVo type to JAXB generated objects for XML serialization. Some data is pulled from the NdcVo directly while
 * other data is pulled from the NDC data fields.
 */
public class NdcDataFieldTypeConverter extends DataFieldTypeConverter {

    /**
     * Private constructor
     */
    private NdcDataFieldTypeConverter() {
    }

    /**
     * Convert the NdcVo type to JAXB generated objects for XML serialization. Some data is pulled from the NdcVo directly
     * while other data is pulled from the NDC data fields.
     *
     * Adding a comment on 7/20 to this class
     * 
     * @param ndc the NdcVo to convert
     * @return DrugDataFieldsType high level XML element from JAXB generated object
      */
    public static DrugDataFieldsType convertNdc(NdcVo ndc) {

        DataFields<DataField> ndcDF = ndc.getVaDataFields();

       

        // ndc id
        DrugDataFieldsType ndcFields = fACTORY.createDrugDataFieldsType();
        DrugRequestIdentifier drugReq = fACTORY.createDrugRequestIdentifier();
        String[] ndcParts = ndc.getNdc().split("-");
        drugReq.setNdc(new BigInteger(ndcParts[0] + ndcParts[1] + ndcParts[2]));
        ndcFields.setDrugRequestIdentifier(drugReq);

        ndcFields = convertNdc1(ndc, ndcFields, ndcDF);
        ndcFields = convertNdc2(ndc, ndcFields);
        
        return ndcFields;
    }
     
    /**
     * convertNdc1
     * @param ndc ndc
     * @param ndcFields ndcFields
     * @param ndcDF ndcDF
     * @return DrugDataFieldsType
     */
    private static DrugDataFieldsType convertNdc1(NdcVo ndc, DrugDataFieldsType ndcFields,  DataFields<DataField> ndcDF) {

        //
        // boolean fields
        //

        // Mark For Local Use
        addBooleanField(ndcFields, ndc.isLocalUse(), BooleanFieldNameType.MARK_FOR_LOCAL_USE);

        // Protect From Light
        addBooleanField(ndcFields, ndcDF, FieldKey.PROTECT_FROM_LIGHT, BooleanFieldNameType.PROTECT_FROM_LIGHT);

        //
        // string fields
        //

        // NDC Item Inactivation Date
        addStringField(ndcFields, ndcDF, FieldKey.INACTIVATION_DATE, StringFieldNameType.NDC_ITEM_INACTIVATION_DATE);

        // NDC
        addStringField(ndcFields, ndc.getNdc(), StringFieldNameType.NDC);

        // Reject Reason Text
        addStringField(ndcFields, ndc.getRejectionReasonText(), StringFieldNameType.REJECT_REASON_TEXT);

        // Trade Name
        addStringField(ndcFields, ndc.getTradeName(), StringFieldNameType.TRADE_NAME);

        // UPC/UPN
        addStringField(ndcFields, ndc.getUpcUpn(), StringFieldNameType.UPC_UPN);

        // Vendor
        addStringField(ndcFields, ndcDF, FieldKey.VENDOR, StringFieldNameType.VENDOR);

        // VSN
        addStringField(ndcFields, ndcDF, FieldKey.VENDOR_STOCK_NUMBER, StringFieldNameType.VSN);

        // Imprint
        //     addStringField(ndcFields, ndc.getImprint(), StringFieldNameType.IMPRINT);

        // Imprint2
        //     addStringField(ndcFields, ndc.getImprint2(), StringFieldNameType.IMPRINT2);

        // Proposed Inactivation Date
        addStringField(ndcFields, ndcDF, FieldKey.PROPOSED_INACTIVATION_DATE, StringFieldNameType.PROPOSED_INACTIVATION_DATE);

        // Previous NDC
        addStringField(ndcFields, ndcDF, FieldKey.PREVIOUS_NDC, StringFieldNameType.PREVIOUS_NDC);

        // Previous UPN
        addStringField(ndcFields, ndcDF, FieldKey.PREVIOUS_UPC_UPN, StringFieldNameType.PREVIOUS_UPC_UPN);

        // Source
        addStringField(ndcFields, ndc.getSource().toString(), StringFieldNameType.SOURCE);

        // Package Size
        if (isValid(ndc.getPackageSize())) {
            addDoubleField(ndcFields, ndc.getPackageSize(), DoubleFieldNameType.PACKAGE_SIZE);
        }

        //
        // double fields
        //

        // NDC Price Per Dispense Unit
        addDoubleField(ndcFields, ndcDF, FieldKey.NDC_PRICE_PER_DISPENSE_UNIT,
            DoubleFieldNameType.NDC_PRICE_PER_DISPENSE_UNIT);

        // NDC Price Per Order Unit
        addDoubleField(ndcFields, ndcDF, FieldKey.NDC_PRICE_PER_ORDER_UNIT, DoubleFieldNameType.NDC_PRICE_PER_ORDER_UNIT);

        // Unit Price
        addDoubleField(ndcFields, ndcDF, FieldKey.UNIT_PRICE, DoubleFieldNameType.UNIT_PRICE);

        // NDC Dispense Units Per Order Unit
        addDoubleField(ndcFields, ndc.getNdcDispUnitsPerOrdUnit(), DoubleFieldNameType.NDC_DISPENSE_UNITS_PER_ORDER_UNIT);

       
        //
        // list fields
        //

        // Application Package Use
        addListField(ndcFields, ndcDF, FieldKey.APPLICATION_PACKAGE_USE, ListFieldNameType.APPLICATION_PACKAGE_USE);

        // OTC/RX Indicator
        if (isValid(ndc.getOtcRxIndicator())) {
            addListField(ndcFields, ndc.getOtcRxIndicator().getValue(), ListFieldNameType.OTC_RX_INDICATOR);
        }

        // Request Status
        // Request Status is deprecated

        // Scored
        addListField(ndcFields, ndcDF, FieldKey.SCORED, ListFieldNameType.SCORED);

        // Product Type
//        addListField(ndcFields, ndcDF, FieldKey.CATEGORIES, ListFieldNameType.CATEGORIES);
//
        // Refrigeration
        addListField(ndcFields, ndcDF, FieldKey.REFRIGERATION, ListFieldNameType.REFRIGERATION);

        // Shape
        if (isValid(ndc.getShape())) {
            addListField(ndcFields, ndc.getShape().getValue(), ListFieldNameType.SHAPE);
        }

        // Color
        if (isValid(ndc.getColor())) {
            addListField(ndcFields, ndc.getColor().getValue(), ListFieldNameType.COLOR);
        }

        // Request Rejection Reason
        addListField(ndcFields, ndcDF, FieldKey.REQUEST_REJECTION_REASON, ListFieldNameType.REQUEST_REJECTION_REASON);

        // Item Status
        if (isValid(ndc.getItemStatus())) {
            addListField(ndcFields, ndc.getItemStatus().toString(), ListFieldNameType.ITEM_STATUS);
        }

        return ndcFields;
    }
        
    /**
     * convertNdc2
     * @param ndc ndc
     * @param ndcFields ndcFields
     * @return DrugDataFieldsType
     */
    private static DrugDataFieldsType convertNdc2(NdcVo ndc, DrugDataFieldsType ndcFields) {
      
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        
        // Manufacturer
        if (ndc.getManufacturer() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            if (ndc.getManufacturer().getAddress() != null) {
                items.put(ComplexItemNameType.ADDRESS.value(), ndc.getManufacturer().getAddress());
            }

            if (ndc.getManufacturer().getInactivationDate() != null) {
                items.put(ComplexItemNameType.INACTIVATION_DATE.value(), dateFormat.format(ndc.getManufacturer()
                    .getInactivationDate()));
            }

            if (ndc.getManufacturer().getValue() != null) {
                items.put(ComplexItemNameType.MANUFACTURER_NAME.value(), ndc.getManufacturer().getValue());
            }

            if (ndc.getManufacturer().getPhone() != null) {
                items.put(ComplexItemNameType.PHONE_NUMBER.value(), ndc.getManufacturer().getPhone());
            }

            ndcFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createComplexField(ComplexFieldNameType.MANUFACTURER, items));
        }

        // Order Unit
        if (ndc.getOrderUnit() != null && ndc.getOrderUnit().getValue() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            if (ndc.getOrderUnit().getAbbrev() != null) {
                items.put(ComplexItemNameType.ORDER_UNIT_ABBREVIATION.value(), ndc.getOrderUnit().getAbbrev());
            }

            if (ndc.getOrderUnit().getExpansion() != null) {
                items.put(ComplexItemNameType.ORDER_UNIT_EXPANSION.value(), ndc.getOrderUnit().getExpansion());
            }

            if (ndc.getOrderUnit().getInactivationDate() != null) {
                items.put(ComplexItemNameType.INACTIVATION_DATE.value(), dateFormat.format(ndc.getOrderUnit()
                    .getInactivationDate()));
            }

            ndcFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createComplexField(ComplexFieldNameType.ORDER_UNIT, items));
        }

        // Package Type
        if (ndc.getPackageType() != null && ndc.getPackageType().getValue() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            if (ndc.getPackageType().getValue() != null) {
                items.put(ComplexItemNameType.PACKAGE_TYPE_NAME.value(), ndc.getPackageType().getValue());
            }

            if (ndc.getPackageType().getInactivationDate() != null) {
                items.put(ComplexItemNameType.INACTIVATION_DATE.value(), dateFormat.format(ndc.getPackageType()
                    .getInactivationDate()));
            }

            ndcFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createComplexField(ComplexFieldNameType.PACKAGE_TYPE, items));
        }

        return ndcFields;
    }
}
