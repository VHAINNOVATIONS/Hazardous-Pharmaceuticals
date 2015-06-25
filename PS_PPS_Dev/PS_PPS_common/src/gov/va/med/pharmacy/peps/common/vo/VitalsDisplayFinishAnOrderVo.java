/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Vitals display finish an order
 */
public class VitalsDisplayFinishAnOrderVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * getId for the VitalsDisplayFinishAnOrderVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue for the VitalsDisplayFinishAnOrderVo.
     * 
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * The VitalsDisplayFinishingAnOrderVo setValue method
     * 
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * The VitalsDisplayFinishingAnOrderVo toShortString method
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue() + "<p>";
    }
}
