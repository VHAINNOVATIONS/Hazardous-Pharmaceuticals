/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedicationInstructionDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.MedicationInstructionConverter;

import junit.framework.TestCase;


/**
 * Test the {@link MedicationInstructionConverter}
 */
public class MedicationInstructionConverterTest extends TestCase {

    private static final Long EPL_ID = 9999L;
    private static final Date INACTIVATION_DATE = new java.util.Date();

    private static final ItemStatus ITEM_STATUS = ItemStatus.INACTIVE;
    private static final RequestItemStatus REQUEST_STATUS = RequestItemStatus.APPROVED;
    private static final String REJECT_REASON_TEXT = "None";
    private static final RequestRejectionReason REQUEST_REJECT_REASON = RequestRejectionReason.ITEM_EXISTS;

    private static final String ADDITIONAL_INSTRUCTION = "additional_instruction";
    private static final String DEFAULT_ADMIN_TIMES = "default_admin_times";
    private static final String MED_INSTRUCTION_EXPANSION = "med_instruction_expansion";
    private static final Long FREQUENCY_IN_MINUTES = 30L;
    private static final String INSTRUCTIONS = "instructions";
    private static final String MED_INSTRUCTION_NAME = "MED_INSTRUCTION_NAME";
    private static final String MED_INSTRUCTION_OTHER_LANG_EXP = "Med_instruction_other_lang_exp";
    private static final String PLURAL = "Plural";
    private static final String MED_INSTRUCTION_SCHEDULE = "med_instruction_schedule";
    private static final String MED_INSTRUCTION_SYNONYM = "med_instruction_synonym";

    private static final String EQUAL = "should be equal";
    private static final long REVISION_NUMBER = 3L;

    private MedicationInstructionConverter medicationInstructionConverter = new MedicationInstructionConverter();

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private EplMedicationInstructionDo createDo() {
        EplMedicationInstructionDo dataDo = new EplMedicationInstructionDo();

        dataDo.setInactivationDate(INACTIVATION_DATE);
        dataDo.setItemStatus(ITEM_STATUS.toString());
        dataDo.setRequestStatus(REQUEST_STATUS.toString());
        dataDo.setRejectReasonText(REJECT_REASON_TEXT);
        dataDo.setRequestRejectReason(REQUEST_REJECT_REASON.toString());

        dataDo.setAdditionalInstruction(ADDITIONAL_INSTRUCTION);
        dataDo.setDefaultAdminTimes(DEFAULT_ADMIN_TIMES);
        dataDo.setMedInstructionExpansion(MED_INSTRUCTION_EXPANSION);
        dataDo.setFrequencyInMinutes(FREQUENCY_IN_MINUTES);
        dataDo.setInstructions(INSTRUCTIONS);
        dataDo.setMedInstructionName(MED_INSTRUCTION_NAME);
        dataDo.setMedInstructionOtherLangExp(MED_INSTRUCTION_OTHER_LANG_EXP);
        dataDo.setPlural(PLURAL);
        dataDo.setMedInstructionSchedule(MED_INSTRUCTION_SCHEDULE);
        dataDo.setMedInstructionSynonym(MED_INSTRUCTION_SYNONYM);

        dataDo.setEplId(EPL_ID);

        dataDo.setRevisionNumber(REVISION_NUMBER);

        return dataDo;
    }

    /**
     * Test conversion to value object
     */
    public void testToMedicationInstructionVoHasAllAttributes() {
        EplMedicationInstructionDo dataDo = createDo();
        MedicationInstructionVo objectVo = medicationInstructionConverter.convert(dataDo);

        assertEquals(EQUAL, INACTIVATION_DATE, objectVo.getInactivationDate());
        assertEquals(EQUAL, ITEM_STATUS, objectVo.getItemStatus());
        assertEquals(EQUAL, REQUEST_STATUS, objectVo.getRequestItemStatus());
        assertEquals(EQUAL, REJECT_REASON_TEXT, objectVo.getRejectionReasonText());
        assertEquals(EQUAL, REQUEST_REJECT_REASON, objectVo.getRequestRejectionReason());

        assertEquals(EQUAL, ADDITIONAL_INSTRUCTION, objectVo.getAdditionalInstruction());
        assertEquals(EQUAL, DEFAULT_ADMIN_TIMES, objectVo.getDefaultAdminTimes());
        assertEquals(EQUAL, MED_INSTRUCTION_EXPANSION, objectVo.getMedInstructionExpansion());
        assertEquals(EQUAL, FREQUENCY_IN_MINUTES, objectVo.getFrequency());
        assertEquals(EQUAL, INSTRUCTIONS, objectVo.getInstructions());
        assertEquals(EQUAL, MED_INSTRUCTION_NAME, objectVo.getValue());
        assertEquals(EQUAL, MED_INSTRUCTION_OTHER_LANG_EXP, objectVo.getOtherLanguageExpansion());
        assertEquals(EQUAL, PLURAL, objectVo.getPlural());
        assertEquals(EQUAL, MED_INSTRUCTION_SCHEDULE, objectVo.getMedInstructionSchedule());
        assertEquals(EQUAL, MED_INSTRUCTION_SYNONYM, objectVo.getMedInstructionSynonym());

        assertEquals(EQUAL, EPL_ID, new Long(objectVo.getId()));

        assertEquals(EQUAL, REVISION_NUMBER, objectVo.getRevisionNumber());
    }

