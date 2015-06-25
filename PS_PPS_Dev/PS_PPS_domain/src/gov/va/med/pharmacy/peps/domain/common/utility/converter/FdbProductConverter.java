/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbProductVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;


/**
 * Convert to/from {@link IngredientVo} and {@link EplIngredientDo}.
 */
public class FdbProductConverter extends
        Converter<FdbProductVo, EplFdbProductDo> {

    /**
     * Standard constructor
     */
    public FdbProductConverter() {
    }

    /**
     * Fully copies data from the given FdbProductVo into a
     * {@link DataObject}.
     * 
     * @param data
     *            FdbProductVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbProductDo toDataObject(FdbProductVo data) {
        EplFdbProductDo fdbProductDo = new EplFdbProductDo();
      
        fdbProductDo.setProductIdFk(data.getProductIdFk());
        fdbProductDo.setDgGenericDosageForm(data.getDgGenericDosageForm());
        fdbProductDo.setDgGenericDrugName(data.getDgGenericDrugName());
        fdbProductDo.setDgHasPackagedDrugs(data.getDgHasPackagedDrugs());
        fdbProductDo.setDgGenericDrugId(data.getDgGenericDrugId());
        fdbProductDo.setDgRoute(data.getDgRoute());
        fdbProductDo.setDgSingleIngredient(data.getDgSingleIngredient());
        fdbProductDo.setDgStrength(data.getDgStrength());
        fdbProductDo.setDdConceptType(data.getDdConceptType());
        fdbProductDo.setDdDosageForm(data.getDdDosageForm());
        fdbProductDo.setDdFederalDeaClassCode(data.getDdFederalDeaClassCode());
        fdbProductDo.setDdGcnSeqno(data.getDdGcnSeqno());
        fdbProductDo.setDdHasPackagedDrugs(data.getDdHasPackagedDrugs());
        fdbProductDo.setDdDispenseDrugId(data.getDdDispenseDrugId());
        fdbProductDo.setDdMultisource(data.getDdMultisource());
        fdbProductDo.setDdDispenseDrugName(data.getDdDispenseDrugName());
        fdbProductDo.setDdObsoleteDate(data.getDdObsoleteDate());
        fdbProductDo.setDdReplaced(data.getDdReplaced());
        fdbProductDo.setDdRoute(data.getDdRoute());
        fdbProductDo.setDdStatusCode(data.getDdStatusCode());
        fdbProductDo.setDdStrengthUnit(data.getDdStrengthUnit());
        fdbProductDo.setDdStrength(data.getDdStrength());

        return fdbProductDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbProductVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated FdbProductVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbProductVo toValueObject(EplFdbProductDo data) {
        FdbProductVo fdbProductVo = new FdbProductVo();
        fdbProductVo.setProductIdFk(data.getProductIdFk());
        fdbProductVo.setDgGenericDosageForm(data.getDgGenericDosageForm());
        fdbProductVo.setDgGenericDrugName(data.getDgGenericDrugName());
        fdbProductVo.setDgHasPackagedDrugs(data.getDgHasPackagedDrugs());
        fdbProductVo.setDgGenericDrugId(data.getDgGenericDrugId());
        fdbProductVo.setDgRoute(data.getDgRoute());
        fdbProductVo.setDgSingleIngredient(data.getDgSingleIngredient());
        fdbProductVo.setDgStrength(data.getDgStrength());
        fdbProductVo.setDdConceptType(data.getDdConceptType());
        fdbProductVo.setDdDosageForm(data.getDdDosageForm());
        fdbProductVo.setDdFederalDeaClassCode(data.getDdFederalDeaClassCode());
        fdbProductVo.setDdGcnSeqno(data.getDdGcnSeqno());
        fdbProductVo.setDdHasPackagedDrugs(data.getDdHasPackagedDrugs());
        fdbProductVo.setDdDispenseDrugId(data.getDdDispenseDrugId());
        fdbProductVo.setDdMultisource(data.getDdMultisource());
        fdbProductVo.setDdDispenseDrugName(data.getDdDispenseDrugName());
        fdbProductVo.setDdObsoleteDate(data.getDdObsoleteDate());
        fdbProductVo.setDdReplaced(data.getDdReplaced());
        fdbProductVo.setDdRoute(data.getDdRoute());
        fdbProductVo.setDdStatusCode(data.getDdStatusCode());
        fdbProductVo.setDdStrengthUnit(data.getDdStrengthUnit());
        fdbProductVo.setDdStrength(data.getDdStrength());

        return fdbProductVo;
    }
}
