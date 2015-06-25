/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleTypeVo;
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplScheduleTypeDo;


/**
 * Convert to/from {@link AdministrationScheduleVo} and {@link EplAdminScheduleDo}
 * 
 * 
 */
public class AdminScheduleTypeConverter extends Converter<AdministrationScheduleTypeVo, EplScheduleTypeDo> {

    /**
     * Fully copies data from the given EplScheduleTypeDo into a AdministrationScheduleTypeVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated AdministrationScheduleTypeVo are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate EplScheduleTypeDo} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected AdministrationScheduleTypeVo toValueObject(EplScheduleTypeDo data) {
        AdministrationScheduleTypeVo type = new AdministrationScheduleTypeVo();

        type.setCode(data.getCode());
        type.setId(data.getId().toString());
        type.setValue(data.getScheduleTypeName());

        return type;

    }

    /**
     * Fully copies data from the given AdministrationScheduleTypeVo into a EplScheduleTypeDo.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplScheduleTypeDo toDataObject(AdministrationScheduleTypeVo data) {
        EplScheduleTypeDo type = new EplScheduleTypeDo();

        type.setId(new Long(data.getId()));

        return type;
    }

}
