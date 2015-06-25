/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.LocalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplDoseUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalPossibleDosageDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert to/from {@link LocalPossibleDosagesVo} and {@link EplLocalPossibleDosageDo}.
 */
public class LocalPossibleDosagesConverter extends
    AssociationConverter<LocalPossibleDosagesVo, EplLocalPossibleDosageDo, EplProductDo> {

    private DoseUnitConverter doseUnitConverter;

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplLocalPossibleDosageDo toDataObject(LocalPossibleDosagesVo data) {
        EplLocalPossibleDosageDo dosage = new EplLocalPossibleDosageDo();
        dosage.setBcmaUnitsPerDose(data.getBcmaUnitsPerDose());
        dosage.setLocalPossibleDosage(data.getLocalPossibleDosage());
        dosage.setOtherLanguageDosageName(data.getOtherLanguageDosageName());
        dosage.setNumericDose(Double.valueOf(data.getNumericDose()));
        EplDoseUnitDo unit = new EplDoseUnitDo();

        if (data.getDoseUnit() != null) {
            unit.setEplId(new Long(data.getDoseUnit().getId()));
            dosage.setEplDoseUnit(unit);
        }

        if (data.getPossibleDosagePackage().size() == 1) {
            for (PossibleDosagesPackageVo possibleDosagesPackage : data.getPossibleDosagePackage()) {
                if (possibleDosagesPackage != null) {
                    if (I_INPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        dosage.setPackageName(INPATIENT);
                    } else if (O_OUTPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        dosage.setPackageName(OUTPATIENT);
                    }

                }
            }// end for
        } else if (data.getPossibleDosagePackage().size() == 2) {
            dosage.setPackageName(BOTH);
        }

        return dosage;
    }

    /**
     * Fully copies data from the given LocalPossibleDosagesVo into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(LocalPossibleDosagesVo)} 
     * and then populate the parent data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data {@link LocalPossibleDosagesVo} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link LocalPossibleDosagesVo} to convert, 
     *  used only if order matters in this association
     * @return fully populated {@link DataObject}
     */
    protected EplLocalPossibleDosageDo toDataObject(LocalPossibleDosagesVo data, EplProductDo parent, int sequence) {
        EplLocalPossibleDosageDo possibleDosage = convert(data);
        possibleDosage.setEplProduct(parent);

        return possibleDosage;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a LocalPossibleDosagesVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated LocalPossibleDosagesVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected LocalPossibleDosagesVo toValueObject(EplLocalPossibleDosageDo data) {
        LocalPossibleDosagesVo localPossibleDosage = new LocalPossibleDosagesVo();
        localPossibleDosage.setBcmaUnitsPerDose(data.getBcmaUnitsPerDose());
        localPossibleDosage.setLocalPossibleDosage(data.getLocalPossibleDosage());
        localPossibleDosage.setNumericDose(new Double(data.getNumericDose()));

        localPossibleDosage.setDoseUnit(doseUnitConverter.convertMinimal(data.getEplDoseUnit()));
        localPossibleDosage.setOtherLanguageDosageName(data.getOtherLanguageDosageName());

        PossibleDosagesPackageVo possibleDosagesPackage = new PossibleDosagesPackageVo();
        List<PossibleDosagesPackageVo> lstPossibleDosages = new ArrayList<PossibleDosagesPackageVo>();

        if (BOTH.equalsIgnoreCase(data.getPackageName())) {

            possibleDosagesPackage.setValue(I_INPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);

            possibleDosagesPackage = new PossibleDosagesPackageVo();
            possibleDosagesPackage.setValue(O_OUTPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        } else if ((INPATIENT.equalsIgnoreCase(data.getPackageName()))
            || (I_INPATIENT.equalsIgnoreCase(data.getPackageName()))) {
            possibleDosagesPackage.setValue(I_INPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        } else if ((OUTPATIENT.equalsIgnoreCase(data.getPackageName()))
            || (O_OUTPATIENT.equalsIgnoreCase(data.getPackageName()))) {
            possibleDosagesPackage.setValue(O_OUTPATIENT);
            lstPossibleDosages.add(possibleDosagesPackage);
        }

        localPossibleDosage.setPossibleDosagePackage(lstPossibleDosages);
        localPossibleDosage.setId(data.getId());

        return localPossibleDosage;
    }

    /**
     * setDoseUnitConverter
     * 
     * @param doseUnitConverter doseUnitConverter property
     */
    public void setDoseUnitConverter(DoseUnitConverter doseUnitConverter) {
        this.doseUnitConverter = doseUnitConverter;
    }
}
