/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleIntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.WardSelectionVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedInstructWardDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedicationInstructionDo;


/**
 * Convert to/from {@link MedicationInstructionVo} and {@link EplMedicationInstructionDo}.
 */
public class MedicationInstructionConverter extends Converter<MedicationInstructionVo, EplMedicationInstructionDo> {

    private LocalMedicationRouteConverter localMedicationRouteConverter;

    /**
     * Converts Collection of PossibleIntendedUseVo to String
     * 
     * @param packagesCode PossibleIntendedUseVo
     * @return String
     */
    private static String getIntendedUse(Collection<PossibleIntendedUseVo> packagesCode) {
        String packageName = null;

        if (packagesCode.size() == 1) {
            for (PossibleIntendedUseVo possibleDosagesPackage : packagesCode) {
                if (possibleDosagesPackage != null) {
                    if (I_INPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())
                        || INPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        packageName = INPATIENT;
                    } else if (O_OUTPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())
                        || OUTPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        packageName = OUTPATIENT;
                    }
                }
            }// end for
        } else if (packagesCode.size() == 2) {
            packageName = BOTH;
        }

        return packageName;
    }

    /**
     * Converts string package name to PossibleIntendedUseVo collection
     * 
     * @param packageName String
     * @return List<PossibleIntendedUseVo>
     */
    private static List<PossibleIntendedUseVo> getIntendedUse(String packageName) {

        PossibleIntendedUseVo possibleDosagesPackage = new PossibleIntendedUseVo();
        List<PossibleIntendedUseVo> lstPossibleDosages = new ArrayList<PossibleIntendedUseVo>();

        if (BOTH.equalsIgnoreCase(packageName)) {
            possibleDosagesPackage.setValue(INPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);

            possibleDosagesPackage = new PossibleIntendedUseVo();
            possibleDosagesPackage.setValue(OUTPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        } else if ((INPATIENT.equalsIgnoreCase(packageName)) || (I_INPATIENT.equalsIgnoreCase(packageName))) {
            possibleDosagesPackage.setValue(INPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        } else if ((OUTPATIENT.equalsIgnoreCase(packageName)) || (O_OUTPATIENT.equalsIgnoreCase(packageName))) {
            possibleDosagesPackage.setValue(OUTPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        }

        return (lstPossibleDosages);

    }

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data MedicationInstructionVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplMedicationInstructionDo toDataObject(MedicationInstructionVo data) {
        EplMedicationInstructionDo dataObject = new EplMedicationInstructionDo();
        dataObject.setEplId(new Long(data.getId()));

        dataObject.setAdditionalInstruction(data.getAdditionalInstruction());
        dataObject.setDefaultAdminTimes(data.getDefaultAdminTimes());

        dataObject.setCreatedBy(data.getCreatedBy());
        dataObject.setCreatedDtm(data.getCreatedDate());
        dataObject.setLastModifiedBy(data.getModifiedBy());
        dataObject.setLastModifiedDtm(data.getModifiedDate());

        if (data.getFrequency() != null) {
            dataObject.setFrequencyInMinutes(data.getFrequency());

        }

        dataObject.setRevisionNumber(data.getRevisionNumber());
        dataObject.setInstructions(data.getInstructions());

        if (data.getIntendedUse() != null && data.getIntendedUse().size() > 0) {
            dataObject.setIntendedUse(getIntendedUse(data.getIntendedUse()));
        }

        dataObject.setMedInstructionExpansion(data.getMedInstructionExpansion());
        dataObject.setMedInstructionName(data.getValue());
        dataObject.setMedInstructionOtherLangExp(data.getOtherLanguageExpansion());
        dataObject.setMedInstructionSchedule(data.getMedInstructionSchedule());
        dataObject.setMedInstructionSynonym(data.getMedInstructionSynonym());
        dataObject.setPlural(data.getPlural());

        if (data.getLocalMedicationRoute() != null) {
            dataObject.setEplLocalMedRoute(localMedicationRouteConverter.convertMinimal(data.getLocalMedicationRoute()));
        }

        if (data.getItemStatus() != null) {
            dataObject.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            dataObject.setRequestStatus(data.getRequestItemStatus().toString());
        }

        dataObject.setRejectReasonText(data.getRejectionReasonText());

        if (data.getRequestRejectionReason() != null) {
            dataObject.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        if (data.getInactivationDate() != null) {
            dataObject.setInactivationDate(data.getInactivationDate());
        }

        if (data.getMedInstructionWardMultiple() != null && data.getMedInstructionWardMultiple().size() > 0) {

            Set<EplMedInstructWardDo> eplMedInstructWards = new HashSet<EplMedInstructWardDo>();

            for (WardMultipleVo wards : data.getMedInstructionWardMultiple()) {
                EplMedInstructWardDo dWard = new EplMedInstructWardDo();
                dWard.setEplMedicationInstruction(dataObject);
                dWard.setWard(wards.getWardSelection().getValue());
                dWard.setWardDefaultAdminTimes(wards.getWardAdminTimes());
                eplMedInstructWards.add(dWard);
            }

            dataObject.setEplMedInstructWards(eplMedInstructWards);

        }

        return dataObject;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a {@link ValueObject}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated MedicationInstructionVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected MedicationInstructionVo toValueObject(EplMedicationInstructionDo data) {
        MedicationInstructionVo dataObject = new MedicationInstructionVo();
        dataObject.setId(data.getEplId().toString());

        dataObject.setAdditionalInstruction(data.getAdditionalInstruction());
        dataObject.setDefaultAdminTimes(data.getDefaultAdminTimes());
        dataObject.setCreatedBy(data.getCreatedBy());
        dataObject.setCreatedDate(data.getCreatedDtm());
        dataObject.setModifiedBy(data.getLastModifiedBy());
        dataObject.setModifiedDate(data.getLastModifiedDtm());

        if (data.getFrequencyInMinutes() != null) {
            dataObject.setFrequency(data.getFrequencyInMinutes());
        }

        dataObject.setRevisionNumber(data.getRevisionNumber());
        dataObject.setInstructions(data.getInstructions());

        if (data.getIntendedUse() != null) {
            dataObject.setIntendedUse(getIntendedUse(data.getIntendedUse()));
        }

        dataObject.setMedInstructionExpansion(data.getMedInstructionExpansion());
        dataObject.setValue(data.getMedInstructionName());
        dataObject.setOtherLanguageExpansion(data.getMedInstructionOtherLangExp());
        dataObject.setMedInstructionSchedule(data.getMedInstructionSchedule());
        dataObject.setMedInstructionSynonym(data.getMedInstructionSynonym());
        dataObject.setPlural(data.getPlural());

        if (data.getEplMedInstructWards() != null && data.getEplMedInstructWards().size() > 0) {

            for (EplMedInstructWardDo ward : data.getEplMedInstructWards()) {

                WardSelectionVo selection = new WardSelectionVo();
                selection.setValue(ward.getWard());

                WardMultipleVo dWard = new WardMultipleVo();
                dWard.setWardSelection(selection);
                dWard.setWardAdminTimes(ward.getWardDefaultAdminTimes());
                dataObject.getMedInstructionWardMultiple().add(dWard);
            }

        }

        dataObject.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        dataObject.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));

        if (data.getRequestRejectReason() != null) {
            dataObject.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        dataObject.setRejectionReasonText(data.getRejectReasonText());

        if (data.getInactivationDate() != null) {
            dataObject.setInactivationDate(data.getInactivationDate());
        }

        if (data.getEplLocalMedRoute() != null) {
            dataObject.setLocalMedicationRoute(localMedicationRouteConverter.convertMinimal(data.getEplLocalMedRoute()));
        }

        return dataObject;

    }

    /**
     * Minimally copies data from the given {@link DataObject} into a MedicationInstructionVo.
     * <p>
     * The returned MedicationInstructionVo likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated MedicationInstructionVo
     */
    @Override
    protected MedicationInstructionVo toMinimalValueObject(EplMedicationInstructionDo data) {
        MedicationInstructionVo medicationInstruction = new MedicationInstructionVo();

        medicationInstruction.setId(String.valueOf(data.getEplId()));
        medicationInstruction.setValue(data.getMedInstructionName());

        return medicationInstruction;
    }

    /**
     * setLocalMedicationRouteConverter
     * 
     * @param localMedicationRouteConverter localMedicationRouteConverter property
     */
    public void setLocalMedicationRouteConverter(LocalMedicationRouteConverter localMedicationRouteConverter) {
        this.localMedicationRouteConverter = localMedicationRouteConverter;
    }
}
