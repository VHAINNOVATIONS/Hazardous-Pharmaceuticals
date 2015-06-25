/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.OiScheduleTypeVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplOiScheduleTypeDo;


/**
 * Convert to/from {@link OiScheduleTypeVo} and {@link EplOiScheduleTypeDo}.
 */
public class OiScheduleTypeConverter extends Converter<OiScheduleTypeVo, EplOiScheduleTypeDo> {

    /**
     * Fully copies data from the given OiScheduleTypeVo into a {@link DataObject}.
     * 
     * @param data OiScheduleTypeVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplOiScheduleTypeDo toDataObject(OiScheduleTypeVo data) {
        EplOiScheduleTypeDo type = new EplOiScheduleTypeDo();
        type.setId(Long.valueOf(data.getId()));
        
        return type;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a OiScheduleTypeVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated OiScheduleTypeVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected OiScheduleTypeVo toValueObject(EplOiScheduleTypeDo data) {
        OiScheduleTypeVo type = new OiScheduleTypeVo();

        type.setId(String.valueOf(data.getId()));
        type.setCode(data.getCode());
        type.setType(data.getOiScheduleType());

        return type;
    }
}
