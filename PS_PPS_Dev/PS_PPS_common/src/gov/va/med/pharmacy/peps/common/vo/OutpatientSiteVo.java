/**
 * Source file created in 2007 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.common.vo;


/**
 *  Data representing an Outpatient Site
 */
public class OutpatientSiteVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * getId for OutpatientSiteVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue for OutpatientSiteVo.
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for OutpatientSiteVo.
     * 
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for OutpatientSiteVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue();
    }
}
