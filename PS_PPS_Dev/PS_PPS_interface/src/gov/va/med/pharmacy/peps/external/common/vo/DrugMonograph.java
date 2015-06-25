/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Drug monograph from the drug data vendor interface.
 */
public class DrugMonograph extends ValueObject {

    private static final long serialVersionUID = 1L;
    
    // these are the fields of the drug monograph.
    private String title;
    private String brandName;
    private String missedDose;
    private String phonetics;
    private String howToTake;
    private String drugInteractions;
    private String medicalAlerts;
    private String notes;
    private String overdose;
    private String precautions;
    private String storage;
    private String sideEffects;
    private String uses;
    private String warnings;
    private String disclaimer;

    /**
     * Create monograph.
     * 
     * @param title title
     * @param brandName common brand names
     * @param missedDose missed doses
     * @param phonetics phonetics
     * @param howToTake how to take
     * @param drugInteractions drug interactions
     * @param medicalAlerts medical alerts
     * @param notes notes
     * @param overdose overdose
     * @param precautions precautions
     * @param storage storage
     * @param sideEffects side effects
     * @param uses uses
     * @param warnings warnings
     * @param disclaimer disclaimer
     */
    public DrugMonograph(String title, String brandName, String missedDose, String phonetics, String howToTake,
                         String drugInteractions, String medicalAlerts, String notes, String overdose, String precautions,
                         String storage, String sideEffects, String uses, String warnings, String disclaimer) {

        this.title = title;
        this.brandName = brandName;
        this.missedDose = missedDose;
        this.phonetics = phonetics;
        this.howToTake = howToTake;
        this.drugInteractions = drugInteractions;
        this.medicalAlerts = medicalAlerts;
        this.notes = notes;
        this.overdose = overdose;
        this.precautions = precautions;
        this.storage = storage;
        this.sideEffects = sideEffects;
        this.uses = uses;
        this.warnings = warnings;
        this.disclaimer = disclaimer;
    }

    /**
     * Title.
     * 
     * @return title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Brand name.
     * 
     * @return brand name
     */
    public String getBrandName() {
        return this.brandName;
    }

    /**
     * Missed dose.
     * 
     * @return missed dose.
     */
    public String getMissedDose() {
        return this.missedDose;
    }

    /**
     * Phonetics.
     * 
     * @return phonetics
     */
    public String getPhonetics() {
        return this.phonetics;
    }

    /**
     * How to take.
     * 
     * @return how to take
     */
    public String getHowToTake() {
        return this.howToTake;
    }

    /**
     * Drug interactions.
     * 
     * @return drug interactions.
     */
    public String getDrugInteractions() {
        return this.drugInteractions;
    }

    /**
     * Medical alerts.
     * 
     * @return medical alerts
     */
    public String getMedicalAlerts() {
        return this.medicalAlerts;
    }

    /**
     * Notes.
     * 
     * @return notes
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Overdose.
     * 
     * @return overdose
     */
    public String getOverdose() {
        return this.overdose;
    }

    /**
     * Precautions.
     * 
     * @return precautions
     */
    public String getPrecautions() {
        return this.precautions;
    }

    /**
     * Storage.
     * 
     * @return storage
     */
    public String getStorage() {
        return this.storage;
    }

    /**
     * Side effects.
     * 
     * @return side effects
     */
    public String getSideEffects() {
        return this.sideEffects;
    }

    /**
     * Uses.
     * 
     * @return uses
     */
    public String getUses() {
        return this.uses;
    }

    /**
     * Warnings.
     * 
     * @return warnings
     */
    public String getWarnings() {
        return this.warnings;
    }

    /**
     * Disclaimer.
     * 
     * @return disclaimer
     */
    public String getDisclaimer() {
        return this.disclaimer;
    }
}
