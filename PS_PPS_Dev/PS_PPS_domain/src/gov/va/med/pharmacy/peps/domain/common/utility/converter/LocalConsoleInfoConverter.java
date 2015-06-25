/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.LocalConsoleInfoVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalConsoleInfoDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalConsoleInfoDoKey;


/**
 * Convert to/from {@link LocalConsoleInfoVo} and {@link EplLocalConsoleInfoDo}.
 */
public class LocalConsoleInfoConverter extends Converter<LocalConsoleInfoVo, EplLocalConsoleInfoDo> {

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
    protected EplLocalConsoleInfoDo toDataObject(LocalConsoleInfoVo data) {
        EplLocalConsoleInfoDo consoleDo = new EplLocalConsoleInfoDo();
        EplLocalConsoleInfoDoKey key = new EplLocalConsoleInfoDoKey();

        key.setSiteNumber(data.getSiteNumber());
        key.setSoftwareUpdateType(data.getSoftwareUpdateType());

        consoleDo.setKey(key);
        consoleDo.setSiteName(data.getSiteName());
        consoleDo.setSoftwareUpdateVersion(data.getSoftwareUpdateVersion());
        consoleDo.setVersionInstallDtm(data.getVersionInstallDtm());
        consoleDo.setServerName(data.getServerName());

        return consoleDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a LocalConsoleInfoVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated LocalConsoleInfoVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected LocalConsoleInfoVo toValueObject(EplLocalConsoleInfoDo data) {
        LocalConsoleInfoVo value = new LocalConsoleInfoVo();

        value.setServerName(data.getServerName());
        value.setSiteNumber(data.getKey().getSiteNumber());
        value.setSiteName(data.getSiteName());
        value.setSoftwareUpdateType(data.getKey().getSoftwareUpdateType());
        value.setSoftwareUpdateVersion(data.getSoftwareUpdateVersion());
        value.setVersionInstallDtm(data.getVersionInstallDtm());

        return value;
    }
}
