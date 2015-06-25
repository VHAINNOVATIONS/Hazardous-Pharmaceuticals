/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a NDC's color.
 */
public class ColorVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 2L;

    private String id;
    private String value;

    /**
     * getId for ColorVo.
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for ColorVo
     * 
     * @param colorId colorId property
     */
    public void setId(String colorId) {
        this.id = colorId;
    }

    /**
     * getValue.
     * 
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue.
     * 
     * @param value value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * This is the colorVo short string method
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {
        
        return (getValue() == null ? "(Not specified)" : getValue());
    }
}
