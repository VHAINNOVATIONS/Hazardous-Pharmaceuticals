/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Drug monograph from the drug data vendor interface.
 */
public class DrugMonographVo extends ValueObject {

    private static final long serialVersionUID = 1L;
    
    // these are the fields of the drug monograph.
    private Long monographId;
    private String englishTitle;
    private String spanishTitle;
    private String englishBrandName;
    private String spanishBrandName;
    private String englishMissedDose;
    private String spanishMissedDose;
    private String englishPhonetics;
    private String spanishPhonetics;
    private String englishHowToTake;
    private String spanishHowToTake;
    private String englishDrugInteractions;
    private String spanishDrugInteractions;
    private String englishMedicalAlerts;
    private String spanishMedicalAlerts;
    private String englishNotes;
    private String spanishNotes;
    private String englishOverdose;
    private String spanishOverdose;
    private String englishPrecautions;
    private String spanishPrecautions;
    private String englishStorage;
    private String spanishStorage;
    private String englishSideEffects;
    private String spanishSideEffects;
    private String englishUses;
    private String spanishUses;
    private String englishWarnings;
    private String spanishWarnings;
    private String englishDisclaimer;
    private String spanishDisclaimer;

    /**
     * default constructor
     */
    public DrugMonographVo() {
    }
     
    
    /**
     * getMonographId
     * @return monographId
     */
    public Long getMonographId() {
        return this.monographId;
    }
    
    /**
     * setMonographID
     * @param monographId monographId
     */
    public void setMonographId(Long monographId) {
        this.monographId = monographId;
    }
    
