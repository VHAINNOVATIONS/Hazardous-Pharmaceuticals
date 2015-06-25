/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdWarnLabelNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdWarnLabelNAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplWarnLabelDo;


/**
 * Convert to/from {@link RxConsultVo} and {@link EplProdWarnLabelNAssocDo}.
 */
public class NationalRxConsultAssociationConverter extends
    AssociationConverter<RxConsultVo, EplProdWarnLabelNAssocDo, EplProductDo> {

    private RxConsultConverter rxConsultConverter;

    /**
     * Convert a Collection of {@link DataObject} into a List of fully populated RxConsultVo.
     * <p>
     * Overrides default implementation to force an order to the List returned based upon the sequence number stored in the
     * {@link DataObject}
     * 
     * @param data Collection of {@link DataObject} to convert
     * @return List of fully populated {@link RxConsultVo}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#convert(java.util.Collection)
     */
    @Override
    public List<RxConsultVo> convert(Collection<EplProdWarnLabelNAssocDo> data) {
        List<RxConsultVo> rxValues = new ArrayList<RxConsultVo>();

        if (data != null) {
            RxConsultVo[] warningLabels = new RxConsultVo[data.size()];

            for (EplProdWarnLabelNAssocDo current : data) {
                RxConsultVo value = convert(current);

                if (value != null) {
                    warningLabels[current.getKey().getSeqNumber()] = value;
                }
            }
            
            rxValues = Arrays.asList(warningLabels);
        }

        return rxValues;
    }

    /**
     * Fully copies data from the given RxConsultVo into a EplProductDo
     * <p>
     * Implementations should populate the data with a call to {@link #convert(RxConsultVo)} and then populate the parent
     * data into the association.
     * <p>
     * The parent EplProductDo must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data RxConsultVo to convert into a data object
     * @param parent EplProductDo representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplProdWarnLabelNAssocDo toDataObject(RxConsultVo data, EplProductDo parent, int sequence) {
        EplProdWarnLabelNAssocDo warningLabel = convert(data);
        warningLabel.getKey().setEplIdProdFk(parent.getEplId());
        warningLabel.getKey().setSeqNumber(sequence);

        return warningLabel;
    }

    /**
     * Fully copies data from the given {@link RxConsultVo} into a {@link DataObject}.
     * 
     * @param data RxConsultVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplProdWarnLabelNAssocDo toDataObject(RxConsultVo data) {
        EplProdWarnLabelNAssocDo warnLabel = new EplProdWarnLabelNAssocDo();
        EplProdWarnLabelNAssocDoKey key = new EplProdWarnLabelNAssocDoKey();
        key.setEplIdWarnLabelFk(Long.valueOf(data.getId()));
        warnLabel.setKey(key);

        EplWarnLabelDo warnDo = new EplWarnLabelDo();
        warnDo.setEplId(Long.valueOf(data.getId()));
        warnLabel.setEplWarnLabel(warnDo);

        return warnLabel;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a {@link RxConsultVo}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link RxConsultVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated RxConsultVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected RxConsultVo toValueObject(EplProdWarnLabelNAssocDo data) {
        return rxConsultConverter.convert(data.getEplWarnLabel());
    }

    /**
     * setRxConsultConverter
     * @param rxConsultConverter rxConsultConverter property
     */
    public void setRxConsultConverter(RxConsultConverter rxConsultConverter) {
        this.rxConsultConverter = rxConsultConverter;
    }
}
