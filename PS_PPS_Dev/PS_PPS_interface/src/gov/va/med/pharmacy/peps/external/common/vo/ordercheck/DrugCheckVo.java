/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Contains data about a single drug
 */
public class DrugCheckVo extends ValueObject {

    /** VA_CUSTOM_CATEGORY */
    public static final String VA_CUSTOM_CATEGORY = "VA";
    private static final long serialVersionUID = 1L;

    private double singleDoseAmount;
    private String doseUnit = "";
    private String doseRate = "";
    private String frequency = "";
    private long duration;
    private String durationRate = "";
    private String doseRoute = "";
    private String doseType = "";
    private String vuid = "";
    private String ien = "";
    private String orderNumber = "";
    private boolean prospective;
    private String gcnSeqNo = "";
    private boolean doseCheck;
    private String drugName = "";
    private DoseCheckType doseCheckType;

    /**
     * constructor
     */
    public DrugCheckVo() {
        super();
    }

    /**
     * getIen
     * @return ien property
     */
    public String getIen() {
        return ien;
    }

    /**
     * setIen
     * @param ien ien property
     */
    public void setIen(String ien) {
        this.ien = ien;
    }

    /**
     * getVuid
     * @return vuid property
     */
    public String getVuid() {
        return vuid;
    }

    /**
     * setVuid
     * @param vuid vuid property
     */
    public void setVuid(String vuid) {
        this.vuid = vuid;
    }

    /**
     * isProspective
     * @return prospective property
     */
    public boolean isProspective() {
        return prospective;
    }

    /**
     * setProspective
     * @param propsective prospective property
     */
    public void setProspective(boolean propsective) {
        this.prospective = propsective;
    }

    /**
     * getOrderNumber
     * @return orderNumber property
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * setOrderNumber
     * @param orderNumber orderNumber property
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * getDoseRate
     * @return doseRate property
     */
    public String getDoseRate() {
        return doseRate;
    }

    /**
     * setDoseRate
     * @param doseRate doseRate property
     */
    public void setDoseRate(String doseRate) {
        this.doseRate = doseRate;
    }

    /**
     * getDoseRoute
     * @return doseRoute property
     */
    public String getDoseRoute() {
        return doseRoute;
    }

    /**
     * setDoseRoute
     * @param doseRoute doseRoute property
     */
    public void setDoseRoute(String doseRoute) {
        this.doseRoute = doseRoute;
    }

    /**
     * getDoseUnit
     * @return doseUnit property
     */
    public String getDoseUnit() {
        return doseUnit;
    }

    /**
     * setDoseUnit
     * @param doseUnit doseUnit property
     */
    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    /**
     * getDuration
     * @return duration property
     */
    public long getDuration() {
        return duration;
    }

    /**
     * setDuration
     * @param duration duration property
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * getDurationRate
     * @return durationRate property
     */
    public String getDurationRate() {
        return durationRate;
    }

    /**
     * setDurationRate
     * @param durationRate durationRate property
     */
    public void setDurationRate(String durationRate) {
        this.durationRate = durationRate;
    }

    /**
     * getFrequency
     * @return frequency property
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * setFrequency
     * @param frequency frequency property
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * getSingleDoseAmount
     * @return singleDoseAmount property
     */
    public double getSingleDoseAmount() {
        return singleDoseAmount;
    }

    /**
     * setSingleDoseAmount
     * @param singleDoseAmount singleDoseAmount property
     */
    public void setSingleDoseAmount(double singleDoseAmount) {
        this.singleDoseAmount = singleDoseAmount;
    }

    /**
     * getDoseType
     * @return getDoseType property
     */
    public String getDoseType() {
        return doseType;
    }

    /**
     * setDoseType
     * @param type type property
     */
    public void setDoseType(String type) {
        this.doseType = type;
    }

    /**
     * getGcnSeqNo
     * @return gcnSeqNo property
     */
    public String getGcnSeqNo() {
        return gcnSeqNo;
    }

    /**
     * setGcnSeqNo
     * @param gcnSeqNo gcnSeqNo property
     */
    public void setGcnSeqNo(String gcnSeqNo) {
        this.gcnSeqNo = gcnSeqNo;
    }

    /**
     * isDoseCheck
     * @return doseCheck property
     */
    public boolean isDoseCheck() {
        return doseCheck;
    }

    /**
     * setDoseCheck
     * @param doseCheck doseCheck property
     */
    public void setDoseCheck(boolean doseCheck) {
        this.doseCheck = doseCheck;
    }

    /**
     * getDrugName
     * @return drugName property
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * setDrugName
     * @param drugName drugName property
     */
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    /**
     * getDoseCheckType
     * @return doseCheckType property
     */
    public DoseCheckType getDoseCheckType() {
        return doseCheckType;
    }

    /**
     * setDoseCheckType
     * @param doseCheckType doseCheckType property
     */
    public void setDoseCheckType(DoseCheckType doseCheckType) {
        this.doseCheckType = doseCheckType;
    }

    /**
     * isGeneralDoseCheck
     * @return true if this DrugCheckVO is a dose check and if it is of the general type
     */
    public boolean isGeneralDoseCheck() {
        return isDoseCheck() && doseCheckType != null && DoseCheckType.GENERAL.equals(doseCheckType);
    }

    /**
     * isSpecificDoseCheck
     * @return true if this DrugCheckVo is a dose check and if it is of the specific type
     */
    public boolean isSpecificDoseCheck() {
        return isDoseCheck() && doseCheckType != null && DoseCheckType.SPECIFIC.equals(doseCheckType);
    }
}
