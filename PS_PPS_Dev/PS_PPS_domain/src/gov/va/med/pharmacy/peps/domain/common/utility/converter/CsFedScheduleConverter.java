/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.CsFedScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplCsFedScheduleDo;


/**
 * Convert to/from {@link CsFedScheduleVo} and {@link EplCsFedScheduleDo}.
 */
public class CsFedScheduleConverter extends Converter<CsFedScheduleVo, EplCsFedScheduleDo> {

    /**
     * Fully copies data from the given {@link DataObject} into a {@link ValueObject}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     */
    @Override
    protected CsFedScheduleVo toValueObject(EplCsFedScheduleDo data) {
        CsFedScheduleVo csFedSchedule = new CsFedScheduleVo();
        csFedSchedule.setId(data.getEplId().toString());
        csFedSchedule.setValue(data.getScheduleName());

        if (data.getInactivationDate() != null) {
            csFedSchedule.setInactivationDate(data.getInactivationDate());
        }

        csFedSchedule.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        csFedSchedule.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        csFedSchedule.setRejectionReasonText(data.getRejectReasonText());
        csFedSchedule.setRevisionNumber(data.getRevisionNumber());
        csFedSchedule.setCreatedBy(data.getCreatedBy());
        csFedSchedule.setCreatedDate(data.getCreatedDtm());
        csFedSchedule.setModifiedBy(data.getLastModifiedBy());
        csFedSchedule.setModifiedDate(data.getLastModifiedDtm());

        if (data.getRequestRejectReason() != null) {
            csFedSchedule.setRequestRejectReason(data.getRequestRejectReason());
        }

        return csFedSchedule;
    }

    /**
     * Fully copies data from the given CsFedScheduleVo into a EplCsFedScheduleDo.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplCsFedScheduleDo toDataObject(CsFedScheduleVo data) {
        EplCsFedScheduleDo unit = new EplCsFedScheduleDo();

        unit.setScheduleName(data.getValue());
        unit.setEplId(new Long(data.getId()));

        if (data.getItemStatus() != null) {
            unit.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            unit.setRequestStatus(data.getRequestItemStatus().toString());
        }

        unit.setRejectReasonText(data.getRejectionReasonText());
        unit.setRevisionNumber(data.getRevisionNumber());

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(data.getInactivationDate());
        }

        if (data.getRequestRejectReason() != null) {
            unit.setRequestRejectReason(data.getRequestRejectReason().toString());
        }

        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDtm(data.getCreatedDate());
        unit.setLastModifiedBy(data.getModifiedBy());
        unit.setLastModifiedDtm(data.getModifiedDate());

        return unit;
    }

}
