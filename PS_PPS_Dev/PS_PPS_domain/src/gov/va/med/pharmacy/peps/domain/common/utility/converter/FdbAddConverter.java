/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbAddDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;



/**
 * Convert to/from {@link IngredientVo} and {@link EplIngredientDo}.
 */
public class FdbAddConverter extends Converter<FdbAddVo, EplFdbAddDo> {

    /**
     * Standard constructor
     */
    public FdbAddConverter() {
        super();
    }

    /**
     * Fully copies data from the given FdbAddVo into a
     * {@link DataObject}.
     * 
     * @param data FdbAddVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbAddDo toDataObject(FdbAddVo data) {
     
        EplFdbAddDo fdbAddDo = new EplFdbAddDo();

        fdbAddDo.setNdc(data.getNdc());
        fdbAddDo.setPackageSize(data.getPackageSize());
        fdbAddDo.setPackageType(data.getPackageType());
        fdbAddDo.setManufacturer(data.getManufacturer());
        fdbAddDo.setLabelName(data.getLabelName());
        fdbAddDo.setAddDesc(data.getAddDesc());
        fdbAddDo.setTradeName(data.getTradeName());
        fdbAddDo.setFdbProductName(data.getFdbProductName());
        fdbAddDo.setGcnSeqno(data.getGcnSeqno());
        fdbAddDo.setFdbCreationDate(data.getFdbCreationDate());

        return fdbAddDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * FdbAddVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated FdbAddVo
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populatedFdbAddVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected FdbAddVo toValueObject(EplFdbAddDo data) {
        FdbAddVo fdbAddVo = new FdbAddVo();
        
        fdbAddVo.setNdc(data.getNdc());
        fdbAddVo.setPackageSize(data.getPackageSize());
        fdbAddVo.setPackageType(data.getPackageType());
        fdbAddVo.setManufacturer(data.getManufacturer());
        fdbAddVo.setLabelName(data.getLabelName());
        fdbAddVo.setAddDesc(data.getAddDesc());
        fdbAddVo.setTradeName(data.getTradeName());
        fdbAddVo.setFdbProductName(data.getFdbProductName());
        fdbAddVo.setGcnSeqno(data.getGcnSeqno());
        fdbAddVo.setFdbCreationDate(data.getFdbCreationDate());

        return fdbAddVo;
    }
}
