/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Data representing a dose type for a drug
 */
public class DoseTypeVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    /**
     * getId
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getName
     * @return name property
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * @param name name property
     */
    public void setName(String name) {
        this.name = name;
    }
}
