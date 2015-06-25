/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.drug.data;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.NumberFormatUtility;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.AtcCanisterVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayFinishingAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.LabDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.common.vo.LocalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.NationalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayAdministrationVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayFinishAnOrderVo;
import gov.va.med.pharmacy.peps.common.vo.VitalsDisplayOrderEntryVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.BooleanFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ComplexItemNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DoubleFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugDataFieldsType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.DrugRequestIdentifier;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.IntegerFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.ListFieldNameType;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.data.response.StringFieldNameType;


/**
 * Convert the ProductVo type to JAXB generated objects for XML serialization. Some data is pulled from the productVo
 * directly while other data is pulled from the product data fields.
 */
public class ProductDataFieldTypeConverter extends DataFieldTypeConverter {

    /**
     * Private constructor
     */
    private ProductDataFieldTypeConverter() {
        super();
    }

    /**
     * Convert the ProductVo type to JAXB generated objects for XML serialization. Some data is pulled from the productVo
     * directly while other data is pulled from the product data fields.
     * 
     * @param product the productVo to convert
     * @return DrugDataFieldsType high level XML element from JAXB generated object
     */
    public static DrugDataFieldsType convertProduct(ProductVo product) {

        DataFields<DataField> productDF = product.getVaDataFields();


        // product vuid
        DrugDataFieldsType productFields = fACTORY.createDrugDataFieldsType();
        DrugRequestIdentifier drugReq = fACTORY.createDrugRequestIdentifier();
        drugReq.setVuid(new BigInteger(product.getVuid()));
        productFields.setDrugRequestIdentifier(drugReq);

        productFields = convertProduct1(productFields, productDF, product);
        productFields = convertProduct2(productFields, productDF, product);
        productFields = convertProduct3(productFields, productDF, product);
        productFields = convertProduct4(productFields, productDF, product);
        productFields = convertProduct5(productFields, productDF, product);
        productFields = convertProduct6(productFields, productDF, product);
        
        return productFields;
    }
    
