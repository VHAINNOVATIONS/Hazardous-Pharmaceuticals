/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.InterfaceCounterVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplInterfaceCounterDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplInterfaceCounterDoKey;


/**
 * Convert to/from {@link InterfaceCounterVo} and {@link EplInterfaceCounterDo}.
 */
public class InterfaceCounterConverter extends Converter<InterfaceCounterVo, EplInterfaceCounterDo> {

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
    protected EplInterfaceCounterDo toDataObject(InterfaceCounterVo data) {
        EplInterfaceCounterDo dataObject = new EplInterfaceCounterDo();
        EplInterfaceCounterDoKey key = new EplInterfaceCounterDoKey();

        key.setCounterName(data.getCounterName());
        key.setInterfaceName(data.getInterfaceName());
        dataObject.setKey(key);
        dataObject.setCounterValue(data.getCounterValue());
        dataObject.setCreatedBy(data.getCreatedBy());
        dataObject.setCreatedDtm(data.getCreatedDate());
        dataObject.setLastModifiedBy(data.getModifiedBy());
        dataObject.setLastModifiedDtm(data.getModifiedDate());

        return dataObject;
    }

    /**
     * InterfaceCounterVo toValueObject(EplInterfaceCounterDo data)
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     * gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected InterfaceCounterVo toValueObject(EplInterfaceCounterDo data) {
        InterfaceCounterVo valueObject = new InterfaceCounterVo();
        valueObject.setCounterValue(data.getCounterValue());
        valueObject.setCounterName(data.getKey().getCounterName());
        valueObject.setInterfaceName(data.getKey().getInterfaceName());
        valueObject.setCreatedBy(data.getCreatedBy());
        valueObject.setCreatedDate(data.getCreatedDtm());
        valueObject.setModifiedBy(data.getLastModifiedBy());
        valueObject.setModifiedDate(data.getLastModifiedDtm());

        return valueObject;
    }

}
