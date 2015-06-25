/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplSiteUpdateScheduleDo;


/**
 * Convert to/from {@link SiteUpdateScheduleVo} and {@link EplSiteUpdateScheduleDo}
 */
public class SiteUpdateScheduleConverter extends Converter<SiteUpdateScheduleVo, EplSiteUpdateScheduleDo> {

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
    protected EplSiteUpdateScheduleDo toDataObject(SiteUpdateScheduleVo data) {
        EplSiteUpdateScheduleDo dataObject = new EplSiteUpdateScheduleDo();

        dataObject.setId(data.getId());
        dataObject.setSiteNumber(data.getSiteNumber());
        dataObject.setSoftwareUpdateType(data.getSoftwareUpdateType());
        dataObject.setSoftwareUpdateVersion(data.getSoftwareUpdateVersion());
        dataObject.setInProgress(data.getInProgress());
        dataObject.setStartDtm(data.getStartDtm());
        dataObject.setEndDtm(data.getEndDtm());
        dataObject.setScheduledStartDtm(data.getScheduleStartDtm());
        dataObject.setScheduledInterval(data.getScheduleInterval());
        dataObject.setMd5sum(data.getMd5Sum());

        return dataObject;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a SiteUpdateScheduleVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated SiteUpdateScheduleVo are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated SiteUpdateScheduleVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected SiteUpdateScheduleVo toValueObject(EplSiteUpdateScheduleDo data) {
        SiteUpdateScheduleVo dataObject = new SiteUpdateScheduleVo();

        dataObject.setId(data.getId());
        dataObject.setSiteNumber(data.getSiteNumber());
        dataObject.setSoftwareUpdateType(data.getSoftwareUpdateType());
        dataObject.setSoftwareUpdateVersion(data.getSoftwareUpdateVersion());
        dataObject.setInProgress(data.getInProgress());
        dataObject.setStartDtm(data.getStartDtm());
        dataObject.setEndDtm(data.getEndDtm());
        dataObject.setScheduleStartDtm(data.getScheduledStartDtm());
        dataObject.setScheduleInterval(data.getScheduledInterval());
        dataObject.setMd5Sum(data.getMd5sum());

        return dataObject;
    }
}
