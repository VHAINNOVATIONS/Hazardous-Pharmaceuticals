/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;

import org.apache.commons.lang.StringUtils;


/**
 * Data representing a SynonymVo.
 * 
 */
public class SynonymVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    @IgnoreEquals
    private Long id;

    private String synonymName;
    private String ndcCode;
    private String synonymVsn;
    private Double synonymDispenseUnitPerOrderUnit;
    private Double synonymPricePerDispenseUnit;
    private String synonymVendor;
    private Double synonymPricePerOrderUnit;
    private IntendedUseVo synonymIntendedUse;
    private OrderUnitVo synonymOrderUnit;

    /**
     * Description
     * 
     * @return synonymName property
     */
    public String getSynonymName() {
        return synonymName;
    }

    /**
     * Description
     * 
     * @param synonymName synonymName property
     */
    public void setSynonymName(String synonymName) {
        this.synonymName = trimToEmpty(synonymName);
    }

    /**
     * Description
     * 
     * @return ndcCode property
     */
    public String getNdcCode() {
        return ndcCode;
    }

    /**
     * Description
     * 
     * @param ndcCode ndcCode property
     */
    public void setNdcCode(String ndcCode) {
        this.ndcCode = trimToEmpty(ndcCode);
    }

    /**
     * Description
     * 
     * 
     * @return synonymVendorStockNumber property
     */
    public String getSynonymVsn() {
        return synonymVsn;
    }

    /**
     * Description
     * 
     * 
     * @param synonymVendorStockNumber property
     */
    public void setSynonymVsn(String synonymVendorStockNumber) {
        this.synonymVsn = trimToEmpty(synonymVendorStockNumber);
    }

    /**
     * Description
     * 
     * 
     * @return synonymDispenseUnitPerOrderUnit property
     */
    public Double getSynonymDispenseUnitPerOrderUnit() {
        return synonymDispenseUnitPerOrderUnit;
    }

    /**
     * Description
     * 
     * 
     * @param synonymDispenseUnitPerOrderUnit property
     */
    public void setSynonymDispenseUnitPerOrderUnit(Double synonymDispenseUnitPerOrderUnit) {
        this.synonymDispenseUnitPerOrderUnit = synonymDispenseUnitPerOrderUnit;
    }

    /**
     * Description
     * 
     * @return synonymPricePerDispenseUnit property
     */
    public Double getSynonymPricePerDispenseUnit() {
        return synonymPricePerDispenseUnit;
    }

    /**
     * Description
     * 
     * @param synonymPricePerDispenseUnit synonymPricePerDispenseUnit property
     */
    public void setSynonymPricePerDispenseUnit(Double synonymPricePerDispenseUnit) {
        this.synonymPricePerDispenseUnit = synonymPricePerDispenseUnit;
    }

    /**
     * Description
     * 
     * 
     * @return synonymVendor property
     */
    public String getSynonymVendor() {
        return synonymVendor;
    }

    /**
     * Description
     * 
     * 
     * @param synonymVendor property
     */
    public void setSynonymVendor(String synonymVendor) {
        this.synonymVendor = trimToEmpty(synonymVendor);
    }

    /**
     * Description
     * 
     * @return synonymPricePerOrderUnit property
     */
    public Double getSynonymPricePerOrderUnit() {
        return synonymPricePerOrderUnit;
    }

    /**
     * Description
     * 
     * @param synonymPricePerOrderUnit synonymPricePerOrderUnit property
     */
    public void setSynonymPricePerOrderUnit(Double synonymPricePerOrderUnit) {
        this.synonymPricePerOrderUnit = synonymPricePerOrderUnit;
    }

    /**
     * Description
     * 
     * 
     * @return productIntendedUse property
     */
    public IntendedUseVo getSynonymIntendedUse() {
        return synonymIntendedUse;
    }

    /**
     * Description
     * 
     * 
     * @param productIntendedUse property
     */
    public void setSynonymIntendedUse(IntendedUseVo productIntendedUse) {
        this.synonymIntendedUse = productIntendedUse;
    }

    /**
     * Description
     * 
     * @return synonymOrderUnit property
     */
    public OrderUnitVo getSynonymOrderUnit() {
        return synonymOrderUnit;
    }

    /**
     * Description
     * 
     * @param synonymOrderUnit synonymOrderUnit property
     */
    public void setSynonymOrderUnit(OrderUnitVo synonymOrderUnit) {
        this.synonymOrderUnit = synonymOrderUnit;
    }

    /**
     * Used for Item Audit History purposes
     * 
     * @return string
     */
    public String toIAHString() {

        return ("Name: " + this.synonymName + "<p>" + "NDC : " + this.ndcCode + "<p>" + "IntUse: " 
            + this.synonymIntendedUse.getValue() + "<p>" + "Vendor: " + this.synonymVendor + "<p>" + "VSN: "
            + this.synonymVsn + "<p>" + "OrderUnit: " + this.synonymOrderUnit.getValue() + "<p>" + "PPOU: "
            + this.synonymPricePerOrderUnit + "<p>" + "PPDU: " + this.synonymPricePerDispenseUnit);

    }

    /**
     * toShortString returns toString unless overridden in VO
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        String s1 = FieldKey.getLocalizedName(FieldKey.SYNONYM_NAME, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.NDC, Locale.getDefault());
        String s3 = FieldKey.getLocalizedName(FieldKey.SYNONYM_INTENDED_USE, Locale.getDefault());
        String s4 = FieldKey.getLocalizedName(FieldKey.VENDOR, Locale.getDefault());
        String s5 = FieldKey.getLocalizedAbbreviation(FieldKey.VENDOR_STOCK_NUMBER, Locale.getDefault());
        String s6 = FieldKey.getLocalizedName(FieldKey.ORDER_UNIT, Locale.getDefault());
        String s7 = FieldKey.getLocalizedAbbreviation(FieldKey.SYNONYM_PRICE_PER_ORDER_UNIT, Locale.getDefault());
        String s8 = FieldKey.getLocalizedAbbreviation(FieldKey.SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT, Locale.getDefault());
        String s9 = FieldKey.getLocalizedAbbreviation(FieldKey.SYNONYM_PRICE_PER_DISPENSE_UNIT, Locale.getDefault());

        return (s1 + ": " + this.synonymName + "<p>" + s2 + ": " + (ndcCode == null ? "(Not specified)" : ndcCode) + "<p>"
            + s3 + ": " + (synonymIntendedUse == null ? "" : synonymIntendedUse.getValue()) + "<p>" + s4 + ": "
            + (synonymVendor == null ? "" : synonymVendor) + "<p>" + s5 + ": " + (synonymVsn == null ? "" : synonymVsn)
            + "<p>" + s6 + ": " + (synonymOrderUnit == null ? "" : synonymOrderUnit.getValue()) + "<p>" + s7 + ": "
            + (synonymPricePerOrderUnit == null ? "" : synonymPricePerOrderUnit) + "<p>" + s8 + ": "
            + (synonymDispenseUnitPerOrderUnit == null ? "" : synonymDispenseUnitPerOrderUnit) + "<p>" + s9 + ": "
            + (synonymPricePerDispenseUnit == null ? "" : synonymPricePerDispenseUnit) + "<p>");

    }

    /**
     * Description
     * 
     * @return id property
     */
    public Long getId() {
        return id;
    }

    /**
     * Description
     * 
     * @param id id property
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**]
     * equals method
     * @param o ProductObject
     * @return true if the two are equal on EPLID
     */
    @Override
    public boolean equals(Object o) {
        SynonymVo synonym = (SynonymVo) o;
        
        boolean isEquals = true;
        
     // check Synonym Name
        if (isEquals) {
            if (StringUtils.isBlank(synonym.getSynonymName())) {
                if (StringUtils.isBlank(this.getSynonymName())) {
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (StringUtils.isBlank(this.getSynonymName())) {
                    isEquals = false;
                } else { 
                    isEquals = synonym.getSynonymName().equals(this.getSynonymName());
                }
            }
        }
        
        // check NDC Code
        if (isEquals) {
            if (StringUtils.isBlank(synonym.getNdcCode())) {
                if (StringUtils.isBlank(this.getNdcCode())) {
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (StringUtils.isBlank(this.getNdcCode())) {
                    isEquals = false;
                } else { 
                    isEquals = synonym.getNdcCode().equals(this.getNdcCode());
                }
            }
        }
        
        // check Vendor
        if (isEquals) {
            if (StringUtils.isBlank(synonym.getSynonymVendor())) {
                if (StringUtils.isBlank(this.getSynonymVendor())) {
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (StringUtils.isBlank(this.getSynonymVendor())) {
                    isEquals = false;
                } else { 
                    isEquals = synonym.getSynonymVendor().equals(this.getSynonymVendor());
                }
            }
        }
        
        // check VSN
        if (isEquals) {
            if (StringUtils.isBlank(synonym.getSynonymVsn())) {
                if (StringUtils.isBlank(this.getSynonymVsn())) {
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (StringUtils.isBlank(this.getSynonymVsn())) {
                    isEquals = false;
                } else { 
                    isEquals = synonym.getSynonymVsn().equals(this.getSynonymVsn());
                }
            }
        }

        // price per dispense unit
        if (isEquals) {
            if (this.getSynonymPricePerDispenseUnit() == null) {
                if (synonym.getSynonymPricePerDispenseUnit() == null) {
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (synonym.getSynonymPricePerDispenseUnit() == null) {
                    isEquals = false;
                } else {
                    isEquals = (this.getSynonymDispenseUnitPerOrderUnit().doubleValue() 
                        == synonym.getSynonymDispenseUnitPerOrderUnit().doubleValue());
                }
            }
        }
        
        // price per order unit
        if (isEquals) {
            if (this.getSynonymPricePerOrderUnit() == null) {
                if (synonym.getSynonymPricePerOrderUnit() == null) {
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (synonym.getSynonymPricePerOrderUnit() == null) {
                    isEquals = false;
                } else {
                    isEquals = (this.getSynonymPricePerOrderUnit().doubleValue() == 
                        synonym.getSynonymPricePerOrderUnit().doubleValue());
                }
            }
        }
        
        // getSynonymDispenseUnitPerOrderUnit
        if (isEquals) {
            if (this.getSynonymDispenseUnitPerOrderUnit() == null) {
                if (synonym.getSynonymDispenseUnitPerOrderUnit() == null){
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (synonym.getSynonymDispenseUnitPerOrderUnit() == null){
                    isEquals = false;
                } else {
                    isEquals = (this.getSynonymDispenseUnitPerOrderUnit().doubleValue() == synonym.getSynonymDispenseUnitPerOrderUnit().doubleValue());
                }
            }
        }

        // getSynonymIntendedUse
        if (isEquals) {
            if (this.getSynonymIntendedUse() == null) {
                if (synonym.getSynonymIntendedUse() == null){
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (synonym.getSynonymIntendedUse() == null){
                    isEquals = false;
                } else {
                    isEquals = (this.getSynonymIntendedUse().equals(synonym.getSynonymIntendedUse()));
                }
            }
        }

     // getSynonymOrderUnit
        if (isEquals) {
            if (this.getSynonymOrderUnit() == null) {
                if (synonym.getSynonymOrderUnit() == null){
                    isEquals = true;
                } else {
                    isEquals = false;
                }
            } else {
                if (synonym.getSynonymOrderUnit() == null){
                    isEquals = false;
                } else {
                    isEquals = (this.getSynonymOrderUnit().equals(synonym.getSynonymOrderUnit()));
                }
            }
        }

        return isEquals;
        
    }
    
    /**
     * HashCode  
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = PPSConstants.I175;
        result = PPSConstants.I37 * result + (getId() == null ? 0 : this.getId().hashCode());

        return result;
    }
}
