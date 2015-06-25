/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;


/**
 * Data representing an Ndc by Outpatient Site
 */
public class NdcByOutpatientSiteNdcVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private OutpatientSiteVo outpatientSite;
    private String lastLocalNdc;
    private String lastCmopNdc;

    /**
     * getOutpatientSite
     * 
     * @return outpatientSite property
     */
    public OutpatientSiteVo getOutpatientSite() {
        return outpatientSite;
    }

    /**
     * setOutpatientSite
     * 
     * @param outpatientSite outpatientSite property
     */
    public void setOutpatientSite(OutpatientSiteVo outpatientSite) {
        this.outpatientSite = outpatientSite;
    }

    /**
     * getLastLocalNdc
     * 
     * @return lastLocalNdc property
     */
    public String getLastLocalNdc() {
        return lastLocalNdc;
    }

    /**
     * setLastLocalNdc
     * 
     * @param lastLocalNdc lastLocalNdc property
     */
    public void setLastLocalNdc(String lastLocalNdc) {
        this.lastLocalNdc = trimToEmpty(lastLocalNdc);
    }

    /**
     * getLastCmopNdc
     * 
     * @return lastCmopNdc property
     */
    public String getLastCmopNdc() {
        return lastCmopNdc;
    }

    /**
     * setLastCmopNdc
     * 
     * @param lastCmopNdc lastCmopNdc property
     */
    public void setLastCmopNdc(String lastCmopNdc) {
        this.lastCmopNdc = trimToEmpty(lastCmopNdc);
    }

    /**
     * toShortString returns toString unless overridden in NdcByOutpatientSiteNdcVo.
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        String s1 = FieldKey.getLocalizedName(FieldKey.NDC_BY_OUTPATIENT_SITE_NDC, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.LAST_CMOP_NDC, Locale.getDefault());
        String s3 = FieldKey.getLocalizedName(FieldKey.LAST_LOCAL_NDC, Locale.getDefault());

        return (s1 + ": "
            + (outpatientSite == null ? "(Not specified)" : outpatientSite.toShortString())
            + "<p>" + s2 + ": " + (lastCmopNdc == null ? "(Not specified)" : lastCmopNdc) + "<p>" + s3 + ": "
            + (lastLocalNdc == null ? "(Not specified)" : lastLocalNdc) + "p");

    }

}
