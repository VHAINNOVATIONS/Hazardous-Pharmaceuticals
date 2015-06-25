/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcByOutpatientSiteNdcVo;
import gov.va.med.pharmacy.peps.common.vo.OutpatientSiteVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcByOutpatientSiteNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert to/from {@link NdcByOutpatientSiteNdcVo} and {@link EplNdcByOutpatientSiteNdcDo}.
 */
public class NdcByOutpatientSiteNdcConverter extends
    AssociationConverter<NdcByOutpatientSiteNdcVo, EplNdcByOutpatientSiteNdcDo, EplProductDo> {

    /**
     * Fully copies data from the given {@link NdcByOutpatientSiteNdcVo} into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to NdcByOutpatientSiteNdcVo and then populate the parent
     * data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data {@link NdcByOutpatientSiteNdcVo} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current NdcByOutpatientSiteNdcVo to convert, used only if order 
     * matters in this association
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.AssociationConverter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject,
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject, int)
     */
    @Override
    protected EplNdcByOutpatientSiteNdcDo toDataObject(NdcByOutpatientSiteNdcVo data, EplProductDo parent, int sequence) {
        EplNdcByOutpatientSiteNdcDo value = convert(data);
        value.setEplProduct(parent);
        
        return value;
    }

    /**
     * Fully copies data from the given {@link NdcByOutpatientSiteNdcVo} into a {@link DataObject}.
     * 
     * @param data {@link NdcByOutpatientSiteNdcVo} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplNdcByOutpatientSiteNdcDo toDataObject(NdcByOutpatientSiteNdcVo data) {
        EplNdcByOutpatientSiteNdcDo value = new EplNdcByOutpatientSiteNdcDo();
        value.setOutpatientSite(data.getOutpatientSite().getValue());
        value.setLastCmopNdc(data.getLastCmopNdc());
        value.setLastLocalNdc(data.getLastLocalNdc());
        
        return value;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a {@link NdcByOutpatientSiteNdcVo}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link NdcByOutpatientSiteNdcVo}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected NdcByOutpatientSiteNdcVo toValueObject(EplNdcByOutpatientSiteNdcDo data) {
        NdcByOutpatientSiteNdcVo value = new NdcByOutpatientSiteNdcVo();
        value.setLastCmopNdc(data.getLastCmopNdc());
        value.setLastLocalNdc(data.getLastLocalNdc());
        
        OutpatientSiteVo outpatientSite = new OutpatientSiteVo();
        outpatientSite.setValue(data.getOutpatientSite());
        value.setOutpatientSite(outpatientSite);

        return value;
    }
}
