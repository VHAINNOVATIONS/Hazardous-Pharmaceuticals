/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdIngredientAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert to/from {@link ActiveIngredientVo} and
 * {@link EplProdIngredientAssocDo}
 */
public class ActiveIngredientConverter
        extends
        AssociationConverter<ActiveIngredientVo, EplProdIngredientAssocDo, EplProductDo> {

    private IngredientConverter ingredientConverter;
    private DrugUnitConverter drugUnitConverter;

    /**
     * Fully copies data from the given ActiveIngredientVo into a EplProductDo
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplProdIngredientAssocDo toDataObject(ActiveIngredientVo data) {
        EplProdIngredientAssocDo ingredient = new EplProdIngredientAssocDo();
        EplProdIngredientAssocDoKey key = new EplProdIngredientAssocDoKey();
        key.setIngredientIdFk(Long.valueOf(data.getIngredient().getId()));
        ingredient.setKey(key);
        
        if (data.getDrugUnit() == null 
                || StringUtils.isBlank(data.getDrugUnit().getValue())) {
            ingredient.setEplDrugUnit(null);
        } else {
            ingredient.setEplDrugUnit(drugUnitConverter.convertMinimal(data
                    .getDrugUnit()));
        }
        
        ingredient.setActiveYn(toYesOrNo(data.getActive()));
        ingredient.setStrength(data.getStrength());

        return ingredient;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to
     * {@link #convert(ValueObject)} and then populate the parent data into the
     * association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data
     * required already set prior to calling this method.
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @param parent
     *            {@link DataObject} representing the parent item in the
     *            association
     * @param sequence
     *            ordinal location of the current {@link ValueObject} to
     *            convert, used only if order matters in this association
     * @return fully populated {@link DataObject}
     */
    protected EplProdIngredientAssocDo toDataObject(ActiveIngredientVo data,
            EplProductDo parent, int sequence) {
        EplProdIngredientAssocDo ingredient = convert(data);
        ingredient.getKey().setEplIdProductFk(parent.getEplId());

        return ingredient;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * {@link ValueObject}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected ActiveIngredientVo toValueObject(EplProdIngredientAssocDo data) {
        ActiveIngredientVo activeIngredient = new ActiveIngredientVo();
        activeIngredient.setIngredient(ingredientConverter.convert(data
                .getEplIngredient()));
        activeIngredient.setDrugUnit(drugUnitConverter.convertMinimal(data
                .getEplDrugUnit()));
        activeIngredient.setStrength(data.getStrength());
        activeIngredient.setActive(toBoolean(data.getActiveYn()));

        return activeIngredient;
    }

    /**
     * setIngredientConverter
     * 
     * @param ingredientConverter
     *            ingredientConverter property
     */
    public void setIngredientConverter(IngredientConverter ingredientConverter) {
        this.ingredientConverter = ingredientConverter;
    }

    /**
     * setDrugUnitConverter
     * 
     * @param drugUnitConverter
     *            drugUnitConverter property
     */
    public void setDrugUnitConverter(DrugUnitConverter drugUnitConverter) {
        this.drugUnitConverter = drugUnitConverter;
    }
}
