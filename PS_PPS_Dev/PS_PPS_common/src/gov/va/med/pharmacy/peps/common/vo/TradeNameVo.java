/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;


/**
 * Data representing a Trade Name.
 * 
 */
public class TradeNameVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String tradeName;
    private String id;

    /**
     * Description
     * 
     * @return TradeName property
     */
    public String getTradeName() {
        return tradeName;
    }

    /**
     * Description
     * 
     * @param tradeName The tradeName property
     */
    public void setTradeName(String tradeName) {
        this.tradeName = trimToEmpty(tradeName);
    }

    /**
     * getId for TradeNameVo.
     * 
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * setId for TradeNameVo.
     * 
     * @param id id property
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * toShortString returns the TradeName short String values for TradeNameVo.
     * 
     * @return TradeName short String
     */
    @Override
    public String toShortString() {
    
        String s1 = FieldKey.getLocalizedName(FieldKey.TRADE_NAME, Locale.getDefault());
        
        return (s1 + ": " + (tradeName == null ? "(Not specified)" : tradeName));
        
    }

}
