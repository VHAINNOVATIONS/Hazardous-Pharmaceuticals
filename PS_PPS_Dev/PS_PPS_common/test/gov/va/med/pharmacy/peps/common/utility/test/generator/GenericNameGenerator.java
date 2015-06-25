/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;


/**
 * Generate a list of Routes to use in test cases
 */
public class GenericNameGenerator extends VoGenerator<GenericNameVo> {

    /**
     * Generate a list of Routes
     * 
     * @return List of Routes
     */
    protected List<GenericNameVo> generate() {
        List<GenericNameVo> genericNameVos = new ArrayList<GenericNameVo>();

        // 1
        GenericNameVo genericName = new GenericNameVo();
        genericName.setId("244");
        genericName.setValue("DIGOXIN");
        genericName.setVuid("3234");
        genericNameVos.add(genericName);

        // 2
        genericName = new GenericNameVo();
        genericName.setId("1");
        genericName.setValue("ATROPINE");
        genericName.setVuid("2323");
        genericNameVos.add(genericName);

        // 3
        genericName = new GenericNameVo();
        genericName.setId("2");
        genericName.setValue("CODEINE");
        genericName.setVuid("23423");
        genericNameVos.add(genericName);

        return genericNameVos;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
