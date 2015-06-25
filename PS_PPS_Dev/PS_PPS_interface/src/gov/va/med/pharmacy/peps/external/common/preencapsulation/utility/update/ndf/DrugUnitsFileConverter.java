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
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugunits.DrugUnitsFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugunits.ObjectFactory;


/**
 * Converts Drug Unit VO to Drug Unit File document.
 */
public class DrugUnitsFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.INACTIVATION_DATE })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DrugUnitsFileConverter() {
    }

    /**
     * Convert Drug Unit VO to Drug Unit File document.
     * 
     * @param drugUnitVo drug unit
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return drug unit file
     */
    public static DrugUnitsFile toDrugUnitsFile(DrugUnitVo drugUnitVo, Map<FieldKey, Difference> differences,
                                                ItemAction itemAction) {

        DrugUnitsFile drugUnitsFile = FACTORY.createDrugUnitsFile();
        drugUnitsFile.setCandidateKey(FACTORY.createDrugUnitsFileCandidateKey());
        drugUnitsFile.setNumber(new Float("50.607"));

        // NAME M/M - Candidate Key
        drugUnitsFile.getCandidateKey().setName(FACTORY.createNameKey());
        drugUnitsFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, drugUnitVo.getValue()));
        drugUnitsFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            drugUnitsFile.setName(FACTORY.createNameKey());
            drugUnitsFile.getName().setValue(drugUnitVo.getValue());
            drugUnitsFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // INACTIVATION DATE O/M
        Date inactivationDate = drugUnitVo.getInactivationDate();

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactivationDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {

            DrugUnitsFile.InactivationDate field = FACTORY.createDrugUnitsFileInactivationDate();
            field.setNumber(1f);

            JAXBElement<DrugUnitsFile.InactivationDate> element = FACTORY.createDrugUnitsFileInactivationDate(field);
            drugUnitsFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactivationDate));
            }
        }

        return drugUnitsFile;
    }
}
