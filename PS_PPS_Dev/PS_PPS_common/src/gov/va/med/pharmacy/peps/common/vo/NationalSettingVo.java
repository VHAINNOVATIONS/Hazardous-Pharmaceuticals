/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Data representing an Ingredient
 */
public class NationalSettingVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String keyName;
    private String stringValue;
    private Long integerValue;
    private Long decimalValue;
    private Boolean booleanValue;
    private Date dateValue;



    /**
     * the constructor
     */
    public NationalSettingVo() {

        super();
    }

    /**
     * getId
     * @return id id
     */
    public Long getId() {
    
        return id;
    }

    /**
     * setId
     * @param id id
     */
    public void setId(Long id) {
    
        this.id = id;
    }

    /**
     * getKeyName
     * @return keyName keyName
     */
    public String getKeyName() {
    
        return keyName;
    }

    /**
     * setKeyName
     * @param keyName keyName
     */
    public void setKeyName(String keyName) {
    
        this.keyName = keyName;
    }

    /**
     * getStringValue
     * @return stringValue stringValue
     */
    public String getStringValue() {
    
        return stringValue;
    }

    /**
     * setStringValue
     * @param stringValue stringValue
     */
    public void setStringValue(String stringValue) {
    
        this.stringValue = stringValue;
    }

    /**
     * getIntegerValue
     * @return integerValue integerValue
     */
    public Long getIntegerValue() {
    
        return integerValue;
    }

    /**
     * setIntegerValue
     * @param integerValue integerValue
     */
    public void setIntegerValue(Long integerValue) {
    
        this.integerValue = integerValue;
    }

    /**
     * getDecimalValue
     * @return decimalValue decimalValue
     */
    public Long getDecimalValue() {
    
        return decimalValue;
    }

    /**
     * setDecimalValue
     * @param decimalValue decimalValue
     */
    public void setDecimalValue(Long decimalValue) {
    
        this.decimalValue = decimalValue;
    }

    /**
     * getBooleanValue
     * @return booleanValue booleanValue
     */
    public Boolean getBooleanValue() {
    
        return booleanValue;
    }

    /**
     * setBooleanValue
     * @param booleanValue booleanValue
     */
    public void setBooleanValue(Boolean booleanValue) {
    
        this.booleanValue = booleanValue;
    }

    /**
     * getDateValue
     * @return dateValue dateValue
     */
    public Date getDateValue() {
    
        return dateValue;
    }

    /**
     * setDateValue
     * @param dateValue dateValue
     */
    public void setDateValue(Date dateValue) {
    
        this.dateValue = dateValue;
    }


    /**
     * getDomainGroup for NationalSettingsVo.
     *      * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_1;
    }

    /**
     * Returns true if the domain is standardized for NationalSettingsVo.
     * 
     * @return true
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain for NationalSettingsVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for NationalSettingsVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return true;
    }
}
