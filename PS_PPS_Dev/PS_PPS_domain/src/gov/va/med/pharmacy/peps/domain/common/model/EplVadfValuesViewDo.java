/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.model;


/**
 * Data representing a row in the EPL_VADF_VALUES_VIEW
 * 
 * @hibernate.class
 */
public class EplVadfValuesViewDo extends DataObject {
    public static final String KEY = "key";
    public static final String OWNER_ID = "key.vadfOwnerId";
    public static final String VADF_NAME = "key.vadfName";
    public static final String VADF_VALUE = "key.vadfValue";

    private static final long serialVersionUID = 1L;

    private EplVadfValuesViewDoKey key;

    /**
     * 
     * @return key property
     */
    public EplVadfValuesViewDoKey getKey() {
        return key;
    }

    /**
     * 
     * @param key key property
     */
    public void setKey(EplVadfValuesViewDoKey key) {
        this.key = key;
    }
}