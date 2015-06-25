/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 * 
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ReducedCopayVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplReducedCopayDo;


/**
 * Convert to/from {@link DrugUnitVo} and {@link EplDrugUnitDo}.
 */
public class ReducedCopayConverter extends
        AssociationConverter<ReducedCopayVo, EplReducedCopayDo, EplProductDo> {

    /**
     * to ReducedCopayVo from EplReducedCopayDo     
     * @param data EplReducedCopayDo
     * @return ReducedCopayVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected ReducedCopayVo toValueObject(EplReducedCopayDo data) {
        ReducedCopayVo unit = new ReducedCopayVo();
        unit.setEplId(data.getId());
        unit.setProductFk(data.getProductFk());
        unit.setReducedCopayStartDate(data.getStartDate());
        unit.setReducedCopayStopDate(data.getStopDate());
        
        return unit;
    }
    
    /**
     * to ReducedCopayVo from EplReducedCopayDo     
     * @param dataList EplReducedCopayDo
     * @return ReducedCopayVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    protected List<ReducedCopayVo> toValueObject(List<EplReducedCopayDo> dataList) {
        List<ReducedCopayVo> list = new  ArrayList<ReducedCopayVo>();
        
        for (EplReducedCopayDo data : dataList) {
            ReducedCopayVo unit = new ReducedCopayVo();
            unit.setEplId(data.getId());
            unit.setProductFk(data.getProductFk());
            unit.setReducedCopayStartDate(data.getStartDate());
            unit.setReducedCopayStopDate(data.getStopDate());
            list.add(unit);
        }
        
        return list;
    }

    /**
     * Fully copies the ReducedCopayVo to a EplReducedCopayDo
     * 
     * @param data EplReducedCopayDo
     * @return fully populated EplReducedCopayDo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject
     *      (gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplReducedCopayDo toDataObject(ReducedCopayVo data) {
        EplReducedCopayDo unit = new EplReducedCopayDo();
        
       
        unit.setId(data.getEplId());
        unit.setProductFk(data.getProductFk());
        unit.setStartDate(data.getReducedCopayStartDate());
        unit.setStopDate(data.getReducedCopayStopDate());
        
        return unit;
    }
   
    /**
     * Fully copies the ReducedCopayVo to a EplReducedCopayDo
     * 
     * @param dataList EplReducedCopayDo
     * @return fully populated EplReducedCopayDo List
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject
     *      (gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    protected List<EplReducedCopayDo> toDataObject(List<ReducedCopayVo> dataList) {
        List<EplReducedCopayDo> list = new ArrayList<EplReducedCopayDo>();
        
        for (ReducedCopayVo data : dataList) {
            EplReducedCopayDo unit = new EplReducedCopayDo();
            unit.setId(data.getEplId());
            unit.setProductFk(data.getProductFk());
            unit.setStartDate(data.getReducedCopayStartDate());
            unit.setStopDate(data.getReducedCopayStopDate());
            list.add(unit);
        }
        
        return list;
    }
   

    /**
     * Minimally value object converter for ReducedCopayVo History
     * @param data EplReducedCopayDo
     * @return ReducedCopayVo
     */
    @Override
    protected ReducedCopayVo toMinimalValueObject(
            EplReducedCopayDo data) {
        ReducedCopayVo dataObject = new ReducedCopayVo();
        dataObject = toValueObject(data);

        return dataObject;
    }

    @Override
    protected EplReducedCopayDo toDataObject(ReducedCopayVo data, EplProductDo parent, int sequence) {
        return null;
    }
}
