/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Ward group for Atc canister
 */
public class WardGroupForAtcVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    private String value;

    /**
     * the constructor
     */
    public WardGroupForAtcVo() {
        super();
    }

    /**
     * Description
     * 
     * 
     * @return id property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return value;
    }

    /**
     * Description
     * 
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * Description
     * 
     * 
     * @param value of String type
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * Description
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }
}
