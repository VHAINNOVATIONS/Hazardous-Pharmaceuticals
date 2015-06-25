/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EventCategory;
import gov.va.med.pharmacy.peps.common.vo.ItemAuditHistoryDetailsVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplItemAuditHistoryDetailDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplItemAuditHistoryDo;


/**
 * Convert to/from {@link ItemAuditHistoryDetailsVo} and {@link EplItemAuditHistoryDetailDo}.
 */
public class ItemAuditHistoryDetailsConverter extends
    AssociationConverter<ItemAuditHistoryDetailsVo, EplItemAuditHistoryDetailDo, EplItemAuditHistoryDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplItemAuditHistoryDetailDo toDataObject(ItemAuditHistoryDetailsVo data) {
        EplItemAuditHistoryDetailDo detail = new EplItemAuditHistoryDetailDo();

        detail.setColNm(data.getColumnName());
        detail.setCreatedBy(data.getCreatedBy());
        detail.setCreatedDtm(data.getCreatedDate());
        detail.setDetailReason(data.getDetailReason());

        if (NumberUtils.isNumber(data.getId())) {
            detail.setEplId(Long.valueOf(data.getId()));
        }

        detail.setDetailEventCategory(data.getEventCategory().toString());
        detail.setLastModifiedBy(data.getModifiedBy());
        detail.setLastModifiedDtm(data.getModifiedDate());
        
        if (StringUtils.isNotEmpty(data.getNewValue()) 
            && data.getNewValue().length() > PPSConstants.I4000) {
            detail.setNewValue(data.getNewValue().substring(0, PPSConstants.I4000));
        } else {
            detail.setNewValue(data.getNewValue());
        }
        
        if (StringUtils.isNotEmpty(data.getOldValue()) 
            && data.getOldValue().length() > PPSConstants.I4000) {
            detail.setOldValue(data.getOldValue().substring(0, PPSConstants.I4000));
        } else {
            detail.setOldValue(data.getOldValue());
        }

        return detail;
    }

    /**
     * Fully copies data from the given ItemAuditHistoryDetailsVo into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(ValueObject)} and then populate the parent
     * data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data ItemAuditHistoryDetailsVo to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated ItemAuditHistoryDetailsVo
     */
    @Override
    protected EplItemAuditHistoryDetailDo toDataObject(ItemAuditHistoryDetailsVo data, EplItemAuditHistoryDo parent,
                                                       int sequence) {
        EplItemAuditHistoryDetailDo detail = convert(data);
        detail.setEplItemAuditHistory(parent);

        return detail;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a ItemAuditHistoryDetailsVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated ItemAuditHistoryDetailsVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected ItemAuditHistoryDetailsVo toValueObject(EplItemAuditHistoryDetailDo data) {
        ItemAuditHistoryDetailsVo detail = new ItemAuditHistoryDetailsVo();
        detail.setColumnName(data.getColNm());
        detail.setEventCategory(EventCategory.valueOf(data.getDetailEventCategory()));
        detail.setCreatedBy(data.getCreatedBy());
        detail.setCreatedDate(data.getCreatedDtm());
        detail.setDetailReason(data.getDetailReason());
        detail.setId(String.valueOf(data.getEplId()));
        detail.setModifiedBy(data.getLastModifiedBy());
        detail.setModifiedDate(data.getLastModifiedDtm());
        detail.setNewValue(data.getNewValue());
        detail.setOldValue(data.getOldValue());

        return detail;
    }
}
