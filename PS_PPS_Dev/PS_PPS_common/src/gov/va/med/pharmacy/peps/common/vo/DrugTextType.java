/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types available to an DrugText
 */
public enum DrugTextType {
    
    /**
     * LOCAL.
     */
    LOCAL,
    
    /**
     * NATIONAL.
     */
    NATIONAL;

    /**
     * isLocal.
     * @return boolean true if this DrugTextType is LOCAL
     */
    public boolean isLocal() {
        return LOCAL.equals(this);
    }

    /**
     * isNational.
     * @return boolean true if this DrugTextType is NATIONAL
     */
    public boolean isNational() {
        return NATIONAL.equals(this);
    }
}
