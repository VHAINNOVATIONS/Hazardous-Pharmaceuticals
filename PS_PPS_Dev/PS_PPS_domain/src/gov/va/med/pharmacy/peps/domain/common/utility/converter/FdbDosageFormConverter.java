/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbDosageFormVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbDosageFormDo;


/**
 * Convert to/from FdbDosageFormVo and {@link EplFdbDosageFormDo}.
 */
public class FdbDosageFormConverter extends
        Converter<FdbDosageFormVo, EplFdbDosageFormDo> {

    /**
     * Standard constructor
     */
    public FdbDosageFormConverter() {
    }

    /**
     * Fully copies data from the given FdbDosageFormVo into a
     * {@link DataObject}.
     * 
     * @param data
     *           FdbDosageFormVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbDosageFormDo toDataObject(FdbDosageFormVo data) {
        EplFdbDosageFormDo fdbDosageForm = new EplFdbDosageFormDo();

        fdbDosageForm.setDrugDosageFormDesc(data.getDrugDosageFormDesc());
        fdbDosageForm.setEplDosageFormFk(data.getEplId());
        
        return fdbDosageForm;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbDosageFormVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated FdbDosageFormVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbDosageFormVo toValueObject(EplFdbDosageFormDo data) {
        FdbDosageFormVo fdbDosageForm = new FdbDosageFormVo();

        fdbDosageForm.setDrugDosageFormDesc(data.getDrugDosageFormDesc());
        fdbDosageForm.setEplId(data.getEplDosageFormFk());
        
        return fdbDosageForm;
    }
}
