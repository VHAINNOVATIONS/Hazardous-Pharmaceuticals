/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAutoUpdateDo;


/**
 * Convert to/from FdbAutoUpdateVo and {@link EplIngredientDo}.
 */
public class FdbAutoUpdateConverter extends
        Converter<FdbAutoUpdateVo, EplFdbAutoUpdateDo> {

    /**
     * Standard constructor
     */
    public FdbAutoUpdateConverter() {
    }

    /**
     * Fully copies data from the given FdbAutoUpdateVo into a
     * {@link DataObject}.
     * 
     * @param data
     *            FdbAutoUpdateVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *          gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbAutoUpdateDo toDataObject(FdbAutoUpdateVo data) {
        EplFdbAutoUpdateDo fdbAutoUpdateDo = new EplFdbAutoUpdateDo();

        fdbAutoUpdateDo.setEplId(Long.valueOf(data.getId()));
        fdbAutoUpdateDo.setNdc(data.getNdc());
        fdbAutoUpdateDo.setGcnSeqno(data.getGcnSeqno());
        fdbAutoUpdateDo.setMessage(data.getMessage());
        fdbAutoUpdateDo.setVaProductName(data.getVaProductName());
        fdbAutoUpdateDo.setFdbProductName(data.getFdbProductName());
        fdbAutoUpdateDo.setNdcIdFk(data.getNdcIdFk());
        fdbAutoUpdateDo.setProductIdFk(data.getProductFk());

        return fdbAutoUpdateDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbAutoUpdateVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populatedFdbAutoUpdateVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbAutoUpdateVo toValueObject(EplFdbAutoUpdateDo data) {
        FdbAutoUpdateVo fdbAutoUpdateVo = new FdbAutoUpdateVo();

        fdbAutoUpdateVo.setEplId(data.getEplId());
        fdbAutoUpdateVo.setNdc(data.getNdc());
        fdbAutoUpdateVo.setGcnSeqno(data.getGcnSeqno());
        fdbAutoUpdateVo.setMessage(data.getMessage());
        fdbAutoUpdateVo.setVaProductName(data.getVaProductName());
        fdbAutoUpdateVo.setFdbProductName(data.getFdbProductName());
        fdbAutoUpdateVo.setNdcIdFk(data.getNdcIdFk());
        fdbAutoUpdateVo.setProductFk(data.getProductIdFk());

        
        return fdbAutoUpdateVo;
    }
}
