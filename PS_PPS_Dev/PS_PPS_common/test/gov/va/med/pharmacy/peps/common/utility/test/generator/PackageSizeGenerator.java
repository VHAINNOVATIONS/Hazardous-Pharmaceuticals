/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PackageSizeVo;


/**
 * Generate a list of Manufacturers for test cases
 */
public class PackageSizeGenerator extends VoGenerator<PackageSizeVo> {

    /**
     * Generate a list of Manufacturers for test cases
     * 
     * @return List of Manufacturers
     */
    protected List<PackageSizeVo> generate() {
        List<PackageSizeVo> testData = new ArrayList<PackageSizeVo>();
        PackageSizeVo packSizeVo = new PackageSizeVo();
        packSizeVo.setId("1");
        packSizeVo.setValue("1 FLO");
        testData.add(packSizeVo);
        
        PackageSizeVo secondPackSizeVo = new PackageSizeVo();
        secondPackSizeVo.setId("2");
        secondPackSizeVo.setValue("NOT STATED");
        testData.add(secondPackSizeVo);

        return testData;

    }
    
    /**
     * doInitialization for the PackageSizeGenerator
     */
    @Override
    protected void doInitialization() {
        
    }
}
