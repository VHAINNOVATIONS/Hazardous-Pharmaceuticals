/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;


/**
 * Generate a list of Manufacturers for test cases
 */
public class PackageTypeGenerator extends VoGenerator<PackageTypeVo> {

    /**
     * Generate a list of Manufacturers for test cases
     * 
     * @return List of Manufacturers
     */
    protected List<PackageTypeVo> generate() {
        List<PackageTypeVo> testData = new ArrayList<PackageTypeVo>();
        PackageTypeVo packSizeVo = new PackageTypeVo();
        packSizeVo.setId("99940");
        packSizeVo.setValue("INJECTOR,AUTOMATIC");
        testData.add(packSizeVo);
        
        PackageTypeVo secondPackTypeVo = new PackageTypeVo();
        secondPackTypeVo.setId("99947");
        secondPackTypeVo.setValue("AMP FOR MYELOGRAM TRAY");
        testData.add(secondPackTypeVo);

        return testData;

    }
    
    /**
     * doInitialization for the PackageType Generator.
     */
    @Override
    protected void doInitialization() {
        
    }
}
