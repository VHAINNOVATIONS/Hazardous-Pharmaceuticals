/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.AtcCanisterVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IfcapItemNumberVo;
import gov.va.med.pharmacy.peps.common.vo.LocalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.NationalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.NdcByOutpatientSiteNdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultType;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.AtcCanisterFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.DrugFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.DrugTextEntryFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.FormularyAlternativeFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.IfcapItemNumberFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.LocalPossibleDosageFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.NdcByOutpatientSiteNdcopFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.NewWarningLabelListWarnFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.PossibleDosagesFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drug.SynonymFile;


/**
 * Converts a Product VO to a Drug File.
 */
public class DrugFileConverter extends AbstractConverter {

    /**
     * FIELDS
     */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.LOCAL_USE,
            FieldKey.LOCAL_PRINT_NAME,
            FieldKey.DEA_SCHEDULE,
            FieldKey.SPECIAL_HANDLING,
            FieldKey.FSN,

//                FieldKey.WARNING_LABEL, FieldKey.NATIONAL_WARNING_LABELS, FieldKey.LOCAL_WARNING_LABELS, FieldKey.SYNONYMS,
            FieldKey.LOCAL_DRUG_TEXTS, FieldKey.NATIONAL_DRUG_TEXTS, FieldKey.ORDER_UNIT,
            FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT, FieldKey.PRODUCT_PRICE_PER_DISPENSE_UNIT, FieldKey.REORDER_LEVEL,
            FieldKey.MONITOR_MAX_DAYS, FieldKey.NORMAL_AMOUNT_TO_ORDER, FieldKey.DISPENSE_UNIT,
            FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT, FieldKey.SPECIMEN_TYPE, FieldKey.MONITOR_ROUTINE,
            FieldKey.LAB_MONITOR_MARK, FieldKey.OP_EXTERNAL_DISPENSE, FieldKey.NATIONAL_FORMULARY_INDICATOR,
            FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, FieldKey.ACTION_PROFILE_MESSAGE, FieldKey.CURRENT_INVENTORY,
            FieldKey.LOCAL_NON_FORMULARY, FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, FieldKey.UNIT_DOSE_SCHEDULE_TYPE,
            FieldKey.UNIT_DOSE_SCHEDULE, FieldKey.CORRESPONDING_OUTPATIENT_DRUG, FieldKey.APPLICATION_PACKAGE_USE,
            FieldKey.FORMULARY_ALTERNATIVE, FieldKey.DAW_CODE, FieldKey.NCPDP_DISPENSE_UNIT,
            FieldKey.NDCDP_QUANTITY_MULTIPLIER, FieldKey.INACTIVATION_DATE, FieldKey.MESSAGE, FieldKey.ATC_CANISTERS,
            FieldKey.ATC_MNEMONIC, FieldKey.TRANSMIT_TO_CMOP, FieldKey.QUANTITY_DISPENSE_MESSAGE,
            FieldKey.INPATIENT_PHARMACY_LOCATION, FieldKey.AR_WS_AMIS_CATEGORY, FieldKey.AR_WS_CONVERSION_NUMBER,
            FieldKey.IFCAP_ITEM_NUMBER, FieldKey.ACTIVE_INGREDIENT, FieldKey.NATIONAL_POSSIBLE_DOSAGES,
            FieldKey.LOCAL_POSSIBLE_DOSAGE, FieldKey.CORRESPONDING_INPATIENT_DRUG, FieldKey.VUID })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DrugFileConverter() {
    }

    /**
     * Convert a Product VO to a Drug File.
     * 
     * @param productVo Product VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Drug File
     */
    public static DrugFile toDrugFile(ProductVo productVo, Map<FieldKey, Difference> differences, ItemAction itemAction) {

        DataFields<DataField> dataFields = productVo.getVaDataFields();

        DrugFile drugFile = FACTORY.createDrugFile();
        drugFile.setCandidateKey(FACTORY.createDrugFileCandidateKey());
        drugFile.setNumber(new Float("50f)"));

        // GENERIC NAME M/M - Candidate Key
        drugFile.getCandidateKey().setGenericName(FACTORY.createGenericNameKey());
        drugFile.getCandidateKey().getGenericName()
            .setValue((String) toCandidateKeyValue(FieldKey.LOCAL_PRINT_NAME, differences, productVo.getLocalPrintName()));
        drugFile.getCandidateKey().getGenericName().setNumber(PPSConstants.F0POINT01);

        // GENERIC NAME M/M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.LOCAL_PRINT_NAME, differences)) {
            drugFile.setGenericName(FACTORY.createGenericNameKey());
            drugFile.getGenericName().setValue(productVo.getLocalPrintName());
            drugFile.getGenericName().setNumber(PPSConstants.F0POINT01);
        }

        // VA CLASSIFICATION O/O
        if (isValid(productVo.getPrimaryDrugClass()) || isUnset(FieldKey.PRIMARY_DRUG_CLASS, differences)) {
            DrugFile.VaClassification field = FACTORY.createDrugFileVaClassification();
            field.setNumber(2f);

            JAXBElement<DrugFile.VaClassification> element = FACTORY.createDrugFileVaClassification(field);
            drugFile.setVaClassification(element);

            if (isUnset(FieldKey.PRIMARY_DRUG_CLASS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(productVo.getPrimaryDrugClass().getCode());
            }
        }

        // PHARMACY ORDERABLE ITEM M/O
        drugFile.setPharmacyOrderableItem(FACTORY.createDrugFilePharmacyOrderableItem());
        drugFile.getPharmacyOrderableItem().setValue(
            productVo.getOrderableItem().getVistaOiName() + '|'
                + productVo.getOrderableItem().getDosageForm().getDosageFormName());
        drugFile.getPharmacyOrderableItem().setNumber(new Float("2.1"));

        // DEA, SPECIAL HDLG O/O
        ListDataField<String> deaSchedule = dataFields.getDataField(FieldKey.DEA_SCHEDULE); // mandatory

        if (isValid(deaSchedule) || isUnset(FieldKey.DEA_SCHEDULE, differences)
            || isUnset(FieldKey.SPECIAL_HANDLINGS, differences)) {

            DrugFile.DeaSpecialHdlg field = FACTORY.createDrugFileDeaSpecialHdlg();
            field.setNumber(new Float("3"));

            JAXBElement<DrugFile.DeaSpecialHdlg> element = FACTORY.createDrugFileDeaSpecialHdlg(field);
            drugFile.setDeaSpecialHdlg(element);

            if (isUnset(FieldKey.DEA_SCHEDULE, differences)) { // unset
                element.setNil(true);
            } else { // set
                StringBuilder deaSpecialHandling = new StringBuilder();

                for (String value : deaSchedule.getValue()) { // mandatory
                    deaSpecialHandling.append(toString(VALUE_DASH_PATTERN, value));
                }

                ListDataField<SpecialHandlingVo> spHandls = dataFields.getDataField(FieldKey.SPECIAL_HANDLINGS);

                if (isValid(spHandls)) { // optional
                    for (SpecialHandlingVo value : spHandls.getValue()) {
                        deaSpecialHandling.append(value.getCode());
                    }
                }

                field.setValue(deaSpecialHandling.toString());
            }
        }

        // MAXIMUM DOSE PER DAY O/O
        if (isValid(productVo.getMaxDosePerDay()) || isUnset(FieldKey.MAX_DOSE_PER_DAY, differences)) {
            DrugFile.MaximumDosePerDay field = FACTORY.createDrugFileMaximumDosePerDay();
            field.setNumber(new Float("4"));

            JAXBElement<DrugFile.MaximumDosePerDay> element = FACTORY.createDrugFileMaximumDosePerDay(field);
            drugFile.setMaximumDosePerDay(element);

            if (isUnset(FieldKey.MAX_DOSE_PER_DAY, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(productVo.getMaxDosePerDay()));
            }
        }

        // FSN O/O
        DataField<String> fsnField = dataFields.getDataField(FieldKey.FSN);

        if (isValid(fsnField) || isUnset(FieldKey.FSN, differences)) {
            DrugFile.Fsn field = FACTORY.createDrugFileFsn();
            field.setNumber(new Float("6f"));

            JAXBElement<DrugFile.Fsn> element = FACTORY.createDrugFileFsn(field);
            drugFile.setFsn(element);

            if (isUnset(FieldKey.FSN, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(fsnField.getValue());
            }
        }

        // WARNING LABEL O/O
//        DataField<String> warningLabel = dataFields.getDataField(FieldKey.WARNING_LABEL);
//
//        if (isValid(warningLabel) || isUnset(FieldKey.WARNING_LABEL, differences)) {
//            DrugFile.WarningLabel field = FACTORY.createDrugFileWarningLabel();
//            field.setNumber(8f);
//
//            JAXBElement<DrugFile.WarningLabel> element = FACTORY.createDrugFileWarningLabel(field);
//            drugFile.setWarningLabel(element);
//
//            if (isUnset(FieldKey.WARNING_LABEL, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(warningLabel.getValue());
//            }
//        }

        // NEW WARNING LABEL LIST WARN is updated and is now a multiple.
        // NEW WARNING LABEL LIST WARN (Multiple)
        // ORDER M/O
        // TYPE M/O
        // TEXT M/O
//        ArrayList<RxConsultVo> rxConsultVos = new ArrayList<RxConsultVo>();
//        rxConsultVos.addAll(productVo.getNationalWarningLabels());
//        rxConsultVos.addAll(productVo.getLocalWarningLabels());
//
//        if (isValid(rxConsultVos) || isUnset(FieldKey.NATIONAL_WARNING_LABELS, differences)
//            || isUnset(FieldKey.LOCAL_WARNING_LABELS, differences)) {
//            DrugFile.NewWarningLabelListWarn field = FACTORY.createDrugFileNewWarningLabelListWarn();
//            field.setMultiple(true);
//            field.setNumber(8.1f);
//
//            JAXBElement<DrugFile.NewWarningLabelListWarn> element = FACTORY.createDrugFileNewWarningLabelListWarn(field);
//            drugFile.setNewWarningLabelListWarn(element);
//
//            if (rxConsultVos.isEmpty()) { // unset
//                element.setNil(true);
//            }
//            else { // set
//                int i = 1;
//
//                for (RxConsultVo rxConsultVo : rxConsultVos) {
//                    field.getNewWarningLabelListWarnFile().add(
//                        toNewWarningLabelListWarnFile(rxConsultVo, i, differences, itemAction));
//
//                    i++;
//                }
//            }
//        }

        // SYNONYM (Multiple)
        // SYNONYM M/O
        // INTENDED USE O/O
        // NDC CODE O/O
        // VSN O/O
        // ORDER UNIT O/O
        // PRICE PER ORDER UNIT O/O
        // DISPENSE UNITS PER ORDER UNIT O/O
        // PRICE PER DISPENSE UNIT O/O
        // VENDOR O/O
        if (isValid(productVo.getSynonyms()) || isUnset(FieldKey.SYNONYMS, differences)) {
            DrugFile.Synonym field = FACTORY.createDrugFileSynonym();
            field.setMultiple(true);
            field.setNumber(new Float("9"));

            JAXBElement<DrugFile.Synonym> element = FACTORY.createDrugFileSynonym(field);
            drugFile.setSynonym(element);

            if (isUnset(FieldKey.SYNONYMS, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (SynonymVo synonymVo : productVo.getSynonyms()) {
                    field.getSynonymFile().add(toSynonymFile(synonymVo, differences, itemAction));
                }
            }
        }

        // REORDER LEVEL O/O
        DataField<Long> reorderLevelField = dataFields.getDataField(FieldKey.REORDER_LEVEL);

        if (isValid(reorderLevelField) || isUnset(FieldKey.REORDER_LEVEL, differences)) {
            DrugFile.ReorderLevel field = FACTORY.createDrugFileReorderLevel();
            field.setNumber(new Float("11"));

            JAXBElement<DrugFile.ReorderLevel> element = FACTORY.createDrugFileReorderLevel(field);
            drugFile.setReorderLevel(element);

            if (isUnset(FieldKey.REORDER_LEVEL, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(reorderLevelField.getValue()));
            }
        }

        // ORDER UNIT O/O
        if (isValid(productVo.getOrderUnit()) || isUnset(FieldKey.ORDER_UNIT, differences)) {
            DrugFile.OrderUnit field = FACTORY.createDrugFileOrderUnit();
            field.setNumber(new Float("12"));

            JAXBElement<DrugFile.OrderUnit> element = FACTORY.createDrugFileOrderUnit(field);
            drugFile.setOrderUnit(element);

            if (isUnset(FieldKey.ORDER_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(productVo.getOrderUnit().getValue());
            }
        }

        // PRICE PER ORDER UNIT O/O
        DataField<Double> pricePerOrderUnitField = dataFields.getDataField(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT);

        if (isValid(pricePerOrderUnitField) || isUnset(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT, differences)) {
            DrugFile.PricePerOrderUnit field = FACTORY.createDrugFilePricePerOrderUnit();
            field.setNumber(new Float("13"));

            JAXBElement<DrugFile.PricePerOrderUnit> element = FACTORY.createDrugFilePricePerOrderUnit(field);
            drugFile.setPricePerOrderUnit(element);

            if (isUnset(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pricePerOrderUnitField.getValue().floatValue());
            }
        }

        // NORMAL AMOUNT TO ORDER O/O
        DataField<Long> normalAmountToOrderField = dataFields.getDataField(FieldKey.NORMAL_AMOUNT_TO_ORDER);

        if (isValid(normalAmountToOrderField) || isUnset(FieldKey.NORMAL_AMOUNT_TO_ORDER, differences)) {
            DrugFile.NormalAmountToOrder field = FACTORY.createDrugFileNormalAmountToOrder();
            field.setNumber(new Float("14"));

            JAXBElement<DrugFile.NormalAmountToOrder> element = FACTORY.createDrugFileNormalAmountToOrder(field);
            drugFile.setNormalAmountToOrder(element);

            if (isUnset(FieldKey.NORMAL_AMOUNT_TO_ORDER, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(normalAmountToOrderField.getValue()));
            }
        }

        // DISPENSE UNIT M/O
        drugFile.setDispenseUnit(FACTORY.createDrugFileDispenseUnit());
        drugFile.getDispenseUnit().setValue(productVo.getDispenseUnit().getValue());
        drugFile.getDispenseUnit().setNumber(new Float("14.5"));

        DataField<Double> dispenseUnitPerOrderUnitField =
            dataFields.getDataField(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

        // DISPENSE UNITS PER ORDER UNIT O/O
        if (isValid(dispenseUnitPerOrderUnitField) || isUnset(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT, differences)) {
            DrugFile.DispenseUnitsPerOrderUnit field = FACTORY.createDrugFileDispenseUnitsPerOrderUnit();
            field.setNumber(new Float("15"));

            JAXBElement<DrugFile.DispenseUnitsPerOrderUnit> element = FACTORY.createDrugFileDispenseUnitsPerOrderUnit(field);
            drugFile.setDispenseUnitsPerOrderUnit(element);

            if (isUnset(FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(dispenseUnitPerOrderUnitField.getValue().floatValue());
            }
        }

        // PRICE PER DISPENSE UNIT O/O
        DataField<Double> pricePerDispenseUnitField = dataFields.getDataField(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT);

        if (isValid(pricePerDispenseUnitField) || isUnset(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT, differences)) {
            DrugFile.PricePerDispenseUnit field = FACTORY.createDrugFilePricePerDispenseUnit();
            field.setNumber(new Float("16"));

            JAXBElement<DrugFile.PricePerDispenseUnit> element = FACTORY.createDrugFilePricePerDispenseUnit(field);
            drugFile.setPricePerDispenseUnit(element);

            if (isUnset(FieldKey.PRODUCT_PRICE_PER_ORDER_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pricePerDispenseUnitField.getValue().floatValue());
            }
        }

        // MONITOR MAX DAYS O/O
        DataField<Long> monitorMaxDaysField = dataFields.getDataField(FieldKey.MONITOR_MAX_DAYS);

        if (isValid(monitorMaxDaysField) || isUnset(FieldKey.MONITOR_MAX_DAYS, differences)) {
            DrugFile.MonitorMaxDays field = FACTORY.createDrugFileMonitorMaxDays();
            field.setNumber(new Float("17.3f"));

            JAXBElement<DrugFile.MonitorMaxDays> element = FACTORY.createDrugFileMonitorMaxDays(field);
            drugFile.setMonitorMaxDays(element);

            if (isUnset(FieldKey.MONITOR_MAX_DAYS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(monitorMaxDaysField.getValue()));
            }
        }

        // SPECIMEN TYPE O/O
        if (isValid(productVo.getSpecimenType()) || isUnset(FieldKey.SPECIMEN_TYPE, differences)) {
            DrugFile.SpecimenType field = FACTORY.createDrugFileSpecimenType();
            field.setNumber(new Float("17.4"));

            JAXBElement<DrugFile.SpecimenType> element = FACTORY.createDrugFileSpecimenType(field);
            drugFile.setSpecimenType(element);

            if (isUnset(FieldKey.SPECIMEN_TYPE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(productVo.getSpecimenType().getValue());
            }
        }

        // MONITOR ROUTINE O/O
        DataField<String> monitorRoutineField = dataFields.getDataField(FieldKey.MONITOR_ROUTINE);

        if (isValid(monitorRoutineField) || isUnset(FieldKey.MONITOR_ROUTINE, differences)) {
            DrugFile.MonitorRoutine field = FACTORY.createDrugFileMonitorRoutine();
            field.setNumber(new Float("17.5"));

            JAXBElement<DrugFile.MonitorRoutine> element = FACTORY.createDrugFileMonitorRoutine(field);
            drugFile.setMonitorRoutine(element);

            if (isUnset(FieldKey.MONITOR_ROUTINE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(monitorRoutineField.getValue());
            }
        }

        // LAB MONITOR MARK O/O
        DataField<Boolean> labMonitorMarkField = dataFields.getDataField(FieldKey.LAB_MONITOR_MARK);

        if (isValid(labMonitorMarkField) || isUnset(FieldKey.LAB_MONITOR_MARK, differences)) {
            DrugFile.LabMonitorMark field = FACTORY.createDrugFileLabMonitorMark();
            field.setNumber(new Float("17.6"));

            JAXBElement<DrugFile.LabMonitorMark> element = FACTORY.createDrugFileLabMonitorMark(field);
            drugFile.setLabMonitorMark(element);

            if (isUnset(FieldKey.LAB_MONITOR_MARK, differences) || !labMonitorMarkField.getValue().booleanValue()) { // unset
                element.setNil(true);
            } else { // set
                field.setValue("1");
            }
        }

        // OP EXTERNAL DISPENSE O/O
        DataField<Boolean> opExternalDispenseField = dataFields.getDataField(FieldKey.OP_EXTERNAL_DISPENSE);

        if (isValid(opExternalDispenseField) || isUnset(FieldKey.OP_EXTERNAL_DISPENSE, differences)) {
            DrugFile.OpExternalDispense field = FACTORY.createDrugFileOpExternalDispense();
            field.setNumber(new Float("28"));

            JAXBElement<DrugFile.OpExternalDispense> element = FACTORY.createDrugFileOpExternalDispense(field);
            drugFile.setOpExternalDispense(element);

            if (isUnset(FieldKey.OP_EXTERNAL_DISPENSE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(toBooleanOneOrZero(opExternalDispenseField.getValue().booleanValue()));
            }
        }

        // NATIONAL FORMULARY INDICATOR O/O
        if (isValid(productVo.getNationalFormularyIndicator()) || isUnset(FieldKey.NATIONAL_FORMULARY_INDICATOR, differences)) {
            DrugFile.NationalFormularyIndicator field = FACTORY.createDrugFileNationalFormularyIndicator();
            field.setNumber(new Float("29"));

            JAXBElement<DrugFile.NationalFormularyIndicator> element = FACTORY.createDrugFileNationalFormularyIndicator(field);
            drugFile.setNationalFormularyIndicator(element);

            if (isUnset(FieldKey.NATIONAL_FORMULARY_INDICATOR, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(toBooleanOneOrZero(productVo.getNationalFormularyIndicator()));
            }
        }

        // NDC BY OUTPATIENT SITE NDCOP (Multiple)
        // OUTPATIENT SITE M/O
        // LAST LOCAL NDC O/O
        // LAST CMOP NDC O/O
        if (isValid(productVo.getNdcByOutpatientSiteNdc()) || isUnset(FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, differences)) {
            DrugFile.NdcByOutpatientSiteNdcop field = FACTORY.createDrugFileNdcByOutpatientSiteNdcop();
            field.setMultiple(true);
            field.setNumber(new Float("32"));

            JAXBElement<DrugFile.NdcByOutpatientSiteNdcop> element = FACTORY.createDrugFileNdcByOutpatientSiteNdcop(field);
            drugFile.setNdcByOutpatientSiteNdcop(element);

            if (isUnset(FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (NdcByOutpatientSiteNdcVo ndcByOutpatientSiteNdcVo : productVo.getNdcByOutpatientSiteNdc()) {
                    field.getNdcByOutpatientSiteNdcopFile().add(
                        toNdcByOutpatientSiteNdcopFile(ndcByOutpatientSiteNdcVo, differences, itemAction));
                }
            }
        }

        // DRUG TEXT ENTRY (Multiple)
        // DRUG TEXT ENTRY M/O
        if (isValid(productVo.getLocalDrugTexts()) || isUnset(FieldKey.LOCAL_DRUG_TEXTS, differences)) {
            DrugFile.DrugTextEntry field = FACTORY.createDrugFileDrugTextEntry();
            field.setMultiple(true);
            field.setNumber(new Float("37"));

            JAXBElement<DrugFile.DrugTextEntry> element = FACTORY.createDrugFileDrugTextEntry(field);
            drugFile.setDrugTextEntry(element);

            if (isUnset(FieldKey.LOCAL_DRUG_TEXTS, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (DrugTextVo drugTextVo : productVo.getLocalDrugTexts()) {
                    field.getDrugTextEntryFile().add(toDrugTextEntryFile(drugTextVo, differences, itemAction));
                }
            }
        }

        // ACTION PROFILE MESSAGE (OP) O/O
        DataField<String> actionProfileMessageField = dataFields.getDataField(FieldKey.ACTION_PROFILE_MESSAGE);

        if (isValid(actionProfileMessageField) || isUnset(FieldKey.ACTION_PROFILE_MESSAGE, differences)) {
            DrugFile.ActionProfileMessage field = FACTORY.createDrugFileActionProfileMessage();
            field.setNumber(new Float("40"));

            JAXBElement<DrugFile.ActionProfileMessage> element = FACTORY.createDrugFileActionProfileMessage(field);
            drugFile.setActionProfileMessage(element);

            if (isUnset(FieldKey.ACTION_PROFILE_MESSAGE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(actionProfileMessageField.getValue());
            }
        }

        // CURRENT INVENTORY O/O
        DataField<Long> currentInventoryField = dataFields.getDataField(FieldKey.CURRENT_INVENTORY);

        if (isValid(currentInventoryField) || isUnset(FieldKey.CURRENT_INVENTORY, differences)) {
            DrugFile.CurrentInventory field = FACTORY.createDrugFileCurrentInventory();
            field.setNumber(new Float("50"));

            JAXBElement<DrugFile.CurrentInventory> element = FACTORY.createDrugFileCurrentInventory(field);
            drugFile.setCurrentInventory(element);

            if (isUnset(FieldKey.CURRENT_INVENTORY, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(currentInventoryField.getValue()));
            }
        }

        // LOCAL NON-FORMULARY O/O
        DataField<Boolean> localNonFormularyField = dataFields.getDataField(FieldKey.LOCAL_NON_FORMULARY);

        if (isValid(localNonFormularyField) || isUnset(FieldKey.LOCAL_NON_FORMULARY, differences)) {
            DrugFile.LocalNonFormulary field = FACTORY.createDrugFileLocalNonFormulary();
            field.setNumber(new Float("51"));

            JAXBElement<DrugFile.LocalNonFormulary> element = FACTORY.createDrugFileLocalNonFormulary(field);
            drugFile.setLocalNonFormulary(element);

            if (isUnset(FieldKey.LOCAL_NON_FORMULARY, differences) || !localNonFormularyField.getValue()) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(VISTA_TRUE);
            }
        }

        // DAY (nD) or DOSE (nL) LIMIT O/O
        DataField<String> dayOrDoseLimitField = dataFields.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);

        if (isValid(dayOrDoseLimitField) || isUnset(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, differences)) {
            DrugFile.DayOrDoseLimit field = FACTORY.createDrugFileDayOrDoseLimit();
            field.setNumber(new Float("62.01"));

            JAXBElement<DrugFile.DayOrDoseLimit> element = FACTORY.createDrugFileDayOrDoseLimit(field);
            drugFile.setDayOrDoseLimit(element);

            if (isUnset(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(dayOrDoseLimitField.getValue());
            }
        }

        // UNIT DOSE SCHEDULE TYPE O/O
        ListDataField<String> unitDoseScheduleTypeField = dataFields.getDataField(FieldKey.UNIT_DOSE_SCHEDULE_TYPE);

        if (isValid(unitDoseScheduleTypeField) || isUnset(FieldKey.UNIT_DOSE_SCHEDULE_TYPE, differences)) {
            DrugFile.UnitDoseScheduleType field = FACTORY.createDrugFileUnitDoseScheduleType();
            field.setNumber(new Float("62.03"));

            JAXBElement<DrugFile.UnitDoseScheduleType> element = FACTORY.createDrugFileUnitDoseScheduleType(field);
            drugFile.setUnitDoseScheduleType(element);

            if (isUnset(FieldKey.UNIT_DOSE_SCHEDULE_TYPE, differences) || unitDoseScheduleTypeField.isEmpty()) { // unset
                element.setNil(true);
            } else { // set
                String value =
                    unitDoseScheduleTypeField.getValue().get(0) == null ? "" : unitDoseScheduleTypeField.getValue().get(0)
                        .toUpperCase().trim();

                if (value.startsWith("R")) {
                    field.setValue("R");
                } else if (value.startsWith("OC")) {
                    field.setValue("OC");
                } else if (value.startsWith("O")) {
                    field.setValue("O");
                } else if (value.startsWith("P")) {
                    field.setValue("P");
                }
            }
        }

//        // UNIT DOSE SCHEDULE O/O
//        DataField<String> unitDoseScheduleField = dataFields.getDataField(FieldKey.UNIT_DOSE_SCHEDULE);
//
//        if (isValid(unitDoseScheduleField) || isUnset(FieldKey.UNIT_DOSE_SCHEDULE, differences)) {
//            DrugFile.UnitDoseSchedule field = FACTORY.createDrugFileUnitDoseSchedule();
//            field.setNumber(62.04f);
//
//            JAXBElement<DrugFile.UnitDoseSchedule> element = FACTORY.createDrugFileUnitDoseSchedule(field);
//            drugFile.setUnitDoseSchedule(element);
//
//            if (isUnset(FieldKey.UNIT_DOSE_SCHEDULE, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(unitDoseScheduleField.getValue());
//            }
//        }
//
//        // CORRESPONDING OUTPATIENT DRUG O/O
//        ListDataField<ProductVo> correspondingOutpatientDrugField =
//                dataFields.getDataField(FieldKey.CORRESPONDING_OUTPATIENT_DRUG);
//
//        if ((isValid(correspondingOutpatientDrugField) && (correspondingOutpatientDrugField.getValue().get(0)).isLocalUse())
//            || isUnset(FieldKey.CORRESPONDING_OUTPATIENT_DRUG, differences)) {
//            DrugFile.CorrespondingOutpatientDrug field = FACTORY.createDrugFileCorrespondingOutpatientDrug();
//            field.setNumber(62.05f);
//
//            JAXBElement<DrugFile.CorrespondingOutpatientDrug> element =
//                    FACTORY.createDrugFileCorrespondingOutpatientDrug(field);
//            drugFile.setCorrespondingOutpatientDrug(element);
//
//            if (isUnset(FieldKey.CORRESPONDING_OUTPATIENT_DRUG, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue((correspondingOutpatientDrugField.getValue().get(0)).getVuid());
//            }
//        }
//
//        // APPLICATION PACKAGES' USE O/O
//        ListDataField<String> applicationPackagesUseField = dataFields.getDataField(FieldKey.APPLICATION_PACKAGE_USE);
//
//        if (isValid(applicationPackagesUseField) || isUnset(FieldKey.APPLICATION_PACKAGE_USE, differences)) {
//            DrugFile.ApplicationPackagesUse field = FACTORY.createDrugFileApplicationPackagesUse();
//            field.setNumber(63f);
//
//            JAXBElement<DrugFile.ApplicationPackagesUse> element = FACTORY.createDrugFileApplicationPackagesUse(field);
//            drugFile.setApplicationPackagesUse(element);
//
//            if (isUnset(FieldKey.APPLICATION_PACKAGE_USE, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                StringBuilder vistaFormatted = new StringBuilder();
//
//                for (String value : applicationPackagesUseField.getValue()) {
//                    if ("W".equalsIgnoreCase(value)) { // ignore W - Ward Stock
//                        continue;
//                    }
//
//                    vistaFormatted.append(toString(VALUE_DASH_PATTERN, value));
//                }
//
//                field.setValue(vistaFormatted.toString());
//            }
//        }
//
//        // FORMULARY ALTERNATIVE (Multiple)
//        // FORMULARY ALTERNATIVE M/O
//        ListDataField<ProductVo> formularyAlternativeField = dataFields.getDataField(FieldKey.FORMULARY_ALTERNATIVE);
//
//        if (isValid(formularyAlternativeField)) { // clone and remove products not marked for local use
//            try {
//                formularyAlternativeField = (ListDataField<ProductVo>) formularyAlternativeField.clone();
//            } catch (CloneNotSupportedException e) {
//                throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
//            }
//
//            for (int i = formularyAlternativeField.getValue().size() - 1; i >= 0; i--) {
//                ProductVo value = formularyAlternativeField.getValue().get(i);
//
//                if (!value.isLocalUse()) {
//                    formularyAlternativeField.getValue().remove(i);
//                }
//            }
//        }
//
//        if (isValid(formularyAlternativeField) || isUnset(FieldKey.FORMULARY_ALTERNATIVE, differences)) {
//            DrugFile.FormularyAlternative field = FACTORY.createDrugFileFormularyAlternative();
//            field.setMultiple(true);
//            field.setNumber(65f);
//
//            JAXBElement<DrugFile.FormularyAlternative> element = FACTORY.createDrugFileFormularyAlternative(field);
//            drugFile.setFormularyAlternative(element);
//
//            if (isUnset(FieldKey.FORMULARY_ALTERNATIVE, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                boolean setFormulary = false;
//
//                for (ProductVo formularyDrug : formularyAlternativeField.getValue()) {
//
//                    if (formularyDrug.isLocalUse()) {
//                        field.getFormularyAlternativeFile().add(toFormularyAlternativeFile(formularyDrug, differences,
//                                                                                           itemAction));
//
//                        setFormulary = true;
//                    }
//                }
//
//                if (!setFormulary) {
//                    drugFile.setFormularyAlternative(null);
//                }
//            }
//        }
//
//        // DAW CODE O/O
//        ListDataField<String> dawCodeField = dataFields.getDataField(FieldKey.DAW_CODE);
//
//        if (isValid(dawCodeField) || isUnset(FieldKey.DAW_CODE, differences)) {
//            DrugFile.DawCode field = FACTORY.createDrugFileDawCode();
//            field.setNumber(81f);
//
//            JAXBElement<DrugFile.DawCode> element = FACTORY.createDrugFileDawCode(field);
//            drugFile.setDawCode(element);
//
//            if (isUnset(FieldKey.DAW_CODE, differences) || dawCodeField.isEmpty()) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(toString(VALUE_DASH_PATTERN, dawCodeField.getValue().get(0)));
//            }
//        }
//
//        // NCPDP DISPENSE UNIT M/O
//        ListDataField<String> ndcpdpDispenseUnitField = dataFields.getDataField(FieldKey.NCPDP_DISPENSE_UNIT);
//        drugFile.setNcpdpDispenseUnit(FACTORY.createDrugFileNcpdpDispenseUnit());
//        drugFile.getNcpdpDispenseUnit().setValue(toString(VALUE_DASH_PATTERN, ndcpdpDispenseUnitField.getValue().get(0)));
//        drugFile.getNcpdpDispenseUnit().setNumber(82f);
//
//        // NCPDP QUANTITY MULTIPLIER M/O
//        DataField<String> ncpdpQuantityMultiplierField = dataFields.getDataField(FieldKey.NDCDP_QUANTITY_MULTIPLIER);
//        drugFile.setNcpdpQuantityMultiplier(FACTORY.createDrugFileNcpdpQuantityMultiplier());
//        drugFile.getNcpdpQuantityMultiplier().setValue(Float.valueOf(ncpdpQuantityMultiplierField.getValue()));
//        drugFile.getNcpdpQuantityMultiplier().setNumber(83f);
//
//        // INACTIVE DATE O/M
//        Date inactiveDate = (Date) getNewValue(FieldKey.INACTIVATION_DATE, differences);
//
//        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactiveDate)
//            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {
//
//            DrugFile.InactiveDate field = FACTORY.createDrugFileInactiveDate();
//            field.setNumber(100f);
//
//            JAXBElement<DrugFile.InactiveDate> element = FACTORY.createDrugFileInactiveDate(field);
//            drugFile.setInactiveDate(element);
//
//            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(DateFormatter.toDateString(inactiveDate));
//            }
//        }
//
//        // MESSAGE O/O
//        DataField<String> messageField = dataFields.getDataField(FieldKey.MESSAGE);
//
//        if (isValid(messageField) || isUnset(FieldKey.MESSAGE, differences)) {
//            DrugFile.Message field = FACTORY.createDrugFileMessage();
//            field.setNumber(101f);
//
//            JAXBElement<DrugFile.Message> element = FACTORY.createDrugFileMessage(field);
//            drugFile.setMessage(element);
//
//            if (isUnset(FieldKey.MESSAGE, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(messageField.getValue());
//            }
//        }
//
//        // ATC CANISTER (Multiple)
//        // WARD GROUP FOR ATC CANISTER M/O
//        // ATC CANISTER O/O
//        if (isValid(productVo.getAtcCanisters()) || isUnset(FieldKey.ATC_CANISTERS, differences)) {
//            DrugFile.AtcCanister field = FACTORY.createDrugFileAtcCanister();
//            field.setMultiple(true);
//            field.setNumber(212f);
//
//            JAXBElement<DrugFile.AtcCanister> element = FACTORY.createDrugFileAtcCanister(field);
//            drugFile.setAtcCanister(element);
//
//            if (isUnset(FieldKey.ATC_CANISTERS, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                for (AtcCanisterVo atcCanisterVo : productVo.getAtcCanisters()) {
//                    field.getAtcCanisterFile().add(toAtcCanisterFile(atcCanisterVo, differences, itemAction));
//                }
//            }
//        }
//
//        // ATC MNEMONIC O/O
//        if (isValid(productVo.getAtcMnemonic()) || isUnset(FieldKey.ATC_MNEMONIC, differences)) {
//            DrugFile.AtcMnemonic field = FACTORY.createDrugFileAtcMnemonic();
//            field.setNumber(212.2f);
//
//            JAXBElement<DrugFile.AtcMnemonic> element = FACTORY.createDrugFileAtcMnemonic(field);
//            drugFile.setAtcMnemonic(element);
//
//            if (isUnset(FieldKey.ATC_MNEMONIC, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(productVo.getAtcMnemonic());
//            }
//        }
//
//        // CMOP DISPENSE O/O
//        DataField<Boolean> localCmop = dataFields.getDataField(FieldKey.TRANSMIT_TO_CMOP);
//
//        if (isValid(localCmop) || isUnset(FieldKey.TRANSMIT_TO_CMOP, differences)) {
//            DrugFile.CmopDispense field = FACTORY.createDrugFileCmopDispense();
//            field.setNumber(213f);
//
//            JAXBElement<DrugFile.CmopDispense> element = FACTORY.createDrugFileCmopDispense(field);
//            drugFile.setCmopDispense(element);
//
//            if (isUnset(FieldKey.TRANSMIT_TO_CMOP, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(toBooleanOneOrZero(localCmop.getValue().booleanValue()));
//            }
//        }
//
//        // QUANTITY DISPENSE MESSAGE O/O
//        DataField<String> quantityDispenseMessageField = dataFields.getDataField(FieldKey.QUANTITY_DISPENSE_MESSAGE);
//
//        if (isValid(quantityDispenseMessageField) || isUnset(FieldKey.QUANTITY_DISPENSE_MESSAGE, differences)) {
//            DrugFile.QuantityDispenseMessage field = FACTORY.createDrugFileQuantityDispenseMessage();
//            field.setNumber(215f);
//
//            JAXBElement<DrugFile.QuantityDispenseMessage> element = FACTORY.createDrugFileQuantityDispenseMessage(field);
//            drugFile.setQuantityDispenseMessage(element);
//
//            if (isUnset(FieldKey.QUANTITY_DISPENSE_MESSAGE, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(quantityDispenseMessageField.getValue());
//            }
//        }
//
//        // INPATIENT PHARMACY LOCATION O/O
//        DataField<String> inpatientPharmacyLocationField = dataFields.getDataField(FieldKey.INPATIENT_PHARMACY_LOCATION);
//
//        if (isValid(inpatientPharmacyLocationField) || isUnset(FieldKey.INPATIENT_PHARMACY_LOCATION, differences)) {
//            DrugFile.InpatientPharmacyLocation field = FACTORY.createDrugFileInpatientPharmacyLocation();
//            field.setNumber(300f);
//
//            JAXBElement<DrugFile.InpatientPharmacyLocation> element = FACTORY.createDrugFileInpatientPharmacyLocation(field);
//            drugFile.setInpatientPharmacyLocation(element);
//
//            if (isUnset(FieldKey.INPATIENT_PHARMACY_LOCATION, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(inpatientPharmacyLocationField.getValue());
//            }
//        }
//
//        // AR/WS AMIS CATEGORY O/O
//        ListDataField<String> arWsAmisCategoryField = dataFields.getDataField(FieldKey.AR_WS_AMIS_CATEGORY);
//
//        if (isValid(arWsAmisCategoryField) || isUnset(FieldKey.AR_WS_AMIS_CATEGORY, differences)) {
//            DrugFile.ArWsAmisCategory field = FACTORY.createDrugFileArWsAmisCategory();
//            field.setNumber(301f);
//
//            JAXBElement<DrugFile.ArWsAmisCategory> element = FACTORY.createDrugFileArWsAmisCategory(field);
//            drugFile.setArWsAmisCategory(element);
//
//            if (isUnset(FieldKey.AR_WS_AMIS_CATEGORY, differences) || arWsAmisCategoryField.isEmpty()) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(toString(VALUE_DASH_PATTERN, arWsAmisCategoryField.getValue().get(0)));
//            }
//        }
//
//        // AR/WS AMIS CONVERSION NUMBER O/O
//        DataField<Long> arWsAmisConversionNumberField = dataFields.getDataField(FieldKey.AR_WS_CONVERSION_NUMBER);
//
//        if (isValid(arWsAmisConversionNumberField) || isUnset(FieldKey.AR_WS_CONVERSION_NUMBER, differences)) {
//            DrugFile.ArWsAmisConversionNumber field = FACTORY.createDrugFileArWsAmisConversionNumber();
//            field.setNumber(302f);
//
//            JAXBElement<DrugFile.ArWsAmisConversionNumber> element = FACTORY.createDrugFileArWsAmisConversionNumber(field);
//            drugFile.setArWsAmisConversionNumber(element);
//
//            if (isUnset(FieldKey.AR_WS_CONVERSION_NUMBER, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue(BigInteger.valueOf(arWsAmisConversionNumberField.getValue()));
//            }
//        }
//
//        // IFCAP ITEM NUMBER (Multiple)
//        // ITEM NUMBER M/O
//        if (isValid(productVo.getIfcapItemNumber()) || isUnset(FieldKey.IFCAP_ITEM_NUMBER, differences)) {
//            DrugFile.IfcapItemNumber field = FACTORY.createDrugFileIfcapItemNumber();
//            field.setMultiple(true);
//            field.setNumber(441f);
//
//            JAXBElement<DrugFile.IfcapItemNumber> element = FACTORY.createDrugFileIfcapItemNumber(field);
//            drugFile.setIfcapItemNumber(element);
//
//            if (isUnset(FieldKey.IFCAP_ITEM_NUMBER, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//
//                Collection<IfcapItemNumberVo> ifcapItemNumbers = productVo.getIfcapItemNumber();
//
//                for (IfcapItemNumberVo ifcapItemNumberVo : ifcapItemNumbers) {
//                    field.getIfcapItemNumberFile().add(toIfcapItemNumberFile(ifcapItemNumberVo, differences, itemAction));
//
//                }
//
//            }
//        }
//
//        Collection<ActiveIngredientVo> activeIngredients = productVo.getActiveIngredients();
//
//        if (isValid(activeIngredients) || isUnset(FieldKey.ACTIVE_INGREDIENT, differences)) {
//            ActiveIngredientVo activeIngredient = activeIngredients.iterator().next();
//
//            // STRENGTH O/O
//            DrugFile.Strength strengthField = FACTORY.createDrugFileStrength();
//            strengthField.setNumber(901f);
//
//            JAXBElement<DrugFile.Strength> strengthElement = FACTORY.createDrugFileStrength(strengthField);
//            drugFile.setStrength(strengthElement);
//
//            // UNITS O/O
//            DrugFile.Unit unitField = FACTORY.createDrugFileUnit();
//            unitField.setNumber(902f);
//
//            JAXBElement<DrugFile.Unit> unitsElement = FACTORY.createDrugFileUnit(unitField);
//            drugFile.setUnit(unitsElement);
//
//            if (isUnset(FieldKey.ACTIVE_INGREDIENT, differences) || (activeIngredients.size() > 1)) { // unset
//                strengthElement.setNil(true);
//                unitsElement.setNil(true);
//            } else { // set
//                strengthField.setValue(activeIngredient.getStrength());
//                unitField.setValue(activeIngredient.getDrugUnit().getValue());
//            }
//        }
//
//        // POSSIBLE DOSAGES (Multiple)
//        // DISPENSE UNITS PER DOSE M/O
//        // DOSE O/O
//        // PACKAGE O/O
//        // BCMA UNITS PER DOSE O/O
//        if (isValid(productVo.getNationalPossibleDosages()) || isUnset(FieldKey.NATIONAL_POSSIBLE_DOSAGES, differences)) {
//            DrugFile.PossibleDosages field = FACTORY.createDrugFilePossibleDosages();
//            field.setMultiple(true);
//            field.setNumber(903f);
//
//            JAXBElement<DrugFile.PossibleDosages> element = FACTORY.createDrugFilePossibleDosages(field);
//            drugFile.setPossibleDosages(element);
//
//            if (isUnset(FieldKey.NATIONAL_POSSIBLE_DOSAGES, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                for (NationalPossibleDosagesVo nationalPossibleDosagesVo : productVo.getNationalPossibleDosages()) {
//                    field.getPossibleDosagesFile()
//                            .add(toPossibleDosagesFile(nationalPossibleDosagesVo, differences, itemAction));
//                }
//            }
//        }
//
//        // LOCAL POSSIBLE DOSAGE (Multiple)
//        // LOCAL POSSIBLE DOSAGE M/O
//        // PACKAGE O/O
//        // BCMA UNITS PER DOSE O/O
//        // OTHER LANGUAGE DOSAGE NAME O/O
//        // DOSE UNIT O/O
//        if (isValid(productVo.getLocalPossibleDosages()) || isUnset(FieldKey.LOCAL_POSSIBLE_DOSAGES, differences)) {
//            DrugFile.LocalPossibleDosage field = FACTORY.createDrugFileLocalPossibleDosage();
//            field.setMultiple(true);
//            field.setNumber(904f);
//
//            JAXBElement<DrugFile.LocalPossibleDosage> element = FACTORY.createDrugFileLocalPossibleDosage(field);
//            drugFile.setLocalPossibleDosage(element);
//
//            if (isUnset(FieldKey.LOCAL_POSSIBLE_DOSAGES, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                for (LocalPossibleDosagesVo localPossibleDosagesVo : productVo.getLocalPossibleDosages()) {
//                    field.getLocalPossibleDosageFile().add(toLocalPossibleDosageFile(localPossibleDosagesVo, differences,
//                                                                                     itemAction));
//                }
//            }
//        }
//
//        // CORRESPONDING INPATIENT DRUG O/O
//        ListDataField<ProductVo> correspondingInpatientDrugField =
//                dataFields.getDataField(FieldKey.CORRESPONDING_INPATIENT_DRUG);
//
//        if ((isValid(correspondingInpatientDrugField) && (correspondingInpatientDrugField.getValue().get(0)).isLocalUse())
//            || isUnset(FieldKey.CORRESPONDING_INPATIENT_DRUG, differences)) {
//            DrugFile.CorrespondingInpatientDrug field = FACTORY.createDrugFileCorrespondingInpatientDrug();
//            field.setNumber(905f);
//
//            JAXBElement<DrugFile.CorrespondingInpatientDrug> element = 
//              FACTORY.createDrugFileCorrespondingInpatientDrug(field);
//            drugFile.setCorrespondingInpatientDrug(element);
//
//            if (isUnset(FieldKey.CORRESPONDING_INPATIENT_DRUG, differences)) { // unset
//                element.setNil(true);
//            } else { // set
//                field.setValue((correspondingInpatientDrugField.getValue().get(0)).getVuid());
//            }
//        }
//
//        // LINKAGE ID M/M - Candidate Key
//        drugFile.getCandidateKey().setLinkageId(FACTORY.createLinkageIdKey());
//        drugFile.getCandidateKey().getLinkageId()
//                .setValue(((String) toCandidateKeyValue(FieldKey.VUID, differences, productVo.getVuid())));
//        drugFile.getCandidateKey().getLinkageId().setNumber(99.99f);
//
//        // LINKAGE ID M/M
//        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VUID, differences)) {
//            drugFile.setLinkageId(FACTORY.createLinkageIdKey());
//            drugFile.getLinkageId().setValue(productVo.getVuid());
//            drugFile.getLinkageId().setNumber(99.99f);
//        }

        return drugFile;

    }

    /**
     * SYNONYM (Multiple)
     * 
     * @param synonymVo Synonym VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Synonym File
     */
    private static SynonymFile toSynonymFile(SynonymVo synonymVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        SynonymFile synonymFile = FACTORY.createSynonymFile();
        synonymFile.setNumber(new Float("50.1"));

        // SYNONYM M/O
        synonymFile.setSynonym(FACTORY.createSynonymFileSynonym());
        synonymFile.getSynonym().setValue(synonymVo.getSynonymName());
        synonymFile.getSynonym().setNumber(PPSConstants.F0POINT01);

        // INTENDED USE O/O
        if (isValid(synonymVo.getSynonymIntendedUse()) || isUnset(FieldKey.SYNONYM_INTENDED_USE, differences)) {
            SynonymFile.IntendedUse field = FACTORY.createSynonymFileIntendedUse();
            field.setNumber(1f);

            JAXBElement<SynonymFile.IntendedUse> element = FACTORY.createSynonymFileIntendedUse(field);
            synonymFile.setIntendedUse(element);

            if (isUnset(FieldKey.SYNONYM_INTENDED_USE, differences)) { // unset
                element.setNil(true);
            } else { // set
                if ("0-TRADE NAME".equalsIgnoreCase(synonymVo.getSynonymIntendedUse().getIntendedUse())) {
                    field.setValue("0");
                } else if ("1-QUICK CODE".equalsIgnoreCase(synonymVo.getSynonymIntendedUse().getIntendedUse())) {
                    field.setValue("1");
                } else if ("D-DRUG ACCOUNTABILITY".equalsIgnoreCase(synonymVo.getSynonymIntendedUse().getIntendedUse())) {
                    field.setValue("D");
                } else if ("C-CONTROLLED SUBSTANCES".equalsIgnoreCase(synonymVo.getSynonymIntendedUse().getIntendedUse())) {
                    field.setValue("C");
                }
            }
        }

        // NDC CODE O/O
        if (isValid(synonymVo.getNdcCode()) || isUnset(FieldKey.NDC_CODE, differences)) {
            SynonymFile.NdcCode field = FACTORY.createSynonymFileNdcCode();
            field.setNumber(2f);

            JAXBElement<SynonymFile.NdcCode> element = FACTORY.createSynonymFileNdcCode(field);
            synonymFile.setNdcCode(element);

            if (isUnset(FieldKey.NDC_CODE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(synonymVo.getNdcCode());
            }
        }

        // VSN O/O
        if (isValid(synonymVo.getSynonymVsn()) || isUnset(FieldKey.VENDOR_STOCK_NUMBER, differences)) {
            SynonymFile.Vsn field = FACTORY.createSynonymFileVsn();
            field.setNumber(new Float("400"));

            JAXBElement<SynonymFile.Vsn> element = FACTORY.createSynonymFileVsn(field);
            synonymFile.setVsn(element);

            if (isUnset(FieldKey.VENDOR_STOCK_NUMBER, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(synonymVo.getSynonymVsn());
            }
        }

        // ORDER UNIT O/O
        if (isValid(synonymVo.getSynonymOrderUnit()) || isUnset(FieldKey.ORDER_UNIT, differences)) {
            SynonymFile.OrderUnit field = FACTORY.createSynonymFileOrderUnit();
            field.setNumber(new Float("401"));

            JAXBElement<SynonymFile.OrderUnit> element = FACTORY.createSynonymFileOrderUnit(field);
            synonymFile.setOrderUnit(element);

            if (isUnset(FieldKey.ORDER_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(synonymVo.getSynonymOrderUnit().getValue());
            }
        }

        // PRICE PER ORDER UNIT O/O
        if (isValid(synonymVo.getSynonymPricePerOrderUnit()) || isUnset(FieldKey.SYNONYM_PRICE_PER_ORDER_UNIT, differences)) {
            SynonymFile.PricePerOrderUnit field = FACTORY.createSynonymFilePricePerOrderUnit();
            field.setNumber(new Float("402"));

            JAXBElement<SynonymFile.PricePerOrderUnit> element = FACTORY.createSynonymFilePricePerOrderUnit(field);
            synonymFile.setPricePerOrderUnit(element);

            if (isUnset(FieldKey.SYNONYM_PRICE_PER_ORDER_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(synonymVo.getSynonymPricePerOrderUnit().floatValue());
            }
        }

        // DISPENSE UNITS PER ORDER UNIT O/O
        if (isValid(synonymVo.getSynonymDispenseUnitPerOrderUnit())
            || isUnset(FieldKey.SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT, differences)) {
            SynonymFile.DispenseUnitsPerOrderUnit field = FACTORY.createSynonymFileDispenseUnitsPerOrderUnit();
            field.setNumber(new Float("403"));

            JAXBElement<SynonymFile.DispenseUnitsPerOrderUnit> element =
                FACTORY.createSynonymFileDispenseUnitsPerOrderUnit(field);
            synonymFile.setDispenseUnitsPerOrderUnit(element);

            if (isUnset(FieldKey.SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(synonymVo.getSynonymDispenseUnitPerOrderUnit().floatValue());
            }
        }

        // PRICE PER DISPENSE UNIT O/O
        if (isValid(synonymVo.getSynonymPricePerDispenseUnit())
            || isUnset(FieldKey.SYNONYM_PRICE_PER_DISPENSE_UNIT, differences)) {
            SynonymFile.PricePerDispenseUnit field = FACTORY.createSynonymFilePricePerDispenseUnit();
            field.setNumber(new Float("404"));

            JAXBElement<SynonymFile.PricePerDispenseUnit> element = FACTORY.createSynonymFilePricePerDispenseUnit(field);
            synonymFile.setPricePerDispenseUnit(element);

            if (isUnset(FieldKey.SYNONYM_PRICE_PER_DISPENSE_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(synonymVo.getSynonymPricePerDispenseUnit().floatValue());
            }
        }

        // VENDOR O/O
        if (isValid(synonymVo.getSynonymVendor()) || isUnset(FieldKey.VENDOR, differences)) {
            SynonymFile.Vendor field = FACTORY.createSynonymFileVendor();
            field.setNumber(new Float("405"));

            JAXBElement<SynonymFile.Vendor> element = FACTORY.createSynonymFileVendor(field);
            synonymFile.setVendor(element);

            if (isUnset(FieldKey.VENDOR, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(synonymVo.getSynonymVendor());
            }
        }

        return synonymFile;

    }

    /**
     * NDC BY OUTPATIENT SITE NDCOP (Multiple)
     * 
     * @param ndcByOutpatientSiteNdcopVo VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return NDC By Outpatient Site NDC OP File
     */
    private static NdcByOutpatientSiteNdcopFile toNdcByOutpatientSiteNdcopFile(
        NdcByOutpatientSiteNdcVo ndcByOutpatientSiteNdcopVo, Map<FieldKey, Difference> differences, ItemAction itemAction) {

        NdcByOutpatientSiteNdcopFile ndcByOutpatientSiteNdcopFile = FACTORY.createNdcByOutpatientSiteNdcopFile();

        // OUTPATIENT SITE M/O
        ndcByOutpatientSiteNdcopFile.setOutpatientSite(FACTORY.createNdcByOutpatientSiteNdcopFileOutpatientSite());
        ndcByOutpatientSiteNdcopFile.getOutpatientSite().setValue(ndcByOutpatientSiteNdcopVo.getOutpatientSite().getValue());
        ndcByOutpatientSiteNdcopFile.getOutpatientSite().setNumber(PPSConstants.F0POINT01);

        // LAST LOCAL NDC O/O
        if (isValid(ndcByOutpatientSiteNdcopVo.getLastLocalNdc()) || isUnset(FieldKey.LAST_LOCAL_NDC, differences)) {
            NdcByOutpatientSiteNdcopFile.LastLocalNdc field = FACTORY.createNdcByOutpatientSiteNdcopFileLastLocalNdc();
            field.setNumber(1f);

            JAXBElement<NdcByOutpatientSiteNdcopFile.LastLocalNdc> element =
                FACTORY.createNdcByOutpatientSiteNdcopFileLastLocalNdc(field);
            ndcByOutpatientSiteNdcopFile.setLastLocalNdc(element);

            if (isUnset(FieldKey.LAST_LOCAL_NDC, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(ndcByOutpatientSiteNdcopVo.getLastLocalNdc());
            }
        }

        // LAST CMOP NDC O/O
        if (isValid(ndcByOutpatientSiteNdcopVo.getLastCmopNdc()) || isUnset(FieldKey.LAST_CMOP_NDC, differences)) {
            NdcByOutpatientSiteNdcopFile.LastCmopNdc field = FACTORY.createNdcByOutpatientSiteNdcopFileLastCmopNdc();
            field.setNumber(2f);

            JAXBElement<NdcByOutpatientSiteNdcopFile.LastCmopNdc> element =
                FACTORY.createNdcByOutpatientSiteNdcopFileLastCmopNdc(field);
            ndcByOutpatientSiteNdcopFile.setLastCmopNdc(element);

            if (isUnset(FieldKey.LAST_CMOP_NDC, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(ndcByOutpatientSiteNdcopVo.getLastCmopNdc());
            }
        }

        ndcByOutpatientSiteNdcopFile.setNumber(new Float("50.032"));

        return ndcByOutpatientSiteNdcopFile;
    }

    /**
     * DRUG TEXT ENTRY (Multiple)
     * 
     * @param drugTextVo Drug Text VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Drug Text Entry File
     */
    private static DrugTextEntryFile toDrugTextEntryFile(DrugTextVo drugTextVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        DrugTextEntryFile drugTextEntryFile = FACTORY.createDrugTextEntryFile();

        // DRUG TEXT ENTRY M/O
        drugTextEntryFile.setDrugTextEntry(FACTORY.createDrugTextEntryFileDrugTextEntry());
        drugTextEntryFile.getDrugTextEntry().setValue(drugTextVo.getValue());
        drugTextEntryFile.getDrugTextEntry().setNumber(PPSConstants.F0POINT01);

        drugTextEntryFile.setNumber(new Float("50.037"));

        return drugTextEntryFile;
    }

    /**
     * FORMULARY ALTERNATIVE (Multiple)
     * 
     * @param productVo Product VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return file
     */
    private static FormularyAlternativeFile toFormularyAlternativeFile(ProductVo productVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        FormularyAlternativeFile formularyAlternativeFile = FACTORY.createFormularyAlternativeFile();

        // FORMULARY ALTERNATIVE M/O
        formularyAlternativeFile.setFormularyAlternative(FACTORY.createFormularyAlternativeFileFormularyAlternative());
        formularyAlternativeFile.getFormularyAlternative().setValue(productVo.getVuid());
        formularyAlternativeFile.getFormularyAlternative().setNumber(PPSConstants.F0POINT01);

        formularyAlternativeFile.setNumber(new Float("50.065"));

        return formularyAlternativeFile;

    }

    /**
     * ATC CANISTER (Multiple)
     * 
     * @param atcCanisterVo ATC Canister VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return ATC Canister File
     */
    private static AtcCanisterFile toAtcCanisterFile(AtcCanisterVo atcCanisterVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        AtcCanisterFile atcCanisterFile = FACTORY.createAtcCanisterFile();
        atcCanisterFile.setNumber(new Float("50.0212"));

        // WARD GROUP FOR ATC CANISTER M/O
        atcCanisterFile.setWardGroupForAtcCanister(FACTORY.createAtcCanisterFileWardGroupForAtcCanister());
        atcCanisterFile.getWardGroupForAtcCanister().setValue(atcCanisterVo.getWardGroupForAtc().getValue());
        atcCanisterFile.getWardGroupForAtcCanister().setNumber(PPSConstants.F0POINT01);

        // ATC CANISTER O/O
        if (isValid(atcCanisterVo.getAtcCanister()) || isUnset(FieldKey.ATC_CANISTER, differences)) {
            AtcCanisterFile.AtcCanister field = FACTORY.createAtcCanisterFileAtcCanister();
            field.setNumber(1f);

            JAXBElement<AtcCanisterFile.AtcCanister> element = FACTORY.createAtcCanisterFileAtcCanister(field);
            atcCanisterFile.setAtcCanister(element);

            if (isUnset(FieldKey.ATC_CANISTER, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(atcCanisterVo.getAtcCanister()));
            }
        }

        return atcCanisterFile;
    }

    /**
     * IFCAP ITEM NUMBER (Multiple)
     * 
     * @param ifcapItemNumberVo IFCAP Item Number VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return file
     */
    private static IfcapItemNumberFile toIfcapItemNumberFile(IfcapItemNumberVo ifcapItemNumberVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        IfcapItemNumberFile ifcapItemNumberFile = FACTORY.createIfcapItemNumberFile();

        // ITEM NUMBER M/O
        ifcapItemNumberFile.setItemNumber(FACTORY.createIfcapItemNumberFileItemNumber());
        ifcapItemNumberFile.getItemNumber().setValue(ifcapItemNumberVo.getValue());
        ifcapItemNumberFile.getItemNumber().setNumber(PPSConstants.F0POINT01);

        ifcapItemNumberFile.setNumber(new Float("50.0441"));

        return ifcapItemNumberFile;

    }

    /**
     * POSSIBLE DOSAGES (Multiple)
     * 
     * @param nationalPossibleDosagesVo VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Possible Dosages File
     */
    private static PossibleDosagesFile toPossibleDosagesFile(NationalPossibleDosagesVo nationalPossibleDosagesVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        PossibleDosagesFile possibleDosagesFile = FACTORY.createPossibleDosagesFile();

        // DISPENSE UNITS PER DOSE M/O
        possibleDosagesFile.setDispenseUnitsPerDose(FACTORY.createPossibleDosagesFileDispenseUnitsPerDose());
        possibleDosagesFile.getDispenseUnitsPerDose().setValue(
            nationalPossibleDosagesVo.getPossibleDosagesDispenseUnitsPerDose().floatValue());
        possibleDosagesFile.getDispenseUnitsPerDose().setNumber(PPSConstants.F0POINT01);

        // DOSE O/O
        if (isValid(nationalPossibleDosagesVo.getDose()) || isUnset(FieldKey.DOSE, differences)) {
            PossibleDosagesFile.Dose field = FACTORY.createPossibleDosagesFileDose();
            field.setNumber(1f);

            JAXBElement<PossibleDosagesFile.Dose> element = FACTORY.createPossibleDosagesFileDose(field);
            possibleDosagesFile.setDose(element);

            if (isUnset(FieldKey.DOSE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(nationalPossibleDosagesVo.getDose().floatValue());
            }
        }

        // PACKAGE O/O
        if (isValid(nationalPossibleDosagesVo.getPossibleDosagePackage())
            || isUnset(FieldKey.POSSIBLE_DOSAGE_PACKAGE, differences)) {
            PossibleDosagesFile.Package field = FACTORY.createPossibleDosagesFilePackage();
            field.setNumber(2f);

            JAXBElement<PossibleDosagesFile.Package> element = FACTORY.createPossibleDosagesFilePackage(field);
            possibleDosagesFile.setPackage(element);

            if (isUnset(FieldKey.POSSIBLE_DOSAGE_PACKAGE, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (nationalPossibleDosagesVo.getPossibleDosagePackage().size() == 2) {
                    field.setValue("IO");
                } else if (nationalPossibleDosagesVo.getPossibleDosagePackage().contains("INPATIENT")) {
                    field.setValue("I");
                } else {
                    field.setValue("O");
                }
            }
        }

        // BCMA UNITS PER DOSE O/O
        if (isValid(nationalPossibleDosagesVo.getBcmaUnitsPerDose()) || isUnset(FieldKey.BCMA_UNITS_PER_DOSE, differences)) {
            PossibleDosagesFile.BcmaUnitsPerDose field = FACTORY.createPossibleDosagesFileBcmaUnitsPerDose();
            field.setNumber(new Float("3"));

            JAXBElement<PossibleDosagesFile.BcmaUnitsPerDose> element =
                FACTORY.createPossibleDosagesFileBcmaUnitsPerDose(field);
            possibleDosagesFile.setBcmaUnitsPerDose(element);

            if (isUnset(FieldKey.BCMA_UNITS_PER_DOSE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(nationalPossibleDosagesVo.getBcmaUnitsPerDose().floatValue());
            }
        }

        possibleDosagesFile.setNumber(new Float("50.0903"));

        return possibleDosagesFile;

    }

    /**
     * LOCAL POSSIBLE DOSAGE (Multiple)
     * 
     * @param localPossibleDosagesVo Local Possible Dosages VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Local Possible Dosages File
     */
    private static LocalPossibleDosageFile toLocalPossibleDosageFile(LocalPossibleDosagesVo localPossibleDosagesVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        LocalPossibleDosageFile localPossibleDosageFile = FACTORY.createLocalPossibleDosageFile();

        // LOCAL POSSIBLE DOSAGE M/O
        localPossibleDosageFile.setLocalPossibleDosage(FACTORY.createLocalPossibleDosageFileLocalPossibleDosage());
        localPossibleDosageFile.getLocalPossibleDosage().setValue(localPossibleDosagesVo.getLocalPossibleDosage());
        localPossibleDosageFile.getLocalPossibleDosage().setNumber(PPSConstants.F0POINT01);

        // PACKAGE O/O
        if (isValid(localPossibleDosagesVo.getPossibleDosagePackage())
            || isUnset(FieldKey.POSSIBLE_DOSAGE_PACKAGE, differences)) {
            LocalPossibleDosageFile.Package field = FACTORY.createLocalPossibleDosageFilePackage();
            field.setNumber(1f);

            JAXBElement<LocalPossibleDosageFile.Package> element = FACTORY.createLocalPossibleDosageFilePackage(field);
            localPossibleDosageFile.setPackage(element);

            if (isUnset(FieldKey.POSSIBLE_DOSAGE_PACKAGE, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (localPossibleDosagesVo.getPossibleDosagePackage().size() == 2) {
                    field.setValue("IO");
                } else if (localPossibleDosagesVo.getPossibleDosagePackage().contains("INPATIENT")) {
                    field.setValue("I");
                } else {
                    field.setValue("O");
                }
            }
        }

        // BCMA UNITS PER DOSE O/O
        if (isValid(localPossibleDosagesVo.getBcmaUnitsPerDose()) || isUnset(FieldKey.BCMA_UNITS_PER_DOSE, differences)) {
            LocalPossibleDosageFile.BcmaUnitsPerDose field = FACTORY.createLocalPossibleDosageFileBcmaUnitsPerDose();
            field.setNumber(2f);

            JAXBElement<LocalPossibleDosageFile.BcmaUnitsPerDose> element =
                FACTORY.createLocalPossibleDosageFileBcmaUnitsPerDose(field);
            localPossibleDosageFile.setBcmaUnitsPerDose(element);

            if (isUnset(FieldKey.BCMA_UNITS_PER_DOSE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(localPossibleDosagesVo.getBcmaUnitsPerDose().floatValue());
            }
        }

        // OTHER LANGUAGE DOSAGE NAME O/O
        if (isValid(localPossibleDosagesVo.getOtherLanguageDosageName())
            || isUnset(FieldKey.OTHER_LANGUAGE_DOSAGE_NAME, differences)) {
            LocalPossibleDosageFile.OtherLanguageDosageName field =
                FACTORY.createLocalPossibleDosageFileOtherLanguageDosageName();
            field.setNumber(new Float("3"));

            JAXBElement<LocalPossibleDosageFile.OtherLanguageDosageName> element =
                FACTORY.createLocalPossibleDosageFileOtherLanguageDosageName(field);
            localPossibleDosageFile.setOtherLanguageDosageName(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_DOSAGE_NAME, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(localPossibleDosagesVo.getOtherLanguageDosageName().toString());
            }
        }

        // DOSE UNIT O/O
        if (isValid(localPossibleDosagesVo.getDoseUnit()) || isUnset(FieldKey.DOSE_UNIT, differences)) {
            LocalPossibleDosageFile.DoseUnit field = FACTORY.createLocalPossibleDosageFileDoseUnit();
            field.setNumber(new Float("4"));

            JAXBElement<LocalPossibleDosageFile.DoseUnit> element = FACTORY.createLocalPossibleDosageFileDoseUnit(field);
            localPossibleDosageFile.setDoseUnit(element);

            if (isUnset(FieldKey.DOSE_UNIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(localPossibleDosagesVo.getDoseUnit().getValue());
            }
        }

        localPossibleDosageFile.setNumber(new Float("50.0904"));

        return localPossibleDosageFile;
    }

    /**
     * NEW WARNING LABEL LIST WARN (Multiple)
     * 
     * @param rxConsultVo Rx Consult VO
     * @param order Order of Warning Label
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Local Possible Dosages File
     */
    private static NewWarningLabelListWarnFile toNewWarningLabelListWarnFile(RxConsultVo rxConsultVo, int order,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        NewWarningLabelListWarnFile newWarningLabelListWarnFile = FACTORY.createNewWarningLabelListWarnFile();
        newWarningLabelListWarnFile.setNumber(0f);

        // ORDER M/O
        newWarningLabelListWarnFile.setOrder(FACTORY.createNewWarningLabelListWarnFileOrder());
        newWarningLabelListWarnFile.getOrder().setNumber(0f);
        newWarningLabelListWarnFile.getOrder().setValue(BigInteger.valueOf(order));

        // TYPE M/O
        newWarningLabelListWarnFile.setType(FACTORY.createNewWarningLabelListWarnFileType());
        newWarningLabelListWarnFile.getType().setNumber(0f);

        if (RxConsultType.COTS.equals(rxConsultVo.getRxConsultType())) {
            newWarningLabelListWarnFile.getType().setValue("F");

            // TEXT M/O
            newWarningLabelListWarnFile.setText(FACTORY.createNewWarningLabelListWarnFileText());
            newWarningLabelListWarnFile.getText().setNumber(0f);
            newWarningLabelListWarnFile.getText().setValue(rxConsultVo.getConsultText());
        } else {
            newWarningLabelListWarnFile.getType().setValue("V");

            // NAME M/O
            newWarningLabelListWarnFile.setName(FACTORY.createNewWarningLabelListWarnFileName());
            newWarningLabelListWarnFile.getName().setNumber(0f);
            newWarningLabelListWarnFile.getName().setValue(rxConsultVo.getValue());
        }

        return newWarningLabelListWarnFile;
    }
}
