/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationSelectionVo;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.WardSelectionVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplHospitalLocationDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplWardDo;


/**
 * Convert to/from {@link AdministrationScheduleVo} and {@link EplAdminScheduleDo}
 * 
 * 
 */
public class AdminScheduleConverter extends Converter<AdministrationScheduleVo, EplAdminScheduleDo> {

    private AdminScheduleTypeConverter adminScheduleTypeConverter;

    /**
     * * Converts do to vo
     * 
     * @param data EplAdminScheduleDo
     * @return AdminScheduleVo
     */
    private HospitalLocationVo toHospitalLocation(EplHospitalLocationDo data) {
        HospitalLocationVo type = new HospitalLocationVo();

        HospitalLocationSelectionVo locSel = new HospitalLocationSelectionVo();
        locSel.setValue(data.getHospitalLocation());
        type.setHospitalLocationSelection(locSel);

        type.setAdministrationTimes(data.getAdministrationTimes());
        type.setShifts(data.getShifts());
        type.setId(data.getId());

        return type;

    }

    /**
     * * Converts do to vo
     * 
     * @param data EplWardDo
     * @return WardMultipleVo
     */
    private WardMultipleVo toWard(EplWardDo data) {
        WardMultipleVo type = new WardMultipleVo();

        WardSelectionVo wardSel = new WardSelectionVo();
        wardSel.setValue(data.getWard());

        type.setWardSelection(wardSel);
        type.setWardAdminTimes(data.getWardAdminTimes());

        return type;

    }

    /**
     * 
     * @param data AdministrationScheduleTypeVo
     * @param eplAdminSchedule AdminScheduleVo
     * @return EplVaAdminScheduleDo
     */
    private Set<EplHospitalLocationDo> toHospitalLocations(Collection<HospitalLocationVo> data,
                                                                  EplAdminScheduleDo eplAdminSchedule) {
        Set<EplHospitalLocationDo> sets = new HashSet<EplHospitalLocationDo>();

        for (HospitalLocationVo locs : data) {
            EplHospitalLocationDo type = new EplHospitalLocationDo();

            type.setAdministrationTimes(locs.getAdministrationTimes());
            type.setEplAdminSchedule(eplAdminSchedule);
            type.setHospitalLocation(locs.getHospitalLocationSelection().getValue());
            type.setShifts(locs.getShifts());
            sets.add(type);
        }

        return sets;
    }

    /**
     * 
     * @param data AdministrationScheduleTypeVo
     * @param eplAdminSchedule EplAdminScheduleDo
     * @return EplVaAdminScheduleDo
     */
    private Set<EplWardDo> toWards(Collection<WardMultipleVo> data, EplAdminScheduleDo eplAdminSchedule) {
        Set<EplWardDo> sets = new HashSet<EplWardDo>();

        for (WardMultipleVo locs : data) {
            EplWardDo type = new EplWardDo();
            type.setWard(locs.getWardSelection().getValue());
            type.setWardAdminTimes(locs.getWardAdminTimes());
            type.setEplAdminSchedule(eplAdminSchedule);
            sets.add(type);
        }

        return sets;
    }

