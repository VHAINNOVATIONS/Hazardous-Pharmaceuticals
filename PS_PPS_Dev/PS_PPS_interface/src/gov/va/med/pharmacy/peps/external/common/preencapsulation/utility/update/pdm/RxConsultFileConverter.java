/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.rxconsult.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.rxconsult.RxConsultFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.rxconsult.TextFile;


/**
 * Converts an Rx Consult VO to an Rx Consult File.
 */
public class RxConsultFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
            .asList(new FieldKey[] {
                FieldKey.VALUE, FieldKey.CONSULT_TEXT, FieldKey.WARNING_MAPPING, FieldKey.SPANISH_TRANSLATION })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private RxConsultFileConverter() {
    }

    /**
     * Convert an Rx Consult VO to an Rx Consult File.
     * 
     * @param rxConsultVo Rx Consult VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Rx Consult File
     */
    public static RxConsultFile toRxConsultFile(RxConsultVo rxConsultVo, Map<FieldKey, Difference> differences,
                                                ItemAction itemAction) {

        RxConsultFile rxConsultFile = FACTORY.createRxConsultFile();
        rxConsultFile.setCandidateKey(FACTORY.createRxConsultFileCandidateKey());
        rxConsultFile.setNumber(new Float("54"));

        // NAME M - Candidate Key
        rxConsultFile.getCandidateKey().setName(FACTORY.createNameKey());
        rxConsultFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, rxConsultVo.getValue()));
        rxConsultFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            rxConsultFile.setName(FACTORY.createNameKey());
            rxConsultFile.getName().setValue(rxConsultVo.getValue());
            rxConsultFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // TEXT (Multiple)
        // TEXT M
        rxConsultFile.setText(FACTORY.createRxConsultFileText());

        // break text into 40 character parts, up to 280 characters
        for (int i = 0, length = rxConsultVo.getConsultText().length(); i < length; i += PPSConstants.I40) {
            String line = rxConsultVo.getConsultText().substring(i, i + Math.min(PPSConstants.I40, length - i));

            if (line.length() < PPSConstants.I3) { // 1 or 2 character strings, pad with spaces
                line += "  ".substring(0, PPSConstants.I3 - line.length());
            }

            rxConsultFile.getText().getTextFile().add(toTextFile(line));
        }

        rxConsultFile.getText().setMultiple(true);
        rxConsultFile.getText().setNumber(1f);

        // WARNING MAPPING O
        if (isValid(rxConsultVo.getWarningMapping()) || isUnset(FieldKey.WARNING_MAPPING, differences)) {
            RxConsultFile.WarningMapping field = FACTORY.createRxConsultFileWarningMapping();
            field.setNumber(2f);

            JAXBElement<RxConsultFile.WarningMapping> element = FACTORY.createRxConsultFileWarningMapping(field);
            rxConsultFile.setWarningMapping(element);

            if (isUnset(FieldKey.WARNING_MAPPING, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(new BigInteger(rxConsultVo.getWarningMapping().getValue()));
            }
        }

        // SPANISH TRANSLATION O
        if (isValid(rxConsultVo.getSpanishTranslation()) || isUnset(FieldKey.SPANISH_TRANSLATION, differences)) {
            RxConsultFile.SpanishTranslation field = FACTORY.createRxConsultFileSpanishTranslation();
            field.setNumber(new Float("3"));

            JAXBElement<RxConsultFile.SpanishTranslation> element = FACTORY.createRxConsultFileSpanishTranslation(field);
            rxConsultFile.setSpanishTranslation(element);

            if (isUnset(FieldKey.SPANISH_TRANSLATION, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(rxConsultVo.getSpanishTranslation());
            }
        }

        return rxConsultFile;

    }

    /**
     * This method convets a string to a TextFile
     * 
     * @param text The text to set into the file
     * @return TextFile
     */
    private static TextFile toTextFile(String text) {
        TextFile textFile = FACTORY.createTextFile();
        textFile.setNumber(new Float("54.1"));

        // TEXT M
        textFile.setText(FACTORY.createTextFileText());
        textFile.getText().setValue(text);
        textFile.getText().setNumber(PPSConstants.F0POINT01);

        return textFile;
    }
}
