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
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vadispenseunit.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.vadispenseunit.VaDispenseUnitFile;


/**
 * Converts Dispense Unit VO to VA Dispense File document.
 */
public class VaDispenseUnitFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.INACTIVATION_DATE })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private VaDispenseUnitFileConverter() {
    }

    /**
     * Convert Dispense Unit VO to VA Dispense File document.
     * 
     * @param dispenseUnitVo dispense unit
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return dispense unit
     */
    public static VaDispenseUnitFile toVaDispenseUnitFile(DispenseUnitVo dispenseUnitVo,
                                                          Map<FieldKey, Difference> differences, ItemAction itemAction) {

        VaDispenseUnitFile vaDispenseUnitFile = FACTORY.createVaDispenseUnitFile();
        vaDispenseUnitFile.setCandidateKey(FACTORY.createVaDispenseUnitFileCandidateKey());
        vaDispenseUnitFile.setNumber(new Float("50.64"));

        // NAME M/M - Candidate Key
        vaDispenseUnitFile.getCandidateKey().setName(FACTORY.createNameKey());
        vaDispenseUnitFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, dispenseUnitVo.getValue()));
        vaDispenseUnitFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            vaDispenseUnitFile.setName(FACTORY.createNameKey());
            vaDispenseUnitFile.getName().setValue(dispenseUnitVo.getValue());
            vaDispenseUnitFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // INACTIVATION DATE O/M
        Date inactivationDate = dispenseUnitVo.getInactivationDate();

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactivationDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {

            VaDispenseUnitFile.InactivationDate field = FACTORY.createVaDispenseUnitFileInactivationDate();
            field.setNumber(1f);

            JAXBElement<VaDispenseUnitFile.InactivationDate> element = FACTORY
                .createVaDispenseUnitFileInactivationDate(field);
            vaDispenseUnitFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactivationDate));
            }
        }

        return vaDispenseUnitFile;
    }
}
