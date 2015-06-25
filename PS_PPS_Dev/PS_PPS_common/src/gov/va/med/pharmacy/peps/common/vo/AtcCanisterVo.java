/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;


/**
 * Represents one association of ATC-related information to a Product.  A Product may have zero or more such 
 * associations.     
 */
public class AtcCanisterVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long atcCanister;
    private WardGroupForAtcVo wardGroupForAtc;

    /**
     * getId for AtcCanisterVo
     * 
     * @return id property
     */
    public Long getId() {
        return id;
    }

    /**
     * setId for AtcCanisterVo
     * @param id id property
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getAtcCanister..
     * @return atcCanister property
     */
    public Long getAtcCanister() {
        return atcCanister;
    }

    /**
     * setAtcCanister.
     * @param atcCanister atcCanister property
     */
    public void setAtcCanister(Long atcCanister) {
        this.atcCanister = atcCanister;
    }

    /**
     * setAtcCanister.
     * @return wardGroupForAtc property
     */
    public WardGroupForAtcVo getWardGroupForAtc() {
        return wardGroupForAtc;
    }

    /**
     * setWardGroupForAtc.
     * @param incomingWardGroupForAtc wardGroupForAtc property
     */
    public void setWardGroupForAtc(WardGroupForAtcVo incomingWardGroupForAtc) {
        this.wardGroupForAtc = incomingWardGroupForAtc;
    }

    /**
     * This is the ATC Canister short string method
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        
        String s1 = FieldKey.getLocalizedName(FieldKey.ATC_CANISTER, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.WARD_GROUP_FOR_ATC, Locale.getDefault());
        
        return (s1 + ": " + (atcCanister == null ? "(Not specified)" + "<p>" : atcCanister) 
                + s2 + ": " 
                + (wardGroupForAtc == null ? "(Not Specified)" : wardGroupForAtc.toShortString()));

    }

}
