/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbDrugIngredientVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDrugIngredientDo;


/**
 * Convert to/from {@link FdbDrugIngredientVo} and {@link EplFdbDrugIngredientDo}.
 */
public class FdbDrugIngredientConverter extends
        Converter<FdbDrugIngredientVo, EplFdbDrugIngredientDo> {

    /**
     * Standard constructor
     */
    public FdbDrugIngredientConverter() {
    }

    /**
     * Fully copies data from the given FdbDrugIngredientVo into a
     * {@link DataObject}.
     * 
     * @param data
     *            FdbDrugIngredientVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbDrugIngredientDo toDataObject(FdbDrugIngredientVo data) {
        EplFdbDrugIngredientDo fdbDrugIngredient = new EplFdbDrugIngredientDo();

        fdbDrugIngredient.setFdbDrugIngredient(data.getFdbDrugIngredient());
        fdbDrugIngredient.setEplDrugIngredientFk(data.getEplId());
        
        return fdbDrugIngredient;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbDrugIngredientVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated FdbDrugIngredientVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbDrugIngredientVo toValueObject(EplFdbDrugIngredientDo data) {
        FdbDrugIngredientVo fdbDrugIngredient = new FdbDrugIngredientVo();

        fdbDrugIngredient.setFdbDrugIngredient(data.getFdbDrugIngredient());
        fdbDrugIngredient.setEplId(data.getEplDrugIngredientFk());
        
        return fdbDrugIngredient;
    }
}
