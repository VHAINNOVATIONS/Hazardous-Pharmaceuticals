/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.DrugMonographGcnseqnoAssocsVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbGcnseqnoPemDo;;


/**
 * Convert to/from {@link DrugMonographGcnseqnoAssocsVo} and {@link EplFdbGcnseqnoPemDo}.
 */
public class FdbGcnseqnoPemConverter extends
        Converter<DrugMonographGcnseqnoAssocsVo, EplFdbGcnseqnoPemDo> {

    /**
     * Standard constructor
     */
    public FdbGcnseqnoPemConverter() {
        
    }

    /**
     * Fully copies data from the given DrugMonographGcnseqnoAssocsVo into a
     * {@link DataObject}.
     * 
     * @param data
     *           DrugMonographGcnseqnoAssocsVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbGcnseqnoPemDo toDataObject(DrugMonographGcnseqnoAssocsVo data) {
        EplFdbGcnseqnoPemDo fdbGcnseqnoPemDo = new EplFdbGcnseqnoPemDo();

        fdbGcnseqnoPemDo.setGcnSeqNo(data.getGcnSeqNo());
        fdbGcnseqnoPemDo.setMonographId(data.getMonographId());
        fdbGcnseqnoPemDo.setEplId(data.getEplId());
        
        return fdbGcnseqnoPemDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * DrugMonographGcnseqnoAssocsVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated DrugMonographGcnseqnoAssocsVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DrugMonographGcnseqnoAssocsVo toValueObject(EplFdbGcnseqnoPemDo data) {
        DrugMonographGcnseqnoAssocsVo drugMonographGcnseqnoAssocsVo = new DrugMonographGcnseqnoAssocsVo();

        drugMonographGcnseqnoAssocsVo.setGcnSeqNo(data.getGcnSeqNo());
        drugMonographGcnseqnoAssocsVo.setMonographId(data.getMonographId());
        drugMonographGcnseqnoAssocsVo.setEplId(data.getEplId());
        
        return drugMonographGcnseqnoAssocsVo;
    }
}
