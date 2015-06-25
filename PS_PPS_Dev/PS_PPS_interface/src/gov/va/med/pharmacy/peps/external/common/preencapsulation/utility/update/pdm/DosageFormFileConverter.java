/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormNounVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.dosageform.DispenseUnitsPerDoseFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.dosageform.DosageFormFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.dosageform.MedRouteForDosageFormFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.dosageform.NounFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.dosageform.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.dosageform.UnitsFile;


/**
 * Converts a Dosage Form VO to a Dosage Form File.
 */
public class DosageFormFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.DOSAGE_FORM_NAME, FieldKey.LOCAL_MED_ROUTES, FieldKey.LOCAL_MEDICATION_ROUTE, FieldKey.VERB,
            FieldKey.OTHER_LANGUAGE_VERB, FieldKey.PREPOSITION, FieldKey.OTHER_LANGUAGE_PREPOSITION, FieldKey.DF_NOUNS,
            FieldKey.INACTIVATION_DATE, FieldKey.DF_UNITS, FieldKey.DF_DISPENSE_UNITS_PER_DOSE, FieldKey.CONJUNCTION })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DosageFormFileConverter() {
    }

    /**
     * Convert a Dosage Form VO to a Dosage Form File.
     * 
     * @param dosageFormVo Dosage Form VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Dosage Form File
     */
    public static DosageFormFile toDosageFormFile(DosageFormVo dosageFormVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        DataFields<DataField> dataFields = dosageFormVo.getVaDataFields();

        DosageFormFile dosageFormFile = FACTORY.createDosageFormFile();
        dosageFormFile.setCandidateKey(FACTORY.createDosageFormFileCandidateKey());
        dosageFormFile.setNumber(new Float("50.606"));

        // NAME M - Candidate Key
        dosageFormFile.getCandidateKey().setName(FACTORY.createNameKey());
        dosageFormFile.getCandidateKey().getName()
            .setValue((String) toCandidateKeyValue(FieldKey.DOSAGE_FORM_NAME, differences, dosageFormVo.getDosageFormName()));
        dosageFormFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.DOSAGE_FORM_NAME, differences)) {
            dosageFormFile.setName(FACTORY.createNameKey());
            dosageFormFile.getName().setValue(dosageFormVo.getDosageFormName());
            dosageFormFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // MED ROUTE FOR DOSAGE FORM (Multiple)
        // MED ROUTE FOR DOSAGE FORM M/O
        if (isValid(dosageFormVo.getLocalMedRoutes()) || isUnset(FieldKey.LOCAL_MED_ROUTES, differences)) {
            DosageFormFile.MedRouteForDosageForm field = FACTORY.createDosageFormFileMedRouteForDosageForm();
            field.setMultiple(true);
            field.setNumber(1f);

            JAXBElement<DosageFormFile.MedRouteForDosageForm> element =
                FACTORY.createDosageFormFileMedRouteForDosageForm(field);

            dosageFormFile.setMedRouteForDosageForm(element);

            if (isUnset(FieldKey.LOCAL_MED_ROUTES, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (LocalMedicationRouteVo localMedicationRouteVo : dosageFormVo.getLocalMedRoutes()) {
                    field.getMedRouteForDosageFormFile().add(
                        toMedRouteForDosageFormFile(localMedicationRouteVo, differences, itemAction));
                }
            }
        }

        convertVerbsNounsLanguages(dataFields, differences, dosageFormFile, dosageFormVo, itemAction);

        // INACTIVATION DATE O/M
        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(dosageFormVo.getInactivationDate())
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {

            DosageFormFile.InactivationDate field = FACTORY.createDosageFormFileInactivationDate();
            field.setNumber(new Float("7"));

            JAXBElement<DosageFormFile.InactivationDate> element = FACTORY.createDosageFormFileInactivationDate(field);
            dosageFormFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(dosageFormVo.getInactivationDate()));
            }

        }

        // UNITS (Multiple)
        // UNITS M/O
        // PACKAGE O/O
        if (isValid(dosageFormVo.getDfUnits()) || isUnset(FieldKey.DF_UNITS, differences)) {
            DosageFormFile.Units field = FACTORY.createDosageFormFileUnits();
            field.setMultiple(true);
            field.setNumber(new Float("8"));

            JAXBElement<DosageFormFile.Units> element = FACTORY.createDosageFormFileUnits(field);

            dosageFormFile.setUnits(element);

            if (isUnset(FieldKey.DF_UNITS, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (DosageFormUnitVo dosageFormUnitVo : dosageFormVo.getDfUnits()) {
                    field.getUnitsFile().add(toUnitsFile(dosageFormUnitVo, differences, itemAction));
                }
            }
        }

        convertDispenseUnitsPerDose(dataFields, differences, dosageFormFile, dosageFormVo, itemAction);
        convertConjunctionField(dataFields, differences, dosageFormFile);

        return dosageFormFile;
    }

    /**
     * convertVerbsNounsLanguages
     *
     * @param dataFields DataFields<DataField>
     * @param differences Map<FieldKey, Difference> differences
     * @param dosageFormFile DosageFormFile dosageFormFile
     * @param dosageFormVo DosageFormVo
     * @param itemAction ItemAction
     */
    private static void convertVerbsNounsLanguages(DataFields<DataField> dataFields, Map<FieldKey, Difference> differences,
        DosageFormFile dosageFormFile, DosageFormVo dosageFormVo, ItemAction itemAction) {

        // VERB O/O
        DataField<String> verbField = dataFields.getDataField(FieldKey.VERB);

        if (isValid(verbField) || isUnset(FieldKey.VERB, differences)) {
            DosageFormFile.Verb field = FACTORY.createDosageFormFileVerb();
            field.setNumber(new Float("3"));

            JAXBElement<DosageFormFile.Verb> element = FACTORY.createDosageFormFileVerb(field);
            dosageFormFile.setVerb(element);

            if (isUnset(FieldKey.VERB, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(verbField.getValue());
            }
        }

        // OTHER LANGUAGE VERB O/O
        DataField<String> otherLanguageVerbField = dataFields.getDataField(FieldKey.OTHER_LANGUAGE_VERB);

        if (isValid(otherLanguageVerbField) || isUnset(FieldKey.OTHER_LANGUAGE_VERB, differences)) {
            DosageFormFile.OtherLanguageVerb field = FACTORY.createDosageFormFileOtherLanguageVerb();
            field.setNumber(new Float("3.1"));

            JAXBElement<DosageFormFile.OtherLanguageVerb> element = FACTORY.createDosageFormFileOtherLanguageVerb(field);
            dosageFormFile.setOtherLanguageVerb(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_VERB, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(otherLanguageVerbField.getValue());
            }
        }

        // PREPOSITION O/O
        DataField<String> prepositionField = dataFields.getDataField(FieldKey.PREPOSITION);

        if (isValid(prepositionField) || isUnset(FieldKey.PREPOSITION, differences)) {
            DosageFormFile.Preposition field = FACTORY.createDosageFormFilePreposition();
            field.setNumber(new Float("5"));

            JAXBElement<DosageFormFile.Preposition> element = FACTORY.createDosageFormFilePreposition(field);
            dosageFormFile.setPreposition(element);

            if (isUnset(FieldKey.PREPOSITION, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(prepositionField.getValue());
            }
        }

        // OTHER LANGUAGE PREPOSITION O/O
        DataField<String> otherLanguageOtherLanguagePrepositionField =
            dataFields.getDataField(FieldKey.OTHER_LANGUAGE_PREPOSITION);

        if (isValid(otherLanguageOtherLanguagePrepositionField) || isUnset(FieldKey.OTHER_LANGUAGE_PREPOSITION, differences)) {
            DosageFormFile.OtherLanguagePreposition field = FACTORY.createDosageFormFileOtherLanguagePreposition();
            field.setNumber(new Float("5.1"));

            JAXBElement<DosageFormFile.OtherLanguagePreposition> element =
                FACTORY.createDosageFormFileOtherLanguagePreposition(field);
            dosageFormFile.setOtherLanguagePreposition(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_PREPOSITION, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(otherLanguageOtherLanguagePrepositionField.getValue());
            }
        }

        // NOUN (Multiple)
        // NOUN M/O
        // PACKAGE O/O
        // OTHER LANGUAGE NOUN O/O
        if (isValid(dosageFormVo.getDfNouns()) || isUnset(FieldKey.DF_NOUNS, differences)) {
            DosageFormFile.Noun field = FACTORY.createDosageFormFileNoun();
            field.setMultiple(true);
            field.setNumber(new Float("6f"));

            JAXBElement<DosageFormFile.Noun> element = FACTORY.createDosageFormFileNoun(field);

            dosageFormFile.setNoun(element);

            if (isUnset(FieldKey.DF_NOUNS, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (DosageFormNounVo dosageFormNounVo : dosageFormVo.getDfNouns()) {
                    field.getNounFile().add(toNounFile(dosageFormNounVo, differences, itemAction));
                }
            }
        }
    }

    /**
     * convertDispenseUnitsPerDose
     *
     * @param dataFields DataFields<DataField>
     * @param differences Map<FieldKey, Difference> differences
     * @param dosageFormFile DosageFormFile dosageFormFile
     * @param dosageFormVo DosageFormVo
     * @param itemAction ItemAction
     */
    private static void convertDispenseUnitsPerDose(DataFields<DataField> dataFields, Map<FieldKey, Difference> differences,
        DosageFormFile dosageFormFile, DosageFormVo dosageFormVo, ItemAction itemAction) {

        // DISPENSE UNITS PER DOSE (Multiple)
        // DISPENSE UNITS PER DOSE M/O
        // PACKAGE O/O
        if (isValid(dosageFormVo.getDfDispenseUnitsPerDose()) || isUnset(FieldKey.DF_DISPENSE_UNITS_PER_DOSE, differences)) {
            DosageFormFile.DispenseUnitsPerDose field = FACTORY.createDosageFormFileDispenseUnitsPerDose();
            field.setMultiple(true);
            field.setNumber(new Float("9"));

            JAXBElement<DosageFormFile.DispenseUnitsPerDose> element = FACTORY.createDosageFormFileDispenseUnitsPerDose(field);

            dosageFormFile.setDispenseUnitsPerDose(element);

            if (isUnset(FieldKey.DF_DISPENSE_UNITS_PER_DOSE, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (DispenseUnitPerDoseVo dispenseUnitPerDoseVo : dosageFormVo.getDfDispenseUnitsPerDose()) {
                    field.getDispenseUnitsPerDoseFile().add(
                        toDispenseUnitsPerDoseFile(dispenseUnitPerDoseVo, differences, itemAction));
                }
            }
        }
    }

    /**
     * convertConjunctionField
     *
     * @param dataFields DataFields<DataField>
     * @param differences Map<FieldKey, Difference> differences
     * @param dosageFormFile DosageFormFile dosageFormFile
     */
    private static void convertConjunctionField(DataFields<DataField> dataFields, Map<FieldKey, Difference> differences,
        DosageFormFile dosageFormFile) {

        // CONJUNCTION O/O
        DataField<String> conjunctionField = dataFields.getDataField(FieldKey.CONJUNCTION);

        if (isValid(conjunctionField) || isUnset(FieldKey.CONJUNCTION, differences)) {
            DosageFormFile.Conjunction field = FACTORY.createDosageFormFileConjunction();
            field.setNumber(new Float("10"));

            JAXBElement<DosageFormFile.Conjunction> element = FACTORY.createDosageFormFileConjunction(field);
            dosageFormFile.setConjunction(element);

            if (isUnset(FieldKey.CONJUNCTION, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(conjunctionField.getValue());
            }
        }
    }

    /**
     * MED ROUTE FOR DOSAGE FORM (Multiple)
     * 
     * @param localMedicationRouteVo Local Medication Route VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Med Route For Dosage Form File
     */
    private static MedRouteForDosageFormFile toMedRouteForDosageFormFile(LocalMedicationRouteVo localMedicationRouteVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        MedRouteForDosageFormFile medRouteForDosageFormFile = FACTORY.createMedRouteForDosageFormFile();
        medRouteForDosageFormFile.setNumber(new Float("50.6061"));

        // MED ROUTE FOR DOSAGE FORM M/O
        medRouteForDosageFormFile.setMedRouteForDosageForm(FACTORY.createMedRouteForDosageFormFileMedRouteForDosageForm());
        medRouteForDosageFormFile.getMedRouteForDosageForm().setNumber(PPSConstants.F0POINT01);
        medRouteForDosageFormFile.getMedRouteForDosageForm().setValue(localMedicationRouteVo.getAbbreviation());

        return medRouteForDosageFormFile;
    }

    /**
     * NOUN (Multiple)
     * 
     * @param dosageFormNounVo Dosage Form Noun VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Noun File
     */
    private static NounFile toNounFile(DosageFormNounVo dosageFormNounVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        NounFile nounFile = FACTORY.createNounFile();
        nounFile.setNumber(new Float("50.6066"));

        // NOUN M/O
        nounFile.setNoun(FACTORY.createNounFileNoun());
        nounFile.getNoun().setNumber(PPSConstants.F0POINT01);
        nounFile.getNoun().setValue(dosageFormNounVo.getNoun());

        // PACKAGE O/O
        if (isValid(dosageFormNounVo.getPackages()) || isUnset(FieldKey.POSSIBLE_DOSAGE_PACKAGE, differences)) {
            NounFile.Package field = FACTORY.createNounFilePackage();
            field.setNumber(1f);

            JAXBElement<NounFile.Package> element = FACTORY.createNounFilePackage(field);
            nounFile.setPackage(element);

            if (isUnset(FieldKey.PACKAGES, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (dosageFormNounVo.getPackages().size() == 2) {
                    field.setValue("IO");
                } else if (dosageFormNounVo.getPackages().contains(PPSConstants.INPATIENT)) {
                    field.setValue("I");
                } else {
                    field.setValue("O");
                }
            }
        }

        // OTHER LANGUAGE NOUN O/O
        if (isValid(dosageFormNounVo.getOtherLanguageNoun()) || isUnset(FieldKey.OTHER_LANGUAGE_NOUN, differences)) {
            NounFile.OtherLanguageNoun field = FACTORY.createNounFileOtherLanguageNoun();
            field.setNumber(new Float("3"));

            JAXBElement<NounFile.OtherLanguageNoun> element = FACTORY.createNounFileOtherLanguageNoun(field);
            nounFile.setOtherLanguageNoun(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_NOUN, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(dosageFormNounVo.getOtherLanguageNoun());
            }
        }

        return nounFile;

    }

    /**
     * UNITS (Multiple)
     * 
     * @param dosageFormUnitsVo Dosage Form Units VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Units File
     */
    private static UnitsFile toUnitsFile(DosageFormUnitVo dosageFormUnitsVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        UnitsFile unitsFile = FACTORY.createUnitsFile();
        unitsFile.setNumber(new Float("50.6068"));

        // UNITS M/O
        unitsFile.setUnits(FACTORY.createUnitsFileUnits());
        unitsFile.getUnits().setNumber(PPSConstants.F0POINT01);
        unitsFile.getUnits().setValue(dosageFormUnitsVo.getDrugUnit().getValue());

        // PACKAGE O/O
        if (isValid(dosageFormUnitsVo.getPackages()) || isUnset(FieldKey.PACKAGES, differences)) {
            UnitsFile.Package field = FACTORY.createUnitsFilePackage();
            field.setNumber(1f);

            JAXBElement<UnitsFile.Package> element = FACTORY.createUnitsFilePackage(field);
            unitsFile.setPackage(element);

            if (isUnset(FieldKey.PACKAGES, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (dosageFormUnitsVo.getPackages().size() == 2) {
                    field.setValue("IO");
                } else if (dosageFormUnitsVo.getPackages().contains(PPSConstants.INPATIENT)) {
                    field.setValue("I");
                } else {
                    field.setValue("O");
                }
            }
        }

        return unitsFile;

    }

    /**
     * DISPENSE UNITS PER DOSE (Multiple)
     * 
     * @param dispenseUnitsPerDoseVo Dispense Units Per Dose VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Dispense Units Per Dose File
     */
    private static DispenseUnitsPerDoseFile toDispenseUnitsPerDoseFile(DispenseUnitPerDoseVo dispenseUnitsPerDoseVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        DispenseUnitsPerDoseFile dispenseUnitsPerDoseFile = FACTORY.createDispenseUnitsPerDoseFile();
        dispenseUnitsPerDoseFile.setNumber(new Float("50.6069"));

        // DISPENSE UNITS PER DOSE M/O
        dispenseUnitsPerDoseFile.setDispenseUnitsPerDose(FACTORY.createDispenseUnitsPerDoseFileDispenseUnitsPerDose());
        dispenseUnitsPerDoseFile.getDispenseUnitsPerDose().setNumber(PPSConstants.F0POINT01);
        dispenseUnitsPerDoseFile.getDispenseUnitsPerDose().setValue(dispenseUnitsPerDoseVo.getStrDispenseUnitPerDose());

        // PACKAGE O/O
        if (isValid(dispenseUnitsPerDoseVo.getPackages()) || isUnset(FieldKey.PACKAGES, differences)) {
            DispenseUnitsPerDoseFile.Package field = FACTORY.createDispenseUnitsPerDoseFilePackage();
            field.setNumber(1f);

            JAXBElement<DispenseUnitsPerDoseFile.Package> element = FACTORY.createDispenseUnitsPerDoseFilePackage(field);
            dispenseUnitsPerDoseFile.setPackage(element);

            if (isUnset(FieldKey.PACKAGES, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (dispenseUnitsPerDoseVo.getPackages().size() == 2) {
                    field.setValue("IO");
                } else if (dispenseUnitsPerDoseVo.getPackages().contains(PPSConstants.INPATIENT)) {
                    field.setValue("I");
                } else {
                    field.setValue("O");
                }
            }
        }

        return dispenseUnitsPerDoseFile;
    }
}
