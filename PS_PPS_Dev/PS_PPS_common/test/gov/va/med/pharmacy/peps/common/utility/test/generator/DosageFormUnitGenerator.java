/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


/**
 * Source file created in 2008 by Southwest Research Institute
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DosageFormUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;


/**
 * generate a list of dosage form units
 */
public class DosageFormUnitGenerator extends VoGenerator<DosageFormUnitVo> {

    /**
     * Generate a list of DosageFormUnitVo
     * 
     * @return List of DosageFormUnitVo
     * 
     */
    @Override
    protected List<DosageFormUnitVo> generate() {
        List<DosageFormUnitVo> dosageFormUnits = new ArrayList<DosageFormUnitVo>();

        DosageFormUnitVo dosageFormUnit1 = new DosageFormUnitVo();
        dosageFormUnit1.setDrugUnit(new DrugUnitGenerator().getFirst());
        Collection<PossibleDosagesPackageVo> pkgs1 = new ArrayList<PossibleDosagesPackageVo>();
        PossibleDosagesPackageVo pkg1 = new PossibleDosagesPackageGenerator().getFirst();
        pkgs1.add(pkg1);
        dosageFormUnit1.setPackages(pkgs1);

        DosageFormUnitVo dosageFormUnit2 = new DosageFormUnitVo();
        List<DrugUnitVo> drugUnits = new DrugUnitGenerator().getList();
        dosageFormUnit2.setDrugUnit(drugUnits.get(drugUnits.size() - 1));
        Collection<PossibleDosagesPackageVo> lstPkgs = new PossibleDosagesPackageGenerator().getList();

        dosageFormUnit2.setPackages(lstPkgs);

        dosageFormUnits.add(dosageFormUnit1);
        dosageFormUnits.add(dosageFormUnit2);
        
        return dosageFormUnits;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }

}
