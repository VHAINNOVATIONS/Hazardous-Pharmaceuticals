/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;


/**
 * Convert to/from {@link ManufacturerVo} and {@link EplManufacturerDo}.
 */
public class ManufacturerConverter extends Converter<ManufacturerVo, EplManufacturerDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplManufacturerDo toDataObject(ManufacturerVo data) {
        EplManufacturerDo manufacturer = new EplManufacturerDo();

        manufacturer.setEplId(Long.valueOf(data.getId()));

        if (data.getManufacturerIen() != null) {
            manufacturer.setNdfManufacturerIen(new Long(data.getManufacturerIen()));
        }

        manufacturer.setName(data.getValue());
        manufacturer.setAddress(data.getAddress());
        manufacturer.setPhone(data.getPhone());
        manufacturer.setRevisionNumber(data.getRevisionNumber());
        
        if (data.getManufacturerIen() != null) {
            manufacturer.setNdfManufacturerIen(Long.valueOf(data.getManufacturerIen()));
        }
        
        manufacturer.setCreatedBy(data.getCreatedBy());
        manufacturer.setCreatedDtm(data.getCreatedDate());
        manufacturer.setLastModifiedBy(data.getModifiedBy());
        manufacturer.setLastModifiedDtm(data.getModifiedDate());
        manufacturer.setRejectReasonText(data.getRejectionReasonText());
        manufacturer.setInactivationDate(data.getInactivationDate());

        if (data.getItemStatus() != null) {
            manufacturer.setItemStatus(data.getItemStatus().name());
        }

        if (data.getRequestItemStatus() != null) {
            manufacturer.setRequestStatus(data.getRequestItemStatus().name());
        }

        return manufacturer;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a ManufacturerVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated ManufacturerVo
     * 
     */
    @Override
    protected ManufacturerVo toValueObject(EplManufacturerDo data) {
        ManufacturerVo manufacturer = new ManufacturerVo();

        manufacturer.setId(String.valueOf(data.getEplId()));

        if (data.getNdfManufacturerIen() != null) {
            manufacturer.setManufacturerIen(data.getNdfManufacturerIen().toString());
        }

     
        manufacturer.setValue(data.getName());
        manufacturer.setAddress(data.getAddress());
        manufacturer.setPhone(data.getPhone());
        manufacturer.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        manufacturer.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        manufacturer.setRejectionReasonText(data.getRejectReasonText());
        manufacturer.setRevisionNumber(data.getRevisionNumber());
        manufacturer.setCreatedBy(data.getCreatedBy());
        manufacturer.setCreatedDate(data.getCreatedDtm());
        manufacturer.setModifiedBy(data.getLastModifiedBy());
        manufacturer.setModifiedDate(data.getLastModifiedDtm());
        manufacturer.setInactivationDate(data.getInactivationDate());

        return manufacturer;
    }

    /**
     * toMinimalValueObject(EplManufacturerDo data) 
     * 
     * @param data EplManufacturerDo
     * @return minimally populated ManufacturerVo
     */
    @Override
    protected ManufacturerVo toMinimalValueObject(EplManufacturerDo data) {
        ManufacturerVo manufacturer = new ManufacturerVo();

        manufacturer.setId(String.valueOf(data.getEplId()));
        manufacturer.setValue(data.getName());

        if (data.getNdfManufacturerIen() != null) {
            manufacturer.setManufacturerIen(data.getNdfManufacturerIen().toString());
        }

        return manufacturer;
    }
}
