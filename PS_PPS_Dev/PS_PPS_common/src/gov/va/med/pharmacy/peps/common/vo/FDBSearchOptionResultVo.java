/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * This class holds individual results from the FDB Search Option
 */
public class FDBSearchOptionResultVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    
    //Packaged Drug objects
    private String strAdditionalDescriptor;
    private String strDEACode;
    private String strDosageFormDescription;
    private String strDrugClassification;
    private String strDrugFormCode;
    private String strDrugStrengthDescription;
    private String strGCNSeqNo;
    private String strGenericName;

    private String strLabelName; 
    private String strLabelName25;
    private String strManufacturerDistributorName;
    private String strNDC; 
    private String strNDCFormatCode;
    private String strObsoleteDate;
    private String strPackageDescription;
    private String strPackageSize;
    private String strPreviousNDC;
    private String strReplacementNDC;
    private String strTop200Rank;
    private String strTop50GenRank;
    private String strUnitDoseIndicator;
    private String strColor;
    private String strShape;
    private String strFlavor;
    private String strOTCIndicator;
    private String strDuplicateTherapies;
    
    private String strTradeName;
    private String strFederalLegendCode;
    private String strScored;
    private String strImprint1;
    private String strImprint2;
    private String strTenDigitNdc;
    
    //Dispensable Drug object
    private String strBrandName;
    private SingleMultiSourceProductVo singleMultiSourceProductVo;
  
    /**
     * Returns the singleMultiSourceProductVo Value
     * 
     * @return The singleMultiSourceProductVo Value set in the object
     */
    public SingleMultiSourceProductVo getSingleMultiSourceProductVo() {
        return singleMultiSourceProductVo;
    }

    /**
     * Sets the SingleMultiSourceProductVo Value in this object
     * 
     * @param singleMultiSourceProductVo This is the SingleMultiSourceProductVo Value to set in this object
     */
    public void setSingleMultiSourceProductVo(SingleMultiSourceProductVo singleMultiSourceProductVo) {
        this.singleMultiSourceProductVo = singleMultiSourceProductVo;
    }
    
    /**
     * Returns the NDC Value
     * 
     * @return The NDC Value set in the object
     */
    public String getNDC() {
        return strNDC;
    }

    /**
     * Returns the NDCFormatCode Value
     * 
     * @return The NDCFormatCode Value set in the object
     */
    public String getNDCFormatCode() {
        return strNDCFormatCode;
    }

    /**
     * Returns the getStrTenDigitFormatIndicator Value
     * 
     * @return The strTenDigitNdc Value set in the object
     */
    public String getStrTenDigitNdc() {
        return strTenDigitNdc;
    }
    
    /**
     * sets the getStrTenDigitFormatIndicator Value
     * 
     * @param strTenDigitNdc1 Value set in the object
     */
    public void setStrTenDigitNdc(String strTenDigitNdc1) {
        this.strTenDigitNdc = strTenDigitNdc1;
    }
    
    
    /**
     * Returns the Brand Name Value
     * 
     * @return The Brand Name Value set in the object
     */
    public String getBrandName() { 
        return strBrandName;
    }
    
    /**
     * Returns the Generic Name Value
     * 
     * @return The Generic Name Value set in the object
     */
    public String getGenericName() {
        return strGenericName;
    }
    
    /**
     * Returns the GCNSeqNo Value
     * 
     * @return The GNCSeqNo Value set in the object
     */
    public String getGCNSeqNo() {
        return  strGCNSeqNo;
    }

    /**
     * Returns the Dosage Form Description Value
     * 
     * @return The Dosage Form Description Value set in the object
     */
    public String getDosageFormDescription() {
        return  strDosageFormDescription;
    }
    
    /**
     * Returns the Drug Classifications Value
     * 
     * @return The Drug Classification Value set in the object.
     */
    public String getDrugClassification() {
        return strDrugClassification;
    }

    /**
     * Returns the Drug Strength Description Value
     * 
     * @return The Drug Strength Description Value set in the object
     */
    public String getDrugStrengthDescription() {
        return  strDrugStrengthDescription;
    }

    /**
     * Returns the AdditionalDescriptor Value
     * 
     * @return The AdditionalDescriptor Value set in the object
     */
    public String getAdditionalDescriptor() {
        return  strAdditionalDescriptor;
    }

    /**
     * Returns the ManufacturerDistributor Value
     * 
     * @return The ManufacturerDistributor Value set in the object
     */
    public String getManufacturerDistributorName() {
        return  strManufacturerDistributorName;
    }

    /**
     * Returns the Package Description Value
     * 
     * @return The Package Description Value set in the object
     */
    public String getPackageDescription() {
        return  strPackageDescription;
    }

    /**
     * Returns the Package Size Value
     * 
     * @return The Package Size Value set in the object
     */
    public String getPackageSize() {
        return  strPackageSize;
    }

    /**
     * Returns the Obsolete Date Value
     * 
     * @return The Obsolete Date Value set in the object
     */
    public String getObsoleteDate() {
        return  strObsoleteDate;
    }

    /**
     * Returns the Label Name Value
     * 
     * @return The Label Name Value set in the object
     */
    public String getLabelName() {
        return  strLabelName;
    }

    /**
     * Returns the Label Name 25 Value
     * 
     * @return The Label Name 25 Value set in the object
     */
    public String getLabelName25() {
        return  strLabelName25;
    }
    
    /**
     * Returns the Unit Dose Indicator Value
     * 
     * @return The Unit Dose Indicator Value set in the object
     */
    public String getUnitDoseIndicator() {
        return  strUnitDoseIndicator;
    }

    /**
     * Returns the DEA Code Value
     * 
     * @return The DEA Code Value set in the object
     */
    public String getDEACode() {
        return strDEACode;
    }

    /**
     * Gets the Replacement NDC value from the object.
     * 
     * @return The Replacement NDC value from the object.
     */
    public String getReplacementNDC() {
        return strReplacementNDC;
    }
    
    /**
     * Gets the Previous NDC value from the object.
     * 
     * @return The Previous NDC value from the object.
     */
    public String getPreviousNDC() {
        return strPreviousNDC;
    }
    
    /**
     * Gets the Top 200 Rank value from the object.
     * 
     * @return The Top 200 Rank value from the object.
     */
    public String getTop200Rank() {
        return strTop200Rank;
    }
    
    /**
     * Gets the Top 50 Rank value from the object.
     * 
     * @return The Top 50 Rank value from the object.
     */  
    public String getTop50GenRank() {
        return strTop50GenRank;
    }
    
    /**
     * Gets the Color value from the object.
     * 
     * @return The Color value from the object.
     */
    public String getColor() {
        return strColor;
    }

    /**
     * Gets the Flavor value from the object.
     * 
     * @return The Flavor value from the object.
     */
    public String getFlavor() {
        return strFlavor;
    }

    /**
     * Gets the shape value from the object.
     * 
     * @return The shape value from the object.
     */
    public String getShape() {
        return strShape;
    }
    
    /**
     * Gets the drug form code
     * 
     * @return The drug form code
     */
    public String getDrugFormCode() {
        return strDrugFormCode;
    }
    
    /**
     * Gets the OTC Indicator  
     *  
     * @return the OTC Indicator
     */
    public String getOTCIndicator() {
        return this.strOTCIndicator;
    }
  
    /**
     * Gets the Duplicate Therapy drug classes  
     *  
     * @return the Duplicate Therapy drug classes
     */
    public String getDuplicateTherapies() {
        return this.strDuplicateTherapies;
    }
 
    /**
     * Sets the NDC Value in this object
     * 
     * @param importStrNDC This is the NDC Value to set in this object
     */
    public void setNDC(String importStrNDC) {
        this.strNDC = importStrNDC;
    }

    /**
     * Sets the NDCFormatCode Value in this object
     * 
     * @param importStrNDCFormatCode This is the NDC Format Code Value to set in this object
     */
    public void setNDCFormatCode(String importStrNDCFormatCode) {
        this.strNDCFormatCode = importStrNDCFormatCode;
    }
    
    /**
     * Sets the Brand Name Value in this object
     * 
     * @param strBrandNameIn This is the Brand Name Value to set in this object
     */
    public void setBrandName(String strBrandNameIn) {
        this.strBrandName = strBrandNameIn;
    }

    /**
     * Sets the Generic Name Value in this object
     * 
     * @param strGenericNameIn This is the Generic Name Value to set in this object
     */
    public void setGenericName(String strGenericNameIn) {
        this.strGenericName = strGenericNameIn;
    }

    /**
     * Sets the GCNSeqNo Value in this object
     * 
     * @param strGCNSeqNoIn This is the GCNSeqNo Value to set in this object
     */
    public void setGCNSeqNo(String strGCNSeqNoIn) {
        this.strGCNSeqNo = strGCNSeqNoIn;
    }

    /**
     * Sets the Dosage Form Description in this object
     * 
     * @param strDosageFormDescriptionIn This is the Dosage Form Description Value to set in this object
     */
    public void setDosageFormDescription(String strDosageFormDescriptionIn) {
        this.strDosageFormDescription = strDosageFormDescriptionIn;
    }

    /**
     * Sets the Drug Classification in this object
     * 
     * @param strDrugClassificationIn This is the Drug Classification value to set in this object.
     */
    public void setDrugClassification(String strDrugClassificationIn) {
        this.strDrugClassification = strDrugClassificationIn;
    }
    
    /**
     * Sets the Drug Strength Description Value in this object
     * 
     * @param strDrugStrengthDescriptionIn This is the Drug Strength Description Value to set in this object
     */
    public void setDrugStrengthDescription(String strDrugStrengthDescriptionIn) {
        this.strDrugStrengthDescription = strDrugStrengthDescriptionIn;
    }

    /**
     * Sets the AdditionalDescriptor Value in this object
     * 
     * @param strAdditionalDescriptorIn This is the Additional Descriptor Value to set in this object
     */
    public void setAdditionalDescriptor(String strAdditionalDescriptorIn) {
        this.strAdditionalDescriptor = strAdditionalDescriptorIn;
    }

    /**
     * Sets the ManufacturerDistributorName Value in this object
     * 
     * @param strManufacturerDistributorNameIn This is the ManufactorDistributorName Value to set in this object
     */
    public void setManufacturerDistributorName(String strManufacturerDistributorNameIn) {
        this.strManufacturerDistributorName = strManufacturerDistributorNameIn;
    }

    /**
     * Sets the Package Description Value in this object
     * 
     * @param strPackageDescriptionIn This is the Package Description Value to set in this object
     */
    public void setPackageDescription(String strPackageDescriptionIn) {
        this.strPackageDescription = strPackageDescriptionIn;
    }

    /**
     * Sets the Package Size Value in this object
     * 
     * @param packageSize This is the Package Size Value to set in this object
     */
    public void setPackageSize(String packageSize) {
        this.strPackageSize = packageSize;
    }

    /**
     * Sets the Obsolete Date Value in this object
     * 
     * @param obsoleteDate This is the Obsolete Date Value to set in this object
     */
    public void setObsoleteDate(String obsoleteDate) {
        this.strObsoleteDate = obsoleteDate;
    }

    /**
     * Sets the Label Name Value in this object
     * 
     * @param labelName This is the Label Name Value to set in this object
     */
    public void setLabelName(String labelName) {
        this.strLabelName = labelName;
    }
    
    /**
     * Sets the Label Name 25 Value in this object
     * 
     * @param labelName25 This is the Label Name 25 Value to set in this object
     */
    public void setLabelName25(String labelName25) {
        this.strLabelName25 = labelName25;
    }
    
    /**
     * Sets the Unit Dose Indicator Value in this object
     * 
     * @param unitDoseIndicator This is the Unit Dose Indicator Value to set in this object
     */
    public void setUnitDoseIndicator(String unitDoseIndicator) {
        this.strUnitDoseIndicator = unitDoseIndicator;
    }

    /**
     * Sets the DEA Code Value in this object
     * 
     * @param dEACode This is the DEA Code Value to set in this object
     */
    public void setDEACode(String dEACode) {
        this.strDEACode = dEACode;
    }

    /**
     * Sets the Replacement NDC value in this object
     *  
     * @param replacementNDC This is the Replacement NDC Value to set in this object.
     */
    public void setReplacementNDC(String replacementNDC) {
        this.strReplacementNDC = replacementNDC;
    }
    
    /**
     * Sets the Previous NDC value in this object
     * 
     * @param previousNDC This is the Previous NDC Value to set in this object.
     */
    public void setPreviousNDC(String previousNDC) {
        this.strPreviousNDC = previousNDC;
    }

    /**
     * Sets the Top 200 Rank value in this object
     * 
     * @param top200Rank This is the Top 200 Rank Value to set in this object.
     */
    public void setTop200Rank(String top200Rank) {
        this.strTop200Rank = top200Rank;
    }

    /**
     * Sets the Top 50 Rank value in this object
     * 
     * @param top50GenRank This is the Top 50 Rank Value to set in this object.
     */
    public void setTop50GenRank(String top50GenRank) {
        this.strTop50GenRank = top50GenRank;
    }
    

    /**
     * Sets the Color value in this object
     * 
     * @param color This is the Color Value to set in this object.
     */
    public void setColor(String color) {
        this.strColor = color;
    }

    /**
     * Sets the Shape value in this object
     * 
     * @param shape This is the Shape Value to set in this object.
     */
    public void setShape(String shape) {
        this.strShape = shape;
    }
    
    /**
     * Sets the Flavor value in this object
     * 
     * @param flavor This is the Flavor Value to set in this object.
     */
    public void setFlavor(String flavor) {
        this.strFlavor = flavor;
    }

    /**
     * Sets the Drug Form code value in this object
     * 
     * @param drugFormCode This is the Drug Form Code value to set in this object.
     */
    public void setDrugFormCode(String drugFormCode) {
        this.strDrugFormCode = drugFormCode;
    }
    
    /**
     * Sets the OTCIndicator value in this object
     * 
     * @param oTCIndicator This is the OTCIndicator value to set in this object.
     */
    public void setOTCIndicator(String oTCIndicator) {
        this.strOTCIndicator = oTCIndicator;
    }
    
    /**
     * Sets the Duplicate Therapy classes in this object
     * 
     * @param duplicateTherapies This is the Duplicate Therapy classes value to set in this object.
     */
    public void setDuplicateTherapies(String duplicateTherapies) {
        this.strDuplicateTherapies = duplicateTherapies;
    }

    /**
     * getStrTradeName.
     * @return the tradeName
     */
    public String getStrTradeName() {
        return strTradeName;
    }

    /**
     * setStrTradeName
     * @param tradeName the strTradeName to set
     */
    public void setStrTradeName(String tradeName) {
        this.strTradeName = tradeName;
    }

    /**
     * getStrFederalLegendCode.
     * @return the strFederalLegendCode
     */
    public String getStrFederalLegendCode() {
        return strFederalLegendCode;
    }

    /**
     * setStrFederalLegendCode
     * @param strFederalLegendCode the strFederalLegendCode to set
     */
    public void setStrFederalLegendCode(String strFederalLegendCode) {
        this.strFederalLegendCode = strFederalLegendCode;
    }
    
    
    /**
     * getStrScored
     * @return the strScored
     */
    public String getStrScored() {
        return strScored;
    }

    /**
     * setStrScored
     * @param strScored the strScored to set
     */
    public void setStrScored(String strScored) {
        this.strScored = strScored;
    }

    /**
     * getStrImprint1
     * @return the strImprint1
     */
    public String getStrImprint1() {
        return strImprint1;
    }

    /**
     * setStrImprint1
     * @param strImprint1 the strImprint1 to set
     */
    public void setStrImprint1(String strImprint1) {
        this.strImprint1 = strImprint1;
    }

    /**
     * getStrImprint2
     * @return the strImprint2
     */
    public String getStrImprint2() {
        return strImprint2;
    }

    /**
     * strImprint2
     * @param strImprint2 the strImprint2 to set
     */
    public void setStrImprint2(String strImprint2) {
        this.strImprint2 = strImprint2;
    }
}
