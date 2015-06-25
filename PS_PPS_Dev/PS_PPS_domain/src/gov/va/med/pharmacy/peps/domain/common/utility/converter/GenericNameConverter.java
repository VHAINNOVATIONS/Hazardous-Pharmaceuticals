/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaGenNameDo;


/**
 * Convert to/from {@link GenericNameVo} and {@link EplGenericNameDo}.
 */
public class GenericNameConverter extends
        Converter<GenericNameVo, EplVaGenNameDo> {

    /**
     * Fully copies data from the given GenericNameVo into a
     * {@link DataObject}.
     * 
     * @param data GenericNameVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplVaGenNameDo toDataObject(GenericNameVo data) {
        EplVaGenNameDo genericNameDo = new EplVaGenNameDo();

        genericNameDo.setGenericName(data.getValue());
        genericNameDo.setEplId(new Long(data.getId()));

        if (StringUtils.isNotEmpty(data.getGenericIen())) {
            genericNameDo.setNdfGenericIen(new Long(data.getGenericIen()));
        }

        genericNameDo.setVuid(data.getVuid());

        if (data.getMasterEntryForVuid()) {
            genericNameDo.setMasterEntryForVuid("1");
        } else {
            genericNameDo.setMasterEntryForVuid("0");
        }

        genericNameDo.setCreatedBy(data.getCreatedBy());
        genericNameDo.setCreatedDtm(data.getCreatedDate());
        genericNameDo.setLastModifiedBy(data.getModifiedBy());
        genericNameDo.setLastModifiedDtm(data.getModifiedDate());

        if (data.getItemStatus() != null) {
            genericNameDo.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            genericNameDo.setRequestStatus(data.getRequestItemStatus()
                    .toString());
        }

        genericNameDo.setRejectReasonText(data.getRejectionReasonText());
        genericNameDo.setRevisionNumber(data.getRevisionNumber());

        if (data.getRequestRejectionReason() != null) {
            genericNameDo.setRequestRejectReason(data
                    .getRequestRejectionReason().toString());
        }

        if (data.getInactivationDate() != null) {
            genericNameDo.setInactivationDate(data.getInactivationDate());
        }

        return genericNameDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * GenericNameVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated GenericNameVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected GenericNameVo toValueObject(EplVaGenNameDo data) {

        GenericNameVo genericName = new GenericNameVo();
        genericName.setId(data.getEplId().toString());
        genericName.setVuid(data.getVuid());

        if (data.getNdfGenericIen() != null) {
            genericName.setGenericIen(String.valueOf(data.getNdfGenericIen()));
        }

        if (data.getMasterEntryForVuid() == null) {
            genericName.setMasterEntryForVuid(false);
        } else {
            if (data.getMasterEntryForVuid().equals("1")) {
                genericName.setMasterEntryForVuid(true);
            } else {
                genericName.setMasterEntryForVuid(false);
            }
        }

        genericName.setValue(data.getGenericName());
        genericName.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        genericName.setRequestItemStatus(RequestItemStatus.valueOf(data
                .getRequestStatus()));
        genericName.setRejectionReasonText(data.getRejectReasonText());
        genericName.setCreatedBy(data.getCreatedBy());
        genericName.setCreatedDate(data.getCreatedDtm());
        genericName.setModifiedBy(data.getLastModifiedBy());
        genericName.setModifiedDate(data.getLastModifiedDtm());

        if (data.getRequestRejectReason() != null) {
            genericName.setRequestRejectionReason(RequestRejectionReason
                    .valueOf(data.getRequestRejectReason()));
        }

        genericName.setRevisionNumber(data.getRevisionNumber().longValue());

        if (data.getInactivationDate() != null) {
            genericName.setInactivationDate(data.getInactivationDate());
        }

        return genericName;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * GenericNameVo.
     * <p>
     * The returned GenericNameVo likely only has enough data for the
     * {@linkGenericNameVo#toShortString()} and {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the
     * {@link ValueObject} in a drop-down or multi-select list where a simple
     * text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated GenericNameVo
     */
    @Override
    protected GenericNameVo toMinimalValueObject(EplVaGenNameDo data) {
        GenericNameVo genericName = new GenericNameVo();

        genericName.setId(String.valueOf(data.getEplId()));
        genericName.setValue(data.getGenericName());

        if (data.getNdfGenericIen() != null) {
            genericName.setGenericIen(String.valueOf(data.getNdfGenericIen()));
        }

        return genericName;
    }
}
