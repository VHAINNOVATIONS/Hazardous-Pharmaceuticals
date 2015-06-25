/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing a NDC's package size.
 */
public class PackageSizeVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String value;
    private String vuid;

    /**
     * getId.
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for PackageSizeVo
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getValue for PackageSizeVo.
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for PackageSizeVo.
     * 
     * @param value value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for PackageSizeVo.
     * 
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }

    /**
     * getVuid
     * 
     * @return vuid property
     */
    public String getVuid() {
        return vuid;
    }

    /**
     * setVuid
     * 
     * @param vuid vuid property
     */
    public void setVuid(String vuid) {
        this.vuid = trimToEmpty(vuid);
    }
}
