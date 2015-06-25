/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;


/**
 * List of units to use in test cases
 */
public class UnitGenerator extends VoGenerator<DrugUnitVo> {

    /**
     * Generate a list of Units
     * 
     * @return List of Units
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<DrugUnitVo> generate() {
        List<DrugUnitVo> list = new ArrayList<DrugUnitVo>();

        DrugUnitVo unit1 = new DrugUnitVo();
        unit1.setId("999161");
        unit1.setValue("MG");
        list.add(unit1);

        DrugUnitVo unit2 = new DrugUnitVo();
        unit2.setId("99932");
        unit2.setValue("GAL");
        list.add(unit2);
        
        return list;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
