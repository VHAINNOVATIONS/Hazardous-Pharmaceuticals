/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplSiteConfigDo;


/**
 * Convert to/from {@link SiteConfigVo} and {@link EplSiteConfigDo}.
 */
public class SiteConfigConverter extends Converter<SiteConfigVo, EplSiteConfigDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplSiteConfigDo toDataObject(SiteConfigVo data) {
        EplSiteConfigDo site = new EplSiteConfigDo();

        site.setSiteNumber(Integer.valueOf(data.getSiteNumber()));
        site.setSiteName(data.getSiteName());
        site.setServerName(data.getSiteServerName());
        site.setEplVersion(data.getSitePepsDbVersion());

        return site;
    }

    /**
     * Fully copies data from the given EplSiteConfigDo into a {@link ValueObject}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data EplSiteConfigDo to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected SiteConfigVo toValueObject(EplSiteConfigDo data) {
        SiteConfigVo site = new SiteConfigVo();

        site.setSiteNumber(data.getSiteNumber().toString());
        site.setSiteName(data.getSiteName());
        site.setSiteServerName(data.getServerName());
        site.setSitePepsDbVersion(data.getEplVersion());

        return site;
    }

}
