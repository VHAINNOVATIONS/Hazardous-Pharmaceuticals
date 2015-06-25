/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIntendedUseDo;


/**
 * Convert to/from {@link IntendedUseVo} and {@link EplIntendedUseDo}.
 */
public class IntendedUseConverter extends Converter<IntendedUseVo, EplIntendedUseDo> {

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
    protected EplIntendedUseDo toDataObject(IntendedUseVo data) {
        EplIntendedUseDo use = new EplIntendedUseDo();
        use.setEplId(Long.valueOf(data.getId()));
        use.setIntendedUse(data.getIntendedUse());
        use.setCode(data.getValue());

        return use;
    }

    /**
     * IntendedUseVo toValueObject(EplIntendedUseDo data)
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected IntendedUseVo toValueObject(EplIntendedUseDo data) {
        IntendedUseVo value = new IntendedUseVo();
        value.setId(String.valueOf(data.getEplId()));
        value.setIntendedUse(data.getIntendedUse());

        return value;
    }
}