    /**
     * 
     * @return EplSearchTemplateDo
     */
    private MedicationInstructionVo createVo() {
        MedicationInstructionVo objectVo = new MedicationInstructionVo();

       
        objectVo.setItemStatus(ITEM_STATUS);
        objectVo.setInactivationDate(INACTIVATION_DATE);
        objectVo.setRequestItemStatus(REQUEST_STATUS);
        objectVo.setRejectionReasonText(REJECT_REASON_TEXT);
        objectVo.setRequestRejectionReason(REQUEST_REJECT_REASON);
        objectVo.setAdditionalInstruction(ADDITIONAL_INSTRUCTION);
        objectVo.setDefaultAdminTimes(DEFAULT_ADMIN_TIMES);
        objectVo.setMedInstructionExpansion(MED_INSTRUCTION_EXPANSION);
        objectVo.setFrequency(new Long(FREQUENCY_IN_MINUTES));
        objectVo.setInstructions(INSTRUCTIONS);
        objectVo.setValue(MED_INSTRUCTION_NAME);
        objectVo.setOtherLanguageExpansion(MED_INSTRUCTION_OTHER_LANG_EXP);
        objectVo.setPlural(PLURAL);
        objectVo.setMedInstructionSchedule(MED_INSTRUCTION_SCHEDULE);
        objectVo.setMedInstructionSynonym(MED_INSTRUCTION_SYNONYM);

        objectVo.setId(EPL_ID.toString());

        objectVo.setRevisionNumber(REVISION_NUMBER);

        return objectVo;
    }

    /**
     * Test
     */
    public void testToDoEqualAttributes() {
        MedicationInstructionVo objectVo = createVo();
        EplMedicationInstructionDo dataDo = medicationInstructionConverter.convert(objectVo);
        assertEquals(EQUAL, INACTIVATION_DATE, dataDo.getInactivationDate());
        assertEquals(EQUAL, ITEM_STATUS.name(), dataDo.getItemStatus());
        assertEquals(EQUAL, REQUEST_STATUS.name(), dataDo.getRequestStatus());
        assertEquals(EQUAL, REJECT_REASON_TEXT, dataDo.getRejectReasonText());
        assertEquals(EQUAL, REQUEST_REJECT_REASON.name(), dataDo.getRequestRejectReason());

        assertEquals(EQUAL, ADDITIONAL_INSTRUCTION, dataDo.getAdditionalInstruction());
        assertEquals(EQUAL, DEFAULT_ADMIN_TIMES, dataDo.getDefaultAdminTimes());
        assertEquals(EQUAL, MED_INSTRUCTION_EXPANSION, dataDo.getMedInstructionExpansion());
        assertEquals(EQUAL, FREQUENCY_IN_MINUTES, dataDo.getFrequencyInMinutes());
        assertEquals(EQUAL, INSTRUCTIONS, dataDo.getInstructions());
        assertEquals(EQUAL, MED_INSTRUCTION_NAME, dataDo.getMedInstructionName());
        assertEquals(EQUAL, MED_INSTRUCTION_OTHER_LANG_EXP, dataDo.getMedInstructionOtherLangExp());
        assertEquals(EQUAL, PLURAL, dataDo.getPlural());
        assertEquals(EQUAL, MED_INSTRUCTION_SCHEDULE, dataDo.getMedInstructionSchedule());
        assertEquals(EQUAL, MED_INSTRUCTION_SYNONYM, dataDo.getMedInstructionSynonym());

        assertEquals(EQUAL, EPL_ID, new Long(dataDo.getEplId()));

        assertEquals(EQUAL, REVISION_NUMBER, dataDo.getRevisionNumber().longValue());
    }

}
