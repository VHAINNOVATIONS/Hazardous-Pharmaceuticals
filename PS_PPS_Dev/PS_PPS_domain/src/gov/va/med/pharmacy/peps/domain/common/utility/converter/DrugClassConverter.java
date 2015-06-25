/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDrugClassDo;


/**
 * Convert to/from {@link DrugClassVo} and {@link EplVaDrugClassDo}.
 */
public class DrugClassConverter extends Converter<DrugClassVo, EplVaDrugClassDo> {

    private static final String DASH = " - ";
    private DrugClassificationTypeConverter drugClassificationTypeConverter;

    /**
     * setDrugClassificationTypeConverter
     * @param drugClassificationTypeConverter drugClassificationTypeConverter property
     */
    public void setDrugClassificationTypeConverter(DrugClassificationTypeConverter drugClassificationTypeConverter) {
        this.drugClassificationTypeConverter = drugClassificationTypeConverter;
    }

    /**
     * Fully copies data from the given EplVaDrugClassDo into a DrugClassVo.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#
     *     toDataObject(gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplVaDrugClassDo toDataObject(DrugClassVo data) {
        EplVaDrugClassDo drugClass = new EplVaDrugClassDo();
        drugClass.setEplId(new Long(data.getId()));

        if (StringUtils.isNotEmpty(data.getDrugClassIen())) {
            drugClass.setNdfClassIen(new Long(data.getDrugClassIen()));
        }

        drugClass.setDescription(data.getDescription());
        drugClass.setVuid(data.getVuid());

        if (data.isMasterEntryForVuid()) {
            drugClass.setMasterEntryForVuid("1");
        } else {
            drugClass.setMasterEntryForVuid("0");
        }

        drugClass.setClassificationName(data.getClassification());
        drugClass.setCode(data.getCode());
        drugClass.setRevisionNumber(new Long(data.getRevisionNumber()));
        drugClass.setCreatedDtm(data.getCreatedDate());
        drugClass.setCreatedBy(data.getCreatedBy());
        drugClass.setLastModifiedDtm(data.getModifiedDate());
        drugClass.setLastModifiedBy(data.getModifiedBy());

        if (data.getParentDrugClass() != null) {
            EplVaDrugClassDo parent = new EplVaDrugClassDo();
            parent.setEplId(new Long(data.getParentDrugClass().getId()));
            parent.setCode(data.getParentDrugClass().getCode());
            parent.setClassificationName(data.getParentDrugClass().getClassification());
            parent.setVuid(data.getParentDrugClass().getVuid());

            if (data.getParentDrugClass().getDrugClassIen() != null) {
                parent.setNdfClassIen(new Long(data.getParentDrugClass().getDrugClassIen()));
            }

            drugClass.setEplVaDrugClass(parent);
        }

        drugClass.setEplClassType(drugClassificationTypeConverter.convert(data.getClassificationType()));

        if (data.getInactivationDate() != null) {
            drugClass.setInactivationDate((data.getInactivationDate()));
        }

        if (data.getItemStatus() != null) {
            drugClass.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            drugClass.setRequestStatus(data.getRequestItemStatus().toString());
        }

        drugClass.setRejectReasonText(data.getRejectionReasonText());

        return drugClass;
    }

    /**
     * Uses toValueObject
     *  
     * @param data EplVaDrugClassDo to convert
     * @return minimally populated DrugClassVo
     */
    @Override
    protected DrugClassVo toMinimalValueObject(EplVaDrugClassDo data) {
        
        return toValueObject(data);
    }

    /**
     * Fully copies data from the given EplVaDrugClassDo into a DrugClassVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data EplVaDrugClassDo to convert
     * @return fully populated DrugClassVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#
     *     toValueObject(gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DrugClassVo toValueObject(EplVaDrugClassDo data) {
        DrugClassVo drugClass = new DrugClassVo();
        drugClass.setId(String.valueOf(data.getEplId()));

        if (data.getNdfClassIen() != null) {
            drugClass.setDrugClassIen(String.valueOf(data.getNdfClassIen()));
        }

        drugClass.setVuid(data.getVuid());

        if ("1".equals(data.getMasterEntryForVuid())) {
            drugClass.setMasterEntryForVuid(true);
        } else if ("0".equals(data.getMasterEntryForVuid())) {
            drugClass.setMasterEntryForVuid(false);
        }

        drugClass.setDescription(data.getDescription());
        drugClass.setClassification(data.getClassificationName());
        drugClass.setCode(data.getCode());
        drugClass.setCreatedBy(data.getCreatedBy());
        drugClass.setCreatedDate(data.getCreatedDtm());
        drugClass.setModifiedBy(data.getLastModifiedBy());
        drugClass.setModifiedDate(data.getLastModifiedDtm());

        drugClass.setClassificationType(drugClassificationTypeConverter.convert(data.getEplClassType()));

        if (data.getEplVaDrugClass() != null) {
            DrugClassVo parent = new DrugClassVo();
            parent.setId(data.getEplVaDrugClass().getEplId().toString());
            parent.setDescription(data.getEplVaDrugClass().getDescription());
            parent.setCode(data.getEplVaDrugClass().getCode());
            parent.setClassification(data.getEplVaDrugClass().getClassificationName());
            parent.setValue(data.getEplVaDrugClass().getCode() + DASH
                            + data.getEplVaDrugClass().getClassificationName());
            parent.setClassificationType(drugClassificationTypeConverter.convert(data.getEplVaDrugClass().getEplClassType()));
            
            if (data.getEplVaDrugClass().getNdfClassIen() != null) {
                parent.setDrugClassIen(data.getEplVaDrugClass().getNdfClassIen().toString());
            }
            
            drugClass.setParentDrugClass(parent);
        }

        if (data.getInactivationDate() != null) {
            drugClass.setInactivationDate(data.getInactivationDate());
        }

        drugClass.setDescription(data.getDescription());
        drugClass.setRevisionNumber(data.getRevisionNumber().longValue());
        drugClass.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        drugClass.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        drugClass.setRejectionReasonText(data.getRejectReasonText());

        // Value can be changed, but by default it is code - classification
        drugClass.setValue(data.getCode() + DASH + data.getClassificationName());

        return drugClass;
    }
}
