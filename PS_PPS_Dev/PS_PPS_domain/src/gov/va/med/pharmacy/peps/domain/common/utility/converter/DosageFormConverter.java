/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormNounVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ExcludeDosageCheck;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplDfMedRtDfAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDfMedRtDfAssocDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplDfNounDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDfNounDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplDfUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDfUnitDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplDispenseUnitsPerDoseDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDispenseUnitsPerDoseDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;


/**
 * Convert to/from {@link DosageFormVo} and {@link EplDosageFormDo}.
 */
public class DosageFormConverter extends Converter<DosageFormVo, EplDosageFormDo> {

    private LocalMedicationRouteConverter localMedicationRouteConverter;

    /**
     * Fully copies data from the given DosageFormVo into a EplDosageFormDo
     * 
     * @param data DosageFormVo to convert
     * @return fully populated EplDosageFormDo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplDosageFormDo toDataObject(DosageFormVo data) {
        EplDosageFormDo dosageForm = new EplDosageFormDo();
        dosageForm.setEplId(Long.valueOf(data.getId()));
        
        if (data.getDosageFormIen() != null) {
            dosageForm.setDosageformIen(Long.valueOf(data.getDosageFormIen()));
        }
        
        dosageForm.setDfName(data.getDosageFormName());
        dosageForm.setRevisionNumber(data.getRevisionNumber());

        dosageForm.setCreatedBy(data.getCreatedBy());
        dosageForm.setCreatedDtm(data.getCreatedDate());
        dosageForm.setLastModifiedBy(data.getModifiedBy());
        dosageForm.setLastModifiedDtm(data.getModifiedDate());

        if (data.getExcludeFromDosageChks() != null) {
            dosageForm.setExcludeFromDosageChks(data.getExcludeFromDosageChks()
                    .name());
        }

        if (data.getItemStatus() != null) {
            dosageForm.setItemStatus(data.getItemStatus().name());
        }

        if (data.getRequestItemStatus() != null) {
            dosageForm.setRequestStatus(data.getRequestItemStatus().name());
        }

        dosageForm.setRejectReasonText(data.getRejectionReasonText());

        if (data.getInactivationDate() != null) {
            dosageForm.setInactivationDate(data.getInactivationDate());
        }

        if (data.getDfDispenseUnitsPerDose() != null
                && data.getDfDispenseUnitsPerDose().size() > 0) {
            Set<EplDispenseUnitsPerDoseDo> eplDispenseUnitsPerDoses = new HashSet<EplDispenseUnitsPerDoseDo>();

            for (DispenseUnitPerDoseVo dose : data.getDfDispenseUnitsPerDose()) {
                EplDispenseUnitsPerDoseDoKey key = new EplDispenseUnitsPerDoseDoKey();
                key.setDispenseUnitPerDose(Double.valueOf(dose
                        .getStrDispenseUnitPerDose()));
                key.setDosageFormEplIdFk(dosageForm.getEplId());

                EplDispenseUnitsPerDoseDo doseDo = new EplDispenseUnitsPerDoseDo();
                doseDo.setKey(key);

                if (dose.getPackages() != null && dose.getPackages().size() > 0) {
                    doseDo.setPackageCode(getPackageName(dose.getPackages()));
                }

                eplDispenseUnitsPerDoses.add(doseDo);
            }

            dosageForm.setEplDispenseUnitsPerDoses(eplDispenseUnitsPerDoses);
        }

        if (data.getDfNouns() != null && data.getDfNouns().size() > 0) {
            Set<EplDfNounDo> eplDfNouns = new HashSet<EplDfNounDo>();

            for (DosageFormNounVo noun : data.getDfNouns()) {
                EplDfNounDoKey key = new EplDfNounDoKey();
                key.setDosageFormEplIdFk(dosageForm.getEplId());
                key.setNoun(noun.getNoun());

                EplDfNounDo nounDo = new EplDfNounDo();
                nounDo.setOtherLanguageNoun(noun.getOtherLanguageNoun());

                if (noun.getPackages() != null) {
                    nounDo.setPackageCode(getPackageName(noun.getPackages()));
                }

                nounDo.setKey(key);

                eplDfNouns.add(nounDo);
            }

            dosageForm.setEplDfNouns(eplDfNouns);
        }

        if (data.getDfUnits() != null && data.getDfUnits().size() > 0) {
            Set<EplDfUnitDo> eplDfUnits = new HashSet<EplDfUnitDo>();

            for (DosageFormUnitVo unit : data.getDfUnits()) {
                EplDfUnitDoKey key = new EplDfUnitDoKey();
                key.setDosageFormEplIdFk(dosageForm.getEplId());
                key.setDrugUnitEplIdFk(new Long(unit.getDrugUnit().getId()));

                EplDfUnitDo unitDo = new EplDfUnitDo();

                if (unit.getPackages() != null && unit.getPackages().size() > 0) {
                    unitDo.setPackageCode(getPackageName(unit.getPackages()));
                }

                unitDo.setKey(key);
                eplDfUnits.add(unitDo);
            }

            dosageForm.setEplDfUnits(eplDfUnits);
        }

        if (data.getLocalMedRoutes() != null
                && data.getLocalMedRoutes().size() > 0) {
            Set<EplDfMedRtDfAssocDo> localAssocs = new HashSet<EplDfMedRtDfAssocDo>();

            for (LocalMedicationRouteVo localRoute : data.getLocalMedRoutes()) {
                if ((localRoute != null) && (localRoute.getId() != null)) {
                    EplDfMedRtDfAssocDoKey key = new EplDfMedRtDfAssocDoKey();
                    key.setDosageFormIdFk(dosageForm.getEplId());
                    key.setMedRouteIdFk(new Long(localRoute.getId()));

                    EplDfMedRtDfAssocDo assocDo = new EplDfMedRtDfAssocDo();
                    assocDo.setKey(key);
                    localAssocs.add(assocDo);
                }
            }

            dosageForm.setEplDfMedRtDfAssocs(localAssocs);
        }

        return dosageForm;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * DosageFormVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated DosageFormVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected DosageFormVo toValueObject(EplDosageFormDo data) {
        DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setId(String.valueOf(data.getEplId()));
        
        if (data.getDosageformIen() != null) {
            dosageForm.setDosageFormIen(String.valueOf(data.getDosageformIen()));
        }
        
        dosageForm.setDosageFormName(data.getDfName());

        dosageForm.setDosageFormName(data.getDfName());
        dosageForm.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        dosageForm.setRequestItemStatus(RequestItemStatus.valueOf(data
                .getRequestStatus()));
        dosageForm.setRejectionReasonText(data.getRejectReasonText());
        dosageForm.setRevisionNumber(data.getRevisionNumber());
        dosageForm.setCreatedBy(data.getCreatedBy());
        dosageForm.setCreatedDate(data.getCreatedDtm());
        dosageForm.setModifiedBy(data.getLastModifiedBy());
        dosageForm.setModifiedDate(data.getLastModifiedDtm());

        if (data.getExcludeFromDosageChks() != null) {
            dosageForm.setExcludeFromDosageChks(ExcludeDosageCheck.valueOf(data
                    .getExcludeFromDosageChks()));
        }

        if (data.getInactivationDate() != null) {
            dosageForm.setInactivationDate(data.getInactivationDate());
        }

        if (data.getEplDfNouns() != null && data.getEplDfNouns().size() > 0) {
            Collection<DosageFormNounVo> dfNouns = new ArrayList<DosageFormNounVo>();

            for (EplDfNounDo nounDo : data.getEplDfNouns()) {
                DosageFormNounVo noun = new DosageFormNounVo();
                noun.setNoun(nounDo.getKey().getNoun());
                noun.setOtherLanguageNoun(nounDo.getOtherLanguageNoun());

                if (nounDo.getPackageCode() != null) {
                    noun.setPackages(getPossibleDosagePackageVo(nounDo
                            .getPackageCode()));
                }

                dfNouns.add(noun);
            }

            dosageForm.setDfNouns(dfNouns);
        }

        if (data.getEplDfUnits() != null && data.getEplDfUnits().size() > 0) {
            Collection<DosageFormUnitVo> dfUnits = convertDfUnits(data);
            dosageForm.setDfUnits(dfUnits);
        }

        if (data.getEplDispenseUnitsPerDoses() != null
                && data.getEplDispenseUnitsPerDoses().size() > 0) {
            Collection<DispenseUnitPerDoseVo> dfUnitsPerDose = new ArrayList<DispenseUnitPerDoseVo>();

            for (EplDispenseUnitsPerDoseDo dispenseUnitPerDose : data
                    .getEplDispenseUnitsPerDoses()) {
                DispenseUnitPerDoseVo noun = new DispenseUnitPerDoseVo();
                noun.setStrDispenseUnitPerDose(String
                        .valueOf(dispenseUnitPerDose.getKey()
                                .getDispenseUnitPerDose()));
                noun.setPackages(getPossibleDosagePackageVo(dispenseUnitPerDose
                        .getPackageCode()));

                dfUnitsPerDose.add(noun);
            }

            dosageForm.setDfDispenseUnitsPerDose(dfUnitsPerDose);
        }

        if (data.getEplDfMedRtDfAssocs() != null
                && data.getEplDfMedRtDfAssocs().size() > 0) {
            Collection<LocalMedicationRouteVo> localRoutes = new ArrayList<LocalMedicationRouteVo>();

            for (EplDfMedRtDfAssocDo assoc : data.getEplDfMedRtDfAssocs()) {
                LocalMedicationRouteVo route = localMedicationRouteConverter
                        .convertMinimal(assoc.getEplLocalMedRoute());
                localRoutes.add(route);
            }

            dosageForm.setLocalMedRoutes(localRoutes);
        }

        // dosageForm.setDataFields(dataFieldsDomainCapability.retrieve(data.getEplId(),
        // EntityType.DOSAGE_FORM));

        return dosageForm;
    }

    /**
     * convertDfUnits
     * @param data EplDosageFormDo
     * @return Collection<DosageFormUnitVo>
     */
    private Collection<DosageFormUnitVo> convertDfUnits(EplDosageFormDo data) {
        Collection<DosageFormUnitVo> dfUnits = new ArrayList<DosageFormUnitVo>();

        for (EplDfUnitDo dfUnitDo : data.getEplDfUnits()) {
            DosageFormUnitVo dosageFormUnit = new DosageFormUnitVo();
            DrugUnitVo unit = new DrugUnitVo();
            unit.setId(dfUnitDo.getKey().getDrugUnitEplIdFk().toString());

            if (dfUnitDo.getEplDrugUnit() != null) {
                unit.setValue(dfUnitDo.getEplDrugUnit().getName());
                
                if (dfUnitDo.getEplDrugUnit().getNdfDrugunitIen() != null) {
                    unit.setDrugUnitIen(dfUnitDo.getEplDrugUnit().getNdfDrugunitIen().toString());
                }
            }

            dosageFormUnit.setDrugUnit(unit);

            if (dfUnitDo.getPackageCode() != null) {
                dosageFormUnit
                        .setPackages(getPossibleDosagePackageVo(dfUnitDo
                                .getPackageCode()));
            }

            dfUnits.add(dosageFormUnit);
        }
        
        return dfUnits;

    }
    
