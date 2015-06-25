/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PossibleDosagesPackageVo;


/**
 * generate list of Possible dosages package vo
 */
public class PossibleDosagesPackageGenerator extends VoGenerator<PossibleDosagesPackageVo> {

    /**
     * Generate a list of PossibleDosagesPackageVo
     * 
     * @return List of PossibleDosagesPackageVo
     * 
     */
    @Override
    protected List<PossibleDosagesPackageVo> generate() {
        List<PossibleDosagesPackageVo> packages = new ArrayList<PossibleDosagesPackageVo>();
        PossibleDosagesPackageVo pkg1 = new PossibleDosagesPackageVo();
        pkg1.setValue("I-Inpatient");
        PossibleDosagesPackageVo pkg2 = new PossibleDosagesPackageVo();
        pkg2.setValue("O-Outpatient");

        packages.add(pkg1);
        packages.add(pkg2);

        return packages;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
