/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.doseunits.DoseUnitsFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.doseunits.DoseUnitsFile.EffectiveDateTime.EffectiveDateTimeFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.doseunits.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.doseunits.SynonymFile;


/**
 * Converts a Dose Units VO to a Dose Units File.
 */
public class DoseUnitsFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.DOSE_UNIT_NAME, FieldKey.FDB_DOSE_UNIT, FieldKey.DOSE_UNIT_SYNONYMS, FieldKey.REPLACEMENT_DOSE_UNIT,
            FieldKey.VUID })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DoseUnitsFileConverter() {
    }

    /**
     * Convert a Dose Unit Vo to a Dose Units File.
     * 
     * @param doseUnitVo Dose Unit Vo
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Dose Units File
     */
    public static DoseUnitsFile toDoseUnitsFile(DoseUnitVo doseUnitVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        DoseUnitsFile doseUnitsFile = FACTORY.createDoseUnitsFile();
        doseUnitsFile.setCandidateKey(FACTORY.createDoseUnitsFileCandidateKey());
        doseUnitsFile.setNumber(new Float("51.24"));

        // NAME M - Candidate Key
        doseUnitsFile.getCandidateKey().setName(FACTORY.createNameKey());
        doseUnitsFile.getCandidateKey().getName()
            .setValue((String) toCandidateKeyValue(FieldKey.DOSE_UNIT_NAME, differences, doseUnitVo.getDoseUnitName()));
        doseUnitsFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.DOSE_UNIT_NAME, differences)) {
            doseUnitsFile.setName(FACTORY.createNameKey());
            doseUnitsFile.getName().setValue(doseUnitVo.getDoseUnitName());
            doseUnitsFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // FIRST DATABANK DOSE UNIT M
        doseUnitsFile.setFirstDataBankDoseUnit(FACTORY.createDoseUnitsFileFirstDataBankDoseUnit());
        doseUnitsFile.getFirstDataBankDoseUnit().setValue(doseUnitVo.getFdbDoseUnit());
        doseUnitsFile.getFirstDataBankDoseUnit().setNumber(1f);

        // SYNONYM (Multiple)
        // SYNONYM M
        Collection<DoseUnitSynonymVo> doseUnitSynonyms = doseUnitVo.getDoseUnitSynonyms();

        if (isValid(doseUnitSynonyms) || isUnset(FieldKey.DOSE_UNIT_SYNONYMS, differences)) {
            DoseUnitsFile.Synonym field = FACTORY.createDoseUnitsFileSynonym();
            field.setMultiple(true);
            field.setNumber(2f);

            JAXBElement<DoseUnitsFile.Synonym> element = FACTORY.createDoseUnitsFileSynonym(field);

            doseUnitsFile.setSynonym(element);

            if (isUnset(FieldKey.DOSE_UNIT_SYNONYMS, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (DoseUnitSynonymVo doseUnitSynonymVo : doseUnitSynonyms) {
                    field.getSynonymFile().add(toSynonymFile(doseUnitSynonymVo, differences, itemAction));
                }
            }
        }

        // REPLACED BY VHA STANDARD TERM O
        if (isValid(doseUnitVo.getReplacementDoseUnit()) || isUnset(FieldKey.REPLACEMENT_DOSE_UNIT, differences)) {
            DoseUnitsFile.ReplacedByVhaStandardTerm field = FACTORY.createDoseUnitsFileReplacedByVhaStandardTerm();
            field.setNumber(new Float("99.97"));

            JAXBElement<DoseUnitsFile.ReplacedByVhaStandardTerm> element =
                FACTORY.createDoseUnitsFileReplacedByVhaStandardTerm(field);
            doseUnitsFile.setReplacedByVhaStandardTerm(element);

            if (isUnset(FieldKey.REPLACEMENT_DOSE_UNIT, differences)) { // unset
                element.setNil(true);
            }
        }

        // EFFECTIVE DATE/TIME (Multiple)
        // EFFECTIVE DATE/TIME M
        // STATUS M
        doseUnitsFile.setEffectiveDateTime(FACTORY.createDoseUnitsFileEffectiveDateTime());
        doseUnitsFile.getEffectiveDateTime().getEffectiveDateTimeFile().add(createEffectiveDateTimeFile(doseUnitVo));
        doseUnitsFile.getEffectiveDateTime().setNumber(new Float("99.991"));
        doseUnitsFile.getEffectiveDateTime().setMultiple(true);

        return doseUnitsFile;

    }

    /**
     * SYNONYM (Multiple)
     * 
     * @param doseUnitSynonymVo Dose Unit Synonym VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Synonym File
     */
    private static SynonymFile toSynonymFile(DoseUnitSynonymVo doseUnitSynonymVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        SynonymFile synonymFile = FACTORY.createSynonymFile();
        synonymFile.setNumber(new Float("51.242"));

        // SYNONYM M
        synonymFile.setSynonym(FACTORY.createSynonymFileSynonym());
        synonymFile.getSynonym().setValue(doseUnitSynonymVo.getDoseUnitSynonymName());
        synonymFile.getSynonym().setNumber(PPSConstants.F0POINT01);

        return synonymFile;

    }

    /**
     * EFFECTIVE DATE/TIME (Multiple)
     * 
     * @param doseUnitVo THis is the DoseUnitVo
     * @return Effective Date Time File
     */
    private static EffectiveDateTimeFile createEffectiveDateTimeFile(DoseUnitVo doseUnitVo) {

        EffectiveDateTimeFile effectiveDateTimeFile = FACTORY.createDoseUnitsFileEffectiveDateTimeEffectiveDateTimeFile();
        effectiveDateTimeFile.setNumber(PPSConstants.F51POINT2499);

        // EFFECTIVE DATE/TIME M
        gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory abstractObjectFactory =
            new gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory();
        effectiveDateTimeFile
            .setEffectiveDateTime(abstractObjectFactory.createAbstractEffectiveDateTimeFileEffectiveDateTime());
        effectiveDateTimeFile.getEffectiveDateTime().setValue(DateFormatter.toDateString(new Date()));
        effectiveDateTimeFile.getEffectiveDateTime().setNumber(PPSConstants.F0POINT01);

        // STATUS M
        effectiveDateTimeFile.setStatus(abstractObjectFactory.createAbstractEffectiveDateTimeFileStatus());
        effectiveDateTimeFile.getStatus().setValue(toBooleanOneOrZero(doseUnitVo.getItemStatus().isActive()));
        effectiveDateTimeFile.getStatus().setNumber(PPSConstants.F0POINT02);

        return effectiveDateTimeFile;

    }
}
