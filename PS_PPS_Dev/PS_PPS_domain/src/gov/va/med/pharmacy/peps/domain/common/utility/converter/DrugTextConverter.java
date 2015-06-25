/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.DrugTextSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextType;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDtSynonymDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDtSynonymDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextNAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdDrugTextNAssocDoKey;


/**
 * Convert to/from {@link DrugTextVo} and {@link EplDrugTextDo}.
 */
public class DrugTextConverter extends Converter<DrugTextVo, EplDrugTextDo> {

    /**
     * Fully copies data from the given {@link DataObject} into a DrugTextVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated DrugTextVo are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated DrugTextVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DrugTextVo toValueObject(EplDrugTextDo data) {
        DrugTextVo valueObject = new DrugTextVo();

        valueObject.setId(data.getEplId().toString());
        valueObject.setValue(data.getDrugTextName().toString());
        valueObject.setTextLocal(data.getDrugTextLocal());
        valueObject.setTextNational(data.getDrugTextNational());
        valueObject.setCreatedBy(data.getCreatedBy());
        valueObject.setCreatedDate(data.getCreatedDtm());
        valueObject.setModifiedBy(data.getLastModifiedBy());
        valueObject.setModifiedDate(data.getLastModifiedDtm());

        if (data.getEplDtSynonyms() != null && data.getEplDtSynonyms().size() > 0) {

            Collection<DrugTextSynonymVo> syns = new ArrayList<DrugTextSynonymVo>();

            for (EplDtSynonymDo syn : data.getEplDtSynonyms()) {
                DrugTextSynonymVo synVo = new DrugTextSynonymVo();
                synVo.setDrugTextSynonymName(syn.getKey().getDrugTextSynonym());
                syns.add(synVo);
            }

            valueObject.setDrugTextSynonyms(syns);
        }

        if (data.getDrugTextType() != null) {
            valueObject.setDrugTextType(DrugTextType.valueOf(data.getDrugTextType()));
        }

        if (data.getRequestStatus() != null) {
            valueObject.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        }

        if (data.getItemStatus() != null) {
            valueObject.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        }

        valueObject.setRejectionReasonText(data.getRejectReasonText());

        if (data.getRequestRejectReason() != null) {
            valueObject.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        valueObject.setRevisionNumber(data.getRevisionNumber());

        if (data.getInactivationDate() != null) {
            valueObject.setInactivationDate(data.getInactivationDate());
        }
        
        return valueObject;
    }

    /**
     * EplProdDrugTextNAssocDo Converts RxConsultVo to EplProdDrugTextLAssocDo by sequence
     * 
     * @param data that has to be converted
     * @param productId String
     * @param sequence int
     * @return EplProdDrugTextNAssocDo converted to
     */
    private EplProdDrugTextNAssocDo convertNationalAssociation(RxConsultVo data, String productId, int sequence) {
        EplProdDrugTextNAssocDo drugText = new EplProdDrugTextNAssocDo();
        EplProdDrugTextNAssocDoKey key = new EplProdDrugTextNAssocDoKey();

        key.setEplIdProdFk(new Long(productId));

        key.setDrugTextIdFk(new Long(data.getId()));

        drugText.setKey(key);

        EplDrugTextDo warnDo = new EplDrugTextDo();
        warnDo.setEplId(Long.valueOf(data.getId()));
        drugText.setEplDrugText(warnDo);

        return drugText;
    }

    /**
     * Converts RxConsultVo to EplProdDrugTextNAssocDo in sequence
     * 
     * @param data that has to be converted
     * @param productId String
     * @return EplProdDrugTextLAssocDo converted to
     */
    public Set<EplProdDrugTextNAssocDo> convertNationalAssociation(List<RxConsultVo> data, String productId) {
        Set<EplProdDrugTextNAssocDo> values = new HashSet<EplProdDrugTextNAssocDo>();

        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                RxConsultVo current = data.get(i);
                EplProdDrugTextNAssocDo value = convertNationalAssociation(current, productId, i);

                if (value != null) {
                    values.add(value);
                }
            }
        }

        return values;
    }

    /**
     * Fully copies data from the given DrugTextVo into a {@link DataObject}.
     * 
     * @param data DrugTextVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplDrugTextDo toDataObject(DrugTextVo data) {
        EplDrugTextDo dataObject = new EplDrugTextDo();
        dataObject.setEplId(new Long(data.getId()));

        dataObject.setInactivationDate(data.getInactivationDate());
        dataObject.setDrugTextName(data.getValue());
        dataObject.setDrugTextLocal(data.getTextLocal());
        dataObject.setDrugTextNational(data.getTextNational());
        dataObject.setCreatedBy(data.getCreatedBy());
        dataObject.setCreatedDtm(data.getCreatedDate());
        dataObject.setLastModifiedBy(data.getModifiedBy());
        dataObject.setLastModifiedDtm(data.getModifiedDate());

        if (data.getDrugTextSynonyms() != null && data.getDrugTextSynonyms().size() > 0) {
            Set<EplDtSynonymDo> eplDtSynonyms = new HashSet<EplDtSynonymDo>();

            for (DrugTextSynonymVo syn : data.getDrugTextSynonyms()) {
                EplDtSynonymDoKey key = new EplDtSynonymDoKey();
                key.setEplIdDtFk(new Long(data.getId()));
                key.setDrugTextSynonym(syn.getDrugTextSynonymName());

                EplDtSynonymDo synDo = new EplDtSynonymDo();
                synDo.setKey(key);

                eplDtSynonyms.add(synDo);
            }

            dataObject.setEplDtSynonyms(eplDtSynonyms);
        }

        if (data.getRequestItemStatus() != null) {
            dataObject.setRequestStatus(data.getRequestItemStatus().toString());
        }

        if (data.getRequestRejectionReason() != null) {
            dataObject.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        if (data.getItemStatus() != null) {
            dataObject.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getDrugTextType() != null) {
            dataObject.setDrugTextType(data.getDrugTextType().toString());
        }

        dataObject.setRejectReasonText(data.getRejectionReasonText());
        dataObject.setRevisionNumber(data.getRevisionNumber());

        return dataObject;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a DrugTextVo.
     * <p>
     * The returned DrugTextVo likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls DrugTextVo.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated DrugTextVo
     */
    protected DrugTextVo toMinimalValueObject(EplDrugTextDo data) {
        DrugTextVo dataObject = new DrugTextVo();
        dataObject.setId(data.getEplId().toString());
        dataObject.setValue(data.getDrugTextName());

        return dataObject;
    }
}
