/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugmanufacturer.DrugManufacturerFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.drugmanufacturer.ObjectFactory;


/**
 * Converts Manufacturer VO to Drug Manufacturer File document.
 */
public class DrugManufacturerFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new HashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.INACTIVATION_DATE })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private DrugManufacturerFileConverter() {
    }

    /**
     * Convert Manufacturer VO to Drug Manufacturer File document.
     * 
     * @param manufacturerVo manufacturer
     * @param differences set difference
     * @param itemAction add/modify/inactivate
     * @return manufacturer file
     */
    public static DrugManufacturerFile toDrugManufacturerFile(ManufacturerVo manufacturerVo,
                                                              Map<FieldKey, Difference> differences, ItemAction itemAction) {

        DrugManufacturerFile manufacturerFile = FACTORY.createDrugManufacturerFile();
        manufacturerFile.setNumber(new Float("55.95"));

        // NAME M/M - Candidate Key
        manufacturerFile.setCandidateKey(FACTORY.createDrugManufacturerFileCandidateKey());
        manufacturerFile.getCandidateKey().setName(FACTORY.createNameKey());
        manufacturerFile.getCandidateKey().getName().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, manufacturerVo.getValue()));
        manufacturerFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            manufacturerFile.setName(FACTORY.createNameKey());
            manufacturerFile.getName().setValue(manufacturerVo.getValue());
            manufacturerFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // INACTIVATION DATE O/M
        Date inactivationDate = manufacturerVo.getInactivationDate();

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactivationDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {

            DrugManufacturerFile.InactivationDate field = FACTORY.createDrugManufacturerFileInactivationDate();
            field.setNumber(2f);

            JAXBElement<DrugManufacturerFile.InactivationDate> element = FACTORY
                .createDrugManufacturerFileInactivationDate(field);
            manufacturerFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactivationDate));
            }
        }

        return manufacturerFile;
    }
}
