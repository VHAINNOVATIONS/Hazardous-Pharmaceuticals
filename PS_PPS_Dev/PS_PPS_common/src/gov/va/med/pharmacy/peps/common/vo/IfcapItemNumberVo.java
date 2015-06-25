/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * IFCAP Item Number
 */
public class IfcapItemNumberVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    
    private String value;

    /**
     * getId.
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue.
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue.
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString.
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue();
    }
}
