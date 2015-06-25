/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbUpdateDo;


/**
 * Convert to/from {@link IngredientVo} and {@link EplIngredientDo}.
 */
public class FdbUpdateConverter extends Converter<FdbUpdateVo, EplFdbUpdateDo> {

    /**
     * Standard constructor
     */
    public FdbUpdateConverter() {
    }

    /**
     * Fully copies data from the given FdbUpdateVo into a
     * {@link DataObject}.
     * 
     * @param data
     *            FdbUpdateVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbUpdateDo toDataObject(FdbUpdateVo data) {
        EplFdbUpdateDo fdbUpdateDo = new EplFdbUpdateDo();

        fdbUpdateDo.setEplId(Long.valueOf(data.getId()));
        fdbUpdateDo.setGcnSeqno(data.getGcnSeqno());
        fdbUpdateDo.setMessage(data.getMessage());
        fdbUpdateDo.setVaProductName(data.getVaProductName());
        fdbUpdateDo.setNdc(data.getNdc());
        fdbUpdateDo.setFdbProductName(data.getFdbProductName());
        fdbUpdateDo.setNdcIdFk(data.getNdcIdFk());
        fdbUpdateDo.setProductIdFk(data.getProductFk());


        return fdbUpdateDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbUpdateVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated FdbUpdateVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbUpdateVo toValueObject(EplFdbUpdateDo data) {
        FdbUpdateVo fdbUpdateVo = new FdbUpdateVo();

        fdbUpdateVo.setEplId(data.getEplId());
        fdbUpdateVo.setGcnSeqno(data.getGcnSeqno());
        fdbUpdateVo.setMessage(data.getMessage());
        fdbUpdateVo.setVaProductName(data.getVaProductName());
        fdbUpdateVo.setNdc(data.getNdc());
        fdbUpdateVo.setFdbProductName(data.getFdbProductName());
        fdbUpdateVo.setNdcIdFk(data.getNdcIdFk());
        fdbUpdateVo.setProductFk(data.getProductIdFk());

        return fdbUpdateVo;
    }
}
