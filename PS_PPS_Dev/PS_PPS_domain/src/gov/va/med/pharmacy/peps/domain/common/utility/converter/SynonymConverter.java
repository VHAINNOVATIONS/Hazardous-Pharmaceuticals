/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSynonymDo;


/**
 * Convert to/from {@link SynonymVo} and {@link EplSynonymDo}.
 */
public class SynonymConverter extends AssociationConverter<SynonymVo, EplSynonymDo, EplProductDo> {

    private OrderUnitConverter orderUnitConverter;
    private IntendedUseConverter intendedUseConverter;

    /**
     * Fully copies data from the given SynonymVo into a EplSynonymDo.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(ValueObject)} and then populate the parent
     * data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data SynonymVo to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated EplSynonymDo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.AssociationConverter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject,
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject, int)
     */
    @Override
    protected EplSynonymDo toDataObject(SynonymVo data, EplProductDo parent, int sequence) {
        EplSynonymDo value = convert(data);
        value.setEplProduct(parent);

        return value;
    }

    /**
     * Fully copies data from the given SynonymVo into a {@link DataObject}.
     * 
     * @param data {SynonymVo to convert
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplSynonymDo toDataObject(SynonymVo data) {
        EplSynonymDo synonym = new EplSynonymDo();
        
        synonym.setId(data.getId());

        if (data.getSynonymDispenseUnitPerOrderUnit() != null) {
            synonym.setDispenseUnitsPerOrderUnit(Double.valueOf(data.getSynonymDispenseUnitPerOrderUnit()));
        }

        synonym.setEplIntendedUs(intendedUseConverter.convert(data.getSynonymIntendedUse()));
        synonym.setEplOrderUnit(orderUnitConverter.convert(data.getSynonymOrderUnit()));

        synonym.setNdcCode(data.getNdcCode());
        synonym.setPricePerOrderUnit(data.getSynonymPricePerOrderUnit());
        synonym.setPricePerDispenseUnit(data.getSynonymPricePerDispenseUnit());
        
        if (StringUtils.isNotBlank(data.getSynonymName())) {
            synonym.setSynonymName(data.getSynonymName().toUpperCase(Locale.US));
        }
        
        synonym.setVendor(data.getSynonymVendor());
        synonym.setVsn(data.getSynonymVsn());

        return synonym;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a SynonymVo.
     * <p>
     * 
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated SynonymVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected SynonymVo toValueObject(EplSynonymDo data) {
        SynonymVo value = new SynonymVo();
        value.setId(data.getId());

        if (data.getDispenseUnitsPerOrderUnit() != null) {
            value.setSynonymDispenseUnitPerOrderUnit(data.getDispenseUnitsPerOrderUnit());
        }

        value.setSynonymIntendedUse(intendedUseConverter.convert(data.getEplIntendedUs()));
        value.setNdcCode(data.getNdcCode());
        value.setSynonymOrderUnit(orderUnitConverter.convertMinimal(data.getEplOrderUnit()));
        value.setSynonymPricePerDispenseUnit(data.getPricePerDispenseUnit());
        value.setSynonymPricePerOrderUnit(data.getPricePerOrderUnit());

        value.setSynonymVendor(data.getVendor());
        value.setSynonymVsn(data.getVendor());
        
        if (StringUtils.isNotBlank(data.getSynonymName())) {
            value.setSynonymName(data.getSynonymName().toUpperCase(Locale.US));
        }
        

        return value;
    }

    /**
     * setOrderUnitConverter
     * @param orderUnitConverter orderUnitConverter property
     */
    public void setOrderUnitConverter(OrderUnitConverter orderUnitConverter) {
        this.orderUnitConverter = orderUnitConverter;
    }

    /**
     * setIntendedUseConverter
     * @param intendedUseConverter intendedUseConverter property
     */
    public void setIntendedUseConverter(IntendedUseConverter intendedUseConverter) {
        this.intendedUseConverter = intendedUseConverter;
    }
}
