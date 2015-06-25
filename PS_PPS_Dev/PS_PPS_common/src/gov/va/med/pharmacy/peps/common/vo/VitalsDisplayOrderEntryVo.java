/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Vitals display order entry
 */
public class VitalsDisplayOrderEntryVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * getId for VitalsDisplayOrderEntryVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * The VitalsDisplayOrderEntryVo getValue method.
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * Description value for the VitalsDisplayOrderEntryVo
     * 
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for the VitalsDisplayOrderEntryVo
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue() + "<p>";
    }
}
