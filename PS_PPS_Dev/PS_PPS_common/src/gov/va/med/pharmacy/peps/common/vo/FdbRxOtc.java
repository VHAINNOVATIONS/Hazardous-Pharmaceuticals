/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * FdbRxOtc filter
 */
public enum FdbRxOtc {
    
    /** RX */
    RX, 
    
    /** OTC */
    OTC, 
    
    /** BOTH */
    BOTH;
    
    /**
     * isRx.
     * @return boolean true if this FdbRxOtc filter is RX
     */
    public boolean isRx() {
        return RX.equals(this);
    }

    /**
     * isOtc.
     * @return boolean true if this FdbRxOtc filter is OTC
     */
    public boolean isOtc() {
        return OTC.equals(this);
    }

    /**
     * isBoth.
     * @return boolean true if this FdbRxOtc filter is both RX and OTC
     */
    public boolean isBoth() {
        return BOTH.equals(this);
    }
    
}
