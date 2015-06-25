/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Single or Multi Source Product
 */
public class SingleMultiSourceProductVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    
    private String value;

    /**
     * the constructor
     */
    public SingleMultiSourceProductVo() {
        super();
    }

    /**
     * getId.
     * 
     * @return id property
     */
    public String getId() {
        return value;
    }

    /**
     * getValue.
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue.
     * 
     * @param value of String type
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * Returns the singleMultiSource Value. 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }
}
