/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.utility;


import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DrugMonograph;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.pmi.response.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.drug.pmi.response.PmiData;


/**
 * Converts FDB monograph data to a PMI document.
 */
public class PatientMedicationInformationConverter extends AbstractConverter {

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private PatientMedicationInformationConverter() {
    }

    /**
     * Convert to PMI document.
     * 
     * @param monograph FDB monograph
     * @return document
     */
    public static PmiData toPmiData(DrugMonograph monograph) {
        PmiData pmiData = FACTORY.createPmiData();

        if (monograph == null) {
            pmiData.setNoData(FACTORY.createPmiDataNoData());
        } else {
            pmiData.setTitle(monograph.getTitle());
            pmiData.setCommonBrandNames(monograph.getBrandName());
            pmiData.setMissedDose(monograph.getMissedDose());
            pmiData.setPhonetics(monograph.getPhonetics());
            pmiData.setHowToTake(monograph.getHowToTake());
            pmiData.setDrugInteractions(monograph.getDrugInteractions());
            pmiData.setMedicalAlert(monograph.getMedicalAlerts());
            pmiData.setNotes(monograph.getNotes());
            pmiData.setOverdose(monograph.getOverdose());
            pmiData.setPrecautions(monograph.getPrecautions());
            pmiData.setStorage(monograph.getStorage());
            pmiData.setSideEffects(monograph.getSideEffects());
            pmiData.setUses(monograph.getUses());
            pmiData.setWarnings(monograph.getWarnings());
            pmiData.setDisclaimer(monograph.getDisclaimer());
        }

        return pmiData;
    }

}
