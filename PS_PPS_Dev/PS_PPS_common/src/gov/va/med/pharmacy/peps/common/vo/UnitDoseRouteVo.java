/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Unit dose route
 */
public class UnitDoseRouteVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String value;
    private String vuid;
    private String inactivationDate;

    /**
     * Description
     * 
     * @return id property of UnitDoseRouteVo
     */
    public String getId() {
        return id;
    }

    /**
     * Description
     * 
     * @param id id property  of UnitDoseRouteVo
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Description
     * 
     * @return value property of UnitDoseRouteVo
     */
    public String getValue() {
        return value;
    }

    /**
     * Description
     * 
     * @param value value property of UnitDoseRouteVo
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * Description
     * 
     * @return short string value of UnitDoseRouteVo
     */
    @Override
    public String toShortString() {
        return value;
    }

    /**
     * Description of UnitDoseRouteVo
     * 
     * @return vuid property
     */
    public String getVuid() {
        return vuid;
    }

    /**
     * setVuid of UnitDoseRouteVo 
     * 
     * @param vuid vuid property
     */
    public void setVuid(String vuid) {
        this.vuid = trimToEmpty(vuid);
    }

    /**
     * getInactivationDate of UnitDoseRouteVo
     * 
     * @return inactivationDate property
     */
    public String getInactivationDate() {
        return inactivationDate;
    }

    /**
     * setInactivationDate of UnitDoseRouteVo
     * 
     * @param inactivationDate inactivationDate property
     */
    public void setInactivationDate(String inactivationDate) {
        this.inactivationDate = inactivationDate;
    }
}
