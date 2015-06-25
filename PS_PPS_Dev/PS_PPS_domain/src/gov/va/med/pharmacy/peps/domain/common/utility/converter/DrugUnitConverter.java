/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.sql.Timestamp;

import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;



/**
 * Convert to/from {@link DrugUnitVo} and {@link EplDrugUnitDo}.
 */
public class DrugUnitConverter extends Converter<DrugUnitVo, EplDrugUnitDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplDrugUnitDo toDataObject(DrugUnitVo data) {
        EplDrugUnitDo unit = new EplDrugUnitDo();

        unit.setName(data.getValue());
        unit.setEplId(new Long(data.getId()));
        
        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDtm(data.getCreatedDate());
        unit.setLastModifiedBy(data.getModifiedBy());
        unit.setLastModifiedDtm(data.getModifiedDate());

        if (data.getDrugUnitIen() != null) {
            unit.setNdfDrugunitIen(new Long(data.getDrugUnitIen()));
        }

        if (data.getRequestItemStatus() != null) {
            unit.setRequestStatus(data.getRequestItemStatus().toString());
        }

        if (data.getItemStatus() != null) {
            unit.setItemStatus(data.getItemStatus().toString());
        }

        unit.setRejectReasonText(data.getRejectionReasonText());
        unit.setRevisionNumber(data.getRevisionNumber());

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(new Timestamp(data.getInactivationDate().getTime()));
        }

        return unit;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a DrugUnitVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated DrugUnitVo are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated DrugUnitVo
     * 
     */
    @Override
    protected DrugUnitVo toValueObject(EplDrugUnitDo data) {
        DrugUnitVo unit = new DrugUnitVo();
        unit.setId(data.getEplId().toString());
        
        if (data.getNdfDrugunitIen() != null) {
            unit.setDrugUnitIen(data.getNdfDrugunitIen().toString());
        }
        
        unit.setValue(data.getName());
        unit.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        unit.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        unit.setRejectionReasonText(data.getRejectReasonText());
        unit.setRevisionNumber(data.getRevisionNumber().longValue());
        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDate(data.getCreatedDtm());
        unit.setModifiedBy(data.getLastModifiedBy());
        unit.setModifiedDate(data.getLastModifiedDtm());

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(data.getInactivationDate());
        }

        return unit;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a DrugUnitVo.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls DrugUnitVo.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated DrugUnitVo
     */
    @Override
    protected DrugUnitVo toMinimalValueObject(EplDrugUnitDo data) {
        DrugUnitVo drugUnit = new DrugUnitVo();

        drugUnit.setId(String.valueOf(data.getEplId()));
        drugUnit.setValue(data.getName());

        if (data.getNdfDrugunitIen() != null) {
            drugUnit.setDrugUnitIen(data.getNdfDrugunitIen().toString());
        }
        
        return drugUnit;
    }
}
