/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitPerDoseVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;


/**
 * generate a list of dispense unit per dose vo
 */
public class DispenseUnitPerDoseGenerator extends VoGenerator<DispenseUnitPerDoseVo> {

    /**
     * Generate a list of DispenseUnitPerDoseVo
     * 
     * @return List of DispenseUnitPerDoseVo
     * 
     */
    @Override
    protected List<DispenseUnitPerDoseVo> generate() {
        List<DispenseUnitPerDoseVo> dispUnitsPerDose = new ArrayList<DispenseUnitPerDoseVo>();
        DispenseUnitPerDoseVo dispUnitPerDose1 = new DispenseUnitPerDoseVo();
        dispUnitPerDose1.setStrDispenseUnitPerDose("1.0");
        Collection<PossibleDosagesPackageVo> pkgs1 = new ArrayList<PossibleDosagesPackageVo>();
        PossibleDosagesPackageVo pkg1 = new PossibleDosagesPackageGenerator().getFirst();
        pkgs1.add(pkg1);
        dispUnitPerDose1.setPackages(pkgs1);

        // add a second value
        DispenseUnitPerDoseVo dispUnitPerDose2 = new DispenseUnitPerDoseVo();
        dispUnitPerDose2.setStrDispenseUnitPerDose("2.0");
        Collection<PossibleDosagesPackageVo> pkgs2 = new ArrayList<PossibleDosagesPackageVo>();
        PossibleDosagesPackageVo pkg2 = new PossibleDosagesPackageGenerator().getList().get(1);
        pkgs2.add(pkg2);
        dispUnitPerDose2.setPackages(pkgs2);

        dispUnitsPerDose.add(dispUnitPerDose1);
        dispUnitsPerDose.add(dispUnitPerDose2);

        return dispUnitsPerDose;

    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
