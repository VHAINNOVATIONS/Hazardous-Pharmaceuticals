/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing an intended use.
 */
public class IntendedUseVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    private static final String TRADE_NAME = "0-TRADE NAME";

    private String id;
    private String value;
    private String intendedUse;
    
    /**
     * The getId field for the IntendedUseVo
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * The setId field for the IntendedUseVo.
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The getValue field for the IntendedUseVo.
     * @return value property
     */
    public String getValue() {
        return intendedUse;
    }

    /**
     * The shortString method for the IntendedUseVo..
     * @return short string value
     */
    @Override
    public String toShortString() {
        return value;
    }

    /**
     * getIntendedUse.
     * @return vuid property
     */
    public String getIntendedUse() {
        return intendedUse;
    }

    /**
     * setIntendedUse.
     * @param intendedUse property
     */
    public void setIntendedUse(String intendedUse) {
        this.intendedUse = trimToEmpty(intendedUse);
    }
    
    /**
     * True if {@link #getIntendedUse()} equals {@value #TRADE_NAME}.
     * 
     * @return boolean
     */
    public boolean isTradeName() {
        return TRADE_NAME.equalsIgnoreCase(getIntendedUse());
    }
}
