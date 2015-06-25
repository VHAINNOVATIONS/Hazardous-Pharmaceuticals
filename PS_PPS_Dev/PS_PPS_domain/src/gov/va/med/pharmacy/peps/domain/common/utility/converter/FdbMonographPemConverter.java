/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.DrugMonographVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplFdbMonographPemDo;


/**
 * Convert to/from {@link DrugMonographVo} and {@link EplFdbMonographPemDo}.
 */
public class FdbMonographPemConverter extends
        Converter<DrugMonographVo, EplFdbMonographPemDo> {

    /**
     * Standard constructor
     */
    public FdbMonographPemConverter() {
    }

    /**
     * Fully copies data from the given DrugMonographVo into a
     * {@link DataObject}.
     * 
     * @param data
     *            DrugMonographVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplFdbMonographPemDo toDataObject(DrugMonographVo data) {
        EplFdbMonographPemDo monographDo = new EplFdbMonographPemDo();

        
        monographDo.setMonographId(data.getMonographId());
        monographDo.setEnglishTitle(data.getEnglishTitle());
        monographDo.setSpanishTitle(data.getSpanishTitle());
        monographDo.setEnglishBrandName(data.getEnglishBrandName());
        monographDo.setSpanishBrandName(data.getSpanishBrandName());
        monographDo.setEnglishMissedDose(data.getEnglishMissedDose());
        monographDo.setSpanishMissedDose(data.getSpanishMissedDose());
        monographDo.setEnglishPhonetics(data.getEnglishPhonetics());
        monographDo.setSpanishPhonetics(data.getSpanishPhonetics());
        monographDo.setEnglishHowToTake(data.getEnglishHowToTake());
        monographDo.setSpanishHowToTake(data.getSpanishHowToTake());
        monographDo.setEnglishDrugInteractions(data.getEnglishDrugInteractions());
        monographDo.setSpanishDrugInteractions(data.getSpanishDrugInteractions());
        monographDo.setEnglishMedicalAlerts(data.getEnglishMedicalAlerts());
        monographDo.setSpanishMedicalAlerts(data.getSpanishMedicalAlerts());
        monographDo.setEnglishNotes(data.getEnglishNotes());
        monographDo.setSpanishNotes(data.getSpanishNotes());
        monographDo.setEnglishOverdose(data.getEnglishOverdose());
        monographDo.setSpanishOverdose(data.getSpanishOverdose());
        monographDo.setEnglishPrecautions(data.getEnglishPrecautions());
        monographDo.setSpanishPrecautions(data.getSpanishPrecautions());
        monographDo.setEnglishStorage(data.getEnglishStorage());
        monographDo.setSpanishStorage(data.getSpanishStorage());
        monographDo.setEnglishSideEffects(data.getEnglishSideEffects());
        monographDo.setSpanishSideEffects(data.getSpanishSideEffects());
        monographDo.setEnglishUses(data.getEnglishUses());
        monographDo.setSpanishUses(data.getSpanishUses());
        monographDo.setEnglishWarnings(data.getEnglishWarnings());
        monographDo.setSpanishWarnings(data.getSpanishWarnings());
        monographDo.setEnglishDisclaimer(data.getEnglishDisclaimer());
        monographDo.setSpanishDisclaimer(data.getSpanishDisclaimer());
        
        return monographDo;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * DrugMonographVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data EplFdbMonographPemDo to convert
     * @return fully populated DrugMonographVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DrugMonographVo toValueObject(EplFdbMonographPemDo data) {
        DrugMonographVo monographVo = new DrugMonographVo();

        monographVo.setMonographId(data.getMonographId());
        monographVo.setEnglishTitle(data.getEnglishTitle());
        monographVo.setSpanishTitle(data.getSpanishTitle());
        monographVo.setEnglishBrandName(data.getEnglishBrandName());
        monographVo.setSpanishBrandName(data.getSpanishBrandName());
        monographVo.setEnglishMissedDose(data.getEnglishMissedDose());
        monographVo.setSpanishMissedDose(data.getSpanishMissedDose());
        monographVo.setEnglishPhonetics(data.getEnglishPhonetics());
        monographVo.setSpanishPhonetics(data.getSpanishPhonetics());
        monographVo.setEnglishHowToTake(data.getEnglishHowToTake());
        monographVo.setSpanishHowToTake(data.getSpanishHowToTake());
        monographVo.setEnglishDrugInteractions(data.getEnglishDrugInteractions());
        monographVo.setSpanishDrugInteractions(data.getSpanishDrugInteractions());
        monographVo.setEnglishMedicalAlerts(data.getEnglishMedicalAlerts());
        monographVo.setSpanishMedicalAlerts(data.getSpanishMedicalAlerts());
        monographVo.setEnglishNotes(data.getEnglishNotes());
        monographVo.setSpanishNotes(data.getSpanishNotes());
        monographVo.setEnglishOverdose(data.getEnglishOverdose());
        monographVo.setSpanishOverdose(data.getSpanishOverdose());
        monographVo.setEnglishPrecautions(data.getEnglishPrecautions());
        monographVo.setSpanishPrecautions(data.getSpanishPrecautions());
        monographVo.setEnglishStorage(data.getEnglishStorage());
        monographVo.setSpanishStorage(data.getSpanishStorage());
        monographVo.setEnglishSideEffects(data.getEnglishSideEffects());
        monographVo.setSpanishSideEffects(data.getSpanishSideEffects());
        monographVo.setEnglishUses(data.getEnglishUses());
        monographVo.setSpanishUses(data.getSpanishUses());
        monographVo.setEnglishWarnings(data.getEnglishWarnings());
        monographVo.setSpanishWarnings(data.getSpanishWarnings());
        monographVo.setEnglishDisclaimer(data.getEnglishDisclaimer());
        monographVo.setSpanishDisclaimer(data.getSpanishDisclaimer());
        
        return monographVo;
    }
}
