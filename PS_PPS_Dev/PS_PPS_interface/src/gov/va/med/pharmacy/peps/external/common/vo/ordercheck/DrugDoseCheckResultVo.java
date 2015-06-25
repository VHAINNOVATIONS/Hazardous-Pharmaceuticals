/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Results from a drug dose check
 */
public class DrugDoseCheckResultVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private DrugCheckVo drug;
    private String singleDoseStatus = "";
    private String singleDoseStatusCode = "";
    private String singleDoseMessage = "";
    private String singleDoseMax = "";
    private String rangeDoseStatus = "";
    private String rangeDoseStatusCode = "";
    private String rangeDoseMessage = "";
    private String rangeDoseLow = "";
    private String rangeDoseHigh = "";
    private String durationStatus = "";
    private String durationStatusCode = "";
    private String durationMessage = "";
    private String frequencyStatus = "";
    private String frequencyStatusCode = "";
    private String frequencyMessage = "";
    private String dailyDoseStatus = "";
    private String dailyDoseStatusCode = "";
    private String dailyDoseMessage = "";
    private String maxDailyDoseStatus = "";
    private String maxDailyDoseStatusCode = "";
    private String maxDailyDoseMessage = "";
    private String maxLifetimeDose = "";
    private String doseLow = "";
    private String doseLowUnit = "";
    private String doseHigh = "";
    private String doseHighUnit = "";
    private String doseRouteDescription = "";
    private String doseFormLow = "";
    private String doseFormLowUnit = "";
    private String doseFormHigh = "";
    private String doseFormHighUnit = "";

    /**
     * description
     */
    public DrugDoseCheckResultVo() {
        super();
    }

    /**
     * description
     * @return rangeDoseHigh property
     */
    public String getRangeDoseHigh() {
        return rangeDoseHigh;
    }

    /**
     * description
     * @param rangeDoseHigh rangeDoseHigh property
     */
    public void setRangeDoseHigh(String rangeDoseHigh) {
        this.rangeDoseHigh = rangeDoseHigh;
    }

    /**
     * description
     * @return rangeDoseLow property
     */
    public String getRangeDoseLow() {
        return rangeDoseLow;
    }

    /**
     * description
     * @param rangeDoseLow rangeDoseLow property
     */
    public void setRangeDoseLow(String rangeDoseLow) {
        this.rangeDoseLow = rangeDoseLow;
    }

    /**
     * description
     * @return rangeDoseMessage property
     */
    public String getRangeDoseMessage() {
        return rangeDoseMessage;
    }

    /**
     * description
     * @param rangeDoseMessage rangeDoseMessage property
     */
    public void setRangeDoseMessage(String rangeDoseMessage) {
        this.rangeDoseMessage = rangeDoseMessage;
    }

    /**
     * description
     * @return rangeDoseStatus property
     */
    public String getRangeDoseStatus() {
        return rangeDoseStatus;
    }

    /**
     * description
     * @param rangeDoseStatus rangeDoseStatus property
     */
    public void setRangeDoseStatus(String rangeDoseStatus) {
        this.rangeDoseStatus = rangeDoseStatus;
    }

    /**
     * description
     * @return rangeDoseStatusCode property
     */
    public String getRangeDoseStatusCode() {
        return rangeDoseStatusCode;
    }

    /**
     * description
     * @param rangeDoseStatusCode rangeDoseStatusCode property
     */
    public void setRangeDoseStatusCode(String rangeDoseStatusCode) {
        this.rangeDoseStatusCode = rangeDoseStatusCode;
    }

    /**
     * description
     * @return singleDoseMax property
     */
    public String getSingleDoseMax() {
        return singleDoseMax;
    }

    /**
     * description
     * @param singleDoseMax singleDoseMax property
     */
    public void setSingleDoseMax(String singleDoseMax) {
        this.singleDoseMax = singleDoseMax;
    }

    /**
     * description
     * @return singleDoseMessage property
     */
    public String getSingleDoseMessage() {
        return singleDoseMessage;
    }

    /**
     * description
     * @param singleDoseMessage singleDoseMessage property
     */
    public void setSingleDoseMessage(String singleDoseMessage) {
        this.singleDoseMessage = singleDoseMessage;
    }

    /**
     * description
     * @return singleDoseStatus property
     */
    public String getSingleDoseStatus() {
        return singleDoseStatus;
    }

    /**
     * description
     * @param singleDoseStatus singleDoseStatus property
     */
    public void setSingleDoseStatus(String singleDoseStatus) {
        this.singleDoseStatus = singleDoseStatus;
    }

    /**
     * description
     * @return singleDoseStatusCode property
     */
    public String getSingleDoseStatusCode() {
        return singleDoseStatusCode;
    }

    /**
     * description
     * @param singleDoseStatusCode singleDoseStatusCode property
     */
    public void setSingleDoseStatusCode(String singleDoseStatusCode) {
        this.singleDoseStatusCode = singleDoseStatusCode;
    }

    /**
     * description
     * @return DrugCheckVo property
     */
    public DrugCheckVo getDrug() {
        return drug;
    }

    /**
     * description
     * @param drugCheckVo DrugCheckVo property
     */
    public void setDrug(DrugCheckVo drugCheckVo) {
        this.drug = drugCheckVo;
    }

    /**
     * description
     * @return durationMessage property
     */
    public String getDurationMessage() {
        return durationMessage;
    }

    /**
     * description
     * @param durationMessage durationMessage property
     */
    public void setDurationMessage(String durationMessage) {
        this.durationMessage = durationMessage;
    }

    /**
     * description
     * @return durationStatus property
     */
    public String getDurationStatus() {
        return durationStatus;
    }

    /**
     * description
     * @param durationStatus durationStatus property
     */
    public void setDurationStatus(String durationStatus) {
        this.durationStatus = durationStatus;
    }

    /**
     * description
     * @return durationStatusCode property
     */
    public String getDurationStatusCode() {
        return durationStatusCode;
    }

    /**
     * description
     * @param durationStatusCode durationStatusCode property
     */
    public void setDurationStatusCode(String durationStatusCode) {
        this.durationStatusCode = durationStatusCode;
    }

    /**
     * description
     * @return frequencyMessage property
     */
    public String getFrequencyMessage() {
        return frequencyMessage;
    }

    /**
     * description
     * @param frequencyMessage frequencyMessage property
     */
    public void setFrequencyMessage(String frequencyMessage) {
        this.frequencyMessage = frequencyMessage;
    }

    /**
     * description
     * @return frequencyStatus property
     */
    public String getFrequencyStatus() {
        return frequencyStatus;
    }

    /**
     * description
     * @param frequencyStatus frequencyStatus property
     */
    public void setFrequencyStatus(String frequencyStatus) {
        this.frequencyStatus = frequencyStatus;
    }

    /**
     * description
     * @return frequencyStatusCode property
     */
    public String getFrequencyStatusCode() {
        return frequencyStatusCode;
    }

    /**
     * description
     * @param frequencyStatusCode frequencyStatusCode property
     */
    public void setFrequencyStatusCode(String frequencyStatusCode) {
        this.frequencyStatusCode = frequencyStatusCode;
    }

    /**
     * description
     * @return dailyDoseMessage property
     */
    public String getDailyDoseMessage() {
        return dailyDoseMessage;
    }

    /**
     * description
     * @param dailyDoseMessage dailyDoseMessage property
     */
    public void setDailyDoseMessage(String dailyDoseMessage) {
        this.dailyDoseMessage = dailyDoseMessage;
    }

    /**
     * description
     * @return dailyDoseStatus property
     */
    public String getDailyDoseStatus() {
        return dailyDoseStatus;
    }

    /**
     * description
     * @param dailyDoseStatus dailyDoseStatus property
     */
    public void setDailyDoseStatus(String dailyDoseStatus) {
        this.dailyDoseStatus = dailyDoseStatus;
    }

    /**
     * description
     * @return dailyDoseStatusCode property
     */
    public String getDailyDoseStatusCode() {
        return dailyDoseStatusCode;
    }

    /**
     * description
     * @param dailyDoseStatusCode dailyDoseStatusCode property
     */
    public void setDailyDoseStatusCode(String dailyDoseStatusCode) {
        this.dailyDoseStatusCode = dailyDoseStatusCode;
    }

    /**
     * description
     * @return maxDailyDoseMessage property
     */
    public String getMaxDailyDoseMessage() {
        return maxDailyDoseMessage;
    }

    /**
     * description
     * @param maxDailyDoseMessage maxDailyDoseMessage property
     */
    public void setMaxDailyDoseMessage(String maxDailyDoseMessage) {
        this.maxDailyDoseMessage = maxDailyDoseMessage;
    }

    /**
     * description
     * @return maxDailyDoseStatus property
     */
    public String getMaxDailyDoseStatus() {
        return maxDailyDoseStatus;
    }

    /**
     * description
     * @param maxDailyDoseStatus maxDailyDoseStatus property
     */
    public void setMaxDailyDoseStatus(String maxDailyDoseStatus) {
        this.maxDailyDoseStatus = maxDailyDoseStatus;
    }

    /**
     * description
     * @return maxDailyDoseStatusCode property
     */
    public String getMaxDailyDoseStatusCode() {
        return maxDailyDoseStatusCode;
    }

    /**
     * description
     * @param maxDailyDoseStatusCode maxDailyDoseStatusCode property
     */
    public void setMaxDailyDoseStatusCode(String maxDailyDoseStatusCode) {
        this.maxDailyDoseStatusCode = maxDailyDoseStatusCode;
    }

    /**
     * description
     * @return maxLifetimeDose property
     */
    public String getMaxLifetimeDose() {
        return maxLifetimeDose;
    }

    /**
     * description
     * @param maxLifetimeDose maxLifetimeDose property
     */
    public void setMaxLifetimeDose(String maxLifetimeDose) {
        this.maxLifetimeDose = maxLifetimeDose;
    }

    /**
     * description
     * @return doseLow property
     */
    public String getDoseLow() {
        return doseLow;
    }

    /**
     * description
     * @param doseLow doseLow property
     */
    public void setDoseLow(String doseLow) {
        this.doseLow = doseLow;
    }

    /**
     * description
     * @return doseLowUnit property
     */
    public String getDoseLowUnit() {
        return doseLowUnit;
    }

    /**
     * description
     * @param doseLowUnit doseLowUnit property
     */
    public void setDoseLowUnit(String doseLowUnit) {
        this.doseLowUnit = doseLowUnit;
    }

    /**
     * description
     * @return doseHigh property
     */
    public String getDoseHigh() {
        return doseHigh;
    }

    /**
     * description
     * @param doseHigh doseHigh property
     */
    public void setDoseHigh(String doseHigh) {
        this.doseHigh = doseHigh;
    }

    /**
     * description
     * @return doseHighUnit property
     */
    public String getDoseHighUnit() {
        return doseHighUnit;
    }

    /**
     * description
     * @param doseHighUnit doseHighUnit property
     */
    public void setDoseHighUnit(String doseHighUnit) {
        this.doseHighUnit = doseHighUnit;
    }

    /**
     * description
     * @return doseRouteDescription property
     */
    public String getDoseRouteDescription() {
        return doseRouteDescription;
    }

    /**
     * description
     * @param doseRouteDescription doseRouteDescription property
     */
    public void setDoseRouteDescription(String doseRouteDescription) {
        this.doseRouteDescription = doseRouteDescription;
    }

    /**
     * description
     * @return doseFormLow property
     */
    public String getDoseFormLow() {
        return doseFormLow;
    }

    /**
     * description
     * @param doseFormLow doseFormLow property
     */
    public void setDoseFormLow(String doseFormLow) {
        this.doseFormLow = doseFormLow;
    }

    /**
     * description
     * @return doseFormLowUnit property
     */
    public String getDoseFormLowUnit() {
        return doseFormLowUnit;
    }

    /**
     * description
     * @param doseFormLowUnit doseFormLowUnit property
     */
    public void setDoseFormLowUnit(String doseFormLowUnit) {
        this.doseFormLowUnit = doseFormLowUnit;
    }

    /**
     * description
     * @return doseFormHigh property
     */
    public String getDoseFormHigh() {
        return doseFormHigh;
    }

    /**
     * description
     * @param doseFormHigh doseFormHigh property
     */
    public void setDoseFormHigh(String doseFormHigh) {
        this.doseFormHigh = doseFormHigh;
    }

    /**
     * description
     * @return doseFormHighUnit property
     */
    public String getDoseFormHighUnit() {
        return doseFormHighUnit;
    }

    /**
     * description
     * @param doseFormHighUnit doseFormHighUnit property
     */
    public void setDoseFormHighUnit(String doseFormHighUnit) {
        this.doseFormHighUnit = doseFormHighUnit;
    }
}
