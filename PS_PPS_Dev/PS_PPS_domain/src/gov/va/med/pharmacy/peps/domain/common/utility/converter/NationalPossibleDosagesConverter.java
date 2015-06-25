/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NationalPossibleDosagesVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplNationalPossibleDosageDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Converts to/from {@link NationalPossibleDosagesVo} and {@link EplNationalPossibleDosageDo}.
 */
public class NationalPossibleDosagesConverter extends
    AssociationConverter<NationalPossibleDosagesVo, EplNationalPossibleDosageDo, EplProductDo> {

    /**
     * Fully copies data from the given {@link NationalPossibleDosagesVo} into a {@link DataObject}.
     * 
     * @param data {@link NationalPossibleDosagesVo} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplNationalPossibleDosageDo toDataObject(NationalPossibleDosagesVo data) {
        EplNationalPossibleDosageDo nationalPossibleDosage = new EplNationalPossibleDosageDo();
        nationalPossibleDosage.setBcmaUnitsPerDose(data.getBcmaUnitsPerDose());
        nationalPossibleDosage.setDispenseUnitsPerDose(data.getPossibleDosagesDispenseUnitsPerDose());
        nationalPossibleDosage.setDose(data.getDose());

        if (data.getPossibleDosagePackage().size() == 1) {
            for (PossibleDosagesPackageVo possibleDosagesPackage : data.getPossibleDosagePackage()) {
                if (possibleDosagesPackage != null) {
                    if (I_INPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        nationalPossibleDosage.setPackageName(INPATIENT);
                    } else if (O_OUTPATIENT.equalsIgnoreCase(possibleDosagesPackage.getValue())) {
                        nationalPossibleDosage.setPackageName(OUTPATIENT);
                    }

                }
            }// end for
        } else if (data.getPossibleDosagePackage().size() == 2) {
            nationalPossibleDosage.setPackageName(BOTH);
        }

        return nationalPossibleDosage;
    }

    /**
     * Fully copies data from the given {@link NationalPossibleDosagesVo} into a {@link DataObject}.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(NationalPossibleDosagesVo)} 
     * and then populate the parent data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data {@link NationalPossibleDosagesVo} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     */
    protected EplNationalPossibleDosageDo toDataObject(NationalPossibleDosagesVo data, EplProductDo parent, int sequence) {
        EplNationalPossibleDosageDo possibleDosage = convert(data);
        possibleDosage.setEplProduct(parent);

        return possibleDosage;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a {@link NationalPossibleDosagesVo}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated {@link NationalPossibleDosagesVo}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected NationalPossibleDosagesVo toValueObject(EplNationalPossibleDosageDo data) {
        NationalPossibleDosagesVo nationalPossibleDosage = new NationalPossibleDosagesVo();
        nationalPossibleDosage.setBcmaUnitsPerDose(data.getBcmaUnitsPerDose());
        nationalPossibleDosage.setPossibleDosagesDispenseUnitsPerDose(data.getDispenseUnitsPerDose());

        nationalPossibleDosage.setDose(data.getDose());

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

        nationalPossibleDosage.setPossibleDosagePackage(lstPossibleDosages);
        nationalPossibleDosage.setId(data.getId());

        return nationalPossibleDosage;
    }
}
