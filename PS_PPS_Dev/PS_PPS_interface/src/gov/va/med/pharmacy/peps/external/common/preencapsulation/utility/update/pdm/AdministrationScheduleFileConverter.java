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
import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.HospitalLocationVo;
import gov.va.med.pharmacy.peps.common.vo.WardMultipleVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.administrationschedule.AdministrationScheduleFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.administrationschedule.HospitalLocationFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.administrationschedule.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.administrationschedule.WardFile;


/**
 * Converts an Administration Schedule VO to an Administration Schedule File.
 */
public class AdministrationScheduleFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.VALUE, FieldKey.STANDARD_ADMINISTRATION_TIMES, FieldKey.FREQUENCY, FieldKey.WARD_MULTIPLE,
            FieldKey.PACKAGE_PREFIX, FieldKey.OI_SCHEDULE_TYPE, FieldKey.STANDARD_SHIFTS, FieldKey.HOSPITAL_LOCATION_MULTIPLE,
            FieldKey.SHIFTS, FieldKey.ADMINISTRATION_TIMES, FieldKey.SHIFTS, FieldKey.OUTPATIENT_EXPANSION,
            FieldKey.OTHER_LANGUAGE_EXPANSION })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private AdministrationScheduleFileConverter() {
    }

    /**
     * Convert an Administration Schedule VO to an Administration Schedule File.
     * 
     * @param as Administration Schedule VO
     * @param diffs Differences
     * @param action Add/Modify/Inactivate
     * @return Administration Schedule File
     */
    public static AdministrationScheduleFile toAdministrationFile(AdministrationScheduleVo as, Map<FieldKey, Difference> diffs,
        ItemAction action) {

        AdministrationScheduleFile asFile = FACTORY.createAdministrationScheduleFile();
        asFile.setCandidateKey(FACTORY.createAdministrationScheduleFileCandidateKey());
        asFile.setNumber(new Float("51.1"));

        asFile.getCandidateKey().setName(FACTORY.createNameKey()); // NAME M - Candidate Key
        asFile.getCandidateKey().getName().setValue((String) toCandidateKeyValue(FieldKey.VALUE, diffs, as.getValue()));
        asFile.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        if (ItemAction.ADD.equals(action) || hasOldValue(FieldKey.VALUE, diffs)) { // NAME M
            asFile.setName(FACTORY.createNameKey());
            asFile.getName().setValue(as.getValue());
            asFile.getName().setNumber(PPSConstants.F0POINT01);
        }

        // STANDARD ADMINSTRATION TIMES O
        if (isValid(as.getStandardAdministrationTimes()) || isUnset(FieldKey.STANDARD_ADMINISTRATION_TIMES, diffs)) {
            AdministrationScheduleFile.StandardAdministrationTimes field =
                FACTORY.createAdministrationScheduleFileStandardAdministrationTimes();
            field.setNumber(1f);
            JAXBElement<AdministrationScheduleFile.StandardAdministrationTimes> element =
                FACTORY.createAdministrationScheduleFileStandardAdministrationTimes(field);
            asFile.setStandardAdministrationTimes(element);

            if (isUnset(FieldKey.STANDARD_ADMINISTRATION_TIMES, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(as.getStandardAdministrationTimes());
            }
        }

        // FREQUENCY (IN MINUTES) O
        if (isValid(as.getFrequency()) || isUnset(FieldKey.FREQUENCY, diffs)) {
            AdministrationScheduleFile.Frequency field = FACTORY.createAdministrationScheduleFileFrequency();
            field.setNumber(2f);
            JAXBElement<AdministrationScheduleFile.Frequency> element =
                FACTORY.createAdministrationScheduleFileFrequency(field);
            asFile.setFrequency(element);

            if (isUnset(FieldKey.FREQUENCY, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(BigInteger.valueOf(as.getFrequency()));
            }
        }

        // WARD (Multiple), WARD M, WARD ADMINISTRATION TIMES M
        if (isValid(as.getWardMultiple()) || isUnset(FieldKey.WARD_MULTIPLE, diffs)) {
            AdministrationScheduleFile.Ward field = FACTORY.createAdministrationScheduleFileWard();
            field.setMultiple(true);
            field.setNumber(new Float("3"));
            JAXBElement<AdministrationScheduleFile.Ward> element = FACTORY.createAdministrationScheduleFileWard(field);
            asFile.setWard(element);

            if (isUnset(FieldKey.WARD_MULTIPLE, diffs)) { // unset
                element.setNil(true);
            } else { // set
                for (WardMultipleVo wardMultipleVo : as.getWardMultiple()) {
                    field.getWardFile().add(toWardFile(wardMultipleVo, diffs, action));
                }
            }
        }

        // PACKAGE PREFIX M
        asFile.setPackagePrefix(FACTORY.createAdministrationScheduleFilePackagePrefix());
        asFile.getPackagePrefix().setValue(as.getPackagePrefix());
        asFile.getPackagePrefix().setNumber(new Float("4"));

        // TYPE OF SCHEDULE O
        if (isValid(as.getAdminScheduleType()) || isUnset(FieldKey.ADMIN_SCHEDULE_TYPE, diffs)) {
            AdministrationScheduleFile.TypeOfSchedule field = FACTORY.createAdministrationScheduleFileTypeOfSchedule();
            field.setNumber(new Float("5"));
            JAXBElement<AdministrationScheduleFile.TypeOfSchedule> element =
                FACTORY.createAdministrationScheduleFileTypeOfSchedule(field);
            asFile.setTypeOfSchedule(element);

            if (isUnset(FieldKey.ADMIN_SCHEDULE_TYPE, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(as.getAdminScheduleType().getCode());
            }
        }

        if (isValid(as.getStandardShifts()) || isUnset(FieldKey.STANDARD_SHIFTS, diffs)) { // STANDARD SHIFTS O
            AdministrationScheduleFile.StandardShifts field = FACTORY.createAdministrationScheduleFileStandardShifts();
            field.setNumber(new Float("6f"));
            JAXBElement<AdministrationScheduleFile.StandardShifts> element =
                FACTORY.createAdministrationScheduleFileStandardShifts(field);
            asFile.setStandardShifts(element);

            if (isUnset(FieldKey.STANDARD_SHIFTS, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(as.getStandardShifts());
            }
        }

        // HOSPITAL LOCATION (Multiple), HOSPITAL LOCATION M, ADMINISTRATION TIMES O, SHIFTS O
        if (isValid(as.getHospitalLocationMultiple()) || isUnset(FieldKey.HOSPITAL_LOCATION_MULTIPLE, diffs)) {
            AdministrationScheduleFile.HospitalLocation field = FACTORY.createAdministrationScheduleFileHospitalLocation();
            field.setMultiple(true);
            field.setNumber(new Float("7"));
            JAXBElement<AdministrationScheduleFile.HospitalLocation> element =
                FACTORY.createAdministrationScheduleFileHospitalLocation(field);
            asFile.setHospitalLocation(element);

            if (isUnset(FieldKey.HOSPITAL_LOCATION_MULTIPLE, diffs)) { // unset
                element.setNil(true);
            } else { // set
                for (HospitalLocationVo hospitalLocationVo : as.getHospitalLocationMultiple()) {
                    field.getHospitalLocationFile().add(toHospitalLocationFile(hospitalLocationVo, diffs, action));
                }
            }
        }

        // OUTPATIENT EXPANSION O
        if (isValid(as.getScheduleOutpatientExpansion()) || isUnset(FieldKey.OUTPATIENT_EXPANSION, diffs)) {
            AdministrationScheduleFile.OutpatientExpansion field =
                FACTORY.createAdministrationScheduleFileOutpatientExpansion();
            field.setNumber(new Float("8"));
            JAXBElement<AdministrationScheduleFile.OutpatientExpansion> element =
                FACTORY.createAdministrationScheduleFileOutpatientExpansion(field);
            asFile.setOutpatientExpansion(element);

            if (isUnset(FieldKey.OUTPATIENT_EXPANSION, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(as.getScheduleOutpatientExpansion());
            }
        }

        // OTHER LANGUAGE EXPANSION O
        if (isValid(as.getOtherLanguageExpansion()) || isUnset(FieldKey.OTHER_LANGUAGE_EXPANSION, diffs)) {
            AdministrationScheduleFile.OtherLanguageExpansion field =
                FACTORY.createAdministrationScheduleFileOtherLanguageExpansion();
            field.setNumber(new Float("8.1"));
            JAXBElement<AdministrationScheduleFile.OtherLanguageExpansion> element =
                FACTORY.createAdministrationScheduleFileOtherLanguageExpansion(field);
            asFile.setOtherLanguageExpansion(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_EXPANSION, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(as.getOtherLanguageExpansion());
            }
        }

        return asFile;
    }

    /**
     * WARD (Multiple)
     * 
     * @param wardMultipleVo Ward Multiple VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Ward File
     */
    private static WardFile toWardFile(WardMultipleVo wardMultipleVo, Map<FieldKey, Difference> differences,
        ItemAction itemAction) {

        ObjectFactory adminScheduleObjectFactory = new ObjectFactory();
        WardFile wardFile = adminScheduleObjectFactory.createWardFile();
        wardFile.setNumber(new Float("51.11"));

        // WARD M
        wardFile.setWard(adminScheduleObjectFactory.createWardFileWard());
        wardFile.getWard().setValue(wardMultipleVo.getWardSelection().getValue());
        wardFile.getWard().setNumber(PPSConstants.F0POINT01);

        // WARD ADMINISTRATION TIMES M
        wardFile.setWardAdministrationTimes(adminScheduleObjectFactory.createWardFileWardAdministrationTimes());
        wardFile.getWardAdministrationTimes().setValue(wardMultipleVo.getWardAdminTimes());
        wardFile.getWardAdministrationTimes().setNumber(1f);

        return wardFile;

    }

    /**
     * HOSPITAL LOCATION (Multiple)
     * 
     * @param hospitalLocationVo Hospital Location VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Hospital Location File
     */
    private static HospitalLocationFile toHospitalLocationFile(HospitalLocationVo hospitalLocationVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        HospitalLocationFile hospitalLocationFile = FACTORY.createHospitalLocationFile();
        hospitalLocationFile.setNumber(new Float("51.17"));

        // HOSPITAL LOCATION M
        hospitalLocationFile.setHospitalLocation(FACTORY.createHospitalLocationFileHospitalLocation());
        hospitalLocationFile.getHospitalLocation().setValue(hospitalLocationVo.getHospitalLocationSelection().getValue());
        hospitalLocationFile.getHospitalLocation().setNumber(PPSConstants.F0POINT01);

        // ADMINISTRATION TIMES O
        if (isValid(hospitalLocationVo.getAdministrationTimes()) || isUnset(FieldKey.ADMINISTRATION_TIMES, differences)) {
            HospitalLocationFile.AdministrationTimes field = FACTORY.createHospitalLocationFileAdministrationTimes();
            field.setNumber(1f);

            JAXBElement<HospitalLocationFile.AdministrationTimes> element =
                FACTORY.createHospitalLocationFileAdministrationTimes(field);

            hospitalLocationFile.setAdministrationTimes(element);

            if (isUnset(FieldKey.ADMINISTRATION_TIMES, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(hospitalLocationVo.getAdministrationTimes());
            }
        }

        // SHIFTS O
        if (isValid(hospitalLocationVo.getShifts()) || isUnset(FieldKey.SHIFTS, differences)) {
            HospitalLocationFile.Shifts field = FACTORY.createHospitalLocationFileShifts();
            field.setNumber(2f);

            JAXBElement<HospitalLocationFile.Shifts> element = FACTORY.createHospitalLocationFileShifts(field);

            hospitalLocationFile.setShifts(element);

            if (isUnset(FieldKey.SHIFTS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(hospitalLocationVo.getShifts());
            }
        }

        return hospitalLocationFile;

    }
}
