/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbNdcVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbNdcDo;



/**
 * Convert to/from {@link IngredientVo} and {@link EplIngredientDo}.
 */
public class FdbNdcConverter extends Converter<FdbNdcVo, EplFdbNdcDo> {

    /**
     * Standard constructor
     */
    public FdbNdcConverter() {
        
    }

    /**
     * Copes the data from the Vo to the Do
     * 
     * @param data FdbNdcVo
     * @return EplFdbNdcDo  
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbNdcDo toDataObject(FdbNdcVo data) {
      
        EplFdbNdcDo fdbNdcDo = new EplFdbNdcDo();
        fdbNdcDo.setNdcIdFk(data.getNdcIdFk());
        fdbNdcDo.setDosageForm(data.getDosageForm());
        fdbNdcDo.setFederalDeaClassCode(data.getFederalDeaClassCode());
        fdbNdcDo.setFederalLegendCode(data.getFederalLegendCode());
        fdbNdcDo.setFormatCode(data.getFormatCode());
        fdbNdcDo.setGcnSeqno(data.getGcnSeqno());
        fdbNdcDo.setLabelerName(data.getLabelerName());
        fdbNdcDo.setNdc(data.getNdc());
        fdbNdcDo.setNdcFormatted(data.getNdcFormatted());
        fdbNdcDo.setObsoleteDate(data.getObsoleteDate());
        fdbNdcDo.setPackageDescription(data.getPackageDescription());
        fdbNdcDo.setPackageSize(data.getPackageSize());
        fdbNdcDo.setPreviousNdc(data.getPreviousNdc());
        fdbNdcDo.setReplacementNdc(data.getReplacementNdc());
        fdbNdcDo.setStrengthNumeric(data.getStrengthNumeric());
        fdbNdcDo.setStrengthUnit(data.getStrengthUnit());

        return fdbNdcDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbNdcVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated FdbNdcVo
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated FdbNdcVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbNdcVo toValueObject(EplFdbNdcDo data) {
        FdbNdcVo fdbNdcVo = new FdbNdcVo();
        
        fdbNdcVo.setNdcIdFk(data.getNdcIdFk());
        fdbNdcVo.setDosageForm(data.getDosageForm());
        fdbNdcVo.setFederalDeaClassCode(data.getFederalDeaClassCode());
        fdbNdcVo.setFederalLegendCode(data.getFederalLegendCode());
        fdbNdcVo.setFormatCode(data.getFormatCode());
        fdbNdcVo.setGcnSeqno(data.getGcnSeqno());
        fdbNdcVo.setLabelerName(data.getLabelerName());
        fdbNdcVo.setNdc(data.getNdc());
        fdbNdcVo.setNdcFormatted(data.getNdcFormatted());
        fdbNdcVo.setObsoleteDate(data.getObsoleteDate());
        fdbNdcVo.setPackageDescription(data.getPackageDescription());
        fdbNdcVo.setPackageSize(data.getPackageSize());
        fdbNdcVo.setPreviousNdc(data.getPreviousNdc());
        fdbNdcVo.setReplacementNdc(data.getReplacementNdc());
        fdbNdcVo.setStrengthNumeric(data.getStrengthNumeric());
        fdbNdcVo.setStrengthUnit(data.getStrengthUnit());

        return fdbNdcVo;
    }
}
