/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacysystem.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.pharmacysystem.PharmacySystemFile;


/**
 * Converts a Pharmacy System VO to a Pharmacy System File.
 */
public class PharmacySystemFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.PHARMACY_SYSTEM_SITE_NAME, FieldKey.PHARMACY_SYSTEM_PMIS_PRINTER, FieldKey.PHARMACY_SYSTEM_PMIS_LANGUAGE,
            FieldKey.PHARMACY_SYSTEM_PMIS_SECTION_DELETE, FieldKey.PHARMACY_SYSTEM_WARNING_LABEL_SOURCE,
            FieldKey.PHARMACY_SYSTEM_CMOP_WARNING_LABEL_SOURCE, FieldKey.PHARMACY_SYSTEM_OPAI_WARNING_LABEL_SOURCE,
            FieldKey.PHARMACY_SYSTEM_SECONDS, FieldKey.PHARMACY_SYSTEM_MINUTES, FieldKey.PHARMACY_SYSTEM_DAYS,
            FieldKey.PHARMACY_SYSTEM_WEEKS, FieldKey.PHARMACY_SYSTEM_HOURS, FieldKey.PHARMACY_SYSTEM_MONTHS,
            FieldKey.PHARMACY_SYSTEM_AND, FieldKey.PHARMACY_SYSTEM_THEN, FieldKey.PHARMACY_SYSTEM_EXCEPT,
            FieldKey.PHARMACY_SYSTEM_ONE, FieldKey.PHARMACY_SYSTEM_TWO, FieldKey.PHARMACY_SYSTEM_THREE,
            FieldKey.PHARMACY_SYSTEM_FOUR, FieldKey.PHARMACY_SYSTEM_FIVE, FieldKey.PHARMACY_SYSTEM_SIX,
            FieldKey.PHARMACY_SYSTEM_SEVEN, FieldKey.PHARMACY_SYSTEM_EIGHT, FieldKey.PHARMACY_SYSTEM_NINE,
            FieldKey.PHARMACY_SYSTEM_TEN, FieldKey.PHARMACY_SYSTEM_ONE_HALF, FieldKey.PHARMACY_SYSTEM_ONE_FOURTH,
            FieldKey.PHARMACY_SYSTEM_ONE_THIRD, FieldKey.PHARMACY_SYSTEM_TWO_THIRDS, FieldKey.PHARMACY_SYSTEM_THREE_FOURTHS,
            FieldKey.PHARMACY_SYSTEM_FOR })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden Constructor
     */
    private PharmacySystemFileConverter() {
    }

    /**
     * Convert a Pharmacy System VO to a Pharmacy System File.
     * 
     * @param pharmacySystemVo Pharmacy System VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Pharmacy System File
     */
    public static PharmacySystemFile toPharmacySystemFile(PharmacySystemVo pharmacySystemVo,
        Map<FieldKey, Difference> differences, ItemAction itemAction) {

        PharmacySystemFile pharmacySystemFile = FACTORY.createPharmacySystemFile();
        pharmacySystemFile.setCandidateKey(FACTORY.createPharmacySystemFileCandidateKey());
        pharmacySystemFile.setNumber(new Float("59.7"));

        // SITE NAME M - Candidate Key
        pharmacySystemFile.getCandidateKey().setSiteName(FACTORY.createSiteNameKey());
        pharmacySystemFile.getCandidateKey().getSiteName().setValue(
            (String) toCandidateKeyValue(FieldKey.PHARMACY_SYSTEM_SITE_NAME, differences, pharmacySystemVo.getSiteName()));
        pharmacySystemFile.getCandidateKey().getSiteName().setNumber(PPSConstants.F0POINT01);

        // SITE NAME M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.PHARMACY_SYSTEM_SITE_NAME, differences)) {
            pharmacySystemFile.setSiteName(FACTORY.createSiteNameKey());
            pharmacySystemFile.getSiteName().setValue(pharmacySystemVo.getSiteName());
            pharmacySystemFile.getSiteName().setNumber(PPSConstants.F0POINT01);
        }

        // PMIS PRINTER M
        pharmacySystemFile.setPmisPrinter(FACTORY.createPharmacySystemFilePmisPrinter());
        pharmacySystemFile.getPmisPrinter().setValue(pharmacySystemVo.getPsPmisPrinter());
        pharmacySystemFile.getPmisPrinter().setNumber(new Float("13"));

        // PMIS LANGUAGE O
        // Interface Rule 27
        if (isValid(pharmacySystemVo.getPsPmisLanguage()) || isUnset(FieldKey.PHARMACY_SYSTEM_PMIS_LANGUAGE, differences)) {
            PharmacySystemFile.PmisLanguage field = FACTORY.createPharmacySystemFilePmisLanguage();
            field.setNumber(new Float("14"));

            JAXBElement<PharmacySystemFile.PmisLanguage> element = FACTORY.createPharmacySystemFilePmisLanguage(field);
            pharmacySystemFile.setPmisLanguage(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_PMIS_LANGUAGE, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (pharmacySystemVo.getPsPmisLanguage().isEnglish()) {
                    field.setValue("1");
                } else if (pharmacySystemVo.getPsPmisLanguage().isSpanish()) {
                    field.setValue("2");
                }
            }
        }

        // PMIS SECTION DELETE O
        if (isValid(pharmacySystemVo.getPsPmisSectionDelete())
            || isUnset(FieldKey.PHARMACY_SYSTEM_PMIS_SECTION_DELETE, differences)) {
            PharmacySystemFile.PmisSectionDelete field = FACTORY.createPharmacySystemFilePmisSectionDelete();
            field.setNumber(new Float("15"));

            JAXBElement<PharmacySystemFile.PmisSectionDelete> element = FACTORY
                .createPharmacySystemFilePmisSectionDelete(field);
            pharmacySystemFile.setPmisSectionDelete(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_PMIS_SECTION_DELETE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsPmisSectionDelete());
            }
        }

        // WARNING LABEL SOURCE O
        if (isValid(pharmacySystemVo.getPsWarningLabelSource())
            || isUnset(FieldKey.PHARMACY_SYSTEM_WARNING_LABEL_SOURCE, differences)) {
            PharmacySystemFile.WarningLabelSource field = FACTORY.createPharmacySystemFileWarningLabelSource();
            field.setNumber(new Float("16"));

            JAXBElement<PharmacySystemFile.WarningLabelSource> element = FACTORY
                .createPharmacySystemFileWarningLabelSource(field);
            pharmacySystemFile.setWarningLabelSource(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_WARNING_LABEL_SOURCE, differences)
                || !pharmacySystemVo.getPsWarningLabelSource().isCots()) { // unset
                element.setNil(true);
            } else { // set
                field.setValue("N");
            }
        }

        // CMOP WARNING LABEL SOURCE O
        if (isValid(pharmacySystemVo.getPsCmopWarningLabelSource())
            || isUnset(FieldKey.PHARMACY_SYSTEM_CMOP_WARNING_LABEL_SOURCE, differences)) {
            PharmacySystemFile.CmopWarningLabelSource field = FACTORY.createPharmacySystemFileCmopWarningLabelSource();
            field.setNumber(new Float("16.1"));

            JAXBElement<PharmacySystemFile.CmopWarningLabelSource> element = FACTORY
                .createPharmacySystemFileCmopWarningLabelSource(field);
            pharmacySystemFile.setCmopWarningLabelSource(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_CMOP_WARNING_LABEL_SOURCE, differences)
                || !pharmacySystemVo.getPsCmopWarningLabelSource().isCots()) { // unset
                element.setNil(true);
            } else { // set
                field.setValue("N");
            }
        }

        // OPAI WARNING LABEL SOURCE O
        if (isValid(pharmacySystemVo.getPsOpaiWarningLabelSource())
            || isUnset(FieldKey.PHARMACY_SYSTEM_OPAI_WARNING_LABEL_SOURCE, differences)) {
            PharmacySystemFile.OpaiWarningLabelSource field = FACTORY.createPharmacySystemFileOpaiWarningLabelSource();
            field.setNumber(new Float("16.2"));

            JAXBElement<PharmacySystemFile.OpaiWarningLabelSource> element = FACTORY
                .createPharmacySystemFileOpaiWarningLabelSource(field);
            pharmacySystemFile.setOpaiWarningLabelSource(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_OPAI_WARNING_LABEL_SOURCE, differences)
                || !pharmacySystemVo.getPsOpaiWarningLabelSource().isCots()) { // unset
                element.setNil(true);
            } else { // set
                field.setValue("N");
            }
        }

        // SECONDS O
        if (isValid(pharmacySystemVo.getPsSeconds()) || isUnset(FieldKey.PHARMACY_SYSTEM_SECONDS, differences)) {
            PharmacySystemFile.Seconds field = FACTORY.createPharmacySystemFileSeconds();
            field.setNumber(new Float("40.21"));

            JAXBElement<PharmacySystemFile.Seconds> element = FACTORY.createPharmacySystemFileSeconds(field);
            pharmacySystemFile.setSeconds(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_SECONDS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsSeconds());
            }
        }

        // MINUTES O
        if (isValid(pharmacySystemVo.getPsMinutes()) || isUnset(FieldKey.PHARMACY_SYSTEM_MINUTES, differences)) {
            PharmacySystemFile.Minutes field = FACTORY.createPharmacySystemFileMinutes();
            field.setNumber(new Float("40.22"));

            JAXBElement<PharmacySystemFile.Minutes> element = FACTORY.createPharmacySystemFileMinutes(field);
            pharmacySystemFile.setMinutes(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_MINUTES, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsMinutes());
            }
        }

        // DAYS O
        if (isValid(pharmacySystemVo.getPsDays()) || isUnset(FieldKey.PHARMACY_SYSTEM_DAYS, differences)) {
            PharmacySystemFile.Days field = FACTORY.createPharmacySystemFileDays();
            field.setNumber(new Float("40.23"));

            JAXBElement<PharmacySystemFile.Days> element = FACTORY.createPharmacySystemFileDays(field);
            pharmacySystemFile.setDays(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_DAYS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsDays());
            }
        }

        // WEEKS O
        if (isValid(pharmacySystemVo.getPsWeeks()) || isUnset(FieldKey.PHARMACY_SYSTEM_WEEKS, differences)) {
            PharmacySystemFile.Weeks field = FACTORY.createPharmacySystemFileWeeks();
            field.setNumber(new Float("40.24"));

            JAXBElement<PharmacySystemFile.Weeks> element = FACTORY.createPharmacySystemFileWeeks(field);
            pharmacySystemFile.setWeeks(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_WEEKS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsWeeks());
            }
        }

        // HOURS O
        if (isValid(pharmacySystemVo.getPsHours()) || isUnset(FieldKey.PHARMACY_SYSTEM_HOURS, differences)) {
            PharmacySystemFile.Hours field = FACTORY.createPharmacySystemFileHours();
            field.setNumber(new Float("40.25"));

            JAXBElement<PharmacySystemFile.Hours> element = FACTORY.createPharmacySystemFileHours(field);
            pharmacySystemFile.setHours(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_HOURS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsHours());
            }
        }

        // MONTHS O
        if (isValid(pharmacySystemVo.getPsMonths()) || isUnset(FieldKey.PHARMACY_SYSTEM_MONTHS, differences)) {
            PharmacySystemFile.Months field = FACTORY.createPharmacySystemFileMonths();
            field.setNumber(new Float("40.26"));

            JAXBElement<PharmacySystemFile.Months> element = FACTORY.createPharmacySystemFileMonths(field);
            pharmacySystemFile.setMonths(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_MONTHS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsMonths());
            }
        }

        // AND O
        if (isValid(pharmacySystemVo.getPsAnd()) || isUnset(FieldKey.PHARMACY_SYSTEM_AND, differences)) {
            PharmacySystemFile.And field = FACTORY.createPharmacySystemFileAnd();
            field.setNumber(new Float("40.27"));

            JAXBElement<PharmacySystemFile.And> element = FACTORY.createPharmacySystemFileAnd(field);
            pharmacySystemFile.setAnd(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_AND, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsAnd());
            }
        }

        // THEN O
        if (isValid(pharmacySystemVo.getPsThen()) || isUnset(FieldKey.PHARMACY_SYSTEM_THEN, differences)) {
            PharmacySystemFile.Then field = FACTORY.createPharmacySystemFileThen();
            field.setNumber(new Float("40.28"));

            JAXBElement<PharmacySystemFile.Then> element = FACTORY.createPharmacySystemFileThen(field);
            pharmacySystemFile.setThen(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_THEN, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsThen());
            }
        }

        // EXCEPT O
        if (isValid(pharmacySystemVo.getPsExcept()) || isUnset(FieldKey.PHARMACY_SYSTEM_EXCEPT, differences)) {
            PharmacySystemFile.Except field = FACTORY.createPharmacySystemFileExcept();
            field.setNumber(new Float("40.29"));

            JAXBElement<PharmacySystemFile.Except> element = FACTORY.createPharmacySystemFileExcept(field);
            pharmacySystemFile.setExcept(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_EXCEPT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsExcept());
            }
        }

        // ONE O
        if (isValid(pharmacySystemVo.getPsOne()) || isUnset(FieldKey.PHARMACY_SYSTEM_ONE, differences)) {
            PharmacySystemFile.One field = FACTORY.createPharmacySystemFileOne();
            field.setNumber(new Float("40.3"));

            JAXBElement<PharmacySystemFile.One> element = FACTORY.createPharmacySystemFileOne(field);
            pharmacySystemFile.setOne(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_ONE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsOne());
            }
        }

        // TWO O

        if (isValid(pharmacySystemVo.getPsTwo()) || isUnset(FieldKey.PHARMACY_SYSTEM_TWO, differences)) {
            PharmacySystemFile.Two field = FACTORY.createPharmacySystemFileTwo();
            field.setNumber(new Float("40.31"));

            JAXBElement<PharmacySystemFile.Two> element = FACTORY.createPharmacySystemFileTwo(field);
            pharmacySystemFile.setTwo(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_TWO, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsTwo());
            }
        }

        // THREE O
        if (isValid(pharmacySystemVo.getPsThree()) || isUnset(FieldKey.PHARMACY_SYSTEM_THREE, differences)) {
            PharmacySystemFile.Three field = FACTORY.createPharmacySystemFileThree();
            field.setNumber(new Float("40.32"));

            JAXBElement<PharmacySystemFile.Three> element = FACTORY.createPharmacySystemFileThree(field);
            pharmacySystemFile.setThree(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_THREE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsThree());
            }
        }

        // FOUR O
        if (isValid(pharmacySystemVo.getPsFour()) || isUnset(FieldKey.PHARMACY_SYSTEM_FOUR, differences)) {
            PharmacySystemFile.Four field = FACTORY.createPharmacySystemFileFour();
            field.setNumber(new Float("40.33"));

            JAXBElement<PharmacySystemFile.Four> element = FACTORY.createPharmacySystemFileFour(field);
            pharmacySystemFile.setFour(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_FOUR, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsFour());
            }
        }

        // FIVE O
        if (isValid(pharmacySystemVo.getPsFive()) || isUnset(FieldKey.PHARMACY_SYSTEM_FIVE, differences)) {
            PharmacySystemFile.Five field = FACTORY.createPharmacySystemFileFive();
            field.setNumber(new Float("40.34"));

            JAXBElement<PharmacySystemFile.Five> element = FACTORY.createPharmacySystemFileFive(field);
            pharmacySystemFile.setFive(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_FIVE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsFive());
            }
        }

        // SIX O
        if (isValid(pharmacySystemVo.getPsSix()) || isUnset(FieldKey.PHARMACY_SYSTEM_SIX, differences)) {
            PharmacySystemFile.Six field = FACTORY.createPharmacySystemFileSix();
            field.setNumber(new Float("40.35"));

            JAXBElement<PharmacySystemFile.Six> element = FACTORY.createPharmacySystemFileSix(field);
            pharmacySystemFile.setSix(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_SIX, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsSix());
            }
        }

        // SEVEN O
        if (isValid(pharmacySystemVo.getPsSeven()) || isUnset(FieldKey.PHARMACY_SYSTEM_SEVEN, differences)) {
            PharmacySystemFile.Seven field = FACTORY.createPharmacySystemFileSeven();
            field.setNumber(new Float("40.36"));

            JAXBElement<PharmacySystemFile.Seven> element = FACTORY.createPharmacySystemFileSeven(field);
            pharmacySystemFile.setSeven(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_SEVEN, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsSeven());
            }
        }

        // EIGHT O
        if (isValid(pharmacySystemVo.getPsEight()) || isUnset(FieldKey.PHARMACY_SYSTEM_EIGHT, differences)) {
            PharmacySystemFile.Eight field = FACTORY.createPharmacySystemFileEight();
            field.setNumber(new Float("40.37"));

            JAXBElement<PharmacySystemFile.Eight> element = FACTORY.createPharmacySystemFileEight(field);
            pharmacySystemFile.setEight(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_EIGHT, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsEight());
            }
        }

        // NINE O
        if (isValid(pharmacySystemVo.getPsNine()) || isUnset(FieldKey.PHARMACY_SYSTEM_NINE, differences)) {
            PharmacySystemFile.Nine field = FACTORY.createPharmacySystemFileNine();
            field.setNumber(new Float("40.38"));

            JAXBElement<PharmacySystemFile.Nine> element = FACTORY.createPharmacySystemFileNine(field);
            pharmacySystemFile.setNine(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_NINE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsNine());
            }
        }

        // TEN O
        if (isValid(pharmacySystemVo.getPsTen()) || isUnset(FieldKey.PHARMACY_SYSTEM_TEN, differences)) {
            PharmacySystemFile.Ten field = FACTORY.createPharmacySystemFileTen();
            field.setNumber(new Float("40.39"));

            JAXBElement<PharmacySystemFile.Ten> element = FACTORY.createPharmacySystemFileTen(field);
            pharmacySystemFile.setTen(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_TEN, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsTen());
            }
        }

        // ONE-HALF O
        if (isValid(pharmacySystemVo.getPsOneHalf()) || isUnset(FieldKey.PHARMACY_SYSTEM_ONE_HALF, differences)) {
            PharmacySystemFile.OneHalf field = FACTORY.createPharmacySystemFileOneHalf();
            field.setNumber(new Float("40.4"));

            JAXBElement<PharmacySystemFile.OneHalf> element = FACTORY.createPharmacySystemFileOneHalf(field);
            pharmacySystemFile.setOneHalf(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_ONE_HALF, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsOneHalf());
            }
        }

        // ONE-FOURTH O
        if (isValid(pharmacySystemVo.getPsOneFourth()) || isUnset(FieldKey.PHARMACY_SYSTEM_ONE_FOURTH, differences)) {
            PharmacySystemFile.OneFourth field = FACTORY.createPharmacySystemFileOneFourth();
            field.setNumber(new Float("40.41"));

            JAXBElement<PharmacySystemFile.OneFourth> element = FACTORY.createPharmacySystemFileOneFourth(field);
            pharmacySystemFile.setOneFourth(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_ONE_FOURTH, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsOneFourth());
            }
        }

        // ONE-THIRD O
        if (isValid(pharmacySystemVo.getPsOneThird()) || isUnset(FieldKey.PHARMACY_SYSTEM_ONE_THIRD, differences)) {
            PharmacySystemFile.OneThird field = FACTORY.createPharmacySystemFileOneThird();
            field.setNumber(new Float("40.42"));

            JAXBElement<PharmacySystemFile.OneThird> element = FACTORY.createPharmacySystemFileOneThird(field);
            pharmacySystemFile.setOneThird(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_ONE_THIRD, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsOneThird());
            }
        }

        // TWO-THIRDS O
        if (isValid(pharmacySystemVo.getPsTwoThirds()) || isUnset(FieldKey.PHARMACY_SYSTEM_TWO_THIRDS, differences)) {
            PharmacySystemFile.TwoThirds field = FACTORY.createPharmacySystemFileTwoThirds();
            field.setNumber(new Float("40.43"));

            JAXBElement<PharmacySystemFile.TwoThirds> element = FACTORY.createPharmacySystemFileTwoThirds(field);
            pharmacySystemFile.setTwoThirds(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_TWO_THIRDS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsTwoThirds());
            }
        }

        // THREE-FOURTHS O
        if (isValid(pharmacySystemVo.getPsThreeFourths()) || isUnset(FieldKey.PHARMACY_SYSTEM_THREE_FOURTHS, differences)) {
            PharmacySystemFile.ThreeFourths field = FACTORY.createPharmacySystemFileThreeFourths();
            field.setNumber(new Float("40.44"));

            JAXBElement<PharmacySystemFile.ThreeFourths> element = FACTORY.createPharmacySystemFileThreeFourths(field);
            pharmacySystemFile.setThreeFourths(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_THREE_FOURTHS, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsThreeFourths());
            }
        }

        // FOR O
        if (isValid(pharmacySystemVo.getPsFor()) || isUnset(FieldKey.PHARMACY_SYSTEM_FOR, differences)) {
            PharmacySystemFile.For field = FACTORY.createPharmacySystemFileFor();
            field.setNumber(new Float("40.45"));

            JAXBElement<PharmacySystemFile.For> element = FACTORY.createPharmacySystemFileFor(field);
            pharmacySystemFile.setFor(element);

            if (isUnset(FieldKey.PHARMACY_SYSTEM_FOR, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(pharmacySystemVo.getPsFor());
            }
        }

        return pharmacySystemFile;

    }
}
