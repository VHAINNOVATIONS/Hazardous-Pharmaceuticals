/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * A RX Consult can be a national, local, or COTS type.
 */
public enum RxConsultType {

    /** COTS */
    COTS,

    /** LOCAL */
    LOCAL,

    /** NATIONAL */
    NATIONAL;

    /**
     * Convert {@link Environment#LOCAL} to {@link RxConsultType#LOCAL} and {@link Environment#NATIONAL} to
     * {@link RxConsultType#NATIONAL}.
     * 
     * @param environment {@link Environment} to convert to a {@link RxConsultType}
     * @return {@link RxConsultType}
     */
    public static RxConsultType toRxConsultType(Environment environment) {

        return Environment.NATIONAL.equals(environment) ? NATIONAL : LOCAL;
    }

    /**
     * isCots
     * @return true if this instance is equal to COTS
     */
    public boolean isCots() {

        return COTS.equals(this);
    }

    /**
     * isLocal
     * @return true if this instance is equal to LOCAL
     */
    public boolean isLocal() {

        return LOCAL.equals(this);
    }

    /**
     * isNational
     * @return true if this instance is equal to NATIONAL
     */
    public boolean isNational() {

        return NATIONAL.equals(this);
    }
}
