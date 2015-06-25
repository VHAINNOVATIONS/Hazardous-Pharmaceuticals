/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an Administration Schedule's Type.
 */
public class AdministrationScheduleTypeVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    private static final String DASH = " - ";
    private String id;
    private String value;
    private String code;

    /**
     * getId.
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId in AdministrationScheduleTypeVo.
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getValue in AdministrationScheduleTypeVo.
     * @return value property
     */
    public String getValue() {
        return code + DASH + value;
    }

    /**
     * setValue in AdministrationScheduleTypeVo.
     * @param value value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString in AdministrationScheduleTypeVo.
     * @return short string value
     */
    @Override
    public String toShortString() {
        return code + DASH + value;
    }

    /**
     * toString.
     * @return short string value
     */
    @Override
    public String toString() {
        return code + DASH + value;
    }

    /**
     * getCode in AdministrationScheduleVo.
     * @return code property
     */
    public String getCode() {
        return code;
    }

    /**
     * setCode in AdministrationScheduleVo.
     * @param code code property
     */
    public void setCode(String code) {
        this.code = trimToEmpty(code);
    }

}
