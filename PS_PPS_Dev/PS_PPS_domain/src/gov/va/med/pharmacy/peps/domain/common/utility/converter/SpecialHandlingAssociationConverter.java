/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdSpecHandlingAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdSpecHandlingAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSpecialHandlingDo;


/**
 * Convert to/from {@link SpecialHandlingVo} and {@link EplProdSpecHandlingAssocDo}.
 */
public class SpecialHandlingAssociationConverter extends
    AssociationConverter<SpecialHandlingVo, EplProdSpecHandlingAssocDo, EplProductDo> {

    private SpecialHandlingConverter specialHandlingConverter;

    /**
     * Fully copies data from the given EplProdSpecHandlingAssocDo into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(ValueObject)} and then populate the parent
     * data into the association.
     * <p>
     * The parent EplProductDo must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data EplProdSpecHandlingAssocDo to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated EplProductDo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.AssociationConverter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject,
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected EplProdSpecHandlingAssocDo toDataObject(SpecialHandlingVo data, EplProductDo parent, int sequence) {
        EplProdSpecHandlingAssocDo specialHandling = convert(data);
        specialHandling.getKey().setEplIdProdFk(parent.getEplId());

        return specialHandling;
    }

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
    protected EplProdSpecHandlingAssocDo toDataObject(SpecialHandlingVo data) {
        EplProdSpecHandlingAssocDo drugClass = new EplProdSpecHandlingAssocDo();
        EplProdSpecHandlingAssocDoKey key = new EplProdSpecHandlingAssocDoKey();
        key.setEplIdSpecHandlingFk(Long.valueOf(data.getId()));
        drugClass.setKey(key);

        return drugClass;
    }

    /**
     * Retrieves the {@link EplSpecialHandlingDo} and calls
     * {@link SpecialHandlingConverter#convertMinimal(EplSpecialHandlingDo)}.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected SpecialHandlingVo toValueObject(EplProdSpecHandlingAssocDo data) {
        return specialHandlingConverter.convertMinimal(data.getEplSpecialHandling());
    }

    /**
     * setSpecialHandlingConverter
     * 
     * @param specialHandlingConverter specialHandlingConverter property
     */
    public void setSpecialHandlingConverter(SpecialHandlingConverter specialHandlingConverter) {
        this.specialHandlingConverter = specialHandlingConverter;
    }
}
