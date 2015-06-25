/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vageneric.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vageneric.VaGenericFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vageneric.VaGenericFile.EffectiveDateTime.EffectiveDateTimeFile;


/**
 * Converts Generic VO to VA Generic File document.
 */
public class VaGenericFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.GENERIC_NAME, FieldKey.INACTIVATION_DATE, FieldKey.VUID })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private VaGenericFileConverter() {
    }

    /**
     * Convert a Generic VO to a VA Generic File document.
     * 
     * @param genericNameVo Generic VO
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return Generic File
     */
    public static VaGenericFile toVaGenericFile(GenericNameVo genericNameVo, Map<FieldKey, Difference> differences,
                                                ItemAction itemAction) {

        VaGenericFile vaGenericNameFile = FACTORY.createVaGenericFile();
        vaGenericNameFile.setCandidateKey(FACTORY.createVaGenericFileCandidateKey());
        vaGenericNameFile.setNumber(new Float("50.6"));

        // NAME M/M - Candidate Key
        vaGenericNameFile.getCandidateKey().setName(FACTORY.createNameKey());
        vaGenericNameFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.GENERIC_NAME, differences, genericNameVo.getValue()));
        vaGenericNameFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.GENERIC_NAME, differences)) {
            vaGenericNameFile.setName(FACTORY.createNameKey());
            vaGenericNameFile.getName().setValue(genericNameVo.getValue());
            vaGenericNameFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // INACTIVATION DATE O/O
        Date inactivationDate = genericNameVo.getInactivationDate();

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactivationDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {

            VaGenericFile.InactivationDate field = FACTORY.createVaGenericFileInactivationDate();
            field.setNumber(1f);

            JAXBElement<VaGenericFile.InactivationDate> element = FACTORY.createVaGenericFileInactivationDate(field);
            vaGenericNameFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactivationDate));
            }
        }

        // VUID M/M - Candidate Key
        vaGenericNameFile.getCandidateKey().setVuid(FACTORY.createVuidKey());
        vaGenericNameFile.getCandidateKey().getVuid().setValue(genericNameVo.getVuid());
        vaGenericNameFile.getCandidateKey().getVuid().setNumber(new Float("99.99"));

        // VUID O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VUID, differences)) {
            vaGenericNameFile.setVuid(FACTORY.createVuidKey());
            vaGenericNameFile.getVuid().setValue(genericNameVo.getVuid());
            vaGenericNameFile.getVuid().setNumber(new Float("99.99"));
        }

        // EFFECTIVE DATE TIME M/M
        vaGenericNameFile.setEffectiveDateTime(FACTORY.createVaGenericFileEffectiveDateTime());
        vaGenericNameFile.getEffectiveDateTime().getEffectiveDateTimeFile().add(createEffectiveDateTimeFile(genericNameVo));
        vaGenericNameFile.getEffectiveDateTime().setNumber(new Float("99.991"));
        vaGenericNameFile.getEffectiveDateTime().setMultiple(true);

        return vaGenericNameFile;
    }

    /**
     * Create effective date/time.
     * 
     * @param genericNameVo generic name
     * @return effective date/time
     */
    private static EffectiveDateTimeFile createEffectiveDateTimeFile(GenericNameVo genericNameVo) {
        EffectiveDateTimeFile effectiveDateTimeFile = FACTORY.createVaGenericFileEffectiveDateTimeEffectiveDateTimeFile();
        effectiveDateTimeFile.setNumber(new Float("50.6009"));

        // EFFECTIVE DATE TIME M/M
        effectiveDateTimeFile.setEffectiveDateTime(ABSTRACT_FACTORY.createAbstractEffectiveDateTimeFileEffectiveDateTime());
        effectiveDateTimeFile.getEffectiveDateTime().setValue(DateFormatter.toDateString(new Date()));
        effectiveDateTimeFile.getEffectiveDateTime().setNumber(PPSConstants.F0POINT01);

        // STATUS M/M
        effectiveDateTimeFile.setStatus(ABSTRACT_FACTORY.createAbstractEffectiveDateTimeFileStatus());
        effectiveDateTimeFile.getStatus().setValue(toBooleanOneOrZero(genericNameVo.getItemStatus().isActive()));
        effectiveDateTimeFile.getStatus().setNumber(PPSConstants.F0POINT02);

        return effectiveDateTimeFile;
    }
}
