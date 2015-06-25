/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Consumer monograph data
 */
public class ConsumerMonographVo extends ValueObject {
    
    /**
     * MONOGRAPH_TYPE
     */
    public static final String MONOGRAPH_TYPE = "FDB-CE";
    
    /**
     * DISCLAIMER_SECTION
     */
    public static final String DISCLAIMER_SECTION = "Z";
    
    /**
     * TITLE_SECTION
     */
    public static final String TITLE_SECTION = "T";
    
    /**
     * MEDICAL_WARNING_SECTION
     */
    public static final String MEDICAL_WARNING_SECTION = "L";
    
    /**
     * HOW_OCCURS_SECTION
     */
    public static final String HOW_OCCURS_SECTION = "A";
    
    /**
     * WHAT_MIGHT_HAPPEN_SECTION
     */
    public static final String WHAT_MIGHT_HAPPEN_SECTION = "E";
    
    /**
     * WHAT_TO_DO_SECTION
     */
    public static final String WHAT_TO_DO_SECTION = "M";
    
    /**
     * REFERENCE_SECTION
     */
    public static final String REFERENCE_SECTION = "R";

    private static final long serialVersionUID = 1L;

    private boolean fdbMonographSourceType = true;
    private String disclaimer = "";
    private String monographTitle = "";
    private String medicalWarning = "";
    private String howOccurs = "";
    private String whatMightHappen = "";
    private String whatToDo = "";
    private List<String> references = new ArrayList<String>(0);

    /**
     * getDisclaimer
     * @return disclaimer property
     */
    public String getDisclaimer() {
        return disclaimer;
    }

    /**
     * setDisclaimer
     * @param disclaimer disclaimer property
     */
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    /**
     * getMonographTitle
     * @return monographTitle property
     */
    public String getMonographTitle() {
        return monographTitle;
    }

    /**
     * setMonographTitle
     * @param monographTitle monographTitle property
     */
    public void setMonographTitle(String monographTitle) {
        this.monographTitle = monographTitle;
    }

    /**
     * getMedicalWarning
     * @return medicalWarning property
     */
    public String getMedicalWarning() {
        return medicalWarning;
    }

    /**
     * setMedicalWarning
     * @param medicalWarning medicalWarning property
     */
    public void setMedicalWarning(String medicalWarning) {
        this.medicalWarning = medicalWarning;
    }

    /**
     * getHowOccurs
     * @return howOccurs property
     */
    public String getHowOccurs() {
        return howOccurs;
    }

    /**
     * setHowOccurs
     * @param howOccurs howOccurs property
     */
    public void setHowOccurs(String howOccurs) {
        this.howOccurs = howOccurs;
    }

    /**
     * getWhatMightHappen
     * @return whatMightHappen property
     */
    public String getWhatMightHappen() {
        return whatMightHappen;
    }

    /**
     * setWhatMightHappen
     * @param whatMightHappen whatMightHappen property
     */
    public void setWhatMightHappen(String whatMightHappen) {
        this.whatMightHappen = whatMightHappen;
    }

    /**
     * getWhatToDo
     * @return whatToDo property
     */
    public String getWhatToDo() {
        return whatToDo;
    }

    /**
     * setWhatToDo
     * @param whatToDo whatToDo property
     */
    public void setWhatToDo(String whatToDo) {
        this.whatToDo = whatToDo;
    }

    /**
     * getReferences
     * @return references property
     */
    public List<String> getReferences() {
        return references;
    }

    /**
     * setReferences
     * @param references references property
     */
    public void setReferences(List<String> references) {
        this.references = references;
    }

    /**
     * isFdbMonographSourceType
     * @return fdbMonographSourceType property
     */
    public boolean isFdbMonographSourceType() {
        return fdbMonographSourceType;
    }

    /**
     * setFdbMonographSourceType
     * @param fdbMonographSourceType fdbMonographSourceType property
     */
    public void setFdbMonographSourceType(boolean fdbMonographSourceType) {
        this.fdbMonographSourceType = fdbMonographSourceType;
    }
}
