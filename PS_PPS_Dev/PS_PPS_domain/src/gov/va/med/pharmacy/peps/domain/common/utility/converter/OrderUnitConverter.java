/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderUnitDo;


/**
 * Convert to/from {@link OrderUnitVo} and {@link EplOrderUnitDo}.
 */
public class OrderUnitConverter extends Converter<OrderUnitVo, EplOrderUnitDo> {

    /**
     * Fully copies data from the given OrderUnitVo into a {@link DataObject}.
     * 
     * @param data OrderUnitVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplOrderUnitDo toDataObject(OrderUnitVo data) {
        EplOrderUnitDo orderUnit = new EplOrderUnitDo();

        orderUnit.setEplId(Long.valueOf(data.getId()));
        orderUnit.setAbbrev(data.getAbbrev());
        orderUnit.setRevisionNumber(data.getRevisionNumber());
        orderUnit.setOrderUnitExpansion(data.getExpansion());
        orderUnit.setCreatedBy(data.getCreatedBy());
        orderUnit.setCreatedDtm(data.getCreatedDate());
        orderUnit.setLastModifiedBy(data.getModifiedBy());
        orderUnit.setLastModifiedDtm(data.getModifiedDate());
        orderUnit.setRejectReasonText(data.getRejectionReasonText());
        orderUnit.setInactivationDate(data.getInactivationDate());

        if (data.getItemStatus() != null) {
            orderUnit.setItemStatus(data.getItemStatus().name());
        }

        if (data.getRequestItemStatus() != null) {
            orderUnit.setRequestStatus(data.getRequestItemStatus().name());
        }

        return orderUnit;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a OrderUnitVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link OrderUnitVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated OrderUnitVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected OrderUnitVo toValueObject(EplOrderUnitDo data) {
        OrderUnitVo orderUnit = new OrderUnitVo();

        orderUnit.setId(String.valueOf(data.getEplId()));
        orderUnit.setAbbrev(data.getAbbrev());
        orderUnit.setExpansion(data.getOrderUnitExpansion());
        orderUnit.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        orderUnit.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        orderUnit.setRejectionReasonText(data.getRejectReasonText());
        orderUnit.setRevisionNumber(data.getRevisionNumber());
        orderUnit.setCreatedBy(data.getCreatedBy());
        orderUnit.setCreatedDate(data.getCreatedDtm());
        orderUnit.setModifiedBy(data.getLastModifiedBy());
        orderUnit.setModifiedDate(data.getLastModifiedDtm());
        orderUnit.setInactivationDate(data.getInactivationDate());

        return orderUnit;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a OrderUnitVo.
     * <p>
     * The returned OrderUnitVo likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated OrderUnitVo
     */
    @Override
    protected OrderUnitVo toMinimalValueObject(EplOrderUnitDo data) {
        OrderUnitVo orderUnit = new OrderUnitVo();

        orderUnit.setId(String.valueOf(data.getEplId()));
        orderUnit.setAbbrev(data.getAbbrev());

        return orderUnit;
    }
}
