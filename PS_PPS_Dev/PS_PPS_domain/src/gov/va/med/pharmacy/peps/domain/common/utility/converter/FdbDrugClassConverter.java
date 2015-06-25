/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbDrugClassVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDrugClassDo;


/**
 * Convert to/from {@link FdbDrugClassVo} and {@link EplFdbDrugClassDo}.
 */
public class FdbDrugClassConverter extends
        Converter<FdbDrugClassVo, EplFdbDrugClassDo> {

    /**
     * Standard constructor
     */
    public FdbDrugClassConverter() {
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * 
     * @param data
     *            {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbDrugClassDo toDataObject(FdbDrugClassVo data) {
        EplFdbDrugClassDo fdbDrugClass = new EplFdbDrugClassDo();

        fdbDrugClass.setFdbDrugClass(data.getFdbDrugClass());
        fdbDrugClass.setEplDrugClassFk(data.getEplId());

        return fdbDrugClass;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbDrugClassVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated FdbDrugClassVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbDrugClassVo toValueObject(EplFdbDrugClassDo data) {
        FdbDrugClassVo fdbDrugClass = new FdbDrugClassVo();

        fdbDrugClass.setFdbDrugClass(data.getFdbDrugClass());
        fdbDrugClass.setEplId(data.getEplDrugClassFk());

        return fdbDrugClass;
    }
}
