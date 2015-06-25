/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


/**
 * Data representing the "surrogate" composite key on the EPL_VADF_VALUES_VIEW.
 */
public class EplVadfValuesViewDoKey extends DataObject {
    private static final long serialVersionUID = 1L;

    private Long vadfOwnerId;
    private String vadfName;
    private String vadfValue;

    /**
     * 
     * @return vadfOwnerId property
     */
    public Long getVadfOwnerId() {
        return vadfOwnerId;
    }

    /**
     * 
     * @param vadfOwnerId vadfOwnerId property
     */
    public void setVadfOwnerId(Long vadfOwnerId) {
        this.vadfOwnerId = vadfOwnerId;
    }

    /**
     * 
     * @return vadfName property
     */
    public String getVadfName() {
        return vadfName;
    }

    /**
     * 
     * @param vadfName vadfName property
     */
    public void setVadfName(String vadfName) {
        this.vadfName = vadfName;
    }

    /**
     * 
     * @return vadfValue property
     */
    public String getVadfValue() {
        return vadfValue;
    }

    /**
     * 
     * @param vadfValue vadfValue property
     */
    public void setVadfValue(String vadfValue) {
        this.vadfValue = vadfValue;
    }
}