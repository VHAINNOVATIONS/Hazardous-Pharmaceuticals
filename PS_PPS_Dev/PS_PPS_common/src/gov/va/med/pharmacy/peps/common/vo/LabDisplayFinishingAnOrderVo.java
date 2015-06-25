/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Lab display finishing an order
 */
public class LabDisplayFinishingAnOrderVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    
    private String value;

    /**
     * getId for LabDisplayFinishingAnOrderVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue for LabDisplayFinishingAnOrderVo.
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for LabDisplayFinishingAnOrderVo.
     * 
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for LabDisplayFinishingAnOrderVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue() + "<p>";
    }
}
