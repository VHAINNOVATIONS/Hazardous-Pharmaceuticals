/**
 * Source file created in 2007 by Southwest Research Institute
 * 
 * 
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.VuidItemType;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVuidStatusHistoryDo;


/**
 * Convert to/from {@link DrugUnitVo} and {@link EplDrugUnitDo}.
 */
public class VuidStatusHistoryConverter extends
        Converter<VuidStatusHistoryVo, EplVuidStatusHistoryDo> {

    /**
     * toVuidStatusHistoryVO from VuidStatusHistDo     
     * @param data EplVuidStatusHistoryDo
     * @return VuidStatusHistoryVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected VuidStatusHistoryVo toValueObject(EplVuidStatusHistoryDo data) {
        VuidStatusHistoryVo unit = new VuidStatusHistoryVo();
        
        if (data.getItemType().equals(0)) {
            unit.setItemType(VuidItemType.PRODUCTS);
        } else if (data.getItemType().equals(PPSConstants.I1)) {
            unit.setItemType(VuidItemType.INGREDIENTS);
        } else if (data.getItemType().equals(PPSConstants.I2)) {
            unit.setItemType(VuidItemType.DRUG_CLASSES);
        } else if (data.getItemType().equals(PPSConstants.I3)) {
            unit.setItemType(VuidItemType.GENERIC);
        } else if (data.getItemType().equals(PPSConstants.I4)) {
            unit.setItemType(VuidItemType.STANDARD_MED_ROUTE);
        }
        
        unit.setVuid(Long.parseLong(data.getVuid()));
        
        unit.setEffectiveDateTime(data.getEffectiveDtm());
        
        if (data.getStatus().equals("1")) {
            unit.setEffectiveDtmStatus(ItemStatus.ACTIVE);
        } else if (data.getStatus().equals("0")) {
            unit.setEffectiveDtmStatus(ItemStatus.INACTIVE);
        }
        
        return unit;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject
     *      (gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplVuidStatusHistoryDo toDataObject(VuidStatusHistoryVo data) {
        EplVuidStatusHistoryDo unit = new EplVuidStatusHistoryDo();
        
        if (data.getItemType().equals(VuidItemType.PRODUCTS)) {
            unit.setItemType(new Long("0"));
        }
        
        if (data.getItemType().equals(VuidItemType.INGREDIENTS)) {
            unit.setItemType(new Long("1"));
        }
        
        if (data.getItemType().equals(VuidItemType.DRUG_CLASSES)) {
            unit.setItemType(new Long("2"));
        }
        
        if (data.getItemType().equals(VuidItemType.GENERIC)) {
            unit.setItemType(new Long("3"));
        }
        
        if (data.getItemType().equals(VuidItemType.STANDARD_MED_ROUTE)) {
            unit.setItemType(new Long("4"));
        }
        
        unit.setVuid(String.valueOf(data.getVuid()));
        unit.setEffectiveDtm(data.getEffectiveDateTime());
        unit.setId(Long.valueOf(data.getId()));
        
        if (data.getEffectiveDtmStatus().isActive()) {
            unit.setStatus("1");
        } else if (data.getEffectiveDtmStatus().isInactive()) {
            unit.setStatus("0");
        }
        
        return unit;
    }

    /**
     * VuidStatusHistoryVo toMinimalValueObject     * 
     * @param data EplVuidStatusHistoryDo
     * @return minimally populated VuidStatusHistoryVo
     */
    @Override
    protected VuidStatusHistoryVo toMinimalValueObject(
            EplVuidStatusHistoryDo data) {
        VuidStatusHistoryVo dataObject = new VuidStatusHistoryVo();
        dataObject = toValueObject(data);

        return dataObject;
    }
}
