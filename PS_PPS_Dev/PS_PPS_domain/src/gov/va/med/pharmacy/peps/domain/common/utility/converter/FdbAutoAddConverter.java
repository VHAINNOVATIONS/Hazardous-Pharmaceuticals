/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAutoAddDo;



/**
 * Convert to/from {@link FdbAutoAddVo} and {@link EplIngredientDo}.
 */
public class FdbAutoAddConverter extends
        Converter<FdbAutoAddVo, EplFdbAutoAddDo> {

    /**
     * Standard constructor
     */
    public FdbAutoAddConverter() {
    }

    /**
     * Fully copies data from the given FdbAutoAddVo into a
     * {@link DataObject}.
     * 
     * @param data FdbAutoAddVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbAutoAddDo toDataObject(FdbAutoAddVo data) {
        EplFdbAutoAddDo fdbAutoAddDo = new EplFdbAutoAddDo();

        fdbAutoAddDo.setNdc(data.getNdc());
        fdbAutoAddDo.setPackageSize(data.getPackageSize());
        fdbAutoAddDo.setPackageType(data.getPackageType());
        fdbAutoAddDo.setLabelName(data.getLabelName());
        fdbAutoAddDo.setGcnSeqno(data.getGcnSeqno());
        fdbAutoAddDo.setVaProductName(data.getVaProductName());
        fdbAutoAddDo.setFdbProductName(data.getFdbProductName());
        fdbAutoAddDo.setAddDesc(data.getAddDesc());
        fdbAutoAddDo.setNdcIdFk(data.getNdcIdFk());

        return fdbAutoAddDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbAutoAddVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated FdbAutoAddVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbAutoAddVo toValueObject(EplFdbAutoAddDo data) {
        FdbAutoAddVo fdbAutoAddVo = new FdbAutoAddVo();

        fdbAutoAddVo.setNdc(data.getNdc());
        fdbAutoAddVo.setPackageSize(data.getPackageSize());
        fdbAutoAddVo.setPackageType(data.getPackageType());
        fdbAutoAddVo.setGcnSeqno(data.getGcnSeqno());
        fdbAutoAddVo.setLabelName(data.getLabelName());
        fdbAutoAddVo.setAddDesc(data.getAddDesc());
        fdbAutoAddVo.setVaProductName(data.getVaProductName());
        fdbAutoAddVo.setFdbProductName(data.getFdbProductName());
        fdbAutoAddVo.setNdcIdFk(data.getNdcIdFk());

        return fdbAutoAddVo;
    }
}
