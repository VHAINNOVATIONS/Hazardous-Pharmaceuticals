/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;


/**
 * Generator Class for MedicationInstructionVos
 *  
 */
public class MedicationInstructionGenerator extends VoGenerator<MedicationInstructionVo> {

    /**
     * Generates a list of MedicationInstructionVo for testing
     * 
     * @return List of MedicationInstructionVo
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<MedicationInstructionVo> generate() {
        List<MedicationInstructionVo> testData = new ArrayList<MedicationInstructionVo>();

        MedicationInstructionVo firstVo = new MedicationInstructionVo();
        firstVo.setId(RandomGenerator.nextNumeric(I_10));
        firstVo.setValue("NAME1");
        firstVo.setMedInstructionExpansion("Expansion is not really mandatory.");
        testData.add(firstVo);

        MedicationInstructionVo secondVo = new MedicationInstructionVo();
        secondVo.setId(RandomGenerator.nextNumeric(I_10));
        secondVo.setValue("NAME2");
        secondVo.setMedInstructionExpansion("Expansion is mandatory.");
        testData.add(secondVo);

        return testData;

    }
    
    /**
     * doInitialization for the MedicationInstructionGenerator.
     */
    @Override
    protected void doInitialization() {
        
    }
}
