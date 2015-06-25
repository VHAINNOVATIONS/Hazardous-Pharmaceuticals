/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.RxConsultType;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplWarnLabelDo;


/**
 * Convert to/from {@link RxConsultVo} and {@link EplWarnLabelDo}.
 */
public class RxConsultConverter extends Converter<RxConsultVo, EplWarnLabelDo> {

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
    protected EplWarnLabelDo toDataObject(RxConsultVo data) {
        EplWarnLabelDo eplWarnLabel = new EplWarnLabelDo();

        eplWarnLabel.setEplId(Long.valueOf(data.getId()));
        eplWarnLabel.setCreatedBy(data.getCreatedBy());
        eplWarnLabel.setCreatedDtm(data.getCreatedDate());
        eplWarnLabel.setLastModifiedBy(data.getModifiedBy());
        eplWarnLabel.setLastModifiedDtm(data.getModifiedDate());
        eplWarnLabel.setInactivationDate(data.getInactivationDate());
        eplWarnLabel.setEplWarnLabel(convertMinimal(data.getWarningMapping()));
        eplWarnLabel.setName(data.getValue());
        eplWarnLabel.setWarningLabelType(data.getRxConsultType().name());
        eplWarnLabel.setSpanishTranslation(data.getSpanishTranslation());
        eplWarnLabel.setConsultText(data.getConsultText());
        eplWarnLabel.setRejectReasonText(data.getRejectionReasonText());
        eplWarnLabel.setRevisionNumber(data.getRevisionNumber());

        if (data.getRequestItemStatus() != null) {
            eplWarnLabel.setRequestStatus(data.getRequestItemStatus().name());
        }

        if (data.getRequestRejectionReason() != null) {
            eplWarnLabel.setRequestRejectReason(data.getRequestRejectionReason().name());
        }

        if (data.getItemStatus() != null) {
            eplWarnLabel.setItemStatus(data.getItemStatus().name());
        }

        return eplWarnLabel;
    }

    /**
     * Minimally copies data from the given {@link ValueObject} into a {@link DataObject}.
     * <p>
     * The returned {@link DataObject} likely only has the primary key data populated.
     * <p>
     * Set the ID and Name attributes.
     * 
     * @param data {@link ValueObject} to convert
     * @return minimally populated {@link DataObject}
     */
    protected EplWarnLabelDo toMinimalDataObject(RxConsultVo data) {
        EplWarnLabelDo warnLabel = new EplWarnLabelDo();
        warnLabel.setEplId(Long.valueOf(data.getId()));
        warnLabel.setName(data.getValue());

        return warnLabel;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a RxConsultVo.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     */
    @Override
    protected RxConsultVo toValueObject(EplWarnLabelDo data) {
        RxConsultVo valueObject = new RxConsultVo();

        valueObject.setId(String.valueOf(data.getEplId()));
        
        valueObject.setCreatedBy(data.getCreatedBy());
        valueObject.setCreatedDate(data.getCreatedDtm());
        valueObject.setModifiedBy(data.getLastModifiedBy());
        valueObject.setModifiedDate(data.getLastModifiedDtm());
        valueObject.setValue(data.getName());
        valueObject.setWarningMapping(convertMinimal(data.getEplWarnLabel()));
        valueObject.setSpanishTranslation(data.getSpanishTranslation());
        valueObject.setConsultText(data.getConsultText());
        valueObject.setRxConsultType(RxConsultType.valueOf(data.getWarningLabelType()));
        valueObject.setRejectionReasonText(data.getRejectReasonText());
        valueObject.setRevisionNumber(data.getRevisionNumber());

        if (data.getItemStatus() != null) {
            valueObject.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        }

        if (data.getRequestRejectReason() != null) {
            valueObject.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        if (data.getRequestStatus() != null) {
            valueObject.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        }

        valueObject.setInactivationDate(data.getInactivationDate());
        
        return valueObject;
    }

    /**
     * Minimally copies data from the given EplWarnLabelDo to an RxConsultVo
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    protected RxConsultVo toMinimalValueObject(EplWarnLabelDo data) {
        RxConsultVo value = new RxConsultVo();
        value.setId(String.valueOf(data.getEplId()));
        value.setValue(data.getName());
        value.setRxConsultType(RxConsultType.valueOf(data.getWarningLabelType()));
        value.setConsultText(data.getConsultText());

        return value;
    }
}