    public String getEnglishTitle() {
        return this.englishTitle;
    }
    
    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }
    
    public String getSpanishTitle() {
        return this.spanishTitle;
    }
    
    public void setSpanishTitle(String spanishTitle) {
        this.spanishTitle = spanishTitle;
    }
    
    public String getEnglishBrandName() {
        return this.englishBrandName;
    }
    
    public void setEnglishBrandName(String englishBrandName) {
        this.englishBrandName = englishBrandName;
    }
    
    public String getSpanishBrandName() {
        return this.spanishBrandName;
    }
    
    public void setSpanishBrandName(String spanishBrandName) {
        this.spanishBrandName = spanishBrandName;
    }

    public String getEnglishMissedDose() {
        return this.englishMissedDose;
    }
    
    public void setEnglishMissedDose(String englishMissedDose) {
        this.englishMissedDose = englishMissedDose;
    }
    
    public String getSpanishMissedDose() {
        return this.spanishMissedDose;
    }
    
    public void setSpanishMissedDose(String spanishMissedDose) {
        this.spanishMissedDose = spanishMissedDose;
    }
        
    public String getEnglishPhonetics() {
        return this.englishPhonetics;
    }
    
    public void setEnglishPhonetics(String englishPhonetics) {
        this.englishPhonetics = englishPhonetics;
    }
    
    public String getSpanishPhonetics() {
        return this.spanishPhonetics;
    }
    
    public void setSpanishPhonetics(String spanishPhonetics) {
        this.spanishPhonetics = spanishPhonetics;
    }

    public String getEnglishHowToTake() {
        return this.englishHowToTake;
    }
    
    public void setEnglishHowToTake(String englishHowToTake) {
        this.englishHowToTake = englishHowToTake;
    }

    public String getSpanishHowToTake() {
        return this.spanishHowToTake;
    }
    
    public void setSpanishHowToTake(String spanishHowToTake) {
        this.spanishHowToTake = spanishHowToTake;
    }
    
    public String getEnglishDrugInteractions() {
        return this.englishDrugInteractions;
    }
    
    public void setEnglishDrugInteractions(String englishDrugInteractions) {
        this.englishDrugInteractions = englishDrugInteractions;
    }

    
    public String getSpanishDrugInteractions() {
        return this.spanishDrugInteractions;
    }
    
    public void setSpanishDrugInteractions(String spanishDrugInteractions) {
        this.spanishDrugInteractions = spanishDrugInteractions;
    }
    
    public String getEnglishMedicalAlerts() {
        return this.englishMedicalAlerts;
    }
    
    public void setEnglishMedicalAlerts(String englishMedicalAlerts) {
        this.englishMedicalAlerts = englishMedicalAlerts;
    }

    
    public String getSpanishMedicalAlerts() {
        return this.spanishMedicalAlerts;
    }
    
    public void setSpanishMedicalAlerts(String spanishMedicalAlerts) {
        this.spanishMedicalAlerts = spanishMedicalAlerts;
    }
    
    public String getEnglishNotes() {
        return this.englishNotes;
    }
    
    public void setEnglishNotes(String englishNotes) {
        this.englishNotes = englishNotes;
    }

    
    public String getSpanishNotes() {
        return this.spanishNotes;
    }
    
    public void setSpanishNotes(String spanishNotes) {
        this.spanishNotes = spanishNotes;
    }
    
    public String getEnglishOverdose() {
        return this.englishOverdose;
    }
    
    public void setEnglishOverdose(String englishOverdose) {
        this.englishOverdose = englishOverdose;
    }

    
    public String getSpanishOverdose() {
        return this.spanishOverdose;
    }
    
    public void setSpanishOverdose(String spanishOverdose) {
        this.spanishOverdose = spanishOverdose;
    }

    public String getEnglishPrecautions() {
        return this.englishPrecautions;
    }
    
    public void setEnglishPrecautions(String englishPrecautions) {
        this.englishPrecautions = englishPrecautions;
    }

    
    public String getSpanishPrecautions() {
        return this.spanishPrecautions;
    }
    
    public void setSpanishPrecautions(String spanishPrecautions) {
        this.spanishPrecautions = spanishPrecautions;
    }

    public String getEnglishStorage() {
        return this.englishStorage;
    }
    
    public void setEnglishStorage(String englishStorage) {
        this.englishStorage = englishStorage;
    }

    
    public String getSpanishStorage() {
        return this.spanishStorage;
    }
    
    public void setSpanishStorage(String spanishStorage) {
        this.spanishStorage = spanishStorage;
    }
    
    public String getEnglishSideEffects() {
        return this.englishSideEffects;
    }
    
    public void setEnglishSideEffects(String englishSideEffects) {
        this.englishSideEffects = englishSideEffects;
    }

    
    public String getSpanishSideEffects() {
        return this.spanishSideEffects;
    }
    
    public void setSpanishSideEffects(String spanishSideEffects) {
        this.spanishSideEffects = spanishSideEffects;
    }
   
    public String getEnglishUses() {
        return this.englishUses;
    }
    
    public void setEnglishUses(String englishUses) {
        this.englishUses = englishUses;
    }

    
    public String getSpanishUses() {
        return this.spanishUses;
    }
    
    public void setSpanishUses(String spanishUses) {
        this.spanishUses = spanishUses;
    }

    public String getEnglishWarnings() {
        return this.englishWarnings;
    }
    
    public void setEnglishWarnings(String englishWarnings) {
        this.englishWarnings = englishWarnings;
    }

    
    public String getSpanishWarnings() {
        return this.spanishWarnings;
    }
    
    public void setSpanishWarnings(String spanishWarnings) {
        this.spanishWarnings = spanishWarnings;
    }     

    public String getEnglishDisclaimer() {
        return this.englishDisclaimer;
    }
    
    public void setEnglishDisclaimer(String englishDisclaimer) {
        this.englishDisclaimer = englishDisclaimer;
    }

    
    public String getSpanishDisclaimer() {
        return this.spanishDisclaimer;
    }
    
    public void setSpanishDisclaimer(String spanishDisclaimer) {
        this.spanishDisclaimer = spanishDisclaimer;
    }

}
