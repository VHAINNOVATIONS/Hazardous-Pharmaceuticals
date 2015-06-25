/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplItemAuditHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNotificationDo;


/**
 * Convert to/from {@link ItemAuditHistoryVo} and {@link EplItemAuditHistoryDo}.
 */
public class ItemAuditHistoryConverter extends
    AssociationConverter<ItemAuditHistoryVo, EplItemAuditHistoryDo, EplNotificationDo> {
    
    private ItemAuditHistoryDetailsConverter itemAuditHistoryDetailsConverter;

    /**
     * EplItemAuditHistoryDo toDataObject(ItemAuditHistoryVo data)
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplItemAuditHistoryDo toDataObject(ItemAuditHistoryVo data) {
        EplItemAuditHistoryDo audit = new EplItemAuditHistoryDo();

        audit.setEplId(Long.valueOf(data.getId()));
        audit.setAuditItemType(data.getAuditItemType().toString());
        audit.setAuditItemId(Long.valueOf(data.getAuditItemId()));
        audit.setSiteName(data.getSiteName());
        audit.setEventCategory(data.getEventCategory().toString());
        audit.setReason(data.getReason());
        audit.setCreatedBy(data.getUsername());
        audit.setEplItemAuditHistoryDetails(itemAuditHistoryDetailsConverter.convert(data.getDetails(), audit));

        return audit;
    }

    /**
     * EplItemAuditHistoryDo toDataObject(ItemAuditHistoryVo data, EplNotificationDo parent, int sequence)
     * 
     * @param data {@link ValueObject} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     */
    protected EplItemAuditHistoryDo toDataObject(ItemAuditHistoryVo data, EplNotificationDo parent, int sequence) {
        EplItemAuditHistoryDo audit = convert(data);
        audit.setEplNotification(parent);

        return audit;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a ItemAuditHistoryVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated ItemAuditHistoryVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected ItemAuditHistoryVo toValueObject(EplItemAuditHistoryDo data) {
        ItemAuditHistoryVo history = new ItemAuditHistoryVo();

        history.setId(data.getEplId().toString());
        history.setAuditItemType(EntityType.valueOf(data.getAuditItemType()));
        history.setAuditItemId(data.getAuditItemId().toString());
        history.setEventCategory(EventCategory.valueOf(data.getEventCategory()));
        history.setReason(data.getReason());
        history.setCreatedBy(data.getCreatedBy());
        history.setCreatedDate(data.getCreatedDtm());
        history.setDetails(itemAuditHistoryDetailsConverter.convert(data.getEplItemAuditHistoryDetails()));
        history.setUsername(data.getCreatedBy());
        history.setDtModified(data.getCreatedDtm());
        history.setSiteName(data.getSiteName());

        return history;
    }

    /**
     * setItemAuditHistoryDetailsConverter
     * @param itemAuditHistoryDetailsConverter itemAuditHistoryDetailsConverter property
     */
    public void setItemAuditHistoryDetailsConverter(ItemAuditHistoryDetailsConverter itemAuditHistoryDetailsConverter) {
        this.itemAuditHistoryDetailsConverter = itemAuditHistoryDetailsConverter;
    }
}
