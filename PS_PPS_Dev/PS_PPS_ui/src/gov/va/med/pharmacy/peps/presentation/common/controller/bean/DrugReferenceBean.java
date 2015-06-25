/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.vo.LanguageChoice;
import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;


/**
 * Retrieve data from the COTS drug data vendor.
 */
@Component("drugReferenceBean")
@Scope("prototype")
public class DrugReferenceBean {

    private static final long serialVersionUID = 1L;

    
    private PatientMedicationInstructionVo patientMedicationInstruction;

    
    private String gcnSequenceNumber;

    
    private LanguageChoice languageChoice = LanguageChoice.ENGLISH;

    /**
     * Description
     * 
     * @return patientMedicationInstruction property
     */
    public PatientMedicationInstructionVo getPatientMedicationInstruction() {
        return patientMedicationInstruction;
    }

    /**
     * Description
     * 
     * @param patientMedicationInstruction patientMedicationInstruction property
     */
    public void setPatientMedicationInstruction(PatientMedicationInstructionVo patientMedicationInstruction) {
        this.patientMedicationInstruction = patientMedicationInstruction;
    }

    /**
     * Description
     * 
     * @return gcnSequenceNumber property
     */
    public String getGcnSequenceNumber() {
        return gcnSequenceNumber;
    }

    /**
     * Description
     * 
     * @param gcnSequenceNumber gcnSequenceNumber property
     */
    public void setGcnSequenceNumber(String gcnSequenceNumber) {
        this.gcnSequenceNumber = gcnSequenceNumber;
    }

    /**
     * Description
     * 
     * @return languageChoice property
     */
    public LanguageChoice getLanguageChoice() {
        return languageChoice;
    }

    /**
     * Description
     * 
     * @param languageChoice languageChoice property
     */
    public void setLanguageChoice(LanguageChoice languageChoice) {
        this.languageChoice = languageChoice;
    }

}
