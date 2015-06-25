/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;


/**
 * Class to generate DoseUnitVos for testing
 */
public class DoseUnitGenerator extends VoGenerator<DoseUnitVo> {

    /**
     * Generaets a list of DoseUnitVo for testing
     * 
     * @return List of DoseUnitVo
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<DoseUnitVo> generate() {
        List<DoseUnitVo> doseUnitVos = new ArrayList<DoseUnitVo>();

        DoseUnitVo doseUnitVo = new DoseUnitVo();
        doseUnitVo.setDoseUnitName("mg");
        doseUnitVo.setFdbDoseUnit("mg");
        doseUnitVos.add(doseUnitVo);

        doseUnitVo = new DoseUnitVo();
        doseUnitVo.setDoseUnitName("g");
        doseUnitVo.setFdbDoseUnit("g");
        doseUnitVos.add(doseUnitVo);

        return doseUnitVos;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
