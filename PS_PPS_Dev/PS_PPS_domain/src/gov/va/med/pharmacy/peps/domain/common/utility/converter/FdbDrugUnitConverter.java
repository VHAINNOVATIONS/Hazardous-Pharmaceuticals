/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbDrugUnitVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDrugUnitDo;


/**
 * Convert to/from {@link FdbDrugUnitVo} and {@link EplFdbDrugUnitDo}.
 */
public class FdbDrugUnitConverter extends
        Converter<FdbDrugUnitVo, EplFdbDrugUnitDo> {

    /**
     * Standard constructor
     */
    public FdbDrugUnitConverter() {
    }

    /**
     * Fully copies data from the given FdbDrugUnitVo into a
     * {@link DataObject}.
     * 
     * @param data
     *           FdbDrugUnitVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbDrugUnitDo toDataObject(FdbDrugUnitVo data) {
        EplFdbDrugUnitDo fdbDrugUnit = new EplFdbDrugUnitDo();

        fdbDrugUnit.setFdbDrugStrengthunits(data.getFdbDrugStrengthunits());
        fdbDrugUnit.setEplDrugUnitsFk(data.getEplId());
        
        return fdbDrugUnit;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbDrugUnitVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated FdbDrugUnitVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbDrugUnitVo toValueObject(EplFdbDrugUnitDo data) {
        FdbDrugUnitVo fdbDrugUnit = new FdbDrugUnitVo();

        fdbDrugUnit.setFdbDrugStrengthunits(data.getFdbDrugStrengthunits());
        fdbDrugUnit.setEplId(data.getEplDrugUnitsFk());
        
        return fdbDrugUnit;
    }
}
