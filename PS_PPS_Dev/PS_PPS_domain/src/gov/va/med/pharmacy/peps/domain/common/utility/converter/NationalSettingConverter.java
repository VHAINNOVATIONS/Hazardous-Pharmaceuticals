/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNationalSettingDo;



/**
 * Convert to/from {@link NationalSettingVo} and {@link EplIngredientDo}.
 */
public class NationalSettingConverter extends
        Converter<NationalSettingVo, EplNationalSettingDo> {

    /**
     * Standard constructor
     */
    public NationalSettingConverter() {
    }

    /**
     * Fully copies data from the given {@link NationalSettingVo} into a
     * {@link DataObject}.
     * 
     * @param data {@link NationalSettingVo} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplNationalSettingDo toDataObject(NationalSettingVo data) {
        EplNationalSettingDo nationalSettingDo = new EplNationalSettingDo();

        nationalSettingDo.setId(data.getId());
        nationalSettingDo.setKeyName(data.getKeyName());
        nationalSettingDo.setStringValue(data.getStringValue());
        nationalSettingDo.setIntegerValue(data.getIntegerValue());
        nationalSettingDo.setDecimalValue(data.getDecimalValue());
        
        if (data.getBooleanValue() != null) {
            if (data.getBooleanValue()) {
                nationalSettingDo.setBooleanValue(new Long("1"));
            } else {
                nationalSettingDo.setBooleanValue(new Long("0"));
            }
        }
        
        nationalSettingDo.setDateValue(data.getDateValue());

        return nationalSettingDo;
    }

    /**
     * toValueObject
     * 
     * @param data EplNationalSettingDo
     * @return NationalSettingVo
     */
    @Override
    protected NationalSettingVo toValueObject(EplNationalSettingDo data) {
        NationalSettingVo nationalSettingVo = new NationalSettingVo();

        nationalSettingVo.setId(data.getId());
        nationalSettingVo.setKeyName(data.getKeyName());
        nationalSettingVo.setStringValue(data.getStringValue());
        nationalSettingVo.setIntegerValue(data.getIntegerValue());
        nationalSettingVo.setDecimalValue(data.getDecimalValue());

        if (data.getBooleanValue() != null) {
            if (data.getBooleanValue().equals(1L)) {
                nationalSettingVo.setBooleanValue(true);
            } else {
                nationalSettingVo.setBooleanValue(false);
            }
        }
        
        nationalSettingVo.setDateValue(data.getDateValue());
        
        return nationalSettingVo;
    }
}
