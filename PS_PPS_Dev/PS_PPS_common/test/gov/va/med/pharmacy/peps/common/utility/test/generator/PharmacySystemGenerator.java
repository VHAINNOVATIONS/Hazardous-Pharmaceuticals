/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;


/**
 * Generator class for PharmacySystemVos
 * 
 */
public class PharmacySystemGenerator extends VoGenerator<PharmacySystemVo> {

    /**
     * Generates a list of PharmacySystemVos for testing
     * 
     * @return List of PharmacySystemVo
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<PharmacySystemVo> generate() {

        List<PharmacySystemVo> testData = new ArrayList<PharmacySystemVo>();
        PharmacySystemVo pharmacySystemVo = new PharmacySystemVo();
        pharmacySystemVo.setPharmacySystem("SITE NAME1");
        pharmacySystemVo.setPsPmisPrinter("PMIS PRINTER1");
        pharmacySystemVo.setPsPmisSectionDelete("PMIS,SECTION,DELETE1");
        testData.add(pharmacySystemVo);

        pharmacySystemVo = new PharmacySystemVo();
        pharmacySystemVo.setPharmacySystem("SITE NAME2");
        pharmacySystemVo.setPsPmisPrinter("PMIS PRINTER2");
        pharmacySystemVo.setPsPmisSectionDelete("PMIS,SECTION,DELETE2");
        testData.add(pharmacySystemVo);

        return testData;

    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
