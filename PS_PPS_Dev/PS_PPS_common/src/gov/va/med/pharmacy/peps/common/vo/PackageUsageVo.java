/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;




/**
 * Data representing a NDC's PackageUsage.
 */
public class PackageUsageVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String value;
    private String description;

    /**
     * getId for PackageUsageVo.
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for PackageUsageVo.
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Modified method to make the gui more  for PackageUsageVo.
     * 
     * @return value property
     */
    public String getValue() {
        return this.value + "-" + getDescription();
    }

    /**
     * setValue for PackageUseVo.
     * 
     * @param value value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * This is the Package Usage Short string method.
     *  
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        String s1 = getValue();

        return (s1);
    }
    
    /**
     * toShortString returns toString unless overridden in VO
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return toShortString();
    }

    /**
     * getDescription
     * 
     * @return description property
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDescription
     * 
     * @param description description property
     */
    public void setDescription(String description) {
        this.description = trimToEmpty(description);
    }
}
