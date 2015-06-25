/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplIngredientDo;


/**
 * Convert to/from {@link IngredientVo} and {@link EplIngredientDo}.
 */
public class IngredientConverter extends
        Converter<IngredientVo, EplIngredientDo> {

    /**
     * Fully copies data from the given IngredientVo into a
     * {@link DataObject}.
     * 
     * @param data IngredientVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplIngredientDo toDataObject(IngredientVo data) {
        EplIngredientDo ingredient = new EplIngredientDo();
        ingredient.setEplId(new Long(data.getId()));
        ingredient.setName(data.getValue());
        ingredient.setVuid(data.getVuid());
        
        if (StringUtils.isNotEmpty(data.getNdfIngredientIen())) {
            ingredient.setNdfIngredientIen(new Long(data.getNdfIngredientIen()));
        }
        
        if (data.getMasterEntryForVuid()) {
            ingredient.setMasterEntryForVuid("1");
        } else {
            ingredient.setMasterEntryForVuid("0");
        }
        
        ingredient.setRevisionNumber(new Long(data.getRevisionNumber()));
        ingredient.setCreatedBy(data.getCreatedBy());
        ingredient.setCreatedDtm(data.getCreatedDate());
        ingredient.setLastModifiedBy(data.getModifiedBy());
        ingredient.setLastModifiedDtm(data.getModifiedDate());

        if (data.getPrimaryIngredient() != null
                && data.getPrimaryIngredient().getId() != null) {
            EplIngredientDo primaryIngredient = new EplIngredientDo();
            primaryIngredient.setEplId(new Long(data.getPrimaryIngredient()
                    .getId()));
            primaryIngredient.setName(data.getPrimaryIngredient().getValue());
            primaryIngredient.setRevisionNumber(new Long(data
                    .getPrimaryIngredient().getRevisionNumber()));
            primaryIngredient.setVuid(data.getPrimaryIngredient().getVuid());
            ingredient.setEplIngredient(primaryIngredient);

        }

        if (data.getItemStatus() != null) {
            ingredient.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            ingredient.setRequestStatus(data.getRequestItemStatus().toString());
        }

        if (data.getRequestRejectionReason() != null) {
            ingredient.setRequestRejectReason(data.getRequestRejectionReason().name());
        }

        ingredient.setRejectReasonText(data.getRejectionReasonText());

        if (data.getInactivationDate() != null) {
            ingredient.setInactivationDate(data.getInactivationDate());
        }

        return ingredient;
    }

    /**
     * IngredientVo toValueObject
     *      
     * @param data {@link DataObject} to convert
     * @return fully populated IngredientVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected IngredientVo toValueObject(EplIngredientDo data) {
        IngredientVo ing = new IngredientVo();
        ing.setId(data.getEplId().toString());
        ing.setValue(data.getName());
        ing.setRevisionNumber(data.getRevisionNumber().longValue());
        ing.setVuid(data.getVuid());
        
        if (data.getNdfIngredientIen() != null) {
            ing.setIngredientIen(String.valueOf(data.getNdfIngredientIen()));
        }

        if ("1".equals(data.getMasterEntryForVuid())) {
            ing.setMasterEntryForVuid(true);
        } else if ("0".equals(data.getMasterEntryForVuid())) {
            ing.setMasterEntryForVuid(false);
        }

        ing.setCreatedBy(data.getCreatedBy());
        ing.setCreatedDate(data.getCreatedDtm());
        ing.setModifiedBy(data.getLastModifiedBy());
        ing.setModifiedDate(data.getLastModifiedDtm());

        if (data.getEplIngredient() != null) {
            IngredientVo primaryVo = new IngredientVo();
            primaryVo.setId(data.getEplIngredient().getEplId().toString());
            primaryVo.setValue(data.getEplIngredient().getName());
            
            if (data.getEplIngredient().getNdfIngredientIen() != null) {
                primaryVo.setIngredientIen(String.valueOf(data.getEplIngredient().getNdfIngredientIen()));
            }

            ing.setPrimaryIngredient(primaryVo);
        }

       
        ing.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        
        if (data.getInactivationDate() != null) {
            ing.setInactivationDate(data.getInactivationDate());
        }

        ing.setRequestItemStatus(RequestItemStatus.valueOf(data
                .getRequestStatus()));

        if (data.getRequestRejectReason() != null) {
            ing.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        ing.setRejectionReasonText(data.getRejectReasonText());

        return ing;
    }

    /**
     * IngredientVo toMinimalValueObject(EplIngredientDo data
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated IngredientVo
     */
    @Override
    protected IngredientVo toMinimalValueObject(EplIngredientDo data) {
        IngredientVo ingredient = new IngredientVo();

        ingredient.setId(String.valueOf(data.getEplId()));
        
        if (data.getNdfIngredientIen() != null) {
            ingredient.setIngredientIen(String.valueOf(data.getNdfIngredientIen()));
        }
        
        ingredient.setValue(data.getName());

        return ingredient;
    }
}
