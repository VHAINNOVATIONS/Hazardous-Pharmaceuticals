/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugTextSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.WordProcessingFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField.WordProcessingFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drugtext.DrugTextFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drugtext.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.drugtext.SynonymFile;


/**
 * Converts a Drug Text VO to a Drug Text File.
 */
public class DrugTextFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(
        Arrays.asList(new FieldKey[] {
            FieldKey.VALUE, FieldKey.DRUG_TEXT_SYNONYMS, FieldKey.INACTIVATION_DATE, FieldKey.TEXT_LOCAL })));

    private static final ObjectFactory FACTORY = new ObjectFactory();
    private static final gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory ABSTRACT_OBJECT_FACTORY =
        new gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DrugTextFileConverter() {
    }

    /**
     * Convert a Drug Text VO to a Drug Text File.
     * 
     * @param drugTextVo Drug Text VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Drug Text File
     */
    public static DrugTextFile toDrugTextFile(DrugTextVo drugTextVo, Map<FieldKey, Difference> differences,
                                              ItemAction itemAction) {

        DrugTextFile drugTextFile = FACTORY.createDrugTextFile();
        drugTextFile.setCandidateKey(FACTORY.createDrugTextFileCandidateKey());
        drugTextFile.setNumber(new Float("51.7"));

        // NAME M - Candidate Key
        drugTextFile.getCandidateKey().setName(FACTORY.createNameKey());
        drugTextFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, drugTextVo.getValue()));
        drugTextFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            drugTextFile.setName(FACTORY.createNameKey());
            drugTextFile.getName().setValue(drugTextVo.getValue());
            drugTextFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // SYNONYM (Multiple)
        // SYNONYM M/O
        Collection<DrugTextSynonymVo> drugTextSynonyms = drugTextVo.getDrugTextSynonyms();

        if (isValid(drugTextSynonyms) || isUnset(FieldKey.DRUG_TEXT_SYNONYMS, differences)) {
            DrugTextFile.Synonym field = FACTORY.createDrugTextFileSynonym();
            field.setMultiple(true);
            field.setNumber(1f);

            JAXBElement<DrugTextFile.Synonym> element = FACTORY.createDrugTextFileSynonym(field);
            drugTextFile.setSynonym(element);

            if (isUnset(FieldKey.DRUG_TEXT_SYNONYMS, differences)) { // unset
                element.setNil(true);
            } else { // set
                for (DrugTextSynonymVo drugTextSynonymVo : drugTextSynonyms) {
                    field.getSynonymFile().add(toSynonymFile(drugTextSynonymVo, differences, itemAction));
                }
            }
        }

        // INACTIVATION DATE O/M
        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(drugTextVo.getInactivationDate())
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {
            DrugTextFile.InactivationDate field = FACTORY.createDrugTextFileInactivationDate();
            field.setNumber(2f);

            JAXBElement<DrugTextFile.InactivationDate> element = FACTORY.createDrugTextFileInactivationDate(field);
            drugTextFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(drugTextVo.getInactivationDate()));
            }
        }

        // TEXT M/O
        drugTextFile.setText(FACTORY.createDrugTextFileText());
        drugTextFile.getText().setWordProcessing(true);
        drugTextFile.getText().setNumber(new Float("3"));
        drugTextFile.getText().setWordProcessingFile(
            toWordProcessingFile(isValid(drugTextVo.getTextLocal()) ? drugTextVo.getTextLocal() : drugTextVo
                .getTextNational(), differences, itemAction));

        return drugTextFile;
    }

    /**
     * SYNONYM (Multiple)
     * 
     * @param drugTextSynonymVo Synonym
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Synonym File
     */
    private static SynonymFile toSynonymFile(DrugTextSynonymVo drugTextSynonymVo, Map<FieldKey, Difference> differences,
                                             ItemAction itemAction) {

        SynonymFile synonymFile = FACTORY.createSynonymFile();
        synonymFile.setNumber(new Float("51.71"));

        // SYNONYM M/O
        synonymFile.setSynonym(FACTORY.createSynonymFileSynonym());
        synonymFile.getSynonym().setValue(drugTextSynonymVo.getDrugTextSynonymName());
        synonymFile.getSynonym().setNumber(PPSConstants.F0POINT01);

        return synonymFile;

    }

    /**
     * Convert text to Word Processing File
     * 
     * @param text Text
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Word Processing File
     */
    private static WordProcessingFile toWordProcessingFile(String text, Map<FieldKey, Difference> differences,
                                                           ItemAction itemAction) {

        WordProcessingFile wordProcessingFile = ABSTRACT_OBJECT_FACTORY
            .createAbstractWordProcessingFieldWordProcessingFile();

        // TEXT M/O
        wordProcessingFile.setValue(new WordProcessingFormatter(text).format(PPSConstants.I70, 
            PPSConstants.I2000, false));
        wordProcessingFile.setAppend(false);

        return wordProcessingFile;

    }
}