    /**
     * Fully copies data from the given AdministrationScheduleVo into a EplAdminScheduleDo.
     * 
     * @param data AdministrationScheduleVo to convert
     * @return fully populated EplAdminScheduleDo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplAdminScheduleDo toDataObject(AdministrationScheduleVo data) {
        EplAdminScheduleDo unit = new EplAdminScheduleDo();

        if (data.getAdminScheduleType() != null) {
            unit.setEplScheduleType(adminScheduleTypeConverter.convertMinimal((data.getAdminScheduleType())));
        }

        unit.setFrequencyInMinutes(data.getFrequency());

        unit.setOtherLanguageExpansion(data.getOtherLanguageExpansion());
        unit.setPackagePrefix(data.getPackagePrefix());
        unit.setRevisionNumber(data.getRevisionNumber());
        unit.setScheduleName(data.getValue());
        unit.setScheduleOutpatientExpansion(data.getScheduleOutpatientExpansion());
        unit.setStandardAdministrationTimes(data.getStandardAdministrationTimes());
        unit.setStandardShifts(data.getStandardShifts());
        unit.setCreatedBy(data.getCreatedBy());
        unit.setCreatedDtm(data.getCreatedDate());
        unit.setLastModifiedBy(data.getModifiedBy());
        unit.setLastModifiedDtm(data.getModifiedDate());

        unit.setEplId(new Long(data.getId()));

        if (data.getItemStatus() != null) {
            unit.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            unit.setRequestStatus(data.getRequestItemStatus().toString());
        }

        unit.setRejectReasonText(data.getRejectionReasonText());

        if (data.getInactivationDate() != null) {
            unit.setInactivationDate(data.getInactivationDate());
        }

        if (data.getHospitalLocationMultiple() != null && data.getHospitalLocationMultiple().size() > 0) {
            unit.setEplHospitalLocations(toHospitalLocations(data.getHospitalLocationMultiple(), unit));
        }

        if (data.getWardMultiple() != null && data.getWardMultiple().size() > 0) {
            unit.setEplWards(toWards(data.getWardMultiple(), unit));
        }

        if (data.getRequestRejectionReason() != null) {
            unit.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        return unit;

    }

    /**
     * Fully copies data from the given {@link DataObject} into a {@link ValueObject}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link ValueObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected AdministrationScheduleVo toValueObject(EplAdminScheduleDo data) {
        AdministrationScheduleVo adminSchedule = new AdministrationScheduleVo();

        adminSchedule.setId(data.getEplId().toString());
        adminSchedule.setValue(data.getScheduleName());
        adminSchedule.setFrequency(data.getFrequencyInMinutes());
        adminSchedule.setOtherLanguageExpansion(data.getOtherLanguageExpansion());
        adminSchedule.setPackagePrefix(data.getPackagePrefix());
        adminSchedule.setRevisionNumber(data.getRevisionNumber());
        adminSchedule.setScheduleOutpatientExpansion(data.getScheduleOutpatientExpansion());
        adminSchedule.setStandardAdministrationTimes(data.getStandardAdministrationTimes());
        adminSchedule.setStandardShifts(data.getStandardShifts());
        adminSchedule.setCreatedBy(data.getCreatedBy());
        adminSchedule.setCreatedDate(data.getCreatedDtm());
        adminSchedule.setModifiedBy(data.getLastModifiedBy());
        adminSchedule.setModifiedDate(data.getLastModifiedDtm());

        if (data.getEplScheduleType() != null) {
            adminSchedule.setAdminScheduleType(adminScheduleTypeConverter.convert(data.getEplScheduleType()));
        }

        adminSchedule.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        
        if (data.getInactivationDate() != null) {
            adminSchedule.setInactivationDate(data.getInactivationDate());
        }

        if (data.getEplHospitalLocations() != null) {
            Collection<HospitalLocationVo> hosps = new ArrayList<HospitalLocationVo>();

            for (EplHospitalLocationDo loc : data.getEplHospitalLocations()) {
                hosps.add(toHospitalLocation(loc));
            }

            adminSchedule.setHospitalLocationMultiple(hosps);
        }

        if (data.getEplWards() != null) {
            Collection<WardMultipleVo> hosps = new ArrayList<WardMultipleVo>();

            for (EplWardDo loc : data.getEplWards()) {
                hosps.add(toWard(loc));
            }

            adminSchedule.setWardMultiple(hosps);
        }

        
        adminSchedule.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        adminSchedule.setRejectionReasonText(data.getRejectReasonText());

        if (data.getRequestRejectReason() != null) {
            adminSchedule.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));

        }

        return adminSchedule;
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
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated {@link ValueObject}
     */
    @Override
    protected AdministrationScheduleVo toMinimalValueObject(EplAdminScheduleDo data) {
        AdministrationScheduleVo adminSchedule = new AdministrationScheduleVo();

        adminSchedule.setId(data.getEplId().toString());
        adminSchedule.setValue(data.getScheduleName());

        return adminSchedule;
    }

    /**
     * setAdminScheduleTypeConverter
     * 
     * @param adminScheduleTypeConverter adminScheduleTypeConverter property
     */
    public void setAdminScheduleTypeConverter(AdminScheduleTypeConverter adminScheduleTypeConverter) {
        this.adminScheduleTypeConverter = adminScheduleTypeConverter;
    }

  

}
