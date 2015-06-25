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
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.DsplyOnIvpIvbpTabInBcma;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.InactivationDate;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.IvFlag;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.OtherLanguageExpansion;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.OutpatientExpansion;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.PackageUse;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.PromptForInjSiteInBcma;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.MedicationRoutesFile.StandardMedicationRoute;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.medicationroutes.ObjectFactory;


/**
 * Converts a Local Medication Route VO to a Medication Routes File.
 */
public class MedicationRoutesFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.VALUE, FieldKey.ABBREVIATION, FieldKey.PACKAGE_USAGES, FieldKey.OUTPATIENT_EXPANSION,
            FieldKey.OTHER_LANGUAGE_EXPANSION, FieldKey.INACTIVATION_DATE, FieldKey.IV_FLAG,
            FieldKey.PROMPT_FOR_INJECTION_SITE, FieldKey.DISPLAY_ON_IVP, FieldKey.STANDARD_MED_ROUTE })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private MedicationRoutesFileConverter() {
    }

    /**
     * Convert a Local Medication Route VO to a Medication Routes File.
     * 
     * @param localRoute Local Medication Route VO
     * @param diffs Differences
     * @param action Add/Modify/Inactivate
     * @return Medication Routes File
     */
    public static MedicationRoutesFile toMedicationRoutesFile(LocalMedicationRouteVo localRoute,
        Map<FieldKey, Difference> diffs, ItemAction action) {

        MedicationRoutesFile mrf = FACTORY.createMedicationRoutesFile();
        mrf.setCandidateKey(FACTORY.createMedicationRoutesFileCandidateKey());
        mrf.setNumber(new Float("51.2"));

        // NAME M - Candidate Key
        mrf.getCandidateKey().setName(FACTORY.createNameKey());
        mrf.getCandidateKey().getName().setValue((String) toCandidateKeyValue(FieldKey.VALUE, diffs, localRoute.getValue()));
        mrf.getCandidateKey().getName().setNumber(PPSConstants.F0POINT01);

        // NAME M
        if (ItemAction.ADD.equals(action) || hasOldValue(FieldKey.VALUE, diffs)) {
            mrf.setName(FACTORY.createNameKey());
            mrf.getName().setValue(localRoute.getValue());
            mrf.getName().setNumber(PPSConstants.F0POINT01);
        }

        // ABBREVIATION M
        mrf.setAbbreviation(FACTORY.createMedicationRoutesFileAbbreviation());
        mrf.getAbbreviation().setValue(localRoute.getAbbreviation());
        mrf.getAbbreviation().setNumber(1f);

        // PACKAGE USE O
        Collection<PackageUsageVo> packageUsages = localRoute.getPackageUsages();

        if (packageUsages != null && !packageUsages.isEmpty()) {

            PackageUsageVo[] usages = new PackageUsageVo[packageUsages.size()];
            PackageUsageVo packageUsageVo = packageUsages.toArray(usages)[0];

            if (isValid(packageUsageVo) || isUnset(FieldKey.PACKAGE_USAGES, diffs)) {
                PackageUse field = FACTORY.createMedicationRoutesFilePackageUse();
                field.setNumber(new Float("3"));
                JAXBElement<PackageUse> element = FACTORY.createMedicationRoutesFilePackageUse(field);
                mrf.setPackageUse(element);

                if (isUnset(FieldKey.PACKAGE_USAGES, diffs)) { // unset
                    element.setNil(true);
                } else { // set
                    field.setValue(toBooleanOneOrZero(Boolean.valueOf(packageUsageVo.getValue())));
                }
            }
        }

        // OUTPATIENT EXPANSION O
        if (isValid(localRoute.getOutpatientExpansion()) || isUnset(FieldKey.OUTPATIENT_EXPANSION, diffs)) {
            OutpatientExpansion field = FACTORY.createMedicationRoutesFileOutpatientExpansion();
            field.setNumber(new Float("4"));
            JAXBElement<OutpatientExpansion> element = FACTORY.createMedicationRoutesFileOutpatientExpansion(field);
            mrf.setOutpatientExpansion(element);

            if (isUnset(FieldKey.OUTPATIENT_EXPANSION, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(localRoute.getOutpatientExpansion());
            }
        }

        // OTHER LANGUAGE EXPANSION O
        if (isValid(localRoute.getOtherLanguageExpansion()) || isUnset(FieldKey.OTHER_LANGUAGE_EXPANSION, diffs)) {
            OtherLanguageExpansion field = FACTORY.createMedicationRoutesFileOtherLanguageExpansion();
            field.setNumber(new Float("4.1"));
            JAXBElement<OtherLanguageExpansion> element = FACTORY.createMedicationRoutesFileOtherLanguageExpansion(field);
            mrf.setOtherLanguageExpansion(element);

            if (isUnset(FieldKey.OTHER_LANGUAGE_EXPANSION, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(localRoute.getOtherLanguageExpansion());
            }
        }

        // INACTIVATION DATE O
        if (ItemAction.INACTIVATE.equals(action) || isValid(localRoute.getInactivationDate())
            || isUnset(FieldKey.INACTIVATION_DATE, diffs)) {
            InactivationDate field = FACTORY.createMedicationRoutesFileInactivationDate();
            field.setNumber(new Float("5"));
            JAXBElement<InactivationDate> element = FACTORY.createMedicationRoutesFileInactivationDate(field);
            mrf.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(localRoute.getInactivationDate()));
            }
        }

        // IV FLAG O
        if (isValid(localRoute.getIvFlag()) || isUnset(FieldKey.IV_FLAG, diffs)) {
            IvFlag field = FACTORY.createMedicationRoutesFileIvFlag();
            field.setNumber(new Float("6f"));
            JAXBElement<IvFlag> element = FACTORY.createMedicationRoutesFileIvFlag(field);
            mrf.setIvFlag(element);

            if (isUnset(FieldKey.IV_FLAG, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(toBooleanOneOrZero(localRoute.getIvFlag()));
            }
        }

        // PROMPT FOR INJ. SITE IN BCMA O
        if (isValid(localRoute.getPromptForInjectionSite()) || isUnset(FieldKey.PROMPT_FOR_INJECTION_SITE, diffs)) {
            PromptForInjSiteInBcma field = FACTORY.createMedicationRoutesFilePromptForInjSiteInBcma();
            field.setNumber(new Float("8"));
            JAXBElement<PromptForInjSiteInBcma> element = FACTORY.createMedicationRoutesFilePromptForInjSiteInBcma(field);
            mrf.setPromptForInjSiteInBcma(element);

            if (isUnset(FieldKey.PROMPT_FOR_INJECTION_SITE, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(toBooleanOneOrZero(localRoute.getPromptForInjectionSite()));
            }
        }

        // DSPLY ON IVP/IVPB TAB IN BCMA? O
        if (isValid(localRoute.getDisplayOnIvp()) || isUnset(FieldKey.DISPLAY_ON_IVP, diffs)) {
            DsplyOnIvpIvbpTabInBcma field = FACTORY.createMedicationRoutesFileDsplyOnIvpIvbpTabInBcma();
            field.setNumber(new Float("9"));
            JAXBElement<DsplyOnIvpIvbpTabInBcma> element = FACTORY.createMedicationRoutesFileDsplyOnIvpIvbpTabInBcma(field);
            mrf.setDsplyOnIvpIvbpTabInBcma(element);

            if (isUnset(FieldKey.DISPLAY_ON_IVP, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(toBooleanOneOrZero(localRoute.getDisplayOnIvp()));
            }
        }

        // STANDARD MEDICATION ROUTE O
        if (isValid(localRoute.getStandardMedRoute()) || isUnset(FieldKey.STANDARD_MED_ROUTE, diffs)) {
            StandardMedicationRoute field = FACTORY.createMedicationRoutesFileStandardMedicationRoute();
            field.setNumber(new Float("10"));
            JAXBElement<StandardMedicationRoute> element = FACTORY.createMedicationRoutesFileStandardMedicationRoute(field);
            mrf.setStandardMedicationRoute(element);

            if (isUnset(FieldKey.STANDARD_MED_ROUTE, diffs)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(localRoute.getStandardMedRoute().getValue());
            }
        }

        return mrf;
    }
}
