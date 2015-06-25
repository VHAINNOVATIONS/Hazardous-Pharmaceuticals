/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types available to an Item
 */
public enum Type {

    /** ENTITY_TYPE */
    ENTITY_TYPE,

    /** DOMAIN_TYPE */
    DOMAIN_TYPE,

    /** OTHER_TYPE */
    OTHER_TYPE;

    /**
     * Description
     * 
     * @return boolean true if this Type is an entity
     */
    public boolean isEntityType() {
        return ENTITY_TYPE.equals(this);
    }

    /**
     * Description
     * 
     * @return boolean true if this Type is a domain
     */
    public boolean isDomainType() {
        return DOMAIN_TYPE.equals(this);
    }
    
    /**
     * isOtherType
     * @return OtherType
     */
    public boolean isOtherType() {
        return OTHER_TYPE.equals(this);
    }
}
