/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain;


import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.AdministrationScheduleFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.DosageFormFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.DoseUnitsFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.DrugTextFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.MedicationInstructionFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.MedicationRoutesFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.OrderUnitFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.PharmacySystemFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.RxConsultFileConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.StandardMedicationRoutesFileConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.PdmDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.administrationschedule.AdministrationScheduleFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.dosageform.DosageFormFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.doseunits.DoseUnitsFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drugtext.DrugTextFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationinstruction.MedicationInstructionFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.orderunit.OrderUnitFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacysystem.PharmacySystemFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.rxconsult.RxConsultFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.standardmedicationroutes.StandardMedicationRoutesFile;


/**
 * This is the PdmDomainConverter class
 */
public class PdmDomainConverter {

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Constructor
     *
     */
    private PdmDomainConverter() {
        super();
    }

    /**
     * toPdmDomain
     * 
     * @param items List of PDM Domain Items
     * @return PdmDomain
     */
    public static PdmDomain toPdmDomain(DomainItem[] items) {
        PdmDomain pdmDomain = FACTORY.createPdmDomain();

        for (DomainItem item : items) {
            if ((item.getItem() instanceof DosageFormVo)
                && (DosageFormFileConverter.hasNewOrModifiedFields(DosageFormFileConverter.FIELDS, item.getDifference(),
                    item.getAction()))) {

                DosageFormFile file = DosageFormFileConverter.toDosageFormFile((DosageFormVo) item.getItem(), item
                    .getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getDosageFormFile().add(file);

            } else if ((item.getItem() instanceof AdministrationScheduleVo)
                && (AdministrationScheduleFileConverter.hasNewOrModifiedFields(AdministrationScheduleFileConverter.FIELDS,
                    item.getDifference(), item.getAction()))) {

                AdministrationScheduleFile file = AdministrationScheduleFileConverter.toAdministrationFile(
                    (AdministrationScheduleVo) item.getItem(), item.getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getAdministrationScheduleFile().add(file);

            } else if ((item.getItem() instanceof DrugTextVo)
                && (DrugTextFileConverter.hasNewOrModifiedFields(DrugTextFileConverter.FIELDS, item.getDifference(), item
                    .getAction()))) {

                DrugTextFile file = DrugTextFileConverter.toDrugTextFile((DrugTextVo) item.getItem(), item.getDifference(),
                    item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getDrugTextFile().add(file);

            } else if ((item.getItem() instanceof OrderUnitVo)
                && (OrderUnitFileConverter.hasNewOrModifiedFields(OrderUnitFileConverter.FIELDS, item.getDifference(), item
                    .getAction()))) {

                OrderUnitFile file = OrderUnitFileConverter.toOrderUnitFile((OrderUnitVo) item.getItem(), item
                    .getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getOrderUnitFile().add(file);

            } else if ((item.getItem() instanceof RxConsultVo)
                && (RxConsultFileConverter.hasNewOrModifiedFields(RxConsultFileConverter.FIELDS, item.getDifference(), item
                    .getAction()))) {

                if (ItemAction.INACTIVATE.equals(item.getAction())) { // VistA does not support inactivating Rx Consult
                    continue;
                }

                RxConsultFile file = RxConsultFileConverter.toRxConsultFile((RxConsultVo) item.getItem(), item
                    .getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getRxConsultFile().add(file);

            } else if ((item.getItem() instanceof MedicationInstructionVo)
                && (MedicationInstructionFileConverter.hasNewOrModifiedFields(MedicationInstructionFileConverter.FIELDS,
                    item.getDifference(), item.getAction()))) {

                MedicationInstructionFile file = MedicationInstructionFileConverter.toMedicationInstructionFile(
                    (MedicationInstructionVo) item.getItem(), item.getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getMedicationInstructionFile().add(file);

            } else if ((item.getItem() instanceof PharmacySystemVo)
                && (PharmacySystemFileConverter.hasNewOrModifiedFields(PharmacySystemFileConverter.FIELDS, item
                    .getDifference(), item.getAction()))) {

                PharmacySystemFile file = PharmacySystemFileConverter.toPharmacySystemFile(
                    (PharmacySystemVo) item.getItem(), item.getDifference(), item.getAction());
                file.setAction(ItemAction.MODIFY);
                pdmDomain.setPharmacySystemFile(file);

            } else if ((item.getItem() instanceof StandardMedRouteVo)
                && (StandardMedicationRoutesFileConverter.hasNewOrModifiedFields(
                    StandardMedicationRoutesFileConverter.FIELDS, item.getDifference(), item.getAction()))) {

                StandardMedicationRoutesFile file = StandardMedicationRoutesFileConverter.toStandardMedicationRoutesFile(
                    (StandardMedRouteVo) item.getItem(), item.getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getStandardMedicationRoutesFile().add(file);

            } else if ((item.getItem() instanceof LocalMedicationRouteVo)
                && (MedicationRoutesFileConverter.hasNewOrModifiedFields(MedicationRoutesFileConverter.FIELDS, item
                    .getDifference(), item.getAction()))) {

                MedicationRoutesFile file = MedicationRoutesFileConverter.toMedicationRoutesFile(
                    (LocalMedicationRouteVo) item.getItem(), item.getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getMedicationRoutesFile().add(file);

            } else if ((item.getItem() instanceof DoseUnitVo)
                && (DoseUnitsFileConverter.hasNewOrModifiedFields(DoseUnitsFileConverter.FIELDS, item.getDifference(), item
                    .getAction()))) {

                DoseUnitsFile file = DoseUnitsFileConverter.toDoseUnitsFile((DoseUnitVo) item.getItem(), item
                    .getDifference(), item.getAction());
                file.setAction(toPdmDomainFileAction(item));
                pdmDomain.getDoseUnitsFile().add(file);

            }
        }

        return pdmDomain;
    }

    /**
     * Obtain the correct domain item action.
     * 
     * @param item domain item
     * @return domain item action
     */
    private static ItemAction toPdmDomainFileAction(DomainItem item) {
        if (item.getItem().isBeingReplacedByNational()) { // MODIFY action if replacing existing local item
            return ItemAction.MODIFY;
        }

        return item.getAction();
    }
}
