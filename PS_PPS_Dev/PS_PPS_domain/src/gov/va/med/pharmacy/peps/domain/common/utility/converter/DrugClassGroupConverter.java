/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugClassAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugClassAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert to/from {@link DrugClassGroupVo} and {@link EplProdDrugClassAssocDo}.
 */
public class DrugClassGroupConverter extends AssociationConverter<DrugClassGroupVo, EplProdDrugClassAssocDo, EplProductDo> {
    private DrugClassConverter drugClassConverter;

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
    protected EplProdDrugClassAssocDo toDataObject(DrugClassGroupVo data) {
        EplProdDrugClassAssocDo drugClass = new EplProdDrugClassAssocDo();
        drugClass.setPrimaryYn(toYesOrNo(data.getPrimary()));

        EplProdDrugClassAssocDoKey key = new EplProdDrugClassAssocDoKey();
        key.setDrugClassIdFk(Long.valueOf(data.getDrugClass().getId()));
        drugClass.setKey(key);

        return drugClass;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a EplProdDrugClassAssocDo.
     * <p>
     * Implementations should populate the data with a call to the converter and then populate the parent
     * data into the association.
     * <p>
     * The parent EplProductDo must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data {@link ValueObject} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     */
    protected EplProdDrugClassAssocDo toDataObject(DrugClassGroupVo data, EplProductDo parent, int sequence) {
        EplProdDrugClassAssocDo drugClass = convert(data);
        drugClass.getKey().setEplIdProductFk(parent.getEplId());

        return drugClass;
    }

    /**
     * Fully copies data from the given EplProdDrugClassAssocDo into a DrugClassGroupVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated DrugClassGroupVo are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data EplProdDrugClassAssocDo} to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DrugClassGroupVo toValueObject(EplProdDrugClassAssocDo data) {
        DrugClassVo drugVo = drugClassConverter.convertMinimal(data.getEplVaDrugClass());
        DrugClassGroupVo drugClassGroup = new DrugClassGroupVo();
        drugClassGroup.setPrimary(toBoolean(data.getPrimaryYn()));
        drugClassGroup.setDrugClass(drugVo);

        return drugClassGroup;
    }

    /**
     * setDrugClassConverter
     * 
     * @param drugClassConverter drugClassConverter property
     */
    public void setDrugClassConverter(DrugClassConverter drugClassConverter) {
        this.drugClassConverter = drugClassConverter;
    }
}