    /**
     * Minimally copies data from the given {@link DataObject} into a
     * DosageFormVo.
     * <p>
     * The returned {@link ValueObject} likely only has enough data for the
     * {@link ValueObject#toShortString()} and {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the
     * DosageFormVo in a drop-down or multi-select list where a simple
     * text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls DosageFormVo.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated DosageFormVo
     */
    @Override
    protected DosageFormVo toMinimalValueObject(EplDosageFormDo data) {
        
        return toValueObject(data);
        
        //Figure out a better way?

        /*DosageFormVo dosageForm = new DosageFormVo();
        dosageForm.setId(String.valueOf(data.getEplId()));
        dosageForm.setDosageFormName(data.getDfName());
     
        if (data.getDosageformIen() != null) {
            dosageForm.setDosageFormIen(String.valueOf(data.getDosageformIen()));
        }
        
        dosageForm.setDosageFormName(data.getDfName());

        return dosageForm;*/
    }

    /**
     * Converts package name to PossibleDosagesPackageVo collection
     * 
     * @param packageName
     *            String
     * @return List<PossibleDosagesPackageVo>
     */
    private List<PossibleDosagesPackageVo> getPossibleDosagePackageVo(
            String packageName) {

        PossibleDosagesPackageVo possibleDosagesPackage = new PossibleDosagesPackageVo();
        List<PossibleDosagesPackageVo> lstPossibleDosages = new ArrayList<PossibleDosagesPackageVo>();

        if (BOTH.equalsIgnoreCase(packageName)) {
            possibleDosagesPackage.setValue(I_INPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);

            possibleDosagesPackage = new PossibleDosagesPackageVo();
            possibleDosagesPackage.setValue(O_OUTPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        } else if ((INPATIENT.equalsIgnoreCase(packageName))
                || (I_INPATIENT.equalsIgnoreCase(packageName))) {
            possibleDosagesPackage.setValue(I_INPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        } else if ((OUTPATIENT.equalsIgnoreCase(packageName))
                || (O_OUTPATIENT.equalsIgnoreCase(packageName))) {
            possibleDosagesPackage.setValue(O_OUTPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        }

        return (lstPossibleDosages);
    }

    /**
     * Converts PossibleDosagesPackageVo to String
     * 
     * @param packagesCode
     *            PossibleDosagesPackageVo
     * @return String
     */
    private String getPackageName(
            Collection<PossibleDosagesPackageVo> packagesCode) {
        String packageName = null;

        if (packagesCode.size() == 1) {
            for (PossibleDosagesPackageVo possibleDosagesPackage : packagesCode) {
                if (possibleDosagesPackage != null) {

                    if (I_INPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        packageName = INPATIENT;
                    } else if (O_OUTPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        packageName = OUTPATIENT;
                    }

                }
            }// end for
        } else if (packagesCode.size() == 2) {
            packageName = BOTH;
        }

        return packageName;
    }

    /**
     * setLocalMedicationRouteConverter
     * @param localMedicationRouteConverter
     *            localMedicationRouteConverter property
     */
    public void setLocalMedicationRouteConverter(
            LocalMedicationRouteConverter localMedicationRouteConverter) {
        this.localMedicationRouteConverter = localMedicationRouteConverter;
    }

}
