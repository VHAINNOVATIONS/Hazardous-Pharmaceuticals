/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdWarnLabelLAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdWarnLabelLAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplWarnLabelDo;


/**
 * Convert to/from {@link RxConsultVo} and {@link EplProdWarnLabelLAssocDo}.
 */
public class LocalRxConsultAssociationConverter extends
    AssociationConverter<RxConsultVo, EplProdWarnLabelLAssocDo, EplProductDo> {

    private RxConsultConverter rxConsultConverter;

    /**
     * Convert a Collection of {@link DataObject} into a List of fully populated RxConsultVo.
     * <p>
     * Overrides default implementation to force an order to the List returned based upon the sequence number stored in the
     * {@link DataObject}
     * 
     * @param data Collection of {@link DataObject} to convert
     * @return List of fully populated RxConsultVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#convert(java.util.Collection)
     */
    @Override
    public List<RxConsultVo> convert(Collection<EplProdWarnLabelLAssocDo> data) {
        List<RxConsultVo> values = new ArrayList<RxConsultVo>();

        if (data != null) {
            RxConsultVo[] warningLabels = new RxConsultVo[data.size()];

            for (EplProdWarnLabelLAssocDo current : data) {
                RxConsultVo value = convert(current);

                if (value != null) {
                    warningLabels[current.getKey().getSeqNumber()] = value;
                }
            }

            values = Arrays.asList(warningLabels);
        }

        return values;
    }

    /**
     * Fully copies data from the given RxConsultVo into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(ValueObject)} and then populate the parent
     * data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data RxConsultVo to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.AssociationConverter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject,
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected EplProdWarnLabelLAssocDo toDataObject(RxConsultVo data, EplProductDo parent, int sequence) {
        EplProdWarnLabelLAssocDo warningLabel = convert(data);
        warningLabel.getKey().setEplIdProdFk(parent.getEplId());
        warningLabel.getKey().setSeqNumber(sequence);

        return warningLabel;
    }

    /**
     * Fully copies data from the givenRxConsultVo into a {@link DataObject}.
     * 
     * @param data RxConsultVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplProdWarnLabelLAssocDo toDataObject(RxConsultVo data) {
        EplProdWarnLabelLAssocDo warnLabel = new EplProdWarnLabelLAssocDo();
        EplProdWarnLabelLAssocDoKey key = new EplProdWarnLabelLAssocDoKey();
        key.setEplIdWarnLabelFk(Long.valueOf(data.getId()));
        warnLabel.setKey(key);

        EplWarnLabelDo warnDo = new EplWarnLabelDo();
        warnDo.setEplId(Long.valueOf(data.getId()));
        warnLabel.setEplWarnLabel(warnDo);

        return warnLabel;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a RxConsultVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated RxConsultVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected RxConsultVo toValueObject(EplProdWarnLabelLAssocDo data) {
        return rxConsultConverter.convert(data.getEplWarnLabel());
    }

    /**
     * setRxConsultConverter
     * 
     * @param rxConsultConverter rxConsultConverter property
     */
    public void setRxConsultConverter(RxConsultConverter rxConsultConverter) {
        this.rxConsultConverter = rxConsultConverter;
    }
}