    /**
     * convertProduct1
     * @param productFields productFields
     * @param productDF productDF
     * @param product product
     * @return DrugDataFieldsType DrugDataFieldsType
     */
    private static DrugDataFieldsType convertProduct1(DrugDataFieldsType productFields,  DataFields<DataField> productDF,
        ProductVo product) {
        
        //
        // Boolean fields
        //

        // Transmit to CMOP (Local)
        addBooleanField(productFields, productDF, FieldKey.TRANSMIT_TO_CMOP, BooleanFieldNameType.TRANSMIT_TO_CMOP_LOCAL);

        // Do Not Mail
        addBooleanField(productFields, productDF, FieldKey.DO_NOT_MAIL, BooleanFieldNameType.DO_NOT_MAIL);

        // High-Risk Follow-Up
        addBooleanField(productFields, productDF, FieldKey.HIGH_RISK_FOLLOWUP, BooleanFieldNameType.HIGH_RISK_FOLLOW_UP);

        // High-Risk Med
        addBooleanField(productFields, productDF, FieldKey.HIGH_RISK, BooleanFieldNameType.HIGH_RISK_MED);

        // Local Non-Formulary
        addBooleanField(productFields, productDF, FieldKey.LOCAL_NON_FORMULARY, BooleanFieldNameType.LOCAL_NON_FORMULARY);

        // Mark For Local Use
        addBooleanField(productFields, product.isLocalUse(), BooleanFieldNameType.MARK_FOR_LOCAL_USE);

        // Med Controlled Substance
        // addBooleanField(productFields, productDF, FieldKey.MED_CONTROLLED_SUBSTANCE,
        // BooleanFieldNameType.MED_CONTROLLED_SUBSTANCE);

        // National Formulary Indicator
        addBooleanField(productFields, product.getNationalFormularyIndicator(),
                        BooleanFieldNameType.NATIONAL_FORMULARY_INDICATOR);

        // OP External Dispense
        addBooleanField(productFields, productDF, FieldKey.OP_EXTERNAL_DISPENSE, BooleanFieldNameType.OP_EXTERNAL_DISPENSE);

        // CMOP Dispense (National)
        addBooleanField(productFields, productDF, FieldKey.CMOP_DISPENSE, BooleanFieldNameType.CMOP_DISPENSE_NATIONAL);

        // Witness Required
        addBooleanField(productFields, productDF, FieldKey.WITNESS_TO_ADMINISTRATION, BooleanFieldNameType.WITNESS_REQUIRED);

        // Patient Specific Label
        addBooleanField(productFields, productDF, FieldKey.PATIENT_SPECIFIC_LABEL, BooleanFieldNameType.PATIENT_SPECIFIC_LABEL);

        // Protect From Light
        addBooleanField(productFields, productDF, FieldKey.PROTECT_FROM_LIGHT, BooleanFieldNameType.PROTECT_FROM_LIGHT);

        // Do Not Handle if Pregnant
        addBooleanField(productFields, productDF, FieldKey.DO_NOT_HANDLE_IF_PREGNANT,
                        BooleanFieldNameType.DO_NOT_HANDLE_IF_PREGNANT);

        // Follow-Up Time
        addBooleanField(productFields, productDF, FieldKey.FOLLOW_UP_TIME, BooleanFieldNameType.FOLLOW_UP_TIME);

        // Hazardous to Dispose
        addBooleanField(productFields, productDF, FieldKey.HAZARDOUS_TO_DISPOSE, BooleanFieldNameType.HAZARDOUS_TO_DISPOSE);

        // Hazardous to Handle
        addBooleanField(productFields, productDF, FieldKey.HAZARDOUS_TO_HANDLE, BooleanFieldNameType.HAZARDOUS_TO_HANDLE);

        // Hazardous to Patient
        addBooleanField(productFields, productDF, FieldKey.HAZARDOUS_TO_PATIENT, BooleanFieldNameType.HAZARDOUS_TO_PATIENT);

        // Long Term Out of Stock
        addBooleanField(productFields, productDF, FieldKey.LONG_TERM_OUT_OF_STOCK, BooleanFieldNameType.LONG_TERM_OUT_OF_STOCK);

        // Low Safety Margin
        addBooleanField(productFields, productDF, FieldKey.LOW_SAFETY_MARGIN, BooleanFieldNameType.LOW_SAFETY_MARGIN);

        // Non-Renewable
        addBooleanField(productFields, productDF, FieldKey.NON_RENEWABLE, BooleanFieldNameType.NON_RENEWABLE);

        //
        // Integer fields
        //

        // AR/WS Amis Conversion Number
        addIntegerField(productFields, productDF, FieldKey.AR_WS_CONVERSION_NUMBER,
                        IntegerFieldNameType.AR_WS_AMIS_CONVERSION_NUMBER);

        // Current Inventory
        addIntegerField(productFields, productDF, FieldKey.CURRENT_INVENTORY, IntegerFieldNameType.CURRENT_INVENTORY);

        // Dispense Days Supply Limit
        addIntegerField(productFields, productDF, FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT,
                        IntegerFieldNameType.DISPENSE_DAYS_SUPPLY_LIMIT);

        // GCNSEQNO
        addIntegerField(productFields, BigInteger.valueOf(new Long(product.getGcnSequenceNumber())),
                        IntegerFieldNameType.GCNSEQNO);

        // Maximum Dose Per Day
        addIntegerField(productFields, BigInteger.valueOf(product.getMaxDosePerDay()),
                        IntegerFieldNameType.MAXIMUM_DOSE_PER_DAY);

        // Monitor Max Days
        addIntegerField(productFields, productDF, FieldKey.MONITOR_MAX_DAYS, IntegerFieldNameType.MONITOR_MAX_DAYS);

        // Normal Amount To Order
        addIntegerField(productFields, productDF, FieldKey.NORMAL_AMOUNT_TO_ORDER, IntegerFieldNameType.NORMAL_AMOUNT_TO_ORDER);

        // Reorder Level
        addIntegerField(productFields, productDF, FieldKey.REORDER_LEVEL, IntegerFieldNameType.REORDER_LEVEL);

        //
        // Double fields
        //

        // Product Dispense Units Per Order Unit
        if (product.getDispenseUnit() != null) {
            addDoubleField(productFields, productDF, FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT,
                           DoubleFieldNameType.PRODUCT_DISPENSE_UNITS_PER_ORDER_UNIT);
        }

        // Product Price Per Dispense Unit
        addDoubleField(productFields, productDF, FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT,
                       DoubleFieldNameType.PRODUCT_PRICE_PER_DISPENSE_UNIT);

        // Product Price Per Order Unit
        addDoubleField(productFields, productDF, FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT,
                       DoubleFieldNameType.PRODUCT_PRICE_PER_ORDER_UNIT);

        
        return productFields;
    }
    
