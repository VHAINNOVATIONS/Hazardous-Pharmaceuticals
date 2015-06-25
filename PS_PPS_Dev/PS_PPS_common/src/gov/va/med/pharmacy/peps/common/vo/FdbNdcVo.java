/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an FDB NDC data
 */
public class FdbNdcVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String dosageForm;
    private String federalDeaClassCode;
    private String federalLegendCode;
    private String formatCode;
    private Long gcnSeqno;
    private String labelerName;
    private String ndc;
    private Long ndcIdFk;
    private String ndcFormatted;
    private String obsoleteDate;
    private String packageDescription;
    private Double packageSize;
    private String previousNdc;
    private String replacementNdc;
    private Double strengthNumeric;
    private String strengthUnit;
    private String multiSourceCode;
    private String tradeName;

    /**
     * the constructor
     */
    public FdbNdcVo() {
        super();
    }

    /**
     * setNdcIdFk.
     * @param ndcIdFk point to tradeName
     */
    public void setNdcIdFk(Long ndcIdFk) {
        this.ndcIdFk = ndcIdFk;
    }
    
    /**
     * getNdcIdFk.
     * @return ndcIdFk  
     */
    public Long getNdcIdFk() {
        return ndcIdFk;
    }

    
    
    /**
     * getMultiSourceCode.
     * @return multiSourceCode  
     */
    public String getMultiSourceCode() {
        return multiSourceCode;
    }

    /**
     * setTradeName.
     * @param tradeName point to tradeName
     */
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
    
    /**
     * getTradeName.
     * @return tradeName  
     */
    public String getTradeName() {
        return tradeName;
    }

    /**
     * setMultiSourceCode.
     * @param multiSourceCode point to multiSourceCode
     */
    public void setMultiSourceCode(String multiSourceCode) {
        this.multiSourceCode = multiSourceCode;
    }
    
    
    /**
     * getDosageForm.
     * @return dosageForm  
     */
    public String getDosageForm() {
        return dosageForm;
    }

    /**
     * setDosageForm.
     * @param dosageForm point to dosage form
     */
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    /**
     * getFederalDeaClassCode.
     * @return federalDeaClassCode
     */
    public String getFederalDeaClassCode() {
        return federalDeaClassCode;
    }

    /**
     * setFederalDeaClassCode.
     * @param federalDeaClassCode DEA Code
     */
    public void setFederalDeaClassCode(String federalDeaClassCode) {
        this.federalDeaClassCode = federalDeaClassCode;
    }

    /**
     * getFederalLegendCode.
     * @return federalLegendCode
     */
    public String getFederalLegendCode() {
        return federalLegendCode;
    }

    /**
     * setFederalLegendCode.
     * @param federalLegendCode notes
     */
    public void setFederalLegendCode(String federalLegendCode) {
        this.federalLegendCode = federalLegendCode;
    }

    /**
     * getFormatCode.
     * @return formatCode
     */
    public String getFormatCode() {
        return formatCode;
    }

    /**
     * setFormatCode.
     * @param formatCode format style
     */
    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    /**
     * getGcnSeqno.
     * @return gcnSeqno 
     */
    public Long getGcnSeqno() {
        return gcnSeqno;
    }

    /**
     * setGcnSeqno.
     * @param gcnSeqno GCN Seq Number
     */
    public void setGcnSeqno(Long gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    /**
     * getLabelerName.
     * @return labelerName
     */
    public String getLabelerName() {
        return labelerName;
    }

    /**
     * setLabelerName.
     * @param labelerName Labeler Name
     */
    public void setLabelerName(String labelerName) {
        this.labelerName = labelerName;
    }

    /**
     * getNdc.
     * @return ndc 
     */
    public String getNdc() {
        return ndc;
    }

    /**
     * setNdc.
     * @param ndc ID number
     */
    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    /**
     * getNdcFormatted.
     * @return ndcFormatted
     */
    public String getNdcFormatted() {
        return ndcFormatted;
    }

    /**
     * setNdcFormatted.
     * @param ndcFormatted format for NDC
     */
    public void setNdcFormatted(String ndcFormatted) {
        this.ndcFormatted = ndcFormatted;
    }

    /**
     * getObsoleteDate.
     * @return obsoleteDate
     */
    public String getObsoleteDate() {
        return obsoleteDate;
    }

    /**
     * setObsoleteDate.
     * @param obsoleteDate Product obsolete date
     */
    public void setObsoleteDate(String obsoleteDate) {
        this.obsoleteDate = obsoleteDate;
    }

    /**
     * getPackageDescription.
     * @return packageDescription
     */
    public String getPackageDescription() {
        return packageDescription;
    }

    /**
     * setPackageDescription.
     * @param packageDescription package description
     */
    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    /**
     * getPackageSize.
     * @return packageSize
     */
    public Double getPackageSize() {
        return packageSize;
    }

    /**
     * setPackageSize.
     * @param packageSize size of package
     */
    public void setPackageSize(Double packageSize) {
        this.packageSize = packageSize;
    }

    /**
     * getPreviousNdc.
     * @return previousNdc
     */
    public String getPreviousNdc() {
        return previousNdc;
    }

    /**
     * setPreviousNdc.
     * @param previousNdc NDC before this one
     */
    public void setPreviousNdc(String previousNdc) {
        this.previousNdc = previousNdc;
    }

    /**
     * getReplacementNdc.
     * @return replacementNdc
     */
    public String getReplacementNdc() {
        return replacementNdc;
    }

    /**
     * setReplacementNdc.
     * @param replacementNdc substitute NDC number
     */
    public void setReplacementNdc(String replacementNdc) {
        this.replacementNdc = replacementNdc;
    }

    /**
     * getStrengthNumeric.
     * @return strengthNumeric
     */
    public Double getStrengthNumeric() {
        return strengthNumeric;
    }

    /**
     * setStrengthNumeric.
     * @param strengthNumeric strength of product
     */
    public void setStrengthNumeric(Double strengthNumeric) {
        this.strengthNumeric = strengthNumeric;
    }

    /**
     * getStrengthUnit.
     * @return strengthUnit
     */
    public String getStrengthUnit() {
        return strengthUnit;
    }

    /**
     * setStrengthUnit.
     * @param strengthUnit strength of dosage
     */
    public void setStrengthUnit(String strengthUnit) {
        this.strengthUnit = strengthUnit;
    }

    /**
     * returns the domain group of the managed Data for this FdbNdcVo
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {
        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for this FdbNdcVo
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain for this FdbNdcVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for this FdbNdcVo
     * 
     * @return boolean
     */
    public boolean isNdf() {
        return true;
    }

}
