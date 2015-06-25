/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;


/**
 * Generate a list of Manufacturers for test cases
 */
public class ManufacturerGenerator extends VoGenerator<ManufacturerVo> {

    /**
     * Generate a list of Manufacturers for test cases
     * 
     * @return List of Manufacturers
     */
    protected List<ManufacturerVo> generate() {
        List<ManufacturerVo> testData = new ArrayList<ManufacturerVo>();
        ManufacturerVo manVo = new ManufacturerVo();
        manVo.setId("9991");
        manVo.setValue("LILLY");
        testData.add(manVo);
        
        ManufacturerVo manSecondVo = new ManufacturerVo();
        manSecondVo.setId("9992");
        manSecondVo.setValue("BRISTOL-MYERS SQUIBB");
        testData.add(manSecondVo);

        return testData;

    }
    
    /**
     * doInitialization for the ManufacturerGenerator
     */
    @Override
    protected void doInitialization() {
        
    }
}