    /**
     * convertProduct2
     * @param productFields productFields
     * @param productDF productDF
     * @param product product
     * @return DrugDataFieldsType DrugDataFieldsType
     */
    private static DrugDataFieldsType convertProduct2(DrugDataFieldsType productFields,  DataFields<DataField> productDF,
        ProductVo product) {

        // Action Profile Message (OP)
        addStringField(productFields, productDF, FieldKey.ACTION_PROFILE_MESSAGE,
            StringFieldNameType.ACTION_PROFILE_MESSAGE_OP);

        // Day (ND) Or Dose (NL) Limit
        addStringField(productFields, productDF, FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, StringFieldNameType.DAY_ND_OR_DOSE_NL_LIMIT);

        // DEA Special Handling
        addStringField(productFields, productDF, FieldKey.SPECIAL_HANDLING, StringFieldNameType.DEA_SPECIAL_HANDLING);

        // Dispense Limit for Order
        addStringField(productFields, productDF, FieldKey.DISPENSE_LIMIT_FOR_ORDER,
                       StringFieldNameType.DISPENSE_LIMIT_FOR_ORDER);

        // Dispense Limit Schedule
        addStringField(productFields, productDF, FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE,
                       StringFieldNameType.DISPENSE_LIMIT_SCHEDULE);

        // Dispense Override
        addStringField(productFields, productDF, FieldKey.DISPENSE_OVERRIDE, StringFieldNameType.DISPENSE_OVERRIDE);

        // Dispense Override Reason
        addStringField(productFields, productDF, FieldKey.DISPENSE_OVERRIDE_REASON,
                       StringFieldNameType.DISPENSE_OVERRIDE_REASON);

        // Dispense Quantity Limit
        addStringField(productFields, productDF, FieldKey.DISPENSE_QUANTITY_LIMIT_TIME,
                       StringFieldNameType.DISPENSE_QUANTITY_LIMIT);

        // Dispense Quantity Override
        addStringField(productFields, productDF, FieldKey.DISPENSE_QUANTITY_OVERRIDE,
                       StringFieldNameType.DISPENSE_QUANTITY_OVERRIDE);

        // Dispense Quantity Override Reason
        addStringField(productFields, productDF, FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON,
                       StringFieldNameType.DISPENSE_QUANTITY_OVERRIDE_REASON);

        // FSN
        addStringField(productFields, productDF, FieldKey.FSN, StringFieldNameType.FSN);

        // High-Risk Follow-Up Time Period
        addStringField(productFields, productDF, FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD,
                       StringFieldNameType.HIGH_RISK_FOLLOW_UP_TIME_PERIOD);

        // IFCAP Item Number
        // Is this required to be a list?
        addStringField(productFields, productDF, FieldKey.IFCAP_ITEM_NUMBER, StringFieldNameType.IFCAP_ITEM_NUMBER);

        // Product Inactivation Date
        addStringField(productFields, productDF, FieldKey.INACTIVATION_DATE, StringFieldNameType.PRODUCT_INACTIVATION_DATE);

        // Inpatient Medication Expired Orders Max Time
        addStringField(productFields, productDF, FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME,
                       StringFieldNameType.INPATIENT_MEDICATION_EXPIRED_ORDERS_MAX_TIME);

        // Inpatient Medication Expired Orders Min Time
        addStringField(productFields, productDF, FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME,
                       StringFieldNameType.INPATIENT_MEDICATION_EXPIRED_ORDERS_MIN_TIME);

        // Max Dispense Limit
        addStringField(productFields, productDF, FieldKey.MAX_DISPENSE_LIMIT, StringFieldNameType.MAX_DISPENSE_LIMIT);

        // Message
        addStringField(productFields, productDF, FieldKey.MESSAGE, StringFieldNameType.MESSAGE);

        // Generic Name
        addStringField(productFields, product.getGenericName().getValue(), StringFieldNameType.GENERIC_NAME);

        // National Formulary Name
        addStringField(productFields, product.getNationalFormularyName(), StringFieldNameType.NATIONAL_FORMULARY_NAME);

        // Override Reason Enterer
        addStringField(productFields, productDF, FieldKey.OVERRIDE_REASON_ENTERER, StringFieldNameType.OVERRIDE_REASON_ENTERER);

        // Quantity Dispense Message
        addStringField(productFields, productDF, FieldKey.QUANTITY_DISPENSE_MESSAGE,
                       StringFieldNameType.QUANTITY_DISPENSE_MESSAGE);

        // Reject Reason Text
        addStringField(productFields, product.getRejectionReasonText(), StringFieldNameType.REJECT_REASON_TEXT);

        // Specimen Type
        // should this be list type?
        addStringField(productFields, productDF, FieldKey.SPECIMEN_TYPE, StringFieldNameType.SPECIMEN_TYPE);

        // Total Dispense Quantity
        addStringField(productFields, productDF, FieldKey.TOTAL_DISPENSE_QUANTITY, StringFieldNameType.TOTAL_DISPENSE_QUANTITY);

        // Unit Dose Schedule
        addStringField(productFields, productDF, FieldKey.UNIT_DOSE_SCHEDULE, StringFieldNameType.UNIT_DOSE_SCHEDULE);

        // VA Print Name
        addStringField(productFields, product.getVaPrintName(), StringFieldNameType.VA_PRINT_NAME);

        // VA Product Name
        addStringField(productFields, product.getVaProductName(), StringFieldNameType.VA_PRODUCT_NAME);

        // VUID
        addStringField(productFields, product.getVuid(), StringFieldNameType.VUID);
        
        // Primary EPA Code
        addStringField(productFields, productDF, FieldKey.PRIMARY_EPA_CODE,
                       StringFieldNameType.PRIMARY_EPA_CODE);
        
       
     // Waste Sort Code
        addStringField(productFields, productDF, FieldKey.WASTE_SORT_CODE,
                       StringFieldNameType.WASTE_SORT_CODE);
        
     // DOT Shipping Label
        addStringField(productFields, productDF, FieldKey.DOT_SHIPPING_NAME,
                       StringFieldNameType.DOT_SHIPPING_LABEL);

        // Default Mail Service
        addStringField(productFields, productDF, FieldKey.DEFAULT_MAIL_SERVICE, StringFieldNameType.DEFAULT_MAIL_SERVICE);

        // CMOP ID
        addStringField(productFields, productDF, FieldKey.CMOP_ID, StringFieldNameType.CMOP_ID);

        // Proposed Inactivation Date
        addStringField(productFields, productDF, FieldKey.PROPOSED_INACTIVATION_DATE,
                       StringFieldNameType.PROPOSED_INACTIVATION_DATE);
        
       
        // Tallman Lettering
        addStringField(productFields, product.getTallManLettering(), StringFieldNameType.TALLMAN_LETTERING);

        // Source - removed from Product
        //addStringField(productFields, product.getSource().toString(), StringFieldNameType.SOURCE);

        // List fields

        // Active Ingredients
        List<String> activeIngredients = new ArrayList<String>();

        for (ActiveIngredientVo ingredient : product.getActiveIngredients()) {
            activeIngredients.add(ingredient.getIngredient().getVuid());
        }

        addListField(productFields, activeIngredients, ListFieldNameType.ACTIVE_INGREDIENTS);

        return productFields;
    }
        
