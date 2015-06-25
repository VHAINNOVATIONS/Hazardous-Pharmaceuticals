/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.IfcapItemNumberVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplIfcapItemNumberDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert to/from {@link IfcapItemNumberVo} and {@link EplIfcapItemNumberDo}.
 */
public class IfcapItemNumberConverter extends AssociationConverter<IfcapItemNumberVo, EplIfcapItemNumberDo, EplProductDo> {

    /**
     * Fully copies data from the given IfcapItemNumberVo into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(IfcapItemNumberVo)} and then populate the parent
     * data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data IfcapItemNumberVo to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current IfcapItemNumberVo to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.AssociationConverter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject,
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject, int)
     */
    @Override
    protected EplIfcapItemNumberDo toDataObject(IfcapItemNumberVo data, EplProductDo parent, int sequence) {
        EplIfcapItemNumberDo value = convert(data);
        value.setEplProduct(parent);
        
        return value;
    }

    /**
     * Fully copies data from the given IfcapItemNumberVo into a {@link DataObject}.
     * 
     * @param data dataIfcapItemNumberVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplIfcapItemNumberDo toDataObject(IfcapItemNumberVo data) {
        EplIfcapItemNumberDo value = new EplIfcapItemNumberDo();
        value.setIfcapItemNumber(data.getValue());
        
        return value;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a IfcapItemNumberVo}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated IfcapItemNumberVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected IfcapItemNumberVo toValueObject(EplIfcapItemNumberDo data) {
        IfcapItemNumberVo value = new IfcapItemNumberVo();
        value.setValue(data.getIfcapItemNumber());

        return value;
    }
}
