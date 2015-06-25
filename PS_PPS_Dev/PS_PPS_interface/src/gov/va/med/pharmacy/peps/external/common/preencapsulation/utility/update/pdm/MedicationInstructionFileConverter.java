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
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationinstruction.MedicationInstructionFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationinstruction.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationinstruction.WardFile;


/**
 * Converts a Medication Instruction VO to a Medication Instruction File.
 */
public class MedicationInstructionFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.VALUE, FieldKey.SYNONYM, FieldKey.EXPANSION, FieldKey.OTHER_LANGUAGE_EXPANSION, FieldKey.MED_ROUTE,
            FieldKey.SCHEDULE, FieldKey.INSTRUCTIONS, FieldKey.ADDITIONAL_INSTRUCTION, FieldKey.PLURAL,
            FieldKey.DEFAULT_ADMIN_TIMES, FieldKey.WARD_MULTIPLE, FieldKey.INTENDED_USE, FieldKey.FREQUENCY })));

    /** FACTORY */
    public static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private MedicationInstructionFileConverter() {
    }

    /**
     * Convert a Medication Instruction VO to a Medication Instruction File.
     * 
     * @param medicationInstructionVo Medication Instruction VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Medication INstruction File
     */
    public static MedicationInstructionFile toMedicationInstructionFile(MedicationInstructionVo medicationInstructionVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        MedicationInstructionFile medicationInstructionFile = FACTORY.createMedicationInstructionFile();
        medicationInstructionFile.setCandidateKey(FACTORY.createMedicationInstructionFileCandidateKey());
        medicationInstructionFile.setNumber(new Float("51"));

        // NAME M - Candidate Key
        medicationInstructionFile.getCandidateKey().setName(FACTORY.createNameKey());
        medicationInstructionFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, medicationInstructionVo.getValue()));
        medicationInstructionFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            medicationInstructionFile.setName(FACTORY.createNameKey());
            medicationInstructionFile.getName().setValue(medicationInstructionVo.getValue());
            medicationInstructionFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // SYNONYM O
        if (isValid(medicationInstructionVo.getMedInstructionSynonym())
            || isUnset(FieldKey.MED_INSTRUCTION_SYNONYM, differences)) {
            MedicationInstructionFile.Synonym field = FACTORY.createMedicationInstructionFileSynonym();
            field.setNumber(PPSConstants.F0POINT5);

            JAXBElement<MedicationInstructionFile.Synonym> element = FACTORY.createMedicationInstructionFileSynonym(field);

            medicationInstructionFile.setSynonym(element);

            if (isUnset(FieldKey.MED_INSTRUCTION_SYNONYM, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getMedInstructionSynonym());
            }
        }

        // EXPANSION M
        MedicationInstructionFile.Expansion expansion = FACTORY.createMedicationInstructionFileExpansion();
        medicationInstructionFile.setExpansion(expansion);
        medicationInstructionFile.getExpansion().setValue(medicationInstructionVo.getMedInstructionExpansion());
        medicationInstructionFile.getExpansion().setNumber(1f);

        // OTHER LANGUAGE EXPANSION O
        if (isValid(medicationInstructionVo.getOtherLanguageExpansion())
            || isUnset(FieldKey.OTHER_LANGUAGE_EXPANSION, differences)) {
            MedicationInstructionFile.OtherLanguageExpansion field = FACTORY
                .createMedicationInstructionFileOtherLanguageExpansion();
            field.setNumber(PPSConstants.F1POINT1);

            JAXBElement<MedicationInstructionFile.OtherLanguageExpansion> element = FACTORY
                .createMedicationInstructionFileOtherLanguageExpansion(field);

            medicationInstructionFile.setOtherLanguageExpansion(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_EXPANSION, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getOtherLanguageExpansion());
            }
        }

        // MED ROUTE O
        if (isValid(medicationInstructionVo.getLocalMedicationRoute())
            || isUnset(FieldKey.LOCAL_MEDICATION_ROUTE, differences)) {
            MedicationInstructionFile.MedRoute field = FACTORY.createMedicationInstructionFileMedRoute();
            field.setNumber(2f);

            JAXBElement<MedicationInstructionFile.MedRoute> element = FACTORY.createMedicationInstructionFileMedRoute(field);

            medicationInstructionFile.setMedRoute(element);

            if (isUnset(FieldKey.LOCAL_MEDICATION_ROUTE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getLocalMedicationRoute().getValue());
            }
        }

        // SCHEDULE O
        if (isValid(medicationInstructionVo.getMedInstructionSchedule())
            || isUnset(FieldKey.MED_INSTRUCTION_SCHEDULE, differences)) {
            MedicationInstructionFile.Schedule field = FACTORY.createMedicationInstructionFileSchedule();
            field.setNumber(new Float("3"));

            JAXBElement<MedicationInstructionFile.Schedule> element = FACTORY.createMedicationInstructionFileSchedule(field);

            medicationInstructionFile.setSchedule(element);

            if (isUnset(FieldKey.MED_INSTRUCTION_SCHEDULE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getMedInstructionSchedule());
            }
        }

        // INSTRUCTIONS O
        if (isValid(medicationInstructionVo.getInstructions()) || isUnset(FieldKey.INSTRUCTIONS, differences)) {
            MedicationInstructionFile.Instructions field = FACTORY.createMedicationInstructionFileInstructions();
            field.setNumber(new Float("4"));

            JAXBElement<MedicationInstructionFile.Instructions> element = FACTORY
                .createMedicationInstructionFileInstructions(field);

            medicationInstructionFile.setInstructions(element);

            if (isUnset(FieldKey.INSTRUCTIONS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getInstructions());
            }
        }

        // ADDITIONAL INSTRUCTIONS O
        if (isValid(medicationInstructionVo.getAdditionalInstruction())
            || isUnset(FieldKey.ADDITIONAL_INSTRUCTION, differences)) {
            MedicationInstructionFile.AdditionalInstructions field = FACTORY
                .createMedicationInstructionFileAdditionalInstructions();
            field.setNumber(new Float("5"));

            JAXBElement<MedicationInstructionFile.AdditionalInstructions> element = FACTORY
                .createMedicationInstructionFileAdditionalInstructions(field);

            medicationInstructionFile.setAdditionalInstructions(element);

            if (isUnset(FieldKey.ADDITIONAL_INSTRUCTION, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getAdditionalInstruction());
            }
        }

        // PLURAL O
        if (isValid(medicationInstructionVo.getPlural()) || isUnset(FieldKey.PLURAL, differences)) {
            MedicationInstructionFile.Plural field = FACTORY.createMedicationInstructionFilePlural();
            field.setNumber(new Float("9"));

            JAXBElement<MedicationInstructionFile.Plural> element = FACTORY.createMedicationInstructionFilePlural(field);

            medicationInstructionFile.setPlural(element);

            if (isUnset(FieldKey.PLURAL, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getPlural());
            }
        }

        // DEFAULT ADMIN TIMES O
        if (isValid(medicationInstructionVo.getDefaultAdminTimes()) || isUnset(FieldKey.DEFAULT_ADMIN_TIMES, differences)) {
            MedicationInstructionFile.DefaultAdminTimes field = FACTORY.createMedicationInstructionFileDefaultAdminTimes();
            field.setNumber(new Float("10"));

            JAXBElement<MedicationInstructionFile.DefaultAdminTimes> element = FACTORY
                .createMedicationInstructionFileDefaultAdminTimes(field);

            medicationInstructionFile.setDefaultAdminTimes(element);

            if (isUnset(FieldKey.DEFAULT_ADMIN_TIMES, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(medicationInstructionVo.getDefaultAdminTimes());
            }
        }

        // WARD (Multiple)
        // WARD M/O
        if (isValid(medicationInstructionVo.getMedInstructionWardMultiple())
            || isUnset(FieldKey.MED_INSTRUCTION_WARD_MULTIPLE, differences)) {
            MedicationInstructionFile.Ward field = FACTORY.createMedicationInstructionFileWard();
            field.setMultiple(true);
            field.setNumber(new Float("20"));

            JAXBElement<MedicationInstructionFile.Ward> element = FACTORY.createMedicationInstructionFileWard(field);

            medicationInstructionFile.setWard(element);

            if (isUnset(FieldKey.MED_INSTRUCTION_WARD_MULTIPLE, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (WardMultipleVo wardMultipleVo : medicationInstructionVo.getMedInstructionWardMultiple()) {
                    field.getWardFile().add(toWardFile(wardMultipleVo));
                }
            }
        }

        // INTENDED USE O/O
        if (isValid(medicationInstructionVo.getIntendedUse()) || isUnset(FieldKey.DEFAULT_ADMIN_TIMES, differences)) {
            MedicationInstructionFile.IntendedUse field = FACTORY.createMedicationInstructionFileIntendedUse();
            field.setNumber(new Float("30"));

            JAXBElement<MedicationInstructionFile.IntendedUse> element = FACTORY
                .createMedicationInstructionFileIntendedUse(field);

            medicationInstructionFile.setIntendedUse(element);

            if (isUnset(FieldKey.DEFAULT_ADMIN_TIMES, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (medicationInstructionVo.getIntendedUse() != null && medicationInstructionVo.getIntendedUse().size() > 0) {
                    if (medicationInstructionVo.getIntendedUse().size() == 2) {
                        field.setValue("2");
                    } else {
                        if (medicationInstructionVo.getIntendedUse().contains("OUTPATIENT")) {
                            field.setValue("0");
                        } else {
                            field.setValue("1");
                        }
                    }
                }

            }
        }

        // FREQUENCY (IN MINUTES) O
        if (isValid(medicationInstructionVo.getFrequency()) || isUnset(FieldKey.FREQUENCY, differences)) {
            MedicationInstructionFile.FrequencyInMinutes field = FACTORY.createMedicationInstructionFileFrequencyInMinutes();
            field.setNumber(new Float("31"));

            JAXBElement<MedicationInstructionFile.FrequencyInMinutes> element = FACTORY
                .createMedicationInstructionFileFrequencyInMinutes(field);

            medicationInstructionFile.setFrequencyInMinutes(element);

            if (isUnset(FieldKey.FREQUENCY, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(medicationInstructionVo.getFrequency()));
            }
        }

        return medicationInstructionFile;
    }

    /**
     * WARD (Multiple)
     * 
     * @param wardMultipleVo Ward Multiple VO
     * @return Ward File
     */
    private static WardFile toWardFile(WardMultipleVo wardMultipleVo) {

        WardFile wardFile = FACTORY.createWardFile();
        wardFile.setNumber(new Float("51.01"));

        // WARD M
        wardFile.setWard(FACTORY.createWardFileWard());
        wardFile.getWard().setValue(wardMultipleVo.getWardSelection().getValue());
        wardFile.getWard().setNumber(PPSConstants.F0POINT01);

        // DEFAULT ADMIN TIMES M
        wardFile.setDefaultAdministrationTimes(FACTORY.createWardFileDefaultAdministrationTimes());
        wardFile.getDefaultAdministrationTimes().setValue(wardMultipleVo.getWardAdminTimes());
        wardFile.getDefaultAdministrationTimes().setNumber(PPSConstants.F0POINT02);

        return wardFile;
    }
}
