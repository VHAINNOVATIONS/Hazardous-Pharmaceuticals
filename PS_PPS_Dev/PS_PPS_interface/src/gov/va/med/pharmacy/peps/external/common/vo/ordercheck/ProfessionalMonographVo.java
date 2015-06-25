/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Professional monograph data
 */
public class ProfessionalMonographVo extends ValueObject {

    /** MONOGRAPH_TYPE */
    public static final String MONOGRAPH_TYPE = "FDB-PE";

    /** DISCLAIMER_SECTION */
    public static final String DISCLAIMER_SECTION = "Z";

    /** TITLE_SECTION */
    public static final String TITLE_SECTION = "T";

    /** SEVERITY_SECTION */
    public static final String SEVERITY_SECTION = "L";

    /** MECHANISM_SECTION */
    public static final String MECHANISM_SECTION = "A";

    /** CLINICAL_SECTION */
    public static final String CLINICAL_SECTION = "E";

    /** PREDISPOSING_SECTION */
    public static final String PREDISPOSING_SECTION = "P";

    /** PATIENT_SECTION */
    public static final String PATIENT_SECTION = "M";

    /** DISCUSSION_SECTION */
    public static final String DISCUSSION_SECTION = "D";

    /** REFERENCES_SECTION */
    public static final String REFERENCES_SECTION = "R";

    private static final long serialVersionUID = 1L;

    private boolean fdbMonographSourceType = true;
    private String disclaimer = "";
    private String monographTitle = "";
    private String severityLevel = "";
    private String mechanismOfAction = "";
    private String clinicalEffects = "";
    private String predisposingFactors = "";
    private String patientManagement = "";
    private String discussion = "";
    private List<String> references = new ArrayList<String>(0);

    /**
     * description
     * @return disclaimer property
     */
    public String getDisclaimer() {
        return disclaimer;
    }

    /**
     * description
     * @param disclaimer disclaimer property
     */
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    /**
     * description
     * @return monographTitle property
     */
    public String getMonographTitle() {
        return monographTitle;
    }

    /**
     * description
     * @param monographTitle monographTitle property
     */
    public void setMonographTitle(String monographTitle) {
        this.monographTitle = monographTitle;
    }

    /**
     * description
     * @return severityLevel property
     */
    public String getSeverityLevel() {
        return severityLevel;
    }

    /**
     * description
     * @param severityLevel severityLevel property
     */
    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    /**
     * description
     * @return mechanismOfAction property
     */
    public String getMechanismOfAction() {
        return mechanismOfAction;
    }

    /**
     * description
     * @param mechanismOfAction mechanismOfAction property
     */
    public void setMechanismOfAction(String mechanismOfAction) {
        this.mechanismOfAction = mechanismOfAction;
    }

    /**
     * description
     * @return clinicalEffects property
     */
    public String getClinicalEffects() {
        return clinicalEffects;
    }

    /**
     * description
     * @param clinicalEffects clinicalEffects property
     */
    public void setClinicalEffects(String clinicalEffects) {
        this.clinicalEffects = clinicalEffects;
    }

    /**
     * description
     * @return predisposingFactors property
     */
    public String getPredisposingFactors() {
        return predisposingFactors;
    }

    /**
     * description
     * @param predisposingFactors predisposingFactors property
     */
    public void setPredisposingFactors(String predisposingFactors) {
        this.predisposingFactors = predisposingFactors;
    }

    /**
     * description
     * @return patientManagement property
     */
    public String getPatientManagement() {
        return patientManagement;
    }

    /**
     * description
     * @param patientManagement patientManagement property
     */
    public void setPatientManagement(String patientManagement) {
        this.patientManagement = patientManagement;
    }

    /**
     * description
     * @return discussion property
     */
    public String getDiscussion() {
        return discussion;
    }

    /**
     * description
     * @param discussion discussion property
     */
    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    /**
     * description
     * @return references property
     */
    public List<String> getReferences() {
        return references;
    }

    /**
     * description
     * @param references references property
     */
    public void setReferences(List<String> references) {
        this.references = references;
    }

    /**
     * description
     * @return fdbMonographSourceType property
     */
    public boolean isFdbMonographSourceType() {
        return fdbMonographSourceType;
    }

    /**
     * description
     * @param fdbMonographSourceType fdbMonographSourceType property
     */
    public void setFdbMonographSourceType(boolean fdbMonographSourceType) {
        this.fdbMonographSourceType = fdbMonographSourceType;
    }
}
