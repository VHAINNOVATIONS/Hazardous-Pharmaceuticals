/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleAssocVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OiRouteVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacyorderableitem.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacyorderableitem.OiDrugTextEntryFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacyorderableitem.PharmacyOrderableItemFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacyorderableitem.SynonymFile;


/**
 * Converts an Orderable Item VO to a Pharmacy Orderable Item File.
 */
public class PharmacyOrderableItemFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
            .asList(new FieldKey[] {
                FieldKey.LOCAL_USE, FieldKey.VISTA_OI_NAME, FieldKey.DOSAGE_FORM, FieldKey.OI_IV_FLAG,
                FieldKey.INACTIVATION_DATE, FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, FieldKey.OI_ROUTE, FieldKey.ADMIN_SCHEDULES,
                FieldKey.ORDERABLE_ITEM_TYPE, FieldKey.SYNONYMS, FieldKey.ORDERABLE_ITEM_SYNONYM,
                FieldKey.LOCAL_DRUG_TEXTS, FieldKey.PATIENT_INSTRUCTIONS, FieldKey.OTHER_LANGUAGE_INSTRUCTIONS,
                FieldKey.NON_VA_MED })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private PharmacyOrderableItemFileConverter() {
    }

    /**
     * Convert an Orderable Item VO to a Pharmacy Orderable Item File.
     * 
     * @param orderableItemVo Orderable Item VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Pharmacy Orderable Item File
     */
    public static PharmacyOrderableItemFile toPharmacyOrderableItemFile(OrderableItemVo orderableItemVo,
                                                                        Map<FieldKey, Difference> differences,
                                                                        ItemAction itemAction) {

        DataFields<DataField> dataFields = orderableItemVo.getVaDataFields();

        PharmacyOrderableItemFile pharmacyOrderableItemFile = new PharmacyOrderableItemFile();
        pharmacyOrderableItemFile.setCandidateKey(FACTORY.createPharmacyOrderableItemFileCandidateKey());
        pharmacyOrderableItemFile.setNumber(new Float("50.7"));

        // NAME M - Candidate Key
        pharmacyOrderableItemFile.getCandidateKey().setName(FACTORY.createNameKey());
        pharmacyOrderableItemFile.getCandidateKey().getName()
                .setValue((String) toCandidateKeyValue(FieldKey.VISTA_OI_NAME, differences, orderableItemVo.getVistaOiName()));
        pharmacyOrderableItemFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VISTA_OI_NAME, differences)) {
            pharmacyOrderableItemFile.setName(FACTORY.createNameKey());
            pharmacyOrderableItemFile.getName().setValue(orderableItemVo.getVistaOiName());
            pharmacyOrderableItemFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // DOSAGE FORM M/M - Candidate Key
        pharmacyOrderableItemFile.getCandidateKey().setDosageForm(FACTORY.createDosageFormKey());
        pharmacyOrderableItemFile
                .getCandidateKey()
                .getDosageForm()
                .setValue(((DosageFormVo) toCandidateKeyValue(FieldKey.DOSAGE_FORM, differences,
                                                              orderableItemVo.getDosageForm())).getDosageFormName());
        pharmacyOrderableItemFile.getCandidateKey().getDosageForm().setNumber(PPSConstants.F0POINT02);

        // DOSAGE FORM M/M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.DOSAGE_FORM, differences)) {
            pharmacyOrderableItemFile.setDosageForm(FACTORY.createDosageFormKey());
            pharmacyOrderableItemFile.getDosageForm().setValue(orderableItemVo.getDosageForm().getDosageFormName());
            pharmacyOrderableItemFile.getDosageForm().setNumber(PPSConstants.F0POINT02);
        }

        // IV FLAG O/O
        // Interface Rule I5
        DataField<Boolean> ivFlagField = dataFields.getDataField(FieldKey.OI_IV_FLAG);

        if (isValid(ivFlagField) || isUnset(FieldKey.OI_IV_FLAG, differences)) {
            PharmacyOrderableItemFile.IvFlag field = FACTORY.createPharmacyOrderableItemFileIvFlag();
            field.setNumber(PPSConstants.F0POINT03);

            JAXBElement<PharmacyOrderableItemFile.IvFlag> element = FACTORY.createPharmacyOrderableItemFileIvFlag(field);
            pharmacyOrderableItemFile.setIvFlag(element);

            if (isUnset(FieldKey.OI_IV_FLAG, differences) || !ivFlagField.getValue()) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(VISTA_TRUE);
            }
        }

        // INACTIVE DATE O/M
        Date inactiveDate = (Date) getNewValue(FieldKey.INACTIVATION_DATE, differences);

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactiveDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {
            PharmacyOrderableItemFile.InactiveDate field = FACTORY.createPharmacyOrderableItemFileInactiveDate();
            field.setNumber(PPSConstants.F0POINT04);

            JAXBElement<PharmacyOrderableItemFile.InactiveDate> element =
                    FACTORY.createPharmacyOrderableItemFileInactiveDate(field);
            pharmacyOrderableItemFile.setInactiveDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactiveDate));
            }
        }

        // DAY (nD) or DOSE (nL) LIMIT O/O
        DataField<String> dayOrDoseLimitField = dataFields.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);

        if (isValid(dayOrDoseLimitField) || isUnset(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, differences)) {
            PharmacyOrderableItemFile.DayOrDoseLimit field = FACTORY.createPharmacyOrderableItemFileDayOrDoseLimit();
            field.setNumber(PPSConstants.F0POINT05);

            JAXBElement<PharmacyOrderableItemFile.DayOrDoseLimit> element =
                    FACTORY.createPharmacyOrderableItemFileDayOrDoseLimit(field);
            pharmacyOrderableItemFile.setDayOrDoseLimit(element);

            if (isUnset(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(dayOrDoseLimitField.getValue());
            }
        }

        // MED ROUTE O/O
        if (isValid(orderableItemVo.getOiRoute()) || isUnset(FieldKey.OI_ROUTE, differences)) {
            PharmacyOrderableItemFile.MedRoute field = FACTORY.createPharmacyOrderableItemFileMedRoute();
            field.setNumber(PPSConstants.F0POINT06);

            JAXBElement<PharmacyOrderableItemFile.MedRoute> element = FACTORY.createPharmacyOrderableItemFileMedRoute(field);
            pharmacyOrderableItemFile.setMedRoute(element);

            if (isUnset(FieldKey.OI_ROUTE, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (OiRouteVo oiRouteVo : orderableItemVo.getOiRoute()) {
                    if (oiRouteVo.isDefaultRoute()) {
                        field.setValue(oiRouteVo.getLocalMedicationRoute().getValue());
                    }
                }
            }
        }

        // SCHEDULE TYPE O/O
        if (isValid(orderableItemVo.getOiScheduleType()) || isUnset(FieldKey.OI_SCHEDULE_TYPE, differences)) {
            PharmacyOrderableItemFile.ScheduleType field = FACTORY.createPharmacyOrderableItemFileScheduleType();
            field.setNumber(PPSConstants.F0POINT07);

            JAXBElement<PharmacyOrderableItemFile.ScheduleType> element =
                    FACTORY.createPharmacyOrderableItemFileScheduleType(field);
            pharmacyOrderableItemFile.setScheduleType(element);

            if (isUnset(FieldKey.OI_SCHEDULE_TYPE, differences)
                || "C".equalsIgnoreCase(orderableItemVo.getOiScheduleType().getCode())) { // unset

                element.setNil(true);
            } else { // set
                field.setValue(orderableItemVo.getOiScheduleType().getCode());
            }
        }

        // SCHEDULE O/O
        if (isValid(getDefaultSchedule(orderableItemVo)) || isUnset(FieldKey.ADMIN_SCHEDULES, differences)) {
            PharmacyOrderableItemFile.Schedule field = FACTORY.createPharmacyOrderableItemFileSchedule();
            field.setNumber(PPSConstants.F0POINT08);

            JAXBElement<PharmacyOrderableItemFile.Schedule> element = FACTORY.createPharmacyOrderableItemFileSchedule(field);
            pharmacyOrderableItemFile.setSchedule(element);

            if (isUnset(FieldKey.ADMIN_SCHEDULES, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(getDefaultSchedule(orderableItemVo).getScheduleName());
            }
        }

        // SUPPLY O/O
        ListDataField<Category> productType = orderableItemVo.getVaDataFields().getDataField(FieldKey.CATEGORIES);

        if (isValid(productType) || isUnset(FieldKey.CATEGORIES, differences)) {
            PharmacyOrderableItemFile.Supply field = FACTORY.createPharmacyOrderableItemFileSupply();
            field.setNumber(PPSConstants.F0POINT09);

            JAXBElement<PharmacyOrderableItemFile.Supply> element = FACTORY.createPharmacyOrderableItemFileSupply(field);
            pharmacyOrderableItemFile.setSupply(element);

            if (isUnset(FieldKey.CATEGORIES, differences) || !productType.getValue().contains("SUPPLY")) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(VISTA_TRUE);
            }
        }

        // SYNONYM (Multiple)
        // SYNONYM M/O
        MultitextDataField<String> synonymField = dataFields.getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM);

        if (isValid(synonymField) || isUnset(FieldKey.ORDERABLE_ITEM_SYNONYM, differences)) {
            PharmacyOrderableItemFile.Synonym field = FACTORY.createPharmacyOrderableItemFileSynonym();
            field.setMultiple(true);
            field.setNumber(2f);

            JAXBElement<PharmacyOrderableItemFile.Synonym> element = FACTORY.createPharmacyOrderableItemFileSynonym(field);
            pharmacyOrderableItemFile.setSynonym(element);

            if (isUnset(FieldKey.ORDERABLE_ITEM_SYNONYM, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (String synonym : synonymField.getValue()) {
                    field.getSynonymFile().add(toSynonymFile(synonym, differences, itemAction));
                }
            }
        }

        // FORMULARY STATUS M/O
        //String formularyStatus = orderableItemVo.getFormularyStatus().name();

        //PharmacyOrderableItemFile.FormularyStatus formularyStatusFieldDefinition =
        //        FACTORY.createPharmacyOrderableItemFileFormularyStatus();
        //formularyStatusFieldDefinition.setNumber(5f);
//
        //JAXBElement<PharmacyOrderableItemFile.FormularyStatus> formularyStatusElement =
        //        FACTORY.createPharmacyOrderableItemFileFormularyStatus(formularyStatusFieldDefinition);
        //pharmacyOrderableItemFile.setFormularyStatus(formularyStatusElement);

        // optional in the schema, but mandatory in the program logic
        //if ("FORMULARY".equalsIgnoreCase(formularyStatus)) {
        //    formularyStatusElement.setNil(true);
        //} else {
        //    formularyStatusFieldDefinition.setValue(VISTA_TRUE);
        //}

        // OI-DRUG TEXT ENTRY (Multiple)
        // OI-DRUG TEXT ENTRY M/O
        if (isValid(orderableItemVo.getLocalDrugTexts()) || isUnset(FieldKey.LOCAL_DRUG_TEXTS, differences)) {
            pharmacyOrderableItemFile.setOiDrugTextEntry(FACTORY.createPharmacyOrderableItemFileOiDrugTextEntry());

            for (DrugTextVo drugText : orderableItemVo.getLocalDrugTexts()) {
                pharmacyOrderableItemFile.getOiDrugTextEntry().getOiDrugTextEntryFile()
                        .add(toOiDrugTextEntryFile(drugText, differences, itemAction));
            }

            pharmacyOrderableItemFile.getOiDrugTextEntry().setMultiple(true);
            pharmacyOrderableItemFile.getOiDrugTextEntry().setNumber(new Float("6f"));

        }

        // PATIENT INSTRUCTIONS O/O
        DataField<String> patientInstructionsField = dataFields.getDataField(FieldKey.PATIENT_INSTRUCTIONS);

        if (isValid(patientInstructionsField) || isUnset(FieldKey.PATIENT_INSTRUCTIONS, differences)) {
            PharmacyOrderableItemFile.PatientInstructions field = FACTORY.createPharmacyOrderableItemFilePatientInstructions();
            field.setNumber(new Float("7"));

            JAXBElement<PharmacyOrderableItemFile.PatientInstructions> element =
                    FACTORY.createPharmacyOrderableItemFilePatientInstructions(field);
            pharmacyOrderableItemFile.setPatientInstructions(element);

            if (isUnset(FieldKey.PATIENT_INSTRUCTIONS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(patientInstructionsField.getValue());
            }
        }

        // OTHER LANGUAGE INSTRUCTIONS O/O
        DataField<String> otherLanguageInstructionsField = dataFields.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS);

        if (isValid(otherLanguageInstructionsField) || isUnset(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS, differences)) {
            PharmacyOrderableItemFile.OtherLanguageInstructions field =
                    FACTORY.createPharmacyOrderableItemFileOtherLanguageInstructions();
            field.setNumber(new Float("7.1"));

            JAXBElement<PharmacyOrderableItemFile.OtherLanguageInstructions> element =
                    FACTORY.createPharmacyOrderableItemFileOtherLanguageInstructions(field);
            pharmacyOrderableItemFile.setOtherLanguageInstructions(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(otherLanguageInstructionsField.getValue());
            }
        }

        // NON-VA MED O/O
        // Interface Rule I6
        Boolean nonVaMedField = orderableItemVo.getNonVaMed();

        if (isValid(nonVaMedField) || isUnset(FieldKey.NON_VA_MED, differences)) {
            PharmacyOrderableItemFile.NonVaMed field = FACTORY.createPharmacyOrderableItemFileNonVaMed();
            field.setNumber(new Float("8"));

            JAXBElement<PharmacyOrderableItemFile.NonVaMed> element = FACTORY.createPharmacyOrderableItemFileNonVaMed(field);
            pharmacyOrderableItemFile.setNonVaMed(element);

            if (isUnset(FieldKey.NON_VA_MED, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(VISTA_TRUE);
            }
        }

        return pharmacyOrderableItemFile;

    }

    /**
     * Get the default schedule.
     * 
     * @param orderableItemVo orderable item
     * @return default schedule or null if no default
     */
    private static AdministrationScheduleVo getDefaultSchedule(OrderableItemVo orderableItemVo) {
        if (isValid(orderableItemVo.getAdminSchedules())) {
            for (AdministrationScheduleAssocVo administrationScheduleAssocVo : orderableItemVo.getAdminSchedules()) {
                if (administrationScheduleAssocVo.isDefaultSchedule()) {
                    return administrationScheduleAssocVo.getAdministrationSchedule();
                }
            }
        }

        return null;
    }

    /**
     * SYNONYM (Multiple)
     * 
     * @param synonym Synonym
     * @param differences Map of the differences
     * @param itemAction ItemAction
     * @return SynonymFile
     */
    private static SynonymFile toSynonymFile(String synonym, Map<FieldKey, Difference> differences, ItemAction itemAction) {

        SynonymFile synonymFile = FACTORY.createSynonymFile();
        synonymFile.setNumber(new Float("50.72"));

        // SYNONYM M/O
        synonymFile.setSynonym(FACTORY.createSynonymFileSynonym());
        synonymFile.getSynonym().setValue(synonym);
        synonymFile.getSynonym().setNumber(PPSConstants.F0POINT01);

        return synonymFile;

    }

    /**
     * OI-DRUG TEXT ENTRY (Multiple)
     * 
     * @param drugTextVo DrugTextVo
     * @param differences Map of the differences
     * @param itemAction ItemAction
     * @return OiDrugTextEntryFile
     */
    private static OiDrugTextEntryFile toOiDrugTextEntryFile(DrugTextVo drugTextVo, Map<FieldKey, Difference> differences,
                                                             ItemAction itemAction) {

        OiDrugTextEntryFile oiDrugTextEntryFile = FACTORY.createOiDrugTextEntryFile();
        oiDrugTextEntryFile.setNumber(new Float("50.76"));

        // OI-DRUG TEXT ENTRY M/O
        oiDrugTextEntryFile.setOiDrugTextEntry(FACTORY.createOiDrugTextEntryFileOiDrugTextEntry());
        oiDrugTextEntryFile.getOiDrugTextEntry().setValue(drugTextVo.getValue());
        oiDrugTextEntryFile.getOiDrugTextEntry().setNumber(PPSConstants.F0POINT01);

        return oiDrugTextEntryFile;

    }
}
