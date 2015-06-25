/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a NDC's shape.
 */
public class ShapeVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String value;

    /**
     * Description
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * Description
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Description
     * 
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * Description
     * 
     * @param value value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for the ShapeVo.
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }
    
    /**
     * Added specific code for null value objects to = others
     * 
     * @param other object to compare too
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        
        if (other instanceof ShapeVo) {
            return super.equals(other);
        }
        
        if ((this.value == null || this.value.length() == 0)  && other == null) {
            return true;
        }
        
        return false;
    }
    
    /**
     * The hashcode method for the ShaprVo()
     * 
     * @return ShapeVo hashCode
     * 
     */
    @Override
    public int hashCode() {
        
        int hashCode = super.hashCode();
        
        return hashCode;
    }
}
