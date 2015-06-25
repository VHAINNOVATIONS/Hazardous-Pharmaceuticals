/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;


/**
 * Generate a list of DrugClasses
 */
public class DrugClassGenerator extends VoGenerator<DrugClassGroupVo> {

    /**
     * Generate a list of DrugClasses
     * 
     * @return List of DrugClasses
     */
    protected List<DrugClassGroupVo> generate() {
        List<DrugClassGroupVo> testData = new ArrayList<DrugClassGroupVo>();

        DrugClassGroupVo vo = new DrugClassGroupVo();
        DrugClassVo drugClassification = new DrugClassVo();

        drugClassification.setId("-1");
        drugClassification.setCode("AB123");
        drugClassification.setVuid("VUID");
        drugClassification.setClassification("CLASSIFICATION");

        DrugClassificationTypeVo drugClassificationTypeVo = new DrugClassificationTypeVo();
        drugClassificationTypeVo.setValue("1-1");

        drugClassification.setClassificationType(drugClassificationTypeVo);

        vo.setDrugClass(drugClassification);
        vo.setPrimary(true);

        testData.add(vo);

        return testData;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
