/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Vitals display administration
 */
public class VitalsDisplayAdministrationVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * Description
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue for VitalsDisplayAdministrationVo.
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for VitalsDisplayAdministrationVo.
     * 
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for VitalsDisplayAdministrationVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue() + "<p>";
    }
}
