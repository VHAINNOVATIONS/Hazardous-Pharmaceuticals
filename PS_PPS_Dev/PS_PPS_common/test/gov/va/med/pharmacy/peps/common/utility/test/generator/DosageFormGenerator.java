/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;


/**
 * Generate a list of DosageForms
 */
public class DosageFormGenerator extends VoGenerator<DosageFormVo> {

    /**
     * Generate a list of DosageForms
     * 
     * @return List of DosageForms
     */
    protected List<DosageFormVo> generate() {
        List<DosageFormVo> doseForms = new ArrayList<DosageFormVo>();

        DosageFormVo doseForm = new DosageFormVo();
        doseForm.setId("99963");
        doseForm.setDosageFormName("TAB");
        
        //doseForm.setValue("TAB");
        doseForms.add(doseForm);

        doseForm = new DosageFormVo();
        doseForm.setId("9994");
        doseForm.setDosageFormName("Bottle");
        
        //doseForm.setValue("Bottle");
        doseForms.add(doseForm);

        return doseForms;
    }    
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
