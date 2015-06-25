/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbGenericNameVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbGenericNameDo;


/**
 * Convert to/from {@link FdbGenericNameVo} and {@link EplFdbGenericNameDo}.
 */
public class FdbGenericNameConverter extends
        Converter<FdbGenericNameVo, EplFdbGenericNameDo> {

    /**
     * Standard constructor
     */
    public FdbGenericNameConverter() {
    }

    /**
     * Fully copies data from the given FdbGenericNameVo into a
     * {@link DataObject}.
     * 
     * @param data
     *            FdbGenericNameVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbGenericNameDo toDataObject(FdbGenericNameVo data) {
        EplFdbGenericNameDo fdbGenericName = new EplFdbGenericNameDo();

        fdbGenericName.setFdbGenericDrugname(data.getFdbGenericDrugname());
        fdbGenericName.setEplGenericNameFk(data.getEplId());
        
        return fdbGenericName;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbGenericNameVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated FdbGenericNameVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbGenericNameVo toValueObject(EplFdbGenericNameDo data) {
        FdbGenericNameVo fdbGenericName = new FdbGenericNameVo();

        fdbGenericName.setFdbGenericDrugname(data.getFdbGenericDrugname());
        fdbGenericName.setEplId(data.getEplGenericNameFk());
        
        return fdbGenericName;
    }
}