    /**
     * convertProduct3
     * @param productFields productFields
     * @param productDF productDF
     * @param product product
     * @return DrugDataFieldsType DrugDataFieldsType
     */
    private static DrugDataFieldsType convertProduct3(DrugDataFieldsType productFields,  DataFields<DataField> productDF,
        ProductVo product) {

        // Application Package Use
        addListField(productFields, productDF, FieldKey.APPLICATION_PACKAGE_USE, ListFieldNameType.APPLICATION_PACKAGE_USE);

        // Approved For Splitting
        addListField(productFields, productDF, FieldKey.APPROVED_FOR_SPLITTING, ListFieldNameType.APPROVED_FOR_SPLITTING);

        // AR/WS Amis Category
        addListField(productFields, productDF, FieldKey.AR_WS_AMIS_CATEGORY, ListFieldNameType.AR_WS_AMIS_CATEGORY);

        // Corresponding Inpatient Drug
        addListField(productFields, productDF, FieldKey.CORRESPONDING_INPATIENT_DRUG,
                     ListFieldNameType.CORRESPONDING_INPATIENT_DRUG);

        // Corresponding Outpatient Drug
        addListField(productFields, productDF, FieldKey.CORRESPONDING_OUTPATIENT_DRUG,
                     ListFieldNameType.CORRESPONDING_OUTPATIENT_DRUG);

        // CS Federal Schedule
        addListField(productFields, product.getCsFedSchedule().getValue(), ListFieldNameType.CS_FEDERAL_SCHEDULE);

        // DAW Code
        addListField(productFields, productDF, FieldKey.DAW_CODE, ListFieldNameType.DAW_CODE);

        // Formulary Alternative
        addListField(productFields, productDF, FieldKey.FORMULARY_ALTERNATIVE, ListFieldNameType.FORMULARY_ALTERNATIVE);

        addStringField(productFields, productDF, FieldKey.INPATIENT_PHARMACY_LOCATION,
                       StringFieldNameType.INPATIENT_PHARMACY_LOCATION);

        // Order
        addListField(productFields, productDF, FieldKey.ORDER_UNIT, ListFieldNameType.ORDER_UNIT);

        // Primary VA Drug Class
        addListField(productFields, product.getPrimaryDrugClass().getCode(), ListFieldNameType.PRIMARY_VA_DRUG_CLASS);

        // Request Status
        addListField(productFields, product.getRequestItemStatus().toString(), ListFieldNameType.REQUEST_STATUS);

        // Secondary VA Drug Class
        List<String> drugClasses = new ArrayList<String>();

        for (DrugClassGroupVo drugClassGroup : product.getDrugClasses()) {
            if (!drugClassGroup.getPrimary()) {
                drugClasses.add(drugClassGroup.getDrugClass().getValue());
            }
        }

        addListField(productFields, drugClasses, ListFieldNameType.SECONDARY_VA_DRUG_CLASS);

        // Single/Multi Source Product
        addListField(productFields, product.getSingleMultiSourceProduct().getValue(),
                     ListFieldNameType.SINGLE_MULTI_SOURCE_PRODUCT);

        // Product Type
        //     addListField(productFields, productDF, FieldKey.CATEGORIES, ListFieldNameType.CATEGORIES);

        // Unit Dose Schedule Type
        //   addListField(productFields, productDF, FieldKey.UNIT_DOSE_SCHEDULE_TYPE, ListFieldNameType.CATEGORIES);

        // VA Dispense Unit
        // how are dispense unit and va dispense unit different?
        addListField(productFields, product.getDispenseUnit().getValue(), ListFieldNameType.VA_DISPENSE_UNIT);

        // Refrigeration
        addListField(productFields, productDF, FieldKey.REFRIGERATION, ListFieldNameType.REFRIGERATION);

        // Recall Level
        addListField(productFields, productDF, FieldKey.RECALL_LEVEL, ListFieldNameType.RECALL_LEVEL);

        // DEA Schedule
        addListField(productFields, productDF, FieldKey.DEA_SCHEDULE, ListFieldNameType.DEA_SCHEDULE);

        // Formulary
        addListField(productFields, productDF, FieldKey.FORMULARY, ListFieldNameType.FORMULARY);

        // Request Rejection Reason
        addListField(productFields, productDF, FieldKey.REQUEST_REJECTION_REASON, ListFieldNameType.REQUEST_REJECTION_REASON);

        // Item Status
        addListField(productFields, product.getItemStatus().toString(), ListFieldNameType.ITEM_STATUS);

        // Labs During Administration
        List<String> labsDuringAdministration = new ArrayList<String>();

        for (LabDisplayAdministrationVo lab : product.getOrderableItem().getLabDisplayAdministration()) {
            labsDuringAdministration.add(lab.getValue());
        }

        addListField(productFields, labsDuringAdministration, ListFieldNameType.LABS_DURING_ADMINISTRATION);

        // Labs During Finishing An Order
        List<String> labsDuringOrder = new ArrayList<String>();

        for (LabDisplayFinishingAnOrderVo lab : product.getOrderableItem().getLabDisplayFinishingAnOrder()) {
            labsDuringOrder.add(lab.getValue());
        }

        addListField(productFields, labsDuringOrder, ListFieldNameType.LABS_DURING_FINISHING_AN_ORDER);

        // Labs During Order Entry
        List<String> labsDuringOrderEntry = new ArrayList<String>();

        for (LabDisplayOrderEntryVo lab : product.getOrderableItem().getLabDisplayOrderEntry()) {
            labsDuringOrderEntry.add(lab.getValue());
        }

        addListField(productFields, labsDuringOrderEntry, ListFieldNameType.LABS_DURING_ORDER_ENTRY);

        // Vitals During Administration
        List<String> vitalsDuringAdministration = new ArrayList<String>();

        for (VitalsDisplayAdministrationVo lab : product.getOrderableItem().getVitalsDisplayAdministration()) {
            vitalsDuringAdministration.add(lab.getValue());
        }

        addListField(productFields, vitalsDuringAdministration, ListFieldNameType.VITALS_DURING_ADMINISTRATION);

        // Vitals During Finishing An Order
        List<String> vitalsDuringOrder = new ArrayList<String>();

        for (VitalsDisplayFinishAnOrderVo lab : product.getOrderableItem().getVitalsDisplayFinishAnOrder()) {
            vitalsDuringOrder.add(lab.getValue());
        }

        addListField(productFields, vitalsDuringOrder, ListFieldNameType.VITALS_DURING_FINISHING_AN_ORDER);

        // Vitals During Order Entry
        List<String> vitalsDuringOrderEntry = new ArrayList<String>();

        for (VitalsDisplayOrderEntryVo lab : product.getOrderableItem().getVitalsDisplayOrderEntry()) {
            vitalsDuringOrderEntry.add(lab.getValue());
        }

        addListField(productFields, vitalsDuringOrderEntry, ListFieldNameType.VITALS_DURING_ORDER_ENTRY);

        return productFields;
    }
    
