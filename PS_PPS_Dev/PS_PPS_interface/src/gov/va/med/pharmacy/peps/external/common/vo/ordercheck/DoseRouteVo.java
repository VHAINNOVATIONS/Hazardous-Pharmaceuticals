/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Data representing a dose route for a drug
 */
public class DoseRouteVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    /**
     * getId for DoseRouteVo.
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for DoseRouteVo.
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getName for DoseRouteVo.
     * @return name property
     */
    public String getName() {
        return name;
    }

    /**
     * setName for DoseRouteVo.
     * @param name name property
     */
    public void setName(String name) {
        this.name = name;
    }
}
