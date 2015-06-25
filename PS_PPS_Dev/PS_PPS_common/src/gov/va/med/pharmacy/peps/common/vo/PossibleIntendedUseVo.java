/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data Representing PossibleIntendedUse
 */
public class PossibleIntendedUseVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    private String value;

    /**
     * the constructor
     */
    public PossibleIntendedUseVo() {
        super();
    }

    /**
     * getId
     * 
     * @return id property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return value;
    }

    /**
     * getValue for the PossibleIntendedUseVo.
     * 
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for the PossibleIntendedUseVo.
     * 
     * @param value of String type
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }
}