    /**
     * convertProduct4
     * @param productFields productFields
     * @param productDF productDF
     * @param product product
     * @return DrugDataFieldsType DrugDataFieldsType
     */
    private static DrugDataFieldsType convertProduct4(DrugDataFieldsType productFields,  DataFields<DataField> productDF,
        ProductVo product) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        // Ingredients
        // is this supposed to be active ingredients?
        for (ActiveIngredientVo activeIngredient : product.getActiveIngredients()) {
            HashMap<String, String> items = new HashMap<String, String>();

            if (activeIngredient.getIngredient() != null) {
                if (activeIngredient.getIngredient().getInactivationDate() != null) {
                    items.put(ComplexItemNameType.INACTIVATION_DATE.value(),
                              dateFormat.format(activeIngredient.getIngredient().getInactivationDate()));
                }

                items.put(ComplexItemNameType.INGREDIENT_NAME.value(), activeIngredient.getIngredient().getValue());

                if (activeIngredient.getIngredient().getPrimaryIngredient() != null) {
                    items.put(ComplexItemNameType.PRIMARY_INGREDIENTS.value(), activeIngredient.getIngredient()
                            .getPrimaryIngredient().getValue());
                }

                if (activeIngredient.getIngredient().getVuid() != null) {
                    items.put(ComplexItemNameType.VUID.value(), activeIngredient.getIngredient().getVuid());
                }

                productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                    createComplexField(ComplexFieldNameType.INGREDIENTS, items));
            }
        }

        // Drug Class
        for (DrugClassGroupVo drugClassGroup : product.getDrugClasses()) {
            HashMap<String, String> items = new HashMap<String, String>();

            if (drugClassGroup.getDrugClass() != null) {
                DrugClassVo drugClass = drugClassGroup.getDrugClass();

                if (drugClass.getClassification() != null) {
                    items.put(ComplexItemNameType.CLASSIFICATION.value(), drugClass.getClassification());
                }

                if (drugClass.getCode() != null) {
                    items.put(ComplexItemNameType.CODE.value(), drugClass.getCode());
                }

                if (drugClass.getInactivationDate() != null) {
                    items
                        .put(ComplexItemNameType.INACTIVATION_DATE.value(), dateFormat.format(drugClass.getInactivationDate()));
                }

                if (drugClass.getParentDrugClass() != null) {
                    items.put(ComplexItemNameType.PARENT_CLASS.value(), drugClass.getParentDrugClass().getValue());
                }

                if (drugClass.getClassificationType() != null) {
                    items.put(ComplexItemNameType.CLASSIFICATION.value(), drugClass.getClassificationType().getValue());
                }

                if (drugClass.getVuid() != null) {
                    items.put(ComplexItemNameType.VUID.value(), drugClass.getVuid());
                }

                if (drugClass.getDescription() != null) {
                    items.put(ComplexItemNameType.DESCRIPTION.value(), drugClass.getDescription());
                }

                productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                    createComplexField(ComplexFieldNameType.DRUG_CLASS, items));
            }
        }

        // Dispense Unit
        if (product.getDispenseUnit() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            if (product.getDispenseUnit().getInactivationDate() != null) {
                items.put(ComplexItemNameType.INACTIVATION_DATE.value(),
                          dateFormat.format(product.getDispenseUnit().getInactivationDate()));
            }

            items.put(ComplexItemNameType.DISPENSE_UNIT_NAME.value(), product.getDispenseUnit().getValue());

            productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField()
                    .add(createComplexField(ComplexFieldNameType.DISPENSE_UNIT, items));
        }

        // Dose Units
        if (product.getDoseUnitVo() != null) {
            HashMap<String, String> items = new HashMap<String, String>();
            DoseUnitVo doseUnit = product.getDoseUnitVo();

            if (doseUnit.getInactivationDate() != null) {
                items.put(ComplexItemNameType.INACTIVATION_DATE.value(), dateFormat.format(doseUnit.getInactivationDate()));
            }

            if (doseUnit.getDoseUnitName() != null) {
                items.put(ComplexItemNameType.DOSE_UNIT_NAME.value(), doseUnit.getDoseUnitName());
            }

            if (doseUnit.getFdbDoseUnit() != null) {
                items.put(ComplexItemNameType.FIRST_DATA_BANK_DOSE_UNIT.value(), doseUnit.getFdbDoseUnit());
            }

            if (doseUnit.getDoseUnitSynonyms() != null) {
                String doseUnitSynonyms = new String();

                for (DoseUnitSynonymVo doseUnitSynonym : doseUnit.getDoseUnitSynonyms()) {
                    doseUnitSynonyms.concat(doseUnitSynonym.getDoseUnitSynonymName());
                }

                items.put(ComplexItemNameType.DOSE_UNIT_SYNONYM.value(), doseUnitSynonyms);
            }

            if (doseUnit.getReplacementDoseUnit() != null) {
                items.put(ComplexItemNameType.REPLACED_BY_VHA_STANDARD_TERMS.value(), doseUnit.getReplacementDoseUnit()
                        .getDoseUnitName());
            }

            productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                createComplexField(ComplexFieldNameType.DOSE_UNITS, items));
        }
        
        return productFields;
        
    }

    /**
     * convertProduct5
     * @param productFields productFields
     * @param productDF productDF
     * @param product product
     * @return DrugDataFieldsType DrugDataFieldsType
     */
    private static DrugDataFieldsType convertProduct5(DrugDataFieldsType productFields,  DataFields<DataField> productDF,
        ProductVo product) {
    
        // Multiple Expandable Complex fields

        // Synonym - Multiple
        if (product.getSynonyms() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            for (SynonymVo synonym : product.getSynonyms()) {
                if (synonym.getSynonymDispenseUnitPerOrderUnit() != null) {
                    items.put(ComplexItemNameType.DISPENSE_UNITS_PER_ORDER_UNIT.value(), synonym
                            .getSynonymDispenseUnitPerOrderUnit().toString());
                }

                if (synonym.getSynonymIntendedUse() != null) {
                    items.put(ComplexItemNameType.PRODUCT_INTENDED_USE.value(), synonym.getSynonymIntendedUse().getValue());
                }

                if (synonym.getSynonymName() != null) {
                    items.put(ComplexItemNameType.SYNONYM_NAME.value(), synonym.getSynonymName());
                }

                if (synonym.getSynonymOrderUnit() != null) {
                    items.put(ComplexItemNameType.ORDER_UNIT.value(), synonym.getSynonymOrderUnit().getValue());
                }

                if (synonym.getSynonymVendor() != null) {
                    items.put(ComplexItemNameType.VENDOR.value(), synonym.getSynonymVendor());
                }

                if (synonym.getSynonymVsn() != null) {
                    items.put(ComplexItemNameType.VSN.value(), synonym.getSynonymVsn());
                }

                if (synonym.getSynonymPricePerOrderUnit() != null) {
                    items.put(ComplexItemNameType.PRICE_PER_ORDER_UNIT.value(), synonym.getSynonymPricePerOrderUnit()
                            .toString());
                }

                if (synonym.getNdcCode() != null) {
                    items.put(ComplexItemNameType.NDC_CODE.value(), synonym.getNdcCode());
                }

                if (synonym.getSynonymPricePerDispenseUnit() != null) {
                    items.put(ComplexItemNameType.PRICE_PER_DISPENSE_UNIT.value(), synonym.getSynonymPricePerDispenseUnit()
                            .toString());
                }

                productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                    createComplexField(ComplexFieldNameType.SYNONYM, items));
            }
        }
        
        return productFields;
    }


    /**
     * convertProduct6
     * @param productFields productFields
     * @param productDF productDF
     * @param product product
     * @return DrugDataFieldsType DrugDataFieldsType
     */
    private static DrugDataFieldsType convertProduct6(DrugDataFieldsType productFields,  DataFields<DataField> productDF,
        ProductVo product) {
    
        // ATC Canisters
        if (product.getAtcCanisters() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            for (AtcCanisterVo atcCanister : product.getAtcCanisters()) {
                if (atcCanister.getAtcCanister() != null) {
                    items.put(ComplexItemNameType.ATC_CANISTER.value(), atcCanister.getAtcCanister().toString());
                }

                if (atcCanister.getWardGroupForAtc() != null) {
                    items.put(ComplexItemNameType.WARD_GROUP_FOR_ATC_CANISTERS.value(), atcCanister.getWardGroupForAtc()
                            .getValue());
                }

                productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                    createComplexField(ComplexFieldNameType.ATC_CANISTERS, items));
            }
        }

        // Possible Dosages
        if (product.getNationalPossibleDosages() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            for (NationalPossibleDosagesVo nationalDosage : product.getNationalPossibleDosages()) {
                if (nationalDosage.getBcmaUnitsPerDose() != null) {
                    items.put(ComplexItemNameType.BCMA_UNITS_PER_DOSE.value(), nationalDosage.getBcmaUnitsPerDose().toString());
                }

                if (nationalDosage.getPossibleDosagesDispenseUnitsPerDose() != null) {
                    items.put(ComplexItemNameType.DISPENSE_UNITS_PER_DOSE.value(), nationalDosage
                            .getPossibleDosagesDispenseUnitsPerDose().toString());
                }

                if (nationalDosage.getDose() != null) {
                    items.put(ComplexItemNameType.DOSE.value(), nationalDosage.getDose().toString());
                }

                if (nationalDosage.getPossibleDosagePackage() != null) {
                    String packages = "";

                    for (PossibleDosagesPackageVo packageVo : nationalDosage.getPossibleDosagePackage()) {
                        packages.concat(packageVo.getValue());
                    }

                    items.put(ComplexItemNameType.PACKAGE.value(), packages);
                }

                productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField().add(
                    createComplexField(ComplexFieldNameType.POSSIBLE_DOSAGES, items));
            }
        }

        // Local Possible Dosage - Multiple
        if (product.getLocalPossibleDosages() != null) {
            HashMap<String, String> items = new HashMap<String, String>();

            for (LocalPossibleDosagesVo localDosage : product.getLocalPossibleDosages()) {
                if (localDosage.getBcmaUnitsPerDose() != null) {
                    items.put(ComplexItemNameType.BCMA_UNITS_PER_DOSE.value(), localDosage.getBcmaUnitsPerDose().toString());
                }

                if (localDosage.getLocalPossibleDosage() != null) {
                    items.put(ComplexItemNameType.LOCAL_POSSIBLE_DOSAGE.value(), localDosage.getLocalPossibleDosage());
                }

                if (localDosage.getOtherLanguageDosageName() != null) {
                    items.put(ComplexItemNameType.OTHER_LANGUAGE_DOSAGE_NAME.value(), localDosage.getOtherLanguageDosageName()
                            .toString());
                }

                if (localDosage.getPossibleDosagePackage() != null) {
                    String packages = "";

                    for (PossibleDosagesPackageVo packageVo : localDosage.getPossibleDosagePackage()) {
                        packages.concat(packageVo.getValue());
                    }

                    items.put(ComplexItemNameType.PACKAGE.value(), packages);
                }

                if (localDosage.getDoseUnit() != null) {
                    items.put(ComplexItemNameType.DOSE_UNIT.value(), localDosage.getDoseUnit().getDoseUnitName());
                }

                items.put(ComplexItemNameType.NUMERIC_DOSE.value(), NumberFormatUtility.format(localDosage.getNumericDose()));

                productFields.getStringDataFieldOrBooleanDataFieldOrIntegerDataField()
                        .add(createComplexField(ComplexFieldNameType.LOCAL_POSSIBLE_DOSAGE, items));
            }
        }

        return productFields;
    }
}
