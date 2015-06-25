/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.DoseUnitSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitSynonymDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitSynonymDoKey;


/**
 * Convert to/from {@link DoseUnitVo} and {@link EplDoseUnitDo}.
 */
public class DoseUnitConverter extends Converter<DoseUnitVo, EplDoseUnitDo> {

    /**
     * Converts EplDoseUnitSynonymDo to DoseUnitSynonymVo
     * 
     * @param data EplDoseUnitSynonymDo
     * @return  DoseUnitSynonymVo
     */
    private static DoseUnitSynonymVo toDoseUnitSynonym(EplDoseUnitSynonymDo data) {
        DoseUnitSynonymVo synVo = new DoseUnitSynonymVo();

        synVo.setDoseUnitSynonymName(data.getKey().getdoseUnitSynonymId());
        synVo.setDoseUnitId(data.getKey().getDoseUnitEplIdFk().toString());

        return synVo;
    }

    /**
     * Fully copies data from the given EplDoseUnitDo into a DoseUnitVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data EplDoseUnitDo to convert
     * @return fully populated DoseUnitVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DoseUnitVo toValueObject(EplDoseUnitDo data) {
        DoseUnitVo unit = new DoseUnitVo();
        unit.setId(data.getEplId().toString());
        unit.setDoseUnitName(data.getDoseUnitName());
        unit.setFdbDoseUnit(data.getFirstDatabankDoseUnit());
        unit.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        unit.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        unit.setRejectionReasonText(data.getRejectReasonText());
        unit.setInactivationDate(data.getInactivationDate());
        unit.setRevisionNumber(data.getRevisionNumber());
        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDate(data.getCreatedDtm());
        unit.setModifiedBy(data.getLastModifiedBy());
        unit.setModifiedDate(data.getLastModifiedDtm());
        
        if ("Y".equalsIgnoreCase(data.getDoseUnitInd())) {
            unit.setDoseIndicator(true);
        } else {
            unit.setDoseIndicator(false);
        }

        if (data.getRequestRejectReason() != null) {
            unit.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(data.getInactivationDate());
        }

        if (data.getEplDoseUnitSynonyms() != null && data.getEplDoseUnitSynonyms().size() > 0) {
            Collection<DoseUnitSynonymVo> synonyms = new ArrayList<DoseUnitSynonymVo>();

            for (EplDoseUnitSynonymDo synDo : data.getEplDoseUnitSynonyms()) {
                synonyms.add(toDoseUnitSynonym(synDo));
            }

            unit.setDoseUnitSynonyms(synonyms);
        }

        //M5P2 remove reference to deprecated method. Setting the doseUnit name should be able to handle it
        if (data.getEplDoseUnit() != null) {
            DoseUnitVo replacement = new DoseUnitVo();
            replacement.setId(data.getEplDoseUnit().getEplId().toString());
            replacement.setDoseUnitName(data.getEplDoseUnit().getDoseUnitName());

            unit.setReplacementDoseUnit(replacement);
        }

        return unit;
    }

    /**
     * Fully copies data from the given DoseUnitVo into a EplDoseUnitDo.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplDoseUnitDo toDataObject(DoseUnitVo data) {
        EplDoseUnitDo unit = new EplDoseUnitDo();

        unit.setEplId(new Long(data.getId()));
        unit.setDoseUnitName(data.getDoseUnitName());
        unit.setFirstDatabankDoseUnit(data.getFdbDoseUnit());
        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDtm(data.getCreatedDate());
        unit.setLastModifiedBy(data.getModifiedBy());
        unit.setLastModifiedDtm(data.getModifiedDate());

        if (data.getRequestItemStatus() != null) {
            unit.setRequestStatus(data.getRequestItemStatus().toString());
        }

        if (data.getItemStatus() != null) {
            unit.setItemStatus(data.getItemStatus().toString());
        }
        
        if (data.getDoseIndicator() == null) {
            unit.setDoseUnitInd("N");
        } else {
            if (data.getDoseIndicator()) {
                unit.setDoseUnitInd("Y");
            } else {
                unit.setDoseUnitInd("N");
            }
        }


        unit.setRejectReasonText(data.getRejectionReasonText());

        if (data.getRequestRejectionReason() != null) {
            unit.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(data.getInactivationDate());
        }

        unit.setRevisionNumber(data.getRevisionNumber());

        if (data.getDoseUnitSynonyms() != null && data.getDoseUnitSynonyms().size() > 0) {
            Set<EplDoseUnitSynonymDo> eplDoseUnitSynonyms = new HashSet<EplDoseUnitSynonymDo>();

            for (DoseUnitSynonymVo synVo : data.getDoseUnitSynonyms()) {
                EplDoseUnitSynonymDoKey key = new EplDoseUnitSynonymDoKey();
                key.setdoseUnitSynonymId(synVo.getDoseUnitSynonymName());
                key.setDoseUnitEplIdFk(new Long(data.getId()));

                EplDoseUnitSynonymDo synDo = new EplDoseUnitSynonymDo();
                synDo.setKey(key);
                eplDoseUnitSynonyms.add(synDo);
            }

            unit.setEplDoseUnitSynonyms(eplDoseUnitSynonyms);
        }

        if (data.getReplacementDoseUnit() != null) {
            EplDoseUnitDo replacementDoseUnit = new EplDoseUnitDo();
            replacementDoseUnit.setEplId(new Long(data.getReplacementDoseUnit().getId()));
            unit.setEplDoseUnit(replacementDoseUnit);
        }

        return unit;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a DoseUnitVo.
     * <p>
     * The returned DoseUnitVo likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated DoseUnitVo
     */
    protected DoseUnitVo toMinimalValueObject(EplDoseUnitDo data) {
        DoseUnitVo dataObject = new DoseUnitVo();
        dataObject.setId(data.getEplId().toString());
        dataObject.setDoseUnitName(data.getDoseUnitName());
        
 
        return dataObject;
    }
}
