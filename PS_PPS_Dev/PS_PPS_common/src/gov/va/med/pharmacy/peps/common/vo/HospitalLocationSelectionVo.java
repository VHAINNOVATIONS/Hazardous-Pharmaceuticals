/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Hospital Location Selection
 */
public class HospitalLocationSelectionVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 4L;
    
    private String value;

    /**
     * getId for HospitalLocationSelectionVo.
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue for HospitalLocationSelectionVo.
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for HospitalLocationSelectionVo.
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for HospitalLocationSelectionVo.
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue();
    }
}
