/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an OI's Schedule type
 */
public class OiScheduleTypeVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String code;
    private String type;

    /**
     * getId for OiScheduleTypeVo.
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for OiScheduleTypeVo.
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getValue for OiScheduleTypeVo.
     * 
     * @return value property
     */
    public String getValue() {
        if (code == null) {
            return "";
        }

        return getCode() + " - " + getType();
    }

    /**
     * toShortString for OiScheduleTypeVo.
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return getValue();
    }

    /**
     * getCode
     * 
     * @return code property
     */
    public String getCode() {
        return code;
    }

    /**
     * setCode
     * 
     * @param code code property
     */
    public void setCode(String code) {
        this.code = trimToEmpty(code);
    }

    /**
     * getType
     * 
     * @return type property
     */
    public String getType() {
        return type;
    }

    /**
     * setType
     * 
     * @param type type property
     */
    public void setType(String type) {
        this.type = trimToEmpty(type);
    }
}
