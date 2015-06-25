/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplClassTypeDo;


/**
 * Convert to/from {@link DrugClassificationTypeVo} and {@link EplClassTypeDo}.
 */
public class DrugClassificationTypeConverter extends Converter<DrugClassificationTypeVo, EplClassTypeDo> {

    /**
     * Fully copies data from the given DrugClassificationTypeVo into a {@link DataObject}.
     * 
     * @param data DrugClassificationTypeVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplClassTypeDo toDataObject(DrugClassificationTypeVo data) {
        EplClassTypeDo type = new EplClassTypeDo();
        type.setClassType(data.getValue());
        type.setId(Long.valueOf(data.getId()));

        return type;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a DrugClassificationTypeVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated DrugClassificationTypeVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DrugClassificationTypeVo toValueObject(EplClassTypeDo data) {
        DrugClassificationTypeVo type = new DrugClassificationTypeVo();

        type.setId(data.getId().toString());
        type.setValue(data.getClassType());

        return type;
    }
}
