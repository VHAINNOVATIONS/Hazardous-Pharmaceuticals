/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a Drug Class's classification type.
 */
public class DrugClassificationTypeVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String value;

    /**
     * getId.
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId.
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getValue.
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue.
     * @param value value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString.
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }
}
