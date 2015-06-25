/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDispenseUnitDo;


/**
 * Convert to/from {@link DispenseUnitVo} and {@link EplDispenseUnitDo}.
 */
public class DispenseUnitConverter extends Converter<DispenseUnitVo, EplVaDispenseUnitDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplVaDispenseUnitDo toDataObject(DispenseUnitVo data) {
        EplVaDispenseUnitDo unit = new EplVaDispenseUnitDo();

        unit.setDispenseUnitName(data.getValue());
        unit.setEplId(new Long(data.getId()));
        
        if (data.getDispenseUnitIen() != null) {
            unit.setNdfDispenseunitIen(new Long(data.getDispenseUnitIen()));
        }
        
        unit.setRevisionNumber(data.getRevisionNumber());
        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDtm(data.getCreatedDate());
        unit.setLastModifiedBy(data.getModifiedBy());
        unit.setLastModifiedDtm(data.getModifiedDate());

        if (data.getItemStatus() != null) {
            unit.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            unit.setRequestStatus(data.getRequestItemStatus().toString());
        }

        unit.setRejectReasonText(data.getRejectionReasonText());

        if (data.getRequestRejectionReason() != null) {
            unit.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(data.getInactivationDate());
        }

        return unit;
    }

    /**
     * Fully copies data from the given EplVaDispenseUnitDo into a DispenseUnitVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated DispenseUnitVo are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate converter should be called.
     * 
     * @param data EplVaDispenseUnitDo to convert
     * @return fully populated DispenseUnitVo
     * 
     */
    @Override
    protected DispenseUnitVo toValueObject(EplVaDispenseUnitDo data) {
        DispenseUnitVo unit = new DispenseUnitVo();
        unit.setId(data.getEplId().toString());
        
        if (data.getNdfDispenseunitIen() != null) {
            unit.setDispenseUnitIen(data.getNdfDispenseunitIen().toString());
        }
        
        unit.setValue(data.getDispenseUnitName());
        unit.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        unit.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        unit.setRejectionReasonText(data.getRejectReasonText());
        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDate(data.getCreatedDtm());
        unit.setModifiedBy(data.getLastModifiedBy());
        unit.setModifiedDate(data.getLastModifiedDtm());

        if (data.getRequestRejectReason() != null) {
            unit.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        unit.setRevisionNumber(data.getRevisionNumber());

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(data.getInactivationDate());
        }

        return unit;
    }
}
