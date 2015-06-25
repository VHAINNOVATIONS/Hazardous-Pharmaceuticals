/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.WordProcessingFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractEffectiveDateTimeFile.EffectiveDateTime;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractEffectiveDateTimeFile.Status;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.AbstractWordProcessingField.WordProcessingFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vadrugclass.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vadrugclass.VaDrugClassFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vadrugclass.VaDrugClassFile.EffectiveDateTime.EffectiveDateTimeFile;


/**
 * Converts Drug Class VO to VA Drug Class File document.
 */
public class VaDrugClassFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.CODE, FieldKey.CLASSIFICATION, FieldKey.PARENT_DRUG_CLASS, FieldKey.CLASSIFICATION_TYPE,
            FieldKey.DESCRIPTION, FieldKey.VUID })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private VaDrugClassFileConverter() {
    }

    /**
     * Convert Drug Class VO to VA Drug Class File document.
     * 
     * @param drugClassVo drug class
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return drug class file
     */
    public static VaDrugClassFile toVaDrugClassFile(DrugClassVo drugClassVo, Map<FieldKey, Difference> differences,
                                                    ItemAction itemAction) {

        // CODE M/M
        // CLASSIFICATION M/M
        // PARENT CLASS O/O
        // TYPE M/O
        // DESCRIPTION O/O
        // VUID M/O
        // EFFECTIVE DATE/TIME M/M
        // STATUS M/M

        VaDrugClassFile vaDrugClassFile = FACTORY.createVaDrugClassFile();
        vaDrugClassFile.setCandidateKey(FACTORY.createVaDrugClassFileCandidateKey());
        vaDrugClassFile.setNumber(new Float("50.605"));

        // CODE M/M - Candidate Key
        vaDrugClassFile.getCandidateKey().setCode(FACTORY.createCodeKey());
        vaDrugClassFile.getCandidateKey().getCode().setValue(
            (String) toCandidateKeyValue(FieldKey.CODE, differences, drugClassVo.getCode()));
        vaDrugClassFile.getCandidateKey().getCode().setNumber(PPSConstants.F0POINT01);

        // CODE O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.CODE, differences)) {
            vaDrugClassFile.setCode(FACTORY.createCodeKey());
            vaDrugClassFile.getCode().setValue(drugClassVo.getCode());
            vaDrugClassFile.getCode().setNumber(PPSConstants.F0POINT01);
        }

        // CLASSIFICATION M/M - Candidate Key
        vaDrugClassFile.getCandidateKey().setClassification(FACTORY.createClassificationKey());
        vaDrugClassFile.getCandidateKey().getClassification()
            .setValue((String) toCandidateKeyValue(FieldKey.CLASSIFICATION, differences, drugClassVo.getClassification()));
        vaDrugClassFile.getCandidateKey().getClassification().setNumber(1f);

        // CLASSIFICATION O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.CLASSIFICATION, differences)) {
            vaDrugClassFile.setClassification(FACTORY.createClassificationKey());
            vaDrugClassFile.getClassification().setValue(drugClassVo.getClassification());
            vaDrugClassFile.getClassification().setNumber(1f);
        }

        // PARENT CLASS O/O
        if (isValid(drugClassVo.getParentDrugClass()) || isUnset(FieldKey.PARENT_DRUG_CLASS, differences)) {
            VaDrugClassFile.ParentClass field = FACTORY.createVaDrugClassFileParentClass();
            field.setNumber(2f);

            JAXBElement<VaDrugClassFile.ParentClass> element = FACTORY.createVaDrugClassFileParentClass(field);
            vaDrugClassFile.setParentClass(element);

            if (isUnset(FieldKey.PARENT_DRUG_CLASS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(drugClassVo.getParentDrugClass().getCode());
            }
        }

        // TYPE M/O
        vaDrugClassFile.setType(FACTORY.createVaDrugClassFileType());
        vaDrugClassFile.getType().setNumber(new Float("3"));
        vaDrugClassFile.getType().setValue(
            new BigInteger(toString(VALUE_DASH_PATTERN, drugClassVo.getClassificationType().getValue())));

        // DESCRIPTION O/O
        if (isValid(drugClassVo.getDescription()) || isUnset(FieldKey.DESCRIPTION, differences)) {
            VaDrugClassFile.Description field = FACTORY.createVaDrugClassFileDescription();
            field.setWordProcessing(true);
            field.setNumber(new Float("4"));

            JAXBElement<VaDrugClassFile.Description> element = FACTORY.createVaDrugClassFileDescription(field);
            vaDrugClassFile.setDescription(element);

            if (isUnset(FieldKey.DESCRIPTION, differences)) { // unset
                element.setNil(true);
            } else { // set
                WordProcessingFile wordProcessing = ABSTRACT_FACTORY.createAbstractWordProcessingFieldWordProcessingFile();
                wordProcessing.setValue(new WordProcessingFormatter(drugClassVo.getDescription()).format(PPSConstants.I70, 
                    PPSConstants.I2000, false));
                wordProcessing.setAppend(false);

                field.setWordProcessingFile(wordProcessing);
            }
        }

        // VUID M/M - CandidateKey
        vaDrugClassFile.getCandidateKey().setVuid(FACTORY.createVuidKey());
        vaDrugClassFile.getCandidateKey().getVuid().setValue(
            (String) toCandidateKeyValue(FieldKey.VUID, differences, drugClassVo.getVuid()));
        vaDrugClassFile.getCandidateKey().getVuid().setNumber(new Float("99.99"));

        // VUID O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VUID, differences)) {
            vaDrugClassFile.setVuid(FACTORY.createVuidKey());
            vaDrugClassFile.getVuid().setValue(drugClassVo.getVuid());
            vaDrugClassFile.getVuid().setNumber(new Float("99.99"));
        }

        // EFFECTIVE DATE TIME M/M
        vaDrugClassFile.setEffectiveDateTime(FACTORY.createVaDrugClassFileEffectiveDateTime());
        vaDrugClassFile.getEffectiveDateTime().getEffectiveDateTimeFile().add(createEffectiveDateTime(drugClassVo));
        vaDrugClassFile.getEffectiveDateTime().setNumber(new Float("99.991"));
        vaDrugClassFile.getEffectiveDateTime().setMultiple(true);

        return vaDrugClassFile;
    }

    /**
     * Create effective date/time.
     * 
     * @param drugClassVo drug class
     * @return effective date/time
     */
    private static EffectiveDateTimeFile createEffectiveDateTime(DrugClassVo drugClassVo) {
        EffectiveDateTimeFile effectiveDateTimeFile = FACTORY.createVaDrugClassFileEffectiveDateTimeEffectiveDateTimeFile();
        effectiveDateTimeFile.setNumber(new Float("50.60509"));

        // EFFECTIVE DATE TIME M/M
        EffectiveDateTime effectiveDateTime = ABSTRACT_FACTORY.createAbstractEffectiveDateTimeFileEffectiveDateTime();
        effectiveDateTime.setValue(DateFormatter.toDateString(new Date())); // current time
        effectiveDateTime.setNumber(PPSConstants.F0POINT01);
        effectiveDateTimeFile.setEffectiveDateTime(effectiveDateTime);

        // STATUS M/M
        Status status = ABSTRACT_FACTORY.createAbstractEffectiveDateTimeFileStatus();
        status.setValue(toBooleanOneOrZero(drugClassVo.getItemStatus().isActive()));
        status.setNumber(PPSConstants.F0POINT02);
        effectiveDateTimeFile.setStatus(status);

        return effectiveDateTimeFile;
    }
}
