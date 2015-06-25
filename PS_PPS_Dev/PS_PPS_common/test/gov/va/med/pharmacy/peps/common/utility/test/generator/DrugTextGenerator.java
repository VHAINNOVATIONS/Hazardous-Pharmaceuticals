/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DrugTextType;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;


/**
 * Generate a list of drug text for test cases
 */
public class DrugTextGenerator extends VoGenerator<DrugTextVo> {

    /**
     * Generate a list of drug text for test cases
     * 
     * @return List of drug text
     */
    protected List<DrugTextVo> generate() {
        List<DrugTextVo> testData = new ArrayList<DrugTextVo>();

        DrugTextVo firstVo = new DrugTextVo();
        firstVo.setId(RandomGenerator.nextNumeric(I_10));
        firstVo.setValue("firstValue");
        firstVo.setTextLocal("Local First Value.");
        firstVo.setTextNational(firstVo.getTextLocal());
        testData.add(firstVo);

        DrugTextVo secondVo = new DrugTextVo();
        secondVo.setId(RandomGenerator.nextNumeric(I_10));
        secondVo.setValue("secondValue");
        secondVo.setTextNational("Text is mandatory.");
        secondVo.setTextLocal(secondVo.getTextNational());
        testData.add(secondVo);

        return testData;

    }

    /**
     * Create a Local Warning Label.
     * 
     * @return Local warning label
     */
    public DrugTextVo generateLocal() {
        DrugTextVo vo = new DrugTextVo();
        vo.setTextLocal("Do not take with alcohol.");
        vo.setItemStatus(ItemStatus.ACTIVE);
        vo.setDrugTextType(DrugTextType.LOCAL);

        vo.setValue("Local Name");

        return vo;
    }

    /**
     * doInitialization for DrugTextGenerator.
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Create a Local Warning Label.
     * 
     * @return Local warning label
     */
    public DrugTextVo generateNational() {
        DrugTextVo vo = new DrugTextVo();
        vo.setTextNational("Do not take with milk.");
        vo.setItemStatus(ItemStatus.ACTIVE);
        vo.setDrugTextType(DrugTextType.NATIONAL);

        vo.setValue("National Name");

        return vo;
    }
}
