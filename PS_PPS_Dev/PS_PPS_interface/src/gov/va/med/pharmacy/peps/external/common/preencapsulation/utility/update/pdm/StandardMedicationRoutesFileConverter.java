/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.standardmedicationroutes.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.standardmedicationroutes.StandardMedicationRoutesFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.standardmedicationroutes.StandardMedicationRoutesFile.EffectiveDateTime.EffectiveDateTimeFile;


/**
 * This class is used to convert the medicaiton routs file
 */
public class StandardMedicationRoutesFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {FieldKey.VALUE, FieldKey.FDB_MED_ROUTE, FieldKey.VUID})));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private StandardMedicationRoutesFileConverter() {
    }

    /**
     * Creates the StandardMedicationRoutesFile from the input
     * 
     * @param standardMedicationRouteVo The StandardMedicationRouteVo
     * @param differences The differences file for modification purposes.
     * @param itemAction The ItemAction
     * @return StandardMedicationRoutesFile
     */
    public static StandardMedicationRoutesFile toStandardMedicationRoutesFile(StandardMedRouteVo standardMedicationRouteVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

//        DataFields<DataField> dataFields = standardMedicationRouteVo.getVaDataFields();

        StandardMedicationRoutesFile standardMedicationRoutesFile = FACTORY.createStandardMedicationRoutesFile();
        standardMedicationRoutesFile.setCandidateKey(FACTORY.createStandardMedicationRoutesFileCandidateKey());
        standardMedicationRoutesFile.setNumber(new Float("51.23"));

        // NAME M - Candidate Key
        standardMedicationRoutesFile.getCandidateKey().setName(FACTORY.createNameKey());
        standardMedicationRoutesFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, standardMedicationRouteVo.getValue()));
        standardMedicationRoutesFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            standardMedicationRoutesFile.setName(FACTORY.createNameKey());
            standardMedicationRoutesFile.getName().setValue(standardMedicationRouteVo.getValue());
            standardMedicationRoutesFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // FIRST DATABANK MED ROUTE M
        standardMedicationRoutesFile.setFirstDataBankMedicationRoute(FACTORY
            .createStandardMedicationRoutesFileFirstDataBankMedicationRoute());
        standardMedicationRoutesFile.getFirstDataBankMedicationRoute().setValue(
            standardMedicationRouteVo.getFirstDatabankMedRoute());
        standardMedicationRoutesFile.getFirstDataBankMedicationRoute().setNumber(1f);

        // REPLACED BY VHA STANDARD TERM O
        if (isValid(standardMedicationRouteVo.getReplacementStandardMedRoute())
            || isUnset(FieldKey.REPLACEMENT_STANDARD_MED_ROUTE, differences)) {
            StandardMedicationRoutesFile.ReplacedByVhaStandardTerm field = FACTORY
                .createStandardMedicationRoutesFileReplacedByVhaStandardTerm();
            field.setNumber(new Float("99.97"));

            JAXBElement<StandardMedicationRoutesFile.ReplacedByVhaStandardTerm> element = FACTORY
                .createStandardMedicationRoutesFileReplacedByVhaStandardTerm(field);
            standardMedicationRoutesFile.setReplacedByVhaStandardTerm(element);

            if (isUnset(FieldKey.REPLACEMENT_STANDARD_MED_ROUTE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(standardMedicationRouteVo.getReplacementStandardMedRoute().getValue());
            }
        }

        // VUID M - Candidate Key
        standardMedicationRoutesFile.getCandidateKey().setVuid(FACTORY.createVuidKey());
        standardMedicationRoutesFile.getCandidateKey().getVuid().setValue(
            ((String) toCandidateKeyValue(FieldKey.VUID, differences, standardMedicationRouteVo.getVuid())));
        standardMedicationRoutesFile.getCandidateKey().getVuid().setNumber(new Float("99.99"));

        // VUID M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VUID, differences)) {
            standardMedicationRoutesFile.setVuid(FACTORY.createVuidKey());
            standardMedicationRoutesFile.getVuid().setValue(standardMedicationRouteVo.getVuid());
            standardMedicationRoutesFile.getVuid().setNumber(new Float("99.99"));
        }

        // EFFECTIVE DATE/TIME M
        // STATUS M
        standardMedicationRoutesFile.setEffectiveDateTime(FACTORY.createStandardMedicationRoutesFileEffectiveDateTime());
        standardMedicationRoutesFile.getEffectiveDateTime().getEffectiveDateTimeFile().add(
            createEffectiveDateTimeFile(standardMedicationRouteVo));
        standardMedicationRoutesFile.getEffectiveDateTime().setMultiple(true);
        standardMedicationRoutesFile.getEffectiveDateTime().setNumber(new Float("99.991"));

        return standardMedicationRoutesFile;

    }

    /**
     * Creates the EffectiveDateTime File
     * 
     * @param standardMedicationRouteVo The StandardMedicationRouteVO
     * @return StandardMedicationRoutesFile.EffectiveDateTime.EffectiveDateTimeFile
     */
    private static StandardMedicationRoutesFile.EffectiveDateTime.EffectiveDateTimeFile createEffectiveDateTimeFile(
        StandardMedRouteVo standardMedicationRouteVo) {

        // EFFECTIVE DATE/TIME M
        EffectiveDateTimeFile effectiveDateTimeFile = FACTORY
            .createStandardMedicationRoutesFileEffectiveDateTimeEffectiveDateTimeFile();
        effectiveDateTimeFile.setNumber(new Float("51.2399"));

        gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory abstractObjectFactory =
            new gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ObjectFactory();
        effectiveDateTimeFile
            .setEffectiveDateTime(abstractObjectFactory.createAbstractEffectiveDateTimeFileEffectiveDateTime());
        effectiveDateTimeFile.getEffectiveDateTime().setValue(DateFormatter.toDateString(new Date()));
        effectiveDateTimeFile.getEffectiveDateTime().setNumber(PPSConstants.F0POINT01);

        // STATUS M
        effectiveDateTimeFile.setStatus(abstractObjectFactory.createAbstractEffectiveDateTimeFileStatus());
        effectiveDateTimeFile.getStatus().setValue(toBooleanOneOrZero(standardMedicationRouteVo.getItemStatus().isActive()));
        effectiveDateTimeFile.getStatus().setNumber(PPSConstants.F0POINT02);

        return effectiveDateTimeFile;
    }
}
