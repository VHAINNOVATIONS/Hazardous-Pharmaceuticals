/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;


/**
 * Generate a list of drug units for test cases
 */
public class DrugUnitGenerator extends VoGenerator<DrugUnitVo> {

    /**
     * Generate a list of drug units for test cases
     * 
     * @return List of drug units
     */
    protected List<DrugUnitVo> generate() {
        List<DrugUnitVo> testData = new ArrayList<DrugUnitVo>();
        
        DrugUnitVo firstVo = new DrugUnitVo();
        firstVo.setId("99932");
        firstVo.setValue("GAL");
        testData.add(firstVo);
        
        DrugUnitVo secondVo = new DrugUnitVo();
        secondVo.setId("999154");
        secondVo.setValue("GM");
        testData.add(secondVo);

        return testData;

    }
    
    /**
     * doInitialization in the DrugUnitGenerator.
     */
    @Override
    protected void doInitialization() {
        
    }
}
