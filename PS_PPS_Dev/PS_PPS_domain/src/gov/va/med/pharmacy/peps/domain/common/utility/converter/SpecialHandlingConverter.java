/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SpecialHandlingVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplProdSpecHandlingAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSpecialHandlingDo;


/**
 * Convert to/from {@link SpecialHandlingVo} and {@link EplProdSpecHandlingAssocDo}.
 */
public class SpecialHandlingConverter extends Converter<SpecialHandlingVo, EplSpecialHandlingDo> {

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
    protected EplSpecialHandlingDo toDataObject(SpecialHandlingVo data) {
        EplSpecialHandlingDo specialHandling = new EplSpecialHandlingDo();
        specialHandling.setEplId(Long.valueOf(data.getId()));
        specialHandling.setSpecialHandlingCode(data.getCode());

        specialHandling.setRevisionNumber(data.getRevisionNumber());
        specialHandling.setSpecialHandlingDescription(data.getDescription());
        specialHandling.setCreatedBy(data.getCreatedBy());
        specialHandling.setCreatedDtm(data.getCreatedDate());
        specialHandling.setLastModifiedBy(data.getModifiedBy());
        specialHandling.setLastModifiedDtm(data.getModifiedDate());

        if (data.getItemStatus() != null) {
            specialHandling.setItemStatus(data.getItemStatus().name());
        }

        if (data.getInactivationDate() != null) {
            specialHandling.setInactivationDate(data.getInactivationDate());
        }

        return specialHandling;
    }

    /**
     * Fully copies data from the givenEplSpecialHandlingDo into a SpecialHandlingVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data EplSpecialHandlingDo to convert
     * @return fully populated SpecialHandlingVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected SpecialHandlingVo toValueObject(EplSpecialHandlingDo data) {
        SpecialHandlingVo spHandling = new SpecialHandlingVo();
        spHandling.setId(data.getEplId().toString());

        spHandling.setCode(data.getSpecialHandlingCode());
        spHandling.setDescription(data.getSpecialHandlingDescription());
        spHandling.setRequestItemStatus(RequestItemStatus.APPROVED);
        spHandling.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        spHandling.setRevisionNumber(data.getRevisionNumber());
        spHandling.setCreatedBy(data.getCreatedBy());
        spHandling.setCreatedDate(data.getCreatedDtm());
        spHandling.setModifiedBy(data.getLastModifiedBy());
        spHandling.setModifiedDate(data.getLastModifiedDtm());

        if (data.getInactivationDate() != null) {
            spHandling.setInactivationDate(data.getInactivationDate());
        }

        return spHandling;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a {@link ValueObject}.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Sets the ID and the Code.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    protected SpecialHandlingVo toMinimalValueObject(EplSpecialHandlingDo data) {
        SpecialHandlingVo spHandling = new SpecialHandlingVo();
        spHandling.setId(data.getEplId().toString());
        spHandling.setCode(data.getSpecialHandlingCode());
        spHandling.setDescription(data.getSpecialHandlingDescription());

        return spHandling;
    }
}
